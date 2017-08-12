package com.sf.energy.prepayment.model;

public class ByDayDetailModel
{

    String name = "";
    float money = 0;
    int times = 0;
    String queryDate = "";

    public String getQueryDate()
    {
        return queryDate;
    }

    public void setQueryDate(String queryDate)
    {
        this.queryDate = queryDate;
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
