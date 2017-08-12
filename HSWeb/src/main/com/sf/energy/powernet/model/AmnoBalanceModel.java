package com.sf.energy.powernet.model;

public class AmnoBalanceModel {
	
	private float CURRENTA;//A相电流
	private float CURRENTB;//B相电流
	private float CURRENTC;//C相电流
	private String Valuetime;//数据时间点
	private float  NobalanceRate;//电流不平衡率
	
	
	public float getCURRENTA() {
		return CURRENTA;
	}
	public void setCURRENTA(float cURRENTA) {
		CURRENTA = cURRENTA;
	}
	public float getCURRENTB() {
		return CURRENTB;
	}
	public void setCURRENTB(float cURRENTB) {
		CURRENTB = cURRENTB;
	}
	public float getCURRENTC() {
		return CURRENTC;
	}
	public void setCURRENTC(float cURRENTC) {
		CURRENTC = cURRENTC;
	}
	public String getValuetime() {
		return Valuetime;
	}
	public void setValuetime(String valuetime) {
		Valuetime = valuetime;
	}
	public float getNobalanceRate() {
		return NobalanceRate;
	}
	public void setNobalanceRate(float nobalanceRate) {
		NobalanceRate = nobalanceRate;
	}
	
	
}
