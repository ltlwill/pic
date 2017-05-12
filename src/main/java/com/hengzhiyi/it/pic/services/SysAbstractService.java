package com.hengzhiyi.it.pic.services;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hengzhiyi.it.pic.common.JndiEnvBean;
import com.hengzhiyi.it.pic.common.JndiEnvBeanHolder;

/**
 * 系统抽象服务类
 * 
 * @author liutianlong
 *
 */
@Service
public abstract class SysAbstractService
{

	@Value("${virtualDir}")
	private String virtualDir;

	@Value("${virtualDirContext}")
	private String virtualDirContext;

	@Value("${defaultCompressWidth}")
	private Integer defaultCompressWidth;

	@Value("${defaultCompressHeight}")
	private Integer defaultCompressHeight;

	@Value("${compressFolder}")
	private String compressFolder;

	/**
	 * 获取请求的主机
	 * 
	 * @param request
	 * @return
	 */
	protected String getRequestHost(HttpServletRequest request)
	{
		String host = request.getHeader("Host"); // 无http协议前缀
		String protocol = request.getScheme();
		if (StringUtils.isEmpty(host) || StringUtils.isEmpty(protocol))
		{
			throw new RuntimeException("Get the host error.");
		}
		return protocol + "://" + host;
	}

	/**
	 * 获取服务器设置的虚拟目录
	 * 
	 * @return
	 */
	protected String getVirtualDir()
	{
		// 如果JNDI中配置了下列信息，则以JNDI中的配置为主；如果JNDI中没有配置，则以默认的信息(即conf/image.properties中的配置)为主
		JndiEnvBean bean = JndiEnvBeanHolder.getJndiEnvBean();
		if (bean == null || StringUtils.isBlank(bean.getVirtualDir()))
		{
			return this.virtualDir;
		}
		return bean.getVirtualDir();
	}

	/**
	 * 获取访问服务器虚拟目录的上下文
	 * 
	 * @return
	 */
	protected String getVirtualDirContext()
	{
		// 如果JNDI中配置了下列信息，则以JNDI中的配置为主；如果JNDI中没有配置，则以默认的信息(即conf/image.properties中的配置)为主
		JndiEnvBean bean = JndiEnvBeanHolder.getJndiEnvBean();
		if (bean == null || StringUtils.isBlank(bean.getVirtualDirContext()))
		{
			return this.virtualDirContext;
		}
		return bean.getVirtualDirContext();
	}

	/**
	 * 获取压缩图片的默认宽度
	 * 
	 * @return
	 */
	protected Integer getDefaultCompressWidth()
	{
		// 如果JNDI中配置了下列信息，则以JNDI中的配置为主；如果JNDI中没有配置，则以默认的信息(即conf/image.properties中的配置)为主
		JndiEnvBean bean = JndiEnvBeanHolder.getJndiEnvBean();
		if (bean == null || bean.getDefaultCompressWidth() == null)
		{
			return this.defaultCompressWidth;
		}
		return Integer.valueOf(bean.getDefaultCompressWidth());
	}

	/**
	 * 获取压缩图片的默认高度
	 * 
	 * @return
	 */
	protected Integer getDefaultCompressHeight()
	{
		// 如果JNDI中配置了下列信息，则以JNDI中的配置为主；如果JNDI中没有配置，则以默认的信息(即conf/image.properties中的配置)为主
		JndiEnvBean bean = JndiEnvBeanHolder.getJndiEnvBean();
		if (bean == null || bean.getDefaultCompressHeight() == null)
		{
			return this.defaultCompressWidth;
		}
		return Integer.valueOf(bean.getDefaultCompressHeight());
	}

	/**
	 * 获取压缩版所在文件夹
	 * 
	 * @return
	 */
	protected String getCompressFolder()
	{
		// 如果JNDI中配置了下列信息，则以JNDI中的配置为主；如果JNDI中没有配置，则以默认的信息(即conf/image.properties中的配置)为主
		JndiEnvBean bean = JndiEnvBeanHolder.getJndiEnvBean();
		if (bean == null || StringUtils.isBlank(bean.getCompressFolder()))
		{
			return this.compressFolder;
		}
		return bean.getCompressFolder();
	}

}
