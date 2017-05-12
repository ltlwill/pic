package com.hengzhiyi.it.pic.base;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.services.ISysService;
import com.hengzhiyi.it.pic.services.IUserService;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 基础控制器,提供基础的数据绑定（日期转换等）
 * 
 * @author liutianlong
 *
 */
// @Component
public abstract class BaseController implements ApplicationContextAware,
		Preparable
{

	protected final static Logger logger = Logger
			.getLogger(BaseController.class);

	@Autowired
	protected IUserService userService;

	@Autowired
	protected ISysService sysService;

	private ApplicationContext applicationcontext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationcontext)
			throws BeansException
	{
		this.applicationcontext = applicationcontext;
	}

	@Override
	public void prepare(HttpServletRequest request, HttpServletResponse response)
	{
		logger.debug("------invoke baseController prepare method....------");

		HttpSession session = request.getSession(true);
		User loginUser = null;
		if (session.getAttribute(Constants.LOGIN_USER) != null)
		{
			loginUser = (User) session.getAttribute(Constants.LOGIN_USER);
			if (Constants.UserDefinition.ADMIN.equals(loginUser.getUserName()))
			{
				// 管理员标识
				session.setAttribute("isAdmin", true);
			}
			if (Constants.UserDefinition.ADMIN.equals(loginUser.getUserName())
					|| Integer.valueOf(User.UserType.ADMIN).equals(
							loginUser.getUserType())
					|| Integer.valueOf(User.UserType.SUB_ADMIN).equals(
							loginUser.getUserType()))
			{
				// 设置删除图片的权限
				session.setAttribute("del_img_permission", true);
			}
		}
		try
		{
			// 系统设置数据
			String settings = sysService.getSysSetting();
			session.setAttribute(Constants.SYS_SETTINGS_NAME, settings);
		} catch (Exception e)
		{
			logger.error("获取系统设置信息失败", e);
		}

	}

	/**
	 * 公共的异常处理(如果出现异常，则打印异常信息并返回500页面)
	 * 
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String hanldeException(HttpServletRequest request,
			HttpServletResponse response, Exception e)
	{
		logger.error(e.getMessage());
		return "common/500";
	}

	/**
	 * 初始化自定义日期格式化器
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}

	/**
	 * 获取主机(如:192.168.2.34:8088)
	 * 
	 * @param request
	 * @return
	 */
	protected String getHost(WebRequest request)
	{
		String host = request.getHeader("Host");
		if (StringUtils.isEmpty(host))
		{
			throw new RuntimeException("Get the host is null.");
		}
		return host;
	}

	/**
	 * 获取应用上下文
	 * 
	 * @return
	 */
	protected ApplicationContext getApplicationContext()
	{
		return this.applicationcontext;
	}

	/**
	 * 根据Bean名称获取Bean对象
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getBean(String name)
	{
		this.check();
		return (T) this.applicationcontext.getBean(name);
	}

	/**
	 * 根据Bean类型获取Bean对象
	 */
	protected <T> T getBean(Class<T> clazz)
	{
		this.check();
		return (T) this.applicationcontext.getBean(clazz);
	}

	private void check()
	{
		if (this.applicationcontext == null)
		{
			throw new IllegalArgumentException(
					"The bean of BaseController is not be create.");
		}
	}

}
