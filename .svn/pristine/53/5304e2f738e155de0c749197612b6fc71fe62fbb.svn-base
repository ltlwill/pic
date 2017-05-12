package com.hengzhiyi.it.pic.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext
 * 
 * @author liutianlong
 *
 */
 @Component
public class SpringContextHolder implements ApplicationContextAware
{
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationcontext)
			throws BeansException
	{
		applicationContext = applicationcontext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext
	 * 
	 * @param applicationcontext
	 * @return
	 */
	public static ApplicationContext getApplicationContext()
	{
		check();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name)
	{
		check();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz)
	{
		return (T) applicationContext.getBean(clazz);
	}

	/**
	 * 清除applicationContext
	 */
	public static void clean()
	{
		applicationContext = null;
	}

	private static void check()
	{
		if (applicationContext == null)
		{
			throw new IllegalArgumentException(
					"applicationContext参数未注入,未定义SpringContextHolder,请在springContext.xml中或注解定义");
		}
	}

}
