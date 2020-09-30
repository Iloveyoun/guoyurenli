package com.example.guoyurenli.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.example.guoyurenli.entity.GyUms;
import com.example.guoyurenli.entity.GyUsers;
import com.example.guoyurenli.entity.Manpower;
import com.example.guoyurenli.mapper.ManpowerMapper;
import com.example.guoyurenli.mapper.UmsMapper;
import com.example.guoyurenli.mapper.UserMapper;
import com.example.guoyurenli.util.AfRestDataa;

import af.spring.AfRestData;
import af.spring.AfRestError;

@Controller
public class StaffController
{
	@Autowired
	ManpowerMapper manpowerMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	UmsMapper umsMapper;
	
	// 增加人员页面
	@GetMapping("staffSave")
	public String staffSave(Model model)
	{
		// 取得当前用户的信息放入会话
		GyUsers user = (GyUsers)SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		
		return "staff/save";
	}
	
	// 增加人员Restful
	@PostMapping("staffSave.do")
	public String staffSavedo(Model model, Manpower manpower)
	{
		// 填表日期
		manpower.setWriteTime(new Date());
		
		// 录入用户基本信息
		manpowerMapper.addManpower(manpower);
		
		// 录入用户状态信息
		GyUms gyUms = new GyUms();
		gyUms.setManpowerid(manpower.getId());
		gyUms.state = 0;		// 员工状态	，员工状态变成其他的时候才有后面的那些
//		gyUms.entryunit = manpower.company;	// 入职单位
//		gyUms.dateofentry = new Date();		// 入职日期
//		gyUms.rebates = 200;		// 返费金额
//		gyUms.rebatestime = xxx;	// 返回时间，可以是天数，也可以是时间，目前数据库是时间
//		gyUms.reasonforfailure = xxx;	// 面试失败原因
		umsMapper.addUms(gyUms);
		
		
		// 取得当前用户的信息放入模板
		GyUsers user = (GyUsers)SecurityUtils.getSubject().getPrincipal();
		// 如果未登录，就说明是扫描二维码来添加的人，就把推荐人写到模板
		if(user == null)
		{
			user = userMapper.queryUserByUserid(manpower.creator);
		}
		
		model.addAttribute("user", user);
		
		return "staff/save";
	}
	
	// 删除一个人员的信息
	@PostMapping("staffDelece.do")
	public Object staffDelece(Integer id, Integer operation)
	{
		// 执行删除等操作
		if(operation == 0)
		{
			// 删除
			manpowerMapper.deleteManpower(id);
			return new AfRestData("删除成功");
		}
		else if(operation == 1)
		{
			// 雪藏
			manpowerMapper.concealManpower(id);
			return new AfRestData("雪藏成功");
		}
		else
		{
			return new AfRestError("错误操作");
		}
		
	}
	
	// 人员列表页面
	@GetMapping("stafflist")
	public String stafflist()
	{
		return "staff/list";
	}
	
	// 全部人员列表页面
	@GetMapping("staffAlllist")
	public String staffAlllist()
	{
		return "staff/listFB";
	}
	
	// 人员列表数据
	@GetMapping("stafflist.do")
	public Object stafflistdo(Integer page, Integer limit, String name, Integer operation)
	{
		// 当前用户
		GyUsers user = (GyUsers)SecurityUtils.getSubject().getPrincipal();
		
		Subject subject = SecurityUtils.getSubject();
		
		// 模糊查询条件处理
		Map<String, Object> map = new HashMap<>();			// 总数查询
		String like = null;
		if(name != null && name.length() > 0){
			like = "%" + name + "%";
		}
		map.put("like", like);
		
		// 如果是员工，只能查看本人的数据
		if(subject.hasRole("employee"))	
		{
			map.put("creator", user.user_id);
		}
		
		if(operation != 0)
		{
			if(subject.hasRole("admin"))
			{
				map.put("creator", user.user_id);
			}
		}
		
		// 如果是数据，只能查看以提交的数据
		if(subject.hasRole("dataclerk"))	
		{
			map.put("state", 0);
		}
		
		// count:符合条件的记录一共多少条
		int count = manpowerMapper.getManpower(map);		//取得一共多少条记录
		if(count == 0) {
			return new AfRestDataa(null, 0);
		}
		//每页显示条数
		int pageSize = limit;		
		// 开始条数
		int startIndex = pageSize * (page - 1);
		
		// 查询所有符合条件的数据
		map.put("startIndex", startIndex);
		map.put("pageSize", pageSize);
		List<Map<String, Object>> data = (List<Map<String, Object>>) manpowerMapper.getManpowerList(map);
		
		// 对时间字段进行处理
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(data.size() != 0) {
			for(Map<String, Object> man : data){
				man.put("entry", sdf.format(man.get("entry")));
				man.put("writeTime", sdf.format(man.get("writeTime")));
			}
		} 
		
		return new AfRestDataa(data, count);
		
	}
	
