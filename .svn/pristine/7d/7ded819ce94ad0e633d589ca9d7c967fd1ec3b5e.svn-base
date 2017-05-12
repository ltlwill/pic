package com.hengzhiyi.it.pic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hengzhiyi.it.pic.vo.PagedVO;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 用户DAO
 * 
 * @author liutianlong
 *
 */
@Repository
public interface IUserDao
{
	/**
	 * 新增用户
	 * 
	 * @param user
	 */
	void addUser(User user);

	/**
	 * 修改用户
	 * 
	 * @param user
	 */
	void editUser(User user);

	/**
	 * 根据用户名和密码获取用户
	 * 
	 * @param name
	 * @param pwd
	 * @return
	 */
	User getUserByNamePwd(String name, String pwd);

	/**
	 * 根据用户ID获取用户
	 * 
	 * @param id
	 * @return
	 */
	User getUserById(String id);

	/**
	 * 分页查询账号信息
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<User> getUserList(PagedVO<?> params);

	/**
	 * 查询用户总记录数
	 * 
	 * @param params
	 * @return
	 */
	int getUserListCount(PagedVO<?> params);

	/**
	 * 根据账号名称获取用户
	 * 
	 * @param name
	 * @return
	 */
	User getUserByName(String name);

	/**
	 * 根据ID批量删除账号
	 * 
	 * @param ids
	 */
	void delUserByIds(List<String> ids);

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param pwd
	 */
	void updatePwdById(String userId, String pwd);

}
