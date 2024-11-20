package com.orbitinsight.pipeline.channel;

import com.google.common.collect.Lists;
import com.orbitinsight.core.pipeline.Channel;
import com.orbitinsight.model.LogInfo;
import com.orbitinsight.pipeline.sink.KafkaLogsSink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author dingjiefei
 */
@Component
public class LogsFliterChannel extends Channel<List<LogInfo>, List<LogInfo>> {

    @Autowired
    private KafkaLogsSink kafkaLogsSink;

    @Override
    protected List<LogInfo> doExecute(List<LogInfo> list) throws Exception {

        return list;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Collection<com.orbitinsight.core.pipeline.Component> getDownStreams() {
        return Lists.newArrayList(kafkaLogsSink);
    }
}
