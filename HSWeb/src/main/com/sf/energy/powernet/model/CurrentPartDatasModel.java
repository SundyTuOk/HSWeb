package com.sf.energy.powernet.model;

public class CurrentPartDatasModel
{
	private int partID;
	private String partName;
	private String valueTime="2001-01-01 00:00:00";
	/*private float tempValue;
	private float wetValue;*/
	private float ZvalueZY;
	private float Ia;
	private float Ib;
	private float Ic;
	private float Ua;
	private float Ub;
	private float Uc;
	private float P;//有功
	private float Q;//无功
	private float PF;//功率因素
	public int getPartID()
	{
		return partID;
	}
	public void setPartID(int partID)
	{
		this.partID = partID;
	}
	public String getPartName()
	{
		return partName;
	}
	public void setPartName(String partName)
	{
		this.partName = partName;
	}
	public String getValueTime()
	{
		return valueTime;
	}
	public void setValueTime(String valueTime)
	{
		this.valueTime = valueTime;
	}
/*	public float getTempValue()
	{
		return tempValue;
	}
	public void setTempValue(float tempValue)
	{
		this.tempValue = tempValue;
	}
	public float getWetValue()
	{
		return wetValue;
	}
	public void setWetValue(float wetValue)
	{
		this.wetValue = wetValue;
	}*/
	public float getZvalueZY()
	{
		return ZvalueZY;
	}
	public void setZvalueZY(float zvalueZY)
	{
		ZvalueZY = zvalueZY;
	}
	public float getIa()
	{
		return Ia;
	}
	public void setIa(float ia)
	{
		Ia = ia;
	}
	public float getIb()
	{
		return Ib;
	}
	public void setIb(float ib)
	{
		Ib = ib;
	}
	public float getIc()
	{
		return Ic;
	}
	public void setIc(float ic)
	{
		Ic = ic;
	}
	public float getUa()
	{
		return Ua;
	}
	public void setUa(float ua)
	{
		Ua = ua;
	}
	public float getUb()
	{
		return Ub;
	}
	public void setUb(float ub)
	{
		Ub = ub;
	}
	public float getP()
	{
		return P;
	}
	public void setP(float p)
	{
		P = p;
	}
	public float getQ()
	{
		return Q;
	}
	public void setQ(float q)
	{
		Q = q;
	}
	public float getPF()
	{
		return PF;
	}
	public void setPF(float pF)
	{
		PF = pF;
	}
	public float getUc()
	{
		return Uc;
	}
	public void setUc(float uc)
	{
		Uc = uc;
	}
}
