package com.sf.energy.statistics.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.statistics.model.ReportModel;

/**
 * 电能计量子系统相关统计功能封装
 * 
 * @author 王钊
 * @version 1.0
 * @since version 1.0
 */
public interface ElecReportHelper
{

    /***
     * 查询获得指定部门的年度用电详细情况，分别获得用电分项分布，用电性质分布，总用电量，总电费
     * 
     * @param groupID
     *            查询的部门ID
     * @param queryYear
     *            查询的年份
     * @return ReportModel 查询结果实体集
     * @throws SQLException
     */
    public ReportModel getGroupCountDetailByYear(int groupID, int queryYear)
            throws SQLException;

    /**
     * 获取全校的某年某月能耗信息
     * 
     * @param year
     * @return
     * @throws SQLException
     */

    public ReportModel getAllSchoolEnergyByMonth(int year, int month)
            throws SQLException;

    /**
     * 查询得到全校指定年份的分类分项数据
     * 
     * @param queryYear
     *            　所要查询的年份
     * @return　Map<String, Float>　查询得到的分项分布数据
     * @throws SQLException
     */
    public Map<String, Float> getAllSchoolFenLeiCountByYear(int queryYear)
            throws SQLException;

    /**
     * 查询得到制指定建筑类别指定月份的分类分项数据
     * 
     * @param arcStyle
     *            所要查询的建筑ID
     * @param queryYear
     *            所要查询的年份
     * @param queryMonth
     *            所要查询的月份
     * @return Map<String, Float> 查询得到的分项分部
     * @throws SQLException
     */
    public Map<String, Float> getArchStyleFenLeiCountByMonth(String archStyle,
            int queryYear, int queryMonth) throws SQLException;

    /**
     * 查询得到指定区域指定年份的分类分项数据
     * 
     * @param areaID
     *            所要查询的区域ＩＤ
     * @param queryYear
     *            　所要查询的年份
     * @return　Map<String, Float>　查询得到的分项分布数据
     * @throws SQLException
     */
    public Map<String, Float> getAreaFenLeiCountByYear(int areaID, int queryYear)
            throws SQLException;

    /**
     * 查询得到全校指定年份的分类分项数据
     * 
     * @param queryYear
     *            　所要查询的年份
     * @return　Map<String, Float>　查询得到的分项分布数据
     * @throws SQLException
     */
    public Map<String, Float> getAllschoolFenLeiCountByMonth(int queryYear,
            int queryMonth) throws SQLException;

    /**
     * 查询得到指定区域指定月份的分类分项数据
     * 
     * @param areaID
     *            所要查询的区域ＩＤ
     * @param queryYear
     *            　所要查询的年份
     * @return　Map<String, Float>　查询得到的分项分布数据
     * @throws SQLException
     */
    public Map<String, Float> getAreaFenLeiCountByMonth(int areaID,
            int queryYear, int queryMonth) throws SQLException;

    /***
     * 查询指定部门的月度用电情况，分别获得用电分项分布，用电性质分布，总用电量，总电费
     * 
     * @param groupID
     *            所要查询的部门ID
     * @param queryYear
     *            查询的年份
     * @param queryMonth
     *            查询的月份
     * @return ReportModel 查询结果实体集
     * @throws SQLException
     */
    public ReportModel getGroupCountDetailByMonth(int groupID, int queryYear,
            int queryMonth) throws SQLException;

    /***
     * 查询指定时间段内的指定部门的用电情况，包括用电量和电费
     * 
     * @param groupID
     *            索要查询的部门ID
     * @param start
     *            起始时间点
     * @param end
     *            截止时间点
     * @return List<ReportModel> 查询结果集，链表的每一项都是单独的一天的记录值（用电量和电费）
     * @throws SQLException
     */
    public List<ReportModel> getGroupCountBetween(int groupID, Date start,
            Date end) throws SQLException;

    /**
     * 查询指定部门指定年份的用电情况，包括用电量和电费
     * 
     * @param groupID
     *            所要查询的部门ＩＤ
     * @param queryYear
     *            　所要查询的年份
     * @return　ReportModel　查询结果实体类
     * @throws SQLException
     */
    public ReportModel getGroupCountByYear(int groupID, int queryYear)
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
     * @return　ReportModel　查询得到的结果
     * @throws SQLException
     */
    public ReportModel getGroupCountByMonth(int groupID, int queryYear,
            int queryMonth) throws SQLException;

    /***
     * 查询指定部门指定年份的每个月的用电情况，分别获得用电分项分布，用电性质分布，总用电量，总电费
     * 
     * @param groupID
     *            所要查询的部门ID
     * @param queryYear
     *            所要查询的年份
     * @throws SQLException
     * @return List<ReportModel> 查询得到12个月份的结果集
     */
    public List<ReportModel> getGroupCountEveryMonth(int groupID, int queryYear)
            throws SQLException;

    /***
     * 查询指定部门指定某年月的每一天的用电量
     * 
     * @param groupID
     *            查询的部门ID
     * @param queryYear
     *            查询的年份
     * @param queryMonth
     *            查询的月份
     * @return List<ReportModel> 查询得到的每一天的数据结果集
     * @throws SQLException
     * 
     */
    public List<ReportModel> getGroupCountEveryDay(int groupID, int queryYear,
            int queryMonth) throws SQLException;

