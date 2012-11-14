package org.exitsoft.common.mapper;

import java.util.HashMap;
import java.util.Map;

import org.dozer.DozerBeanMapper;

/**
 * Java Bean 工具类
 * 
 * @author vincent
 *
 */
public class BeanMapper {
	
	private static DozerBeanMapper  dozer = new DozerBeanMapper();

	/**
	 * 构造新的destinationClass实例对象，通过source对象中的字段内容
	 * 映射到destinationClass实例对象中，并返回新的destinationClass实例对象。
	 * 
	 * @param source 源数据对象
	 * @param destinationClass 要构造新的实例对象Class
	 * 
	 * @return Object
	 */
	public static <T> T map(Object source, Class<T> destinationClass) {
		return dozer.map(source, destinationClass);
	}

	/**
	 * 将对象source的所有属性值拷贝到对象destination中.
	 * 
	 * @param source 对象source
	 * @param destination 对象destination
	 * 
	 */
	public static void map(Object source, Object destination) {
		dozer.map(source, destination);
	}
	
	/**
	 * 将目标对象的所有属性转换成Map对象
	 * 
	 * @param target 目标对象
	 * @param ignoreParent 是否忽略父类的属性
	 * @param ignoreEmptyValue 是否不把空值添加到Map中
	 * @param ignoreProperties 不需要添加到Map的属性名
	 * 
	 * @return Map
	 */
	public static <T> Map<String, T> toMap(Object target,boolean ignoreParent,boolean ignoreEmptyValue,String... ignoreProperties) {
		Map<String, T> map = new HashMap<String, T>();
		
		return map;
	}
	
}
