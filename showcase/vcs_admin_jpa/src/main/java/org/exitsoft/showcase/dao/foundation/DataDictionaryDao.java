package org.exitsoft.showcase.dao.foundation;

import java.util.List;

import org.exitsoft.showcase.entity.foundation.DataDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据字典数据访问
 * 
 * @author vincent
 *
 */
public interface DataDictionaryDao extends JpaRepository<DataDictionary, String>, JpaSpecificationExecutor<DataDictionary>{

    /**
     * 根据字典类别代码获取数据字典集合
     *
     * @param code 列别代码
     *
     * @return List
     */
	List<DataDictionary> findAllByCategoryCode(String code);

    /**
     * 根据字典类别代码获取数据字典集合
     *
     * @param code 列别代码
     * @param ignoreValue 忽略字典的值
     *
     * @return List
     */
	List<DataDictionary> findByCategoryCodeAndValueNot(String code,String ignoreValue);

}
