package com.orbitinsight.model;

/**
 * @author dingjiefei
 */
public enum SinkType {
    KAFKA(1),
    ;

    SinkType(Integer type) {
        this.type = type;
    }

    private Integer type;

    public Integer getType() {
        return type;
    }
}