package com.sf.energy.project.system.model;

public class Users implements java.io.Serializable
{

	// /用户编号
	private int usersID;

	// /区域编号
	private int areaID;

	// /用户编号
	private String usersNum;

	// /用户姓名
	private String usersName;

	// /登录姓名
	private String usersLoginName;

	// /登录密码
	private String usersPassword;

	// /性别
	private String usersGender;

	// /生日
	private String usersBirth;

	// /照片
	private String usersPhoto;

	// /部门
	private String usersDepartment;

	// /电话1
	private String usersPhone;

	// /电话2
	private String usersPhone1;

	// /电话3
	private String usersPhone2;

	// /地址
	private String usersHomeAddress;

	// /用户当前状态
	private int userstateID;

	// /备注
	private String usersRemark;

	// /是否在线
	private int OnLine;

	// /上线IP
	private String IP;

	// /是否报警
	private int isAlarm;

	// /登录网址
	private String UrlHost;

	public int getUsersID()
	{
		return usersID;
	}

	public void setUsersID(int usersID)
	{
		this.usersID = usersID;
	}

	public int getAreaID()
	{
		return areaID;
	}

	public void setAreaID(int areaID)
	{
		this.areaID = areaID;
	}

	public String getUsersNum()
	{
		return usersNum;
	}

	public void setUsersNum(String usersNum)
	{
		this.usersNum = usersNum;
	}

	public String getUsersName()
	{
		return usersName;
	}

	public void setUsersName(String usersName)
	{
		this.usersName = usersName;
	}

	public String getUsersLoginName()
	{
		return usersLoginName;
	}

	public void setUsersLoginName(String usersLoginName)
	{
		this.usersLoginName = usersLoginName;
	}

	public String getUsersPassword()
	{
		return usersPassword;
	}

	public void setUsersPassword(String usersPassword)
	{
		this.usersPassword = usersPassword;
	}

	public String getUsersGender()
	{
		return usersGender;
	}

	public void setUsersGender(String usersGender)
	{
		this.usersGender = usersGender;
	}

	public String getUsersBirth()
	{
		return usersBirth;
	}

	public void setUsersBirth(String usersBirth)
	{
		this.usersBirth = usersBirth;
	}

	public String getUsersPhoto()
	{
		return usersPhoto;
	}

	public void setUsersPhoto(String usersPhoto)
	{
		this.usersPhoto = usersPhoto;
	}

	public String getUsersDepartment()
	{
		return usersDepartment;
	}

	public void setUsersDepartment(String usersDepartment)
	{
		this.usersDepartment = usersDepartment;
	}

	public String getUsersPhone()
	{
		return usersPhone;
	}

	public void setUsersPhone(String usersPhone)
	{
		this.usersPhone = usersPhone;
	}

	public String getUsersPhone1()
	{
		return usersPhone1;
	}

	public void setUsersPhone1(String usersPhone1)
	{
		this.usersPhone1 = usersPhone1;
	}

	public String getUsersPhone2()
	{
		return usersPhone2;
	}

	public void setUsersPhone2(String usersPhone2)
	{
		this.usersPhone2 = usersPhone2;
	}

	public String getUsersHomeAddress()
	{
		return usersHomeAddress;
	}

	public void setUsersHomeAddress(String usersHomeAddress)
	{
		this.usersHomeAddress = usersHomeAddress;
	}

	public int getUserstateID()
	{
		return userstateID;
	}

	public void setUserstateID(int userstateID)
	{
		this.userstateID = userstateID;
	}

	public String getUsersRemark()
	{
		return usersRemark;
	}

	public void setUsersRemark(String usersRemark)
	{
		this.usersRemark = usersRemark;
	}

	public int getOnLine()
	{
		return OnLine;
	}

	public void setOnLine(int onLine)
	{
		OnLine = onLine;
	}

	public String getIP()
	{
		return IP;
	}

	public void setIP(String iP)
	{
		IP = iP;
	}

	public int getIsAlarm()
	{
		return isAlarm;
	}

	public void setIsAlarm(int isAlarm)
	{
		this.isAlarm = isAlarm;
	}

	public String getUrlHost()
	{
		return UrlHost;
	}

	public void setUrlHost(String urlHost)
	{
		UrlHost = urlHost;
	}

}
