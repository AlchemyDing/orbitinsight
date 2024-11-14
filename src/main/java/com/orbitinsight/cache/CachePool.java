package com.orbitinsight.cache;

import com.alibaba.fastjson2.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.opentelemetry.proto.resource.v1.Resource;

import java.util.concurrent.TimeUnit;

/**
 * @author dingjiefei
 */
public class CachePool {

    CachePool() {
    }

    public static final Cache<Resource, JSONObject> RESOURCE_CACHE = Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(10, TimeUnit.SECONDS).build();
}
