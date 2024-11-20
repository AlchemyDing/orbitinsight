package com.orbitinsight.utils;

import com.alibaba.fastjson2.JSONObject;
import com.google.protobuf.ByteString;
import io.opentelemetry.proto.common.v1.AnyValue;
import io.opentelemetry.proto.common.v1.KeyValue;

import java.util.List;

public class OtlpUtil {

    public static JSONObject convertAttributesToJson(List<KeyValue> attributesList) {
        JSONObject jsonObject = new JSONObject();
        for (KeyValue keyValue : attributesList) {
            String key = keyValue.getKey();
            AnyValue value = keyValue.getValue();
            if (value.hasStringValue()) {
                jsonObject.put(key, value.getStringValue());
            } else if (value.hasIntValue()) {
                jsonObject.put(key, value.getIntValue());
            } else if (value.hasDoubleValue()) {
                jsonObject.put(key, value.getDoubleValue());
            } else if (value.hasBoolValue()) {
                jsonObject.put(key, value.getBoolValue());
            } else if (value.hasArrayValue()) {
                jsonObject.put(key, value.getArrayValue().getValuesList().stream().map(OtlpUtil::convertAnyValue).toList());
            } else if (value.hasKvlistValue()) {
                jsonObject.put(key, convertAttributesToJson(value.getKvlistValue().getValuesList()));
            }
        }
        return jsonObject;
    }

    private static Object convertAnyValue(AnyValue anyValue) {
        if (anyValue.hasStringValue()) {
            return anyValue.getStringValue();
        } else if (anyValue.hasIntValue()) {
            return anyValue.getIntValue();
        } else if (anyValue.hasDoubleValue()) {
            return anyValue.getDoubleValue();
        } else if (anyValue.hasBoolValue()) {
            return anyValue.getBoolValue();
        } else if (anyValue.hasArrayValue()) {
            return anyValue.getArrayValue().getValuesList().stream().map(OtlpUtil::convertAnyValue).toList();
        } else if (anyValue.hasKvlistValue()) {
            return convertAttributesToJson(anyValue.getKvlistValue().getValuesList());
        } else {
            return null;
        }
    }

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public static String byteStringToHex(ByteString byteString) {
        byte[] bytes = byteString.toByteArray();
        StringBuilder hexString = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hexString.append(HEX_CHARS[(b & 0xFF) >> 4]);
            hexString.append(HEX_CHARS[b & 0x0F]);
        }
        return hexString.toString();
    }
}
