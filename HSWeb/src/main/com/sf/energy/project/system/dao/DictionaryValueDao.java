package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.util.ConnDB;

public class DictionaryValueDao
{
	public List<DictionaryValueModel> getAllDictValue() throws SQLException
	{
		List<DictionaryValueModel> list = null;

		String sql = "select * from DictionaryValue order by Dictionary_ID desc,DictionaryValue_ID asc,DictionaryValue_num";
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
				if (list == null)
					list = new LinkedList<DictionaryValueModel>();

				DictionaryValueModel dvm = new DictionaryValueModel();
				dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
				dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
				dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
				dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));
				list.add(dvm);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			if (list == null)
//				list = new LinkedList<DictionaryValueModel>();
//
//			DictionaryValueModel dvm = new DictionaryValueModel();
//			dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
//			dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
//			dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
//			dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));
//			list.add(dvm);
//		}
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}

	/**
	 * 通过字典词条名字获取字典数据（查字典）
	 * 
	 * @param dictionaryName
	 *            字典词条名字
	 * @return 字典数据的集合
	 * @throws SQLException
	 */
	public ArrayList<DictionaryValueModel> listDictionaryValueByName(
			String dictionaryName) throws SQLException
	{
		ArrayList<DictionaryValueModel> list = new ArrayList<DictionaryValueModel>();
		DictionaryValueModel dvm;
		String sql = "Select * from DictionaryValue where Dictionary_ID= "
				+ "(select Dictionary_ID From Dictionary where Dictionary_Name='"
				+ dictionaryName + "')";
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
				dvm = new DictionaryValueModel();
				dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
				dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
				dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
				dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));
				list.add(dvm);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			dvm = new DictionaryValueModel();
//			dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
//			dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
//			dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
//			dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));
//			list.add(dvm);
//		}
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();
		return list;
	}

	/**
	 * 获取电能相应分项的一级子项
	 * 
	 * @param dictionaryName
	 *            字典词条名字
	 * 
	 * @param fenxiangValue
	 *            字典词条名字
	 * @return 字典数据的集合
	 * @throws SQLException
	 */
	public ArrayList<DictionaryValueModel> listDVByEnergy(
			String dictionaryName, String fenxiangValue) throws SQLException
	{
		ArrayList<DictionaryValueModel> list = new ArrayList<DictionaryValueModel>();
		DictionaryValueModel dvm;
		String sql = "Select * from DictionaryValue where substr(dictionaryValue_num,1,1)='"
				+ fenxiangValue
				+ "' and  Dictionary_ID= "
				+ "(select Dictionary_ID From Dictionary where Dictionary_Name='"
				+ dictionaryName + "')";
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
				dvm = new DictionaryValueModel();
				dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
				dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
				dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
				dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));
				list.add(dvm);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			dvm = new DictionaryValueModel();
