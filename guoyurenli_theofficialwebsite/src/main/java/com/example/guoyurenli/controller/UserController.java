package com.example.guoyurenli.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.guoyurenli.entity.GyUsers;
import com.example.guoyurenli.mapper.UserMapper;
import com.example.guoyurenli.service.UserService;

import af.spring.AfRestData;
import af.spring.AfRestError;

@Controller
public class UserController
{
	@Autowired
	private UserService userService;
	
	@Autowired
	UserMapper userMapper;
	
	// 登录页面
	@GetMapping("login")
	public String login()
	{
		return "login";
	}
	
	// 登录REST
	@PostMapping("login.do")
	public String logindo(Model model, String userName, String userPwd)
	{
		try
		{
			// 登录校验
			userService.checkLogin(userName, userPwd);
			
			// 取得当前用户的信息放入会话
			Subject subject = SecurityUtils.getSubject();
			GyUsers user = (GyUsers) subject.getPrincipal();
			model.addAttribute("user", user);
			
			// 如果是供应商身份，就直接给他返回到岗位主页
			if(subject.hasRole("supplier"))
			{
				return "enterprise/factoryTop";
			}
			
			return "index";
		} catch (Exception e)
		{
			return "login";
		}
	}
	
	// 注册页面
	@GetMapping("regist")
	public String regist(String name, String password)
	{
		return "regist";
	}
	
	// 注册REST
	@PostMapping("regist.do")
	public Object registdo(String userName
			, String userPassword
			, String userPasswordA
			, Integer roles )
	{
		if(userName.length() == 0 || userName == null || userPassword.length() == 0 || userPassword == null){
			return new AfRestError("账号或者密码输入有误。");
		}
		
		if(!userPassword.equals(userPasswordA)) {
			return new AfRestError("两次输入的密码不一致");
		}
		
		// 密码加密
		userPassword = new Md5Hash(userPassword).toString();
		
		GyUsers user = new GyUsers();
		user.username = userName;		// 账号
		user.password = userPassword;	// 密码
		user.timeCreate = new Date();	// 注册时间
		
		try
		{
			// 添加用户,返回ID
			userMapper.registGyUsers(user);
			// 为用户分配角色
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", user.user_id);
			map.put("rid", roles);
			userMapper.registGyRoles(map);
		} catch (DuplicateKeyException e)
		{
			return new AfRestError("用户名重复");
		}
		
		return "regist";
	}
	
	@GetMapping("index")
	public String index(Model model)
	{
		// 取得当前用户的信息放入会话
		GyUsers user = (GyUsers)SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "index";
	}
	
	// 修改密码页面
	@GetMapping("alterAsk")
	public String alterAsk()
	{
		return "alterAsk";
	}
	
	// 修改密码Rest
	@PostMapping("alterAsk.do")
	public Object alterAskdo( String userName
			, String userPwd
			, String userPwdA
			, String userPwdB)
	{
		if(userName.length() == 0 || userName == null || userPwd.length() == 0 || userPwd == null){
			return new AfRestError("原用户名或密码错误");
		}
		
		if(!userPwdA.equals(userPwdB)) {
			return new AfRestError("两次输入的密码不一致");
		}
		
		try
		{
			// 登录校验
			userService.checkLogin(userName, userPwd);
			
		} catch (Exception e)
		{
			return new AfRestError("原用户名或密码错误");
		}
		
		// 密码加密
		userPwdA = new Md5Hash(userPwdA).toString();
		// 取得当前用户的信息放入会话
		GyUsers user = (GyUsers)SecurityUtils.getSubject().getPrincipal();
		
		// 修改密码
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("password", userPwdA);
		map.put("id", user.user_id);
		userMapper.alterAsk(map);
		
		return new AfRestData("修改成功");
	}
	
}
