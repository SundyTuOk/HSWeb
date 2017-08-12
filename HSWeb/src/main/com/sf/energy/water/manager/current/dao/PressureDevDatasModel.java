package com.sf.energy.water.manager.current.dao;

import java.util.Date;

public class PressureDevDatasModel
{
	// /压力计ID
	private int PressureDev_ID = 0;
	//压力计名称
	private String PressureDev_name="";
	
	// /电表数据记录时间
	private Date recordTimeDate = null;
	// /压力计示数
	private float PressureValue = 0;
	// /小时
	private int hour = 0;

	public int getPressureDev_ID()
	{
		return PressureDev_ID;
	}
	public void setPressureDev_ID(int pressureDev_ID)
	{
		PressureDev_ID = pressureDev_ID;
	}
	public String getPressureDev_name()
	{
		return PressureDev_name;
	}
	public void setPressureDev_name(String pressureDev_name)
	{
		PressureDev_name = pressureDev_name;
	}
	public Date getRecordTimeDate()
	{
		return recordTimeDate;
	}
	public void setRecordTimeDate(Date recordTimeDate)
	{
		this.recordTimeDate = recordTimeDate;
	}
	public float getPressureValue()
	{
		return PressureValue;
	}
	public void setPressureValue(float pressureValue)
	{
		PressureValue = pressureValue;
	}
	public int getHour()
	{
		return hour;
	}
	public void setHour(int hour)
	{
		this.hour = hour;
	}		
}
