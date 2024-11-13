package com.orbitinsight;

import com.orbitinsight.core.SourceInfo;
import com.orbitinsight.core.SourceType;
import com.orbitinsight.core.bean.KafkaConsumerBean;
import com.orbitinsight.handler.LogsHandler;
import com.orbitinsight.core.OrbitInsightProperties;
import com.orbitinsight.core.bean.BeanCreator;
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

//    @Autowired
//    private OrbitInsightProperties orbitInsightProperties;
//
//    @Autowired
//    private LogsHandler logsHandler;

    @Override
    public void run(String... args) throws Exception {
//        // 初始化kafka
//        List< SourceInfo> logs = orbitInsightProperties.getLogs();
//        Map<SourceType, List<SourceInfo>> sourceMap = logs.stream().collect(Collectors.groupingBy(SourceInfo::getSourceType));
//        List<SourceInfo> kafkaSources = sourceMap.get(SourceType.KAFKA);
//        for (SourceInfo kafkaSourceInfo : kafkaSources) {
//            KafkaConsumerBean consumerBean = (KafkaConsumerBean)BeanCreator.createBean(new KafkaConsumerBean<>(kafkaSourceInfo.getName(), kafkaSourceInfo.getProperties(), kafkaSourceInfo.getTopics(), 1, logsHandler));
//            consumerBean.start();
//        }

    }
}
