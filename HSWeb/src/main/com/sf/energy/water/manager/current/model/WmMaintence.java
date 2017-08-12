package com.sf.energy.water.manager.current.model;

import java.util.Date;

public class WmMaintence
{
	int maintID = 0;
	int watermeterID = 0;
	String watermeterName = null;
	Date mainTime = null;
	String maintRemark = null;
	String maintMan = null;

	public String getWatermeterName()
	{
		return watermeterName;
	}

	public void setWatermeterName(String watermeterName)
	{
		this.watermeterName = watermeterName;
	}

	public int getWatermeterID()
	{
		return watermeterID;
	}

	public void setWatermeterID(int watermeterID)
	{
		this.watermeterID = watermeterID;
	}

	public int getMaintID()
	{
		return maintID;
	}

	public void setMaintID(int maintID)
	{
		this.maintID = maintID;
	}

	public Date getMainTime()
	{
		return mainTime;
	}

	public void setMainTime(Date mainTime)
	{
		this.mainTime = mainTime;
	}

	public String getMaintRemark()
	{
		return maintRemark;
	}

	public void setMaintRemark(String maintRemark)
	{
		this.maintRemark = maintRemark;
	}

	public String getMaintMan()
	{
		return maintMan;
	}

	public void setMaintMan(String maintMan)
	{
		this.maintMan = maintMan;
	}

}
