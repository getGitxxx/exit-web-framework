package org.exitsoft.showcase.web.account;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.exitsoft.common.spring.mvc.SpringMvcHolder;
import org.exitsoft.orm.core.Page;
import org.exitsoft.orm.core.PageRequest;
import org.exitsoft.orm.core.PageRequest.Sort;
import org.exitsoft.orm.core.PropertyFilter;
import org.exitsoft.orm.core.PropertyFilters;
import org.exitsoft.showcase.common.SystemVariableUtils;
import org.exitsoft.showcase.common.enumeration.SystemDictionaryCode;
import org.exitsoft.showcase.common.enumeration.entity.GroupType;
import org.exitsoft.showcase.entity.account.User;
import org.exitsoft.showcase.entity.foundation.DataDictionary;
import org.exitsoft.showcase.service.account.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 用户管理Controller
 * 
 * @author vincent
 *
 */
@Controller
@RequestMapping("/account/user")
public class UserController {
	
	@Autowired
	private AccountManager accountManager;
	
	/**
	 * 获取用户列表
	 * 
	 * @param pageRequest 分页实体信息
	 * @param request HttpServlet请求
	 * 
	 * @return {@link Page}
	 */
	@RequestMapping("view")
	public Page<User> view(PageRequest pageRequest,HttpServletRequest request) {
		
		List<PropertyFilter> filters = PropertyFilters.build(request, true);

		request.setAttribute("states", SystemVariableUtils.getDataDictionariesByCategoryCode(SystemDictionaryCode.State,"3"));
		
		if (!pageRequest.isOrderBySetted()) {
			pageRequest.setOrderBy("id");
			pageRequest.setOrderDir(Sort.DESC);
		}
		
		return accountManager.searchUserPage(pageRequest, filters);
	}
	
	@RequestMapping("get-portrait")
	public ResponseEntity<byte[]> getPortrait(@ModelAttribute("entity")User entity) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_GIF);
		
		String path = SpringMvcHolder.getRealPath("") + File.separator + entity.getPortrait();
		
		File portrait = new File(path);
		
		if (!portrait.exists()) {
			portrait = new File(SpringMvcHolder.getRealPath("") + File.separator + "portrait" + File.separator + "empty.jpg");
		}
		
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(portrait), headers, HttpStatus.OK);
	}
	
	
	/**
	 * 创建用户,创建成功后重定向到:account/user/view
	 * 
	 * @param entity 实体信息
	 * @param groupIds 所在组id
	 * @param redirectAttributes spring mvc 重定向属性
	 * 
	 * @return String
	 * 
	 */
	@RequestMapping("insert")
	public String insert(User entity,
							   @RequestParam(required=false)List<String> groupId,
							   RedirectAttributes redirectAttributes) {
		
		entity.setGroupsList(accountManager.getGroups(groupId));
		
		accountManager.insertUser(entity);
		redirectAttributes.addFlashAttribute("success", "新增成功");
		
		return "redirect:/account/user/view";
	}
	
	/**
	 * 通过主键id集合删除用户,删除成功后重定向到:account/user/view
	 * 
	 * @param ids 主键id集合
	 * @param redirectAttributes spring mvc 重定向属性
	 * 
	 * @return String
	 */
	@RequestMapping("delete")
	public String delete(@RequestParam("ids")List<String> ids,RedirectAttributes redirectAttributes) {
		accountManager.deleteUsers(ids);
		redirectAttributes.addFlashAttribute("success", "删除" + ids.size() + "条信息成功");
		return "redirect:/account/user/view";
	}
	
	/**
	 * 更新用户，更新成功后重定向到:account/user/view
	 * 
	 * @param entity 实体信息
	 * @param groupIds 所在组id
	 * @param redirectAttributes spring mvc 重定向属性
	 * 
	 * @return String
	 */
	@RequestMapping(value="update")
	public String update(@ModelAttribute("entity")User entity, 
								 @RequestParam(required=false)List<String> groupId,
								 RedirectAttributes redirectAttributes) {

		entity.setGroupsList(accountManager.getGroups(groupId));
		
		accountManager.updateUser(entity);
		redirectAttributes.addFlashAttribute("success", "修改成功");
		return "redirect:/account/user/view";
	}
	
	/**
	 * 判断用户帐号是否唯一,在新建时使用,如果存在用户返回"true",否则返回"false"
	 * 
	 * @param username 用户帐号
	 * 
	 * @return boolean 
	 */
	@ResponseBody
	@RequestMapping("isUsernameUnique")
	public String isUsernameUnique(String username) {
		
		return String.valueOf(accountManager.isUsernameUnique(username));
		
	}
	
	/**
	 * 创建和更新使用的方法签名.如果链接没有?id=*会跳转到create.flt,如果存在跳转到read.ftl
	 * 
	 * @param id 主键id
	 * @param model Spring mvc的Model接口，主要是将model的属性返回到页面中
	 * 
	 * @return String
	 * 
	 */
	@RequestMapping("read")
	public String read(@RequestParam(value = "id", required = false)String id,Model model) {
		
		List<DataDictionary> data =null;
		data = SystemVariableUtils.getDataDictionariesByCategoryCode(SystemDictionaryCode.State,"3");
		
		model.addAttribute("states", data);
		model.addAttribute("groupsList", accountManager.getAllGroup(GroupType.RoleGorup));
		
		if (StringUtils.isEmpty(id)) {
			return "account/user/create";
		} else {
			return "account/user/read";
		}
		
	}
	
	/**
	 * 绑定实体数据，如果存在id时获取后从数据库获取记录，进入到相对的C后在将数据库获取的记录填充到相应的参数中
	 * 
	 * @param id 主键ID
	 * 
	 */
	@ModelAttribute("entity")
	public User bindingModel(@RequestParam(value = "id", required = false)String id) {
		
		User user = new User();
		
		if (StringUtils.isNotEmpty(id)) {
			user = accountManager.getUser(id);
		}
		
		return user;
	}
	
}
