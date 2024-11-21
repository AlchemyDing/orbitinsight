package com.orbitinsight.model;

import lombok.Getter;

/**
 * @author dingjiefei
 */
@Getter
public enum SourceType {
    KAFKA(1),
    ;

    SourceType(Integer type) {
        this.type = type;
    }

    private Integer type;

}