/**
 * 2014-3-24
 */
package com.sf.energy.statistics.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.ConnDB;

/**
 * 电能计量子系统统计功能封装的测试类
 * 
 * @author 王钊
 * @version 1.0
 * @since version 1.0
 */
public class ReportTest
{

    private ElecReportHelperImpl rh = new ElecReportHelperImpl();

    @Test
    public void getAmmeterCountEveryDay() throws SQLException
    {
        Date start = new Date();
        start.setYear(114);
        start.setMonth(3);
        start.setDate(1);

        Date end = new Date();
        end.setYear(114);
        end.setMonth(4);
        end.setDate(1);

        System.out.println(start.toLocaleString());
        System.out.println(end.toLocaleString());
        List<ReportModel> list = rh.getAmmeterCountEveryDay(1956, start, end);

        print(list);
    }

    @Test
    public void getGroupCountBetween()
    {
        Date start = new Date();
        start.setYear(112);
        start.setMonth(8);
        start.setDate(1);

        Date end = new Date();
        end.setYear(112);
        end.setMonth(8);
        end.setDate(30);
        try
        {
            List<ReportModel> list = rh.getGroupCountBetween(1, start, end);

            print(list);
        }
        catch (SQLException e)
        {

            e.printStackTrace();
        }
    }

    @Test
    public void getGroupCountByMonth()
    {
        try
        {
            ReportModel rm = rh.getGroupCountByMonth(1, 2012, 4);
            print(rm);
        }
        catch (SQLException e)
        {

            e.printStackTrace();
        }
    }

