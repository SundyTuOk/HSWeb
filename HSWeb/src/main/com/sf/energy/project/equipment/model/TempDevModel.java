package com.sf.energy.project.equipment.model;


public class TempDevModel
{
	private int TempDev_ID;
	private String TempDev_Name;
	private int Area_ID;
	private String area_Name;
	private int Gather_ID;
	private String gather_Name;
	private int VoltageTrans_ID;
	private String voltageTrans_Name;
	private String TempDev_485Address;
	private String LastTime;
	private String TempDev_Num;
	private int TempDev_Port;
	public int getTempDev_ID()
	{
		return TempDev_ID;
	}
	public void setTempDev_ID(int tempDev_ID)
	{
		TempDev_ID = tempDev_ID;
	}
	public String getTempDev_Name()
	{
		return TempDev_Name;
	}
	public void setTempDev_Name(String tempDev_Name)
	{
		TempDev_Name = tempDev_Name;
	}
	public int getArea_ID()
	{
		return Area_ID;
	}
	public void setArea_ID(int area_ID)
	{
		Area_ID = area_ID;
	}
	public String getArea_Name()
	{
		return area_Name;
	}
	public void setArea_Name(String area_Name)
	{
		this.area_Name = area_Name;
	}
	public int getGather_ID()
	{
		return Gather_ID;
	}
	public void setGather_ID(int gather_ID)
	{
		Gather_ID = gather_ID;
	}
	public String getGather_Name()
	{
		return gather_Name;
	}
	public void setGather_Name(String gather_Name)
	{
		this.gather_Name = gather_Name;
	}
	public int getVoltageTrans_ID()
	{
		return VoltageTrans_ID;
	}
	public void setVoltageTrans_ID(int voltageTrans_ID)
	{
		VoltageTrans_ID = voltageTrans_ID;
	}
	public String getVoltageTrans_Name()
	{
		return voltageTrans_Name;
	}
	public void setVoltageTrans_Name(String voltageTrans_Name)
	{
		this.voltageTrans_Name = voltageTrans_Name;
	}
	public String getTempDev_485Address()
	{
		return TempDev_485Address;
	}
	public void setTempDev_485Address(String tempDev_485Address)
	{
		TempDev_485Address = tempDev_485Address;
	}
	public String getLastTime()
	{
		return LastTime;
	}
	public void setLastTime(String lastTime)
	{
		LastTime = lastTime;
	}
	public String getTempDev_Num()
	{
		return TempDev_Num;
	}
	public void setTempDev_Num(String tempDev_Num)
	{
		TempDev_Num = tempDev_Num;
	}
	public int getTempDev_Port()
	{
		return TempDev_Port;
	}
	public void setTempDev_Port(int tempDev_Port)
	{
		TempDev_Port = tempDev_Port;
	}
}
