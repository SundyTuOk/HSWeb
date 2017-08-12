package com.sf.energy.project.right.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebService;

import com.sf.energy.project.right.model.ModuleModel;
import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.project.right.service.ListUsersModule;
import com.sf.energy.util.ConnDB;


/**
 * 用户和功能模块之间的相关权限操作
 * 本类中包含通过用户信息查询出该用户具有的模块信息
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */
@WebService
public class ListUsersModuleImpl implements ListUsersModule
{
	
	/**
	 * 获取某一个用户拥有的模块权限
	 * @param um 用户实体类的实例
	 * @return 获取用户对应的模块
	 * @throws SQLException
	 */
	public ArrayList<ModuleModel> getUsersModule(UsersModel um) throws SQLException 
	{
		ArrayList<ModuleModel> lst = new ArrayList<ModuleModel>();
		ModuleModel mm=null;
		
		//查询视图V_UsersToModule
		String sql="SELECT * FROM V_UsersToModule WHERE Users_ID="+um.getUsersID();
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				//每次都要new一个新的ModuleModel实例
				mm=new ModuleModel();
				
				mm.setModuleID(rs.getInt(7));
				mm.setModuleName(rs.getString(8));
				mm.setModuleNum(rs.getString(9));
				mm.setModuleAddress(rs.getString(11));
				mm.setModuleParent(rs.getInt(10));
				mm.setModulePart1(rs.getString(12));
				mm.setModuleImage(rs.getString(13));
				mm.setModuleOrder(rs.getInt(14));
				
				lst.add(mm);	
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return  lst;
	}
	
	/**
	 * 获取某一个用户拥有的模块权限、
	 * （在实际的每个sidebar.jsp中使用）
	 * @param id 用户ID
	 * @return  获取用户对应的模块
	 * @throws SQLException
	 */
	public ArrayList<ModuleModel> getUsersModuleByID(int id) throws SQLException 
	{
		ArrayList<ModuleModel> lst = new ArrayList<ModuleModel>();
		ModuleModel mm=null;
		
		//查询视图V_UsersToModule
		String sql="SELECT distinct module_Name,module_id ,module_num,module_parent,module_order,module_image  FROM V_UsersToModule  WHERE Users_ID="+id+" order by module_order desc";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				//每次都要new一个新的ModuleModel实例
				mm=new ModuleModel();
				
				mm.setModuleID(rs.getInt("Module_ID"));
				mm.setModuleName(rs.getString("Module_Name"));
				mm.setModuleNum(rs.getString("Module_Num"));
				//mm.setModuleAddress(rs.getString(11));
				mm.setModuleParent(rs.getInt("Module_Parent"));
				//mm.setModulePart1(rs.getString());
				mm.setModuleImage(rs.getString("module_image"));
				mm.setModuleOrder(rs.getInt("Module_Order"));
				
				lst.add(mm);	
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return  lst;
	}
	
	
	/**
	 * 获取某一个用户拥有的模块权限
	 * @param id 用户ID
	 * @return 获取用户对应的模块
	 * @throws SQLException
	 */
	public ArrayList<String> getUsersRootModuleNameByID(int id) throws SQLException
	{
		ArrayList<String> lst = new ArrayList<String>();

		
		//查询视图V_UsersToModule
		String sql="SELECT distinct Module_Name FROM V_UsersToModule WHERE Users_ID="+id+" and Module_Parent=0";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next())
			{

				lst.add(rs.getString("Module_Name"));	
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return  lst;
	}
	
	/**
	 * 获取模块路径
	 * @param id 用户ID
	 * @return 获取用户对应的模块
	 * @throws SQLException
	 */
	public String getUsersModuleURLByName(String moduleName) throws SQLException
	{
		String url="";
		//查询视图V_UsersToModule
		String sql="SELECT Module_Address FROM Module WHERE Module_Name='"+moduleName+"' and module_Parent=0";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				url=rs.getString("Module_Address");	
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return  url;
	}
	
}
