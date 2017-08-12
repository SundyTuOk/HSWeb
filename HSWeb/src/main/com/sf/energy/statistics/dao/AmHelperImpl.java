package com.sf.energy.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.statistics.model.ArcAmDeTimeModel;
import com.sf.energy.statistics.model.ArcAmDetailData;
import com.sf.energy.util.ConnDB;

public class AmHelperImpl
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
	 * 通过区域ID和起始截止时间查询该区域下的所有电表的建筑用电明细信息
	 * 
	 * @param areaID
	 *            区域ID
	 * @param start
	 *            起始时间
	 * @param end
	 *            截止时间
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<ArcAmDetailData> getAmDtailByarea(int thePageCount, int thePageIndex, int areaID, String start, String end, String order,int userID)
			throws SQLException
			{
		/*PreparedStatement pstmt = null;
		ResultSet rs = null;*/
		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		List<ArcAmDetailData> ArcAmDetail_list = new LinkedList<ArcAmDetailData>();
		ArcAmDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量

		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();

		String sql = "select distinct " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
				+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
				+ "	UseAmStyle ,area_name "

				+ "	from  " + "	Ammeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment_ID "
				+ "				left join Architecture on a.Architecture_ID = Architecture.Architecture_ID "
				+ "				inner join	area on a.area_id = area.area_id  and a.area_id =?	 where a.PARTMENT_ID in(select Partment_ID from OPRPARTMENT_LIST where USERS_ID="+userID+") " + order;
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
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
				aadd = new ArcAmDetailData();
				int ammeterid = rs.getInt("Ammeter_ID");
				aadd.setAmmeter_ID(ammeterid);
				aadd.setAmmeter_name(rs.getString("Ammeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));

				String maxtime = null;
				String mintime = null;

				//				sql = "SELECT max(ZVALUEZY) maxzvaluezy,min(ZVALUEZY) minzvaluezy,NVL(TO_CHAR(MAX(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime"
				//						+ ",NVL(TO_CHAR(MIN(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(MAX(ZVALUEZY)-MIN(ZVALUEZY))AmValue  "
				//						+ "from ZAMDATAS"
				//						+ ammeterid
				//						+ " WHERE "
				//						+ "valuetime BETWEEN TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') AND TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') ";
				//				//				Connection conn1 = null;
				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) AmValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				//System.out.println("area:  "+sqllString);
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					//					conn1 = ConnDB.getConnection();
					ps1 = conn.prepareStatement(sqllString,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					//ps1.setString(1, start + " 00:00:00");
					//ps1.setString(2, end + " 23:59:59");
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
						aadd.setTotalvalue(rs1.getFloat("AmValue"));
						aadd.setAmmeter_num(rs.getString("Ammeter_num"));
						String Ammeter_num = aadd.getAmmeter_num();
						String Fenxiang = 'A' + "";
						if (Ammeter_num == null || Ammeter_num.length() < 16)
						{
							Fenxiang = 'A' + "";
						} else
						{
							Fenxiang = Ammeter_num.toCharArray()[13] + "";
						}

						aadd.setAmmeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 7));
						style = rs.getString("UseAmStyle");
						aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
						aadd.setBeiLv(rs.getInt("BeiLv"));
						aadd.setAmmeterNo(rs.getString("AmMeter_485Address"));
						count = rs.getInt("isComputation");
						if (count == 1)
						{
							name = "是";
							aadd.setIstotalvalue(rs1.getFloat("AmValue"));
						} else
						{
							name = "否";
							aadd.setNototalvalue(rs1.getFloat("AmValue"));
						}
						aadd.setIsTotal(name);
						ArcAmDetail_list.add(aadd);
						num--;
					}


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
			ConnDB.release(conn, ps,rs);
		}


		return ArcAmDetail_list;

			}

	/**
	 * 通过建筑ID和起始截止时间查询该区域下的所有电表的建筑用电明细信息
	 * 
	 * @param arcID
	 *            建筑ID
	 * @param start
	 *            起始时间
	 * @param end
	 *            截止时间
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<ArcAmDetailData> getAmDtailByarc(int thePageCount, int thePageIndex, int arcID, String start, String end, String order,int userID)
			throws SQLException
			{
		/*PreparedStatement pstmt = null;
		ResultSet rs = null;*/
		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		List<ArcAmDetailData> ArcAmDetail_list = new LinkedList<ArcAmDetailData>();
		ArcAmDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量

		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();

		String sql = "select distinct " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
				+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
				+ "	UseAmStyle ,area_name " + "	from  " + "	Ammeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment_ID "
				+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID  and a.Architecture_ID =?"
				+ "				left join	area on a.area_id = area.area_id  	where a.PARTMENT_ID in(select Partment_ID from OPRPARTMENT_LIST where USERS_ID="+userID+") " + order;
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, arcID);
			rs = ps.executeQuery();
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
				aadd = new ArcAmDetailData();
				int ammeterid = rs.getInt("Ammeter_ID");
				aadd.setAmmeter_ID(ammeterid);
				aadd.setAmmeter_name(rs.getString("Ammeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));

				String maxtime = null;
				String mintime = null;

				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) AmValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				//System.out.println("arc:  "+sqllString);
				//				Connection conn1 = null;
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					//					conn1 = ConnDB.getConnection();
					ps1 = conn.prepareStatement(sqllString,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					//ps1.setString(1, start + " 00:00:00");
					//ps1.setString(2, end + " 23:59:59");
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
						aadd.setTotalvalue(rs1.getFloat("AmValue"));
						aadd.setAmmeter_num(rs.getString("Ammeter_num"));
						String Ammeter_num = aadd.getAmmeter_num();
						String Fenxiang = 'A' + "";
						if (Ammeter_num == null || Ammeter_num.length() < 16)
						{
							Fenxiang = 'A' + "";
						} else
						{
							Fenxiang = Ammeter_num.toCharArray()[13] + "";
						}

						aadd.setAmmeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 7));
						style = rs.getString("UseAmStyle");
						aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
						aadd.setBeiLv(rs.getInt("BeiLv"));
						aadd.setAmmeterNo(rs.getString("AmMeter_485Address"));
						count = rs.getInt("isComputation");
						if (count == 1)
						{
							name = "是";
							aadd.setIstotalvalue(rs1.getFloat("AmValue"));
						} else
						{
							name = "否";
							aadd.setNototalvalue(rs1.getFloat("AmValue"));
						}
						aadd.setIsTotal(name);
						ArcAmDetail_list.add(aadd);
						num--;
					}


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
			ConnDB.release(conn, ps,rs);
		}


		return ArcAmDetail_list;

			}


	/**
	 * 通过建筑ID查询建筑下的所有电表ID
	 * 
	 * @param arcID
	 *            建筑ID
	 * @return 电表ID
	 * @throws SQLException
	 */
	public List<Integer> getamIDbyArchitecture(int arcID) throws SQLException
	{
		List<Integer> list = null;
		int amID = 0;

		String sql = "select Ammeter_ID from  Ammeter where Architecture_ID = ?";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, arcID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				list = new ArrayList<Integer>();
				amID = rs.getInt("Ammeter_ID");
				list.add(amID);
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
	 * 通过建筑ID获取建筑的名字
	 * 
	 * @throws SQLException
	 */
	public String getArchitectureName(int Architecture_id) throws SQLException
	{

		String sql = "select Architecture_Name from Architecture where Architecture_ID = ?";
		String name = null;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Architecture_id);
			rs = ps.executeQuery();
			if (rs.next())
			{
				name = rs.getString("Architecture_Name");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return name;
	}

	/**
	 * 通过部门ID获取部门名字
	 * 
	 * @throws SQLException
	 */
	public String getPartmentName(int p_id) throws SQLException
	{
		String sql = "select Partment_Name from Partment where Partment_ID = ?";
		String name = null;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, p_id);
			rs = ps.executeQuery();
			if (rs.next())
			{
				name = rs.getString("Partment_Name");
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return name;
	}

	/**
	 * 通过部门ID，起始和截止时间获取部门用电明细的详细信息
	 */
	public List<ArcAmDetailData> getAmDetailBetweenByPartment(int thePageCount, int thePageIndex, int partment_id, String start, String end,
			String order) throws SQLException
			{
		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		List<ArcAmDetailData> ArcAmDetail_list = new LinkedList<ArcAmDetailData>();
		ArcAmDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量

		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();

		String sql = "select  " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
				+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
				+ "	UseAmStyle ,area_name " + "	from  " + "	Ammeter a  "
				+ "				inner join  Partment on Partment.Partment_ID = a.Partment_ID AND (Partment.Partment_ID in (select PARTMENT_ID from PARTMENT START WITH Partment.partment_parent=? CONNECT by PRIOR Partment.partment_id=Partment.partment_parent) or Partment.partment_id=?  )"
				+ "				left join Architecture on a.Architecture_ID = Architecture.Architecture_ID "
				+ "				left join	area on a.area_id = area.area_id	 " + order;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, partment_id);
			ps.setInt(2, partment_id);
			rs = ps.executeQuery();
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
				aadd = new ArcAmDetailData();
				int ammeterid = rs.getInt("Ammeter_ID");
				aadd.setAmmeter_ID(ammeterid);
				aadd.setAmmeter_name(rs.getString("Ammeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));

				String maxtime = null;
				String mintime = null;

				//				sql = "SELECT max(ZVALUEZY) maxzvaluezy,min(ZVALUEZY) minzvaluezy,NVL(TO_CHAR(MAX(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime"
				//						+ ",NVL(TO_CHAR(MIN(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(MAX(ZVALUEZY)-MIN(ZVALUEZY))AmValue  "
				//						+ "from ZAMDATAS"
				//						+ ammeterid
				//						+ " WHERE "
				//						+ "valuetime BETWEEN TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') AND TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') ";
				//				//				Connection conn1 = null;
				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) AmValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					//					conn1 = ConnDB.getConnection();
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
						aadd.setTotalvalue(rs1.getFloat("AmValue"));
					}
					aadd.setAmmeter_num(rs.getString("Ammeter_num"));
					String Ammeter_num = aadd.getAmmeter_num();
					String Fenxiang = 'A' + "";
					if (Ammeter_num == null || Ammeter_num.length() < 16)
					{
						Fenxiang = 'A' + "";
					} else
					{
						Fenxiang = Ammeter_num.toCharArray()[13] + "";
					}

					aadd.setAmmeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 7));
					style = rs.getString("UseAmStyle");
					aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
					aadd.setBeiLv(rs.getInt("BeiLv"));
					aadd.setAmmeterNo(rs.getString("AmMeter_485Address"));
					count = rs.getInt("isComputation");
					if (count == 1)
					{
						name = "是";
						aadd.setIstotalvalue(rs1.getFloat("AmValue"));
					} else
					{
						name = "否";
						aadd.setNototalvalue(rs1.getFloat("AmValue"));
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
			ConnDB.release(conn, ps,rs);
		}
		return ArcAmDetail_list;

			}

	/**
	 * 通过起始和截止时间查询所有部门下的电表的建筑用电明细
	 */
	public List<ArcAmDetailData> getAmDtailAllPartment(int thePageCount, int thePageIndex, String start, String end, String order,int userID)
			throws SQLException
			{
		List<ArcAmDetailData> ArcAmDetail_list = new LinkedList<ArcAmDetailData>();
		ArcAmDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量
		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		// //System.out.println("1:"+new Date());
		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();

		String sql1 = "SELECT T.Ammeter_ID,T.Architecture_ID,T.Ammeter_name,T.isComputation,T.Architecture_NAME"
				+ ",NVL(T.Partment_Name,'-')Partment_Name,T.floor,T.Ammeter_Num,T.Ammeter_485address,T.BeiLv,T.UseAmStyle"
				+ ",NVL(T.area_name,'-')area_name"
				+ " FROM (SELECT  a.Ammeter_ID,Architecture_ID,Ammeter_name,isComputation, (SELECT Architecture_NAME FROM"
				+ " ARCHITECTURE WHERE a.Architecture_ID = Architecture.Architecture_ID  )Architecture_NAME, "
				+ "(SELECT NVL(Partment_Name,'-') FROM PARTMENT where Partment.Partment_ID = a.Partment_ID   )Partment_Name, "
				+ "floor,Ammeter_Num,Ammeter_485address,BeiLv,UseAmStyle, "
				+ "(SELECT NVL(area_name,'-') FROM area where a.area_id = area.area_id)area_name " + "FROM " + "Ammeter a where a.PARTMENT_ID in(select Partment_ID from OPRPARTMENT_LIST where USERS_ID="+userID+"))T  ";

		String sql = sql1 + order;
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			// //System.out.println("3:"+new Date());
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
				aadd = new ArcAmDetailData();
				int ammeterid = rs.getInt("Ammeter_ID");
				aadd.setAmmeter_ID(ammeterid);
				aadd.setAmmeter_name(rs.getString("Ammeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));

				String maxtime = null;
				String mintime = null;

				//				sql = "SELECT max(ZVALUEZY) maxzvaluezy,min(ZVALUEZY) minzvaluezy,NVL(TO_CHAR(MAX(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime"
				//						+ ",NVL(TO_CHAR(MIN(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(MAX(ZVALUEZY)-MIN(ZVALUEZY))AmValue  "
				//						+ "from ZAMDATAS"
				//						+ ammeterid
				//						+ " WHERE "
				//						+ "valuetime BETWEEN TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') AND TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') ";

				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) AmValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC) WHERE ROWNUM = 1)"
						+")))";
				//				Connection conn1 = null;
				////System.out.println("From ZAMDATA:"+sql);
				//System.out.println("All:  "+sqllString);
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					//					conn1 = ConnDB.getConnection();
					ps1 = conn.prepareStatement(sqllString,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					//ps1.setString(1, start + " 00:00:00");
					//ps1.setString(2, end + " 23:59:59");

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
						aadd.setTotalvalue(rs1.getFloat("AmValue"));
					}
					aadd.setAmmeter_num(rs.getString("Ammeter_num"));
					String Ammeter_num = aadd.getAmmeter_num();
					String Fenxiang = 'A' + "";
					if (Ammeter_num == null || Ammeter_num.length() < 16)
					{
						Fenxiang = 'A' + "";
					} else
					{
						Fenxiang = Ammeter_num.toCharArray()[13] + "";
					}

					aadd.setAmmeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 7));
					style = rs.getString("UseAmStyle");
					aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
					aadd.setBeiLv(rs.getInt("BeiLv"));
					aadd.setAmmeterNo(rs.getString("AmMeter_485Address"));
					count = rs.getInt("isComputation");
					if (count == 1)
					{
						name = "是";
						aadd.setIstotalvalue(rs1.getFloat("AmValue"));
					} else
					{
						name = "否";
						aadd.setNototalvalue(rs1.getFloat("AmValue"));
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
			ConnDB.release(conn, ps,rs);
		}

		return ArcAmDetail_list;

			}

	public Map<Integer, String> getAllParInfo() throws SQLException
	{
		String sql = "select Architecture_ID,Architecture_name from Architecture";
		Map<Integer, String> map = new HashMap<Integer, String>();
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				map.put(rs.getInt("Architecture_ID"), rs.getString("Architecture_name"));
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return map;
	}

	public List<ArcAmDetailData> exportArcAmDetail(int arcAmDetail_ArcID, int areaID, String st, String et)
	{
		List<ArcAmDetailData> ArcAmDetail_list = new LinkedList<ArcAmDetailData>();
		ArcAmDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量
		String startime = st + " 00:00:00";
		String endtime = et + " 23:59:59";
		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();
		String sql ="";
		if(arcAmDetail_ArcID!=-1 && areaID!=-1){
			sql ="select distinct " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
					+ "	UseAmStyle ,area_name " + "	from  " + "	Ammeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment_ID "
					+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID  and a.Architecture_ID ="+arcAmDetail_ArcID
					+ "				left join	area on a.area_id = area.area_id  	order by Architecture_ID ,ammeter_name " ;
		}else if(arcAmDetail_ArcID==-1 && areaID!=-1){
			sql ="select distinct " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
					+ "	UseAmStyle ,area_name " + "	from  " + "	Ammeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment_ID "
					+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID  and a.Area_ID ="+areaID
					+ "				left join	area on a.area_id = area.area_id  	 order by Architecture_ID ,ammeter_name" ;
		}else{
			sql ="select distinct " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
					+ "	UseAmStyle ,area_name " + "	from  " + "	Ammeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment_ID "
					+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID "
					+ "				left join	area on a.area_id = area.area_id  	 order by Architecture_ID ,ammeter_name" ;
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
				aadd = new ArcAmDetailData();
				int ammeterid = rs.getInt("Ammeter_ID");
				aadd.setAmmeter_ID(ammeterid);
				aadd.setAmmeter_name(rs.getString("Ammeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));
				String maxtime = null;
				String mintime = null;
				//				sql = "SELECT max(ZVALUEZY) maxzvaluezy,min(ZVALUEZY) minzvaluezy,NVL(TO_CHAR(MAX(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime"
				//						+ ",NVL(TO_CHAR(MIN(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(MAX(ZVALUEZY)-MIN(ZVALUEZY))AmValue  "
				//						+ "from ZAMDATAS"
				//						+ ammeterid
				//						+ " WHERE "
				//						+ "valuetime BETWEEN TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') AND TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') ";
				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) AmValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				//System.out.println(sqllString);
				//				sql = "select max(ZVALUEZY) maxzvaluezy,min(ZVALUEZY) minzvaluezy,nvl(to_char(max(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')max_valuetime"
				//						+ ",nvl(to_char(min(valuetime),'yyyy-mm-dd hh24:mi:ss'),'-')min_valuetime,(max(ZVALUEZY)-min(ZVALUEZY))AmValue "
				//						+ " from Ammeter a"
				//						+ " left join  ZAMDATAS"
				//						+ ammeterid
				//						+ " b on b.VALUETIME between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')  ";
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
						aadd.setTotalvalue(rs1.getFloat("AmValue"));
						aadd.setAmmeter_num(rs.getString("Ammeter_num"));
						String Ammeter_num = aadd.getAmmeter_num();
						String Fenxiang = 'A' + "";
						if (Ammeter_num == null || Ammeter_num.length() < 16)
						{
							Fenxiang = 'A' + "";
						} else
						{
							Fenxiang = Ammeter_num.toCharArray()[13] + "";
						}
						aadd.setAmmeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 7));
						style = rs.getString("UseAmStyle");
						aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
						aadd.setBeiLv(rs.getInt("BeiLv"));
						aadd.setAmmeterNo(rs.getString("AmMeter_485Address"));
						count = rs.getInt("isComputation");
						if (count == 1)
						{
							name = "是";
							aadd.setIstotalvalue(rs1.getFloat("AmValue"));
						} else
						{
							name = "否";
							aadd.setNototalvalue(rs1.getFloat("AmValue"));
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
}
