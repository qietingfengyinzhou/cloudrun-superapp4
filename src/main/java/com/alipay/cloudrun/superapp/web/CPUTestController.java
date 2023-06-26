/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.cloudrun.superapp.web;

import com.alipay.cloudrun.superapp.aop.annotation.ControllerPointCut;
import com.alipay.cloudrun.superapp.web.response.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * cpu负载测试
 * 用于测试云托管监控及扩缩容能力
 *
 * @author siwei
 * @version CPUTestController.java, v 0.1 2023年04月12日 11:12 siwei
 */
@Log4j2
@RestController
@RequestMapping("/api/cpu")
public class CPUTestController {

    /**
     * 负载状态
     */
    private static boolean LOAD = false;

    /**
     * CPU压力测试
     *
     * @param processors 消耗处理器数量
     * @return {@link Result}<{@link Void}>
     */
    @GetMapping("/load")
    @ControllerPointCut
    public Result<Void> stressTest(@RequestParam(name = "processors", required = false) Integer processors) {
        LOAD = true;
        if (processors == null) {
            processors = Runtime.getRuntime().availableProcessors();
        }
        for (int i = 0; i < processors; i ++) {
            new Thread(() -> {
                int n = 1000000;
                while (LOAD) {
                    MontePI(n);
                    n++;
                }
            }).start();
        }
        return Result.success();
    }

    /**
     * 取消CPU压力测试
     *
     * @return {@link Result}<{@link Void}>
     */
    @GetMapping("/cancel")
    @ControllerPointCut
    public Result<Void> cancelStress() {
        LOAD = false;
        return Result.success();
    }

    /**
     * 蒙特卡洛算法计算pi近似值
     *
     * @param n n
     * @return double
     */
    private static double MontePI(int n) {
        double PI;
        double x, y;
        int i, sum;
        sum = 0;
        for (i = 1; i < n; i++) {
            if(!LOAD){
                break;
            }
            x = Math.random();
            y = Math.random();
            if ((x * x + y * y) <= 1) {
                sum++;
            }
        }
        PI = 4.0 * sum / n;
        System.out.println(PI);
        return PI;
    }

}