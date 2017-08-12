package com.sf.energy.expert.model;

//建筑能耗审计Model层
public class EnAuditArcModel {
	//ID
	private int EnAuditArc_ID;
	//报告编号
	private String EnAuditArc_Num;
	//名称
	private String EnAuditArc_Name;
	//审计的建筑ID
	private int Architecture_ID;
	//审计年度
	private String EnAuditArc_Year;
	//审计时间
	private String EnAuditArc_Time;
	//审计人
	private String EnAuditArc_FZMan;
	
	private String EnAuditArc_SHMan;
	//审计报表内容
	private String EnAuditArc_Text;
	//审计单位
	private String EnAuditArc_Employers;
	//起始时间
	private String enAuditArc_StartTime;
	//结束时间
	private String EnAuditArc_EndTime;
	
	private String EnAuditArc_Img;
	//建筑编号
	private String Architecture_Num;
	//建筑名称
	private String Architecture_Name;
	//建筑面积
	private String ARCHITECTURE_AREA;
	//建筑空调面积
	private String ARCHITECTURE_AIRCONDITION;
	//建筑建造时间
	private String ARCHITECTURE_TIME;
	//建筑类型
	private String ARCHITECTURE_STYLE;

	public int getEnAuditArc_ID() {
		return EnAuditArc_ID;
	}

	public void setEnAuditArc_ID(int enAuditArc_ID) {
		EnAuditArc_ID = enAuditArc_ID;
	}

	public String getEnAuditArc_Num() {
		return EnAuditArc_Num;
	}

	public void setEnAuditArc_Num(String enAuditArc_Num) {
		EnAuditArc_Num = enAuditArc_Num;
	}

	public String getEnAuditArc_Name() {
		return EnAuditArc_Name;
	}

	public void setEnAuditArc_Name(String enAuditArc_Name) {
		EnAuditArc_Name = enAuditArc_Name;
	}

	public int getArchitecture_ID() {
		return Architecture_ID;
	}

	public void setArchitecture_ID(int architecture_ID) {
		Architecture_ID = architecture_ID;
	}

	public String getEnAuditArc_Year() {
		return EnAuditArc_Year;
	}

	public void setEnAuditArc_Year(String enAuditArc_Year) {
		EnAuditArc_Year = enAuditArc_Year;
	}

	public String getEnAuditArc_Time() {
		return EnAuditArc_Time;
	}

	public void setEnAuditArc_Time(String enAuditArc_Time) {
		EnAuditArc_Time = enAuditArc_Time;
	}

	

	public String getEnAuditArc_FZMan() {
		return EnAuditArc_FZMan;
	}

	public void setEnAuditArc_FZMan(String enAuditArc_FZMan) {
		EnAuditArc_FZMan = enAuditArc_FZMan;
	}

	public String getEnAuditArc_SHMan() {
		return EnAuditArc_SHMan;
	}

	public void setEnAuditArc_SHMan(String enAuditArc_SHMan) {
		EnAuditArc_SHMan = enAuditArc_SHMan;
	}

	public String getEnAuditArc_Text() {
		return EnAuditArc_Text;
	}

	public void setEnAuditArc_Text(String enAuditArc_Text) {
		EnAuditArc_Text = enAuditArc_Text;
	}

	public String getEnAuditArc_Employers() {
		return EnAuditArc_Employers;
	}

	public void setEnAuditArc_Employers(String enAuditArc_Employers) {
		EnAuditArc_Employers = enAuditArc_Employers;
	}

	public String getEnAuditArc_StartTime() {
		return enAuditArc_StartTime;
	}

	public void setEnAuditArc_StartTime(String enAuditArc_StartTime) {
		this.enAuditArc_StartTime = enAuditArc_StartTime;
	}

	public String getEnAuditArc_EndTime() {
		return EnAuditArc_EndTime;
	}

	public void setEnAuditArc_EndTime(String enAuditArc_EndTime) {
		EnAuditArc_EndTime = enAuditArc_EndTime;
	}

	public String getEnAuditArc_Img() {
		return EnAuditArc_Img;
	}

	public void setEnAuditArc_Img(String enAuditArc_Img) {
		EnAuditArc_Img = enAuditArc_Img;
	}

	public String getArchitecture_Num() {
		return Architecture_Num;
	}

	public void setArchitecture_Num(String architecture_Num) {
		Architecture_Num = architecture_Num;
	}

	public String getArchitecture_Name() {
		return Architecture_Name;
	}

	public void setArchitecture_Name(String architecture_Name) {
		Architecture_Name = architecture_Name;
	}

	public String getARCHITECTURE_AREA() {
		return ARCHITECTURE_AREA;
	}

	public void setARCHITECTURE_AREA(String aRCHITECTURE_AREA) {
		ARCHITECTURE_AREA = aRCHITECTURE_AREA;
	}

	public String getARCHITECTURE_AIRCONDITION() {
		return ARCHITECTURE_AIRCONDITION;
	}

	public void setARCHITECTURE_AIRCONDITION(String aRCHITECTURE_AIRCONDITION) {
		ARCHITECTURE_AIRCONDITION = aRCHITECTURE_AIRCONDITION;
	}

	public String getARCHITECTURE_TIME() {
		return ARCHITECTURE_TIME;
	}

	public void setARCHITECTURE_TIME(String aRCHITECTURE_TIME) {
		ARCHITECTURE_TIME = aRCHITECTURE_TIME;
	}

	public String getARCHITECTURE_STYLE() {
		return ARCHITECTURE_STYLE;
	}

	public void setARCHITECTURE_STYLE(String aRCHITECTURE_STYLE) {
		ARCHITECTURE_STYLE = aRCHITECTURE_STYLE;
	}
	
	
}
