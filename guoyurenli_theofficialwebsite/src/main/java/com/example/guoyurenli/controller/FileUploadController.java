package com.example.guoyurenli.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.guoyurenli.util.MyUtil;
import com.example.guoyurenli.util.TmpFile;

import af.spring.AfRestData;


// 接收文件上传的Controller
@Controller
public class FileUploadController
{
	// 企业的招商简介文件上传
	@PostMapping("fileUpload")
	public Object upload(HttpServletRequest request) throws Exception
	{
		MultipartHttpServletRequest mhr = (MultipartHttpServletRequest) request;
		
		// 附带的字段
//		String tag = mhr.getParameter("tag");
		
		MultipartFile mf = mhr.getFile("file");		// 表单的name='file'
		Map<String, Object> result = new HashMap<>();
		if(mf != null && !mf.isEmpty())
		{
			// 文件临时存放位置:c:\guoyufile\tmp
//			File dir = TmpFile.getDir();
			
			// 取得文件的名称并保存
			String realName = mf.getOriginalFilename();		// 取得文件的名字，realName:数据库.txt
			File tmpFile = TmpFile.getFile(realName);		// file:c:/guoyufile/tmp/数据库.txt
			
			// 接收上传并保存到临时文件
			mf.transferTo(tmpFile);
			
			// 回应给客户端的消息
			result.put("realNmae", realName);	// 原名称
		}
		
		return new AfRestData(result);
	}
	
	// 下载招聘简章
	@GetMapping("/u/file")
	public void getCompanyFile(HttpServletRequest request
			, HttpServletResponse response
			, String realName
			, String guid) throws Exception
	{	 
		// 判断文件是否存在
		if(realName == null || realName.length() == 0)
		{
			response.sendError(404);
			return;
		}
		
		// 总存储文件夹
		File dataDir = TmpFile.getFactory();
		String suffix = MyUtil.getSuffix(realName);	// 后缀
		
		// 文件名
		String fileName = guid + suffix;
		
		// 需要的总文件路径
		File targetFile = new File(dataDir, fileName);
		
		// 检查目标文件是否存在
		if (!targetFile.exists() || !targetFile.isFile())
		{
			System.out.println("目标文件不存在：" + targetFile);
			response.sendError(404);
			return;
		}
		
		//设置应答：Content-Type(文件类型) / Content-Length(文件大小) ，读取目标文件，发送到客户端
		String contentType = MyUtil.getContentType(suffix);	// .ppt (文件后缀名)
		long contentLength = targetFile.length();
		response.setContentType(contentType);  			// 告诉浏览器文件是什么类型
		response.setHeader("Content-Length", String.valueOf(contentLength)); 	// 告诉浏览器文件的长度
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(realName, "UTF-8"));	// 告诉浏览器下载的文件名,再提示一个下载框：13.png
																			// 后面传入文件名（提示前端的文件名，可以自己定义）
		
		// 应答：读取目标文件数据，发送给客户端
		InputStream inputStream = new FileInputStream(targetFile);	// 读取文件
		OutputStream outputStream = response.getOutputStream();		// 发送给浏览器
		
		try
		{
			MyUtil.copy(inputStream, outputStream);
		} catch (Exception e)
		{
			try{ inputStream.close(); } catch (Exception e2){}
		}
		
		outputStream.close();
		
	}
	
}
