package com.hengzhiyi.it.pic.listener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.hengzhiyi.it.pic.common.JndiEnvBean;
import com.hengzhiyi.it.pic.common.JndiEnvBeanHolder;

/**
 * 系统中JNDI环境监听器(系统启动和销毁时触发),注:因为在spring创建的bean(如:SysAbstractService)中有用到JNDI信息,
 * 所以在web.xml中配置这个监听器时,需放在spring加载资源的ContextLoaderListener监听器之前，以保证spring在创建bean时
 * ,系统的JNDI信息已经初始化完成.
 * 
 * @author liutianlong
 *
 */
public class SysJndiEnvListener implements ServletContextListener
{

	private final static Logger logger = Logger
			.getLogger(SysJndiEnvListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event)
	{
		logger.info("------destory sysContextListener...------");

	}

	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		logger.info("------init sysContextListener...------");
		initJndiEnvironment(event);
	}

	/**
	 * 初始化JNDI环境
	 * 
	 * @param event
	 */
	private void initJndiEnvironment(ServletContextEvent event)
	{
		try
		{
			Context context = new InitialContext();
			Context env = (Context) context.lookup("java:comp/env");
			JndiEnvBean bean = (JndiEnvBean) env.lookup("bean/appConfig");
			if (bean != null)
			{
				JndiEnvBeanHolder.storeJndiBean(bean);
			}
		} catch (NamingException e)
		{
			logger.error("------init JNDI environment fail------,cause:", e);
			// 如果抛出异常，则导致应用启动失败，不能访问，这里的JNDI应该是可有可无的，有无不影响应用的启动，所以出错不抛出异常
//			throw new RuntimeException(e);
		}
	}

}
