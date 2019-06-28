package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 软件灌装和设备校准审批记录的实体类
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProducePartsSoftInstallApprove {
    private String id;

    private String codes;

    private Integer number;

    private String softVersion;

    private String operator;

    private String remark;

    private String image;

    private Integer approveStatus;

    private Integer approveResult;

    private Date createTime;

    private Date updateTime;

}