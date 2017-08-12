package com.sf.energy.publicity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.sf.energy.util.ConnDB;



public class publicityDao
{
	public List<JSONObject> getArchStyleInfo() throws JSONException{
		List<JSONObject> arr = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select DICTIONARYVALUE_NUM num,DICTIONARYVALUE_VALUE value,'<li>'||DICTIONARYVALUE_VALUE||'</li>' title from DictionaryValue where Dictionary_ID=3";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				if(arr==null){
					arr = new ArrayList<JSONObject>();
				}
				JSONObject obj = new JSONObject();
				obj.put("num", rs.getString("num"));
				obj.put("value", rs.getString("value"));
				obj.put("title", rs.getString("title"));
				arr.add(obj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return arr;
	}
	public String getArchEnergyByStyle(String style){
		StringBuffer sBuffer = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT "
				+" '<tr><td>'||Architecture_Name||'</td><td>'||TheMonthAm||'</td><td>'||TheYearAm||'</td><td>'||TheMonthWater||'</td><td>'||TheYearWater||'</td><td>-</td><td>-</td></tr>'  title"
				+" FROM"
				+"	("
				+"		SELECT"
				+"			A .Architecture_ID,"
				+"			Architecture_Name,"
				+"			NVL (TheMonthAm, 0) TheMonthAm,"
				+"			NVL (TheYearAm, 0) TheYearAm,"
				+"			NVL (TheMonthWater, 0) TheMonthWater,"
				+"			NVL (TheYearWater, 0) TheYearWater"
				+"		FROM"
				+"			("
				+"				SELECT"
				+"					Architecture_ID,"
				+"					Architecture_Name"
				+"				FROM"
				+"					Architecture"
				+"				WHERE"
				+"					Architecture_Style = ?"
				+"			) A"
				+"		LEFT JOIN ("
				+"			SELECT"
				+"				Architecture_ID,"
				+"				SUM (ZGross) TheMonthAm"
				+"			FROM"
				+"				T_ArcDayAm"
				+"			WHERE"
				+"				TO_DATE ("
				+"					SelectYear || '-' || SelectMonth,"
				+"					'yyyy-mm'"
				+"				) = TO_DATE ("
				+"					TO_CHAR (SYSDATE, 'yyyy-mm'),"
				+"					'yyyy-mm'"
				+"				) "
				+"			GROUP BY"
				+"				Architecture_ID"
				+"		) b ON A .Architecture_ID = b.Architecture_ID"
				+"		LEFT JOIN ("
				+"			SELECT"
				+"				Architecture_ID,"
				+"				SUM (ZGross) TheYearAm"
				+"			FROM"
				+"				T_ArcDayAm"
				+"			WHERE"
				+"				TO_DATE (SelectYear, 'yyyy') = TO_DATE ("
				+"					TO_CHAR (SYSDATE, 'yyyy'),"
				+"					'yyyy'"
				+"				) "
				+"			GROUP BY"
				+"				Architecture_ID"
				+"		) c ON A .Architecture_ID = c.Architecture_ID"
				+"		LEFT JOIN ("
				+"			SELECT"
				+"				Architecture_ID,"
				+"				SUM (ZGross) TheMonthWater"
				+"			FROM"
				+"				T_ArcDayWater"
				+"			WHERE"
				+"				TO_DATE ("
				+"					SelectYear || '-' || SelectMonth,"
				+"					'yyyy-mm'"
				+"				) = TO_DATE ("
				+"					TO_CHAR (SYSDATE, 'yyyy-mm'),"
				+"					'yyyy-mm'"
				+"				)" 
				+"			GROUP BY"
				+"				Architecture_ID"
				+"		) D ON A .Architecture_ID = D .Architecture_ID"
				+"		LEFT JOIN ("
				+"			SELECT"
				+"				Architecture_ID,"
				+"				SUM (ZGross) TheYearWater"
				+"			FROM"
				+"				T_ArcDayWater"
				+"			WHERE"
				+"				TO_DATE (SelectYear, 'yyyy') = TO_DATE ("
				+"					TO_CHAR (SYSDATE, 'yyyy'),"
				+"					'yyyy'"
				+"				)" 
				+"			GROUP BY"
				+"				Architecture_ID"
				+"		) E ON A .Architecture_ID = E .Architecture_ID"
				+"	) order by Architecture_Name";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, style);
			rs = ps.executeQuery();
			while(rs.next()){
				sBuffer.append(rs.getString(1));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return sBuffer.toString();
	}
	public String getArchFenxiangEnergy(){
		StringBuffer sBuffer = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select '<tr><td>'||Architecture_Name||'</td><td>'||ZAG||'</td><td>'||ZWG||'</td><td>'||ZG1||'</td><td>'||ZG2||'</td><td>'||ZG3||'</td><td>'||ZG4||'</td><td>'||ZG5||'</td></tr>' title"
				+" FROM (SELECT	A .Architecture_ID,	A.Architecture_Area,	A.count_man,	A.Architecture_Name,"
				+"	NVL (T1.ZG, 0) AS ZG1,	NVL (T2.ZG, 0) AS ZG2,	NVL (T3.ZG, 0) AS ZG3,	NVL (T4.ZG, 0) AS ZG4,"
				+"	NVL (T5.ZG, 0) AS ZG5,	NVL (ZA.ZG, 0) AS ZAG,	NVL (ZW.ZG, 0) AS ZWG"
				+" FROM	(select * from Architecture) A"
				+" LEFT JOIN ("
				+"	SELECT Architecture_ID,	SUM (zgross) ZG"
				+"	FROM T_ArcStyleDayAm"
				+"	WHERE TO_DATE (SelectYear || '-' || SelectMonth,'yyyy-mm') = TO_DATE (TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm')"
				+"	AND useAmStyle = 1"
				+"	GROUP BY Architecture_ID) T1 ON A .Architecture_ID = T1.Architecture_ID"
				+" LEFT JOIN ("
				+ "SELECT Architecture_ID,SUM (zgross) ZG "
				+ " FROM T_ArcStyleDayAm "
				+ "WHERE TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'), 'yyyy-mm' ) "
				+ "AND useAmStyle = 2 GROUP BY Architecture_ID) T2 ON A .Architecture_ID = T2.Architecture_ID "
				+ "LEFT JOIN ("
				+ "SELECT Architecture_ID, SUM (zgross) ZG "
				+ " FROM T_ArcStyleDayAm "
				+ "WHERE TO_DATE (SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm') "
				+ "AND useAmStyle = 3 GROUP BY Architecture_ID "
				+ ") T3 ON A .Architecture_ID = T3.Architecture_ID "
				+ "LEFT JOIN ( "
				+ "SELECT Architecture_ID, SUM (zgross) ZG "
				+ " FROM T_ArcStyleDayAm "
				+ "WHERE TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm') "
				+ "AND useAmStyle = 4 GROUP BY Architecture_ID "
				+ ") T4 ON A .Architecture_ID = T4.Architecture_ID "
				+ "LEFT JOIN ( "
				+ "SELECT Architecture_ID, SUM (zgross) ZG "
				+ "FROM T_ArcStyleDayAm "
				+ "WHERE TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm') "
				+ "AND useAmStyle = 5 GROUP BY Architecture_ID "
				+ ") T5 ON A .Architecture_ID = T5.Architecture_ID "
				+ "LEFT JOIN ("
				+ "SELECT Architecture_ID, SUM (zgross) ZG "
				+ "FROM T_ArcDayAm "
				+ "WHERE TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm' ) "
				+ "GROUP BY Architecture_ID "
				+ ") ZA ON A .Architecture_ID = ZA.Architecture_ID "
				+ "LEFT JOIN ( "
				+ "SELECT Architecture_ID, SUM (zgross) ZG "
				+ "FROM T_ArcDayWater "
				+ "WHERE TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm' ) "
				+ "GROUP BY Architecture_ID "
				+ ") ZW ON A .Architecture_ID = ZW.Architecture_ID) ORDER BY ARCHITECTURE_name ";
		//System.out.println(sql);
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				sBuffer.append(rs.getString(1));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return sBuffer.toString();

	}
	/**
	 * 获取建筑平均能耗和水耗
	 * @return [0]：能耗 [1]：水耗
	 */
	public String[] getArchAverage(){
		StringBuffer sBufferAm = new StringBuffer();
		StringBuffer sBufferWm = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT '<tr><td>'||Architecture_Name||'</td><td>'||Architecture_Area||'</td><td>'||count_man||'</td><td>'||ZAG||'</td><td>'||MJA||'</td><td>'||RJA||'</td></tr>' am,"
				+ " '<tr><td>'||Architecture_Name||'</td><td>'||Architecture_Area||'</td><td>'||count_man||'</td><td>'||ZWG||'</td><td>'||MJW||'</td><td>'||RJW||'</td></tr>' wm"
				+ " FROM (SELECT Architecture_ID, Architecture_Area, count_man, Architecture_Name, ZAG, ZWG,"
				+ " ROUND (ZAG / Architecture_Area, 2) MJA, ROUND (ZWG / Architecture_Area, 2) MJW, ROUND (ZAG / count_man, 2) RJA,ROUND (ZWG / count_man, 2) RJW "
				+ " FROM ( "
				+ " SELECT A .Architecture_ID, A .Architecture_Area, A .count_man, A .Architecture_Name, NVL (ZA.ZG, 0) AS ZAG,NVL (ZW.ZG, 0) AS ZWG "
				+ " FROM ( (SELECT * FROM Architecture) A LEFT JOIN ( SELECT Architecture_ID, SUM (zgross) ZG FROM T_ArcDayAm "
				+ " WHERE TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm') "
				+ " GROUP BY Architecture_ID ) ZA ON A .Architecture_ID = ZA.Architecture_ID "
				+ " LEFT JOIN ( SELECT Architecture_ID, SUM (zgross) ZG FROM T_ArcDayWater "
				+ " WHERE TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm' ) "
				+ " GROUP BY Architecture_ID ) ZW ON A .Architecture_ID = ZW.Architecture_ID ) ) T) ORDER BY Architecture_Name";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				sBufferAm.append(rs.getString(1));
				sBufferWm.append(rs.getString(2));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		String arr[] = {sBufferAm.toString(),sBufferWm.toString()};
		return arr;
	}
	public String getPartmentFenxiangEnergy(){
		StringBuffer sBuffer = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT"
				+ " '<tr><td>'||Partment_Name||'</td><td>'||ZAG||'</td><td>'||ZWG||'</td><td>'||ZG1||'</td><td>'||ZG2||'</td><td>'||ZG3||'</td><td>'||ZG4||'</td><td>'||ZG5||'</td></tr>' title "
				+ "FROM(SELECT A .Partment_ID, A .Partment_Name, NVL (T1.ZG, 0) AS ZG1, NVL (T2.ZG, 0) AS ZG2, "
				+ "NVL (T3.ZG, 0) AS ZG3, NVL (T4.ZG, 0) AS ZG4, NVL (T5.ZG, 0) AS ZG5, NVL (ZA.ZG, 0) AS ZAG, NVL (ZW.ZG, 0) AS ZWG "
				+ "FROM ( (SELECT * FROM Partment) A LEFT JOIN ( SELECT Partment_ID, SUM (zgross) ZG FROM T_PartmentStyleDayAm "
				+ "WHERE TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm')AND useAmStyle = 1 "
				+ "GROUP BY Partment_ID ) T1 ON A .Partment_ID = T1.Partment_ID LEFT JOIN ( SELECT Partment_ID, SUM (zgross) ZG "
				+ "FROM T_PartmentStyleDayAm WHERE TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm') "
				+ "AND useAmStyle = 2 GROUP BY Partment_ID ) T2 ON A .Partment_ID = T2.Partment_ID LEFT JOIN ( "
				+ "SELECT Partment_ID, SUM (zgross) ZG FROM T_PartmentStyleDayAm WHERE TO_DATE ( "
				+ "SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'), 'yyyy-mm') "
				+ "AND useAmStyle = 3 GROUP BY Partment_ID ) T3 ON A .Partment_ID = T3.Partment_ID LEFT JOIN ( "
				+ "SELECT Partment_ID, SUM (zgross) ZG FROM T_PartmentStyleDayAm WHERE "
				+ "TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm') "
				+ "AND useAmStyle = 4 GROUP BY Partment_ID ) T4 ON A .Partment_ID = T4.Partment_ID LEFT JOIN ( "
				+ "SELECT Partment_ID, SUM (zgross) ZG FROM T_PartmentStyleDayAm WHERE "
				+ "TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm') "
				+ "AND useAmStyle = 5 GROUP BY Partment_ID ) T5 ON A .Partment_ID = T5.Partment_ID "
				+ "LEFT JOIN ( SELECT Partment_ID, SUM (zgross) ZG FROM T_PartmentDayAm "
				+ "WHERE TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm' ) = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm' ) "
				+ "GROUP BY Partment_ID ) ZA ON A .Partment_ID = ZA.Partment_ID LEFT JOIN ( SELECT "
				+ "Partment_ID, SUM (zgross) ZG FROM T_PartmentDayWater WHERE "
				+ "TO_DATE ( SelectYear || '-' || SelectMonth, 'yyyy-mm') = TO_DATE ( TO_CHAR (SYSDATE, 'yyyy-mm'),'yyyy-mm' ) "
				+ "GROUP BY Partment_ID ) ZW ON A .Partment_ID = ZW.Partment_ID ) )ORDER BY PARTMENT_name ";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				sBuffer.append(rs.getString(1));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return sBuffer.toString();

	}
	public static void main(String[] args) {
		int a = 50;
		int b = 28;
		System.out.println(Integer.toBinaryString(a));
		System.out.println(Integer.toBinaryString(b));
	}
}
