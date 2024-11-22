package com.orbitinsight.core.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author dingjiefei
 */
public class DisruptorEventFactory<T> implements EventFactory<DisruptorEvent<T>> {
    @Override
    public DisruptorEvent<T> newInstance() {
        return new DisruptorEvent<>();
    }
}
