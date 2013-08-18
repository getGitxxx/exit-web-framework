package org.exitsoft.showcase.dao.account;

import org.exitsoft.orm.core.hibernate.support.HibernateSupportDao;
import org.exitsoft.showcase.entity.account.Group;
import org.springframework.stereotype.Repository;

/**
 * 部门数据访问
 * 
 * @author vincent
 *
 */
@Repository
public class GroupDao extends HibernateSupportDao<Group, String>{

}
