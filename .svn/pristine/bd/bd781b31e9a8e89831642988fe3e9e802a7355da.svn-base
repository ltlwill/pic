package com.hengzhiyi.it.pic.tools;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * 文件辅助工具类
 * 
 * @author liutianlong
 *
 */
public final class FileHelper
{

	private final static Logger logger = Logger.getLogger(FileHelper.class);

	/**
	 * 删除文件和目录（如果目录为空，则删除目录，否则不删除目录）
	 */
	public final static void cycleDeleteFile(String path) throws Exception
	{

		cycleDeleteFile(new File(path));
	}

	public final static void cycleDeleteFile(File file) throws Exception
	{
		if (file == null || !file.exists())
		{
			return;
		}
		// 如果是文件
		if(file.isFile())
		{
			try
			{
				// 强制删除文件
				FileUtils.forceDelete(file);
				cycleDeleteFile(file.getParentFile());
			} catch (Exception e)
			{
				logger.error(
						"------Delete file or directory error------,cause:", e);
				throw e;
			}
		}	
		// 如果是目录且目录下没有其他文件，则删除
		else if(file.listFiles().length == 0)
		{
			// 删除目录
			file.delete();
			cycleDeleteFile(file.getParentFile());
		}
	}

}
