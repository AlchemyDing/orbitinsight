package com.orbitinsight.pipeline;

import com.orbitinsight.core.pipeline.Channel;
import com.orbitinsight.core.pipeline.Component;

import java.util.Collection;
import java.util.List;

@org.springframework.stereotype.Component
public class ProtoLogsDeserializerChannel extends Channel<byte[],Object> {
    @Override
    protected Object doExecute(byte[] o) {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Collection<Component> getDownStreams() {
        return List.of();
    }
}
