package com.sf.energy.project.PDRoom.model;

/**
 * 配电网配置
 * @author Administrator
 *
 */
public class PD_PartModel {
	//ID
	private int Part_ID=0;
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
	
	
	//详细地址
    private String Address;
	//电压等级
	private String DYLevel;
	//数据地址
	private String XMLNAME;
	
	public int getPart_ID() {
		return Part_ID;
	}
	public void setPart_ID(int part_ID) {
		Part_ID = part_ID;
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
	public String getPartStyle() {
		return PartStyle;
	}
	public void setPartStyle(String partStyle) {
		PartStyle = partStyle;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getDYLevel() {
		return DYLevel;
	}
	public void setDYLevel(String dYLevel) {
		DYLevel = dYLevel;
	}
	public String getXMLNAME() {
		return XMLNAME;
	}
	public void setXMLNAME(String xMLNAME) {
		XMLNAME = xMLNAME;
	}
	public String getPart_IDS() {
		return Part_IDS;
	}
	public void setPart_IDS(String part_IDS) {
		Part_IDS = part_IDS;
	}
	public String getPart_Parent_name() {
		return part_Parent_name;
	}
	public void setPart_Parent_name(String part_Parent_name) {
		this.part_Parent_name = part_Parent_name;
	}
	
	
}
