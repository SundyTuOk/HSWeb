package com.sf.energy.weixin.models;

public class billModel
{
	private String date;
	private int state;
	private String datas;
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public int getState()
	{
		return state;
	}
	public void setState(int state)
	{
		this.state = state;
	}
	public String getDatas()
	{
		return datas;
	}
	public void setDatas(String datas)
	{
		this.datas = datas;
	}
	@Override
	public String toString()
	{
		return "billModel [date=" + date + ", state=" + state + ", datas=" + datas + "]";
	}
	
}
