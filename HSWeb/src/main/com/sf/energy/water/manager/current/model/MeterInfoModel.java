package com.sf.energy.water.manager.current.model;

public class MeterInfoModel
{
	private String Watermeter_ID=null;
	private String Watermeter_name=null;
	private String partment=null;
	private int isCheck;
	public String getWatermeter_ID()
	{
		return Watermeter_ID;
	}
	public void setWatermeter_ID(String Watermeter_ID)
	{
		this.Watermeter_ID = Watermeter_ID;
	}
	public String getWatermeter_name()
	{
		return Watermeter_name;
	}
	public void setWatermeter_name(String Watermeter_name)
	{
		this.Watermeter_name = Watermeter_name;
	}
	public String getPartment()
	{
		return partment;
	}
	public void setPartment(String partment)
	{
		this.partment = partment;
	}
	public int getIsCheck()
	{
		return isCheck;
	}
	public void setIsCheck(int isCheck)
	{
		this.isCheck = isCheck;
	}


}
