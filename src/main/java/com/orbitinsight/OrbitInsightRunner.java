package com.orbitinsight;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.orbitinsight.core.bean.BeanCreator;
import com.orbitinsight.core.bean.KafkaConsumerBean;
import com.orbitinsight.domain.SourceConfig;
import com.orbitinsight.mapper.SourceConfigMapper;
import com.orbitinsight.pipeline.ProtoKafkaLogsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

/**
 * @author dingjiefei
 */
@Component
public class OrbitInsightRunner implements CommandLineRunner {

    @Autowired
    private ProtoKafkaLogsSource protoKafkaLogsSource;

    @Autowired
    private SourceConfigMapper sourceConfigMapper;

    @Override
    public void run(String... args) throws Exception {
        List<SourceConfig> list = sourceConfigMapper.queryAll();
        for (SourceConfig config : list) {
            Integer signalType = config.getSignalType();
            Integer type = config.getType();
            if (signalType == 1) {
                // logs
                if (type == 1) {
                    //kafka
                    JSONObject jsonObject = JSON.parseObject(config.getProperties());
                    Properties properties = new Properties();
                    properties.putAll(jsonObject.getJSONObject("properties"));
                    properties.putIfAbsent("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                    properties.putIfAbsent("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
                    Integer parallel = jsonObject.getInteger("parallel");
                    List<String> topics = jsonObject.getList("topics", String.class);
                    KafkaConsumerBean<String, byte[]> consumerBean = (KafkaConsumerBean<String, byte[]>) BeanCreator.createBean(new KafkaConsumerBean<>(config.getName(), properties, topics, parallel, protoKafkaLogsSource));
                    consumerBean.start();
                }
            }
        }
    }
}
