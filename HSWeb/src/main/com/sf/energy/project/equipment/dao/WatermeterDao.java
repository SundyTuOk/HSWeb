package com.sf.energy.project.equipment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.equipment.model.WatermeterModel;
import com.sf.energy.project.system.dao.MeterStyleDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.MeterStyle;
import com.sf.energy.project.system.model.MeterTexing;
import com.sf.energy.util.ConnDB;

public class WatermeterDao
{
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	MeterStyleDao msd = new MeterStyleDao();

	int total;
	String sidebarItem = "";

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public List<WatermeterModel> getWatermeterByArch(int archID) throws SQLException
	{
		List<WatermeterModel> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from V_WaterMeter where ARCHITECTURE_ID=?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<WatermeterModel>();
				WatermeterModel wm = buildInstance(rs);
				// wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				// wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
				// wm.setAREA_ID(rs.getInt("AREA_ID"));
				// wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				// wm.setPARENT_ID(rs.getInt("PARENT_ID"));
				// wm.setGATHER_ID(rs.getInt("GATHER_ID"));
				// wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
				// wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				// wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
				// wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
				// wm.setMAKER(rs.getString("MAKER"));
				// wm.setMAKERCODE(rs.getString("MAKERCODE"));
				// wm.setASSETNO(rs.getString("ASSETNO"));
				// wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
				// wm.setISSUPPLY(rs.getInt("ZVALUE"));
				// wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
				// wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
				// wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
				// wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
				// wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
				// wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
				// wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
				// wm.setPARTMENT(rs.getInt("PARTMENT"));
				// wm.setFLOOR(rs.getInt("FLOOR"));
				// wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
				// wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
				// wm.setPRICE_ID(rs.getInt("PRICE_ID"));
				// wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
				// wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
				// wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
				// wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
				// wm.setXIUZHENG(rs.getInt("XIUZHENG"));
				// wm.setLASTTIME(rs.getDate("LASTTIME"));
				// wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
				// wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
				// wm.setDATAFROM(rs.getInt("DATAFROM"));
				// wm.setBEILV(rs.getFloat("BEILV"));
				// wm.setLimitPart(rs.getFloat("LimitPart"));

				list.add(wm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}
	/**
	 * 列出所有总表
	 * 
	 * @param archID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<WatermeterModel> listCountWatermeter() throws SQLException
	{
		ArrayList<WatermeterModel> list = new ArrayList<WatermeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from V_watermeter where iscountMeter=1 order by WATERMETER_NAME";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				WatermeterModel wm = buildInstance(rs);

				list.add(wm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}
	/**
	 * 列出所有的水表
	 * 
	 * @param archID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<WatermeterModel> listWatermeter() throws SQLException
	{
		ArrayList<WatermeterModel> list = new ArrayList<WatermeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from V_watermeter";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				WatermeterModel wm = buildInstance(rs);
				// wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				// wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
				// wm.setAREA_ID(rs.getInt("AREA_ID"));
				// wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				// wm.setPARENT_ID(rs.getInt("PARENT_ID"));
				// wm.setGATHER_ID(rs.getInt("GATHER_ID"));
				// wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
				// wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				// wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
				// wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
				// wm.setMAKER(rs.getString("MAKER"));
				// wm.setMAKERCODE(rs.getString("MAKERCODE"));
				// wm.setASSETNO(rs.getString("ASSETNO"));
				// wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
				// wm.setISSUPPLY(rs.getInt("ZVALUE"));
				// wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
				// wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
				// wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
				// wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
				// wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
				// wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
				// wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
				// wm.setPARTMENT(rs.getInt("PARTMENT"));
				// wm.setFLOOR(rs.getInt("FLOOR"));
				// wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
				// wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
				// wm.setPRICE_ID(rs.getInt("PRICE_ID"));
				// wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
				// wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
				// wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
				// wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
				// wm.setXIUZHENG(rs.getInt("XIUZHENG"));
				// wm.setLASTTIME(rs.getDate("LASTTIME"));
				// wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
				// wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
				// wm.setDATAFROM(rs.getInt("DATAFROM"));
				// wm.setBEILV(rs.getFloat("BEILV"));
				// wm.setLimitPart(rs.getFloat("LimitPart"));

				list.add(wm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public ArrayList<WatermeterModel> listWatermeter(String sql) throws SQLException
	{
		ArrayList<WatermeterModel> list = new ArrayList<WatermeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{

				WatermeterModel wm = buildInstance(rs);
				// wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				// wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
				// wm.setAREA_ID(rs.getInt("AREA_ID"));
				// wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				// wm.setPARENT_ID(rs.getInt("PARENT_ID"));
				// wm.setGATHER_ID(rs.getInt("GATHER_ID"));
				// wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
				// wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				// wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
				// wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
				// wm.setMAKER(rs.getString("MAKER"));
				// wm.setMAKERCODE(rs.getString("MAKERCODE"));
				// wm.setASSETNO(rs.getString("ASSETNO"));
				// wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
				// wm.setISSUPPLY(rs.getInt("ZVALUE"));
				// wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
				// wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
				// wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
				// wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
				// wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
				// wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
				// wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
				// wm.setPARTMENT(rs.getInt("PARTMENT"));
				// wm.setFLOOR(rs.getInt("FLOOR"));
				// wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
				// wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
				// wm.setPRICE_ID(rs.getInt("PRICE_ID"));
				// wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
				// wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
				// wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
				// wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
				// wm.setXIUZHENG(rs.getInt("XIUZHENG"));
				// wm.setLASTTIME(rs.getDate("LASTTIME"));
				// wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
				// wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
				// wm.setDATAFROM(rs.getInt("DATAFROM"));
				// wm.setBEILV(rs.getFloat("BEILV"));
				// wm.setLimitPart(rs.getFloat("LimitPart"));

				list.add(wm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public WatermeterModel getWatermeterByID(int ID) throws SQLException
	{
		WatermeterModel wm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from V_watermeter where WATERMETER_ID=?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ID);
			rs = ps.executeQuery();

			if (rs.next())
			{
				wm = buildInstance(rs);
				// wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				// wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
				// wm.setAREA_ID(rs.getInt("AREA_ID"));
				// wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				// wm.setPARENT_ID(rs.getInt("PARENT_ID"));
				// wm.setGATHER_ID(rs.getInt("GATHER_ID"));
				// wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
				// wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				// wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
				// wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
				// wm.setMAKER(rs.getString("MAKER"));
				// wm.setMAKERCODE(rs.getString("MAKERCODE"));
				// wm.setASSETNO(rs.getString("ASSETNO"));
				// wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
				// wm.setISSUPPLY(rs.getInt("ZVALUE"));
				// wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
				// wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
				// wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
				// wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
				// wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
				// wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
				// wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
				// wm.setPARTMENT(rs.getInt("PARTMENT"));
				// wm.setFLOOR(rs.getInt("FLOOR"));
				// wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
				// wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
				// wm.setPRICE_ID(rs.getInt("PRICE_ID"));
				// wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
				// wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
				// wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
				// wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
				// wm.setXIUZHENG(rs.getInt("XIUZHENG"));
				// wm.setLASTTIME(rs.getDate("LASTTIME"));
				// wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
				// wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
				// wm.setDATAFROM(rs.getInt("DATAFROM"));
				// wm.setBEILV(rs.getFloat("BEILV"));
				// wm.setLimitPart(rs.getFloat("LimitPart"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return wm;
	}

	public WatermeterModel getWatermeterInfoByID(int ID) throws SQLException
	{
		WatermeterModel wm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from V_watermeter where WATERMETER_ID=?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ID);
			rs = ps.executeQuery();

			if (rs.next())
			{
				wm = buildInstance(rs);
				// wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				// wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
				// wm.setAREA_ID(rs.getInt("AREA_ID"));
				// wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				// wm.setPARENT_ID(rs.getInt("PARENT_ID"));
				// wm.setGATHER_ID(rs.getInt("GATHER_ID"));
				// wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
				// wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				// wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
				// wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
				// wm.setMAKER(rs.getString("MAKER"));
				// wm.setMAKERCODE(rs.getString("MAKERCODE"));
				// wm.setASSETNO(rs.getString("ASSETNO"));
				// wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
				// wm.setISSUPPLY(rs.getInt("ZVALUE"));
				// wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
				// wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
				// wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
				// wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
				// wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
				// wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
				// wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
				// wm.setPARTMENT(rs.getInt("PARTMENT"));
				// wm.setFLOOR(rs.getInt("FLOOR"));
				// wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
				// wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
				// wm.setPRICE_ID(rs.getInt("PRICE_ID"));
				// wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
				// wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
				// wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
				// wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
				// wm.setXIUZHENG(rs.getInt("XIUZHENG"));
				// wm.setLASTTIME(rs.getDate("LASTTIME"));
				// wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
				// wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
				// wm.setDATAFROM(rs.getInt("DATAFROM"));
				// wm.setBEILV(rs.getFloat("BEILV"));
				// wm.setLimitPart(rs.getFloat("LimitPart"));
				//
				// wm.setArchName(rs.getString("Architecture_Name"));
				// wm.setGatherName(rs.getString("Gather_Name"));
				// wm.setAreaName(rs.getString("Area_Name"));
				// wm.setUseAmStyleName(rs.getString("USEAMFX"));// 分项
				// wm.setUseStyleName(rs.getString("USEAMXZ"));// 性质
				// wm.setPartmentName(rs.getString("PartmentName"));
				// wm.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型
				// wm.setUseAmStyleNameJYZX(rs.getString("USEAMYJZX"));// 一级子项
				// wm.setMeterName(rs.getString("Meter_Name"));// 电表合并之后的名称

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return wm;
	}

	public WatermeterModel getWatermeterInfoByCondiction(String info) throws SQLException
	{
		// info--id/sidebarItem
		WatermeterModel wm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String[] infos = info.split("/");
			int ID = Integer.parseInt(infos[0]);
			if (infos.length == 2)
			{
				sidebarItem = infos[1];
			}

			String sql = "select * from V_watermeter where WATERMETER_ID=?";
			if ("4C01".equals(sidebarItem))
			{
				// 预付费水表监测 查询剩余金额
				sql = "select a.*,(select THISREMAINMONEY from (select * from WATERJIESUAN where WATERMETER_ID=" + ID
						+ " ORDER BY THISTIME desc) where ROWNUM=1)THISREMAINMONEY from V_watermeter a where WATERMETER_ID=?";
			}
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ID);
			rs = ps.executeQuery();

			if (rs.next())
			{
				wm = buildInstance(rs);
				// wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				// wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
				// wm.setAREA_ID(rs.getInt("AREA_ID"));
				// wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				// wm.setPARENT_ID(rs.getInt("PARENT_ID"));
				// wm.setGATHER_ID(rs.getInt("GATHER_ID"));
				// wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
				// wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				// wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
				// wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
				// wm.setMAKER(rs.getString("MAKER"));
				// wm.setMAKERCODE(rs.getString("MAKERCODE"));
				// wm.setASSETNO(rs.getString("ASSETNO"));
				// wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
				// wm.setISSUPPLY(rs.getInt("ZVALUE"));
				// wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
				// wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
				// wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
				// wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
				// wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
				// wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
				// wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
				// wm.setPARTMENT(rs.getInt("PARTMENT"));
				// wm.setFLOOR(rs.getInt("FLOOR"));
				// wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
				// wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
				// wm.setPRICE_ID(rs.getInt("PRICE_ID"));
				// wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
				// wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
				// wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
				// wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
				// wm.setXIUZHENG(rs.getInt("XIUZHENG"));
				// wm.setLASTTIME(rs.getDate("LASTTIME"));
				// wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
				// wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
				// wm.setDATAFROM(rs.getInt("DATAFROM"));
				// wm.setBEILV(rs.getFloat("BEILV"));
				// wm.setLimitPart(rs.getFloat("LimitPart"));
				//
				// wm.setArchName(rs.getString("Architecture_Name"));
				// wm.setGatherName(rs.getString("Gather_Name"));
				// wm.setAreaName(rs.getString("Area_Name"));
				// wm.setUseAmStyleName(rs.getString("USEAMFX"));// 分项
				// wm.setUseStyleName(rs.getString("USEAMXZ"));// 性质
				// wm.setPartmentName(rs.getString("PartmentName"));
				// wm.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型
				// wm.setUseAmStyleNameJYZX(rs.getString("USEAMYJZX"));// 一级子项
				// wm.setMeterName(rs.getString("Meter_Name"));// 电表合并之后的名称

			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return wm;
	}

	public boolean addWatermeter(List<WatermeterModel> list) throws SQLException
	{
		boolean flag = false;

		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "insert into watermeter (WATERMETER_POINT,AREA_ID,ARCHITECTURE_ID,PARENT_ID,"
					+ "GATHER_ID,WATERMETER_NUM,WATERMETER_NAME,WATERMETER_PASSWORD,WATERMETER_485ADDRESS,"
					+ "MAKER,MAKERCODE,ASSETNO,ISSUPPLY,ZVALUE,USEAMSTYLE,CONSUMERNUM,CONSUMERNAME,CONSUMERPHONE,"
					+ "CONSUMERADDRESS,ISIMPORTANTUSER,ISCOUNTMETER,PARTMENT,FLOOR,METESTYLE_ID,ISCOMPUTATION,PRICE_ID,"
					+ "WATERMETER_STAT,ISOFFALARM,COSTCHECK,STANDBYCHECK,XIUZHENG,LASTTIME,WLEAKAGECHECK,WLEAKAGEVALUE,"
					+ "DATAFROM,BEILV,LimitPart) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?)";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			int count = 0, executeCount = 0;

			if (list != null && list.size() > 0)
			{
				for (WatermeterModel wm : list)
				{
					if (wm != null)
					{
						wm.setWATERMETER_NUM(buildNum(wm));

						int newBeiLv = 1;
						MeterStyle ms = msd.queryByID(wm.getMETESTYLE_ID());
						List<MeterTexing> dataList = ms.getDetaiList();

						if (dataList != null && dataList.size() > 0)
						{
							for (MeterTexing mt : dataList)
							{
								if (mt.getMETERTEXING_ID() == 13)
								{
									newBeiLv = Integer.parseInt(mt.getValue());
									wm.setBEILV(newBeiLv);
									break;
								}
							}
						}

						ps.setInt(1, wm.getWATERMETER_POINT());
						ps.setInt(2, wm.getAREA_ID());
						ps.setInt(3, wm.getARCHITECTURE_ID());
						ps.setInt(4, wm.getPARENT_ID());
						ps.setInt(5, wm.getGATHER_ID());
						ps.setString(6, wm.getWATERMETER_NUM());
						ps.setString(7, wm.getWATERMETER_NAME());
						ps.setString(8, wm.getWATERMETER_PASSWORD());
						ps.setString(9, wm.getWATERMETER_485ADDRESS());
						ps.setString(10, wm.getMAKER());
						ps.setString(11, wm.getMAKERCODE());
						ps.setString(12, wm.getASSETNO());
						ps.setInt(13, wm.getISSUPPLY());
						ps.setFloat(14, wm.getZVALUE());
						ps.setInt(15, wm.getUSEAMSTYLE());
						ps.setString(16, wm.getCONSUMERNUM());
						ps.setString(17, wm.getCONSUMERNAME());
						ps.setString(18, wm.getCONSUMERPHONE());
						ps.setString(19, wm.getCONSUMERADDRESS());
						ps.setInt(20, wm.getISIMPORTANTUSER());
						ps.setInt(21, wm.getISCOUNTMETER());
						ps.setInt(22, wm.getPARTMENT());
						ps.setInt(23, wm.getFLOOR());
						ps.setInt(24, wm.getMETESTYLE_ID());
						ps.setInt(25, wm.getISCOMPUTATION());
						ps.setInt(26, wm.getPRICE_ID());
						ps.setInt(27, wm.getWATERMETER_STAT());
						ps.setInt(28, wm.getISOFFALARM());
						ps.setInt(29, wm.getCOSTCHECK());
						ps.setInt(30, wm.getSTANDBYCHECK());
						ps.setFloat(31, wm.getXIUZHENG());
						if (wm.getLASTTIME() != null)
							ps.setString(32,wm.getLASTTIME());
						else
							ps.setString(32, df.format(new Date()));
						ps.setInt(33, wm.getWLEAKAGECHECK());
						ps.setFloat(34, wm.getWLEAKAGEVALUE());
						ps.setInt(35, wm.getDATAFROM());
						ps.setFloat(36, wm.getBEILV());
						ps.setFloat(37, wm.getLimitPart());
						// ps.setInt(37, wm.getWATERMETER_ID());

						if (count <= 1000)
						{
							ps.addBatch();
							count++;
						} else
						{
							int[] array = ps.executeBatch();
							for (int i : array)
							{
								if (i >= 0)
									executeCount += i;
							}

							count = 0;
						}
					}

				}
			} else
			{
				return false;
			}

			if (count > 0)
			{
				int[] array = ps.executeBatch();
				for (int i : array)
				{
					if (i >= 0)
						executeCount += i;
				}
			}

			flag = (executeCount == list.size());
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public boolean addWatermeter(WatermeterModel wm) throws SQLException
	{
		boolean flag = false;

		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "insert into watermeter (WATERMETER_POINT,AREA_ID,ARCHITECTURE_ID,PARENT_ID,"
					+ "GATHER_ID,WATERMETER_NUM,WATERMETER_NAME,WATERMETER_PASSWORD,WATERMETER_485ADDRESS,"
					+ "MAKER,MAKERCODE,ASSETNO,ISSUPPLY,ZVALUE,USEAMSTYLE,CONSUMERNUM,CONSUMERNAME,CONSUMERPHONE,"
					+ "CONSUMERADDRESS,ISIMPORTANTUSER,ISCOUNTMETER,PARTMENT,FLOOR,METESTYLE_ID,ISCOMPUTATION,PRICE_ID,"
					+ "WATERMETER_STAT,ISOFFALARM,COSTCHECK,STANDBYCHECK,XIUZHENG,LASTTIME,WLEAKAGECHECK,WLEAKAGEVALUE,"
					+ "DATAFROM,BEILV,LimitPart,IsSale,IsRecycle) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?)";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			if (wm != null)
			{
//				wm.setWATERMETER_NUM(buildNum(wm));
				int newBeiLv = 1;
				MeterStyle ms = msd.queryByID(wm.getMETESTYLE_ID());
				List<MeterTexing> dataList = ms.getDetaiList();
				if (dataList != null && dataList.size() > 0)
				{
					for (MeterTexing mt : dataList)
					{
						if (mt.getMETERTEXING_ID() == 13)
						{
							newBeiLv = Integer.parseInt(mt.getValue());
							wm.setBEILV(newBeiLv);
							break;
						}
					}
				}

				ps.setInt(1, wm.getWATERMETER_POINT());
				ps.setInt(2, wm.getAREA_ID());
				ps.setInt(3, wm.getARCHITECTURE_ID());
				ps.setInt(4, wm.getPARENT_ID());
				ps.setInt(5, wm.getGATHER_ID());
				ps.setString(6, wm.getWATERMETER_NUM());
				ps.setString(7, wm.getWATERMETER_NAME());
				ps.setString(8, wm.getWATERMETER_PASSWORD());
				ps.setString(9, wm.getWATERMETER_485ADDRESS());
				ps.setString(10, wm.getMAKER());
				ps.setString(11, wm.getMAKERCODE());
				ps.setString(12, wm.getASSETNO());
				ps.setInt(13, wm.getISSUPPLY());
				ps.setFloat(14, wm.getZVALUE());
				ps.setInt(15, wm.getUSEAMSTYLE());
				ps.setString(16, wm.getCONSUMERNUM());
				ps.setString(17, wm.getCONSUMERNAME());
				ps.setString(18, wm.getCONSUMERPHONE());
				ps.setString(19, wm.getCONSUMERADDRESS());
				ps.setInt(20, wm.getISIMPORTANTUSER());
				ps.setInt(21, wm.getISCOUNTMETER());
				ps.setInt(22, wm.getPARTMENT());
				ps.setInt(23, wm.getFLOOR());
				ps.setInt(24, wm.getMETESTYLE_ID());
				ps.setInt(25, wm.getISCOMPUTATION());
				ps.setInt(26, wm.getPRICE_ID());
				ps.setInt(27, wm.getWATERMETER_STAT());
				ps.setInt(28, wm.getISOFFALARM());
				ps.setInt(29, wm.getCOSTCHECK());
				ps.setInt(30, wm.getSTANDBYCHECK());
				ps.setFloat(31, wm.getXIUZHENG());
				if (wm.getLASTTIME() != null)
					ps.setString(32, wm.getLASTTIME());
				else
					ps.setString(32, df.format(new Date()));
				ps.setInt(33, wm.getWLEAKAGECHECK());
				ps.setFloat(34, wm.getWLEAKAGEVALUE());
				ps.setInt(35, wm.getDATAFROM());
				ps.setFloat(36, wm.getBEILV());
				ps.setFloat(37, wm.getLimitPart());
				ps.setInt(38, wm.getIsSale());
				ps.setInt(39, wm.getIsRecycle());
				// ps.setInt(37, wm.getWATERMETER_ID());
				if (ps.executeUpdate() == 1)
					flag = true;
				if (flag)// 新建该表对应的WATERMETERDATAS
				{
					// Connection conn4=null;
					PreparedStatement ps4 = null;
					ResultSet rs4 = null;

					try
					{
						String WameterID = "";
						sql = "select WATERMETER_ID from WATERMETER where Gather_ID=" + wm.getGATHER_ID() + " and WATERMETER_485ADDRESS='"
								+ wm.getWATERMETER_485ADDRESS() + "'";
						// conn4 = ConnDB.getConnection();
						ps4 = conn.prepareStatement(sql);
						rs4 = ps4.executeQuery();
						if (rs4.next())
						{
							WameterID = rs4.getString("WATERMETER_ID");
							if (!"".equals(WameterID) && WameterID != null)
							{
								// Connection conn2=null;
								PreparedStatement ps2 = null;
								ResultSet rs2 = null;
								sql = "select count(1) as NUM from all_tables where TABLE_NAME = 'ZWATERDATAS" + String.valueOf(WameterID) + "'";
								try
								{
									// conn2 = ConnDB.getConnection();
									ps2 = conn.prepareStatement(sql);
									rs2 = ps2.executeQuery();
									if (rs2.next())
									{
										String num = rs2.getString("NUM");
										if ("1".equals(num))
										{
											// Connection conn1=null;
											PreparedStatement ps1 = null;
											try
											{
												sql = "drop table ZWATERDATAS" + String.valueOf(WameterID);
												// conn1 =
												// ConnDB.getConnection();
												ps1 = conn.prepareStatement(sql);
												ps1.executeUpdate();
											} catch (Exception e)
											{
												e.printStackTrace();
											} finally
											{
												ConnDB.release(ps1);
											}
										}
									}
								} catch (Exception e)
								{
									e.printStackTrace();
								} finally
								{
									ConnDB.release(ps2, rs2);
								}
								sql = "create table ZWaterDatas" + String.valueOf(WameterID);
								sql += "(VALUETIME DATE not null,ZVALUEZY NUMBER not null ,CurrentFlow NUMBER default 0 not null，primary key (VALUETIME)) ";
								// Connection conn3=null;
								PreparedStatement ps3 = null;
								try
								{
									// conn3 = ConnDB.getConnection();
									ps3 = conn.prepareStatement(sql);
									ps3.executeUpdate();
								} catch (Exception e)
								{
									e.printStackTrace();
								} finally
								{
									ConnDB.release(ps3);
								}
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(ps4, rs4);
					}
				}
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return flag;
	}

	public boolean updateWatermeter(WatermeterModel wm) throws SQLException
	{
		int newBeiLv = 1;
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			MeterStyle ms = msd.queryByID(wm.getMETESTYLE_ID());
			List<MeterTexing> dataList = ms.getDetaiList();
//			wm.setWATERMETER_NUM(buildNum(wm));
			if (dataList != null && dataList.size() > 0)
			{
				for (MeterTexing mt : dataList)
				{
					if (mt.getMETERTEXING_ID() == 13)
					{
						newBeiLv = Integer.parseInt(mt.getValue());
						wm.setBEILV(newBeiLv);
						break;
					}
				}
			}

			flag = true;

			String sql = "update watermeter set WATERMETER_POINT = ?, AREA_ID = ?,ARCHITECTURE_ID = ?,"
					+ "PARENT_ID = ?,GATHER_ID = ?,WATERMETER_NUM = ?,WATERMETER_NAME = ?,WATERMETER_PASSWORD = ?,"
					+ "WATERMETER_485ADDRESS = ?,MAKER = ?,MAKERCODE = ?,ASSETNO = ?,ISSUPPLY = ?,ZVALUE = ?,"
					+ "USEAMSTYLE = ?,CONSUMERNUM = ?,CONSUMERNAME = ?,CONSUMERPHONE = ?,CONSUMERADDRESS = ?,"
					+ "ISIMPORTANTUSER = ?,ISCOUNTMETER = ?,PARTMENT = ?,FLOOR = ?,METESTYLE_ID = ?,ISCOMPUTATION = ?,"
					+ "PRICE_ID = ?,WATERMETER_STAT = ?,ISOFFALARM = ?,COSTCHECK = ?,STANDBYCHECK = ?,XIUZHENG = ?,"
					+ "LASTTIME = to_date(?,'yyyy-mm-dd hh24:mi:ss'),WLEAKAGECHECK = ?,WLEAKAGEVALUE = ?,DATAFROM = ?,BEILV = ?,isSale = "+wm.getIsSale()+",isRecycle = "+wm.getIsRecycle()+"  where WATERMETER_ID = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			if (wm != null)
			{
				ps.setInt(1, wm.getWATERMETER_POINT());
				ps.setInt(2, wm.getAREA_ID());
				ps.setInt(3, wm.getARCHITECTURE_ID());
				ps.setInt(4, wm.getPARENT_ID());
				ps.setInt(5, wm.getGATHER_ID());
				ps.setString(6, wm.getWATERMETER_NUM());
				ps.setString(7, wm.getWATERMETER_NAME());
				ps.setString(8, wm.getWATERMETER_PASSWORD());
				ps.setString(9, wm.getWATERMETER_485ADDRESS());
				ps.setString(10, wm.getMAKER());
				ps.setString(11, wm.getMAKERCODE());
				ps.setString(12, wm.getASSETNO());
				ps.setInt(13, wm.getISSUPPLY());
				ps.setFloat(14, wm.getZVALUE());
				ps.setInt(15, wm.getUSEAMSTYLE());
				ps.setString(16, wm.getCONSUMERNUM());
				ps.setString(17, wm.getCONSUMERNAME());
				ps.setString(18, wm.getCONSUMERPHONE());
				ps.setString(19, wm.getCONSUMERADDRESS());
				ps.setInt(20, wm.getISIMPORTANTUSER());
				ps.setInt(21, wm.getISCOUNTMETER());
				ps.setInt(22, wm.getPARTMENT());
				ps.setInt(23, wm.getFLOOR());
				ps.setInt(24, wm.getMETESTYLE_ID());
				ps.setInt(25, wm.getISCOMPUTATION());
				ps.setInt(26, wm.getPRICE_ID());
				ps.setInt(27, wm.getWATERMETER_STAT());
				ps.setInt(28, wm.getISOFFALARM());
				ps.setInt(29, wm.getCOSTCHECK());
				ps.setInt(30, wm.getSTANDBYCHECK());
				ps.setFloat(31, wm.getXIUZHENG());
				if (wm.getLASTTIME() != null)
					ps.setString(32, wm.getLASTTIME());
				else
					ps.setString(32, df.format(new Date()));
				ps.setInt(33, wm.getWLEAKAGECHECK());
				ps.setFloat(34, wm.getWLEAKAGEVALUE());
				ps.setInt(35, wm.getDATAFROM());
				ps.setFloat(36, wm.getBEILV());
				//ps.setFloat(37, wm.getLimitPart());
				ps.setInt(37, wm.getWATERMETER_ID());
				ps.executeUpdate();
				// if (ps.executeUpdate() == 1)
				// flag = true;
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return flag;
	}

	public boolean deleteWatermeterByID(int ID) throws SQLException
	{
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "delete from watermeter where WATERMETER_ID=?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ID);

			if (ps.executeUpdate() == 1)
				flag = true;
			if (flag)// 删除该表对应的WATERMETERDATAS
			{
				// Connection conn2=null;
				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				try
				{
					sql = "select count(1) as NUM from all_tables where TABLE_NAME = 'ZWATERDATAS" + String.valueOf(ID) + "'";
					// conn2 = ConnDB.getConnection();
					ps2 = conn.prepareStatement(sql);
					rs2 = ps2.executeQuery();
					if (rs2.next())
					{
						String num = rs2.getString("NUM");
						if ("1".equals(num))
						{
							// Connection conn1=null;
							PreparedStatement ps1 = null;
							try
							{
								sql = "drop table ZWATERDATAS" + String.valueOf(ID);
								// conn1 = ConnDB.getConnection();
								ps1 = conn.prepareStatement(sql);
								ps1.executeUpdate();
							} catch (Exception e)
							{
								e.printStackTrace();
							} finally
							{
								ConnDB.release(ps1);
							}
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(ps2, rs2);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return flag;
	}

	public boolean deleteSomeWatermeter(List<Integer> wmIDList) throws SQLException
	{
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "delete from watermeter where WATERMETER_ID in (";

			for (int i = 0; i < wmIDList.size(); i++)
			{
				sql += wmIDList.get(i);
				if (i < wmIDList.size() - 1)
					sql += ",";
				else
					sql += ")";
			}
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			if (ps.executeUpdate() == wmIDList.size())
				flag = true;
			else
				flag = false;

			if (flag)// 删除该表对应的WATERMETERDATAS
			{
				for (int i = 0; i < wmIDList.size(); i++)
				{
					// Connection conn2=null;
					PreparedStatement ps2 = null;
					ResultSet rs2 = null;
					try
					{
						sql = "select count(1) as NUM from all_tables where TABLE_NAME = 'ZWATERDATAS" + wmIDList.get(i) + "'";
						// conn2 = ConnDB.getConnection();
						ps2 = conn.prepareStatement(sql);
						rs2 = ps2.executeQuery();
						if (rs2.next())
						{
							String num = rs2.getString("NUM");
							if ("1".equals(num))
							{
								// Connection conn1=null;
								PreparedStatement ps1 = null;
								try
								{
									sql = "drop table ZWATERDATAS" + wmIDList.get(i);
									// conn1 = ConnDB.getConnection();
									ps1 = conn.prepareStatement(sql);
									ps1.executeUpdate();
								} catch (Exception e)
								{
									e.printStackTrace();
								} finally
								{
									ConnDB.release(ps1);
								}
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(ps2, rs2);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public List<WatermeterModel> paginate(int pageCount, int pageIndex) throws SQLException
	{
		WatermeterModel wm = null;
		List<WatermeterModel> lst = new LinkedList<WatermeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_watermeter ORDER BY WATERMETER_ID desc";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			if (count <= (pageCount * pageIndex))
				return null;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);

			// rs.previous();
			while (rs.next() && (count > 0))
			{
				wm = buildInstance(rs);

				// wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				// wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
				// wm.setAREA_ID(rs.getInt("AREA_ID"));
				// wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				// wm.setPARENT_ID(rs.getInt("PARENT_ID"));
				// wm.setGATHER_ID(rs.getInt("GATHER_ID"));
				// wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
				// wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				// wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
				// wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
				// wm.setMAKER(rs.getString("MAKER"));
				// wm.setMAKERCODE(rs.getString("MAKERCODE"));
				// wm.setASSETNO(rs.getString("ASSETNO"));
				// wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
				// wm.setISSUPPLY(rs.getInt("ZVALUE"));
				// wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
				// wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
				// wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
				// wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
				// wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
				// wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
				// wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
				// wm.setPARTMENT(rs.getInt("PARTMENT"));
				// wm.setFLOOR(rs.getInt("FLOOR"));
				// wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
				// wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
				// wm.setPRICE_ID(rs.getInt("PRICE_ID"));
				// wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
				// wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
				// wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
				// wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
				// wm.setXIUZHENG(rs.getInt("XIUZHENG"));
				// wm.setLASTTIME(rs.getDate("LASTTIME"));
				// wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
				// wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
				// wm.setDATAFROM(rs.getInt("DATAFROM"));
				// wm.setBEILV(rs.getFloat("BEILV"));
				// wm.setLimitPart(rs.getFloat("LimitPart"));

				lst.add(wm);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}
	public String queryNameByID(int watermeterId) throws SQLException
	{

		String name = "------";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT WaterMeter_Name FROM V_watermeter WHERE WATERMETER_ID=" + watermeterId;

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				name = rs.getString("WaterMeter_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return name;
	}
	/**
	 * 查询出全校的水表（排序，分页,用于水表批量设置）
	 * 
	 * @param sortName
	 * @param order
	 * @param pageCount
	 * @param pageIndex
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<WatermeterModel> paginate(String sortName, String order, int pageCount, int pageIndex) throws SQLException
	{
		WatermeterModel wm = null;
		ArrayList<WatermeterModel> lst = new ArrayList<WatermeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_watermeter ";
			if (order.equalsIgnoreCase("desc"))
			{
				sql += " order by " + sortName + " desc";
			} else if (order.equalsIgnoreCase("asc"))
			{
				sql += " order by " + sortName + " asc";
			}
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return null;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);

			// rs.previous();
			while (rs.next() && (count > 0))
			{
				wm = buildInstance(rs);
				// wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				// wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
				// wm.setAREA_ID(rs.getInt("AREA_ID"));
				// wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				// wm.setPARENT_ID(rs.getInt("PARENT_ID"));
				// wm.setGATHER_ID(rs.getInt("GATHER_ID"));
				// wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
				// wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				// wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
				// wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
				// wm.setMAKER(rs.getString("MAKER"));
				// wm.setMAKERCODE(rs.getString("MAKERCODE"));
				// wm.setASSETNO(rs.getString("ASSETNO"));
				// wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
				// wm.setISSUPPLY(rs.getInt("ZVALUE"));
				// wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
				// wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
				// wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
				// wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
				// wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
				// wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
				// wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
				// wm.setPARTMENT(rs.getInt("PARTMENT"));
				// wm.setFLOOR(rs.getInt("FLOOR"));
				// wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
				// wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
				// wm.setPRICE_ID(rs.getInt("PRICE_ID"));
				// wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
				// wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
				// wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
				// wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
				// wm.setXIUZHENG(rs.getInt("XIUZHENG"));
				// wm.setLASTTIME(rs.getDate("LASTTIME"));
				// wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
				// wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
				// wm.setDATAFROM(rs.getInt("DATAFROM"));
				// wm.setBEILV(rs.getFloat("BEILV"));
				// wm.setLimitPart(rs.getFloat("LimitPart"));
				//
				// wm.setArchName(rs.getString("Architecture_Name"));
				// wm.setGatherName(rs.getString("Gather_Name"));
				// wm.setAreaName(rs.getString("Area_Name"));
				// wm.setUseAmStyleName(rs.getString("USEAMFX"));// 分项
				// wm.setUseStyleName(rs.getString("USEAMXZ"));// 性质
				// wm.setPartmentName(rs.getString("PartmentName"));
				// wm.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型
				// wm.setUseAmStyleNameJYZX(rs.getString("USEAMYJZX"));// 一级子项
				// wm.setMeterName(rs.getString("Meter_Name"));// 电表合并之后的名称
				lst.add(wm);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	/**
	 * 通过条件查询出所有水表【注意：此方法用于（批量设置）的列出水表】
	 * 
	 * @param areaID
	 *            区域ID
	 * @param archID
	 *            建筑ID
	 * @param floor
	 *            楼层
	 * @param ammID
	 *            水表ID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<WatermeterModel> searchAll(String sortName, String order, int areaID, int archID, int floor, int ammID, int pageCount,
			int pageIndex) throws SQLException
	{
		ArrayList<WatermeterModel> list = new ArrayList<WatermeterModel>();
		WatermeterModel wm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			String a, b, c, d;
			if (areaID == -1)
			{
				a = "'%'";
			} else
			{
				a = areaID + "";
			}

			if (archID == -1)
			{
				b = "'%'";
			} else
			{
				b = archID + "";
			}

			if (floor == -1)
			{
				c = "'%'";
			} else
			{
				c = floor + "";
			}

			if (ammID == -1)
			{
				d = "'%'";
			} else
			{
				d = ammID + "";
			}

			String sql = "";

			sql = "Select  * from V_Watermeter  where area_ID like " + a + " and Architecture_ID like " + b + " and floor like " + c
					+ " and Watermeter_ID like " + d;
			if (order.equalsIgnoreCase("asc"))
			{
				sql += " order by " + sortName + " asc";
			} else if (order.equalsIgnoreCase("desc"))
			{
				sql += " order by " + sortName + " desc";
			}
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{

				wm = buildInstance(rs);
				// wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				// wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
				// wm.setAREA_ID(rs.getInt("AREA_ID"));
				// wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				// wm.setPARENT_ID(rs.getInt("PARENT_ID"));
				// wm.setGATHER_ID(rs.getInt("GATHER_ID"));
				// wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
				// wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				// wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
				// wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
				// wm.setMAKER(rs.getString("MAKER"));
				// wm.setMAKERCODE(rs.getString("MAKERCODE"));
				// wm.setASSETNO(rs.getString("ASSETNO"));
				// wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
				// wm.setISSUPPLY(rs.getInt("ZVALUE"));
				// wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
				// wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
				// wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
				// wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
				// wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
				// wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
				// wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
				// wm.setPARTMENT(rs.getInt("PARTMENT"));
				// wm.setFLOOR(rs.getInt("FLOOR"));
				// wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
				// wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
				// wm.setPRICE_ID(rs.getInt("PRICE_ID"));
				// wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
				// wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
				// wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
				// wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
				// wm.setXIUZHENG(rs.getInt("XIUZHENG"));
				// wm.setLASTTIME(rs.getDate("LASTTIME"));
				// wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
				// wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
				// wm.setDATAFROM(rs.getInt("DATAFROM"));
				// wm.setBEILV(rs.getFloat("BEILV"));
				// wm.setLimitPart(rs.getFloat("LimitPart"));
				//
				// wm.setArchName(rs.getString("Architecture_Name"));
				// wm.setGatherName(rs.getString("Gather_Name"));
				// wm.setAreaName(rs.getString("Area_Name"));
				// wm.setUseAmStyleName(rs.getString("USEAMFX"));// 分项
				// wm.setUseStyleName(rs.getString("USEAMXZ"));// 性质
				// wm.setPartmentName(rs.getString("PartmentName"));
				// wm.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型
				// wm.setUseAmStyleNameJYZX(rs.getString("USEAMYJZX"));// 一级子项
				// wm.setMeterName(rs.getString("Meter_Name"));// 电表合并之后的名称

				list.add(wm);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public int getRecordCount() throws SQLException
	{
		int count = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT count(*) as recordCount FROM Watermeter";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
				count = rs.getInt("recordCount");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		ConnDB.release(conn, ps, rs);

		return count;

	}

	/**
	 * 修改Ammeter_Num中的分项和一级子项信息
	 * 
	 * @param ammYJFX
	 *            AmmNum中的一级子项14和15位,一级子项包含了分项（14位）
	 * @return
	 * @throws SQLException
	 */
	public boolean editFXNum(int ammID, String ammYJZX) throws SQLException
	{
		boolean b = false;
		String sql = "select WaterMeter_Num from WaterMeter where WaterMeter_ID=" + ammID;
		String oldString = null;
		String newString = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				oldString = rs.getString("WaterMeter_Num");
			}
			// 1到13位+ammYJFX+15位之后的
			newString = oldString.substring(0, 13) + ammYJZX + oldString.substring(15);
			String newSql = "Update WaterMeter Set WaterMeter_Num=? where WaterMeter_ID=" + ammID;
			// Connection conn1=null;
			PreparedStatement ps1 = null;
			try
			{
				// conn1= ConnDB.getConnection();
				ps1 = conn.prepareStatement(newSql);
				ps1.setString(1, newString);
				b = (ps1.executeUpdate() == 1);
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(ps1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return b;

	}

	/**
	 * 修改Ammeter_Num中的分项(不包括一级子项)
	 * 
	 * @param ammFX
	 *            AmmNum中的分项14 000025Fe05801 B 10-->000025Fe05801 A 10
	 * @return
	 * @throws SQLException
	 */
	public boolean editFX(int ammID, String ammFX) throws SQLException
	{
		// 这样不行，replace(x,y,z)返回值为将串X中的Y串用Z串替换后的结果字符串，如果Y有很多则都会修改
		// String newSql =
		// "Update Ammeter Set Ammeter_Num=replace(Ammeter_Num,substr(Ammeter_Num,14,1),'?') where Ammeter_ID="
		// + ammID;
		boolean b = false;

		String sql = "select Watermeter_Num from Watermeter where Watermeter_ID=" + ammID;
		String oldString = null;
		String newString = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				oldString = rs.getString("Watermeter_Num");
			}
			// 1到13位+ammFX（14位）+14位之后的
			newString = oldString.substring(0, 13) + ammFX + oldString.substring(14);
			String newSql = "Update Watermeter Set Watermeter_Num='" + newString + "' where Watermeter_ID=" + ammID;
			// Connection conn1=null;
			PreparedStatement ps1 = null;
			try
			{
				// conn1 = ConnDB.getConnection();
				ps1 = conn.prepareStatement(newSql);
				b = (ps1.executeUpdate() == 1);
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(ps1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return b;
	}

	public List<WatermeterModel> queryByArchID(int archID) throws SQLException
	{
		List<WatermeterModel> lst = new LinkedList<WatermeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Watermeter where Architecture_ID = ? ORDER BY Watermeter_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				WatermeterModel am = buildInstance(rs);
				lst.add(am);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}

	/**
	 * 查询某建筑某层楼的所有水表
	 * 
	 * @param archID
	 * @param floor
	 * @return
	 * @throws SQLException
	 */
	public List<WatermeterModel> queryByArchAndFloor(int archID, int floor) throws SQLException
	{
		WatermeterModel wm = null;
		List<WatermeterModel> lst = new LinkedList<WatermeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_watermeter where Architecture_ID = ? and Floor=? ORDER BY Watermeter_Name";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			ps.setInt(2, floor);
			rs = ps.executeQuery();
			while (rs.next())
			{
				wm = buildInstance(rs);
				// wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				// wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
				// wm.setAREA_ID(rs.getInt("AREA_ID"));
				// wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				// wm.setPARENT_ID(rs.getInt("PARENT_ID"));
				// wm.setGATHER_ID(rs.getInt("GATHER_ID"));
				// wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
				// wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				// wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
				// wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
				// wm.setMAKER(rs.getString("MAKER"));
				// wm.setMAKERCODE(rs.getString("MAKERCODE"));
				// wm.setASSETNO(rs.getString("ASSETNO"));
				// wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
				// wm.setISSUPPLY(rs.getInt("ZVALUE"));
				// wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
				// wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
				// wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
				// wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
				// wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
				// wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
				// wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
				// wm.setPARTMENT(rs.getInt("PARTMENT"));
				// wm.setFLOOR(rs.getInt("FLOOR"));
				// wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
				// wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
				// wm.setPRICE_ID(rs.getInt("PRICE_ID"));
				// wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
				// wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
				// wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
				// wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
				// wm.setXIUZHENG(rs.getInt("XIUZHENG"));
				// wm.setLASTTIME(rs.getDate("LASTTIME"));
				// wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
				// wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
				// wm.setDATAFROM(rs.getInt("DATAFROM"));
				// wm.setBEILV(rs.getFloat("BEILV"));
				// wm.setLimitPart(rs.getFloat("LimitPart"));
				//
				// wm.setArchName(rs.getString("Architecture_Name"));
				// wm.setGatherName(rs.getString("Gather_Name"));
				// wm.setAreaName(rs.getString("Area_Name"));
				// wm.setUseAmStyleName(rs.getString("USEAMFX"));// 分项
				// wm.setUseStyleName(rs.getString("USEAMXZ"));// 性质
				// wm.setPartmentName(rs.getString("PartmentName"));
				// wm.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型
				// wm.setUseAmStyleNameJYZX(rs.getString("USEAMYJZX"));// 一级子项
				// wm.setMeterName(rs.getString("Meter_Name"));// 电表合并之后的名称

				lst.add(wm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	public List<Area> getAllAreaInWaterMeter() throws SQLException
	{
		List<Area> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from Area where Area_ID in (select distinct Area_ID from WaterMeter)";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<Area>();

				Area area = new Area();
				area.setId(rs.getInt("Area_ID"));
				area.setNum(rs.getString("Area_Num"));
				area.setName(rs.getString("Area_Name"));
				area.setAddress(rs.getString("Area_Address"));
				area.setPhone(rs.getString("Area_Phone"));
				area.setRemark(rs.getString("Area_Remark"));

				list.add(area);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<Architecture> getAllArchInWaterMeter() throws SQLException
	{
		List<Architecture> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from Architecture where Architecture_ID in (select distinct Architecture_ID from WaterMeter)";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<Architecture>();

				Architecture arch = new Architecture();

				arch.setId(rs.getInt("Architecture_ID"));
				arch.setAreaID(rs.getInt("Area_ID"));
				arch.setNum(rs.getString("Architecture_Num"));
				arch.setName(rs.getString("Architecture_Name"));
				arch.setStyle(rs.getString("Architecture_Style"));
				arch.setTime(rs.getString("Architecture_Time"));
				arch.setStorey(rs.getInt("Architecture_Storey"));
				arch.setArea(rs.getFloat("Architecture_Area"));
				arch.setAircondition(rs.getFloat("Architecture_Aircondition"));
				arch.setFunction(rs.getString("Architecture_Function"));
				arch.setFL(rs.getString("Architecture_FL"));
				arch.setFloorstart(rs.getInt("Architecture_FloorStart"));
				arch.setImgUrl(rs.getString("Architecture_imgUrl"));
				arch.setPayment(rs.getInt("Architecture_AdvancePayment"));
				arch.setMan(rs.getString("Architecture_Man"));
				arch.setPhone(rs.getString("Architecture_Phone"));
				arch.setCountMan(rs.getInt("Count_Man"));

				list.add(arch);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<Gather> getAllGatherInWaterMeter() throws SQLException
	{
		List<Gather> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from Gather where Gather_ID in (select distinct Gather_ID from WaterMeter)";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<Gather>();

				Gather gather = new Gather();

				gather.setGatherID(rs.getInt("Gather_ID"));
				gather.setAreaID(rs.getInt("Area_ID"));
				gather.setArchitectureID(rs.getInt("Architecture_ID"));
				gather.setDatasiteID(rs.getInt("DataSite_ID"));
				gather.setGatherState(rs.getInt("Gather_State"));
				gather.setGatherNum(rs.getString("Gather_Num"));
				gather.setGatherName(rs.getString("Gather_Name"));
				gather.setGatherAddress(rs.getString("Gather_Address"));
				gather.setGatherPw(rs.getString("Gather_PassWord"));
				gather.setInstallAddress(rs.getString("Gather_Anzhuangaddress"));
				gather.setFactory(rs.getString("Gather_Changshang"));
				gather.setVersion(rs.getString("Gather_Banben"));
				gather.setSize(rs.getString("Gather_Size"));
				gather.setProtocol(rs.getString("Protocol"));
				gather.setSendway(rs.getInt("Sendway"));
				gather.setLastTime(rs.getString("LastTime"));
				gather.setIp(rs.getString("Ip"));
				gather.setLastSetTime(rs.getString("LastSetTime"));
				gather.setLastSetMsg(rs.getString("LastSetMsg"));
				gather.setGatherStyle(rs.getInt("Gather_Style"));

				list.add(gather);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public List<String> getAllAddressInWaterMeter() throws SQLException
	{
		List<String> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select distinct WaterMeter_485Address from WaterMeter";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<String>();

				String addr = rs.getString("WaterMeter_485Address");
				if (addr != null)
					list.add(addr);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<String> getAllNameInWaterMeter() throws SQLException
	{
		List<String> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select distinct WaterMeter_Name from WaterMeter";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<String>();

				String name = rs.getString("WaterMeter_Name");
				if (name != null)
					list.add(name);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<String> getAllConNumInWaterMeter() throws SQLException
	{
		List<String> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select distinct ConsumerNum from WaterMeter";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<String>();

				String name = rs.getString("ConsumerNum");
				if (name != null)
					list.add(name);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public boolean hasAddRepeat(WatermeterModel wm) throws SQLException
	{
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select WaterMeter_ID from WaterMeter where " + "GATHER_ID = ? and (WATERMETER_485ADDRESS = ? or "
					+ "WATERMETER_POINT = ?) and WaterMeter_ID != ? and RowNum = 1";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, wm.getGATHER_ID());
			ps.setString(2, wm.getWATERMETER_485ADDRESS());
			ps.setInt(3, wm.getWATERMETER_POINT());
			ps.setInt(4, wm.getWATERMETER_ID());

			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return flag;
	}

	public boolean hasUpdateRepeat(WatermeterModel wm) throws SQLException
	{
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select WaterMeter_ID from WaterMeter where " + "GATHER_ID = ? and (WATERMETER_485ADDRESS = ? or "
					+ "WATERMETER_POINT = ?) and WaterMeter_ID != ? and RowNum = 1";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, wm.getGATHER_ID());
			ps.setString(2, wm.getWATERMETER_485ADDRESS());
			ps.setInt(3, wm.getWATERMETER_POINT());
			ps.setInt(4, wm.getWATERMETER_ID());

			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return flag;
	}

	public Integer getMaxPointByGather(int gatherID) throws SQLException
	{
		Integer point = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select max(WaterMeter_Point) theMaxPoint from WaterMeter where Gather_ID = ?";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, gatherID);

			rs = ps.executeQuery();

			if (rs.next())
			{
				point = rs.getInt("theMaxPoint");
			}

			if (point == 0)
			{
				point = 1;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return point;
	}

	public boolean checkWmAddrExist(String addr) throws SQLException
	{
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select WaterMeter_ID from WaterMeter where " + "WATERMETER_485ADDRESS = ? and RowNum = 1";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, addr);

			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return flag;
	}

	public Map<String, Object> queryPaginate(int areaID, int archID, int gatherID, String wmAddress, String wmName, String consumerNum,
			String sortLabel, boolean isAsc, int pageCount, int pageIndex) throws SQLException
	{
		List<WatermeterModel> lst = new LinkedList<WatermeterModel>();

		WatermeterModel wm = null;

		Map<String, Object> m = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int total =0;
		int startCount = pageIndex*pageCount+1;
		int endCount = (pageIndex+1)*pageCount;
		String sql = "select * from (select ROWNUM RN ,A.*from ( ";
		sql += buildSql(areaID, archID, gatherID, wmAddress, wmName, consumerNum, sortLabel, isAsc);
		sql+=" )A where ROWNUM<=?) where rn >=?";		
		String countsql = buildSql(areaID, archID, gatherID, wmAddress, wmName, consumerNum, sortLabel, isAsc);
		countsql = countsql.replace("*", "count(*)");
//		System.out.println(sql);
//		System.out.println(startCount+" "+endCount);
//		System.out.println(countsql);
		m = new HashMap<String, Object>();
		try
		{
			
			conn = ConnDB.getConnection();
			/**
			 * 先获取记录条数
			 */
			ps = conn.prepareStatement(countsql);
			rs = ps.executeQuery();
			if(rs.next()){
				total = rs.getInt(1);
			}
			m.put("TotalCount", total);
			
			//m.put("TotalCount", getRecordCount());
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps,rs);
		}
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, endCount);
			ps.setInt(2, startCount);
			rs = ps.executeQuery();
			while (rs.next())
			{
				wm = buildInstance(rs);

				lst.add(wm);
				
			}
			m.put("List", lst);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return m;
	}

	public String buildSql(int areaID, int archID, int gatherID, String wmAddress, String wmName, String consumerNum, String sortLabel, boolean isAsc)
	{
		String sql = "select * from V_WATERMETER ";

		if (areaID != 0 || archID != 0 || gatherID != 0 || (wmAddress != null && !"".equals(wmAddress)) || (wmName != null && !"".equals(wmName))
				|| (consumerNum != null && !"".equals(consumerNum)))
		{
			sql += "where ";

			if (areaID != 0)
			{
				sql += "Area_ID = " + areaID;
			}

			if (archID != 0)
			{
				if (areaID != 0)
				{
					sql += " and";
				}

				sql += " Architecture_ID = " + archID;
			}

			if (gatherID != 0)
			{
				if (areaID != 0 || archID != 0)
				{
					sql += " and";
				}

				sql += " Gather_ID = " + gatherID;
			}

			if (wmAddress != null && !"".equals(wmAddress))
			{
				if (areaID != 0 || archID != 0 || gatherID != 0)
				{
					sql += " and";
				}

				sql += " WaterMeter_485Address like ('%" + wmAddress + "%') ";
			}

			if (wmName != null && !"".equals(wmName))
			{
				if (areaID != 0 || archID != 0 || gatherID != 0 || (wmAddress != null && !"".equals(wmAddress)))
				{
					sql += " and";
				}

				sql += " WaterMeter_Name  like ( '%" + wmName + "%') ";
			}

			if (consumerNum != null && !"".equals(consumerNum))
			{
				if (areaID != 0 || archID != 0 || gatherID != 0 || (wmAddress != null && !"".equals(wmAddress))
						|| (wmName != null && !"".equals(wmName)))
				{
					sql += " and";
				}

				sql += " ConsumerNum  like ( '%" + consumerNum + "%') ";
			}

		}

		if (sortLabel != null && !"".equals(sortLabel))
		{
			if (isAsc)
			{
				sql += " order by " + sortLabel + " asc";
			} else
			{
				sql += " order by " + sortLabel + " desc";
			}
		}

		return sql;
	}

	public void updateBeiLv(int meterStyleID, int newBeiLv) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "update WaterMeter set BeiLv = ? where MeteStyle_ID = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newBeiLv);
			ps.setInt(2, meterStyleID);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
	}

	private WatermeterModel buildInstance(ResultSet rs) throws SQLException
	{
		WatermeterModel wm = new WatermeterModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		wm.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
		wm.setWATERMETER_POINT(rs.getInt("WATERMETER_POINT"));
		wm.setAREA_ID(rs.getInt("AREA_ID"));
		wm.setAreaName(rs.getString("AREA_Name"));
		wm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
		wm.setArchName(rs.getString("ARCHITECTURE_Name"));
		wm.setPARENT_ID(rs.getInt("PARENT_ID"));
		if(rs.getObject("PARENT_ID")!=null){
			wm.setPARENT_NAME(queryNameByID(rs.getInt("PARENT_ID")));
		}
		wm.setGATHER_ID(rs.getInt("GATHER_ID"));
		wm.setGatherName(rs.getString("GATHER_Name"));
		wm.setWATERMETER_NUM(rs.getString("WATERMETER_NUM"));
		wm.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
		wm.setWATERMETER_PASSWORD(rs.getString("WATERMETER_PASSWORD"));
		wm.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
		wm.setMAKER(rs.getString("MAKER"));
		wm.setMAKERCODE(rs.getString("MAKERCODE"));
		wm.setASSETNO(rs.getString("ASSETNO"));
		wm.setISSUPPLY(rs.getInt("ISSUPPLY"));
		wm.setZVALUE(rs.getFloat("ZVALUE"));
		wm.setUSEAMSTYLE(rs.getInt("USEAMSTYLE"));
		wm.setCONSUMERNUM(rs.getString("CONSUMERNUM"));
		wm.setCONSUMERNAME(rs.getString("CONSUMERNAME"));
		wm.setCONSUMERPHONE(rs.getString("CONSUMERPHONE"));
		wm.setCONSUMERADDRESS(rs.getString("CONSUMERADDRESS"));
		wm.setISIMPORTANTUSER(rs.getInt("ISIMPORTANTUSER"));
		wm.setISCOUNTMETER(rs.getInt("ISCOUNTMETER"));
		wm.setPARTMENT(rs.getInt("PARTMENT"));
		wm.setPartmentName(rs.getString("PARTMENTName"));
		wm.setFLOOR(rs.getInt("FLOOR"));
		wm.setMETESTYLE_ID(rs.getInt("METESTYLE_ID"));
		wm.setISCOMPUTATION(rs.getInt("ISCOMPUTATION"));
		wm.setPRICE_ID(rs.getInt("PRICE_ID"));
		wm.setWATERMETER_STAT(rs.getInt("WATERMETER_STAT"));
		wm.setISOFFALARM(rs.getInt("ISOFFALARM"));
		wm.setCOSTCHECK(rs.getInt("COSTCHECK"));
		wm.setSTANDBYCHECK(rs.getInt("STANDBYCHECK"));
		wm.setXIUZHENG(rs.getInt("XIUZHENG"));
		wm.setIsSale(rs.getInt("ISSALE"));
		if (rs.getString("LASTTIME") == null)
		{
			wm.setLASTTIME(df.format(new Date()));
		} else
		{
			try
			{
//				System.out.println(rs.getString("LASTTIME"));
				wm.setLASTTIME(df.format(sdf.parse(rs.getString("LASTTIME"))));
			} catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		wm.setWLEAKAGECHECK(rs.getInt("WLEAKAGECHECK"));
		wm.setWLEAKAGEVALUE(rs.getFloat("WLEAKAGEVALUE"));
		wm.setDATAFROM(rs.getInt("DATAFROM"));
		wm.setBEILV(rs.getFloat("BEILV"));
		wm.setLimitPart(rs.getFloat("LimitPart"));
		wm.setArchName(rs.getString("Architecture_Name"));
		wm.setGatherName(rs.getString("Gather_Name"));
		wm.setAreaName(rs.getString("Area_Name"));
		if ("4C01".equals(sidebarItem))
		{
			String aaString = rs.getString("THISREMAINMONEY");
			if ("".equals(aaString) || aaString == null)
			{
				aaString = "0";
			}
			wm.setUseAmStyleName(aaString);// 剩余金额
		} else
		{
			wm.setUseAmStyleName(rs.getString("USEAMFX"));// 分项
		}

		wm.setUseStyleName(rs.getString("USEAMXZ"));// 性质
		wm.setPartmentName(rs.getString("PartmentName"));
		wm.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型
		wm.setUseAmStyleNameJYZX(rs.getString("USEAMYJZX"));// 一级子项
		wm.setMeterName(rs.getString("Meter_Name"));// 电表合并之后的名称
		wm.setGatherAddress(rs.getString("Gather_Address"));
		wm.setISBig(rs.getInt("ISBig"));
		wm.setISTouChuan(rs.getInt("ISTOUCHUAN"));
		wm.setIsRecycle(rs.getInt("isRecycle"));
		return wm;
	}

	private String buildNum(WatermeterModel wm) throws SQLException
	{
		String num = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select a.XZNum,b.ArchStyle,b.ArchNum from " + "(select nvl(SystemInfo_XZNum,'000000') XZNum "
					+ "from SystemInfo) a,(select nvl(Architecture_Style,'a') " + "ArchStyle,(nvl(Architecture_Num,'001')) ArchNum from "
					+ "Architecture where Architecture_ID=?) b";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, wm.getARCHITECTURE_ID());

			rs = ps.executeQuery();

			String XZNum = null;
			String ArchStyle = null;
			String ArchNum = null;
			if (rs.next())
			{
				XZNum = rs.getString("XZNum");
				ArchStyle = rs.getString("ArchStyle");
				ArchNum = rs.getString("ArchNum");
				XZNum = padLeft(XZNum, 6);
				num = XZNum + "F" + ArchStyle + ArchNum + "02" + wm.getFenlei() + "00";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return num;
	}

	/**
	 * 字符串补齐，不足位用"0"补齐
	 * 
	 * @param s
	 *            需要补齐的字符串
	 * @param length
	 *            需要补齐成的长度
	 * @return 补齐后的字符串
	 */
	private static String padLeft(String s, int length)
	{
		if (s.length() >= length)
		{
			return s.substring(0, length);
		}
		byte[] bs = new byte[length];
		byte[] ss = s.getBytes();
		Arrays.fill(bs, (byte) (48 & 0xff));
		System.arraycopy(ss, 0, bs, length - ss.length, ss.length);
		return new String(bs);
	}

	public void updateAmMeterDatasWithChangeXiuzheng(int watermeter_ID, float oldXiuZheng, float xiuzheng)
	{
		String sql = "update ZWATERDATAS"+watermeter_ID+" set ZVALUEZY=(ZVALUEZY-"+oldXiuZheng+"+ "+xiuzheng+")";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
	}

	public void updateAmMeterDatasWithChangeBeiLV(int watermeter_ID, float oldBeiLv, float beilv, float xiuzheng)
	{
		String sql = "update ZWATERDATAS"+watermeter_ID+" set ZVALUEZY=((ZVALUEZY-"+xiuzheng+")/"+oldBeiLv+"*"+beilv+"+"+xiuzheng+")";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}		
	}
	
	public void updateWaterMeterWithLasttimeandState(int watermeterID,String lasttime)
	{
		String sql = "update watermeter set lasttime=to_date(?,'yyyy-mm-dd hh24:mi:ss'),WATERMETER_STAT=1 where WATERMETER_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, lasttime);
			ps.setInt(2, watermeterID);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
	}

}
