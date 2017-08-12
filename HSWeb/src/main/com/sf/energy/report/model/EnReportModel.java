package com.sf.energy.report.model;

public class EnReportModel {
	
	
	//能耗编码
	private String energynum="";
	//能耗名称
	private String energyname="";
	//单位
	private String energyunit="";
	//比热值
	private double calorific=0;
	//折标系数
	private double coefficient=0;
	//是否启动手动录入
	private int ismanual=0;
	//是否启动上报
	private int isreport=0;
	
	//能耗用量
	private float ammValue=0;
	
	//用能比例
	private float ratio=0;
	
	//折标后的值
	private float zhebiaoValue=0;
	
	
	
	public float getZhebiaoValue()
	{
		return zhebiaoValue;
	}
	public void setZhebiaoValue(float zhebiaoValue)
	{
		this.zhebiaoValue = zhebiaoValue;
	}
	public float getAmmValue()
	{
		return ammValue;
	}
	public void setAmmValue(float ammValue)
	{
		this.ammValue = ammValue;
	}
	public float getRatio()
	{
		return ratio;
	}
	public void setRatio(float ratio)
	{
		this.ratio = ratio;
	}
	public String getEnergynum() {
		return energynum;
	}
	public void setEnergynum(String energynum) {
		this.energynum = energynum;
	}
	public String getEnergyname() {
		return energyname;
	}
	public void setEnergyname(String energyname) {
		this.energyname = energyname;
	}
	public String getEnergyunit() {
		return energyunit;
	}
	public void setEnergyunit(String energyunit) {
		this.energyunit = energyunit;
	}
	public double getCalorific() {
		return calorific;
	}
	public void setCalorific(double calorific) {
		this.calorific = calorific;
	}
	public double getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}
	public int getIsmanual() {
		return ismanual;
	}
	public void setIsmanual(int ismanual) {
		this.ismanual = ismanual;
	}
	public int getIsreport() {
		return isreport;
	}
	public void setIsreport(int isreport) {
		this.isreport = isreport;
	}
	
	
	
	
	
	
}
