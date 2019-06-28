package com.crsri.mes.vo;

import lombok.ToString;

import lombok.Setter;

import lombok.Getter;

/**
 * 产品装配审批的vo对象
 * 
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceProductProduceApproveVO {

	private String id;
	
	private String productName;

	private String codes;

	private Integer number;

	private String operator;

	private Integer approveStatus;

	private Integer approveResult;

	private String image;

	private String remark;
	
	private Long deptId;
	
	private String userId;
}
