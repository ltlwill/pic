package com.hengzhiyi.it.pic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.common.SpringContextHolder;
import com.hengzhiyi.it.pic.services.IAppTokenService;

/**
 * 客户端APP请求拦截器
 * 
 * @author liutianlong
 *
 */
public class AppSecurityInterceptor extends HandlerInterceptorAdapter
{
	private final static String PARAM_TOKEN_NAME = "token";
	private final static String PARAM_DEVICE_ID_NAME = "deviceid";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{
		String token = request.getParameter(PARAM_TOKEN_NAME);
		String deviceId = request.getParameter(PARAM_DEVICE_ID_NAME);
		IAppTokenService service = SpringContextHolder
				.getBean(IAppTokenService.class);
		boolean result = service.validateToken(token, deviceId);
		// 验证不通过，未登录
		if (!result)
		{
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(Constants.ErrorCode.TOKEN_INVALID + "");
		}
		return result;
	}
}
