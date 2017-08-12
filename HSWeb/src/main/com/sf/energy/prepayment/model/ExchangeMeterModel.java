package com.sf.energy.prepayment.model;

public class ExchangeMeterModel
{
	// /ID
	private int ID = 0;
	// /电表ID
	private int AmMeter_ID = 0;
	// /区域
	private String Area_Name = "";
	// /建筑
	private String Architecture_Name = "";
	// /楼层
	private String FloorName = "";
	// /房间
	private String AmMeter_Name = "";
	// /旧表通讯地址
	private String OldAddress = "";
	// /新表通讯地址
	private String NewAddress = "";
	// /旧表底数
	private float OldZValue = 0;
	// /新表底数
	private float NewZValue = 0;
	// /旧表剩余量(度)
	private float OldSYValue = 0;
	// /新表剩余量(度)
	private float NewSYValue = 0;
	// /总清
	private String isZQ = "";
	// /操作员
	private String Users_Name = "";
	// /换表时间
	private String insertTime = "";
	// / 操作员ID
	private int USERS_ID = 0;
	// /换表原因
	private String REASON = "";
	// /备注
	private String REMARK = "";

	// /修正
	private String isXZ = "";
	
	public String getREASON()
	{
		return REASON;
	}

	public void setREASON(String rEASON)
	{
		REASON = rEASON;
	}

	public String getREMARK()
	{
		return REMARK;
	}

	public void setREMARK(String rEMARK)
	{
		REMARK = rEMARK;
	}

	public int getUSERS_ID()
	{
		return USERS_ID;
	}

	public void setUSERS_ID(int uSERS_ID)
	{
		USERS_ID = uSERS_ID;
	}

	public int getID()
	{
		return ID;
	}

	public void setID(int iD)
	{
		ID = iD;
	}

	public int getAmMeter_ID()
	{
		return AmMeter_ID;
	}

	public void setAmMeter_ID(int amMeter_ID)
	{
		AmMeter_ID = amMeter_ID;
	}

	public String getArea_Name()
	{
		return Area_Name;
	}

	public void setArea_Name(String area_Name)
	{
		Area_Name = area_Name;
	}

	public String getArchitecture_Name()
	{
		return Architecture_Name;
	}

	public void setArchitecture_Name(String architecture_Name)
	{
		Architecture_Name = architecture_Name;
	}

	public String getFloorName()
	{
		return FloorName;
	}

	public void setFloorName(String floorName)
	{
		FloorName = floorName;
	}

	public String getAmMeter_Name()
	{
		return AmMeter_Name;
	}

	public void setAmMeter_Name(String amMeter_Name)
	{
		AmMeter_Name = amMeter_Name;
	}

	public String getOldAddress()
	{
		return OldAddress;
	}

	public void setOldAddress(String oldAddress)
	{
		OldAddress = oldAddress;
	}

	public String getNewAddress()
	{
		return NewAddress;
	}

	public void setNewAddress(String newAddress)
	{
		NewAddress = newAddress;
	}

	public float getOldZValue()
	{
		return OldZValue;
	}

	public void setOldZValue(float oldZValue)
	{
		OldZValue = oldZValue;
	}

	public float getNewZValue()
	{
		return NewZValue;
	}

	public void setNewZValue(float newZValue)
	{
		NewZValue = newZValue;
	}

	public float getOldSYValue()
	{
		return OldSYValue;
	}

	public void setOldSYValue(float oldSYValue)
	{
		OldSYValue = oldSYValue;
	}

	public float getNewSYValue()
	{
		return NewSYValue;
	}

	public void setNewSYValue(float newSYValue)
	{
		NewSYValue = newSYValue;
	}

	public String getIsZQ()
	{
		return isZQ;
	}

	public void setIsZQ(String isZQ)
	{
		this.isZQ = isZQ;
	}

	public String getUsers_Name()
	{
		return Users_Name;
	}

	public void setUsers_Name(String users_Name)
	{
		Users_Name = users_Name;
	}

	public String getInsertTime()
	{
		return insertTime;
	}

	public void setInsertTime(String insertTime)
	{
		this.insertTime = insertTime;
	}
	public String getIsXZ()
	{
		return isXZ;
	}

	public void setIsXZ(String isXZ)
	{
		this.isXZ = isXZ;
	}
}
