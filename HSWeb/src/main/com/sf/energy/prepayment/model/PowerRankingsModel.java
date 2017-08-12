package com.sf.energy.prepayment.model;

public class PowerRankingsModel
{
	// /用电排名
	private String rank = "";
	// /建筑名称
	private String Architecture_Name = "";
	// /楼层
	private String FloorName = "";
	// /房间
	private String AmMeter_Name = "";
	// /电费类型
	private String Price_Name = "";
	// /用电量
	private String ZGross = "";
	// /金额
	private String ZMoney = "";

	public String getRank()
	{
		return rank;
	}

	public void setRank(String rank)
	{
		this.rank = rank;
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

	public String getPrice_Name()
	{
		return Price_Name;
	}

	public void setPrice_Name(String price_Name)
	{
		Price_Name = price_Name;
	}

	public String getZGross()
	{
		return ZGross;
	}

	public void setZGross(String zGross)
	{
		ZGross = zGross;
	}

	public String getZMoney()
	{
		return ZMoney;
	}

	public void setZMoney(String zMoney)
	{
		ZMoney = zMoney;
	}
}
