package com.orbitinsight.pipeline;

import com.lmax.disruptor.EventHandler;
import com.orbitinsight.core.disruptor.Component;
import com.orbitinsight.core.disruptor.DisruptorEvent;
import com.orbitinsight.core.disruptor.DisruptorPublisher;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 * @author dingjiefei
 */
public abstract class AbstractEventHandler<T, R> implements EventHandler<DisruptorEvent<T>>, Component<R> {

    protected final long ordinal;
    protected final long numberOfConsumers;

    protected AbstractEventHandler(long ordinal, long numberOfConsumers) {
        this.ordinal = ordinal;
        this.numberOfConsumers = numberOfConsumers;
    }


    @Override
    public void onEvent(DisruptorEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        if (sequence % numberOfConsumers != ordinal) {
            return;
        }
        // 逻辑
        R r = doExecute(event.getData());
        Collection<DisruptorPublisher<R>> downStreams = getDownStreams();
        if (CollectionUtils.isNotEmpty(downStreams)) {
            downStreams.forEach(publisher -> publisher.publish(r));
        }
    }

    protected abstract R doExecute(T o) throws Exception;

    @Override
    public String getName() {
        return getClass().getSimpleName() + "-" + ordinal;
    }
}
