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

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.project.system.dao.PressureDevDao;
import com.sf.energy.project.system.dao.PumpDao;
import com.sf.energy.project.system.model.PressureDevModel;
import com.sf.energy.project.system.model.PumpModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class PressureDevManageServlet extends HttpServlet
{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		
		String method = request.getParameter("method");
		
		if("getPressureDevManageContent".equalsIgnoreCase(method))
		{
			try
			{
				getPressureDevManageContent(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("AddPressureDevManageContent".equalsIgnoreCase(method))
		{
			 try
			{
				AddPressureDevManageContent(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("deleteSomePressureDevManageContent".equalsIgnoreCase(method))
		{
			try
			{
				deleteSomePressureDevManageContent(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("EditPressureDevManageContent".equalsIgnoreCase(method))
		{
			try
			{
				EditPressureDevManageContent(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	public void getPressureDevManageContent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		
		PressureDevDao  pdDao = new PressureDevDao();
		List<PressureDevModel>  list = null;
		
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
		String tableName="";
		String order="";
		if (request.getParameter("tableName") != null)
			tableName = request.getParameter("tableName");
		if (request.getParameter("order") != null)
			order = request.getParameter("order");
		list = pdDao.getPressureDevManageContentOrder(thePageCount,thePageIndex,tableName,order);
		
		totalcount = pdDao.getTotalCount();
		
		Gson gson = new Gson();
		
		
		String data = gson.toJson(new Object[]{list,totalcount});
	//	System.out.println("数据    "+data);
		out.println(data);
		out.flush();
		out.close();
	}
	
	//新增
	public void AddPressureDevManageContent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		boolean  isSuccess = false;
		
		String Num = "";
		String Name = "";
		int  areaID = 0;
		int  gatherID = 0;
		int PumpID = 0;
		String PDM485addr = "";
		int  ISout = 0;
		String LastTime = "";
		int Port=0;
		PressureDevModel pModel = new PressureDevModel();
		PressureDevDao  pdDao = new PressureDevDao();
		
		if(request.getParameter("PressureDevManageEditPort") != null)
		{
			Port = Integer.parseInt(request.getParameter("PressureDevManageEditPort"));
		}
		if(request.getParameter("PressureDevManageEditNum") != null)
		{
			Num = request.getParameter("PressureDevManageEditNum");
		}
		if(request.getParameter("PressureDevManageEditName") != null)
		{
			Name = request.getParameter("PressureDevManageEditName");
			//System.out.println("++++++"+Name);
		}
		if(request.getParameter("PressureDevManageEditArea") != null)
		{
			areaID = Integer.parseInt(request.getParameter("PressureDevManageEditArea"));
		}
		if(request.getParameter("PressureDevManageEditGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("PressureDevManageEditGatherID"));
		}
		if(request.getParameter("PressureDevManageEditPumpID") != null)
		{
			PumpID = Integer.parseInt(request.getParameter("PressureDevManageEditPumpID"));
		}
		if(request.getParameter("PressureDevManageEdit485Address") != null)
		{
			PDM485addr = request.getParameter("PressureDevManageEdit485Address");
		}
		if(request.getParameter("PressureDevManageEditIsOut") != null)
		{
			ISout = Integer.parseInt(request.getParameter("PressureDevManageEditIsOut"));
		}
		if(request.getParameter("PressureDevManageEditLastTime") != null)
		{
			LastTime = request.getParameter("PressureDevManageEditLastTime");
		}
		
		pModel.setPressureDev_Num(Num);
		pModel.setArea_ID(areaID);
		pModel.setGather_ID(gatherID);
		pModel.setIsoutpressureDev(ISout);
		pModel.setPre_485Address(PDM485addr);
		pModel.setPressureDev_Name(Name);
		pModel.setPump_ID(PumpID);
		pModel.setLasttime(LastTime);
		pModel.setPressureDev_Port(Port);
		
		isSuccess = pdDao.insertPressureDev(pModel);
		OperationLogInterface log = new OperationLogImplement();
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		log.writeLog(userLoginName, "压力计管理", "新增压力计！"+pModel.getPressureDev_Name());
//		System.out.println("新建 克隆:"+pModel.getPressureDev_ID()+" |  "
//				+pModel.getPressureDev_Num()+" |  "
//				+pModel.getArea_ID()+" |  "
//				+pModel.getGather_ID()+" |  "
//				+pModel.getIsoutpressureDev()+" |  "
//				+pModel.getPre_485Address()+" |  "
//				+pModel.getPressureDev_Name()+" |  "
//				+pModel.getPump_ID()+" |  "
//				+pModel.getLasttime());
//		
//		System.out.println(isSuccess);
		out.println(isSuccess);
		out.flush();
		out.close();
	}

	//删除
	public void deleteSomePressureDevManageContent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		boolean b=false;
		
		PressureDevDao  pdDao = new PressureDevDao();
		
		String list = request.getParameter("CheckedDataList");
		
		String[] arr = list.split(" ");
		
		for(int i=0;i<arr.length;i++)
		{
			//System.out.println("ID"+Integer.parseInt(arr[i]));
			b=pdDao.deletePressureDev(Integer.parseInt(arr[i]));
			
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			log.writeLog(userLoginName, "压力计管理", "删除压力计！"+pdDao.query(i));
		}
		int num = pdDao.queryAllPressureNum();
		JSONObject obj = new JSONObject();
		obj.put("result", b);
		obj.put("num", num);
	//	System.out.println("删除调用  "+obj.toString());
		out.println(obj.toString());
		out.flush();
		out.close();
	}
	
	//修改
	public void EditPressureDevManageContent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
//		response.setCharacterEncoding("UTF-8");
//		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		boolean  isSuccess = false;
		
		int preDevID = 0;
		String Num = "";
		String Name = "";
		int  areaID = 0;
		int  gatherID = 0;
		int PumpID = 0;
		String PDM485addr = "";
		int  ISout = 0;
		String LastTime = "";
		int Port=0;
		
		PressureDevModel pModel = new PressureDevModel();
		PressureDevDao  pdDao = new PressureDevDao();
		
		if(request.getParameter("PressureDevManageEditPort") != null)
		{
			Port = Integer.parseInt(request.getParameter("PressureDevManageEditPort"));
		}
		if(request.getParameter("PressureDevID") != null)
		{
			preDevID = Integer.parseInt(request.getParameter("PressureDevID"));
		}
		if(request.getParameter("PressureDevManageEditPumpID") != null)
		{
			PumpID = Integer.parseInt(request.getParameter("PressureDevManageEditPumpID"));
		}
		if(request.getParameter("PressureDevManageEditNum") != null)
		{
			Num = request.getParameter("PressureDevManageEditNum");
		}
		if(request.getParameter("PressureDevManageEditName") != null)
		{
			Name = request.getParameter("PressureDevManageEditName");
		}
		if(request.getParameter("PressureDevManageEditArea") != null)
		{
			areaID = Integer.parseInt(request.getParameter("PressureDevManageEditArea"));
		}
		if(request.getParameter("PressureDevManageEditGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("PressureDevManageEditGatherID"));
		}
		if(request.getParameter("PressureDevManageEditPumpID") != null)
		{
			PumpID = Integer.parseInt(request.getParameter("PressureDevManageEditPumpID"));
		}
		if(request.getParameter("PressureDevManageEdit485Address") != null)
		{
			PDM485addr = request.getParameter("PressureDevManageEdit485Address");
		}
		if(request.getParameter("PressureDevManageEditIsOut") != null)
		{
			ISout = Integer.parseInt(request.getParameter("PressureDevManageEditIsOut"));
		}
		if(request.getParameter("PressureDevManageEditLastTime") != null)
		{
			LastTime = request.getParameter("PressureDevManageEditLastTime");
		}
		
		pModel.setPressureDev_ID(preDevID);
		pModel.setPressureDev_Num(Num);
		pModel.setArea_ID(areaID);
		pModel.setGather_ID(gatherID);
		pModel.setIsoutpressureDev(ISout);
		pModel.setPre_485Address(PDM485addr);
		pModel.setPressureDev_Name(Name);
		pModel.setPump_ID(PumpID);
		pModel.setLasttime(LastTime);
		pModel.setPressureDev_Port(Port);
		
		isSuccess = pdDao.updatePressureDev(pModel);
		OperationLogInterface log = new OperationLogImplement();
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		log.writeLog(userLoginName, "压力计管理", "编辑压力计！"+pModel.getPressureDev_Name());
//		System.out.println(pModel.getPressureDev_ID()+" |  "
//		+pModel.getPressureDev_Num()+" |  "
//		+pModel.getArea_ID()+" |  "
//		+pModel.getGather_ID()+" |  "
//		+pModel.getIsoutpressureDev()+" |  "
//		+pModel.getPre_485Address()+" |  "
//		+pModel.getPressureDev_Name()+" |  "
//		+pModel.getPump_ID()+" |  "
//		+pModel.getLasttime());
//		System.out.println(isSuccess);
		out.println(isSuccess);
		out.flush();
		out.close();
	}

}
