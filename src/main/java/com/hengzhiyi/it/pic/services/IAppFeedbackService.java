package com.hengzhiyi.it.pic.services;

import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.vo.AppFeedbackVO;

public interface IAppFeedbackService
{
	/**
	 * 保存问题
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	void addFeedback(AppFeedbackVO vo) throws BusinessException;

}
