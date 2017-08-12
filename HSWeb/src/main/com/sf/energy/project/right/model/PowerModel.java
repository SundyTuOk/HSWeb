package com.sf.energy.project.right.model;

/**
 * 数据表【Power】的实例类
 *
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */
public class PowerModel implements java.io.Serializable
{
	///权限id
	private int powerID; 
	
	///模块id	
	private int moduleID;
	
	///权限编号
	private String powerNum;
	
	///权限名字
	private String powerName;
	
	///权限备注
	private String powerRemark;
	
	///权限级别
	private int powerLevel;
	
	///父模块（视图V_ModuleToPower）
	private int moduleParent;
	///模块名称
	private String moduleName;
	
	///模块编号
	private String moduleNum;
	

	public String getModuleNum()
	{
		return moduleNum;
	}

	public void setModuleNum(String moduleNum)
	{
		this.moduleNum = moduleNum;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public int getModuleParent()
	{
		return moduleParent;
	}
	
	public void setModuleParent(int moduleParent)
	{
		this.moduleParent = moduleParent;
	}
	
	public int getPowerID()
	{
		return powerID;
	}
	public void setPowerID(int powerID)
	{
		this.powerID = powerID;
	}
	public int getModuleID()
	{
		return moduleID;
	}
	public void setModuleID(int moduleID)
	{
		this.moduleID = moduleID;
	}
	public String getPowerNum()
	{
		return powerNum;
	}
	public void setPowerNum(String powerNum)
	{
		this.powerNum = powerNum;
	}
	public String getPowerName()
	{
		return powerName;
	}
	public void setPowerName(String powerName)
	{
		this.powerName = powerName;
	}
	public String getPowerRemark()
	{
		return powerRemark;
	}
	public void setPowerRemark(String powerRemark)
	{
		this.powerRemark = powerRemark;
	}
	public int getPowerLevel()
	{
		return powerLevel;
	}
	public void setPowerLevel(int powerLevel)
	{
		this.powerLevel = powerLevel;
	}

	
}
