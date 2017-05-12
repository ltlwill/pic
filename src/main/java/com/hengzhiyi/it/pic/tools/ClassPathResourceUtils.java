package com.hengzhiyi.it.pic.tools;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * CLASS PATH 下的资源工具类
 * 
 * @author liutianlong
 *
 */
public final class ClassPathResourceUtils
{
	public final static String getResourceAsBase64String(String path)
			throws Exception
	{
		byte[] bytes = getResounceAsByte(path);
		return Base64.encodeBase64String(bytes);
	}

	public final static byte[] getResourceAsBase64(String path)
			throws Exception
	{
		byte[] bytes = getResounceAsByte(path);
		return Base64.encodeBase64(bytes);
	}
	
	public final static String getResourceAsString(String path) throws Exception
	{
		return new String(getResounceAsByte(path),Charset.forName("UTF-8"));
	}

	public final static byte[] getResounceAsByte(String path) throws Exception
	{
		if (StringUtils.isBlank(path))
		{
			return null;
		}
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		byte[] buff = new byte[1024];
		int len = 0;
		try
		{
			is = ClassPathResourceUtils.class.getClassLoader()
					.getResourceAsStream(path);
			bos = new ByteArrayOutputStream();
			while ((len = is.read(buff)) != -1)
			{
				bos.write(buff, 0, len);
			}
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				} catch (Exception e)
				{
				}
			}
			if (bos != null)
			{
				try
				{
					bos.flush();
					bos.close();
				} catch (Exception e)
				{
				}
			}
		}
		return bos.toByteArray();
	}

}
