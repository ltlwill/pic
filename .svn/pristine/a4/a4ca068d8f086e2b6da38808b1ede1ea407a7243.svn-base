package com.hengzhiyi.it.pic.test.junit;

import java.io.File;

import org.junit.Test;

import com.hengzhiyi.it.pic.tools.FileHelper;

public class FileTest
{

	@Test
	public void test1()
	{
		String path = "D:\\";
		try
		{
			FileHelper.cycleDeleteFile(path);
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	@Test
	public void test2()
	{
		String path = "D:\\";
		File file = new File(path);
		System.out.println("result:" + file.delete());
	}
	
	@Test
	public void test3()
	{
		String path = "D:\\999";
		File file = new File(path);
		System.out.println("parent:" + file.getParentFile());
	}
}
