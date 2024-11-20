package com.orbitinsight.pipeline.sink;

import com.orbitinsight.core.pipeline.Sink;
import com.orbitinsight.model.LogInfo;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class KafkaLogsSink extends Sink<List<LogInfo>, Void> {

    @Override
    protected Void doExecute(List<LogInfo> o) throws Exception {
        return null;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Collection<com.orbitinsight.core.pipeline.Component> getDownStreams() {
        return Collections.emptyList();
    }
}
