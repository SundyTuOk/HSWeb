package com.sf.energy.prepayment.model;

public class PullSwitchManagerModel
{
	///ID
	private int id = 0;
	///计划编号
	private String planNum = "";
	///计划名称
	private String planName = "";
	///拉闸
	private String laZha = "";
	///合闸
	private String heZha = "";
	///表计类型
	private String biaoJiType = "";
	///操作回路
	private String caoZuoHuiLu = "";
	///执行类型
	private String zhiXingType = "";
	///选中建筑/区域/电表ID
	private String choose = "";
	///备注
	private String beiZhu = "";
	private String lazhadate="";
	private String hezhadate="";
	public String getLazhadate()
	{
		return lazhadate;
	}
	public void setLazhadate(String lazhadate)
	{
		this.lazhadate = lazhadate;
	}
	public String getHezhadate()
	{
		return hezhadate;
	}
	public void setHezhadate(String hezhadate)
	{
		this.hezhadate = hezhadate;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getPlanNum()
	{
		return planNum;
	}
	public void setPlanNum(String planNum)
	{
		this.planNum = planNum;
	}
	public String getPlanName()
	{
		return planName;
	}
	public void setPlanName(String planName)
	{
		this.planName = planName;
	}
	
	public String getLaZha()
	{
		return laZha;
	}
	public void setLaZha(String laZha)
	{
		this.laZha = laZha;
	}
	public String getHeZha()
	{
		return heZha;
	}
	public void setHeZha(String heZha)
	{
		this.heZha = heZha;
	}
	public String getBiaoJiType()
	{
		return biaoJiType;
	}
	public void setBiaoJiType(String biaoJiType)
	{
		this.biaoJiType = biaoJiType;
	}
	public String getCaoZuoHuiLu()
	{
		return caoZuoHuiLu;
	}
	public void setCaoZuoHuiLu(String caoZuoHuiLu)
	{
		this.caoZuoHuiLu = caoZuoHuiLu;
	}
	public String getZhiXingType()
	{
		return zhiXingType;
	}
	public void setZhiXingType(String zhiXingType)
	{
		this.zhiXingType = zhiXingType;
	}
	public String getChoose()
	{
		return choose;
	}
	public void setChoose(String choose)
	{
		this.choose = choose;
	}
	public String getBeiZhu()
	{
		return beiZhu;
	}
	public void setBeiZhu(String beiZhu)
	{
		this.beiZhu = beiZhu;
	}
	

}
