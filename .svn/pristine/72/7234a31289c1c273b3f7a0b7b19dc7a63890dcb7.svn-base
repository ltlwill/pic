package com.hengzhiyi.it.pic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hengzhiyi.it.pic.vo.UserAccountVO;

@Repository
public interface IUserAccountDao
{
	void add(UserAccountVO vo);

	void addList(List<UserAccountVO> vos);

	List<UserAccountVO> getByUserId(String userId);

	List<String> getAccountIdsByUserId(String userId);

	void delByUserId(String userId);
}
