package com.orbitinsight.mapper;

import com.orbitinsight.domain.SinkConfig;
import com.orbitinsight.domain.SinkFeature;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dingjiefei
 */
@Repository
public interface SinkFeatureMapper {
    List<SinkFeature> queryAll();
}

