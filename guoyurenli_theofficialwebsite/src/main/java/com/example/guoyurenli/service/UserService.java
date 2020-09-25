package com.example.guoyurenli.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
	// 传入用户名跟密码,把“记住我”也传过来了
	public void checkLogin(String userName, String userPwd) throws Exception	// 认证失败抛出异常，认证成功则没有
	{
		// 得到Subject
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, userPwd);
		subject.login(token);
	}
}
