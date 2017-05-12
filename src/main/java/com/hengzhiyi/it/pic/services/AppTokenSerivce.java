package com.hengzhiyi.it.pic.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.ehcache.Element;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.dao.IAppTokenDao;
import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.tools.EhCacheManager;
import com.hengzhiyi.it.pic.vo.AppTokenVO;
import com.hengzhiyi.it.pic.vo.User;

@Service
public class AppTokenSerivce implements IAppTokenService
{
	private final static Logger logger = Logger
			.getLogger(AppTokenSerivce.class);

	@Autowired
	private IAppTokenDao appTokenDao;

	@Autowired
	private IUserService userService;

	@Autowired
	private ISysService sysService;

	/**
	 * 每次请求时的验证
	 */
	@Override
	public boolean validateToken(String token, String deviceId)
	{
		if (StringUtils.isBlank(token) || StringUtils.isBlank(deviceId))
		{
			return false;
		}
		AppTokenVO tokenVo = null;
		// 从缓存中获取
		Element ele = EhCacheManager.get(token);
		// 缓存中没有，从数据库中获取
		if (ele != null)
		{
			tokenVo = (AppTokenVO) ele.getValue();
		} else
		{
			tokenVo = appTokenDao.getTokenByTokenStr(token);
		}
		if (tokenVo == null || AppTokenVO.Status.INVALID == tokenVo.getStatus())
		{
			EhCacheManager.remove(token);
			return false;
		}
		// 设备是否匹配
		if (!deviceId.equals(tokenVo.getDeviceId()))
		{
			return false;
		}
		long current = Calendar.getInstance().getTimeInMillis();
		Date loginDate = tokenVo.getLoginTime();
		// 如果上次登录时间到现在超过最大的天数时，则需要重新登录
		if ((current - loginDate.getTime()) > (Constants.TOKEN_MAX_USABLE_DAYS * 24l * 60l * 60l * 1000l))
		{
			// 设置为无效
			appTokenDao
					.updateStatusByTokenStr(token, AppTokenVO.Status.INVALID);
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Object> appUserLogin(User user, String deviceid)
			throws BusinessException
	{
		Map<String, Object> result = new HashMap<String, Object>();
		if (user == null || StringUtils.isBlank(user.getUserName())
				|| StringUtils.isBlank(user.getPassword()))
		{
			throw new BusinessException("登录失败");
		}
		String token = null;
		try
		{
			boolean validResult = userService.validateUser(user.getUserName(),
					user.getPassword());
			if (!validResult)
			{
				result.put("resultCode",
						Constants.ErrorCode.USER_NAME_PWD_ERROR);
			} else
			{
				user = userService.getUserByNamePwd(user.getUserName(),
						user.getPassword());
				// 验证登录时间
				boolean validateTime = sysService.validateLoginTime(user);
				// 非工作时间登录限制
				if (!validateTime)
				{
					result.put("resultCode",
							Constants.ErrorCode.NON_WORKIING_TIME);
				} else
				{
					user.setPassword(null); // 置空密码数据
					token = doTokenBusinesss(user, deviceid);
					result.put("resultCode", Constants.ErrorCode.SUCCESS);
					result.put("user", user);
					result.put("token", token);
				}
			}
		} catch (Exception e)
		{
			result.put("resultCode", Constants.ErrorCode.UNKNOW);
			throw new BusinessException(e);
		}
		return result;
	}

	private String doTokenBusinesss(User user, String deviceid)
			throws Exception
	{
		// 创建一个token串
		String token = UUID.randomUUID() + Constants.Split.COMMON_SPLIT
				+ user.getUserId();
		// 在数据库中查询token
		AppTokenVO tokenVo = appTokenDao.getTokenByUserId(user.getUserId());
		boolean isUpdate = true;
		if (tokenVo == null)
		{
			isUpdate = false;
			tokenVo = new AppTokenVO();
		}
		tokenVo.setDeviceId(deviceid);
		tokenVo.setUserId(user.getUserId());
		tokenVo.setToken(token);
		tokenVo.setStatus(AppTokenVO.Status.VALID);
		tokenVo.setLoginTime(new Date());
		if (isUpdate)
		{
			// 更新
			appTokenDao.updateToken(tokenVo);
		} else
		{
			// 保存
			appTokenDao.add(tokenVo);
		}
		// 将token信息放入缓存
		EhCacheManager.put(token, tokenVo);
		return token;
	}

	@Override
	public void appUserLoginOut(String token, String deviceid)
			throws BusinessException
	{
		if (StringUtils.isBlank(token))
		{
			throw new BusinessException("退出登录失败");
		}
		AppTokenVO tokenVO = appTokenDao.getTokenByTokenStr(token);
		if (tokenVO == null)
		{
			return;
		}
		// 更新数据库中的token状态为不可用
		appTokenDao.updateStatusByTokenStr(token, AppTokenVO.Status.INVALID);
		// 从缓存中移除
		EhCacheManager.remove(token);
	}

	@Override
	public void updateUserPwd(String userId, String pwd)
			throws BusinessException
	{
		try
		{
			userService.updateUserPwd(userId, pwd);
		} catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	@Override
	public Map<String, Object> refreshTokenInfo(String token)
			throws BusinessException
	{
		if (StringUtils.isBlank(token))
		{
			throw new BusinessException("非法的参数", Constants.ErrorCode.UNKNOW);
		}
		Element ele = EhCacheManager.get(token);
		AppTokenVO vo = null;
		if (ele == null)
		{
			vo = appTokenDao.getTokenByTokenStr(token);
		} else
		{
			vo = (AppTokenVO) ele.getValue();
		}
		if (vo == null)
		{
			throw new BusinessException("根据token=" + token + "；没有找到对应的信息",
					Constants.ErrorCode.UNKNOW);
		}
		vo.setLoginTime(new Date());
		// 跟新数据库中的token
		appTokenDao.updateToken(vo);
		// 跟新缓存中的token
		EhCacheManager.put(token, vo);
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.getUserById(vo.getUserId());
		map.put("user", user);
		boolean flag = false;
		try
		{
			// 验证登录时间限制
			flag = sysService.validateLoginTime(user);
		} catch (Exception e)
		{
			logger.error("刷新app的token信息时，验证登录时间出错", e);
		}
		map.put("validateResult", flag);
		String settings = null;
		try
		{
			settings = sysService.getSysSetting();
		} catch (Exception e)
		{
			logger.error("刷新app的token信息时，获取系统设置信息出错", e);
		}
		map.put("sysSettings", settings);
		return map;
	}
}
