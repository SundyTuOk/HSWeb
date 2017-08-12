package com.sf.energy.welcome.Model;

public class BaoJingInfoModel
{
	private int BaoJingInfo_ID;
	private String BaoJingInfo_content;
	private String baoJingInfo_MoudleNum;
	private String baoJingInfo_Time;
	
	public int getBaoJingInfo_ID()
	{
		return BaoJingInfo_ID;
	}
	public void setBaoJingInfo_ID(int baoJingInfo_ID)
	{
		BaoJingInfo_ID = baoJingInfo_ID;
	}
	public String getBaoJingInfo_content()
	{
		return BaoJingInfo_content;
	}
	public void setBaoJingInfo_content(String baoJingInfo_content)
	{
		BaoJingInfo_content = baoJingInfo_content;
	}
	public String getBaoJingInfo_MoudleNum()
	{
		return baoJingInfo_MoudleNum;
	}
	public void setBaoJingInfo_MoudleNum(String baoJingInfo_MoudleNum)
	{
		this.baoJingInfo_MoudleNum = baoJingInfo_MoudleNum;
	}
	public String getBaoJingInfo_Time()
	{
		return baoJingInfo_Time;
	}
	public void setBaoJingInfo_Time(String baoJingInfo_Time)
	{
		this.baoJingInfo_Time = baoJingInfo_Time;
	}
}
