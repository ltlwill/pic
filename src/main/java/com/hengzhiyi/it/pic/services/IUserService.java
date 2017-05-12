package com.hengzhiyi.it.pic.services;

import java.util.List;

import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.vo.PagedVO;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 系统用户服务接口
 * 
 * @author liutianlong
 *
 */
public interface IUserService
{

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return
	 */
	User getUserById(String id) throws BusinessException;

	/**
	 * 根据用户名密码获取用户
	 * 
	 * @param name
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	User getUserByNamePwd(String name, String pwd) throws BusinessException;

	/**
	 * 根据用户名和密码验证用户是否存在,true:有效用户,false:无效用户
	 * 
	 * @param name
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	boolean validateUser(String name, String pwd) throws BusinessException;

	/**
	 * 分页查询账号信息
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	PagedVO<List<User>> getUserList(PagedVO<?> params,User user) throws BusinessException;

	/**
	 * 编辑用户（新增或修改）
	 * 
	 * @param user
	 * @throws BusinessException
	 */
	public void editUser(User user) throws BusinessException;

	/**
	 * 删除用户
	 * 
	 * @param ids
	 * @throws BusinessException
	 */
	void delUserByIds(List<String> ids) throws BusinessException;

	/**
	 * 修改用户的密码
	 * 
	 * @param userId
	 * @param pwd
	 * @throws BusinessException
	 */
	void updateUserPwd(String userId, String pwd) throws BusinessException;
}
