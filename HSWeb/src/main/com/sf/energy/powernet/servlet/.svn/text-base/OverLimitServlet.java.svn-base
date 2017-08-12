package com.sf.energy.powernet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.powernet.dao.OverLimitDao;
import com.sf.energy.powernet.model.OverLimitModel;

public class OverLimitServlet extends HttpServlet
{
	

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

		if ("getOverLimitInfo".equals(method))
			getOverLimitInfo(request, response);
		
		if ("deleteOverLimitInfo".equals(method))
			deleteOverLimitInfo(request, response);


	}

	private void deleteOverLimitInfo(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, SQLException, IOException
	{
		OverLimitDao old = new OverLimitDao();
		String arrayOfID = request.getParameter("overID");
		
		String[] overID = null;
		overID = arrayOfID.split("\\|");
		
		// 统计删除插入条数
		int result = 0;

		for (int i = 0; i < overID.length; i++)
		{
			if(old.delete(Integer.parseInt(overID[i])))
			{
				result++;
			}
		}
		PrintWriter out = response.getWriter();
		out.println("成功删除"+result+"条数据。");
		out.flush();
		out.close();
		
	}

	private void getOverLimitInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		OverLimitDao old = new OverLimitDao();
		OverLimitModel olm = null;
		int thePageCount = Integer.parseInt(request
				.getParameter("overLimitPageCount"));
		int thePageIndex = Integer.parseInt(request
				.getParameter("overLimitPageIndex"));
		int partID = Integer.parseInt(request.getParameter("overLimitPartID"));
		
		String queryMsg = request.getParameter("overLimitQueryStyle")+"|"+request.getParameter("overLimitBeginDate")+"|"+request.getParameter("overLimitEndDate");
		String orderInfo = " order by " + request.getParameter("overLimitTableName") + " " + request.getParameter("overLimitOrder");
		
		ArrayList<OverLimitModel> list = new ArrayList<OverLimitModel>();
		list = old.queryOverLimitInfo(thePageCount, thePageIndex, partID, queryMsg, orderInfo);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("totalCount", old.getTotalCount());
		json.add(jo);
		for (int i = 0; i < list.size(); i++) {
			jo = new JSONObject();
			olm = list.get(i);
			jo.put("PD_OverID", olm.getPD_OverID());
			jo.put("ValueTime", olm.getValueTime());
			jo.put("Part_Name", olm.getPart_Name());
			jo.put("OverStyleName", olm.getOverStyleName());
			jo.put("ZValue", olm.getZValue());
			jo.put("XValue", olm.getXValue());
			jo.put("CValue", olm.getCValue());
			jo.put("PerValue", olm.getPerValue());
			jo.put("Remark", olm.getRemark());
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
