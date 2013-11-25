package org.exitsoft.showcase.entity.foundation.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.exitsoft.showcase.entity.IdEntity;

@Entity
@SuppressWarnings("serial")
@Table(name="TB_RECORD_PARAMETER")
public class RecordParameter extends IdEntity{
	
	//参数名称
	private String name;
	//参数值
	private String value;
	//对应的操作记录
	private OperatingRecord record;
	
	/**
	 * 构造方法
	 */
	public RecordParameter() {
		
	}

	/**
	 * 获取参数名称
	 * 
	 * @return String
	 */
	@Column(length=32,nullable=false)
	public String getName() {
		return name;
	}

	/**
	 * 设置参数名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取参数值
	 * 
	 * @return String
	 */
	@Column(length=3072,nullable=false)
	public String getValue() {
		return value;
	}

	/**
	 * 设置参数值
	 * 
	 * @param value 参数值
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取对应的操作记录
	 * 
	 * @return {@link OperatingRecord}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "FK_RECORD_ID", nullable=false)
	public OperatingRecord getRecord() {
		return record;
	}

	/**
	 * 设置对应的操作记录
	 * 
	 * @param record 操作记录实体
	 */
	public void setRecord(OperatingRecord record) {
		this.record = record;
	}
	
}
