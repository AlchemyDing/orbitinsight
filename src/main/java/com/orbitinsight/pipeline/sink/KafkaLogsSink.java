package com.orbitinsight.pipeline.sink;

import com.orbitinsight.core.bean.KafkaProducerBean;
import com.orbitinsight.core.pipeline.Sink;
import com.orbitinsight.model.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class KafkaLogsSink extends Sink<List<LogInfo>, Void> {

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    protected Void doExecute(List<LogInfo> list) throws Exception {
        KafkaProducerBean defalutKafkaLogsSink = applicationContext.getBean("defalutKafkaLogsSink", KafkaProducerBean.class);

        return null;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Collection<com.orbitinsight.core.pipeline.Component> getDownStreams() {
        return Collections.emptyList();
    }
}
