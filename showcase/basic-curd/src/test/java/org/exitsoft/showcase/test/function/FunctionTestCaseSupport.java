package org.exitsoft.showcase.test.function;

import javax.sql.DataSource;

import org.eclipse.jetty.server.Server;
import org.exitsoft.common.spring.SpringContextHolder;
import org.exitsoft.common.unit.JettyFactory;
import org.exitsoft.common.unit.selenium.Selenium2;
import org.exitsoft.common.unit.selenium.WebDriverFactory;
import org.exitsoft.showcase.test.LaunchJetty;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

public class FunctionTestCaseSupport {
	
	//selenium settings, options include firefox,ie,chrome,remote:localhost:4444:firefox
	public static final String SELENIUM_DRIVER="firefox";
	public static final String URL="http://localhost:8080/exitsoft-basic-curd";
	
	protected static DataSource dataSource;
	protected static Server jettyServer;
	protected static JdbcTemplate jdbcTemplate;
	protected static ResourceLoader resourceLoader = new DefaultResourceLoader();
	protected static Selenium2 s;
	
	@BeforeClass
	public static void install() throws Exception {
		
		if (jettyServer == null) {
			// 设定Spring的profile
			System.setProperty(LaunchJetty.ACTIVE_PROFILE, "test");

			jettyServer = JettyFactory.createServerInSource(LaunchJetty.PORT, LaunchJetty.CONTEXT);
			JettyFactory.setTldJarNames(jettyServer, LaunchJetty.TLD_JAR_NAMES);
			System.out.println("[HINT] Don't forget to set -XX:MaxPermSize=128m");
			jettyServer.start();
		}
		
		if (dataSource == null) {
			dataSource = SpringContextHolder.getBean(DataSource.class);
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		
		if (s == null) {
			WebDriver driver = WebDriverFactory.createDriver(SELENIUM_DRIVER);
			s = new Selenium2(driver, URL);
			s.setStopAtShutdown();
		}
		
		executeScript(dataSource,"classpath:data/h2/cleanup-data.sql","classpath:data/h2/insert-data.sql");
		
		s.open("/");
		s.click(By.xpath("//button[@type='submit']"));
	}
	
	/**
	 * 批量执行sql文件
	 * 
	 * @param dataSource　dataSource
	 * @param sqlResourcePaths sql文件路径
	 * 
	 * @throws DataAccessException
	 */
	public static void executeScript(DataSource dataSource, String... sqlResourcePaths) throws DataAccessException {

		for (String sqlResourcePath : sqlResourcePaths) {
			JdbcTestUtils.executeSqlScript(jdbcTemplate, resourceLoader, sqlResourcePath, true);
		}
	}
	
	@Test
	public void emptyTestMethod() {
		
	}
}
