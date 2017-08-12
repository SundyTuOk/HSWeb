package com.sf.energy.water.statistics.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sf.energy.water.statistics.model.WaterMeterMataData;
import com.sf.energy.water.statistics.model.WaterReportModel;

/**
 * 电能计量子系统相关统计功能封装
 * 
 * @author 王钊
 * @version 1.0
 * @since version 1.0
 */
public interface WaterReportHelper
{

    /***
     * 查询获得指定部门的年度用电详细情况，分别获得用电分项分布，用电性质分布，总用电量，总电费
     * 
     * @param groupID
     *            查询的部门ID
     * @param queryYear
     *            查询的年份
     * @return WaterReportModel 查询结果实体集
     * @throws SQLException
     */
    public WaterReportModel getGroupCountDetailByYear(int groupID, int queryYear)
            throws SQLException;

    /**
     * 获取全校的某年用水信息
     * 
     * @param year
     * @return
     * @throws SQLException
     */

    public WaterReportModel getAllSchoolWaterByYear(int year)
            throws SQLException;

    /**
     * 获取全校的某年某月用水信息
     * 
     * @param year
     * @return
     * @throws SQLException
     */

    public WaterReportModel getAllSchoolWaterByMonth(int year, int month)
            throws SQLException;

    /***
     * 查询指定部门的月度用电情况，分别获得用电分项分布，用电性质分布，总用电量，总电费
     * 
     * @param groupID
     *            所要查询的部门ID
     * @param queryYear
     *            查询的年份
     * @param queryMonth
     *            查询的月份
     * @return WaterReportModel 查询结果实体集
     * @throws SQLException
     */
    public WaterReportModel getGroupCountDetailByMonth(int groupID,
            int queryYear, int queryMonth) throws SQLException;

    /***
     * 查询指定时间段内的指定部门的用电情况，包括用电量和电费
     * 
     * @param groupID
     *            索要查询的部门ID
     * @param start
     *            起始时间点
     * @param end
     *            截止时间点
     * @return List<WaterReportModel> 查询结果集，链表的每一项都是单独的一天的记录值（用电量和电费）
     * @throws SQLException
     */
    public List<WaterReportModel> getGroupCountBetween(int groupID, Date start,
            Date end) throws SQLException;

    /**
     * 查询指定部门指定年份的用电情况，包括用电量和电费
     * 
     * @param groupID
     *            所要查询的部门ＩＤ
     * @param queryYear
     *            　所要查询的年份
     * @return　WaterReportModel　查询结果实体类
     * @throws SQLException
     */
    public WaterReportModel getGroupCountByYear(int groupID, int queryYear)
            throws SQLException;

    /**
     * 查询得到指定部门指定年月的用电情况，包括用电量和电费
     * 
     * @param groupID
     *            所要查询部门ＩＤ
     * @param queryYear
     *            所要查询的年份
     * @param queryMonth
     *            　索要查询的月份
     * @return　WaterReportModel　查询得到的结果
     * @throws SQLException
     */
    public WaterReportModel getGroupCountByMonth(int groupID, int queryYear,
            int queryMonth) throws SQLException;

    /***
     * 查询指定部门指定年份的每个月的用电情况，分别获得用电分项分布，用电性质分布，总用电量，总电费
     * 
     * @param groupID
     *            所要查询的部门ID
     * @param queryYear
     *            所要查询的年份
     * @throws SQLException
     * @return List<WaterReportModel> 查询得到12个月份的结果集
     */
    public List<WaterReportModel> getGroupCountEveryMonth(int groupID,
            int queryYear) throws SQLException;

    /***
     * 查询指定部门指定某年月的每一天的用电量
     * 
     * @param groupID
     *            查询的部门ID
     * @param queryYear
     *            查询的年份
     * @param queryMonth
     *            查询的月份
     * @return List<WaterReportModel> 查询得到的每一天的数据结果集
     * @throws SQLException
     * 
     */
    public List<WaterReportModel> getGroupCountEveryDay(int groupID,
            int queryYear, int queryMonth) throws SQLException;

    /***
     * 查询所有部门年度用电量排名，并按降序排列
     * 
     * @param quryYear
     *            查询的年份
     * @return List<WaterReportModel> 查询结果集
     * @throws SQLException
     * 
     */
    public List<WaterReportModel> getAllGroupCountListDisc(int quryYear)
            throws SQLException;

