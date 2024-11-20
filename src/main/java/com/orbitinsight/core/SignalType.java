package com.orbitinsight.core;

/**
 * @author dingjiefei
 */
public enum SignalType {
    LOGS(1),
    ;

    SignalType(Integer type) {
        this.type = type;
    }

    private Integer type;

    public Integer getType() {
        return type;
    }
}