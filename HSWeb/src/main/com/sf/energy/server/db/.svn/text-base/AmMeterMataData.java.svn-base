
package com.sf.energy.server.db;

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

	
	public int getAmMeterID()
	{
		return amMeterID;
	}

	
	public void setAmMeterID(int amMeterID)
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
