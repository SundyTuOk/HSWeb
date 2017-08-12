package com.sf.energy.prepayment.model;

public class SaleDetailsModel
{
	// /电表ID
	private int AmMeter_ID = 0;
	// /电表485地址
	private String AmMeter_485 = "";
	// /售电单编号（售电信息ID）
	private String SALEINFONUM = "";
	// /订单编号
	private String ORDERNO = "";
	// /学号
	private String STUDENTID = "";
	// /建筑
	private String Architecture_Name = "";
	// /楼层
	private String FloorName = "";
	// /房间
	private String AmMeter_Name = "";
	// /售电类型
	private String KindName = "";
	// /电费类型ID
	private int Price_ID;
	// /电费类型
	private String Price_Name;
	// /下发状态
	private String status = "";
	// /购买时间
	private String BuyTime = "";
	// /单价
	private String Price = "";
	// /购买电量
	private String TheGross = "";
	// /购买金额
	private String TheMoney = "";
	// /下发时间
	private String SendTime = "";
	// /下发前剩余电量
	private String QSYValue = "";
	// /下发后剩余电量
	private String HSYValue = "";
	// /售电人
	private String Users_Name = "";
	// /售电人Id
	private int Users_Id = 1;
	// /操作
	private String strAction = "";
	
	//数据库表中sign字段，对于异常申述订单，sign字段储存异常申述操作时间
	private String sign;
	
	public int getAmMeter_ID()
	{
		return AmMeter_ID;
	}

	public void setAmMeter_ID(int amMeter_ID)
	{
		AmMeter_ID = amMeter_ID;
	}

	public String getSALEINFONUM()
	{
		return SALEINFONUM;
	}

	public void setSALEINFONUM(String sALEINFONUM)
	{
		SALEINFONUM = sALEINFONUM;
	}

	public String getSTUDENTID()
	{
		return STUDENTID;
	}

	public void setSTUDENTID(String sTUDENTID)
	{
		STUDENTID = sTUDENTID;
	}

	public String getArchitecture_Name()
	{
		return Architecture_Name;
	}

	public void setArchitecture_Name(String architecture_Name)
	{
		Architecture_Name = architecture_Name;
	}

	public String getFloorName()
	{
		return FloorName;
	}

	public void setFloorName(String floorName)
	{
		FloorName = floorName;
	}

	public String getAmMeter_Name()
	{
		return AmMeter_Name;
	}

	public void setAmMeter_Name(String amMeter_Name)
	{
		AmMeter_Name = amMeter_Name;
	}

	public String getKindName()
	{
		return KindName;
	}

	public void setKindName(String kindName)
	{
		KindName = kindName;
	}

	public int getPrice_ID()
	{
		return Price_ID;
	}

	public void setPrice_ID(int price_ID)
	{
		Price_ID = price_ID;
	}
	
	public String getPrice_Name()
	{
		return Price_Name;
	}

	public void setPrice_Name(String price_Name)
	{
		Price_Name = price_Name;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getBuyTime()
	{
		return BuyTime;
	}

	public void setBuyTime(String buyTime)
	{
		BuyTime = buyTime;
	}

	public String getPrice()
	{
		return Price;
	}

	public void setPrice(String price)
	{
		Price = price;
	}

	public String getTheGross()
	{
		return TheGross;
	}

	public void setTheGross(String theGross)
	{
		TheGross = theGross;
	}

	public String getTheMoney()
	{
		return TheMoney;
	}

	public void setTheMoney(String theMoney)
	{
		TheMoney = theMoney;
	}

	public String getSendTime()
	{
		return SendTime;
	}

	public void setSendTime(String sendTime)
	{
		SendTime = sendTime;
	}

	public String getQSYValue()
	{
		return QSYValue;
	}

	public void setQSYValue(String qSYValue)
	{
		QSYValue = qSYValue;
	}

	public String getHSYValue()
	{
		return HSYValue;
	}

	public void setHSYValue(String hSYValue)
	{
		HSYValue = hSYValue;
	}

	public String getUsers_Name()
	{
		return Users_Name;
	}

	public void setUsers_Name(String users_Name)
	{
		Users_Name = users_Name;
	}

	public String getStrAction()
	{
		return strAction;
	}

	public void setStrAction(String strAction)
	{
		this.strAction = strAction;
	}

	public String getORDERNO()
	{
		return ORDERNO;
	}

	public void setORDERNO(String oRDERNO)
	{
		ORDERNO = oRDERNO;
	}

	public String getAmMeter_485()
	{
		return AmMeter_485;
	}

	public void setAmMeter_485(String amMeter_485)
	{
		AmMeter_485 = amMeter_485;
	}

	public int getUsers_Id()
	{
		return Users_Id;
	}

	public void setUsers_Id(int users_Id)
	{
		Users_Id = users_Id;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

}
