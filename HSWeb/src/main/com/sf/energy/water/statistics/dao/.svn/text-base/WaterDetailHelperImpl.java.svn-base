package com.sf.energy.water.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sf.energy.util.ConnDB;
import com.sf.energy.statistics.dao.DictionaryValueImpl;
import com.sf.energy.statistics.dao.DictionaryValueHelper;
import com.sf.energy.water.statistics.model.WaterDetailData;

public class WaterDetailHelperImpl
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
	 * 通过起始和截止时间查询所有部门下的电表的建筑用水明细
	 */
	public List<WaterDetailData> getWaterDtailAllPartment(int thePageCount, int thePageIndex, String start, String end, String order)
			throws SQLException, RuntimeException
			{
		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		List<WaterDetailData> ArcAmDetail_list = new ArrayList<WaterDetailData>();
		WaterDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量

		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();

		String sql = "select  "
				+ "	a.Watermeter_ID,a.Architecture_ID,a.Watermeter_name,a.isComputation, "
				+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  "
				+ "	Watermeter_Num, Watermeter_485address,BeiLv, "
				+ "	UseAmStyle ,area_name "
				// +
				// ",max(ZVALUEZY),min(ZVALUEZY),nvl(to_char(max(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime,nvl(to_char(min(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(max(ZVALUEZY)-min(ZVALUEZY))WaterValue "+
				+ "	from  " + "	Watermeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment "
				+ "				left join Architecture on a.Architecture_ID = Architecture.Architecture_ID "
				+ "				left join	area on a.area_id = area.area_id	 " +
				// "				left join  watermeterdatas on a.Watermeter_ID = watermeterdatas.Watermeter_ID    "+
				// "	and watermeterdatas.valuetime between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')  "+
				// "	group by a.Watermeter_ID,a.Architecture_ID,a.Watermeter_name,a.isComputation,Architecture_NAME,Partment_Name,floor, Watermeter_Num, Watermeter_485address,BeiLv,UseAmStyle ,area_name  "+
				order;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			int num = rs.getRow();
			setTotalCount(num);
			if (num <= (thePageCount * thePageIndex))
				return ArcAmDetail_list;

			num = num - thePageCount * thePageIndex;

			if (num >= thePageCount)
				num = thePageCount;

			if ((thePageCount * thePageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);

			while (rs.next() && (num > 0))
			{
				aadd = new WaterDetailData();
				int watermeterid = rs.getInt("Watermeter_ID");
				aadd.setWatermeter_ID(watermeterid);
				aadd.setWatermeter_name(rs.getString("Watermeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));
				String maxtime = null;
				String mintime = null;
				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) WaterValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				//System.out.println(sqllString);
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					ps1 = conn.prepareStatement(sqllString);

					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						if (rs1.getString("max_valuetime") != null)
						{
							maxtime = rs1.getString("max_valuetime");
						} else
						{
							maxtime = "";
						}
						if (rs1.getString("min_valuetime") != null)
						{
							mintime = rs1.getString("min_valuetime");
						} else
						{
							mintime = "";
						}
						aadd.setStarttime(mintime);
						aadd.setEndtime(maxtime);
						aadd.setStartvalue(rs1.getFloat("minzvaluezy"));
						aadd.setEndvalue(rs1.getFloat("maxzvaluezy"));
						aadd.setTotalvalue(rs1.getFloat("WaterValue"));
					}

					aadd.setWatermeter_num(rs.getString("Watermeter_num"));
					String Watermeter_num = aadd.getWatermeter_num();
					String Fenxiang = 'A' + "";
					if (Watermeter_num == null || Watermeter_num.length() != 16) // 如果编号为空或者长度不正常时用默认的
					{
						Fenxiang = 'A' + "";
					} else
					{
						Fenxiang = Watermeter_num.toCharArray()[13] + ""; // 如果编号正常时用指定的14位分项
					}

					aadd.setWatermeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 25));
					style = rs.getString("UseAmStyle");
					aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
					aadd.setBeiLv(rs.getInt("BeiLv"));
					aadd.setWatermeterNo(rs.getString("WaterMeter_485Address"));
					count = rs.getInt("isComputation");
					if (count == 1)
					{
						name = "是";
						aadd.setIstotalvalue(rs1.getFloat("WaterValue"));
					} else
					{
						name = "否";
						aadd.setNototalvalue(rs1.getFloat("WaterValue"));
					}
					aadd.setIsTotal(name);
					ArcAmDetail_list.add(aadd);
					num--;
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps1,rs1);
				}


			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}

		return ArcAmDetail_list;
			}

	/**
	 * 通过起始和截止时间查询所有部门下的水表的建筑用电明细
	 */
	public List<WaterDetailData> getAmDtailAllPartment(int thePageCount, int thePageIndex, String start, String end, String order)
			throws SQLException
			{
		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		List<WaterDetailData> ArcAmDetail_list = new ArrayList<WaterDetailData>();
		WaterDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量

		// System.out.println("1:"+new Date());
		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();

		String sql1 = "SELECT T.WATERmeter_ID,T.Architecture_ID,T.WATERmeter_name,T.isComputation,T.Architecture_NAME"
				+ ",NVL(T.Partment_Name,'-')Partment_Name,T.floor,T.WATERmeter_Num,T.WATERmeter_485address,T.BeiLv,T.UseAmStyle"
				+ ",NVL(T.area_name,'-')area_name"
				+ " FROM (SELECT  a.WATERmeter_ID,Architecture_ID,WATERmeter_name,isComputation, (SELECT Architecture_NAME FROM"
				+ " ARCHITECTURE WHERE a.Architecture_ID = Architecture.Architecture_ID  )Architecture_NAME, "
				+ "(SELECT NVL(Partment_Name,'-') FROM PARTMENT where Partment.Partment_ID = a.Partment )Partment_Name, "
				+ "floor,WATERmeter_Num,WATERmeter_485address,BeiLv,UseAmStyle, "
				+ "(SELECT NVL(area_name,'-') FROM area where a.area_id = area.area_id)area_name " + "FROM " + "WATERmeter a)T  ";

		String sql = sql1 + order;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			// System.out.println("3:"+new Date());
			int num = rs.getRow();
			setTotalCount(num);
			if (num <= (thePageCount * thePageIndex))
				return ArcAmDetail_list;

			num = num - thePageCount * thePageIndex;

			if (num >= thePageCount)
				num = thePageCount;

			if ((thePageCount * thePageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);

			while (rs.next() && (num > 0))
			{
				aadd = new WaterDetailData();
				int watermeterid = rs.getInt("Watermeter_ID");
				aadd.setWatermeter_ID(watermeterid);
				aadd.setWatermeter_name(rs.getString("Watermeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));

				String maxtime = null;
				String mintime = null;

				//				sql = "SELECT max(ZVALUEZY) maxzvaluezy,min(ZVALUEZY) minzvaluezy,NVL(TO_CHAR(MAX(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime"
				//						+ ",NVL(TO_CHAR(MIN(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(MAX(ZVALUEZY)-MIN(ZVALUEZY))WaterValue  "
				//						+ "from ZWATERDATAS"
				//						+ watermeterid
				//						+ " WHERE "
				//						+ "valuetime BETWEEN TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') AND TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') ";
				//				Connection conn1 = null;
				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) WaterValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				//System.out.println(sqllString);
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					ps1 = conn.prepareStatement(sqllString);

					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						if (rs1.getString("max_valuetime") != null)
						{
							maxtime = rs1.getString("max_valuetime");
						} else
						{
							maxtime = "";
						}
						if (rs1.getString("min_valuetime") != null)
						{
							mintime = rs1.getString("min_valuetime");
						} else
						{
							mintime = "";
						}
						aadd.setStarttime(mintime);
						aadd.setEndtime(maxtime);
						aadd.setStartvalue(rs1.getFloat("minzvaluezy"));
						aadd.setEndvalue(rs1.getFloat("maxzvaluezy"));
						aadd.setTotalvalue(rs1.getFloat("WaterValue"));
					}



					aadd.setWatermeter_num(rs.getString("Watermeter_num"));
					String Watermeter_num = aadd.getWatermeter_num();
					String Fenxiang = 'A' + "";
					if (Watermeter_num == null || Watermeter_num.length() < 16)
					{
						Fenxiang = 'A' + "";
					} else
					{
						Fenxiang = Watermeter_num.toCharArray()[13] + "";
					}

					aadd.setWatermeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 25));
					style = rs.getString("UseAmStyle");
					aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
					aadd.setBeiLv(rs.getInt("BeiLv"));
					aadd.setWatermeterNo(rs.getString("WaterMeter_485Address"));
					count = rs.getInt("isComputation");
					if (count == 1)
					{
						name = "是";
						aadd.setIstotalvalue(rs1.getFloat("WaterValue"));
					} else
					{
						name = "否";
						aadd.setNototalvalue(rs1.getFloat("WaterValue"));
					}
					aadd.setIsTotal(name);
					ArcAmDetail_list.add(aadd);
					num--;
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps1,rs1);
				}

			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}

		return ArcAmDetail_list;

			}


	/**
	 * 通过起始和截止时间查询指定区域下的电表的建筑用水明细
	 */
	public List<WaterDetailData> getWaterDtailByarea(int thePageCount, int thePageIndex, int areaID, String start, String end, String order)
			throws SQLException
			{
		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		List<WaterDetailData> ArcAmDetail_list = new ArrayList<WaterDetailData>();
		WaterDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量

		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();

		String sql = "select  "
				+ "	a.Watermeter_ID,a.Architecture_ID,a.Watermeter_name,a.isComputation, "
				+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  "
				+ "	Watermeter_Num, Watermeter_485address,BeiLv, "
				+ "	UseAmStyle ,area_name "
				// +
				// ",max(ZVALUEZY),min(ZVALUEZY),nvl(to_char(max(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime,nvl(to_char(min(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(max(ZVALUEZY)-min(ZVALUEZY))WaterValue "+
				+ "	from  " + "	Watermeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment "
				+ "				left join Architecture on a.Architecture_ID = Architecture.Architecture_ID "
				+ "				inner join	area on a.area_id = area.area_id and  a.area_id =?	 " +order;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, areaID);
			rs = pstmt.executeQuery();
			rs.last();
			int num = rs.getRow();
			setTotalCount(num);
			if (num <= (thePageCount * thePageIndex))
				return ArcAmDetail_list;

			num = num - thePageCount * thePageIndex;

			if (num >= thePageCount)
				num = thePageCount;

			if ((thePageCount * thePageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);

			while (rs.next() && (num > 0))
			{
				aadd = new WaterDetailData();
				int watermeterid = rs.getInt("Watermeter_ID");
				aadd.setWatermeter_ID(watermeterid);
				aadd.setWatermeter_name(rs.getString("Watermeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));
				String maxtime = null;
				String mintime = null;
				//				sql = "select max(ZVALUEZY) maxzvaluezy,min(ZVALUEZY) minzvaluezy,nvl(to_char(max(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime,nvl(to_char(min(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(max(ZVALUEZY)-min(ZVALUEZY))WaterValue "
				//						+ " from Watermeter a"
				//						+ " left join  ZWATERDATAS"
				//						+ watermeterid
				//						+ " b on b.VALUETIME between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')  ";
				//				Connection conn1 = null;

				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) WaterValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				//System.out.println(sqllString);
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					ps1 = conn.prepareStatement(sqllString);

					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						if (rs1.getString("max_valuetime") != null)
						{
							maxtime = rs1.getString("max_valuetime");
						} else
						{
							maxtime = "";
						}
						if (rs1.getString("min_valuetime") != null)
						{
							mintime = rs1.getString("min_valuetime");
						} else
						{
							mintime = "";
						}
						aadd.setStarttime(mintime);
						aadd.setEndtime(maxtime);
						aadd.setStartvalue(rs1.getFloat("minzvaluezy"));
						aadd.setEndvalue(rs1.getFloat("maxzvaluezy"));
						aadd.setTotalvalue(rs1.getFloat("WaterValue"));
					}

					aadd.setWatermeter_num(rs.getString("Watermeter_num"));
					String Watermeter_num = aadd.getWatermeter_num();
					String Fenxiang = 'A' + "";
					if (Watermeter_num == null || Watermeter_num.length() != 16) // 如果编号为空或者长度不正常时用默认的
					{
						Fenxiang = 'A' + "";
					} else
					{
						Fenxiang = Watermeter_num.toCharArray()[13] + ""; // 如果编号正常时用指定的14位分项
					}
					aadd.setWatermeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 25));
					style = rs.getString("UseAmStyle");
					aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
					aadd.setBeiLv(rs.getInt("BeiLv"));
					aadd.setWatermeterNo(rs.getString("WaterMeter_485Address"));
					count = rs.getInt("isComputation");
					if (count == 1)
					{
						name = "是";
						aadd.setIstotalvalue(rs1.getFloat("WaterValue"));
					} else
					{
						name = "否";
						aadd.setNototalvalue(rs1.getFloat("WaterValue"));
					}
					aadd.setIsTotal(name);
					ArcAmDetail_list.add(aadd);
					num--;
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps1,rs1);
				}

			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}

		return ArcAmDetail_list;
			}

	/**
	 * 通过起始和截止时间查询指定部门下的电表的建筑用水明细
	 */
	public List<WaterDetailData> getWaterDtailByPartment(int thePageCount, int thePageIndex, int partment_id, String start, String end, String order)
			throws SQLException
			{

		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		List<WaterDetailData> ArcAmDetail_list = new ArrayList<WaterDetailData>();
		WaterDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量

		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();

		String sql = "select  "
				+ "	a.Watermeter_ID,a.Architecture_ID,a.Watermeter_name,a.isComputation, "
				+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  "
				+ "	Watermeter_Num, Watermeter_485address,BeiLv, "
				+ "	UseAmStyle ,area_name"
				// +
				// " ,max(ZVALUEZY),min(ZVALUEZY),nvl(to_char(max(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime,nvl(to_char(min(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(max(ZVALUEZY)-min(ZVALUEZY))WaterValue "
				+ "	from  " + "	Watermeter a  " + "				inner join  Partment on Partment.Partment_ID = a.Partment and  Partment.Partment_ID =? "
				+ "				left join Architecture on a.Architecture_ID = Architecture.Architecture_ID "
				+ "				left join	area on a.area_id = area.area_id 	 "
				// +
				// "				left join  watermeterdatas on a.Watermeter_ID = watermeterdatas.Watermeter_ID    "
				// +
				// "	and watermeterdatas.valuetime between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')  "
				// +
				// "	group by a.Watermeter_ID,a.Architecture_ID,a.Watermeter_name,a.isComputation,Architecture_NAME,Partment_Name,floor, Watermeter_Num, Watermeter_485address,BeiLv,UseAmStyle ,area_name  "
				+ order;

		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, partment_id);
			rs = pstmt.executeQuery();
			rs.last();
			int num = rs.getRow();
			setTotalCount(num);
			if (num <= (thePageCount * thePageIndex))
				return ArcAmDetail_list;

			num = num - thePageCount * thePageIndex;

			if (num >= thePageCount)
				num = thePageCount;

			if ((thePageCount * thePageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);

			while (rs.next() && (num > 0))
			{
				aadd = new WaterDetailData();
				int watermeterid = rs.getInt("Watermeter_ID");
				aadd.setWatermeter_ID(watermeterid);
				aadd.setWatermeter_name(rs.getString("Watermeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));
				String maxtime = null;
				String mintime = null;
				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) WaterValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				//System.out.println(sqllString);
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					ps1 = conn.prepareStatement(sqllString);

					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						if (rs1.getString("max_valuetime") != null)
						{
							maxtime = rs1.getString("max_valuetime");
						} else
						{
							maxtime = "";
						}
						if (rs1.getString("min_valuetime") != null)
						{
							mintime = rs1.getString("min_valuetime");
						} else
						{
							mintime = "";
						}
						aadd.setStarttime(mintime);
						aadd.setEndtime(maxtime);
						aadd.setStartvalue(rs1.getFloat("minzvaluezy"));
						aadd.setEndvalue(rs1.getFloat("maxzvaluezy"));
						aadd.setTotalvalue(rs1.getFloat("WaterValue"));
					}

					aadd.setWatermeter_num(rs.getString("Watermeter_num"));
					String Watermeter_num = aadd.getWatermeter_num();
					String Fenxiang = 'A' + "";
					if (Watermeter_num == null || Watermeter_num.length() != 16) // 如果编号为空或者长度不正常时用默认的
					{
						Fenxiang = 'A' + "";
					} else
					{
						Fenxiang = Watermeter_num.toCharArray()[13] + ""; // 如果编号正常时用指定的14位分项
					}
					aadd.setWatermeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 25));
					style = rs.getString("UseAmStyle");
					aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
					aadd.setBeiLv(rs.getInt("BeiLv"));
					aadd.setWatermeterNo(rs.getString("WaterMeter_485Address"));
					count = rs.getInt("isComputation");
					if (count == 1)
					{
						name = "是";
						aadd.setIstotalvalue(rs1.getFloat("WaterValue"));
					} else
					{
						name = "否";
						aadd.setNototalvalue(rs1.getFloat("WaterValue"));
					}
					aadd.setIsTotal(name);
					ArcAmDetail_list.add(aadd);
					num--;
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps1,rs1);
				}

			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}

		return ArcAmDetail_list;
			}

	/**
	 * 通过起始和截止时间查询指定建筑下的电表的建筑用水明细
	 */
	public List<WaterDetailData> getWaterDtailByArc(int thePageCount, int thePageIndex, int arcID, String start, String end, String order)
			throws SQLException
			{
		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		List<WaterDetailData> ArcAmDetail_list = new ArrayList<WaterDetailData>();
		WaterDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量

		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();

		String sql = "select  " + "	a.Watermeter_ID,a.Architecture_ID,a.Watermeter_name,a.isComputation, "
				+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  "
				+ "	Watermeter_Num, Watermeter_485address,BeiLv, "
				+ "	UseAmStyle ,area_name "
				// +
				// ",max(ZVALUEZY),min(ZVALUEZY),nvl(to_char(max(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime,nvl(to_char(min(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(max(ZVALUEZY)-min(ZVALUEZY))WaterValue "
				+ "	from  " + "	Watermeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment  "
				+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID and  a.Architecture_ID =? "
				+ "				left join	area on a.area_id = area.area_id 	 "
				// +
				// "				left join  watermeterdatas on a.Watermeter_ID = watermeterdatas.Watermeter_ID    "
				// +
				// "	and watermeterdatas.valuetime between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')  "
				// +
				// "	group by a.Watermeter_ID,a.Architecture_ID,a.Watermeter_name,a.isComputation,Architecture_NAME,Partment_Name,floor, Watermeter_Num, Watermeter_485address,BeiLv,UseAmStyle ,area_name  "
				+ order;

		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, arcID);
			rs = pstmt.executeQuery();
			rs.last();
			int num = rs.getRow();
			setTotalCount(num);
			if (num <= (thePageCount * thePageIndex))
				return ArcAmDetail_list;

			num = num - thePageCount * thePageIndex;

			if (num >= thePageCount)
				num = thePageCount;

			if ((thePageCount * thePageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);

			while (rs.next() && (num > 0))
			{
				aadd = new WaterDetailData();
				int watermeterid = rs.getInt("Watermeter_ID");
				aadd.setWatermeter_ID(watermeterid);
				aadd.setWatermeter_name(rs.getString("Watermeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));
				String maxtime = null;
				String mintime = null;
				//				sql = "select max(ZVALUEZY) maxzvaluezy,min(ZVALUEZY) minzvaluezy,nvl(to_char(max(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime,nvl(to_char(min(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(max(ZVALUEZY)-min(ZVALUEZY))WaterValue "
				//						+ " from Watermeter a"
				//						+ " left join  ZWATERDATAS"
				//						+ watermeterid
				//						+ " b on b.VALUETIME between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')  " ;

				//				Connection conn1 = null;
				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) WaterValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				//System.out.println(sqllString);
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					ps1 = conn.prepareStatement(sqllString);

					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						if (rs1.getString("max_valuetime") != null)
						{
							maxtime = rs1.getString("max_valuetime");
						} else
						{
							maxtime = "";
						}
						if (rs1.getString("min_valuetime") != null)
						{
							mintime = rs1.getString("min_valuetime");
						} else
						{
							mintime = "";
						}
						aadd.setStarttime(mintime);
						aadd.setEndtime(maxtime);
						aadd.setStartvalue(rs1.getFloat("minzvaluezy"));
						aadd.setEndvalue(rs1.getFloat("maxzvaluezy"));
						aadd.setTotalvalue(rs1.getFloat("WaterValue"));
					}

					aadd.setWatermeter_num(rs.getString("Watermeter_num"));
					String Watermeter_num = aadd.getWatermeter_num();
					String Fenxiang = 'A' + "";
					if (Watermeter_num == null || Watermeter_num.length() != 16) // 如果编号为空或者长度不正常时用默认的
					{
						Fenxiang = 'A' + "";
					} else
					{
						Fenxiang = Watermeter_num.toCharArray()[13] + ""; // 如果编号正常时用指定的14位分项
					}
					aadd.setWatermeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 25));
					style = rs.getString("UseAmStyle");
					aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
					aadd.setBeiLv(rs.getInt("BeiLv"));
					aadd.setWatermeterNo(rs.getString("WaterMeter_485Address"));
					count = rs.getInt("isComputation");
					if (count == 1)
					{
						name = "是";
						aadd.setIstotalvalue(rs1.getFloat("WaterValue"));
					} else
					{
						name = "否";
						aadd.setNototalvalue(rs1.getFloat("WaterValue"));
					}
					aadd.setIsTotal(name);
					ArcAmDetail_list.add(aadd);
					num--;
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(ps1,rs1);
				}

			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}


		return ArcAmDetail_list;
			}

	public String getArchitectureName(int Architecture_id) throws SQLException
	{

		String sql = "select Architecture_Name from Architecture where Architecture_ID = ?";
		String name = null;

		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Architecture_id);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				name = rs.getString("Architecture_Name");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}
		
		return name;
	}

	public String getPartmentName(int p_id) throws SQLException
	{

		String sql = "select Partment_Name from Partment where Partment_ID = ?";
		String name = null;

		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_id);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				name = rs.getString("Partment_Name");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}
		
		return name;
	}

	public Map<Integer, String> getAllParInfo()
	{
		String sql = "select Architecture_ID,Architecture_name from Architecture";
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try
		{
			conn=ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				map.put(rs.getInt("Architecture_ID"), rs.getString("Architecture_name"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return map;
	}

	public List<WaterDetailData> exportArcWaterDetail(int arcAmDetail_ArcID, int areaID, String st, String et)
	{
		List<WaterDetailData> ArcAmDetail_list = new ArrayList<WaterDetailData>();
		WaterDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量
		String startime = st + " 00:00:00";
		String endtime = et + " 23:59:59";
		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();
		String sql ="";
		if(arcAmDetail_ArcID!=-1 && areaID!=-1){
			sql ="select distinct " + "	a.WATERMETER_ID,a.Architecture_ID,a.WATERMETER_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	WATERMETER_Num, WATERMETER_485address,BeiLv, "
					+ "	UseAmStyle ,area_name " + "	from  " + "	WATERMETER a  " + "				left join  Partment on Partment.Partment_ID = a.Partment "
					+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID  and a.Architecture_ID ="+arcAmDetail_ArcID
					+ "				left join	area on a.area_id = area.area_id  	order by Architecture_ID ,WATERMETER_name " ;
		}else if(arcAmDetail_ArcID==-1 && areaID!=-1){
			sql ="select distinct " + "	a.WATERMETER_ID,a.Architecture_ID,a.WATERMETER_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	WATERMETER_Num, WATERMETER_485address,BeiLv, "
					+ "	UseAmStyle ,area_name " + "	from  " + "	WATERMETER a  " + "				left join  Partment on Partment.Partment_ID = a.Partment "
					+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID  and a.Area_ID ="+areaID
					+ "				left join	area on a.area_id = area.area_id  	 order by Architecture_ID ,WATERMETER_name" ;
		}else{
			sql ="select distinct " + "	a.WATERMETER_ID,a.Architecture_ID,a.WATERMETER_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	WATERMETER_Num, WATERMETER_485address,BeiLv, "
					+ "	UseAmStyle ,area_name " + "	from  " + "	WATERMETER a  " + "				left join  Partment on Partment.Partment_ID = a.Partment "
					+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID "
					+ "				left join	area on a.area_id = area.area_id  	 order by Architecture_ID ,WATERMETER_name" ;
		}
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next())
			{
				aadd = new WaterDetailData();
				int watermeterid = rs.getInt("WATERMETER_ID");
				aadd.setWatermeter_ID(watermeterid);
				aadd.setWatermeter_name(rs.getString("WATERMETER_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));
				String maxtime = null;
				String mintime = null;
				//				sql = "SELECT max(ZVALUEZY) maxzvaluezy,min(ZVALUEZY) minzvaluezy,NVL(TO_CHAR(MAX(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime"
				//						+ ",NVL(TO_CHAR(MIN(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(MAX(ZVALUEZY)-MIN(ZVALUEZY))WaterValue  "
				//						+ "from ZAMDATAS"
				//						+ ammeterid
				//						+ " WHERE "
				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) WaterValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZWATERDATAS"+watermeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{

					ps1 = conn.prepareStatement(sqllString,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					//ps1.setString(1, st + " 00:00:00");
					//ps1.setString(2, et + " 23:59:59");
					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						if (rs1.getString("max_valuetime") != null)
						{
							maxtime = rs1.getString("max_valuetime");
						} else
						{
							maxtime = "";
						}
						if (rs1.getString("min_valuetime") != null)
						{
							mintime = rs1.getString("min_valuetime");
						} else
						{
							mintime = "";
						}
						aadd.setStarttime(mintime);
						aadd.setEndtime(maxtime);
						aadd.setStartvalue(rs1.getFloat("minzvaluezy"));
						aadd.setEndvalue(rs1.getFloat("maxzvaluezy"));
						aadd.setTotalvalue(rs1.getFloat("WaterValue"));
						aadd.setWatermeter_num(rs.getString("WATERMETER_num"));
						String watermeter_num = aadd.getWatermeter_num();
						String Fenxiang = 'A' + "";
						if (watermeter_num == null || watermeter_num.length() < 16)
						{
							Fenxiang = 'A' + "";
						} else
						{
							Fenxiang = watermeter_num.toCharArray()[13] + "";
						}
						aadd.setWatermeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 25));
						style = rs.getString("UseAmStyle");
						aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
						aadd.setBeiLv(rs.getInt("BeiLv"));
						aadd.setWatermeterNo(rs.getString("WATERMETER_485Address"));
						count = rs.getInt("isComputation");
						if (count == 1)
						{
							name = "是";
							aadd.setIstotalvalue(rs1.getFloat("WaterValue"));
						} else
						{
							name = "否";
							aadd.setNototalvalue(rs1.getFloat("WaterValue"));
						}
						aadd.setIsTotal(name);
						ArcAmDetail_list.add(aadd);
					}
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(ps1, rs1);
				}
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return ArcAmDetail_list;
	}

	public static void main(String[] args)
	{
		// System.out.println(new WaterDetailHelperImpl().getPartmentName(1));
	}

}
