package com.hengzhiyi.it.pic.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.log4j.Logger;

/**
 * 系统级请求监听器(建立请求时触发)
 * 
 * @author liutianlong
 *
 */
public class SysRequestListener implements ServletRequestListener
{

	private final static Logger logger = Logger
			.getLogger(SysRequestListener.class);

	@Override
	public void requestDestroyed(ServletRequestEvent arg0)
	{
//		logger.info("------destory sysRequestListener...------");

	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0)
	{
//		logger.info("------init sysRequestListener...------");

	}

}
