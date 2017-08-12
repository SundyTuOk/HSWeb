package com.sf.energy.powernet.model;

/**
 * PDPartInfo03回路信息model
 * @author yanhao
 *
 */
public class PDPartInfo03HLModel
{
	// 回路ID
	public int PartInfo_ID = 0;

	// 配置节点ID
	public int Part_ID = 0;

	// 计量设备类型，默认为0，多功能表
	public int JLLX = 0;

	// 计量设备ID
	public int JLID = 0;

	// 工作频率
	public float GZPL = 0;

	// 额定功率：总
	public float ZEDGL = 0;

	// 额定功率：A
	public float AEDGL = 0;

	// 额定功率：B
	public float BEDGL = 0;

	// 额定功率：C
	public float CEDGL = 0;

	// 额定功率：D
	public float EDGL = 0;

	// 额定电压
	public float EDDY = 0;

	// 电压比
	public float DYB = 0;

	// 空载电流
	public float KZDL = 0;

	// 空载损耗
	public float KZSH = 0;

	// 效率
	public float XL = 0;

	// 电压上线：A
	public float ADYSX = 0;

	// 电压上线：B
	public float BDYSX = 0;

	// 电压上线：C
	public float CDYSX = 0;

	// 电压下线：A
	public float ADYXX = 0;

	// 电压下线：B
	public float BDYXX = 0;

	// 电压下线：C
	public float CDYXX = 0;

	// 限额电流：A
	public float AXEDL = 0;

	// 限额电流：B
	public float BXEDL = 0;

	// 限额电流：C
	public float CXEDL = 0;

	// 限额电无功：总
	public float ZXEWG = 0;

	// 限额无功：A
	public float AXEWG = 0;

	// 限额无功：B
	public float BXEWG = 0;

	// 限额无功：C
	public float CXEWG = 0;

	// 功率因素
	public float GLYS = 0;

	// 起始位置
	public String STARTADDRESS = "";

	// 终止位置
	public float ENDADDRESS = 0;

	// 截面积
	public String JIEMIAN = "";

	// 线路长度
	public float LENGTH = 0;

	//
	public float AUTOMATICCUT = 0;

	//
	public float DLBALANCE = 0;

	//
	public float DYBALANCE = 0;

	//
	public float YGBALANCE = 0;

	//
	public float WGBALANCE = 0;

	public int getPartInfo_ID()
	{
		return PartInfo_ID;
	}

	public void setPartInfo_ID(int partInfo_ID)
	{
		PartInfo_ID = partInfo_ID;
	}

	public int getPart_ID()
	{
		return Part_ID;
	}

	public void setPart_ID(int part_ID)
	{
		Part_ID = part_ID;
	}

	public int getJLLX()
	{
		return JLLX;
	}

	public void setJLLX(int jLLX)
	{
		JLLX = jLLX;
	}

	public int getJLID()
	{
		return JLID;
	}

	public void setJLID(int jLID)
	{
		JLID = jLID;
	}

	public float getGZPL()
	{
		return GZPL;
	}

	public void setGZPL(float gZPL)
	{
		GZPL = gZPL;
	}

	public float getZEDGL()
	{
		return ZEDGL;
	}

	public void setZEDGL(float zEDGL)
	{
		ZEDGL = zEDGL;
	}

	public float getAEDGL()
	{
		return AEDGL;
	}

	public void setAEDGL(float aEDGL)
	{
		AEDGL = aEDGL;
	}

	public float getBEDGL()
	{
		return BEDGL;
	}

	public void setBEDGL(float bEDGL)
	{
		BEDGL = bEDGL;
	}

	public float getCEDGL()
	{
		return CEDGL;
	}

	public void setCEDGL(float cEDGL)
	{
		CEDGL = cEDGL;
	}

	public float getEDGL()
	{
		return EDGL;
	}

	public void setEDGL(float eDGL)
	{
		EDGL = eDGL;
	}

	public float getEDDY()
	{
		return EDDY;
	}

	public void setEDDY(float eDDY)
	{
		EDDY = eDDY;
	}

	public float getDYB()
	{
		return DYB;
	}

	public void setDYB(float dYB)
	{
		DYB = dYB;
	}

	public float getKZDL()
	{
		return KZDL;
	}

	public void setKZDL(float kZDL)
	{
		KZDL = kZDL;
	}