    /***
     * 查询所有部门年度用电量排名，并按降序排列
     * 
     * @param quryYear
     *            查询的年份
     * @return List<ReportModel> 查询结果集
     * @throws SQLException
     * 
     */
    public List<ReportModel> getAllGroupCountListDisc(int quryYear)
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
     * @return ReportModel 查询结果
     * @throws SQLException
     * 
     */
    public ReportModel getArcCountDetailByYear(int arcID, int queryYear)
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
     * @return ReportModel 查询结果实体集
     * @throws SQLException
     */
    public ReportModel getArcCountDetailByMonth(int arcID, int queryYear,
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
     * @return List<ReportModel> 查询结果集
     * @throws SQLException
     */
    public List<ReportModel> getArcCountBetween(int arcID, Date start, Date end)
            throws SQLException;

    /**
     * 查询指定建筑指定年月份的用电情况，包括用电量和电费
     * 
     * @param arcID
     *            建筑ID
     * @param queryYear
     *            所要查询的年份
     * @param queryMonth
     *            所要查询的月份
     * @return ReportModel 查询结果集
     * @throws SQLException
     */
    public ReportModel getArcCountByMonth(int arcID, int queryYear,
            int queryMonth) throws SQLException;

    /**
     * 查询指定建筑指定年份的用电情况，包括用电量和电费
     * 
     * @param arcID
     *            所要查询的建筑ID
     * @param queryYear
     *            所要查询的年份
     * @return ReportModel 查询返回结果集
     * @throws SQLException
     */
    public ReportModel getArcCountByYear(int arcID, int queryYear)
            throws SQLException;

    /**
     * 通过建筑分类ID和年份，查询该年份类该类的用电信息
     * 
     * @param styleID
     *            分类ID
     * @param queryYear
     *            年份
     * @return 结果集
     * @throws SQLException
     */
    public ReportModel getFenleiArcCountByYear(char styleID, int queryYear)
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
     * @return List<ReportModel> 查询得到12个月份的结果集
     */
    public List<ReportModel> getArcCountEveryMonth(int arcID, int queryYear)
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
     * @return List<ReportModel> 查询得到的每一天的数据结果集
     * @throws SQLException
     * 
     */
    public List<ReportModel> getArcCountEveryDay(int arcID, int queryYear,
            int queryMonth) throws SQLException, ParseException;

    /***
     * 查询获得指定部门下面的所有子部门指定年份的用电量和电费
     * 
     * @param groupID
     *            查询的父部门ID
     * @param queryYear
     *            查询的年份
     * @return List<ReportModel> 查询结果集
     * @throws SQLException
     * 
     */
    public List<ReportModel> getAllSonGroupCountByYear(int groupID,
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
     * @return List<ReportModel> 查询结果集
     * @throws SQLException
     * 
     */
    public List<ReportModel> getAllSonGroupCountByMonth(int groupID,
            int queryYear, int queryMonth) throws SQLException;

    /**
     * 得到指定建筑下的所有电表数据
     * 
     * @param arcID
     *            要查询的建筑ID
     * @return List<AmMeterMataData> 查询结果集
     * @throws SQLException
     * 
     */
    public List<AmMeterMataData> getAmMeterByArc(int arcID) throws SQLException;

    /**
     * 得到指定的ID电表的所有数据
     * 
     * @param amID
     *            要查询的电表ID
     * @return List<AmMeterMataData> 查询结果集
     * @throws SQLException
     * 
     */
    public List<AmMeterMataData> getAmMeterByID(int amID) throws SQLException;

    /**
     * 查询得到指定电表在特定时间段内的数据(按小时聚合)
     * 
     * @param ammeterID
     *            电表ID
     * @param start
     *            起始时间点
     * @param end
     *            截止时间点
     * @return List<AmMeterMataData> 查询结果集
     * @throws SQLException
     * 
     */
    public List<AmMeterMataData> getAmMeterBetween(int ammeterID, Date start,
            Date end) throws SQLException;

    /**
     * 查询得到指定电表在特定时间段内的数据(原始数据，没按小时聚合)
     * 
     * @param ammeterID
     *            电表ID
     * @param start
     *            起始时间点
     * @param end
     *            截止时间点
     * @return List<AmMeterMataData> 查询结果集
     * @throws SQLException
     * 
     */
    public List<AmMeterMataData> getOriginalAmmeterDataBetween(int ammeterID,
            Date start, Date end) throws SQLException;

    /**
     * 查询得到在线，离线和用电的电表的数量
     * 
     * @return Map<String, Integer> 查询结果
     * @throws SQLException
     * 
     */
    public Map<String, Integer> getAmmeterCount() throws SQLException;

    /**
     * 获取全校的某年能耗信息
     * 
     * @param year
     * @return
     * @throws SQLException
     */
    public ReportModel getAllSchoolEnergyByYear(int year) throws SQLException;

    /**
     * 获取建筑类别的能耗信息
     * 
     * @param archStyle
     * @param year
     * @return
     * @throws SQLException
     */

    public ReportModel getArchStyleEnergyByYear(String archStyle, int year)
            throws SQLException;

    public List<ReportModel> getAmmeterCountEveryDay(int ammeterID, Date start,
            Date end) throws SQLException;

    public List<ReportModel> getGroupCountEveryDayBetween(int groupID,
            Date start, Date end) throws SQLException;

    public List<ReportModel> getArchCountEveryDayBetween(int archID,
            Date start, Date end) throws SQLException;

	public List<ReportModel> getAmmeterCountEveryDayZL(int ammeterID, Date start, Date end);

	public List<AmMeterMataData> getShapingAmDataBetween(int ammeterID, Date start, Date end, List<AmMeterMataData> list) throws SQLException;;

	public AmMeterMataData getOriginalAmmeterLastData(int ammeterID, Date start) throws SQLException;;

	public AmMeterMataData getOriginalAmmeterBehindData(int ammeterID, Date end) throws SQLException;;
}
