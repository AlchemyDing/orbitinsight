package com.orbitinsight.core.pipeline;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.shaded.com.google.protobuf.InvalidProtocolBufferException;

import java.util.Collection;

/**
 * 组件抽象实现
 *
 * @param <T> 输入
 * @param <R> 输出
 * @author dingjiefei
 */
public abstract class AbstractComponent<T, R> implements Component<T> {

    @Override
    public void execute(T o) {
        try {
            // 当前组件执行
            R r = doExecute(o);
            // 获取下游组件，并执行
            Collection<Component> downStreams = getDownStreams();
            if (CollectionUtils.isNotEmpty(downStreams)) {
                downStreams.forEach(c -> c.execute(r));
            }
        } catch (Exception e) {

        }
    }

    /**
     * 具体组件执行处理
     *
     * @param o 传入的数据
     * @return
     */
    protected abstract R doExecute(T o) throws InvalidProtocolBufferException;

}