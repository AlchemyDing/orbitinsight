package com.orbitinsight.pipeline;

import com.google.common.collect.Lists;
import com.orbitinsight.core.pipeline.Component;
import com.orbitinsight.core.pipeline.Source;
import com.orbitinsight.pipeline.channel.ProtoLogsDeserializerChannel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author dingjiefei
 */
@org.springframework.stereotype.Component
public class ProtoKafkaLogsSource extends Source<ConsumerRecords<String, byte[]>, List<byte[]>> {

    @Autowired
    private ProtoLogsDeserializerChannel protoLogsDeserializerChannel;

    @Override
    protected List<byte[]> doExecute(ConsumerRecords<String, byte[]> records) {
        List<byte[]> result = new ArrayList<>();
        for (ConsumerRecord<String, byte[]> record : records) {
            result.add(record.value());
        }
        return result;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public Collection<Component> getDownStreams() {
        return Lists.newArrayList(protoLogsDeserializerChannel);
    }
}
