/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.cloudrun.superapp.web;

import com.alipay.cloudrun.superapp.aop.annotation.ControllerPointCut;
import com.alipay.cloudrun.superapp.web.response.MemoryStatus;
import com.alipay.cloudrun.superapp.web.response.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存占用查看
 * 用于测试云托管监控功能
 *
 * @author siwei
 * @version MemTestController.java, v 0.1 2023年04月11日 20:02 siwei
 */
@Log4j2
@RestController
@RequestMapping("/api/memory")
public class MemTestController {

    /**
     * 列表 - 用于进行内存占用
     */
    private static List list = new ArrayList();

    /**
     * 1MB所占字节数
     */
    private static int MB_SIZE = 1024 * 1024;

    /**
     * 增加内存负载
     *
     * @param mb 负载大小(MB)
     * @return {@link Result}<{@link Void}>
     */
    @GetMapping("/load")
    @ControllerPointCut
    public Result<Void> load(@RequestParam(name = "mb", required = false) Integer mb) {

        log.info("/api/memory/load GET request, mb = {}", mb);
        //默认为100MB
        if (mb == null) {
            mb = 100;
        }
        byte[] bytes = new byte[MB_SIZE * mb];
        list.add(bytes);
        return Result.success();

    }

    /**
     * 查看内存状态
     *
     * @return {@link Result}<{@link MemoryStatus}>
     */
    @GetMapping("/status")
    @ControllerPointCut
    public Result<MemoryStatus> memoryStatus() {

        log.info("/api/memory/status GET request");
        MemoryStatus memoryStatus = new MemoryStatus();
        memoryStatus.setTotalMemory(Runtime.getRuntime().totalMemory()/1024/1024);
        memoryStatus.setFreeMemory(Runtime.getRuntime().freeMemory()/1024/1024);
        memoryStatus.setMaxMemory(Runtime.getRuntime().maxMemory()/1024/1024);
        return Result.success(memoryStatus);

    }


    /**
     * 清空内存负载
     *
     * @return {@link Result}<{@link Void}>
     */
    @GetMapping("/clear")
    @ControllerPointCut
    public Result<Void> clearMemory() {

        log.info("/api/memory/clear GET request");
        list.clear();
        System.gc();
        return Result.success();

    }

}