package com.hengzhiyi.it.pic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hengzhiyi.it.pic.base.Preparable;
import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 系统全局拦截器
 * 
 * @author liutianlong
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter
{

	private final static Logger logger = Logger
			.getLogger(SecurityInterceptor.class);

	// AJAX(XMLHttpRequest)具有的请求头:X-Requested-With
	private final static String HEAD_X_REQUESTED_WITH = "X-Requested-With";
	// AJAX(XMLHttpRequest)具有的请求头X-Requested-With的值
	private final static String VALUE_X_REQUESTED_WITH = "XMLHttpRequest";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{
		logger.info("------invoke systemInterceptor...------");
		HttpSession session = request.getSession(true);
		// 从session里获取登录用户信息
		Object user = (User) session.getAttribute(Constants.LOGIN_USER);
		// 如果没有登录，则跳转到登录页面
		if (user == null)
		{
			response.sendRedirect(request.getContextPath() + "/"
					+ Constants.LOGIN_PAGE_URL);
			// response.getWriter().write("<script>window.location.href='"+request.getContextPath()+"/login'</script>");
			return false;
		}
		try
		{
			// 获取XMLHttpRequest的请求头:X-Requested-With
			String xhpHead = request.getHeader(HEAD_X_REQUESTED_WITH);
			// 如果这个请求头部存在或则这个请求头的值不是XMLHttpRequest(即不是AJAX请求)，则执行Prepareble接口的预调用动作
			if (StringUtils.isBlank(xhpHead)
					|| !VALUE_X_REQUESTED_WITH.equalsIgnoreCase(xhpHead))
			{
				Object bean = null;
				if (handler instanceof HandlerMethod)
				{
					bean = ((HandlerMethod) handler).getBean();
				}
				if (bean != null && (bean instanceof Preparable))
				{
					// 如果实现了自定义的Preparable接口，则掉先调用prepare方法，然后再去调用控制器中的对应的处理方法
					((Preparable) bean).prepare(request, response);
				}
			}
		} catch (Exception e)
		{
			logger.error(
					"------SecurityInterceptor happen exception------,cause:",
					e);
		}
		return true;

	}

}
