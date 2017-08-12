package com.sf.energy.project.system.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.junit.Test;

import com.sf.energy.util.ConnDB;
import com.sf.energy.util.DBHelper;

public class AreaDaoTest
{

    @Test
    public void test() throws SQLException
    {
        String sql = "select * from Area";
        ResultSet rs = ConnDB
                .getConnection()
                .prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery();

        rs.last();
        System.out.println(rs.getRow());

        if (rs != null)
            rs.close();

    }

    @Test
    public void justTry() throws SQLException
    {
        String sql = "select * from WATERMAINTENANCE";
        PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ResultSetMetaData meta = null;
        int columnCount = 0;

        if (rs.next())
        {
            if (meta == null)
            {
                meta = rs.getMetaData();
                columnCount = meta.getColumnCount();
                for (int i = 1; i <= columnCount; i++)
                {
                    System.out.print(meta.getColumnTypeName(i) + " ");
                }
                System.out.print("\n");
            }

            for (int j = 1; j <= columnCount; j++)
                System.out.print(rs.getObject(j) + " ");

            System.out.print("\n");
        }

        if (rs != null)
            rs.close();

        if (ps != null)
            ps.close();

    }

    @Test
    public void exportTest() throws SQLException, RowsExceededException,
            WriteException, IOException
    {
        String[] titles = { "水表ID", "水表测量点", "所属区域名称", "所属区域ID", "所属建筑名称",
                "所属建筑ID", "父表ID", "所属网关名称", "所属网关ID", "水表编号", "水表名", "密码",
                "通讯地址", "厂家", "型号", "资产号", "是否供水", "总示数", "用水类型", "用户编号",
                "用户名", "用户电话", "用户地址", "是否重点用户", "是否总表", "所属部门名称", "所属部门ID",
                "所在楼层", "表记类型ID", "是否纳入计量", "价格ID", "水表状态", "是否离线报警",
                "是否检测匹配模型", "修正量", "最后通讯时间", "是否检测漏水", "排漏参数", "数据来源", "倍率" };
        DBHelper dh = DBHelper.getInstance();
        dh.getParginateExportFile(titles, "select * from Ammeter", 10, 0);
    }

    @Test
    public void stringTest()
    {
        String sql = "select * from WATERMETER ";

        System.out.println(sql.replace("WATERMETER", "V_WATERMETER"));
    }
}
