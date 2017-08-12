package com.sf.energy.co2sale.model;

public class CO2CalculatorPaiFangModel {
	
	/** 
	
	* @Fields energyStyleName : TODO(能耗名称) 
	
	*/ 
	private String energyStyleName;
	
	/** 
	
	* @Fields unitName : TODO(单位，万千瓦时) 
	
	*/ 
	private String unitName;
	
	
	/** 
	
	* @Fields useValue : TODO(用量) 
	
	*/ 
	private String useValue;
	
	
	/** 
	
	* @Fields ConversionCoefficient : TODO(折标系数) 
	
	*/ 
	private String ConversionCoefficient;
	
	/** 
	
	* @Fields paiFangValue : TODO(排放量) 
	
	*/ 
	private String paiFangValue;

	
	
	
	public String getEnergyStyleName() {
		return energyStyleName;
	}

	public void setEnergyStyleName(String energyStyleName) {
		this.energyStyleName = energyStyleName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUseValue() {
		return useValue;
	}

	public void setUseValue(String useValue) {
		this.useValue = useValue;
	}

	public String getConversionCoefficient() {
		return ConversionCoefficient;
	}

	public void setConversionCoefficient(String conversionCoefficient) {
		ConversionCoefficient = conversionCoefficient;
	}

	public String getPaiFangValue() {
		return paiFangValue;
	}

	public void setPaiFangValue(String paiFangValue) {
		this.paiFangValue = paiFangValue;
	}
}
