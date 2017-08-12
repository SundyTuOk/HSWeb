package com.sf.energy.powernet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.light.model.SLControlModel;
import com.sf.energy.powernet.dao.EquipmentDao;
import com.sf.energy.powernet.model.EquipmentModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class EquipmentServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try {
			findMethod(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String method = request.getParameter("method");
		//System.out.println("method:" + method);

		if ("getEquipmentInfo".equals(method))
			getEquipmentInfo(request, response);

		if ("getMaintainInfo".equals(method))
			getMaintainInfo(request, response);

		if ("addMaintainInfo".equals(method))
			addMaintainInfo(request, response);

		 if ("deletMaintainInfoByID".equals(method))
		 deletMaintainInfoByID(request, response);
		
		 if ("updateMaintainInfo".equals(method))
		 updateMaintainInfo(request, response);
		
		 if ("queryMaintainInfoByID".equals(method))
		 queryMaintainInfoByID(request, response);

	}

	private void queryMaintainInfoByID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, ParseException, IOException
	{
		EquipmentDao ed = new EquipmentDao();
		EquipmentModel em = null;
		int id = 0;
		if(request.getParameter("maintainID") != null)
		{
			id = Integer.parseInt(request.getParameter("maintainID"));
		}
		////System.out.println(id);
		em = ed.queryByID(id);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("partID", em.getPartID());
		jo.put("partName", em.getPartName());
		jo.put("project", em.getProject());
		jo.put("mainDate", em.getMainDate());
		jo.put("mainMan", em.getMainMan());
		jo.put("remark", em.getRemark());
		
		
		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void updateMaintainInfo(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, IOException {
		EquipmentDao ed = new EquipmentDao();
		OperationLogInterface log = new OperationLogImplement();
		EquipmentModel em = new EquipmentModel();
		em.setMaintainID(Integer.parseInt(request.getParameter("maintainID")));
		em.setPartID(Integer.parseInt(request.getParameter("partID")));
		em.setProject(request.getParameter("project"));
		em.setMainDate(request.getParameter("mainDate"));
		em.setMainMan(request.getParameter("mainMan"));
		em.setRemark(request.getParameter("remark"));
		
		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (ed.modify(em))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "设备巡维", "编辑配电维护信息信息");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "设备巡维", "编辑配电维护信息信息","编辑失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
		
	}

	private void deletMaintainInfoByID(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, SQLException, IOException {
		
		EquipmentDao ed = new EquipmentDao();
		String arrayOfID = request.getParameter("maintainID");
		
		String[] maintainID = null;
		maintainID = arrayOfID.split("\\|");
		
		// 统计删除插入条数
		int result = 0;

		for (int i = 0; i < maintainID.length; i++)
		{
			if(ed.delete(Integer.parseInt(maintainID[i])))
			{
				result++;
			}
		}
		PrintWriter out = response.getWriter();
		out.println("成功删除"+result+"条数据。");
		out.flush();
		out.close();
		
	}

	private void addMaintainInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		EquipmentDao ed = new EquipmentDao();
		OperationLogInterface log = new OperationLogImplement();
		EquipmentModel em = new EquipmentModel();
		em.setPartID(Integer.parseInt(request.getParameter("partID")));
		em.setProject(request.getParameter("project"));
		em.setMainDate(request.getParameter("mainDate"));
		em.setMainMan(request.getParameter("mainMan"));
		em.setRemark(request.getParameter("remark"));
		
		////System.out.println("servlet");

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");

		try {
			if (ed.insert(em)) {
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "设备巡维", "添加配电维护信息");
			} else {
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "设备巡维", "添加配电维护信息",
						"添加失败！！！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void getMaintainInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		EquipmentDao ed = new EquipmentDao();
		EquipmentModel em = null;
		int thePageCount = Integer.parseInt(request
				.getParameter("equipmentPageCount"));
		int thePageIndex = Integer.parseInt(request
				.getParameter("equipmentPageIndex"));
		int partID = Integer.parseInt(request.getParameter("partID"));
		
		String orderInfo = " order by " + request.getParameter("tableName") + " " + request.getParameter("order");

		ArrayList<EquipmentModel> list = new ArrayList<EquipmentModel>();
		list = ed.queryMaintainInfo(thePageCount, thePageIndex, partID, orderInfo);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("totalCount", ed.getTotalCount());
		json.add(jo);
		for (int i = 0; i < list.size(); i++) {
			jo = new JSONObject();
			em = list.get(i);
			jo.put("maintainID", em.getMaintainID());
			jo.put("partNum", em.getPartNum());
			jo.put("partName", em.getPartName());
			jo.put("partStyleName", em.getPartStyleName());
			jo.put("project", em.getProject());
			jo.put("mainDate", em.getMainDate());
			jo.put("mainMan", em.getMainMan());
			jo.put("remark", em.getRemark());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		//System.out.println(json.size());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void getEquipmentInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		EquipmentDao ed = new EquipmentDao();
		EquipmentModel em = null;
		int thePageCount = Integer.parseInt(request
				.getParameter("equipmentPageCount"));
		int thePageIndex = Integer.parseInt(request
				.getParameter("equipmentPageIndex"));
		int partID = Integer.parseInt(request.getParameter("partID"));
		
		String orderInfo = " order by " + request.getParameter("tableName") + " " + request.getParameter("order");

		ArrayList<EquipmentModel> list = new ArrayList<EquipmentModel>();
		list = ed.queryEquipmentInfo(thePageCount, thePageIndex, partID, orderInfo);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("totalCount", ed.getTotalCount());
		json.add(jo);
		for (int i = 0; i < list.size(); i++) {
			jo = new JSONObject();
			em = list.get(i);
			jo.put("partID", em.getPartID());
			jo.put("partNum", em.getPartNum());
			jo.put("partName", em.getPartName());
			jo.put("amMeter485Address", em.getAmMeter485Address());
			jo.put("amMeterState", em.getAmMeterState());
			jo.put("isSupply", em.getIsSupply());
			jo.put("state", em.getState());
			jo.put("lastTime", em.getLastTime());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		//System.out.println(json.size());
		out.println(json.toString());
		out.flush();
		out.close();

	}

}
