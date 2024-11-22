package com.orbitinsight.core.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * @author dingjiefei
 */
public class DisruptorPublisher<T> {

    private final Disruptor<DisruptorEvent<T>> disruptor;

    public DisruptorPublisher(Disruptor<DisruptorEvent<T>> disruptor) {
        this.disruptor = disruptor;
    }

    private final EventTranslatorOneArg<DisruptorEvent<T>, T> translator = (event, sequence, arg0) -> event.setData(arg0);


    public void publish(T data) {
        RingBuffer<DisruptorEvent<T>> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent(translator, data);
    }
}
