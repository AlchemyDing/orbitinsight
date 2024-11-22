package com.orbitinsight;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.orbitinsight.core.bean.KafkaConsumerBean;
import com.orbitinsight.core.bean.KafkaProducerBean;
import com.orbitinsight.core.disruptor.DisruptorPublisher;
import com.orbitinsight.domain.SinkConfig;
import com.orbitinsight.domain.SinkFeature;
import com.orbitinsight.domain.SourceConfig;
import com.orbitinsight.mapper.SinkConfigMapper;
import com.orbitinsight.mapper.SinkFeatureMapper;
import com.orbitinsight.mapper.SourceConfigMapper;
import com.orbitinsight.model.SignalType;
import com.orbitinsight.model.SinkType;
import com.orbitinsight.model.SourceType;
import com.orbitinsight.utils.BeanUtil;
import com.orbitinsight.utils.CacheUtil;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * @author dingjiefei
 */
@Component
public class OrbitInsightRunner implements CommandLineRunner {

    @Autowired
    private SourceConfigMapper sourceConfigMapper;

    @Autowired
    private SinkConfigMapper sinkConfigMapper;

    @Autowired
    private SinkFeatureMapper sinkFeatureMapper;

    @Resource(name = "protoKafkaLogsPublisher")
    private DisruptorPublisher<ConsumerRecords> disruptorPublisher;

    @Override
    public void run(String... args) throws Exception {
        initSinkFeature();
        createSink();
        createSource();
    }

    private void initSinkFeature() {
        List<SinkFeature> sinkFeatures = sinkFeatureMapper.queryAll();
        CacheUtil.addSinkFeature(sinkFeatures);
    }

    private void createSink() {
        List<SinkConfig> sinkConfigs = sinkConfigMapper.queryAll();
        for (SinkConfig config : sinkConfigs) {
            Integer signalType = config.getSignalType();
            Integer type = config.getType();
            if (SignalType.LOGS.getType().equals(signalType)) {
                if (SinkType.KAFKA.getType().equals(type)) {
                    //kafka
                    JSONObject jsonObject = JSON.parseObject(config.getProperties());
                    Properties properties = new Properties();
                    properties.putAll(jsonObject.getJSONObject("properties"));
                    // key,value序列化（必须）：key.serializer，value.serializer
                    properties.putIfAbsent(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
                    properties.putIfAbsent(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
                    // batch.size：批次大小，默认16K
                    properties.putIfAbsent(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
                    // linger.ms：等待时间，默认0
                    properties.putIfAbsent(ProducerConfig.LINGER_MS_CONFIG, 1);
                    // RecordAccumulator：缓冲区大小，默认32M：buffer.memory
                    properties.putIfAbsent(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
                    // compression.type：压缩，默认none，可配置值gzip、snappy、lz4和zstd
                    properties.putIfAbsent(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
                    // enable.idempotence：幂等性
                    properties.putIfAbsent(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
                    KafkaProducerBean<String, String> producerBean = (KafkaProducerBean<String, String>) BeanUtil.createBean(new KafkaProducerBean<>(config.getName(), properties));
                    producerBean.init();
                }
            }
        }
    }

    private void createSource() {
        List<SourceConfig> sourceConfigs = sourceConfigMapper.queryAll();
        for (SourceConfig config : sourceConfigs) {
            Integer signalType = config.getSignalType();
            Integer type = config.getType();
            if (SignalType.LOGS.getType().equals(signalType)) {
                // logs
                if (SourceType.KAFKA.getType().equals(type)) {
                    //kafka
                    JSONObject jsonObject = JSON.parseObject(config.getProperties());
                    Properties properties = new Properties();
                    properties.putAll(jsonObject.getJSONObject("properties"));
                    properties.putIfAbsent(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                    properties.putIfAbsent(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
                    Integer parallel = jsonObject.getInteger("parallel");
                    List<String> topics = jsonObject.getList("topics", String.class);
                    KafkaConsumerBean<String, byte[]> consumerBean = (KafkaConsumerBean<String, byte[]>) BeanUtil.createBean(new KafkaConsumerBean<>(config.getName(), properties, topics, parallel, disruptorPublisher));
                    consumerBean.start();
                }
            }
        }
    }
}
