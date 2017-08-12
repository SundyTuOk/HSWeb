package com.sf.energy.project.PDRoom.model;

/**
 * 变压器详细参数Model类
 * @author Administrator
 *
 */
public class PD_PartInfo02Model {
	
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
	//生产厂家
	private String Part_SCCJ="";
	//搬运日期
	private String Part_TYRQ="";
	//备注
	private String Part_Remark="";
	//父节点
	private String Part_IDS="";
	
	
	//ID
	private int PartInfo_ID;
	//配置节点ID
	private int Part_ID;
	//负责人
	private String Man;
	//负责人联系方式
	private String ManPhone;
	//工作频率
	private String GZPL;
	//额定功率
	private String EDGL;
	//额定电压
	private String EDDY;
	//电压比
	private String DYB;
	//空载电流
	private String KZDL;
	//空载损耗
	private String KZSH;
	//效率
	private String XL;
	//电压上线（伏）
	private String DYSX;
	//电压下线（伏）
	private String DYXX;
	//限额电流（安培）
	private String XEDL;
	//限额无功（千乏）
	private String XEWG;
	//功率因素
	private String GLYS;
	
	
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
	public String getMan() {
		return Man;
	}
	public void setMan(String man) {
		Man = man;
	}
	public String getManPhone() {
		return ManPhone;
	}
	public void setManPhone(String manPhone) {
		ManPhone = manPhone;
	}
	public String getGZPL() {
		return GZPL;
	}
	public void setGZPL(String gZPL) {
		GZPL = gZPL;
	}
	public String getEDGL() {
		return EDGL;
	}
	public void setEDGL(String eDGL) {
		EDGL = eDGL;
	}
	public String getEDDY() {
		return EDDY;
	}
	public void setEDDY(String eDDY) {
		EDDY = eDDY;
	}
	public String getDYB() {
		return DYB;
	}
	public void setDYB(String dYB) {
		DYB = dYB;
	}
	public String getKZDL() {
		return KZDL;
	}
	public void setKZDL(String kZDL) {
		KZDL = kZDL;
	}
	public String getKZSH() {
		return KZSH;
	}
	public void setKZSH(String kZSH) {
		KZSH = kZSH;
	}
	public String getXL() {
		return XL;
	}
	public void setXL(String xL) {
		XL = xL;
	}
	public String getDYSX() {
		return DYSX;
	}
	public void setDYSX(String dYSX) {
		DYSX = dYSX;
	}
	public String getDYXX() {
		return DYXX;
	}
	public void setDYXX(String dYXX) {
		DYXX = dYXX;
	}
	public String getXEDL() {
		return XEDL;
	}
	public void setXEDL(String xEDL) {
		XEDL = xEDL;
	}
	public String getXEWG() {
		return XEWG;
	}
	public void setXEWG(String xEWG) {
		XEWG = xEWG;
	}
	public String getGLYS() {
		return GLYS;
	}
	public void setGLYS(String gLYS) {
		GLYS = gLYS;
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
