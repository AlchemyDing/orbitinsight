package com.orbitinsight.handler;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dingjiefei
 */
@Component
public class LogsHandler implements SignalHandler {
    @Override
    public void handle(Object signal) {
        if (signal == null) {
            return;
        }
        if (signal instanceof ConsumerRecords) {
            ConsumerRecords<String, ?> records = (ConsumerRecords<String, ?>) signal;
            Map<Class, List> classToRecordsMap = new HashMap<>();
            for (ConsumerRecord<String, ?> record : records) {
                classToRecordsMap.computeIfAbsent(record.value().getClass(), k -> new ArrayList<>()).add(record);
            }
            List<byte[]> list = classToRecordsMap.get(byte[].class);

        }
    }


}
