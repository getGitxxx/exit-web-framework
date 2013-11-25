package org.exitsoft.showcase.service.foundation;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.exitsoft.common.spring.mvc.SpringMvcHolder;
import org.exitsoft.common.utils.ConvertUtils;
import org.exitsoft.common.utils.ReflectionUtils;
import org.exitsoft.showcase.common.SessionVariable;
import org.exitsoft.showcase.common.SystemVariableUtils;
import org.exitsoft.showcase.common.annotation.OperatingAudit;
import org.exitsoft.showcase.common.enumeration.entity.OperatingState;
import org.exitsoft.showcase.entity.foundation.audit.OperatingRecord;
import org.exitsoft.showcase.entity.foundation.audit.RecordParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import com.google.common.collect.Lists;

/**
 * 操作记录Aspect,当执行某个Controller时会判断该Controller是否存在OperatingAudit
 * 注解，如果存在表示该Controller需要执行操作记录，当执行完成后，会在TB_OPERATING_RECORD
 * 表中插入一条数据，当某个Controller需要做操作记录但又重定向到另外一个也是需要做操作记录
 * 时，会在TB_OPERATING_RECORDD表中插入两条数据，以此类推。
 * 
 * @author vincent
 *
 */
@Aspect
@Component
public class OperatingRecordAspect {
	
	
	@Autowired
	private SystemAuditManager systemAuditManager;
	
	/**
	 * 空方法，用于pointcut在多处时更好的规范aop的切面方向
	 */
	@Pointcut("@annotation(org.exitsoft.showcase.common.annotation.OperatingAudit)")
	private void controller(){}
	
	/**
	 * 在Controller调用前，构造操作记录实体
	 * 
	 */
	@Before("controller()")
	@SuppressWarnings("unchecked")
	public void doBefore(JoinPoint joinPoint) {
		HttpServletRequest request = SpringMvcHolder.getRequest();
		
		Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
		
		OperatingAudit audit = ReflectionUtils.getAnnotation(method, OperatingAudit.class);
		
		OperatingRecord record = new OperatingRecord();
		record.setStartDate(new Date());
		record.setIp(getIpAddress(request));
		record.setMethod(method.getName());
		record.setOperatingTarget(request.getRequestURI());
		//获取当前SessionVariable，通过该变量获取当前用户
		SessionVariable sessionVariable = SystemVariableUtils.getSessionVariable();
		//如果SessionVariable等于null，表示用户未登录，但一样要记录操作
		if (sessionVariable != null) {
			record.setFkUserId(sessionVariable.getUser().getId());
			record.setUsername(sessionVariable.getUser().getUsername());
		}
		
		String function = audit.function();
		String module = "";
		
		if (StringUtils.isEmpty(audit.value())) {
			OperatingAudit classAnnotation = ReflectionUtils.getAnnotation(method.getDeclaringClass(),OperatingAudit.class);
			module = classAnnotation == null ? "" : classAnnotation.value();
		}
		
		record.setFunction(function);
		record.setModule(module);
		
		//获取本次提交的参数
		Map<String, Object> parameter = request.getParameterMap();
		List<RecordParameter> recordParametersList = Lists.newArrayList();
		//逐个循环参数和值添加到记录参数对象做，并且设置关联
		for(Entry<String, Object> entry : parameter.entrySet()) {
			RecordParameter recordParameter = new RecordParameter();
			
			recordParameter.setName(entry.getKey());
			recordParameter.setValue(getParameterValue(entry.getValue()));
			recordParameter.setRecord(record);
			
			recordParametersList.add(recordParameter);
		}
		
		record.setRecordParametersList(recordParametersList);
		
		request.setAttribute(method.getName(), record);
		
	}
	
	/**
	 * 在Controller完成后，保存操作记录实体
	 * 
	 */
	@After("controller()")
	public void doAfter(JoinPoint joinPoint) {
		
		Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
		
		OperatingRecord record = SpringMvcHolder.getAttribute(method.getName(), RequestAttributes.SCOPE_REQUEST);
		
		record.setEndDate(new Date());
		record.setState(OperatingState.Success.getValue());
		
		systemAuditManager.insertOperatingRecord(record);
	}
	
	/**
	 * 在Controller出现异常时，设置错误信息在保存操作记录实体
	 * 
	 */
	@AfterThrowing(pointcut="controller()",throwing="e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
		
		OperatingRecord record = SpringMvcHolder.getAttribute(method.getName(), RequestAttributes.SCOPE_REQUEST);
		
		record.setEndDate(new Date());
		record.setState(OperatingState.Fail.getValue());
		record.setRemark(e.getMessage());
		
		e.printStackTrace();
		
		systemAuditManager.insertOperatingRecord(record);
	}
	
	/**
	 * 通过Request 获取ip
	 * 
	 * @param request http servlet request
	 * 
	 * @return String
	 */
	private String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.trim();
	}
	
	/**
	 * 辅助方法，获取参数的真实值
	 * 
	 * @param value 参数值
	 * 
	 * @return String
	 */
	private String getParameterValue(Object value) {
		//如果该值为数组，将值用逗号分割
		if (value.getClass().isArray()) {
			return StringUtils.join((Object[])value,",");
		} else {
			return ConvertUtils.convert(value);
		}
	}
}
