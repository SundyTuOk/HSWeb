package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.expert.model.TargetAmAuditModel;
import com.sf.energy.util.ConnDB;

/**
 * 表TargetAmAudit指标审计的数据库操作类
 * @author yanhao
 *
 */
public class TargetAmAuditDao
{
	int total;
	/**
	 * 通过ID查询指标审计数据
	 * @param auditID
	 * @return 返回指标数据的实体类
	 * @throws SQLException 
	 */
	public TargetAmAuditModel queryByID(int auditID) throws SQLException
	{
		TargetAmAuditModel taModel=null;
		String sql="Select * from TargetAmAudit Where TargetAudit_ID="+auditID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				taModel = new TargetAmAuditModel();
				taModel.setAuditID(rs.getInt("TargetAudit_ID"));
				taModel.setTargetID(rs.getInt("Target_ID"));
				taModel.setM1(rs.getFloat("TARGETAudit_JAN"));
				taModel.setM2(rs.getFloat("TARGETAudit_FEB"));
				taModel.setM3(rs.getFloat("TARGETAudit_MAR"));
				taModel.setM4(rs.getFloat("TARGETAudit_APR"));
				taModel.setM5(rs.getFloat("TARGETAudit_MAY"));
				taModel.setM6(rs.getFloat("TARGETAudit_JUN"));
				taModel.setM7(rs.getFloat("TARGETAudit_JUL"));
				taModel.setM8(rs.getFloat("TARGETAudit_AUG"));
				taModel.setM9(rs.getFloat("TARGETAudit_SEPT"));
				taModel.setM10(rs.getFloat("TARGETAudit_OCT"));
				taModel.setM11(rs.getFloat("TARGETAudit_NOV"));
				taModel.setM12(rs.getFloat("TARGETAudit_DEC"));
				taModel.setTargetManName(rs.getString("TARGETAudit_LastMAN"));
				taModel.setTargetTime(rs.getString("TARGETAudit_LastTIME"));
				taModel.setTargetState(rs.getString("TARGETAudit_LastState"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			taModel = new TargetAmAuditModel();
//			taModel.setAuditID(rs.getInt("TargetAudit_ID"));
//			taModel.setTargetID(rs.getInt("Target_ID"));
//			taModel.setM1(rs.getFloat("TARGETAudit_JAN"));
//			taModel.setM2(rs.getFloat("TARGETAudit_FEB"));
//			taModel.setM3(rs.getFloat("TARGETAudit_MAR"));
//			taModel.setM4(rs.getFloat("TARGETAudit_APR"));
//			taModel.setM5(rs.getFloat("TARGETAudit_MAY"));
//			taModel.setM6(rs.getFloat("TARGETAudit_JUN"));
//			taModel.setM7(rs.getFloat("TARGETAudit_JUL"));
//			taModel.setM8(rs.getFloat("TARGETAudit_AUG"));
//			taModel.setM9(rs.getFloat("TARGETAudit_SEPT"));
//			taModel.setM10(rs.getFloat("TARGETAudit_OCT"));
//			taModel.setM11(rs.getFloat("TARGETAudit_NOV"));
//			taModel.setM12(rs.getFloat("TARGETAudit_DEC"));
//			taModel.setTargetManName(rs.getString("TARGETAudit_LastMAN"));
//			taModel.setTargetTime(rs.getString("TARGETAudit_LastTIME"));
//			taModel.setTargetState(rs.getString("TARGETAudit_LastState"));
//		}
		
		return taModel;
		
	}
	
	/**
	 * 列出所有的能源指标审计信息
	 * @return 返回指标数据的实体类的集合
	 * @throws SQLException 
	 */
	public ArrayList<TargetAmAuditModel> listAllAudit() throws SQLException
	{
		TargetAmAuditModel taModel=null;
		ArrayList<TargetAmAuditModel> list=new ArrayList<TargetAmAuditModel>();
		String sql="Select * from TargetAmAudit a inner join TargetAm b on a.Target_ID=b.Target_ID order by a.TargetAudit_ID desc";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				taModel = new TargetAmAuditModel();
				taModel.setTargetTime(rs.getString("Target_Time"));
				taModel.setTargetYear(rs.getInt("Target_Year"));
				taModel.setTargetFenlei(rs.getString("Target_Fenlei"));
				taModel.setArchID(rs.getInt("Target_Index"));
				taModel.setArchName(rs.getString("Target_Name"));
				taModel.setAuditID(rs.getInt("TargetAudit_ID"));
				taModel.setTargetID(rs.getInt("Target_ID"));
				taModel.setM1(rs.getFloat("TARGETAudit_JAN"));
				taModel.setM2(rs.getFloat("TARGETAudit_FEB"));
				taModel.setM3(rs.getFloat("TARGETAudit_MAR"));
				taModel.setM4(rs.getFloat("TARGETAudit_APR"));
				taModel.setM5(rs.getFloat("TARGETAudit_MAY"));
				taModel.setM6(rs.getFloat("TARGETAudit_JUN"));
				taModel.setM7(rs.getFloat("TARGETAudit_JUL"));
				taModel.setM8(rs.getFloat("TARGETAudit_AUG"));
				taModel.setM9(rs.getFloat("TARGETAudit_SEPT"));
				taModel.setM10(rs.getFloat("TARGETAudit_OCT"));
				taModel.setM11(rs.getFloat("TARGETAudit_NOV"));
				taModel.setM12(rs.getFloat("TARGETAudit_DEC"));
				taModel.setTargetManName(rs.getString("TARGETAudit_LastMAN"));
				taModel.setTargetLastTime(rs.getString("TARGETAudit_LastTIME"));
				taModel.setTargetState(rs.getString("TARGETAudit_LastState"));
				list.add(taModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	
//		while (rs.next())
//		{
//			taModel = new TargetAmAuditModel();
//			taModel.setTargetTime(rs.getString("Target_Time"));
//			taModel.setTargetYear(rs.getInt("Target_Year"));
//			taModel.setTargetFenlei(rs.getString("Target_Fenlei"));
//			taModel.setArchID(rs.getInt("Target_Index"));
//			taModel.setArchName(rs.getString("Target_Name"));
//			taModel.setAuditID(rs.getInt("TargetAudit_ID"));
//			taModel.setTargetID(rs.getInt("Target_ID"));
//			taModel.setM1(rs.getFloat("TARGETAudit_JAN"));
//			taModel.setM2(rs.getFloat("TARGETAudit_FEB"));
//			taModel.setM3(rs.getFloat("TARGETAudit_MAR"));
//			taModel.setM4(rs.getFloat("TARGETAudit_APR"));
//			taModel.setM5(rs.getFloat("TARGETAudit_MAY"));
//			taModel.setM6(rs.getFloat("TARGETAudit_JUN"));
//			taModel.setM7(rs.getFloat("TARGETAudit_JUL"));
//			taModel.setM8(rs.getFloat("TARGETAudit_AUG"));
//			taModel.setM9(rs.getFloat("TARGETAudit_SEPT"));
//			taModel.setM10(rs.getFloat("TARGETAudit_OCT"));
//			taModel.setM11(rs.getFloat("TARGETAudit_NOV"));
//			taModel.setM12(rs.getFloat("TARGETAudit_DEC"));
//			taModel.setTargetManName(rs.getString("TARGETAudit_LastMAN"));
//			taModel.setTargetLastTime(rs.getString("TARGETAudit_LastTIME"));
//			taModel.setTargetState(rs.getString("TARGETAudit_LastState"));
//			list.add(taModel);
//		}
		return list;
		
	}
	
	/**
	 * 列出所有的能源指标审计信息
	 * @return 返回指标数据的实体类的集合
	 * @throws SQLException 
	 */
	public ArrayList<TargetAmAuditModel> listAmAndWaterAudit(int pageCount, int pageIndex) throws SQLException
	{
		TargetAmAuditModel taModel=null;
		ArrayList<TargetAmAuditModel> list=new ArrayList<TargetAmAuditModel>();
		String sql="Select a.*,b.*,1 as meterType from TargetAmAudit a inner join TargetAm b on a.Target_ID=b.Target_ID union all Select c.*,d.*,2 as meterType from TargetWaterAudit c inner join TargetWater d on c.Target_ID=d.Target_ID ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
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
			
			while (rs.next()&&(count > 0))
			{
				taModel = new TargetAmAuditModel();
				taModel.setTargetTime(rs.getString("Target_Time"));
				taModel.setTargetYear(rs.getInt("Target_Year"));
				taModel.setTargetFenlei(rs.getString("Target_Fenlei"));
				taModel.setArchID(rs.getInt("Target_Index"));
				taModel.setArchName(rs.getString("Target_Name"));
				taModel.setAuditID(rs.getInt("TargetAudit_ID"));
				taModel.setTargetID(rs.getInt("Target_ID"));
				taModel.setM1(rs.getFloat("TARGETAUDIT_JAN"));
				taModel.setM2(rs.getFloat("TARGETAudit_FEB"));
				taModel.setM3(rs.getFloat("TARGETAudit_MAR"));
				taModel.setM4(rs.getFloat("TARGETAudit_APR"));
				taModel.setM5(rs.getFloat("TARGETAudit_MAY"));
				taModel.setM6(rs.getFloat("TARGETAudit_JUN"));
				taModel.setM7(rs.getFloat("TARGETAudit_JUL"));
				taModel.setM8(rs.getFloat("TARGETAudit_AUG"));
				taModel.setM9(rs.getFloat("TARGETAudit_SEPT"));
				taModel.setM10(rs.getFloat("TARGETAudit_OCT"));
				taModel.setM11(rs.getFloat("TARGETAudit_NOV"));
				taModel.setM12(rs.getFloat("TARGETAudit_DEC"));
				taModel.setTargetManName(rs.getString("TARGETAudit_LastMAN"));
				taModel.setTargetLastTime(rs.getString("TARGETAudit_LastTIME"));
				taModel.setTargetState(rs.getString("TARGETAudit_LastState"));
				taModel.setMeterType(rs.getString("MeterType"));
				list.add(taModel);
				
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	
//		rs.last();
//		int count = rs.getRow();
//		setTotal(count);
//		if (count <= (pageCount * pageIndex))
//			return list;
//
//		count = count - pageCount * pageIndex;
//
//		if (count >= pageCount)
//			count = pageCount;
//
//		if ((pageCount * pageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(pageCount * pageIndex);
//		
//		while (rs.next()&&(count > 0))
//		{
//			taModel = new TargetAmAuditModel();
//			taModel.setTargetTime(rs.getString("Target_Time"));
//			taModel.setTargetYear(rs.getInt("Target_Year"));
//			taModel.setTargetFenlei(rs.getString("Target_Fenlei"));
//			taModel.setArchID(rs.getInt("Target_Index"));
//			taModel.setArchName(rs.getString("Target_Name"));
//			taModel.setAuditID(rs.getInt("TargetAudit_ID"));
//			taModel.setTargetID(rs.getInt("Target_ID"));
//			taModel.setM1(rs.getFloat("TARGETAUDIT_JAN"));
//			taModel.setM2(rs.getFloat("TARGETAudit_FEB"));
//			taModel.setM3(rs.getFloat("TARGETAudit_MAR"));
//			taModel.setM4(rs.getFloat("TARGETAudit_APR"));
//			taModel.setM5(rs.getFloat("TARGETAudit_MAY"));
//			taModel.setM6(rs.getFloat("TARGETAudit_JUN"));
//			taModel.setM7(rs.getFloat("TARGETAudit_JUL"));
//			taModel.setM8(rs.getFloat("TARGETAudit_AUG"));
//			taModel.setM9(rs.getFloat("TARGETAudit_SEPT"));
//			taModel.setM10(rs.getFloat("TARGETAudit_OCT"));
//			taModel.setM11(rs.getFloat("TARGETAudit_NOV"));
//			taModel.setM12(rs.getFloat("TARGETAudit_DEC"));
//			taModel.setTargetManName(rs.getString("TARGETAudit_LastMAN"));
//			taModel.setTargetLastTime(rs.getString("TARGETAudit_LastTIME"));
//			taModel.setTargetState(rs.getString("TARGETAudit_LastState"));
//			taModel.setMeterType(rs.getString("MeterType"));
//			list.add(taModel);
//			
//			count--;
//		}

		return list;
		
	}
	
	
	/**
	 * 查询能耗审计
	 * @param TargetIndex
	 * @param TargetStyle
	 * @param TargetYear
	 * @param fenxiangFenlei
	 * @return
	 * @throws SQLException
	 */
	
	public ArrayList<TargetAmAuditModel> searchAudit(String TargetIndex,int TargetStyle,int TargetYear,String fenxiangFenlei) throws SQLException
	{
		TargetAmAuditModel taModel=null;
		ArrayList<TargetAmAuditModel> list=new ArrayList<TargetAmAuditModel>();
		String sql="Select * from TargetAmAudit a inner join TargetAm b on a.Target_ID=b.Target_ID where Target_Year = "+TargetYear+" and Target_Style="+TargetStyle+" and Target_Index = '"+TargetIndex+"' and Target_Fenlei = '"+fenxiangFenlei+"' order by a.TargetAudit_ID desc";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				taModel = new TargetAmAuditModel();
				taModel.setTargetTime(rs.getString("Target_Time"));
				taModel.setTargetYear(rs.getInt("Target_Year"));
				taModel.setTargetFenlei(rs.getString("Target_Fenlei"));
				taModel.setArchID(rs.getInt("Target_Index"));
				taModel.setArchName(rs.getString("Target_Name"));
				taModel.setAuditID(rs.getInt("TargetAudit_ID"));
				taModel.setTargetID(rs.getInt("Target_ID"));
				taModel.setM1(rs.getFloat("TARGETAudit_JAN"));
				taModel.setM2(rs.getFloat("TARGETAudit_FEB"));
				taModel.setM3(rs.getFloat("TARGETAudit_MAR"));
				taModel.setM4(rs.getFloat("TARGETAudit_APR"));
				taModel.setM5(rs.getFloat("TARGETAudit_MAY"));
				taModel.setM6(rs.getFloat("TARGETAudit_JUN"));
				taModel.setM7(rs.getFloat("TARGETAudit_JUL"));
				taModel.setM8(rs.getFloat("TARGETAudit_AUG"));
				taModel.setM9(rs.getFloat("TARGETAudit_SEPT"));
				taModel.setM10(rs.getFloat("TARGETAudit_OCT"));
				taModel.setM11(rs.getFloat("TARGETAudit_NOV"));
				taModel.setM12(rs.getFloat("TARGETAudit_DEC"));
				taModel.setTargetManName(rs.getString("TARGETAudit_LastMAN"));
				taModel.setTargetLastTime(rs.getString("TARGETAudit_LastTIME"));
				taModel.setTargetState(rs.getString("TARGETAudit_LastState"));
				list.add(taModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		while (rs.next())
//		{
//			taModel = new TargetAmAuditModel();
//			taModel.setTargetTime(rs.getString("Target_Time"));
//			taModel.setTargetYear(rs.getInt("Target_Year"));
//			taModel.setTargetFenlei(rs.getString("Target_Fenlei"));
//			taModel.setArchID(rs.getInt("Target_Index"));
//			taModel.setArchName(rs.getString("Target_Name"));
//			taModel.setAuditID(rs.getInt("TargetAudit_ID"));
//			taModel.setTargetID(rs.getInt("Target_ID"));
//			taModel.setM1(rs.getFloat("TARGETAudit_JAN"));
//			taModel.setM2(rs.getFloat("TARGETAudit_FEB"));
//			taModel.setM3(rs.getFloat("TARGETAudit_MAR"));
//			taModel.setM4(rs.getFloat("TARGETAudit_APR"));
//			taModel.setM5(rs.getFloat("TARGETAudit_MAY"));
//			taModel.setM6(rs.getFloat("TARGETAudit_JUN"));
//			taModel.setM7(rs.getFloat("TARGETAudit_JUL"));
//			taModel.setM8(rs.getFloat("TARGETAudit_AUG"));
//			taModel.setM9(rs.getFloat("TARGETAudit_SEPT"));
//			taModel.setM10(rs.getFloat("TARGETAudit_OCT"));
//			taModel.setM11(rs.getFloat("TARGETAudit_NOV"));
//			taModel.setM12(rs.getFloat("TARGETAudit_DEC"));
//			taModel.setTargetManName(rs.getString("TARGETAudit_LastMAN"));
//			taModel.setTargetLastTime(rs.getString("TARGETAudit_LastTIME"));
//			taModel.setTargetState(rs.getString("TARGETAudit_LastState"));
//			list.add(taModel);
//		}

		
		return list;
		
	}
	
	
	/**
	 * 删除能耗审计
	 * @param auditID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int auditID) throws SQLException
	{
		String sql="delete from TargetAmAudit where TargetAudit_ID="+auditID;
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	
	/**
	 * 删除能耗审计
	 * @param auditID
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteByTargetID(int targetID) throws SQLException
	{
		String sql="delete from TargetAmAudit where Target_ID="+targetID;
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	/**
	 * 判断是否有数据
	 * @param targetID
	 * @return
	 */
	public boolean hasSame(int targetID) throws SQLException
	{
		boolean b=false;
		String sql="Select * from TargetAmAudit Where Target_ID="+targetID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				b=true;
			}
			else 
			{
				b=false;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			b=true;
//		}
//		else 
//		{
//			b=false;
//		}

		return b;
	}
	
	/**
	 * 新增能耗审计
	 * @param taModel
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(TargetAmAuditModel taModel) throws SQLException
	{
		String sql = "INSERT INTO TargetAmAudit(TARGET_ID,TARGETAudit_JAN,TARGETAudit_FEB,TARGETAudit_MAR," +
				"TARGETAudit_APR,TARGETAudit_MAY,TARGETAudit_JUN,TARGETAudit_JUL,TARGETAudit_AUG,TARGETAudit_SEPT,TARGETAudit_OCT,TARGETAudit_NOV,TARGETAudit_DEC,TARGETAudit_LastMAN,TARGETAudit_LastTIME,TargetAudit_LastState)" +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, taModel.getTargetID());
			ps.setFloat(2, taModel.getM1());
			ps.setFloat(3, taModel.getM2());
			ps.setFloat(4, taModel.getM3());
			ps.setFloat(5, taModel.getM4());
			ps.setFloat(6, taModel.getM5());
			ps.setFloat(7, taModel.getM6());
			ps.setFloat(8, taModel.getM7());
			ps.setFloat(9, taModel.getM8());
			ps.setFloat(10, taModel.getM9());
			ps.setFloat(11, taModel.getM10());
			ps.setFloat(12, taModel.getM11());
			ps.setFloat(13, taModel.getM12());
			ps.setString(14, taModel.getTargetManName());
			ps.setString(15, taModel.getTargetLastTime());
			ps.setString(16, taModel.getTargetState());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}		
//		ps.setFloat(2, taModel.getM1());
//		ps.setFloat(3, taModel.getM2());
//		ps.setFloat(4, taModel.getM3());
//		ps.setFloat(5, taModel.getM4());
//		ps.setFloat(6, taModel.getM5());
//		ps.setFloat(7, taModel.getM6());
//		ps.setFloat(8, taModel.getM7());
//		ps.setFloat(9, taModel.getM8());
//		ps.setFloat(10, taModel.getM9());
//		ps.setFloat(11, taModel.getM10());
//		ps.setFloat(12, taModel.getM11());
//		ps.setFloat(13, taModel.getM12());
//		ps.setString(14, taModel.getTargetManName());
//		ps.setString(15, taModel.getTargetLastTime());
//		ps.setString(16, taModel.getTargetState());

		return b;
		
	}
	
	
	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
}
