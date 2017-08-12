package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.system.model.AmFixedFinancialSettlementModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.model.WaterFixedFinancialSettlementModel;

public class WaterFixedFinancialSettlementDao
{
	private int totalCount = 0;

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}
	
	public ArrayList<WaterFixedFinancialSettlementModel> getFinancialSettlementInfo(
			String strYear, String userID, int pageCount, int pageIndex, String order)
			throws SQLException
	{
		WaterFixedFinancialSettlementModel wffsm = null;
		ArrayList<WaterFixedFinancialSettlementModel> list = new ArrayList<WaterFixedFinancialSettlementModel>();
		
		String sql = "SELECT ";
        sql += "    b.BillId,b.BillName, ";
        sql += "    c.BldOrArea_ID,c.name,c.AOrBflg, ";
        sql += "    a.Remark,a.SerialNo,a.Year,a.StartMM,a.EndMM,a.StartYMD,a.EndYMD,to_char(a.systemtime, 'yyyy-mm-dd hh24:mi:ss') as systemtime, ";
        sql += "    nvl(a.MeterCnt,'0') as MeterCnt,d.Users_Name  ";
        sql += "FROM  ";
        sql += "    BldFinalAccountsWater a, ";
        sql += "    BldBillSetWater b, ";
        sql += "    ( ";
        sql += "        select ";
        sql += "            Area_ID as BldOrArea_ID, ";
        sql += "            '0' as AOrBflg, ";
        sql += "            Area_Name as name ";
        sql += "        from ";
        sql += "            Area ";
        sql += "        union all ";
        sql += "        select ";
        sql += "            Architecture_ID  as BldOrArea_ID, ";
        sql += "            '1' as AOrBflg, ";
        sql += "            Architecture_Name as name ";
        sql += "        from ";
        sql += "            Architecture		 ";
        sql += "    ) c, ";
        sql += "    Users  d   ";
        sql += "where  ";
        sql += "    a.BillId = b.BillId	And  ";
        sql += "    b.BldOrArea_ID = c.BldOrArea_ID And  ";
        sql += "    b.AOrBflg = c.AOrBflg And  ";
        sql += "    a.userID = d.Users_ID And  ";
        sql += "    a.Year = '" + strYear + "' " + order;
        
        Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
			{
				ConnDB.release(conn, ps,rs);
				return list;
			}

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				wffsm = new WaterFixedFinancialSettlementModel();
				wffsm.setBldOrArea_ID(rs.getInt("BldOrArea_ID"));
				wffsm.setAOrBflg(rs.getInt("AOrBflg"));
				wffsm.setSerialNo(rs.getInt("SerialNo"));
				wffsm.setName(rs.getString("name"));
				wffsm.setRemark(rs.getString("Remark"));
				wffsm.setYear(rs.getString("Year") + "年" + rs.getString("StartMM")
						+ "月至" + rs.getString("EndMM") + "月");
				wffsm.setPeriod(rs.getString("StartYMD").substring(0, 4) + "年"
						+ rs.getString("StartYMD").substring(4, 6) + "月"
						+ rs.getString("StartYMD").substring(6, 8)
						+ "日 00:00:00 - " + rs.getString("EndYMD").substring(0, 4)
						+ "年" + rs.getString("EndYMD").substring(4, 6) + "月"
						+ rs.getString("EndYMD").substring(6, 8) + "日 00:00:00");
				wffsm.setAmMeterCnt(rs.getInt("MeterCnt"));
				wffsm.setSystemTime(rs.getString("systemtime"));
				wffsm.setUsers_Name(rs.getString("Users_Name"));
				
				list.add(wffsm);
	        	count--;
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
        
		return list;
	}

	public ArrayList<WaterFixedFinancialSettlementModel> getDeptFinancialSettlementInfo(
			String strYear, String userID, int pageCount, int pageIndex, String order)
			throws SQLException
	{
		WaterFixedFinancialSettlementModel wffsm = null;
		ArrayList<WaterFixedFinancialSettlementModel> list = new ArrayList<WaterFixedFinancialSettlementModel>();
		
		String sql = "SELECT " +
				"b.BillId,b.BillName," +
				"c.Partment_ID,c.Partment_Name," +
				"a.Remark,a.SerialNo,a.Year,a.StartMM,a.EndMM,a.StartYMD,a.EndYMD," +
				"to_char(a.systemtime, 'yyyy-mm-dd hh24:mi:ss') as systemtime,nvl(a.MeterCnt,'0') as MeterCnt," +
				"d.Users_Name " +
				"FROM " +
				"DeptFinalAccountsWater a,DeptBillSetWater b,Partment c,Users d  " +
				"where " +
				"a.BillId = b.BillId " +
				"And b.Partment_ID = c.Partment_ID " +
				"And a.userID = d.Users_ID " +
				"And a.Year = '" + strYear + "' " + order;
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
			{
				ConnDB.release(conn, ps,rs);
				return list;
			}

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				wffsm = new WaterFixedFinancialSettlementModel();
				wffsm.setPartment_ID(rs.getInt("Partment_ID"));
				wffsm.setSerialNo(rs.getInt("SerialNo"));
				wffsm.setName(rs.getString("Partment_Name"));
				wffsm.setRemark(rs.getString("Remark"));
				wffsm.setYear(rs.getString("Year") + "年" + rs.getString("StartMM")
						+ "月至" + rs.getString("EndMM") + "月");
				wffsm.setPeriod(rs.getString("StartYMD").substring(0, 4) + "年"
						+ rs.getString("StartYMD").substring(4, 6) + "月"
						+ rs.getString("StartYMD").substring(6, 8)
						+ "日 00:00:00 - " + rs.getString("EndYMD").substring(0, 4)
						+ "年" + rs.getString("EndYMD").substring(4, 6) + "月"
						+ rs.getString("EndYMD").substring(6, 8) + "日 00:00:00");
				wffsm.setAmMeterCnt(rs.getInt("MeterCnt"));
				wffsm.setSystemTime(rs.getString("systemtime"));
				wffsm.setUsers_Name(rs.getString("Users_Name"));
				
				list.add(wffsm);
	        	count--;
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return list;	
	}
}
