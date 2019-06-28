package com.crsri.mes.service;

import java.util.Date;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.SysLog;

/**
 * 〈一句话功能简述〉<br>
 * 〈系统日志service接口〉
 *
 * @author zcj
 * @date 2018/9/21 11:27
 * @since 1.0.0
 */
public interface SysLogService {

    int save(SysLog sysLog);

    ServerResponse getLogs(Date startTime, Date stopTime,String type,Integer page,Integer row);
}
