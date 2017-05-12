package com.hengzhiyi.it.pic.dao;

import org.springframework.stereotype.Repository;

import com.hengzhiyi.it.pic.vo.AppTokenVO;

@Repository
public interface IAppTokenDao
{
	void add(AppTokenVO token);

	AppTokenVO getTokenByUserId(String userId);

	AppTokenVO getTokenByTokenStr(String token);
	
	AppTokenVO getToken(AppTokenVO token);

	void deleteById(String id);
	
	void updateStatusByTokenStr(String token,Integer status);
	
	void updateToken(AppTokenVO tokenVO);
}