	// 修改求职人员的面试企业及时间等
	@PostMapping("updateManpower.do")
	public Object updatePeople(@RequestBody JSONObject json)
	{
		// 把值提取出来
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", json.getIntValue("id"));
		map.put("key", json.getString("key") + "=" + "'" + json.getString("value") + "'");
//		map.put("value", json.getString("value"));  
		
		// 修改
		manpowerMapper.updateManpower(map);
		return new AfRestData("修改成功");
	}
	
	// 状态改变
	@PostMapping("stateChange.do")
	public Object updateChange(@RequestBody JSONObject json)
	{
		/**
		 * 1、提交确认
		 * 2、报名成功
		 * 3、报名失败（失败原因）
		 * 4、面试成功
		 * 5、面试失败（失败原因）
		 * 6、入职成功
		 * 
		 * */
		// 改变成的状态
		int state = json.getIntValue("state");
		// 当前状态
		int currentState = json.getIntValue("currentState");
		
		// 条件指定
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("manpowerid", json.getIntValue("manpowerid"));	// ID
		map.put("state", state);	// 改变成的状态
		
		switch (state)
		{
			// 确认提交（已确认，待报名状态）
			case 1:
				// 当求职者处以【刚填写资料】、【报名失败】、【面试失败】、【已离职】等状态时才可以提交报名
				if(currentState == 0 || currentState == 3 || currentState == 5 || currentState == 8)
				{
					// 修改状态
					manpowerMapper.conditionManpower(map);
					return new AfRestData("提交成功");
				}else
				{
					return new AfRestError("人员状态错误，请不要重复提交报名信息哟。");
				}
					
			// 报名成功
			case 2:
				// 只有当求职者处以【以确认】、【报名失败】等状态时才能修改报名信息
				if(currentState == 1 || currentState == 3)
				{
					// 这里要判断用户状态是否支持报名
					manpowerMapper.conditionManpower(map);
					return new AfRestData("操作成功：【报名成功】");
				}else
				{
					return new AfRestError("人员状态错误，当前状态不支持此操作");
				}
				
				
			// 报名失败
			case 3:
				// 只有当求职者处以【以确认】、【报名成功】状态时才能修改报名信息
				if(currentState == 1 || currentState == 2)
				{
					map.put("reasonforfailure", json.getString("reasonforfailure"));	// 失败原因
					manpowerMapper.conditionManpower(map);
					return new AfRestData("操作成功：【报名失败】");
				}else
				{
					return new AfRestError("人员状态错误，当前状态不支持此操作");
				}
			
			// 面试成功
			case 4:
				// 只有当求职者处以【报名成功】、【面试失败】状态时才能修改报名信息
				if(currentState == 2 || currentState == 5)
				{
					manpowerMapper.conditionManpower(map);
					return new AfRestData("操作成功：【面试成功】");
				}else
				{
					return new AfRestError("人员状态错误，当前状态不支持此操作");
				}
				
			
			// 面试失败
			case 5:
				// 只有当求职者处以【报名成功】、【面试成功】状态时才能修改报名信息
				if(currentState == 2 || currentState == 4)
				{
					map.put("reasonforfailure", json.getString("reasonforfailure"));	// 失败原因
					manpowerMapper.conditionManpower(map);
					return new AfRestData("操作成功：【面试失败】");
				}else
				{
					return new AfRestError("人员状态错误，当前状态不支持此操作");
				}
			
			// 入职成功
			case 6:
				// 只有当求职者处以【面试成功】状态时才能修改报名信息
				if(currentState == 4)
				{
					// 要加入职单位，入职时间等等
					map.put("entryunit", json.getString("entryunit"));	// 入职单位
					map.put("dateofentry", new Date());					// 入职日期
					map.put("rebates", json.getIntValue("rebates"));	// 金额
					
					// 时间
					int timeDay = json.getIntValue("rebatestime");
					Calendar cal = Calendar.getInstance();		// 当前时间
					cal.add(Calendar.DAY_OF_MONTH, timeDay);	// 向后推
					Date date = cal.getTime();
					map.put("rebatestime", date);		// 发放时间
					manpowerMapper.conditionManpower(map);
					return new AfRestData("操作成功：【入职成功】");
				}else
				{
					return new AfRestError("人员状态错误，当前状态不支持此操作");
				}
				
			// 离职
			case 8:
				// 只有当求职者处以【入职成功】状态时才能修改报名信息
				if(currentState == 6)
				{
					map.put("reasonforfailure", json.getString("reasonforfailure"));	// 失败原因
					manpowerMapper.conditionManpower(map);
					return new AfRestData("操作成功：【离职】");
				}else
				{
					return new AfRestError("人员状态错误，当前状态不支持此操作");
				}
				
			default:
				return new AfRestError("操作有误!state=" + state);
		}
		
	}
	
	
	
}
