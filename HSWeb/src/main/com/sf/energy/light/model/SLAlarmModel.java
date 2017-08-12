package com.sf.energy.light.model;

public class SLAlarmModel {
	//回路ID
	private int SLLINE_ID;
	//报警ID
	private int BAOJINGINFO_ID;
	//回路名称
	private String SLLINE_NAME;
	//报警时间
	private String BAOJINGINFO_TIME;
	//报警类型
	private String BaojingInfo_Style;
	//报警内容
	private String BAOJINGINFO_TITLE;
	public int getSLLINE_ID() {
		return SLLINE_ID;
	}
	public void setSLLINE_ID(int sLLINE_ID) {
		SLLINE_ID = sLLINE_ID;
	}
	public int getBAOJINGINFO_ID() {
		return BAOJINGINFO_ID;
	}
	public void setBAOJINGINFO_ID(int bAOJINGINFO_ID) {
		BAOJINGINFO_ID = bAOJINGINFO_ID;
	}
	public String getSLLINE_NAME() {
		return SLLINE_NAME;
	}
	public void setSLLINE_NAME(String sLLINE_NAME) {
		SLLINE_NAME = sLLINE_NAME;
	}
	public String getBAOJINGINFO_TIME() {
		return BAOJINGINFO_TIME;
	}
	public void setBAOJINGINFO_TIME(String bAOJINGINFO_TIME) {
		BAOJINGINFO_TIME = bAOJINGINFO_TIME;
	}
	public String getBaojingInfo_Style() {
		return BaojingInfo_Style;
	}
	public void setBaojingInfo_Style(String baojingInfo_Style) {
		BaojingInfo_Style = baojingInfo_Style;
	}
	public String getBAOJINGINFO_TITLE() {
		return BAOJINGINFO_TITLE;
	}
	public void setBAOJINGINFO_TITLE(String bAOJINGINFO_TITLE) {
		BAOJINGINFO_TITLE = bAOJINGINFO_TITLE;
	}
	
	
}
