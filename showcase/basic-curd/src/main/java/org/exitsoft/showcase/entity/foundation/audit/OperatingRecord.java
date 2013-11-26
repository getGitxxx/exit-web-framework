package org.exitsoft.showcase.entity.foundation.audit;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.exitsoft.showcase.common.SystemVariableUtils;
import org.exitsoft.showcase.common.enumeration.SystemDictionaryCode;
import org.exitsoft.showcase.entity.foundation.BasicRecordProperty;

import com.google.common.collect.Lists;

/**
 * 操作记录类，记录用户的操作信息
 * 
 * @author vincent
 *
 */
@Entity
@SuppressWarnings("serial")
@Table(name="TB_OPERATING_RECORD")
public class OperatingRecord extends BasicRecordProperty{
	
	//ip地址
	private String ip;
	//操作的java方法
	private String method;
	//提交的参数值
	private List<RecordParameter> recordParametersList = Lists.newArrayList();
	//执行状态,1代表成，2代表执行时出现异常
	private Integer state;
	//模块名称
	private String module;
	//功能名称
	private String function;
	//描述
	private String remark;
	
	/**
	 * 构造方法
	 */
	public OperatingRecord() {
		
	}

	/**
	 * 获取id地址
	 * 
	 * @return String
	 */
	@Column(length=64,nullable=false)
	public String getIp() {
		return ip;
	}

	/**
	 * 设置id地址
	 * 
	 * @param ip id地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取操作的java方法
	 * 
	 * @return String
	 */
	@Column(length=256,nullable=false)
	public String getMethod() {
		return method;
	}

	/**
	 * 设置操作的java方法
	 * 
	 * @param method java方法名称
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 获取本次所传入的参数值
	 * 
	 * @return List
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="record",cascade={CascadeType.ALL})
	public List<RecordParameter> getRecordParametersList() {
		return recordParametersList;
	}

	/**
	 * 设置本次所传入的参数值
	 * 
	 * @param recordParametersList 传入的参数值
	 */
	public void setRecordParametersList(List<RecordParameter> recordParametersList) {
		this.recordParametersList = recordParametersList;
	}

	/**
	 * 获取执行状态,1代表成，2代表执行时出现异常
	 * 
	 * @return Integer
	 */
	@Column(nullable=false)
	public Integer getState() {
		return state;
	}

	/**
	 * 设置执行状态
	 * @param state 1代表成，2代表执行时出现异常
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	
	/**
	 * 获取模块名称
	 * 
	 * @return String
	 */
	@Column(length=128)
	public String getModule() {
		return module;
	}

	/**
	 * 设置模块名称
	 * 
	 * @param module 模块名称
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * 获取功能名称
	 * 
	 * @return String
	 */
	@Column(length=128)
	public String getFunction() {
		return function;
	}

	/**
	 * 设置功能名称
	 * 
	 * @param function 功能名称
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * 获取描述
	 * 
	 * @return String
	 */
	@Lob
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置描述
	 * 
	 * @param remark 描述
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 获取状态的中文名称
	 * 
	 * @return String
	 */
	@Transient
	public String getStateName() {
		return SystemVariableUtils.getName(SystemDictionaryCode.OperatingState, this.state);
	}
	
}
