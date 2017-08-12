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
import com.sf.energy.project.system.model.Department;
import com.sf.energy.project.system.model.DepartmentTreeNode;
import com.sf.energy.util.ConnDB;

/**
 * 对部门进行增删改查操作类
 * 
 * @author 李涵淼
 * @version 1.0
 * 
 * @since version 1.0
 * 
 */
public class DepartmentDao
{

	/**
	 * 定义list为ArrayList，对象为部门对象
	 * 
	 */
	public DepartmentDao()
	{

	}

	/**
	 * 传入sql语句，对数据库进行操作，查出所有部门对象，存入List中，并返回
	 * 
	 * 
	 * @return list 对象为部门的List
	 * @throws SQLException
	 */
	public List<Department> display(int userID) throws SQLException
	{
		Department depart = null;
		List<Department> list = new ArrayList<Department>();
		String sql = "SELECT * FROM partment where partment_ID in (select partment_ID from OprPartment_List where Users_ID="
				+ userID + ") order by partment_ID";

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
				depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));

				list.add(depart);
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
	 * 传入sql语句，对数据库进行操作，查出所有部门对象，存入List中，并返回
	 * 
	 * 
	 * @return list 对象为部门的List
	 * @throws SQLException
	 */
	public List<Department> display() throws SQLException
	{
		Department depart = null;
		List<Department> list = new ArrayList<Department>();
		String sql = "SELECT * FROM partment ";

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
				depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));

				list.add(depart);
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
	 * 对数据库进行操作，查出所有部门对象
	 * 
	 * @param id
	 * @return list 对象为部门的List
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	public Department query(int id) throws NumberFormatException, SQLException
	{
		Department depart = null;
		String sql = "Select * from Partment where partment_ID=" + id;
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
				depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return depart;
	}

	public List<Department> getDepartmentByParentID(int parentID)
			throws SQLException
	{
		List<Department> list = null;
		String sql = "select * from Partment where Partment_Parent = ? ";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, parentID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<Department>();

				Department depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));

				list.add(depart);
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
	 * 传入sql语句，对数据库进行操作，查出所有部门对象，存入List中，并返回
	 * 
	 * @param sql
	 *            查询部门信息的sql语句
	 * @return list 对象为部门的List
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	public List<Department> display(String sql) throws NumberFormatException,
			SQLException
	{
		Department depart = null;
		List<Department> list = new ArrayList<Department>();
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
				depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));

				list.add(depart);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	/**
	 * 通过传入一个部门对象，对数据库进行操作，将此部门信息插入数据库中
	 * 
	 * @param depar
	 *            部门对象
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean add(Department depar) throws SQLException
	{
		depar.setDepartmentIDs(buildIDS(depar));
		String sql = "insert into partment(Partment_Num,Partment_Name,Partment_Parent,Partment_Man,Partment_Phone,Partment_Remark,Partment_Order,Partment_Text,Partment_IDs)values('"

				+ depar.getDepartmentNum()
				+ "','"
				+ depar.getDepartmetName()
				+ "',"
				+ depar.getDepartmentParentID()
				+ ",'"
				+ depar.getDepartmentMan()
				+ "','"
				+ depar.getDepartmentPhone()
				+ "','"
				+ depar.getDepartmentRemark()
				+ "',"
				+ depar.getDepartmentOrder()
				+ ",'"
				+ depar.getDepartmentText()
				+ "','" + depar.getDepartmentIDs() + "')";

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
	 * 通过传入一个部门对象，对数据库进行操作，更新数据库中数据
	 * 
	 * @param depar
	 *            部门对象
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean update(Department depar) throws SQLException
	{
		depar.setDepartmentIDs(buildIDS(depar));
		String sql = "update partment set Partment_Num='"
				+ depar.getDepartmentNum() + "' ,Partment_Name='"
				+ depar.getDepartmetName() + "',Partment_Parent="
				+ depar.getDepartmentParentID() + ",Partment_Man='"
				+ depar.getDepartmentMan() + "',Partment_Phone='"
				+ depar.getDepartmentPhone() + "',Partment_Remark='"
				+ depar.getDepartmentRemark() + "',Partment_Order="
				+ depar.getDepartmentOrder() + ",Partment_Text='"
				+ depar.getDepartmentText() + "',Partment_IDs='"
				+ depar.getDepartmentIDs() + "' where Partment_ID="
				+ depar.getDepartmentID();
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
	 * 通过传入部门的id,对数据进行操作，删除此id对应部门
	 * 
	 * @param id
	 *            部门对象的ID
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		List<Department> list = getDepartmentByParentID(id);

		if (list != null && list.size() > 0)
		{
			for (Department dep : list)
			{
				delete(dep.getDepartmentID());
			}
		}

		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "delete from partment where Partment_ID=" + id;
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
	 * 通过部门id查询部门名称
	 * 
	 * @param id
	 *            部门id
	 * @return 部门名称
	 * @throws SQLException
	 */
	public String queryNameById(int id) throws SQLException
	{
		String partmentName = null;
		String sql = "SELECT PARTMENT_NAME FROM PARTMENT WHERE PARTMENT_ID="
				+ id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				partmentName = rs.getString("Partment_Name");
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return partmentName;
	}
	/**
	 * 通过部门id查询部门名称
	 * 
	 * @param id
	 *            部门id
	 * @return 部门名称
	 * @throws SQLException
	 */
	public int queryIdByNum(String num) throws SQLException
	{
		int id = 0;
		String sql = "SELECT PARTMENT_ID FROM PARTMENT WHERE PARTMENT_Num='"
				+ num+"'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				id = rs.getInt("PARTMENT_ID");
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return id;
	}

	public List<Department> getRootDepartment() throws SQLException
	{
		List<Department> list = null;
		String sql = "select * from Partment where Partment_ID in "
				+ "(select Partment_Parent from Partment group by Partment_Parent)";

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
				if (list == null)
					list = new LinkedList<Department>();

				Department depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));

				list.add(depart);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<Department> getRootLeafDepartment() throws SQLException
	{
		List<Department> list = null;
		String sql = "select * from Partment where Partment_Parent = 0 "
				+ "and Partment_ID not in "
				+ "(select Partment_Parent from Partment group by Partment_Parent)";

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
				if (list == null)
					list = new LinkedList<Department>();

				Department depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));

				list.add(depart);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public List<Department> getAllSonPartment(int id) throws SQLException
	{
		List<Department> list = null;
		String sql = "select * from Partment where Partment_Parent = " + id;

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
				if (list == null)
					list = new LinkedList<Department>();

				Department depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));

				list.add(depart);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public Map<String, Department> getAllDepartMapByName() throws SQLException
	{
		Map<String, Department> map = null;
		String sql = "SELECT * FROM partment order by partment_ID";

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
				if (map == null)
					map = new HashMap<String, Department>();

				Department depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));

				map.put(depart.getDepartmetName(), depart);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	public Map<Integer, Department> getAllDepartMap(int userID)
			throws SQLException
	{
		Map<Integer, Department> map = null;
		String sql = "SELECT * FROM partment where partment_ID in (select partment_ID from OprPartment_List where Users_ID="
				+ userID + ") order by partment_ID";

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
				if (map == null)
					map = new HashMap<Integer, Department>();
				Department depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));

				map.put(depart.getDepartmentID(), depart);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	public List<DepartmentTreeNode> getDepartmentTree(int userID)
			throws SQLException
	{
		List<DepartmentTreeNode> treeList = null;
		List<Integer> deleteList = null;
		// 将所有部门数据用映射的形式返回
		Map<Integer, Department> allGroupMap = getAllDepartMap(userID);

		if (allGroupMap != null && allGroupMap.size() > 0)
		{
			deleteList = new LinkedList<Integer>();

			treeList = new LinkedList<DepartmentTreeNode>();

			for (Integer key : allGroupMap.keySet())
			{
				Department value = allGroupMap.get(key);

				// 按照甲方要求，不显示部门树的顶级节点
				if (value.getDepartmentParentID() == 0)
				{
					deleteList.add(key);
				}

				// 将部门树顶级节点的子节点选出来作为属性的一级目录节点
				if (value.getDepartmentParentID() == 1)
				{
					DepartmentTreeNode dad = new DepartmentTreeNode();
					dad.setDepartment(value);

					treeList.add(dad);
					deleteList.add(key);
				}
			}

			// 将选出来的节点从原始数据映射中去除
			deleteNode(allGroupMap, deleteList);

			int tempCount = 0;

			// 反复循环遍历，将剩下的节点分别加入到对应的父节点下面。切记不可存在父节点不存在的野节点，否则此方法将陷入死循环
			while (allGroupMap.size() > 0)
			{
				tempCount = allGroupMap.size();
				for (Integer key : allGroupMap.keySet())
				{
					Department value = allGroupMap.get(key);

					for (DepartmentTreeNode dad : treeList)
					{
						if (insertNode(dad, value))
						{
							deleteList.add(key);
							break;
						}
					}
				}
				deleteNode(allGroupMap, deleteList);
				if (tempCount == allGroupMap.size() && tempCount > 0)
				{
					for (Integer key1 : allGroupMap.keySet())
					{
						Department value1 = allGroupMap.get(key1);
						// //System.out.println("部门:" +
						// value1.getDepartmetName()
						// + "(ID:" + value1.getDepartmentID() + ")"
						// + "的父部门不存在");
						DepartmentTreeNode dad1 = new DepartmentTreeNode();
						dad1.setDepartment(value1);

						treeList.add(dad1);
						deleteList.add(key1);
					}
					// 将选出来的节点从原始数据映射中去除
					deleteNode(allGroupMap, deleteList);
					break;
				}
			}

		}
		return treeList;
	}

	public boolean hasAddRepeat(Department group) throws SQLException
	{
		boolean flag = false;
		String sql = "select Partment_ID from Partment where "
				+ "(Partment_Num = ? or Partment_Name = ?) and RowNum = 1";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;


		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, group.getDepartmentNum());
			ps.setString(2, group.getDepartmetName());

			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return flag;
	}

	public boolean hasUpdateRepeat(Department group) throws SQLException
	{
		boolean flag = false;
		String sql = "select Partment_ID from Partment where "
				+ "(Partment_Num = ? or Partment_Name = ?) and Partment_ID !=? and RowNum = 1";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, group.getDepartmentNum());
			ps.setString(2, group.getDepartmetName());
			ps.setInt(3, group.getDepartmentID());

			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return flag;
	}

	private boolean insertNode(DepartmentTreeNode theDad, Department group)
	{
		boolean flag = false;

		List<DepartmentTreeNode> sonList = theDad.getSonList();

		if (group.getDepartmentParentID() == theDad.getDepartment()
				.getDepartmentID())
		{
			DepartmentTreeNode son = new DepartmentTreeNode();
			son.setDepartment(group);

			if (sonList == null)
			{
				sonList = new LinkedList<DepartmentTreeNode>();

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
				for (DepartmentTreeNode node : sonList)
				{
					if (insertNode(node, group))
					{
						flag = true;
						break;
					}
				}
			}

		}

		return flag;
	}

	public boolean hasAmmeter(int theGroupID) throws SQLException
	{
		boolean flag = false;

		String sql = "select Ammeter_ID,Architecture_ID from Ammeter where Partment_ID = ? and RowNum = 1";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, theGroupID);

			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return flag;
	}

	public boolean hasWaterMeter(int theGroupID) throws SQLException
	{
		boolean flag = false;

		String sql = "select WaterMeter_ID,Architecture_ID from WaterMeter where Partment = ? and RowNum = 1";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, theGroupID);

			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return flag;
	}

	private void deleteNode(Map<Integer, Department> allGroup,
			List<Integer> numList)
	{
		for (Integer key : numList)
		{
			if (allGroup.get(key) != null)
			{
				allGroup.remove(key);
			}
		}

		numList.clear();
	}

	private String buildIDS(Department group) throws NumberFormatException,
			SQLException
	{
		String groupIDS = "";

		if (group.getDepartmentID() == 1)
			groupIDS = group.getDepartmentID() + "";
		else
		{
			Department parent = query(group.getDepartmentParentID());
			if (parent != null)
			{
				groupIDS = buildIDS(parent) + "," + group.getDepartmentID();
			}
		}

		return groupIDS;
	}

}
