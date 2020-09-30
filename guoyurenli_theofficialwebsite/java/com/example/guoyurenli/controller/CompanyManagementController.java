package com.example.guoyurenli.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.example.guoyurenli.mapper.FactoryMapper;
import com.example.guoyurenli.mapper.PostMapper;
import com.example.guoyurenli.util.AfRestDataa;

import af.spring.AfRestData;
import af.spring.AfRestError;

// 企业管理的类
@Controller
public class CompanyManagementController
{
	
	@Resource
	FactoryMapper factoryMapper;
	
	@Resource
	PostMapper postMapper;
	
	// 公司管理，后台管理页面(页面)
	@GetMapping("/factory/management")
	public String factorymanagement() throws Exception
	{
		return "enterprise/factoryManagement";
	}
	
	// 公司管理，后台管理页面（数据）
	@GetMapping("/factory/management.do")
	public Object factorymanagementdo(
			HttpServletResponse response
			, Integer page		// 第几页
			, Integer limit		// 每页显示条数
			, String name		// 搜索数据
			) throws Exception
	{
		// 当前人员信息
		Subject subject = SecurityUtils.getSubject();
		// 如果是员工，给他404
		if(subject.hasRole("employee")){
			response.sendError(404, "你没有权限哦");
		}
		
		// 模糊查询条件处理
		Map<String, Object> map = new HashMap<>();		// 总数查询
		String like = null;
		if(name != null && name.length() > 0){
			like = "%" + name + "%";
		}
		map.put("like", like);
		
		// count:符合条件的记录一共多少条
		int count = factoryMapper.getCount(map);
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
		List<Map<String, Object>> data = (List<Map<String, Object>>) factoryMapper.factoryList(map);
		
		// 对时间字段进行处理
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(data.size() != 0) {
			for(Map<String, Object> man : data){
				man.put("timeupdata", sdf.format(man.get("timeupdata")));
			}
		} 
		
		// 遍历查询企业下面有几个职位在招
		for(Map<String, Object> row : data)
		{
			int factoryId = (int) row.get("id");
			if(factoryMapper.getPostCount(factoryId) == 0){
				row.put("recruitment", false);
			}else
			{
				row.put("recruitment", true);
			}
			
		}
		
		// 数据返回
		return new AfRestDataa(data, count);
		
	}
	
	// 企业的置顶、价格、时间、备注的修改
	@PostMapping("/modify/company.do")
	public Object modifyCompanydo(@RequestBody JSONObject json) throws Exception
	{
		// 企业的在招停招切换
		if(json.getString("key").equals("recruitment"))
		{
			Map<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("timeupdate", new Date());
			mapa.put("company", json.getIntValue("id"));
			
			// 判断状态，true
			if(json.getBooleanValue("value"))
			{
				// 在招
				mapa.put("state", 0);
				factoryMapper.updatetime(mapa);		// 修改公司的更新时间
				postMapper.updateStates(mapa);		// 修改企业的岗位的状态
				return new AfRestData("修改成功");
			}
			else if(!json.getBooleanValue("value"))
			{
				// 停招
				mapa.put("state", 1);
				factoryMapper.updatetime(mapa);		// 修改公司的更新时间
				postMapper.updateStates(mapa);		// 修改企业的岗位的状态
				return new AfRestData("修改成功");
			}
			else
			{
				return new AfRestError("状态错误！");
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", json.getIntValue("id"));			// ID
		map.put("key", json.getString("key"));		// 字段
		map.put("value", json.get("value"));		// 值
		map.put("timeupdata", new Date());
		
		// 把企业设置成置顶/非置顶、设置金额、时间、备注
		try
		{
			factoryMapper.updateState(map);
		} catch (Exception e)
		{
			if(e.getCause() instanceof SQLException)
			{
				return new AfRestError("修改失败，请输入正确的数据类型");
			}
			else
			{
				throw e;
			}
		}
		return new AfRestData("修改成功");
	}
	
	@PostMapping("/factory/delect.do")
	public Object delectFactory(@RequestBody JSONObject json) throws Exception
	{
		int id = json.getIntValue("id");
		
		// 删除一个公司，把删除字段置为1
		factoryMapper.delectFactory(id);
		
		return new AfRestData("");
	}
	
	
}
