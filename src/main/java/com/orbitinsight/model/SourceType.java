package com.orbitinsight.model;

/**
 * @author dingjiefei
 */
public enum SourceType {
    KAFKA(1),
    ;

    SourceType(Integer type) {
        this.type = type;
    }

    private Integer type;

    public Integer getType() {
        return type;
    }
}