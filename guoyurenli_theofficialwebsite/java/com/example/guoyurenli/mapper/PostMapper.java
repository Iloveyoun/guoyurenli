package com.example.guoyurenli.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.example.guoyurenli.entity.GyLabel;
import com.example.guoyurenli.entity.GyPls;
import com.example.guoyurenli.entity.GyPost;

@Mapper
public interface PostMapper
{
	// 查询所有的标签（福利）
	public List<GyLabel> getLabel();

	// 添加一个岗位
	public void addPost(GyPost post);

	// 添加一个岗位的优势（标签）
	public void addLabel(GyPls pls);

	// 根据ID查询岗位的信息
	public List<Map<String, Object>> getPost(Integer postId);

	// 通过岗位ID查询岗位对应的标签
	public Set<String> getPostLabel(Integer pid);
	
	// 查询岗位信息的数量
	public Integer getPostConut(Map<String, Object> map);

	// 查询所有岗位信息
	public List<Map<String, Object>> getPostList(Map<String, Object> map);

	// 修改一个岗位的“在招”“停招”状态
	public void updateState(Map<String, Object> map);

	// 修改一个公司下所有岗位的的“在招”“停招”状态
	public void updateStates(Map<String, Object> mapa);
	
	
}
