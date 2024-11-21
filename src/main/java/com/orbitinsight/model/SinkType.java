package com.orbitinsight.model;

import lombok.Getter;

/**
 * @author dingjiefei
 */
@Getter
public enum SinkType {
    KAFKA(1),
    ;

    SinkType(Integer type) {
        this.type = type;
    }

    private Integer type;

}