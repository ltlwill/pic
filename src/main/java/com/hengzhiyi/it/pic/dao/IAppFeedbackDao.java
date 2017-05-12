package com.hengzhiyi.it.pic.dao;

import org.springframework.stereotype.Repository;

import com.hengzhiyi.it.pic.vo.AppFeedbackVO;

@Repository
public interface IAppFeedbackDao
{
	void add(AppFeedbackVO vo);
}
