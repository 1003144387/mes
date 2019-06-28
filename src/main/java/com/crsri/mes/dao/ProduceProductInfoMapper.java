package com.crsri.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceProductInfo;

public interface ProduceProductInfoMapper {
	int deleteByPrimaryKey(String id);

	int insert(ProduceProductInfo record);

	int insertSelective(ProduceProductInfo record);

	ProduceProductInfo selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ProduceProductInfo record);

	int updateByPrimaryKey(ProduceProductInfo record);

	/**
	 * 获取生产产品的基本信息列表
	 * 
	 * @return
	 */
	List<ProduceProductInfo> queryProduceProductInfoList();

	/**
	 * 按条件搜索
	 * 
	 * @param name
	 * @param id
	 * @param model
	 * @return
	 */
	List<ProduceProductInfo> queryAll(@Param("name") String name, @Param("id") String id,
			@Param("model") String model);

	/**
	 * 根据产品名称查找产品记录
	 * @param productName
	 * @return
	 */
	ProduceProductInfo queryByName(@Param("name")String name);
}