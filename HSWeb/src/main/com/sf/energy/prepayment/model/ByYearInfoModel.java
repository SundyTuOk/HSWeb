package com.sf.energy.prepayment.model;

public class ByYearInfoModel
{
    int queryYear = 0;
    String name = "";
    String feeStyle = "";
    String sellStyle = "";
    double zMoney = 0;
    double zGross = 0;

    public double getzMoney()
	{
		return zMoney;
	}

	public void setzMoney(double zMoney)
	{
		this.zMoney = zMoney;
	}

	public double getzGross()
	{
		return zGross;
	}

	public void setzGross(double zGross)
	{
		this.zGross = zGross;
	}

	public int getQueryYear()
    {
        return queryYear;
    }

    public void setQueryYear(int queryYear)
    {
        this.queryYear = queryYear;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFeeStyle()
    {
        return feeStyle;
    }

    public void setFeeStyle(String feeStyle)
    {
        this.feeStyle = feeStyle;
    }

    public String getSellStyle()
    {
        return sellStyle;
    }

    public void setSellStyle(String sellStyle)
    {
        this.sellStyle = sellStyle;
    }

   
}
