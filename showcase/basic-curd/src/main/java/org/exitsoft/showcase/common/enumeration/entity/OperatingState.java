package org.exitsoft.showcase.common.enumeration.entity;

/**
 * 操作状态
 * 
 * @author vincent
 *
 */
public enum OperatingState {
	/**
	 * 成功
	 */
	Success(1,"成功"),
	/**
	 * 失败
	 */
	Fail(2,"失败");
	
	//值
	private Integer value;
	//名称
	private String name;
	
	private OperatingState(Integer value,String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * 获取值
	 * @return Integer
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * 获取名称
	 * @return String
	 */
	public String getName() {
		return name;
	}
}
