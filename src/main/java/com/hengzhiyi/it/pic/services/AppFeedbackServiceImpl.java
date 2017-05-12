package com.hengzhiyi.it.pic.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengzhiyi.it.pic.dao.IAppFeedbackDao;
import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.vo.AppFeedbackVO;

@Service
public class AppFeedbackServiceImpl implements IAppFeedbackService
{

	@Autowired
	private IAppFeedbackDao appFeedbackDao;
	
	@Override
	public void addFeedback(AppFeedbackVO vo) throws BusinessException
	{
		if(vo == null)
		{
			throw new BusinessException("非法的参数错误");
		}	
		if(vo.getCreateTime() == null)
		{
			vo.setCreateTime(new Date());
		}
		appFeedbackDao.add(vo);
	}

}
