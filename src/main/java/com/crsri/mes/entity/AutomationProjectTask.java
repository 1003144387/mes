package com.crsri.mes.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AutomationProjectTask {
    private String id;

    private String company;

    private String projectName;

    private String projectCategory;

    private String projectDesc;

    private String contact;

    private String phone;

    private String softFunction;

    private String scene;

    private String projectAdvice;

    private String attachment;

    private String operator;

    private String solution;

    private String solutionAuthor;

    private String customerSignature;

    private String customerSignaturePhone;

    private Integer customerConfirmationResult;

    private String customerAdvice;

    private Integer status;

    private Date createTime;

    private Date updateTime;


}