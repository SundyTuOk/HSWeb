package com.sf.energy.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Oracle数据库的连接操作类 懒汉式（延迟加载模式）的单例模式
 * 
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */

public class ConnDB
{
//	private static Connection conn = null;
//    private static Statement stmt = null;
//    private static ResultSet rs = null;
	private static DataSource ds = null;
    /**
    * @Method: getConnection
    * @Description: 从数据源中获取数据库连接
    * @Anthor:孤傲苍狼
    * @return Connection
    * @throws SQLException
    */ 
    public static Connection getConnection() throws SQLException{
        //从数据源中获取数据库连接
    	InitialContext ctx;
		try
		{
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbcoracle");
//			conn=ds.getConnection();
		} catch (NamingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	//通过JNDI查找DataSource
    	return ds.getConnection();
    }
    
    /**
    * @Method: release
    * @Description: 释放资源，
    * 释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象
    * @Anthor:孤傲苍狼
    *
    * @param conn
    * @param st
    * @param rs
    */ 
    public static void release(Connection conn,PreparedStatement ps,ResultSet rs){
        if(rs!=null){
            try{
                //关闭存储查询结果的ResultSet对象
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(ps!=null){
            try{
                //关闭负责执行SQL命令的Statement对象
            	ps.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if(conn!=null){
            try{
                //将Connection连接对象还给数据库连接池
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void release(Connection conn,CallableStatement cs,ResultSet rs){
        if(rs!=null){
            try{
                //关闭存储查询结果的ResultSet对象
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(cs!=null){
            try{
                //关闭负责执行SQL命令的Statement对象
            	cs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if(conn!=null){
            try{
                //将Connection连接对象还给数据库连接池
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * @Method: release
     * @Description: 释放资源，
     * 释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象
     * @Anthor:孤傲苍狼
     *
     * @param conn
     * @param st
     * @param rs
     */ 
     public static void release(Connection conn,PreparedStatement ps){
        
         if(ps!=null){
             try{
                 //关闭负责执行SQL命令的Statement对象
             	ps.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
         
         if(conn!=null){
             try{
                 //将Connection连接对象还给数据库连接池
                 conn.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }
     public static void release(PreparedStatement ps,ResultSet rs){
    	 
    	 if(ps!=null){
    		 try{
    			 //关闭负责执行SQL命令的Statement对象
    			 ps.close();
    		 }catch (Exception e) {
    			 e.printStackTrace();
    		 }
    	 }
    	 
    	 if(rs!=null){
    		 try{
    			 //将Connection连接对象还给数据库连接池
    			 rs.close();
    		 }catch (Exception e) {
    			 e.printStackTrace();
    		 }
    	 }
     }
     public static void release(PreparedStatement ps){
    	 
    	 if(ps!=null){
    		 try{
    			 //关闭负责执行SQL命令的Statement对象
    			 ps.close();
    		 }catch (Exception e) {
    			 e.printStackTrace();
    		 }
    	 }
    	
     }
     /**
      * @Method: release
      * @Description: 释放资源，
      * 释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象
      * @Anthor:孤傲苍狼
      *
      * @param conn
      * @param st
      * @param rs
      */ 
      public static void release(Connection conn,Statement ps){
         
          if(ps!=null){
              try{
                  //关闭负责执行SQL命令的Statement对象
              	ps.close();
              }catch (Exception e) {
                  e.printStackTrace();
              }
          }
          
          if(conn!=null){
              try{
                  //将Connection连接对象还给数据库连接池
                  conn.close();
              }catch (Exception e) {
                  e.printStackTrace();
              }
          }
      }
    /**
     * 执行查找操作
     * 
     * @param sql
     *            要执行的sql语句
     * @return ResultSet 执行的结果集
     */
    public static ResultSet executeQuery(String sql)
    {
    	 Statement stmt = null;
    	 ResultSet rs = null;
    	 Connection conn = null;
        try
        {
            conn = getConnection();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
        if(stmt!=null){
            try{
                //关闭负责执行SQL命令的Statement对象
           	 stmt.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try{
                //将Connection连接对象还给数据库连接池
            	conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    /**
     * 执行数据库增、删、改操作
     * 
     * @param sql
     *            要执行的sql语句
     * @return result 返回执行结果条数
     * @throws SQLException
     */
    public static int executeUpdate(String sql) throws SQLException
    {
    	 Statement stmt = null;
         int result = 0;
         Connection conn = getConnection();
         stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
         result = stmt.executeUpdate(sql);
         
         if(stmt!=null){
             try{
                 //关闭负责执行SQL命令的Statement对象
            	 stmt.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
         
         if(conn!=null){
             try{
                 //将Connection连接对象还给数据库连接池
                 conn.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return result;
    }

    public static List<OracleDataSet> execQuery(String sql) throws SQLException
    {
        List<OracleDataSet> list = new LinkedList<OracleDataSet>();
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet trs = null;
   	 	ResultSet rs = null;
        try
        {
            ps = conn.prepareStatement(sql);
            trs = ps.executeQuery();

            while (trs.next())
            {
                OracleDataSet ods = new OracleDataSet(rs);
                list.add(ods);
            }

        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (trs != null)
                {
                    trs.close();
                }

                if (ps != null)
                {
                    ps.close();
                }
                if(conn!=null){
                    try{
                        //将Connection连接对象还给数据库连接池
                        conn.close();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
       
        return list;
    }

    public static int execUpdate(String sql) throws SQLException
    {
        Connection theConn = getConnection();
        int result = -1;
        PreparedStatement ps = null;
        try
        {
            theConn.setAutoCommit(false);
            ps = theConn.prepareStatement(sql);

            result = ps.executeUpdate();

            if (ps != null)
            {
                ps.close();
            }

            theConn.commit();

            theConn.setAutoCommit(true);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try
            {
                theConn.rollback();

            }
            catch (SQLException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            result = -1;
        }
        finally
        {
            try
            {
                if (ps != null)
                {
                    ps.close();
                }
                if(theConn!=null){
                    try{
                        //将Connection连接对象还给数据库连接池
                    	theConn.close();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return result;
    }
    public static void releasePs(PreparedStatement ps, ResultSet rs)
 	{
    	 if(rs!=null){
    		 try{
    			 //将Connection连接对象还给数据库连接池
    			 rs.close();
    		 }catch (Exception e) {
    			 e.printStackTrace();
    		 }
    	 }
    	 if(ps!=null){
    		 try{
    			 //关闭负责执行SQL命令的Statement对象
    			 ps.getConnection().close();
    			 ps.close();
    		 }catch (Exception e) {
    			 e.printStackTrace();
    		 }
    	 }
 	}
    /**
     * 关闭连接
     *//*
    public static void close()
    {

        try
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }

            if (stmt != null)
            {
                stmt.close();
                stmt = null;
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }*/

}
