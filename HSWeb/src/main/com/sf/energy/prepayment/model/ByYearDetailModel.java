package com.sf.energy.prepayment.model;

public class ByYearDetailModel
{
    int queryYear = 0;
    String name = "";
    float money = 0;
    int times = 0;

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

    public float getMoney()
    {
        return money;
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
}
