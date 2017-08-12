package com.sf.energy.project.system.model;

public class DeptBillSetModel
{
	// /账套ID
	private int BILLID = 0;
	// /部门ID
	private int PARTMENT_ID = 0;
	// /账套名称
	private String BILLNAME = "";
	public int getBILLID()
	{
		return BILLID;
	}
	public void setBILLID(int bILLID)
	{
		BILLID = bILLID;
	}
	public int getPARTMENT_ID()
	{
		return PARTMENT_ID;
	}
	public void setPARTMENT_ID(int pARTMENT_ID)
	{
		PARTMENT_ID = pARTMENT_ID;
	}
	public String getBILLNAME()
	{
		return BILLNAME;
	}
	public void setBILLNAME(String bILLNAME)
	{
		BILLNAME = bILLNAME;
	}

}
