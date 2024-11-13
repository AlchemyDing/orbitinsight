package com.orbitinsight.pipeline;

import com.orbitinsight.core.EncodingType;
import com.orbitinsight.core.OrbitInsightProperties;
import com.orbitinsight.core.SourceInfo;
import com.orbitinsight.core.SourceType;
import com.orbitinsight.core.bean.KafkaConsumerBean;
import com.orbitinsight.core.pipeline.Component;
import com.orbitinsight.core.pipeline.Source;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dingjiefei
 */
@org.springframework.stereotype.Component
public class ProtoLogsSource extends Source<List<byte[]>, Object> {

    @Autowired
    private OrbitInsightProperties orbitInsightProperties;

    private List<KafkaConsumerBean<String, byte[]>> kafkaConsumerBeans;

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public Collection<Component> getDownStreams() {
        return List.of();
    }

    @Override
    protected Object doExecute(List<byte[]> o) {
        return null;
    }

    @Override
    public void init(String config) {
        List<SourceInfo> logs = orbitInsightProperties.getLogs();
        Map<SourceType, List<SourceInfo>> sourceMap = logs.stream().filter(sourceInfo -> sourceInfo.getEncoding().equals(EncodingType.OTLP_PROTO)).collect(Collectors.groupingBy(SourceInfo::getSourceType));
        List<SourceInfo> kafkaSources = sourceMap.get(SourceType.KAFKA);
        if (CollectionUtils.isNotEmpty(kafkaSources)){

        }
    }

    @Override
    public void startup() {

    }
}
