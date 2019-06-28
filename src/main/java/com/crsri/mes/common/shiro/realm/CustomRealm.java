package com.crsri.mes.common.shiro.realm;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.crsri.mes.dao.UserMapper;
import com.crsri.mes.entity.User;
import com.crsri.mes.service.UserService;

/**
 * 自定义鉴权的realm
 * @author 2011102394
 *
 */
public class CustomRealm extends AuthorizingRealm {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserService userService;

	/**
	 * 验证权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String phone = (String) principals.getPrimaryPrincipal();
		String userId = userMapper.selectUserByPhone(phone).getUserId();
		// 通过userId 查找对应的角色和权限信息
		Map<String, Object> res = userService.selectRolesAndPermissions(userId);
		// 设置角色和权限信息
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> roleSet = (Set<String>) res.get("roles");
		info.setRoles(roleSet);
		Set<String> stringPermissions = (Set<String>) res.get("permissions");
		info.setStringPermissions(stringPermissions);
		return info;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// 从数据库中获取用户密码
		User user = userMapper.selectUserByPhone(token.getUsername());
		if (user == null) {
			throw new AccountException("帐号不存在！");
		} else {
			String password = user.getPassword();
			if (!password.equals(new String((char[]) token.getCredentials()))) {
				throw new AccountException("账户和密码不匹配，请检查！");
			}
			return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
		}
	}

}
