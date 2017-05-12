package com.hengzhiyi.it.pic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.hengzhiyi.it.pic.base.BaseController;
import com.hengzhiyi.it.pic.common.Constants;

/**
 * 用户设置控制器
 * 
 * @author liutianlong
 *
 */
@Controller
@RequestMapping("/user")
public class UserSettingController extends BaseController
{
	private final static String TARGET_PAGE = "/pages/setting/setting.jsp";
	private final static String ACTIVE_MENU_ID = "img-setting";
	
	@RequestMapping("/setting")
	public String toImgSearchPage(WebRequest request)
	{
		// 设置要渲染的目标页面名称
		request.setAttribute(Constants.TARGET_PAGE_NAME_FLAG, TARGET_PAGE,
				WebRequest.SCOPE_REQUEST);
		// 设置当前正在使用的菜单ID
		request.setAttribute(Constants.ACTIVE_MENU_ID_FLAG, ACTIVE_MENU_ID,
				WebRequest.SCOPE_REQUEST);
		return "home/index";
	}
}
