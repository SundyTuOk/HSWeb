package com.sf.energy.project.system.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.sf.energy.manager.current.model.ErrorDataModel;
import com.sf.energy.manager.current.servlet.AmErrorDataManage;
import com.sf.energy.project.system.model.BaojingModel;
import com.sf.energy.util.ConnDB;
/**
 *  工程管理用
 * @author Administrator
 *
 */
public class projectBaojingDao
{
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public boolean insertBaojingInfo(String time,String style,String fl,String index,String title,String num){
		String sql = "insert into BaojingInfo(BaojingInfo_Time,BaojingInfo_Style,BaojingInfo_FL,BaojingInfo_Index,BaojingInfo_Title,Module_Num)";
		sql += "values(TO_DATE( ? , 'yyyy-mm-dd hh24:mi:ss'), ?, ?, ? , ? ,?)";
		Connection conn= null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, time);
			ps.setString(2, style);
			ps.setString(3, fl);
			ps.setString(4, index);
			ps.setString(5, title);
			ps.setString(6, num);
			b = (ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	public boolean insertBaojingInfo(BaojingModel model){
		String sql = "insert into BaojingInfo(BaojingInfo_Time,BaojingInfo_Style,BaojingInfo_FL,BaojingInfo_Index,BaojingInfo_Title,Module_Num)";
		sql += "values(TO_DATE( ? , 'yyyy-mm-dd hh24:mi:ss'), ?, ?, ? , ? ,?)";
		Connection conn= null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, model.getBaojingInfo_Time());
			ps.setString(2, model.getBaojingInfo_Style());
			ps.setString(3, model.getBaojingInfo_FL());
			ps.setString(4, model.getBaojingInfo_Index());
			ps.setString(5, model.getBaojingInfo_Title());
			ps.setString(6, model.getModule_Num());
			b = (ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	public int getCount(String startTime,String endTime,String style){
		int count = 0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String condition = buildQueryConditionSql(startTime, endTime, style);
		String sql = "SELECT count(baojinginfo_id) FROM baojinginfo "+condition;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				count = rs.getInt(1);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return count; 
	}

	public List<BaojingModel> queryAllBaojingInfo(int offset,int limit,String startTime,String endTime,String style){
		List<BaojingModel> list =null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String condition = buildQueryConditionSql(startTime, endTime, style);
		try
		{		
			String sql ="SELECT BAOJINGINFO_ID, BAOJINGINFO_TIME, BAOJINGINFO_STYLE, BAOJINGINFO_FL, BAOJINGINFO_INDEX, BAOJINGINFO_TITLE, MODULE_NUM from "
					+ "(SELECT ROWNUM rowno,t.* FROM (SELECT BAOJINGINFO_ID, BAOJINGINFO_TIME, BAOJINGINFO_STYLE, BAOJINGINFO_FL, BAOJINGINFO_INDEX, BAOJINGINFO_TITLE, MODULE_NUM "
					+ "FROM baojingInfo  "+condition+" ORDER BY BAOJINGINFO_TIME desc) t WHERE ROWNUM<="+(limit+offset)
					+ ") where rowno>"+offset;
			//System.out.println(sql);
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if(list==null){
					list =  new ArrayList<BaojingModel>();
				}
				list.add(buildInstance(rs));
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

	public List<BaojingModel> queryAllBaojingInfo(String startTime,String endTime,String style){
		List<BaojingModel> list =new ArrayList<BaojingModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String condition = buildQueryConditionSql(startTime, endTime, style);
		try
		{		
		
			String sql = "SELECT BAOJINGINFO_ID, BAOJINGINFO_TIME, BAOJINGINFO_STYLE, BAOJINGINFO_FL, BAOJINGINFO_INDEX, BAOJINGINFO_TITLE, MODULE_NUM,METER_NAME,AMMETER_485ADDRESS FROM baojingInfo b LEFT JOIN V_AMMETER v ON b.BAOJINGINFO_INDEX=v.AMMETER_ID "+condition+" ORDER BY BAOJINGINFO_TIME desc";
			//System.out.println(sql);
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				list.add(buildExportInstance(rs));
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
	
	private String buildQueryConditionSql(String startTime,String endTime,String style){
		String sql = "";
		if(!startTime.equals("")&&!endTime.equals("")){
			sql+=" where BAOJINGINFO_TIME between TO_DATE( '"+startTime+"' , 'yyyy-mm-dd hh24:mi:ss') "
					+ " and TO_DATE( '"+endTime+"' , 'yyyy-mm-dd hh24:mi:ss')";
			if(!style.equals("0")){
				sql+=" and BAOJINGINFO_STYLE='"+style+"'";
			}
		}
		else{
			if(!style.equals("0")){
				sql+="where BAOJINGINFO_STYLE='"+style+"'";
			}
		}
		return sql;
	}

	public List<String> getBaojingStyle(){
		List<String> list =null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{		
			String sql ="SELECT DISTINCT(BAOJINGINFO_STYLE) FROM BAOJINGINFO ORDER BY BAOJINGINFO_STYLE ASC";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if(list==null){
					list =  new ArrayList<String>();
				}
				list.add(rs.getString(1));
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

	
	
	public int getEQCount(String startTime,String endTime,int errorCode){
		int count = 0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String condition = buildEQQueryConditionSql(startTime, endTime, errorCode);
		String sql = "SELECT count(*) FROM AMMETERERRORDATAS "+condition;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				count = rs.getInt(1);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return count; 
	}
	
	//获取异常数据
	public List<JSONObject> queryAllEQBaojingInfo(int offset,int limit,String startTime,String endTime,int errorCode){
		List<JSONObject> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String joinSql = "";
		if(errorCode<100){
			joinSql = " (SELECT AMMETER_ID,AMMETER_NAME METERNAME,AREA_NAME,ARCHITECTURE_NAME FROM V_AMMETER)a ON a.AMMETER_ID= tt.AMMETER_ID";
		}else{
			joinSql = " (SELECT WATERMETER_ID,WATERMETER_NAME METERNAME,AREA_NAME,ARCHITECTURE_NAME FROM V_WATERMETER)a ON a.WATERMETER_ID= tt.AMMETER_ID";
		}
		String condition = buildEQQueryConditionSql(startTime, endTime, errorCode);
		try
		{		
			String sql = "SELECT * FROM (SELECT * from (SELECT t.*,ROWNUM ro FROM (SELECT AMMETER_ID,VALUETIME,ZVALUEZY,ERRORCODE FROM AMMETERERRORDATAS "+condition+" ORDER BY VALUETIME DESC) t WHERE ROWNUM<="+(limit+offset)+") WHERE ro>"+offset+") tt LEFT JOIN"
					+ joinSql;
			//System.out.println(sql);
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if(list==null){
					list =  new ArrayList<JSONObject>();
				}
				list.add(buildInstance1(rs));
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
	
	private String buildEQQueryConditionSql(String startTime,String endTime,int errorCode){
		String sql = "";
		if(!startTime.equals("")&&!endTime.equals("")){
			sql+=" where valuetime between TO_DATE( '"+startTime+"' , 'yyyy-mm-dd hh24:mi:ss') "
					+ " and TO_DATE( '"+endTime+"' , 'yyyy-mm-dd hh24:mi:ss')";

			sql+=" and errorCode='"+errorCode+"'";

		}
		else{

			sql+=" errorCode='"+errorCode+"'";

		}
		return sql;
	}
	private JSONObject buildInstance1(ResultSet rs) throws SQLException{
		JSONObject model = new JSONObject();
		model.put("METERNAME", rs.getString("METERNAME"));
		model.put("AREA_NAME", rs.getString("AREA_NAME"));
		model.put("ARCHITECTURE_NAME", rs.getString("ARCHITECTURE_NAME"));
		model.put("VALUETIME", df.format(rs.getTimestamp("VALUETIME")));
		model.put("ZVALUEZY", rs.getString("ZVALUEZY"));
		model.put("ERRORCODE", rs.getString("ERRORCODE"));
		return model;
	}
	
	private BaojingModel buildInstance(ResultSet rs) throws SQLException{
		BaojingModel model = new BaojingModel();
		model.setBaojingInfo_Id(rs.getInt("BAOJINGINFO_ID"));
		model.setBaojingInfo_Time(df.format(rs.getTimestamp("BAOJINGINFO_TIME")));
		model.setBaojingInfo_Style(rs.getString("BAOJINGINFO_STYLE"));
		model.setBaojingInfo_FL(rs.getString("BAOJINGINFO_FL"));
		model.setBaojingInfo_Index(rs.getString("BAOJINGINFO_INDEX"));
		model.setBaojingInfo_Title(rs.getString("BAOJINGINFO_TITLE"));
		model.setModule_Num(rs.getString("MODULE_NUM"));
		return model;
	}
	
	private BaojingModel buildExportInstance(ResultSet rs) throws SQLException{
		BaojingModel model = new BaojingModel();
		model.setBaojingInfo_Id(rs.getInt("BAOJINGINFO_ID"));
		model.setBaojingInfo_Time(df.format(rs.getTimestamp("BAOJINGINFO_TIME")));
		model.setBaojingInfo_Style(rs.getString("BAOJINGINFO_STYLE"));
		model.setBaojingInfo_FL(rs.getString("BAOJINGINFO_FL"));
		model.setBaojingInfo_Index(rs.getString("BAOJINGINFO_INDEX"));
		model.setBaojingInfo_Title(rs.getString("BAOJINGINFO_TITLE"));
		model.setModule_Num(rs.getString("MODULE_NUM"));
		model.setMeter_Name(rs.getString("meter_Name"));
		model.setMeter_485Address(rs.getString("AMMETER_485ADDRESS"));
		return model;
	}
}
