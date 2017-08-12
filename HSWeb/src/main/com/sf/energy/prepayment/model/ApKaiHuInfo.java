package com.sf.energy.prepayment.model;

import java.util.Date;

public class ApKaiHuInfo
{
    // 开户信息ID
    int ID = 0;

    // 区域名
    String areaName = null;

    // 建筑名
    String archName = null;

    // 楼层
    int floor = 0;

    // 电表ID
    int Ammeter_ID = 0;

    // 电表名（房间名）
    String Ammeter_Name = null;

    // 开户时间
    Date theTime = new Date();

    // 旧表剩余电量
    float oldsy = 0;
    
    // 旧表剩余金额 zxm 20150721
    float oldmoneyleft = 0;

    // 新增总用量
    float newzValue = 0;

    // 剩余电量
    float syValue = 0;
    // 新表剩余金额 zxm 20150721
    float newmoneyleft = 0;

    // 透支电量
    float tzValue = 0;

    // 透支金额
    float theMoney = 0;

    // 用户ID
    int userID = 0;

    // 用户名
    String userName = null;

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int iD)
    {
        ID = iD;
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

    public int getFloor()
    {
        return floor;
    }

    public void setFloor(int floor)
    {
        this.floor = floor;
    }

    public int getAmmeter_ID()
    {
        return Ammeter_ID;
    }

    public void setAmmeter_ID(int ammeter_ID)
    {
        Ammeter_ID = ammeter_ID;
    }

    public String getAmmeter_Name()
    {
        return Ammeter_Name;
    }

    public void setAmmeter_Name(String ammeter_Name)
    {
        Ammeter_Name = ammeter_Name;
    }

    public Date getTheTime()
    {
        return theTime;
    }

    public void setTheTime(Date theTime)
    {
        this.theTime = theTime;
    }

    public float getOldsy()
    {
        return oldsy;
    }
    public float getOldmoneyleft()//zxm 20150721
    {
    	return oldmoneyleft;
    }

    public void setOldsy(float oldsy)
    {
        this.oldsy = oldsy;
    }

    public float getNewzValue()
    {
        return newzValue;
    }

    public void setNewzValue(float newzValue)
    {
        this.newzValue = newzValue;
    }

    public float getSyValue()
    {
        return syValue;
    }
    public float getNewmoneyleft()//zxm 20150721
    {
    	return newmoneyleft;
    }

    public void setSyValue(float syValue)
    {
        this.syValue = syValue;
    }

    public float getTzValue()
    {
        return tzValue;
    }

    public void setTzValue(float tzValue)
    {
        this.tzValue = tzValue;
    }

    public float getTheMoney()
    {
        return theMoney;
    }

    public void setTheMoney(float theMoney)
    {
        this.theMoney = theMoney;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

}
