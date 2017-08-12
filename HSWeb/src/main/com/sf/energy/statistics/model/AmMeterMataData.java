
package com.sf.energy.statistics.model;

import java.util.Date;

/**
 * 电表数据的实体类
 * 记录了电表的ID、记录时间和抄读数据
 * @author 王钊
 * @version 1.0
 * @since version 1.0
 */
public class AmMeterMataData implements java.io.Serializable
{
	// /电表ID
	private int amMeterID = 0;
	// /电表数据记录时间
	private Date recordTimeDate = null;
	// /电表记录数据
	private float value = 0;
	// /温度值
	private float temperature = 0;
	// /小时
	private int hour = 0;

	//用来判断这条数据是否为插补数据 0：否 ；1：是
	private int isInsert =0; 
	
	public int getAmMeterID()
	{
		return amMeterID;
	}

	
	public void setAmMeterID(int amMeterID)
	{
		this.amMeterID = amMeterID;
	}

	


	
	public Date getRecordTimeDate() {
		return recordTimeDate;
	}


	public void setRecordTimeDate(Date recordTimeDate)
	{
		this.recordTimeDate = recordTimeDate;
	}

	
	public float getValue()
	{
		return value;
	}

	
	public void setValue(float value)
	{
		this.value = value;
	}


	public float getTemperature() {
		return temperature;
	}


	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}


	public int getHour() {
		return hour;
	}


	public void setHour(int hour) {
		this.hour = hour;
	}


	public int getIsInsert()
	{
		return isInsert;
	}


	public void setIsInsert(int isInsert)
	{
		this.isInsert = isInsert;
	}
	
	
}