    /***
     * 查询指定部门指定年月的的用电分项分布
     * 
     * @param groupID
     *            查询的部门ID
     * @param queryYear
     *            查询的年份
     * @param queryMonth
     *            查询的月份
     * @return Map<String,Float> 查询结果
     * @throws SQLException
     * 
     */
    public Map<String, Float> getGroupFenLeiCountByMonth(int groupID,
            int queryYear, int queryMonth) throws SQLException;

    /**
     * 查询得到指定部门指定年份的用电分类分项数据
     * 
     * @param groupID
     *            所要查询的部门ID
     * @param queryYear
     *            所要查询的年份
     * @return Map<String, Float> 查询得到的分类分项数据
     * @throws SQLException
     */
    public Map<String, Float> getGroupFenLeiCountByYear(int groupID,
            int queryYear) throws SQLException;

    /***
     * 查询指定部门指定时间段内的用电分项分布
     * 
     * @param groupID
     *            查询的部门ID
     * @param start
     *            起始时间点
     * @param end
     *            截止时间点
     * @return Map<String,Float> 查询结果
     * @throws SQLException
     * 
     */
    public Map<String, Float> getGroupFenLeiCountBetween(int groupID,
            Date start, Date end) throws SQLException;

    /***
     * 查询指定部门指定时间段内的用电性质分布
     * 
     * @param groupID
     *            查询的部门ID
     * @param start
     *            起始时间点
     * @param end
     *            截止时间点
     * @return Map<String,Float> 查询结果
     * @throws SQLException
     * 
     */
    public Map<String, Float> getGroupStyleCountBetween(int groupID,
            Date start, Date end) throws SQLException;

    /**
     * 查询指定部门指定年月的性质分项数据
     * 
     * @param groupID
     *            所要查询的部门ＩＤ
     * @param queryYear
     *            　所要查询的年份
     * @param queryMonth
     *            　所要查询的月份
     * @return　Map<String, Float>　查询得到的用电性质分项数据
     * @throws SQLException
     */
    public Map<String, Float> getGroupStyleCountByMonth(int groupID,
            int queryYear, int queryMonth) throws SQLException;

    /**
     * 查询得到指定部门指定年份的用电性质分项
     * 
     * @param groupID
     *            所要查询的部门ID
     * @param queryYear
     *            所要查询的年份
     * @return Map<String, Float> 查询得到的性质分项数据
     * @throws SQLException
     */
    public Map<String, Float> getGroupStyleCountByYear(int groupID,
            int queryYear) throws SQLException;

    /***
     * 查询指定建筑的年度用电详细情况，分别获得用电分项分布，用电性质分布，总用电量，总电费
     * 
     * @param arcID
     *            查询的建筑ID
     * @param queryYear
     *            查询的月份
     * @return WaterReportModel 查询结果
     * @throws SQLException
     * 
     */
    public WaterReportModel getArcCountDetailByYear(int arcID, int queryYear)
            throws SQLException;

    /***
     * 查询指定建筑的月度用电详细情况，分别获得用电分项分布，用电性质分布，总用电量，总电费
     * 
     * @param arcID
     *            查询的建筑ID
     * @param queryYear
     *            查询的年份
     * @param queryMonth
     *            查询的月份
     * @return WaterReportModel 查询结果实体集
     * @throws SQLException
     */
    public WaterReportModel getArcCountDetailByMonth(int arcID, int queryYear,
            int queryMonth) throws SQLException;

    /***
     * 查询指定时间段内的指定建筑物的 每一天的用电情况，包括用电量和电费
     * 
     * @param groupID
     *            所要查询的部门ID
     * @param start
     *            起始时间点
     * @param end
     *            截止时间点
     * @return List<WaterReportModel> 查询结果集
     * @throws SQLException
     */
    public List<WaterReportModel> getArcCountBetween(int arcID, Date start,
            Date end) throws SQLException;

    /**
     * 查询指定建筑指定年月份的用电情况，包括用电量和电费
     * 
     * @param arcID
     *            建筑ID
     * @param queryYear
     *            所要查询的年份
     * @param queryMonth
     *            所要查询的月份
     * @return WaterReportModel 查询结果集
     * @throws SQLException
     */
    public WaterReportModel getArcCountByMonth(int arcID, int queryYear,
            int queryMonth) throws SQLException;

    /**
     * 查询指定建筑指定年份的用电情况，包括用电量和电费
     * 
     * @param arcID
     *            所要查询的建筑ID
     * @param queryYear
     *            所要查询的年份
     * @return WaterReportModel 查询返回结果集
     * @throws SQLException
     */
    public WaterReportModel getArcCountByYear(int arcID, int queryYear)
            throws SQLException;

