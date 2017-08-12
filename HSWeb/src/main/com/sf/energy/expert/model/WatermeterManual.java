package com.sf.energy.expert.model;

import java.util.Date;

public class WatermeterManual
{
    int dataID = 0;
    int meterID = 0;
    String meterName = null;
    Date valueTime = null;
    int areaID = 0;
    String areaName = null;
    int archID = 0;
    String archName = null;
    float zValue = 0;
    float zGross = 0;
    float powerFactorz = 0;
    float zMoney = 0;
    Date insertTime = null;
    int userID = 0;
    String userName = null;

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getMeterName()
    {
        return meterName;
    }

    public void setMeterName(String meterName)
    {
        this.meterName = meterName;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public String getArchName()
    {
        return archName;
    }

    public void setArchName(String archName)
    {
        this.archName = archName;
    }

    public int getDataID()
    {
        return dataID;
    }

    public void setDataID(int dataID)
    {
        this.dataID = dataID;
    }

    public int getMeterID()
    {
        return meterID;
    }

    public void setMeterID(int meterID)
    {
        this.meterID = meterID;
    }

    public Date getValueTime()
    {
        return valueTime;
    }

    public void setValueTime(Date valueTime)
    {
        this.valueTime = valueTime;
    }

    public int getAreaID()
    {
        return areaID;
    }

    public void setAreaID(int areaID)
    {
        this.areaID = areaID;
    }

    public int getArchID()
    {
        return archID;
    }

    public void setArchID(int archID)
    {
        this.archID = archID;
    }

    public float getzValue()
    {
        return zValue;
    }

    public void setzValue(float zValue)
    {
        this.zValue = zValue;
    }

    public float getzGross()
    {
        return zGross;
    }

    public void setzGross(float zGross)
    {
        this.zGross = zGross;
    }

    public float getPowerFactorz()
    {
        return powerFactorz;
    }

    public void setPowerFactorz(float powerFactorz)
    {
        this.powerFactorz = powerFactorz;
    }

    public float getzMoney()
    {
        return zMoney;
    }

    public void setzMoney(float zMoney)
    {
        this.zMoney = zMoney;
    }

    public Date getInsertTime()
    {
        return insertTime;
    }

    public void setInsertTime(Date insertTime)
    {
        this.insertTime = insertTime;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }
}
