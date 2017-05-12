package com.hengzhiyi.it.pic.services;

import java.util.List;
import java.util.Map;

import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.vo.AccountVO;
import com.hengzhiyi.it.pic.vo.PagedVO;
import com.hengzhiyi.it.pic.vo.User;

public interface ISysService
{
	/**
	 * 编辑账号信息（新增和修改）
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	void editAccount(AccountVO vo) throws BusinessException;

	/**
	 * 分业务获取账号
	 * 
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	PagedVO<List<AccountVO>> getAccountList(PagedVO<?> params)
			throws BusinessException;

	/**
	 * 根据账号ID获取账号
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	AccountVO getAccountById(String id) throws BusinessException;

	/**
	 * 批量删除账号
	 * 
	 * @param ids
	 * @throws BusinessException
	 */
	void delAccountByIds(List<String> ids) throws BusinessException;

	/**
	 * 根据用户ID获取其绑定的账号信息
	 * 
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	List<Map<String, Object>> getBindAccountsInfo(String userId)
			throws BusinessException;

	/**
	 * 获取所有账号分组后且排序后的数据
	 * 
	 * @return
	 * @throws BusinessException
	 */
	List<Map<String, Object>> getBindAccountsByList() throws BusinessException;

	/**
	 * 获取所有的账号
	 * 
	 * @return
	 * @throws BusinessException
	 */
	List<AccountVO> getAllAccounts() throws BusinessException;

	/**
	 * 获取所有的账号（按平台分组为Map集合，按平台名称、账号名称排序），
	 * 
	 * @return
	 * @throws BusinessException
	 */
	Map<String, List<AccountVO>> getAllAccounts4Group()
			throws BusinessException;

	/**
	 * 绑定账号
	 * 
	 * @param userId
	 * @param accountIds
	 * @throws BusinessException
	 */
	void doBindAccounts(String userId, List<String> accountIds)
			throws BusinessException;

	/**
	 * 系统设置
	 * 
	 * @param settingJson
	 * @return
	 * @throws Exception
	 */
	String sysSetting(String settingJson) throws Exception;

	/**
	 * 获取系统设置数据
	 * 
	 * @return
	 * @throws Exception
	 */
	String getSysSetting() throws Exception;

	/**
	 * 验证登录时间
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean validateLoginTime(User user) throws Exception;

}
