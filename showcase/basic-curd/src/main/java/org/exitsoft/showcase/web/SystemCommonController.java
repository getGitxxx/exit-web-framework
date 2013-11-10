package org.exitsoft.showcase.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.exitsoft.common.bundle.BeanResourceBundle;
import org.exitsoft.common.spring.mvc.SpringMvcHolder;
import org.exitsoft.common.utils.CaptchaUtils;
import org.exitsoft.showcase.common.SystemVariableUtils;
import org.exitsoft.showcase.entity.account.User;
import org.exitsoft.showcase.service.account.AccountManager;
import org.exitsoft.showcase.service.account.CaptchaAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	 * 空头像图片文件
	 */
	public final String EMPTY_PORTRAIT_PATH = "\\resource\\image\\empty.png";
	
	//上传临时文件夹路径
	@Value("${file.upload.temp.path}")
	private String fileUploadTempPath;
	
	//上传文件存放的真实路径
	@Value("${file.upload.path}")
	private String fileUploadPath;
	
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
     * 默认进入首页的C
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
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping("/change-profile")
	public Map<String, Object> changeProfile(String realname,String email,@RequestParam(required = false)String portrait) throws IOException {
		//获取当前用户
		User entity = SystemVariableUtils.getCommonVariableModel().getUser();
		
		entity.setRealname(realname);
		entity.setEmail(email);
		
		//如果存在头像文件,将替换原有的头像文件
		if (StringUtils.isNotEmpty(portrait)) {
			//判断是否当前用户为首次更改头像，如果是，将图片路径赋值到portrait中
			if (StringUtils.isEmpty(entity.getPortrait())) {
				entity.setPortrait(fileUploadPath + entity.getId());
			}
			//获取用户头像路径
			File portraitFile = new File(entity.getPortrait());
			//如果头像不不存在就创建头像文件
			if (!portraitFile.exists()) {
				portraitFile.createNewFile();
			}
			//获取临时头像文件
			File tempFile = new File(fileUploadTempPath + portrait);
			//复制文件到文件存放的真实路径中，不临时文件，由SystemScheduled类的deleteTempUploadFile来执行删除
			FileUtils.copyFile(tempFile, portraitFile);
		}
		
		accountManager.updateUser(entity);
		SystemVariableUtils.getCommonVariableModel().setUser(entity);
		
		return MapUtils.toMap(new BeanResourceBundle(entity,new String[]{"realname"}));
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
		String name = "temp_upload_" + UUID.randomUUID().toString().replaceAll("-", "");
		
		File tempFile = new File(fileUploadTempPath + name);
		tempFile.mkdirs();
		file.transferTo(tempFile);
		
		return name;
	}
	
	/**
	 * 通过名称获取临时文件
	 * 
	 * @param name 临时文件名称
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/get-temp-upload-file")
	public ResponseEntity<byte[]> getTempUploadFile(String name) throws IOException {
		File tempFile = new File(fileUploadTempPath + name);
		byte[] b = FileUtils.readFileToByteArray(tempFile);
		return new ResponseEntity<byte[]>(b, HttpStatus.OK);
	}
	
	/**
	 * 生成验证码
	 * 
	 * @throws IOException 
	 */
	@RequestMapping("/get-captcha")
	public ResponseEntity<byte[]> getCaptcha(HttpSession session) throws IOException {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String captcha = CaptchaUtils.getCaptcha(80, 32, 5, outputStream).toLowerCase();
		
		session.setAttribute(CaptchaAuthenticationFilter.DEFAULT_CAPTCHA_PARAM,captcha);
		byte[] bs = outputStream.toByteArray();
		outputStream.close();
		return new ResponseEntity<byte[]>(bs,headers, HttpStatus.OK);
	}
	
	/**
	 * 获取当前用户头像
	 * 
	 * @return {@link ResponseEntity}
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/get-current-user-portrait")
	public ResponseEntity<byte[]> getCurrentUserPortrait() throws IOException {
		
		String portrait = SystemVariableUtils.getCommonVariableModel().getUser().getPortrait();
		
		//如果头像为空，设置默认空头像
		if (StringUtils.isEmpty(portrait)) {
			portrait = SpringMvcHolder.getRealPath("") + EMPTY_PORTRAIT_PATH;
		}
		
		File f = new File(portrait);
		
		byte[] b = FileUtils.readFileToByteArray(f);
		
		return new ResponseEntity<byte[]>(b, HttpStatus.OK);
	}
}
