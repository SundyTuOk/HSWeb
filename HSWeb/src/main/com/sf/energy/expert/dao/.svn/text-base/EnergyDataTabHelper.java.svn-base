package com.sf.energy.expert.dao;

import java.sql.SQLException;
import java.util.List;

import com.sf.energy.expert.model.EnergyDataTabData;

public interface EnergyDataTabHelper {
	/**
	 * 通过建筑ID,和时间年月，查询能耗数据表相关信息
	 * @param Architecture_ID	建筑ID
	 * @param year			查询的年份
	 * @param month			查询的月份
	 * @return				返回结果集
	 * @throws SQLException
	 */
	public EnergyDataTabData  getEnergyDataTabData(int Architecture_ID,int year,int month)  throws SQLException;
	/**
	 * 通过建筑ID查询建筑分级名称
	 * @param Architecture_ID	建筑的ID
	 * @return		返回查询的结果
	 * @throws SQLException
	 */
	public String getArcSortName(int Architecture_ID) throws SQLException;
	/**
	 * 通过区域ID，和时间年月，查询该区域内所有建筑能耗数据表相关信息
	 * @param area_ID	区域ID
	 * @param year		查询的年份
	 * @param month		查询的月份
	 * @return			返回结果集
	 * @throws SQLException
	 */
	public  List<EnergyDataTabData>  getAreaDataTab(int area_ID,int year,int month) throws SQLException;

}
