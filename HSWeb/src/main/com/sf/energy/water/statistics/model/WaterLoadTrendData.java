package com.sf.energy.water.statistics.model;

public class WaterLoadTrendData {
//  /水表名称
	private String Watermeter_name = null;
	//  /查询时间
	private String Date = null;
	//  /用水量
	private Float  WaterValue = 0.0f ;
	//  /区域名称
	private String area_name = null;
	//  /区域地址
	private String area_address = null;
	//	/查得的数据时间
	private	String	valueTime = null;
	//	/水的流量
	private float	valuerate = 0;
	//	/相差的时间数
	private	float	minusTime = 0;
	
	public String getWatermeter_name() {
		return Watermeter_name;
	}
	public void setWatermeter_name(String watermeter_name) {
		Watermeter_name = watermeter_name;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public Float getWaterValue() {
		return WaterValue;
	}
	public void setWaterValue(Float waterValue) {
		WaterValue = waterValue;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getArea_address() {
		return area_address;
	}
	public void setArea_address(String area_address) {
		this.area_address = area_address;
	}
	public String getValueTime() {
		return valueTime;
	}
	public void setValueTime(String valueTime) {
		this.valueTime = valueTime;
	}
	public float getMinusTime() {
		return minusTime;
	}
	public void setMinusTime(float minusTime) {
		this.minusTime = minusTime;
	}
	public float getValuerate() {
		return valuerate;
	}
	public void setValuerate(float valuerate) {
		this.valuerate = valuerate;
	}
	
	

}
