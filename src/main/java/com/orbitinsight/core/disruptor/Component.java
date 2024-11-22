package com.orbitinsight.core.disruptor;

import java.util.Collection;

/**
 * 组件
 * @author dingjiefei
 */
public interface Component<T> {
    /**
     * 组件名称
     *
     * @return
     */
    String getName();

    /**
     * 获取下游组件
     *
     * @return
     */
    Collection<DisruptorPublisher<T>> getDownStreams();
}