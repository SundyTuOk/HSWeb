package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.expert.model.TargetAmModel;
import com.sf.energy.util.ConnDB;

/**
 * TargetAm的数据表操作类
 * @author yanhao
 *
 */
public class TargetAmDao
{
	/**
	 * 通过ID查询指标数据
	 * @param targetID
	 * @return 返回指标数据的实体类
	 * @throws SQLException 
	 */
	public TargetAmModel queryByID(int targetID) throws SQLException
	{
		TargetAmModel taModel=null;
		String sql="Select * from TargetAm Where Target_ID="+targetID;
		
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
				taModel = new TargetAmModel();
				taModel.setTargetID(rs.getInt("Target_ID"));
				taModel.setTargetIndex(rs.getString("TARGET_INDEX"));
				taModel.setTargetStyle(rs.getInt("TARGET_STYLE"));
				taModel.setTargetFenlei(rs.getString("TARGET_FENLEI"));
				taModel.setTargetName(rs.getString("TARGET_NAME"));
				taModel.setTargetYear(rs.getInt("TARGET_YEAR"));
				taModel.setM1(rs.getFloat("TARGET_JAN"));
				taModel.setM2(rs.getFloat("TARGET_FEB"));
				taModel.setM3(rs.getFloat("TARGET_MAR"));
				taModel.setM4(rs.getFloat("TARGET_APR"));
				taModel.setM5(rs.getFloat("TARGET_MAY"));
				taModel.setM6(rs.getFloat("TARGET_JUN"));
				taModel.setM7(rs.getFloat("TARGET_JUL"));
				taModel.setM8(rs.getFloat("TARGET_AUG"));
				taModel.setM9(rs.getFloat("TARGET_SEPT"));
				taModel.setM10(rs.getFloat("TARGET_OCT"));
				taModel.setM11(rs.getFloat("TARGET_NOV"));
				taModel.setM12(rs.getFloat("TARGET_DEC"));
				taModel.setTargetMan(rs.getInt("TARGET_MAN"));
				taModel.setTargetTime(rs.getString("TARGET_TIME"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			taModel = new TargetAmModel();
//			taModel.setTargetID(rs.getInt("Target_ID"));
//			taModel.setTargetIndex(rs.getString("TARGET_INDEX"));
//			taModel.setTargetStyle(rs.getInt("TARGET_STYLE"));
//			taModel.setTargetFenlei(rs.getString("TARGET_FENLEI"));
//			taModel.setTargetName(rs.getString("TARGET_NAME"));
//			taModel.setTargetYear(rs.getInt("TARGET_YEAR"));
//			taModel.setM1(rs.getFloat("TARGET_JAN"));
//			taModel.setM2(rs.getFloat("TARGET_FEB"));
//			taModel.setM3(rs.getFloat("TARGET_MAR"));
//			taModel.setM4(rs.getFloat("TARGET_APR"));
//			taModel.setM5(rs.getFloat("TARGET_MAY"));
//			taModel.setM6(rs.getFloat("TARGET_JUN"));
//			taModel.setM7(rs.getFloat("TARGET_JUL"));
//			taModel.setM8(rs.getFloat("TARGET_AUG"));
//			taModel.setM9(rs.getFloat("TARGET_SEPT"));
//			taModel.setM10(rs.getFloat("TARGET_OCT"));
//			taModel.setM11(rs.getFloat("TARGET_NOV"));
//			taModel.setM12(rs.getFloat("TARGET_DEC"));
//			taModel.setTargetMan(rs.getInt("TARGET_MAN"));
//			taModel.setTargetTime(rs.getString("TARGET_TIME"));
//		}

		
		return taModel;
		
	}
	
	/**
	 * 通过目标类型对象和对应的对象ID查询指定年份的指标用量
	 * @param TargetIndex	对象ID
	 * @param TargetStyle	对象类型,2:建筑	1:区域
	 * @param TargetYear	年份
	 * @param energyFenlei	分项
	 * @return	结果集
	 * @throws SQLException
	 */
	public TargetAmModel queryByIndex(String TargetIndex,int TargetStyle,int TargetYear,String energyFenlei) throws SQLException
	{
		TargetAmModel taModel=new TargetAmModel();
		String sql="Select * from TargetAm Where Target_Index='"+TargetIndex +"' and Target_Style="+TargetStyle+" and Target_Year="+TargetYear+" and Target_Fenlei='"+energyFenlei+"'";
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
				taModel.setTargetID(rs.getInt("Target_ID"));
				taModel.setTargetIndex(TargetIndex);
				taModel.setTargetStyle(TargetStyle);
				taModel.setTargetIndex(rs.getString("TARGET_INDEX"));
				taModel.setTargetStyle(rs.getInt("TARGET_STYLE"));
				taModel.setTargetFenlei(rs.getString("TARGET_FENLEI"));
				taModel.setTargetName(rs.getString("TARGET_NAME"));
				taModel.setTargetYear(TargetYear);
				taModel.setM1(rs.getFloat("TARGET_JAN"));
				taModel.setM2(rs.getFloat("TARGET_FEB"));
				taModel.setM3(rs.getFloat("TARGET_MAR"));
				taModel.setM4(rs.getFloat("TARGET_APR"));
				taModel.setM5(rs.getFloat("TARGET_MAY"));
				taModel.setM6(rs.getFloat("TARGET_JUN"));
				taModel.setM7(rs.getFloat("TARGET_JUL"));
				taModel.setM8(rs.getFloat("TARGET_AUG"));
				taModel.setM9(rs.getFloat("TARGET_SEPT"));
				taModel.setM10(rs.getFloat("TARGET_OCT"));
				taModel.setM11(rs.getFloat("TARGET_NOV"));
				taModel.setM12(rs.getFloat("TARGET_DEC"));
				taModel.setTargetMan(rs.getInt("TARGET_MAN"));
				taModel.setTargetTime(rs.getString("TARGET_TIME"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			taModel.setTargetID(rs.getInt("Target_ID"));
//			taModel.setTargetIndex(TargetIndex);
//			taModel.setTargetStyle(TargetStyle);
//			taModel.setTargetIndex(rs.getString("TARGET_INDEX"));
//			taModel.setTargetStyle(rs.getInt("TARGET_STYLE"));
//			taModel.setTargetFenlei(rs.getString("TARGET_FENLEI"));
//			taModel.setTargetName(rs.getString("TARGET_NAME"));
//			taModel.setTargetYear(TargetYear);
//			taModel.setM1(rs.getFloat("TARGET_JAN"));
//			taModel.setM2(rs.getFloat("TARGET_FEB"));
//			taModel.setM3(rs.getFloat("TARGET_MAR"));
//			taModel.setM4(rs.getFloat("TARGET_APR"));
//			taModel.setM5(rs.getFloat("TARGET_MAY"));
//			taModel.setM6(rs.getFloat("TARGET_JUN"));
//			taModel.setM7(rs.getFloat("TARGET_JUL"));
//			taModel.setM8(rs.getFloat("TARGET_AUG"));
//			taModel.setM9(rs.getFloat("TARGET_SEPT"));
//			taModel.setM10(rs.getFloat("TARGET_OCT"));
//			taModel.setM11(rs.getFloat("TARGET_NOV"));
//			taModel.setM12(rs.getFloat("TARGET_DEC"));
//			taModel.setTargetMan(rs.getInt("TARGET_MAN"));
//			taModel.setTargetTime(rs.getString("TARGET_TIME"));
//		}
		
		return taModel;
		
	}
	
	
	
	/**
	 * 新增指标数据
	 * @param TargetAmModel
	 * @return 是否操作成功
	 * @throws SQLException 
	 */
	public boolean insert(TargetAmModel taModel) throws SQLException
	{
		String sql = "INSERT INTO TargetAm(TARGET_INDEX,TARGET_STYLE,TARGET_FENLEI,TARGET_NAME,TARGET_YEAR,TARGET_JAN,TARGET_FEB,TARGET_MAR," +
				"TARGET_APR,TARGET_MAY,TARGET_JUN,TARGET_JUL,TARGET_AUG,TARGET_SEPT,TARGET_OCT,TARGET_NOV,TARGET_DEC,TARGET_MAN,TARGET_TIME)" +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, taModel.getTargetIndex());
			ps.setInt(2, taModel.getTargetStyle());
			ps.setString(3, taModel.getTargetFenlei());
			ps.setString(4, taModel.getTargetName());
			ps.setFloat(5, taModel.getTargetYear());
			ps.setFloat(6, taModel.getM1());
			ps.setFloat(7, taModel.getM2());
			ps.setFloat(8, taModel.getM3());
			ps.setFloat(9, taModel.getM4());
			ps.setFloat(10, taModel.getM5());
			ps.setFloat(11, taModel.getM6());
			ps.setFloat(12, taModel.getM7());
			ps.setFloat(13, taModel.getM8());
			ps.setFloat(14, taModel.getM9());
			ps.setFloat(15, taModel.getM10());
			ps.setFloat(16, taModel.getM11());
			ps.setFloat(17, taModel.getM12());
			ps.setFloat(18, taModel.getTargetMan());
			ps.setString(19, taModel.getTargetTime());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
//		ps.setString(1, taModel.getTargetIndex());
//		ps.setInt(2, taModel.getTargetStyle());
//		ps.setString(3, taModel.getTargetFenlei());
//		ps.setString(4, taModel.getTargetName());
//		ps.setFloat(5, taModel.getTargetYear());
//		ps.setFloat(6, taModel.getM1());
//		ps.setFloat(7, taModel.getM2());
//		ps.setFloat(8, taModel.getM3());
//		ps.setFloat(9, taModel.getM4());
//		ps.setFloat(10, taModel.getM5());
//		ps.setFloat(11, taModel.getM6());
//		ps.setFloat(12, taModel.getM7());
//		ps.setFloat(13, taModel.getM8());
//		ps.setFloat(14, taModel.getM9());
//		ps.setFloat(15, taModel.getM10());
//		ps.setFloat(16, taModel.getM11());
//		ps.setFloat(17, taModel.getM12());
//		ps.setFloat(18, taModel.getTargetMan());
//		ps.setString(19, taModel.getTargetTime());

		
		return b;
		
	}
	
	/**
	 * 判断是否已经插入相同的数据
	 * @param TargetIndex
	 * @param TargetStyle
	 * @param TargetYear
	 * @param energyFenlei 
	 * @return
	 * @throws SQLException
	 */
	public boolean hasSame(String TargetIndex,int TargetStyle,int TargetYear,String energyFenlei) throws SQLException
	{
		boolean b=false;
		String sql="Select * from TargetAm Where Target_Index='"+TargetIndex +"' and Target_Style="+TargetStyle+" and Target_Year="+TargetYear+" and Target_Fenlei='"+energyFenlei+"'";
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
	 * 删除数据
	 * @param TargetIndex
	 * @param TargetStyle
	 * @param TargetYear
	 * @param energyFenlei
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(String TargetIndex,int TargetStyle,int TargetYear,String energyFenlei) throws SQLException
	{
		String sql="delete  from TargetAm Where Target_Index='"+TargetIndex +"' and Target_Style="+TargetStyle+" and Target_Year="+TargetYear+" and Target_Fenlei='"+energyFenlei+"'";
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
	 * 判断该指标是否以审计
	 * @param targetID
	 * @return
	 * @throws SQLException
	 */
	public boolean isAudit(int targetID) throws SQLException
	{
		boolean b=false;
		String sql="Select * from TargetAmAudit where Target_ID="+targetID;
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
			else {
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
//		else {
//			b=false;
//		}

		return b;
	}
	
}
