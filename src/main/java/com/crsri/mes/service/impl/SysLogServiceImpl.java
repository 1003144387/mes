package com.crsri.mes.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.SysLogMapper;
import com.crsri.mes.entity.SysLog;
import com.crsri.mes.service.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br>
 * 〈系统日志service接口的实现〉
 *
 * @author zcj
 * @date 2018/9/21 11:27
 * @since 1.0.0
 */
@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public int save(SysLog sysLog) {

        return sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public ServerResponse getLogs(Date startTime, Date stopTime,String type,Integer page,Integer row) {
        if(page==null){
            page = 1;
        }
        if(row == null){
            row = 10;
        }
        //开始分页
        PageHelper.startPage(page,row);
        List<SysLog> logs = sysLogMapper.getLogs(startTime,stopTime,type);
        //获取分页结果
        PageInfo<SysLog> pageInfo = new PageInfo<>(logs);
        //获取总记录数
        long total = pageInfo.getTotal();
        int totalPage = pageInfo.getPages();
        int startRow = pageInfo.getStartRow();
        int endRow = pageInfo.getEndRow();
        List<SysLog> list = pageInfo.getList();
        Map<String,Object> data = new HashMap();
        data.put("total",total);
        data.put("totalPage",totalPage);
        data.put("data",list);
        data.put("startRow",startRow);
        data.put("endRow",endRow);
        data.put("currentPage",pageInfo.getPageNum());
        return ServerResponse.createBySuccess(data);
    }
}
