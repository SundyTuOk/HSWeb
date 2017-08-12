package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.sf.energy.project.system.model.EnergyQuotaManagerModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.model.WaterQuotaManagerModel;

public class WaterArchitectureFixedDao
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

	/**
	 * 获取建筑用水定额全部数据
	 * @param strYear 年份
	 * @param userID 用户ID
	 * @param pageCount 分页大小
	 * @param pageIndex 当前页码
	 * @return EnergyQuotaManagerModel对象列表
	 * @throws SQLException
	 */
	public ArrayList<WaterQuotaManagerModel> getAllInfo(String strYear,
			String userID, int pageCount, int pageIndex) throws SQLException
	{
		WaterQuotaManagerModel wqmm = null;
		ArrayList<WaterQuotaManagerModel> list = new ArrayList<WaterQuotaManagerModel>();

		String sql = "select g.name as name, g.BldOrArea_ID,nvl(f.totalQuota,0) as totalQuota,nvl(f.standardQuota,0) as standardQuota ,nvl(f.TiaozhengFixed,0) as TiaozhengFixed,nvl(f.addPerMonthQuota,0) as addPerMonthQuota,nvl(f.ZMoney,0) as ZMoney,nvl(f.remainQuota,0) as remainQuota from ";
		sql += "( ";
		sql += "	select ";
		sql += "        a.Area_ID as BldOrArea_ID, ";
		sql += "        '0' as AOrBflg, ";
		sql += "        a.Area_Name as name ";
		sql += "	from  ";
		sql += "	    Area a, ";
		sql += "	    OprArea_List b ";
		sql += "	where ";
		sql += "	    b.Users_ID = " + userID + " And ";
		sql += "	    a.Area_ID = b.Area_ID ";
		sql += "	union all ";
		sql += "	select ";
		sql += "	    a.Architecture_ID  as BldOrArea_ID, ";
		sql += "        '1' as AOrBflg, ";
		sql += "	    a.Architecture_Name as name ";
		sql += "	from   ";
		sql += "	    Architecture a,  ";
		sql += "	    OprArc_List b  ";
		sql += "	where  ";
		sql += "	    b.Users_ID = " + userID + " And  ";
		sql += "	    a.Architecture_ID = b.Architecture_ID  ";
		sql += ") g  ";
		sql += " left join  ";
		sql += "(   ";
		sql += "    select  ";
		sql += "        d.BldOrArea_ID, ";
		sql += "        d.AOrBflg, ";
		sql += "        d.standardQuota + d.TiaozhengFixed + d.addPerMonthQuota  as totalQuota,  ";
		sql += "        d.standardQuota,  ";
		sql += "        d.TiaozhengFixed,  ";
		sql += "        d.addPerMonthQuota,  ";
		sql += "        e.ZMoney,  ";
		sql += "        d.standardQuota + d.TiaozhengFixed + d.addPerMonthQuota - e.ZMoney as remainQuota  ";
		sql += "    from   ";
		sql += "        (  ";
		sql += "            select  ";
		sql += "                c.BldOrArea_ID, ";
		sql += "                c.AOrBflg, ";
		sql += "                c.TiaozhengFixed,  ";
		sql += "                c.FixedZhuijia1 + FixedZhuijia2 + FixedZhuijia3 + FixedZhuijia4 + c.FixedZhuijia5 + FixedZhuijia6 + FixedZhuijia7 + FixedZhuijia8 + c.FixedZhuijia9 + FixedZhuijia10 + FixedZhuijia11 + FixedZhuijia12 as addPerMonthQuota,  ";
		sql += "                b.standardQuota  ";
		sql += "            from   ";
		sql += "                (  ";
		sql += "                    select   ";
		sql += "                        a.BldOrArea_ID, ";
		sql += "                        a.AOrBflg, ";
		sql += "                        nvl(sum(a.sum1),0) as standardQuota  ";
		sql += "                    from   ";
		sql += "                        (  ";
		sql += "                            select  ";
		sql += "                                BldOrArea_ID, ";
		sql += "                                AOrBflg,  ";
		sql += "                                nvl(sum(  ";
		sql += "                                    ( case M1 when 1 then 1 else 0 end +  ";
		sql += "                                      case M2 when 1 then 1 else 0 end +  ";
		sql += "                                      case M3 when 1 then 1 else 0 end +  ";
		sql += "                                      case M4 when 1 then 1 else 0 end +   ";
		sql += "                                      case M5 when 1 then 1 else 0 end +  ";
		sql += "                                      case M6 when 1 then 1 else 0 end +  ";
		sql += "                                      case M7 when 1 then 1 else 0 end +  ";
		sql += "                                      case M8 when 1 then 1 else 0 end +  ";
		sql += "                                      case M9 when 1 then 1 else 0 end +   ";
		sql += "                                      case M10 when 1 then 1 else 0 end +  ";
		sql += "                                      case M11 when 1 then 1 else 0 end +  ";
		sql += "                                      case M12 when 1 then 1 else 0 end  ";
		sql += "                                    ) * TotalDevice * Power * RunHoursPerMth * Price ),0) as sum1 ";
		sql += "                            from  ";
		sql += "                                BldQutaDetlByDevWater  ";
		sql += "                            where  ";
		sql += "                                Year = '" + strYear + "'";
		sql += "                            Group by  ";
		sql += "                                BldOrArea_ID,AOrBflg ";
		sql += "                            union all  ";
		sql += "                            select  ";
		sql += "                                BldOrArea_ID, ";
		sql += "                                AOrBflg,  ";
		sql += "                                nvl(sum(  ";
		sql += "                                    ( case M1 when 1 then 1 else 0 end +  ";
		sql += "                                      case M2 when 1 then 1 else 0 end +  ";
		sql += "                                      case M3 when 1 then 1 else 0 end +   ";
		sql += "                                      case M4 when 1 then 1 else 0 end +  ";
		sql += "                                      case M5 when 1 then 1 else 0 end +  ";
		sql += "                                      case M6 when 1 then 1 else 0 end +   ";
		sql += "                                      case M7 when 1 then 1 else 0 end +  ";
		sql += "                                      case M8 when 1 then 1 else 0 end +   ";
		sql += "                                      case M9 when 1 then 1 else 0 end +  ";
		sql += "                                      case M10 when 1 then 1 else 0 end +  ";
		sql += "                                      case M11 when 1 then 1 else 0 end +  ";
		sql += "                                      case M12 when 1 then 1 else 0 end  ";
		sql += "                                      ) * TotalPeople * EStandard * Price ),0)  as sum1   ";
		sql += "                            from  ";
		sql += "                                BldQutaDetlByPeoWater ";
		sql += "                            where  ";
		sql += "                               Year = '" + strYear + "'";
		sql += "                            Group by  ";
		sql += "                                BldOrArea_ID,AOrBflg ";
		sql += "                        ) a  ";
		sql += "                    group by  ";
		sql += "                        a.BldOrArea_ID,a.AOrBflg  ";
		sql += "                ) b,  ";
		sql += "                BldQuotaWater c  ";
		sql += "            where  ";
		sql += "                c.BldOrArea_ID = b.BldOrArea_ID  And  ";
		sql += "                c.AOrBflg = b.AOrBflg  And  ";
		sql += "                c.Year = '" + strYear + "'";
		sql += "            ) d left join   ";
		sql += "            (  ";
		sql += "                select  ";
		sql += "                    Architecture_ID as BldOrArea_ID, ";
		sql += "                    '1' as AOrBflg,  ";
		sql += "                    nvl(sum(ZMoney),0) AS ZMoney  ";
		sql += "                from  ";
		sql += "                    T_ArcDayWater ";
		sql += "                where  ";
		sql += "                    SelectYear = '" + strYear + "'";
		sql += "                group by Architecture_ID ";
		sql += "                union all ";
		sql += "                select  ";
		sql += "                    Area_ID as BldOrArea_ID, ";
		sql += "                    '0' as AOrBflg,  ";
		sql += "                    nvl(sum(ZMoney),0) AS ZMoney  ";
		sql += "                from  ";
		sql += "                    T_ArcDayWater ";
		sql += "                where  ";
		sql += "                    SelectYear = '" + strYear + "'";
		sql += "                group by Area_ID ";
		sql += "            ) e  ";
		sql += "            on e.BldOrArea_ID = d.BldOrArea_ID  And ";
		sql += "            e.AOrBflg = d.AOrBflg   ";
		sql += ") f  ";
		sql += "on f.BldOrArea_ID = g.BldOrArea_ID  And ";
		sql += "   f.AOrBflg = g.AOrBflg  order by g.AOrBflg,g.BldOrArea_ID ";
		
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
				ConnDB.release(conn, ps, rs);
				return list;
			}

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.beforeFirst();
			} else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				wqmm = new WaterQuotaManagerModel();

				wqmm.setYear(strYear);
				wqmm.setName(rs.getString("name"));
				wqmm.setTotalQuota(rs.getString("totalQuota"));
				wqmm.setStandardQuota(rs.getString("standardQuota"));
				wqmm.setTiaozhengFixed(rs.getString("TiaozhengFixed"));
				wqmm.setAddPerMonthQuota(rs.getString("addPerMonthQuota"));
				wqmm.setZMoney(rs.getString("ZMoney"));
				wqmm.setRemainQuota(rs.getString("remainQuota"));

				list.add(wqmm);
				count--;
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery();

		rs.last();
		int count = rs.getRow();
		setTotalCount(count);
		if (count <= (pageCount * pageIndex))
		{
			if (rs != null)
			{
				rs.close();
			}
			if (ps != null)
			{
				ps.close();
			}
			return list;
		}

		count = count - pageCount * pageIndex;

		if (count >= pageCount)
			count = pageCount;

		if ((pageCount * pageIndex) == 0)
		{
			rs.beforeFirst();
		} else
			rs.absolute(pageCount * pageIndex);

		while (rs.next() && (count > 0))
		{
			wqmm = new WaterQuotaManagerModel();

			wqmm.setYear(strYear);
			wqmm.setName(rs.getString("name"));
			wqmm.setTotalQuota(rs.getString("totalQuota"));
			wqmm.setStandardQuota(rs.getString("standardQuota"));
			wqmm.setTiaozhengFixed(rs.getString("TiaozhengFixed"));
			wqmm.setAddPerMonthQuota(rs.getString("addPerMonthQuota"));
			wqmm.setZMoney(rs.getString("ZMoney"));
			wqmm.setRemainQuota(rs.getString("remainQuota"));

			list.add(wqmm);
			count--;
		}

		if (rs != null)
		{
			rs.close();
		}
		if (ps != null)
		{
			ps.close();
		}*/
		return list;

	}

	/**
	 * 获取建筑人员类别标准分配定额详细信息
	 * @param strYear 年份
	 * @param BldOrArea_ID 建筑或区域ID
 	 * @param AOrBFlg 建筑或区域标识
	 * @return EnergyQuotaManagerModel对象列表
	 * @throws SQLException
	 */
	public ArrayList<WaterQuotaManagerModel> getWaterPeoInfoByPartment_ID(
			String strYear, String BldOrArea_ID, String AOrBFlg)
			throws SQLException
	{
		WaterQuotaManagerModel wqmm = null;
		ArrayList<WaterQuotaManagerModel> list = new ArrayList<WaterQuotaManagerModel>();

		String sql = "select ValueId,BldOrArea_ID,Year,Name,Price,"
				+ "M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TotalPeople,EStandard,Remark "
				+ "from BldQutaDetlByPeoWater where BldOrArea_ID=";
		sql += BldOrArea_ID + " AND Year = '";
		sql += strYear + "' And AOrBflg = ";
		// 区域Or建筑ID
		if ("Area".equals(AOrBFlg))
		{
			sql += "0";
		} else
		{
			sql += "1";
		}
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next())
			{
				double fResult = 0;
				int iResult = 0;
				String biaozhun = "";

				wqmm = new WaterQuotaManagerModel();
				wqmm.setValueID(rs.getInt("ValueId"));
				wqmm.setYear(strYear);
				wqmm.setUserAmStyle(rs.getString("Name"));

				biaozhun = "人员总数×用水标准×适用月数：" + rs.getString("TotalPeople") + "人×"
						+ rs.getString("EStandard") + "吨/人月×";
				if ("1".equals(rs.getString("M1")))
					iResult++;
				if ("1".equals(rs.getString("M2")))
					iResult++;
				if ("1".equals(rs.getString("M3")))
					iResult++;
				if ("1".equals(rs.getString("M4")))
					iResult++;
				if ("1".equals(rs.getString("M5")))
					iResult++;
				if ("1".equals(rs.getString("M6")))
					iResult++;
				if ("1".equals(rs.getString("M7")))
					iResult++;
				if ("1".equals(rs.getString("M8")))
					iResult++;
				if ("1".equals(rs.getString("M9")))
					iResult++;
				if ("1".equals(rs.getString("M10")))
					iResult++;
				if ("1".equals(rs.getString("M11")))
					iResult++;
				if ("1".equals(rs.getString("M12")))
					iResult++;
				biaozhun += iResult + "个月";
				wqmm.setBiaozhun(biaozhun);

				fResult = rs.getInt("TotalPeople") * rs.getDouble("EStandard")
						* iResult;
				wqmm.setCountFixedGross(String.valueOf(fResult));
				wqmm.setPrice(rs.getString("Price"));
				wqmm.setCountFixedMoney(String.valueOf((double)Math.round(fResult * rs.getDouble("Price") * 100) / 100));
				wqmm.setRemark(rs.getString("Remark"));

				list.add(wqmm);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			double fResult = 0;
			int iResult = 0;
			String biaozhun = "";

			wqmm = new WaterQuotaManagerModel();
			wqmm.setValueID(rs.getInt("ValueId"));
			wqmm.setYear(strYear);
			wqmm.setUserAmStyle(rs.getString("Name"));

			biaozhun = "人员总数×用水标准×适用月数：" + rs.getString("TotalPeople") + "人×"
					+ rs.getString("EStandard") + "吨/人月×";
			if ("1".equals(rs.getString("M1")))
				iResult++;
			if ("1".equals(rs.getString("M2")))
				iResult++;
			if ("1".equals(rs.getString("M3")))
				iResult++;
			if ("1".equals(rs.getString("M4")))
				iResult++;
			if ("1".equals(rs.getString("M5")))
				iResult++;
			if ("1".equals(rs.getString("M6")))
				iResult++;
			if ("1".equals(rs.getString("M7")))
				iResult++;
			if ("1".equals(rs.getString("M8")))
				iResult++;
			if ("1".equals(rs.getString("M9")))
				iResult++;
			if ("1".equals(rs.getString("M10")))
				iResult++;
			if ("1".equals(rs.getString("M11")))
				iResult++;
			if ("1".equals(rs.getString("M12")))
				iResult++;
			biaozhun += iResult + "个月";
			wqmm.setBiaozhun(biaozhun);

			fResult = rs.getInt("TotalPeople") * rs.getDouble("EStandard")
					* iResult;
			wqmm.setCountFixedGross(String.valueOf(fResult));
			wqmm.setPrice(rs.getString("Price"));
			wqmm.setCountFixedMoney(String.valueOf((double)Math.round(fResult * rs.getDouble("Price") * 100) / 100));
			wqmm.setRemark(rs.getString("Remark"));

			list.add(wqmm);
		}

		if (rs != null)
		{
			rs.close();
		}
		if (ps != null)
		{
			ps.close();
		}*/
		return list;

	}

	/**
	 * 获取建筑设备类别标准分配定额详细信息
	 * @param strYear 年份
	 * @param BldOrArea_ID 建筑或区域ID
	 * @param AOrBFlg 区域或建筑标识
	 * @return EnergyQuotaManagerModel对象列表
	 * @throws SQLException
	 */
	public ArrayList<WaterQuotaManagerModel> getWaterDevInfoByPartment_ID(
			String strYear, String BldOrArea_ID, String AOrBFlg)
			throws SQLException
	{
		WaterQuotaManagerModel wqmm = null;
		ArrayList<WaterQuotaManagerModel> list = new ArrayList<WaterQuotaManagerModel>();

		String sql = "select ValueId,BldOrArea_ID,Year,Name,Price,M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TotalDevice,Power,RunHoursPerMth,Remark from BldQutaDetlByDevWater where BldOrArea_ID=";
		sql += BldOrArea_ID + " AND Year = '";
		sql += strYear + "' And AOrBflg = ";
		// 区域Or建筑ID
		if ("Area".equals(AOrBFlg))
		{
			sql += "0";
		} else
		{
			sql += "1";
		}
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next())
			{
				double fResult = 0;
				int iResult = 0;
				String biaozhun = "";

				wqmm = new WaterQuotaManagerModel();
				wqmm.setValueID(rs.getInt("ValueId"));
				wqmm.setYear(strYear);
				wqmm.setUserAmStyle(rs.getString("Name"));

				biaozhun = "设备总数×功率×运行时间×适用月数：" + rs.getString("TotalDevice")
						+ "台×" + rs.getString("Power") + "吨/小时×"
						+ rs.getString("RunHoursPerMth") + "时/台月×";
				if ("1".equals(rs.getString("M1")))
					iResult++;
				if ("1".equals(rs.getString("M2")))
					iResult++;
				if ("1".equals(rs.getString("M3")))
					iResult++;
				if ("1".equals(rs.getString("M4")))
					iResult++;
				if ("1".equals(rs.getString("M5")))
					iResult++;
				if ("1".equals(rs.getString("M6")))
					iResult++;
				if ("1".equals(rs.getString("M7")))
					iResult++;
				if ("1".equals(rs.getString("M8")))
					iResult++;
				if ("1".equals(rs.getString("M9")))
					iResult++;
				if ("1".equals(rs.getString("M10")))
					iResult++;
				if ("1".equals(rs.getString("M11")))
					iResult++;
				if ("1".equals(rs.getString("M12")))
					iResult++;
				biaozhun += iResult + "个月";
				wqmm.setBiaozhun(biaozhun);

				fResult = rs.getInt("TotalDevice") * rs.getDouble("Power")
						* rs.getDouble("RunHoursPerMth") * iResult;
				wqmm.setCountFixedGross(String.valueOf(fResult));
				wqmm.setPrice(rs.getString("Price"));
				wqmm.setCountFixedMoney(String.valueOf((double)Math.round(fResult * rs.getDouble("Price") * 100) / 100));
				wqmm.setRemark(rs.getString("Remark"));

				list.add(wqmm);
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			double fResult = 0;
			int iResult = 0;
			String biaozhun = "";

			wqmm = new WaterQuotaManagerModel();
			wqmm.setValueID(rs.getInt("ValueId"));
			wqmm.setYear(strYear);
			wqmm.setUserAmStyle(rs.getString("Name"));

			biaozhun = "设备总数×功率×运行时间×适用月数：" + rs.getString("TotalDevice")
					+ "台×" + rs.getString("Power") + "吨/小时×"
					+ rs.getString("RunHoursPerMth") + "时/台月×";
			if ("1".equals(rs.getString("M1")))
				iResult++;
			if ("1".equals(rs.getString("M2")))
				iResult++;
			if ("1".equals(rs.getString("M3")))
				iResult++;
			if ("1".equals(rs.getString("M4")))
				iResult++;
			if ("1".equals(rs.getString("M5")))
				iResult++;
			if ("1".equals(rs.getString("M6")))
				iResult++;
			if ("1".equals(rs.getString("M7")))
				iResult++;
			if ("1".equals(rs.getString("M8")))
				iResult++;
			if ("1".equals(rs.getString("M9")))
				iResult++;
			if ("1".equals(rs.getString("M10")))
				iResult++;
			if ("1".equals(rs.getString("M11")))
				iResult++;
			if ("1".equals(rs.getString("M12")))
				iResult++;
			biaozhun += iResult + "个月";
			wqmm.setBiaozhun(biaozhun);

			fResult = rs.getInt("TotalDevice") * rs.getDouble("Power")
					* rs.getDouble("RunHoursPerMth") * iResult;
			wqmm.setCountFixedGross(String.valueOf(fResult));
			wqmm.setPrice(rs.getString("Price"));
			wqmm.setCountFixedMoney(String.valueOf((double)Math.round(fResult * rs.getDouble("Price") * 100) / 100));
			wqmm.setRemark(rs.getString("Remark"));

			list.add(wqmm);
		}

		if (rs != null)
		{
			rs.close();
		}
		if (ps != null)
		{
			ps.close();
		}*/
		return list;
	}

	/**
	 * 获取选中对象年的标准分配定额
	 * @param strYear 年份	 
	 * @param BldOrArea_ID 建筑或区域ID
	 * @param AOrBFlg 建筑或区域标志
	 * @return 选中对象年的标准分配定额
	 * @throws SQLException 
	 */
	public double getTotalStandardQuota(String strYear, String BldOrArea_ID,
			String AOrBFlg) throws SQLException
	{
		// /选中对象年的标准分配定额
		double totalStandardQuota = 0;

		String sql = "select ValueId,BldOrArea_ID,Year,Name,Price,"
				+ "M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TotalPeople,EStandard,Remark "
				+ "from BldQutaDetlByPeoWater where BldOrArea_ID=";
		sql += BldOrArea_ID + " AND Year = '";
		sql += strYear + "' And AOrBflg = ";
		// 区域Or建筑ID
		if ("Area".equals(AOrBFlg))
		{
			sql += "0";
		} else
		{
			sql += "1";
		}

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next())
			{
				double fResult = 0;
				int iResult = 0;

				if ("1".equals(rs.getString("M1")))
					iResult++;
				if ("1".equals(rs.getString("M2")))
					iResult++;
				if ("1".equals(rs.getString("M3")))
					iResult++;
				if ("1".equals(rs.getString("M4")))
					iResult++;
				if ("1".equals(rs.getString("M5")))
					iResult++;
				if ("1".equals(rs.getString("M6")))
					iResult++;
				if ("1".equals(rs.getString("M7")))
					iResult++;
				if ("1".equals(rs.getString("M8")))
					iResult++;
				if ("1".equals(rs.getString("M9")))
					iResult++;
				if ("1".equals(rs.getString("M10")))
					iResult++;
				if ("1".equals(rs.getString("M11")))
					iResult++;
				if ("1".equals(rs.getString("M12")))
					iResult++;

				fResult = rs.getInt("TotalPeople") * rs.getDouble("EStandard")
						* iResult;
				fResult = fResult * rs.getDouble("Price");
				totalStandardQuota += fResult;
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		sql = "select ValueId,BldOrArea_ID,Year,Name,Price,M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TotalDevice,Power,RunHoursPerMth,Remark from BldQutaDetlByDevWater where BldOrArea_ID=";
		sql += BldOrArea_ID + " AND Year = '";
		sql += strYear + "' And AOrBflg = ";
		// 区域Or建筑ID
		if ("Area".equals(AOrBFlg))
		{
			sql += "0";
		} else
		{
			sql += "1";
		}
		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs1 = ps1.executeQuery();
			while (rs1.next())
			{
				double fResult = 0;
				int iResult = 0;
				if ("1".equals(rs1.getString("M1")))
					iResult++;
				if ("1".equals(rs1.getString("M2")))
					iResult++;
				if ("1".equals(rs1.getString("M3")))
					iResult++;
				if ("1".equals(rs1.getString("M4")))
					iResult++;
				if ("1".equals(rs1.getString("M5")))
					iResult++;
				if ("1".equals(rs1.getString("M6")))
					iResult++;
				if ("1".equals(rs1.getString("M7")))
					iResult++;
				if ("1".equals(rs1.getString("M8")))
					iResult++;
				if ("1".equals(rs1.getString("M9")))
					iResult++;
				if ("1".equals(rs1.getString("M10")))
					iResult++;
				if ("1".equals(rs1.getString("M11")))
					iResult++;
				if ("1".equals(rs1.getString("M12")))
					iResult++;

				fResult = rs1.getInt("TotalDevice") * rs1.getDouble("Power")
						* rs1.getDouble("RunHoursPerMth") * iResult;
				fResult = fResult * rs1.getDouble("Price");

				totalStandardQuota += fResult;
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}
		
		totalStandardQuota = (double)Math.round(totalStandardQuota * 100) / 100;
		return totalStandardQuota;
	}

	/**
	 * 根据给定的ID和年求出定额使用明细fusioncharts字符串
	 * @param strYear 年份	
	 * @param BldOrArea_ID 建筑或区域ID
	 * @param AOrBFlg 建筑或区域标识
	 * @return strChart
	 * @throws SQLException
	 */
	public String getWaterQuotaUseInfo(String strYear, String BldOrArea_ID,
			String AOrBFlg) throws SQLException
	{
		// 加载定额使用明细页面的数据
		int i;
		// / 选中对象年的已经使用定额
		double[] fM1 = new double[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		// / 选中对象年的前一年的已经使用定额
		double[] fM2 = new double[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		// / 选中对象年的标准分配定额
		double totalStandardQuota = 0;
		// / 选中对象年的调整定额
		double tiaozhengFixed = 0;
		// / 选中对象年的每月追加总定额
		double[] fM3 = new double[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		totalStandardQuota = wafd.getTotalStandardQuota(strYear, BldOrArea_ID,
				AOrBFlg);

		String strChart = "";
		strChart = "<graph caption='";

		// / 获取大学名称
		String sql = "select SystemInfo_ComplayName from SystemInfo where rownum = 1";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next())
			{
				strChart += rs.getString("SystemInfo_ComplayName");
			} else
			{
				strChart += " ";
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		sql = "select BldOrArea_ID,Year,TiaozhengFixed,"
				+ "FixedZhuijia1,FixedZhuijia2,FixedZhuijia3,FixedZhuijia4,"
				+ "FixedZhuijia5,FixedZhuijia6,FixedZhuijia7,FixedZhuijia8,"
				+ "FixedZhuijia9,FixedZhuijia10,FixedZhuijia11,FixedZhuijia12  "
				+ "from BldQuotaWater where BldOrArea_ID=";
		sql += BldOrArea_ID + " AND Year = '";
		sql += strYear + "' And AOrBflg = ";
		// 区域Or建筑标识
		if ("Area".equals(AOrBFlg))
		{
			sql += "0";
		} else
		{
			sql += "1";
		}
		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs1 = ps1.executeQuery();
			if (rs1.next())
			{
				tiaozhengFixed = rs1.getDouble("TiaozhengFixed");
				fM3[0] = rs1.getDouble("FixedZhuijia1");
				fM3[1] = rs1.getDouble("FixedZhuijia2");
				fM3[2] = rs1.getDouble("FixedZhuijia3");
				fM3[3] = rs1.getDouble("FixedZhuijia4");
				fM3[4] = rs1.getDouble("FixedZhuijia5");
				fM3[5] = rs1.getDouble("FixedZhuijia6");
				fM3[6] = rs1.getDouble("FixedZhuijia7");
				fM3[7] = rs1.getDouble("FixedZhuijia8");
				fM3[8] = rs1.getDouble("FixedZhuijia9");
				fM3[9] = rs1.getDouble("FixedZhuijia10");
				fM3[10] = rs1.getDouble("FixedZhuijia11");
				fM3[11] = rs1.getDouble("FixedZhuijia12");
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}
		
		
		// 选中对象年的已经使用定额(选中区域建筑的)
		fM1 = wafd.GetCostPerMByYearBldID(BldOrArea_ID, AOrBFlg, strYear);
		// 前一年的已经使用定额(选中区域建筑的)
		fM2 = wafd.GetCostPerMByYearBldID(BldOrArea_ID, AOrBFlg,
				String.valueOf(Integer.parseInt(strYear) - 1));

		strChart += "定额使用明细' baseFont='宋体' baseFontSize='12'  yAxisName='水费（元）' showValues='0' decimalPrecision='2' formatNumberScale='0'>";
		strChart += "<categories>";
		strChart += "<category name='1月' /> ";
		strChart += "<category name='2月' /> ";
		strChart += "<category name='3月' /> ";
		strChart += "<category name='4月' /> ";
		strChart += "<category name='5月' /> ";
		strChart += "<category name='6月' /> ";
		strChart += "<category name='7月' /> ";
		strChart += "<category name='8月' /> ";
		strChart += "<category name='9月' /> ";
		strChart += "<category name='10月' /> ";
		strChart += "<category name='11月' /> ";
		strChart += "<category name='12月' /> ";
		strChart += "</categories>";

		strChart += "<dataset seriesName='";
		strChart += strYear + "年剩余定额' color='3399CC'>";

		// 年剩余定额 = 标准分配定额 + 调整给定额
		double RemaindQuota = totalStandardQuota + tiaozhengFixed;
		for (i = 0; i < 12; i++)
		{
			// + 该当月的追加额 - 该当月的已经使用定额
			RemaindQuota += fM3[i] - fM1[i];
			strChart += "<set value='" + RemaindQuota + "'/>";
		}
		strChart += "</dataset>";

		strChart += "<dataset seriesName='";
		strChart += strYear + "年已经使用定额' color='FF6633'>";
		double usedQuota = 0;
		for (i = 0; i < 12; i++)
		{
			usedQuota += fM1[i];
			strChart += "<set value='" + usedQuota + "'/>";
		}
		strChart += "</dataset>";

		strChart += "<dataset seriesName='";
		strChart += (Integer.parseInt(strYear) - 1) + "年费用' color='33CC33'>";
		usedQuota = 0;
		for (i = 0; i < 12; i++)
		{
			usedQuota += fM2[i];
			strChart += "<set value='" + usedQuota + "'/>";
		}
		strChart += "</dataset>";
		strChart += "</graph>";

		/*if(rs != null)
			rs.close();
		if(sm != null)
			sm.close();*/
		return strChart;
	}

	/**
	 * 根据给定的ID和年求出12个月的已用水定额
	 * @param BldOrArea_ID 建筑或区域ID
	 * @param AOrBFlg 建筑或区域标识
	 * @param strYear 年份
	 * @return 12个月的已用水定额数组
	 * @throws SQLException
	 */
	public double[] GetCostPerMByYearBldID(String BldOrArea_ID, String AOrBFlg,
			String strYear) throws SQLException
	{
		int i = 0;
		double[] fM = new double[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		String sql = "";
		if ("Area".equals(AOrBFlg))
		{
			sql = "select SelectMonth, nvl(sum(ZMoney),0) as ZMoney From T_ArcDayWater Where Area_ID ="
					+ BldOrArea_ID;
		} else
		{
			sql = "select SelectMonth, nvl(sum(ZMoney),0) as ZMoney From T_ArcDayWater Where Architecture_ID ="
					+ BldOrArea_ID;
		}
		sql += " And SelectYear =" + strYear;
		sql += "  Group by SelectMonth ";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next())
			{
				fM[rs.getInt("SelectMonth") - 1] = rs.getDouble("ZMoney");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*Statement sm = null;
		sm = ConnDB.getConnection().createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(sql);

		while (rs.next())
		{
			fM[rs.getInt("SelectMonth") - 1] = rs.getDouble("ZMoney");
		}
		
		if(rs != null)
			rs.close();
		if(sm != null)
			sm.close();*/
		return fM;
	}

	/**
	 * 根据给定的ID和年以及查询方式求出能耗结构划分fusioncharts字符串
	 * @param strYear 年份
	 * @param strStyle 查询方式
	 * @param BldOrArea_ID 建筑或区域ID
	 * @param AOrBFlg 建筑区域标识
	 * @return fusioncharts字符串
	 * @throws SQLException
	 */
	public String getWaterStructureInfo(String strYear, String strStyle,
			String BldOrArea_ID, String AOrBFlg) throws SQLException
	{
		String strChart = "";
		strChart = "<graph caption='";

		// / 获取大学名称
		String sql = "select SystemInfo_ComplayName from SystemInfo where rownum = 1";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next())
			{
				strChart += rs.getString("SystemInfo_ComplayName");
			} else
			{
				strChart += " ";
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		/*Statement sm = null;
		sm = ConnDB.getConnection().createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(sql);

		if (rs.next())
		{
			strChart += rs.getString("SystemInfo_ComplayName");
		} else
		{
			strChart += " ";
		}*/

		strChart += "——用水结构划分' baseFont='宋体' baseFontSize='12'  yAxisName='水费（元）' showValues='0' decimalPrecision='2' formatNumberScale='0'>";

		strChart += "<categories>";
		strChart += "<category name='1月' />";
		strChart += "<category name='2月' />";
		strChart += "<category name='3月' />";
		strChart += "<category name='4月' />";
		strChart += "<category name='5月' />";
		strChart += "<category name='6月' />";
		strChart += "<category name='7月' />";
		strChart += "<category name='8月' />";
		strChart += "<category name='9月' />";
		strChart += "<category name='10月' />";
		strChart += "<category name='11月' />";
		strChart += "<category name='12月' />";
		strChart += "</categories>";

		// 分类颜色
		String[] colorarry = new String[]
		{ "AFD8F8", "F6BD0F", "8BBA00", "6FB0D0", "4F8020", "2FA0F0", "0AC0F6",
				"C2D4F6", "7D0B7C", "770022", "66AADD", "60A00D" };
		int i = 0;

		// 按用水性质
		if ("1".equals(strStyle))
		{
			// 从数据字典表中取出用水分类分项信息
            sql = "SELECT DictionaryValue_ID,Dictionary_ID,DictionaryValue_Num,DictionaryValue_Value FROM DictionaryValue where Dictionary_ID = 26 ORDER BY DictionaryValue_Num";
           
            Connection conn1 = null;
    		PreparedStatement ps1 =null;
    		ResultSet rs1 =null;
    		try{
    			conn1 = ConnDB.getConnection();
    			ps1 = conn1.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    			rs1 = ps1.executeQuery();
    			while(rs1.next())
                {
                	if(i++ >= colorarry.length)
                	{
                		break;
                	}
                	strChart += "<dataset seriesName='";
                    strChart += rs1.getString("DictionaryValue_Value") + "' color='" + colorarry[i] + "' showValues='0'>";
                    // 区域建筑分项日用水
                    double[] fM1 = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                    if ("Area".equals(AOrBFlg)) // 区域
                    {
                    	sql = "select SelectMonth, nvl(sum(ZMoney),0) as ZMoney From T_ArcStyleDayWater Where Area_ID =" + BldOrArea_ID;
                    }
                    else   // 建筑
                    {
                    	sql = "select SelectMonth, nvl(sum(ZMoney),0) as ZMoney From T_ArcStyleDayWater Where Architecture_ID =" + BldOrArea_ID;
                    }
                    sql += "  And SelectYear =";
                    sql += strYear + " And UseAmStyle = " + rs1.getString("DictionaryValue_Num") + "  Group by SelectMonth ";
                    Connection conn2 = null;
            		PreparedStatement ps2 =null;
            		ResultSet rs2 =null;
            		try{
            			conn2 = ConnDB.getConnection();
            			ps2 = conn2.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            			rs2 = ps2.executeQuery();
						while (rs2.next())
						{
							fM1[rs2.getInt("SelectMonth") - 1] = rs2.getDouble("ZMoney");
						}
            		}catch (SQLException e)
            		{
            			e.printStackTrace();
            		} finally
            		{
            			ConnDB.release(conn2, ps2,rs2);
            		}
                    
                    for (int j = 0; j < 12; j++)
                    {
                        strChart += "<set value='" + fM1[j] + "' />";
                    }
                    strChart += "</dataset>";
                }
    		}catch (SQLException e)
    		{
    			e.printStackTrace();
    		} finally
    		{
    			ConnDB.release(conn1, ps1,rs1);
    		}
            
		}
		// 按用水分项
		else if ("2".equals(strStyle))
		{
			// 从数据字典表中取出用水分类分项信息
            sql = "SELECT DictionaryValue_ID,Dictionary_ID,DictionaryValue_Num,DictionaryValue_Value FROM DictionaryValue where Dictionary_ID = 25 ORDER BY DictionaryValue_Num";
            Connection conn3 = null;
    		PreparedStatement ps3 =null;
    		ResultSet rs3 =null;
    		try{
    			conn3 = ConnDB.getConnection();
    			ps3 = conn3.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    			rs3 = ps3.executeQuery();
    			while(rs3.next())
                {
                	if(i++ >= colorarry.length)
                	{
                		break;
                	}
                	strChart += "<dataset seriesName='";
                    strChart += rs3.getString("DictionaryValue_Value") + "' color='" + colorarry[i] + "' showValues='0'>";
                    // 区域建筑分项日用水
                    double[] fM1 = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

                    if ("Area".equals(AOrBFlg)) // 区域
                    {
                    	sql = "select SelectMonth, nvl(sum(ZMoney),0) as ZMoney From T_ArcFenleiDayWater Where Area_ID =" + BldOrArea_ID;
                    }
                    else   // 建筑
                    {
                    	sql = "select SelectMonth, nvl(sum(ZMoney),0) as ZMoney From T_ArcFenleiDayWater Where Architecture_ID =" + BldOrArea_ID;
                    }
                    sql += "  And SelectYear =";
                    sql += strYear + " And Fenlei = '" + rs3.getString("DictionaryValue_Num") + "'  Group by SelectMonth ";
                    Connection conn4 = null;
            		PreparedStatement ps4 =null;
            		ResultSet rs4 =null;
            		try{
            			conn4 = ConnDB.getConnection();
            			ps4 = conn4.prepareStatement(sql);
            			rs4 = ps4.executeQuery();
						while (rs4.next())
						{
							fM1[rs4.getInt("SelectMonth") - 1] = rs4.getDouble("ZMoney");
						}
            		}catch (SQLException e)
            		{
            			e.printStackTrace();
            		} finally
            		{
            			ConnDB.release(conn4, ps4,rs4);
            		}
                    
                    for (int j = 0; j < 12; j++)
                    {
                        strChart += "<set value='" + fM1[j] + "' />";
                    }
                    strChart += "</dataset>";
                }
    		}catch (SQLException e)
    		{
    			e.printStackTrace();
    		} finally
    		{
    			ConnDB.release(conn3, ps3,rs3);
    		}
            
		}
		// 按工作时间
		else if ("3".equals(strStyle))
		{
			// 区域建筑分项日用水
            double[] fM1 = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            double[] fM2 = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

            if ("Area".equals(AOrBFlg)) // 区域
            {
            	sql = "select SelectMonth, nvl(sum(WorkMoney),0) as WorkMoney, nvl(sum(UnWorkMoney),0) as UnWorkMoney From T_ArcFenleiDayWater Where Area_ID =" + BldOrArea_ID;
            }
            else   // 建筑
            {
            	sql = "select SelectMonth, nvl(sum(WorkMoney),0) as WorkMoney, nvl(sum(UnWorkMoney),0) as UnWorkMoney From T_ArcFenleiDayWater Where Architecture_ID =" + BldOrArea_ID;
            }
            sql += "  And SelectYear =";
            sql += strYear + "  Group by SelectMonth ";
            Connection conn5 = null;
    		PreparedStatement ps5 =null;
    		ResultSet rs5 =null;
    		try{
    			conn5 = ConnDB.getConnection();
    			ps5 = conn5.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    			rs5 = ps5.executeQuery();
				while (rs5.next())
				{
					fM1[rs5.getInt("SelectMonth") - 1] = rs5.getDouble("WorkMoney");
					fM2[rs5.getInt("SelectMonth") - 1] = rs5.getDouble("UnWorkMoney");
				}
    		}catch (SQLException e)
    		{
    			e.printStackTrace();
    		} finally
    		{
    			ConnDB.release(conn5, ps5,rs5);
    		}
            
            strChart += "<dataset seriesName='";
            strChart += "工作时段" + "' color='" + colorarry[0] + "' showValues='0'>";
            for (int j = 0; j < 12; j++)
            {
                strChart += "<set value='" + fM1[j] + "' />";
            }
            strChart += "</dataset>";

            strChart += "<dataset seriesName='";
            strChart += "非工作时段" + "' color='" + colorarry[1] + "' showValues='0'>";
            for (int j = 0; j < 12; j++)
            {
                strChart += "<set value='" + fM2[j] + "' />";
            } 
            strChart += "</dataset>";
        }
        strChart += "</graph>";
		
		return strChart;
	}
	
	/**
	 * 查询定额统计信息
	 * @param strYear
	 * @param BldOrArea_ID
	 * @param AOrBFlg
	 * @return
	 * @throws SQLException
	 */
	public String getWaterQuotaStatisticInfo(String strYear,String BldOrArea_ID,String AOrBFlg) throws SQLException
	{
		///已用定额
		double fZMoney = 0;
		///每月追加总定额
		double fPerMonthQuota = 0;
		/// 选中对象年的标准分配定额
		double totalStandardQuota = 0;
		totalStandardQuota = getTotalStandardQuota(strYear, BldOrArea_ID, AOrBFlg);
		
		String strTable = "";
		
        // 区域Or建筑ID日用电 
        String sql = "select nvl(sum(ZMoney),0) AS  ZMoney  from T_ArcDayWater where ";
        if ("Area".equals(AOrBFlg))
        {
            sql += " Area_ID =" + BldOrArea_ID;
        }
        else
        {
            sql += " Architecture_ID =" + BldOrArea_ID;
        }
        sql += " AND SelectYear = ";
        sql += strYear;
        
        Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				fZMoney = rs.getDouble("ZMoney");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
       /* Statement sm = null; 
        sm = ConnDB.getConnection().createStatement();
        ResultSet rs = null;
        rs = sm.executeQuery(sql);
        
		if(rs.next())
		{
			fZMoney = rs.getDouble("ZMoney");
		}
		if(rs != null)
        	rs.close();
        if(sm != null)
        	sm.close();
		*/
		// 根据区域或建筑和选择年份获取建筑定额分配表数据
        sql = "select BldOrArea_ID,Year,TiaozhengFixed,FixedZhuijia1,FixedZhuijia2,FixedZhuijia3,FixedZhuijia4,FixedZhuijia5,FixedZhuijia6,FixedZhuijia7,FixedZhuijia8,FixedZhuijia9,FixedZhuijia10,FixedZhuijia11,FixedZhuijia12  from BldQuotaWater where BldOrArea_ID=";       
        sql += BldOrArea_ID + " AND Year = '";
        sql += strYear + "' And AOrBflg = ";
        if ("Area".equals(AOrBFlg))
        {
            sql += "0";
        }
        else
        {
            sql += "1";
        }
        Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			if(!rs1.next())
	        {
	        	String sql1 = "insert into BldQuotaWater ( BldOrArea_ID,AOrBflg,Year,TiaozhengFixed,FixedZhuijia1,FixedZhuijia2,FixedZhuijia3,FixedZhuijia4,FixedZhuijia5,FixedZhuijia6,FixedZhuijia7,FixedZhuijia8,FixedZhuijia9,FixedZhuijia10,FixedZhuijia11,FixedZhuijia12 ) values ( ";
	            sql1 += BldOrArea_ID;
	            // 区域Or建筑ID
	            if ("Area".equals(AOrBFlg))
	            {
	                sql1 += ",0,'";
	            }
	            else
	            {
	                sql1 += ",1,'";
	            }
	            sql1 += strYear + "',0,0,0,0,0,0,0,0,0,0,0,0,0)";
	            Connection conn2 = null;
	    		PreparedStatement ps2 =null;
	    		ResultSet rs2 =null;
	    		try{
	    			conn2 = ConnDB.getConnection();
	    			ps2 = conn2.prepareStatement(sql);
	    			ps2.executeUpdate();

	    		}catch (SQLException e)
	    		{
	    			e.printStackTrace();
	    		} finally
	    		{
	    			ConnDB.release(conn2, ps2,rs2);
	    		}
	          /*  Statement sm2 = ConnDB.getConnection().createStatement();
	            sm2.executeUpdate(sql1);
	            
	            if(sm2 != null)
	            	sm2.close();*/
	            
	            
	        }
	        
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}
        
		Connection conn3 = null;
		PreparedStatement ps3 =null;
		ResultSet rs3 =null;
		try{
			conn3 = ConnDB.getConnection();
			ps3 = conn3.prepareStatement(sql);
			rs3 = ps3.executeQuery();
			if(rs3.next())
			{
				fPerMonthQuota += rs3.getDouble("FixedZhuijia1");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia2");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia3");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia4");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia5");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia6");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia7");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia8");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia9");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia10");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia11");
		    	fPerMonthQuota += rs3.getDouble("FixedZhuijia12");
		    	
		    	strTable = "<table width='100%' border='0' cellspacing='1' cellpadding='1' style='background-color: #b5d6e6'>"+
								"<tr>"+
								  "<td>"+strYear+"年定额统计信息</td> "+
								"</tr> "+
								"<tr>"+
									"<td style='background-color:#E5F4FD;padding: 3 0 3 0'' colspan='8'> "+
								    	"<table border=0 width='100%'>"+
											"<tr>"+
												"<td>分配总定额(元)</td><td>=</td><td>标准分配定额(元)</td><td>+</td><td>调整给定额(元)</td><td>+</td><td>每月追加总定额(元)</td>"+
												"<td>  &nbsp;&nbsp;</td>"+
												"<td>剩余定额(元)</td><td>=</td><td>分配总定额(元)</td><td>-</td><td>已用定额(元)</td>"+
											"</tr> "+
											"<tr>"+
												"<td><label>"+String.valueOf(totalStandardQuota+fPerMonthQuota+rs3.getDouble("TiaozhengFixed"))+"</label></td>" +
												"<td>=</td>" +
												"<td><label>"+totalStandardQuota+"</label></td>" +
												"<td>+</td><td><input type='text' id='TiaozhengFixed' value='"+rs3.getDouble("TiaozhengFixed")+"'  style='width:80px' /></td>" +
												"<td>+</td>" +
												"<td><label>"+fPerMonthQuota+"</label></td>"+
												"<td>  &nbsp;&nbsp;</td>"+
												"<td><label>"+String.valueOf(totalStandardQuota+fPerMonthQuota+rs3.getDouble("TiaozhengFixed")-fZMoney)+"</label></td>" +
												"<td>=</td>" +
												"<td><label>"+String.valueOf(totalStandardQuota+fPerMonthQuota+rs3.getDouble("TiaozhengFixed"))+"</label></td>" +
												"<td>-</td>" +
												"<td><label>"+fZMoney+"</label></td>"+
											"</tr> "+
								        "</table> "+
								    "</td>"+
								"</tr> "+
								"<tr>"+
									"<td style='background-color:#E5F4FD;padding: 3 0 3 0''  valign='top'> "+
								    	"<table  width='100%' border='0' cellspacing='1' cellpadding='1' > "+
								        	"<tr>"+
												"<td align=right>1月份追加(元):</td><td><input type='text' id='waterArchZhuijia1'  value='"+rs3.getDouble("FixedZhuijia1")+"'  style='width:80px' /></td> "+
								            	"<td align=right>2月份追加(元):</td><td><input type='text' id='waterArchZhuijia2'  value='"+rs3.getDouble("FixedZhuijia2")+"'  style='width:80px' /></td> "+
								            	"<td align=right>3月份追加(元):</td><td><input type='text' id='waterArchZhuijia3'  value='"+rs3.getDouble("FixedZhuijia3")+"'  style='width:80px' /></td> "+
								            	"<td align=right>4月份追加(元):</td><td><input type='text' id='waterArchZhuijia4'  value='"+rs3.getDouble("FixedZhuijia4")+"'  style='width:80px' /></td> "+
								            "</tr> "+
								            "<tr>"+
												"<td align=right>5月份追加(元):</td><td><input type='text' id='waterArchZhuijia5'  value='"+rs3.getDouble("FixedZhuijia5")+"'  style='width:80px' /></td> "+
								                "<td align=right>6月份追加(元):</td><td><input type='text' id='waterArchZhuijia6'  value='"+rs3.getDouble("FixedZhuijia6")+"'  style='width:80px' /></td> "+
								                "<td align=right>7月份追加(元):</td><td><input type='text' id='waterArchZhuijia7'  value='"+rs3.getDouble("FixedZhuijia7")+"'  style='width:80px' /></td> "+
								                "<td align=right>8月份追加(元):</td><td><input type='text' id='waterArchZhuijia8'  value='"+rs3.getDouble("FixedZhuijia8")+"'  style='width:80px' /></td> "+
								            "</tr>   "+
								            "<tr>"+
												"<td align=right>9月份追加(元):</td><td><input type='text' id='waterArchZhuijia9'  value='"+rs3.getDouble("FixedZhuijia9")+"'  style='width:80px' /></td> "+
								                "<td align=right>10月份追加(元):</td><td><input type='text' id='waterArchZhuijia10'  value='"+rs3.getDouble("FixedZhuijia10")+"'  style='width:80px' /></td> "+
								                "<td align=right>11月份追加(元):</td><td><input type='text' id='waterArchZhuijia11'  value='"+rs3.getDouble("FixedZhuijia11")+"'  style='width:80px' /></td> "+
								                "<td align=right>12月份追加(元):</td><td><input type='text' id='waterArchZhuijia12'  value='"+rs3.getDouble("FixedZhuijia12")+"'  style='width:80px' /></td> "+
								            "</tr> "+
										"</table> "+
									"</td> "+
								"</tr> "+
								"<tr>"+
									"<td colspan='8' style='background-color:#E5F4FD; padding: 3 0 3 0'' align='right' >"+
									"<input type='button' id='waterArchQSSubmitBtn'  class='btn' value='提交定额信息' style='width:120px' onclick='waterArchQSSubmit()' />"+
									"</td>"+
								"</tr>"+
								"</table> ";
			}
	        
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn3, ps3,rs3);
		}
		
		return strTable;		
	}
	
	/**
	 * 更新定额统计信息
	 * @param strYear
	 * @param BldOrArea_ID
	 * @param AOrBFlg
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public String saveWaterArchQuotaStatisticInfo(String strYear, String BldOrArea_ID,String AOrBFlg, String values) throws SQLException
	{
		String returnMsg = "";
		String[] valuesList = values.split("\\|");
		
		if("Architecture".equals(AOrBFlg))
		{
			double parentTotalQuota = 0;     // 区域的总分配定额
            int areaID = -1;            // 所属的区域ID

            // 求出所属的区域ID
            String queryAreaID = "select Area_ID from Architecture where Architecture_ID="+BldOrArea_ID;
            Connection conn = null;
    		PreparedStatement ps =null;
    		ResultSet rs =null;
    		try{
    			conn = ConnDB.getConnection();
    			ps = conn.prepareStatement(queryAreaID);
    			rs = ps.executeQuery();
    			if(rs.next())
                {
                	areaID = rs.getInt("Area_ID");
                }
    		}catch (SQLException e)
    		{
    			e.printStackTrace();
    		} finally
    		{
    			ConnDB.release(conn, ps,rs);
    		}
            
           /* Statement sm_queryAreaID = ConnDB.getConnection().createStatement();
            ResultSet rs_queryAreaID = sm_queryAreaID.executeQuery(queryAreaID);
            if(rs_queryAreaID.next())
            {
            	areaID = rs_queryAreaID.getInt("Area_ID");
            }
            if(rs_queryAreaID != null)
            	rs_queryAreaID.close();
            if(sm_queryAreaID != null)
            	sm_queryAreaID.close();*/
            
            if(areaID != -1)
            {
                // 区域的总分配定额
                parentTotalQuota = getWaterQuotaInfoByYearBldID(String.valueOf(areaID), "0", strYear);

                // 取得区域下面的所有建筑
                String queryChildArch = "SELECT Architecture_ID FROM Architecture WHERE Area_ID = " + areaID;
                
                Connection conn2 = null;
        		PreparedStatement ps2 =null;
        		ResultSet rs2 =null;
        		try{
        			conn2 = ConnDB.getConnection();
        			ps2 = conn2.prepareStatement(queryChildArch);
        			rs2 = ps2.executeQuery();
					while (rs2.next())
					{
						// 求出兄弟建筑的对象年定额情报（包括自己）
						parentTotalQuota -= getWaterQuotaInfoByYearBldID(rs2.getString("Architecture_ID"), "1", strYear);
					}
        		}catch (SQLException e)
        		{
        			e.printStackTrace();
        		} finally
        		{
        			ConnDB.release(conn2, ps2,rs2);
        		}
              
            }

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            // 月份追加
            for (int i = 1; i <= 12; i++)
            {
            	iResult += Double.valueOf(valuesList[i]);
            }
            thisTotalStandardQuota = iResult + Double.valueOf(valuesList[0]);

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
		}
		
		String sql = "update BldQuotaWater set ";
        // 月份追加
        for (int i = 1; i <= 12; i++)
        {
            sql += "FixedZhuijia" + i + " = " + valuesList[i] + ", ";
        }
        // 调整给定额(元)
        sql += " TiaozhengFixed = " + valuesList[0];

        // 区域建筑ID
        sql += " where BldOrArea_ID = " + BldOrArea_ID + " AND Year = '";
		sql += strYear + "' And AOrBflg = ";
		// 区域Or建筑标识
		if ("Area".equals(AOrBFlg))
		{
			sql += "0";
		} else
		{
			sql += "1";
		}
		Connection conn3 = null;
		PreparedStatement ps3 = null;
		try
		{
			conn3 = ConnDB.getConnection();
			ps3 = conn3.prepareStatement(sql);
			if(ps3.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn3, ps3);
		}
		
		return returnMsg;
	}
	
	public String makeQuotaTablePerMonth(String strYear, String BldOrArea_ID,String AOrBFlg) throws SQLException
	{
		int i = 0;
		// / 选中对象年的已经使用定额
		double[] fM1 = new double[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		// / 选中对象年的前一年的已经使用定额
		double[] fM2 = new double[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置 
        String[] strM1 = new String[] { "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-" }; // 同比增幅
        String[] strM2 = new String[] { "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-" }; // 环比增幅
        String write = "";
        
        // 选中对象年的已经使用定额(选中区域建筑的)
     	fM1 = GetCostPerMByYearBldID(BldOrArea_ID, AOrBFlg, strYear);
        // 前一年的已经使用定额(选中区域建筑的) 
     	fM2 = GetCostPerMByYearBldID(BldOrArea_ID, AOrBFlg, String.valueOf(Integer.parseInt(strYear)-1));
     	
     	write += "<table class='table table-striped table-bordered table-hover'>"; 
     	write += "<tr><th>月份</th>";
     	for(int k=1;k<=12;k++)
     	{
     		write += "<th>"+k+"月</th>";
     	}
     	write += "</tr>";
     	
     	// 选中对象年的已经使用定额
        write += "<tr><td>" + strYear + "年</td>";
        for (i = 0; i < 12; i++)
        {
            write += "<td>"+decimalFormat.format(fM1[i])+"</td>";
        }
        write += "</tr>";

        // 选中对象年的前年已经使用定额
        write += "<tr><td>" + (Integer.parseInt(strYear)-1) + "年</td>";
        for (i = 0; i < 12; i++)
        {
        	write += "<td>"+decimalFormat.format(fM2[i])+"</td>";
        }
        write += "</tr>";
        
        // 同比增幅
        write += "<tr><td>同比增幅</td>";
        for (i = 0; i < 12; i++)
        {
            double fResult;
            if (fM1[i] != 0 && fM2[i] != 0)
            {
                fResult = fM1[i] - fM2[i];
                fResult = fResult / Math.abs(fM2[i]);
                fResult = Math.round(fResult * 100 * 10)/10;
                if (fResult >= 0)
                {
                    // 红色的增幅
                    write += "<td><span style='color:Red'>↑" + String.valueOf(fResult) + "%</td>";
                }
                else
                {
                    // Gree色的减幅
                    write += "<td><span style='color:Green'>↓" + String.valueOf(fResult) + "%</td>";
                }
            }
            else
            {
                // “-”无可比性
                write += "<td>" + strM1[i] + "</td>";
            }
        }
        write += "</tr>";
        
        // 环比增幅
        write += "<tr><td>环比增幅</td>";
        // 选中对象年1月份和前年12月份的环比
        if (fM1[0] != 0 && fM2[11] != 0)
        {
            double fResult;
            fResult = fM1[0] - fM2[11];
            fResult = fResult / Math.abs(fM2[11]);
            fResult = Math.round(fResult * 100 * 10)/10;
            if (fResult >= 0)
            {
                // 红色的增幅
                write += "<td><span style='color:Red'>↑" + String.valueOf(fResult) + "%</td>";
            }
            else
            {
                // Gree色的减幅
                write += "<td><span style='color:Green'>↓" + String.valueOf(fResult) + "%</td>";
            }
        }
        else
        {
            // “-”无可比性
            write += "<td>" + strM2[0] + "</td>";
        }
        for (i = 1; i < 12; i++)
        {
            if (fM1[i] != 0 && fM1[i - 1] != 0)
            {
                double fResult;
                fResult = fM1[i] - fM1[i - 1];
                fResult = fResult / Math.abs(fM1[i-1]);
                fResult = Math.round(fResult * 100 * 10)/10;
                if (fResult >= 0)
                {
                    // 红色的增幅
                    write += "<td><span style='color:Red'>↑" + String.valueOf(fResult) + "%</td>";
                }
                else
                {
                    // Gree色的减幅
                    write += "<td><span style='color:Green'>↓" + String.valueOf(fResult) + "%</td>";
                }
            }
            else
            {
                // “-”无可比性
                write += "<td>" + strM2[1] + "</td>";
            }
        }
        write += "</tr></table>";
		return write;
		
	}
	
	public ArrayList<WaterQuotaManagerModel> sortQuery(String strYear,
			String userID, int pageCount, int pageIndex, String order) throws SQLException
	{
		WaterQuotaManagerModel wqmm = null;
		ArrayList<WaterQuotaManagerModel> list = new ArrayList<WaterQuotaManagerModel>();

		String sql = "select g.name as name, g.BldOrArea_ID,nvl(f.totalQuota,0) as totalQuota,nvl(f.standardQuota,0) as standardQuota ,nvl(f.TiaozhengFixed,0) as TiaozhengFixed,nvl(f.addPerMonthQuota,0) as addPerMonthQuota,nvl(f.ZMoney,0) as ZMoney,nvl(f.remainQuota,0) as remainQuota from ";
		sql += "( ";
		sql += "	select ";
		sql += "        a.Area_ID as BldOrArea_ID, ";
		sql += "        '0' as AOrBflg, ";
		sql += "        a.Area_Name as name ";
		sql += "	from  ";
		sql += "	    Area a, ";
		sql += "	    OprArea_List b ";
		sql += "	where ";
		sql += "	    b.Users_ID = " + userID + " And ";
		sql += "	    a.Area_ID = b.Area_ID ";
		sql += "	union all ";
		sql += "	select ";
		sql += "	    a.Architecture_ID  as BldOrArea_ID, ";
		sql += "        '1' as AOrBflg, ";
		sql += "	    a.Architecture_Name as name ";
		sql += "	from   ";
		sql += "	    Architecture a,  ";
		sql += "	    OprArc_List b  ";
		sql += "	where  ";
		sql += "	    b.Users_ID = " + userID + " And  ";
		sql += "	    a.Architecture_ID = b.Architecture_ID  ";
		sql += ") g  ";
		sql += " left join  ";
		sql += "(   ";
		sql += "    select  ";
		sql += "        d.BldOrArea_ID, ";
		sql += "        d.AOrBflg, ";
		sql += "        d.standardQuota + d.TiaozhengFixed + d.addPerMonthQuota  as totalQuota,  ";
		sql += "        d.standardQuota,  ";
		sql += "        d.TiaozhengFixed,  ";
		sql += "        d.addPerMonthQuota,  ";
		sql += "        e.ZMoney,  ";
		sql += "        d.standardQuota + d.TiaozhengFixed + d.addPerMonthQuota - e.ZMoney as remainQuota  ";
		sql += "    from   ";
		sql += "        (  ";
		sql += "            select  ";
		sql += "                c.BldOrArea_ID, ";
		sql += "                c.AOrBflg, ";
		sql += "                c.TiaozhengFixed,  ";
		sql += "                c.FixedZhuijia1 + FixedZhuijia2 + FixedZhuijia3 + FixedZhuijia4 + c.FixedZhuijia5 + FixedZhuijia6 + FixedZhuijia7 + FixedZhuijia8 + c.FixedZhuijia9 + FixedZhuijia10 + FixedZhuijia11 + FixedZhuijia12 as addPerMonthQuota,  ";
		sql += "                b.standardQuota  ";
		sql += "            from   ";
		sql += "                (  ";
		sql += "                    select   ";
		sql += "                        a.BldOrArea_ID, ";
		sql += "                        a.AOrBflg, ";
		sql += "                        nvl(sum(a.sum1),0) as standardQuota  ";
		sql += "                    from   ";
		sql += "                        (  ";
		sql += "                            select  ";
		sql += "                                BldOrArea_ID, ";
		sql += "                                AOrBflg,  ";
		sql += "                                nvl(sum(  ";
		sql += "                                    ( case M1 when 1 then 1 else 0 end +  ";
		sql += "                                      case M2 when 1 then 1 else 0 end +  ";
		sql += "                                      case M3 when 1 then 1 else 0 end +  ";
		sql += "                                      case M4 when 1 then 1 else 0 end +   ";
		sql += "                                      case M5 when 1 then 1 else 0 end +  ";
		sql += "                                      case M6 when 1 then 1 else 0 end +  ";
		sql += "                                      case M7 when 1 then 1 else 0 end +  ";
		sql += "                                      case M8 when 1 then 1 else 0 end +  ";
		sql += "                                      case M9 when 1 then 1 else 0 end +   ";
		sql += "                                      case M10 when 1 then 1 else 0 end +  ";
		sql += "                                      case M11 when 1 then 1 else 0 end +  ";
		sql += "                                      case M12 when 1 then 1 else 0 end  ";
		sql += "                                    ) * TotalDevice * Power * RunHoursPerMth * Price ),0) as sum1 ";
		sql += "                            from  ";
		sql += "                                BldQutaDetlByDevWater  ";
		sql += "                            where  ";
		sql += "                                Year = '" + strYear + "'";
		sql += "                            Group by  ";
		sql += "                                BldOrArea_ID,AOrBflg ";
		sql += "                            union all  ";
		sql += "                            select  ";
		sql += "                                BldOrArea_ID, ";
		sql += "                                AOrBflg,  ";
		sql += "                                nvl(sum(  ";
		sql += "                                    ( case M1 when 1 then 1 else 0 end +  ";
		sql += "                                      case M2 when 1 then 1 else 0 end +  ";
		sql += "                                      case M3 when 1 then 1 else 0 end +   ";
		sql += "                                      case M4 when 1 then 1 else 0 end +  ";
		sql += "                                      case M5 when 1 then 1 else 0 end +  ";
		sql += "                                      case M6 when 1 then 1 else 0 end +   ";
		sql += "                                      case M7 when 1 then 1 else 0 end +  ";
		sql += "                                      case M8 when 1 then 1 else 0 end +   ";
		sql += "                                      case M9 when 1 then 1 else 0 end +  ";
		sql += "                                      case M10 when 1 then 1 else 0 end +  ";
		sql += "                                      case M11 when 1 then 1 else 0 end +  ";
		sql += "                                      case M12 when 1 then 1 else 0 end  ";
		sql += "                                      ) * TotalPeople * EStandard * Price ),0)  as sum1   ";
		sql += "                            from  ";
		sql += "                                BldQutaDetlByPeoWater ";
		sql += "                            where  ";
		sql += "                               Year = '" + strYear + "'";
		sql += "                            Group by  ";
		sql += "                                BldOrArea_ID,AOrBflg ";
		sql += "                        ) a  ";
		sql += "                    group by  ";
		sql += "                        a.BldOrArea_ID,a.AOrBflg  ";
		sql += "                ) b,  ";
		sql += "                BldQuotaWater c  ";
		sql += "            where  ";
		sql += "                c.BldOrArea_ID = b.BldOrArea_ID  And  ";
		sql += "                c.AOrBflg = b.AOrBflg  And  ";
		sql += "                c.Year = '" + strYear + "'";
		sql += "            ) d left join   ";
		sql += "            (  ";
		sql += "                select  ";
		sql += "                    Architecture_ID as BldOrArea_ID, ";
		sql += "                    '1' as AOrBflg,  ";
		sql += "                    nvl(sum(ZMoney),0) AS ZMoney  ";
		sql += "                from  ";
		sql += "                    T_ArcDayWater ";
		sql += "                where  ";
		sql += "                    SelectYear = '" + strYear + "'";
		sql += "                group by Architecture_ID ";
		sql += "                union all ";
		sql += "                select  ";
		sql += "                    Area_ID as BldOrArea_ID, ";
		sql += "                    '0' as AOrBflg,  ";
		sql += "                    nvl(sum(ZMoney),0) AS ZMoney  ";
		sql += "                from  ";
		sql += "                    T_ArcDayWater ";
		sql += "                where  ";
		sql += "                    SelectYear = '" + strYear + "'";
		sql += "                group by Area_ID ";
		sql += "            ) e  ";
		sql += "            on e.BldOrArea_ID = d.BldOrArea_ID  And ";
		sql += "            e.AOrBflg = d.AOrBflg   ";
		sql += ") f  ";
		sql += "on f.BldOrArea_ID = g.BldOrArea_ID  And ";
		sql += "   f.AOrBflg = g.AOrBflg  " + order;

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
				rs.beforeFirst();
			} else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				wqmm = new WaterQuotaManagerModel();

				wqmm.setYear(strYear);
				wqmm.setName(rs.getString("name"));
				wqmm.setTotalQuota(rs.getString("totalQuota"));
				wqmm.setStandardQuota(rs.getString("standardQuota"));
				wqmm.setTiaozhengFixed(rs.getString("TiaozhengFixed"));
				wqmm.setAddPerMonthQuota(rs.getString("addPerMonthQuota"));
				wqmm.setZMoney(rs.getString("ZMoney"));
				wqmm.setRemainQuota(rs.getString("remainQuota"));

				list.add(wqmm);
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
	
	/**
	 * 根据给定的ID,对象年求出其用水定额情报
	 * @param BldOrArea_ID
	 * @param AOrBFlg
	 * @param strYear
	 * @return
	 * @throws SQLException
	 */
	public double getWaterQuotaInfoByYearBldID(String BldOrArea_ID, String AOrBFlg, String strYear) throws SQLException
	{
		double totalQuota = 0;			// 总分配定额
        double totalStandardQuota = 0;   // 标准分配定额
        double tiaozhengQuota = 0;       // 调整给定额
        double fPerMonthQuota = 0;       // 每月追加总定额
        double spentQuota = 0;           // 已用定额
        double remainQuota = 0;          // 剩余定额
        
        
        if("0".equals(AOrBFlg))
        {
        	totalStandardQuota = getTotalStandardQuota(strYear, BldOrArea_ID, "Area");
        }
        else
        {
        	totalStandardQuota = getTotalStandardQuota(strYear, BldOrArea_ID, "Arch");
        }
        
        ////System.out.println(totalStandardQuota);
        // 已用定额
        String sql = "";
//        sql = "select nvl(sum(ZMoney),0) AS ZMoney from T_ArcDayAm where SelectYear = '";
//        sql += strYear + "' And ";
//        // 区域ID的时候
//        if ("0".equals(AOrBFlg))
//        {
//            sql += "Area_ID = " + BldOrArea_ID;
//        }
//        else
//        {
//            sql += "Architecture_ID = " + BldOrArea_ID;
//        }
//        Statement sm = ConnDB.getConnection().createStatement();
//        ResultSet rs = sm.executeQuery(sql);
//        if(rs.next())
//        {
//        	spentQuota = rs.getDouble("ZMoney");
//        }
//        if(rs!=null)
//        	rs.close();
//        if(sm!=null)
//        	sm.close();

        // 区域建筑的选中对象年的【部门定额分配表】情报取得
        sql = "select BldOrArea_ID,Year,TiaozhengFixed,FixedZhuijia1,FixedZhuijia2,FixedZhuijia3,FixedZhuijia4,FixedZhuijia5,FixedZhuijia6,FixedZhuijia7,FixedZhuijia8,FixedZhuijia9,FixedZhuijia10,FixedZhuijia11,FixedZhuijia12  from BldQuotaWater where BldOrArea_ID=";
        sql += BldOrArea_ID + " AND AOrBflg = " + AOrBFlg + " AND Year = '" + strYear + "' ";

		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			 if(rs1.next())
		        {
		            // 每月追加总定额
		        	fPerMonthQuota += rs1.getDouble("FixedZhuijia1");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia2");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia3");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia4");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia5");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia6");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia7");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia8");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia9");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia10");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia11");
			    	fPerMonthQuota += rs1.getDouble("FixedZhuijia12");

		            // 调整给定额
		            tiaozhengQuota = rs1.getDouble("TiaozhengFixed");
		            // 总分配定额
		            totalQuota =totalStandardQuota + fPerMonthQuota + tiaozhengQuota;
		            // 剩余定额
		            remainQuota = totalQuota - spentQuota;
		        }
		        else// 如果该部门选中对象年的【部门定额分配表】情报不存在的时候
		        {
		        	// 每月追加总定额
		            fPerMonthQuota = 0;
		            // 调整给定额
		            tiaozhengQuota = 0;
		            // 总分配定额
		            totalQuota = totalStandardQuota + fPerMonthQuota +tiaozhengQuota;
		            // 剩余定额
		            remainQuota = totalQuota - spentQuota;
		        }
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}
      
		return totalQuota;	
	}
	
}
