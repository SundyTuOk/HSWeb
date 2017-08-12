/**
 * 2014-4-25
 */
package com.sf.energy.project.right.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.right.dao.RolesDao;
import com.sf.energy.project.right.dao.RolesPowerDao;
import com.sf.energy.project.right.model.RolesModel;
import com.sf.energy.project.right.model.RolesPowerModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

/**
 * @author WangZhao 2014-4-25
 * 
 * 
 */
public class RoleManage extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		RolesDao rd = new RolesDao();
		String method = request.getParameter("method");
		//System.out.println("method:" + method);

		if ("getAllRoles".equals(method))
			getAllRole(request, response);

		if ("deleteRole".equals(method))
			deleteRole(request, response);

		if ("addRole".equals(method))
			addRole(request, response);

		if ("updateRole".equals(method))
			updateRole(request, response);

		if ("deleteSomeRole".equals(method))
			deleteSomeRole(request, response);

		if("getAllPowers".equals(method))
			getAllPowers(request, response);
		
		if("getAllRoleInUsers".equals(method))
			getAllRoleInUsers(request, response);
	}

	private void deleteSomeRole(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		RolesDao rd = new RolesDao();
		String idList = request.getParameter("IDList");
		String[] list = idList.split(" ");
		Integer ID = 0;
		String resultInfo = "success";
		for (String id : list)
		{
			ID = Integer.parseInt(id);
			if (!rd.delete(ID))
				resultInfo = "fail";
			//删除角色对应的权限
			RolesPowerDao rpd=new RolesPowerDao();
			rpd.delete(ID);
		}
		
		OperationLogInterface log = new OperationLogImplement();
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		log.writeLog(userLoginName, "角色管理", "删除角色！");
		
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void updateRole(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		RolesDao rd = new RolesDao();
		RolesModel role = new RolesModel();

		role.setRolesID(Integer.parseInt(request.getParameter("Role_ID")));
		role.setRolesName(request.getParameter("Role_Name"));
		role.setRolesEnable(Integer.parseInt(request
				.getParameter("Role_Enable")));
		role.setRolesRemark(request.getParameter("Role_Remark"));
		boolean a=false;
		boolean b=false;
		a=rd.modify(role);
		
		
		
		RolesPowerModel rpm = new RolesPowerModel();
		RolesPowerDao rpd = new RolesPowerDao();
		//先删除表Roles_Power表中的数据，再新增
		rpd.delete(Integer.parseInt(request.getParameter("Role_ID")));
		
		
		rpm.setRolesID(Integer.parseInt(request.getParameter("Role_ID")));
		// 获取checkbox选中的powerID
		String powers=request.getParameter("Role_Power");
		powers = java.net.URLDecoder.decode(powers, "utf-8");
		//System.out.println("Powers After ToJSON:"+powers);
		
		if(!"(})".equals(powers))
		{	
			
			List<String> powersList = parseJsonToArray(powers);
		
			for (int i = 0; i < powersList.size(); i++)
			{
				if(powersList.get(i)!=null)
				{
					rpm.setPowerID(Integer.parseInt(powersList.get(i)));
					
					try
					{
						b=rpd.insert(rpm);
					} catch (SQLException e)
					{
						e.printStackTrace();
					}
				
				}
				else {
					return;
				}
			}
		}
		else {
			return;
		}
		
		String resultInfo  = "failed";
		if(a==true&&b==true)
		{
			resultInfo="success";
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			log.writeLog(userLoginName, "角色管理", "编辑角色！"+role.getRolesName());
		}
		
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addRole(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		RolesDao rd = new RolesDao();
		RolesModel role = new RolesModel();
		String roleName=request.getParameter("Role_Name");
		boolean hasSame=false;
		if(rd.hasSameRoleName(roleName))//有相同的角色名
		{
			hasSame=true;
			PrintWriter out = response.getWriter();
			out.println(hasSame);
			out.flush();
			out.close();
		}
		else 
		{
			role.setRolesName(roleName);
			role.setRolesEnable(Integer.parseInt(request.getParameter("Role_Enable")));
			role.setRolesRemark(request.getParameter("Role_Remark"));

			////System.out.println(request.getParameter("Role_Name"));
			boolean a=false;
			boolean b=false;
			a=rd.insert(role);
			
			// 首先查出新增角色的Roles_ID
			RolesModel rmOnlyId = null;
			try
			{
				rmOnlyId = rd.queryByName(roleName);
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}

			RolesPowerModel rpm = new RolesPowerModel();
			RolesPowerDao rpd = new RolesPowerDao();

			rpm.setRolesID(rmOnlyId.getRolesID());

			// 获取checkbox选中的powerID
			String powers=request.getParameter("Role_Power");
			powers = java.net.URLDecoder.decode(powers, "utf-8");
			//System.out.println("Powers After ToJSON:"+powers);
			
			if(!"(})".equals(powers))//空权限编号
			{	
				
				List<String> powersList = parseJsonToArray(powers);
			
				for (int i = 0; i < powersList.size(); i++)
				{
					if(powersList.get(i)!=null)
					{
						rpm.setPowerID(Integer.parseInt(powersList.get(i)));
						
						try
						{
							b=rpd.insert(rpm);
						} catch (SQLException e)
						{
							e.printStackTrace();
						}
					
					}
					else 
					{
						return;
					}
				}
			}
			else 
			{
				return;
			}
			
			String resultInfo  = "failed";
			if(a==true&&b==true)
			{
				resultInfo="success";
				
				OperationLogInterface log = new OperationLogImplement();
				HttpSession session = request.getSession();
				String userLoginName = (String) session.getAttribute("userName");
				log.writeLog(userLoginName, "角色管理", "新增角色！"+role.getRolesName());
			}
			PrintWriter out = response.getWriter();
			out.println(resultInfo);
			out.flush();
			out.close();
		}
		
		
	}

	private void deleteRole(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		RolesDao rd = new RolesDao();
		Integer RoleID = Integer.parseInt(request.getParameter("RoleID"));

		String resultInfo = null;

		rd.delete(RoleID);
		
		//删除角色对应的权限
		RolesPowerDao rpd=new RolesPowerDao();
		if(rpd.delete(RoleID))
		{
			resultInfo="success";
		}
		else {
			resultInfo="failed";
		}

		OperationLogInterface log = new OperationLogImplement();
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		log.writeLog(userLoginName, "角色管理", "删除角色！"+rd.query(RoleID).getRolesName());
		
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getAllRole(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		RolesDao rd = new RolesDao();
		Integer thePageCount = Integer.parseInt(request.getParameter("RolePageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("RolePageIndex"));
		
		List<RolesModel> list = rd.listRoles(thePageCount,thePageIndex);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("totalCount", rd.getTotal());
		json.add(jo);
		for (int i = list.size() - 1; i >= 0; i--)
		{
			RolesModel n = list.get(i);
			 jo = new JSONObject();
			jo.put("Role_ID", n.getRolesID());
			jo.put("Role_Name", n.getRolesName());
			jo.put("Role_Enable", n.getRolesEnable());
			jo.put("Role_Remark", n.getRolesRemark());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	private void getAllRoleInUsers(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		RolesDao rd = new RolesDao();
		List<RolesModel> list = rd.listRoles();

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		for (int i = list.size() - 1; i >= 0; i--)
		{
			RolesModel n = list.get(i);
			 jo = new JSONObject();
			jo.put("Role_ID", n.getRolesID());
			jo.put("Role_Name", n.getRolesName());
			jo.put("Role_Enable", n.getRolesEnable());
			jo.put("Role_Remark", n.getRolesRemark());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	private void getAllPowers(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArrayList<Integer> lst=new ArrayList<Integer>();
		RolesPowerDao rpd=new RolesPowerDao();
		int roleID=1;
		if(request.getParameter("updateRoleID")!=null && !"".equals(request.getParameter("updateRoleID")) ) 
		 {
		 	roleID=Integer.parseInt(request.getParameter("updateRoleID"));
		 }
		
		 ////System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx updateRoleID in js is :"+roleID);
		
		 lst=rpd.listRolesPower(roleID);
		 
		 String powersString = "";
		 for(int i=0;i<lst.size();i++)
		 {
			 powersString+=lst.get(i).toString()+" ";
		 }
	 
		 //要去掉最后一个空格，API[忽略前导空白和尾部空白]
		 //System.out.println(powersString.trim());
		 
		
		PrintWriter out = response.getWriter();
		out.println(powersString.trim());
		out.flush();
		out.close();
	}

	
	
	private String readJSONString(HttpServletRequest request)
	{
		StringBuffer json = new StringBuffer();
		String line = null;
		try
		{
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
			{
				json.append(line);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * JSON转化成List
	 * @param data
	 * @return
	 */
	public static List<String> parseJsonToArray(String data) {
        List<String> list = new ArrayList<String>();
        if (null == data || data.length() == 0) {
            return null;
        }
        if (data.length() < 4) {
            return null;
        }
        String temp = data.substring(2, data.length() - 2);
        temp = temp.replaceAll("\\'", "");
        String splitResult[] = temp.split(",");
        for (int i = 0; i < splitResult.length; i++) {
            list.add(splitResult[i].substring(splitResult[i].indexOf(":") + 1));
        }
        return list;
    }
	
	
}
