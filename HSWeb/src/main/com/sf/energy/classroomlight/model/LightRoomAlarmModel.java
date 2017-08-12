package com.sf.energy.classroomlight.model;

public class LightRoomAlarmModel
{
	// /报警ID
	private int BaojingInfo_id = 0;
	// /教室名称
	private String LightRoomName = "";
	// /报警时间
	private String BaojingInfo_Time = "";
	// /报警类型
	private String BaojingInfo_Style = "";
	// /报警内容
	private String BaojingInfo_Title = "";
	
	public int getBaojingInfo_id()
	{
		return BaojingInfo_id;
	}
	public void setBaojingInfo_id(int baojingInfo_id)
	{
		BaojingInfo_id = baojingInfo_id;
	}
	public String getLightRoomName()
	{
		return LightRoomName;
	}
	public void setLightRoomName(String lightRoomName)
	{
		LightRoomName = lightRoomName;
	}
	public String getBaojingInfo_Time()
	{
		return BaojingInfo_Time;
	}
	public void setBaojingInfo_Time(String baojingInfo_Time)
	{
		BaojingInfo_Time = baojingInfo_Time;
	}
	public String getBaojingInfo_Style()
	{
		return BaojingInfo_Style;
	}
	public void setBaojingInfo_Style(String baojingInfo_Style)
	{
		BaojingInfo_Style = baojingInfo_Style;
	}
	public String getBaojingInfo_Title()
	{
		return BaojingInfo_Title;
	}
	public void setBaojingInfo_Title(String baojingInfo_Title)
	{
		BaojingInfo_Title = baojingInfo_Title;
	}

}
