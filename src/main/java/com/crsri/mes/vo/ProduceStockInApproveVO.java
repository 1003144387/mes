package com.crsri.mes.vo;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ProduceStockInApproveVO {

	private String id;

	private Integer goodsType;

	private String typeName;

	private String codes;

	private Integer number;

	private String position;

	private String operator;

	private Integer approveStatus;

	private Integer approveResult;

	private String image;

	private String remark;
	
	private Long deptId;
	
	private String userId;
}
