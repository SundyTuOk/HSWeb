package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.sf.energy.project.system.model.DictionaryModel;
import com.sf.energy.util.ConnDB;

public class DictionaryDao
{
    public boolean updateDictionary(DictionaryModel dm) throws SQLException
    {
        boolean flag = false;

        String sql = "UPDATE Dictionary SET Dictionary_Name= ? , Dictionary_Remark = ? WHERE Dictionary_ID = ?";

        Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, dm.getDictionaryName());
			ps.setString(2, dm.getDictionaryRemark());
			ps.setInt(3, dm.getDictionaryID());

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

    public List<DictionaryModel> getAllDictionary() throws SQLException
    {
        List<DictionaryModel> list = null;

        String sql = "select * from Dictionary order by Dictionary_ID desc";
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
					 list = new LinkedList<DictionaryModel>();
				 DictionaryModel dm = new DictionaryModel();
				 dm.setDictionaryID(rs.getInt("Dictionary_ID"));
				 dm.setDictionaryName(rs.getString("Dictionary_Name"));
				 dm.setDictionaryRemark(rs.getString("Dictionary_Remark"));
				 list.add(dm);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next())
//        {
//            if (list == null)
//                list = new LinkedList<DictionaryModel>();
//
//            DictionaryModel dm = new DictionaryModel();
//            dm.setDictionaryID(rs.getInt("Dictionary_ID"));
//            dm.setDictionaryName(rs.getString("Dictionary_Name"));
//            dm.setDictionaryRemark(rs.getString("Dictionary_Remark"));
//
//            list.add(dm);
//        }
//
//        if (rs != null)
//            rs.close();
//
//        if (ps != null)
//            ps.close();

        return list;
    }

    public DictionaryModel getDictionaryByID(int theDictionaryID)
            throws SQLException
    {
        DictionaryModel dm = null;

        String sql = "select * from Dictionary where Dictionary_ID=?";

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			 if (rs.next())
		        {
		            dm = new DictionaryModel();

		            dm.setDictionaryID(rs.getInt("Dictionary_ID"));
		            dm.setDictionaryName(rs.getString("Dictionary_Name"));
		            dm.setDictionaryRemark(rs.getString("Dictionary_Remark"));
		        }

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//
//        ps.setInt(1, theDictionaryID);
//
//        ResultSet rs = ps.executeQuery();
//
//        if (rs.next())
//        {
//            dm = new DictionaryModel();
//
//            dm.setDictionaryID(rs.getInt("Dictionary_ID"));
//            dm.setDictionaryName(rs.getString("Dictionary_Name"));
//            dm.setDictionaryRemark(rs.getString("Dictionary_Remark"));
//        }
//
//        if (rs != null)
//            rs.close();
//
//        if (ps != null)
//            ps.close();
        return dm;
    }

    public DictionaryModel getDictionaryByName(String theDictionaryName)
            throws SQLException
    {
        DictionaryModel dm = null;

        String sql = "select * from Dictionary where Dictionary_Name=?";
        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			 if (rs.next())
		        {
		            dm = new DictionaryModel();

		            dm.setDictionaryID(rs.getInt("Dictionary_ID"));
		            dm.setDictionaryName(rs.getString("Dictionary_Name"));
		            dm.setDictionaryRemark(rs.getString("Dictionary_Remark"));
		        }

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//
//        ps.setString(1, theDictionaryName);
//
//        ResultSet rs = ps.executeQuery();
//
//        if (rs.next())
//        {
//            dm = new DictionaryModel();
//
//            dm.setDictionaryID(rs.getInt("Dictionary_ID"));
//            dm.setDictionaryName(rs.getString("Dictionary_Name"));
//            dm.setDictionaryRemark(rs.getString("Dictionary_Remark"));
//        }
//
//        if (rs != null)
//            rs.close();
//
//        if (ps != null)
//            ps.close();

        return dm;
    }

    public boolean addDictionary(DictionaryModel dm) throws SQLException
    {
        boolean flag = false;

        String sql = "insert into Dictionary (Dictionary_Name,Dictionary_Remark) values (?,?)";

        if (getDictionaryByName(dm.getDictionaryName()) != null)
        {
            return false;
        }

        Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, dm.getDictionaryName());
			ps.setString(2, dm.getDictionaryRemark());

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

    public boolean deleteDictionaryByID(int theID) throws SQLException
    {
        boolean flag = false;
        String sql = null;
        sql = "delete from DictionaryValue where Dictionary_ID = ?";
        Connection conn1=null;
		PreparedStatement ps1 =null;
        try
		{
    		conn1 = ConnDB.getConnection();
    		ps1 = conn1.prepareStatement(sql);
			ps1.setInt(1, theID);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1);
		}

        sql = "delete from Dictionary where Dictionary_ID = ?";

        Connection conn=null;
		PreparedStatement ps =null;
        try
		{
    		conn = ConnDB.getConnection();
    		ps = conn.prepareStatement(sql);
			ps.setInt(1, theID);

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

    public boolean deleteDictionaryByName(String theName) throws SQLException
    {
        boolean flag = false;

        String sql = "delete from Dictionary where Dictionary_Name = ?";

        Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, theName);
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

}
