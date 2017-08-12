package com.sf.energy.light.model;

import java.util.Date;

/**
 * 智能路灯控制子系统历史记录的model
 * @author Administrator
 *
 */
public class SlHistoryModel {
	//标识
	private int slHistory_Id;
	//执行时间
	private String slHistory_Time;
	//执行状态
	private String slHistory_State;
	//执行用户
	private String users_Name;
	//执行类型
	private String slHistory_Style;
	//操作细节
	private String slHistory_Opr;
	//任务编号
	private String TASKNUM;
	//线路ID
	private int SLLINE_ID;
	
	private String slLine_Name;
	
	public int getSlHistory_Id() {
		return slHistory_Id;
	}
	public void setSlHistory_Id(int slHistory_Id) {
		this.slHistory_Id = slHistory_Id;
	}
	
	public String getSlHistory_Time() {
		return slHistory_Time;
	}
	public void setSlHistory_Time(String slHistory_Time) {
		this.slHistory_Time = slHistory_Time;
	}
	public String getSlHistory_State() {
		return slHistory_State;
	}
	public void setSlHistory_State(String slHistory_State) {
		this.slHistory_State = slHistory_State;
	}
	
	public String getUsers_Name() {
		return users_Name;
	}
	public void setUsers_Name(String users_Name) {
		this.users_Name = users_Name;
	}
	public String getSlHistory_Style() {
		return slHistory_Style;
	}
	public void setSlHistory_Style(String slHistory_Style) {
		this.slHistory_Style = slHistory_Style;
	}
	public String getSlHistory_Opr() {
		return slHistory_Opr;
	}
	public void setSlHistory_Opr(String slHistory_Opr) {
		this.slHistory_Opr = slHistory_Opr;
	}
	public String getTASKNUM() {
		return TASKNUM;
	}
	public void setTASKNUM(String tASKNUM) {
		TASKNUM = tASKNUM;
	}
	public int getSLLINE_ID() {
		return SLLINE_ID;
	}
	public void setSLLINE_ID(int sLLINE_ID) {
		SLLINE_ID = sLLINE_ID;
	}
	public String getSlLine_Name() {
		return slLine_Name;
	}
	public void setSlLine_Name(String slLine_Name) {
		this.slLine_Name = slLine_Name;
	}
	
	
	
}
