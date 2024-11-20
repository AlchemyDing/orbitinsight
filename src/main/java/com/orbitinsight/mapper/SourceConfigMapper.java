package com.orbitinsight.mapper;

import com.orbitinsight.domain.SourceConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dingjiefei
 */
@Repository
public interface SourceConfigMapper {
    List<SourceConfig> queryAll();
}

