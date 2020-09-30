package com.example.guoyurenli.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.example.guoyurenli.entity.Manpower;

@Mapper
public interface ManpowerMapper
{
	// 插入一个人员信息
	public void addManpower(Manpower manpower);
	
	// 查询人员的个数
	public int getManpower(Map<String, Object> map);
	
	// 查询所有人员信息
	public List<Map<String, Object>> getManpowerList(Map<String, Object> map);
	
	// 删除一个人
	public void deleteManpower(Integer id);
	
	// 雪藏一个人
	public void concealManpower(Integer id);
	
	// 修改一个人的状态
	public void conditionManpower(Map<String, Object> map);

	// 修改人员信息
	public void updateManpower(Map<String, Object> map);

	
	
}
