package com.sf.energy.expert.model;

/**
 * 手工录入能耗的model,用于单位面积能耗页面，电、煤、汽油...
 * @author yanhao
 *
 */
public class ManualEnergyModel
{
	//建筑ID
	int archID=0;
	//建筑名称
	String archName="";
	//建筑面积
	float archArea=0;
	//建筑人口
	int archCountMan=0;
	//电能
	float energyValue=0;
	//水能
	float waterValue=0;
	//煤
	float meiValue=0;
	//汽油
	float qiyouValue=0;
	//煤油
	float meiyouValue=0;
	//柴油
	float chaiyouValue=0;
	
	//单位面积能耗
	float unitEnergy=0;
	float unitMei=0;
	float unitMeiyou=0;
	float unitQiyou=0;
	float unitChaiyou=0;
	
	//人均能耗
	float averageEnergy=0;
	float averageMei=0;
	float averageMeiyou=0;
	float averageQiyou=0;
	float averageChaiyou=0;
	
	int queryYear=0;
	int queryMonth=0;
	//建筑类型名称
	String archStyleName="";
	
	//用能比例
	String ratioEnergy="";
	String ratioMei="";
	String ratioMeiyou="";
	String ratioQiyou="";
	String ratioChaiyou="";
	
	
	
	
	
	public String getRatioEnergy()
	{
		return ratioEnergy;
	}
	public void setRatioEnergy(String ratioEnergy)
	{
		this.ratioEnergy = ratioEnergy;
	}
	public String getRatioMei()
	{
		return ratioMei;
	}
	public void setRatioMei(String ratioMei)
	{
		this.ratioMei = ratioMei;
	}
	public String getRatioMeiyou()
	{
		return ratioMeiyou;
	}
	public void setRatioMeiyou(String ratioMeiyou)
	{
		this.ratioMeiyou = ratioMeiyou;
	}
	public String getRatioQiyou()
	{
		return ratioQiyou;
	}
	public void setRatioQiyou(String ratioQiyou)
	{
		this.ratioQiyou = ratioQiyou;
	}
	public String getRatioChaiyou()
	{
		return ratioChaiyou;
	}
	public void setRatioChaiyou(String ratioChaiyou)
	{
		this.ratioChaiyou = ratioChaiyou;
	}
	public String getArchStyleName()
	{
		return archStyleName;
	}
	public void setArchStyleName(String archStyleName)
	{
		this.archStyleName = archStyleName;
	}
	public int getQueryYear()
	{
		return queryYear;
	}
	public void setQueryYear(int queryYear)
	{
		this.queryYear = queryYear;
	}
	public int getQueryMonth()
	{
		return queryMonth;
	}
	public void setQueryMonth(int queryMonth)
	{
		this.queryMonth = queryMonth;
	}
	public float getUnitEnergy()
	{
		return unitEnergy;
	}
	public void setUnitEnergy(float unitEnergy)
	{
		this.unitEnergy = unitEnergy;
	}
	public float getUnitMei()
	{
		return unitMei;
	}
	public void setUnitMei(float unitMei)
	{
		this.unitMei = unitMei;
	}
	public float getUnitMeiyou()
	{
		return unitMeiyou;
	}
	public void setUnitMeiyou(float unitMeiyou)
	{
		this.unitMeiyou = unitMeiyou;
	}
	public float getUnitQiyou()
	{
		return unitQiyou;
	}
	public void setUnitQiyou(float unitQiyou)
	{
		this.unitQiyou = unitQiyou;
	}
	public float getUnitChaiyou()
	{
		return unitChaiyou;
	}
	public void setUnitChaiyou(float unitChaiyou)
	{
		this.unitChaiyou = unitChaiyou;
	}
	public float getAverageEnergy()
	{
		return averageEnergy;
	}
	public void setAverageEnergy(float averageEnergy)
	{
		this.averageEnergy = averageEnergy;
	}
	public float getAverageMei()
	{
		return averageMei;
	}
	public void setAverageMei(float averageMei)
	{
		this.averageMei = averageMei;
	}
	public float getAverageMeiyou()
	{
		return averageMeiyou;
	}
	public void setAverageMeiyou(float averageMeiyou)
	{
		this.averageMeiyou = averageMeiyou;
	}
	public float getAverageQiyou()
	{
		return averageQiyou;
	}
	public void setAverageQiyou(float averageQiyou)
	{
		this.averageQiyou = averageQiyou;
	}
	public float getAverageChaiyou()
	{
		return averageChaiyou;
	}
	public void setAverageChaiyou(float averageChaiyou)
	{
		this.averageChaiyou = averageChaiyou;
	}
	public int getArchID()
	{
		return archID;
	}
	public void setArchID(int archID)
	{
		this.archID = archID;
	}
	public String getArchName()
	{
		return archName;
	}
	public void setArchName(String archName)
	{
		this.archName = archName;
	}
	public float getArchArea()
	{
		return archArea;
	}
	public void setArchArea(float archArea)
	{
		this.archArea = archArea;
	}
	
	public int getArchCountMan()
	{
		return archCountMan;
	}
	public void setArchCountMan(int archCountMan)
	{
		this.archCountMan = archCountMan;
	}
	public float getEnergyValue()
	{
		return energyValue;
	}
	public void setEnergyValue(float energyValue)
	{
		this.energyValue = energyValue;
	}
	public float getWaterValue()
	{
		return waterValue;
	}
	public void setWaterValue(float waterValue)
	{
		this.waterValue = waterValue;
	}
	public float getMeiValue()
	{
		return meiValue;
	}
	public void setMeiValue(float meiValue)
	{
		this.meiValue = meiValue;
	}
	public float getQiyouValue()
	{
		return qiyouValue;
	}
	public void setQiyouValue(float qiyouValue)
	{
		this.qiyouValue = qiyouValue;
	}
	public float getMeiyouValue()
	{
		return meiyouValue;
	}
	public void setMeiyouValue(float meiyouValue)
	{
		this.meiyouValue = meiyouValue;
	}
	public float getChaiyouValue()
	{
		return chaiyouValue;
	}
	public void setChaiyouValue(float chaiyouValue)
	{
		this.chaiyouValue = chaiyouValue;
	}
	
	
	
}
