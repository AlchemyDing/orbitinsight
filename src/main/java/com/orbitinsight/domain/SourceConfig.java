package com.orbitinsight.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dingjiefei
 */
@Data
public class SourceConfig implements Serializable {
    private Long id;
    private Integer signalType;
    private Integer type;
    private String name;
    private String properties;
    private String createAccount;
    private Date createTime;
    private String modifyAccount;
    private Date modifyTime;
    private Long version;
    private Integer isDeleted;
}
