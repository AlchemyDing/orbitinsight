package com.orbitinsight.utils;

import com.orbitinsight.core.bean.KafkaProducerBean;
import com.orbitinsight.domain.SinkFeature;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class CacheUtil {
    private static final Map<String, KafkaProducerBean> KAFKA_PRODUCER_BEAN_MAP = new ConcurrentHashMap<>();

    private static final List<SinkFeature> SINK_FEATURE_LIST = new CopyOnWriteArrayList<>();

    public static void putKafkaProducerBean(String key, KafkaProducerBean kafkaProducerBean) {
        KAFKA_PRODUCER_BEAN_MAP.put(key, kafkaProducerBean);
    }

    public static KafkaProducerBean getKafkaProducerBean(String key) {
        return KAFKA_PRODUCER_BEAN_MAP.get(key);
    }

    public static void removeKafkaProducerBean(String key) {
        KAFKA_PRODUCER_BEAN_MAP.remove(key);
    }

    public static void addSinkFeature(List<SinkFeature> sinkFeatures) {
        SINK_FEATURE_LIST.addAll(sinkFeatures);
    }

}
