package com.sf.energy.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.util.ConnDB;

/**
 * 其他信息管理
 * @author ky
 *
 */
public class JcOrtherMenage
{
	private String ConnString = "";
	public JcOrtherMenage(String strcon)
	{
		ConnString = strcon;
	}

	/**
	 * 获取列表的相关列
	 * @param ColumnInfo_Table 要获取列的表名
	 * @return
	 */
	public ResultSet GetLiebiaoInfo(String ColumnInfo_Table)
	{
		String sql="select ColumnInfo_ID,ColumnInfo_name,ColumnInfo_title,ColumnInfo_shunxu,ColumnInfo_disable,ColumnInfo_type from ColumnInfo where ColumnInfo_Table='" + ColumnInfo_Table + "' order by ColumnInfo_shunxu";
		ResultSet rs = null;
		PreparedStatement ps =null;
		Connection conn =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return rs;
	}

	/**
	 * 获取列表的相关列
	 * @param ColumnInfo_Table 要获取列的表名
	 * @return
	 */   
	public ResultSet GetLiebiaoSelectInfo(String ColumnInfo_Table)
	{
		String sql="select ColumnInfo_ID,ColumnInfo_name,ColumnInfo_title,ColumnInfo_shunxu,ColumnInfo_disable,ColumnInfo_type from ColumnInfo where ColumnInfo_disable=1 and ColumnInfo_Table='" + ColumnInfo_Table + "' order by ColumnInfo_shunxu";
		ResultSet rs = null;
		PreparedStatement ps =null;
		Connection conn =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}  
		return rs;
	}
	/**
	 * 根据TheTime查询Information的信息
	 * @param TheTime
	 * @return
	 * @throws SQLException
	 */
	public ResultSet GetInformation(String TheTime) throws SQLException
	{
		String sql="select Information_ID from Information where TheTime='"+TheTime+"'";
		ResultSet rs1=null;
		PreparedStatement ps1 = null;
		Connection conn1 =null;

		ResultSet rs = null;
		PreparedStatement ps =null;
		Connection conn =null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 =conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			rs1.last();

			if(rs1.getRow()!=0){
				sql="select * from Information where TheTime='"+TheTime+"'";
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn, ps);
				}  
			}
			else{
				sql="insert into Information(TDArea,JZArea,LHArea,ManCount,TheTime) value(select TDArea,JZArea,LHArea,ManCount,TheTime from Information where TheTime='"+(Integer.parseInt(TheTime)-1)+"'";
				PreparedStatement ps0 =null;
				Connection conn0 =null;
				try
				{
					conn0 = ConnDB.getConnection();
					ps0 = conn0.prepareStatement(sql);
					ps0.executeUpdate();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn0, ps0);
				}
				sql="select * from Information where TheTime='"+TheTime+"'";
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn, ps);
				}  
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{       
			ConnDB.release(conn1, ps1, rs1);
		}
		return rs;
	}


}
