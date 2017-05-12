package com.hengzhiyi.it.pic.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.hengzhiyi.it.pic.base.BaseController;
import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.services.ISysService;
import com.hengzhiyi.it.pic.services.IUserService;
import com.hengzhiyi.it.pic.vo.AccountVO;
import com.hengzhiyi.it.pic.vo.PagedVO;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 系统管理控制器
 * 
 * @author liutianlong
 *
 */
@Controller
@RequestMapping("/sys")
public class SysManagerController extends BaseController
{
	// private final static Logger logger = Logger
	// .getLogger(UserManagerController.class);

	private final static String TARGET_PAGE = "/pages/sys/sys.jsp";
	private final static String ACTIVE_MENU_ID = "img-sys";

	@Autowired
	private ISysService sysService;

	@Autowired
	private IUserService userService;

	@RequestMapping("/manager")
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

	@RequestMapping("/getUserList")
	@ResponseBody
	public PagedVO<List<User>> getUserByPage(PagedVO<?> params, User user)
	{
		try
		{
			return userService.getUserList(params, user);
		} catch (Exception e)
		{
			throw new RuntimeException("分页查询账号失败", e);
		}
	}

	@RequestMapping("/editUser")
	public ResponseEntity<Object> editUser(User user)
	{
		ResponseEntity<Object> res = null;
		try
		{
			userService.editUser(user);
			res = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (BusinessException e)
		{
			logger.error("操作账号失败,此用户名已存在！", e);
			res = new ResponseEntity<Object>(e.getErrorCode(),
					HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e)
		{
			logger.error("操作账号失败！", e);
			res = new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}

	@RequestMapping("/getUserById")
	@ResponseBody
	public User getUserById(String id)
	{
		try
		{
			return userService.getUserById(id);
		} catch (Exception e)
		{
			logger.error("获取用户失败", e);
			throw new RuntimeException("获取用户失败", e);
		}
	}

	@RequestMapping("/delUserByIds")
	public ResponseEntity<Object> delUserByIds(
			@RequestParam("ids[]") List<String> ids)
	{
		ResponseEntity<Object> res = null;
		try
		{
			userService.delUserByIds(ids);
			res = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("删除用户失败", e);
			res = new ResponseEntity<Object>(e.getMessage(),
					HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}

	@RequestMapping("/editAccount")
	public ResponseEntity<Object> editAccount(AccountVO accountVO)
	{
		ResponseEntity<Object> res = null;
		try
		{
			sysService.editAccount(accountVO);
			res = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (BusinessException e)
		{
			logger.error("编辑账号失败,此账号名称已存在！", e);
			res = new ResponseEntity<Object>(e.getErrorCode(),
					HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e)
		{
			logger.error("编辑账号失败", e);
			res = new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}

	@RequestMapping("/getAccountList")
	@ResponseBody
	public PagedVO<List<AccountVO>> getAccountList(PagedVO<?> params)
	{
		try
		{
			return sysService.getAccountList(params);
		} catch (Exception e)
		{
			throw new RuntimeException("分页查询账号失败", e);
		}
	}

	@RequestMapping("/getAccountById")
	@ResponseBody
	public AccountVO getAccountById(String id)
	{
		try
		{
			return sysService.getAccountById(id);
		} catch (Exception e)
		{
			logger.error("获取账号失败", e);
			throw new RuntimeException("获取账号失败", e);
		}
	}

	@RequestMapping("/delAccountByIds")
	public ResponseEntity<Object> delAccountByIds(
			@RequestParam("ids[]") List<String> ids)
	{
		ResponseEntity<Object> res = null;
		try
		{
			sysService.delAccountByIds(ids);
			res = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("删除用户失败", e);
			res = new ResponseEntity<Object>(e.getMessage(),
					HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}

	@RequestMapping("/getBindAccountsInfo")
	@ResponseBody
	public List<Map<String, Object>> getBindAccountsInfo(String userId)
	{
		return sysService.getBindAccountsInfo(userId);
	}

	@RequestMapping("/getBindAccountsByList")
	@ResponseBody
	public List<Map<String, Object>> getBindAccountsByList()
	{
		return sysService.getBindAccountsByList();
	}

	@RequestMapping("/doBindAccounts")
	public ResponseEntity<Object> doBindAccounts(String userId,
			String accountIdsStr)
	{
		ResponseEntity<Object> res = null;
		try
		{
			String[] arr = new String[] {};
			if (!StringUtils.isBlank(accountIdsStr))
			{
				arr = accountIdsStr.split(",");
			}
			sysService.doBindAccounts(userId, Arrays.asList(arr));
			res = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("绑定账号失败", e);
			res = new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}

	@RequestMapping("/setting")
	public ResponseEntity<Object> sysSetting(String settingJson)
			throws Exception
	{
		ResponseEntity<Object> res = null;
		try
		{
			String result = sysService.sysSetting(settingJson);
			res = new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (Exception e)
		{
			res = new ResponseEntity<Object>(e, HttpStatus.EXPECTATION_FAILED);
			logger.error("保存系统设置失败", e);
		}
		return res;
	}
}
