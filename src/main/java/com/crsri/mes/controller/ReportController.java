package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.ReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 〈一句话功能简述〉<br>
 * 〈报表相关的Controller〉
 *
 * @author zcj
 * @date 2018/10/24 8:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/report")
@Api(tags="周报操作相关的接口")
public class ReportController {

    @Resource
    private ReportService reportService;


    /**
     * 获取报表简单信息
     * @param json
     * @return
     */
    @PostMapping("/simple")
    @ApiOperation("获取报表简单信息")
    public ServerResponse getReportSimple(@RequestBody JSONObject json) {
        return reportService.getReportSimple(json);
    }


    /**
     * 获取报表信息
     * @param json
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("获取报表信息")
    public ServerResponse getReportList(@RequestBody JSONObject json) {
        return reportService.getReportList(json);
    }

    /**
     * 获取售后任务、维修任务、自动化任务的数量
     * @param json
     * @return
     */
    @PostMapping("/count")
    @ApiOperation("获取售后任务、维修任务、自动化任务的数量")
    public ServerResponse getCount(@RequestBody JSONObject json){
        return reportService.getCount(json);
    }
}



























