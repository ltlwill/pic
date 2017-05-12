package com.hengzhiyi.it.pic.services;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.dao.IUserDao;
import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.vo.PagedVO;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 系统用户服务实现类
 * 
 * @author liutianlong
 *
 */

@Service
public class UserService implements IUserService
{

	@Autowired
	private IUserDao userDao;

	@Override
	public User getUserById(String id) throws BusinessException
	{
		return userDao.getUserById(id);
	}

	@Override
	public User getUserByNamePwd(String name, String pwd) throws BusinessException
	{
		return userDao.getUserByNamePwd(name, pwd);
	}

	@Override
	public boolean validateUser(String name, String pwd) throws BusinessException
	{
		if (StringUtils.isBlank(name) || StringUtils.isBlank(pwd))
		{
			return false;
		}
		User user = userDao.getUserByNamePwd(name, pwd);
		return (user != null && Integer.valueOf(User.Status.ENABLED).equals(
				user.getStatus())) ? true : false;
	}

	@Override
	public PagedVO<List<User>> getUserList(PagedVO<?> params,User user) throws BusinessException
	{
		params.setParams(user == null ? new User() : user);
		// 获取当前页数据
		List<User> users = userDao.getUserList(params);
		// 获取总记录数
		int total = userDao.getUserListCount(params);
		PagedVO<List<User>> result = new PagedVO<List<User>>();
		// 设置数据
		result.setRows(users);
		result.setCurrPage(params.getCurrPage());
		result.setPageSize(params.getPageSize());
		result.setTotal(total);
		return result;
	}

	@Override
	public void editUser(User user) throws BusinessException
	{
		if (user == null)
		{
			throw new BusinessException("参数为空");
		}
		if (StringUtils.isBlank(user.getUserId())) // 新建
		{
			User _user = userDao.getUserByName(user.getUserName());
			if (_user != null)
			{
				throw new BusinessException("创建用户失败，账号已存在",
						Constants.ErrorCode.USER_EXISTS);
			}
			user.setCreateTime(new Date());
			user.setPwdStatus(User.PwdStatus.INIT);// 设置密码状态为初始化状态，则用户第一次登录后需要修改密码才能使用
			userDao.addUser(user);
		} else
		// 修改
		{
			user.setPwdStatus(User.PwdStatus.NOT_INIT);
			userDao.editUser(user);
		}
	}

	@Override
	public void delUserByIds(List<String> ids) throws BusinessException
	{
		if (ids == null || ids.isEmpty())
		{
			throw new BusinessException("删除账号失败，因参数为空！");
		}
		userDao.delUserByIds(ids);
	}

	@Override
	public void updateUserPwd(String userId, String pwd)
			throws BusinessException
	{
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(pwd))
		{
			throw new BusinessException("修改账号密码失败，参数为空");
		}
		userDao.updatePwdById(userId, pwd);
	}
}
