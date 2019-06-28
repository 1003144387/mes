package com.crsri.mes.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.SysLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 日志操作的相关接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api/sysLog")
@Api(tags="日志操作相关的接口")
public class SysLogController {

	@Resource
    private SysLogService sysLogService;

    @PostMapping("/list")
    @ApiOperation("获取操作日志列表")
    public ServerResponse getOperationLogs(@RequestBody JSONObject json){
        Date startTime = json.getDate("startTime");
        Date stopTime = json.getDate("stopTime");
        Integer page = json.getInteger("page");
        Integer row = json.getInteger("row");
        return sysLogService.getLogs(startTime,stopTime,"0",page,row);
    }
    @PostMapping("/list/exception")
    @ApiOperation("获取异常日志列表")
    public ServerResponse getExceptionLogs(@RequestBody JSONObject json){
        Date startTime = json.getDate("startTime");
        Date stopTime = json.getDate("stopTime");
        Integer page = json.getInteger("page");
        Integer row = json.getInteger("row");
        return sysLogService.getLogs(startTime,stopTime,"1",page,row);
    }
}
