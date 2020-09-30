package com.example.guoyurenli.entity; 


/** 本类由 POJO生成器 自动生成于 2020-08-05 11:20:58
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `gy_pls`
        (`pid`, `lid`) 
  VALUES(?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `gy_pls`
        (`pid`, `lid`) 
  VALUES(#{pid}, #{lid}) 

  自增主键: 无
*/ 

public class GyPls 
{ 
 
	public Integer pid ; 
	public Integer lid ; 


	public void setPid(Integer pid)
	{
		this.pid=pid;
	}
	public Integer getPid()
	{
		return this.pid;
	}
	public void setLid(Integer lid)
	{
		this.lid=lid;
	}
	public Integer getLid()
	{
		return this.lid;
	}

} 
 