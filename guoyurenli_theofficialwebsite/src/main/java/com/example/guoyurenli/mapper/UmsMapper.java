package com.example.guoyurenli.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.guoyurenli.entity.GyUms;

@Mapper
public interface UmsMapper
{
	// 添加人员的状态信息
	public void addUms(GyUms gyUms);
	
}
