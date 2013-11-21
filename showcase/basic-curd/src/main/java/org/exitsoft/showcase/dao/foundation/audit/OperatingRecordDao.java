package org.exitsoft.showcase.dao.foundation.audit;

import java.util.List;

import org.exitsoft.common.utils.CollectionUtils;
import org.exitsoft.common.utils.ReflectionUtils;
import org.exitsoft.orm.core.PropertyFilter;
import org.exitsoft.orm.core.hibernate.support.HibernateSupportDao;
import org.exitsoft.showcase.entity.foundation.audit.OperatingRecord;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.internal.CriteriaImpl.Subcriteria;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

/**
 * 操作记录数据访问
 * 
 * @author vincent
 *
 */
@Repository
public class OperatingRecordDao extends HibernateSupportDao<OperatingRecord, String>{
	
	/**
	 * 重写父类方法，当存在根据用户名查询时，构建QBC关联
	 */
	@Override
	protected Criteria createCriteria(List<PropertyFilter> filters,Order... orders) {
		
		Criteria criteria = createCriteria();

		if (CollectionUtils.isEmpty(filters)) {
			return criteria;
		}
		
		for (PropertyFilter filter : filters) {
			String[] pns = filter.getPropertyNames();
			
			for (String pn : pns) {
				//获取子QBC，用子QBC判断是否已经存在别名的关联，如：subcriteriaList.size() > 0时代表已经关联
				List<Subcriteria> subcriteriaList = ReflectionUtils.getFieldValue(criteria, "subcriteriaList");
				if (pn.contains("user.") && !pn.contains("user.id") && subcriteriaList.size() <= 0) {
					criteria.createAlias("user", "user",JoinType.LEFT_OUTER_JOIN);
				}
			}
			
			criteria.add(createCriterion(filter));
		}
		
		setOrderToCriteria(criteria, orders);
		
		return criteria;
	}
}
