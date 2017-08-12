package com.sf.energy.project.equipment.model;

import java.util.Date;

/**
 * 电能表【Ammeter】的实体类
 * 
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */
public class AmmeterModel
{
	// / 表计ID,标识
	private int ammeterID = 0;

	// / 表计的计量点
	private int ammeterPoint = 0;

	// / 区域ID
	private int areaID = 0;

	// / 建筑物ID
	private int architectureID = 0;

	// / 网关ID
	private int gatherID = 0;

	private String gatherAddress = "";

	// / 电表分类分项信息
	private String ammeterNum = "";

	// / 表计名称
	private String ammeterName = "";

	// / 表计密码
	private String ammeterPassword = "";

	// / 表计485地址
	private String ammeterAddress485 = "";

	// / 表计厂家
	private String maker = "";

	// / 表计厂家编号,型号
	private String makerCode = "";

	// / 资产号
	private String assetNo = "";

	// / 是否供电
	private int isSupply = 0;

	// / 总电量
	private double zValue = 0;

	// / 尖电量
	private double jValue = 0;

	// / 峰电量
	private double fValue = 0;

	// / 平电量
	private double pValue = 0;

	// / 谷电量
	private double gValue = 0;

	// / 用电性质，教学，生活，商业
	private int useAmStyle = 0;

	// / 用户编号
	private String consumerNum = "";

	// / 用户名称
	private String consumerName = "";

	// / 用户电话
	private String consumerPhone = "";

	// / 用户地址
	private String consumerAddress = "";

	// / 重点用户标志
	private int isImportantUser = 0;

	// / 是总表
	private int isCountMeter = 0;

	// / 纳入计量标志
	private int isComputation = 0;

	// / 相线
	private int ammeterPlose = 0;

	// / 部门
	private int partment = 0;

	// / 楼层
	private int floor = 0;

	// / 表计类型,三相、单相等
	private int meteStyleID = 0;

	// / 价格
	private int priceID = 1;

	// / 电表状态
	private int ammeterStat = 0;

	// / 离线报警
	private int isOffAlarm = 0;

	// / 当前功率
	private double curPower = 0;

	// / 是否检测匹配模型
	private int costCheck = 0;

	// / 是否检测待机模型
	private int standByCheck = 0;

	// / 是否检测无压无流状态
	private int isVCCheck = 0;

	// / 修正量
	private float xiuzheng = 0;

	// / 最后通讯时间
	//private String lastTime = new Date().toLocaleString();
	private String lastTime = "2000-01-01 00:00:00";//李嵘20150905 默认值不能为当前时间，刚导入就在线不合理

	// / 数据来源
	private int dataFrom = 0;

	// /倍率
	private int beilv = 1;

	// / 父表ID
	private int parentID = 0;

	private String parent_Name="";

	private String Apstate = "";

	private float LimitPart = 0;

	private float ApybValue = 0;

	private int ISzb = 0;

	private float ApManCount = 0;

	private float RoomManCount=0;

	// /区域名称
	private String areaName = "";

	// /建筑名称
	private String archName = "";

	// /网关名称
	private String gatherName = "";

	// /部门名称
	private String partmentName = "";

	// /用电分项名称
	private String useStyleName = "";// num中的第14位

	// /用电分项中的一级子项
	private String useStyleNameYJZX = "";// num中的第14、15位

	// /分项插入到num中的String值 A、B、C、D
	private String useStyleValue = "";

	// /分项一级子项插入到num中的String值 A1、B2、C1、D1
	private String useStyleValueYJZX = "";
	// /性质名称
	private String useAmStyleName = "";

	// /电表类型
	private String meterStyleName = "";

	// /合并之后的电表名称
	private String meterName = "";

	private int volBeilv=1;

	private int isRecycle = 0;
	
	private int colBeilv=1;

	public int getIsRecycle()
	{
		return isRecycle;
	}

	public void setIsRecycle(int isRecycle)
	{
		this.isRecycle = isRecycle;
	}

	public String getGatherAddress()
	{
		return gatherAddress;
	}

	public void setGatherAddress(String gatherAddress)
	{
		this.gatherAddress = gatherAddress;
	}

	public String getMeterName()
	{
		return meterName;
	}

	public void setMeterName(String meterName)
	{
		this.meterName = meterName;
	}

	public int getDataFrom()
	{
		return dataFrom;
	}

	public void setDataFrom(int dataFrom)
	{
		this.dataFrom = dataFrom;
	}

	public String getApstate()
	{
		return Apstate;
	}

	public void setApstate(String apstate)
	{
		Apstate = apstate;
	}

	public float getLimitPart()
	{
		return LimitPart;
	}

	public void setLimitPart(float limitPart)
	{
		LimitPart = limitPart;
	}

