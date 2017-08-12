package com.sf.energy.water.manager.current.model;

public class BldBillSetWaterModel
{
	// /账套ID
	private int BillId = 0;
	// /区域或建筑ID
	private int BldOrArea_ID = 0;
	// /区域或建筑标识
	private int AOrBflg = 0;
	// /账套名称
	private String BillName = "";
	
	public int getBillId()
	{
		return BillId;
	}
	public void setBillId(int billId)
	{
		BillId = billId;
	}
	public int getBldOrArea_ID()
	{
		return BldOrArea_ID;
	}
	public void setBldOrArea_ID(int bldOrArea_ID)
	{
		BldOrArea_ID = bldOrArea_ID;
	}
	public int getAOrBflg()
	{
		return AOrBflg;
	}
	public void setAOrBflg(int aOrBflg)
	{
		AOrBflg = aOrBflg;
	}
	public String getBillName()
	{
		return BillName;
	}
	public void setBillName(String billName)
	{
		BillName = billName;
	}

}