    /**
     * 通过建筑分类ID和年份，查询该年份类该类的用水信息
     * 
     * @param styleID
     *            分类ID
     * @param queryYear
     *            年份
     * @return 结果集
     * @throws SQLException
     */
    public WaterReportModel getFenleiArcCountByYear(char styleID, int queryYear)
            throws SQLException;

    /***
     * 查询指定建筑指定时间段内的用电分项分布
     * 
     * @param groupID
     *            查询的建筑物ID
     * @param start
     *            起始时间点
     * @param end
     *            查截止时间点
     * @return Map<String,Float> 查询结果
     * @throws SQLException
     * 
     */
    public Map<String, Float> getArcFenLeiCountBetween(int arcID, Date start,
            Date end) throws SQLException;

    /**
     * 查询得到制指定建筑指定月份的分类分项数据
     * 
     * @param arcID
     *            所要查询的建筑ID
     * @param queryYear
     *            所要查询的年份
     * @param queryMonth
     *            所要查询的月份
     * @return Map<String, Float> 查询得到的分项分部
     * @throws SQLException
     */
    public Map<String, Float> getArcFenLeiCountByMonth(int arcID,
            int queryYear, int queryMonth) throws SQLException;

    /**
     * 查询得到全校指定月份的分类分项数据
     * 
     * 
     * @param queryYear
     *            所要查询的年份
     * @param queryMonth
     *            所要查询的月份
     * @return Map<String, Float> 查询得到的分项分部
     * @throws SQLException
     */
    public Map<String, Float> getAllSchoolFenLeiCountByMonth(int queryYear,
            int queryMonth) throws SQLException;

    /**
     * 查询得到某区域指定月份的分类分项数据
     * 
     * @param areaID
     *            所要查询的建筑ID
     * @param queryYear
     *            所要查询的年份
     * @param queryMonth
     *            所要查询的月份
     * @return Map<String, Float> 查询得到的分项分部
     * @throws SQLException
     */
    public Map<String, Float> getAreaFenLeiCountByMonth(int areaID,
            int queryYear, int queryMonth) throws SQLException;

    /**
     * 查询得到指定建筑指定年份的分类分项数据
     * 
     * @param arcID
     *            所要查询的建筑ＩＤ
     * @param queryYear
     *            　所要查询的年份
     * @return　Map<String, Float>　查询得到的分项分布数据
     * @throws SQLException
     */
    public Map<String, Float> getArcFenLeiCountByYear(int arcID, int queryYear)
            throws SQLException;

    /***
     * 查询指定建筑指定时间段内的用电性质分布
     * 
     * @param groupID
     *            查询的建筑物ID
     * @param start
     *            起始时间点
     * @param end
     *            查截止时间点
     * @return Map<String,Float> 查询结果
     * @throws SQLException
     * 
     */
    public Map<String, Float> getArcStyleCountBetween(int arcID, Date start,
            Date end) throws SQLException;

    /**
     * 查询得到指定建筑指定年月的用电性质分布
     * 
     * @param arcID
     *            所要查询的建筑ID
     * @param queryYear
     *            所要查询的年份
     * @param queryMonth
     *            所要查询的月份
     * @return Map<String, Float> 查询得到的用电性质分布数据
     * @throws SQLException
     */
    public Map<String, Float> getArcStyleCountByMonth(int arcID, int queryYear,
            int queryMonth) throws SQLException;

    /**
     * 查询得到指定建筑的指定年份的用电性质分项数据
     * 
     * @param arcID
     *            所要查询的建筑ID
     * @param queryYear
     *            所要查询的年份
     * @return Map<String, Float> 查询得到的用电性质分项数据
     * @throws SQLException
     */
    public Map<String, Float> getArcStyleCountByYear(int arcID, int queryYear)
            throws SQLException;

    /***
     * 查询指定建筑指定年份的每个月的用电情况，分别获得用电分项分布，用电性质分布，总用电量，总电费
     * 
     * @param arcID
     *            查询的建筑ID
     * @param queryYear
     *            查询的年份
     * @throws SQLException
     * @return List<WaterReportModel> 查询得到12个月份的结果集
     */
    public List<WaterReportModel> getArcCountEveryMonth(int arcID, int queryYear)
            throws SQLException;

