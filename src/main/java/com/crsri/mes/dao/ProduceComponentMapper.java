package com.crsri.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceComponent;

public interface ProduceComponentMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProduceComponent record);

    int insertSelective(ProduceComponent record);

    ProduceComponent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProduceComponent record);

    int updateByPrimaryKey(ProduceComponent record);

	List<ProduceComponent> queryAll(@Param("name")String name, @Param("id")String id,@Param("model")String model);

	/**
	 * 根据组件名查找组件
	 * @param componentName
	 * @return
	 */
	ProduceComponent queryByName(@Param("componentName")String componentName);
}