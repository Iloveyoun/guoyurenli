package com.example.guoyurenli.entity; 

/** 本类由 POJO生成器 自动生成于 2020-08-04 16:33:01
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `gy_label`
        (`id`, `title`) 
  VALUES(?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `gy_label`
        (`id`, `title`) 
  VALUES(#{id}, #{title}) 

  自增主键: id
*/ 

public class GyLabel 
{ 
 
	public Integer id ; 
	public String title ; 


	public void setId(Integer id)
	{
		this.id=id;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setTitle(String title)
	{
		this.title=title;
	}
	public String getTitle()
	{
		return this.title;
	}

} 
 