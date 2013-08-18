package org.exitsoft.showcase.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.exitsoft.common.utils.CaptchaUtils;
import org.exitsoft.showcase.common.SystemVariableUtils;
import org.exitsoft.showcase.service.account.AccountManager;
import org.exitsoft.showcase.service.account.CaptchaAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 系统安全控制器
 * 
 * @author vincent
 *
 */
@Controller
public class SystemCommonController {
	
	@Autowired
	private AccountManager accountManager;
	
	/**
	 * 登录C，返回登录页面。当C发现当前用户已经登录名且认真后，会自动跳转到index页面
	 * 
	 * @return String
	 */
	@RequestMapping("/login")
	public String login(HttpServletResponse response) {
		if (!SystemVariableUtils.isAuthenticated()) {
			return "login";
		}
		return "redirect:/main";
	}
	
	/**
	 * 首页C,在request中获取当前用户的菜单集合给页面循环
	 * 
	 * @param model String mvc model 接口
	 * 
	 * @return String
	 */
	@RequestMapping("/main")
	public String main(Model model) {
		
		model.addAttribute("cvm", SystemVariableUtils.getCommonVariableModel());
		
		return "main";
	}

    /**
     * 默认进入首页的C，因为使用一个页面去做登录或未登录的功能，所以默认放一个页面在那。
     * 在index里面会马上去加载main控制器，如果用户没登录时，会跳转到login控制器中。
     */
    @RequestMapping("/index")
    public void index(){}
	
	/**
	 * 当前用户修改密码C.修改成功返回"true"否则返回"false"
	 * 
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * 
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("/changePassword")
	public String changePassword(String oldPassword,String newPassword) {
		
		if (accountManager.updateUserPassword(oldPassword,newPassword)) {
			return "true";
		}
		
		return "false";
		
	}
	
	/**
	 * 生成验证码
	 * 
	 * @return byte[]
	 * @throws IOException 
	 */
	@RequestMapping("/getCaptcha")
	public ResponseEntity<byte[]> getCaptcha(HttpSession session) throws IOException {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_GIF);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String captcha = CaptchaUtils.getCaptcha(80, 32, 5, outputStream).toLowerCase();
		
		session.setAttribute(CaptchaAuthenticationFilter.DEFAULT_CAPTCHA_PARAM,captcha);
		byte[] bs = outputStream.toByteArray();
		outputStream.close();
		
		return new ResponseEntity<byte[]>(bs,headers, HttpStatus.OK);
	}
}
