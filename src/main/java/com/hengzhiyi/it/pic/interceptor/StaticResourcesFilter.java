package com.hengzhiyi.it.pic.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class StaticResourcesFilter implements Filter
{

	private final static Logger logger = Logger
			.getLogger(StaticResourcesFilter.class);

	@Override
	public void destroy()
	{

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException
	{
		logger.debug("------invkoke SecurityFilter------");
		// HttpServletRequest request = (HttpServletRequest)req;
		// HttpServletResponse response = (HttpServletResponse)res;
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{

	}

}
