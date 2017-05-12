package com.hengzhiyi.it.pic.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户实体类
 * 
 * @author liutianlong
 *
 */
public class User extends BaseVO
{

	private static final long serialVersionUID = 139158407468795177L;

	// 用户ID（为了和WebUploader自带的参数id区别）
	private String userId;
	// 用户名（为了和WebUploader自带的参数name区别）
	private String userName;
	// 年龄
	private int age;
	// 密码
	private String password;
	// 地址
	private String address;
	// 电话
	private String phone;
	// 电子邮箱
	private String email;
	// 昵称
	private String nickName;
	// 创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	// 状态
	private Integer status;
	// 密码状态
	private Integer pwdStatus;
	// 用户类型
	private Integer userType;
	// 白名单类型
	private Integer whiteListType;

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

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Integer getPwdStatus()
	{
		return pwdStatus;
	}

	public void setPwdStatus(Integer pwdStatus)
	{
		this.pwdStatus = pwdStatus;
	}

	public Integer getUserType()
	{
		return userType;
	}

	public void setUserType(Integer userType)
	{
		this.userType = userType;
	}

	public Integer getWhiteListType()
	{
		return whiteListType;
	}

	public void setWhiteListType(Integer whiteListType)
	{
		this.whiteListType = whiteListType;
	}

	@Override
	public String toString()
	{
		return "id=" + this.id + ";userId=" + this.userId + ";userName="
				+ this.userName + ";age=" + this.age + ";address="
				+ this.address + ";phone=" + this.phone + ";email="
				+ this.email + ";nickName=" + this.nickName + ";createTime=" + this.createTime
				+ ";status=" + this.status + ";pwdStatus=" + this.pwdStatus
				+ ";userType=" + this.userType;
	}
	
	public final static class Status
	{
		public final static int ENABLED = 1;   // 启用
		public final static int DISABLED = 0;  // 禁用
	}
	
	public final static class PwdStatus
	{
		public final static int INIT = 0;      // 初始状态   
		public final static int NOT_INIT = 1;  // 非初始状态
	}
	
	public final static class UserType
	{
		public final static int ADMIN = 0;     // 管理员
		public final static int SUB_ADMIN = 1; // 子管理员 
		public final static int NORMAL = 2;    // 一般人员
	}
	public final static class WhiteListType
	{
		public final static int WHITE_LIST = 1;    // 白名单
		public final static int NOT_WHITE_LIST = 0;// 非白名单
	}
}





