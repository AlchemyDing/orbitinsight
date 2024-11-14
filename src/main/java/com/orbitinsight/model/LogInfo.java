package com.orbitinsight.model;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dingjiefei
 */
@Data
public class LogInfo implements Serializable {

    private String serviceName;

    private String severityText;

    private Integer severityNumber;

    private String body;

    private String traceId;

    private String spanId;

    private Long timeUnixNano;

    private Long observedTimeUnixNano;

    private String scopeName;

    private JSONObject resourceAttributes;

    private JSONObject scopeAttributes;

    private JSONObject logAttributes;
}
