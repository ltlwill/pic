package com.hengzhiyi.it.pic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.hengzhiyi.it.pic.base.BaseController;
import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.services.ISysService;
import com.hengzhiyi.it.pic.services.IUserService;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 登录控制器
 * 
 * @author liutianlong
 *
 */

@Controller
public class LoginController extends BaseController
{

	private final static Logger logger = Logger
			.getLogger(LoginController.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private ISysService sysService;

	@RequestMapping("/login")
	public String toLoginPage(HttpServletRequest request,HttpServletResponse response)
	{
		// login不走拦截器，不能将默认的信息（如：系统设置）保存本到session中，需要手动调prepare接口
		super.prepare(request, response);
		logger.debug("------to login page------");
		return "login/login";
	}

	@RequestMapping(value = "/userLogin", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XHTML_XML_VALUE })
	@ResponseBody
	public String userLogin(User user, WebRequest request)
	{
		String flag = "";
		User _user = null;
		try
		{
			if (user != null)
			{
				// 获取数据库中用户信息
				_user = userService.getUserByNamePwd(user.getUserName(),
						user.getPassword());
			}
			// 用户存在且是有效的（即：启用状态）
			if (_user != null
					&& Integer.valueOf(User.Status.ENABLED).equals(
							_user.getStatus()))
			{
				// 登录时间验证
				boolean validateResult = sysService.validateLoginTime(_user);
				// 登录时间验证不通过
				if (!validateResult)
				{
					flag = "non_working_time";
				} else
				{
					flag = "ok";
					// 将user保存到session中
					_user.setPassword(null);
					request.setAttribute(Constants.LOGIN_USER, _user,
							WebRequest.SCOPE_SESSION);
				}
			} else
			{
				flag = "fail";
			}
			// flag = (_user != null && Integer.valueOf(User.Status.ENABLED)
			// .equals(_user.getStatus())) ? "ok" : "fail";
		} catch (Exception e)
		{
			flag = "error";
			logger.error("------validation user error!------,cause:", e);
		}
		return flag;
	}

	@RequestMapping("/logout")
	public String logout(WebRequest request, HttpServletRequest request2,
			HttpServletResponse response)
	{
		request.removeAttribute(Constants.LOGIN_USER, WebRequest.SCOPE_SESSION);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		request2.getSession().invalidate();
		return "redirect:/login";
	}

}
