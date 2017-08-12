package com.sf.energy.co2sale.model;


/** 

* @ClassName: CO2WarningModel 

* @Description: TODO 碳交易平台报警信息

* @author xiaoh

* @date 2015年4月8日 上午9:20:22 
 

*/
public class CO2WarningModel {
	
	/**
	 * 部门ID
	 */
	private int partID;
	
	/**
	 * 部门名称
	 */
	private String partName;
	
	/** 
	
	* @Fields areaID : 区域ID
	
	*/ 
	private int areaID;
	
	/** 
	
	* @Fields areaName : 区域名称
	
	*/ 
	private String areaName;
	
	/**
	 * 指标用量
	 */
	private float quotaValue;
	
	/** 
	
	* @Fields remainingValue : 剩余用量
	
	*/ 
	private float remainingValue;

	
	
	
	
	
	public int getPartID() {
		return partID;
	}

	public void setPartID(int partID) {
		this.partID = partID;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public float getQuotaValue() {
		return quotaValue;
	}

	public void setQuotaValue(float f) {
		this.quotaValue = f;
	}

	public float getRemainingValue() {
		return remainingValue;
	}

	public void setRemainingValue(float remainingValue) {
		this.remainingValue = remainingValue;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
