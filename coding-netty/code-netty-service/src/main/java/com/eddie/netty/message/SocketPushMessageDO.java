package com.eddie.netty.message;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 消息盒子基础对象信息
 *
 * @author eddie.lys
 * @since 2021/9/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketPushMessageDO implements Serializable {

    /**
     * 扩展字段
     */
    private Map<String, String> featureMap;

    public void setFeatureMapByJson(String featureMapStr) {
        Map<String, String> featureMap = new HashMap<>();
        if (StringUtils.isNotEmpty(featureMapStr)) {
            JSONObject featureJson = JSON.parseObject(featureMapStr);
            for (String featureKey : featureJson.keySet()) {
                featureMap.put(featureKey, featureJson.getString(featureKey));
            }
        }
        if (Objects.isNull(this.featureMap)) {
            this.featureMap = featureMap;
        } else {
            this.featureMap.putAll(featureMap);
        }
    }

    public void addFeatureValue(String key, String value) {
        if (Objects.isNull(this.featureMap)) {
            this.featureMap = new HashMap<>();
        }
        this.featureMap.put(key, value);
    }
}
