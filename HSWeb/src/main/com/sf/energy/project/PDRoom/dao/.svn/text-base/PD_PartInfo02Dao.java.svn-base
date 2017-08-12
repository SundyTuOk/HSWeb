package com.sf.energy.project.PDRoom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.project.PDRoom.model.PD_PartInfo02Model;
import com.sf.energy.util.ConnDB;

/**
 * 变压器Dao层
 * @author Administrator
 *
 */
public class PD_PartInfo02Dao {
	
	
	PD_PartInfo02Model partInfo02Model=null;
	List<PD_PartInfo02Model> list=new ArrayList<PD_PartInfo02Model>();
	
	/**
	 * 根据ID得到变压器
	 * @return
	 * @throws SQLException
	 */
	public PD_PartInfo02Model queryPD_PartInfo02(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select a.Part_ID,(select DictionaryValue_Value from DictionaryValue where PartStyle_ID=DictionaryValue_Num and Dictionary_ID=28) PartStyle,a.PartStyle_ID,a.Part_Parent,nvl((select Part_Name from PD_Part where Part_ID=a.Part_Parent),(select systemInfo_complayname from systeminfo where rownum=1)) parent_name,(select Part_IDS from PD_Part where Part_ID=a.Part_Parent) Part_IDS,a.State,a.Part_Num,a.Part_Name,a.Part_SCCJ,to_char(a.Part_TYRQ,'mm/dd/yyyy') Part_TYRQ,a.Part_Remark,b.PartInfo_ID,b.Man,b.ManPhone,b.GZPL,b.EDGL,b.EDDY,b.DYB,b.KZDL,b.KZSH,b.XL,b.DYSX,b.DYXX,b.XEDL,b.XEWG,b.GLYS  from PD_Part a inner join PD_PARTINFO02 b on   a.Part_ID=b.Part_ID where a.Part_ID="+id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				partInfo02Model=new PD_PartInfo02Model();
				
				partInfo02Model.setPart_ID(rs.getInt("Part_ID"));
				partInfo02Model.setPartStyle(rs.getString("PartStyle"));
				partInfo02Model.setPartStyle_ID(rs.getString("PartStyle_ID"));
				partInfo02Model.setPart_Parent(rs.getInt("Part_Parent"));
				partInfo02Model.setState(rs.getString("State"));
				partInfo02Model.setPart_Num(rs.getString("Part_Num"));
				partInfo02Model.setPart_Name(rs.getString("Part_Name"));
				partInfo02Model.setPart_SCCJ(rs.getString("Part_SCCJ"));
				partInfo02Model.setPart_TYRQ(rs.getString("Part_TYRQ"));
				partInfo02Model.setPart_Remark(rs.getString("Part_Remark"));
				
				partInfo02Model.setPartInfo_ID(rs.getInt("PartInfo_ID"));			
				partInfo02Model.setMan(rs.getString("Man"));
				partInfo02Model.setManPhone(rs.getString("ManPhone"));
				partInfo02Model.setGZPL(rs.getString("GZPL"));
				partInfo02Model.setEDGL(rs.getString("EDGL"));
				partInfo02Model.setEDDY(rs.getString("EDDY"));
				partInfo02Model.setDYB(rs.getString("DYB"));
				partInfo02Model.setKZDL(rs.getString("KZDL"));
				partInfo02Model.setKZSH(rs.getString("KZSH"));
				partInfo02Model.setXL(rs.getString("XL"));
				partInfo02Model.setDYSX(rs.getString("DYSX"));
				partInfo02Model.setDYXX(rs.getString("DYXX"));
				partInfo02Model.setXEDL(rs.getString("XEDL"));
				partInfo02Model.setXEWG(rs.getString("XEWG"));
				partInfo02Model.setGLYS(rs.getString("GLYS"));
				partInfo02Model.setPart_Parent_name(rs.getString("parent_name"));
				partInfo02Model.setPart_IDS(rs.getString("Part_IDS"));
				
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return partInfo02Model;
	}
	
	
	
	
	
	
	
	
	
	
	
   /**
    * 获取变压器的id
    * @return
    * @throws SQLException
    */
	
	
	public int getPD_PartInfo02Id() throws SQLException
	{
		int id=0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select max(PartInfo_ID) Id from PD_PARTINFO02";
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
	 * 删除变压器表里面的信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deletePD_PartInfo02(int id) throws SQLException
	{		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql="delete PD_PARTINFO02 where part_id in(select part_id from pd_part start with part_id='"+id+"' connect by prior part_id= part_parent) ";
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
	 * 增加表里面的信息
	 * @param partInfo02Model
	 * @return
	 * @throws SQLException
	 */
	public boolean addPD_PartInfo02(PD_PartInfo02Model partInfo02Model) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql="insert into PD_PartInfo02(PartInfo_ID,Part_ID,Man,ManPhone,GZPL,EDGL,EDDY,DYB,KZDL,KZSH,XL,DYSX,DYXX,XEDL,XEWG,GLYS) values('"
				     +partInfo02Model.getPartInfo_ID()+"','"+partInfo02Model.getPart_ID()+"','"
					 +partInfo02Model.getMan()+"','"+partInfo02Model.getManPhone()+"','"
				     +partInfo02Model.getGZPL()+"','"+partInfo02Model.getEDGL()+"','"+partInfo02Model.getEDDY()+"','"+partInfo02Model.getDYB()+"'," +
				     "'"+partInfo02Model.getKZDL()+"','"+partInfo02Model.getKZSH()+"','"+partInfo02Model.getXL()+"'," +
				     "'"+partInfo02Model.getDYSX()+"','"+partInfo02Model.getDYXX()+"','"+partInfo02Model.getXEDL()+"'," +
				     "'"+partInfo02Model.getXEWG()+"','"+partInfo02Model.getGLYS()+"')";
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
	 * 修改
	 * @param partInfo02Model
	 * @return
	 * @throws SQLException
	 */
	public boolean editPD_PartInfo02(PD_PartInfo02Model partInfo02Model) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b = false;
		try
		{
			String sql="update PD_PartInfo02 set Man='"+partInfo02Model.getMan()+"',ManPhone='"+partInfo02Model.getManPhone()
					  +"',GZPL='"+partInfo02Model.getGZPL()+"',EDGL='"+partInfo02Model.getEDGL()+"',EDDY='"+partInfo02Model.getEDDY()
					  +"',DYB='"+partInfo02Model.getDYB()+"',KZDL='"+partInfo02Model.getKZDL()+"',KZSH='"+partInfo02Model.getKZSH()
					  +"',XL='"+partInfo02Model.getXL()+"',DYSX='"+partInfo02Model.getDYSX()+"',DYXX='"+partInfo02Model.getDYXX()
					  +"',XEDL='"+partInfo02Model.getXEDL()+"',XEWG='"+partInfo02Model.getXEWG()+"',GLYS='"+partInfo02Model.getGLYS()
					  +"'where PART_ID="+partInfo02Model.getPart_ID();
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
