package com.hengzhiyi.it.pic.vo;

import java.util.Date;

public class SysSettingsVO extends BaseVO
{
	private static final long serialVersionUID = 1L;
	
	private String content; 
	
	private Date updateTime;
	
	public SysSettingsVO()
	{
	}
	
	public SysSettingsVO(String content,Date updateTime)
	{
		this.content = content;
		this.updateTime = updateTime;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	
	

}
