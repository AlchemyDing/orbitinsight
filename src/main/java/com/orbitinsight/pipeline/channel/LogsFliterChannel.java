package com.orbitinsight.pipeline.channel;

import com.orbitinsight.core.pipeline.Channel;
import com.orbitinsight.model.LogInfo;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author dingjiefei
 */
@Component
public class LogsFliterChannel extends Channel<List<LogInfo>, List<LogInfo>> {
    @Override
    protected List<LogInfo> doExecute(List<LogInfo> list) throws Exception {

        return list;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public Collection<com.orbitinsight.core.pipeline.Component> getDownStreams() {
        return List.of();
    }
}
