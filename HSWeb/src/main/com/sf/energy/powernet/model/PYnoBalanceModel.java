package com.sf.energy.powernet.model;

/** 

* @ClassName: PYnoBalanceModel 

* @Description: TODO(当前有功功率不平衡model) 

* @author xiaoh

* @date 2015年6月4日 下午4:30:14 

* 
 

*/ 
public class PYnoBalanceModel
{
	private double PowerAY;
	private double PowerBY;
	private double PowerCY;
	private double PYrate;
	private String Valuetime;
	public double getPowerAY()
	{
		return PowerAY;
	}
	public void setPowerAY(double powerAY)
	{
		PowerAY = powerAY;
	}
	public double getPowerBY()
	{
		return PowerBY;
	}
	public void setPowerBY(double powerBY)
	{
		PowerBY = powerBY;
	}
	public double getPowerCY()
	{
		return PowerCY;
	}
	public void setPowerCY(double powerCY)
	{
		PowerCY = powerCY;
	}
	public double getPYrate()
	{
		return PYrate;
	}
	public void setPYrate(double pYrate)
	{
		PYrate = pYrate;
	}
	public String getValuetime()
	{
		return Valuetime;
	}
	public void setValuetime(String valuetime)
	{
		Valuetime = valuetime;
	}
}
