package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import com.sf.energy.project.system.model.APManageModel;
import com.sf.energy.util.ConnDB;

public class APMangeDao
{
	/**
	 * 增
	 * @param slcm
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(APManageModel apmm) throws SQLException
	{
		String sql = "INSERT INTO PRICE(Price_Style,Price_Num,Price_Name,Price_Value,BJValue,LZValue,YBValue,EXFZ,EXFZSX,EXFZXX,AutoCut,EXFZHF,Limit,YBMonthList,TYDSD,QDYFF,AutoTZ,TZDL)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setString(1, apmm.getPriceStyle());
			ps.setString(2, apmm.getPriceNum());
			ps.setString(3, apmm.getPriceName());
			ps.setString(4, apmm.getPriceValue());
			ps.setString(5, apmm.getBJValue());
			ps.setString(6, apmm.getLZValue());
			ps.setString(7, apmm.getYBValue());
			ps.setString(8, apmm.getEXFZ());
			ps.setString(9, apmm.getEXFZSX());
			ps.setString(10, apmm.getEXFZXX());
			ps.setString(11, apmm.getAutoCut());
			ps.setString(12, apmm.getEXFZHF());
			ps.setString(13, apmm.getLimit());
			ps.setString(14, apmm.getYBMonthList());
			ps.setString(15, apmm.getTYDSD());
			ps.setString(16, apmm.getQDYFF());
			ps.setString(17, apmm.getAUTOTZ());
			ps.setString(18, apmm.getTZDL());
			
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
	 * 删
	 * @param PRICE_ID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int PRICE_ID) throws SQLException
	{
		String sql = "delete FROM PRICE where PRICE_ID =  " + PRICE_ID;
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
	 * 改
	 * @param slcm
	 * @return
	 * @throws SQLException
	 */
	public boolean modify(APManageModel apmm) throws SQLException
	{
		String sql = "UPDATE PRICE SET Price_Style=?,Price_Num=?,Price_Name=?,Price_Value=?,BJValue=?,LZValue=?,YBValue=?,EXFZ=?,EXFZSX=?," +
				"EXFZXX=?,AutoCut=?,EXFZHF=?,Limit=?,YBMonthList=?,TYDSD=?,QDYFF=?,AutoTZ=?,TZDL=? where PRICE_ID=" + apmm.getPriceID();
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);		
			ps.setString(1, apmm.getPriceStyle());
			ps.setString(2, apmm.getPriceNum());
			ps.setString(3, apmm.getPriceName());
			ps.setString(4, apmm.getPriceValue());
			ps.setString(5, apmm.getBJValue());
			ps.setString(6, apmm.getLZValue());
			ps.setString(7, apmm.getYBValue());
			ps.setString(8, apmm.getEXFZ());
			ps.setString(9, apmm.getEXFZSX());
			ps.setString(10, apmm.getEXFZXX());
			ps.setString(11, apmm.getAutoCut());
			ps.setString(12, apmm.getEXFZHF());
			ps.setString(13, apmm.getLimit());
			ps.setString(14, apmm.getYBMonthList());
			ps.setString(15, apmm.getTYDSD());
			ps.setString(16, apmm.getQDYFF());
			ps.setString(17, apmm.getAUTOTZ());
			ps.setString(18, apmm.getTZDL());
			
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
	 * 根据ID查询
	 * @param SLKONGZHI_ID
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public APManageModel queryByID(int PRICE_ID) throws SQLException, ParseException
	{
		APManageModel apmm = new APManageModel();
		
		DecimalFormat df=new DecimalFormat("0.00");
		DecimalFormat df1=new DecimalFormat("0.000");
		String sql = "Select * from PRICE where PRICE_ID=" + PRICE_ID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				apmm.setPriceID(PRICE_ID);
				apmm.setPriceStyle(rs.getString("Price_Style"));
				if(!"".equals(rs.getString("Price_Num")) && rs.getString("Price_Num") != null && !"null".equals(rs.getString("Price_Num")))
				{
					apmm.setPriceNum(rs.getString("Price_Num"));
				}else
				{
					apmm.setPriceNum("");
				}
				if(!"".equals(rs.getString("Price_Name")) && rs.getString("Price_Name") != null && !"null".equals(rs.getString("Price_Name")))
				{
					apmm.setPriceName(rs.getString("Price_Name"));
				}else
				{
					apmm.setPriceName("");
				}
				if(!"".equals(rs.getString("Price_Value")) && rs.getString("Price_Value") != null && !"null".equals(rs.getString("Price_Value")))
				{
					apmm.setPriceValue(df1.format(df1.parse(rs.getString("Price_Value"))));
				}else
				{
					apmm.setPriceValue("");
				}
				if(!"".equals(rs.getString("BJValue")) && rs.getString("BJValue") != null && !"null".equals(rs.getString("BJValue")))
				{
					apmm.setBJValue(df.format(df.parse(rs.getString("BJValue"))));
				}else
				{
					apmm.setBJValue("");
				}
				if(!"".equals(rs.getString("LZValue")) && rs.getString("LZValue") != null && !"null".equals(rs.getString("LZValue")))
				{
					apmm.setLZValue(df.format(df.parse(rs.getString("LZValue"))));
				}else
				{
					apmm.setLZValue("");
				}
				if(!"".equals(rs.getString("YBValue")) && rs.getString("YBValue") != null && !"null".equals(rs.getString("YBValue")))
				{
					apmm.setYBValue(df.format(df.parse(rs.getString("YBValue"))));
				}else
				{
					apmm.setYBValue("");
				}
				apmm.setEXFZ(rs.getString("EXFZ"));
				apmm.setEXFZSX(rs.getString("EXFZSX"));
				apmm.setEXFZXX(rs.getString("EXFZXX"));
				apmm.setAutoCut("");
				apmm.setEXFZHF(rs.getString("EXFZHF"));
				apmm.setLimit(rs.getString("Limit"));
				apmm.setYBMonthList(rs.getString("YBMonthList"));
				apmm.setTYDSD(rs.getString("TYDSD"));
				apmm.setQDYFF(rs.getString("QDYFF"));
				apmm.setAUTOTZ(rs.getString("AutoTZ"));
				apmm.setTZDL(rs.getString("TZDL"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return apmm;
	}
	
	/**
	 * 查询所有
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public ArrayList<APManageModel> queryAll() throws SQLException, ParseException
	{
		APManageModel apmm = null;
		ArrayList<APManageModel> list = new ArrayList<APManageModel>();
		
		String priceStyle = ""; 
		DecimalFormat df=new DecimalFormat("0.00");
		DecimalFormat df1=new DecimalFormat("0.000");
		
		String sql = "Select * from PRICE ORDER BY PRICE_ID";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				apmm = new APManageModel();
				apmm.setPriceID(rs.getInt("PRICE_ID"));
				
				priceStyle = rs.getString("Price_Style");
				if(!"".equals(priceStyle) && priceStyle != null && !"null".equals(priceStyle))
				{
					if("W".equals(priceStyle))
					{
						apmm.setPriceStyle("水费");
					} else
					{
						apmm.setPriceStyle("电费");
					}
				}else
				{
					apmm.setPriceStyle("");
				}
				if(!"".equals(rs.getString("Price_Num")) && rs.getString("Price_Num") != null && !"null".equals(rs.getString("Price_Num")))
				{
					apmm.setPriceNum(rs.getString("Price_Num"));
				}else
				{
					apmm.setPriceNum("");
				}
				if(!"".equals(rs.getString("Price_Name")) && rs.getString("Price_Name") != null && !"null".equals(rs.getString("Price_Name")))
				{
					apmm.setPriceName(rs.getString("Price_Name"));
				}else
				{
					apmm.setPriceName("");
				}
				if(!"".equals(rs.getString("Price_Value")) && rs.getString("Price_Value") != null && !"null".equals(rs.getString("Price_Value")))
				{
					apmm.setPriceValue(df1.format(df1.parse(rs.getString("Price_Value"))));
				}else
				{
					apmm.setPriceValue("");
				}
				if(!"".equals(rs.getString("BJValue")) && rs.getString("BJValue") != null && !"null".equals(rs.getString("BJValue")))
				{
					apmm.setBJValue(df.format(df.parse(rs.getString("BJValue"))));
				}else
				{
					apmm.setBJValue("");
				}
				if(!"".equals(rs.getString("LZValue")) && rs.getString("LZValue") != null && !"null".equals(rs.getString("LZValue")))
				{
					apmm.setLZValue(df.format(df.parse(rs.getString("LZValue"))));
				}else
				{
					apmm.setLZValue("");
				}
				if(!"".equals(rs.getString("YBValue")) && rs.getString("YBValue") != null && !"null".equals(rs.getString("YBValue")))
				{
					apmm.setYBValue(df.format(df.parse(rs.getString("YBValue"))));
				}else
				{
					apmm.setYBValue("");
				}
				apmm.setEXFZ(rs.getString("EXFZ"));
				apmm.setEXFZSX(rs.getString("EXFZSX"));
				apmm.setEXFZXX(rs.getString("EXFZXX"));
				apmm.setAutoCut("");
				apmm.setEXFZHF(rs.getString("EXFZHF"));
				apmm.setLimit(rs.getString("Limit"));
				apmm.setYBMonthList(rs.getString("YBMonthList"));
				apmm.setTYDSD(rs.getString("TYDSD"));
				apmm.setQDYFF(rs.getString("QDYFF"));
				apmm.setAUTOTZ(rs.getString("AutoTZ"));
				apmm.setTZDL(rs.getString("TZDL"));
				list.add(apmm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
	public boolean hasrepeatNum(String num)
	{
		boolean flag=false;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select Price_Num from Price where " + "(Price_Num = ? ) " + "and RowNum = 1";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, num);	
			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return flag;
	}
	
	public boolean hasrepeatEditNum(APManageModel apmm)
	{
		boolean flag=false;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select Price_Num from Price where " + "(Price_Num = ? ) " + "and RowNum = 1 and Price_id <> ? ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, apmm.getPriceNum());	
			ps.setInt(2, apmm.getPriceID());
			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return flag;
	}
	
	public boolean checkInfo(APManageModel apmm)
	{
		boolean flag = false;
		if((apmm.getPriceNum()==null||apmm.getPriceNum().equals(""))
				||(apmm.getPriceName()==null||apmm.getPriceName().equals(""))
				||(apmm.getPriceValue()==null||apmm.getPriceValue().equals(""))){
			flag=true;
		}
		return flag;
	}

}
