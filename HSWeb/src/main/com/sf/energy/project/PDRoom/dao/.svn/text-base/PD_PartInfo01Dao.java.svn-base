package com.sf.energy.project.PDRoom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.project.PDRoom.model.PD_PartInfo01Model;
import com.sf.energy.project.PDRoom.model.PD_PartModel;
import com.sf.energy.util.ConnDB;

public class PD_PartInfo01Dao {
	
	
	PD_PartModel partModel=null;
	
	
	/**
	 * 查询得到配电网的信息
	 * @return
	 * @throws SQLException
	 */
	public PD_PartModel queryPD_Part(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select a.Part_ID,(select DictionaryValue_Value from DictionaryValue where PartStyle_ID=DictionaryValue_Num and Dictionary_ID=28)PartStyle,a.PartStyle_ID,a.Part_Parent,nvl((select Part_Name from PD_Part where Part_ID=a.Part_Parent),(select systemInfo_complayname from systeminfo where rownum=1)) parent_name,a.State,a.Part_Num,(select Part_IDS from PD_Part where Part_ID=a.Part_Parent) Part_IDS,a.Part_Name,a.Part_SCCJ,to_char(a.Part_TYRQ,'mm/dd/yyyy') Part_TYRQ,a.Part_Remark,b.Address,b.DYLevel,b.XMLNAME from PD_Part a inner join PD_PARTINFO01 b on   a.Part_ID=b.Part_ID where a.Part_ID="+id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
			 partModel=new PD_PartModel();
			 partModel.setPart_ID(rs.getInt("Part_ID"));
			 partModel.setPartStyle(rs.getString("PartStyle"));
			 partModel.setPartStyle_ID(rs.getString("PartStyle_ID"));
			 partModel.setPart_Parent(rs.getInt("Part_Parent"));
			 partModel.setState(rs.getString("State"));
			 partModel.setPart_Num(rs.getString("Part_Num"));
			 partModel.setPart_Name(rs.getString("Part_Name"));
			 partModel.setPart_SCCJ(rs.getString("Part_SCCJ"));
			 partModel.setPart_TYRQ(rs.getString("Part_TYRQ"));
			 partModel.setPart_Remark(rs.getString("Part_Remark"));	
			// //System.out.println("地方撒旦"+rs.getString("Address"));
			 partModel.setAddress(rs.getString("Address"));
			 partModel.setDYLevel(rs.getString("DYLevel"));
			 partModel.setXMLNAME(rs.getString("XMLNAME"));
			 partModel.setPart_Parent_name(rs.getString("parent_name"));
			 partModel.setPart_IDS(rs.getString("Part_IDS"));	    
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return partModel;
	}
	/**
	 * 往配电房详细信息表里面增加信息
	 */	
	public boolean addPD_PartInfo01(PD_PartInfo01Model partInfo01Model) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql="insert into PD_PARTINFO01(PARTINFO_ID,PART_ID,ADDRESS,DYLEVEL,XMLNAME) values('"
				     +partInfo01Model.getPartInfo_ID()+"','"+partInfo01Model.getPart_ID()+"','"
					 +partInfo01Model.getAddress()+"','"+partInfo01Model.getDYLevel()+"','"
				     +partInfo01Model.getXMLNAME()+"')";
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
	 * 获取配电参数的Id
	 * @return
	 * @throws SQLException
	 */
	public int getPD_PartInfo01Id() throws SQLException
	{
		int id=0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select max(PARTINFO_ID) Id from PD_PARTINFO01";
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
	 * 删除配电房详细信息表里面的数据
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deletePD_PartInfo01(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql="delete PD_PARTINFO01 where part_id in (select part_id from pd_part start with part_id='"+id+"' connect by prior part_id= part_parent)";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate()== 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if(ps!=null){
			ps.close();
		}
		
		return b;
	}
	
	/**
	 * 对配电房详细信息进行修改
	 * @param partModel
	 * @return
	 * @throws SQLException 
	 */
	public boolean editPD_PartInfo01(PD_PartInfo01Model partInfo01Model) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql="update PD_PARTINFO01 set PART_ID='"+partInfo01Model.getPart_ID()+"',ADDRESS='"+partInfo01Model.getAddress()
					  +"',DYLEVEL='"+partInfo01Model.getDYLevel()+"',XMLNAME='"+partInfo01Model.getXMLNAME()
					  +"'where PART_ID="+partInfo01Model.getPart_ID();
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
