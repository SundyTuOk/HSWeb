package com.sf.energy.expert.model;

//部门和建筑的ID，名称
public class AreaArcModel {
	//区域ID
	private int Area_ID;
	//区域名称
	private String Area_Name;
	//建筑物ID
	private int Architecture_ID;
	//建筑物名称
	private String Architecture_Name;
	
	public int getArea_ID() {
		return Area_ID;
	}
	public void setArea_ID(int area_ID) {
		Area_ID = area_ID;
	}
	public String getArea_Name() {
		return Area_Name;
	}
	public void setArea_Name(String area_Name) {
		Area_Name = area_Name;
	}
	public int getArchitecture_ID() {
		return Architecture_ID;
	}
	public void setArchitecture_ID(int architecture_ID) {
		Architecture_ID = architecture_ID;
	}
	public String getArchitecture_Name() {
		return Architecture_Name;
	}
	public void setArchitecture_Name(String architecture_Name) {
		Architecture_Name = architecture_Name;
	}
	
	

}
