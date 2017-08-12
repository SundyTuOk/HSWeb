/**
 * 2014-4-22
 */
package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.right.dao.UsersPartmentDao;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.project.system.model.DepartmentTreeNode;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

/**
 * @author WangZhao 2014-4-22
 * 
 * 
 */
public class PartmentManage extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        try
        {
            findMethod(request, response);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        try
        {
            findMethod(request, response);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void findMethod(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
        String method = request.getParameter("method");

        if ("getAllPartment".equalsIgnoreCase(method))
            getAllPartment(request, response);

        if ("getPartmentTree".equalsIgnoreCase(method))
            getPartmentTree(request, response);

        if ("getAllPartmentTree".equalsIgnoreCase(method))
            getAllPartmentTree(request, response);

        if ("deletePartment".equalsIgnoreCase(method))
            deletePartment(request, response);

        if ("addPartment".equalsIgnoreCase(method))
            addPartment(request, response);

        if ("updatePartment".equals(method))
            updatePartment(request, response);

        if ("getAllSonPartment".equals(method))
            getAllSonPartment(request, response);
        
        if ("getAllUserPartmentTree".equalsIgnoreCase(method))
			getAllUserPartmentTree(request, response); 

    }

    private void getAllPartmentTree(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	DepartmentDao dd = new DepartmentDao();
        HttpSession session = request.getSession();
        int userID = 0;
        if (session.getAttribute("userId") != null)
        {
            userID = Integer
                    .parseInt(session.getAttribute("userId").toString());
        }

        List<DepartmentTreeNode> treeList = dd.getDepartmentTree(userID);

        if (treeList != null && treeList.size() > 0)
        {
            JSONObject result = new JSONObject();

            result.put("treeNode", buildTreeData(treeList, true));
            result.put("listNode", buildListData(userID));
            result.put("selectData", buildParentSelect(treeList, ""));

            PrintWriter out = response.getWriter();
        //    System.out.println(result.toString());
            out.println(result.toString());
            out.flush();
            out.close();
        }

    }

    private void getPartmentTree(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	DepartmentDao dd = new DepartmentDao();
        HttpSession session = request.getSession();
        int userID = 0;
        if (session.getAttribute("userId") != null)
        {
            userID = Integer
                    .parseInt(session.getAttribute("userId").toString());
        }
    //    System.out.println("userID:"+userID);

        List<DepartmentTreeNode> treeList = dd.getDepartmentTree(userID);

        if (treeList != null && treeList.size() > 0)
        {
            JSONObject result = new JSONObject();

            result.put("treeNode", buildTreeData(treeList, true));
            result.put("listNode", buildListData(userID));
            result.put("selectData", buildParentSelect(treeList, ""));

            PrintWriter out = response.getWriter();
     //     System.out.println(result.toString());
            out.println(result.toString());
            out.flush();
            out.close();
        }

    }

    private String buildParentSelect(List<DepartmentTreeNode> list,
            String spliter)
    {
        String select = "";

        for (DepartmentTreeNode node : list)
        {
            select += "<option value='"
                    + node.getDepartment().getDepartmentID() + "'>" + spliter
                    + node.getDepartment().getDepartmetName() + "</option>";
            if (node.getSonList() != null)
                select += buildParentSelect(node.getSonList(), spliter
                        + "&nbsp;∟");
        }

        return select;
    }

    private JSONArray buildListData(int userID) throws SQLException
    {

    	DepartmentDao dd = new DepartmentDao();
        JSONArray groupListData = null;

        List<Department> list = dd.display(userID);

        if (list != null && list.size() > 0)
        {
            groupListData = new JSONArray();

            for (Department n : list)
            {
                JSONObject node = new JSONObject();

                node.put("PARTMENT_ID", n.getDepartmentID());
                node.put("PARTMENT_NUM", n.getDepartmentNum());
                node.put("PARTMENT_NAME", n.getDepartmetName());
                node.put("PARTMENT_PARENT", n.getDepartmentParentID());
                node.put("PARTMENT_MAN", n.getDepartmentMan());
                node.put("PARTMENT_PHONE", n.getDepartmentPhone());
                node.put("PARTMENT_REMARK", n.getDepartmentRemark());
                node.put("PARTMENT_ORDER", n.getDepartmentOrder());
                node.put("PARTMENT_TEXT", n.getDepartmentText());
                node.put("PARTMENT_IDS", n.getDepartmentIDs());

                groupListData.add(node);
            }
        }

        return groupListData;
    }

    private JSONObject buildTreeData(List<DepartmentTreeNode> theTreeList,
            boolean isRoot)
    {
        JSONObject groupTreeData = null;
        if (theTreeList != null && theTreeList.size() > 0)
        {
            groupTreeData = new JSONObject();

            for (DepartmentTreeNode t : theTreeList)
            {
                int group_ID = t.getDepartment().getDepartmentID();
                String group_Name = t.getDepartment().getDepartmetName();

                JSONObject jo = new JSONObject();

                jo.put("name", group_Name);
                jo.put("GroupID", group_ID);

                if (t.getSonList() != null)
                {
                    jo.put("type", "folder");

                    jo.put("additionalParameters",
                            buildTreeData(t.getSonList(), false));
                }
                else
                {
                    jo.put("type", "item");
                }

                groupTreeData.put(group_ID, jo);
            }

            if (!isRoot)
            {
                JSONObject childrenNode = new JSONObject();
                childrenNode.put("children", groupTreeData);

                return childrenNode;
            }
        }

        return groupTreeData;
    }

    private void updatePartment(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	DepartmentDao dd = new DepartmentDao();
        Department partment = new Department();

        if (request.getParameter("groupID") != null)
            partment.setDepartmentID(Integer.parseInt(request
                    .getParameter("groupID")));

        if (request.getParameter("groupParentID") != null)
            partment.setDepartmentParentID(Integer.parseInt(request
                    .getParameter("groupParentID")));

        if (request.getParameter("groupName") != null)
            partment.setDepartmetName(request.getParameter("groupName"));

        if (request.getParameter("groupNum") != null)
            partment.setDepartmentNum(request.getParameter("groupNum"));

        if (request.getParameter("groupMan") != null)
            partment.setDepartmentMan(request.getParameter("groupMan"));

        if (request.getParameter("groupPhone") != null)
            partment.setDepartmentPhone(request.getParameter("groupPhone"));

        if (request.getParameter("groupRemark") != null)
            partment.setDepartmentRemark(request.getParameter("groupRemark"));

        String resultInfo = null;
        if (dd.hasUpdateRepeat(partment))
        {
            resultInfo = "不允许添加重复编码和名称";
        }
        else
            if (dd.update(partment)){
                resultInfo = "success";
                OperationLogInterface log = new OperationLogImplement();
                HttpSession session = request.getSession();
        		String userLoginName = (String) session.getAttribute("userName");
        		log.writeLog(userLoginName, "部门管理", "编辑部门！"+partment.getDepartmetName());
            }
            else
                resultInfo = "fail";

        PrintWriter out = response.getWriter();
        out.println(resultInfo);
        out.flush();
        out.close();
    }

    private void addPartment(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
    	DepartmentDao dd = new DepartmentDao();
        Department Partment = new Department();

        if (request.getParameter("groupParentID") != null)
            Partment.setDepartmentParentID(Integer.parseInt(request
                    .getParameter("groupParentID")));

        if (request.getParameter("groupName") != null)
            Partment.setDepartmetName(request.getParameter("groupName"));

        if (request.getParameter("groupNum") != null)
            Partment.setDepartmentNum(request.getParameter("groupNum"));

        if (request.getParameter("groupMan") != null)
            Partment.setDepartmentMan(request.getParameter("groupMan"));

        if (request.getParameter("groupPhone") != null)
            Partment.setDepartmentPhone(request.getParameter("groupPhone"));

        if (request.getParameter("groupRemark") != null)
            Partment.setDepartmentRemark(request.getParameter("groupRemark"));

        String resultInfo = null;
        if (dd.hasAddRepeat(Partment))
        {
            resultInfo = "不允许添加重复编码和名称";
        }
        else
            if (dd.add(Partment))
            {
                resultInfo = "success";
                // 新增时新曾相应权限
                HttpSession session = request.getSession();
                int userID = 0;
                if (session.getAttribute("userId") != null)
                {
                    userID = Integer.parseInt(session.getAttribute("userId")
                            .toString());
                }
                UsersPartmentDao upd = new UsersPartmentDao();
                upd.rightAfterInsert(Partment.getDepartmetName(), userID);
                OperationLogInterface log = new OperationLogImplement();
        		String userLoginName = (String) session.getAttribute("userName");
        		log.writeLog(userLoginName, "部门管理", "新增部门！"+Partment.getDepartmetName());
            }

            else
                resultInfo = "fail";

        PrintWriter out = response.getWriter();
        out.println(resultInfo);
        out.flush();
        out.close();
    }

    private void deletePartment(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
    	DepartmentDao dd = new DepartmentDao();
        Integer PartmentID = Integer.parseInt(request.getParameter("groupID"));

        String resultInfo = null;

        if (dd.hasAmmeter(PartmentID) || dd.hasWaterMeter(PartmentID))
        {
            resultInfo = "该部门下挂有电表或水表，删除该部门前，请先解除设备附属关系";
        }
        else
            if (dd.delete(PartmentID))
            {
                resultInfo = "success";
                UsersPartmentDao upd = new UsersPartmentDao();
                upd.rightAfterDelete(PartmentID);
                OperationLogInterface log = new OperationLogImplement();
                HttpSession session = request.getSession();
        		String userLoginName = (String) session.getAttribute("userName");
        		log.writeLog(userLoginName, "部门管理", "删除部门！"+dd.queryNameById(PartmentID));
            }
            else
                resultInfo = "fail";

        PrintWriter out = response.getWriter();
        out.println(resultInfo);
        out.flush();
        out.close();
    }

    private void getAllPartment(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
    	DepartmentDao dd = new DepartmentDao();
        HttpSession session = request.getSession();
        int userID = 0;
        if (session.getAttribute("userId") != null)
        {
            userID = Integer
                    .parseInt(session.getAttribute("userId").toString());
        }
        List<Department> list = dd.display(userID);

        JSONArray json = new JSONArray();
        for (Department n : list)
        {
            JSONObject jo = new JSONObject();
            jo.put("PARTMENT_ID", n.getDepartmentID());
            jo.put("PARTMENT_NUM", n.getDepartmentNum());
            jo.put("PARTMENT_NAME", n.getDepartmetName());
            jo.put("PARTMENT_PARENT", n.getDepartmentParentID());
            jo.put("PARTMENT_MAN", n.getDepartmentMan());
            jo.put("PARTMENT_PHONE", n.getDepartmentPhone());
            jo.put("PARTMENT_REMARK", n.getDepartmentRemark());
            jo.put("PARTMENT_ORDER", n.getDepartmentOrder());
            jo.put("PARTMENT_TEXT", n.getDepartmentText());
            jo.put("PARTMENT_IDS", n.getDepartmentIDs());
            json.add(jo);
        }

        PrintWriter out = response.getWriter();
//        System.out.println(json.toString());
        out.println(json.toString());
        out.flush();
        out.close();
    }

    private void getAllSonPartment(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
    	DepartmentDao dd = new DepartmentDao();
        int id = 0;
        if (request.getParameter("partmentID") != null)
        {
            id = Integer.parseInt(request.getParameter("partmentID"));
        }

        List<Department> list = dd.getAllSonPartment(id);

        JSONArray json = new JSONArray();
        for (Department n : list)
        {
            JSONObject jo = new JSONObject();
            jo.put("PARTMENT_ID", n.getDepartmentID());
            jo.put("PARTMENT_NUM", n.getDepartmentNum());
            jo.put("PARTMENT_NAME", n.getDepartmetName());
            jo.put("PARTMENT_PARENT", n.getDepartmentParentID());
            jo.put("PARTMENT_MAN", n.getDepartmentMan());
            jo.put("PARTMENT_PHONE", n.getDepartmentPhone());
            jo.put("PARTMENT_REMARK", n.getDepartmentRemark());
            jo.put("PARTMENT_ORDER", n.getDepartmentOrder());
            jo.put("PARTMENT_TEXT", n.getDepartmentText());
            jo.put("PARTMENT_IDS", n.getDepartmentIDs());
            json.add(jo);
        }

        PrintWriter out = response.getWriter();
        out.println(json.toString());
        out.flush();
        out.close();
    }

    private void getAllUserPartmentTree(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		DepartmentDao dd = new DepartmentDao();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null) {
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}
		// System.out.println("userID:"+userID);

		List<DepartmentTreeNode> treeList = dd.getDepartmentTree(userID);
		List<Department> dept = dd.getDepartmentByParentID(0);
		String fistOption = "<option value="+dept.get(0).getDepartmentID()+">"+dept.get(0).getDepartmetName()+"</option>";
		if (treeList != null && treeList.size() > 0) {
			JSONObject result = new JSONObject();
			result.put("treeNode", buildTreeData(treeList, true));
			result.put("listNode", buildListData(userID));
			result.put("selectData", fistOption+buildParentSelect(treeList, "&nbsp;∟"));
			PrintWriter out = response.getWriter();
			//System.out.println("user:"+result.toString());
			out.println(result.toString());
			out.flush();
			out.close();
		}
	}
}
