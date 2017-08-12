package com.sf.energy.project.PDRoom.model;

/**
 * 配电房详细信息
 * @author Administrator
 *
 */
public class PD_PartInfo01Model {
	//ID
	private int PartInfo_ID;
	//配置节点ID
	private int Part_ID;
	//详细地址
	private String Address;
	//电压等级
	private String DYLevel;
	//数据地址
	private String XMLNAME;
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
	
}
