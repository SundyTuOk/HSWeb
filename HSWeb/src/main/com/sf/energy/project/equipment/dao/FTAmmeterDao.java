package com.sf.energy.project.equipment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.project.equipment.model.FTAmmeterModel;
import com.sf.energy.util.ConnDB;

/**
 * 能耗分摊涉及操作数据库方法
 * @author Administrator
 *
 */
public class FTAmmeterDao
{
	public int getCount(int areaId,int archId,int gatherId,String ammeterName){
		int count = 0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String condition = buildQueryConditionSql(areaId, archId, gatherId, ammeterName);
		String sql = "SELECT count(Ammeter_ID) FROM AmMeter  where DATAFROM = 3 "+condition;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				count = rs.getInt(1);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return count;
	}
	
	public String buildQueryConditionSql(int areaId,int archId,int gatherId,String ammeterName){
		if(areaId==-3&&archId==-3&&gatherId==-3&&(ammeterName==null||ammeterName.replace(" ", "").length()==0)){
			return " ";
		}
		String sql = " And ";
		if(areaId!=-3){
			sql +=" Area_ID="+areaId;
		}
		if(archId!=-3){
			if(areaId!=-3){
				sql+=" And ";
			}
			sql +=" Architecture_ID="+archId;
		}
		if(gatherId!=-3){
			if(areaId!=-3||archId!=-3){
				sql+=" And ";
			}
			sql +=" Gather_ID="+gatherId;
		}
		if(ammeterName!=null&&ammeterName.replace(" ", "").length()>0){
			if(areaId!=-3||archId!=-3||gatherId!=-3){
				sql+=" And ";
			}
			sql +=" AmMeter_Name='"+ammeterName+"'";
		}
		return sql;
	}
	public List<FTAmmeterModel> queryAllFTAmmeter(int offset,int limit,int areaId,int archId,int gatherId,String ammeterName){
		List<FTAmmeterModel> list =null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String condition = buildQueryConditionSql(areaId, archId, gatherId, ammeterName);
		try
		{		
			String sql ="SELECT * from (SELECT ROWNUM rowno,t.* FROM (SELECT Ammeter_ID,AmMeter_Name,Partment_ID,PartmentName,Area_ID,Area_Name,Architecture_ID, Architecture_Name,Floor,Gather_ID,Gather_Name "
					+ "FROM V_AmMeter  where DATAFROM = 3 "+condition+" ORDER BY Ammeter_ID) t WHERE ROWNUM<="+(limit+offset)
					+ ") v  left join FTAMMETER f on v.AMMETER_ID = f.AMMETER_ID where rowno>"+offset;
			//System.out.println(sql);
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if(list==null){
					list =  new ArrayList<FTAmmeterModel>();
				}
				list.add(buildInstance(rs));
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<FTAmmeterModel> queryAllFTAmmeter(){
		List<FTAmmeterModel> list =null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * from (SELECT Ammeter_ID,AmMeter_Name,Partment_ID,PartmentName,Area_ID,Area_Name,Architecture_ID,"
					+ "Architecture_Name,Floor,Gather_ID,Gather_Name"
					+" FROM V_AmMeter  where DATAFROM = 3 ORDER BY Ammeter_ID) v left join FTAMMETER f on v.AMMETER_ID = f.AMMETER_ID";
			//System.out.println(sql);
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if(list==null){
					list =  new ArrayList<FTAmmeterModel>();
				}
				list.add(buildInstance(rs));
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}
	public List<FTAmmeterModel> queryAllFTAmmeterIDs(){
		List<FTAmmeterModel> list =null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql ="SELECT v.Ammeter_ID AMMETER_ID,PARAMETER,FORMULA from (SELECT Ammeter_ID FROM V_AmMeter  where DATAFROM = 3 ORDER BY Ammeter_ID) "
					+ "v left join FTAMMETER f on v.AMMETER_ID = f.AMMETER_ID";
			//System.out.println(sql);
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if(list==null){
					list =  new ArrayList<FTAmmeterModel>();
				}
				list.add(buildInstance1(rs));
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}
	public FTAmmeterModel queryOneFTAmmeter(int ammeter_Id){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		FTAmmeterModel model = null;
		try
		{
			String sql = "SELECT * from (SELECT Ammeter_ID,AmMeter_Name,Partment_ID,PartmentName,Area_ID,Area_Name,Architecture_ID,"
					+ "Architecture_Name,Floor,Gather_ID,Gather_Name"
					+" FROM V_AmMeter  where DATAFROM = 3 and AMMETER_ID = ?) v left join FTAMMETER f on v.AMMETER_ID = f.AMMETER_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ammeter_Id);
			rs = ps.executeQuery();
			if (rs.next())
			{
				model = buildInstance(rs);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return model;
	}
	public boolean insertFTAmmeter(FTAmmeterModel model){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "insert into FTAmmeter(AMMETER_ID,PARAMETER,FORMULA) values(?,?,?)";
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, model.getAmmeterId());
			ps.setString(2, model.getParameters());
			ps.setString(3, model.getFormula());
			b = (ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	public boolean updateFTAmmeter(FTAmmeterModel model){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update FTAmmeter set PARAMETER=?,FORMULA=? where AMMETER_ID=? ";
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, model.getParameters());
			ps.setString(2, model.getFormula());
			ps.setInt(3, model.getAmmeterId());
			b = (ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	public boolean deleteFTAmmeter(int ammeterId){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from FTAmmeter  where AMMETER_ID=? ";
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ammeterId);

			b = (ps.executeUpdate()<=1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	public boolean deleteFTAmmeter(String ammeterIds){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from FTAmmeter  where AMMETER_ID in (?) ";
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, ammeterIds);

			b = (ps.executeUpdate()>=0);
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	public String getFormulaInfo(int ammeterId){
		String formlua=null;
		String parameterIds=null;
		String rformlua=null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from FTAmmeter where AMMETER_ID=?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ammeterId);
			rs = ps.executeQuery();
			if (rs.next())
			{
				parameterIds = rs.getString("parameter");
				rformlua = rs.getString("formlua");
			}
			formlua = formateFormula(parameterIds,rformlua);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return formlua;
	}

	/**
	 * eg: parameterIds =37302,40 rformlua=x1+x2
	 * return Am(37302)+Am(40)
	 * @param parameterIds
	 * @param rformlua
	 * @return
	 */
	public String formateFormula(String parameterIds,String rformlua){
		if(parameterIds!=null&&rformlua!=null&&!parameterIds.equals("")&&!rformlua.equals("")){
			String[] ids = parameterIds.split(",");
			int num = ids.length;
			for(int i=num-1;i>=0;i--){
				rformlua = rformlua.replace("x"+(i+1), "Am("+ids[i]+")");
			}
		}else{
			rformlua = null;
		}
		return rformlua;
	}
	private String getParameterNames(String parameter) throws Exception{
		String parameterNames = "";
		if(parameter!=null&&!"".equals(parameter)){
			AmmeterDao dao = new AmmeterDao();
			String ids[] = parameter.split(",");
			for(int i=0;i<ids.length;i++){
				String name = dao.queryNameByID(Integer.parseInt(ids[i]));
				if(i!=ids.length-1){
					parameterNames+=name+",";
				}else{
					parameterNames+=name;
				}
			}
		}
		return parameterNames;
	}

	private FTAmmeterModel buildInstance(ResultSet rs) throws Exception{
		FTAmmeterModel model = new FTAmmeterModel();
		model.setAmmeterId(rs.getInt("Ammeter_ID"));
		model.setAmmeterName(rs.getString("AmMeter_Name"));

		model.setPartmentId(rs.getInt("Partment_ID"));
		model.setPartmentName(rs.getString("PartmentName"));

		model.setAreaId(rs.getInt("Area_ID"));
		model.setAreaName(rs.getString("Area_Name"));

		model.setArchitectureId(rs.getInt("Architecture_ID"));
		model.setArchitectureName(rs.getString("Architecture_Name"));

		model.setFloor(rs.getInt("Floor"));

		model.setGatherId(rs.getInt("Gather_ID"));
		model.setGatherName(rs.getString("Gather_Name"));
		if(!"".equals(rs.getString("Parameter"))&&rs.getString("Parameter")!=null)
			model.setParameters(rs.getString("Parameter"));
		model.setParameterNames(getParameterNames(rs.getString("Parameter")));

		model.setFormula(rs.getString("Formula"));

		return model;
	}
	private FTAmmeterModel buildInstance1(ResultSet rs) throws Exception{
		FTAmmeterModel model = new FTAmmeterModel();
		model.setAmmeterId(rs.getInt("Ammeter_ID"));
		if(!"".equals(rs.getString("Parameter"))&&rs.getString("Parameter")!=null)
			model.setParameters(rs.getString("Parameter"));	
		model.setFormula(rs.getString("Formula"));
		return model;
	}
}
