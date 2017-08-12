package com.sf.energy.project.system.dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.sf.energy.project.system.model.Department;
import com.sf.energy.project.system.model.DepartmentTreeNode;

public class PartmentDaoTest
{
	DepartmentDao dd = new DepartmentDao();

	@Test
	public void updateGroup() throws SQLException
	{
		Department d = dd.query(65);

		d.setDepartmentRemark("哇啊撒双casc");

		dd.update(d);
	}

	@Test
	public void updateGroupIDS() throws SQLException
	{
		List<Department> list = dd.display();

		for (Department d : list)
		{
			dd.update(d);
		}
	}

	@Test
	public void buildTree() throws SQLException
	{
		List<DepartmentTreeNode> treeList = null;
		List<Integer> deleteList = null;
		Map<Integer, Department> allGroupMap = dd.getAllDepartMap(1);

		if (allGroupMap != null && allGroupMap.size() > 0)
		{
			deleteList = new LinkedList<Integer>();

			treeList = new LinkedList<DepartmentTreeNode>();

			for (Integer key : allGroupMap.keySet())
			{
				Department value = allGroupMap.get(key);

				if (value.getDepartmentParentID() == 0)
				{
					DepartmentTreeNode dad = new DepartmentTreeNode();
					dad.setDepartment(value);

					treeList.add(dad);
					deleteList.add(key);
				}
			}

			deleteNode(allGroupMap, deleteList);

			while (allGroupMap.size() > 0)
			{
				for (Integer key : allGroupMap.keySet())
				{
					Department value = allGroupMap.get(key);

					for (DepartmentTreeNode dad : treeList)
					{
						if (insertNode(dad, value))
						{
							System.out.println("insert: ID-"
									+ value.getDepartmentID() + " name:"
									+ value.getDepartmetName() + " parent:"
									+ value.getDepartmentParentID());
							deleteList.add(key);
							break;
						}
					}
				}
				deleteNode(allGroupMap, deleteList);
			}

		}

		System.out.println("size:" + allGroupMap.size());
	}

	private void deleteNode(Map<Integer, Department> allGroup,
			List<Integer> numList)
	{
		for (Integer key : numList)
		{
			if (allGroup.get(key) != null)
			{
				System.out.println("delete: ID-"
						+ allGroup.get(key).getDepartmentID() + " name:"
						+ allGroup.get(key).getDepartmetName() + " parent:"
						+ allGroup.get(key).getDepartmentParentID());
				allGroup.remove(key);
			}
		}

		numList.clear();
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
}
