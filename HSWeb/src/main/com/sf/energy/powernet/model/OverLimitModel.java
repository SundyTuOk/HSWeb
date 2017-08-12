package com.sf.energy.powernet.model;

public class OverLimitModel
{
	// /越限ID
	private String PD_OverID = "";
	// /越限时间
	private String ValueTime = "";
	// /回路名称
	private String Part_Name = "";
	// /越限类型
	private String OverStyleName = "";
	// /抄读量
	private String ZValue = "";
	// /越限值
	private String XValue = "";
	// /越限量
	private String CValue = "";
	// /越限率(%)
	private String PerValue = "";
	// /说明
	private String Remark = "";

	public String getPD_OverID()
	{
		return PD_OverID;
	}

	public void setPD_OverID(String pD_OverID)
	{
		PD_OverID = pD_OverID;
	}

	public String getValueTime()
	{
		return ValueTime;
	}

	public void setValueTime(String valueTime)
	{
		ValueTime = valueTime;
	}

	public String getPart_Name()
	{
		return Part_Name;
	}

	public void setPart_Name(String part_Name)
	{
		Part_Name = part_Name;
	}

	public String getOverStyleName()
	{
		return OverStyleName;
	}

	public void setOverStyleName(String overStyleName)
	{
		OverStyleName = overStyleName;
	}

	public String getZValue()
	{
		return ZValue;
	}

	public void setZValue(String zValue)
	{
		ZValue = zValue;
	}

	public String getXValue()
	{
		return XValue;
	}

	public void setXValue(String xValue)
	{
		XValue = xValue;
	}

	public String getCValue()
	{
		return CValue;
	}

	public void setCValue(String cValue)
	{
		CValue = cValue;
	}

	public String getPerValue()
	{
		return PerValue;
	}

	public void setPerValue(String perValue)
	{
		PerValue = perValue;
	}

	public String getRemark()
	{
		return Remark;
	}

	public void setRemark(String remark)
	{
		Remark = remark;
	}

}
