package org.exitsoft.showcase.dao.foundation;

import org.exitsoft.orm.core.hibernate.support.HibernateSupportDao;
import org.exitsoft.showcase.entity.foundation.DictionaryCategory;
import org.springframework.stereotype.Repository;

/**
 * 字典类别数据访问
 * 
 * @author vincent
 *
 */
@Repository
public class DictionaryCategoryDao extends HibernateSupportDao<DictionaryCategory, String>{

}
