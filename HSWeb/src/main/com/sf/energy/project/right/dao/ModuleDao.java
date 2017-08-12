package com.sf.energy.project.right.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.project.right.model.ModuleModel;
import com.sf.energy.project.system.model.ModuleTreeNode;
import com.sf.energy.util.ConnDB;

/**
 * 对模块表【Module】操作， 实现模块表【Module】增、删、改、查操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */
public class ModuleDao
{
	/**
	 * 列出所有模块
	 * 
	 * @return 模块实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<ModuleModel> listModule() throws SQLException
	{
		ModuleModel mm = null;
		ArrayList<ModuleModel> lst = new ArrayList<ModuleModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Module ORDER BY Module_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				mm = new ModuleModel();

				mm.setModuleID(rs.getInt(1));
				mm.setModuleName(rs.getString(2));
				mm.setModuleNum(rs.getString(3));
				mm.setModuleAddress(rs.getString(4));
				mm.setModuleMark(rs.getString(5));
				mm.setModuleParent(rs.getInt(6));
				mm.setModulePart1(rs.getString(7));
				mm.setModulePart2(rs.getString(8));
				mm.setModuleImage(rs.getString(9));
				mm.setModuleRemark(rs.getString(10));
				mm.setModuleOrder(rs.getInt(11));

				lst.add(mm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	public List<ModuleTreeNode> getModuleTree() throws SQLException
	{
		List<ModuleTreeNode> treeList = null;
		Map<Integer, ModuleModel> map = getModuleMap();
		List<Integer> deleteList = null;

		if (map != null && map.size() > 0)
		{
			deleteList = new LinkedList<Integer>();

			treeList = new LinkedList<ModuleTreeNode>();

			for (Integer key : map.keySet())
			{
				ModuleModel value = map.get(key);

				if (value.getModuleParent() == 0)
				{
					ModuleTreeNode dad = new ModuleTreeNode();
					dad.setModuel(value);

					treeList.add(dad);
					deleteList.add(key);
				}
			}

			deleteNode(map, deleteList);

			int tempCount = 0;
			while (map.size() > 0)
			{
				tempCount = map.size();
				for (Integer key : map.keySet())
				{
					ModuleModel value = map.get(key);

					for (ModuleTreeNode dad : treeList)
					{
						if (insertNode(dad, value))
						{
							deleteList.add(key);
							break;
						}
					}
				}

				deleteNode(map, deleteList);

				if (map.size() == tempCount)
				{
					for (Integer key1 : map.keySet())
					{
						ModuleModel value1 = map.get(key1);

						ModuleTreeNode dad1 = new ModuleTreeNode();
						dad1.setModuel(value1);
						////System.out.println("模块:" + value1.getModuleName()
						//		+ "(ID:" + value1.getModuleID() + ")"
						//		+ "的父模块不存在");
						treeList.add(dad1);
						deleteList.add(key1);

					}
					deleteNode(map, deleteList);

					break;
				}
			}

		}

		return treeList;
	}

	private boolean insertNode(ModuleTreeNode theDad, ModuleModel module)
	{
		boolean flag = false;

		List<ModuleTreeNode> sonList = theDad.getSonList();

		if (module.getModuleParent() == theDad.getModuel().getModuleID())
		{
			ModuleTreeNode son = new ModuleTreeNode();
			son.setModuel(module);

			if (sonList == null)
			{
				sonList = new LinkedList<ModuleTreeNode>();

				sonList.add(son);

				theDad.setSonList(sonList);
			} else
			{
				sonList.add(son);
			}
			flag = true;
		} else
		{
			if (sonList != null)
			{
				for (ModuleTreeNode node : sonList)
				{
					if (insertNode(node, module))
					{
						flag = true;
						break;
					}
				}
			}

		}

		return flag;
	}

	private void deleteNode(Map<Integer, ModuleModel> allModule,
			List<Integer> numList)
	{
		for (Integer key : numList)
		{
			if (allModule.get(key) != null)
			{
				allModule.remove(key);
			}
		}

		numList.clear();
	}

	public Map<Integer, ModuleModel> getModuleMap() throws SQLException
	{
		ModuleModel mm = null;
		Map<Integer, ModuleModel> map = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Module ORDER BY Module_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (map == null)
				{
					map = new HashMap<Integer, ModuleModel>();
				}

				mm = new ModuleModel();

				mm.setModuleID(rs.getInt(1));
				mm.setModuleName(rs.getString(2));
				mm.setModuleNum(rs.getString(3));
				mm.setModuleAddress(rs.getString(4));
				mm.setModuleMark(rs.getString(5));
				mm.setModuleParent(rs.getInt(6));
				mm.setModulePart1(rs.getString(7));
				mm.setModulePart2(rs.getString(8));
				mm.setModuleImage(rs.getString(9));
				mm.setModuleRemark(rs.getString(10));
				mm.setModuleOrder(rs.getInt(11));

				map.put(mm.getModuleID(), mm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	/**
	 * 查询模块 通过模块id查询模块信息
	 * 
	 * @param id
	 *            模块ID
	 * @return 模块实体类的实例
	 * @throws SQLException
	 */
	public ModuleModel query(int id) throws SQLException
	{
		ModuleModel mm = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Module WHERE Module_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
			{
				mm = new ModuleModel();
				mm.setModuleID(rs.getInt(1));
				mm.setModuleName(rs.getString(2));
				mm.setModuleNum(rs.getString(3));
				mm.setModuleAddress(rs.getString(4));
				mm.setModuleMark(rs.getString(5));
				mm.setModuleParent(rs.getInt(6));
				mm.setModulePart1(rs.getString(7));
				mm.setModulePart2(rs.getString(8));
				mm.setModuleImage(rs.getString(9));
				mm.setModuleRemark(rs.getString(10));
				mm.setModuleOrder(rs.getInt(11));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return mm;
	}

	/**
	 * 插入模块
	 * 
	 * @param u
	 *            模块实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean insert(ModuleModel u) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b = false;
		try
		{
			String sql = "INSERT INTO Module (Module_Name,Module_Num,Module_Address,Module_BIAOZHI,Module_Parent,Module_part1,Module_part2,Module_Image,Module_Remark,Module_Order) values('"
					+ u.getModuleName()
					+ "','"
					+ u.getModuleNum()
					+ "','"
					+ u.getModuleAddress()
					+ "','"
					+ u.getModuleMark()
					+ "',"
					+ u.getModuleParent()
					+ ",'"
					+ u.getModulePart1()
					+ "','"
					+ u.getModulePart2()
					+ "','"
					+ u.getModuleImage()
					+ "','"
					+ u.getModuleRemark() + "'," + u.getModuleOrder() + ")";

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
	 * 删除模块
	 * 
	 * @param id
	 *            模块ID
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b = false;
		try
		{
			String sql = "DELETE FROM Module WHERE Module_ID=" + id;

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
	 * 查询子模块
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> querySonModule(int id) throws SQLException
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "Select Module_ID from Module where Module_Parent=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			rs = ps.executeQuery();
			while (rs.next())
			{
				list.add(rs.getInt("Module_ID"));
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
	 * 修改模块
	 * 
	 * @param 模块实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean modify(ModuleModel u) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;		
		boolean b=false;
		try
		{
			String sql = "UPDATE Module SET Module_Name='" + u.getModuleName()
					+ "',Module_Num='" + u.getModuleNum() + "',Module_Address='"
					+ u.getModuleAddress() + "',Module_BIAOZHI='"
					+ u.getModuleMark() + "',Module_Parent=" + u.getModuleParent()
					+ ",Module_Part1='" + u.getModulePart1() + "',Module_Part2='"
					+ u.getModulePart2() + "',Module_Image='" + u.getModuleImage()
					+ "',Module_Remark='" + u.getModuleRemark() + "',Module_Order="
					+ u.getModuleOrder() + " WHERE Module_ID=" + u.getModuleID();
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
	 * 通过模块编号查询模块ID，主要作用于新建模块自动生成对应一级权限
	 * 
	 * @param moduleNum
	 * @return
	 * @throws SQLException
	 */
	public int queryModuleIDByNum(String moduleNum) throws SQLException
	{
		int moduleID = 0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "Select Module_ID from Module where Module_Num='"
					+ moduleNum + "'";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			rs = ps.executeQuery();
			if (rs.next())
			{
				moduleID = rs.getInt("Module_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return moduleID;
	}

	/**
	 * 列出所有子系统
	 * 
	 * @return 模块实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<ModuleModel> listAllParentModule() throws SQLException
	{
		ModuleModel mm = null;
		ArrayList<ModuleModel> lst = new ArrayList<ModuleModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from module where module_parent=0 and module_part2='1' order by module_order  desc ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			rs = ps.executeQuery();

			while (rs.next())
			{
				mm = new ModuleModel();

				mm.setModuleID(rs.getInt(1));
				mm.setModuleName(rs.getString(2));
				mm.setModuleNum(rs.getString(3));
				mm.setModuleAddress(rs.getString(4));
				mm.setModuleMark(rs.getString(5));
				mm.setModuleParent(rs.getInt(6));
				mm.setModulePart1(rs.getString(7));
				mm.setModulePart2(rs.getString(8));
				mm.setModuleImage(rs.getString(9));
				mm.setModuleRemark(rs.getString(10));
				mm.setModuleOrder(rs.getInt(11));

				lst.add(mm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	/**
	 * 判断是否有相同的模块编码
	 * 
	 * @param moduleNum
	 * @return
	 * @throws SQLException
	 */
	public boolean hasSameModuleNum(String moduleNum) throws SQLException
	{
		boolean b = false;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "Select Module_ID from Module where Module_Num='"
					+ moduleNum + "'";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			rs = ps.executeQuery();

			if (rs.next())
			{
				b = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return b;
	}

}
