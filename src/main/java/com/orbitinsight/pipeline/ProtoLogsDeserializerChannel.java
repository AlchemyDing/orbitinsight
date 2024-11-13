package com.orbitinsight.pipeline;

import com.orbitinsight.core.pipeline.Channel;
import com.orbitinsight.core.pipeline.Component;
import org.apache.kafka.shaded.com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.shaded.io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceRequest;

import java.util.Collection;
import java.util.List;

/**
 * @author dingjiefei
 */
@org.springframework.stereotype.Component
public class ProtoLogsDeserializerChannel extends Channel<List<byte[]>,Object> {
    @Override
    protected Object doExecute(List<byte[]> list) throws InvalidProtocolBufferException {
        for (byte[] bytes : list) {
            ExportLogsServiceRequest request = ExportLogsServiceRequest.parseFrom(bytes);
        }
        return null;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public Collection<Component> getDownStreams() {
        return List.of();
    }
}
