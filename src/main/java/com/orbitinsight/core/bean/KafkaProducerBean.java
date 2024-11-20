package com.orbitinsight.core.bean;

import com.orbitinsight.utils.UnsafeUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.internals.Sender;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author dingjiefei
 */
public class KafkaProducerBean<K, V> extends AbstractManualBean {

    private final Unsafe unsafe = UnsafeUtil.getUnsafeAccessor().getUnsafe();

    private final KafkaProducer<K, V> producer;

    public KafkaProducerBean(String beanName, Properties properties) {
        super(beanName);
        this.producer = new KafkaProducer<>(properties);
    }

    @Override
    public Class<?> beanClass() {
        return getClass();
    }

    @Override
    public void destroy() throws Exception {
        producer.flush();
        producer.close();
    }

    public void init() {
        Field field = null;
        try {
            field = KafkaProducer.class.getDeclaredField("sender");
        } catch (NoSuchFieldException e) {
            return;
        }
        long offset = unsafe.objectFieldOffset(field);
        Sender sender = (Sender) unsafe.getObject(producer, offset);
        sender.wakeup();
    }

    public void send(String topic, V value) {
        producer.send(new ProducerRecord<>(topic, value));
    }
}
