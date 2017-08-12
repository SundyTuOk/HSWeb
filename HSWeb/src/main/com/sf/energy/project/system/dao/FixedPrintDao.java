package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sf.energy.project.system.model.FixedPrintModel;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.dao.WaterArchitectureFixedDao;
import com.sf.energy.water.manager.current.dao.WaterPartmentFixedDao;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;

public class FixedPrintDao
{
	ElecReportHelperImpl erhi = new ElecReportHelperImpl();
	WaterReportHelperImpl wrhi = new WaterReportHelperImpl();
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 用电建筑打印
	 * 
	 * @param serialNo
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public ArrayList<FixedPrintModel> getArchPrintInfo(String serialNo)
			throws SQLException, NumberFormatException, ParseException
			{
		ArrayList<FixedPrintModel> list = new ArrayList<FixedPrintModel>();
		FixedPrintModel fpm = null;

		ArchitectureFixedDao afd = new ArchitectureFixedDao();

		String BldOrArea_ID = "";
		String AOrBflg = "";
		String name = "";
		String area_Name = "";
		String architecture_Name = "";
		String JSDate = "";
		String Year = "";
		String StartYMD = "";
		String EndYMD = "";
		String LastSerialNo = "";
		String LastYear = "";
		String LastStartYMD = "";
		String LastEndYMD = "";
		String unitName = "";

		String sql = "SELECT c.BldOrArea_ID,c.name, c.AOrBflg,nvl(a.LASTSERIALNO,'0') as LASTSERIALNO,a.StartYMD,a.EndYMD,a.year "
				+ "FROM  BldFinalAccounts a, BldBillSet b, "
				+ "(select Area_ID as BldOrArea_ID, '0' as AOrBflg, Area_Name as name from Area union all select Architecture_ID  as BldOrArea_ID,'1' as AOrBflg, Architecture_Name as name from Architecture) c "
				+ "where a.BillId = b.BillId	And b.BldOrArea_ID = c.BldOrArea_ID And b.AOrBflg = c.AOrBflg And a.serialNo = "
				+ serialNo;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				BldOrArea_ID = rs.getString("BldOrArea_ID");
				AOrBflg = rs.getString("AOrBflg");
				Year = rs.getString("Year");
				StartYMD = rs.getString("StartYMD");
				EndYMD = rs.getString("EndYMD");
				LastSerialNo = rs.getString("LASTSERIALNO");
				name = rs.getString("name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		//		Statement ps = ConnDB.getConnection().createStatement();
		//		//ResultSet rs = sm.executeQuery(sql);
		//		if (rs.next())
		//		{
		//			BldOrArea_ID = rs.getString("BldOrArea_ID");
		//			AOrBflg = rs.getString("AOrBflg");
		//			Year = rs.getString("Year");
		//			StartYMD = rs.getString("StartYMD");
		//			EndYMD = rs.getString("EndYMD");
		//			LastSerialNo = rs.getString("LASTSERIALNO");
		//			name = rs.getString("name");
		//		}
		//		if (rs != null)
		//			rs.close();
		//		if (sm != null)
		//			sm.close();

		if ("1".equals(AOrBflg))
		{
			sql = "select b.Area_Name, a.Architecture_Name from Architecture a,Area b where a.Area_ID = b.Area_ID  And a.Architecture_ID = "
					+ BldOrArea_ID;
			Connection conn1 = null;
			PreparedStatement psName =
					null;
			ResultSet rsName =
					null;
			try
			{
				conn1 = ConnDB.getConnection();
				psName = conn1.prepareStatement(sql);
				rsName = psName.executeQuery();
				if (rsName.next())
				{
					area_Name = rsName.getString("Area_Name");
					architecture_Name = rsName.getString("Architecture_Name");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn1, psName, rsName);
			}
		} else
		{
			area_Name = name;
			architecture_Name = "";
		}

		if (!"0".equals(LastSerialNo))
		{
			sql = "select Year,StartYMD,EndYMD from BldFinalAccounts where SerialNo = "
					+ LastSerialNo;
			Connection conn0=null;
			PreparedStatement psLast =
					null;
			ResultSet rsLast =
					null;
			try
			{
				conn0 = ConnDB.getConnection();
				psLast = conn0.prepareStatement(sql);
				rsLast = psLast.executeQuery();
				if (rsLast.next())
				{
					LastYear = rsLast.getString("Year");
					LastStartYMD = rsLast.getString("StartYMD");
					LastEndYMD = rsLast.getString("EndYMD");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn0, psLast, rsLast);
			}
		}

		sql = "select Partment_Name from Partment where Partment_ID = 1";
		PreparedStatement psUnit =
				null;
		ResultSet rsUnit =
				null;
		Connection conn01=null;
		try
		{
			conn01 = ConnDB.getConnection();

			psUnit = conn01.prepareStatement(sql);
			rsUnit = psUnit.executeQuery();
			if (rsUnit.next())
			{
				unitName = rsUnit.getString("Partment_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn01, psUnit, rsUnit);
		}
		// 结算期间
		JSDate = StartYMD.substring(0, 4) + "年" + StartYMD.substring(4, 6)
				+ "月" + StartYMD.substring(6, 8) + " 至 "
				+ EndYMD.substring(0, 4) + "年" + EndYMD.substring(4, 6) + "月"
				+ EndYMD.substring(6, 8);

		sql = "SELECT Area_ID  as BldOrArea_ID,'0' as AOrBflg FROM Area where Area_ID = "
				+ BldOrArea_ID
				+ " And '"
				+ AOrBflg
				+ "' = '0' "
				+ "union all "
				+ "SELECT Architecture_ID  as BldOrArea_ID,'1' as AOrBflg FROM Architecture where Architecture_ID = "
				+ BldOrArea_ID
				+ " And '"
				+ AOrBflg
				+ "' = '1' "
				+ "union all "
				+ "SELECT Architecture_ID  as BldOrArea_ID,'1' as AOrBflg FROM Architecture where Area_ID = "
				+ BldOrArea_ID + " And '" + AOrBflg + "' = '0'";

		Connection conn2=null;

		PreparedStatement psQuery =
				null;
		ResultSet rsQuery =
				null;
		try
		{
			conn2 = ConnDB.getConnection();
			psQuery = conn2.prepareStatement(sql);
			rsQuery = psQuery.executeQuery();
			while (rsQuery.next())
			{
				fpm = new FixedPrintModel();
				BldOrArea_ID = rsQuery.getString("BldOrArea_ID");
				AOrBflg = rsQuery.getString("AOrBflg");

				fpm.setUnitName(unitName);// 结算单位
				fpm.setReportUnit(unitName);// 制表单位
				fpm.setPeriod(JSDate);// 结算期间
				if ("1".equals(AOrBflg))// 设置结算名称
				{
					sql = "select Architecture_Name from Architecture where Architecture_ID = "
							+ BldOrArea_ID;
					Connection conn1=null;
					PreparedStatement psName =
							null;
					ResultSet rsName =
							null;
					try
					{
						conn1 = ConnDB.getConnection();
						psName = conn1.prepareStatement(sql);
						rsName = psName.executeQuery();
						if (rsName.next())
						{
							architecture_Name = rsName.getString("Architecture_Name");
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn1, psName, rsName);
					}
					fpm.setOrganizeName("建筑：" + area_Name + "." + architecture_Name);
				} else
				{
					fpm.setOrganizeName("区域：" + area_Name);
				}
				fpm.setTotalQuota(String.valueOf(afd.getQuotaInfoByYearBldID(
						BldOrArea_ID, AOrBflg, Year)));// 年度计划

				sql = "select nvl(sum(ZMoney),0)ZMoney, nvl(sum(ZGross),0)ZPower "
						+ "from T_ArcDayAm " + "where SelectYear = " + Year
						+ " and (( Architecture_ID = " + BldOrArea_ID + " And '"
						+ AOrBflg + "' = '1') Or (Area_ID = " + BldOrArea_ID
						+ " And '" + AOrBflg + "' = '0'))";
				Connection conn3=null;
				conn = ConnDB.getConnection();
				PreparedStatement psZ =
						null;
				ResultSet rsZ =
						null;
				try
				{
					conn3 = ConnDB.getConnection();
					psZ = conn3.prepareStatement(sql);
					rsZ = psZ.executeQuery();
					if (rsZ.next())
					{
						fpm.setZMoney(rsZ.getString("ZMoney"));// 年度金额
						fpm.setZPower(rsZ.getString("ZPower"));// 年度电量
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn3, psZ, rsZ);
				}

				if (!"0".equals(LastSerialNo))
				{
					sql = "select nvl(sum(ZMoney),0)ZMoney,nvl(sum(ZGross),0)ZPower from T_ArcDayAm "
							+ "where (( Architecture_ID = "
							+ BldOrArea_ID
							+ " And '"
							+ AOrBflg
							+ "' = '1') Or (Area_ID = "
							+ BldOrArea_ID
							+ " And '"
							+ AOrBflg
							+ "' = '0')) "
							+ "and SelectYear*10000 + SelectMonth*100 + SelectDay >= "
							+ LastStartYMD
							+ " And SelectYear*10000 + SelectMonth*100 + SelectDay <= "
							+ LastEndYMD;
					Connection conn4=null;
					PreparedStatement psL =
							null;
					ResultSet rsL =
							null;
					try
					{
						conn4 = ConnDB.getConnection();
						psL = conn4.prepareStatement(sql);
						rsL = psL.executeQuery();
						if (rsL.next())
						{
							fpm.setLastPower(rsL.getString("ZPower"));// 上期电量
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn4, psL, rsL);
					}
				} else
				{
					fpm.setLastPower("0");// 上期电量
				}

				sql = "select nvl(sum(ZMoney),0)ZMoney,nvl(sum(ZGross),0)ZPower from T_ArcDayAm "
						+ "where (( Architecture_ID = "
						+ BldOrArea_ID
						+ " And '"
						+ AOrBflg
						+ "' = '1') Or (Area_ID = "
						+ BldOrArea_ID
						+ " And '"
						+ AOrBflg
						+ "' = '0')) "
						+ "and SelectYear*10000 + SelectMonth*100 + SelectDay >= "
						+ StartYMD
						+ " And SelectYear*10000 + SelectMonth*100 + SelectDay <= "
						+ EndYMD;

				Connection conn4=null;
				PreparedStatement psT =
						null;
				ResultSet rsT =
						null;
				try
				{
					conn4 = ConnDB.getConnection();
					psT = conn4.prepareCall(sql);
					rsT = psT.executeQuery();
					if (rsT.next())
					{
						fpm.setThisMoney(rsT.getString("ZMoney"));// 本期金额
						fpm.setThisPower(rsT.getString("ZPower"));// 本期电量
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn4, psT, rsT);
				}

				/*********************************************************************************************/
				Map<String, Float> fenlei = new HashMap<String, Float>();
				String fenleiStr = "";
				Map<String, Float> xingzhi = new HashMap<String, Float>();
				String xingzhiStr = "";
				if ("0".equals(AOrBflg))// 区域
				{
					fenlei = erhi.getAreaFenLeiCountBetween(
							Integer.parseInt(BldOrArea_ID), df.parse(StartYMD),
							df.parse(EndYMD));
				} else
				{
					// 建筑
					fenlei = erhi.getArcFenLeiCountBetween(
							Integer.parseInt(BldOrArea_ID), df.parse(StartYMD),
							df.parse(EndYMD));
				}

				for (Entry<String, Float> entry : fenlei.entrySet())
				{

					String key = entry.getKey();

					Float value = entry.getValue();

					fenleiStr += key + ": " + value + "<br>";
				}

				if ("0".equals(AOrBflg))// 区域
				{
					xingzhi = erhi.getAreaStyleCountBetween(
							Integer.parseInt(BldOrArea_ID), df.parse(StartYMD),
							df.parse(EndYMD));
				} else
				{
					// 建筑
					xingzhi = erhi.getArcStyleCountBetween(
							Integer.parseInt(BldOrArea_ID), df.parse(StartYMD),
							df.parse(EndYMD));
				}

				for (Entry<String, Float> entry : xingzhi.entrySet())
				{

					String key = entry.getKey();

					Float value = entry.getValue();

					xingzhiStr += key + ": " + value + "<br>";
				}

				fpm.setFenxiangDetil(fenleiStr);
				fpm.setXingzhiDetil(xingzhiStr);
				/*********************************************************************************************/

				if (Double.valueOf(fpm.getZPower()) > 0)
				{
					double price = 0;
					price = (double) Math.round(Double.valueOf(fpm.getZMoney())
							/ Double.valueOf(fpm.getZPower()) * 100) / 100;
					fpm.setPrice(String.valueOf(price));
				} else
				{
					fpm.setPrice("-");
				}

				double remainQuota = 0;
				remainQuota = (double) Math.round((Double.valueOf(fpm
						.getTotalQuota()) - Double.valueOf(fpm.getZMoney())) * 100) / 100;
				fpm.setRemainQuota(String.valueOf(remainQuota));

				list.add(fpm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, psQuery, rsQuery);
		}

		return list;
			}

	/**
	 * 用水建筑打印
	 * 
	 * @param serialNo
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public ArrayList<FixedPrintModel> getArchWaterPrintInfo(String serialNo)
			throws SQLException, NumberFormatException, ParseException
			{
		ArrayList<FixedPrintModel> list = new ArrayList<FixedPrintModel>();
		FixedPrintModel fpm = null;

		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();

		String BldOrArea_ID = "";
		String AOrBflg = "";
		String name = "";
		String area_Name = "";
		String architecture_Name = "";
		String JSDate = "";
		String Year = "";
		String StartYMD = "";
		String EndYMD = "";
		String LastSerialNo = "";
		String LastYear = "";
		String LastStartYMD = "";
		String LastEndYMD = "";
		String unitName = "";

		String sql = "SELECT c.BldOrArea_ID,c.name, c.AOrBflg,nvl(a.LASTSERIALNO,'0') as LASTSERIALNO,a.StartYMD,a.EndYMD,a.year "
				+ "FROM  BLDFINALACCOUNTSWATER a, BLDBILLSETWATER b, "
				+ "(select Area_ID as BldOrArea_ID, '0' as AOrBflg, Area_Name as name from Area union all select Architecture_ID  as BldOrArea_ID,'1' as AOrBflg, Architecture_Name as name from Architecture) c "
				+ "where a.BillId = b.BillId	And b.BldOrArea_ID = c.BldOrArea_ID And b.AOrBflg = c.AOrBflg And a.serialNo = "
				+ serialNo;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
			{
				BldOrArea_ID = rs.getString("BldOrArea_ID");
				AOrBflg = rs.getString("AOrBflg");
				Year = rs.getString("Year");
				StartYMD = rs.getString("StartYMD");
				EndYMD = rs.getString("EndYMD");
				LastSerialNo = rs.getString("LASTSERIALNO");
				name = rs.getString("name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		//		Statement sm = ConnDB.getConnection().createStatement();
		//		ResultSet rs = sm.executeQuery(sql);
		//		if (rs.next())
		//		{
		//			BldOrArea_ID = rs.getString("BldOrArea_ID");
		//			AOrBflg = rs.getString("AOrBflg");
		//			Year = rs.getString("Year");
		//			StartYMD = rs.getString("StartYMD");
		//			EndYMD = rs.getString("EndYMD");
		//			LastSerialNo = rs.getString("LASTSERIALNO");
		//			name = rs.getString("name");
		//		}
		//		if (rs != null)
		//			rs.close();
		//		if (sm != null)
		//			sm.close();

		if ("1".equals(AOrBflg))
		{
			sql = "select b.Area_Name, a.Architecture_Name from Architecture a,Area b where a.Area_ID = b.Area_ID  And a.Architecture_ID = "
					+ BldOrArea_ID;
			Connection conn1=null;
			PreparedStatement psName =
					null;
			ResultSet rsName =
					null;
			try
			{	
				conn1 = ConnDB.getConnection();
				psName = conn1.prepareStatement(sql);
				rsName = psName.executeQuery();
				if (rsName.next())
				{
					area_Name = rsName.getString("Area_Name");
					architecture_Name = rsName.getString("Architecture_Name");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn1, psName, rsName);
			}
		} else
		{
			area_Name = name;
			architecture_Name = "";
		}

		if (!"0".equals(LastSerialNo))
		{
			sql = "select Year,StartYMD,EndYMD from BldFinalAccounts where SerialNo = "
					+ LastSerialNo;
			Connection conn2=null;
			PreparedStatement psLast =
					null;
			ResultSet rsLast =
					null;
			try
			{
				conn2 = ConnDB.getConnection();
				psLast = conn2.prepareCall(sql);
				rsLast = psLast.executeQuery();
				if (rsLast.next())
				{
					LastYear = rsLast.getString("Year");
					LastStartYMD = rsLast.getString("StartYMD");
					LastEndYMD = rsLast.getString("EndYMD");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn2, psLast, rsLast);
			}
		}

		sql = "select Partment_Name from Partment where Partment_ID = 1";
		Connection conn2=null;
		PreparedStatement psUnit =
				null;
		ResultSet rsUnit =
				null;
		try
		{		
			conn2 = ConnDB.getConnection();
			psUnit = conn2.prepareStatement(sql);
			rsUnit = psUnit.executeQuery();
			if (rsUnit.next())
			{
				unitName = rsUnit.getString("Partment_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, psUnit, rsUnit);
		}
		// 结算期间
		JSDate = StartYMD.substring(0, 4) + "年" + StartYMD.substring(4, 6)
				+ "月" + StartYMD.substring(6, 8) + " 至 "
				+ EndYMD.substring(0, 4) + "年" + EndYMD.substring(4, 6) + "月"
				+ EndYMD.substring(6, 8);

		sql = "SELECT Area_ID  as BldOrArea_ID,'0' as AOrBflg FROM Area where Area_ID = "
				+ BldOrArea_ID
				+ " And '"
				+ AOrBflg
				+ "' = '0' "
				+ "union all "
				+ "SELECT Architecture_ID  as BldOrArea_ID,'1' as AOrBflg FROM Architecture where Architecture_ID = "
				+ BldOrArea_ID
				+ " And '"
				+ AOrBflg
				+ "' = '1' "
				+ "union all "
				+ "SELECT Architecture_ID  as BldOrArea_ID,'1' as AOrBflg FROM Architecture where Area_ID = "
				+ BldOrArea_ID + " And '" + AOrBflg + "' = '0'";

		PreparedStatement psQuery =
				null;
		ResultSet rsQuery =
				null;
		Connection conn0=null;
		try
		{
			conn0 = ConnDB.getConnection();
			psQuery = conn0.prepareStatement(sql);
			rsQuery = psQuery.executeQuery();
			while (rsQuery.next())
			{
				fpm = new FixedPrintModel();
				BldOrArea_ID = rsQuery.getString("BldOrArea_ID");
				AOrBflg = rsQuery.getString("AOrBflg");

				fpm.setUnitName(unitName);// 结算单位
				fpm.setReportUnit(unitName);// 制表单位
				fpm.setPeriod(JSDate);// 结算期间
				if ("1".equals(AOrBflg))// 设置结算名称
				{
					sql = "select Architecture_Name from Architecture where Architecture_ID = "
							+ BldOrArea_ID;
					Connection conn1=null;
					PreparedStatement psName =
							null;
					ResultSet rsName =
							null;
					try
					{
						conn1 = ConnDB.getConnection();
						psName = conn1.prepareStatement(sql);
						rsName = psName.executeQuery();
						if (rsName.next())
						{
							architecture_Name = rsName.getString("Architecture_Name");
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn1, psName, rsName);
					}
					fpm.setOrganizeName("建筑：" + area_Name + "." + architecture_Name);
				} else
				{
					fpm.setOrganizeName("区域：" + area_Name);
				}
				fpm.setTotalQuota(String.valueOf(wafd.getWaterQuotaInfoByYearBldID(
						BldOrArea_ID, AOrBflg, Year)));// 年度计划

				sql = "select nvl(sum(ZMoney),0)ZMoney, nvl(sum(ZGross),0)ZPower "
						+ "from T_ArcDayWater " + "where SelectYear = " + Year
						+ " and (( Architecture_ID = " + BldOrArea_ID + " And '"
						+ AOrBflg + "' = '1') Or (Area_ID = " + BldOrArea_ID
						+ " And '" + AOrBflg + "' = '0'))";

				Connection connz=null;
				PreparedStatement psZ =
						null;
				ResultSet rsZ =
						null;
				try
				{
					connz = ConnDB.getConnection();
					psZ = connz.prepareCall(sql);
					rsZ = psZ.executeQuery();
					if (rsZ.next())
					{
						fpm.setZMoney(rsZ.getString("ZMoney"));// 年度金额
						fpm.setZPower(rsZ.getString("ZPower"));// 年度电量
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(connz, psZ, rsZ);
				}

				if (!"0".equals(LastSerialNo))
				{
					sql = "select nvl(sum(ZMoney),0)ZMoney,nvl(sum(ZGross),0)ZPower from T_ArcDayWater "
							+ "where (( Architecture_ID = "
							+ BldOrArea_ID
							+ " And '"
							+ AOrBflg
							+ "' = '1') Or (Area_ID = "
							+ BldOrArea_ID
							+ " And '"
							+ AOrBflg
							+ "' = '0')) "
							+ "and SelectYear*10000 + SelectMonth*100 + SelectDay >= "
							+ LastStartYMD
							+ " And SelectYear*10000 + SelectMonth*100 + SelectDay <= "
							+ LastEndYMD;
					ResultSet rsL =
							null;
					PreparedStatement psL =
							null;
					Connection connL=null;
					try
					{
						connL = ConnDB.getConnection();
						psL = connL.prepareStatement(sql);
						rsL = psL.executeQuery();
						if (rsL.next())
						{
							fpm.setLastPower(rsL.getString("ZPower"));// 上期电量
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(connL, psL, rsL);
					}
				} else
				{
					fpm.setLastPower("0");// 上期电量
				}

				sql = "select nvl(sum(ZMoney),0)ZMoney,nvl(sum(ZGross),0)ZPower from T_ArcDayWater "
						+ "where (( Architecture_ID = "
						+ BldOrArea_ID
						+ " And '"
						+ AOrBflg
						+ "' = '1') Or (Area_ID = "
						+ BldOrArea_ID
						+ " And '"
						+ AOrBflg
						+ "' = '0')) "
						+ "and SelectYear*10000 + SelectMonth*100 + SelectDay >= "
						+ StartYMD
						+ " And SelectYear*10000 + SelectMonth*100 + SelectDay <= "
						+ EndYMD;
				PreparedStatement psT =
						null;
				ResultSet rsT =
						null;
				Connection connT=null;
				try
				{
					connT = ConnDB.getConnection();
					psT = connT.prepareStatement(sql);
					rsT = psT.executeQuery();
					if (rsT.next())
					{
						fpm.setThisMoney(rsT.getString("ZMoney"));// 本期金额
						fpm.setThisPower(rsT.getString("ZPower"));// 本期电量
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(connT, psT, rsT);
				}

				/*********************************************************************************************/
				Map<String, Float> fenlei = new HashMap<String, Float>();
				String fenleiStr = "";
				Map<String, Float> xingzhi = new HashMap<String, Float>();
				String xingzhiStr = "";
				if ("0".equals(AOrBflg))// 区域
				{
					fenlei = wrhi.getAreaFenLeiCountBetween(
							Integer.parseInt(BldOrArea_ID), df.parse(StartYMD),
							df.parse(EndYMD));
				} else
				{
					// 建筑
					fenlei = wrhi.getArcFenLeiCountBetween(
							Integer.parseInt(BldOrArea_ID), df.parse(StartYMD),
							df.parse(EndYMD));
				}

				for (Entry<String, Float> entry : fenlei.entrySet())
				{

					String key = entry.getKey();

					Float value = entry.getValue();

					fenleiStr += key + ": " + value + "<br>";
				}

				if ("0".equals(AOrBflg))// 区域
				{
					xingzhi = wrhi.getAreaStyleCountBetween(
							Integer.parseInt(BldOrArea_ID), df.parse(StartYMD),
							df.parse(EndYMD));
				} else
				{
					// 建筑
					xingzhi = wrhi.getArcStyleCountBetween(
							Integer.parseInt(BldOrArea_ID), df.parse(StartYMD),
							df.parse(EndYMD));
				}

				for (Entry<String, Float> entry : xingzhi.entrySet())
				{

					String key = entry.getKey();

					Float value = entry.getValue();

					xingzhiStr += key + ": " + value + "<br>";
				}

				fpm.setFenxiangDetil(fenleiStr);
				fpm.setXingzhiDetil(xingzhiStr);
				/*********************************************************************************************/

				if (Double.valueOf(fpm.getZPower()) > 0)
				{
					double price = 0;
					price = (double) Math.round(Double.valueOf(fpm.getZMoney())
							/ Double.valueOf(fpm.getZPower()) * 100) / 100;
					fpm.setPrice(String.valueOf(price));
				} else
				{
					fpm.setPrice("-");
				}

				double remainQuota = 0;
				remainQuota = (double) Math.round((Double.valueOf(fpm
						.getTotalQuota()) - Double.valueOf(fpm.getZMoney())) * 100) / 100;
				fpm.setRemainQuota(String.valueOf(remainQuota));

				list.add(fpm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, psQuery, rsQuery);
		}

		return list;
			}

	/**
	 * 用电部门打印
	 * 
	 * @param serialNo
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public ArrayList<FixedPrintModel> getDeptWaterPrintInfo(String serialNo)
			throws SQLException, NumberFormatException, ParseException
			{
		ArrayList<FixedPrintModel> list = new ArrayList<FixedPrintModel>();
		FixedPrintModel fpm = null;

		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();

		String DeptID = "";
		String name = "";
		String JSDate = "";
		String Year = "";
		String StartYMD = "";
		String EndYMD = "";
		String LastSerialNo = "";
		String LastYear = "";
		String LastStartYMD = "";
		String LastEndYMD = "";
		String unitName = "";
		String reportUnit = "";
		String PrefixFullName = ""; // 父部门全称
		String ParentDeptID = ""; // 父部门ID
		String ParentDeptName = ""; // 父部门名称

		String sql = "SELECT  c.Partment_ID,c.Partment_Name,a.Year,nvl(a.LASTSERIALNO,'0') as LASTSERIALNO,a.StartYMD,a.EndYMD "
				+ "FROM DEPTFINALACCOUNTSWATER a,DEPTBILLSETWATER b,Partment c "
				+ "where a.BillId = b.BillId And b.Partment_ID = c.Partment_ID And a.serialNo = "
				+ serialNo;
		PreparedStatement ps =
				null;
		ResultSet rs =
				null;
		Connection conn=null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				DeptID = rs.getString("Partment_ID");
				Year = rs.getString("Year");
				StartYMD = rs.getString("StartYMD");
				EndYMD = rs.getString("EndYMD");
				LastSerialNo = rs.getString("LASTSERIALNO");
				name = rs.getString("Partment_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		ParentDeptID = DeptID;

		if ("1".equals(ParentDeptID))
		{
			sql = "select Partment_Name from Partment where Partment_ID = 1";
			Connection connUnit=null;
			PreparedStatement psUnit =
					null;
			ResultSet rsUnit =
					null;
			try
			{
				connUnit = ConnDB.getConnection();
				psUnit = connUnit.prepareStatement(sql);
				rsUnit = psUnit.executeQuery();
				if (rsUnit.next())
				{
					unitName = rsUnit.getString("Partment_Name");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(connUnit, psUnit, rsUnit);
			}
		}

		while (!"1".equals(ParentDeptID))
		{
			sql = "select p.Partment_ID,p.Partment_Name from Partment p,Partment c where c.Partment_ID = "
					+ ParentDeptID + " And c.Partment_Parent = p.Partment_ID";
			PreparedStatement psUnit =
					null;
			ResultSet rsUnit =
					null;
			Connection connUnit=null;
			try
			{
				connUnit = ConnDB.getConnection();
				psUnit = connUnit.prepareStatement(sql);
				rsUnit = psUnit.executeQuery();
				if (rsUnit.next())
				{
					ParentDeptID = rsUnit.getString("Partment_ID");
					ParentDeptName = rsUnit.getString("Partment_Name");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(connUnit, psUnit, rsUnit);
			}

			// 部门全称取得
			PrefixFullName = ParentDeptName + '．' + PrefixFullName;
			// 单位名称取得
			unitName = ParentDeptName;
		}

		if (!"0".equals(LastSerialNo))
		{
			sql = "select Year,StartYMD,EndYMD from DEPTFINALACCOUNTSWATER where SerialNo = "
					+ LastSerialNo;
			PreparedStatement psLast =
					null;
			ResultSet rsLast =
					null;
			Connection connLast=null;
			try
			{
				connLast = ConnDB.getConnection();
				psLast = connLast.prepareStatement(sql);
				rsLast = psLast.executeQuery();
				if (rsLast.next())
				{
					LastYear = rsLast.getString("Year");
					LastStartYMD = rsLast.getString("StartYMD");
					LastEndYMD = rsLast.getString("EndYMD");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(connLast, psLast, rsLast);
			}
		}

		// 结算期间
		JSDate = StartYMD.substring(0, 4) + "年" + StartYMD.substring(4, 6)
				+ "月" + StartYMD.substring(6, 8) + " 至 "
				+ EndYMD.substring(0, 4) + "年" + EndYMD.substring(4, 6) + "月"
				+ EndYMD.substring(6, 8);
		// 制表单位
		reportUnit = PrefixFullName + name;

		sql = "SELECT Partment_ID FROM Partment START WITH Partment_ID = "
				+ DeptID + " CONNECT BY PRIOR Partment_ID = Partment_Parent";

		PreparedStatement psQuery =
				null;
		ResultSet rsQuery =
				null;
		Connection connQuery=null;
		try
		{
			connQuery = ConnDB.getConnection();
			psQuery = connQuery.prepareStatement(sql);
			rsQuery = psQuery.executeQuery();
			while (rsQuery.next())
			{
				String varDeptID = rsQuery.getString("Partment_ID");
				fpm = new FixedPrintModel();

				fpm.setUnitName(unitName);// 结算单位
				fpm.setReportUnit(reportUnit);// 制表单位
				fpm.setPeriod(JSDate);// 结算期间

				String Partment_Name = "";
				sql = "select Partment_Name from Partment where Partment_ID = "
						+ varDeptID;
				PreparedStatement psPName =
						null;
				ResultSet rsPName =
						null;
				Connection connName=null;
				try
				{
					connName = ConnDB.getConnection();
					psPName = connName.prepareStatement(sql);
					rsPName = psPName.executeQuery();
					if (rsPName.next())
					{
						Partment_Name = rsPName.getString("Partment_Name");
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(connName, psPName, rsPName);
				}

				if (DeptID.equals(varDeptID))// 设置结算名称
				{
					fpm.setOrganizeName("部门：" + PrefixFullName + Partment_Name);
				} else
				{
					fpm.setOrganizeName("部门:" + PrefixFullName + name + '．'
							+ Partment_Name);
				}
				fpm.setTotalQuota(String.valueOf(wpfd.getQuotaInfoByYearDeptID(
						varDeptID, Year)));// 年度计划

				sql = "select s0.ZMoney,s0.ZGross "
						+ "from (select s1.partment_id,nvl(sum(s1.ZMoney),0) AS  ZMoney,nvl(sum(s1.ZGross),0) AS ZGross from T_PartmentDayWater s1 where s1.SelectYear = "
						+ Year + " group by s1.partment_id)s0 "
						+ "where s0.Partment_ID = " + varDeptID;
				PreparedStatement psZ =
						null;
				ResultSet rsZ =
						null;
				Connection connZ=null;
				try
				{
					connZ = ConnDB.getConnection();
					psZ = connZ.prepareStatement(sql);
					rsZ = psZ.executeQuery();
					if (rsZ.next())
					{
						fpm.setZMoney(rsZ.getString("ZMoney"));// 年度金额
						fpm.setZPower(rsZ.getString("ZGross"));// 年度电量
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(connZ, psZ, rsZ);
				}

				if (!"0".equals(LastSerialNo))
				{
					sql = "select s0.ZGross from (select s1.partment_id,nvl(sum(s1.ZMoney),0) AS  ZMoney,nvl(sum(s1.ZGross),0) AS ZGross from T_PartmentDayWater s1 where s1.SelectYear*10000 + s1.SelectMonth*100 + s1.SelectDay >= "
							+ LastStartYMD
							+ " And s1.SelectYear*10000 + s1.SelectMonth*100 + s1.SelectDay <= "
							+ LastEndYMD
							+ " group by s1.partment_id )s0 where s0.Partment_ID = "
							+ varDeptID;
					PreparedStatement psL =
							null;
					ResultSet rsL =
							null;
					Connection connL=null;
					try
					{
						connL = ConnDB.getConnection();
						psL = connL.prepareStatement(sql);
						rsL = psL.executeQuery();
						if (rsL.next())
						{
							fpm.setLastPower(rsL.getString("ZGross"));// 上期电量
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(connL, psL, rsL);
					}
				} else
				{
					fpm.setLastPower("0");// 上期电量
				}

				sql = "select s0.ZMoney,s0.ZGross from (select s1.partment_id,nvl(sum(s1.ZMoney),0) AS  ZMoney,nvl(sum(s1.ZGross),0) AS ZGross from T_PartmentDayWater s1 where s1.SelectYear*10000 + s1.SelectMonth*100 + s1.SelectDay >= "
						+ StartYMD
						+ " And s1.SelectYear*10000 + s1.SelectMonth*100 + s1.SelectDay <= "
						+ EndYMD
						+ " group by s1.partment_id )s0 where s0.Partment_ID = "
						+ varDeptID;
				PreparedStatement psT =
						null;
				ResultSet rsT =
						null;
				Connection connT=null;
				try
				{
					connT = ConnDB.getConnection();
					psT = connT.prepareStatement(sql);
					rsT = psT.executeQuery();
					if (rsT.next())
					{
						fpm.setThisMoney(rsT.getString("ZMoney"));// 本期金额
						fpm.setThisPower(rsT.getString("ZGross"));// 本期电量
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(connT, psT, rsT);
				}

				/*********************************************************************************************/
				Map<String, Float> fenlei = new HashMap<String, Float>();
				String fenleiStr = "";
				Map<String, Float> xingzhi = new HashMap<String, Float>();
				String xingzhiStr = "";
				fenlei = wrhi.getGroupFenLeiCountBetween(
						Integer.parseInt(varDeptID), df.parse(StartYMD),
						df.parse(EndYMD));

				for (Entry<String, Float> entry : fenlei.entrySet())
				{

					String key = entry.getKey();

					Float value = entry.getValue();

					fenleiStr += key + ": " + value + "<br>";
				}

				xingzhi = wrhi.getGroupStyleCountBetween(
						Integer.parseInt(varDeptID), df.parse(StartYMD),
						df.parse(EndYMD));

				for (Entry<String, Float> entry : xingzhi.entrySet())
				{

					String key = entry.getKey();

					Float value = entry.getValue();

					xingzhiStr += key + ": " + value + "<br>";
				}

				fpm.setFenxiangDetil(fenleiStr);
				fpm.setXingzhiDetil(xingzhiStr);
				/*********************************************************************************************/

				if (Double.valueOf(fpm.getZPower()) > 0)
				{
					double price = 0;
					price = (double) Math.round(Double.valueOf(fpm.getZMoney())
							/ Double.valueOf(fpm.getZPower()) * 100) / 100;
					fpm.setPrice(String.valueOf(price));
				} else
				{
					fpm.setPrice("-");
				}

				double remainQuota = 0;
				remainQuota = (double) Math.round((Double.valueOf(fpm
						.getTotalQuota()) - Double.valueOf(fpm.getZMoney())) * 100) / 100;
				fpm.setRemainQuota(String.valueOf(remainQuota));

				list.add(fpm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(connQuery, psQuery, rsQuery);
		}

		return list;
			}

	/**
	 * 用水部门打印
	 * 
	 * @param serialNo
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public ArrayList<FixedPrintModel> getDeptPrintInfo(String serialNo)
			throws SQLException, NumberFormatException, ParseException
			{
		ArrayList<FixedPrintModel> list = new ArrayList<FixedPrintModel>();
		FixedPrintModel fpm = null;

		PartmentFixedDao pfd = new PartmentFixedDao();

		String DeptID = "";
		String name = "";
		String JSDate = "";
		String Year = "";
		String StartYMD = "";
		String EndYMD = "";
		String LastSerialNo = "";
		String LastYear = "";
		String LastStartYMD = "";
		String LastEndYMD = "";
		String unitName = "";
		String reportUnit = "";
		String PrefixFullName = ""; // 父部门全称
		String ParentDeptID = ""; // 父部门ID
		String ParentDeptName = ""; // 父部门名称

		String sql = "SELECT  c.Partment_ID,c.Partment_Name,a.Year,nvl(a.LASTSERIALNO,'0') as LASTSERIALNO,a.StartYMD,a.EndYMD "
				+ "FROM DeptFinalAccounts a,DeptBillSet b,Partment c "
				+ "where a.BillId = b.BillId And b.Partment_ID = c.Partment_ID And a.serialNo = "
				+ serialNo;
		PreparedStatement ps =
				null;
		ResultSet rs =
				null;
		Connection conn=null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				DeptID = rs.getString("Partment_ID");
				Year = rs.getString("Year");
				StartYMD = rs.getString("StartYMD");
				EndYMD = rs.getString("EndYMD");
				LastSerialNo = rs.getString("LASTSERIALNO");
				name = rs.getString("Partment_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		ParentDeptID = DeptID;

		if ("1".equals(ParentDeptID))
		{
			sql = "select Partment_Name from Partment where Partment_ID = 1";
			PreparedStatement psUnit =
					null;
			ResultSet rsUnit =
					null;
			Connection connUnit=null;
			try
			{
				connUnit = ConnDB.getConnection();
				psUnit = connUnit.prepareStatement(sql);
				rsUnit = psUnit.executeQuery();
				if (rsUnit.next())
				{
					unitName = rsUnit.getString("Partment_Name");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(connUnit, psUnit, rsUnit);
			}
		}

		while (!"1".equals(ParentDeptID))
		{
			sql = "select p.Partment_ID,p.Partment_Name from Partment p,Partment c where c.Partment_ID = "
					+ ParentDeptID + " And c.Partment_Parent = p.Partment_ID";
			PreparedStatement psUnit =
					null;
			ResultSet rsUnit =
					null;
			Connection connUnit=null;
			try
			{
				connUnit = ConnDB.getConnection();
				psUnit = connUnit.prepareStatement(sql);
				rsUnit = psUnit.executeQuery();
				if (rsUnit.next())
				{
					ParentDeptID = rsUnit.getString("Partment_ID");
					ParentDeptName = rsUnit.getString("Partment_Name");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(connUnit, psUnit, rsUnit);
			}

			// 部门全称取得
			PrefixFullName = ParentDeptName + '．' + PrefixFullName;
			// 单位名称取得
			unitName = ParentDeptName;
		}

		if (!"0".equals(LastSerialNo))
		{
			sql = "select Year,StartYMD,EndYMD from DeptFinalAccounts where SerialNo = "
					+ LastSerialNo;
			PreparedStatement psLast =
					null;
			ResultSet rsLast =
					null;
			Connection connLast=null;
			try
			{
				connLast = ConnDB.getConnection();
				psLast = connLast.prepareStatement(sql);
				rsLast = psLast.executeQuery();
				if (rsLast.next())
				{
					LastYear = rsLast.getString("Year");
					LastStartYMD = rsLast.getString("StartYMD");
					LastEndYMD = rsLast.getString("EndYMD");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(connLast, psLast, rsLast);
			}
		}

		// 结算期间
		JSDate = StartYMD.substring(0, 4) + "年" + StartYMD.substring(4, 6)
				+ "月" + StartYMD.substring(6, 8) + " 至 "
				+ EndYMD.substring(0, 4) + "年" + EndYMD.substring(4, 6) + "月"
				+ EndYMD.substring(6, 8);
		// 制表单位
		reportUnit = PrefixFullName + name;

		sql = "SELECT Partment_ID FROM Partment START WITH Partment_ID = "
				+ DeptID + " CONNECT BY PRIOR Partment_ID = Partment_Parent";

		PreparedStatement psQuery =
				null;
		ResultSet rsQuery =
				null;
		Connection connQuery=null;
		try
		{
			connQuery = ConnDB.getConnection();
			psQuery = connQuery.prepareStatement(sql);
			rsQuery = psQuery.executeQuery();
			while (rsQuery.next())
			{
				String varDeptID = rsQuery.getString("Partment_ID");
				fpm = new FixedPrintModel();

				fpm.setUnitName(unitName);// 结算单位
				fpm.setReportUnit(reportUnit);// 制表单位
				fpm.setPeriod(JSDate);// 结算期间

				String Partment_Name = "";
				sql = "select Partment_Name from Partment where Partment_ID = "
						+ varDeptID;
				PreparedStatement psPName =
						null;
				ResultSet rsPName =
						null;
				Connection connName=null;
				try
				{
					connName = ConnDB.getConnection();
					psPName = connName.prepareStatement(sql);
					rsPName = psPName.executeQuery();
					if (rsPName.next())
					{
						Partment_Name = rsPName.getString("Partment_Name");
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(connName, psPName, rsPName);
				}

				if (DeptID.equals(varDeptID))// 设置结算名称
				{
					fpm.setOrganizeName("部门：" + PrefixFullName + Partment_Name);
				} else
				{
					fpm.setOrganizeName("部门:" + PrefixFullName + name + '．'
							+ Partment_Name);
				}
				fpm.setTotalQuota(String.valueOf(pfd.getQuotaInfoByYearDeptID(
						varDeptID, Year)));// 年度计划

				sql = "select s0.ZMoney,s0.ZGross "
						+ "from (select s1.partment_id,nvl(sum(s1.ZMoney),0) AS  ZMoney,nvl(sum(s1.ZGross),0) AS ZGross from T_PartmentDayAm s1 where s1.SelectYear = "
						+ Year + " group by s1.partment_id)s0 "
						+ "where s0.Partment_ID = " + varDeptID;
				PreparedStatement psZ =
						null;
				ResultSet rsZ =
						null;
				Connection connZ=null;
				try
				{
					connZ = ConnDB.getConnection();
					psZ = connZ.prepareStatement(sql);
					rsZ = psZ.executeQuery();
					if (rsZ.next())
					{
						fpm.setZMoney(rsZ.getString("ZMoney"));// 年度金额
						fpm.setZPower(rsZ.getString("ZGross"));// 年度电量
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(connZ, psZ, rsZ);
				}

				if (!"0".equals(LastSerialNo))
				{
					sql = "select s0.ZGross from (select s1.partment_id,nvl(sum(s1.ZMoney),0) AS  ZMoney,nvl(sum(s1.ZGross),0) AS ZGross from T_PartmentDayAm s1 where s1.SelectYear*10000 + s1.SelectMonth*100 + s1.SelectDay >= "
							+ LastStartYMD
							+ " And s1.SelectYear*10000 + s1.SelectMonth*100 + s1.SelectDay <= "
							+ LastEndYMD
							+ " group by s1.partment_id )s0 where s0.Partment_ID = "
							+ varDeptID;
					PreparedStatement psL =
							null;
					ResultSet rsL =
							null;
					Connection connL=null;
					try
					{
						connL = ConnDB.getConnection();
						psL = connL.prepareStatement(sql);
						rsL = psL.executeQuery();
						if (rsL.next())
						{
							fpm.setLastPower(rsL.getString("ZGross"));// 上期电量
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(connL, psL, rsL);
					}
				} else
				{
					fpm.setLastPower("0");// 上期电量
				}

				sql = "select s0.ZMoney,s0.ZGross from (select s1.partment_id,nvl(sum(s1.ZMoney),0) AS  ZMoney,nvl(sum(s1.ZGross),0) AS ZGross from T_PartmentDayAm s1 where s1.SelectYear*10000 + s1.SelectMonth*100 + s1.SelectDay >= "
						+ StartYMD
						+ " And s1.SelectYear*10000 + s1.SelectMonth*100 + s1.SelectDay <= "
						+ EndYMD
						+ " group by s1.partment_id )s0 where s0.Partment_ID = "
						+ varDeptID;
				PreparedStatement psT =
						null;
				ResultSet rsT =
						null;
				Connection connT=null;
				try
				{
					connT = ConnDB.getConnection();
					psT = connT.prepareStatement(sql);
					rsT = psT.executeQuery();
					if (rsT.next())
					{
						fpm.setThisMoney(rsT.getString("ZMoney"));// 本期金额
						fpm.setThisPower(rsT.getString("ZGross"));// 本期电量
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(connT, psT, rsT);
				}

				/*********************************************************************************************/
				Map<String, Float> fenlei = new HashMap<String, Float>();
				String fenleiStr = "";
				Map<String, Float> xingzhi = new HashMap<String, Float>();
				String xingzhiStr = "";
				fenlei = erhi.getGroupFenLeiCountBetween(
						Integer.parseInt(varDeptID), df.parse(StartYMD),
						df.parse(EndYMD));

				for (Entry<String, Float> entry : fenlei.entrySet())
				{

					String key = entry.getKey();

					Float value = entry.getValue();

					fenleiStr += key + ": " + value + "<br>";
				}

				xingzhi = erhi.getGroupStyleCountBetween(
						Integer.parseInt(varDeptID), df.parse(StartYMD),
						df.parse(EndYMD));

				for (Entry<String, Float> entry : xingzhi.entrySet())
				{

					String key = entry.getKey();

					Float value = entry.getValue();

					xingzhiStr += key + ": " + value + "<br>";
				}

				fpm.setFenxiangDetil(fenleiStr);
				fpm.setXingzhiDetil(xingzhiStr);
				/*********************************************************************************************/

				if (Double.valueOf(fpm.getZPower()) > 0)
				{
					double price = 0;
					price = (double) Math.round(Double.valueOf(fpm.getZMoney())
							/ Double.valueOf(fpm.getZPower()) * 100) / 100;
					fpm.setPrice(String.valueOf(price));
				} else
				{
					fpm.setPrice("-");
				}

				double remainQuota = 0;
				remainQuota = (double) Math.round((Double.valueOf(fpm
						.getTotalQuota()) - Double.valueOf(fpm.getZMoney())) * 100) / 100;
				fpm.setRemainQuota(String.valueOf(remainQuota));

				list.add(fpm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(connQuery, psQuery, rsQuery);
		}

		return list;
			}

}
