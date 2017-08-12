package com.sf.energy.prepayment.model;

public class CMAreaModel
{
	//区域ID
	private int AreaID=0;
	
	//建筑ID
		private int Architecture_ID=0;
	// /区域或者建筑名称
	private String AreaOrArchName = "";
	// /当天售电量(千瓦时)
	private float DayAmGross = 0;
	// /当天售电金额(元)
	private float DayAmMoney = 0;
	// /当月售电量(千瓦时)
	private float MonthAmGross = 0;
	// /当月售电金额(元)
	private float MonthAmMoney = 0;
	// /预付费电表(个)
	private int MeterCount = 0;
	// /在线电表(个)
	private int UsingMeterCount = 0;
	// /断电电表(个)
	private int offMeterCount = 0;

	public String getAreaOrArchName()
	{
		return AreaOrArchName;
	}

	public void setAreaOrArchName(String areaOrArchName)
	{
		AreaOrArchName = areaOrArchName;
	}

	public float getDayAmGross()
	{
		return DayAmGross;
	}

	public void setDayAmGross(float dayAmGross)
	{
		DayAmGross = dayAmGross;
	}

	public float getDayAmMoney()
	{
		return DayAmMoney;
	}

	public void setDayAmMoney(float dayAmMoney)
	{
		DayAmMoney = dayAmMoney;
	}

	public float getMonthAmGross()
	{
		return MonthAmGross;
	}

	public void setMonthAmGross(float monthAmGross)
	{
		MonthAmGross = monthAmGross;
	}

	public float getMonthAmMoney()
	{
		return MonthAmMoney;
	}

	public void setMonthAmMoney(float monthAmMoney)
	{
		MonthAmMoney = monthAmMoney;
	}

	public int getMeterCount()
	{
		return MeterCount;
	}

	public void setMeterCount(int meterCount)
	{
		MeterCount = meterCount;
	}

	public int getUsingMeterCount()
	{
		return UsingMeterCount;
	}

	public void setUsingMeterCount(int usingMeterCount)
	{
		UsingMeterCount = usingMeterCount;
	}

	public int getOffMeterCount()
	{
		return offMeterCount;
	}

	public void setOffMeterCount(int offMeterCount)
	{
		this.offMeterCount = offMeterCount;
	}

	public int getAreaID()
	{
		return AreaID;
	}

	public void setAreaID(int areaID)
	{
		AreaID = areaID;
	}

	public int getArchitecture_ID()
	{
		return Architecture_ID;
	}

	public void setArchitecture_ID(int architecture_ID)
	{
		Architecture_ID = architecture_ID;
	}
	

}
