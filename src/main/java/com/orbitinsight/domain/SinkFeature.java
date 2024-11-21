package com.orbitinsight.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dingjiefei
 */
@Data
public class SinkFeature implements Serializable {
    private Long id;
    private String sinkName;
    private String feature;
    private Integer priority;
    private String createAccount;
    private Date createTime;
    private String modifyAccount;
    private Date modifyTime;
    private Long version;
    private Integer isDeleted;
}
