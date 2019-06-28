package com.crsri.mes.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceParts;

public interface ProducePartsMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProduceParts record);

    int insertSelective(ProduceParts record);

    ProduceParts selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProduceParts record);

    int updateByPrimaryKey(ProduceParts record);

	/**
	 * 获取生产部件的基本信息（id，name）列表
	 */
	List<Map<String, Object>> selectProducePartsListSimple();

	/**
	 * 获取所有的生产部件信息列表
	 * @param operator 
	 * @param name 
	 * @param id 
	 * @return
	 */
	List<ProduceParts> queryAll(@Param("id")String id,@Param("name") String name,@Param("operator") String operator);

	/**
	 * 通过名称查找生产部件
	 * @param partsName
	 * @return
	 */
	ProduceParts queryByName(@Param("name")String partsName);

	
}