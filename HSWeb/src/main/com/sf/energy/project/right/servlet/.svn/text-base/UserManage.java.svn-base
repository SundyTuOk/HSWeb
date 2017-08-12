/**
 * 2014-4-25
 */
package com.sf.energy.project.right.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.right.dao.UsersArchitectureDao;
import com.sf.energy.project.right.dao.UsersAreaDao;
import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.project.right.dao.UsersPartmentDao;
import com.sf.energy.project.right.dao.UsersRolesDao;
import com.sf.energy.project.right.model.UsersArchitectureModel;
import com.sf.energy.project.right.model.UsersAreaModel;
import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.project.right.model.UsersPartmentModel;
import com.sf.energy.project.right.model.UsersRolesModel;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.util.CreateLog;
import com.sf.energy.util.IPAddressInfo;
import com.sf.energy.util.MD5Encryption;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

/**
 * @author WangZhao 2014-4-25
 * 
 * 
 */
public class UserManage extends HttpServlet
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
		String method = request.getParameter("method");
		//System.out.println("method:" + method);

		if ("getAllUsers".equals(method))
			getAllUser(request, response);

		if ("deleteUser".equals(method))
			deleteUser(request, response);

		if ("addUser".equals(method))
			addUser(request, response);

		if ("updateUser".equals(method))
			updateUser(request, response);

		if ("deleteSomeUser".equals(method))
			deleteSomeUser(request, response);

		if("updateArchRightBtn".equals(method))
			updateArchRightBtn(request, response);
		
		if("updatePartmentRightBtn".equals(method))
			updatePartmentRightBtn(request, response);
		if("updateButtonRightBtn".equals(method))
			updateButtonRightBtn(request, response);
		if("getButtonRightBtn".equals(method))
			getButtonRightBtn(request, response);
		if("getButtonRightBtn2".equals(method))
			getButtonRightBtn2(request, response);
		
		if("getUserRightString".equals(method))
			getUserRightString(request, response);
		
		if("clearUsersAreaRight".equals(method))
			clearUsersAreaRight(request, response);
		
		if("clearUsersArchRight".equals(method))
			clearUsersArchRight(request, response);
		
		if("clearUsersPartRight".equals(method))
			clearUsersPartRight(request, response);
		if("clearUsersButtonRight".equals(method))
			clearUsersButtonRight(request, response);
		
		if("getUserTree".equals(method))
			getUserTree(request, response);
		if("getUserID".equals(method))
			getUserID(request, response);
		
		if("changePass".equals(method))
			changePass(request, response);
		
		if("getUserBaojingFL".equals(method))
			getUserBaojingFL(request, response);
	}

	/**
	 * 获取用户报警分类信息
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void getUserBaojingFL(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		int userID = 0;
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}
		String info = new UsersDao().queryUserBaojingFL(userID);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
		
	}

	private void getUserTree(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		
		UsersDao ud = new UsersDao();
		UsersRolesDao urd = new UsersRolesDao();
		
		
		List<UsersModel> list = ud.getAllUsers();

		JSONObject json = new JSONObject();
		JSONObject jo =null;
		for (UsersModel model : list)
		{
			jo=new JSONObject();
			int userID=model.getUsersID();
			String userName=model.getUsersName();
			
			jo.put("type", "item");
			jo.put("name", userName);
			jo.put("userID", userID);
			json.put(userID, jo);
			
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 操作员的建筑和区域权限
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 */
	private void updateArchRightBtn(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		UsersArchitectureDao uArchDao=new UsersArchitectureDao();
		AreaDao ad=new AreaDao();
		UsersAreaDao uAreaDao=new UsersAreaDao();
		UsersAreaModel uAreaModel=null;
		UsersArchitectureModel uArchModel=null;
		
		String archList = request.getParameter("archList");//建筑的ID组string
		String areaList=request.getParameter("areaList");
		int usersID=Integer.parseInt(request.getParameter("userID"));
		String[] archArray = archList.split(" ");
		String[] areaArray = areaList.split(" ");
		int archID = 0;
		int areaID=0;
		String resultInfo = "success";
		
		//插入之前先删除之前的
		uAreaDao.delete(usersID);
		uArchDao.delete(usersID);
		for(String AREAID:areaArray)
		{
			//插入区域权限表
			areaID=Integer.parseInt(AREAID);
			
			uAreaModel=new UsersAreaModel();
			uAreaModel.setUsersID(usersID);
			uAreaModel.setAreaID(areaID);
			if(!uAreaDao.insert(uAreaModel))
			{
				resultInfo="failed";
			}
		}
		
		for (String ARCHID : archArray)
		{
			archID = Integer.parseInt(ARCHID);//建筑ID

			//插入建筑权限表
			uArchModel=new UsersArchitectureModel();
			uArchModel.setUsersID(usersID);
			uArchModel.setAreaID(areaID);
			uArchModel.setArchID(archID);
			if (!uArchDao.insert(uArchModel))
			{
				resultInfo="failed";
			}				
		}
			
	
		
		
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
		
	}
	
	
	/**
	 * 操作员的部门权限
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 */
	private void updatePartmentRightBtn(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		UsersPartmentDao upd=new UsersPartmentDao();
		UsersPartmentModel upm=new UsersPartmentModel();
		int usersID=Integer.parseInt(request.getParameter("userID"));
		
		String idList = request.getParameter("partList");//建筑的ID组string
		String[] list = idList.split(" ");
			
		int partID=0;
		String resultInfo = "success";
		
		//插入之前先删除之前的
		upd.delete(usersID);
		
		for (String PARTID : list)
		{
			partID = Integer.parseInt(PARTID);//建筑ID
				
			//插入建筑权限表
			upm.setUsersID(usersID);
			upm.setPartmentID(partID);
			if (!upd.insert(upm))
			{
				resultInfo="failed";
			}				
		}
	
		
		
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	
	
	private void deleteSomeUser(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		UsersDao ud = new UsersDao();
		UsersRolesDao urd = new UsersRolesDao();
		String idList = request.getParameter("IDList");
		String[] list = idList.split(" ");
		Integer ID = 0;
		String resultInfo = "success";
		for (String id : list)
		{
			ID = Integer.parseInt(id);
			if (!ud.deleteAll(ID))//删除所有与users相关联的记录
				resultInfo = "fail";

		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void updateUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		UsersModel user = new UsersModel();
		UsersDao ud = new UsersDao();
		UsersRolesDao urd = new UsersRolesDao();
		
		user.setUsersID(Integer.parseInt(request.getParameter("User_ID")));
		user.setUsersName(request.getParameter("User_Name"));
		user.setUsersLoginName(request.getParameter("User_LoginName"));
		user.setUsersPassword(request.getParameter("User_PassWord"));
		user.setUsersNum(request.getParameter("User_Num"));
		user.setUsersDepartment(Integer.parseInt(request
				.getParameter("User_Department")));
		user.setUsersStateID(Integer.parseInt(request
				.getParameter("User_StateID")));
		user.setUsersGender((request.getParameter("User_Gender")));
		user.setUsersPhone(request.getParameter("User_Phone"));
		user.setUsersPhone1(request.getParameter("User_Phone1"));
		user.setUsersPhone2(request.getParameter("User_Phone2"));
		user.setUsersHomeAddress(request.getParameter("User_HomeAddress"));
		user.setIsAlarm(Integer.parseInt(request.getParameter("IsAlarm")));
		UsersRolesModel urm = new UsersRolesModel();

		urm.setUsersID(Integer.parseInt(request.getParameter("User_ID")));
		urm.setRolesID(Integer.parseInt(request.getParameter("User_Role")));

		String resultInfo = null;

		OperationLogInterface log = new OperationLogImplement();
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");

		try
		{
			if (ud.modify(user) && urd.modify(urm))
			{

				log.writeLog(userLoginName, "操作员管理", "编辑操作员，"+user.getUsersName());
				resultInfo = "success";
			} else
			{

				resultInfo = "fail";
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			log.writeLogErrInfo(userLoginName, "操作员管理", "编辑操作员", e.getMessage());
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		UsersDao ud = new UsersDao();
		UsersRolesDao urd = new UsersRolesDao();
		UsersModel user = new UsersModel();
		String resultInfo = "failed";
		String loginName = null;
		if (request.getParameter("User_LoginName") != null)
		{
			loginName = request.getParameter("User_LoginName");
		}
		// 新增用户前先验证是否登录名相同
		if (ud.hasSameLoginName(loginName) )// 能查到ID，则相同
		{
			int hasSameLoginName = 1;
			PrintWriter out = response.getWriter();
			out.println(hasSameLoginName);
			out.flush();
			out.close();

		} else
		{
			
			user.setUsersLoginName(loginName);
			user.setUsersPassword(request.getParameter("User_PassWord"));
			user.setUsersNum(request.getParameter("User_Num"));
			user.setUsersName(request.getParameter("User_Name"));
			user.setUsersDepartment(Integer.parseInt(request
					.getParameter("User_Department")));
			user.setUsersStateID(Integer.parseInt(request
					.getParameter("User_StateID")));
			user.setUsersGender((request.getParameter("User_Gender")));
			user.setUsersPhone(request.getParameter("User_Phone"));
			user.setUsersPhone1(request.getParameter("User_Phone1"));
			user.setUsersPhone2(request.getParameter("User_Phone2"));
			user.setUsersHomeAddress(request.getParameter("User_HomeAddress"));
			user.setUsersLastTime(new Date().toLocaleString());
			user.setIP(IPAddressInfo.getIPAddress(request));
			user.setIsAlarm(Integer.parseInt(request
					.getParameter("IsAlarm")));
			
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			try
			{
				ud.insert(user);

				user = ud.queryByName(request.getParameter("User_LoginName"));

				UsersRolesModel urm = new UsersRolesModel();
				urm.setUsersID(user.getUsersID());
				urm.setRolesID(Integer.parseInt(request.getParameter("User_Role")));

				if(urd.insert(urm)==true)
				{
					resultInfo = "success";
				}
				// 写入日志
				log.writeLog(userLoginName, "操作员管理", "添加操作员，"+user.getUsersName());

			} catch (SQLException e)
			{
				e.printStackTrace();
				// 写入日志
				log.writeLogErrInfo(userLoginName, "操作员管理", "编辑操作员",
						e.getMessage());
			}
			
			PrintWriter out = response.getWriter();
			out.println(resultInfo);
			out.flush();
			out.close();
		}
	}

	private void deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		UsersDao ud = new UsersDao();
		Integer UserID = Integer.parseInt(request.getParameter("UserID"));
		String resultInfo = null;
		if (ud.deleteAll(UserID))//删除所有与users相关联的记录
		{
			resultInfo = "success";
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			log.writeLog(userLoginName, "操作员管理", "删除操作员！"+ud.queryById(UserID).getUsersName());
		} else
		{
			resultInfo = "failed";
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getAllUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		UsersDao ud = new UsersDao();
		UsersRolesDao urd = new UsersRolesDao();
		Integer thePageCount = Integer.parseInt(request.getParameter("UserPageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("UserPageIndex"));
		
		List<UsersModel> list = ud.listUsers(thePageCount,thePageIndex);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("totalCount", ud.getTotal());
		json.add(jo);
		for (int i = list.size() - 1; i >= 0; i--)
		{
			UsersModel n = list.get(i);
			jo = new JSONObject();
			jo.put("User_ID", n.getUsersID());
			jo.put("User_Num", n.getUsersNum());
			jo.put("User_Name", n.getUsersName());
			jo.put("User_LoginName", n.getUsersLoginName());
			jo.put("User_PassWord", n.getUsersPassword());

			if (urd.query(n.getUsersID()) != null)
				jo.put("User_Role", urd.query(n.getUsersID()).getRolesID());
			else
			{
				jo.put("User_Role", null);
			}
			jo.put("User_DepartmentID", n.getUsersDepartment());
			jo.put("User_Department", n.getUsersPartmentName());
			jo.put("User_StateID", n.getUsersStateIDName());

			if ("1".equals(n.getUsersGender()))
			{
				jo.put("User_Gender", "男");
			} else
			{
				jo.put("User_Gender", "女");
			}

			jo.put("User_Phone", n.getUsersPhone());
			jo.put("User_Phone1", n.getUsersPhone1());
			jo.put("User_Phone2", n.getUsersPhone2());
			jo.put("User_HomeAddress", n.getUsersHomeAddress());
			jo.put("UsersLastTime", n.getUsersLastTime());
			
			if (1==n.getIsAlarm())
			{
				jo.put("IsAlarm", "是");
			} else
			{
				jo.put("IsAlarm", "否");
			}

			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
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
	
	private void getUserRightString(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		int userID=Integer.parseInt(request.getParameter("userID"));
		
		UsersArchitectureDao uArcDao=new UsersArchitectureDao();
		UsersAreaDao uAreaDao=new UsersAreaDao();
		UsersPartmentDao upd=new UsersPartmentDao();
		
		String archList=uArcDao.getUserArchRight(userID).trim();
		String areaList=uAreaDao.getUserAreaRight(userID).trim();
		String partList=upd.getUserPartmentRight(userID).trim();
		
		//System.out.println("archList:"+archList);
		//System.out.println("areaList:"+areaList);
		//System.out.println("partList:"+partList);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("archList",archList);
		jo.put("areaList",areaList);
		jo.put("partList",partList);
		json.add(jo);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	/**
	 * 清除用户区域权限
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	private void clearUsersAreaRight(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		UsersAreaDao ua = new UsersAreaDao();
		int userID = Integer.parseInt(request.getParameter("userID"));
		String resultInfo = null;
		if (ua.delete(userID))//删除所有与users相关联的记录
		{
			resultInfo = "success";
		} else
		{
			resultInfo = "failed";
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	
	/**
	 * 清除用户建筑权限
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	private void clearUsersArchRight(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		UsersArchitectureDao ua = new UsersArchitectureDao();
		int userID = Integer.parseInt(request.getParameter("userID"));
		String resultInfo = null;
		if (ua.delete(userID))//删除所有与users相关联的记录
		{
			resultInfo = "success";
		} else
		{
			resultInfo = "failed";
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	/**
	 * 清除用户部门权限
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	private void clearUsersPartRight(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		UsersPartmentDao ua = new UsersPartmentDao();
		int userID = Integer.parseInt(request.getParameter("userID"));
		String resultInfo = null;
		if (ua.delete(userID))//删除所有与users相关联的记录
		{
			resultInfo = "success";
		} else
		{
			resultInfo = "failed";
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	/**
	 * 清除用户部门权限
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	private void clearUsersButtonRight(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		UsersDao ua = new UsersDao();
		int userID = Integer.parseInt(request.getParameter("userID"));
		String resultInfo = null;
		if (ua.deleteButton(userID))//删除所有与users相关联的记录
		{
			resultInfo = "success";
		} else
		{
			resultInfo = "failed";
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	/**
	 * 操作员的按钮权限
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 */
	private void updateButtonRightBtn(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		UsersDao ua = new UsersDao();
		
		int usersID=Integer.parseInt(request.getParameter("userID"));
		
		String idList = request.getParameter("buttonList");//建筑的ID组string
			
		String resultInfo = "success";
		
		//插入之前先删除之前的
		ua.deleteButton(usersID);
		
		if(ua.updateButton(usersID,idList)){
			resultInfo = "success";
		} else
		{
			resultInfo = "failed";
		}
		
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	
	/**
	 * 操作员的按钮权限
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getButtonRightBtn(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		UsersDao ua = new UsersDao();
		
		int usersID=Integer.parseInt(request.getParameter("userID"));
		
			
		String resultInfo = ua.getButton(usersID);
		
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	private void getButtonRightBtn2(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		UsersDao ua = new UsersDao();
		HttpSession session = request.getSession();
        int usersID = 0;
        if (session.getAttribute("userId") != null)
        {
        	usersID = Integer.parseInt(session.getAttribute("userId").toString());
        }
		
			
		String resultInfo = ua.getButton(usersID);
		
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	
	private void getUserID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		HttpSession session = request.getSession();
        int userID = 0;
        if (session.getAttribute("userId") != null)
        {
            userID = Integer.parseInt(session.getAttribute("userId").toString());
        }
		
		PrintWriter out = response.getWriter();
		out.println(userID);
		out.flush();
		out.close();
	}
	private void changePass(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		HttpSession session = request.getSession();
        int userID = 0;
        if (session.getAttribute("userId") != null)
        {
            userID = Integer.parseInt(session.getAttribute("userId").toString());
        }
        String oldPass="";
        if(request.getParameter("oldPass")!=null){
        	oldPass=request.getParameter("oldPass");
        }
        String newPass1="";
        if(request.getParameter("newPass1")!=null){
        	newPass1=request.getParameter("newPass1");
        }
        String newPass2="";
        if(request.getParameter("newPass2")!=null){
        	newPass2=request.getParameter("newPass2");
        }
        
		UsersModel user = new UsersModel();
		UsersDao ud = new UsersDao();
		user=ud.queryById(userID);
		String resultInfo = null;
		if(!user.getUsersPassword().equals(MD5Encryption.MD5(oldPass)))
		{
			resultInfo = "fail";
			PrintWriter out = response.getWriter();
			out.println(resultInfo);
			out.flush();
			out.close();
		}

		else{
			try
			{
				user.setUsersPassword(newPass1);
				if (ud.modify(user))
				{
					resultInfo = "success";
				} else
				{

					resultInfo = "fail";
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}

			PrintWriter out = response.getWriter();
			out.println(resultInfo);
			out.flush();
			out.close();
		}
		
	}
}