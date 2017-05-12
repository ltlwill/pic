package com.hengzhiyi.it.pic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hengzhiyi.it.pic.vo.AccountVO;
import com.hengzhiyi.it.pic.vo.PagedVO;

@Repository
public interface IAccountDao
{
	List<AccountVO> getAccountList(PagedVO<?> params);
	
	int getAccountListCount(PagedVO<?> params);
	
	void addAccount(AccountVO vo);
	
	void editAccount(AccountVO vo);
	
	void delAccountByIds(List<String> ids);
	
	AccountVO getAccontById(String id);
	
	List<AccountVO> getAccounts(AccountVO vo);
	
	List<AccountVO> getAllAccounts();
}
