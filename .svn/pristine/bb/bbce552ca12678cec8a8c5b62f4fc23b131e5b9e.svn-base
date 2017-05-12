package com.hengzhiyi.it.pic.appcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengzhiyi.it.pic.base.BaseController;
import com.hengzhiyi.it.pic.services.IAppFeedbackService;
import com.hengzhiyi.it.pic.services.IAppTokenService;
import com.hengzhiyi.it.pic.vo.AppFeedbackVO;
import com.hengzhiyi.it.pic.vo.User;

@Controller
@RequestMapping("/app")
public class AppController extends BaseController
{
	@Autowired
	private IAppTokenService appTokenService;
	
	@Autowired
	private IAppFeedbackService appFeedbackService;

	@RequestMapping("/userLogin")
	public ResponseEntity<Object> userLogin(User user, String deviceid)
	{
		ResponseEntity<Object> res = null;
		try
		{
			Map<String, Object> result = appTokenService.appUserLogin(user,
					deviceid);
			res = new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("App用户登录失败", e);
			res = new ResponseEntity<Object>(e, HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}

	@RequestMapping("/userLoginOut")
	public ResponseEntity<Object> userLoginOut(String token, String deviceid)
	{
		ResponseEntity<Object> res = null;
		try
		{
			appTokenService.appUserLoginOut(token, deviceid);
			res = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("App退出登录失败", e);
			res = new ResponseEntity<Object>(e, HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}

	@RequestMapping("/updateUserPwd")
	public ResponseEntity<Object> updateUserPwd(String userId, String pwd)
	{
		ResponseEntity<Object> res = null;
		try
		{
			appTokenService.updateUserPwd(userId, pwd);
			res = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("App修改用户密码失败", e);
			res = new ResponseEntity<Object>(e, HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}

	@RequestMapping("/refreshTokenInfo")
	public ResponseEntity<Object> refreshTokenInfo(String token)
	{
		ResponseEntity<Object> res = null;
		try
		{
			Map<String, Object> map = appTokenService.refreshTokenInfo(token);
			res = new ResponseEntity<Object>(map, HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("App刷新token信息失败", e);
			res = new ResponseEntity<Object>(e, HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}
	
	@RequestMapping("/feedback")
	public ResponseEntity<Object> feedback(AppFeedbackVO vo)
	{
		ResponseEntity<Object> res = null;
		try
		{
			appFeedbackService.addFeedback(vo);
			res = new ResponseEntity<Object>( HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("App反馈问题失败", e);
			res = new ResponseEntity<Object>(e, HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	} 
	@RequestMapping("/getSysSettings")
	public ResponseEntity<Object> getSysSettings()
	{
		ResponseEntity<Object> res = null;
		try
		{
			String settings = sysService.getSysSetting();
			res = new ResponseEntity<Object>(settings,HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("App反馈问题失败", e);
			res = new ResponseEntity<Object>(e, HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	} 

}
