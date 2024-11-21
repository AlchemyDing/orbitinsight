package com.orbitinsight.utils;

import com.orbitinsight.core.bean.KafkaProducerBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ManualBeanUtil {
    private static final Map<String, KafkaProducerBean> KAFKA_PRODUCER_BEAN_MAP = new ConcurrentHashMap<>();

    public static void putKafkaProducerBean(String key, KafkaProducerBean kafkaProducerBean) {
        KAFKA_PRODUCER_BEAN_MAP.put(key, kafkaProducerBean);
    }

}
