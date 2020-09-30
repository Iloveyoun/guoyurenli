package com.example.guoyurenli.util;

import java.io.File;


/** 临时文件的位置、永久文件位置
 *
 */
public class TmpFile
{
	// 临时文件目录 
	public static File getDir()
	{
		return new File("c:/guoyufile/tmp");
	}
	
	// 获取临时文件目录
	public static File getFile(String tmpName)
	{
		File dir = getDir();
		dir.mkdirs();
		return new File(dir, tmpName);
	}
	
	// 文件存储目录 
	public static File getFactory()
	{
		return new File("c:/guoyufile/factory");
	}
	
	// 获取文件存储目录
	public static File getFactoryFile(String tmpName)
	{
		File dir = getFactory();
		dir.mkdirs();
		return new File(dir, tmpName);
	}
	
	
}
