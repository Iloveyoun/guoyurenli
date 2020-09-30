package com.example.guoyurenli.config;

import java.util.LinkedHashMap;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig
{
	
	// thymeleaf对shiro标签支持=======================================================================
	@Bean
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
	
	// Shiro的加密解释器，会把得到的密码加密之后再跟数据库查询到的密码匹配=======================================================================
	@Bean
	public HashedCredentialsMatcher getHashedCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		// 第一种解密：指定加密规则,md5加密
		matcher.setHashAlgorithmName("md5");
		// 第三种解密：hash次数，就是加密几次,这里是1次，
		matcher.setHashIterations(1);
		
		return matcher;
	}
	
	// 自定义Realm=======================================================================
	@Bean
	public MyRealm getMyRealm(HashedCredentialsMatcher matcher) {	// 然后把加密规则给Realm
		MyRealm myRealm = new MyRealm();
		// 把加密解释器传给他
		myRealm.setCredentialsMatcher(matcher);
		return myRealm;
	}
	
	// 设置缓存管理器，Shiro少查数据库就要用到缓存，再把缓存管理器交给安全管理器=======================================================================
	@Bean
	public EhCacheManager getEhCacheManager() {
		EhCacheManager ehCacheManager = new EhCacheManager();
		// 设置缓存参数，从resources.ehcache.xml文件中读取
		ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return ehCacheManager;
	}

	// 第二步，由于下面需要DefaultWebSecurityManager(安全管理器)，那我们就创建一个给他
	@Bean
	public DefaultWebSecurityManager getDefaultWebSecurityManager(MyRealm myRealm)
	{
		// 创建一个安全管理器
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// securityManager要完成校验，需要realm(数据源),但是这儿也没有
		securityManager.setRealm(myRealm);
		// 配置缓存处理
		securityManager.setCacheManager(getEhCacheManager());
		
		// 返回去
		return securityManager;
	}
	
	// 第一步，配置Shrio所需要的过滤器,创建一个Shiro过滤器工程，交由Spring管理
	@Bean
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager)// SecurityManager（传入安全管理器），但是容器里面是没有的,由上面传入
	{
		// 一个过滤器，拦截器
		ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
		// 过滤器就是Shiro进行权限校验的核心，进行认证和授权是需要SecurityManager（安全管理器）的,所以我们得给
		filter.setSecurityManager(securityManager);	// 要从Spring容器里面传入
		
		// 设置Shiro拦截规则
		// anon : 匿名用户可访问
		// authc : 认证用户可访问
		// user : 使用RemeberMe的用户可访问（伪装的身份用户可访问）
		// perms :对应权限可访问
		// role : 对应的角色可访问
		LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
		filterMap.put("/login", "anon");			// 表示登录页面不拦截
		filterMap.put("/login.do", "anon");			// 表示登录请求不拦截
		filterMap.put("/js/**", "anon");			// 两个静态文件不拦着
		filterMap.put("/layui/**", "anon");
		filterMap.put("/img/**", "anon");			// 静态图片
		filterMap.put("/join", "anon");				// 分享码也不拦
		filterMap.put("/staffSave.do", "anon");		// 提交求职者信息
		
		filterMap.put("/u/**", "anon");		// 表示游客页面不拦截（招聘及岗位信息等）
		
		///////////////////
//		filterMap.put("/su/*", "anon");		// 这里是所有人都可以访问的页面，主要公司展示、岗位展示等等。后期全部改掉
		///////////////////
		
		filterMap.put("/exit", "logout");			// 退出
		
		filterMap.put("/**", "authc");				// 其他的全部拦截，都需要登录才能访问

		// 把规则给权限管理器
		filter.setFilterChainDefinitionMap(filterMap);

		// 设置默认的登录页面
		filter.setLoginUrl("/login");
		// 设置未授权访问的页面路径，就是你访问一个要登录了才能访问的页面，但是你没有登录，就把你跳转到一个页面里面去
		filter.setUnauthorizedUrl("/login");
		
		return filter;
	}
}
