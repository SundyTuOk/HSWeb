package com.sf.energy.report.model;

/**
 * 建筑抽样的Model层
 * @author Administrator
 *
 */
public class ArcSampleModel {
	//建筑ID
	private int architecture_ID=0;
	//区域ID
	private int area_ID=0;
	//建筑编号
	private String architecture_Num="";
	//建筑名称
	private String architecture_Name="";
	//建筑类型
	private String architecture_Style="";
	//负责人
	private String architecture_Man="";
	//负责人电话
	private String architecture_Phone="";
	//是否被抽样
	private int isEnReport=0;
	public int getArchitecture_ID() {
		return architecture_ID;
	}
	public void setArchitecture_ID(int architecture_ID) {
		this.architecture_ID = architecture_ID;
	}
	public int getArea_ID() {
		return area_ID;
	}
	public void setArea_ID(int area_ID) {
		this.area_ID = area_ID;
	}
	public String getArchitecture_Num() {
		return architecture_Num;
	}
	public void setArchitecture_Num(String architecture_Num) {
		this.architecture_Num = architecture_Num;
	}
	public String getArchitecture_Name() {
		return architecture_Name;
	}
	public void setArchitecture_Name(String architecture_Name) {
		this.architecture_Name = architecture_Name;
	}
	public String getArchitecture_Style() {
		return architecture_Style;
	}
	public void setArchitecture_Style(String architecture_Style) {
		this.architecture_Style = architecture_Style;
	}
	public String getArchitecture_Man() {
		return architecture_Man;
	}
	public void setArchitecture_Man(String architecture_Man) {
		this.architecture_Man = architecture_Man;
	}
	public String getArchitecture_Phone() {
		return architecture_Phone;
	}
	public void setArchitecture_Phone(String architecture_Phone) {
		this.architecture_Phone = architecture_Phone;
	}
	public int getIsEnReport() {
		return isEnReport;
	}
	public void setIsEnReport(int isEnReport) {
		this.isEnReport = isEnReport;
	}
	
	
	
}
