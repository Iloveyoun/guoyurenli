package com.example.guoyurenli.entity; 

import java.util.Date; 

/** 本类由 POJO生成器 自动生成于 2020-07-14 16:41:33
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `gy_ums`
        (`manpowerid`, `state`, `entryunit`, `dateofentry`, `rebates`, `rebatestime`, `reasonforfailure`) 
  VALUES(?, ?, ?, ?, ?, ?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `gy_ums`
        (`manpowerid`, `state`, `entryunit`, `dateofentry`, `rebates`, `rebatestime`, `reasonforfailure`) 
  VALUES(#{manpowerid}, #{state}, #{entryunit}, #{dateofentry}, #{rebates}, #{rebatestime}, #{reasonforfailure}) 

  自增主键: 无
*/ 

public class GyUms 
{ 
 
	public Integer manpowerid ; 
	public Byte state ; 
	public String entryunit ; 
	public Date dateofentry ; 
	public Double rebates ; 
	public Date rebatestime ; 
	public String reasonforfailure ; 


	public void setManpowerid(Integer manpowerid)
	{
		this.manpowerid=manpowerid;
	}
	public Integer getManpowerid()
	{
		return this.manpowerid;
	}
	public void setState(Byte state)
	{
		this.state=state;
	}
	public Byte getState()
	{
		return this.state;
	}
	public void setEntryunit(String entryunit)
	{
		this.entryunit=entryunit;
	}
	public String getEntryunit()
	{
		return this.entryunit;
	}
	public void setDateofentry(Date dateofentry)
	{
		this.dateofentry=dateofentry;
	}
	public Date getDateofentry()
	{
		return this.dateofentry;
	}
	public void setRebates(Double rebates)
	{
		this.rebates=rebates;
	}
	public Double getRebates()
	{
		return this.rebates;
	}
	public void setRebatestime(Date rebatestime)
	{
		this.rebatestime=rebatestime;
	}
	public Date getRebatestime()
	{
		return this.rebatestime;
	}
	public void setReasonforfailure(String reasonforfailure)
	{
		this.reasonforfailure=reasonforfailure;
	}
	public String getReasonforfailure()
	{
		return this.reasonforfailure;
	}

} 
 