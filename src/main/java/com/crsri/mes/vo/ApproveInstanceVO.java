package com.crsri.mes.vo;

import java.util.List;

import com.crsri.mes.common.constant.DingTalkConstant;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 审批实例的VO对象
 * 
 * @author 2011102394
 *
 */
@ToString
@Getter
@Setter
public class ApproveInstanceVO {

	// E应用的标识
	private Long agentId = DingTalkConstant.AGENTID;
	// 审批流的编号
	private String processCode;
	// 审批实例发起人的userid
	private String originatorUserId;
	// 审批发起人的部门id
	private Long deptId;
	// 审批人userid列表，最大列表长度：20。多个审批人用逗号分隔，按传入的顺序依次审批
	private String approvers;
	// 抄送人userid列表，最大列表长度：20。多个抄送人用逗号分隔
	private String ccList;
	// 抄送时间，分为（START, FINISH, START_FINISH）
	private String ccPosition = "FINISH";
	// 审批流表单参数，最大列表长度：20。
	private List<FormComponentValueVo> formComponentValueVos;

}
