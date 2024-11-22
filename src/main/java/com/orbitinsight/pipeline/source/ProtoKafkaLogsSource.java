package com.orbitinsight.pipeline.source;

import com.google.common.collect.Lists;
import com.orbitinsight.core.disruptor.DisruptorPublisher;
import com.orbitinsight.pipeline.AbstractEventHandler;
import com.orbitinsight.utils.BeanUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author dingjiefei
 */
public class ProtoKafkaLogsSource extends AbstractEventHandler<ConsumerRecords<String, byte[]>, List<byte[]>> {
    public ProtoKafkaLogsSource(long ordinal, long numberOfConsumers) {
        super(ordinal, numberOfConsumers);
    }


    @Override
    protected List<byte[]> doExecute(ConsumerRecords<String, byte[]> records) throws Exception {
        List<byte[]> result = new ArrayList<>();
        for (ConsumerRecord<String, byte[]> record : records) {
            result.add(record.value());
        }
        return result;
    }

    @Override
    public Collection<DisruptorPublisher<List<byte[]>>> getDownStreams() {
        return Lists.newArrayList((DisruptorPublisher<List<byte[]>>) BeanUtil.getBean("protoLogsDeserializePublisher", DisruptorPublisher.class));
    }
}
