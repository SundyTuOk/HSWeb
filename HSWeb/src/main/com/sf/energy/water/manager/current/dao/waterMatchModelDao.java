package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.sf.energy.manager.monitor.dao.EnergyMatchModelDao;
import com.sf.energy.util.ConnDB;

public class waterMatchModelDao
{
	String WATERMETER_ID;
	String H0;
	String H1;
	String H2;
	String H3;
	String H4;
	String H5;
	String H6;
	String H7;
	String H8;
	String H9;
	String H10;
	String H11;
	String H12;
	String H13;
	String H14;
	String H15;
	String H16;
	String H17;
	String H18;
	String H19;
	String H20;
	String H21;
	String H22;
	String H23;
	String LASTCHECKTIME;
	String isCheck="-1";
	public waterMatchModelDao()
	{
		this.WATERMETER_ID="";
		this.H0="0";
		this.H1="0";
		this.H2="0";
		this.H3="0";
		this.H4="0";
		this.H5="0";
		this.H6="0";
		this.H7="0";
		this.H8="0";
		this.H9="0";
		this.H10="0";
		this.H11="0";
		this.H12="0";
		this.H13="0";
		this.H14="0";
		this.H15="0";
		this.H16="0";
		this.H17="0";
		this.H18="0";
		this.H19="0";
		this.H20="0";
		this.H21="0";
		this.H22="0";
		this.H23="0";
		this.LASTCHECKTIME="";
	}
	/**
	 * 插入用电匹配模型
	 * @return 修改的个数
	 * @throws SQLException
	 */
	public int addModel() throws SQLException
	{
		int result=0;
		String strSql="select * from watermatchmodel where WATERMETER_ID="+WATERMETER_ID;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				result=updateModel();
			}
			else
			{
				result=insertIntoModel();
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		
		strSql="update watermeter set COSTCHECK="+isCheck+" where WATERMETER_ID="+WATERMETER_ID;
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		boolean b = false;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(strSql);
			b = (ps2.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn2, ps2);
		}
		/*
		ps=ConnDB.getConnection().prepareStatement(strSql);
		int k=ps.executeUpdate();
		if(rs!=null)
			rs.close();
		if(ps!=null)
			ps.close();
		*/
		return result;	
	}
	
	public int insertIntoModel() throws SQLException
	{
		int result=0;
		String strSql="insert into watermatchmodel (WATERMETER_ID,H0,H1,H2,H3,H4,H5,H6,H7,H8,H9,H10,H11,H12,H13,H14,H15,H16,H17,H18,H19,H20,H21,H22,H23) values ("+
				WATERMETER_ID+","+H0+","+H1+","+H2+","+H3+","+H4+","+H5+","+H6+","+H7+","+H8+","+H9+","+H10
		+","+H11+","+H12+","+H13+","+H14+","+H15+","+H16+","+H17+","+H18+","+H19+","+H20+","+H21+","+H22+","+H23+")";
		
		PreparedStatement ps=ConnDB.getConnection().prepareStatement(strSql);
		result=ps.executeUpdate();
		
		if(ps!=null)
			ps.close();
		return result;
	}
	
	public int updateModel() throws SQLException
	{
		int result=0;
		String strSql="update watermatchmodel set H0="+H0+", H1="+H1+", H2="+H2+", H3="+H3+", H4="+H4+", H5="+H5+", H6="+H6+", H7="+H7+", H8="+H8+", H9="+H9+", H10="+H10+", H11="+H11
				+", H12="+H12+", H13="+H13+", H14="+H14+", H15="+H15+", H16="+H16+", H17="+H17+", H18="+H18+", H19="+H19
				+", H20="+H20+", H21="+H21+", H22="+H22+", H23="+H23+"where WATERMETER_ID="+WATERMETER_ID;
		
		Connection conn = null;
		PreparedStatement ps = null;
//		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			result = ps.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(strSql);
		result=ps.executeUpdate();
		
		if(ps!=null)
			ps.close();*/
		return result;
	}
	/**
	 * 通过表的ID 获得表的用电匹配模型
	 * @param ammeterID 电表ID
	 * @return 电表用电匹配模型
	 * @throws SQLException
	 */
	public waterMatchModelDao getModelByID(String waterMeterID) throws SQLException
	{
		DecimalFormat df=new DecimalFormat("0.##");
		String strSql="select * from watermatchmodel where WATERMETER_ID="+waterMeterID;
		waterMatchModelDao model=new waterMatchModelDao();
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				model.setH0(df.format(rs.getFloat("H0")));
				model.setH1(df.format(rs.getFloat("H1")));
				model.setH2(df.format(rs.getFloat("H2")));
				model.setH3(df.format(rs.getFloat("H3")));
				model.setH4(df.format(rs.getFloat("H4")));
				model.setH5(df.format(rs.getFloat("H5")));
				model.setH6(df.format(rs.getFloat("H6")));
				model.setH7(df.format(rs.getFloat("H7")));
				
				model.setH8(df.format(rs.getFloat("H8")));
				model.setH9(df.format(rs.getFloat("H9")));
				model.setH10(df.format(rs.getFloat("H10")));
				model.setH11(df.format(rs.getFloat("H11")));
				model.setH12(df.format(rs.getFloat("H12")));
				model.setH13(df.format(rs.getFloat("H13")));
				model.setH14(df.format(rs.getFloat("H14")));
				model.setH15(df.format(rs.getFloat("H15")));
				model.setH16(df.format(rs.getFloat("H16")));
				model.setH17(df.format(rs.getFloat("H17")));
				model.setH18(df.format(rs.getFloat("H18")));
				model.setH19(df.format(rs.getFloat("H19")));
				model.setH20(df.format(rs.getFloat("H20")));
				model.setH21(df.format(rs.getFloat("H21")));
				model.setH22(df.format(rs.getFloat("H22")));
				model.setH23(df.format(rs.getFloat("H23")));
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return model;
	}
	public String getWATERMETER_ID()
	{
		return WATERMETER_ID;
	}
	public void setWATERMETER_ID(String wATERMETER_ID)
	{
		WATERMETER_ID = wATERMETER_ID;
	}
	public String getH0()
	{
		return H0;
	}
	public void setH0(String h0)
	{
		H0 = h0;
	}
	public String getH1()
	{
		return H1;
	}
	public void setH1(String h1)
	{
		H1 = h1;
	}
	public String getH2()
	{
		return H2;
	}
	public void setH2(String h2)
	{
		H2 = h2;
	}
	public String getH3()
	{
		return H3;
	}
	public void setH3(String h3)
	{
		H3 = h3;
	}
	public String getH4()
	{
		return H4;
	}
	public void setH4(String h4)
	{
		H4 = h4;
	}
	public String getH5()
	{
		return H5;
	}
	public void setH5(String h5)
	{
		H5 = h5;
	}
	public String getH6()
	{
		return H6;
	}
	public void setH6(String h6)
	{
		H6 = h6;
	}
	public String getH7()
	{
		return H7;
	}
	public void setH7(String h7)
	{
		H7 = h7;
	}
	public String getH8()
	{
		return H8;
	}
	public void setH8(String h8)
	{
		H8 = h8;
	}
	public String getH9()
	{
		return H9;
	}
	public void setH9(String h9)
	{
		H9 = h9;
	}
	public String getH10()
	{
		return H10;
	}
	public void setH10(String h10)
	{
		H10 = h10;
	}
	public String getH11()
	{
		return H11;
	}
	public void setH11(String h11)
	{
		H11 = h11;
	}
	public String getH12()
	{
		return H12;
	}
	public void setH12(String h12)
	{
		H12 = h12;
	}
	public String getH13()
	{
		return H13;
	}
	public void setH13(String h13)
	{
		H13 = h13;
	}
	public String getH14()
	{
		return H14;
	}
	public void setH14(String h14)
	{
		H14 = h14;
	}
	public String getH15()
	{
		return H15;
	}
	public void setH15(String h15)
	{
		H15 = h15;
	}
	public String getH16()
	{
		return H16;
	}
	public void setH16(String h16)
	{
		H16 = h16;
	}
	public String getH17()
	{
		return H17;
	}
	public void setH17(String h17)
	{
		H17 = h17;
	}
	public String getH18()
	{
		return H18;
	}
	public void setH18(String h18)
	{
		H18 = h18;
	}
	public String getH19()
	{
		return H19;
	}
	public void setH19(String h19)
	{
		H19 = h19;
	}
	public String getH20()
	{
		return H20;
	}
	public void setH20(String h20)
	{
		H20 = h20;
	}
	public String getH21()
	{
		return H21;
	}
	public void setH21(String h21)
	{
		H21 = h21;
	}
	public String getH22()
	{
		return H22;
	}
	public void setH22(String h22)
	{
		H22 = h22;
	}
	public String getH23()
	{
		return H23;
	}
	public void setH23(String h23)
	{
		H23 = h23;
	}
	public String getLASTCHECKTIME()
	{
		return LASTCHECKTIME;
	}
	public void setLASTCHECKTIME(String lASTCHECKTIME)
	{
		LASTCHECKTIME = lASTCHECKTIME;
	}
	public String getIsCheck()
	{
		return isCheck;
	}
	public void setIsCheck(String isCheck)
	{
		this.isCheck = isCheck;
	}

	
	
	

}
