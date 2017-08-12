package com.sf.energy.expert.dao;

import java.sql.SQLException;
import java.util.List;

import com.sf.energy.expert.model.BasicInfo_saveData;
/**
 * 节能管理指标统计方法封装
 * @author 雷小龙
 *
 */
public interface BasicInfo_saveHelper
{
	/**
	 *  查询指定年份节约校园考核指标总分
	 * @param year 查询的年份
	 * @return		结果集
	 * @throws SQLException
	 */
	public List<BasicInfo_saveData> getBasicInfo_save(int year)
			throws SQLException;
}
