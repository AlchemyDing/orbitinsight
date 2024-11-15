package com.orbitinsight;

import com.orbitinsight.core.EncodingType;
import com.orbitinsight.core.OrbitInsightSourceProperties;
import com.orbitinsight.core.SourceInfo;
import com.orbitinsight.core.SourceType;
import com.orbitinsight.core.bean.BeanCreator;
import com.orbitinsight.core.bean.KafkaConsumerBean;
import com.orbitinsight.pipeline.ProtoKafkaLogsSource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dingjiefei
 */
@Component
public class OrbitInsightRunner implements CommandLineRunner {

    @Autowired
    private OrbitInsightSourceProperties orbitInsightSourceProperties;

    @Autowired
    private ProtoKafkaLogsSource protoKafkaLogsSource;

    @Override
    public void run(String... args) throws Exception {
        List<SourceInfo> logs = orbitInsightSourceProperties.getLogs();
        Map<SourceType, List<SourceInfo>> sourceMap = logs.stream().filter(sourceInfo -> sourceInfo.getEncoding().equals(EncodingType.OTLP_PROTO)).collect(Collectors.groupingBy(SourceInfo::getSourceType));
        List<SourceInfo> kafkaSources = sourceMap.get(SourceType.KAFKA);
        if (CollectionUtils.isNotEmpty(kafkaSources)) {
            for (SourceInfo kafkaSource : kafkaSources) {
                KafkaConsumerBean<String, byte[]> consumerBean = (KafkaConsumerBean<String, byte[]>) BeanCreator.createBean(new KafkaConsumerBean<>(kafkaSource.getName(), kafkaSource.getProperties(), kafkaSource.getTopics(), kafkaSource.getParallel(), protoKafkaLogsSource));
                consumerBean.start();
            }
        }
    }
}
