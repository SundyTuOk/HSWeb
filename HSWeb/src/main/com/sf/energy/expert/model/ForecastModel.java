package com.sf.energy.expert.model;

/**
 * 能源预测相关模块的model
 * @author yanhao
 *
 */
public class ForecastModel
{
	//建筑类别编号
	private String styleID="";
	
	//建筑类别名称
	private String styleName="";
	
	//建筑类别总面积
	private double cArea=1;
	
	//建筑类别总人口
	private double cMan=1;
	
	//建筑类别总面积
	private double cAreaThis=1;
		
	//建筑类别总人口
	private double cManThis=1;
	
	//用量 
	private double zGross=0;
	
	//预测之后的用量
	private String zGrossForecast="";
	
	//指标用量
	private String zGrossTarget="";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getzGrossTarget()
	{
		return zGrossTarget;
	}
	public void setzGrossTarget(String zGrossTarget)
	{
		this.zGrossTarget = zGrossTarget;
	}
	public double getcAreaThis()
	{
		return cAreaThis;
	}
	public void setcAreaThis(double cAreaThis)
	{
		this.cAreaThis = cAreaThis;
	}
	public double getcManThis()
	{
		return cManThis;
	}
	public void setcManThis(double cManThis)
	{
		this.cManThis = cManThis;
	}
	public String getzGrossForecast()
	{
		return zGrossForecast;
	}
	public void setzGrossForecast(String zGrossForecast)
	{
		this.zGrossForecast = zGrossForecast;
	}
	public double getzGross()
	{
		return zGross;
	}
	public void setzGross(double zGross)
	{
		this.zGross = zGross;
	}
	public String getStyleID()
	{
		return styleID;
	}
	public void setStyleID(String styleID)
	{
		this.styleID = styleID;
	}
	public String getStyleName()
	{
		return styleName;
	}
	public void setStyleName(String styleName)
	{
		this.styleName = styleName;
	}
	public double getcArea()
	{
		return cArea;
	}
	public void setcArea(double cArea)
	{
		this.cArea = cArea;
	}
	public double getcMan()
	{
		return cMan;
	}
	public void setcMan(double cMan)
	{
		this.cMan = cMan;
	}
	

}
