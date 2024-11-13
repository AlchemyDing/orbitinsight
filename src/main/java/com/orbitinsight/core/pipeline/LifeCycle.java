package com.orbitinsight.core.pipeline;

/**
 * 生命周期
 */
public interface LifeCycle {
    /**
     * 初始化
     */
    default void init() {
    }

    /**
     * 启动
     */
    void startup();

    /**
     * 结束
     */
    void shutdown();
}