package com.example.guoyurenli.entity; 

import java.util.Date; 

/** 本类由 POJO生成器 自动生成于 2020-08-14 15:43:48
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `gy_factory`
        (`id`, `creator`, `title`, `nature`, `address`, `content`, `attribute`, `timecreate`, `storePath`, `realName`, `guid`, `suffix`, `topflag`, `banflag`, `delflag`, `price`, `expiration`, `remarks`, `timeupdata`) 
  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `gy_factory`
        (`id`, `creator`, `title`, `nature`, `address`, `content`, `attribute`, `timecreate`, `storePath`, `realName`, `guid`, `suffix`, `topflag`, `banflag`, `delflag`, `price`, `expiration`, `remarks`, `timeupdata`) 
  VALUES(#{id}, #{creator}, #{title}, #{nature}, #{address}, #{content}, #{attribute}, #{timecreate}, #{storePath}, #{realName}, #{guid}, #{suffix}, #{topflag}, #{banflag}, #{delflag}, #{price}, #{expiration}, #{remarks}, #{timeupdata}) 

  自增主键: id
*/ 

public class GyFactory 
{ 
 
	public Integer id ; 
	public Integer creator ; 
	public String title ; 
	public Integer nature ; 
	public String address ; 
	public String content ; 
	public Byte attribute ; 
	public Date timecreate ; 
	public String storePath ; 
	public String realName ; 
	public String guid ; 
	public String suffix ; 
	public Boolean topflag ; 
	public Boolean banflag ; 
	public Boolean delflag ; 
	public Integer price ; 
	public Integer expiration ; 
	public String remarks ; 
	public Date timeupdata ; 


	public void setId(Integer id)
	{
		this.id=id;
	}
	public Integer getId()
	{
		return this.id;
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
	public void setNature(Integer nature)
	{
		this.nature=nature;
	}
	public Integer getNature()
	{
		return this.nature;
	}
	public void setAddress(String address)
	{
		this.address=address;
	}
	public String getAddress()
	{
		return this.address;
	}
	public void setContent(String content)
	{
		this.content=content;
	}
	public String getContent()
	{
		return this.content;
	}
	public void setAttribute(Byte attribute)
	{
		this.attribute=attribute;
	}
	public Byte getAttribute()
	{
		return this.attribute;
	}
	public void setTimecreate(Date timecreate)
	{
		this.timecreate=timecreate;
	}
	public Date getTimecreate()
	{
		return this.timecreate;
	}
	public void setStorePath(String storePath)
	{
		this.storePath=storePath;
	}
	public String getStorePath()
	{
		return this.storePath;
	}
	public void setRealName(String realName)
	{
		this.realName=realName;
	}
	public String getRealName()
	{
		return this.realName;
	}
	public void setGuid(String guid)
	{
		this.guid=guid;
	}
	public String getGuid()
	{
		return this.guid;
	}
	public void setSuffix(String suffix)
	{
		this.suffix=suffix;
	}
	public String getSuffix()
	{
		return this.suffix;
	}
	public void setTopflag(Boolean topflag)
	{
		this.topflag=topflag;
	}
	public Boolean getTopflag()
	{
		return this.topflag;
	}
	public void setBanflag(Boolean banflag)
	{
		this.banflag=banflag;
	}
	public Boolean getBanflag()
	{
		return this.banflag;
	}
	public void setDelflag(Boolean delflag)
	{
		this.delflag=delflag;
	}
	public Boolean getDelflag()
	{
		return this.delflag;
	}
	public void setPrice(Integer price)
	{
		this.price=price;
	}
	public Integer getPrice()
	{
		return this.price;
	}
	public void setExpiration(Integer expiration)
	{
		this.expiration=expiration;
	}
	public Integer getExpiration()
	{
		return this.expiration;
	}
	public void setRemarks(String remarks)
	{
		this.remarks=remarks;
	}
	public String getRemarks()
	{
		return this.remarks;
	}
	public void setTimeupdata(Date timeupdata)
	{
		this.timeupdata=timeupdata;
	}
	public Date getTimeupdata()
	{
		return this.timeupdata;
	}

} 
 