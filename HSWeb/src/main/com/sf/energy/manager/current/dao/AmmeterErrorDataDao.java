package com.sf.energy.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sf.energy.manager.current.model.ErrorDataModel;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.util.ConnDB;

public class AmmeterErrorDataDao
{

	public int getAmErrorDataCountByAm(int ammeterID, Date start, Date end){
		int count=0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = null;
		sql = "select count(*) num from AMMETERERRORDATAS where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and ammeter_id=? and errorcode<100 " + "order by ValueTime ASC";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			ps.setInt(3, ammeterID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				count = rs.getInt(1);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return count;
	}
	
	/**
	 * JDBC分页实现
	 * @param ammeterID
	 * @param start
	 * @param end
	 * @param pageCount
	 * @param pageIndex
	 * @return
	 * @throws SQLException
	 */
	public List<ErrorDataModel> getAmErrorDataByAm(int ammeterID, Date start, Date end,int pageCount, int pageIndex) throws SQLException
	{
		AmmeterDao aDao = new AmmeterDao();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ammeterName = aDao.queryNameByID(ammeterID);
		if (ammeterName == null)
			return null;
		List<ErrorDataModel> list = null;
		int startCount = pageIndex*pageCount+1;
		int endCount = (pageIndex+1)*pageCount;
		String sql = null;
		sql = "select * from (select B.*,ROWNUM RN from (SELECT * FROM( "
				+ "SELECT A .AMMETER_ID AMMETER_ID, TO_CHAR (VALUETIME,'yyyy-mm-dd hh24:mi:ss') VALUETIME,ZVALUEZY,ERRORCODE,NVL (ISPUSHUP ,- 1) ISPUSHUP";
		sql += " FROM( "
				+ "SELECT * FROM AMMETERERRORDATAS WHERE ValueTime BETWEEN TO_DATE (?,'yyyy-mm-dd hh24:mi:ss' )AND TO_DATE (?,'yyyy-mm-dd hh24:mi:ss')AND errorcode < 100 AND ammeter_id = ?) A))B where ROWNUM<=?) where rn>=?";
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			ps.setInt(3, ammeterID);
			ps.setInt(4, endCount);
			ps.setInt(5, startCount);
			//System.out.println(startCount+" "+endCount);
			//System.out.println(startString+" "+endString);
			rs = ps.executeQuery();   
			while (rs.next())
			{
				if (list == null)
					list = new ArrayList<ErrorDataModel>();

				ErrorDataModel amd = new ErrorDataModel();
				amd=buildIstance(rs);
				amd.setAmmeterName(ammeterName);
				list.add(amd);
				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public int getAmErrorDataCountByFloor(int archID,int floorID, Date start, Date end) throws SQLException{
		int count=0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AmmeterDao aDao = new AmmeterDao();
		List<AmmeterModel> amList = aDao.queryByArchAndFloor(archID, floorID);
		if(amList==null||amList.size()<=0){
			return count;
		}
		
		StringBuffer sql = null;
		sql = new StringBuffer("select count(*) num from AMMETERERRORDATAS where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and errorcode<100 and (" );
		for(AmmeterModel a:amList){
			sql.append("ammeter_id="+a.getAmmeterID()+" or ");
		}
		sql.append("ammeter_id="+0);
		sql.append( ") order by ValueTime,Ammeter_id");
		////System.out.println(df.format(start)+" " +df.format(start));
		////System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql.toString());
			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			rs = ps.executeQuery();
			if (rs.next())
			{
				count = rs.getInt(1);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return count;
	}
	public List<ErrorDataModel> getAmErrorDataByFloor(int archID,int floorID, Date start, Date end,int pageCount, int pageIndex) throws SQLException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AmmeterDao aDao = new AmmeterDao();
		List<AmmeterModel> amList = aDao.queryByArchAndFloor(archID, floorID);
		if(amList==null||amList.size()<=0){
			return null;
		}
		////System.out.println("电表个数:"+amList.size());
		List<ErrorDataModel> list = null;
		int startCount = pageIndex*pageCount+1;
		int endCount = (pageIndex+1)*pageCount;
		StringBuffer sql = null;
		sql = new StringBuffer("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select * from ( ");
		sql.append("select A.AMMETER_ID AMMETER_ID,AMMETER_NAME,to_char(VALUETIME,'yyyy-mm-dd hh24:mi:ss') VALUETIME,ZVALUEZY,ERRORCODE,nvl(ISPUSHUP,-1) ISPUSHUP  from "
				+ "(select * from AMMETERERRORDATAS where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and errorcode<100 and (" );
		for(AmmeterModel a:amList){
			sql.append("ammeter_id="+a.getAmmeterID()+" or ");
		}
		sql.append("ammeter_id="+0);
		sql.append(") order by ValueTime ,ammeter_id ) A ");
		sql.append("left join (select ammeter_id,ammeter_name from ammeter where ");
		for(AmmeterModel a:amList){
			sql.append("ammeter_id="+a.getAmmeterID()+" or ");
		}
		sql.append("ammeter_id="+0);
		sql.append(" ) B on A.ammeter_id=B.ammeter_id ");
		sql.append(" )) A WHERE ROWNUM <= ?) WHERE RN >= ?");
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			ps.setInt(3, endCount);
			ps.setInt(4, startCount);
			//System.out.println(startCount+" "+endCount);
			//System.out.println(startString+" "+endString);
			rs = ps.executeQuery();
			// rs.previous();
			while (rs.next())
			{
				if (list == null)
					list = new ArrayList<ErrorDataModel>();

				ErrorDataModel amd = new ErrorDataModel();
				amd=buildIstance(rs);
				amd.setAmmeterName(rs.getString("ammeter_name"));
				list.add(amd);
				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public int getAmErrorDataCountByArch(int archID, Date start, Date end) throws SQLException{
		int count=0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AmmeterDao aDao = new AmmeterDao();
		List<AmmeterModel> amList = aDao.queryByArchID(archID);
		if(amList==null||amList.size()<=0){
			return count;
		}
		StringBuffer sql = null;
		sql = new StringBuffer("select count(*) num from AMMETERERRORDATAS where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and errorcode<100 and (" );
		for(AmmeterModel a:amList){
			sql.append("ammeter_id="+a.getAmmeterID()+" or ");
		}
		sql.append("ammeter_id="+0);
		sql.append( ") order by ValueTime,Ammeter_id");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql.toString());
			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			rs = ps.executeQuery();
			if (rs.next())
			{
				count = rs.getInt(1);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return count;
	}
	public List<ErrorDataModel> getAmErrorDataByArch(int archID, Date start, Date end,int pageCount, int pageIndex) throws SQLException
	{
		AmmeterDao aDao = new AmmeterDao();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<AmmeterModel> amList = aDao.queryByArchID(archID);
		if(amList==null||amList.size()<=0){
			return null;
		}
		////System.out.println("电表个数:"+amList.size());
		List<ErrorDataModel> list = null;
		int startCount = pageIndex*pageCount+1;
		int endCount = (pageIndex+1)*pageCount;
		StringBuffer sql = null;
		sql = new StringBuffer("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select * from ( ");
		sql.append("select A.AMMETER_ID AMMETER_ID,AMMETER_NAME,to_char(VALUETIME,'yyyy-mm-dd hh24:mi:ss') VALUETIME,ZVALUEZY,ERRORCODE,nvl(ISPUSHUP,-1) ISPUSHUP  from "
				+ "(select * from AMMETERERRORDATAS where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and errorcode<100 and (" );
		for(AmmeterModel a:amList){
			sql.append("ammeter_id="+a.getAmmeterID()+" or ");
		}
		sql.append("ammeter_id="+0);
		sql.append(") order by ValueTime ,ammeter_id ) A ");
		sql.append("left join (select ammeter_id,ammeter_name from ammeter where ");
		for(AmmeterModel a:amList){
			sql.append("ammeter_id="+a.getAmmeterID()+" or ");
		}
		sql.append("ammeter_id="+0);
		sql.append(" ) B on A.ammeter_id=B.ammeter_id ");
		sql.append(" )) A WHERE ROWNUM <= ?) WHERE RN >= ?");
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql.toString());
			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			ps.setInt(3, endCount);
			ps.setInt(4, startCount);
			//System.out.println(startCount+" "+endCount);
			//System.out.println(startString+" "+endString);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				if (list == null)
					list = new ArrayList<ErrorDataModel>();

				ErrorDataModel amd = new ErrorDataModel();
				amd=buildIstance(rs);
				amd.setAmmeterName(rs.getString("ammeter_name"));
				list.add(amd);				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public int getAmErrorDataCountByArea(int areaID, int userID,Date start, Date end) throws SQLException{
		int count=0;
		ArchitectureDao arcdao = new ArchitectureDao();
		List<Architecture> archilist=arcdao.queryArchByAreaID(areaID, userID);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AmmeterDao aDao = new AmmeterDao();
		List<AmmeterModel> amList = new ArrayList<AmmeterModel>();
		for(Architecture a:archilist){
			amList.addAll(aDao.queryByArchID(a.getId()));
		}
		if(amList==null||amList.size()<=0){
			return count;
		}
		////System.out.println("电表个数:"+amList.size());
		StringBuffer sql = null;
		sql = new StringBuffer("select count(*) num from AMMETERERRORDATAS where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and errorcode<100 and (" );
		for(AmmeterModel a:amList){
			sql.append("ammeter_id="+a.getAmmeterID()+" or ");
		}
		sql.append("ammeter_id="+0);
		sql.append( ") order by ValueTime,Ammeter_id");
		////System.out.println("count:"+sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql.toString());
			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			/////System.out.println("count:"+startString+" "+endString);
			rs = ps.executeQuery();
			if (rs.next())
			{				
				count = rs.getInt(1);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return count;
	}
	public List<ErrorDataModel> getAmErrorDataByArea(int areaID,int userID, Date start, Date end,int pageCount, int pageIndex) throws SQLException
	{
		ArchitectureDao arcdao = new ArchitectureDao();
		List<Architecture> archilist=arcdao.queryArchByAreaID(areaID, userID);
		AmmeterDao aDao = new AmmeterDao();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<AmmeterModel> amList = new ArrayList<AmmeterModel>();
		for(Architecture a:archilist){
			amList.addAll(aDao.queryByArchID(a.getId()));
		}
		if(amList==null||amList.size()<=0){
			return null;
		}
		List<ErrorDataModel> list = null;
		int startCount = pageIndex*pageCount+1;
		int endCount = (pageIndex+1)*pageCount;
		StringBuffer sql = null;
		sql = new StringBuffer("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select * from ( ");
		sql.append("select A.AMMETER_ID AMMETER_ID,AMMETER_NAME,to_char(VALUETIME,'yyyy-mm-dd hh24:mi:ss') VALUETIME,ZVALUEZY,ERRORCODE,nvl(ISPUSHUP,-1) ISPUSHUP  from "
				+ "(select * from AMMETERERRORDATAS where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and errorcode<100 and (" );
		for(AmmeterModel a:amList){
			sql.append("ammeter_id="+a.getAmmeterID()+" or ");
		}
		sql.append("ammeter_id="+0);
		sql.append(") order by ValueTime ,ammeter_id ) A ");
		sql.append("left join (select ammeter_id,ammeter_name from ammeter where ");
		for(AmmeterModel a:amList){
			sql.append("ammeter_id="+a.getAmmeterID()+" or ");
		}
		sql.append("ammeter_id="+0);
		sql.append(" ) B on A.ammeter_id=B.ammeter_id ");
		sql.append(" )) A WHERE ROWNUM <= ?) WHERE RN >= ?");
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql.toString());
			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			ps.setInt(3, endCount);
			ps.setInt(4, startCount);
			//System.out.println(startCount+" "+endCount);
			//System.out.println(startString+" "+endString);
			rs = ps.executeQuery();
			// rs.previous();
			while (rs.next())
			{
				if (list == null)
					list = new ArrayList<ErrorDataModel>();

				ErrorDataModel amd = new ErrorDataModel();
				amd=buildIstance(rs);
				amd.setAmmeterName(rs.getString("ammeter_name"));
				list.add(amd);
				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public boolean deleteErrorDatasByAm(int Ammeter_ID,Date start, Date end){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from AMMETERERRORDATAS where Ammeter_ID=? and ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and errorcode<100 ";
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Ammeter_ID);
			String startString = df.format(start);
			ps.setString(2, startString);
			String endString = df.format(end);
			ps.setString(3, endString);
			int count = ps.executeUpdate();
			////System.out.println("按电表删除:"+count);
			b=(count>=1);
		} catch (SQLException e){
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	public boolean deleteErrorDatasByFloor(int archID,int floorID,Date start, Date end) throws SQLException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AmmeterDao aDao = new AmmeterDao();
		List<AmmeterModel> amList = aDao.queryByArchAndFloor(archID, floorID);
		if(amList==null||amList.size()<=0){
			return true;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from AMMETERERRORDATAS where errorcode<100 and (";
		for(AmmeterModel a:amList){
			sql+="ammeter_id="+a.getAmmeterID()+" or ";
		}
		sql +="ammeter_id="+0;
		sql+=") and ValueTime between ";
		sql+="to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')";
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			int count = ps.executeUpdate();
			////System.out.println("按楼层删除:"+count);
			b=(count>=1);
		} catch (SQLException e){
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	public boolean deleteErrorDatasByArch(int archID,Date start, Date end) throws SQLException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AmmeterDao aDao = new AmmeterDao();
		List<AmmeterModel> amList = aDao.queryByArchID(archID);
		if(amList==null||amList.size()<=0){
			return true;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from AMMETERERRORDATAS where errorcode<100 and (";
		for(AmmeterModel a:amList){
			sql+="ammeter_id="+a.getAmmeterID()+" or ";
		}
		sql +="ammeter_id="+0;
		sql+=") and ValueTime between ";
		sql+="to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')";
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			int count = ps.executeUpdate();
			////System.out.println("按建筑删除:"+count);
			b=(count>=1);
		} catch (SQLException e){
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	public boolean deleteErrorDatasByArea(int areaID,int userID,Date start, Date end) throws SQLException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArchitectureDao arcdao = new ArchitectureDao();
		List<Architecture> archilist=arcdao.queryArchByAreaID(areaID, userID);
		AmmeterDao aDao = new AmmeterDao();
		List<AmmeterModel> amList = new ArrayList<AmmeterModel>();
		for(Architecture a:archilist){
			amList.addAll(aDao.queryByArchID(a.getId()));
		}
		if(amList==null||amList.size()<=0){
			return true;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from AMMETERERRORDATAS where errorcode<100 and (";
		for(AmmeterModel a:amList){
			sql+="ammeter_id="+a.getAmmeterID()+" or ";
		}
		sql +="ammeter_id="+0;
		sql+=") and ValueTime between ";
		sql+="to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')";
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			int count = ps.executeUpdate();
			////System.out.println("按区域删除:"+count);
			b=(count>=1);
		} catch (SQLException e){
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	public boolean deleteOneErrorData(int Ammeter_ID,String ValueTime,float ZvalueZY){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from AMMETERERRORDATAS where Ammeter_ID=? and ValueTime=to_date(?,'yyyy-mm-dd hh24:mi:ss') and ZvalueZY=?";
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Ammeter_ID);
			ps.setString(2, ValueTime);
			ps.setFloat(3, ZvalueZY);
			b=(ps.executeUpdate()>=1);
		} catch (SQLException e){
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	private ErrorDataModel buildIstance(ResultSet rs) throws SQLException{
		ErrorDataModel e = new ErrorDataModel();
		e.setAmmeterID(rs.getInt("ammeter_id"));
		e.setErrorCode(rs.getInt("errorcode"));
		e.setValueTime(rs.getString("valuetime"));
		e.setZvalueZY(rs.getFloat("zvaluezy"));
		e.setIsPushUp(rs.getInt("ispushup"));
		return e;
	}
}
