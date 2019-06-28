package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.SysLog;

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

	List<SysLog> getLogs(@Param("startTime") Date startTime,
                         @Param("stopTime") Date stopTime,
                         @Param("type") String type);
}