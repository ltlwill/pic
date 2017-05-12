package com.hengzhiyi.it.pic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hengzhiyi.it.pic.vo.SysSettingsVO;

@Repository
public interface ISysSettingsDao
{
	void add(SysSettingsVO vo);
	
	void update(SysSettingsVO vo);
	
	List<SysSettingsVO> getAll();
}
