package com.sf.energy.light.model;

import java.util.Date;

/**
 * 路灯基本信息
 * @author Administrator
 *
 */
public class SlLampModel {
	//标识
	private int slLamp_Id=0;
	//所属区域
	private int area_Id=0;
	//
	private int gather_Id=0;
	//所属照明线路
	private int slKongzhi_Id=0;
	//所属照明线路
	private int slLine_Id=0;
	//编号
	private String slLamp_Num="";
	//状态
	private String slLamp_State="";
	//型号
	private String slLamp_Size="";
	//最后登录时间
	private Date lastTime=null;
	//最后登录时间
	private String lamplasttime="";
	//亮度
	private int intPercent=0;
	//安装位置
	private String AREA_ADDRESS="";
	//控制箱名称
	private String SLKongzhi_Name="";
	public int getSlLamp_Id() {
		return slLamp_Id;
	}
	public void setSlLamp_Id(int slLamp_Id) {
		this.slLamp_Id = slLamp_Id;
	}
	public int getArea_Id() {
		return area_Id;
	}
	public void setArea_Id(int area_Id) {
		this.area_Id = area_Id;
	}
	public int getGather_Id() {
		return gather_Id;
	}
	public void setGather_Id(int gather_Id) {
		this.gather_Id = gather_Id;
	}
	public int getSlKongzhi_Id() {
		return slKongzhi_Id;
	}
	public void setSlKongzhi_Id(int slKongzhi_Id) {
		this.slKongzhi_Id = slKongzhi_Id;
	}
	public int getSlLine_Id() {
		return slLine_Id;
	}
	public void setSlLine_Id(int slLine_Id) {
		this.slLine_Id = slLine_Id;
	}
	public String getSlLamp_Num() {
		return slLamp_Num;
	}
	public void setSlLamp_Num(String slLamp_Num) {
		this.slLamp_Num = slLamp_Num;
	}
	public String getSlLamp_State() {
		return slLamp_State;
	}
	public void setSlLamp_State(String slLamp_State) {
		this.slLamp_State = slLamp_State;
	}
	public String getSlLamp_Size() {
		return slLamp_Size;
	}
	public void setSlLamp_Size(String slLamp_Size) {
		this.slLamp_Size = slLamp_Size;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public int getIntPercent() {
		return intPercent;
	}
	public void setIntPercent(int intPercent) {
		this.intPercent = intPercent;
	}
	public String getAREA_ADDRESS() {
		return AREA_ADDRESS;
	}
	public void setAREA_ADDRESS(String aREA_ADDRESS) {
		AREA_ADDRESS = aREA_ADDRESS;
	}
	public String getSLKongzhi_Name() {
		return SLKongzhi_Name;
	}
	public void setSLKongzhi_Name(String sLKongzhi_Name) {
		SLKongzhi_Name = sLKongzhi_Name;
	}
	public String getLamplasttime() {
		return lamplasttime;
	}
	public void setLamplasttime(String lamplasttime) {
		this.lamplasttime = lamplasttime;
	}
	
	
	
}
