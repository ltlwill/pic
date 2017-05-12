package com.hengzhiyi.it.pic.common;

/**
 * 
 * JNDI环境实体类
 * 
 * @author liutianlong
 *
 */
public class JndiEnvBean
{

	// 服务器设置的虚拟路径
	private String virtualDir;

	// 访问服务器虚拟路径的上下文
	private String virtualDirContext;
	
	// 默认压缩版的宽度
	private String defaultCompressWidth;
	
	// 默认压缩版的高度
	private String defaultCompressHeight;
	
	// 压缩文件的文件夹
	private String compressFolder;

	public String getVirtualDir()
	{
		return virtualDir;
	}

	public void setVirtualDir(String virtualDir)
	{
		this.virtualDir = virtualDir;
	}

	public String getVirtualDirContext()
	{
		return virtualDirContext;
	}

	public void setVirtualDirContext(String virtualDirContext)
	{
		this.virtualDirContext = virtualDirContext;
	}

	public String getDefaultCompressWidth()
	{
		return defaultCompressWidth;
	}

	public void setDefaultCompressWidth(String defaultCompressWidth)
	{
		this.defaultCompressWidth = defaultCompressWidth;
	}

	public String getDefaultCompressHeight()
	{
		return defaultCompressHeight;
	}

	public void setDefaultCompressHeight(String defaultCompressHeight)
	{
		this.defaultCompressHeight = defaultCompressHeight;
	}

	public String getCompressFolder()
	{
		return compressFolder;
	}

	public void setCompressFolder(String compressFolder)
	{
		this.compressFolder = compressFolder;
	}

}
