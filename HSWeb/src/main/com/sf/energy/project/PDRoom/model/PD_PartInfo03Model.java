package com.sf.energy.project.PDRoom.model;

/**
 * 回路详细参数Model
 * @author Administrator
 *
 */
public class PD_PartInfo03Model {
	
	//配置类型
	private String PartStyle="";
	//配置类型ID
	private String PartStyle_ID="";
	//父配置节点ID
	private int Part_Parent=0;
	//父配置节点名称
	private String part_Parent_name="";
	//设备状态
	private String State="";
	//编号
	private String Part_Num="";
	//名称
	private String Part_Name="";
	
	//是否是母线
	private double ISMULINE=0;
	//生产厂家
	private String Part_SCCJ="";
	//投运日期
	private String Part_TYRQ="";
	//备注
	private String Part_Remark="";
	//父节点
	private String Part_IDS="";
	
	
	
	
	
	
	//ID
	private int PartInfo_ID=0;
	//配置节点ID
	private int Part_ID=0;
	//计量设备类型，默认为0，多功能表
	private int JLLX=0;
	//计量设备ID
	private String JLID="";
	//计量设备名称
	private String  JLNAME = "";

	//工作频率
	private double GZPL=0;
	//额定功率
	private double ZEDGL=0;	
	private double AEDGL=0;
	private double BEDGL=0;
	private double CEDGL=0;
	
	//额定电压
	private double EDDY=0;
	//电压比
	private double DYB=0;
	//空载电流
	private double KZDL=0;
	//空载损耗
	private double KZSH=0;
	//效率
	private double XL=0;
	//电压上线（伏）
	private double ADYSX=0;
	private double BDYSX=0;
	private double CDYSX=0;
	private double DYSXL=0;
	//电压下线（伏）
	private double ADYXX=0;
	private double BDYXX=0;
	private double CDYXX=0;
	private double DYXXL=0;
	//限额电流（安培）
	private double AXEDL=0;
	private double BXEDL=0;
	private double CXEDL=0;
	private double XEDLL=0;
	//限额无功（千乏）
	private double ZXEWG=0;
	private double AXEWG=0;
	private double BXEWG=0;
	private double CXEWG=0;
	//功率因素
	private double GLYS=0;
	//线路截面
	private double Jiemian=0;
	//线路长度
	private int  Length=0;
	//起始地址
	private String StartAddress="";
	//结束地址
	private String EndAddress="";
	
	private int AutomaticCut=0;
	
	private double DLBalance=0;
	
	private double DYBalance=0;
	
	private double YGBalance=0;
	
	private double WGBalance=0;


	
	public String getJLNAME()
	{
		return JLNAME;
	}

	public void setJLNAME(String jLNAME)
	{
		JLNAME = jLNAME;
	}
	
	public int getPartInfo_ID() {
		return PartInfo_ID;
	}

	public void setPartInfo_ID(int partInfo_ID) {
		PartInfo_ID = partInfo_ID;
	}

	public int getPart_ID() {
		return Part_ID;
	}

	public void setPart_ID(int part_ID) {
		Part_ID = part_ID;
	}

	public int getJLLX() {
		return JLLX;
	}

	public void setJLLX(int jLLX) {
		JLLX = jLLX;
	}

	public String getJLID() {
		return JLID;
	}

	public void setJLID(String jLID) {
		JLID = jLID;
	}

	public double getGZPL() {
		return GZPL;
	}

	public void setGZPL(double gZPL) {
		GZPL = gZPL;
	}

	public double getZEDGL() {
		return ZEDGL;
	}

	public void setZEDGL(double zEDGL) {
		ZEDGL = zEDGL;
	}

	public double getAEDGL() {
		return AEDGL;
	}

	public void setAEDGL(double aEDGL) {
		AEDGL = aEDGL;
	}

	public double getBEDGL() {
		return BEDGL;
	}

	public void setBEDGL(double bEDGL) {
		BEDGL = bEDGL;
	}

	public double getCEDGL() {
		return CEDGL;
	}

	public void setCEDGL(double cEDGL) {
		CEDGL = cEDGL;
	}

	public double getEDDY() {
		return EDDY;
	}

	public void setEDDY(double eDDY) {
		EDDY = eDDY;
	}

	public double getDYB() {
		return DYB;
	}

	public void setDYB(double dYB) {
		DYB = dYB;
	}

	public double getKZDL() {
		return KZDL;
	}

	public void setKZDL(double kZDL) {
		KZDL = kZDL;
	}

	public double getKZSH() {
		return KZSH;
	}

	public void setKZSH(double kZSH) {
		KZSH = kZSH;
	}

	public double getXL() {
		return XL;
	}

	public void setXL(double xL) {
		XL = xL;
	}

	public double getADYSX() {
		return ADYSX;
	}

	public void setADYSX(double aDYSX) {
		ADYSX = aDYSX;
	}

	public double getBDYSX() {
		return BDYSX;
	}

	public void setBDYSX(double bDYSX) {
		BDYSX = bDYSX;
	}

	public double getCDYSX() {
		return CDYSX;
	}

	public void setCDYSX(double cDYSX) {
		CDYSX = cDYSX;
	}
	public double getDYSXL() {
		return DYSXL;
	}

