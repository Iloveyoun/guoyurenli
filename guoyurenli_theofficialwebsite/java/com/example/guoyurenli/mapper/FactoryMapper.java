package com.example.guoyurenli.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.example.guoyurenli.entity.GyFactory;

@Mapper
public interface FactoryMapper
{
	// 添加一个厂区（公司）
	public void addFactory(GyFactory factory);
	
	// 查询所有公司数量（所有）
	public int getCount(Map<String, Object> map);
	
	// 查询所有公司列表（分页条数）
	public List<Map<String, Object>> factoryList(Map<String, Object> map);
	
	// 根据公司ID查询公司下有几个发布的岗位
	public int getPostCount(int factoryid);

	// 根据特定ID查询公司信息（公司详细信息页面）
	public GyFactory getFactory(Integer enterpriseId);

	// 根据厂区的ID查询厂区发布的岗位
	public List<Map<String, Object>> getPostList(Integer id);

	
	/*++++++++企业管理系统++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	// 把企业设置成置顶/非置顶
	@Update("UPDATE `gy_factory` SET ${key}=#{value}, timeupdata=#{timeupdata} WHERE id=#{id}")
	public void updateState(Map<String, Object> map);

	// 更新所有岗位状态的时候，更改企业的更新时间
	@Update("UPDATE `gy_factory` SET timeupdata=#{timeupdate} WHERE id=#{company}")
	public void updatetime(Map<String, Object> mapa);

	// 删除一个公司，把删除字段置为1
	@Update("UPDATE `gy_factory` SET delflag=1 WHERE id=#{id}")
	public void delectFactory(int id);

	
	// 定时任务:获取所有delflag字段为1的数据,就是删除了的
	public List<GyFactory> getDelFactory();
	
	// 定时任务，删除公司的数据。
	@Delete("DELETE FROM `gy_factory` WHERE id=#{id}")
	public void delFactory(int id);

	
}
