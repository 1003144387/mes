package com.crsri.mes.vo;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

/**
 * 软件灌装和设备校准审批的VO对象
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProducePartsSoftInstallApproveVO {

	private String id;

    private String codes;

    private Integer number;

    private String softVersion;

    private String operator;

    private String remark;

    private String image;

    private Integer approveStatus;

    private Integer approveResult;

    private Long deptId;
    
    private String userId;
}
