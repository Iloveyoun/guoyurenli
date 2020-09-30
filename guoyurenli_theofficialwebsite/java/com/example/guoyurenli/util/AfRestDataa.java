package com.example.guoyurenli.util;
/*执行正确的返回View
 * 
 * 构造一个JSON返回去
 * 
 * */

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import af.spring.AfRestView;

public class AfRestDataa implements AfRestView
{	
	Object data;
	int count;
	
	public AfRestDataa(Object data, int count)
	{
		this.data = data;
		this.count = count;
	}
		
	@Override
	public void render(Map<String, ?> model
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception
	{
		JSONObject json = new JSONObject(true);
		json.put("code", 0);
		json.put("msg", "OK");
		json.put("count", count);
		if(data != null)
		{
			if(data instanceof JSON) // 本身就是 JSONObject 或 JSONArray
				json.put("data", data);
			else
				json.put("data", JSON.toJSON(data));
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		response.getWriter().print( JSON.toJSONString(json,SerializerFeature.PrettyFormat) );
	}

}
