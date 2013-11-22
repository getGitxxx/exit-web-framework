package org.exitsoft.showcase.service.foundation;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.exitsoft.common.utils.ReflectionUtils;
import org.exitsoft.showcase.common.SessionVariable;
import org.exitsoft.showcase.common.SystemVariableUtils;
import org.exitsoft.showcase.common.annotation.OperatingAudit;
import org.exitsoft.showcase.common.enumeration.entity.OperatingState;
import org.exitsoft.showcase.entity.foundation.audit.OperatingRecord;
import org.exitsoft.showcase.entity.foundation.audit.RecordParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

/**
 * 操作记录拦截器当执行某个Controller时会判断该Controller是否存在OperatingAudit
 * 注解，如果存在表示该Controller需要执行操作记录，当执行完成后，会在TB_OPERATING_RECORD
 * 表中插入一条数据，当某个Controller需要做操作记录但又重定向到另外一个也是需要做操作记录
 * 时，会在TB_OPERATING_RECORDD表中插入两条数据，以此类推。
 * 
 * @author vincent
 *
 */
public class OperatingRecordInterceptor implements HandlerInterceptor {
	
	@Autowired
	private SystemAuditManager systemAuditManager;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//如果不是spring mvc 控制器方法不需要处理
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		//获取方法名称
		Method method = handlerMethod.getMethod();
		//获取方法中是否存在@OperatingAudit注解
		OperatingAudit auditHandler = ReflectionUtils.getAnnotation(method, OperatingAudit.class);
		//如果不存在，不做操作记录
		if (auditHandler == null) {
			return true;
		}
		//创建操作记录
		OperatingRecord record = createOperatingRecord(method,request);
		//将操作记录对象放到request中，与方法名做ke，当afterCompletion执行后，会删除该attribute
		request.setAttribute(method.getName(), record);
		
		return true;
	}

	/**
	 * 通过method和request创建操作记录
	 * 
	 * @param method method
	 * @param request request
	 * 
	 * @return {@link OperatingRecord}
	 */
	@SuppressWarnings("unchecked")
	private OperatingRecord createOperatingRecord(Method method, HttpServletRequest request) {
		
		OperatingRecord record = new OperatingRecord();
		record.setStartDate(new Date());
		record.setIp(getIpAddress(request));
		record.setMethod(method.getName());
		record.setOperatingTarget(request.getRequestURI());
		//获取当前SessionVariable，通过该变量获取当前用户
		SessionVariable sessionVariable = SystemVariableUtils.getSessionVariable();
		//如果SessionVariable等于null，表示用户未登录，但一样要记录操作
		record.setUser(sessionVariable == null ? null : sessionVariable.getUser());
		//获取本次提交的参数
		Map<String, String> parameter = request.getParameterMap();
		List<RecordParameter> recordParametersList = Lists.newArrayList();
		//逐个循环参数和值添加到记录参数对象做，并且设置关联
		for(Entry<String, String> entry : parameter.entrySet()) {
			RecordParameter recordParameter = new RecordParameter();
			
			recordParameter.setName(entry.getKey());
			recordParameter.setValue(entry.getValue());
			recordParameter.setRecord(record);
			
			recordParametersList.add(recordParameter);
		}
		
		record.setRecordParametersList(recordParametersList);
		
		return record;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//如果不是spring mvc 控制器方法不需要处理
		if (!(handler instanceof HandlerMethod)) {
			return ;
		}
		
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Method method = handlerMethod.getMethod();
		//通过方法名获取操作记录
		OperatingRecord record = (OperatingRecord) request.getAttribute(method.getName());
		//如果操作记录不存在，将什么都不做
		if (record == null) {
			return ;
		}
		//设置执行本次请求的结束时间
		record.setEndDate(new Date());
		//如果存在异常，将状态设置为失败状态，并将异常信息写到备注中，否则设置为成功状态
		if (ex != null) {
			record.setState(OperatingState.Fail.getValue());
			record.setRemark(ex.getMessage());
		} else {
			record.setState(OperatingState.Success.getValue());
		}
		//保存操作记录
		systemAuditManager.insertOperatingRecord(record);
		//将操作记录存放到request的对象移除
		request.removeAttribute(method.getName());
		
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

}
