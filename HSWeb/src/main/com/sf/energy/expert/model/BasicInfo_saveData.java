package com.sf.energy.expert.model;

public class BasicInfo_saveData implements java.io.Serializable
{
	// /指标总分
	private int count;
	// /查找日期
	private String date;

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

}
