package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sf.energy.expert.model.EnergyDataTabData;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.GetSystemInfo;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class EnergyDataTabImpl
{
	/**
	 * 通过建筑ID,和时间年份，查询能耗数据表相关信息，可获取年的能耗信息
	 * 
	 * @param Architecture_ID
	 *            建筑ID
	 * @param year
	 *            查询的年份
	 * @return 查询的结果集
	 * @throws SQLException
	 */
	public EnergyDataTabData getEnergyDataTabArcByYear(int Architecture_ID, int year) throws SQLException
	{

		EnergyDataTabData edtd = new EnergyDataTabData();
		// ReportModel energyList = null;
		ReportModel energyListYear = null;
		// WaterReportModel waterList = null;
		WaterReportModel waterListYear = null;
		Architecture archModel = null;

		String sql = "select sum(EN07) EN07,sum(EN10) EN10,sum(EN11) EN11,sum(EN12) EN12, architecture_name,architecture_area from architecture,MANUALMONTH "
				+ "where architecture.architecture_id = MANUALMONTH.architecture_id and architecture.architecture_id = ? and selectyear = ? "
				+ " group by architecture_name,architecture_area ";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, Architecture_ID);
			ps.setInt(2, year);
			rs = ps.executeQuery();

			ElecReportHelper EnergyData = new ElecReportHelperImpl();
			WaterReportHelper WaterData = new WaterReportHelperImpl();
			ArchitectureDao archDao = new ArchitectureDao();

			archModel = archDao.queryByID(Architecture_ID);
			// energyList = EnergyData.getArcCountByMonth(Architecture_ID, year,
			// month);
			energyListYear = EnergyData.getArcCountByYear(Architecture_ID, year);
			// waterList = WaterData.getArcCountByMonth(Architecture_ID, year,
			// month);
			waterListYear = WaterData.getArcCountByYear(Architecture_ID, year);
			if (rs.next())
			{
				// //System.out.println("xxx"+Architecture_ID);
				edtd = new EnergyDataTabData();
				edtd.setArcID(Architecture_ID);
				edtd.setArcName(archModel.getName());
				edtd.setArcArea(archModel.getArea());
				edtd.setAmData(energyListYear.getTotalEnergyCount());//
				// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
				if(archModel.getArea()!=0){
					edtd.setAmAreaData(energyListYear.getTotalEnergyCount() / archModel.getArea());
					edtd.setWaterAreaData(waterListYear.getTotalWaterCount() / archModel.getArea());
				}
				
				edtd.setWaterData(waterListYear.getTotalWaterCount());
				// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
				
				edtd.setCoaldata(rs.getInt("EN07"));// 煤
				edtd.setGasdata(rs.getInt("EN10"));// 汽油
				edtd.setCoaloildata(rs.getInt("EN11"));// 煤油
				edtd.setDieseloildata(rs.getInt("EN12"));// 柴油
			} else
			{
				edtd = new EnergyDataTabData();
				edtd.setArcID(Architecture_ID);
				edtd.setArcName(archModel.getName());
				edtd.setArcArea(archModel.getArea());
				edtd.setAmData(energyListYear.getTotalEnergyCount());//
				// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
				if(archModel.getArea()!=0){
					edtd.setAmAreaData(energyListYear.getTotalEnergyCount() / archModel.getArea());
					edtd.setWaterAreaData(waterListYear.getTotalWaterCount() / archModel.getArea());
				}
				edtd.setWaterData(waterListYear.getTotalWaterCount());
				// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
				
				edtd.setCoaldata(0);// 煤
				edtd.setGasdata(0);// 汽油
				edtd.setCoaloildata(0);// 煤油
				edtd.setDieseloildata(0);// 柴油
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		// ps.setInt(1,Architecture_ID );
		// ps.setInt(2, year);
		// rs = ps.executeQuery();
		//
		// ElecReportHelper EnergyData = new ElecReportHelperImpl();
		// WaterReportHelper WaterData = new WaterReportHelperImpl();
		// ArchitectureDao archDao = new ArchitectureDao();
		//
		// archModel = archDao.queryByID(Architecture_ID);
		// //energyList = EnergyData.getArcCountByMonth(Architecture_ID, year,
		// month);
		// energyListYear=EnergyData.getArcCountByYear(Architecture_ID, year);
		// //waterList = WaterData.getArcCountByMonth(Architecture_ID, year,
		// month);
		// waterListYear=WaterData.getArcCountByYear(Architecture_ID, year);
		// if(rs.next())
		// {
		// ////System.out.println("xxx"+Architecture_ID);
		// edtd = new EnergyDataTabData();
		// edtd.setArcID(Architecture_ID);
		// edtd.setArcName(archModel.getName());
		// edtd.setArcArea(archModel.getArea());
		// edtd.setAmData(energyListYear.getTotalEnergyCount());//
		// //edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
		// edtd.setAmAreaData(energyListYear.getTotalEnergyCount()/archModel.getArea());
		// edtd.setWaterData(waterListYear.getTotalWaterCount());
		// //edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
		// edtd.setWaterAreaData(waterListYear.getTotalWaterCount()/archModel.getArea());
		// edtd.setCoaldata(rs.getInt("EN07"));//煤
		// edtd.setGasdata(rs.getInt("EN10"));//汽油
		// edtd.setCoaloildata(rs.getInt("EN11"));//煤油
		// edtd.setDieseloildata(rs.getInt("EN12"));//柴油
		// }
		// else
		// {
		// edtd = new EnergyDataTabData();
		// edtd.setArcID(Architecture_ID);
		// edtd.setArcName(archModel.getName());
		// edtd.setArcArea(archModel.getArea());
		// edtd.setAmData(energyListYear.getTotalEnergyCount());//
		// //edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
		// edtd.setAmAreaData(energyListYear.getTotalEnergyCount()/archModel.getArea());
		// edtd.setWaterData(waterListYear.getTotalWaterCount());
		// //edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
		// edtd.setWaterAreaData(waterListYear.getTotalWaterCount()/archModel.getArea());
		// edtd.setCoaldata(0);//煤
		// edtd.setGasdata(0);//汽油
		// edtd.setCoaloildata(0);//煤油
		// edtd.setDieseloildata(0);//柴油
		// }

		return edtd;
	}

	/**
	 * 查询指定建筑指定月份能耗数据表数据
	 * 
	 * @param Architecture_ID
	 *            建筑ID
	 * @param year
	 *            年
	 * @return 结果集
	 * @throws SQLException
	 */
	public EnergyDataTabData getEnergyDataTabArcByMonth(int Architecture_ID, int year, int month) throws SQLException
	{
		EnergyDataTabData edtd = new EnergyDataTabData();
		ReportModel energyList = null;
		WaterReportModel waterList = null;
		Architecture archModel = null;
		if (Architecture_ID == 0)
		{
			String sql = "select sum(EN07) EN07,sum(EN10) EN10,sum(EN11) EN11,sum(EN12) EN12, architecture_name,architecture_area from architecture,MANUALMONTH "
					+ "where architecture.architecture_id = MANUALMONTH.architecture_id and   selectyear = ? and selectmonth = ? "
					+ " group by architecture_name,architecture_area ";

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, year);
				ps.setInt(2, month);
				rs = ps.executeQuery();

				ElecReportHelper EnergyData = new ElecReportHelperImpl();
				WaterReportHelper WaterData = new WaterReportHelperImpl();

				energyList = EnergyData.getArcCountByMonth(Architecture_ID, year, month);
				waterList = WaterData.getArcCountByMonth(Architecture_ID, year, month);

				if (rs.next())
				{
					// //System.out.println("xxx"+Architecture_ID);
					edtd = new EnergyDataTabData();
					edtd.setArcID(Architecture_ID);
					String schoolName = GetSystemInfo.getSchoolName();
					
					edtd.setArcName(schoolName);
					edtd.setMonth(month);
					edtd.setAmData(energyList.getTotalEnergyCount());//
					// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
//					edtd.setAmAreaData(energyList.getTotalEnergyCount() / archModel.getArea());
					edtd.setWaterData(waterList.getTotalWaterCount());
					// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
//					edtd.setWaterAreaData(waterList.getTotalWaterCount() / archModel.getArea());
					edtd.setCoaldata(rs.getInt("EN07"));// 煤
					edtd.setGasdata(rs.getInt("EN10"));// 汽油
					edtd.setCoaloildata(rs.getInt("EN11"));// 煤油
					edtd.setDieseloildata(rs.getInt("EN12"));// 柴油

				} else
				{
					// //System.out.println("xxx"+Architecture_ID);
					edtd = new EnergyDataTabData();
					edtd.setArcID(Architecture_ID);
					String schoolName = GetSystemInfo.getSchoolName();
					
					edtd.setArcName(schoolName);
					edtd.setMonth(month);
					edtd.setAmData(energyList.getTotalEnergyCount());//
					// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
//					edtd.setAmAreaData(energyList.getTotalEnergyCount() / archModel.getArea());
					edtd.setWaterData(waterList.getTotalWaterCount());
					// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
//					edtd.setWaterAreaData(waterList.getTotalWaterCount() / archModel.getArea());
					edtd.setCoaldata(0);// 煤
					edtd.setGasdata(0);// 汽油
					edtd.setCoaloildata(0);// 煤油
					edtd.setDieseloildata(0);// 柴油
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}
		} else
		{
			String sql = "select sum(EN07) EN07,sum(EN10) EN10,sum(EN11) EN11,sum(EN12) EN12, architecture_name,architecture_area from architecture,MANUALMONTH "
					+ "where architecture.architecture_id = MANUALMONTH.architecture_id and architecture.architecture_id = ? and selectyear = ? and selectmonth = ? "
					+ " group by architecture_name,architecture_area ";

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, Architecture_ID);
				ps.setInt(2, year);
				ps.setInt(3, month);
				rs = ps.executeQuery();

				ElecReportHelper EnergyData = new ElecReportHelperImpl();
				WaterReportHelper WaterData = new WaterReportHelperImpl();
				ArchitectureDao archDao = new ArchitectureDao();

				archModel = archDao.queryByID(Architecture_ID);

				energyList = EnergyData.getArcCountByMonth(Architecture_ID, year, month);
				waterList = WaterData.getArcCountByMonth(Architecture_ID, year, month);

				if (rs.next())
				{
					// //System.out.println("xxx"+Architecture_ID);
					edtd = new EnergyDataTabData();
					edtd.setArcID(Architecture_ID);
					edtd.setMonth(month);
					edtd.setArcName(archModel.getName());
					edtd.setArcArea(archModel.getArea());
					edtd.setAmData(energyList.getTotalEnergyCount());//
					// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
					if( archModel.getArea()!=0){
						edtd.setAmAreaData(energyList.getTotalEnergyCount() / archModel.getArea());
						edtd.setWaterAreaData(waterList.getTotalWaterCount() / archModel.getArea());
					}
					
					edtd.setWaterData(waterList.getTotalWaterCount());
					// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
					
					edtd.setCoaldata(rs.getInt("EN07"));// 煤
					edtd.setGasdata(rs.getInt("EN10"));// 汽油
					edtd.setCoaloildata(rs.getInt("EN11"));// 煤油
					edtd.setDieseloildata(rs.getInt("EN12"));// 柴油

				} else
				{
					// //System.out.println("xxx"+Architecture_ID);
					edtd = new EnergyDataTabData();
					edtd.setArcID(Architecture_ID);
					edtd.setMonth(month);
					edtd.setArcName(archModel.getName());
					edtd.setArcArea(archModel.getArea());
					edtd.setAmData(energyList.getTotalEnergyCount());//
					// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
					if(archModel.getArea()!=0){
						edtd.setAmAreaData(energyList.getTotalEnergyCount() / archModel.getArea());
						edtd.setWaterAreaData(waterList.getTotalWaterCount() / archModel.getArea());
					}
					
					edtd.setWaterData(waterList.getTotalWaterCount());
					// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
					
					edtd.setCoaldata(0);// 煤
					edtd.setGasdata(0);// 汽油
					edtd.setCoaloildata(0);// 煤油
					edtd.setDieseloildata(0);// 柴油
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}
		}

		return edtd;
	}

	/**
	 * 查询指定建筑指定日期能耗数据表数据
	 * 
	 * @param Architecture_ID
	 *            建筑ID
	 * @param year
	 *            年
	 * @return 结果集
	 * @throws SQLException
	 */
	public EnergyDataTabData getEnergyDataTabArcByDay(int Architecture_ID, int year, int month, int day) throws SQLException
	{

		EnergyDataTabData edtd = new EnergyDataTabData();
		// List<ReportModel> energyList = null;
		// List<WaterReportModel> waterList = null;
		if (Architecture_ID == 0)
		{
			float energy = 0;
			float water = 0;
			Architecture archModel = null;

			String sql = "select sum(EN07) EN07,sum(EN10) EN10,sum(EN11) EN11,sum(EN12) EN12, architecture_name,architecture_area from architecture,MANUALDAY "
					+ "where architecture.architecture_id = MANUALDAY.architecture_id and  selectyear = ? and selectmonth = ? and selectmonth = ? "
					+ " group by architecture_name,architecture_area ";

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, year);
				ps.setInt(2, month);
				ps.setInt(3, day);
				rs = ps.executeQuery();

				ElecReportHelperImpl EnergyData = new ElecReportHelperImpl();
				WaterReportHelperImpl WaterData = new WaterReportHelperImpl();

				Date start = new Date();
				start.setYear(year);
				start.setMonth(month);
				start.setDate(day);
				Date end = new Date();
				end.setYear(year);
				end.setMonth(month);
				end.setDate(day);

				// 获取日用量
				// energyList = EnergyData.getArcCountBetween(Architecture_ID,
				// start, end);
				// waterList = WaterData.getArcCountBetween(Architecture_ID,
				// start,
				// end);

				energy = getTotalArcAmByDay(Architecture_ID, year, month, day); // 获取用电量
				water = getTotalArcWaterByDay(Architecture_ID, year, month, day); // 获取用水量

				if (rs.next())
				{
					// //System.out.println("xxx"+Architecture_ID);
					edtd = new EnergyDataTabData();
					edtd.setArcID(Architecture_ID);
					edtd.setMonth(month);
					edtd.setDay(day);
					
					String schoolName = GetSystemInfo.getSchoolName();
					
					 edtd.setArcName(schoolName);
					// edtd.setArcArea(archModel.getArea());
					edtd.setAmData(energy);//
					// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
					// edtd.setAmAreaData(energy / archModel.getArea());
					edtd.setWaterData(water);
					// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
					// edtd.setWaterAreaData(water / archModel.getArea());
					edtd.setCoaldata(rs.getInt("EN07"));// 煤
					edtd.setGasdata(rs.getInt("EN10"));// 汽油
					edtd.setCoaloildata(rs.getInt("EN11"));// 煤油
					edtd.setDieseloildata(rs.getInt("EN12"));// 柴油

				} else
				{
					// //System.out.println("xxx"+Architecture_ID);
					edtd = new EnergyDataTabData();
					edtd.setArcID(Architecture_ID);
					edtd.setMonth(month);
					edtd.setDay(day);
					String schoolName = GetSystemInfo.getSchoolName();
					
					 edtd.setArcName(schoolName);
					//edtd.setArcArea(archModel.getArea());
					edtd.setAmData(energy);//
					// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
//					edtd.setAmAreaData(energy / archModel.getArea());
					edtd.setWaterData(water);
					// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
//					edtd.setWaterAreaData(water / archModel.getArea());
					edtd.setCoaldata(0);// 煤
					edtd.setGasdata(0);// 汽油
					edtd.setCoaloildata(0);// 煤油
					edtd.setDieseloildata(0);// 柴油
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}
		} else
		{
			float energy = 0;
			float water = 0;
			Architecture archModel = null;

			String sql = "select sum(EN07) EN07,sum(EN10) EN10,sum(EN11) EN11,sum(EN12) EN12, architecture_name,architecture_area from architecture,MANUALDAY "
					+ "where architecture.architecture_id = MANUALDAY.architecture_id and architecture.architecture_id = ? and selectyear = ? and selectmonth = ? and selectmonth = ? "
					+ " group by architecture_name,architecture_area ";

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, Architecture_ID);
				ps.setInt(2, year);
				ps.setInt(3, month);
				ps.setInt(4, day);
				rs = ps.executeQuery();

				ElecReportHelperImpl EnergyData = new ElecReportHelperImpl();
				WaterReportHelperImpl WaterData = new WaterReportHelperImpl();
				ArchitectureDao archDao = new ArchitectureDao();

				archModel = archDao.queryByID(Architecture_ID);

				Date start = new Date();
				start.setYear(year);
				start.setMonth(month);
				start.setDate(day);
				Date end = new Date();
				end.setYear(year);
				end.setMonth(month);
				end.setDate(day);

				// 获取日用量
				// energyList = EnergyData.getArcCountBetween(Architecture_ID,
				// start, end);
				// waterList = WaterData.getArcCountBetween(Architecture_ID,
				// start,
				// end);

				energy = getTotalArcAmByDay(Architecture_ID, year, month, day); // 获取用电量
				water = getTotalArcWaterByDay(Architecture_ID, year, month, day); // 获取用水量

				if (rs.next())
				{
					// //System.out.println("xxx"+Architecture_ID);
					edtd = new EnergyDataTabData();
					edtd.setArcID(Architecture_ID);
					edtd.setMonth(month);
					edtd.setDay(day);
					edtd.setArcName(archModel.getName());
					edtd.setArcArea(archModel.getArea());
					edtd.setAmData(energy);//
					// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
					edtd.setAmAreaData(energy / archModel.getArea());
					edtd.setWaterData(water);
					// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
					edtd.setWaterAreaData(water / archModel.getArea());
					edtd.setCoaldata(rs.getInt("EN07"));// 煤
					edtd.setGasdata(rs.getInt("EN10"));// 汽油
					edtd.setCoaloildata(rs.getInt("EN11"));// 煤油
					edtd.setDieseloildata(rs.getInt("EN12"));// 柴油

				} else
				{
					// //System.out.println("xxx"+Architecture_ID);
					edtd = new EnergyDataTabData();
					edtd.setArcID(Architecture_ID);
					edtd.setMonth(month);
					edtd.setDay(day);
					edtd.setArcName(archModel.getName());
					edtd.setArcArea(archModel.getArea());
					edtd.setAmData(energy);//
					// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
					edtd.setAmAreaData(energy / archModel.getArea());
					edtd.setWaterData(water);
					// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
					edtd.setWaterAreaData(water / archModel.getArea());
					edtd.setCoaldata(0);// 煤
					edtd.setGasdata(0);// 汽油
					edtd.setCoaloildata(0);// 煤油
					edtd.setDieseloildata(0);// 柴油
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}

		}
		return edtd;
	}

	/**
	 * 查询指定月份各分项用电情况
	 * 
	 * @param archID
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public EnergyDataTabData getEnergyDataTabDataArcEnergyByMonth(int archID, int year, int month) throws SQLException
	{
		// float totalEnergy = 0; //总用电量
		EnergyDataTabData edtd = new EnergyDataTabData();

		ElecReportHelper EnergyData = new ElecReportHelperImpl();
		// WaterReportHelper WaterData = new WaterReportHelperImpl();

		Map<String, Float> map = null;
		Architecture archModel = null;
		map = EnergyData.getArcFenLeiCountByMonth(archID, year, month); // 用电分项电量

		ArchitectureDao archDao = new ArchitectureDao();
		String schoolName = GetSystemInfo.getSchoolName();
		if(archID==0){
			edtd.setArcName(schoolName);
		}
		else{
			archModel = archDao.queryByID(archID);
			edtd.setArcName(archModel.getName());
		}
		edtd.setMonth(month);
		edtd.setZhaomingchazuo(map.get("照明插座"));
		edtd.setKongtiaoyongdian(map.get("空调用电"));
		edtd.setDongliyongdian(map.get("动力用电"));
		edtd.setTeshuyongdian(map.get("特殊用电"));
		edtd.setTotalenergyCount(map.get("照明插座") + map.get("空调用电") + map.get("动力用电") + map.get("特殊用电"));

		return edtd;
	}

	/**
	 * 查询指定月份各分项用水情况
	 * 
	 * @param archID
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public EnergyDataTabData getEnergyDataTabDataArcWaterByMonth(int archID, int year, int month) throws SQLException
	{
		EnergyDataTabData edtd = new EnergyDataTabData();

		// ElecReportHelper EnergyData = new ElecReportHelperImpl();
		WaterReportHelper WaterData = new WaterReportHelperImpl();

		Map<String, Float> map = null;
		Architecture archModel = null;
		map = WaterData.getArcFenLeiCountByMonth(archID, year, month); // 用水分项水量

		ArchitectureDao archDao = new ArchitectureDao();
		String schoolName = GetSystemInfo.getSchoolName();
		if(archID==0){
			edtd.setArcName(schoolName);
		}
		else{
			archModel = archDao.queryByID(archID);
			edtd.setArcName(archModel.getName());
		}
		edtd.setMonth(month);
		edtd.setZhaomingchazuo(map.get("公共洗手间"));
		edtd.setKongtiaoyongdian(map.get("食堂餐厅"));
		edtd.setDongliyongdian(map.get("澡堂淋浴"));
		edtd.setTeshuyongdian(map.get("消防浇灌"));
		edtd.setTotalenergyCount(map.get("公共洗手间") + map.get("食堂餐厅") + map.get("澡堂淋浴") + map.get("消防浇灌"));

		return edtd;
	}

	/**
	 * 查询指定日期各分项用电情况
	 * 
	 * @param archID
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public EnergyDataTabData getEnergyDataTabDataArcEnergyByDay(int archID, int year, int month, int day) throws SQLException
	{
		// float totalEnergy = 0; //总用电量
		EnergyDataTabData edtd = new EnergyDataTabData();

		ElecReportHelper EnergyData = new ElecReportHelperImpl();
		// WaterReportHelper WaterData = new WaterReportHelperImpl();

		Map<String, Float> map = null;
		Architecture archModel = null;

		
		
		
		Date start = new Date();
		start.setYear(year);
		start.setMonth(month);
		start.setDate(day);
		Date end = new Date();
		end.setYear(year);
		end.setMonth(month);
		end.setDate(day);

		map = EnergyData.getArcFenLeiCountBetween(archID, start, end); // 用电分项电量
		ArchitectureDao archDao = new ArchitectureDao();
		String schoolName = GetSystemInfo.getSchoolName();
		if(archID==0){
			edtd.setArcName(schoolName);
		}
		else{
			archModel = archDao.queryByID(archID);
			edtd.setArcName(archModel.getName());
		}
		edtd.setMonth(month);
		edtd.setDay(day);
		edtd.setZhaomingchazuo(map.get("照明插座"));
		edtd.setKongtiaoyongdian(map.get("空调用电"));
		edtd.setDongliyongdian(map.get("动力用电"));
		edtd.setTeshuyongdian(map.get("特殊用电"));
		edtd.setTotalenergyCount(map.get("照明插座") + map.get("空调用电") + map.get("动力用电") + map.get("特殊用电"));

		return edtd;
	}

	/**
	 * 查询指定日期各分项用水情况
	 * 
	 * @param archID
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public EnergyDataTabData getEnergyDataTabDataArcWaterByDay(int archID, int year, int month, int day) throws SQLException
	{
		// float totalEnergy = 0;
		EnergyDataTabData edtd = new EnergyDataTabData();

		// ElecReportHelper EnergyData = new ElecReportHelperImpl();
		WaterReportHelper WaterData = new WaterReportHelperImpl();

		Map<String, Float> map = null;
		Architecture archModel = null;

		ArchitectureDao archDao = new ArchitectureDao();
		

		Date start = new Date();
		start.setYear(year);
		start.setMonth(month);
		start.setDate(day);
		Date end = new Date();
		end.setYear(year);
		end.setMonth(month);
		end.setDate(day);

		map = WaterData.getArcFenLeiCountBetween(archID, start, end); // 用水分项水量
		String schoolName = GetSystemInfo.getSchoolName();
		if(archID==0){
			edtd.setArcName(schoolName);
		}
		else{
			archModel = archDao.queryByID(archID);
			edtd.setArcName(archModel.getName());
		}
//		archModel = archDao.queryByID(archID);
//		edtd.setArcName(archModel.getName());
		edtd.setMonth(month);
		edtd.setDay(day);
		edtd.setZhaomingchazuo(map.get("公共洗手间"));
		edtd.setKongtiaoyongdian(map.get("食堂餐厅"));
		edtd.setDongliyongdian(map.get("澡堂淋浴"));
		edtd.setTeshuyongdian(map.get("消防浇灌"));
		edtd.setTotalenergyCount(map.get("公共洗手间") + map.get("食堂餐厅") + map.get("澡堂淋浴") + map.get("消防浇灌"));

		return edtd;
	}

	/**
	 * 查询指定建筑指定日期用水量
	 * 
	 * @param archID
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTotalArcWaterByDay(int archID, int year, int month, int day) throws SQLException
	{

		float data = 0;
		if (archID == 0)
		{
			String sql = "select sum(a.ZGross) as ZGross   from  T_ArcDayWater a "
					+ "  where  a.SelectYear = ? and a.SelectMonth = ? and a.SelectDay = ? ";

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, year);
				ps.setInt(2, month);
				ps.setInt(3, day);

				rs = ps.executeQuery();

				if (rs.next())
				{
					data = rs.getFloat("ZGross");
				} else
				{
					data = 0;
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}
		} else
		{
			String sql = "select sum(a.ZGross) as ZGross   from  T_ArcDayWater a "
					+ "  where  a.SelectYear = ? and a.SelectMonth = ? and a.SelectDay = ? " + "and a.Architecture_ID = ?";

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, year);
				ps.setInt(2, month);
				ps.setInt(3, day);
				ps.setInt(4, archID);

				rs = ps.executeQuery();

				if (rs.next())
				{
					data = rs.getFloat("ZGross");
				} else
				{
					data = 0;
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}
		}
		// ps.setInt(1, year);
		// ps.setInt(2, month);
		// ps.setInt(3, day);
		// ps.setInt(4,archID);
		//
		// rs = ps.executeQuery();
		//
		// if(rs.next())
		// {
		// data = rs.getFloat("ZGross");
		// }
		// else
		// {
		// data = 0;
		// }

		return data;
	}

	/**
	 * 查询指定建筑指定日期用电量
	 * 
	 * @param archID
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTotalArcAmByDay(int archID, int year, int month, int day) throws SQLException
	{

		float data = 0;
		if (archID == 0)
		{
			String sql = "select sum(a.ZGross) as ZGross   from  T_ArcDayAm a "
					+ "  where  a.SelectYear = ? and a.SelectMonth = ? and a.SelectDay = ? ";

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, year);
				ps.setInt(2, month);
				ps.setInt(3, day);

				rs = ps.executeQuery();

				if (rs.next())
				{
					data = rs.getFloat("ZGross");
				} else
				{
					data = 0;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}
		} else
		{
			String sql = "select sum(a.ZGross) as ZGross   from  T_ArcDayAm a "
					+ "  where  a.SelectYear = ? and a.SelectMonth = ? and a.SelectDay = ? " + "and a.Architecture_ID = ?";

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, year);
				ps.setInt(2, month);
				ps.setInt(3, day);
				ps.setInt(4, archID);

				rs = ps.executeQuery();

				if (rs.next())
				{
					data = rs.getFloat("ZGross");
				} else
				{
					data = 0;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}

		}

		return data;
	}

	// public List<EnergyDataTabData> getEnergyDataTabDataByStyle(String
	// Style_ID,int year,int month) throws SQLException{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// EnergyDataTabData edtd = new EnergyDataTabData();
	// ReportModel energyList = null;
	// ReportModel energyListYear=null;
	// WaterReportModel waterList = null;
	// WaterReportModel waterListYear=null;
	//
	//
	// String sql =
	// "select EN07,EN10,EN11,EN12, architecture_name,architecture_area from architecture,MANUALMONTH "
	// +
	// "where architecture.architecture_id = MANUALMONTH.architecture_id and architecture.architecture_id = ? and selectyear = ? and selectmonth = ?";
	//
	// pstmt = ConnDB.getConnection().prepareStatement(sql);
	// pstmt.setInt(1,Architecture_ID );
	// pstmt.setInt(2, year);
	// pstmt.setInt(3, month);
	// rs = pstmt.executeQuery();
	//
	// ElecReportHelper EnergyData = new ElecReportHelperImpl();
	// WaterReportHelper WaterData = new WaterReportHelperImpl();
	// energyList = EnergyData.getArcCountByMonth(Architecture_ID, year, month);
	// energyListYear=EnergyData.getArcCountByYear(Architecture_ID, year);
	// waterList = WaterData.getArcCountByMonth(Architecture_ID, year, month);
	// waterListYear=WaterData.getArcCountByYear(Architecture_ID, year);
	// if(rs.next())
	// {
	// ////System.out.println("xxx"+Architecture_ID);
	// edtd = new EnergyDataTabData();
	// edtd.setArcID(Architecture_ID);
	// edtd.setArcName(rs.getString("architecture_name"));
	// edtd.setArcArea(rs.getFloat("architecture_area"));
	// edtd.setAmData(energyList.getTotalEnergyCount());//
	// edtd.setAmmValueYear(energyListYear.getTotalEnergyCount());//年用电量
	// edtd.setAmAreaData(energyList.getTotalEnergyCount()/rs.getFloat("architecture_area"));
	// edtd.setWaterData(waterList.getTotalWaterCount());
	// edtd.setWaterValueYear(waterListYear.getTotalWaterCount());//年用水量
	// edtd.setWaterAreaData(waterList.getTotalWaterCount()/rs.getFloat("architecture_area"));
	// edtd.setCoaldata(rs.getInt("EN07"));//煤
	// edtd.setGasdata(rs.getInt("EN10"));//汽油
	// edtd.setCoaloildata(rs.getInt("EN11"));//煤油
	// edtd.setDieseloildata(rs.getInt("EN12"));//柴油
	//
	// }
	// close(pstmt,rs);
	// return edtd;
	// }

	/**
	 * 通过建筑ID查询建筑分级名称
	 * 
	 * @param Architecture_ID
	 *            建筑ID
	 * @return 查询的结果
	 * @throws SQLException
	 */
	public String getArcSortName(int Architecture_ID) throws SQLException
	{
		String ArcSortName = null;
		String sql = "select architecture_style from architecture where architecture_ID = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, Architecture_ID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				ArcSortName = rs.getString("architecture_style");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		// ps.setInt(1,Architecture_ID );
		// rs = ps.executeQuery();
		// while(rs.next())
		// {
		// ArcSortName = rs.getString("architecture_style");
		// }
		return ArcSortName;

	}

	/**
	 * 查询区域内所有建筑的能耗数据表相关信息
	 */

	public List<EnergyDataTabData> getAreaDataTab(int area_ID, int year, int month) throws SQLException
	{
		return null;
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		// int arch_ID = 0;
		//
		// String sql =
		// "select architecture_ID from architecture  where area_ID = ?";
		// pstmt = ConnDB.getConnection().prepareStatement(sql);
		// pstmt.setInt(1, area_ID);
		// rs = pstmt.executeQuery();
		// List<Integer> list = new ArrayList<Integer>();
		// List<List<EnergyDataTabData>> datalist = new
		// ArrayList<List<EnergyDataTabData>>();
		// while(rs.next())
		// {
		// arch_ID = rs.getInt("architecture_ID");
		// list.add(arch_ID);
		// }
		// for(int i = 0;i<=list.size();i++)
		// {
		// List<EnergyDataTabData> areadata = getEnergyDataTabData(list.get(i),
		// year, month);
		// datalist.add(areadata);
		// }
		// close(pstmt,rs);
		// return datalist;
	}

	/**
	 * 私有方法 关闭查询链接，回收资源
	 * 
	 * @param pstmt
	 * @param rs
	 * @throws SQLException
	 *             //
	 */
	// private void close(PreparedStatement pstmt, ResultSet rs) throws
	// SQLException
	// {
	// if (rs != null)
	// rs.close();
	//
	// if (pstmt != null)
	// pstmt.close();
	// }

}