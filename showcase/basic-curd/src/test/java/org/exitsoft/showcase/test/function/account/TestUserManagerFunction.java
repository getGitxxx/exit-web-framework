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
		//填写表单
		s.type(By.id("username"), "test_user");
		//s.type(By.id("realname"), "测试用户");
		s.type(By.id("password"), "123456");
		//s.type(By.id("confirmPassword"), "123456");
		//s.type(By.id("state"), "1");
		s.type(By.id("email"), "test_user@exitsoft.com");
		
		s.click(By.xpath("//button[@type='submit']"));
		WebElement element = s.findElement(By.className("alert alert-success fade in")).findElement(By.tagName("span"));
		assertEquals(element.getText(),"新增成功");
		//准确的是6条记录在列表中，因为已经添加了一条
		assertEquals(trs.size() + 1 ,s.findElement(By.tagName("table")).findElements(By.xpath("//tbody//tr")));
	}
	
}
