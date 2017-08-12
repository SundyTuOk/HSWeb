package com.sf.energy.prepayment.model;

public class ExceptionModel
{
	int APCardErrorInfo_ID=0;
	String BuyTime="";
	String PosNum="";
	String CardManNum="";
	String CardManName="";
	String StudentNum="";
	String TheMoney="";
	String SaleInfoNum="";
	String AmMeter_Name="";
	String ErrorType="";
	String ExecState="";
	String Ammeter_ID="";
	
	public String getAmmeter_ID()
	{
		return Ammeter_ID;
	}
	public void setAmmeter_ID(String ammeter_ID)
	{
		Ammeter_ID = ammeter_ID;
	}
	public int getAPCardErrorInfo_ID()
	{
		return APCardErrorInfo_ID;
	}
	public void setAPCardErrorInfo_ID(int aPCardErrorInfo_ID)
	{
		APCardErrorInfo_ID = aPCardErrorInfo_ID;
	}
	public String getBuyTime()
	{
		return BuyTime;
	}
	public void setBuyTime(String buyTime)
	{
		BuyTime = buyTime;
	}
	public String getPosNum()
	{
		return PosNum;
	}
	public void setPosNum(String posNum)
	{
		PosNum = posNum;
	}
	public String getCardManNum()
	{
		return CardManNum;
	}
	public void setCardManNum(String cardManNum)
	{
		CardManNum = cardManNum;
	}
	public String getCardManName()
	{
		return CardManName;
	}
	public void setCardManName(String cardManName)
	{
		CardManName = cardManName;
	}
	public String getStudentNum()
	{
		return StudentNum;
	}
	public void setStudentNum(String studentNum)
	{
		StudentNum = studentNum;
	}
	public String getTheMoney()
	{
		return TheMoney;
	}
	public void setTheMoney(String theMoney)
	{
		TheMoney = theMoney;
	}
	public String getSaleInfoNum()
	{
		return SaleInfoNum;
	}
	public void setSaleInfoNum(String saleInfoNum)
	{
		SaleInfoNum = saleInfoNum;
	}
	public String getAmMeter_Name()
	{
		return AmMeter_Name;
	}
	public void setAmMeter_Name(String amMeter_Name)
	{
		AmMeter_Name = amMeter_Name;
	}
	public String getErrorType()
	{
		return ErrorType;
	}
	public void setErrorType(String errorType)
	{
		ErrorType = errorType;
	}
	public String getExecState()
	{
		return ExecState;
	}
	public void setExecState(String execState)
	{
		ExecState = execState;
	}
	
}
