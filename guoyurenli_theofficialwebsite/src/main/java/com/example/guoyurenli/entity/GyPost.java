package com.example.guoyurenli.entity; 

import java.util.Date; 

/** 本类由 POJO生成器 自动生成于 2020-08-06 14:13:59
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `gy_post`
        (`id`, `company`, `creator`, `title`, `content`, `workaddress`, `experience`, `academicDegree`, `wagesmin`, `wagesmax`, `timerelease`, `timeupdate`, `hiring`, `state`, `topflag`, `banflag`, `delflag`) 
  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `gy_post`
        (`id`, `company`, `creator`, `title`, `content`, `workaddress`, `experience`, `academicDegree`, `wagesmin`, `wagesmax`, `timerelease`, `timeupdate`, `hiring`, `state`, `topflag`, `banflag`, `delflag`) 
  VALUES(#{id}, #{company}, #{creator}, #{title}, #{content}, #{workaddress}, #{experience}, #{academicDegree}, #{wagesmin}, #{wagesmax}, #{timerelease}, #{timeupdate}, #{hiring}, #{state}, #{topflag}, #{banflag}, #{delflag}) 

  自增主键: id
*/ 

public class GyPost 
{ 
 
	public Integer id ; 
	public Integer company ; 
	public Integer creator ; 
	public String title ; 
	public String content ; 
	public String workaddress ; 
	public Byte experience ; 
	public Byte academicDegree ; 
	public Integer wagesmin ; 
	public Integer wagesmax ; 
	public Date timerelease ; 
	public Date timeupdate ; 
	public Integer hiring ; 
	public Byte state ; 
	public Byte topflag ; 
	public Byte banflag ; 
	public Byte delflag ; 


	public void setId(Integer id)
	{
		this.id=id;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setCompany(Integer company)
	{
		this.company=company;
	}
	public Integer getCompany()
	{
		return this.company;
	}
	public void setCreator(Integer creator)
	{
		this.creator=creator;
	}
	public Integer getCreator()
	{
		return this.creator;
	}
	public void setTitle(String title)
	{
		this.title=title;
	}
	public String getTitle()
	{
		return this.title;
	}
	public void setContent(String content)
	{
		this.content=content;
	}
	public String getContent()
	{
		return this.content;
	}
	public void setWorkaddress(String workaddress)
	{
		this.workaddress=workaddress;
	}
	public String getWorkaddress()
	{
		return this.workaddress;
	}
	public void setExperience(Byte experience)
	{
		this.experience=experience;
	}
	public Byte getExperience()
	{
		return this.experience;
	}
	public void setAcademicDegree(Byte academicDegree)
	{
		this.academicDegree=academicDegree;
	}
	public Byte getAcademicDegree()
	{
		return this.academicDegree;
	}
	public void setWagesmin(Integer wagesmin)
	{
		this.wagesmin=wagesmin;
	}
	public Integer getWagesmin()
	{
		return this.wagesmin;
	}
	public void setWagesmax(Integer wagesmax)
	{
		this.wagesmax=wagesmax;
	}
	public Integer getWagesmax()
	{
		return this.wagesmax;
	}
	public void setTimerelease(Date timerelease)
	{
		this.timerelease=timerelease;
	}
	public Date getTimerelease()
	{
		return this.timerelease;
	}
	public void setTimeupdate(Date timeupdate)
	{
		this.timeupdate=timeupdate;
	}
	public Date getTimeupdate()
	{
		return this.timeupdate;
	}
	public void setHiring(Integer hiring)
	{
		this.hiring=hiring;
	}
	public Integer getHiring()
	{
		return this.hiring;
	}
	public void setState(Byte state)
	{
		this.state=state;
	}
	public Byte getState()
	{
		return this.state;
	}
	public void setTopflag(Byte topflag)
	{
		this.topflag=topflag;
	}
	public Byte getTopflag()
	{
		return this.topflag;
	}
	public void setBanflag(Byte banflag)
	{
		this.banflag=banflag;
	}
	public Byte getBanflag()
	{
		return this.banflag;
	}
	public void setDelflag(Byte delflag)
	{
		this.delflag=delflag;
	}
	public Byte getDelflag()
	{
		return this.delflag;
	}

} 
 