	public float getApybValue()
	{
		return ApybValue;
	}

	public void setApybValue(float apybValue)
	{
		ApybValue = apybValue;
	}

	public int getISzb()
	{
		return ISzb;
	}

	public void setISzb(int iSzb)
	{
		ISzb = iSzb;
	}

	public float getApManCount()
	{
		return ApManCount;
	}

	public void setApManCount(float apManCount)
	{
		ApManCount = apManCount;
	}

	public String getUseStyleNameYJZX()
	{
		return useStyleNameYJZX;
	}

	public void setUseStyleNameYJZX(String useStyleNameYJZX)
	{
		this.useStyleNameYJZX = useStyleNameYJZX;
	}

	public String getUseStyleValue()
	{
		return useStyleValue;
	}

	public void setUseStyleValue(String useStyleValue)
	{
		this.useStyleValue = useStyleValue;
	}

	public String getUseStyleValueYJZX()
	{
		return useStyleValueYJZX;
	}

	public void setUseStyleValueYJZX(String useStyleValueYJZX)
	{
		this.useStyleValueYJZX = useStyleValueYJZX;
	}

	public String getUseAmStyleName()
	{
		return useAmStyleName;
	}

	public void setUseAmStyleName(String useAmStyleName)
	{
		this.useAmStyleName = useAmStyleName;
	}

	public int getBeilv()
	{
		return beilv;
	}

	public void setBeilv(int beilv)
	{
		this.beilv = beilv;
	}

	public String getUseStyleName()
	{
		return useStyleName;
	}

	public void setUseStyleName(String useStyleName)
	{
		this.useStyleName = useStyleName;
	}

	public String getMeterStyleName()
	{
		return meterStyleName;
	}

	public void setMeterStyleName(String meterStyleName)
	{
		this.meterStyleName = meterStyleName;
	}

	public int getAmmeterID()
	{
		return ammeterID;
	}

	public void setAmmeterID(int ammeterID)
	{
		this.ammeterID = ammeterID;
	}

	public int getAmmeterPoint()
	{
		return ammeterPoint;
	}

	public void setAmmeterPoint(int areaPoint)
	{
		this.ammeterPoint = areaPoint;
	}

	public int getAreaID()
	{
		return areaID;
	}

	public void setAreaID(int areaID)
	{
		this.areaID = areaID;
	}

	public int getArchitectureID()
	{
		return architectureID;
	}

	public void setArchitectureID(int architectureID)
	{
		this.architectureID = architectureID;
	}

	public int getGatherID()
	{
		return gatherID;
	}

	public void setGatherID(int gatherID)
	{
		this.gatherID = gatherID;
	}

	public String getAmmeterNum()
	{
		return ammeterNum;
	}

	public void setAmmeterNum(String ammeterNum)
	{
		this.ammeterNum = ammeterNum;
	}

	public String getAmmeterName()
	{
		return ammeterName;
	}

	public void setAmmeterName(String ammeterName)
	{
		this.ammeterName = ammeterName;
	}

	public String getAmmeterPassword()
	{
		return ammeterPassword;
	}

	public void setAmmeterPassword(String ammeterPassword)
	{
		this.ammeterPassword = ammeterPassword;
	}

	public String getAmmeterAddress485()
	{
		return ammeterAddress485;
	}

	public void setAmmeterAddress485(String ammeterAddress485)
	{
		this.ammeterAddress485 = ammeterAddress485;
	}

	public String getMaker()
	{
		return maker;
	}

	public void setMaker(String maker)
	{
		this.maker = maker;
	}

	public String getMakerCode()
	{
		return makerCode;
	}

	public void setMakerCode(String makerCode)
	{
		this.makerCode = makerCode;
	}

	public String getAssetNo()
	{
		return assetNo;
	}

	public void setAssetNo(String assetNo)
	{
		this.assetNo = assetNo;
	}

	public int getIsSupply()
	{
		return isSupply;
	}

	public void setIsSupply(int isSupply)
	{
		this.isSupply = isSupply;
	}

	public double getzValue()
	{
		return zValue;
	}

	public void setzValue(double zValue)
	{
		this.zValue = zValue;
	}

	public double getjValue()
	{
		return jValue;
	}

	public void setjValue(double jValue)
	{
		this.jValue = jValue;
	}

	public double getfValue()
	{
		return fValue;
	}

	public void setfValue(double fValue)
	{
		this.fValue = fValue;
	}

	public double getpValue()
	{
		return pValue;
	}

	public void setpValue(double pValue)
	{
		this.pValue = pValue;
	}

	public double getgValue()
	{
		return gValue;
	}

	public void setgValue(double gValue)
	{
		this.gValue = gValue;
	}

