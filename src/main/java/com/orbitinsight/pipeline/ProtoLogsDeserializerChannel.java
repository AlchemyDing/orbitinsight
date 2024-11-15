package com.orbitinsight.pipeline;

import com.alibaba.fastjson2.JSONObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.orbitinsight.cache.CachePool;
import com.orbitinsight.model.LogInfo;
import com.orbitinsight.core.pipeline.Channel;
import com.orbitinsight.utils.OtlpUtil;
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceRequest;
import io.opentelemetry.proto.logs.v1.LogRecord;
import io.opentelemetry.proto.logs.v1.ResourceLogs;
import io.opentelemetry.proto.logs.v1.ScopeLogs;
import io.opentelemetry.proto.resource.v1.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author dingjiefei
 */
@Component
public class ProtoLogsDeserializerChannel extends Channel<List<byte[]>, Object> {

    @Override
    protected Object doExecute(List<byte[]> list) throws InvalidProtocolBufferException {
        List<LogInfo> logInfos = new ArrayList<>();
        for (byte[] bytes : list) {
            ExportLogsServiceRequest request = ExportLogsServiceRequest.parseFrom(bytes);
            List<ResourceLogs> resourceLogsList = request.getResourceLogsList();
            for (ResourceLogs resourceLogs : resourceLogsList) {
                Resource resource = resourceLogs.getResource();
                JSONObject resourceAttributes = CachePool.RESOURCE_CACHE.get(resource, r -> OtlpUtil.convertAttributesToJson(r.getAttributesList()));
                for (ScopeLogs scopeLogs : resourceLogs.getScopeLogsList()) {
                    JSONObject scopeAttribute = OtlpUtil.convertAttributesToJson(scopeLogs.getScope().getAttributesList());
                    for (LogRecord logRecord : scopeLogs.getLogRecordsList()) {
                        JSONObject logAttribute = OtlpUtil.convertAttributesToJson(logRecord.getAttributesList());
                        LogInfo logInfo = new LogInfo();
                        // 设置服务名
                        logInfo.setServiceName(resourceAttributes.getString("service.name"));
                        // 设置严重性文本
                        logInfo.setSeverityText(logRecord.getSeverityText());
                        // 设置严重性数字
                        logInfo.setSeverityNumber(logRecord.getSeverityNumber().getNumber());
                        // 设置日志正文
                        logInfo.setBody(logRecord.getBody().toString());
                        // 设置追踪ID
                        logInfo.setTraceId(OtlpUtil.byteStringToHex(logRecord.getTraceId()));
                        // 设置跨度ID
                        logInfo.setSpanId(OtlpUtil.byteStringToHex(logRecord.getSpanId()));
                        // 设置日志记录的时间戳
                        logInfo.setTimeUnixNano(logRecord.getTimeUnixNano());
                        // 设置观察到的日志记录的时间戳
                        logInfo.setObservedTimeUnixNano(logRecord.getObservedTimeUnixNano());
                        // 设置作用域名称
                        logInfo.setScopeName(scopeLogs.getScope().getName());
                        // 设置资源属性
                        logInfo.setResourceAttributes(resourceAttributes);
                        // 设置作用域属性
                        logInfo.setScopeAttributes(scopeAttribute);
                        // 设置日志属性
                        logInfo.setLogAttributes(logAttribute);
                        logInfos.add(logInfo);
                    }
                }
            }
        }
        return logInfos;
    }


    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public Collection<com.orbitinsight.core.pipeline.Component> getDownStreams() {
        return List.of();
    }
}
