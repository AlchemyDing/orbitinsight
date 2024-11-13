package com.orbitinsight.pipeline;

import com.orbitinsight.core.pipeline.Channel;
import com.orbitinsight.core.pipeline.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author dingjiefei
 */
@org.springframework.stereotype.Component
public class ProtoLogsDeserializerChannel extends Channel<List<byte[]>,Object> {
    @Override
    protected Object doExecute(List<byte[]> list) {
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
