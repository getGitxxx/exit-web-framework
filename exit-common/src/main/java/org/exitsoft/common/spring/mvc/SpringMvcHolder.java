package org.exitsoft.common.spring.mvc;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * spring mvc 上下文持有者，类似Struts2的ServletActionContext,
 * 
 * @author vincent
 *
 */
@SuppressWarnings("unchecked")
public abstract class SpringMvcHolder {
	
	/**
	 * 获取request attribute
	 * 
	 * @param name 属性名称
	 * 
	 * @return Object
	 */
	public static <T> T getRequestAttribute(String name) {
		return getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}
	
	/**
	 * 设置request attribute
	 * 
	 * @param name 属性名称
	 * @param value 值
	 */
	public static void addRequestAttribute(String name,Object value) {
		addAttribute(name, value, RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 获取sessiont attribute
	 * 
	 * @param name 属性名称
	 * 
	 * @return Object
	 */
	public static <T> T getSessionAttribute(String name) {
		return getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * 设置session attribute
	 * 
	 * @param name 属性名称
	 * @param value 值
	 */
	public static void addSessionAttribute(String name,Object value) {
		addAttribute(name, value, RequestAttributes.SCOPE_SESSION);
	}
	
	
	/**
	 * 根据作用域,获取Attribute
	 * 
	 * @param name attribute名称
	 * @param scope 作用域,参考{@link RequestAttributes}
	 * 
	 * @return Object
	 */
	public static <T> T  getAttribute(String name,int scope) {
		return (T) getNativeWebRequest().getAttribute(name, scope);
	}
	
	/**
	 * 根据作用域,设置Attribute
	 * 
	 * @param name attribute名称
	 * @param value 值
	 * @param scope 作用域,参考{@link RequestAttributes}
	 */
	public static void  addAttribute(String name,Object value, int scope) {
		getNativeWebRequest().setAttribute(name, value, scope);
	}

	/**
	 * 获取NativeResponse
	 * 
	 * @param requiredType Response类型,如果为null返回当前默认的ServletResponse
	 * 
	 * @return {@link ServletResponse}
	 */
	public static <T> T getNativeResponse(Class<T> requiredType) {
		return getNativeWebRequest().getNativeResponse(requiredType);
	}
	/**
	 * 获取NativeRequest
	 * 
	 * @param requiredType Request类型,如果为null返回当前默认的ServletRequest
	 * 
	 * @return {@link ServletRequest}
	 */
	public static <T> T  getNativeRequest(Class<T> requiredType) {
		
		return getNativeWebRequest().getNativeRequest(requiredType);
	}
	
	/**
	 * 获取spring mvc的本地web request
	 * 
	 * @return {@link NativeWebRequest}
	 */
	public static NativeWebRequest getNativeWebRequest() {
		return (NativeWebRequest) RequestContextHolder.currentRequestAttributes();
	}
	
	/**
	 * 获取web项目中的真实路径
	 * 
	 * @param path 指定的虚拟路径
	 * 
	 * @return String
	 */
	public static String getRealPath(String path) {
		return getNativeRequest(HttpServletRequest.class).getSession().getServletContext().getRealPath(path);
	}
}
