package com.crsri.mes.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.constant.DingTalkURLConstant;
import com.crsri.mes.common.constant.LoginConstant;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.SysLogMapper;
import com.crsri.mes.dao.UserMapper;
import com.crsri.mes.dao.UserRoleMapper;
import com.crsri.mes.entity.SysLog;
import com.crsri.mes.entity.User;
import com.crsri.mes.entity.UserRole;
import com.crsri.mes.service.LoginService;
import com.crsri.mes.service.UserService;
import com.crsri.mes.util.dingtalk.AccessTokeUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserRoleMapper userRoleMapper;

	@Resource
	private UserService userService;

	@Resource
	private SysLogMapper sysLogMapper;

	@Override
	public ServerResponse appLogin(String authCode, HttpSession session) {
		// 1、根据code值获取钉钉的userId
		Map<String, String> res = getUserId(authCode);
		String userId = res.get("userId");
		if (StringUtils.isBlank(userId)) {
			log.error("配置钉钉免登失败:未获取到userId信息");
			return ServerResponse.createByFailMessage("配置钉钉免登失败:未获取到userId信息");
		}
		// 2、根据userId从数据库中查找用户对象
		User user = userMapper.selectUserByUserId(userId);
		// 3、找不到用户对象
		if (user == null) {
			user = getUserFromDingTalk(userId, res.get("accessToken"));
			// 将用户插入本地数据库
			userMapper.insertSelective(user);
			// 给用户最基本的权限
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(LoginConstant.BASE_ROLE_ID);
			userRoleMapper.insertSelective(userRole);
		}
		// shiro 登录
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getPhone(), user.getPassword());
		try {
			subject.login(token);
			Serializable id = subject.getSession().getId();
			session.setAttribute(id.toString(), user);
			addLog(user,1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// 置空用户密码，返回用户信息和用户权限
		user.setPassword(null);
		Map<String, Object> roleAndPermission = userService.selectRolesAndPermissions(userId);
		Map<String, Object> response = new HashMap<>();
		response.put("user", user);
		response.put("roleAndPermission", roleAndPermission);
		response.put("token", subject.getSession().getId());
		return ServerResponse.createBySuccess(response);
		
		
	}

	/**
	 * 从钉钉获取用户的user_id
	 * 
	 * @param authCode
	 * @return
	 */
	public Map<String, String> getUserId(String authCode) {
		String accessToken = AccessTokeUtil.getToken();
		// 获取用户信息
		DingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.URL_GET_USER_INFO);
		OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
		request.setCode(authCode);
		request.setHttpMethod("GET");

		OapiUserGetuserinfoResponse response;
		try {
			response = client.execute(request, accessToken);
		} catch (ApiException e) {
			e.printStackTrace();
			return null;
		}
		// 3.查询得到当前用户的userId
		// 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
		String userId = response.getUserid();
		Map<String, String> res = new HashMap<>();
		res.put("userId", userId);
		res.put("accessToken", accessToken);
		return res;
	}

	/**
	 * 从钉钉获取用户信息
	 * 
	 * @param userId
	 * @param accessToken
	 * @return
	 */
	public User getUserFromDingTalk(String userId, String accessToken) {
		try {
			DingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.URL_USER_GET);
			OapiUserGetRequest request = new OapiUserGetRequest();
			request.setUserid(userId);
			request.setHttpMethod("GET");
			OapiUserGetResponse userProfile = client.execute(request, accessToken);
			String username = userProfile.getName();
			Long deptId = userProfile.getDepartment().get(0);
			// 审批里的部门id，1和-1要互相转换一下
			if (deptId.longValue() == 1L) {
				deptId = -1L;
			}
			;
			String phone = userProfile.getMobile();
			User user = new User();
			user.setUsername(username);
			user.setUserId(userId);
			user.setDeptId(deptId);
			user.setPassword("e10adc3949ba59abbe56e057f20f883e");
			user.setPhone(phone);
			return user;
		} catch (ApiException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ServerResponse webLogin(JSONObject json,HttpSession session) {
		String phone = json.getString("phone");
		String password = json.getString("password");
		// shiro 登录
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
		User user = userMapper.selectUserByPhone(phone);
		try {
			subject.login(token);
			addLog(user,0);
		} catch (Exception ex) {
			return ServerResponse.createByFailMessage(ex.getMessage());
		}
		user.setPassword(null);
		Map<String, Object> roleAndPermission = userService.selectRolesAndPermissions(user.getUserId());
		Map<String, Object> response = new HashMap<>();
		Serializable sessionId = subject.getSession().getId();
		session.setAttribute(sessionId.toString(), user);
		response.put("user", user);
		response.put("token", sessionId);
		response.put("roleAndPermission", roleAndPermission);
		return ServerResponse.createBySuccess(response);
	}

	/**
	 * 添加用户登录日志
	 * @param user 用户信息
	 * @param type 登录类型
	 */
	private void addLog(User user,Integer type){
		SysLog sysLog = new SysLog();
		sysLog.setDescription("用户登录");
		sysLog.setType("0");
		if(type==0){
			sysLog.setDeviceType("PC");
		}else{
			sysLog.setDeviceType("移动端");
		}
		sysLog.setOperator(user.getUsername());
		sysLogMapper.insertSelective(sysLog);
	}

}
