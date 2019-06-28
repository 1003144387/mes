package com.crsri.mes.vo;

import java.util.Date;
import java.util.List;

import com.crsri.mes.entity.ProduceProductChildInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProduceProductVO {

	private String id;

	private String name;

	private String model;

	private String image;

	private String remark;

	private String operator;

	private Date createTime;

	private Date updateTime;

	/**
	 * 组成产品的部件
	 */
	private List<ProduceProductChildInfo> parts;

	/**
	 * 组成产品的组件
	 */
	private List<ProduceProductChildInfo> components;
}
