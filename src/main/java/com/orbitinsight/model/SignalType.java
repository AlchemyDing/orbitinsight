package com.orbitinsight.model;

import lombok.Getter;

/**
 * @author dingjiefei
 */
@Getter
public enum SignalType {
    LOGS(1),
    ;

    SignalType(Integer type) {
        this.type = type;
    }

    private Integer type;

}