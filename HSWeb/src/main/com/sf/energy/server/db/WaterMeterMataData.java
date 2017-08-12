package com.sf.energy.server.db;

import java.util.Date;

/**
 * 水表数据的实体类 记录了水表的ID、记录时间和抄读数据
 * 
 * @author 王钊
 * @version 1.0
 * @since version 1.0
 */
public class WaterMeterMataData implements java.io.Serializable
{
	// /水表ID
	private int amMeterID = 0;
	// /水表数据记录时间
	private Date recordTimeDate = null;
	// /水表记录数据
	private float value = 0;

	public int getWaterMeterID()
	{
		return amMeterID;
	}

	public void setWaterMeterID(int amMeterID)
	{
		this.amMeterID = amMeterID;
	}

	public Date getRecordTimeDate()
	{
		return recordTimeDate;
	}

	private void setRecordTimeDate(java.sql.Date date)
	{
		this.recordTimeDate = new Date(date.getTime());
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
}
