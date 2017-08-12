package com.sf.energy.expert.model;

/**
 * 节能管理专家报表导出model
 * @author Administrator
 *
 */
public class ExpertDetailExportModel
{
	private String type_Id;
	private String type_Name;
	private float zgross;
	/**
	 * 1:按部门导出
	 * 2:按电费类型导出
	 */
	private int type;
	

	public float getZgross()
	{
		return zgross;
	}
	public void setZgross(float zgross)
	{
		this.zgross = zgross;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getType_Id()
	{
		return type_Id;
	}
	public void setType_Id(String type_Id)
	{
		this.type_Id = type_Id;
	}
	public String getType_Name()
	{
		return type_Name;
	}
	public void setType_Name(String type_Name)
	{
		this.type_Name = type_Name;
	}
}
