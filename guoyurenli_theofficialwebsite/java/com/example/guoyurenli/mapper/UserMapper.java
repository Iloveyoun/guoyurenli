package com.example.guoyurenli.mapper;

import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.example.guoyurenli.entity.GyUsers;

@Mapper
public interface UserMapper
{
	// 根据用户名查询用户信息
	public GyUsers queryUserByUsername(String username);

	// 根据用户名查询当前用户的角色列表---3张表连接查询,一个用户可以对应多个角色，所以用Set去重
	public Set<String> queryRoleNamesByUsername(String username);

	// 根据用户名查询当前用户的权限---5张表连接查询
	public Set<String> queryPermissionsByUsername(String username);
	
	
	
	// 使用分享页面，根据id查询推荐人的信息
	public GyUsers queryUserByUserid(int id);
	
	// 注册用户信息
	public void registGyUsers(GyUsers user);
	
	// 为注册的用户分配角色
	public void registGyRoles(Map<String, Object> map);

	// 修改密码
	public void alterAsk(Map<String, Object> map);
}
