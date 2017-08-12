package com.sf.energy.prepayment.model;

public class AmmeterJieSuan
{
	Integer ID;
	Integer AMMETER_ID;
	Float THISZVALUE;
	Float LASTZVALUE;
	String THISTIME;
	String LASTTIME;
	String JIESUANTIME;
	Float LASTREMAINMONEY;
	Float THISREMAINMONEY;
	Float USED_MONEY;
	public Integer getID()
	{
		return ID;
	}
	public void setID(Integer iD)
	{
		ID = iD;
	}
	public Integer getAMMETER_ID()
	{
		return AMMETER_ID;
	}
	public void setAMMETER_ID(Integer aMMETER_ID)
	{
		AMMETER_ID = aMMETER_ID;
	}
	public Float getTHISZVALUE()
	{
		return THISZVALUE;
	}
	public void setTHISZVALUE(Float tHISZVALUE)
	{
		THISZVALUE = tHISZVALUE;
	}
	public Float getLASTZVALUE()
	{
		return LASTZVALUE;
	}
	public void setLASTZVALUE(Float lASTZVALUE)
	{
		LASTZVALUE = lASTZVALUE;
	}
	public String getTHISTIME()
	{
		return THISTIME;
	}
	public void setTHISTIME(String tHISTIME)
	{
		THISTIME = tHISTIME;
	}
	public String getLASTTIME()
	{
		return LASTTIME;
	}
	public void setLASTTIME(String lASTTIME)
	{
		LASTTIME = lASTTIME;
	}
	public String getJIESUANTIME()
	{
		return JIESUANTIME;
	}
	public void setJIESUANTIME(String jIESUANTIME)
	{
		JIESUANTIME = jIESUANTIME;
	}
	public Float getLASTREMAINMONEY()
	{
		return LASTREMAINMONEY;
	}
	public void setLASTREMAINMONEY(Float lASTREMAINMONEY)
	{
		LASTREMAINMONEY = lASTREMAINMONEY;
	}
	public Float getTHISREMAINMONEY()
	{
		return THISREMAINMONEY;
	}
	public void setTHISREMAINMONEY(Float tHISREMAINMONEY)
	{
		THISREMAINMONEY = tHISREMAINMONEY;
	}
	public Float getUSED_MONEY()
	{
		return USED_MONEY;
	}
	public void setUSED_MONEY(Float uSED_MONEY)
	{
		USED_MONEY = uSED_MONEY;
	}
	@Override
	public String toString()
	{
		return "AmmeterJieSuan [ID=" + ID + ", AMMETER_ID=" + AMMETER_ID + ", THISZVALUE=" + THISZVALUE + ", LASTZVALUE=" + LASTZVALUE
				+ ", THISTIME=" + THISTIME + ", LASTTIME=" + LASTTIME + ", JIESUANTIME=" + JIESUANTIME + ", LASTREMAINMONEY=" + LASTREMAINMONEY
				+ ", THISREMAINMONEY=" + THISREMAINMONEY + ", USED_MONEY=" + USED_MONEY + "]";
	}
	public AmmeterJieSuan()
	{
		super();
	}
	public AmmeterJieSuan(Integer iD, Integer aMMETER_ID, Float tHISZVALUE, Float lASTZVALUE, String tHISTIME, String lASTTIME, String jIESUANTIME,
			Float lASTREMAINMONEY, Float tHISREMAINMONEY, Float uSED_MONEY)
	{
		super();
		ID = iD;
		AMMETER_ID = aMMETER_ID;
		THISZVALUE = tHISZVALUE;
		LASTZVALUE = lASTZVALUE;
		THISTIME = tHISTIME;
		LASTTIME = lASTTIME;
		JIESUANTIME = jIESUANTIME;
		LASTREMAINMONEY = lASTREMAINMONEY;
		THISREMAINMONEY = tHISREMAINMONEY;
		USED_MONEY = uSED_MONEY;
	}
	
	
}
