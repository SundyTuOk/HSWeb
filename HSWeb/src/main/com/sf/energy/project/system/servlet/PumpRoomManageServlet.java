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

import com.google.gson.Gson;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.system.dao.PumpDao;
import com.sf.energy.project.system.model.PumpModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class PumpRoomManageServlet extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		this.doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String method = request.getParameter("method");
		
		if("getPumpRoomManageContent".equalsIgnoreCase(method))
		{
			try
			{
				getPumpRoomManageContent(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("AddPumpRoomManageContent".equalsIgnoreCase(method))
		{
			 try
			{
				AddPumpRoomManageContent(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("deleteSomePumpRoomManageContent".equalsIgnoreCase(method))
		{
			try
			{
				deleteSomePumpRoomManageContent(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("EditPumpRoomManageContent".equalsIgnoreCase(method))
		{
			try
			{
				EditPumpRoomManageContent(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("queryPumpByAreaID".equalsIgnoreCase(method))
		{
			try
			{
				queryPumpByAreaID(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("queryAmmeterByAreaID".equalsIgnoreCase(method))
		{
			try
			{
				queryAmmeterByAreaID(request,response);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
	}

	private void queryAmmeterByAreaID(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{	
		PrintWriter out = response.getWriter();
		int area_id = -1;
		if(!"".equals(request.getParameter("area_ID"))&&null!=request.getParameter("area_ID")){
			area_id = Integer.parseInt(request.getParameter("area_ID"));
		}
		//System.out.println("area_id :"+area_id);
		PumpDao  pDao = new PumpDao();
		List<AmmeterModel>  list = null;
		AmmeterModel n = null;
		list = pDao.queryAmmeterByAreaID(area_id);
		JSONArray json = new JSONArray();
		for (int i = list.size() - 1; i >= 0; i--)
		{
			 n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("AMMETER_ID", n.getAmmeterID());
			jo.put("AREA_ID", n.getAreaID());
			jo.put("AMMETER_NAME", n.getAmmeterName());
			json.add(jo);
		}	
		String data = json.toString();
	//	System.out.println("数据"+data);
		out.println(data);
		out.flush();
		out.close();	
	}
		

	private void queryPumpByAreaID(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		int area_id = Integer.parseInt(request.getParameter("area_ID"));
		//System.out.println("area_id :"+area_id);
		PumpDao  pDao = new PumpDao();
		List<PumpModel>  list = null;
		PumpModel n = null;
		list = pDao.queryPumpByAreaID(area_id);
		JSONArray json = new JSONArray();
		for (int i = list.size() - 1; i >= 0; i--)
		{
			 n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("PUMP_ID", n.getPump_id());
			jo.put("AREA_ID", n.getArea_id());
			jo.put("ADDRESS", n.getAddress());
			jo.put("PUMP_NAME", n.getPump_name());
			json.add(jo);
		}	
		String data = json.toString();
	//	System.out.println("数据"+data);
		out.println(data);
		out.flush();
		out.close();	
	}

	
	
	public void getPumpRoomManageContent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		
		PumpDao  pDao = new PumpDao();
		List<PumpModel>  list = null;
		
		int totalcount = 0;		//记录在该页第几条
		
		int thePageCount = 0;
		int thePageIndex = 0;
		
		
		if(request.getParameter("pageCount") != null)
		{
			thePageCount = Integer.parseInt(request.getParameter("pageCount"));
		}
		if(request.getParameter("pageIndex") != null)
		{
			thePageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		}
		list = pDao.queryAllPumpByPage(thePageCount,thePageIndex);
		
		totalcount = pDao.getTotalCount();
		Gson gson = new Gson();
		
		String data = gson.toJson(new Object[]{list,totalcount,thePageIndex});
	//	System.out.println("数据"+data);
		out.println(data);
		out.flush();
		out.close();
	}
	
	//新增
	public void AddPumpRoomManageContent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		boolean  isSuccess = false;
		
		String Num = "";
		String Name = "";
		int  areaID = 0;
		String  JLID = "";
		String XMLName = "";
		int XiShu = 0;
		String Address = "";
		String Note = "";
		
		PumpModel pModel = new PumpModel();
		PumpDao  pDao = new PumpDao();
		
		if(request.getParameter("PumpRoomManageAddNum") != null)
		{
			Num = request.getParameter("PumpRoomManageAddNum");
		}
		if(request.getParameter("PumpRoomManageAddName") != null)
		{
			Name = request.getParameter("PumpRoomManageAddName");
		}
		if(request.getParameter("PumpRoomManageAddArea") != null)
		{
			areaID = Integer.parseInt(request.getParameter("PumpRoomManageAddArea"));
		}
		if(request.getParameter("PumpRoomManageAddJLID") != null)
		{
			JLID = request.getParameter("PumpRoomManageAddJLID");
		}
		if(request.getParameter("PumpRoomManageAddXMLName") != null)
		{
			XMLName = request.getParameter("PumpRoomManageAddXMLName");
		}
		if(request.getParameter("PumpRoomManageAddXiShu") != null)
		{
			XiShu = Integer.parseInt(request.getParameter("PumpRoomManageAddXiShu"));
		}
		if(request.getParameter("PumpRoomManageAddAddress") != null)
		{
			Address = request.getParameter("PumpRoomManageAddAddress");
		}
		if(request.getParameter("PumpRoomManageAddRemark") != null)
		{
			Note = request.getParameter("PumpRoomManageAddRemark");
		}
		
		pModel.setAddress(Address);
		pModel.setArea_id(areaID);
		pModel.setPump_jiliangID(JLID);
		pModel.setPump_name(Name);
		pModel.setPump_Num(Num);
		pModel.setPump_xishu(XiShu);
		pModel.setXMLName(XMLName);
		pModel.setPump_Note(Note);
		
		
		isSuccess = pDao.insertPump(pModel);
		OperationLogInterface log = new OperationLogImplement();
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		log.writeLog(userLoginName, "水泵房管理", "新增水泵房！"+pModel.getPump_name());
		out.println(isSuccess);
		out.flush();
		out.close();
	}

	//删除
	public void deleteSomePumpRoomManageContent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		boolean b=false;
		
		PumpDao pDao = new PumpDao();
		
		String list = request.getParameter("CheckedDataList");
	//	System.out.println(request.getParameter("CheckedDataList"));
		String[] arr = list.split(" ");
		
		for(int i=0;i<arr.length;i++)
		{
			//System.out.println("ID"+Integer.parseInt(arr[i]));
			b=pDao.deletePump(Integer.parseInt(arr[i]));
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			log.writeLog(userLoginName, "水泵房管理", "删除水泵房！"+pDao.getPumpNameByID(i));
		}	
		int num = pDao.queryAllPumpNum();
		JSONObject obj = new JSONObject();
		obj.put("result", b);
		obj.put("num", num);
	//	System.out.println("删除调用  "+obj.toString());
		out.println(obj.toString());
		out.flush();
		out.close();
	}
	
	//修改
	public void EditPumpRoomManageContent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		boolean  isSuccess = false;
		
		int PumpID = 0;
		String Num = "";
		String Name = "";
		int  areaID = 0;
		String  JLID = "";
		String XMLName = "";
		int XiShu = 0;
		String Address = "";
		String Note = "";
		
		PumpModel pModel = new PumpModel();
		PumpDao  pDao = new PumpDao();
		
		if(request.getParameter("PumpID") != null)
		{
			PumpID = Integer.parseInt(request.getParameter("PumpID"));
		}
		if(request.getParameter("PumpRoomManageAddNum") != null)
		{
			Num = request.getParameter("PumpRoomManageAddNum");
		}
		if(request.getParameter("PumpRoomManageAddName") != null)
		{
			Name = java.net.URLDecoder.decode(request.getParameter("PumpRoomManageAddName"),"UTF-8");//request.getParameter("PumpRoomManageAddName");
		}
		if(request.getParameter("PumpRoomManageAddArea") != null)
		{
			areaID = Integer.parseInt(request.getParameter("PumpRoomManageAddArea"));
		}
		if(request.getParameter("PumpRoomManageAddJLID") != null)
		{
			JLID = request.getParameter("PumpRoomManageAddJLID");
		}
		if(request.getParameter("PumpRoomManageAddXMLName") != null)
		{
			XMLName = request.getParameter("PumpRoomManageAddXMLName");
		}
		if(request.getParameter("PumpRoomManageAddXiShu") != null)
		{
			XiShu = Integer.parseInt(request.getParameter("PumpRoomManageAddXiShu"));
		}
		if(request.getParameter("PumpRoomManageAddAddress") != null)
		{
			Address = request.getParameter("PumpRoomManageAddAddress");
		}
		if(request.getParameter("PumpRoomManageAddRemark") != null)
		{
			Note = request.getParameter("PumpRoomManageAddRemark");
		}
		
		pModel.setPump_id(PumpID);
		pModel.setAddress(Address);
		pModel.setArea_id(areaID);
		pModel.setPump_jiliangID(JLID);
		pModel.setPump_name(Name);
		pModel.setPump_Num(Num);
		pModel.setPump_xishu(XiShu);
		pModel.setXMLName(XMLName);
		pModel.setPump_Note(Note);
		
		
		isSuccess = pDao.updatePump(pModel);
		OperationLogInterface log = new OperationLogImplement();
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		log.writeLog(userLoginName, "水泵房管理", "编辑水泵房！"+pModel.getPump_name());
		out.println(isSuccess);
		out.flush();
		out.close();
	}
	
}
