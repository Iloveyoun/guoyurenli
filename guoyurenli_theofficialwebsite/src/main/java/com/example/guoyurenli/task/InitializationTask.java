package com.example.guoyurenli.task;

import java.io.File;
import java.time.LocalTime;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.guoyurenli.entity.GyFactory;
import com.example.guoyurenli.mapper.FactoryMapper;
import com.example.guoyurenli.util.TmpFile;



// 定时任务
@Component
public class InitializationTask
{
	// 3小时以上的临时文件
	int EXPIRED = 1000 * 3600 * 3;	
	// 取得临时文件的目录
	File tmpDir = TmpFile.getDir();
	@Autowired
	FactoryMapper factoryMapper;
	
	// 此类使用，参考CSDN收藏夹中，SpringTask使用详解
//	@Scheduled(fixedRate = 5000)		// 每隔5秒执行一次
	@Scheduled(cron = "0 0 1 ? * *")	// 每天1点执行
	public void testTaskFirst() {
    	// 任务1
		System.out.println("定时任务1：清理tmp下过期文件，开始执行，当前时间：" + LocalTime.now());
		this.execute1();
		System.out.println("定时任务1：清理tmp下过期文件，执行完毕，当前时间：" + LocalTime.now());
		
		// 任务2
		System.out.println("定时任务2：清理删除的公司，开始执行，当前时间：" + LocalTime.now());
		this.execute2();
		System.out.println("定时任务2：清理删除的公司，执行完毕，当前时间：" + LocalTime.now());
		
	}
    
    // 清理过期文件
    public void execute1()
	{
		if(tmpDir == null) return;
		if(! tmpDir.exists() ) return;
		
		// 返回此文件目录中所有的文件
		File[] files = tmpDir.listFiles();
		if(files == null || files.length == 0)
			return;
		
		// 清理过期文件
		long now = System.currentTimeMillis();	// 当前时间
		for(File file : files)
		{
			if(now - file.lastModified() > EXPIRED)	// 当前时间-文件最后修改时间 > 三个小时
			{
				try
				{
					System.out.println("** 清理过期文件：" + file.getAbsolutePath());
					FileUtils.deleteQuietly(file);	// 删除
				} catch (Exception e){
					System.out.println("** 清理过期文件过程中失败：" + file.getAbsolutePath());
				}
			}
		}

	}
    
    // 任务二
    public void execute2()
	{
		System.out.println("开始执行删除公司的招聘简章等等");
		// 获取所有delflag字段为1的数据
		List<GyFactory> factoryList = factoryMapper.getDelFactory();
		
		for(GyFactory row : factoryList)
		{
			try{
				
				if(row.suffix != null && row.suffix.length() > 0)
				{
					// 执行清理，清理招聘简章
					this.clearMessage( row );
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// 删除公司的招聘简章及数据库信息
	private void clearMessage(GyFactory row)
	{
		// 文件存储目录
		File dataDir = TmpFile.getFactory();
		// 文件名
		String fileName = row.guid + row.suffix;
		// 需要的总文件路径
		File file = new File(dataDir, fileName);
		
		// 删除招聘简章
		FileUtils.deleteQuietly(file);	
		
		// 删除数据库公司的数据
		factoryMapper.delFactory(row.id);
		System.out.println("** 删除公司任务OK：公司名【" + row.title + "】- 招聘简章名：【" + fileName + "】");
		
	}

}
