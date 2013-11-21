package org.exitsoft.showcase.entity.foundation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.exitsoft.showcase.entity.UniversallyUniqueIdentifier;
import org.exitsoft.showcase.entity.account.User;

/**
 * 基础操作记录属性,该类仅仅是一个父类，提供给audit包下的类去扩展自己的属性
 * 
 * @author vincent
 *
 */
@MappedSuperclass
@SuppressWarnings("serial")
public class BasicRecordProperty extends UniversallyUniqueIdentifier{
	
	//操作人
	private User user;
	//操作开始时间
	private Date startDate;
	//操作结束时间
	private Date endDate;
	//操作目标
	private String operatingTarget;
	
	public BasicRecordProperty() {
		
	}
	
	/**
	 * 获取操作人
	 * 
	 * @return {@link User}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "FK_USER_ID", nullable=false)
	public User getUser() {
		return user;
	}

	/**
	 * 设置操作人
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 获取操作开始时间
	 * 
	 * @return Date
	 */
	@Column(nullable=false)
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * 设置操作开始时间
	 * 
	 * @param startDate 操作开始时间
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 获取操作结束时间
	 * 
	 * @return Date
	 */
	@Column(nullable=false)
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置操作结束时间
	 * 
	 * @param endDate 操作结束时间
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取操作目标
	 * 
	 * @return String
	 */
	@Column(length=512,nullable=false)
	public String getOperatingTarget() {
		return operatingTarget;
	}

	/**
	 * 设置操作目标
	 * 
	 * @param operatingTarget 操作目标
	 */
	public void setOperatingTarget(String operatingTarget) {
		this.operatingTarget = operatingTarget;
	}
	
}
