package com.orbitinsight.core.bean;

import com.alibaba.fastjson2.JSON;
import com.orbitinsight.handler.SignalHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author dingjiefei
 */
@Slf4j
public class KafkaConsumerBean<K, V> extends AbstractManualBean {
    private final KafkaConsumer<K, V> consumer;
    private final List<String> topics;
    private final Integer parallel;
    private final SignalHandler handler;

    private final AtomicBoolean closed = new AtomicBoolean(false);

    public KafkaConsumerBean(String beanName, Properties properties, List<String> topics, Integer parallel, SignalHandler handler) {
        super(beanName);
        this.consumer = new KafkaConsumer<>(properties);
        this.topics = topics;
        this.parallel = Objects.requireNonNullElse(parallel, 1);
        this.handler = handler;
    }

    public void start() {
        if (CollectionUtils.isEmpty(topics)) {
            throw new IllegalStateException("Topic list is empty");
        }
        if (parallel < 1){
            throw new IllegalStateException("Parallel must be greater than 0");
        }
        for (int i = 0; i < parallel; i++) {
            doStart();
        }
    }

    private void doStart() {
        Thread thread = new Thread(() -> {
            consumer.subscribe(topics);
            int errorTimes = 0;
            while (!closed.get()) {
                try {
                    ConsumerRecords<K, V> records = consumer.poll(Duration.ofMillis(1000));
                    if (IterableUtils.isEmpty(records)) {
                        continue;
                    }
                    try {
                        handler.handle(records);
                        ack(records);
                    } catch (Exception e) {
                        log.warn("Handling error, error times:{}, message:{}", errorTimes, e.getMessage());
                        errorTimes++;
                        if (errorTimes > 3) {
                            ack(records);
                            errorTimes = 0;
                            log.warn("Error times greater than 3, force commit!");
                            Map<K, List<Long>> offsetMap = new HashMap<>();
                            for (ConsumerRecord<K, V> record : records) {
                                offsetMap.computeIfAbsent(record.key(), k -> new LinkedList<>()).add(record.offset());
                            }
                            log.warn("Error records: {}", JSON.toJSONString(offsetMap));
                        }
                    }
                } catch (Exception e) {
                    log.warn("Error while processing records: {}", e.getMessage());
                }
            }
        });
        thread.start();
    }

    private void ack(ConsumerRecords<K, V> records) {
        Map<TopicPartition, OffsetAndMetadata> commits = new HashMap<>();
        for (TopicPartition part : records.partitions()) {
            commits.put(part, new OffsetAndMetadata(records.records(part)
                    .get(records.records(part).size() - 1).offset() + 1));
        }
        consumer.commitSync(commits);
    }


    @Override
    public Class<?> beanClass() {
        return getClass();
    }

    @Override
    public void destroy() {
        consumer.close();
        closed.set(true);
    }
}
