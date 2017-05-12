package com.hengzhiyi.it.pic.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * APP token实体类
 * 
 * @author liutianlong
 *
 */
public class AppTokenVO extends BaseVO
{
	private static final long serialVersionUID = 1L;
	
	// token
	private String token;
	// 用户ID
	private String userId;
	// 设备ID
	private String deviceId;
	// 状态
	private Integer status;
	// 登录时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date loginTime;
	
	public final static class Status
	{
		public final static int VALID = 1;   // 有效
		public final static int INVALID = 0; // 无效
	}
	
	public String getToken()
	{
		return token;
	}
	public void setToken(String token)
	{
		this.token = token;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getDeviceId()
	{
		return deviceId;
	}
	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}
	public Integer getStatus()
	{
		return status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	public Date getLoginTime()
	{
		return loginTime;
	}
	public void setLoginTime(Date loginTime)
	{
		this.loginTime = loginTime;
	}
}
