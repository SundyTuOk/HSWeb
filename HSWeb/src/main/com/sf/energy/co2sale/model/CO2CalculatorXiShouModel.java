package com.sf.energy.co2sale.model;


public class CO2CalculatorXiShouModel {
	/** 
	
	* @Fields greeningType : TODO(绿化类型) 
	
	*/ 
	private String greeningType;
	
	
	/** 
	
	* @Fields unitName : TODO(单位：万平方米) 
	
	*/ 
	private String unitName;
	
	/** 
	
	* @Fields nowArea : TODO(现有面积) 
	
	*/ 
	private String nowArea;
	
	
	/** 
	
	* @Fields absorptivity : TODO(吸收率) 
	
	*/ 
	private String absorptivity;
	
	/** 
	
	* @Fields absorption : TODO(吸收量) 
	
	*/ 
	private String absorptionValue;

	public String getGreeningType() {
		return greeningType;
	}

	public void setGreeningType(String greeningType) {
		this.greeningType = greeningType;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getNowArea() {
		return nowArea;
	}

	public void setNowArea(String nowArea) {
		this.nowArea = nowArea;
	}

	public String getAbsorptivity() {
		return absorptivity;
	}

	public void setAbsorptivity(String absorptivity) {
		this.absorptivity = absorptivity;
	}

	public String getAbsorptionValue() {
		return absorptionValue;
	}

	public void setAbsorptionValue(String absorptionValue) {
		this.absorptionValue = absorptionValue;
	}
	
	
	
}
