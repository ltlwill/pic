package com.hengzhiyi.it.pic.tools;

import java.io.Serializable;

import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.hengzhiyi.it.pic.common.SpringContextHolder;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 缓存管理
 * 
 * @author liutianlong
 *
 */
public class EhCacheManager
{
	private final static String DEFAULT_CACHE_NAME = "ehcache";
	private static CacheManager manager;

	private EhCacheManager()
	{
	}

	public static void put(Serializable key, Serializable value)
	{
		getCache().put(new Element(key, value));
	}

	public static Element get(Serializable key)
	{
		return getCache().get(key);
	}

	public static boolean remove(Serializable key)
	{
		return getCache().remove(key);
	}

	public static void dispose()
	{
		getCache().dispose();
	}

	public static Cache getCache()
	{
		return getInstance().getCache(DEFAULT_CACHE_NAME);
	}

	public static CacheManager getInstance()
	{
		if (manager == null)
		{
			synchronized (EhCacheManager.class)
			{
				if (manager == null)
				{
					EhCacheCacheManager _manager = SpringContextHolder
							.getBean(EhCacheCacheManager.class);
					manager = _manager.getCacheManager();
				}
			}
		}
		return manager;
	}

}
