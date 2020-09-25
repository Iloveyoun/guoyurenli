package com.example.guoyurenli.config;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.guoyurenli.entity.GyUsers;
import com.example.guoyurenli.mapper.UserMapper;

public class MyRealm extends AuthorizingRealm
{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public String getName()
	{
		return "myRealm";
	}

	// 授权流程
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		// 获取当前用户的用户名
		GyUsers user = (GyUsers)principals.iterator().next();
		
		// 根据用户名查询当前用户的角色列表
		Set<String> roleNames = userMapper.queryRoleNamesByUsername(user.username);
		// 根据用户名查询当前用户的权限列表
		Set<String> ps = userMapper.queryPermissionsByUsername(user.username);
		
		// 把用户的角色信息跟权限信息全部交给AuthorizationInfo
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roleNames);		// 角色列表
		info.setStringPermissions(ps);	// 权限列表
		
		return info;
	}

	// 认证流程
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException
	{
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// 从token中获取用户名
		String username = token.getUsername();
		// 根据用户名从数据库查询安全数据
		GyUsers user = userMapper.queryUserByUsername(username);
		if(user == null) {	
			return null;
		}
		// 如果用户存在，那我需要把查询到的安全信息转换一下格式交给Shiro,把数据给Shiro如下,要传入三个参数
		AuthenticationInfo info = new SimpleAuthenticationInfo(
				user,			// 用户名/这里把整个用户都放进去
				user.getPassword(),	// 密码
				this.getName()		// 自定义Realm的名字
				);
		
		return info;
	}

}
