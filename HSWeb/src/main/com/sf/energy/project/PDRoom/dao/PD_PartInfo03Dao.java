package com.sf.energy.project.PDRoom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.project.PDRoom.model.PD_PartInfo03Model;
import com.sf.energy.util.ConnDB;

/**
 * 回路的Dao层
 * @author Administrator
 *
 */
public class PD_PartInfo03Dao {
	
	PD_PartInfo03Model partInfo03Model=null;
	
	
	
	/**
	 * 根据ID得到回路信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
			
	public PD_PartInfo03Model queryPD_PartInfo03(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select a.Part_ID,(select DictionaryValue_Value from DictionaryValue "
					+ "where PartStyle_ID=DictionaryValue_Num and Dictionary_ID=28) PartStyle,a.PartStyle_ID,a.Part_Parent"
					+ ",nvl((select Part_Name from PD_Part where Part_ID=a.Part_Parent),(select systemInfo_complayname from systeminfo where rownum=1)) parent_name,"
					+ "(select Part_IDS from PD_Part where Part_ID=a.Part_Parent) Part_IDS,a.State,a.Part_Num,a.Part_Name"
					+ ",a.Part_SCCJ,to_char(a.Part_TYRQ,'yyyy-mm-dd hh24:mi:ss') Part_TYRQ,a.Part_Remark,b.*  "
					+ "from PD_Part a inner join PD_PARTINFO03 b on   a.Part_ID=b.Part_ID where a.Part_ID="+id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				partInfo03Model=new PD_PartInfo03Model();
				
				partInfo03Model.setPart_ID(rs.getInt("Part_ID"));
			 	partInfo03Model.setPartStyle(rs.getString("PartStyle"));
				partInfo03Model.setPartStyle_ID(rs.getString("PartStyle_ID"));
				partInfo03Model.setPart_Parent(rs.getInt("Part_Parent"));
				partInfo03Model.setState(rs.getString("State"));
				partInfo03Model.setPart_Num(rs.getString("Part_Num"));
				partInfo03Model.setPart_Name(rs.getString("Part_Name"));
				partInfo03Model.setPart_SCCJ(rs.getString("Part_SCCJ"));
				partInfo03Model.setPart_TYRQ(rs.getString("Part_TYRQ"));
				partInfo03Model.setPart_Remark(rs.getString("Part_Remark"));
				partInfo03Model.setPart_Parent_name(rs.getString("parent_name"));
				partInfo03Model.setPart_IDS(rs.getString("Part_IDS"));
				
								
				partInfo03Model.setPartInfo_ID(rs.getInt("PartInfo_ID"));
				partInfo03Model.setJLLX(rs.getInt("JLLX"));
				partInfo03Model.setJLID(rs.getString("JLID"));
				partInfo03Model.setGZPL(rs.getDouble("GZPL"));
				partInfo03Model.setZEDGL(rs.getDouble("ZEDGL"));
				partInfo03Model.setAEDGL(rs.getDouble("AEDGL"));
				partInfo03Model.setBEDGL(rs.getDouble("BEDGL"));
				partInfo03Model.setCEDGL(rs.getDouble("CEDGL"));
				partInfo03Model.setEDDY(rs.getDouble("EDDY"));
				partInfo03Model.setDYB(rs.getDouble("DYB"));
				partInfo03Model.setKZDL(rs.getDouble("KZDL"));
				partInfo03Model.setKZSH(rs.getDouble("KZSH"));
				partInfo03Model.setXL(rs.getDouble("XL"));
				partInfo03Model.setADYSX(rs.getDouble("ADYSX"));
				partInfo03Model.setBDYSX(rs.getDouble("BDYSX"));
				partInfo03Model.setCDYSX(rs.getDouble("CDYSX"));
				partInfo03Model.setADYXX(rs.getDouble("ADYXX"));
				partInfo03Model.setBDYXX(rs.getDouble("BDYXX"));
				partInfo03Model.setCDYXX(rs.getDouble("CDYXX"));
				partInfo03Model.setAXEDL(rs.getDouble("AXEDL"));
				partInfo03Model.setBXEDL(rs.getDouble("BXEDL"));
				partInfo03Model.setCXEDL(rs.getDouble("CXEDL"));
				partInfo03Model.setZXEWG(rs.getDouble("ZXEWG"));
				partInfo03Model.setAXEWG(rs.getDouble("AXEWG"));
				partInfo03Model.setBXEWG(rs.getDouble("BXEWG"));
				partInfo03Model.setCXEWG(rs.getDouble("CXEWG"));
				partInfo03Model.setGLYS(rs.getDouble("GLYS"));
				partInfo03Model.setJiemian(rs.getDouble("Jiemian"));
				partInfo03Model.setLength(rs.getInt("Length"));
				partInfo03Model.setStartAddress(rs.getString("StartAddress"));
				partInfo03Model.setEndAddress(rs.getString("EndAddress"));
				partInfo03Model.setAutomaticCut(rs.getInt("AutomaticCut"));
				partInfo03Model.setDLBalance(rs.getDouble("DLBalance"));
				partInfo03Model.setDYBalance(rs.getDouble("DYBalance"));
				partInfo03Model.setYGBalance(rs.getDouble("YGBalance"));
				partInfo03Model.setWGBalance(rs.getDouble("WGBalance"));
				
				partInfo03Model.setISMULINE(rs.getDouble("ISMULINE"));
				partInfo03Model.setXEDLL(rs.getDouble("XEDLL"));
				partInfo03Model.setDYSXL(rs.getDouble("DYSXL"));
				partInfo03Model.setDYXXL(rs.getDouble("DYXXL"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return partInfo03Model;
	}
	
	
	
	
	
	
	
	/**
	 * 获取回路的插入ID
	 * @return
	 * @throws SQLException
	 */
	public int getPD_PartInfo03Id() throws SQLException
	{
		int id=0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select max(PartInfo_ID) Id from PD_PARTINFO03";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				id=rs.getInt("Id");			
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return id+1;
	}
	
