package com.sf.energy.project.equipment.model;

public class GatherToDataSiteModel
{

	// /数据中转站ID
	private int dataSiteID;
	// /数据中转站IP
	private String dataSiteIP = "";
	// /数据网关地址
	private String gatherAddress = "";
	// /数据中转站当前状态
	private int dataSiteState;

	public int getDataSiteID()
	{
		return dataSiteID;
	}

	public void setDataSiteID(int dataSiteID)
	{
		this.dataSiteID = dataSiteID;
	}

	public String getDataSiteIP()
	{
		return dataSiteIP;
	}

	public void setDataSiteIP(String dataSiteIP)
	{
		this.dataSiteIP = dataSiteIP;
	}

	public String getGatherAddress()
	{
		return gatherAddress;
	}

	public void setGatherAddress(String gatherAddress)
	{
		this.gatherAddress = gatherAddress;
	}

	public int getDataSiteState()
	{
		return dataSiteState;
	}

	public void setDataSiteState(int dataSiteState)
	{
		this.dataSiteState = dataSiteState;
	}

}
