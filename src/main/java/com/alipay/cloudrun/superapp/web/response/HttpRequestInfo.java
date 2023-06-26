/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.cloudrun.superapp.web.response;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Map;

/**
 * @author siwei
 * @version HttpRequestInfo.java, v 0.1 2023年05月05日 16:41 siwei
 */
@Data
public class HttpRequestInfo {

    /**
     * http请求方法
     */
    private String method;

    /**
     * 请求header
     */
    private Map<String, String> headers;

    /**
     * 请求参数
     */
    private Map<String, String> requestParams;

    /**
     * 请求体
     */
    private JSONObject requestBody;

}