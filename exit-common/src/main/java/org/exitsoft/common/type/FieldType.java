package org.exitsoft.common.type;

import java.util.Date;

/**
 * 属性数据类型
 * S代表String,I代表Integer,L代表Long, N代表Double, D代表Date,B代表Boolean
 * @author vincent
 * 
 */
public enum FieldType {
	
	/**
	 * String
	 */
	S(String.class),
	/**
	 * Integer
	 */
	I(Integer.class),
	/**
	 * Long
	 */
	L(Long.class),
	/**
	 * Double
	 */
	N(Double.class), 
	/**
	 * Date
	 */
	D(Date.class), 
	/**
	 * Boolean
	 */
	B(Boolean.class);

	//类型Class
	private Class<?> clazz;

	private FieldType(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * 获取类型Class
	 * 
	 * @return Class
	 */
	public Class<?> getValue() {
		return clazz;
	}
}
