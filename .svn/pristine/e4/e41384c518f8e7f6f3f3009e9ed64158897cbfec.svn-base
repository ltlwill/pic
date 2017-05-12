package com.hengzhiyi.it.pic.services;

import java.util.Map;

import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.vo.User;

/**
 * token管理接口
 * 
 * @author liutianlong
 *
 */
public interface IAppTokenService
{
	/**
	 * 验证token
	 * 
	 * @param token
	 * @param deviceId
	 * @return
	 */
	boolean validateToken(String token, String deviceId);

	/**
	 * APP登录
	 * 
	 * @param userO
	 * @param token
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> appUserLogin(User user, String deviceid)
			throws BusinessException;

	/**
	 * APP退出登录
	 * 
	 * @param token
	 * @param deviceid
	 * @return
	 * @throws BusinessException
	 */
	void appUserLoginOut(String token, String deviceid)
			throws BusinessException;

	/**
	 * APP 修改账户密码
	 * 
	 * @param userId
	 * @param pwd
	 */
	void updateUserPwd(String userId, String pwd) throws BusinessException;

	/**
	 * 更新token的信息（如：最后使用时间字段信息）
	 * 
	 * @param token
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> refreshTokenInfo(String token) throws BusinessException;

}
