package com.sf.energy.project.system.model;

public class BldFinalAccountsModel
{
	// /账套ID
	public int BILLID = 0;
	// /结算流水号
	public int SERIALNO = 0;
	// /结算年份
	public String YEAR = "";
	// /结算开始月
	public int STARTMM = 0;
	// /结算结束月
	public int ENDMM = 0;
	// /结算开始年月日
	public String STARTYMD = "";
	// /结算结束年月日
	public String ENDYMD = "";
	// /上期结算流水号
	public int LASTSERIALNO = 0;
	// /备注
	public String REMARK = "";
	// /结算人
	public int USERID = 0;
	// /结算时间
	public String SYSTEMTIME = "";
	// /结算电表数量
	public int AMMETERCNT = 0;

	public int getBILLID()
	{
		return BILLID;
	}

	public void setBILLID(int bILLID)
	{
		BILLID = bILLID;
	}

	public int getSERIALNO()
	{
		return SERIALNO;
	}

	public void setSERIALNO(int sERIALNO)
	{
		SERIALNO = sERIALNO;
	}

	public String getYEAR()
	{
		return YEAR;
	}

	public void setYEAR(String yEAR)
	{
		YEAR = yEAR;
	}

	public int getSTARTMM()
	{
		return STARTMM;
	}

	public void setSTARTMM(int sTARTMM)
	{
		STARTMM = sTARTMM;
	}

	public int getENDMM()
	{
		return ENDMM;
	}

	public void setENDMM(int eNDMM)
	{
		ENDMM = eNDMM;
	}

	public String getSTARTYMD()
	{
		return STARTYMD;
	}

	public void setSTARTYMD(String sTARTYMD)
	{
		STARTYMD = sTARTYMD;
	}

	public String getENDYMD()
	{
		return ENDYMD;
	}

	public void setENDYMD(String eNDYMD)
	{
		ENDYMD = eNDYMD;
	}

	public int getLASTSERIALNO()
	{
		return LASTSERIALNO;
	}

	public void setLASTSERIALNO(int lASTSERIALNO)
	{
		LASTSERIALNO = lASTSERIALNO;
	}

	public String getREMARK()
	{
		return REMARK;
	}

	public void setREMARK(String rEMARK)
	{
		REMARK = rEMARK;
	}

	public int getUSERID()
	{
		return USERID;
	}

	public void setUSERID(int uSERID)
	{
		USERID = uSERID;
	}

	public String getSYSTEMTIME()
	{
		return SYSTEMTIME;
	}

	public void setSYSTEMTIME(String sYSTEMTIME)
	{
		SYSTEMTIME = sYSTEMTIME;
	}

	public int getAMMETERCNT()
	{
		return AMMETERCNT;
	}

	public void setAMMETERCNT(int aMMETERCNT)
	{
		AMMETERCNT = aMMETERCNT;
	}

}
