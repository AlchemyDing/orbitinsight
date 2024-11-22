package com.orbitinsight.pipeline.processor;

import com.orbitinsight.core.disruptor.DisruptorPublisher;
import com.orbitinsight.model.LogInfo;
import com.orbitinsight.pipeline.AbstractEventHandler;

import java.util.Collection;
import java.util.List;

/**
 * @author dingjiefei
 */
public class LogsFilterProcessor extends AbstractEventHandler<List<LogInfo>, List<LogInfo>> {
    protected LogsFilterProcessor(long ordinal, long numberOfConsumers) {
        super(ordinal, numberOfConsumers);
    }

    @Override
    protected List<LogInfo> doExecute(List<LogInfo> list) throws Exception {
        return list;
    }

    @Override
    public Collection<DisruptorPublisher<List<LogInfo>>> getDownStreams() {
        return List.of();
    }
}
