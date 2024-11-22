package com.orbitinsight.core.disruptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dingjiefei
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisruptorEvent<T> implements Serializable {
    private T data;
}
