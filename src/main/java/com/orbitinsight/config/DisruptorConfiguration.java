package com.orbitinsight.config;

import cn.hutool.core.thread.NamedThreadFactory;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.orbitinsight.core.disruptor.DisruptorEvent;
import com.orbitinsight.core.disruptor.DisruptorEventFactory;
import com.orbitinsight.core.disruptor.DisruptorPublisher;
import com.orbitinsight.pipeline.processor.ProtoLogsDeserializeProcessor;
import com.orbitinsight.pipeline.source.ProtoKafkaLogsSource;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author dingjiefei
 */
@Configuration
public class DisruptorConfiguration {

    private static final int BUFFER_SIZE = 1024 * 16;

    @Bean(value = "protoKafkaLogsPublisher", destroyMethod = "destroy")
    public DisruptorPublisher<ConsumerRecords<String, byte[]>> protoKafkaLogsPublisher() {
        Disruptor<DisruptorEvent<ConsumerRecords<String, byte[]>>> disruptor = new Disruptor<>(new DisruptorEventFactory<>(), BUFFER_SIZE, new NamedThreadFactory("protoKafkaLogsPublisher", false), ProducerType.SINGLE, new SleepingWaitStrategy());
        disruptor.handleEventsWith(new ProtoKafkaLogsSource(0, 1));
        disruptor.start();
        return new DisruptorPublisher<>(disruptor);
    }

    @Bean(value = "protoLogsDeserializePublisher", destroyMethod = "destroy")
    public DisruptorPublisher<List<byte[]>> protoLogsDeserializePublisher() {
        Disruptor<DisruptorEvent<List<byte[]>>> disruptor = new Disruptor<>(new DisruptorEventFactory<>(), BUFFER_SIZE, new NamedThreadFactory("protoLogsDeserializePublisher", false), ProducerType.SINGLE, new SleepingWaitStrategy());
        disruptor.handleEventsWith(new ProtoLogsDeserializeProcessor(0, 1));
        disruptor.start();
        return new DisruptorPublisher<>(disruptor);
    }
}
