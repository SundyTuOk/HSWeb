package com.sf.energy.water.manager.current.model;

public class WaterStandByModel
{
	private String WatermeterID;
	private String startTime = "00:00";
	private String endTime = "24:00";
	private String lowLimit = "0";
	private String upperLimit = "0";
	private String lastCheckTime;
	private String isCheck = "-1";
	public String getWatermeterID()
	{
		return WatermeterID;
	}
	public void setWatermeterID(String watermeterID)
	{
		WatermeterID = watermeterID;
	}
	public String getStartTime()
	{
		return startTime;
	}
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}
	public String getEndTime()
	{
		return endTime;
	}
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
	public String getLowLimit()
	{
		return lowLimit;
	}
	public void setLowLimit(String lowLimit)
	{
		this.lowLimit = lowLimit;
	}
	public String getUpperLimit()
	{
		return upperLimit;
	}
	public void setUpperLimit(String upperLimit)
	{
		this.upperLimit = upperLimit;
	}
	public String getLastCheckTime()
	{
		return lastCheckTime;
	}
	public void setLastCheckTime(String lastCheckTime)
	{
		this.lastCheckTime = lastCheckTime;
	}
	public String getIsCheck()
	{
		return isCheck;
	}
	public void setIsCheck(String isCheck)
	{
		this.isCheck = isCheck;
	}

}
