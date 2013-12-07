package org.exitsoft.showcase.entity.foundation.variable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.exitsoft.common.type.FieldType;
import org.exitsoft.common.utils.ConvertUtils;
import org.exitsoft.showcase.entity.IdEntity;


/**
 * 数据字典实体
 * 
 * @author vincent
 *
 */
@Entity
@Table(name="TB_DATA_DICTIONARY")
@NamedQuery(name=DataDictionary.FindByCateGoryCode,query="from DataDictionary dd where dd.category.code = ?1")
public class DataDictionary extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 通过字典类别代码查询所有数据字典的NamedQuery名称
	 */
	public static final String FindByCateGoryCode = "findByCateGoryCode";
	
	/**
	 * 通过字典类别代码查询所有数据字典的缓存key名称,由于getDataDictionariesByCategoryCode方法带有可变参数
	 * 所以必须要自定义Cacheable的key名称，该key名称的规则为当前传入的code参数的getCode值加上可变参数的值并
	 * 于'-'分割。如:getDataDictionariesByCategoryCode(SystemDictionaryCode.Sate,"1","2","3");所生成key
	 * 名称为:state-1-2-3
	 */
	public static final String FindByCateGoryCodeCacheKey = "#code.getCode()+'-'+T(org.apache.commons.lang3.StringUtils).join(#ignoreValue, '-')";
	
	//名称
	private String name;
	//值
	private String value;
	//类型
	private String type = "S";
	//备注
	private String remark;
	//所属类别
	public DictionaryCategory category;
	
	public DataDictionary() {
		
	}
	
	/**
	 * 获取名称
	 * 
	 * @return String
	 */
	@Column(length=256,nullable=false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取值
	 * 
	 * @return String
	 */
	@Column(length=32,nullable=false)
	public String getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value 值
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取值类型
	 * 
	 * @return String
	 */
	@Column(length=1,nullable=false)
	public String getType() {
		return type;
	}

	/**
	 * 设置值类型
	 * 
	 * @param type 值类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取备注
	 * 
	 * @return String
	 */
	@Column(length=512)
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 * 
	 * @param remark 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取所属类别
	 * 
	 * @return {@link DictionaryCategory}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "FK_CATEGORY_ID", nullable=false)
	public DictionaryCategory getCategory() {
		return category;
	}

	/**
	 * 设置所属类别
	 * 
	 * @param category 所属类别
	 */
	public void setCategory(DictionaryCategory category) {
		this.category = category;
	}
	
	/**
	 * 根据type属性的值获取真正的值
	 * 
	 * @return Object
	 */
	@Transient
	public Object getReadValue() {
		return ConvertUtils.convertToObject(this.value, FieldType.valueOf(type).getValue());
	}
}