//			dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
//			dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
//			dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
//			dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));
//			list.add(dvm);
//		}
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();
		return list;
	}

	public DictionaryValueModel getDictionaryValueByValueID(int theValueID)
			throws SQLException
	{
		String sql = "select * from DictionaryValue where DictionaryValue_ID=?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		DictionaryValueModel dvm = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theValueID);

			rs = ps.executeQuery();

			if (rs.next())
			{
				dvm = new DictionaryValueModel();
				dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
				dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
				dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
				dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ps.setInt(1, theValueID);
//
//		ResultSet rs = ps.executeQuery();
//
//		DictionaryValueModel dvm = null;
//
//		if (rs.next())
//		{
//			dvm = new DictionaryValueModel();
//			dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
//			dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
//			dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
//			dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));
//		}
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return dvm;
	}	

	public List<DictionaryValueModel> getDictionaryValueByDictionaryID(
			int theDictionaryID) throws SQLException
	{
		List<DictionaryValueModel> list = null;
		String sql = "select * from DictionaryValue where Dictionary_ID=?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theDictionaryID);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<DictionaryValueModel>();

				DictionaryValueModel dvm = new DictionaryValueModel();
				dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
				dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
				dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
				dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));

				list.add(dvm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ps.setInt(1, theDictionaryID);
//
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			if (list == null)
//				list = new LinkedList<DictionaryValueModel>();
//
//			DictionaryValueModel dvm = new DictionaryValueModel();
//			dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
//			dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
//			dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
//			dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));
//
//			list.add(dvm);
//		}
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();
		return list;
	}

	public boolean deleteDictionaryValueByDictionaryID(int theValueID)
			throws SQLException
	{
		boolean flag = false;

		String sql = "delete from DictionaryValue where DictionaryValue_ID=?";

		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theValueID);

			if (ps.executeUpdate() == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return flag;
	}

	public boolean deleteDictionaryValueByValueID(int theValueID)

	{
		boolean flag = false;

		String sql = "delete from DictionaryValue where DictionaryValue_ID=?";

		Connection conn=null;
		PreparedStatement ps =null;


		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theValueID);

			ps.executeUpdate();
			flag = true;
		} catch (SQLException e)
		{
			e.printStackTrace();
			flag = false;
		}finally{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public boolean addDictionaryValue(DictionaryValueModel dvm)
			throws SQLException
	{
		boolean flag = false;

		if (!checkDictionaryID(dvm.getDictionaryID()))
		{
			return false;
		}

		String sql = "insert into DictionaryValue (Dictionary_ID,DictionaryValue_Num,DictionaryValue_Value) values (?,?,?)";

		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dvm.getDictionaryID());
			ps.setString(2, dvm.getDictionaryValueNum());
			ps.setString(3, dvm.getDictionaryValue());

			if (ps.executeUpdate() == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public boolean updateDictionaryValue(DictionaryValueModel dvm)

	{
		boolean flag = false;

		String sql = "UPDATE DictionaryValue SET Dictionary_ID = ? , DictionaryValue_Num= ? , DictionaryValue_Value = ? WHERE DictionaryValue_ID = ?";
		Connection conn=null;
		PreparedStatement ps =null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dvm.getDictionaryID());
			ps.setString(2, dvm.getDictionaryValueNum());
			ps.setString(3, dvm.getDictionaryValue());
			ps.setInt(4, dvm.getDictionaryValueID());

			ps.executeUpdate();
			flag = true;
		} catch (SQLException e)
		{
			e.printStackTrace();
			flag = false;
		}finally{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public Map<String, DictionaryValueModel> getMapByValue(int dictID)
			throws SQLException
	{
		Map<String, DictionaryValueModel> map = null;
		String sql = "select * from DictionaryValue where Dictionary_ID=?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dictID);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (map == null)
					map = new HashMap<String, DictionaryValueModel>();

				DictionaryValueModel dvm = new DictionaryValueModel();
				dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
				dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
				dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
				dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));

				map.put(dvm.getDictionaryValue(), dvm);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ps.setInt(1, dictID);
//
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			if (map == null)
//				map = new HashMap<String, DictionaryValueModel>();
//
//			DictionaryValueModel dvm = new DictionaryValueModel();
//			dvm.setDictionaryValueID(rs.getInt("DictionaryValue_ID"));
//			dvm.setDictionaryID(rs.getInt("Dictionary_ID"));
//			dvm.setDictionaryValueNum(rs.getString("DictionaryValue_Num"));
//			dvm.setDictionaryValue(rs.getString("DictionaryValue_Value"));
//
//			map.put(dvm.getDictionaryValue(), dvm);
//		}
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();
		return map;
	}

	private boolean checkDictionaryID(int theID) throws SQLException
	{
		boolean flag = false;

		String sql = "select Dictionary_Name from Dictionary where Dictionary_ID= ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theID);

			rs = ps.executeQuery();

			if (rs.next())
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//
//		ps.setInt(1, theID);
//
//		ResultSet rs = ps.executeQuery();
//
//		if (rs.next())
//			flag = true;
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return flag;
	}
	
	public String  queryDicValueNumByDicIDandValue(int dicID,String value)
			throws SQLException
	{
		String sql = "select DictionaryValue_Num from DictionaryValue where Dictionary_ID=? and DictionaryValue_Value=?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String  valueID ="";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dicID);
			ps.setString(2, value);

			rs = ps.executeQuery();

			if (rs.next())
			{
				valueID = rs.getString("DictionaryValue_Num");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return valueID;
	}
}
