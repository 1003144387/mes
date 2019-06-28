package com.crsri.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceComponentParts;

public interface ProduceComponentPartsMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ProduceComponentParts record);

	int insertSelective(ProduceComponentParts record);

	ProduceComponentParts selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ProduceComponentParts record);

	int updateByPrimaryKey(ProduceComponentParts record);

	/**
	 * 根据生产组件id获取组成该组件的部件信息
	 * 
	 * @param item
	 * @return
	 */
	List<ProduceComponentParts> queryByComponentId(@Param("componentId") String componentId);

	/**
	 * 根据部件id删除记录
	 * @param id
	 * @return
	 */
	int deleteByPartsId(@Param("partsId")String id);

	/**
	 * 根据组件id删除记录
	 * @param id
	 * @return
	 */
	int deleteByComponentId(@Param("componentId") String id);
}