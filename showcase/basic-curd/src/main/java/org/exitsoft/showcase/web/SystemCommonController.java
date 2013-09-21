package org.exitsoft.showcase.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.exitsoft.common.spring.mvc.SpringMvcHolder;
import org.exitsoft.common.utils.CaptchaUtils;
import org.exitsoft.common.utils.ImageUtils;
import org.exitsoft.showcase.common.SystemVariableUtils;
import org.exitsoft.showcase.entity.account.User;
import org.exitsoft.showcase.service.account.AccountManager;
import org.exitsoft.showcase.service.account.CaptchaAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


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
	 * 上传临时文件夹名称
	 */
	public static String TEMP_UPLOAD_DIRECTORY = "temp_upload";
	
	/**
	 * 登录C，返回登录页面。当C发现当前用户已经登录名且认真后，会自动跳转到index页面
	 * 
	 * @return String
	 */
	@RequestMapping("/login")
	public String login() {
		
		if (!SystemVariableUtils.isAuthenticated()) {
			return "login";
		}
		return "redirect:/index";
	}

    /**
     * 默认进入首页的C，因为使用一个页面去做登录或未登录的功能，所以默认放一个页面在那。
     * 在index里面会马上去加载main控制器，如果用户没登录时，会跳转到login控制器中。
     */
    @RequestMapping("/index")
    public void index(){}
    
	
	/**
	 * 当前用户修改密码C.修改成功将会注销用户，重新登录
	 * 
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * 
	 * @return String
	 */
	@RequestMapping("/change-password")
	public String changePassword(String oldPassword,String newPassword) {
		
		accountManager.updateUserPassword(oldPassword,newPassword);
			
		return "redirect:/logout";
		
	}
	
	/**
	 * 修改个人信息C，修改成功将会重定向到主页
	 * 
	 * @param user 用户实体
	 * 
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/change-profile")
	public String changeProfile(String realname,String email,@RequestParam(required = false)String portrait) throws IOException {
		User entity = SystemVariableUtils.getCommonVariableModel().getUser();
		
		entity.setRealname(realname);
		entity.setEmail(email);
		
		if (StringUtils.isNotEmpty(portrait)) {
			
			String path = SpringMvcHolder.getRealPath("");
			
			if (StringUtils.isEmpty(entity.getPortrait())) {
				entity.setPortrait("portrait/" + entity.getUsername() + "-portrait-file");
			}
			
			File portraitFile = new File(path + File.separator + entity.getPortrait());
			
			if(!portraitFile.exists()) {
				portraitFile.createNewFile();
			}
			
			File tempFile = new File(path + File.separator + TEMP_UPLOAD_DIRECTORY + File.separator + portrait);
			
			FileUtils.copyFile(tempFile, portraitFile);
			tempFile.deleteOnExit();
		}
		
		accountManager.updateUser(entity);
		return "redirect:/index";
	}
	
	/**
	 * 文件上传的临时存放目录C，将上传上来的文件存储到webapp下杂temp_upload的文件夹中
	 * 
	 * @param file
	 * @return String
	 * 
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/temp-upload")
	public String tempUpload(@RequestParam("file")CommonsMultipartFile file) throws IllegalStateException, IOException {
		String path = SpringMvcHolder.getRealPath("");
		String name = "temp_upload_" + UUID.randomUUID().toString().replaceAll("-", "");
		
		File tempFile = new File(path + File.separator + TEMP_UPLOAD_DIRECTORY + File.separator + name);
		ImageUtils.scale(file.getInputStream(), tempFile, 80, 80);
		return name;
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
