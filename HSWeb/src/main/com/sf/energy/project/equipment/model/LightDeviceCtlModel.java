package com.sf.energy.project.equipment.model;

public class LightDeviceCtlModel
{
    // 教室名称
    String lightRoomName = "";

    // 教室编号
    int LightRoomNo = 0;

    // 照明控制设备地址
    int DeviceNo = 0;

    // 网关ID
    int Gather_ID = 0;

    // 网关端口号
    int Gather_PortNo = 0;

    // 波特率
    int BaudRate = 9600;

    // 校验位
    char Parity = 'n';

    // 数据位
    int DataBit = 8;

    // 停止位
    int StopBit = 1;

    // 报文超时时间单位
    int TimeOutUnit = 0;

    // 报文超时时间值
    int TimeOutTime = 0;

    // 字节超时时间值
    int ByteTimeOutTime = 0;

    // 当前开关灯模式
    int SwitchMode = 0;

    // 照明灯路数
    int LineSum = 0;

    // 教室当前开灯路数
    int CurrentLineOnSum = 0;

    // 教室每路开灯人数
    int MaxPeoplePerLine = 0;

    // 教室当前人数
    int CurrentPeopleSum = 0;

    // 教室开灯光照度
    int SwitchOnLightValue = 0;

    // 教室当前光照度
    int CurrentLightValue = 0;

    // 设备状态
    int Stat = 0;

    // 离线报警
    int IsOffAlarm = 0;

    public String getLightRoomName()
    {
        return lightRoomName;
    }

    public void setLightRoomName(String lightRoomName)
    {
        this.lightRoomName = lightRoomName;
    }

    public int getLightRoomNo()
    {
        return LightRoomNo;
    }

    public void setLightRoomNo(int lightRoomNo)
    {
        LightRoomNo = lightRoomNo;
    }

    public int getDeviceNo()
    {
        return DeviceNo;
    }

    public void setDeviceNo(int deviceNo)
    {
        DeviceNo = deviceNo;
    }

    public int getGather_ID()
    {
        return Gather_ID;
    }

    public void setGather_ID(int gather_ID)
    {
        Gather_ID = gather_ID;
    }

    public int getGather_PortNo()
    {
        return Gather_PortNo;
    }

    public void setGather_PortNo(int gather_PortNo)
    {
        Gather_PortNo = gather_PortNo;
    }

    public int getBaudRate()
    {
        return BaudRate;
    }

    public void setBaudRate(int baudRate)
    {
        BaudRate = baudRate;
    }

    public char getParity()
    {
        return Parity;
    }

    public void setParity(char parity)
    {
        Parity = parity;
    }

    public int getDataBit()
    {
        return DataBit;
    }

    public void setDataBit(int dataBit)
    {
        DataBit = dataBit;
    }

    public int getStopBit()
    {
        return StopBit;
    }

    public void setStopBit(int stopBit)
    {
        StopBit = stopBit;
    }

    public int getTimeOutUnit()
    {
        return TimeOutUnit;
    }

    public void setTimeOutUnit(int timeOutUnit)
    {
        TimeOutUnit = timeOutUnit;
    }

    public int getTimeOutTime()
    {
        return TimeOutTime;
    }

    public void setTimeOutTime(int timeOutTime)
    {
        TimeOutTime = timeOutTime;
    }

    public int getByteTimeOutTime()
    {
        return ByteTimeOutTime;
    }

    public void setByteTimeOutTime(int byteTimeOutTime)
    {
        ByteTimeOutTime = byteTimeOutTime;
    }

    public int getSwitchMode()
    {
        return SwitchMode;
    }

    public void setSwitchMode(int switchMode)
    {
        SwitchMode = switchMode;
    }

    public int getLineSum()
    {
        return LineSum;
    }

    public void setLineSum(int lineSum)
    {
        LineSum = lineSum;
    }

    public int getCurrentLineOnSum()
    {
        return CurrentLineOnSum;
    }

    public void setCurrentLineOnSum(int currentLineOnSum)
    {
        CurrentLineOnSum = currentLineOnSum;
    }

    public int getMaxPeoplePerLine()
    {
        return MaxPeoplePerLine;
    }

    public void setMaxPeoplePerLine(int maxPeoplePerLine)
    {
        MaxPeoplePerLine = maxPeoplePerLine;
    }

    public int getCurrentPeopleSum()
    {
        return CurrentPeopleSum;
    }

    public void setCurrentPeopleSum(int currentPeopleSum)
    {
        CurrentPeopleSum = currentPeopleSum;
    }

    public int getSwitchOnLightValue()
    {
        return SwitchOnLightValue;
    }

    public void setSwitchOnLightValue(int switchOnLightValue)
    {
        SwitchOnLightValue = switchOnLightValue;
    }

    public int getCurrentLightValue()
    {
        return CurrentLightValue;
    }

    public void setCurrentLightValue(int currentLightValue)
    {
        CurrentLightValue = currentLightValue;
    }

    public int getStat()
    {
        return Stat;
    }

    public void setStat(int stat)
    {
        Stat = stat;
    }

    public int getIsOffAlarm()
    {
        return IsOffAlarm;
    }

    public void setIsOffAlarm(int isOffAlarm)
    {
        IsOffAlarm = isOffAlarm;
    }
}
