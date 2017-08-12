package com.sf.energy.classroomlight.model;

public class LightRoomHistoryModel
{
	// /历史记录ID
	private int LightRoomHistory_ID = 0;
	// /教室名称
	private String LightRoomName = "";
	// /执行时间
	private String LightRoomHistory_Time = "";
	// /执行状态
	private String LightRoomHistory_State = "";
	// /执行者
	private String Users_Name = "";
	// /执行类型
	private String LightRoomHistory_Style = "";
	// /操作
	private String LightRoomHistory_Opr = "";

	public int getLightRoomHistory_ID()
	{
		return LightRoomHistory_ID;
	}

	public void setLightRoomHistory_ID(int lightRoomHistory_ID)
	{
		LightRoomHistory_ID = lightRoomHistory_ID;
	}

	public String getLightRoomName()
	{
		return LightRoomName;
	}

	public void setLightRoomName(String lightRoomName)
	{
		LightRoomName = lightRoomName;
	}

	public String getLightRoomHistory_Time()
	{
		return LightRoomHistory_Time;
	}

	public void setLightRoomHistory_Time(String lightRoomHistory_Time)
	{
		LightRoomHistory_Time = lightRoomHistory_Time;
	}

	public String getLightRoomHistory_State()
	{
		return LightRoomHistory_State;
	}

	public void setLightRoomHistory_State(String lightRoomHistory_State)
	{
		LightRoomHistory_State = lightRoomHistory_State;
	}

	public String getUsers_Name()
	{
		return Users_Name;
	}

	public void setUsers_Name(String users_Name)
	{
		Users_Name = users_Name;
	}

	public String getLightRoomHistory_Style()
	{
		return LightRoomHistory_Style;
	}

	public void setLightRoomHistory_Style(String lightRoomHistory_Style)
	{
		LightRoomHistory_Style = lightRoomHistory_Style;
	}

	public String getLightRoomHistory_Opr()
	{
		return LightRoomHistory_Opr;
	}

	public void setLightRoomHistory_Opr(String lightRoomHistory_Opr)
	{
		LightRoomHistory_Opr = lightRoomHistory_Opr;
	}

}
