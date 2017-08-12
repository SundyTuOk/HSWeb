package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.expert.model.EstimateModel;
import com.sf.energy.util.ConnDB;

public class EstimateDao {

	int total;
	/**
	 * 往estimate表里面插入前台输入的数据
	 * @param estm	插入对象
	 * @return		是否插入正确
	 * @throws SQLException
	 */
	public boolean insert(EstimateModel  estm) throws SQLException
	{
		String sql = "INSERT INTO Estimate(Estimate_Index,Estimate_Style,Estimate_Name,Estimate_Year," +
				"Estimate_Count,Estimate_Value0,Estimate_Value1,Estimate_Value2,Estimate_Value3," +
				"Estimate_Value4,Estimate_Value5,Estimate_Value6,Estimate_Value7,Estimate_Value8," +
				"Estimate_Value9,Estimate_Value10,Estimate_Value11) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, estm.getEstimate_Index());
			ps.setInt(2, estm.getEstimate_Style());
			ps.setString(3, estm.getEstimate_Name());
			ps.setInt(4, estm.getEstimate_Year());
			ps.setFloat(5, estm.getEstimate_Count());
			ps.setString(6, estm.getEstimate_Value0());
			ps.setString(7, estm.getEstimate_Value1());
			ps.setString(8, estm.getEstimate_Value2());
			ps.setString(9, estm.getEstimate_Value3());
			ps.setString(10, estm.getEstimate_Value4());
			ps.setString(11, estm.getEstimate_Value5());
			ps.setString(12, estm.getEstimate_Value6());
			ps.setString(13, estm.getEstimate_Value7());
			ps.setString(14, estm.getEstimate_Value8());
			ps.setString(15, estm.getEstimate_Value9());
			ps.setString(16, estm.getEstimate_Value10());
			ps.setString(17, estm.getEstimate_Value11());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		ps.setString(1, estm.getEstimate_Index());
//		ps.setInt(2, estm.getEstimate_Style());
//		ps.setString(3, estm.getEstimate_Name());
//		ps.setInt(4, estm.getEstimate_Year());
//		ps.setFloat(5, estm.getEstimate_Count());
//		ps.setString(6, estm.getEstimate_Value0());
//		ps.setString(7, estm.getEstimate_Value1());
//		ps.setString(8, estm.getEstimate_Value2());
//		ps.setString(9, estm.getEstimate_Value3());
//		ps.setString(10, estm.getEstimate_Value4());
//		ps.setString(11, estm.getEstimate_Value5());
//		ps.setString(12, estm.getEstimate_Value6());
//		ps.setString(13, estm.getEstimate_Value7());
//		ps.setString(14, estm.getEstimate_Value8());
//		ps.setString(15, estm.getEstimate_Value9());
//		ps.setString(16, estm.getEstimate_Value10());
//		ps.setString(17, estm.getEstimate_Value11());
				
		
		return b;
	}
	
	
	/**
	 * 		更新Estimate表里面的已有记录
	 * @param estm	更新的数据对象
	 * @return		是否更新正确
	 * @throws SQLException
	 */
	public	boolean	 modify(EstimateModel  estm) throws SQLException
	{
		String sql = "update Estimate set Estimate_Count="+estm.getEstimate_Count()+",Estimate_Value0='"+estm.getEstimate_Value0()+"',Estimate_Value1='"+estm.getEstimate_Value1()+"' ,Estimate_Value2= '"+estm.getEstimate_Value2()+"'," +
					"	Estimate_Value3='"+estm.getEstimate_Value3()+"' ,Estimate_Value4='"+estm.getEstimate_Value4()+"' ,Estimate_Value5='"+estm.getEstimate_Value5()+"' ,Estimate_Value6='"+estm.getEstimate_Value6()+"' ,Estimate_Value7='"+estm.getEstimate_Value7()+"' ," +
					"	Estimate_Value8='"+estm.getEstimate_Value8()+"' ,Estimate_Value9='"+estm.getEstimate_Value9()+"' ,Estimate_Value10='"+estm.getEstimate_Value10()+"',Estimate_Value11='"+estm.getEstimate_Value11()+"' ,Estimate_Index='"+estm.getEstimate_Index()+"', " +
					" Estimate_style="+estm.getEstimate_Style()+",Estimate_Name='"+estm.getEstimate_Name()+"',Estimate_Year="+estm.getEstimate_Year()+" where Estimate_ID="+estm.getEstimate_ID()+" ";
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			int a=ps.executeUpdate();
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		ps.setFloat(1,estm.getEstimate_Count());
//		ps.setString(2, estm.getEstimate_Value0());
//		ps.setString(3, estm.getEstimate_Value1());
//		ps.setString(4, estm.getEstimate_Value2());
//		ps.setString(5, estm.getEstimate_Value3());
//		ps.setString(6, estm.getEstimate_Value4());
//		ps.setString(7, estm.getEstimate_Value5());
//		ps.setString(8, estm.getEstimate_Value6());
//		ps.setString(9, estm.getEstimate_Value7());
//		ps.setString(10, estm.getEstimate_Value8());
//		ps.setString(11, estm.getEstimate_Value9());
//		ps.setString(12, estm.getEstimate_Value10());
//		ps.setString(13, estm.getEstimate_Value11());
//		ps.setString(14, estm.getEstimate_Index());
//		ps.setInt(15, estm.getEstimate_Style());
//		ps.setString(16, estm.getEstimate_Name());
//		ps.setInt(17, estm.getEstimate_Year());
//		ps.setInt(18, estm.getEstimate_ID());
//		int a=ps.executeUpdate();
//		
//		boolean b = (ps.executeUpdate()==1);
		
		return b;
	}
	
	
	/**
	 * 		删除一条报告记录
	 * @param ID	记录ID
	 * @return		
	 * @throws SQLException
	 */
	public	boolean delete(int ID) throws SQLException
	{
		
		String sql = "delete  from estimate where estimate_ID =?";
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ID);
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
	 * 		通过类型和年份查询对应的Estimate_ID
	 * @param estm		数据对象
	 * @return			结果
	 * @throws SQLException
	 */
	public	String  getEstimate_ID(EstimateModel  estm) throws SQLException
	{
		String esID = null;
		String sql = "select Estimate_ID from Estimate " +
				"where Estimate_Index = ? and Estimate_Style =?  and Estimate_Year=?";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, estm.getEstimate_Index());
			ps.setInt(2, estm.getEstimate_Style());
			ps.setInt(3, estm.getEstimate_Year());
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				esID = rs.getInt("Estimate_ID") +"";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}		
//		ps.setInt(2, estm.getEstimate_Style());
//		ps.setInt(3, estm.getEstimate_Year());
//		
//		ResultSet rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			esID = rs.getInt("Estimate_ID") +"";
//		}
		
		return esID;
	}
	
	
	/**
	 * 通过对应的年份，对象类型，对象ID查询指标考评的详细信息
	 * @param year		年份
	 * @param style		对象类型
	 * @param id		对象ID
	 * @return			结果集
	 * @throws SQLException
	 */
	public EstimateModel getTargetInfo(int year,int style,String id) throws SQLException
	{
		EstimateModel  estm =  null;
		
		String sql = "select ESTIMATE_VALUE0,ESTIMATE_VALUE1,ESTIMATE_VALUE2,ESTIMATE_VALUE3," +
				"ESTIMATE_VALUE4,ESTIMATE_VALUE5,ESTIMATE_VALUE6,ESTIMATE_VALUE7,ESTIMATE_VALUE8," +
				"ESTIMATE_VALUE9,ESTIMATE_VALUE10,ESTIMATE_VALUE11,ESTIMATE_COUNT,ESTIMATE_NAME,ESTIMATE_ID " +
				" from estimate " +
				"where ESTIMATE_style=? and ESTIMATE_year=? and ESTIMATE_index=?";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1,style);
			ps.setInt(2, year);
			ps.setString(3, id);
			
			rs = ps.executeQuery();
			while(rs.next())
			{
				estm = new EstimateModel();
				estm.setEstimate_Index(id);
				estm.setEstimate_Style(style);
				estm.setEstimate_Year(year);
				estm.setEstimate_ID(rs.getInt("Estimate_ID"));
				estm.setEstimate_Name(rs.getString("ESTIMATE_NAME"));
				estm.setEstimate_Count(rs.getFloat("ESTIMATE_COUNT"));
				estm.setEstimate_Value0(rs.getString("ESTIMATE_VALUE0"));
				estm.setEstimate_Value1(rs.getString("ESTIMATE_VALUE1"));
				estm.setEstimate_Value2(rs.getString("ESTIMATE_VALUE2"));
				estm.setEstimate_Value3(rs.getString("ESTIMATE_VALUE3"));
				estm.setEstimate_Value4(rs.getString("ESTIMATE_VALUE4"));
				estm.setEstimate_Value5(rs.getString("ESTIMATE_VALUE5"));
				estm.setEstimate_Value6(rs.getString("ESTIMATE_VALUE6"));
				estm.setEstimate_Value7(rs.getString("ESTIMATE_VALUE7"));
				estm.setEstimate_Value8(rs.getString("ESTIMATE_VALUE8"));
				estm.setEstimate_Value9(rs.getString("ESTIMATE_VALUE9"));
				estm.setEstimate_Value10(rs.getString("ESTIMATE_VALUE10"));
				estm.setEstimate_Value11(rs.getString("ESTIMATE_VALUE11"));
				String Estimate_Value0s[]=estm.getEstimate_Value0().split(",");
				estm.setEstimate_Value0_1(Float.parseFloat(Estimate_Value0s[0]));
				estm.setEstimate_Value0_2(Float.parseFloat(Estimate_Value0s[1]));
				estm.setEstimate_Value0_3(Float.parseFloat(Estimate_Value0s[2]));
				estm.setEstimate_Value0_4(Float.parseFloat(Estimate_Value0s[3]));
				estm.setEstimate_Value0total(Float.parseFloat(Estimate_Value0s[0])+Float.parseFloat(Estimate_Value0s[1])+Float.parseFloat(Estimate_Value0s[2])+Float.parseFloat(Estimate_Value0s[3]));
				////System.out.println("eeee:"+estm.getEstimate_Value0total());
				String Estimate_Value1s[]=estm.getEstimate_Value1().split(",");
				estm.setEstimate_Value1_1(Float.parseFloat(Estimate_Value1s[0]));
				estm.setEstimate_Value1_2(Float.parseFloat(Estimate_Value1s[1]));
				estm.setEstimate_Value1_3(Float.parseFloat(Estimate_Value1s[2]));
				estm.setEstimate_Value1_4(Float.parseFloat(Estimate_Value1s[3]));
				estm.setEstimate_Value1_5(Float.parseFloat(Estimate_Value1s[4]));
				estm.setEstimate_Value1_6(Float.parseFloat(Estimate_Value1s[5]));
				estm.setEstimate_Value1_7(Float.parseFloat(Estimate_Value1s[6]));
				estm.setEstimate_Value1total(Float.parseFloat(Estimate_Value1s[0])+Float.parseFloat(Estimate_Value1s[1])+Float.parseFloat(Estimate_Value1s[2])+Float.parseFloat(Estimate_Value1s[3])+Float.parseFloat(Estimate_Value1s[4])+Float.parseFloat(Estimate_Value1s[5])+Float.parseFloat(Estimate_Value1s[6]));
				String Estimate_Value2s[]=estm.getEstimate_Value2().split(",");
				estm.setEstimate_Value2_1(Float.parseFloat(Estimate_Value2s[0]));
				estm.setEstimate_Value2_2(Float.parseFloat(Estimate_Value2s[1]));
				estm.setEstimate_Value2_3(Float.parseFloat(Estimate_Value2s[2]));
				estm.setEstimate_Value2_4(Float.parseFloat(Estimate_Value2s[3]));
				estm.setEstimate_Value2_5(Float.parseFloat(Estimate_Value2s[4]));
				estm.setEstimate_Value2_6(Float.parseFloat(Estimate_Value2s[5]));
				estm.setEstimate_Value2total(Float.parseFloat(Estimate_Value2s[0])+Float.parseFloat(Estimate_Value2s[1])+Float.parseFloat(Estimate_Value2s[2])+Float.parseFloat(Estimate_Value2s[3])+Float.parseFloat(Estimate_Value2s[4])+Float.parseFloat(Estimate_Value2s[5]));
				String Estimate_Value3s[]=estm.getEstimate_Value3().split(",");
				estm.setEstimate_Value3_1(Float.parseFloat(Estimate_Value3s[0]));
				estm.setEstimate_Value3_2(Float.parseFloat(Estimate_Value3s[1]));
				estm.setEstimate_Value3total(Float.parseFloat(Estimate_Value3s[0])+Float.parseFloat(Estimate_Value3s[1]));
				String Estimate_Value4s[]=estm.getEstimate_Value4().split(",");
				estm.setEstimate_Value4_1(Float.parseFloat(Estimate_Value4s[0]));
				estm.setEstimate_Value4_2(Float.parseFloat(Estimate_Value4s[1]));
				estm.setEstimate_Value4_3(Float.parseFloat(Estimate_Value4s[2]));
				estm.setEstimate_Value4total(Float.parseFloat(Estimate_Value4s[0])+Float.parseFloat(Estimate_Value4s[1])+Float.parseFloat(Estimate_Value4s[2]));
				String Estimate_Value5s[]=estm.getEstimate_Value5().split(",");
				estm.setEstimate_Value5_1(Float.parseFloat(Estimate_Value5s[0]));
				estm.setEstimate_Value5_2(Float.parseFloat(Estimate_Value5s[1]));
				estm.setEstimate_Value5_3(Float.parseFloat(Estimate_Value5s[2]));
				estm.setEstimate_Value5_4(Float.parseFloat(Estimate_Value5s[3]));
				estm.setEstimate_Value5_5(Float.parseFloat(Estimate_Value5s[4]));
				estm.setEstimate_Value5total(Float.parseFloat(Estimate_Value5s[0])+Float.parseFloat(Estimate_Value5s[1])+Float.parseFloat(Estimate_Value5s[2])+Float.parseFloat(Estimate_Value5s[3])+Float.parseFloat(Estimate_Value5s[4]));
				String Estimate_Value6s[]=estm.getEstimate_Value6().split(",");
				estm.setEstimate_Value6_1(Float.parseFloat(Estimate_Value6s[0]));
				estm.setEstimate_Value6_2(Float.parseFloat(Estimate_Value6s[1]));
				estm.setEstimate_Value6_3(Float.parseFloat(Estimate_Value6s[2]));
				estm.setEstimate_Value6_4(Float.parseFloat(Estimate_Value6s[3]));
				estm.setEstimate_Value6_5(Float.parseFloat(Estimate_Value6s[4]));
				estm.setEstimate_Value6_6(Float.parseFloat(Estimate_Value6s[5]));
				estm.setEstimate_Value6_7(Float.parseFloat(Estimate_Value6s[6]));
				estm.setEstimate_Value6_8(Float.parseFloat(Estimate_Value6s[7]));
				estm.setEstimate_Value6total(Float.parseFloat(Estimate_Value6s[0])+Float.parseFloat(Estimate_Value6s[1])+Float.parseFloat(Estimate_Value6s[2])+Float.parseFloat(Estimate_Value6s[3])+Float.parseFloat(Estimate_Value6s[4])+Float.parseFloat(Estimate_Value6s[5])+Float.parseFloat(Estimate_Value6s[6])+Float.parseFloat(Estimate_Value6s[7]));
				String Estimate_Value7s[]=estm.getEstimate_Value7().split(",");
				estm.setEstimate_Value7_1(Float.parseFloat(Estimate_Value7s[0]));
				estm.setEstimate_Value7_2(Float.parseFloat(Estimate_Value7s[1]));
				estm.setEstimate_Value7_3(Float.parseFloat(Estimate_Value7s[2]));
				estm.setEstimate_Value7_4(Float.parseFloat(Estimate_Value7s[3]));
				estm.setEstimate_Value7total(Float.parseFloat(Estimate_Value7s[0])+Float.parseFloat(Estimate_Value7s[1])+Float.parseFloat(Estimate_Value7s[2])+Float.parseFloat(Estimate_Value7s[3]));
				String Estimate_Value8s[]=estm.getEstimate_Value8().split(",");
				estm.setEstimate_Value8_1(Float.parseFloat(Estimate_Value8s[0]));
				estm.setEstimate_Value8_2(Float.parseFloat(Estimate_Value8s[1]));
				estm.setEstimate_Value8_3(Float.parseFloat(Estimate_Value8s[2]));
				estm.setEstimate_Value8_4(Float.parseFloat(Estimate_Value8s[3]));
				estm.setEstimate_Value8_5(Float.parseFloat(Estimate_Value8s[4]));
				estm.setEstimate_Value8_6(Float.parseFloat(Estimate_Value8s[5]));
				estm.setEstimate_Value8total(Float.parseFloat(Estimate_Value8s[0])+Float.parseFloat(Estimate_Value8s[1])+Float.parseFloat(Estimate_Value8s[2])+Float.parseFloat(Estimate_Value8s[3])+Float.parseFloat(Estimate_Value8s[4])+Float.parseFloat(Estimate_Value8s[5]));
				String Estimate_Value9s[]=estm.getEstimate_Value9().split(",");
				estm.setEstimate_Value9_1(Float.parseFloat(Estimate_Value9s[0]));
				estm.setEstimate_Value9_2(Float.parseFloat(Estimate_Value9s[1]));
				estm.setEstimate_Value9_3(Float.parseFloat(Estimate_Value9s[2]));
				estm.setEstimate_Value9_4(Float.parseFloat(Estimate_Value9s[3]));
				estm.setEstimate_Value9_5(Float.parseFloat(Estimate_Value9s[4]));
				estm.setEstimate_Value9total(Float.parseFloat(Estimate_Value9s[0])+Float.parseFloat(Estimate_Value9s[1])+Float.parseFloat(Estimate_Value9s[2])+Float.parseFloat(Estimate_Value9s[3])+Float.parseFloat(Estimate_Value9s[4]));
				String Estimate_Value10s[]=estm.getEstimate_Value10().split(",");
				estm.setEstimate_Value10_1(Float.parseFloat(Estimate_Value10s[0]));
				estm.setEstimate_Value10_2(Float.parseFloat(Estimate_Value10s[1]));
				estm.setEstimate_Value10_3(Float.parseFloat(Estimate_Value10s[2]));
				estm.setEstimate_Value10_4(Float.parseFloat(Estimate_Value10s[3]));
				estm.setEstimate_Value10total(Float.parseFloat(Estimate_Value10s[0])+Float.parseFloat(Estimate_Value10s[1])+Float.parseFloat(Estimate_Value10s[2])+Float.parseFloat(Estimate_Value10s[3]));
				String Estimate_Value11s[]=estm.getEstimate_Value11().split(",");
				estm.setEstimate_Value11_1(Float.parseFloat(Estimate_Value11s[0]));
				estm.setEstimate_Value11_2(Float.parseFloat(Estimate_Value11s[1]));
				estm.setEstimate_Value11total(Float.parseFloat(Estimate_Value11s[0])+Float.parseFloat(Estimate_Value11s[1]));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}		

		
		return estm;
	}
	
	
	
	/**
	 * 		查询指定年份全校所有考评报告记录
	 * @param year		年份
	 * @return			结果集
	 * @throws SQLException
	 */
	public List<EstimateModel> getSchoolTargetInfo(int year,int pageCount, int pageIndex) throws SQLException
	{
		List<EstimateModel>		list = new ArrayList<EstimateModel>();
		EstimateModel  estm = null;
		
		String sql = "select ESTIMATE_VALUE0,ESTIMATE_VALUE1,ESTIMATE_VALUE2,ESTIMATE_VALUE3," +
				"ESTIMATE_VALUE4,ESTIMATE_VALUE5,ESTIMATE_VALUE6,ESTIMATE_VALUE7,ESTIMATE_VALUE8," +
				"ESTIMATE_VALUE9,ESTIMATE_VALUE10,ESTIMATE_VALUE11,ESTIMATE_COUNT,ESTIMATE_NAME, " +
				"ESTIMATE_Style,ESTIMATE_ID" +
				" from estimate " +
				"where  ESTIMATE_year=? ";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, year);	
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
			
			
			while(rs.next()&&(count > 0))
			{
				estm = new EstimateModel();
				estm.setEstimate_Year(year);
				estm.setEstimate_ID(rs.getInt("ESTIMATE_ID"));
				estm.setEstimate_Index(rs.getString("ESTIMATE_Style"));
				estm.setEstimate_Name(rs.getString("ESTIMATE_NAME"));
				estm.setEstimate_Count(rs.getFloat("ESTIMATE_COUNT"));
				estm.setEstimate_Value0(rs.getString("ESTIMATE_VALUE0"));
				estm.setEstimate_Value1(rs.getString("ESTIMATE_VALUE1"));
				estm.setEstimate_Value2(rs.getString("ESTIMATE_VALUE2"));
				estm.setEstimate_Value3(rs.getString("ESTIMATE_VALUE3"));
				estm.setEstimate_Value4(rs.getString("ESTIMATE_VALUE4"));
				estm.setEstimate_Value5(rs.getString("ESTIMATE_VALUE5"));
				estm.setEstimate_Value6(rs.getString("ESTIMATE_VALUE6"));
				estm.setEstimate_Value7(rs.getString("ESTIMATE_VALUE7"));
				estm.setEstimate_Value8(rs.getString("ESTIMATE_VALUE8"));
				estm.setEstimate_Value9(rs.getString("ESTIMATE_VALUE9"));
				estm.setEstimate_Value10(rs.getString("ESTIMATE_VALUE10"));
				estm.setEstimate_Value11(rs.getString("ESTIMATE_VALUE11"));
				String Estimate_Value0s[]=estm.getEstimate_Value0().split(",");
				estm.setEstimate_Value0_1(Float.parseFloat(Estimate_Value0s[0]));
				estm.setEstimate_Value0_2(Float.parseFloat(Estimate_Value0s[1]));
				estm.setEstimate_Value0_3(Float.parseFloat(Estimate_Value0s[2]));
				estm.setEstimate_Value0_4(Float.parseFloat(Estimate_Value0s[3]));
				estm.setEstimate_Value0total(Float.parseFloat(Estimate_Value0s[0])+Float.parseFloat(Estimate_Value0s[1])+Float.parseFloat(Estimate_Value0s[2])+Float.parseFloat(Estimate_Value0s[3]));
				//System.out.println("eeee:"+estm.getEstimate_Value0total());
				String Estimate_Value1s[]=estm.getEstimate_Value1().split(",");
				estm.setEstimate_Value1_1(Float.parseFloat(Estimate_Value1s[0]));
				estm.setEstimate_Value1_2(Float.parseFloat(Estimate_Value1s[1]));
				estm.setEstimate_Value1_3(Float.parseFloat(Estimate_Value1s[2]));
				estm.setEstimate_Value1_4(Float.parseFloat(Estimate_Value1s[3]));
				estm.setEstimate_Value1_5(Float.parseFloat(Estimate_Value1s[4]));
				estm.setEstimate_Value1_6(Float.parseFloat(Estimate_Value1s[5]));
				estm.setEstimate_Value1_7(Float.parseFloat(Estimate_Value1s[6]));
				estm.setEstimate_Value1total(Float.parseFloat(Estimate_Value1s[0])+Float.parseFloat(Estimate_Value1s[1])+Float.parseFloat(Estimate_Value1s[2])+Float.parseFloat(Estimate_Value1s[3])+Float.parseFloat(Estimate_Value1s[4])+Float.parseFloat(Estimate_Value1s[5])+Float.parseFloat(Estimate_Value1s[6]));
				String Estimate_Value2s[]=estm.getEstimate_Value2().split(",");
				estm.setEstimate_Value2_1(Float.parseFloat(Estimate_Value2s[0]));
				estm.setEstimate_Value2_2(Float.parseFloat(Estimate_Value2s[1]));
				estm.setEstimate_Value2_3(Float.parseFloat(Estimate_Value2s[2]));
				estm.setEstimate_Value2_4(Float.parseFloat(Estimate_Value2s[3]));
				estm.setEstimate_Value2_5(Float.parseFloat(Estimate_Value2s[4]));
				estm.setEstimate_Value2_6(Float.parseFloat(Estimate_Value2s[5]));
				estm.setEstimate_Value2total(Float.parseFloat(Estimate_Value2s[0])+Float.parseFloat(Estimate_Value2s[1])+Float.parseFloat(Estimate_Value2s[2])+Float.parseFloat(Estimate_Value2s[3])+Float.parseFloat(Estimate_Value2s[4])+Float.parseFloat(Estimate_Value2s[5]));
				String Estimate_Value3s[]=estm.getEstimate_Value3().split(",");
				estm.setEstimate_Value3_1(Float.parseFloat(Estimate_Value3s[0]));
				estm.setEstimate_Value3_2(Float.parseFloat(Estimate_Value3s[1]));
				estm.setEstimate_Value3total(Float.parseFloat(Estimate_Value3s[0])+Float.parseFloat(Estimate_Value3s[1]));
				String Estimate_Value4s[]=estm.getEstimate_Value4().split(",");
				estm.setEstimate_Value4_1(Float.parseFloat(Estimate_Value4s[0]));
				estm.setEstimate_Value4_2(Float.parseFloat(Estimate_Value4s[1]));
				estm.setEstimate_Value4_3(Float.parseFloat(Estimate_Value4s[2]));
				estm.setEstimate_Value4total(Float.parseFloat(Estimate_Value4s[0])+Float.parseFloat(Estimate_Value4s[1])+Float.parseFloat(Estimate_Value4s[2]));
				String Estimate_Value5s[]=estm.getEstimate_Value5().split(",");
				estm.setEstimate_Value5_1(Float.parseFloat(Estimate_Value5s[0]));
				estm.setEstimate_Value5_2(Float.parseFloat(Estimate_Value5s[1]));
				estm.setEstimate_Value5_3(Float.parseFloat(Estimate_Value5s[2]));
				estm.setEstimate_Value5_4(Float.parseFloat(Estimate_Value5s[3]));
				estm.setEstimate_Value5_5(Float.parseFloat(Estimate_Value5s[4]));
				estm.setEstimate_Value5total(Float.parseFloat(Estimate_Value5s[0])+Float.parseFloat(Estimate_Value5s[1])+Float.parseFloat(Estimate_Value5s[2])+Float.parseFloat(Estimate_Value5s[3])+Float.parseFloat(Estimate_Value5s[4]));
				String Estimate_Value6s[]=estm.getEstimate_Value6().split(",");
				estm.setEstimate_Value6_1(Float.parseFloat(Estimate_Value6s[0]));
				estm.setEstimate_Value6_2(Float.parseFloat(Estimate_Value6s[1]));
				estm.setEstimate_Value6_3(Float.parseFloat(Estimate_Value6s[2]));
				estm.setEstimate_Value6_4(Float.parseFloat(Estimate_Value6s[3]));
				estm.setEstimate_Value6_5(Float.parseFloat(Estimate_Value6s[4]));
				estm.setEstimate_Value6_6(Float.parseFloat(Estimate_Value6s[5]));
				estm.setEstimate_Value6_7(Float.parseFloat(Estimate_Value6s[6]));
				estm.setEstimate_Value6_8(Float.parseFloat(Estimate_Value6s[7]));
				estm.setEstimate_Value6total(Float.parseFloat(Estimate_Value6s[0])+Float.parseFloat(Estimate_Value6s[1])+Float.parseFloat(Estimate_Value6s[2])+Float.parseFloat(Estimate_Value6s[3])+Float.parseFloat(Estimate_Value6s[4])+Float.parseFloat(Estimate_Value6s[5])+Float.parseFloat(Estimate_Value6s[6])+Float.parseFloat(Estimate_Value6s[7]));
				String Estimate_Value7s[]=estm.getEstimate_Value7().split(",");
				estm.setEstimate_Value7_1(Float.parseFloat(Estimate_Value7s[0]));
				estm.setEstimate_Value7_2(Float.parseFloat(Estimate_Value7s[1]));
				estm.setEstimate_Value7_3(Float.parseFloat(Estimate_Value7s[2]));
				estm.setEstimate_Value7_4(Float.parseFloat(Estimate_Value7s[3]));
				estm.setEstimate_Value7total(Float.parseFloat(Estimate_Value7s[0])+Float.parseFloat(Estimate_Value7s[1])+Float.parseFloat(Estimate_Value7s[2])+Float.parseFloat(Estimate_Value7s[3]));
				String Estimate_Value8s[]=estm.getEstimate_Value8().split(",");
				estm.setEstimate_Value8_1(Float.parseFloat(Estimate_Value8s[0]));
				estm.setEstimate_Value8_2(Float.parseFloat(Estimate_Value8s[1]));
				estm.setEstimate_Value8_3(Float.parseFloat(Estimate_Value8s[2]));
				estm.setEstimate_Value8_4(Float.parseFloat(Estimate_Value8s[3]));
				estm.setEstimate_Value8_5(Float.parseFloat(Estimate_Value8s[4]));
				estm.setEstimate_Value8_6(Float.parseFloat(Estimate_Value8s[5]));
				estm.setEstimate_Value8total(Float.parseFloat(Estimate_Value8s[0])+Float.parseFloat(Estimate_Value8s[1])+Float.parseFloat(Estimate_Value8s[2])+Float.parseFloat(Estimate_Value8s[3])+Float.parseFloat(Estimate_Value8s[4])+Float.parseFloat(Estimate_Value8s[5]));
				String Estimate_Value9s[]=estm.getEstimate_Value9().split(",");
				estm.setEstimate_Value9_1(Float.parseFloat(Estimate_Value9s[0]));
				estm.setEstimate_Value9_2(Float.parseFloat(Estimate_Value9s[1]));
				estm.setEstimate_Value9_3(Float.parseFloat(Estimate_Value9s[2]));
				estm.setEstimate_Value9_4(Float.parseFloat(Estimate_Value9s[3]));
				estm.setEstimate_Value9_5(Float.parseFloat(Estimate_Value9s[4]));
				estm.setEstimate_Value9total(Float.parseFloat(Estimate_Value9s[0])+Float.parseFloat(Estimate_Value9s[1])+Float.parseFloat(Estimate_Value9s[2])+Float.parseFloat(Estimate_Value9s[3])+Float.parseFloat(Estimate_Value9s[4]));
				String Estimate_Value10s[]=estm.getEstimate_Value10().split(",");
				estm.setEstimate_Value10_1(Float.parseFloat(Estimate_Value10s[0]));
				estm.setEstimate_Value10_2(Float.parseFloat(Estimate_Value10s[1]));
				estm.setEstimate_Value10_3(Float.parseFloat(Estimate_Value10s[2]));
				estm.setEstimate_Value10_4(Float.parseFloat(Estimate_Value10s[3]));
				estm.setEstimate_Value10total(Float.parseFloat(Estimate_Value10s[0])+Float.parseFloat(Estimate_Value10s[1])+Float.parseFloat(Estimate_Value10s[2])+Float.parseFloat(Estimate_Value10s[3]));
				String Estimate_Value11s[]=estm.getEstimate_Value11().split(",");
				estm.setEstimate_Value11_1(Float.parseFloat(Estimate_Value11s[0]));
				estm.setEstimate_Value11_2(Float.parseFloat(Estimate_Value11s[1]));
				estm.setEstimate_Value11total(Float.parseFloat(Estimate_Value11s[0])+Float.parseFloat(Estimate_Value11s[1]));
				list.add(estm);
				
				count--;
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
//		ps.setInt(1, year);
//	
//		
//		ResultSet rs = ps.executeQuery();
//		
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
//		
//		while(rs.next()&&(count > 0))
//		{
//			estm = new EstimateModel();
//			estm.setEstimate_Year(year);
//			estm.setEstimate_ID(rs.getInt("ESTIMATE_ID"));
//			estm.setEstimate_Index(rs.getString("ESTIMATE_Style"));
//			estm.setEstimate_Name(rs.getString("ESTIMATE_NAME"));
//			estm.setEstimate_Count(rs.getFloat("ESTIMATE_COUNT"));
//			estm.setEstimate_Value0(rs.getString("ESTIMATE_VALUE0"));
//			estm.setEstimate_Value1(rs.getString("ESTIMATE_VALUE1"));
//			estm.setEstimate_Value2(rs.getString("ESTIMATE_VALUE2"));
//			estm.setEstimate_Value3(rs.getString("ESTIMATE_VALUE3"));
//			estm.setEstimate_Value4(rs.getString("ESTIMATE_VALUE4"));
//			estm.setEstimate_Value5(rs.getString("ESTIMATE_VALUE5"));
//			estm.setEstimate_Value6(rs.getString("ESTIMATE_VALUE6"));
//			estm.setEstimate_Value7(rs.getString("ESTIMATE_VALUE7"));
//			estm.setEstimate_Value8(rs.getString("ESTIMATE_VALUE8"));
//			estm.setEstimate_Value9(rs.getString("ESTIMATE_VALUE9"));
//			estm.setEstimate_Value10(rs.getString("ESTIMATE_VALUE10"));
//			estm.setEstimate_Value11(rs.getString("ESTIMATE_VALUE11"));
//			String Estimate_Value0s[]=estm.getEstimate_Value0().split(",");
//			estm.setEstimate_Value0_1(Float.parseFloat(Estimate_Value0s[0]));
//			estm.setEstimate_Value0_2(Float.parseFloat(Estimate_Value0s[1]));
//			estm.setEstimate_Value0_3(Float.parseFloat(Estimate_Value0s[2]));
//			estm.setEstimate_Value0_4(Float.parseFloat(Estimate_Value0s[3]));
//			estm.setEstimate_Value0total(Float.parseFloat(Estimate_Value0s[0])+Float.parseFloat(Estimate_Value0s[1])+Float.parseFloat(Estimate_Value0s[2])+Float.parseFloat(Estimate_Value0s[3]));
//			//System.out.println("eeee:"+estm.getEstimate_Value0total());
//			String Estimate_Value1s[]=estm.getEstimate_Value1().split(",");
//			estm.setEstimate_Value1_1(Float.parseFloat(Estimate_Value1s[0]));
//			estm.setEstimate_Value1_2(Float.parseFloat(Estimate_Value1s[1]));
//			estm.setEstimate_Value1_3(Float.parseFloat(Estimate_Value1s[2]));
//			estm.setEstimate_Value1_4(Float.parseFloat(Estimate_Value1s[3]));
//			estm.setEstimate_Value1_5(Float.parseFloat(Estimate_Value1s[4]));
//			estm.setEstimate_Value1_6(Float.parseFloat(Estimate_Value1s[5]));
//			estm.setEstimate_Value1_7(Float.parseFloat(Estimate_Value1s[6]));
//			estm.setEstimate_Value1total(Float.parseFloat(Estimate_Value1s[0])+Float.parseFloat(Estimate_Value1s[1])+Float.parseFloat(Estimate_Value1s[2])+Float.parseFloat(Estimate_Value1s[3])+Float.parseFloat(Estimate_Value1s[4])+Float.parseFloat(Estimate_Value1s[5])+Float.parseFloat(Estimate_Value1s[6]));
//			String Estimate_Value2s[]=estm.getEstimate_Value2().split(",");
//			estm.setEstimate_Value2_1(Float.parseFloat(Estimate_Value2s[0]));
//			estm.setEstimate_Value2_2(Float.parseFloat(Estimate_Value2s[1]));
//			estm.setEstimate_Value2_3(Float.parseFloat(Estimate_Value2s[2]));
//			estm.setEstimate_Value2_4(Float.parseFloat(Estimate_Value2s[3]));
//			estm.setEstimate_Value2_5(Float.parseFloat(Estimate_Value2s[4]));
//			estm.setEstimate_Value2_6(Float.parseFloat(Estimate_Value2s[5]));
//			estm.setEstimate_Value2total(Float.parseFloat(Estimate_Value2s[0])+Float.parseFloat(Estimate_Value2s[1])+Float.parseFloat(Estimate_Value2s[2])+Float.parseFloat(Estimate_Value2s[3])+Float.parseFloat(Estimate_Value2s[4])+Float.parseFloat(Estimate_Value2s[5]));
//			String Estimate_Value3s[]=estm.getEstimate_Value3().split(",");
//			estm.setEstimate_Value3_1(Float.parseFloat(Estimate_Value3s[0]));
//			estm.setEstimate_Value3_2(Float.parseFloat(Estimate_Value3s[1]));
//			estm.setEstimate_Value3total(Float.parseFloat(Estimate_Value3s[0])+Float.parseFloat(Estimate_Value3s[1]));
//			String Estimate_Value4s[]=estm.getEstimate_Value4().split(",");
//			estm.setEstimate_Value4_1(Float.parseFloat(Estimate_Value4s[0]));
//			estm.setEstimate_Value4_2(Float.parseFloat(Estimate_Value4s[1]));
//			estm.setEstimate_Value4_3(Float.parseFloat(Estimate_Value4s[2]));
//			estm.setEstimate_Value4total(Float.parseFloat(Estimate_Value4s[0])+Float.parseFloat(Estimate_Value4s[1])+Float.parseFloat(Estimate_Value4s[2]));
//			String Estimate_Value5s[]=estm.getEstimate_Value5().split(",");
//			estm.setEstimate_Value5_1(Float.parseFloat(Estimate_Value5s[0]));
//			estm.setEstimate_Value5_2(Float.parseFloat(Estimate_Value5s[1]));
//			estm.setEstimate_Value5_3(Float.parseFloat(Estimate_Value5s[2]));
//			estm.setEstimate_Value5_4(Float.parseFloat(Estimate_Value5s[3]));
//			estm.setEstimate_Value5_5(Float.parseFloat(Estimate_Value5s[4]));
//			estm.setEstimate_Value5total(Float.parseFloat(Estimate_Value5s[0])+Float.parseFloat(Estimate_Value5s[1])+Float.parseFloat(Estimate_Value5s[2])+Float.parseFloat(Estimate_Value5s[3])+Float.parseFloat(Estimate_Value5s[4]));
//			String Estimate_Value6s[]=estm.getEstimate_Value6().split(",");
//			estm.setEstimate_Value6_1(Float.parseFloat(Estimate_Value6s[0]));
//			estm.setEstimate_Value6_2(Float.parseFloat(Estimate_Value6s[1]));
//			estm.setEstimate_Value6_3(Float.parseFloat(Estimate_Value6s[2]));
//			estm.setEstimate_Value6_4(Float.parseFloat(Estimate_Value6s[3]));
//			estm.setEstimate_Value6_5(Float.parseFloat(Estimate_Value6s[4]));
//			estm.setEstimate_Value6_6(Float.parseFloat(Estimate_Value6s[5]));
//			estm.setEstimate_Value6_7(Float.parseFloat(Estimate_Value6s[6]));
//			estm.setEstimate_Value6_8(Float.parseFloat(Estimate_Value6s[7]));
//			estm.setEstimate_Value6total(Float.parseFloat(Estimate_Value6s[0])+Float.parseFloat(Estimate_Value6s[1])+Float.parseFloat(Estimate_Value6s[2])+Float.parseFloat(Estimate_Value6s[3])+Float.parseFloat(Estimate_Value6s[4])+Float.parseFloat(Estimate_Value6s[5])+Float.parseFloat(Estimate_Value6s[6])+Float.parseFloat(Estimate_Value6s[7]));
//			String Estimate_Value7s[]=estm.getEstimate_Value7().split(",");
//			estm.setEstimate_Value7_1(Float.parseFloat(Estimate_Value7s[0]));
//			estm.setEstimate_Value7_2(Float.parseFloat(Estimate_Value7s[1]));
//			estm.setEstimate_Value7_3(Float.parseFloat(Estimate_Value7s[2]));
//			estm.setEstimate_Value7_4(Float.parseFloat(Estimate_Value7s[3]));
//			estm.setEstimate_Value7total(Float.parseFloat(Estimate_Value7s[0])+Float.parseFloat(Estimate_Value7s[1])+Float.parseFloat(Estimate_Value7s[2])+Float.parseFloat(Estimate_Value7s[3]));
//			String Estimate_Value8s[]=estm.getEstimate_Value8().split(",");
//			estm.setEstimate_Value8_1(Float.parseFloat(Estimate_Value8s[0]));
//			estm.setEstimate_Value8_2(Float.parseFloat(Estimate_Value8s[1]));
//			estm.setEstimate_Value8_3(Float.parseFloat(Estimate_Value8s[2]));
//			estm.setEstimate_Value8_4(Float.parseFloat(Estimate_Value8s[3]));
//			estm.setEstimate_Value8_5(Float.parseFloat(Estimate_Value8s[4]));
//			estm.setEstimate_Value8_6(Float.parseFloat(Estimate_Value8s[5]));
//			estm.setEstimate_Value8total(Float.parseFloat(Estimate_Value8s[0])+Float.parseFloat(Estimate_Value8s[1])+Float.parseFloat(Estimate_Value8s[2])+Float.parseFloat(Estimate_Value8s[3])+Float.parseFloat(Estimate_Value8s[4])+Float.parseFloat(Estimate_Value8s[5]));
//			String Estimate_Value9s[]=estm.getEstimate_Value9().split(",");
//			estm.setEstimate_Value9_1(Float.parseFloat(Estimate_Value9s[0]));
//			estm.setEstimate_Value9_2(Float.parseFloat(Estimate_Value9s[1]));
//			estm.setEstimate_Value9_3(Float.parseFloat(Estimate_Value9s[2]));
//			estm.setEstimate_Value9_4(Float.parseFloat(Estimate_Value9s[3]));
//			estm.setEstimate_Value9_5(Float.parseFloat(Estimate_Value9s[4]));
//			estm.setEstimate_Value9total(Float.parseFloat(Estimate_Value9s[0])+Float.parseFloat(Estimate_Value9s[1])+Float.parseFloat(Estimate_Value9s[2])+Float.parseFloat(Estimate_Value9s[3])+Float.parseFloat(Estimate_Value9s[4]));
//			String Estimate_Value10s[]=estm.getEstimate_Value10().split(",");
//			estm.setEstimate_Value10_1(Float.parseFloat(Estimate_Value10s[0]));
//			estm.setEstimate_Value10_2(Float.parseFloat(Estimate_Value10s[1]));
//			estm.setEstimate_Value10_3(Float.parseFloat(Estimate_Value10s[2]));
//			estm.setEstimate_Value10_4(Float.parseFloat(Estimate_Value10s[3]));
//			estm.setEstimate_Value10total(Float.parseFloat(Estimate_Value10s[0])+Float.parseFloat(Estimate_Value10s[1])+Float.parseFloat(Estimate_Value10s[2])+Float.parseFloat(Estimate_Value10s[3]));
//			String Estimate_Value11s[]=estm.getEstimate_Value11().split(",");
//			estm.setEstimate_Value11_1(Float.parseFloat(Estimate_Value11s[0]));
//			estm.setEstimate_Value11_2(Float.parseFloat(Estimate_Value11s[1]));
//			estm.setEstimate_Value11total(Float.parseFloat(Estimate_Value11s[0])+Float.parseFloat(Estimate_Value11s[1]));
//			list.add(estm);
//			
//			count--;
//			
//		}
		
		return list;
	}
	
	
	/**
	 * 		统计所有分数的总分
	 * @param estm	分数model
	 * @return		总分
	 */
	public  float  getTotalCount(EstimateModel  estm)
	{
		float count = 0;		//统计总分
		
		String Estimate_Value0s[]=estm.getEstimate_Value0().split(",");
		estm.setEstimate_Value0_1(Float.parseFloat(Estimate_Value0s[0]));
		estm.setEstimate_Value0_2(Float.parseFloat(Estimate_Value0s[1]));
		estm.setEstimate_Value0_3(Float.parseFloat(Estimate_Value0s[2]));
		estm.setEstimate_Value0_4(Float.parseFloat(Estimate_Value0s[3]));
		estm.setEstimate_Value0total(Float.parseFloat(Estimate_Value0s[0])+Float.parseFloat(Estimate_Value0s[1])+Float.parseFloat(Estimate_Value0s[2])+Float.parseFloat(Estimate_Value0s[3]));
		//System.out.println("eeee:"+estm.getEstimate_Value0total());
		String Estimate_Value1s[]=estm.getEstimate_Value1().split(",");
		estm.setEstimate_Value1_1(Float.parseFloat(Estimate_Value1s[0]));
		estm.setEstimate_Value1_2(Float.parseFloat(Estimate_Value1s[1]));
		estm.setEstimate_Value1_3(Float.parseFloat(Estimate_Value1s[2]));
		estm.setEstimate_Value1_4(Float.parseFloat(Estimate_Value1s[3]));
		estm.setEstimate_Value1_5(Float.parseFloat(Estimate_Value1s[4]));
		estm.setEstimate_Value1_6(Float.parseFloat(Estimate_Value1s[5]));
		estm.setEstimate_Value1_7(Float.parseFloat(Estimate_Value1s[6]));
		estm.setEstimate_Value1total(Float.parseFloat(Estimate_Value1s[0])+Float.parseFloat(Estimate_Value1s[1])+Float.parseFloat(Estimate_Value1s[2])+Float.parseFloat(Estimate_Value1s[3])+Float.parseFloat(Estimate_Value1s[4])+Float.parseFloat(Estimate_Value1s[5])+Float.parseFloat(Estimate_Value1s[6]));
		String Estimate_Value2s[]=estm.getEstimate_Value2().split(",");
		estm.setEstimate_Value2_1(Float.parseFloat(Estimate_Value2s[0]));
		estm.setEstimate_Value2_2(Float.parseFloat(Estimate_Value2s[1]));
		estm.setEstimate_Value2_3(Float.parseFloat(Estimate_Value2s[2]));
		estm.setEstimate_Value2_4(Float.parseFloat(Estimate_Value2s[3]));
		estm.setEstimate_Value2_5(Float.parseFloat(Estimate_Value2s[4]));
		estm.setEstimate_Value2_6(Float.parseFloat(Estimate_Value2s[5]));
		estm.setEstimate_Value2total(Float.parseFloat(Estimate_Value2s[0])+Float.parseFloat(Estimate_Value2s[1])+Float.parseFloat(Estimate_Value2s[2])+Float.parseFloat(Estimate_Value2s[3])+Float.parseFloat(Estimate_Value2s[4])+Float.parseFloat(Estimate_Value2s[5]));
		String Estimate_Value3s[]=estm.getEstimate_Value3().split(",");
		estm.setEstimate_Value3_1(Float.parseFloat(Estimate_Value3s[0]));
		estm.setEstimate_Value3_2(Float.parseFloat(Estimate_Value3s[1]));
		estm.setEstimate_Value3total(Float.parseFloat(Estimate_Value3s[0])+Float.parseFloat(Estimate_Value3s[1]));
		String Estimate_Value4s[]=estm.getEstimate_Value4().split(",");
		estm.setEstimate_Value4_1(Float.parseFloat(Estimate_Value4s[0]));
		estm.setEstimate_Value4_2(Float.parseFloat(Estimate_Value4s[1]));
		estm.setEstimate_Value4_3(Float.parseFloat(Estimate_Value4s[2]));
		estm.setEstimate_Value4total(Float.parseFloat(Estimate_Value4s[0])+Float.parseFloat(Estimate_Value4s[1])+Float.parseFloat(Estimate_Value4s[2]));
		String Estimate_Value5s[]=estm.getEstimate_Value5().split(",");
		estm.setEstimate_Value5_1(Float.parseFloat(Estimate_Value5s[0]));
		estm.setEstimate_Value5_2(Float.parseFloat(Estimate_Value5s[1]));
		estm.setEstimate_Value5_3(Float.parseFloat(Estimate_Value5s[2]));
		estm.setEstimate_Value5_4(Float.parseFloat(Estimate_Value5s[3]));
		estm.setEstimate_Value5_5(Float.parseFloat(Estimate_Value5s[4]));
		estm.setEstimate_Value5total(Float.parseFloat(Estimate_Value5s[0])+Float.parseFloat(Estimate_Value5s[1])+Float.parseFloat(Estimate_Value5s[2])+Float.parseFloat(Estimate_Value5s[3])+Float.parseFloat(Estimate_Value5s[4]));
		String Estimate_Value6s[]=estm.getEstimate_Value6().split(",");
		estm.setEstimate_Value6_1(Float.parseFloat(Estimate_Value6s[0]));
		estm.setEstimate_Value6_2(Float.parseFloat(Estimate_Value6s[1]));
		estm.setEstimate_Value6_3(Float.parseFloat(Estimate_Value6s[2]));
		estm.setEstimate_Value6_4(Float.parseFloat(Estimate_Value6s[3]));
		estm.setEstimate_Value6_5(Float.parseFloat(Estimate_Value6s[4]));
		estm.setEstimate_Value6_6(Float.parseFloat(Estimate_Value6s[5]));
		estm.setEstimate_Value6_7(Float.parseFloat(Estimate_Value6s[6]));
		estm.setEstimate_Value6_8(Float.parseFloat(Estimate_Value6s[7]));
		estm.setEstimate_Value6total(Float.parseFloat(Estimate_Value6s[0])+Float.parseFloat(Estimate_Value6s[1])+Float.parseFloat(Estimate_Value6s[2])+Float.parseFloat(Estimate_Value6s[3])+Float.parseFloat(Estimate_Value6s[4])+Float.parseFloat(Estimate_Value6s[5])+Float.parseFloat(Estimate_Value6s[6])+Float.parseFloat(Estimate_Value6s[7]));
		String Estimate_Value7s[]=estm.getEstimate_Value7().split(",");
		estm.setEstimate_Value7_1(Float.parseFloat(Estimate_Value7s[0]));
		estm.setEstimate_Value7_2(Float.parseFloat(Estimate_Value7s[1]));
		estm.setEstimate_Value7_3(Float.parseFloat(Estimate_Value7s[2]));
		estm.setEstimate_Value7_4(Float.parseFloat(Estimate_Value7s[3]));
		estm.setEstimate_Value7total(Float.parseFloat(Estimate_Value7s[0])+Float.parseFloat(Estimate_Value7s[1])+Float.parseFloat(Estimate_Value7s[2])+Float.parseFloat(Estimate_Value7s[3]));
		String Estimate_Value8s[]=estm.getEstimate_Value8().split(",");
		estm.setEstimate_Value8_1(Float.parseFloat(Estimate_Value8s[0]));
		estm.setEstimate_Value8_2(Float.parseFloat(Estimate_Value8s[1]));
		estm.setEstimate_Value8_3(Float.parseFloat(Estimate_Value8s[2]));
		estm.setEstimate_Value8_4(Float.parseFloat(Estimate_Value8s[3]));
		estm.setEstimate_Value8_5(Float.parseFloat(Estimate_Value8s[4]));
		estm.setEstimate_Value8_6(Float.parseFloat(Estimate_Value8s[5]));
		estm.setEstimate_Value8total(Float.parseFloat(Estimate_Value8s[0])+Float.parseFloat(Estimate_Value8s[1])+Float.parseFloat(Estimate_Value8s[2])+Float.parseFloat(Estimate_Value8s[3])+Float.parseFloat(Estimate_Value8s[4])+Float.parseFloat(Estimate_Value8s[5]));
		String Estimate_Value9s[]=estm.getEstimate_Value9().split(",");
		estm.setEstimate_Value9_1(Float.parseFloat(Estimate_Value9s[0]));
		estm.setEstimate_Value9_2(Float.parseFloat(Estimate_Value9s[1]));
		estm.setEstimate_Value9_3(Float.parseFloat(Estimate_Value9s[2]));
		estm.setEstimate_Value9_4(Float.parseFloat(Estimate_Value9s[3]));
		estm.setEstimate_Value9_5(Float.parseFloat(Estimate_Value9s[4]));
		estm.setEstimate_Value9total(Float.parseFloat(Estimate_Value9s[0])+Float.parseFloat(Estimate_Value9s[1])+Float.parseFloat(Estimate_Value9s[2])+Float.parseFloat(Estimate_Value9s[3])+Float.parseFloat(Estimate_Value9s[4]));
		String Estimate_Value10s[]=estm.getEstimate_Value10().split(",");
		estm.setEstimate_Value10_1(Float.parseFloat(Estimate_Value10s[0]));
		estm.setEstimate_Value10_2(Float.parseFloat(Estimate_Value10s[1]));
		estm.setEstimate_Value10_3(Float.parseFloat(Estimate_Value10s[2]));
		estm.setEstimate_Value10_4(Float.parseFloat(Estimate_Value10s[3]));
		estm.setEstimate_Value10total(Float.parseFloat(Estimate_Value10s[0])+Float.parseFloat(Estimate_Value10s[1])+Float.parseFloat(Estimate_Value10s[2])+Float.parseFloat(Estimate_Value10s[3]));
		String Estimate_Value11s[]=estm.getEstimate_Value11().split(",");
		estm.setEstimate_Value11_1(Float.parseFloat(Estimate_Value11s[0]));
		estm.setEstimate_Value11_2(Float.parseFloat(Estimate_Value11s[1]));
		estm.setEstimate_Value11total(Float.parseFloat(Estimate_Value11s[0])+Float.parseFloat(Estimate_Value11s[1]));
		
		count = estm.getEstimate_Value0total()+estm.getEstimate_Value1total()+estm.getEstimate_Value2total()
				+estm.getEstimate_Value3total()+estm.getEstimate_Value4total()+estm.getEstimate_Value5total()
				+estm.getEstimate_Value6total()+estm.getEstimate_Value7total()+estm.getEstimate_Value8total()
				+estm.getEstimate_Value9total()+estm.getEstimate_Value10total()+estm.getEstimate_Value11total(); 
		
		return count;
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
