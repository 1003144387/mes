package com.crsri.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceProductChildInfo;

public interface ProduceProductChildInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ProduceProductChildInfo record);

	int insertSelective(ProduceProductChildInfo record);

	ProduceProductChildInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ProduceProductChildInfo record);

	int updateByPrimaryKey(ProduceProductChildInfo record);

	/**
	 * 根据组成部件或组件的id删除记录
	 * 
	 * @param id
	 * @return
	 */
	int deleteByChildId(@Param("childId") String childId);
	/**
	 * 产品id删除记录
	 * 
	 * @param id
	 * @return
	 */
	int deleteByProductId(@Param("productId") String productId);

	/**
	 * 根据产品id 和物品类别查找记录
	 * 
	 * @param id
	 * @return
	 */
	List<ProduceProductChildInfo> queryByProductId(@Param("productId") String productId,
			@Param("childType") Integer childType);

}