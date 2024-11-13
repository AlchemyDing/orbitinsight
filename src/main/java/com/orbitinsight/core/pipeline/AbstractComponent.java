package com.orbitinsight.core.pipeline;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 *  组件抽象实现
 * @param <T>   输入
 * @param <R>   输出
 */
public abstract class AbstractComponent<T, R> implements Component<T>{

    @Override
    public void execute(T o) {
        // 当前组件执行
        R r = doExecute(o);
        System.out.println(getName() + " receive " + o + " return " + r);
        // 获取下游组件，并执行
        Collection<Component> downStreams = getDownStreams();
        if (!CollectionUtils.isEmpty(downStreams)) {
            downStreams.forEach(c -> c.execute(r));
        }
    }

    /**
     *  具体组件执行处理
     * @param o 传入的数据
     * @return
     */
    protected abstract R doExecute(T o);

    @Override
    public void startup() {
        // 下游 -> 上游 依次启动
        Collection<Component> downStreams = getDownStreams();
        if (!CollectionUtils.isEmpty(downStreams)) {
            downStreams.forEach(Component::startup);
        }
        // do startup
        System.out.println("--------- " + getName() + " is start --------- ");
    }

    @Override
    public void shutdown() {
        // 上游 -> 下游 依次关闭
        // do shutdown
        System.out.println("--------- " + getName() + " is shutdown --------- ");

        Collection<Component> downStreams = getDownStreams();
        if (!CollectionUtils.isEmpty(downStreams)) {
            downStreams.forEach(Component::shutdown);
        }
    }
}