package com.sf.energy.project.PDRoom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sf.energy.project.PDRoom.model.PD_PartInfo02Model;
import com.sf.energy.project.PDRoom.model.PD_PartInfo03Model;
import com.sf.energy.project.PDRoom.model.PD_PartModel;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.util.ConnDB;

public class PD_PartDao {
	PD_PartModel partModel=null;
	//配电房记录数
	private int totalCount01= 0;
	//变压器记录数
	private int totalCount02= 0;
	//回路记录数
	private int totalCount03= 0;
	
	
	public int getTotalCount01() {
		return totalCount01;
	}

	public void setTotalCount01(int totalCount01) {
		this.totalCount01 = totalCount01;
	}

	public int getTotalCount02() {
		return totalCount02;
	}

	public void setTotalCount02(int totalCount02) {
		this.totalCount02 = totalCount02;
	}

	public int getTotalCount03() {
		return totalCount03;
	}

	public void setTotalCount03(int totalCount03) {
		this.totalCount03 = totalCount03;
	}

	/**
	 * 查询得到配电网的信息
	 * @return
	 * @throws SQLException
	 */
	public List<PD_PartModel> queryPD_Part(int pageCount,int pageIndex,int Pd_Parent) throws SQLException
	{	
		String strWhereString="";
		ArrayList<PD_PartModel> list=new ArrayList<PD_PartModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			if(Pd_Parent!=-1)
			{
				strWhereString=" and a.Part_ID in (SELECT part_id FROM pd_part START WITH part_parent="+Pd_Parent+"CONNECT BY PRIOR part_id = part_parent)";
			}
			String sql="select a.Part_ID,(select DictionaryValue_Value from DictionaryValue where PartStyle_ID=DictionaryValue_Num and Dictionary_ID=28)PartStyle,a.PartStyle_ID,a.Part_Parent,nvl((select Part_Name from PD_Part where Part_ID=a.Part_Parent),(select systemInfo_complayname from systeminfo where rownum=1)) parent_name,a.State,a.Part_Num,(select Part_IDS from PD_Part where Part_ID=a.Part_Parent) Part_IDS,a.Part_Name,a.Part_SCCJ,to_char(a.Part_TYRQ,'yyyy-mm-dd') Part_TYRQ,a.Part_Remark,b.Address,b.DYLevel,b.XMLNAME from PD_Part a left join PD_PARTINFO01 b on   a.Part_ID=b.Part_ID where a.PARTSTYLE_ID='01' "+strWhereString+" order by a.Part_ID desc";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			//得到总记录数
			rs.last();
			int count = rs.getRow();
//	System.out.println("加载数据：count:"+count);
			setTotalCount01(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);
			
			while(rs.next() && (count > 0))			
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
			
			 partModel.setAddress(rs.getString("Address"));
			 partModel.setDYLevel(rs.getString("DYLevel"));
			 partModel.setXMLNAME(rs.getString("XMLNAME"));
			 partModel.setPart_Parent_name(rs.getString("parent_name"));
			 partModel.setPart_IDS(rs.getString("Part_IDS"));
			 list.add(partModel);
			 count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
	/**
	 * 对配电参数进行修改
	 * @param partModel
	 * @return
	 * @throws SQLException 
	 */
	public boolean editPD_Part(PD_PartModel partModel) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql="update PD_Part set Part_ID='"+partModel.getPart_ID()
					  +"',Part_Parent='"+partModel.getPart_Parent()
					  +"',State='"+partModel.getState()+"',Part_Num='"+partModel.getPart_Num()
					  +"',Part_Name='"+partModel.getPart_Name()
					  +"',Part_SCCJ='"+partModel.getPart_SCCJ()+"',Part_TYRQ=to_date('"+partModel.getPart_TYRQ()+"','yyyy-mm-dd'),Part_Remark='"+partModel.getPart_Remark() +"'where Part_ID="+partModel.getPart_ID();
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
	 * 增加记录
	 * @param partModel
	 * @return
	 * @throws SQLException 
	 */
	
	public boolean addPD_Part(PD_PartModel partModel) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
	
		boolean b =
		false;
		try
		{
			String sql="insert into PD_Part(Part_ID,PartStyle_ID,Part_Parent,State,Part_Num,Part_Name,Part_SCCJ,Part_TYRQ,Part_Remark) values('"
				     +partModel.getPart_ID()+"','"+partModel.getPartStyle_ID()+"','"
					 +partModel.getPart_Parent()+"','"+partModel.getState()+"','"
				     +partModel.getPart_Num()+"','"
				     +partModel.getPart_Name()+"','"
					 +partModel.getPart_SCCJ()+"',to_date('"+partModel.getPart_TYRQ()+"','yyyy-mm-dd'),'"
				     +partModel.getPart_Remark()+"')";
	
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
	 * 删除配电信息表里面的数据
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean deletePD_Part(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql="delete pd_part where part_id in(select part_id from pd_part start with part_id='"+id+"' connect by prior part_id= part_parent) ";
			PD_PartInfo01Dao partInfo01Dao=new PD_PartInfo01Dao();
			PD_PartInfo02Dao partInfo02Dao=new PD_PartInfo02Dao();
			PD_PartInfo03Dao partInfo03Dao=new PD_PartInfo03Dao();
			
			partInfo01Dao.deletePD_PartInfo01(id);
			partInfo02Dao.deletePD_PartInfo02(id);
			partInfo03Dao.deletePD_PartInfo03(id);
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
	 * 批量设置回路的父节点
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public boolean SetPD_Parent(String Pd_Parent,String theID) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql="update pd_part set Part_Parent="+Pd_Parent+" where part_id in("+theID+") and part_id not in("+Pd_Parent+")";
	
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
		
			b = (ps.executeUpdate()> 0);
		
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
		
		return b;
	}
	/**李戬
	 * 查询 返回记录条数 01 配电房管理
	 * @param Pd_Parent
	 * @return
	 * @throws SQLException
	 */
	public int QueryPD_Part01Num(int Pd_Parent) throws SQLException{
		int count=0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String strWhereString="";
			if(Pd_Parent!=-1)
			{
				strWhereString=" and a.Part_ID in (SELECT part_id FROM pd_part START WITH part_parent="+Pd_Parent+"CONNECT BY PRIOR part_id = part_parent)";
			}
			String sql="select a.Part_ID,(select DictionaryValue_Value from DictionaryValue where PartStyle_ID=DictionaryValue_Num and Dictionary_ID=28)PartStyle,a.PartStyle_ID,a.Part_Parent,nvl((select Part_Name from PD_Part where Part_ID=a.Part_Parent),(select systemInfo_complayname from systeminfo where rownum=1)) parent_name,a.State,a.Part_Num,(select Part_IDS from PD_Part where Part_ID=a.Part_Parent) Part_IDS,a.Part_Name,a.Part_SCCJ,to_char(a.Part_TYRQ,'yyyy-mm-dd') Part_TYRQ,a.Part_Remark,b.Address,b.DYLevel,b.XMLNAME from PD_Part a left join PD_PARTINFO01 b on   a.Part_ID=b.Part_ID where a.PARTSTYLE_ID='01' "+strWhereString+" order by a.Part_ID desc";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			count = rs.getRow();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return count;
	}
	
	/**李戬
	 * 查询 返回记录条数 02 变压器管理
	 * @param Pd_Parent
	 * @return
	 * @throws SQLException
	 */
	public int QueryPD_Part02Num(int Pd_Parent) throws SQLException{
		int count=0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String strWhere="";
			if(Pd_Parent!=-1)
			{
				strWhere=" and a.Part_ID in (SELECT part_id FROM pd_part START WITH part_parent="+Pd_Parent+"CONNECT BY PRIOR part_id = part_parent)";
			}
			String sql="select a.Part_ID,(select DictionaryValue_Value from DictionaryValue where PartStyle_ID=DictionaryValue_Num and Dictionary_ID=28) PartStyle,a.PartStyle_ID,a.Part_Parent,nvl((select Part_Name from PD_Part where Part_ID=a.Part_Parent),(select systemInfo_complayname from systeminfo where rownum=1)) parent_name,(select Part_IDS from PD_Part where Part_ID=a.Part_Parent) Part_IDS,a.State,a.Part_Num,a.Part_Name,a.Part_SCCJ,to_char(a.Part_TYRQ,'yyyy-mm-dd') Part_TYRQ,a.Part_Remark,b.PartInfo_ID,b.Man,b.ManPhone,b.GZPL,b.EDGL,b.EDDY,b.DYB,b.KZDL,b.KZSH,b.XL,b.DYSX,b.DYXX,b.XEDL,b.XEWG,b.GLYS  from PD_Part a left join PD_PARTINFO02 b on   a.Part_ID=b.Part_ID where a.PARTSTYLE_ID='02' "+strWhere+" order by a.Part_ID desc";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			count = rs.getRow();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return count;
	}
	
	/**李戬
	 * 查询 返回记录条数 03回路管理
	 * @param Pd_Parent
	 * @return
	 * @throws SQLException
	 */
	public int QueryPD_Part03Num(int Pd_Parent) throws SQLException{
		int count=0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String strWhere="";
			if(Pd_Parent!=-1)
			{
				strWhere=" and a.Part_ID in (SELECT part_id FROM pd_part START WITH part_parent="+Pd_Parent+"CONNECT BY PRIOR part_id = part_parent)";
			}
			String sql="select a.Part_ID,(select DictionaryValue_Value from DictionaryValue where PartStyle_ID=DictionaryValue_Num and Dictionary_ID=28) PartStyle,a.PartStyle_ID,a.Part_Parent,nvl((select Part_Name from PD_Part where Part_ID=a.Part_Parent),(select systemInfo_complayname from systeminfo where rownum=1)) parent_name,(select Part_IDS from PD_Part where Part_ID=a.Part_Parent) Part_IDS,a.State,a.Part_Num,a.Part_Name,a.Part_SCCJ,to_char(a.Part_TYRQ,'yyyy-mm-dd') Part_TYRQ,a.Part_Remark,b.*  from PD_Part a left join PD_PARTINFO03 b on   a.Part_ID=b.Part_ID where a.PARTSTYLE_ID='03' "+strWhere+"order by a.Part_ID desc";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			count = rs.getRow();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return count;
	}
	
	
	/**
	 * 获取配电参数的Id
	 * @return
	 * @throws SQLException
	 */
	public int getPD_PartId() throws SQLException
	{
		int id=0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select max(Part_ID) Id from PD_Part";
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
	 * 获得树的值
	 * @param style 树的类型 2代表 有配电房和回路不存在变压器，3代表只有回路
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PD_PartModel> makeTree(int style) throws SQLException
	{
		ArrayList<PD_PartModel> treelist=new ArrayList<PD_PartModel>();
		
		
		if(style==2)
		{
			treelist=queryPD_PartSelect();
			treelist=removetree02(treelist);
		}
		else if(style==3)
		{
			treelist=queryPD_PartSelect();
			treelist=removetree03(treelist);
		}	
		else if(style==4)
		{
			
			treelist=queryPD_Part01Select();
		}
		else if(style==5)
		{
			
			treelist=queryPD_Part02Select();
		}
		else {
			treelist=queryPD_PartSelect();
		}
		return treelist;
	}
	
	private ArrayList<PD_PartModel> removetree03(
			ArrayList<PD_PartModel> treelist)
	{
		
		int partID;
		int parentId;
		String style;
		String removeID="";
		for(int i=0;i<treelist.size();i++)
		{
			PD_PartModel model=treelist.get(i);
			partID=model.getPart_ID();
			parentId=model.getPart_Parent();
			style=model.getPartStyle_ID();
			if("02".equals(style)||"01".equals(style))
			{
				removeID+=i+"|";
				for(int j=0;j<treelist.size();j++)
				{
					PD_PartModel updatemodel=treelist.get(j);
					int updateID=updatemodel.getPart_Parent();
					if(updateID==partID)
					{
						updatemodel.setPart_Parent(parentId);
						treelist.set(j, updatemodel);
					}	
				}
			}
		}
		String[] remove=removeID.split("\\|");
		for(int k=0;k<remove.length;k++)
		{
			int index=Integer.parseInt(remove[k])-k;
			treelist.remove(index);
		}
		return treelist;
	}
	private ArrayList<PD_PartModel> removetree04(
			ArrayList<PD_PartModel> treelist)
	{
		
		int partID;
		int parentId;
		String style;
		String removeID="";
		for(int i=0;i<treelist.size();i++)
		{
			PD_PartModel model=treelist.get(i);
			partID=model.getPart_ID();
			parentId=model.getPart_Parent();
			style=model.getPartStyle_ID();
			if("02".equals(style)||"03".equals(style))
			{
				removeID+=i+"|";
				for(int j=0;j<treelist.size();j++)
				{
					PD_PartModel updatemodel=treelist.get(j);
					int updateID=updatemodel.getPart_Parent();
					if(updateID==partID)
					{
						updatemodel.setPart_Parent(parentId);
						treelist.set(j, updatemodel);
					}	
				}
			}
		}
		String[] remove=removeID.split("\\|");
		for(int k=0;k<remove.length;k++)
		{
			int index=Integer.parseInt(remove[k])-k;
			treelist.remove(index);
		}
		return treelist;
	}

	private ArrayList<PD_PartModel> removetree02(ArrayList<PD_PartModel> treelist)
	{
		
		int partID;
		int parentId;
		String style;
		String removeID="";
		for(int i=0;i<treelist.size();i++)
		{
			PD_PartModel model=treelist.get(i);
			partID=model.getPart_ID();
			parentId=model.getPart_Parent();
			style=model.getPartStyle_ID();
			if("02".equals(style))
			{
				removeID+=i+"|";
				for(int j=0;j<treelist.size();j++)
				{
					PD_PartModel updatemodel=treelist.get(j);
					int updateID=updatemodel.getPart_Parent();
					if(updateID==partID)
					{
						updatemodel.setPart_Parent(parentId);
						treelist.set(j, updatemodel);
					}	
				}
			}
		}
		String[] remove=removeID.split("\\|");
		for(int k=0;k<remove.length;k++)
		{
			int index=Integer.parseInt(remove[k])-k;
			treelist.remove(index);
		}
		return treelist;
	}

	/**
	 * 获取选择列表下拉框的值
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PD_PartModel> queryPD_PartSelect() throws SQLException
	{
		ArrayList<PD_PartModel> list=new ArrayList<PD_PartModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			String sql="select Part_ID,PartStyle_ID,Part_Parent,Part_Name,Part_IDS from PD_Part order by Part_Parent,Part_Name ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
			 partModel=new PD_PartModel();
			 partModel.setPart_ID(rs.getInt("Part_ID"));
			
			 partModel.setPartStyle_ID(rs.getString("PartStyle_ID"));
			 partModel.setPart_Parent(rs.getInt("Part_Parent"));
			
			 partModel.setPart_Name(rs.getString("Part_Name"));

			 partModel.setPart_IDS(rs.getString("Part_IDS"));
			
			 list.add(partModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{	
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
	/**
	 * 获取选择列表下拉框的值
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PD_PartModel> queryPD_Part01Select() throws SQLException
	{
		ArrayList<PD_PartModel> list=new ArrayList<PD_PartModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			String sql="select Part_ID,PartStyle_ID,Part_Parent,Part_Name,Part_IDS from PD_Part where PartStyle_ID='01' order by Part_Name ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
			 partModel=new PD_PartModel();
			 partModel.setPart_ID(rs.getInt("Part_ID"));
			
			 partModel.setPartStyle_ID(rs.getString("PartStyle_ID"));
			 partModel.setPart_Parent(rs.getInt("Part_Parent"));
			
			 partModel.setPart_Name(rs.getString("Part_Name"));

			 partModel.setPart_IDS(rs.getString("Part_IDS"));
			
			 list.add(partModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{	
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	/**
	 * 获取选择列表下拉框的值变压器
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PD_PartModel> queryPD_Part02Select() throws SQLException
	{
		ArrayList<PD_PartModel> list=new ArrayList<PD_PartModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			String sql="select Part_ID,PartStyle_ID,Part_Parent,Part_Name,Part_IDS from PD_Part where PartStyle_ID='02' order by Part_Name ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
			 partModel=new PD_PartModel();
			 partModel.setPart_ID(rs.getInt("Part_ID"));
			
			 partModel.setPartStyle_ID(rs.getString("PartStyle_ID"));
			 partModel.setPart_Parent(rs.getInt("Part_Parent"));
			
			 partModel.setPart_Name(rs.getString("Part_Name"));

			 partModel.setPart_IDS(rs.getString("Part_IDS"));
			
			 list.add(partModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{	
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
	/**
	 * 获取选择列表下拉框的值(不含变压器)
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PD_PartModel> queryPD_PartSelectOut02() throws SQLException
	{
		ArrayList<PD_PartModel> list=new ArrayList<PD_PartModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select Part_ID,PartStyle_ID,Part_Parent,Part_Name,Part_IDS from PD_Part";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
			 partModel=new PD_PartModel();
			 partModel.setPart_ID(rs.getInt("Part_ID"));
			
			 partModel.setPartStyle_ID(rs.getString("PartStyle_ID"));
			 partModel.setPart_Parent(rs.getInt("Part_Parent"));
			
			 partModel.setPart_Name(rs.getString("Part_Name"));

			 partModel.setPart_IDS(rs.getString("Part_IDS"));
			
			 list.add(partModel);
			}
			list=getTreeOut02(list);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
	
	
	
	/**
	 * 去除变压器
	 * @param list
	 * @return
	 */
	public ArrayList<PD_PartModel> getTreeOut02(ArrayList<PD_PartModel> list)
	{
		String PartStyle_ID="";
		int Part_ID=0;
		int cPart_Parent=0;
		int fPart_Parent=0;
		Iterator<PD_PartModel> it = list.iterator();
        while(it.hasNext()){
        	PD_PartModel partModel=new PD_PartModel();
        	partModel=it.next();
        	PartStyle_ID=partModel.getPartStyle_ID();
        	Part_ID=partModel.getPart_ID();
            if("02".equals(PartStyle_ID))
            {
            	fPart_Parent=partModel.getPart_Parent();
            	Iterator<PD_PartModel> its = list.iterator();
                while(its.hasNext()){
                	PD_PartModel partModels=new PD_PartModel();
                	partModels=its.next();
                	cPart_Parent=partModels.getPart_Parent();
                	if(cPart_Parent==Part_ID)
                	{
                		partModels.setPart_Parent(fPart_Parent);
                	}
                }
               it.remove();
                
            }
        }
        return list;
	}
	

	
	//变压器管理
	

	
	
	/**
	 * 查询变压器表里信息
	 * @return
	 * @throws SQLException 
	 */
	public List<PD_PartInfo02Model> queryPD_PartInfo02(int pageCount,int pageIndex,int Pd_Parent) throws SQLException
	{
		List<PD_PartInfo02Model> pList02 =null;
		PD_PartInfo02Model partInfo02Model=null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			String strWhere="";
			if(Pd_Parent!=-1)
			{
				strWhere=" and a.Part_ID in (SELECT part_id FROM pd_part START WITH part_parent="+Pd_Parent+"CONNECT BY PRIOR part_id = part_parent)";
			}
			pList02 = new ArrayList<PD_PartInfo02Model>();
			String sql="select a.Part_ID,(select DictionaryValue_Value from DictionaryValue where PartStyle_ID=DictionaryValue_Num and Dictionary_ID=28) PartStyle,a.PartStyle_ID,a.Part_Parent,nvl((select Part_Name from PD_Part where Part_ID=a.Part_Parent),(select systemInfo_complayname from systeminfo where rownum=1)) parent_name,(select Part_IDS from PD_Part where Part_ID=a.Part_Parent) Part_IDS,a.State,a.Part_Num,a.Part_Name,a.Part_SCCJ,to_char(a.Part_TYRQ,'yyyy-mm-dd') Part_TYRQ,a.Part_Remark,b.PartInfo_ID,b.Man,b.ManPhone,b.GZPL,b.EDGL,b.EDDY,b.DYB,b.KZDL,b.KZSH,b.XL,b.DYSX,b.DYXX,b.XEDL,b.XEWG,b.GLYS  from PD_Part a left join PD_PARTINFO02 b on   a.Part_ID=b.Part_ID where a.PARTSTYLE_ID='02' "+strWhere+" order by a.Part_ID desc";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			//得到总记录数
			rs.last();
			int count = rs.getRow();
			setTotalCount02(count);
			if (count <= (pageCount * pageIndex))
				return pList02;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);
			
			while(rs.next() && (count > 0))		
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
				pList02.add(partInfo02Model);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return pList02;
	}
	
	
	
	/**
	 * 回路管理
	 */
	
	
	
	
	public List<PD_PartInfo03Model> queryPD_PartInfo03(int pageCount,int pageIndex,int Pd_Parent,String tableName,String order) throws SQLException
	{
		AmmeterDao amdDao = new AmmeterDao();
		PD_PartInfo03Model partInfo03Model=null;
		List<PD_PartInfo03Model> pList03 =null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String strWhere="";
			if(Pd_Parent!=-1)
			{
				strWhere=" and (a.part_id="+Pd_Parent+" or  a.Part_ID in (SELECT part_id FROM pd_part START WITH part_parent="+Pd_Parent+" CONNECT BY PRIOR part_id = part_parent ))";
			}
			pList03 = new ArrayList<PD_PartInfo03Model>();
			String sql="select a.Part_ID,(select DictionaryValue_Value from DictionaryValue where PartStyle_ID=DictionaryValue_Num and Dictionary_ID=28) PartStyle,a.PartStyle_ID,a.Part_Parent,nvl((select Part_Name from PD_Part where Part_ID=a.Part_Parent),(select systemInfo_complayname from systeminfo where rownum=1)) parent_name,(select Part_IDS from PD_Part where Part_ID=a.Part_Parent) Part_IDS,a.State,a.Part_Num,a.Part_Name,a.Part_SCCJ,to_char(a.Part_TYRQ,'yyyy-mm-dd') Part_TYRQ,a.Part_Remark,b.*  from PD_Part a left join PD_PARTINFO03 b on   a.Part_ID=b.Part_ID where a.PARTSTYLE_ID='03' "+strWhere+" order by a."+tableName+" "+order;

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			//得到总记录数
			rs.last();
			int count = rs.getRow();
			setTotalCount03(count);
			if (count <= (pageCount * pageIndex))
				return pList03;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);
			
			while(rs.next() && (count > 0))		
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
				String jlname = amdDao.queryNameByID(Integer.parseInt(rs.getString("JLID")));
				partInfo03Model.setJLNAME(jlname);
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
				partInfo03Model.setDYSXL(rs.getDouble("DYSXL"));
				partInfo03Model.setDYXXL(rs.getDouble("DYXXL"));
				partInfo03Model.setXEDLL(rs.getDouble("XEDLL"));
				pList03.add(partInfo03Model);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return pList03;
	}
	
	/**
	 * 得到根节点信息
	 * @return
	 * @throws SQLException
	 */
	public  String getRoot() throws SQLException
	{
		String rootname =null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select systemInfo_complayname from systeminfo where rownum=1";
			rootname = "";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				rootname=rs.getString("systemInfo_complayname");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return rootname;
	}
	
//	public int textTry()
//	{
//		int a = 0;
//		try
//		{
//			a++;
//			System.out.println("---------1");
//			int i = 1/0;
//			a=10;
//			System.out.println("----------2");
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//			System.out.println("-------catch");
//		}finally{
//			System.out.println("----------finally");
//		}
//		
//		return a;
//	}
//	@Test
//	public void ttt(){
//		System.out.println(textTry());
//	}
	
}
