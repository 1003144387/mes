package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;

/**
 * 〈一句话功能简述〉<br>
 * 〈报表相关的service接口〉
 *
 * @author zcj
 * @date 2018/10/24 8:45
 * @since 1.0.0
 */
public interface ReportService {

    /**
     * 按条件查询工作报告列表
     *
     * @param json 查询条件
     * @return
     */
    ServerResponse getReportSimple(JSONObject json);


    /**
     * 按条件查询报表列表
     *
     * @param json 查询条件
     * @return
     */
    ServerResponse getReportList(JSONObject json);

    /**
     * 获取指定时间内售后任务、维修任务、自动化任务的数量
     * @param json 查询条件
     * @return
     */
    ServerResponse getCount(JSONObject json);
}
