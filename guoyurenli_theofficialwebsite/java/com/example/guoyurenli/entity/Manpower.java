package com.example.guoyurenli.entity; 

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat; 

/** 本类由 POJO生成器 自动生成于 2020-07-14 16:41:33
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `manpower`
        (`id`, `creator`, `name`, `idCard`, `myselfCellphone`, `academicDegree`, `residence`, `emergencyContactPerson`, `sex`, `emergencyContactPersonRelation`, `emergencyContactPersonCellphone`, `referrer`, `company`, `entry`, `writeTime`) 
  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `manpower`
        (`id`, `creator`, `name`, `idCard`, `myselfCellphone`, `academicDegree`, `residence`, `emergencyContactPerson`, `sex`, `emergencyContactPersonRelation`, `emergencyContactPersonCellphone`, `referrer`, `company`, `entry`, `writeTime`) 
  VALUES(#{id}, #{creator}, #{name}, #{idCard}, #{myselfCellphone}, #{academicDegree}, #{residence}, #{emergencyContactPerson}, #{sex}, #{emergencyContactPersonRelation}, #{emergencyContactPersonCellphone}, #{referrer}, #{company}, #{entry}, #{writeTime}) 

  自增主键: id
*/ 

public class Manpower 
{ 
 
	public Integer id ; 
	public Integer creator ; 
	public String name ; 
	public String idCard ; 
	public String myselfCellphone ; 
	public Byte academicDegree ; 
	public String residence ; 
	public String emergencyContactPerson ; 
	public Byte sex ; 
	public Byte emergencyContactPersonRelation ; 
	public String emergencyContactPersonCellphone ; 
	public String referrer ; 
	public String company ; 
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date entry ; 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date writeTime ; 


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
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setIdCard(String idCard)
	{
		this.idCard=idCard;
	}
	public String getIdCard()
	{
		return this.idCard;
	}
	public void setMyselfCellphone(String myselfCellphone)
	{
		this.myselfCellphone=myselfCellphone;
	}
	public String getMyselfCellphone()
	{
		return this.myselfCellphone;
	}
	public void setAcademicDegree(Byte academicDegree)
	{
		this.academicDegree=academicDegree;
	}
	public Byte getAcademicDegree()
	{
		return this.academicDegree;
	}
	public void setResidence(String residence)
	{
		this.residence=residence;
	}
	public String getResidence()
	{
		return this.residence;
	}
	public void setEmergencyContactPerson(String emergencyContactPerson)
	{
		this.emergencyContactPerson=emergencyContactPerson;
	}
	public String getEmergencyContactPerson()
	{
		return this.emergencyContactPerson;
	}
	public void setSex(Byte sex)
	{
		this.sex=sex;
	}
	public Byte getSex()
	{
		return this.sex;
	}
	public void setEmergencyContactPersonRelation(Byte emergencyContactPersonRelation)
	{
		this.emergencyContactPersonRelation=emergencyContactPersonRelation;
	}
	public Byte getEmergencyContactPersonRelation()
	{
		return this.emergencyContactPersonRelation;
	}
	public void setEmergencyContactPersonCellphone(String emergencyContactPersonCellphone)
	{
		this.emergencyContactPersonCellphone=emergencyContactPersonCellphone;
	}
	public String getEmergencyContactPersonCellphone()
	{
		return this.emergencyContactPersonCellphone;
	}
	public void setReferrer(String referrer)
	{
		this.referrer=referrer;
	}
	public String getReferrer()
	{
		return this.referrer;
	}
	public void setCompany(String company)
	{
		this.company=company;
	}
	public String getCompany()
	{
		return this.company;
	}
	public void setEntry(Date entry)
	{
		this.entry=entry;
	}
	public Date getEntry()
	{
		return this.entry;
	}
	public void setWriteTime(Date writeTime)
	{
		this.writeTime=writeTime;
	}
	public Date getWriteTime()
	{
		return this.writeTime;
	}
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "Manpower:[" 
				+ "creator:" + creator
				+ ",name:" + name
				+ ",sex:" + sex
				+ ",idCard:" + idCard
				+ ",myselfCellphone:" + myselfCellphone
				+ ",academicDegree:" + academicDegree
				+ ",residence:" + residence
				+ ",emergencyContactPerson:" + emergencyContactPerson
				+ ",emergencyContactPersonCellphone:" + emergencyContactPersonCellphone
				+ ",emergencyContactPersonRelation:" + emergencyContactPersonRelation
				+ ",referrer:" + referrer
				+ ",company:" + company
				+ ",entry:" + entry
				+ ",writeTime:" + writeTime
				+ "]";
	}
	
	

} 
 