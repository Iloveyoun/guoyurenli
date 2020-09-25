package com.example.guoyurenli.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.guoyurenli.entity.GyUsers;
import com.example.guoyurenli.mapper.UserMapper;
import com.example.guoyurenli.util.AESUtil;
import com.example.guoyurenli.util.QRcode;

import af.spring.AfRestError;

@Controller
public class ShareController
{
	@Autowired
	UserMapper usermapper;
	
	// 分享接口，生成信息分享码
	@GetMapping("staff/share")
	public void share(Model model
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception	// 相册ID
	{
		// 判断身份，如果是数据，则不能分享二维码
		Subject subject = SecurityUtils.getSubject();
		if(subject.hasRole("dataclerk"))
		{
			throw new Exception("数据点击了创建二维码，权限不足");
		}
		
		GyUsers user = (GyUsers)SecurityUtils.getSubject().getPrincipal();
		
		/* 创建分享码
		 * 分享码含4个字段：share:random:userId:expire , 分别是：校验字段：随机干扰：推荐人ID：过期时间
		 * 比如：share:763:1:20200713
		 * */
		// 有效日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();	// 默认当前时间
		cal.add(Calendar.DAY_OF_YEAR, 3); 		// 3天有效,表示往后推3天的时间
		String expire = sdf.format( cal.getTime());
		// 随机扰乱种子
		int rand = new Random().nextInt(10000);
		// 原文数据，格式share:random:abumId:expire
		String shareCode = "share:"
				+ rand + ":"
				+ user.user_id + ":"
				+ expire;
		
		// 加密
		String key = "guoyurenliyouxiangongshi";	// AES密钥长度应大于16位，推荐生成MD5值来做密钥
		shareCode = AESUtil.encrypt(shareCode, key);	// 传入，数据、密钥

		// 分享链接
		String shareUrl = "http://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath()
				+ "/join?code=" + shareCode;
		System.out.println(shareUrl);
		
		// 生成二维码并反给前端显示//////////////////////
		QRcode.gen(300, 300, shareUrl, "png", response.getOutputStream());
		
	}
	
	// 使用分享码，
	@GetMapping("/join")
	public Object join(Model model
			, HttpServletRequest request
			, String code) throws Exception
	{	
		String key = "guoyurenliyouxiangongshi";
		// 解密
		String[] sss = null;
		try
		{
			String shareCode = AESUtil.decrypt(code, key);
			sss = shareCode.split(":");
		} catch (Exception e)
		{
			return new AfRestError("邀请码不正确");
		}
		
		// 格式校验
		if(sss.length != 4 || ! sss[0].equals("share"))
			return new AfRestError("邀请码不正确");
		
		// 分享人校验
		int userId = Integer.valueOf(sss[2]);
		GyUsers user = usermapper.queryUserByUserid(userId);
		if(user == null){
			return new AfRestError("此推荐人不存在，或已经注销账号，userId=" + userId);
		}
		
		// 随机码校验
		int rand = Integer.valueOf(sss[1]);
		if(rand > 10000)
			return new AfRestError("邀请码不正确");
		
		// 判断分享用户的身份，一个用户可以有多个身份,如果是Boos的分享码就永久有效
		Set<String> roles = usermapper.queryRoleNamesByUsername(user.username);
		Iterator<String> it = roles.iterator();	// 迭代器
		while(it.hasNext()) {
			if(it.next().equals("admin")){
				model.addAttribute("user", user);
				return "staff/save";
			}
		}
		
		// 日期校验，这里如果分享码是Boos发出的，就算一个永久验证码。就不用验证了
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		String expire = sss[3];
		if(today.compareTo(expire) > 0)
			return new AfRestError("此邀请码已经过期，需要重新分享!");
		
		// 推荐人信息
		model.addAttribute("user", user);	// 信息
		return "staff/save";
	}
}
