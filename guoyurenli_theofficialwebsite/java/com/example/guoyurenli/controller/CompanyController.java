package com.example.guoyurenli.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.guoyurenli.entity.GyFactory;
import com.example.guoyurenli.entity.GyUsers;
import com.example.guoyurenli.mapper.FactoryMapper;
import com.example.guoyurenli.util.MyUtil;
import com.example.guoyurenli.util.TmpFile;

import af.spring.AfRestData;
import af.spring.AfRestError;

// 管理公司的
@Controller
public class CompanyController
{
//	@Resource
	@Autowired
	FactoryMapper factoryMapper;
	
	// 添加招工企业MVC
	@GetMapping("addCompany")
	public String addCompany()
	{
		return "enterprise/factorysave";
	}
	
	// 添加招工企业REST
	@PostMapping("addCompany.do")
	public Object addCompanydo(@RequestBody GyFactory factory) throws Exception
	{
		// 当前登录用户
		GyUsers user = (GyUsers)SecurityUtils.getSubject().getPrincipal();
		
		// 字段处理
		factory.creator = user.user_id;		// 发布者
		factory.timecreate = new Date();	// 发布时间
		factory.suffix = MyUtil.getSuffix(factory.realName);			// 后缀名
		factory.storePath = TmpFile.getFactory().getAbsolutePath();		// 简章路径
		factory.timeupdata = new Date();	// 最近更新
		factory.topflag = false;	// 置顶
		factory.banflag = false;	// 加精
		factory.delflag = false;	// 删除
		factory.price = 0;
		factory.expiration = 0;
		
		if(factory.realName != null && factory.realName.length() != 0)
		{
			// 名字不为空在给起guid
			factory.guid = MyUtil.guid2();		// guid:给文件起的新名字，避免文件名重复
			
			// 临时文件存放位置
			File tmpFile = TmpFile.getFile(factory.realName);
			// 永久文件存放位置，guid + 后缀名
			File storeFile = TmpFile.getFactoryFile(factory.guid + factory.suffix);
			
			try
			{
				// 移动文件：临时文件夹到永久文件夹
				FileUtils.moveFile(tmpFile, storeFile);
			} catch (IOException e)
			{
				e.printStackTrace();
				return new AfRestError("文件移动失败！");
			}
		}
		
		// 插入数据库
		factoryMapper.addFactory(factory);
		return new AfRestData("添加成功！");
	}
	
	// 公司列表
	@GetMapping("/u/companyList/{attribute}")
	public String companyList(
			HttpServletRequest request
			, Model model
			, Integer pageNumber	// 第几页，搜索参数
			, String filter 
			, @PathVariable Integer attribute) throws Exception
	{
		// URL
		String url = request.getRequestURI();
		
		// 分页处理
		if(pageNumber == null)
			pageNumber = 1;
		
		// 模糊查询条件处理
		Map<String, Object> map = new HashMap<>();
		String like = null;
		if(!MyUtil.isEmpty(filter)){
			like = "%" + filter + "%";
		}
		map.put("like", like);
		
		if(attribute != null && attribute != 9 ){
			map.put("attribute", attribute);
		}
		
		// 一共多少条记录
		int count = factoryMapper.getCount(map);
		
		int pageSize = 10;		//每页显示多少条，自己设置
		int pageCount = count / pageSize;	//一共多少页
		if(count % pageSize != 0){ 	//如果不是刚好的倍数，就给页数+1
			pageCount += 1;
		}
		int startIndex = pageSize * (pageNumber - 1);	// 开始条数
		
		map.put("startIndex", startIndex);		// 开始条数
		map.put("pageSize", pageSize);			// 结束条数
		
		// 查询公司信息
		List<Map<String, Object>> factoryList = factoryMapper.factoryList(map);
		
		// 查询每个公司有几个岗位发布
		// 遍历每个Map,用map里面的id去岗位表里面查数量
		for(Map<String, Object> row : factoryList){
			int factoryId = (int) row.get("id");
			row.put("postcount", factoryMapper.getPostCount(factoryId));
		}
		
		model.addAttribute("factoryList", factoryList);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("url", url);
		
		return "enterprise/factorylist";
	}
	
	// 公司的详细信息
	@GetMapping("/u/factoryfin")
	public String postList(Model model, Integer enterpriseId) throws Exception
	{
		if(enterpriseId == null)
			throw new Exception("出现错误，企业不存在，ID=null");
		
		// 根据ID查询特定公司的信息
		GyFactory factory = factoryMapper.getFactory(enterpriseId);
		
		// 根据公司查询公司下所有的招聘信息
		List<Map<String, Object>> postlist = factoryMapper.getPostList(factory.id);
		postlist = PostController.changge(postlist);
		
		// 放入Model
		model.addAttribute("postsize", postlist.size());
		model.addAttribute("factory", factory);
		model.addAttribute("postlist", postlist);	// 岗位信息
		
		return "enterprise/factoryfin";
	}
	
	// 公司列表展示的顶部导航栏
	@GetMapping("/u/factoryTop")
	public String factoryTop() throws Exception
	{
		return "enterprise/factoryTop";
	}

	

}