	public float getKZSH()
	{
		return KZSH;
	}

	public void setKZSH(float kZSH)
	{
		KZSH = kZSH;
	}

	public float getXL()
	{
		return XL;
	}

	public void setXL(float xL)
	{
		XL = xL;
	}

	public float getADYSX()
	{
		return ADYSX;
	}

	public void setADYSX(float aDYSX)
	{
		ADYSX = aDYSX;
	}

	public float getBDYSX()
	{
		return BDYSX;
	}

	public void setBDYSX(float bDYSX)
	{
		BDYSX = bDYSX;
	}

	public float getCDYSX()
	{
		return CDYSX;
	}

	public void setCDYSX(float cDYSX)
	{
		CDYSX = cDYSX;
	}

	public float getADYXX()
	{
		return ADYXX;
	}

	public void setADYXX(float aDYXX)
	{
		ADYXX = aDYXX;
	}

	public float getBDYXX()
	{
		return BDYXX;
	}

	public void setBDYXX(float bDYXX)
	{
		BDYXX = bDYXX;
	}

	public float getCDYXX()
	{
		return CDYXX;
	}

	public void setCDYXX(float cDYXX)
	{
		CDYXX = cDYXX;
	}

	public float getAXEDL()
	{
		return AXEDL;
	}

	public void setAXEDL(float aXEDL)
	{
		AXEDL = aXEDL;
	}

	public float getBXEDL()
	{
		return BXEDL;
	}

	public void setBXEDL(float bXEDL)
	{
		BXEDL = bXEDL;
	}

	public float getCXEDL()
	{
		return CXEDL;
	}

	public void setCXEDL(float cXEDL)
	{
		CXEDL = cXEDL;
	}

	public float getZXEWG()
	{
		return ZXEWG;
	}

	public void setZXEWG(float zXEWG)
	{
		ZXEWG = zXEWG;
	}

	public float getAXEWG()
	{
		return AXEWG;
	}

	public void setAXEWG(float aXEWG)
	{
		AXEWG = aXEWG;
	}

	public float getBXEWG()
	{
		return BXEWG;
	}

	public void setBXEWG(float bXEWG)
	{
		BXEWG = bXEWG;
	}

	public float getCXEWG()
	{
		return CXEWG;
	}

	public void setCXEWG(float cXEWG)
	{
		CXEWG = cXEWG;
	}

	public float getGLYS()
	{
		return GLYS;
	}

	public void setGLYS(float gLYS)
	{
		GLYS = gLYS;
	}

	public String getSTARTADDRESS()
	{
		return STARTADDRESS;
	}

	public void setSTARTADDRESS(String sTARTADDRESS)
	{
		STARTADDRESS = sTARTADDRESS;
	}

	public float getENDADDRESS()
	{
		return ENDADDRESS;
	}

	public void setENDADDRESS(float eNDADDRESS)
	{
		ENDADDRESS = eNDADDRESS;
	}

	public String getJIEMIAN()
	{
		return JIEMIAN;
	}

	public void setJIEMIAN(String jIEMIAN)
	{
		JIEMIAN = jIEMIAN;
	}

	public float getLENGTH()
	{
		return LENGTH;
	}

	public void setLENGTH(float lENGTH)
	{
		LENGTH = lENGTH;
	}

	public float getAUTOMATICCUT()
	{
		return AUTOMATICCUT;
	}

	public void setAUTOMATICCUT(float aUTOMATICCUT)
	{
		AUTOMATICCUT = aUTOMATICCUT;
	}

	public float getDLBALANCE()
	{
		return DLBALANCE;
	}

	public void setDLBALANCE(float dLBALANCE)
	{
		DLBALANCE = dLBALANCE;
	}

	public float getDYBALANCE()
	{
		return DYBALANCE;
	}

	public void setDYBALANCE(float dYBALANCE)
	{
		DYBALANCE = dYBALANCE;
	}

	public float getYGBALANCE()
	{
		return YGBALANCE;
	}

	public void setYGBALANCE(float yGBALANCE)
	{
		YGBALANCE = yGBALANCE;
	}

	public float getWGBALANCE()
	{
		return WGBALANCE;
	}

	public void setWGBALANCE(float wGBALANCE)
	{
		WGBALANCE = wGBALANCE;
	}

	
	
	
}
