package com.hengzhiyi.it.pic.tools;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public final class ListHelper
{
	// 每个集合最大限制长度
	private final static int MAX_LIMIT_SIZE = 1000;

	/**
	 * 将指定集合按照默认的最大长度进行分割
	 * 
	 * @param list
	 *            要分割的集合
	 * @return 分割后的集合
	 */
	public static <T> List<List<T>> split(List<T> list)
	{
		return doSplit(list, MAX_LIMIT_SIZE);
	}

	/**
	 * 按指定的集合按指定的长度分割集合
	 * 
	 * @param list
	 *            要分割的集合
	 * @param limit
	 *            分割长度
	 * @return
	 */
	public static <T> List<List<T>> split(List<T> list, int limit)
	{
		if (limit <= 0)
		{
			throw new RuntimeException(
					"Split list error,cause the illegal argument limit.");
		}
		return doSplit(list, limit);
	}

	private static <T> List<List<T>> doSplit(List<T> list, int limit)
	{
		if (list == null || list.isEmpty())
		{
			return null;
		}
		List<List<T>> result = new ArrayList<List<T>>();
		int size = list.size();
		int mold = size % limit; // 余数
		int remainder = size / limit;// 倍数
		if (remainder == 0)
		{
			result.add(list);
		} else
		{
			for (int i = 0; i < remainder; i++)
			{
				result.add(list.subList(i * limit, (i + 1) * limit));
			}
			if (mold != 0)
			{
				result.add(list.subList(remainder * limit, remainder * limit
						+ mold));
			}
		}
		return result;
	}

	/**
	 * 集合分组
	 * 
	 * @param list
	 *            目标集合
	 * @param fields
	 *            分组字段
	 * @return
	 */
	public static <T> Map<String, List<T>> group(List<T> list, String... fields)
	{
		Map<String, List<T>> groups = new LinkedHashMap<String, List<T>>();
		String key = null;
		for (T t : list)
		{
			key = getGroupKey(t, fields);
			if (!groups.containsKey(key))
			{
				groups.put(key, new ArrayList<T>());
			}
			groups.get(key).add(t);
		}
		return groups;
	}

	public static <T> BigDecimal sum(List<T> list, String field)
	{
		if (list == null || list.isEmpty())
		{
			return null;
		}
		BigDecimal sum = new BigDecimal(0);
		String str = null;
		for (T t : list)
		{
			str = String.valueOf(invokeObj(t, field));
			if (StringUtils.isBlank(str))
			{
				sum = sum.add(new BigDecimal(0));
			} else
			{
				sum = sum.add(new BigDecimal(str));
			}
		}
		return sum;
	}

	private static <T> String getGroupKey(T o, String... fields)
	{
		if (fields == null || fields.length == 0)
		{
			throw new RuntimeException("分组失败，参数:fields=" + fields);
		}

		String key = "";
		for (String field : fields)
		{
			key += String.valueOf(invokeObj(o, field));
		}
		return key;
	}

	@SuppressWarnings("unchecked")
	private static <T> Object invokeObj(T t, String field)
	{
		Class<T> clazz = (Class<T>) t.getClass();
		Method method = null;
		Object result = null;
		try
		{
			if(t instanceof Map)
			{
				method = clazz.getDeclaredMethod("get",Object.class);
				result = method.invoke(t,field);
			}else
			{
				method = clazz.getDeclaredMethod("get"
						+ field.substring(0, 1).toUpperCase() + field.substring(1));
				result = method.invoke(t);
			}	
		} catch (Exception e)
		{
			throw new RuntimeException("reflect error,cause:", e);
		}
		return result;
	}
}
