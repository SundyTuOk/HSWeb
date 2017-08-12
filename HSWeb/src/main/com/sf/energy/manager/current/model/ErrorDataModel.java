package com.sf.energy.manager.current.model;

public class ErrorDataModel
{
	private String ammeterName;
	private int ammeterID;
	private String valueTime;
	private float zvalueZY;
	private int errorCode;
	private int isPushUp;
	public int getAmmeterID()
	{
		return ammeterID;
	}
	public void setAmmeterID(int ammeterID)
	{
		this.ammeterID = ammeterID;
	}
	public String getValueTime()
	{
		return valueTime;
	}
	public void setValueTime(String valueTime)
	{
		this.valueTime = valueTime;
	}
	public float getZvalueZY()
	{
		return zvalueZY;
	}
	public void setZvalueZY(float zvalueZY)
	{
		this.zvalueZY = zvalueZY;
	}
	public int getErrorCode()
	{
		return errorCode;
	}
	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}
	public int getIsPushUp()
	{
		return isPushUp;
	}
	public void setIsPushUp(int isPushUp)
	{
		this.isPushUp = isPushUp;
	}
	public String getAmmeterName()
	{
		return ammeterName;
	}
	public void setAmmeterName(String ammeterName)
	{
		this.ammeterName = ammeterName;
	}
	
}