    @Test
    public void getArcWorkTimeCountByMonth()
    {
        List<ReportModel> list = null;
        try
        {
            list = new LinkedList<ReportModel>();
            list.add(rh.getArcWorkTimeCountByMonth(32, 2014, 4));
            print(list);
        }
        catch (SQLException e)
        {
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void getGroupCountByYear()
    {
        try
        {
            ReportModel rm = rh.getGroupCountByYear(1, 2012);
            print(rm);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getGroupCountDetailByYear() throws SQLException
    {
        ReportModel rm = rh.getGroupCountDetailByYear(1, 2012);
        print(rm);
    }

    @Test
    public void getGroupCountDetailByMonth() throws SQLException
    {
        ReportModel rm = rh.getGroupCountDetailByMonth(1, 2012, 5);
        print(rm);
    }

    @Test
    public void getGroupCountEveryDay() throws SQLException
    {
        List<ReportModel> list = rh.getGroupCountEveryDay(1, 2012, 4);
        print(list);
    }

    @Test
    public void getGroupCountEveryMonth() throws SQLException
    {
        List<ReportModel> list = rh.getGroupCountEveryMonth(1, 2012);
        print(list);
    }

    @Test
    public void getGroupFenLeiCountBetween() throws SQLException
    {
        Date start = new Date();
        start.setYear(111);
        start.setMonth(1);
        start.setDate(1);

        Date end = new Date();
        end.setYear(113);
        end.setMonth(1);
        end.setDate(18);

        Map<String, Float> m = rh.getGroupFenLeiCountBetween(1, start, end);

        print(m);

    }

    @Test
    public void getGroupFenLeiCountByMonth() throws SQLException
    {
        Date start = new Date();
        start.setYear(111);
        start.setMonth(1);
        start.setDate(1);

        Date end = new Date();
        end.setYear(113);
        end.setMonth(1);
        end.setDate(18);

        Map<String, Float> m = rh.getGroupFenLeiCountByMonth(1, 2012, 6);

        print(m);

    }

    @Test
    public void getGroupFenLeiCountByYear() throws SQLException
    {
        Date start = new Date();
        start.setYear(111);
        start.setMonth(1);
        start.setDate(1);

        Date end = new Date();
        end.setYear(113);
        end.setMonth(1);
        end.setDate(18);

        Map<String, Float> m = rh.getGroupFenLeiCountByYear(1, 2012);

        print(m);

    }

    @Test
    public void getGroupStyleCountBetween() throws SQLException
    {
        Date start = new Date();
        start.setYear(112);
        start.setMonth(1);
        start.setDate(1);

        System.out.println(start.toLocaleString());

        Date end = new Date();

        end.setYear(112);
        end.setMonth(7);
        end.setDate(30);

        System.out.println(end.toLocaleString());

        Map<String, Float> m = rh.getGroupStyleCountBetween(1, start, end);

        print(m);

    }

    @Test
    public void getGroupStyleCountByMonth() throws SQLException
    {
        Map<String, Float> m = rh.getGroupStyleCountByMonth(1, 2012, 6);

        print(m);

    }

    @Test
    public void getGroupStyleCountByYear() throws SQLException
    {
        Map<String, Float> m = rh.getGroupStyleCountByYear(1, 2012);

        print(m);

    }

    @Test
    public void getArcCountBetween() throws SQLException
    {
        Date start = new Date();
        start.setYear(114);
        start.setMonth(6);
        start.setDate(13);

        Date end = new Date();
        end.setYear(114);
        end.setMonth(6);
        end.setDate(13);

        List<ReportModel> list = rh.getArcCountBetween(50, start, end);

        print(list);
    }

    @Test
    public void getArcCountByMonth() throws SQLException
    {
        ReportModel rm = rh.getArcCountByMonth(21, 2012, 7);
        print(rm);
    }

    @Test
    public void getArcCountByYear() throws SQLException
    {
        ReportModel rm = rh.getArcCountByYear(21, 2012);
        print(rm);
    }

    @Test
    public void getArcCountDetailByMonth() throws SQLException
    {
        ReportModel rm = rh.getArcCountDetailByMonth(21, 2012, 3);
        print(rm);
    }

    @Test
    public void getArcCountDetailByYear() throws SQLException
    {
        ReportModel rm = null;
        rm = rh.getArcCountDetailByYear(21, 2012);

        print(rm);
    }

    @Test
    public void getArcFenLeiCountBetween() throws SQLException
    {
        Date start = new Date();
        start.setYear(111);
        start.setMonth(1);
        start.setDate(1);

        Date end = new Date();
        end.setYear(113);
        end.setMonth(1);
        end.setDate(18);

        Map<String, Float> m = rh.getArcFenLeiCountBetween(21, start, end);

        print(m);

    }

    @Test
    public void getArcStyleCountBetween() throws SQLException
    {
        Date start = new Date();
        start.setYear(111);
        start.setMonth(1);
        start.setDate(1);

        Date end = new Date();
        end.setYear(113);
        end.setMonth(1);
        end.setDate(18);

        Map<String, Float> m = rh.getArcStyleCountBetween(21, start, end);

        print(m);

    }

    @Test
    public void getArcCountEveryMonth() throws SQLException
    {
        List<ReportModel> list = rh.getArcCountEveryMonth(21, 2012);
        print(list);
    }

    @Test
    public void getArcCountEveryDay() throws SQLException, ParseException
    {
        List<ReportModel> list = rh.getArcCountEveryDay(1021, 2014, 2);
        print(list);
    }

    @Test
    public void getArcFenLeiCountByYear() throws SQLException
    {
        Map<String, Float> m = rh.getArcFenLeiCountByYear(21, 2012);

        print(m);

    }

    @Test
    public void getAreaFenLeiCountByYear() throws SQLException
    {
        Map<String, Float> m = rh.getAreaFenLeiCountByYear(1, 2014);

        print(m);

    }

    @Test
    public void getArcFenLeiCountByMonth() throws SQLException
    {
        Map<String, Float> m = rh.getArcFenLeiCountByMonth(21, 2012, 6);

        print(m);

    }

    @Test
    public void getArcStyleCountByYear() throws SQLException
    {
        Map<String, Float> m = rh.getArcStyleCountByYear(21, 2012);

        print(m);

    }

    @Test
    public void getAreaStyleCountByYear() throws SQLException
    {
        Map<String, Float> m = rh.getAreaStyleCountByYear(1, 2014);

        print(m);

    }

    @Test
    public void getArcStyleCountByMonth() throws SQLException
    {
        Map<String, Float> m = rh.getArcStyleCountByMonth(21, 2012, 6);

        print(m);

    }

    @Test
    public void getAllGroupCountListDisc() throws SQLException
    {
        List<ReportModel> list = rh.getAllGroupCountListDisc(2012);
        print(list);
    }

    @Test
    public void getAllSonGroupCountByMonth() throws SQLException
    {
        List<ReportModel> list = rh.getAllSonGroupCountByMonth(1, 2012, 6);
        print(list);
    }

    @Test
    public void getAllSonGroupCountByYear() throws SQLException
    {

        List<ReportModel> list = rh.getAllSonGroupCountByYear(1, 2012);
        print(list);
    }

    @Test
    public void getAmMeterBetween() throws SQLException
    {
        Date start = new Date();
        start.setYear(111);
        start.setMonth(1);
        start.setDate(1);

        Date end = new Date();
        end.setYear(117);
        end.setMonth(1);
        end.setDate(1);

        System.out.println("start:"
                + DateFormat.getDateInstance(DateFormat.DEFAULT).format(start)
                + " end:"
                + DateFormat.getDateInstance(DateFormat.DEFAULT).format(end));

        List<AmMeterMataData> list = rh.getAmMeterBetween(802, start, end);

        printAmList(list);

    }

    @Test
    public void getAmMeterByArc() throws SQLException
    {
        List<AmMeterMataData> list = rh.getAmMeterByArc(81);
        printAmList(list);
    }

    @Test
    public void getAmMeterByID() throws SQLException
    {
        List<AmMeterMataData> list = rh.getAmMeterByID(802);
        printAmList(list);
    }

    @Test
    public void getAmmeterCount() throws SQLException
    {
        Map<String, Integer> m = rh.getAmmeterCount();

        printInteger(m);
    }

    @Test
    public void getOriginalAmmeterDataBetween() throws SQLException
    {
        Date start = new Date();
        start.setYear(114);
        start.setMonth(6);
        start.setDate(1);

        Date end = new Date();
        end.setYear(114);
        end.setMonth(6);
        end.setDate(31);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("start:" + df.format(start) + "\nend:"
                + df.format(end));

        List<AmMeterMataData> list = rh.getOriginalAmmeterDataBetween(1956,
                start, end);
        printAmList(list);
    }

    private void print(List<ReportModel> l)
    {
        for (ReportModel rm : l)
        {
            print(rm);
        }
    }

    private void printAmList(List<AmMeterMataData> list)
    {
        for (AmMeterMataData amd : list)
        {
            print(amd);
        }
    }

    private void print(Map<String, Float> map)
    {
        for (Entry<String, Float> entry : map.entrySet())
        {
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }
    }

    private void printInteger(Map<String, Integer> map)
    {
        for (Entry<String, Integer> entry : map.entrySet())
        {
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }
    }

    private void print(AmMeterMataData amd)
    {
        if (amd.getAmMeterID() != 0)
            System.out.println("电表ID:" + amd.getAmMeterID());
        if (amd.getRecordTimeDate() != null)
            System.out.println("记录时间:"
                    + amd.getRecordTimeDate().toLocaleString());
        if (amd.getValue() != 0)
            System.out.println("记录值:" + amd.getValue());
        System.out.println("\n");
    }

    private void print(ReportModel rm)
    {
        if (rm.getGroupName() != null)
            System.out.println("部门名称" + rm.getGroupName());

        if (rm.getArcName() != null)
            System.out.println("建筑名称:" + rm.getArcName());

        if (rm.getSelectYear() != 0)
            System.out.println("年:" + rm.getSelectYear());

        if (rm.getSelectMonth() != 0)
            System.out.println("月:" + rm.getSelectMonth());

        if (rm.getSelectDay() != 0)
            System.out.println("日:" + rm.getSelectDay());

        if (rm.getFenXiangXiaoJi() != null)
            System.out.println("分项小计:" + rm.getFenXiangXiaoJi());

        if (rm.getXingZhiXiaoJi() != null)
            System.out.println("性质小计:" + rm.getXingZhiXiaoJi());

        System.out.println("用电量:" + rm.getTotalEnergyCount());

        System.out.println("电费:" + rm.getTotalMoneyCount());

        if (rm.getFenlei() != null)
            print(rm.getFenlei());
        System.out.println("\n");

        if (rm.getStyle() != null)
            print(rm.getStyle());
        System.out.println("\n\n");
    }

    @Test
    public void checkWater() throws SQLException
    {
        String sql1 = "select a.Architecture_ID,b.Area_ID "
                + "from T_ArcDayWater a,Architecture b "
                + "where a.Architecture_ID=b.Architecture_ID";
        String sql2 = "update T_ArcDayWater set Area_ID = ? where Architecture_ID = ?";
        PreparedStatement ps1 = ConnDB.getConnection().prepareStatement(sql1);
        PreparedStatement ps2 = ConnDB.getConnection().prepareStatement(sql2);

        ResultSet rs1 = ps1.executeQuery();

        while (rs1.next())
        {
            ps2.setInt(1, rs1.getInt("Area_ID"));
            ps2.setInt(2, rs1.getInt("Architecture_ID"));
            ps2.addBatch();
        }

        ps2.executeBatch();

        if (rs1 != null)
            rs1.close();

        if (ps1 != null)
            ps1.close();

        if (ps2 != null)
            ps2.close();
    }

    @Test
    public void checkAmmeter() throws SQLException
    {
        String sql1 = "select a.Architecture_ID,b.Area_ID "
                + "from T_ArcDayAm a,Architecture b "
                + "where a.Architecture_ID=b.Architecture_ID";
        String sql2 = "update T_ArcDayAm set Area_ID = ? where Architecture_ID = ?";
        PreparedStatement ps1 = ConnDB.getConnection().prepareStatement(sql1);
        PreparedStatement ps2 = ConnDB.getConnection().prepareStatement(sql2);

        ResultSet rs1 = ps1.executeQuery();

        while (rs1.next())
        {
            ps2.setInt(1, rs1.getInt("Area_ID"));
            ps2.setInt(2, rs1.getInt("Architecture_ID"));
            ps2.addBatch();
        }

        ps2.executeBatch();

        if (rs1 != null)
            rs1.close();

        if (ps1 != null)
            ps1.close();

        if (ps2 != null)
            ps2.close();
    }

    @Test
    public void checkGas() throws SQLException
    {
        String sql1 = "select a.Architecture_ID,b.Area_ID "
                + "from T_ArcDayGas a,Architecture b "
                + "where a.Architecture_ID=b.Architecture_ID";
        String sql2 = "update T_ArcDayGas set Area_ID = ? where Architecture_ID = ?";
        PreparedStatement ps1 = ConnDB.getConnection().prepareStatement(sql1);
        PreparedStatement ps2 = ConnDB.getConnection().prepareStatement(sql2);

        ResultSet rs1 = ps1.executeQuery();

        while (rs1.next())
        {
            ps2.setInt(1, rs1.getInt("Area_ID"));
            ps2.setInt(2, rs1.getInt("Architecture_ID"));
            ps2.addBatch();
        }

        ps2.executeBatch();

        if (rs1 != null)
            rs1.close();

        if (ps1 != null)
            ps1.close();

        if (ps2 != null)
            ps2.close();
    }

}
