/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.cloudrun.superapp.web.response;

import lombok.Data;

/**
 * @author siwei
 * @version MemoryStatus.java, v 0.1 2023年04月12日 10:32 siwei
 */
@Data
public class MemoryStatus {

    /**
     * 已使用内存(MB)
     */
    private long totalMemory;

    /**
     * 已向操作系统获取，但未使用的内存(MB)
     */
    private long freeMemory;

    /**
     * 最大可获取的内存(MB)
     */
    private long maxMemory;

}