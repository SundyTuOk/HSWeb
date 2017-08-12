package com.sf.energy.project.right.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.MD5Encryption;

/**
 * 对数据表【Users】操作， 实现数据表【Users】增、删、改、查操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */

public class UsersDao
{
	int total;

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	/**
	 * 列出所有用户信息
	 * 
	 * @return 用户实体类的实例
	 * @throws SQLException
	 */
	public ArrayList<UsersModel> listUsers() throws SQLException
	{
		AreaDao ad = new AreaDao();
		DepartmentDao dd = new DepartmentDao();
		String usersAreaNameString = null;
		String userPartmentNameString = null;
		String usersStateString = null;
		String usersStateIDName = null;

		ArrayList<UsersModel> lst = new ArrayList<UsersModel>();
		UsersModel um = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Users ORDER BY Users_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);		
			rs = ps.executeQuery();
			while (rs.next())
			{
				um = new UsersModel();

				um.setUsersID(rs.getInt(1));
				// um.setAreaID(rs.getInt(2));
				usersAreaNameString = ad.queryNameById(rs.getInt("Area_ID"));
				um.setUserAreaName(usersAreaNameString);

				um.setUsersNum(rs.getString(3));
				um.setUsersName(rs.getString(4));
				um.setIsAlarm(rs.getInt("ISALARM"));
				um.setUsersBirth(rs.getString(8));
				um.setUsersDepartment(rs.getInt("USERS_DEPARTMENT"));
				userPartmentNameString = dd.queryNameById(rs.getInt("USERS_DEPARTMENT"));
				um.setUsersPartmentName(userPartmentNameString);

				um.setUsersGender(rs.getString(7));
				um.setUsersHomeAddress(rs.getString(14));
				um.setUsersLoginName(rs.getString(5));
				um.setUsersPassword(rs.getString(6));
				um.setUsersPhone(rs.getString(11));
				um.setUsersPhone1(rs.getString(12));
				um.setUsersPhone2(rs.getString(13));
				um.setUsersPhoto(rs.getString(9));
				um.setUsersRemark(rs.getString("Users_Remark"));

				if (rs.getInt("ONLINERE") == 0)
					usersStateString = "<span style=\"color:red\" class=\"offline\">离线</span>";
				else
				{
					usersStateString = "<span style=\"color:green\" class=\"online\">在线</span>";
				}
				um.setOnlineName(usersStateString);

				um.setIP(rs.getString("IP"));
				um.setUsersLastTime(rs.getString("USERS_LASTTIME"));

				if (rs.getInt("USERSTATE_ID") == 0)
					usersStateIDName = "离职";
				else
				{
					usersStateIDName = "在职";
				}
				um.setUsersStateIDName(usersStateIDName);
				um.setUrlHost(rs.getString("URLHOST"));
				um.setUsersRegTime(rs.getString("USERS_REGTIME"));

				lst.add(um);
			}
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	/**
	 * 列出所有用户信息
	 * 
	 * @return 用户实体类的实例
	 * @throws SQLException
	 */
	public ArrayList<UsersModel> listUsers(int pageCount, int pageIndex) throws SQLException
	{
		AreaDao ad = new AreaDao();
		DepartmentDao dd = new DepartmentDao();
		String usersAreaNameString = null;
		String userPartmentNameString = null;
		String usersStateString = null;
		String usersStateIDName = null;

		ArrayList<UsersModel> lst = new ArrayList<UsersModel>();
		UsersModel um = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Users ORDER BY Users_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return lst;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				um = new UsersModel();

				um.setUsersID(rs.getInt(1));
				// um.setAreaID(rs.getInt(2));
				usersAreaNameString = ad.queryNameById(rs.getInt("Area_ID"));
				um.setUserAreaName(usersAreaNameString);

				um.setUsersNum(rs.getString(3));
				um.setUsersName(rs.getString(4));
				um.setIsAlarm(rs.getInt("ISALARM"));
				um.setUsersBirth(rs.getString(8));
				um.setUsersDepartment(rs.getInt("USERS_DEPARTMENT"));
				userPartmentNameString = dd.queryNameById(rs.getInt("USERS_DEPARTMENT"));
				um.setUsersPartmentName(userPartmentNameString);

				um.setUsersGender(rs.getString(7));
				um.setUsersHomeAddress(rs.getString(14));
				um.setUsersLoginName(rs.getString(5));
				um.setUsersPassword(rs.getString(6));
				um.setUsersPhone(rs.getString(11));
				um.setUsersPhone1(rs.getString(12));
				um.setUsersPhone2(rs.getString(13));
				um.setUsersPhoto(rs.getString(9));
				um.setUsersRemark(rs.getString("Users_Remark"));

				if (rs.getInt("ONLINERE") == 0)
					usersStateString = "<span style=\"color:red\" class=\"offline\">离线</span>";
				else
				{
					usersStateString = "<span style=\"color:green\" class=\"online\">在线</span>";
				}
				um.setOnlineName(usersStateString);

				um.setIP(rs.getString("IP"));
				um.setUsersLastTime(rs.getString("USERS_LASTTIME"));

				if (rs.getInt("USERSTATE_ID") == 0)
					usersStateIDName = "离职";
				else
				{
					usersStateIDName = "在职";
				}
				um.setUsersStateIDName(usersStateIDName);
				um.setUrlHost(rs.getString("URLHOST"));
				um.setUsersRegTime(rs.getString("USERS_REGTIME"));

				lst.add(um);

				count--;
			}
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	/**
	 * 通过用户ID查询用户信息
	 * 
	 * @param id
	 *            用户id
	 * @return um 用户实体类的实例
	 * @throws SQLException
	 */
	public UsersModel queryById(int id) throws SQLException
	{
		UsersModel um = null;
		String sql = "SELECT * FROM Users WHERE Users_ID=" + id;
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
				um = new UsersModel();
				um.setUsersID(rs.getInt(1));
				um.setAreaID(rs.getInt(2));
				um.setUsersNum(rs.getString(3));
				um.setUsersName(rs.getString(4));
				um.setIsAlarm(rs.getInt("ISALARM"));
				um.setUsersBirth(rs.getString(8));
				um.setUsersDepartment(rs.getInt(10));
				um.setUsersGender(rs.getString(7));
				um.setUsersHomeAddress(rs.getString(14));
				um.setUsersLoginName(rs.getString(5));
				um.setUsersPassword(rs.getString(6));
				um.setUsersPhone(rs.getString(11));
				um.setUsersPhone1(rs.getString(12));
				um.setUsersPhone2(rs.getString(13));
				um.setUsersPhoto(rs.getString(9));
				um.setUsersRemark(rs.getString("Users_Remark"));
				um.setOnLine(rs.getInt("ONLINERE"));
				um.setIP(rs.getString("IP"));
				um.setUsersLastTime(rs.getString("USERS_LASTTIME"));
				um.setUsersStateID(rs.getInt("USERSTATE_ID"));
				um.setUrlHost(rs.getString("URLHOST"));
				um.setUsersRegTime(rs.getString("USERS_REGTIME"));
			}
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return um;
	}

	public String queryUserBaojingFL(int id) throws SQLException
	{
		String fl = "";
		String sql = "SELECT ROLES_REMARK FROM ROLES WHERE ROLES_ID=(SELECT ROLES_ID FROM USERS_ROLES WHERE USERS_ID="+id+")";
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
				fl = rs.getString(1);
			}
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return fl;
	}
	/**
	 * 通过登录名查询用户信息
	 * 
	 * @param name
	 *            登录用户名
	 * @return um 用户实体类的实例
	 * @throws SQLException
	 */
	public UsersModel queryByName(String name) throws SQLException
	{
		UsersModel um = null;
		String sql = "SELECT * FROM Users  WHERE Users_LoginName='" + name + "'";
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
				um = new UsersModel();
				um.setUsersID(rs.getInt(1));
				um.setAreaID(rs.getInt(2));
				um.setUsersNum(rs.getString(3));
				um.setUsersName(rs.getString(4));
				um.setIsAlarm(rs.getInt("ISALARM"));
				um.setUsersBirth(rs.getString(8));
				um.setUsersDepartment(rs.getInt(10));
				um.setUsersGender(rs.getString(7));
				um.setUsersHomeAddress(rs.getString(14));
				um.setUsersLoginName(rs.getString(5));
				um.setUsersPassword(rs.getString(6));
				um.setUsersPhone(rs.getString(11));
				um.setUsersPhone1(rs.getString(12));
				um.setUsersPhone2(rs.getString(13));
				um.setUsersPhoto(rs.getString(9));
				um.setUsersRemark(rs.getString("Users_Remark"));
				um.setOnLine(rs.getInt("ONLINERE"));
				um.setIP(rs.getString("IP"));
				um.setUsersLastTime(rs.getString("USERS_LASTTIME"));
				um.setUsersStateID(rs.getInt("USERSTATE_ID"));
				um.setUrlHost(rs.getString("URLHOST"));
				um.setUsersRegTime(rs.getString("USERS_REGTIME"));
			}
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return um;
	}
	
	public int queryIdByLoginName(String name) throws SQLException
	{
		int id = 1;
		String sql = "SELECT users_id FROM Users  WHERE Users_LoginName='" + name + "'";
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
				id = rs.getInt(1) ;
			}
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return id;
	}

	/**
	 * 新增用戶
	 * 
	 * @param um
	 *            用户实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean insert(UsersModel u) throws SQLException
	{
		boolean b = false;
		String sql = "INSERT INTO Users (Area_ID,Users_Num,Users_Name,Users_LoginName,Users_Password,Users_Gender,Users_Birth,Users_Photo,Users_Department,Users_Phone,Users_Phone1,Users_Phone2,Users_HomeAddress,USERS_LASTTIME,USERS_REGTIME,USERSTATE_ID,USERS_REMARK,ONLINERE,IP,IsAlarm,URLHOST) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getAreaID());
			ps.setString(2, u.getUsersNum());
			ps.setString(3, u.getUsersName());
			ps.setString(4, u.getUsersLoginName());
			ps.setString(5, MD5Encryption.MD5(u.getUsersPassword()));
			ps.setString(6, u.getUsersGender());
			ps.setString(7, u.getUsersBirth());
			ps.setString(8, u.getUsersPhoto());
			ps.setInt(9, u.getUsersDepartment());
			ps.setString(10, u.getUsersPhone());
			ps.setString(11, u.getUsersPhone1());
			ps.setString(12, u.getUsersPhone2());
			ps.setString(13, u.getUsersHomeAddress());
			ps.setString(14, u.getUsersLastTime());
			ps.setString(15, u.getUsersRegTime());
			ps.setInt(16, u.getUsersStateID());
			ps.setString(17, u.getUsersRemark());
			ps.setInt(18, u.getOnLine());
			ps.setString(19, u.getIP());
			ps.setInt(20, u.getIsAlarm());
			ps.setString(21, u.getUrlHost());

			b = (ps.executeUpdate() == 1);
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;

	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 *            用户id
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */

	public boolean delete(int id) throws SQLException
	{
		boolean b = false;
		String sql = "DELETE FROM Users WHERE Users_ID=" + id;

		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;

	}

	/**
	 * 删除操作员，以及其他相关联的表
	 * 
	 * @param usersID
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteAll(int usersID) throws SQLException
	{
		Connection conn=null;
		Statement st=null;
		try
		{
			conn = ConnDB.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			st.executeUpdate("delete from Users WHERE Users_ID=" + usersID);
			st.executeUpdate("delete from Users_Roles where Users_ID= " + usersID);
			st.executeUpdate("delete from OprArea_List where Users_ID= " + usersID);
			st.executeUpdate("delete from OprArc_List where Users_ID= " + usersID);
			st.executeUpdate("delete from OprPartment_List where Users_ID= " + usersID);
			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} catch (Exception e)
		{
			conn.rollback();
			return false;
		} finally
		{
			ConnDB.release(conn, (PreparedStatement) st);
		}

	}

	/**
	 * 修改用户信息
	 * 
	 * @param um
	 *            用户实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean modify(UsersModel u) throws SQLException
	{
		boolean b = false;
		String sql = "UPDATE Users SET Area_ID=" + u.getAreaID() + ",Users_Num='" + u.getUsersNum() + "',Users_Name='" + u.getUsersName()
				+ "',Users_LoginName='" + u.getUsersLoginName() + "',Users_Password='" + MD5Encryption.MD5(u.getUsersPassword()) + "',Users_Birth='"
				+ u.getUsersBirth() + "',Users_Gender='" + u.getUsersGender() + "',Users_Photo='" + u.getUsersPhoto() + "',Users_Department='"
				+ u.getUsersDepartment() + "',Users_Phone='" + u.getUsersPhone() + "',Users_Phone1='" + u.getUsersPhone1() + "',Users_Phone2='"
				+ u.getUsersPhone2() + "',Users_HomeAddress='" + u.getUsersHomeAddress() + "',USERS_REGTIME='" + u.getUsersRegTime()
				+ "',USERSTATE_ID=" + u.getUsersStateID() + ", USERS_REMARK='" + u.getUsersRemark() + "',ONLINERE=" + u.getOnLine() + ", IP='"
				+ u.getIP() + "', IsAlarm=" + u.getIsAlarm() + ",URLHOST='" + u.getUrlHost() + "' WHERE Users_ID=" + u.getUsersID();

		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;

	}

	/**
	 * 修改登录成功后的用户登录信息
	 * 
	 * @param loginIP
	 *            登录IP
	 * @param loginTime
	 *            上次登录时间
	 * @param usersOnline
	 *            是否在线
	 * @param usersID
	 *            用户ID
	 * @return 是否修改成功
	 * @throws SQLException
	 */
	public boolean modifyLoginInfo(String loginIP, String loginTime, int usersOnline, String loginName) throws SQLException
	{
		boolean b = false;
		String sql = "UPDATE Users SET IP=? , USERS_LASTTIME=?, ONLINERE=? where Users_LoginName='" + loginName + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginIP);
			ps.setString(2, loginTime);
			ps.setInt(3, usersOnline);
			b = (ps.executeUpdate() == 1);
		} finally
		{
			ConnDB.release(conn, ps);
		}

		return b;

	}

	/**
	 * 修改登录成功后的用户登录信息
	 * 
	 * @param loginIP
	 *            登录IP
	 * @param loginTime
	 *            上次登录时间
	 * @param usersOnline
	 *            是否在线
	 * @param usersID
	 *            用户ID
	 * @return 是否修改成功
	 * @throws SQLException
	 */
	public boolean modifyLogOutInfo(int usersOnline, int usersID) throws SQLException
	{
		boolean b = false;
		String sql = "UPDATE Users SET ONLINERE=? where Users_ID=" + usersID;
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, usersOnline);
			b = (ps.executeUpdate() == 1);
		} finally
		{
			ConnDB.release(conn, ps);
		}

		return b;
	}

	/**
	 * 新增操作员之前，判断是否有相同登录名
	 * 
	 * @param loginName
	 * @return
	 * @throws SQLException
	 */
	public boolean hasSameLoginName(String loginName) throws SQLException
	{
		boolean b = false;
		String sql = "select Users_ID from users where Users_LoginName='" + loginName + "'";
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
				b = true;
			}
		} finally
		{

			ConnDB.release(conn, ps, rs);
		}
		return b;

	}

	public List<UsersModel> getAllUsers() throws SQLException
	{
		
		ArrayList<UsersModel> lst = new ArrayList<UsersModel>();
		UsersModel um = null;
		
		String sql = "SELECT * FROM Users";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				um=new UsersModel();
				um.setUsersID(rs.getInt("users_id"));
				um.setUsersName(rs.getString("users_name"));
				lst.add(um);
			}

		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}
	
	public boolean deleteButton(int Users_ID) throws SQLException{
		boolean b = false;
		String sql = "update users set Users_Birth='' where Users_ID=" + Users_ID + "";
		Connection conn=null;
		PreparedStatement ps =null;
		

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			int a=ps.executeUpdate();
			if(a==1){
				b=true;
			}
		} finally
		{

			ConnDB.release(conn, ps);
		}
		return b;
	}

	public boolean updateButton(int Users_ID,String IDList) throws SQLException{
		boolean b = false;
		String sql = "update users set Users_Birth='"+IDList+"' where Users_ID=" + Users_ID + "";
		Connection conn=null;
		PreparedStatement ps =null;
		

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			int a=ps.executeUpdate();
			if(a==1){
				b=true;
			}
		} finally
		{

			ConnDB.release(conn, ps);
		}
		return b;
	}
	public String getButton(int Users_ID) throws SQLException{
		String b = "";
		String sql = "select Users_Birth from users where Users_ID=" + Users_ID + "";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs=null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				b=rs.getString("Users_Birth");
			}
		} finally
		{

			ConnDB.release(conn, ps,rs);
		}
		return b;
	}
	
}