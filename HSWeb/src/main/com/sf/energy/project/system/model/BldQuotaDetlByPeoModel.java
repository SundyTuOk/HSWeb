package com.sf.energy.project.system.model;

public class BldQuotaDetlByPeoModel
{

	///值ID
	private int valueID;
	///建筑/区域ID
	private int bldOrAreaID;
	///建筑/区域标志。1:建筑  0:区域
	private int aOrbFlg;
	///定额分配年
	private String year = "";
	///详细分类名称
	private String name = "";
	///适用单价
	private double price;
	///适用月份。1:适用  0:不适用
	private int m1 = 0;
	private int m2 = 0;
	private int m3 = 0;
	private int m4 = 0;
	private int m5 = 0;
	private int m6 = 0;
	private int m7 = 0;
	private int m8 = 0;
	private int m9 = 0;
	private int m10 = 0;
	private int m11 = 0;
	private int m12 = 0;
	///人员总数：人
	private int totalPeople;
	///用电标准：千瓦时／人月
	private double eStandard;
	///备注
	private String remark = "";
	
	public int getValueID()
	{
		return valueID;
	}
	public void setValueID(int valueID)
	{
		this.valueID = valueID;
	}
	public int getBldOrAreaID()
	{
		return bldOrAreaID;
	}
	public void setBldOrAreaID(int bldOrAreaID)
	{
		this.bldOrAreaID = bldOrAreaID;
	}
	public int getaOrbFlg()
	{
		return aOrbFlg;
	}
	public void setaOrbFlg(int aOrbFlg)
	{
		this.aOrbFlg = aOrbFlg;
	}
	public String getYear()
	{
		return year;
	}
	public void setYear(String year)
	{
		this.year = year;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}
	public int getM1()
	{
		return m1;
	}
	public void setM1(int m1)
	{
		this.m1 = m1;
	}
	public int getM2()
	{
		return m2;
	}
	public void setM2(int m2)
	{
		this.m2 = m2;
	}
	public int getM3()
	{
		return m3;
	}
	public void setM3(int m3)
	{
		this.m3 = m3;
	}
	public int getM4()
	{
		return m4;
	}
	public void setM4(int m4)
	{
		this.m4 = m4;
	}
	public int getM5()
	{
		return m5;
	}
	public void setM5(int m5)
	{
		this.m5 = m5;
	}
	public int getM6()
	{
		return m6;
	}
	public void setM6(int m6)
	{
		this.m6 = m6;
	}
	public int getM7()
	{
		return m7;
	}
	public void setM7(int m7)
	{
		this.m7 = m7;
	}
	public int getM8()
	{
		return m8;
	}
	public void setM8(int m8)
	{
		this.m8 = m8;
	}
	public int getM9()
	{
		return m9;
	}
	public void setM9(int m9)
	{
		this.m9 = m9;
	}
	public int getM10()
	{
		return m10;
	}
	public void setM10(int m10)
	{
		this.m10 = m10;
	}
	public int getM11()
	{
		return m11;
	}
	public void setM11(int m11)
	{
		this.m11 = m11;
	}
	public int getM12()
	{
		return m12;
	}
	public void setM12(int m12)
	{
		this.m12 = m12;
	}
	public int getTotalPeople()
	{
		return totalPeople;
	}
	public void setTotalPeople(int totalPeople)
	{
		this.totalPeople = totalPeople;
	}
	public double geteStandard()
	{
		return eStandard;
	}
	public void seteStandard(double eStandard)
	{
		this.eStandard = eStandard;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
}
