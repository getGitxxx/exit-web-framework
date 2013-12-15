package org.exitsoft.orm.test.hibernate;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import javax.sql.DataSource;

import org.exitsoft.common.unit.Fixtures;
import org.exitsoft.orm.core.hibernate.support.HibernateAuditSupportDao;
import org.exitsoft.orm.test.entity.User;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
public class TestHibernateAuditSupportDao {
	
	private HibernateAuditSupportDao<User, String> userAuditDao;
	
	private SessionFactory sessionFactory;
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private DataSource dataSource;
	
	/**
	 * 通过表名计算出表中的总记录数
	 * 
	 * @param tableName 表名
	 * 
	 * @return int
	 */
	protected int countRowsInTable(String tableName) {
		return jdbcTemplate.queryForObject("SELECT COUNT(0) FROM " + tableName,new HashMap<String, Object>(),Integer.class);
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		userAuditDao = new HibernateAuditSupportDao<User, String>(User.class);
		userAuditDao.setSessionFactory(sessionFactory);
		this.sessionFactory = sessionFactory;
	}
	
	@Before
	public void install() throws Exception {
		Fixtures.reloadData(dataSource, "/sample-data.xml");
	}
	
	@Test
	public void testUpdate() {
		User user = userAuditDao.load("SJDK3849CKMS3849DJCK2039ZMSK0001");
		user.setLoginName("update login name");
		
		userAuditDao.update(user);
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().clear();
		
		user = userAuditDao.load("SJDK3849CKMS3849DJCK2039ZMSK0001");
		assertEquals(user.getLoginName(), "update login name");
	}
}
