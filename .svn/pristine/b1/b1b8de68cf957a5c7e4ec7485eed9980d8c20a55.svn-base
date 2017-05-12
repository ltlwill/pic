package com.hengzhiyi.it.pic.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.vo.User;

public class SecurityFilter implements Filter
{
	
	private final static Logger logger = Logger.getLogger(SecurityFilter.class);

	@Override
	public void destroy()
	{

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException
	{
		logger.debug("------invkoke SecurityFilter------");
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		HttpSession session = request.getSession(true);
		// 从session里获取登录用户信息
		Object user = (User)session.getAttribute(Constants.LOGIN_USER);
		// 如果没有登录，则跳转到登录页面
		if( user == null )
		{
			response.sendRedirect(request.getContextPath() + "/" + Constants.LOGIN_PAGE_URL);
		}	
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{

	}

}
