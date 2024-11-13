package com.orbitinsight.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

/**
 * @author dingjiefei
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "orbit-insight")
public class OrbitInsightProperties implements Serializable {
    private List<SourceInfo> logs;
    private List<SourceInfo> traces;
    private List<SourceInfo> metrics;
}
