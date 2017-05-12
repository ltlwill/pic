package com.hengzhiyi.it.pic.common;

/**
 * 常量类
 * 
 * @author liutianlong
 *
 */
public class Constants
{

	/**
	 * 登录用户
	 */
	public final static String LOGIN_USER = "login_user";

	public final static String LOGIN_PAGE_URL = "login";
	
	public final static String SYS_SETTINGS_NAME = "sysSettingsStr";

	/**
	 * 目标页面名称
	 */
	public final static String TARGET_PAGE_NAME_FLAG = "target_page_name";

	/**
	 * 活动的菜单ID
	 */
	public final static String ACTIVE_MENU_ID_FLAG = "active_menu_id";
	
	/**
	 * token最大使用天数
	 */
	public final static long TOKEN_MAX_USABLE_DAYS = 30l;
	
	public final static class Split
	{
		public final static String COMMON_SPLIT = "@@@";
	}

	public final static class ErrorCode
	{
		// 未知错误
		public final static int UNKNOW = -1;
		// 成功
		public final static int SUCCESS = 0;
		// 文件已存在
		public final static int FILE_EXISTS = 1001;
		// 账号已存在
		public final static int USER_EXISTS = 2001;
		// 账号或密码不对
		public final static int USER_NAME_PWD_ERROR = 2002;
		// 非工作时间
		public final static int NON_WORKIING_TIME = 2003;
		// token失效
		public final static int TOKEN_INVALID = 10001;
	}

	/**
	 * 用户定义常量
	 * 
	 * @author liutianlong
	 *
	 */
	public final static class UserDefinition
	{
		public final static String ADMIN = "admin"; // 管理员名称
	}
}
