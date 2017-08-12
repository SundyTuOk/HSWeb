package com.sf.energy.project.system.model;

public class OnlineMeterModel
{
	// /设备名称
	private String meterName;
	// /表计类型
	private String meteStyleName;
	// /隶属机构
	private String partmentText;
	// /智能网关
	private String gatherName;
	// /通讯地址
	private String meter485Address;
	// /通讯时间
	private String lastTime;
	// /通讯状态
	private String meterState;
	
	private String gatherAddress;
	
	public String getGatherAddress()
	{
		return gatherAddress;
	}
	public void setGatherAddress(String gatherAddress)
	{
		this.gatherAddress = gatherAddress;
	}
	public String getGatherIP()
	{
		return gatherIP;
	}
	public void setGatherIP(String gatherIP)
	{
		this.gatherIP = gatherIP;
	}
	public String getGatherLASTTIME()
	{
		return gatherLASTTIME;
	}
	public void setGatherLASTTIME(String gatherLASTTIME)
	{
		this.gatherLASTTIME = gatherLASTTIME;
	}
	public String getGatherState()
	{
		return gatherState;
	}
	public void setGatherState(String gatherState)
	{
		this.gatherState = gatherState;
	}
	private String gatherIP;
	
	private String gatherLASTTIME;
	
	private String gatherState;
	
	public String getMeterName()
	{
		return meterName;
	}
	public void setMeterName(String meterName)
	{
		this.meterName = meterName;
	}
	public String getMeteStyleName()
	{
		return meteStyleName;
	}
	public void setMeteStyleName(String meteStyleName)
	{
		this.meteStyleName = meteStyleName;
	}
	public String getPartmentText()
	{
		return partmentText;
	}
	public void setPartmentText(String partmentText)
	{
		this.partmentText = partmentText;
	}
	public String getGatherName()
	{
		return gatherName;
	}
	public void setGatherName(String gatherName)
	{
		this.gatherName = gatherName;
	}
	public String getMeter485Address()
	{
		return meter485Address;
	}
	public void setMeter485Address(String meter485Address)
	{
		this.meter485Address = meter485Address;
	}
	public String getLastTime()
	{
		return lastTime;
	}
	public void setLastTime(String lastTime)
	{
		this.lastTime = lastTime;
	}
	public String getMeterState()
	{
		return meterState;
	}
	public void setMeterState(String meterState)
	{
		this.meterState = meterState;
	}


}
