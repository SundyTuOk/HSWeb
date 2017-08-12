package com.sf.energy.manager.current.model;

/**
 * 集中监测Model
 * @author yanhao
 *
 */
public class CurrentManagerModel
{
	
	///电表ID
	private int ammID = 0;
	
	///电表名称
	private String ammName = "";
	
	///区域ID
	private int areaID=0;
	
	///区域名称
	private String areaName="";
	
	///建筑ID
	private int archID=0;
	
	///建筑名
	private String archName="";
	
	///当天用电
	private float dayGross = 0;
	
	///当天电费
	private float dayMoney=0;
	
	///当月用电
	private float monthGross=0;
	
	///当月电费
	private float monthMoney=0;
	
	///总电表个数
	private int totalAmm=0;
	
	///在线电表个数
	private int onlineAmm=0;
	
	///在线电表比例
	private String onlineRatio="";
	
	///当前功率
	private float curPower=0;
	
	///在线离线状态
	private int ammState = 0;
	
	///楼层号
	private int ammFloor= 0;

	
	
	
	
	
	public String getArchName()
	{
		return archName;
	}

	public void setArchName(String archName)
	{
		this.archName = archName;
	}

	public int getAmmID()
	{
		return ammID;
	}

	public void setAmmID(int ammID)
	{
		this.ammID = ammID;
	}

	public String getAmmName()
	{
		return ammName;
	}

	public void setAmmName(String ammName)
	{
		this.ammName = ammName;
	}

	public float getDayGross()
	{
		return dayGross;
	}

	public void setDayGross(float dayGross)
	{
		this.dayGross = dayGross;
	}

	public float getCurPower()
	{
		return curPower;
	}

	public void setCurPower(float curPower)
	{
		this.curPower = curPower;
	}

	public int getAmmState()
	{
		return ammState;
	}

	public void setAmmState(int ammState)
	{
		this.ammState = ammState;
	}

	public int getAmmFloor()
	{
		return ammFloor;
	}

	public void setAmmFloor(int ammFloor)
	{
		this.ammFloor = ammFloor;
	}

	public int getAreaID()
	{
		return areaID;
	}

	public void setAreaID(int areaID)
	{
		this.areaID = areaID;
	}

	public String getAreaName()
	{
		return areaName;
	}

	public void setAreaName(String areaName)
	{
		this.areaName = areaName;
	}

	public int getArchID()
	{
		return archID;
	}

	public void setArchID(int archID)
	{
		this.archID = archID;
	}

	public float getDayMoney()
	{
		return dayMoney;
	}

	public void setDayMoney(float dayMoney)
	{
		this.dayMoney = dayMoney;
	}

	public float getMonthGross()
	{
		return monthGross;
	}

	public void setMonthGross(float monthGross)
	{
		this.monthGross = monthGross;
	}

	public float getMonthMoney()
	{
		return monthMoney;
	}

	public void setMonthMoney(float monthMoney)
	{
		this.monthMoney = monthMoney;
	}

	public int getTotalAmm()
	{
		return totalAmm;
	}

	public void setTotalAmm(int totalAmm)
	{
		this.totalAmm = totalAmm;
	}

	public int getOnlineAmm()
	{
		return onlineAmm;
	}

	public void setOnlineAmm(int onlineAmm)
	{
		this.onlineAmm = onlineAmm;
	}

	public String getOnlineRatio()
	{
		return onlineRatio;
	}

	public void setOnlineRatio(String onlineRatio)
	{
		this.onlineRatio = onlineRatio;
	}
	
	
	

}
