package com.sf.energy.statistics.model;

import java.util.Map;

/**
 * flash报表实体类
 * 
 * @author 王钊
 * @version 1.0
 * @since version 1.0
 */
public class ReportModel implements java.io.Serializable
{
    // /用于报表展示的名称
    private String name = null;

    // /分类建筑的名称
    private String styleName = null;

    // /区域ID
    private int areaID = 0;

    // /区域名称
    private String areaName = null;

    // /区域用电量
    private float areaTotalEnergyCount = 0;

    @Override
	public String toString()
	{
		return "ReportModel [name=" + name + ", styleName=" + styleName + ", areaID=" + areaID + ", areaName=" + areaName + ", areaTotalEnergyCount="
				+ areaTotalEnergyCount + ", groupID=" + groupID + ", groupName=" + groupName + ", arcID=" + arcID + ", arcName=" + arcName
				+ ", fenXiangXiaoJi=" + fenXiangXiaoJi + ", xingZhiXiaoJi=" + xingZhiXiaoJi + ", totalEnergyCount=" + totalEnergyCount
				+ ", totalMoneyCount=" + totalMoneyCount + ", workEnergyCount=" + workEnergyCount + ", unworkEnergyCount=" + unworkEnergyCount
				+ ", thisyGross=" + thisyGross + ", lastyGross=" + lastyGross + ", selectDay=" + selectDay + ", selectMonth=" + selectMonth
				+ ", selectYear=" + selectYear + ", fenlei=" + fenlei + ", style=" + style + "]";
	}

	// /部门ID
    private int groupID = 0;

    // /部门名称
    private String groupName = null;

    // /建筑ID
    private int arcID = 0;

    // /建筑名称
    private String arcName = null;

    // /分项分部查询结果拼接字符串
    private String fenXiangXiaoJi = null;

    // /性质分布查询结果拼接字符串
    private String xingZhiXiaoJi = null;

    // /查询用电量
    private float totalEnergyCount = 0;

    // /查询电费
    private float totalMoneyCount = 0;

    // /工作时间电量
    private float workEnergyCount = 0;

    // /非工作时间电量
    private float unworkEnergyCount = 0;
    
    // /按用电性质、分项对比今年电量
    private float thisyGross = 0;
    
    // /按用电性质、分项对比去年电量
    private float lastyGross = 0;

    // /查询日期
    private int selectDay = 0;

    // /查询月份
    private int selectMonth = 0;

    // /查询年份
    private int selectYear = 0;

    public void setName(String name)
    {
        this.name = name;
    }

    public Map<String, Float> getFenlei()
    {
        return fenlei;
    }

    public void setFenlei(Map<String, Float> fenlei)
    {
        this.fenlei = fenlei;
    }

    public Map<String, Float> getStyle()
    {
        return style;
    }

    public void setStyle(Map<String, Float> style)
    {
        this.style = style;
    }

    // /分项小计
    private Map<String, Float> fenlei = null;

    // /性质分布
    private Map<String, Float> style = null;

    public ReportModel()
    {

    }

    public String getStyleName()
    {
        return styleName;
    }

    public void setStyleName(String styleName)
    {
        this.styleName = styleName;
    }

    public int getAreaID()
    {
        return areaID;
    }

    public float getWorkEnergyCount()
    {
        return workEnergyCount;
    }

    public void setWorkEnergyCount(float workEnergyCount)
    {
        this.workEnergyCount = workEnergyCount;
    }

    public float getUnworkEnergyCount()
    {
        return unworkEnergyCount;
    }

    public float getThisyGross() {
		return thisyGross;
	}

	public void setThisyGross(float thisyGross) {
		this.thisyGross = thisyGross;
	}

	public float getLastyGross() {
		return lastyGross;
	}

	public void setLastyGross(float lastyGross) {
		this.lastyGross = lastyGross;
	}

	public void setUnworkEnergyCount(float unworkEnergyCount)
    {
        this.unworkEnergyCount = unworkEnergyCount;
    }

    public void setAreaID(int areaID)
    {
        this.areaID = areaID;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
        this.name = areaName;
    }

    public float getAreaTotalEnergyCount()
    {
        return areaTotalEnergyCount;
    }

    public void setAreaTotalEnergyCount(float areaTotalEnergyCount)
    {
        this.areaTotalEnergyCount = areaTotalEnergyCount;
    }

    public int getGroupID()
    {
        return groupID;
    }

    public void setGroupID(int groupID)
    {
        this.groupID = groupID;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public String getName()
    {
        return name;
    }

    public void setGroupName(String groupName)
    {
        this.name = groupName;
        this.groupName = groupName;
    }

    public int getArcID()
    {
        return arcID;
    }

    public void setArcID(int arcID)
    {
        this.arcID = arcID;
    }

    public String getArcName()
    {
        return arcName;
    }

    public void setArcName(String arcName)
    {
        this.name = arcName;
        this.arcName = arcName;
    }

    public String getFenXiangXiaoJi()
    {
        return fenXiangXiaoJi;
    }

    public void setFenXiangXiaoJi(String fenXiangXiaoJi)
    {
        this.fenXiangXiaoJi = fenXiangXiaoJi;
    }

    public String getXingZhiXiaoJi()
    {
        return xingZhiXiaoJi;
    }

    public void setXingZhiXiaoJi(String xingZhiXiaoJi)
    {
        this.xingZhiXiaoJi = xingZhiXiaoJi;
    }

    public float getTotalEnergyCount()
    {
        return totalEnergyCount;
    }

    public void setTotalEnergyCount(float totalEnergyCount)
    {
        this.totalEnergyCount = totalEnergyCount;
    }

    public float getTotalMoneyCount()
    {
        return totalMoneyCount;
    }

    public void setTotalMoneyCount(float totalMoneyCount)
    {
        this.totalMoneyCount = totalMoneyCount;
    }

    public int getSelectDay()
    {
        return selectDay;
    }

    public void setSelectDay(int selectDay)
    {
        this.selectDay = selectDay;
    }

    public int getSelectMonth()
    {
        return selectMonth;
    }

    public void setSelectMonth(int selectMonth)
    {
        this.selectMonth = selectMonth;
    }

    public int getSelectYear()
    {
        return selectYear;
    }

    public void setSelectYear(int selectYear)
    {
        this.selectYear = selectYear;
    }

}
