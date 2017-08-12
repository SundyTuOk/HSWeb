package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.expert.model.AreaArcModel;
import com.sf.energy.expert.model.EnAuditArcModel;
import com.sf.energy.util.ConnDB;

public class EnAuditArcDao {

	EnAuditArcModel enAuditArcModel=null;

	//得到总记录
	private int totalCount = 0;

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}


	public boolean addEnAuditArc(EnAuditArcModel enAuditArcModel) throws SQLException
	{
		String sql="insert into EnAuditArc(EnAuditArc_ID,ENAUDITARC_NUM,ENAUDITARC_NAME,ARCHITECTURE_ID,ENAUDITARC_YEAR,ENAUDITARC_TIME,ENAUDITARC_FZMAN,ENAUDITARC_SHMAN,ENAUDITARC_EMPLOYERS,ENAUDITARC_STARTIME,ENAUDITARC_ENDTIME) values('"
				+enAuditArcModel.getEnAuditArc_ID()+"','"+enAuditArcModel.getEnAuditArc_Num()+"','"
				+enAuditArcModel.getEnAuditArc_Name()+"','"+enAuditArcModel.getArchitecture_ID()+"','"
				+enAuditArcModel.getEnAuditArc_Year()+"',to_date('"+enAuditArcModel.getEnAuditArc_Time()+"','yyyy-mm-dd'),'"
				+enAuditArcModel.getEnAuditArc_FZMan()+"','"+enAuditArcModel.getEnAuditArc_SHMan()+"','"
				+enAuditArcModel.getEnAuditArc_Employers()+"','"+enAuditArcModel.getEnAuditArc_StartTime()+"','"				    
				+enAuditArcModel.getEnAuditArc_EndTime()+"')";
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





	/**根据建筑查询
	 * 	
	 * @return
	 * @throws SQLException
	 */

	public List<EnAuditArcModel> querEnAuditArcID(String EnAuditArcsortName,String EnAuditArcorder,String areaId,String arcId,int pageCount,int pageIndex) throws SQLException
	{
		List<EnAuditArcModel> list=new ArrayList<EnAuditArcModel>();
		String strWhere="";		
		if(!("-1".equals(areaId)))
		{
			strWhere+=" and b.Area_ID='" + areaId + "'";
		}
		if(!("-1".equals(arcId)))
		{
			strWhere+=" and b.ARCHITECTURE_ID='" + arcId + "'";
		}
		String sql="select a.ARCHITECTURE_ID,a.EnAuditArc_ID,nvl(a.EnAuditArc_Num,' ') EnAuditArc_Num,nvl(a.EnAuditArc_Name,' ') EnAuditArc_Name,a.EnAuditArc_Year,nvl(a.EnAuditArc_Employers,' ') EnAuditArc_Employers,nvl(a.EnAuditArc_FZMan,' ') EnAuditArc_FZMan,nvl(a.EnAuditArc_SHMan,' ') EnAuditArc_SHMan,to_char(a.EnAuditArc_Time,'yyyy-mm-dd') EnAuditArc_Time,a.ENAUDITARC_STARTIME,a.ENAUDITARC_ENDTIME,b.Architecture_Num,b.Architecture_Name,b.ARCHITECTURE_AREA,b.ARCHITECTURE_AIRCONDITION,b.ARCHITECTURE_TIME,(select DICTIONARYVALUE_VALUE from DICTIONARYVALUE c where c.DICTIONARYVALUE_NUM=b.ARCHITECTURE_STYLE and c.DICTIONARY_ID=3) ARCHITECTURE_STYLE from EnAuditArc a inner join Architecture  b on   a.Architecture_ID=b.Architecture_ID where 1=1"+strWhere+" order by "+EnAuditArcsortName+" "+EnAuditArcorder;
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

			setTotalCount(count);
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
				enAuditArcModel=new EnAuditArcModel();
				enAuditArcModel.setEnAuditArc_ID(rs.getInt("EnAuditArc_ID"));
				enAuditArcModel.setEnAuditArc_Num(rs.getString("EnAuditArc_Num"));
				enAuditArcModel.setEnAuditArc_Name(rs.getString("EnAuditArc_Name"));
				enAuditArcModel.setEnAuditArc_Year(rs.getString("EnAuditArc_Year"));
				enAuditArcModel.setEnAuditArc_Employers(rs.getString("EnAuditArc_Employers"));
				enAuditArcModel.setEnAuditArc_FZMan(rs.getString("EnAuditArc_FZMan"));
				enAuditArcModel.setEnAuditArc_SHMan(rs.getString("EnAuditArc_SHMan"));
				enAuditArcModel.setEnAuditArc_Time(rs.getString("EnAuditArc_Time"));
				enAuditArcModel.setArchitecture_Num(rs.getString("Architecture_Num"));
				enAuditArcModel.setArchitecture_Name(rs.getString("Architecture_Name"));
				enAuditArcModel.setEnAuditArc_StartTime(rs.getString("ENAUDITARC_STARTIME"));
				enAuditArcModel.setEnAuditArc_EndTime(rs.getString("ENAUDITARC_ENDTIME"));	
				enAuditArcModel.setArchitecture_ID(rs.getInt("ARCHITECTURE_ID"));
				enAuditArcModel.setARCHITECTURE_AREA(rs.getString("ARCHITECTURE_AREA"));
				enAuditArcModel.setARCHITECTURE_AIRCONDITION(rs.getString("ARCHITECTURE_AIRCONDITION"));
				enAuditArcModel.setARCHITECTURE_TIME(rs.getString("ARCHITECTURE_TIME"));
				enAuditArcModel.setARCHITECTURE_STYLE(rs.getString("ARCHITECTURE_STYLE"));
				list.add(enAuditArcModel);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}


		//得到总记录数
		//        rs.last();
		//		int count = rs.getRow();
		//		
		//		setTotalCount(count);
		//		if (count <= (pageCount * pageIndex))
		//			return list;
		//
		//		count = count - pageCount * pageIndex;
		//
		//		if (count >= pageCount)
		//			count = pageCount;
		//
		//		if ((pageCount * pageIndex) == 0)
		//		{
		//			rs.absolute(1);
		//			rs.previous();
		//		} else
		//			rs.absolute(pageCount * pageIndex);
		//        
		//        while(rs.next() && (count > 0))		
		//		{
		//			enAuditArcModel=new EnAuditArcModel();
		//			enAuditArcModel.setEnAuditArc_ID(rs.getInt("EnAuditArc_ID"));
		//			enAuditArcModel.setEnAuditArc_Num(rs.getString("EnAuditArc_Num"));
		//			enAuditArcModel.setEnAuditArc_Name(rs.getString("EnAuditArc_Name"));
		//			enAuditArcModel.setEnAuditArc_Year(rs.getString("EnAuditArc_Year"));
		//			enAuditArcModel.setEnAuditArc_Employers(rs.getString("EnAuditArc_Employers"));
		//			enAuditArcModel.setEnAuditArc_FZMan(rs.getString("EnAuditArc_FZMan"));
		//			enAuditArcModel.setEnAuditArc_SHMan(rs.getString("EnAuditArc_SHMan"));
		//			enAuditArcModel.setEnAuditArc_Time(rs.getString("EnAuditArc_Time"));
		//			enAuditArcModel.setArchitecture_Num(rs.getString("Architecture_Num"));
		//			enAuditArcModel.setArchitecture_Name(rs.getString("Architecture_Name"));
		//			enAuditArcModel.setEnAuditArc_StartTime(rs.getString("ENAUDITARC_STARTIME"));
		//			enAuditArcModel.setEnAuditArc_EndTime(rs.getString("ENAUDITARC_ENDTIME"));	
		//			enAuditArcModel.setArchitecture_ID(rs.getInt("ARCHITECTURE_ID"));
		//			enAuditArcModel.setARCHITECTURE_AREA(rs.getString("ARCHITECTURE_AREA"));
		//			enAuditArcModel.setARCHITECTURE_AIRCONDITION(rs.getString("ARCHITECTURE_AIRCONDITION"));
		//			enAuditArcModel.setARCHITECTURE_TIME(rs.getString("ARCHITECTURE_TIME"));
		//			enAuditArcModel.setARCHITECTURE_STYLE(rs.getString("ARCHITECTURE_STYLE"));
		//	        list.add(enAuditArcModel);
		//	        count--;
		//		}
		return list;
	}



	//根据能耗上报ID得到相应的信息	
	public EnAuditArcModel getEnAuditArcByID(String id) throws SQLException
	{
		EnAuditArcModel enAuditArcModel=new EnAuditArcModel();
		String sql="select a.ARCHITECTURE_ID,a.EnAuditArc_ID,nvl(a.EnAuditArc_Num,' ') EnAuditArc_Num,nvl(a.EnAuditArc_Name,' ') EnAuditArc_Name,a.EnAuditArc_Year,nvl(a.EnAuditArc_Employers,' ') EnAuditArc_Employers,nvl(a.EnAuditArc_FZMan,' ') EnAuditArc_FZMan,nvl(a.EnAuditArc_SHMan,' ') EnAuditArc_SHMan,to_char(a.EnAuditArc_Time,'yyyy-mm-dd') EnAuditArc_Time,a.ENAUDITARC_STARTIME,a.ENAUDITARC_ENDTIME,b.Architecture_Num,b.Architecture_Name,b.ARCHITECTURE_AREA,b.ARCHITECTURE_AIRCONDITION,b.ARCHITECTURE_TIME,(select DICTIONARYVALUE_VALUE from DICTIONARYVALUE c where c.DICTIONARYVALUE_NUM=b.ARCHITECTURE_STYLE and c.DICTIONARY_ID=3) ARCHITECTURE_STYLE from EnAuditArc a inner join Architecture  b on   a.Architecture_ID=b.Architecture_ID where a.ENAUDITARC_ID="+id;
		//		System.out.println(sql);
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
				enAuditArcModel.setEnAuditArc_ID(rs.getInt("EnAuditArc_ID"));
				enAuditArcModel.setEnAuditArc_Num(rs.getString("EnAuditArc_Num"));
				enAuditArcModel.setEnAuditArc_Name(rs.getString("EnAuditArc_Name"));
				enAuditArcModel.setEnAuditArc_Year(rs.getString("EnAuditArc_Year"));
				enAuditArcModel.setEnAuditArc_Employers(rs.getString("EnAuditArc_Employers"));
				enAuditArcModel.setEnAuditArc_FZMan(rs.getString("EnAuditArc_FZMan"));
				enAuditArcModel.setEnAuditArc_SHMan(rs.getString("EnAuditArc_SHMan"));
				enAuditArcModel.setEnAuditArc_Time(rs.getString("EnAuditArc_Time"));
				enAuditArcModel.setArchitecture_Num(rs.getString("Architecture_Num"));
				enAuditArcModel.setArchitecture_Name(rs.getString("Architecture_Name"));
				enAuditArcModel.setEnAuditArc_StartTime(rs.getString("ENAUDITARC_STARTIME"));
				enAuditArcModel.setEnAuditArc_EndTime(rs.getString("ENAUDITARC_ENDTIME"));	
				enAuditArcModel.setArchitecture_ID(rs.getInt("ARCHITECTURE_ID"));
				enAuditArcModel.setARCHITECTURE_AREA(rs.getString("ARCHITECTURE_AREA"));
				enAuditArcModel.setARCHITECTURE_AIRCONDITION(rs.getString("ARCHITECTURE_AIRCONDITION"));
				enAuditArcModel.setARCHITECTURE_TIME(rs.getString("ARCHITECTURE_TIME"));
				enAuditArcModel.setARCHITECTURE_STYLE(rs.getString("ARCHITECTURE_STYLE"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		
//		if (rs.next())
//		{
//			enAuditArcModel.setEnAuditArc_ID(rs.getInt("EnAuditArc_ID"));
//			enAuditArcModel.setEnAuditArc_Num(rs.getString("EnAuditArc_Num"));
//			enAuditArcModel.setEnAuditArc_Name(rs.getString("EnAuditArc_Name"));
//			enAuditArcModel.setEnAuditArc_Year(rs.getString("EnAuditArc_Year"));
//			enAuditArcModel.setEnAuditArc_Employers(rs.getString("EnAuditArc_Employers"));
//			enAuditArcModel.setEnAuditArc_FZMan(rs.getString("EnAuditArc_FZMan"));
//			enAuditArcModel.setEnAuditArc_SHMan(rs.getString("EnAuditArc_SHMan"));
//			enAuditArcModel.setEnAuditArc_Time(rs.getString("EnAuditArc_Time"));
//			enAuditArcModel.setArchitecture_Num(rs.getString("Architecture_Num"));
//			enAuditArcModel.setArchitecture_Name(rs.getString("Architecture_Name"));
//			enAuditArcModel.setEnAuditArc_StartTime(rs.getString("ENAUDITARC_STARTIME"));
//			enAuditArcModel.setEnAuditArc_EndTime(rs.getString("ENAUDITARC_ENDTIME"));	
//			enAuditArcModel.setArchitecture_ID(rs.getInt("ARCHITECTURE_ID"));
//			enAuditArcModel.setARCHITECTURE_AREA(rs.getString("ARCHITECTURE_AREA"));
//			enAuditArcModel.setARCHITECTURE_AIRCONDITION(rs.getString("ARCHITECTURE_AIRCONDITION"));
//			enAuditArcModel.setARCHITECTURE_TIME(rs.getString("ARCHITECTURE_TIME"));
//			enAuditArcModel.setARCHITECTURE_STYLE(rs.getString("ARCHITECTURE_STYLE"));
//		}
		return enAuditArcModel;

	}



	/**	
	 * 修改能耗审计信息
	 * @param enAuditArcModel
	 * @return
	 * @throws SQLException
	 **/
	public boolean editEnAuditArc(EnAuditArcModel enAuditArcModel) throws SQLException
	{
		String sql="update EnAuditArc set enAuditArc_Num='"+enAuditArcModel.getEnAuditArc_Num()
				+"',enAuditArc_Name='"+enAuditArcModel.getEnAuditArc_Name()+"',enAuditArc_Year='"+enAuditArcModel.getEnAuditArc_Year()
				+"',enAuditArc_Employers='"+enAuditArcModel.getEnAuditArc_Employers()+"',enAuditArc_FZMan='"+enAuditArcModel.getEnAuditArc_FZMan()
				+"',enAuditArc_SHMan='"+enAuditArcModel.getEnAuditArc_SHMan()+"',enAuditArc_Time=to_date('"+enAuditArcModel.getEnAuditArc_Time()+"','yyyy-mm-dd')," 
				+"ENAUDITARC_STARTIME='"+enAuditArcModel.getEnAuditArc_StartTime()+"',ENAUDITARC_ENDTIME='"+enAuditArcModel.getEnAuditArc_EndTime()+"',Architecture_ID='"+enAuditArcModel.getArchitecture_ID()+"' where EnAuditArc_ID="+enAuditArcModel.getEnAuditArc_ID();
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
	 * 删除能耗审计信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteEnAuditArc(int id) throws SQLException
	{
		String sql="delete from EnAuditArc where EnAuditArc_ID="+id;

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
	 * 得到区域信息
	 * @return
	 * @throws SQLException 
	 */
	public List<AreaArcModel> getArea() throws SQLException
	{

		List<AreaArcModel> arealist=new ArrayList<AreaArcModel>();
		AreaArcModel areaArcModel=null;
		String sql="select area_id,area_name from area";
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
				areaArcModel=new AreaArcModel();
				areaArcModel.setArea_ID(rs.getInt("area_id"));
				areaArcModel.setArea_Name(rs.getString("area_name"));


				arealist.add(areaArcModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		
//		while (rs.next())
//		{
//			areaArcModel=new AreaArcModel();
//			areaArcModel.setArea_ID(rs.getInt("area_id"));
//			areaArcModel.setArea_Name(rs.getString("area_name"));
//
//
//			arealist.add(areaArcModel);
//		}
		return arealist;
	}

	/**
	 * 得到建筑信息
	 * @return
	 * @throws SQLException 
	 */
	public List<AreaArcModel> getArc() throws SQLException
	{
		List<AreaArcModel> arclist=new ArrayList<AreaArcModel>();
		AreaArcModel areaArcModel=null;
		String sql="select Architecture_id,area_id,Architecture_name from Architecture";
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
				areaArcModel=new AreaArcModel();
				areaArcModel.setArchitecture_ID(rs.getInt("Architecture_id"));
				areaArcModel.setArea_ID(rs.getInt("area_id"));
				areaArcModel.setArchitecture_Name(rs.getString("Architecture_name"));

				arclist.add(areaArcModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		
//		while (rs.next())
//		{
//			areaArcModel=new AreaArcModel();
//			areaArcModel.setArchitecture_ID(rs.getInt("Architecture_id"));
//			areaArcModel.setArea_ID(rs.getInt("area_id"));
//			areaArcModel.setArchitecture_Name(rs.getString("Architecture_name"));
//
//			arclist.add(areaArcModel);
//		}
		return arclist;
	}

	/**
	 * 得到根节点信息
	 * @return
	 * @throws SQLException
	 */
	public  String getRoot() throws SQLException
	{
		String sql="select systemInfo_complayname from systeminfo where rownum=1";
		String rootname="";
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
				rootname=rs.getString("systemInfo_complayname");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		
//		if (rs.next())
//		{
//			rootname=rs.getString("systemInfo_complayname");
//		}
		return rootname;
	}
	/**
	 * 获得建筑能耗审计ID
	 * @return
	 * @throws SQLException
	 */
	public int getEnAuditArcId() throws SQLException
	{
		int id=0;
		String sql="select max(EnAuditArc_ID) Id from EnAuditArc";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
		
		
//		if(rs.next())
//		{
//			id=rs.getInt("Id");			
//		}
		return id+1;
	}

	//生成能耗审计报告图表
	public List<String> getReportXml(String id,String arcName,String subName,String queryS,String queryE) throws SQLException
	{
		List<String> list=new ArrayList<String>();
		String title=arcName+"分项电耗比例";
		String subTitle=subName;
		list.add(title);
		list.add(subTitle);
	//	String sql="select (select  DictionaryValue_Value from DictionaryValue where DictionaryValue_Num=Fenlei and Dictionary_ID=7) FenleiName,ZGross,Fenlei from (select Fenlei,sum(ZGross)ZGross from T_ArcFenleiDayAm where Architecture_ID="+id+" group by Fenlei )A order by Fenlei";
		String sql="select (select  DictionaryValue_Value from DictionaryValue where DictionaryValue_Num=Fenlei and Dictionary_ID=7) FenleiName,ZGross,Fenlei from (select Fenlei,sum(ZGross)ZGross from T_ArcFenleiDayAm where Architecture_ID="+id+" AND TO_DATE(SELECTYEAR || '-' || SELECTMONTH || '-' || SELECTDAY, 'yyyy-mm-dd') BETWEEN TO_DATE('"+queryS+"', 'yyyy-mm-dd') AND TO_DATE('"+queryE+"', 'yyyy-mm-dd') group by Fenlei )A order by Fenlei";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while(rs.next())
			{
				list.add(rs.getString("FenleiName"));
				list.add(rs.getString("ZGross"));
				//pieXml+="<set name='"+rs.getString("FenleiName")+"' value='"+rs.getString("ZGross")+"' color='AFD8F8'/>";  
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while(rs.next())
//		{
//			list.add(rs.getString("FenleiName"));
//			list.add(rs.getString("ZGross"));
//			//pieXml+="<set name='"+rs.getString("FenleiName")+"' value='"+rs.getString("ZGross")+"' color='AFD8F8'/>";  
//		}
		//pieXml+="</graph>";
		return list;
	}
	//生成空调系统电耗拆分饼图
	public List<String> kongTiao(String id,String queryS,String queryE) throws SQLException
	{
		List<String> list=new ArrayList<String>();
		String title="空调系统电耗拆分饼图";
		list.add(title);
		String sql="select (select  DictionaryValue_Value from DictionaryValue where DictionaryValue_Num='B'||Fenlei01 and Dictionary_ID=8) FenleiName,ZGross,Fenlei01 from (select Fenlei01,sum(ZGross)ZGross from T_ArcFenleiDayAm where Architecture_ID="+id+" AND TO_DATE(SELECTYEAR || '-' || SELECTMONTH || '-' || SELECTDAY, 'yyyy-mm-dd') BETWEEN TO_DATE('"+queryS+"', 'yyyy-mm-dd') AND TO_DATE('"+queryE+"', 'yyyy-mm-dd') and Fenlei='B' group by Fenlei01 )A order by Fenlei01";
		//		System.out.println("sql:"+sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while(rs.next())
			{
				list.add(rs.getString("FenleiName"));
				list.add(rs.getString("ZGross"));

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while(rs.next())
//		{
//			list.add(rs.getString("FenleiName"));
//			list.add(rs.getString("ZGross"));
//
//		}
		return list;

	}
	
}
