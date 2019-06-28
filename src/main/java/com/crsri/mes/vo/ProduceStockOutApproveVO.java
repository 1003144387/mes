package com.crsri.mes.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 生产物品出库审批的VO对象
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceStockOutApproveVO {

	/**
	 * 审批id
	 */
	private String id;

	/**
	 * 物品类型 0 部件 1组件 2产品
	 */
    private Integer goodsType;

    /**
     * 物品名称
     */
    private String typeName;

    /**
     * 物品编号集合
     */
    private String codes;

    /**
     * 物品数量
     */
    private Integer number;
    
    /**
     * 发起审批人在钉钉系统中的userid
     */
    private String userId;
    
    /**
     * 发起审批人的部门id
     */
    private Long deptId;

    /**
     * 物品领取人
     */
    private String receivePeople;

    /**
     * 物品去向
     */
    private String receiveAddress;

    /**
     * 出库人员
     */
    private String operator;

    /**
     * 审批状态
     */
    private Integer approveStatus;

    /**
     * 审批结果
     */
    private Integer approveResult;

    /**
     * 图片
     */
    private String image;

    /**
     * 备注
     */
    private String remark;

}
