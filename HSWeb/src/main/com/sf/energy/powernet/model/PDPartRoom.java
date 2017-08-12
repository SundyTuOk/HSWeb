package com.sf.energy.powernet.model;

import javax.management.loading.PrivateClassLoader;

public class PDPartRoom
{
	private String Part_Name="";
	private String XMLName="";
	private int part_id=0;
	private String Part_Num="";
	private String partStyle_ID="";
	public String getPart_Name()
	{
		return Part_Name;
	}
	public void setPart_Name(String part_Name)
	{
		Part_Name = part_Name;
	}
	public String getXMLName()
	{
		return XMLName;
	}
	public void setXMLName(String xMLName)
	{
		XMLName = xMLName;
	}
	public int getPart_id()
	{
		return part_id;
	}
	public void setPart_id(int part_id)
	{
		this.part_id = part_id;
	}
	public String getPart_Num()
	{
		return Part_Num;
	}
	public void setPart_Num(String part_Num)
	{
		Part_Num = part_Num;
	}
	public String getPartStyle_ID()
	{
		return partStyle_ID;
	}
	public void setPartStyle_ID(String partStyle_ID)
	{
		this.partStyle_ID = partStyle_ID;
	}
	
}
