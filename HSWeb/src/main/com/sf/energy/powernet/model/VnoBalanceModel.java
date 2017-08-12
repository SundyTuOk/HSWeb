package com.sf.energy.powernet.model;

public class VnoBalanceModel {

	//当前A 相电压 V 
	private double VoltageA;
	//当前B 相电压 V 
	private double VoltageB;
	//当前C 相电压 V 
	private double VoltageC;
	//电压不平衡率
	private double Vrate;
	//数据时间点
	private String Valuetime;
	
	
	public double getVoltageA() {
		return VoltageA;
	}
	public void setVoltageA(double voltageA) {
		VoltageA = voltageA;
	}
	public double getVoltageB() {
		return VoltageB;
	}
	public void setVoltageB(double voltageB) {
		VoltageB = voltageB;
	}
	public double getVoltageC() {
		return VoltageC;
	}
	public void setVoltageC(double voltageC) {
		VoltageC = voltageC;
	}
	public double getVrate() {
		return Vrate;
	}
	public void setVrate(double vrate) {
		Vrate = vrate;
	}
	public String getValuetime() {
		return Valuetime;
	}
	public void setValuetime(String valuetime) {
		Valuetime = valuetime;
	}
	
	
}
