package org.exitsoft.showcase.test.function.account;

import static org.junit.Assert.*;

import java.util.List;

import org.exitsoft.showcase.test.function.FunctionTestCaseSupport;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 测试用户管理功能
 * 
 * @author vincent
 *
 */
public class TestUserManagerFunction extends FunctionTestCaseSupport{
	
	@Test
	public void test() {
		s.open("/");
		//点击权限管理展开菜单
		s.click(By.id("SJDK3849CKMS3849DJCK2039ZMSK0003"));
		//点击菜单中的用户管理菜单
		s.click(By.id("SJDK3849CKMS3849DJCK2039ZMSK0004"));
		//获取table中的所有tr
		List<WebElement> trs = s.findElement(By.tagName("table")).findElements(By.xpath("//tbody//tr"));
		//准确的是5条记录在列表中
		assertEquals(trs.size(), 5);
		
		//打开创建用户页面
		s.click(By.xpath("//a[@href='/exitsoft-basic-curd/account/user/read']"));
	}
	
}
