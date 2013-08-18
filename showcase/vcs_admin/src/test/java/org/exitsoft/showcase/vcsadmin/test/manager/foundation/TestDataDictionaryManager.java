package org.exitsoft.showcase.vcsadmin.test.manager.foundation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.exitsoft.common.type.FieldType;
import org.exitsoft.showcase.vcsadmin.common.SystemVariableUtils;
import org.exitsoft.showcase.vcsadmin.common.enumeration.SystemDictionaryCode;
import org.exitsoft.showcase.vcsadmin.entity.foundation.DataDictionary;
import org.exitsoft.showcase.vcsadmin.entity.foundation.DictionaryCategory;
import org.exitsoft.showcase.vcsadmin.service.foundation.SystemDictionaryManager;
import org.exitsoft.showcase.vcsadmin.test.manager.ManagerTestCaseSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试数据字典管理所有方法
 * 
 * @author vincent
 *
 */
public class TestDataDictionaryManager extends ManagerTestCaseSupport{
	
	@Autowired
	private SystemDictionaryManager systemDictionaryManager;
	
	@Test
	public void testGetDataDictionariesByCategoryCode() {
		
		String valueNoCache = SystemVariableUtils.getDictionaryNameByValue(SystemDictionaryCode.State, "1");
		String valueCache = SystemVariableUtils.getDictionaryNameByValue(SystemDictionaryCode.State, "1");

		assertEquals(valueCache.hashCode(), valueNoCache.hashCode());
		
	}
	
	@Test
	public void testDeleteDataDictionary() {
		
		List<String> ids = new ArrayList<String>();
		CollectionUtils.addAll(ids, new String[]{"SJDK3849CKMS3849DJCK2039ZMSK0018","SJDK3849CKMS3849DJCK2039ZMSK0019"});
		
		int beforeRow = countRowsInTable("TB_DATA_DICTIONARY");
		systemDictionaryManager.deleteDataDictionary(ids);
		int afterRow = countRowsInTable("TB_DATA_DICTIONARY");
		
		assertEquals(afterRow, beforeRow - 2);
	}
	
	@Test
	public void testSaveDataDictionary() {
		
		DictionaryCategory category = systemDictionaryManager.getDictionaryCategory("SJDK3849CKMS3849DJCK2039ZMSK0015");
		
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setCategory(category);
		dataDictionary.setName("test");
		dataDictionary.setValue("4");
		dataDictionary.setType(FieldType.I.toString());
		dataDictionary.setRemark("*");
		
		int beforeRow = countRowsInTable("TB_DATA_DICTIONARY");
		systemDictionaryManager.saveDataDictionary(dataDictionary);
		int afterRow = countRowsInTable("TB_DATA_DICTIONARY");
		
		assertEquals(afterRow, beforeRow + 1);
		
	}
	
	
}