	public int getUseAmStyle()
	{
		return useAmStyle;
	}

	public void setUseAmStyle(int useAmStyle)
	{
		this.useAmStyle = useAmStyle;
	}

	public String getConsumerNum()
	{
		return consumerNum;
	}

	public void setConsumerNum(String consumerNum)
	{
		this.consumerNum = consumerNum;
	}

	public String getConsumerName()
	{
		return consumerName;
	}

	public void setConsumerName(String consumerName)
	{
		this.consumerName = consumerName;
	}

	public String getConsumerPhone()
	{
		return consumerPhone;
	}

	public void setConsumerPhone(String consumerPhone)
	{
		this.consumerPhone = consumerPhone;
	}

	public String getConsumerAddress()
	{
		return consumerAddress;
	}

	public void setConsumerAddress(String consumerAddress)
	{
		this.consumerAddress = consumerAddress;
	}

	public int getIsImportantUser()
	{
		return isImportantUser;
	}

	public void setIsImportantUser(int isImportantUser)
	{
		this.isImportantUser = isImportantUser;
	}

	public int getIsCountMeter()
	{
		return isCountMeter;
	}

	public void setIsCountMeter(int isCountMeter)
	{
		this.isCountMeter = isCountMeter;
	}

	public int getIsComputation()
	{
		return isComputation;
	}

	public void setIsComputation(int isComputation)
	{
		this.isComputation = isComputation;
	}

	public int getAmmeterPlose()
	{
		return ammeterPlose;
	}

	public void setAmmeterPlose(int ammeterPlose)
	{
		this.ammeterPlose = ammeterPlose;
	}

	public int getPartment()
	{
		return partment;
	}

	public void setPartment(int partment)
	{
		this.partment = partment;
	}

	public int getFloor()
	{
		return floor;
	}

	public void setFloor(int floor)
	{
		this.floor = floor;
	}

	public int getMeteStyleID()
	{
		return meteStyleID;
	}

	public void setMeteStyleID(int meteStyleID)
	{
		this.meteStyleID = meteStyleID;
	}

	public int getPriceID()
	{
		return priceID;
	}

	public void setPriceID(int priceID)
	{
		this.priceID = priceID;
	}

	public int getAmmeterStat()
	{
		return ammeterStat;
	}

	public void setAmmeterStat(int ammeterStat)
	{
		this.ammeterStat = ammeterStat;
	}

	public int getIsOffAlarm()
	{
		return isOffAlarm;
	}

	public void setIsOffAlarm(int isOffAlarm)
	{
		this.isOffAlarm = isOffAlarm;
	}

	public double getCurPower()
	{
		return curPower;
	}

	public void setCurPower(double curPower)
	{
		this.curPower = curPower;
	}

	public int getCostCheck()
	{
		return costCheck;
	}

	public void setCostCheck(int costCheck)
	{
		this.costCheck = costCheck;
	}

	public int getStandByCheck()
	{
		return standByCheck;
	}

	public void setStandByCheck(int standByCheck)
	{
		this.standByCheck = standByCheck;
	}

	public float getXiuzheng()
	{
		return xiuzheng;
	}

	public void setXiuzheng(float xiuzheng)
	{
		this.xiuzheng = xiuzheng;
	}

	public String getLastTime()
	{
		return lastTime;
	}

	public void setLastTime(String lastTime)
	{
		this.lastTime = lastTime;
	}

	public int getParentID()
	{
		return parentID;
	}

	public void setParentID(int parentID)
	{
		this.parentID = parentID;
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

	public String getGatherName()
	{
		return gatherName;
	}

	public void setGatherName(String gatherName)
	{
		this.gatherName = gatherName;
	}

	public String getPartmentName()
	{
		return partmentName;
	}

	public void setPartmentName(String partmentName)
	{
		this.partmentName = partmentName;
	}

	public float getRoomManCount()
	{
		return RoomManCount;
	}

	public void setRoomManCount(float roomManCount)
	{
		RoomManCount = roomManCount;
	}

	public String getParent_Name()
	{
		return parent_Name;
	}

	public void setParent_Name(String parent_Name)
	{
		this.parent_Name = parent_Name;
	}
	public int getVolBeilv()
	{
		return volBeilv;
	}

	public void setVolBeilv(int volBeilv)
	{
		this.volBeilv = volBeilv;
	}

	public int getColBeilv()
	{
		return colBeilv;
	}

	public void setColBeilv(int colBeilv)
	{
		this.colBeilv = colBeilv;
	}

	public int getIsVCCheck()
	{
		return isVCCheck;
	}

	public void setIsVCCheck(int isVCCheck)
	{
		this.isVCCheck = isVCCheck;
	}
}
