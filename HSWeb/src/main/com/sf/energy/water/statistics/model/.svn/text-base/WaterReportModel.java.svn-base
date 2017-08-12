package com.sf.energy.water.statistics.model;

import java.util.Map;

/**
 * flash报表实体类
 * 
 * @author 王钊
 * @version 1.0
 * @since version 1.0
 */
public class WaterReportModel implements java.io.Serializable
{
    // /用于报表展示的名称
    private String name = null;

    // /分类建筑名称
    private String styleName = null;

    // /区域ID
    private int areaID = 0;

    // /区域名称
    private String areaName = null;

    // /区域用电量
    private float areaTotalWaterCount = 0;

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

    // /查询用水量
    private float totalWaterCount = 0;

    // /查询水费
    private float totalMoneyCount = 0;
    
    // /按用水性质、分项对比今年电量
    private float thisyGross = 0;
    
    // /按用水性质、分项对比去年电量
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

    public WaterReportModel()
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

    public float getAreaTotalWaterCount()
    {
        return areaTotalWaterCount;
    }

    public void setAreaTotalWaterCount(float areaTotalWaterCount)
    {
        this.areaTotalWaterCount = areaTotalWaterCount;
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

    public float getTotalWaterCount()
    {
        return totalWaterCount;
    }

    public void setTotalWaterCount(float totalWaterCount)
    {
        this.totalWaterCount = totalWaterCount;
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
    
}