	public void setDYSXL(double dYSXL) {
		DYSXL = dYSXL;
	}
	public double getADYXX() {
		return ADYXX;
	}

	public void setADYXX(double aDYXX) {
		ADYXX = aDYXX;
	}
	public double getDYXXL() {
		return DYXXL;
	}

	public void setDYXXL(double dYXXL) {
		DYXXL = dYXXL;
	}
	public double getBDYXX() {
		return BDYXX;
	}

	public void setBDYXX(double bDYXX) {
		BDYXX = bDYXX;
	}

	public double getCDYXX() {
		return CDYXX;
	}

	public void setCDYXX(double cDYXX) {
		CDYXX = cDYXX;
	}

	public double getAXEDL() {
		return AXEDL;
	}

	public void setAXEDL(double aXEDL) {
		AXEDL = aXEDL;
	}

	public double getBXEDL() {
		return BXEDL;
	}

	public void setBXEDL(double bXEDL) {
		BXEDL = bXEDL;
	}

	public double getCXEDL() {
		return CXEDL;
	}

	public void setCXEDL(double cXEDL) {
		CXEDL = cXEDL;
	}
	public double getXEDLL() {
		return XEDLL;
	}

	public void setXEDLL(double xEDLL) {
		XEDLL = xEDLL;
	}
	public double getZXEWG() {
		return ZXEWG;
	}

	public void setZXEWG(double zXEWG) {
		ZXEWG = zXEWG;
	}

	public double getAXEWG() {
		return AXEWG;
	}

	public void setAXEWG(double aXEWG) {
		AXEWG = aXEWG;
	}

	public double getBXEWG() {
		return BXEWG;
	}

	public void setBXEWG(double bXEWG) {
		BXEWG = bXEWG;
	}

	public double getCXEWG() {
		return CXEWG;
	}

	public void setCXEWG(double cXEWG) {
		CXEWG = cXEWG;
	}

	public double getGLYS() {
		return GLYS;
	}

	public void setGLYS(double gLYS) {
		GLYS = gLYS;
	}

	public double getJiemian() {
		return Jiemian;
	}

	public void setJiemian(double jiemian) {
		Jiemian = jiemian;
	}

	public int getLength() {
		return Length;
	}

	public void setLength(int length) {
		Length = length;
	}

	public String getStartAddress() {
		return StartAddress;
	}

	public void setStartAddress(String startAddress) {
		StartAddress = startAddress;
	}

	public String getEndAddress() {
		return EndAddress;
	}

	public void setEndAddress(String endAddress) {
		EndAddress = endAddress;
	}

	public int getAutomaticCut() {
		return AutomaticCut;
	}

	public void setAutomaticCut(int automaticCut) {
		AutomaticCut = automaticCut;
	}

	public double getDLBalance() {
		return DLBalance;
	}

	public void setDLBalance(double dLBalance) {
		DLBalance = dLBalance;
	}

	public double getDYBalance() {
		return DYBalance;
	}

	public void setDYBalance(double dYBalance) {
		DYBalance = dYBalance;
	}

	public double getYGBalance() {
		return YGBalance;
	}

	public void setYGBalance(double yGBalance) {
		YGBalance = yGBalance;
	}

	public double getWGBalance() {
		return WGBalance;
	}

	public void setWGBalance(double wGBalance) {
		WGBalance = wGBalance;
	}

	public String getPartStyle() {
		return PartStyle;
	}

	public void setPartStyle(String partStyle) {
		PartStyle = partStyle;
	}

	public String getPartStyle_ID() {
		return PartStyle_ID;
	}

	public void setPartStyle_ID(String partStyle_ID) {
		PartStyle_ID = partStyle_ID;
	}

	public int getPart_Parent() {
		return Part_Parent;
	}

	public void setPart_Parent(int part_Parent) {
		Part_Parent = part_Parent;
	}

	public String getPart_Parent_name() {
		return part_Parent_name;
	}

	public void setPart_Parent_name(String part_Parent_name) {
		this.part_Parent_name = part_Parent_name;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getPart_Num() {
		return Part_Num;
	}

	public void setPart_Num(String part_Num) {
		Part_Num = part_Num;
	}

	public String getPart_Name() {
		return Part_Name;
	}

	public void setPart_Name(String part_Name) {
		Part_Name = part_Name;
	}
	public double getISMULINE() {
		return ISMULINE;
	}

	public void setISMULINE(double isMULINE) {
		ISMULINE = isMULINE;
	}
	public String getPart_SCCJ() {
		return Part_SCCJ;
	}

	public void setPart_SCCJ(String part_SCCJ) {
		Part_SCCJ = part_SCCJ;
	}

	public String getPart_TYRQ() {
		return Part_TYRQ;
	}

	public void setPart_TYRQ(String part_TYRQ) {
		Part_TYRQ = part_TYRQ;
	}

	public String getPart_Remark() {
		return Part_Remark;
	}

	public void setPart_Remark(String part_Remark) {
		Part_Remark = part_Remark;
	}

	public String getPart_IDS() {
		return Part_IDS;
	}

	public void setPart_IDS(String part_IDS) {
		Part_IDS = part_IDS;
	}
	
	
	
	
	
}
