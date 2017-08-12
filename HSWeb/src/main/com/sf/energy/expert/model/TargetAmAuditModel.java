package com.sf.energy.expert.model;

/**
 * 表TargetAmAudit实体类
 * @author yanhao
 *
 */
public class TargetAmAuditModel
{
	private int auditID=0;
	private int targetID=0;
	private float m1=0;
	private float m2=0;
	private float m3=0;
	private float m4=0;
	private float m5=0;
	private float m6=0;
	private float m7=0;
	private float m8=0;
	private float m9=0;
	private float m10=0;
	private float m11=0;
	private float m12=0;
	private String targetManName=" ";
	private String targetTime=" ";
	private String targetState=" ";
	private	String targetLastTime=" ";
	private String archName=" ";
	private int archID=0;
	private String  targetFenlei=" ";
	private int targetYear=0;
 	
	//为了2个合并分页，假的字段标识电的还是水的
	private String meterType="";
	
	
	
	public String getMeterType()
	{
		return meterType;
	}
	public void setMeterType(String meterType)
	{
		this.meterType = meterType;
	}
	public String getTargetLastTime()
	{
		return targetLastTime;
	}
	public void setTargetLastTime(String targetLastTime)
	{
		this.targetLastTime = targetLastTime;
	}
	public int getTargetYear()
	{
		return targetYear;
	}
	public void setTargetYear(int targetYear)
	{
		this.targetYear = targetYear;
	}
	public String getTargetFenlei()
	{
		return targetFenlei;
	}
	public void setTargetFenlei(String targetFenlei)
	{
		this.targetFenlei = targetFenlei;
	}
	public String getArchName()
	{
		return archName;
	}
	public void setArchName(String archName)
	{
		this.archName = archName;
	}
	public int getArchID()
	{
		return archID;
	}
	public void setArchID(int archID)
	{
		this.archID = archID;
	}
	public int getAuditID()
	{
		return auditID;
	}
	public void setAuditID(int auditID)
	{
		this.auditID = auditID;
	}
	public int getTargetID()
	{
		return targetID;
	}
	public void setTargetID(int targetID)
	{
		this.targetID = targetID;
	}
	public float getM1()
	{
		return m1;
	}
	public void setM1(float m1)
	{
		this.m1 = m1;
	}
	public float getM2()
	{
		return m2;
	}
	public void setM2(float m2)
	{
		this.m2 = m2;
	}
	public float getM3()
	{
		return m3;
	}
	public void setM3(float m3)
	{
		this.m3 = m3;
	}
	public float getM4()
	{
		return m4;
	}
	public void setM4(float m4)
	{
		this.m4 = m4;
	}
	public float getM5()
	{
		return m5;
	}
	public void setM5(float m5)
	{
		this.m5 = m5;
	}
	public float getM6()
	{
		return m6;
	}
	public void setM6(float m6)
	{
		this.m6 = m6;
	}
	public float getM7()
	{
		return m7;
	}
	public void setM7(float m7)
	{
		this.m7 = m7;
	}
	public float getM8()
	{
		return m8;
	}
	public void setM8(float m8)
	{
		this.m8 = m8;
	}
	public float getM9()
	{
		return m9;
	}
	public void setM9(float m9)
	{
		this.m9 = m9;
	}
	public float getM10()
	{
		return m10;
	}
	public void setM10(float m10)
	{
		this.m10 = m10;
	}
	public float getM11()
	{
		return m11;
	}
	public void setM11(float m11)
	{
		this.m11 = m11;
	}
	public float getM12()
	{
		return m12;
	}
	public void setM12(float m12)
	{
		this.m12 = m12;
	}
	public String getTargetManName()
	{
		return targetManName;
	}
	public void setTargetManName(String targetManName)
	{
		this.targetManName = targetManName;
	}
	public String getTargetTime()
	{
		return targetTime;
	}
	public void setTargetTime(String targetTime)
	{
		this.targetTime = targetTime;
	}
	public String getTargetState()
	{
		return targetState;
	}
	public void setTargetState(String targetState)
	{
		this.targetState = targetState;
	}
	
	
}
