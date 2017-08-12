package com.sf.energy.prepayment.model;

public class APXiaohuModel
{
	// /ID
	private int id = 0;
	// /区域
	private String areaName = "";
	// /建筑
	private String architectureName = "";
	// /楼层
	private String floorName = "";
	// /房间
	private String amMeterName = "";
	// /销户时间
	private String theTime = "";
	// /退电金额
	private String theMoney = "";
	// /销户前剩余电量
	private String QSYValue = "";
	// /销户前电表示数
	private String QZValue = "";
	// /操作员
	private String userName = "";
	// /销户状态
	private String status = "";
	//电表id
	private int AmMeter_ID;
	

	public int getAmMeter_ID()
	{
		return AmMeter_ID;
	}

	public void setAmMeter_ID(int amMeter_ID)
	{
		AmMeter_ID = amMeter_ID;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getAreaName()
	{
		return areaName;
	}

	public void setAreaName(String areaName)
	{
		this.areaName = areaName;
	}

	public String getArchitectureName()
	{
		return architectureName;
	}

	public void setArchitectureName(String architectureName)
	{
		this.architectureName = architectureName;
	}

	public String getFloorName()
	{
		return floorName;
	}

	public void setFloorName(String floorName)
	{
		this.floorName = floorName;
	}

	public String getAmMeterName()
	{
		return amMeterName;
	}

	public void setAmMeterName(String amMeterName)
	{
		this.amMeterName = amMeterName;
	}

	public String getTheTime()
	{
		return theTime;
	}

	public void setTheTime(String theTime)
	{
		this.theTime = theTime;
	}

	public String getTheMoney()
	{
		return theMoney;
	}

	public void setTheMoney(String theMoney)
	{
		this.theMoney = theMoney;
	}

	public String getQSYValue()
	{
		return QSYValue;
	}

	public void setQSYValue(String qSYValue)
	{
		QSYValue = qSYValue;
	}

	public String getQZValue()
	{
		return QZValue;
	}

	public void setQZValue(String qZValue)
	{
		QZValue = qZValue;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
