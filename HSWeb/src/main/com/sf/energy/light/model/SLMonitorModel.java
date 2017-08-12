package com.sf.energy.light.model;

/**
 * 路灯监测
 * @author Administrator
 *
 */
public class SLMonitorModel {
	//回路ID
	private int SLLINE_ID;
	//区域ID
	private int AREA_ID;
	//区域名称
	private String Area_Name;
	//回路编号
	private String SLLine_Num;
	//回路名称
	private String SLLine_Name;
	//起始地址
	private String SLLine_Star;
	//终止地址
	private String SLLine_End;
	//控制器名称
	private String SLKongzhi_Name;
	//控制器状态
	private int SLKongzhi_State;
	//线路状态
	private String Lamp_State;
	
	//最后登录时间
	private String LastTime;
	//线路模式
	private String Lamp_moshi;
	
	public int getSLLINE_ID() {
		return SLLINE_ID;
	}
	public void setSLLINE_ID(int sLLINE_ID) {
		SLLINE_ID = sLLINE_ID;
	}
	public int getAREA_ID() {
		return AREA_ID;
	}
	public void setAREA_ID(int aREA_ID) {
		AREA_ID = aREA_ID;
	}
	public String getArea_Name() {
		return Area_Name;
	}
	public void setArea_Name(String area_Name) {
		Area_Name = area_Name;
	}
	public String getSLLine_Num() {
		return SLLine_Num;
	}
	public void setSLLine_Num(String sLLine_Num) {
		SLLine_Num = sLLine_Num;
	}
	public String getSLLine_Name() {
		return SLLine_Name;
	}
	public void setSLLine_Name(String sLLine_Name) {
		SLLine_Name = sLLine_Name;
	}
	public String getSLLine_Star() {
		return SLLine_Star;
	}
	public void setSLLine_Star(String sLLine_Star) {
		SLLine_Star = sLLine_Star;
	}
	public String getSLLine_End() {
		return SLLine_End;
	}
	public void setSLLine_End(String sLLine_End) {
		SLLine_End = sLLine_End;
	}
	public String getSLKongzhi_Name() {
		return SLKongzhi_Name;
	}
	public void setSLKongzhi_Name(String sLKongzhi_Name) {
		SLKongzhi_Name = sLKongzhi_Name;
	}
	public int getSLKongzhi_State() {
		return SLKongzhi_State;
	}
	public void setSLKongzhi_State(int sLKongzhi_State) {
		SLKongzhi_State = sLKongzhi_State;
	}
	
	public String getLastTime() {
		return LastTime;
	}
	public void setLastTime(String lastTime) {
		LastTime = lastTime;
	}
	public String getLamp_State() {
		return Lamp_State;
	}
	public void setLamp_State(String lamp_State) {
		Lamp_State = lamp_State;
	}
	public String getLamp_moshi() {
		return Lamp_moshi;
	}
	public void setLamp_moshi(String lamp_moshi) {
		Lamp_moshi = lamp_moshi;
	}
	
	
	
	
}
