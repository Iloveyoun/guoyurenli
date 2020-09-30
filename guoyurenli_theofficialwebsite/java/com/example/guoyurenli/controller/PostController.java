package com.example.guoyurenli.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.example.guoyurenli.entity.GyFactory;
import com.example.guoyurenli.entity.GyLabel;
import com.example.guoyurenli.entity.GyPls;
import com.example.guoyurenli.entity.GyPost;
import com.example.guoyurenli.entity.GyUsers;
import com.example.guoyurenli.mapper.FactoryMapper;
import com.example.guoyurenli.mapper.PostMapper;
import com.example.guoyurenli.util.MyUtil;

import af.spring.AfRestData;
import af.spring.AfRestError;

// 管理岗位的
@Controller
public class PostController
{
	@Autowired
	PostMapper postMapper;
	
	@Autowired
	FactoryMapper factoryMapper;
	
	// 添加岗位MVC
	@GetMapping("postsave") 
	public String postSave(Model model, Integer company, String title, String address)
	{
		// 查询所有标签（福利）
		List<GyLabel> labelList = postMapper.getLabel();
		
		// 当前企业信息
		model.addAttribute("address", address);	// 地址
		model.addAttribute("company", company);	// ID
		model.addAttribute("title", title);		// 公司名字
		
		// 所有标签信息
		model.addAttribute("labelList", labelList);
		
		return "enterprise/postsave";
	}
	
	// 添加岗位REST
	@PostMapping("postsave.do")
	public Object postsavedo(@RequestBody JSONObject json)
	{
		// 当前用户
		GyUsers user = (GyUsers)SecurityUtils.getSubject().getPrincipal();
		
		// 提取出字段并储存
		GyPost post = new GyPost();
		post.title = json.getString("title");
		post.workaddress = json.getString("workaddress");
		post.experience = json.getByteValue("experience");
		post.academicDegree = json.getByteValue("academicDegree");
		post.wagesmin = json.getIntValue("wagesmin");
		post.wagesmax = json.getIntValue("wagesmax");
		post.content = json.getString("content");
		post.company = json.getIntValue("company");
		post.creator = user.user_id;
		post.hiring = json.getIntValue("hiring");
		post.state = 0;
		post.timerelease = new Date();
		post.timeupdate = new Date();
		
		post.banflag = 0;
		post.topflag = 0;
		post.delflag = 0;
		
		// 插入一条岗位
		postMapper.addPost(post);
		
		// 插入岗位的标签
		GyPls pls = new GyPls();
		pls.pid = post.id;
		for(int i=1; i<12; i++)
		{
			String str = json.getString(i+"");
			if(str != null)
			{
				pls.lid = i;
				postMapper.addLabel(pls);
			}
		}
		
		return new AfRestData("添加成功");
	}
	
	// 岗位详细信息
	@GetMapping("/u/postfin")
	public String postfin(Model model, Integer postId)
	{
		// 通过ID查询岗位信息
		List<Map<String, Object>> post = postMapper.getPost(postId);
		post = PostController.changge(post);
		
		// 通过ID查询岗位对应的公司信息
		GyFactory factory = factoryMapper.getFactory((int)post.get(0).get("company"));
		
		// 通过岗位ID查询岗位对应的标签
		Set<String> label = postMapper.getPostLabel((int)post.get(0).get("id"));
		
		// 添加到模板
		model.addAttribute("post", post.get(0));			// 岗位的信息
		model.addAttribute("factory", factory);		// 公司的信息
		model.addAttribute("label", label);			// 岗位的标签信息
		
		return "enterprise/postfin";
	}
	
	// 岗位列表
	@GetMapping("/u/post/list")
	public String postList(Model model
			, HttpServletRequest request
			, Integer pageNumber
			, String filter)
	{
		// URL
		String url = request.getRequestURI();
		
		if(pageNumber == null)
			pageNumber = 1;
		
		// 模糊查询条件处理
		Map<String, Object> map = new HashMap<>();
		String like = null;
		if(!MyUtil.isEmpty(filter))
		{
			like = "%" + filter + "%";
		}
		map.put("like", like);
		
		// 一共多少条岗位记录
		int count = postMapper.getPostConut(map);
		
		int pageSize = 10;		//每页显示多少条，自己设置
		int pageCount = count / pageSize;	//一共多少页
		if(count % pageSize != 0){ 	//如果不是刚好的倍数，就给页数+1
			pageCount += 1;
		}
		int startIndex = pageSize * (pageNumber - 1);	// 开始条数
		
		map.put("startIndex", startIndex);		// 开始条数
		map.put("pageSize", pageSize);			// 结束条数
		
		// 查询公司信息
		List<Map<String, Object>> postList = postMapper.getPostList(map);
		postList = PostController.changge(postList);
		
		
		model.addAttribute("postList", postList);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("url", url);
		
		return "enterprise/postlist";
	}
	
	// 更改岗位的“在招”“停招”状态
	@PostMapping("/enterprise/updateState.do")
	public Object updateStatedo(@RequestBody JSONObject jreq) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		int postId = jreq.getIntValue("postId");
		String cmd = jreq.getString("cmd");
		map.put("id", postId);
		map.put("timeupdate", new Date());	// 更新时间
		
		// 改为在招
		if (cmd.equals("updateY"))
		{
			map.put("state", 0);
		}
		// 改为停招
		else if(cmd.equals("updateN"))
		{
			map.put("state", 1);
		}
		else
		{
			return new AfRestError("无效的操作:" + cmd );
		}
		
		// 执行修改
		postMapper.updateState(map);
		
		return new AfRestData("");
	}
	
	
	// 处理岗位字段的特殊类型
	public static List<Map<String, Object>> changge(List<Map<String, Object>> postlist)
	{
		// 处理岗位的条件字段
		for(Map<String, Object> row : postlist)
		{
			// 工作经验字段
			int experience = (int)row.get("experience");
			switch (experience)
			{
				case 0:
					row.put("experience", "经验不限");
					break;
				case 1:
					row.put("experience", "1年以下");
					break;
				case 2:
					row.put("experience", "1-3年");
					break;
				case 3:
					row.put("experience", "3-5年");
					break;
				case 4:
					row.put("experience", "5-10年");
					break;
				case 5:
					row.put("experience", "10年以上");
					break;
				default:
					break;
			}
			
			// 学历要求字段
			int academicDegree = (int)row.get("academicDegree");
			switch (academicDegree)
			{
				case 0:
					row.put("academicDegree", "学历不限");
					break;
				case 1:
					row.put("academicDegree", "高中");
					break;
				case 2:
					row.put("academicDegree", "技校");
					break;
				case 3:
					row.put("academicDegree", "中专");
					break;
				case 4:
					row.put("academicDegree", "大专");
					break;
				case 5:
					row.put("academicDegree", "本科");
					break;
				case 6:
					row.put("academicDegree", "硕士");
					break;
				case 7:
					row.put("academicDegree", "博士");
					break;
				default:
					break;
			}
			
//			// 状态字段
//			int state = (int)row.get("state");
//			switch (state)
//			{
//				case 0:
//					row.put("state", "在招");
//					break;
//				case 1:
//					row.put("state", "停招");
//					break;
//				default:
//					break;
//			}
		}
		
		return postlist;
	}

	
}
