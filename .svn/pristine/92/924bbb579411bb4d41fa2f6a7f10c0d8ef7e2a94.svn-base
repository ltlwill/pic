package com.hengzhiyi.it.pic.common;

/**
 * JNDI环境信息持有类
 * 
 * @author liutianlong
 *
 */
public final class JndiEnvBeanHolder
{

	private static JndiEnvBean bean = null;

	private JndiEnvBeanHolder()
	{
	}

	/**
	 * 获取JNDI环境
	 * 
	 * @return
	 */
	public static JndiEnvBean getJndiEnvBean()
	{
		return bean;
	}

	/**
	 * 存储JNDI环境
	 * 
	 * @param jndiEnvBean
	 */
	public static void storeJndiBean(JndiEnvBean jndiEnvBean)
	{
		if (bean == null)
		{
			synchronized (JndiEnvBeanHolder.class)
			{
				if (bean == null)
				{
					bean = jndiEnvBean;
				}
			}
		}
	}
}
