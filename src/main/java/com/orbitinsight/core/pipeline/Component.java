package com.orbitinsight.core.pipeline;

import java.util.Collection;

/**
 * 组件
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
    Collection<Component> getDownStreams();

    /**
     * 执行
     */
    void execute(T o);
}