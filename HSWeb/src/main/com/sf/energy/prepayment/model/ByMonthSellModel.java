package com.sf.energy.prepayment.model;

public class ByMonthSellModel
{
    int queryYear = 0;
    int queryMonth = 0;
    String name = "";
    int day = 0;
    float dayCount = 0;

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public int getQueryYear()
    {
        return queryYear;
    }

    public void setQueryYear(int queryYear)
    {
        this.queryYear = queryYear;
    }

    public int getQueryMonth()
    {
        return queryMonth;
    }

    public void setQueryMonth(int queryMonth)
    {
        this.queryMonth = queryMonth;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getDayCount()
    {
        return dayCount;
    }

    public void setDayCount(float dayCount)
    {
        this.dayCount = dayCount;
    }
}
