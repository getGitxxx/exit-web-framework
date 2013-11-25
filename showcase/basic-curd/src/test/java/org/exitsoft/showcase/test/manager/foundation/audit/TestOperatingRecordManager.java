package org.exitsoft.showcase.test.manager.foundation.audit;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.exitsoft.orm.core.Page;
import org.exitsoft.orm.core.PageRequest;
import org.exitsoft.orm.core.PropertyFilter;
import org.exitsoft.orm.core.PropertyFilters;
import org.exitsoft.showcase.common.enumeration.entity.OperatingState;
import org.exitsoft.showcase.entity.foundation.audit.OperatingRecord;
import org.exitsoft.showcase.service.foundation.SystemAuditManager;
import org.exitsoft.showcase.test.manager.ManagerTestCaseSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * 测试操作记录业务逻辑
 * 
 * @author vincent
 *
 */
public class TestOperatingRecordManager extends ManagerTestCaseSupport{

	@Autowired
	private SystemAuditManager systemAuditManager;
	
	@Test
	@Transactional(readOnly=true)
	public void testGetOperatingRecord() {
		
		OperatingRecord or = systemAuditManager.getOperatingRecord("SJDK3849CKMS3849DJCK2039ZMSK0026");
		
		assertEquals(or.getFkUserId(),"SJDK3849CKMS3849DJCK2039ZMSK0001");
		assertEquals(or.getRemark(),"test remark");
		
	}
	
	@Test
	@Transactional
	public void testInsertOperatingRecord() {
		OperatingRecord or = new OperatingRecord();
		
		or.setStartDate(new Date());
		or.setIp("127.0.0.1");
		or.setMethod("org.exitsoft.showcase.test.manager.foundation.audit.OperatingRecordManager.testSaveOperatingRecord()");
		or.setOperatingTarget("account/user/view");
		or.setState(OperatingState.Success.getValue());
		
		or.setFkUserId("SJDK3849CKMS3849DJCK2039ZMSK0026");
		or.setUsername("admin");
		or.setEndDate(new Date());
		
		int beforeRow = countRowsInTable("TB_OPERATING_RECORD");
		systemAuditManager.insertOperatingRecord(or);
		getSessionFactory().getCurrentSession().flush();
		int afterRow = countRowsInTable("TB_OPERATING_RECORD");
		
		assertEquals(afterRow, beforeRow + 1);
	}
	
	@Test
	public void testSearchOperatingRecordPage(){
		PageRequest request = new PageRequest();
		
		List<PropertyFilter> filters = Lists.newArrayList(
			PropertyFilters.build("LIKES_username", "admin"),
			PropertyFilters.build("EQS_ip", "127.0.0.1")
		);
		
		Page<OperatingRecord> page = systemAuditManager.searchOperatingRecordPage(request, filters);
		
		assertEquals(page.getTotalItems(), 1);
		assertEquals(page.getTotalPages(), 1);
		
	}
	
}
