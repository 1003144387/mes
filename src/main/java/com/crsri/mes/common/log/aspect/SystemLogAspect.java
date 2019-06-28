package com.crsri.mes.common.log.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.log.annontation.SystemServiceLog;
import com.crsri.mes.common.response.ResponseCode;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.SysLog;
import com.crsri.mes.entity.User;
import com.crsri.mes.service.SysLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br>
 * 〈系统操作日志的切面〉
 *
 * @author zcj
 * @date 2018/9/21 9:47
 * @since 1.0.0
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class SystemLogAspect {

	// 注入service用于把日志记录到数据库中
	@Resource
	private SysLogService sysLogService;

	// controller层的切点
	@Pointcut("@annotation(com.crsri.mes.common.log.annontation.SystemControllerLog)")
	public void controllerAspect() {

	}

	// service层的切点
	@Pointcut("@annotation(com.crsri.mes.common.log.annontation.SystemServiceLog)")
	public void serviceAspect() {

	}

	@Around("controllerAspect()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object res = joinPoint.proceed();
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String deviceType = null;
		// 判断客户端类型
		if (isMobileDevic(request.getHeader("user-agent"))) {
			// 客户端访问
			deviceType = "移动设备";
		} else {
			// PC 端访问
			deviceType = "PC";
		}
		String token = request.getHeader("Authorization");
		User user;
		ServerResponse response = (ServerResponse) res;
		if (token == null || token.equalsIgnoreCase("undefined")) {
			// 用户登录
			user = (User) response.getData();
		} else {
			HttpSession session = request.getSession();
			Object obj = session.getAttribute(token);
			if (obj == null) {
				// 用户登录
				user = (User) response.getData();
			} else {
				user = (User) obj;
			}
		}
		// 获取请求的ip
		String remoteAddr = request.getRemoteAddr();
		// 获取请求的方法
		String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
		try {
			// **=========控制台输出============**//
			// 获取请求的方法描述
			String methodDesc = getControllerMethodDescription(joinPoint);
			SysLog sysLog = new SysLog();
			sysLog.setDescription(methodDesc);
			sysLog.setMethod(method);
			sysLog.setType("0");
			sysLog.setDeviceType(deviceType);
			sysLog.setRequestIp(remoteAddr);
			sysLog.setExceptionCode(null);
			sysLog.setParams(null);
			if (user != null) {
				sysLog.setOperator(user.getUsername());
			} else {
				sysLog.setOperator("未授权的用户");
			}
			sysLog.setOperatorTime(new Date());
			if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
				// 操作成功
				sysLog.setResponse(ResponseCode.SUCCESS.getDesc());
			} else {
				sysLog.setResponse(ResponseCode.FAIL.getDesc());
			}
			// 将操作日志对象保存到数据库中
			sysLogService.save(sysLog);
		} catch (Exception e) {
			log.error("=======环绕通知发生异常========");
			log.error("异常信息", e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 *
	 * @param joinPoint 切点
	 * @param e         异常对象
	 */
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		String token = request.getHeader("Authorization");
		Object attribute = session.getAttribute(token);
		User user;
		String userName = null;
		if (attribute != null) {
			user = (User) attribute;
			userName = user.getUsername();
		}
		String deviceType = null;
		// 判断客户端类型
		if (isMobileDevic(request.getHeader("user-agent"))) {
			// 客户端访问
			deviceType = "移动设备";
		} else {
			// PC 端访问
			deviceType = "PC";
		}
		// 获取请求ip
		String ip = request.getRemoteAddr();
		// 获取用户请求方法的参数并序列化为JSON格式字符串
		StringBuilder params = new StringBuilder();
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				params.append(JSONObject.toJSONString(joinPoint.getArgs()[i])).append(";");
			}
		}
		try {
			/* ========控制台输出========= */
			System.out.println("=====异常通知开始=====");
			System.out.println("异常代码:" + e.getClass().getName());
			System.out.println("异常信息:" + e.getMessage());
			System.out.println("异常方法:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));
			System.out.println("请求IP:" + ip);
			System.out.println("请求参数:" + params);

			SysLog log = new SysLog();
			log.setDescription(getServiceMthodDescription(joinPoint));
			log.setExceptionCode(e.getClass().getName());
			log.setType("1");
			log.setExceptionDetail(e.getMessage());
			log.setMethod(
					(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			log.setParams(params.toString());
			log.setOperator(userName);
			log.setOperatorTime(new Date());
			log.setRequestIp(ip);
			log.setResponse("异常操作");
			log.setDeviceType(deviceType);
			// 保存数据库
			sysLogService.save(log);
		} catch (Exception ex) {
			// 记录本地异常日志
			log.error("==异常通知异常==");
			/* ==========记录本地异常日志========== */
			log.error("异常信息:{}", ex.getMessage());
		}
		log.error("==异常通知信息===");
		log.error("异常方法:{}异常代码:{}异常信息:{}参数:{}",
				joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(),
				e.getMessage(), params.toString());

	}

	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 *
	 * @param joinPoint 切点
	 * @return 方法描述
	 * @throws Exception
	 */
	private static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemServiceLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 *
	 * @param joinPoint 切点
	 * @return 方法描述
	 * @throws Exception
	 */
	private static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	private boolean isMobileDevic(String ua) {
		boolean flag = false;
		String[] agent = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" }; // 定义移动端请求的所有可能类型

		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : agent) {
					if (ua.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

}
