package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.ApproveMapper;
import com.crsri.mes.dao.UserMapper;
import com.crsri.mes.entity.Approve;
import com.crsri.mes.service.ApproveService;

/**
 * @author 2011102394
 *
 */
@Service
public class ApproveServiceImpl implements ApproveService {

	@Resource
	private ApproveMapper approveMapper;
	
	@Resource
	private UserMapper userMapper;

	@Override
	public ServerResponse getApproveList() {
		List<Approve> approves = approveMapper.queryApproveList();
		return ServerResponse.createBySuccess(approves);
	}

	@Override
	public ServerResponse updateApprove(Approve approve) {
		String approverList = approve.getApproverList();
		String ccList = approve.getCcList();
		approve.setApproverUser(getUserNames(approverList));
		approve.setCcUser(getUserNames(ccList));
		approveMapper.updateByPrimaryKeySelective(approve);
		return ServerResponse.createBySuccessMessage("操作成功！");
	}
	
	public String getUserNames(String userIds) {
		if(StringUtils.isEmpty(userIds)) {
			return null;
		}else {
			String[] ids = userIds.split(",");
			List<String> usernames = new ArrayList<>();
			for (String userId : ids) {
				String username = userMapper.selectUserByUserId(userId).getUsername();
				usernames.add(username);
			}
			return String.join(",", usernames);
		}
	}

}
