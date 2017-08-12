package com.sf.energy.co2sale.model;

public class CO2CalculatorSavingAdditionalModel {
	/** 
	
	* @Fields itemName : TODO(项目名称) 
	
	*/ 
	private String itemName;
	
	/** 
	
	* @Fields completionRate : TODO(完成率) 
	
	*/ 
	private String completionRate;
	
	/** 
	
	* @Fields savingFormula : TODO(节能公式) 
	
	*/ 
	private String savingFormula;
	
	
	/** 
	
	* @Fields remarks : TODO(备注) 
	
	*/ 
	private String remarks="";


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getCompletionRate() {
		return completionRate;
	}


	public void setCompletionRate(String completionRate) {
		this.completionRate = completionRate;
	}


	public String getSavingFormula() {
		return savingFormula;
	}


	public void setSavingFormula(String savingFormula) {
		this.savingFormula = savingFormula;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
