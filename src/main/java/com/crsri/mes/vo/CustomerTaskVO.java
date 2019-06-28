package com.crsri.mes.vo;

import java.util.Date;
import java.util.List;

import com.crsri.mes.entity.CustomerTask;
import com.crsri.mes.entity.CustomerTaskDetail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 售后任务的VO对象
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class CustomerTaskVO {

	private String id;

	private String company;

	private String project;

	private String contact;

	private String phone;

	private String customerOperator;

	private Integer status;

	private List<CustomerTaskDetail> detail;
	
	private Date createTime;
	
	private Date updateTime;
	
	/**
	 * vo对象转换为实体类对象
	 * @param vo
	 * @return
	 */
	public  CustomerTask customerTaskVO2CustomerTask(CustomerTaskVO vo) {
		CustomerTask customerTask = new CustomerTask();
		customerTask.setCustomerCompany(vo.getCompany());
		customerTask.setProjectName(vo.getProject());
		customerTask.setContact(vo.getContact());
		customerTask.setPhone(vo.getPhone());
		return customerTask;
	}
	
	public CustomerTaskVO customerTask2CustomerTaskVO(CustomerTask task) {
		CustomerTaskVO vo = new CustomerTaskVO();
		vo.setId(task.getId());
		vo.setCompany(task.getCustomerCompany());
		vo.setContact(task.getContact());
		vo.setCreateTime(task.getCreateTime());
		vo.setCustomerOperator(task.getCustomerOperator());
		vo.setPhone(task.getPhone());
		vo.setProject(task.getProjectName());
		vo.setStatus(task.getStatus());
		vo.setUpdateTime(task.getUpdateTime());
		
		return  vo;
	}
}










