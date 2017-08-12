package com.sf.energy.prepayment.model;

public class ByBetweenDetailModel
{
	String date = "";
	float money = 0;
	int times = 0;
	String induce = "";
	String starDate = "";
	String endDate = "";

	public String getStarDate()
	{
		return starDate;
	}

	public void setStarDate(String starDate)
	{
		this.starDate = starDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public float getMoney()
	{
		return money;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getInduce()
	{
		return induce;
	}

	public void setInduce(String induce)
	{
		this.induce = induce;
	}

	public void setMoney(float money)
	{
		this.money = money;
	}

	public int getTimes()
	{
		return times;
	}

	public void setTimes(int times)
	{
		this.times = times;
	}

	@Override
	public String toString()
	{
		return "ByBetweenDetailModel [date=" + date + ", money=" + money + ", times=" + times + ", induce=" + induce + ", starDate=" + starDate
				+ ", endDate=" + endDate + "]";
	}
}
