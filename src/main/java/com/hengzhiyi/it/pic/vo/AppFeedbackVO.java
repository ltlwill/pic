package com.hengzhiyi.it.pic.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AppFeedbackVO extends BaseVO
{
	private static final long serialVersionUID = 1L;
	
	private String question;    // 反馈的问题
	private String contactType; // 联系方式（1：微信，2：QQ，3：邮箱）
	private String contact;     // 联系方式内容
	private String score;       // 应用评分（1到5分）
	private String platform;    // 操作系统（android，ios）
	private String osVersion;   // 操作系统版本号
	private String appId;       // 应用唯一标示
	private String appVersion;  // 应用的版本号
	private String imei;        // 设备标识
	private String model;       // 设置型号
	private String plusVersion; // plus基座的版本号
	private String net;         // 网络类型
	private String userId;      // 用户ID
	private String userName;    // 用户名称
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;    // 反馈时间
	
	public String getQuestion()
	{
		return question;
	}
	public void setQuestion(String question)
	{
		this.question = question;
	}
	public String getContactType()
	{
		return contactType;
	}
	public void setContactType(String contactType)
	{
		this.contactType = contactType;
	}
	public String getContact()
	{
		return contact;
	}
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	public String getScore()
	{
		return score;
	}
	public void setScore(String score)
	{
		this.score = score;
	}
	public String getPlatform()
	{
		return platform;
	}
	public void setPlatform(String platform)
	{
		this.platform = platform;
	}
	public String getOsVersion()
	{
		return osVersion;
	}
	public void setOsVersion(String osVersion)
	{
		this.osVersion = osVersion;
	}
	public String getAppId()
	{
		return appId;
	}
	public void setAppId(String appId)
	{
		this.appId = appId;
	}
	public String getAppVersion()
	{
		return appVersion;
	}
	public void setAppVersion(String appVersion)
	{
		this.appVersion = appVersion;
	}
	public String getImei()
	{
		return imei;
	}
	public void setImei(String imei)
	{
		this.imei = imei;
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public String getPlusVersion()
	{
		return plusVersion;
	}
	public void setPlusVersion(String plusVersion)
	{
		this.plusVersion = plusVersion;
	}
	public String getNet()
	{
		return net;
	}
	public void setNet(String net)
	{
		this.net = net;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
}
