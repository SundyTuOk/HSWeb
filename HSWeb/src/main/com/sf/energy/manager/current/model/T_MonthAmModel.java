package com.sf.energy.manager.current.model;
/**
 * 表T_MonthAm整理每月用电实体类
 * @author yanhao
 *
 */
public class T_MonthAmModel
{
	private int AMMETER_ID=0;			
	private int SELECTYEAR=0;			
	private int SELECTMONTH=0;		
	private String AMMETER_NUM="";	
	private int AREA_ID=0;
	private int ARCHITECTURE_ID=0;	
	//用电性质
	private int USEAMSTYLE=0;		
	private int PARTMENT=0;	
	private float ZGROSS=0;		
	private float ZMONEY=0;		
	private int ISCOUNTMETER=0;		
	private int ISCOMPUTATION=0;
	
	public int getAMMETER_ID()
	{
		return AMMETER_ID;
	}
	public void setAMMETER_ID(int aMMETER_ID)
	{
		AMMETER_ID = aMMETER_ID;
	}
	public int getSELECTYEAR()
	{
		return SELECTYEAR;
	}
	public void setSELECTYEAR(int sELECTYEAR)
	{
		SELECTYEAR = sELECTYEAR;
	}
	public int getSELECTMONTH()
	{
		return SELECTMONTH;
	}
	public void setSELECTMONTH(int sELECTMONTH)
	{
		SELECTMONTH = sELECTMONTH;
	}
	public String getAMMETER_NUM()
	{
		return AMMETER_NUM;
	}
	public void setAMMETER_NUM(String aMMETER_NUM)
	{
		AMMETER_NUM = aMMETER_NUM;
	}
	public int getAREA_ID()
	{
		return AREA_ID;
	}
	public void setAREA_ID(int aREA_ID)
	{
		AREA_ID = aREA_ID;
	}
	public int getARCHITECTURE_ID()
	{
		return ARCHITECTURE_ID;
	}
	public void setARCHITECTURE_ID(int aRCHITECTURE_ID)
	{
		ARCHITECTURE_ID = aRCHITECTURE_ID;
	}
	public int getUSEAMSTYLE()
	{
		return USEAMSTYLE;
	}
	public void setUSEAMSTYLE(int uSEAMSTYLE)
	{
		USEAMSTYLE = uSEAMSTYLE;
	}
	public int getPARTMENT()
	{
		return PARTMENT;
	}
	public void setPARTMENT(int pARTMENT)
	{
		PARTMENT = pARTMENT;
	}
	public float getZGROSS()
	{
		return ZGROSS;
	}
	public void setZGROSS(float zGROSS)
	{
		ZGROSS = zGROSS;
	}
	public float getZMONEY()
	{
		return ZMONEY;
	}
	public void setZMONEY(float zMONEY)
	{
		ZMONEY = zMONEY;
	}
	public int getISCOUNTMETER()
	{
		return ISCOUNTMETER;
	}
	public void setISCOUNTMETER(int iSCOUNTMETER)
	{
		ISCOUNTMETER = iSCOUNTMETER;
	}
	public int getISCOMPUTATION()
	{
		return ISCOMPUTATION;
	}
	public void setISCOMPUTATION(int iSCOMPUTATION)
	{
		ISCOMPUTATION = iSCOMPUTATION;
	}		

	
	
}
