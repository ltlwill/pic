package com.hengzhiyi.it.pic.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义预动作接口，主要解决SpringMVC中没有类似Struts2中的Preparable接口的作用，即：在处理所有的请求时，
 * 都可以在调用控制器的莫表方法之前调用一个公用的方法，这样就可以在一个公用的方法里做一些准备动作
 * 注：这个接口的触发调用是在SpringMVC的拦截器中触发的，已经控制了XMLHttpRequest（即AJAX）的请求不会触发这个预方法的调用，
 * 
 * @author liutianlong
 *
 */
public interface Preparable
{
	/**
	 * 预准备方法
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	void prepare(HttpServletRequest request, HttpServletResponse response);

}