	/**
	 * 删除回路中的记录
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deletePD_PartInfo03(int id) throws SQLException
	{		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql="delete PD_PARTINFO03 where part_id in(select part_id from pd_part start with part_id='"+id+"' connect by prior part_id= part_parent) ";	
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate()== 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	
	/**
	 * 增加回路表的信息
	 * @param partInfo02Model
	 * @return
	 * @throws SQLException
	 */
	public boolean addPD_PartInfo03(PD_PartInfo03Model partInfo03Model) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql="insert into PD_PartInfo03(PartInfo_ID,Part_ID,JLLX,JLID,GZPL,ZEDGL,AEDGL,BEDGL,CEDGL,EDDY,DYB,KZDL,KZSH,XL,ADYSX,BDYSX,CDYSX,ADYXX,BDYXX,CDYXX,AXEDL,BXEDL,CXEDL,ZXEWG,AXEWG,BXEWG,CXEWG,GLYS,Jiemian,Length,StartAddress,EndAddress,AutomaticCut,DLBalance,DYBalance,YGBalance,WGBalance,ISMULINE,XEDLL,DYSXL,DYXXL) values('"
				     +partInfo03Model.getPartInfo_ID()+"','"+partInfo03Model.getPart_ID()+"','"
					 +partInfo03Model.getJLLX()+"','"+partInfo03Model.getJLID()+"','"
				     +partInfo03Model.getGZPL()+"','"+partInfo03Model.getZEDGL()+"','"+partInfo03Model.getAEDGL()+"','"+partInfo03Model.getBEDGL()+"'," +
				     "'"+partInfo03Model.getCEDGL()+"','"+partInfo03Model.getEDDY()+"','"+partInfo03Model.getDYB()+"'," +
				     "'"+partInfo03Model.getKZDL()+"','"+partInfo03Model.getKZSH()+"','"+partInfo03Model.getXL()+"'," +
				     "'"+partInfo03Model.getADYSX()+"','"+partInfo03Model.getBDYSX()+"','"+partInfo03Model.getCDYSX()+"','"+partInfo03Model.getADYXX()+"','"
				     +partInfo03Model.getBDYXX()+"','"+partInfo03Model.getCDYXX()+"','"+partInfo03Model.getAXEDL()+"','"+partInfo03Model.getBXEDL()+"','"
				     +partInfo03Model.getCXEDL()+"','"+partInfo03Model.getZXEWG()+"','"+partInfo03Model.getAXEWG()+"','"+partInfo03Model.getBXEWG()+"','"
				     +partInfo03Model.getCXEWG()+"','"+partInfo03Model.getGLYS()+"','"+partInfo03Model.getJiemian()+"','"+partInfo03Model.getLength()+"','"
				     +partInfo03Model.getStartAddress()+"','"+partInfo03Model.getEndAddress()+"','"+partInfo03Model.getAutomaticCut()+"','"
				     +partInfo03Model.getDLBalance()+"','"+partInfo03Model.getDYBalance()+"','"+partInfo03Model.getYGBalance()+"','"
				     +partInfo03Model.getWGBalance()+"','"+partInfo03Model.getISMULINE()+"','"+partInfo03Model.getXEDLL()+"','"+partInfo03Model.getDYSXL()+"','"+partInfo03Model.getDYXXL()+"')";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate()== 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	
	/**
	 * 修改回路表信息
	 * @param partInfo03Model
	 * @return
	 * @throws SQLException 
	 */
	public boolean editPD_PartInfo03(PD_PartInfo03Model partInfo03Model) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b = false;
		try
		{
			String sql="update PD_PartInfo03 set JLLX='"+partInfo03Model.getJLLX()+"',JLID='"+partInfo03Model.getJLID()
					  +"',GZPL='"+partInfo03Model.getGZPL()+"',ZEDGL='"+partInfo03Model.getZEDGL()+"',AEDGL='"+partInfo03Model.getAEDGL()
					  +"',BEDGL='"+partInfo03Model.getBEDGL()+"',CEDGL='"+partInfo03Model.getCEDGL()+"',EDDY='"+partInfo03Model.getEDDY()
					  +"',DYB='"+partInfo03Model.getDYB()+"',KZDL='"+partInfo03Model.getKZDL()+"',KZSH='"+partInfo03Model.getKZSH()
					  +"',XL='"+partInfo03Model.getXL()+"',ADYSX='"+partInfo03Model.getADYSX()+"',BDYSX='"+partInfo03Model.getBDYSX()
					  +"',CDYSX='"+partInfo03Model.getCDYSX()+"',ADYXX='"+partInfo03Model.getADYXX()+"',BDYXX='"+partInfo03Model.getBDYXX()
					  +"',CDYXX='"+partInfo03Model.getCDYXX()+"',AXEDL='"+partInfo03Model.getAXEDL()+"',BXEDL='"+partInfo03Model.getBXEDL()
					  +"',CXEDL='"+partInfo03Model.getCXEDL()+"',ZXEWG='"+partInfo03Model.getZXEWG()+"',AXEWG='"+partInfo03Model.getAXEWG()
					  +"',BXEWG='"+partInfo03Model.getBXEWG()+"',CXEWG='"+partInfo03Model.getCXEWG()+"',GLYS='"+partInfo03Model.getGLYS()
					  +"',Jiemian='"+partInfo03Model.getJiemian()+"',Length='"+partInfo03Model.getLength()+"',StartAddress='"+partInfo03Model.getStartAddress()
					  +"',EndAddress='"+partInfo03Model.getEndAddress()+"',AutomaticCut='"+partInfo03Model.getAutomaticCut()+"',DLBalance='"+partInfo03Model.getDLBalance()
					  +"',DYBalance='"+partInfo03Model.getDYBalance()+"',YGBalance='"+partInfo03Model.getYGBalance()+"',WGBalance='"+partInfo03Model.getWGBalance()
					  +"',ISMULINE='"+partInfo03Model.getISMULINE()+"',XEDLL='"+partInfo03Model.getXEDLL()+"',DYSXL='"+partInfo03Model.getDYSXL()+"',DYXXL='"+partInfo03Model.getDYXXL()
					  +"' where PART_ID="+partInfo03Model.getPart_ID();
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate()== 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
}
