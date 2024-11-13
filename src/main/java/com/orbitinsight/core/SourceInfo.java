package com.orbitinsight.core;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * @author dingjiefei
 */
@Data
public class SourceInfo implements Serializable {
    private SourceType sourceType;
    private String name;
    private EncodingType encoding;
    private Integer parallel;
    private List<String> topics;
    private Properties properties;
}