    /***
     * 查询指定部门指定某年月的每一天的用电量和电费
     * 
     * @param groupID
     *            查询的部门ID
     * @param queryYear
     *            查询的年份
     * @param queryMonth
     *            查询的月份
     * @return List<WaterReportModel> 查询得到的每一天的数据结果集
     * @throws SQLException
     * 
     */
    public List<WaterReportModel> getArcCountEveryDay(int arcID, int queryYear,
            int queryMonth) throws SQLException, ParseException;

    /**
     * 通过区域ID和指定年月，查询该区域该月内每一天的用水情况
     * 
     * @param areaID
     *            区域ID
     * @param queryYear
     *            年份
     * @param queryMonth
     *            月份
     * @return 结果集
     * @throws SQLException
     */
    public List<WaterReportModel> getAreaCountEveryDay(int areaID,
            int queryYear, // 加的
            int queryMonth) throws SQLException;

    /***
     * 查询获得指定部门下面的所有子部门指定年份的用电量和电费
     * 
     * @param groupID
     *            查询的父部门ID
     * @param queryYear
     *            查询的年份
     * @return List<WaterReportModel> 查询结果集
     * @throws SQLException
     * 
     */
    public List<WaterReportModel> getAllSonGroupCountByYear(int groupID,
            int queryYear) throws SQLException;

    /***
     * 查询指定部门下面的所有子部门指定年月的用电量和电费
     * 
     * @param groupID
     *            查询的父部门的ID
     * @param queryYear
     *            查询的年份
     * @param queryMonth
     *            查询的月份
     * @return List<WaterReportModel> 查询结果集
     * @throws SQLException
     * 
     */
    public List<WaterReportModel> getAllSonGroupCountByMonth(int groupID,
            int queryYear, int queryMonth) throws SQLException;

    /**
     * 得到指定建筑下的所有电表数据
     * 
     * @param arcID
     *            要查询的建筑ID
     * @return List<WaterMeterMataData> 查询结果集
     * @throws SQLException
     * 
     */
    public List<WaterMeterMataData> getWaterMeterByArc(int arcID)
            throws SQLException;

    /**
     * 得到指定的ID电表的所有数据
     * 
     * @param amID
     *            要查询的电表ID
     * @return List<WaterMeterMataData> 查询结果集
     * @throws SQLException
     * 
     */
    public List<WaterMeterMataData> getWaterMeterByID(int amID)
            throws SQLException;

    /**
     * 查询得到指定电表在特定时间段内的数据
     * 
     * @param ammeterID
     *            电表ID
     * @param start
     *            起始时间点
     * @param end
     *            截止时间点
     * @return List<WaterMeterMataData> 查询结果集
     * @throws SQLException
     * 
     */
    public List<WaterMeterMataData> getWaterMeterBetween(int ammeterID,
            Date start, Date end) throws SQLException;

    /**
     * 查询得到在线，离线和用电的电表的数量
     * 
     * @return Map<String, Integer> 查询结果
     * @throws SQLException
     * 
     */
    public Map<String, Integer> getWaterMeterCount() throws SQLException;

    /**
     * 查询得到指定电表指定时间段内（精确到秒）的原始数据（没有按小时聚合）
     * 
     * @return List<AmMeterMataData> 查询电表数据结果
     * @throws SQLException
     * 
     */
    public List<WaterMeterMataData> getOriginalWatermeterDataBetween(
            int watermeterID, Date start, Date end) throws SQLException;

    public List<WaterReportModel> getWatermeterCountEveryDay(int ammeterID,
            Date start, Date end) throws SQLException;

    /***
     * 查询指定部门指定时间区间内的每一天的用水量
     * 
     * @param groupID
     *            查询的部门ID
     * @param start
     *            起始日期
     * @param end
     *            截止日期
     * @return List<ReportModel> 查询得到的每一天的数据结果集
     * @throws SQLException
     * 
     */
    public List<WaterReportModel> getGroupCountEveryDayBetween(int groupID,
            Date start, Date end) throws SQLException;

    /***
     * 查询指定建筑指定时间区间内的每一天的用水量
     * 
     * @param archID
     *            查询的建筑ID
     * @param start
     *            起始日期
     * @param end
     *            截止日期
     * @return List<ReportModel> 查询得到的每一天的数据结果集
     * @throws SQLException
     * 
     */
    public List<WaterReportModel> getArchCountEveryDayBetween(int archID,
            Date start, Date end) throws SQLException;
}
