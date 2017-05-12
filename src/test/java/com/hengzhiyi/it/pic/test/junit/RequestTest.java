package com.hengzhiyi.it.pic.test.junit;

import java.net.URL;
import java.net.URLConnection;

import org.junit.Assert;
import org.junit.Test;

public class RequestTest
{

	@Test
	public void testRequest()
	{
		String urlStr = "http://192.168.2.34:8088/pic/ws/search/doSearch?sku=10018350";
		try
		{
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.connect();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail();
		}
	}
}
