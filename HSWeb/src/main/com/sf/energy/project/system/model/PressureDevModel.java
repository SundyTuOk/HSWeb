package com.sf.energy.project.system.model;

public class PressureDevModel
{
	private int pressureDev_ID;
	private int area_ID;
	private String area_Name;
	private int gather_ID;
	private int pump_ID;
	private String pumpName;
	private String pre_485Address;
	private String pressureDev_Num;
	private String lasttime;
	private String pressureDev_Name;
	private int isoutpressureDev;
	private String isoutName;
	private String gather_Name;
	private int pressureDev_Port;

	
	public String getGather_Name()
	{
		return gather_Name;
	}
	public void setGather_Name(String gather_Name)
	{
		this.gather_Name = gather_Name;
	}
	public String getIsoutName()
	{
		return isoutName;
	}
	public void setIsoutName(String isoutName)
	{
		this.isoutName = isoutName;
	}
	public String getPumpName()
	{
		return pumpName;
	}
	public void setPumpName(String pumpName)
	{
		this.pumpName = pumpName;
	}
	public int getPressureDev_ID()
	{
		return pressureDev_ID;
	}
	public void setPressureDev_ID(int pressureDev_ID)
	{
		this.pressureDev_ID = pressureDev_ID;
	}
	public int getArea_ID()
	{
		return area_ID;
	}
	public void setArea_ID(int area_ID)
	{
		this.area_ID = area_ID;
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
		return gather_ID;
	}
	public void setGather_ID(int gather_ID)
	{
		this.gather_ID = gather_ID;
	}
	public int getPump_ID()
	{
		return pump_ID;
	}
	public void setPump_ID(int pump_ID)
	{
		this.pump_ID = pump_ID;
	}
	public String getPre_485Address()
	{
		return pre_485Address;
	}
	public void setPre_485Address(String pre_485Address)
	{
		this.pre_485Address = pre_485Address;
	}
	public String getPressureDev_Num()
	{
		return pressureDev_Num;
	}
	public void setPressureDev_Num(String pressureDev_Num)
	{
		this.pressureDev_Num = pressureDev_Num;
	}
	public String getLasttime()
	{
		return lasttime;
	}
	public void setLasttime(String lasttime)
	{
		this.lasttime = lasttime;
	}
	public String getPressureDev_Name()
	{
		return pressureDev_Name;
	}
	public void setPressureDev_Name(String pressureDev_Name)
	{
		this.pressureDev_Name = pressureDev_Name;
	}
	public int getIsoutpressureDev()
	{
		return isoutpressureDev;
	}
	public void setIsoutpressureDev(int isoutpressureDev)
	{
		this.isoutpressureDev = isoutpressureDev;
	}
	public int getPressureDev_Port()
	{
		return pressureDev_Port;
	}
	public void setPressureDev_Port(int pressureDev_Port)
	{
		this.pressureDev_Port = pressureDev_Port;
	}
	
	
}
