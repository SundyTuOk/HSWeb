package com.sf.energy.powernet.model;

import java.util.Date;

public class AmMeterPDDatasModel
{

	// ID
	private int AmMeter_ID;
	// 抄读时间
	private String ValueTime;

	private Date recordTime;

	
	// 当前总有功功率 kW
	private double PowerZY;
	// 当前A 相有功功率 kW
	private double PowerAY;
	// 当前B 相有功功率 kW
	private double PowerBY;
	// 当前C 相有功功率 kW
	private double PowerCY;

	// 当前总无功功率 kW
	private double PowerZW;
	// 当前A 相无功功率 kW
	private double PowerAW;
	// 当前B 相无功功率 kW
	private double PowerBW;
	// 当前C 相无功功率 kW
	private double PowerCW;

	// 当前总功率因数％
	private double PowerFactorZ;
	// 当前A 相功率因数％
	private double PowerFactorA;
	// 当前B 相功率因数％
	private double PowerFactorB;
	// 当前C 相功率因数％
	private double PowerFactorC;

	// 当前A 相电压 V
	private double VoltageA;
	// 当前B 相电压 V
	private double VoltageB;
	// 当前C 相电压 V
	private double VoltageC;

	// 当前A 相电流 A
	private double CurrentA;
	// 当前B 相电流 A
	private double CurrentB;
	// 当前C 相电流 A
	private double CurrentC;
	// 当前零序电流 A
	private double Current0;

	// 当前总视在功率 kVA
	private double PowerSZZ;
	// 当前A 相视在功率 kVA
	private double PowerSZA;
	// 当前B 相视在功率 kVA
	private double PowerSZB;
	// 当前C 相视在功率 kVA
	private double PowerSZC;

	private float AnobalanceRate;// 電流不平衡率
	private int count;
	private String time;

	
	
	
	//截取时分秒时间
	private String subTime="";//配网概况中用到
	
	public String getSubTime()
	{
		return subTime;
	}

	public void setSubTime(String subTime)
	{
		this.subTime = subTime;
	}

	public Date getRecordTime()
	{
		return recordTime;
	}

	public void setRecordTime(Date recordTime)
	{
		this.recordTime = recordTime;
	}

	public float getAnobalanceRate()
	{
		return AnobalanceRate;
	}

	public void setAnobalanceRate(float anobalanceRate)
	{
		AnobalanceRate = anobalanceRate;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getAmMeter_ID()
	{
		return AmMeter_ID;
	}

	public void setAmMeter_ID(int amMeter_ID)
	{
		AmMeter_ID = amMeter_ID;
	}

	public String getValueTime()
	{
		return ValueTime;
	}

	public void setValueTime(String valueTime)
	{
		ValueTime = valueTime;
	}

	public double getPowerZY()
	{
		return PowerZY;
	}

	public void setPowerZY(double powerZY)
	{
		PowerZY = powerZY;
	}

	public double getPowerAY()
	{
		return PowerAY;
	}

	public void setPowerAY(double powerAY)
	{
		PowerAY = powerAY;
	}

	public double getPowerBY()
	{
		return PowerBY;
	}

	public void setPowerBY(double powerBY)
	{
		PowerBY = powerBY;
	}

	public double getPowerCY()
	{
		return PowerCY;
	}

	public void setPowerCY(double powerCY)
	{
		PowerCY = powerCY;
	}

	public double getPowerZW()
	{
		return PowerZW;
	}

	public void setPowerZW(double powerZW)
	{
		PowerZW = powerZW;
	}

	public double getPowerAW()
	{
		return PowerAW;
	}

	public void setPowerAW(double powerAW)
	{
		PowerAW = powerAW;
	}

	public double getPowerBW()
	{
		return PowerBW;
	}

	public void setPowerBW(double powerBW)
	{
		PowerBW = powerBW;
	}

	public double getPowerCW()
	{
		return PowerCW;
	}

	public void setPowerCW(double powerCW)
	{
		PowerCW = powerCW;
	}

	public double getPowerFactorZ()
	{
		return PowerFactorZ;
	}

	public void setPowerFactorZ(double powerFactorZ)
	{
		PowerFactorZ = powerFactorZ;
	}

	public double getPowerFactorA()
	{
		return PowerFactorA;
	}

	public void setPowerFactorA(double powerFactorA)
	{
		PowerFactorA = powerFactorA;
	}

	public double getPowerFactorB()
	{
		return PowerFactorB;
	}

	public void setPowerFactorB(double powerFactorB)
	{
		PowerFactorB = powerFactorB;
	}

	public double getPowerFactorC()
	{
		return PowerFactorC;
	}

	public void setPowerFactorC(double powerFactorC)
	{
		PowerFactorC = powerFactorC;
	}

	public double getVoltageA()
	{
		return VoltageA;
	}

	public void setVoltageA(double voltageA)
	{
		VoltageA = voltageA;
	}

	public double getVoltageB()
	{
		return VoltageB;
	}

	public void setVoltageB(double voltageB)
	{
		VoltageB = voltageB;
	}

	public double getVoltageC()
	{
		return VoltageC;
	}

	public void setVoltageC(double voltageC)
	{
		VoltageC = voltageC;
	}

	public double getCurrentA()
	{
		return CurrentA;
	}

	public void setCurrentA(double currentA)
	{
		CurrentA = currentA;
	}

	public double getCurrentB()
	{
		return CurrentB;
	}

	public void setCurrentB(double currentB)
	{
		CurrentB = currentB;
	}

	public double getCurrentC()
	{
		return CurrentC;
	}

	public void setCurrentC(double currentC)
	{
		CurrentC = currentC;
	}

	public double getCurrent0()
	{
		return Current0;
	}

	public void setCurrent0(double current0)
	{
		Current0 = current0;
	}

	public double getPowerSZZ()
	{
		return PowerSZZ;
	}

	public void setPowerSZZ(double powerSZZ)
	{
		PowerSZZ = powerSZZ;
	}

	public double getPowerSZA()
	{
		return PowerSZA;
	}

	public void setPowerSZA(double powerSZA)
	{
		PowerSZA = powerSZA;
	}

	public double getPowerSZB()
	{
		return PowerSZB;
	}

	public void setPowerSZB(double powerSZB)
	{
		PowerSZB = powerSZB;
	}

	public double getPowerSZC()
	{
		return PowerSZC;
	}

	public void setPowerSZC(double powerSZC)
	{
		PowerSZC = powerSZC;
	}

}
