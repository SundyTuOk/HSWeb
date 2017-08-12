package com.sf.energy.water.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.manager.current.model.AmMaintence;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.util.DataValidation;
import com.sf.energy.water.manager.current.dao.WmMaintenceDao;
import com.sf.energy.water.manager.current.model.WmMaintence;

public class WmMaintenceManage extends HttpServlet
{
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			findMethod(request, response);

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			ParseException
	{
		String method = request.getParameter("method");

		if ("addWmMaintence".equals(method))
			addWmMaintence(request, response);

		if ("updateWmMaintence".equals(method))
			updateWmMaintence(request, response);

		if ("deleteWmMaintence".equals(method))
			deleteWmMaintence(request, response);

		if ("getAllWmMaintence".equals(method))
			getAllWmMaintence(request, response);
	}

	private void addWmMaintence(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, SQLException,
			IOException
	{
		WatermeterDao watermeterDao = new WatermeterDao();
		WmMaintenceDao amd = new WmMaintenceDao();
		WmMaintence am = new WmMaintence();

		if (request.getParameter("WatermeterID") != null
				&& DataValidation
						.isNumber(request.getParameter("WatermeterID")))
			am.setWatermeterID(Integer.parseInt(request
					.getParameter("WatermeterID")));

		if (watermeterDao.getWatermeterByID(am.getWatermeterID()) == null)
			return;

		if (request.getParameter("MaintTime") != null)
			am.setMainTime(df.parse(request.getParameter("MaintTime")));

		if (request.getParameter("MaintRemark") != null)
			am.setMaintRemark(request.getParameter("MaintRemark"));

		if (request.getParameter("MaintMan") != null)
			am.setMaintMan(request.getParameter("MaintMan"));

		String info = "fail";

		if (amd.insertWmMaintence(am))
			info = "success";

		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	private void updateWmMaintence(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, SQLException,
			IOException
	{
		WatermeterDao watermeterDao = new WatermeterDao();
		WmMaintenceDao amd = new WmMaintenceDao();
		WmMaintence am = new WmMaintence();

		if (request.getParameter("WatermeterID") != null
				&& DataValidation
						.isNumber(request.getParameter("WatermeterID")))
			am.setWatermeterID(Integer.parseInt(request
					.getParameter("WatermeterID")));

		if (watermeterDao.getWatermeterByID(am.getWatermeterID()) == null)
			return;

		if (request.getParameter("MaintID") != null
				&& DataValidation.isNumber(request.getParameter("MaintID")))
			am.setMaintID(Integer.parseInt(request.getParameter("MaintID")));

		if (request.getParameter("MaintTime") != null)
			am.setMainTime(df.parse(request.getParameter("MaintTime")));

		if (request.getParameter("MaintRemark") != null)
			am.setMaintRemark(request.getParameter("MaintRemark"));

		if (request.getParameter("MaintMan") != null)
			am.setMaintMan(request.getParameter("MaintMan"));

		String info = "fail";

		if (amd.updateWmMaintence(am))
			info = "success";

		PrintWriter out = response.getWriter();
		out.println(info.trim());
		out.flush();
		out.close();
	}

	private void deleteWmMaintence(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, SQLException,
			IOException
	{
		WmMaintenceDao amd = new WmMaintenceDao();
		int mainID = 0;
		if (request.getParameter("MaintID") != null
				&& DataValidation.isNumber(request.getParameter("MaintID")))
			mainID = Integer.parseInt(request.getParameter("MaintID"));

		String info = "fail";

		if (amd.deleteWmMaintence(mainID))
			info = "success";

		PrintWriter out = response.getWriter();
		out.println(info.trim());
		out.flush();
		out.close();
	}

	private void getAllWmMaintence(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, SQLException,
			IOException
	{
		WmMaintenceDao amd = new WmMaintenceDao();
		String sortLabel = "Maint_ID";
		if (request.getParameter("SortLabel") != null)
			sortLabel = request.getParameter("SortLabel");
		int schoolID=-1;
		int areaID=-1;
		int archID=-1;
		int floor=-1;
		int wamID=-1;
		boolean isAsc = false;
		if (request.getParameter("SortType") != null)
		{
			if ("Asc".equalsIgnoreCase(request.getParameter("SortType")))
				isAsc = true;

			if ("Desc".equalsIgnoreCase(request.getParameter("SortType")))
				isAsc = false;
		}
		Integer thePageCount = Integer.parseInt(request.getParameter("AmMaintencePageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("AmMaintencePageIndex"));
		if(Integer.parseInt(request.getParameter("schoolID"))!=-1)
		{
			schoolID=Integer.parseInt(request.getParameter("schoolID"));
		}
		if(Integer.parseInt(request.getParameter("areaID"))!=-1)
		{
			areaID=Integer.parseInt(request.getParameter("areaID"));
		}
		if(Integer.parseInt(request.getParameter("archID"))!=-1)
		{
			archID=Integer.parseInt(request.getParameter("archID"));
		}
		
		if(Integer.parseInt(request.getParameter("floor"))!=-1)
		{
			floor=Integer.parseInt(request.getParameter("floor"));
		}
		if(Integer.parseInt(request.getParameter("wamID"))!=-1)
		{
			wamID=Integer.parseInt(request.getParameter("wamID"));
		}	
		List<WmMaintence> list =new ArrayList<WmMaintence>();
		//如果是全校业务
		if(areaID==-1 && archID==-1 && floor==-1 && wamID==-1)
		{
			schoolID=0;//将school置为非-1的任何数，则代表是全校的业务
		}
		if(schoolID!=-1)//查整个学校
		{
			try
			{
				list = amd.selectAllWmMaintenceByLableOrder(
                sortLabel, isAsc,thePageCount,thePageIndex);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}else{
			try
			{
				list = amd.selectWmMaintenceByTree(
						sortLabel,isAsc,areaID,archID,floor,wamID,thePageCount,thePageIndex);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		JSONArray main = new JSONArray();
		JSONObject jo11 = new JSONObject(); 
        jo11.put("totalCount", amd.getTotal());
        main.add(jo11);
		if (list != null && list.size() > 0)
		{
//			JSONArray main = new JSONArray();

			for (WmMaintence am : list)
			{
				JSONObject jo = new JSONObject();

				jo.put("MaintID", am.getMaintID());
				jo.put("WatermeterID", am.getWatermeterID());
				jo.put("WatermeterName", am.getWatermeterName());
				if (am.getMainTime() != null)
					jo.put("MaintTime", df.format(am.getMainTime()));
				jo.put("MaintRemark", am.getMaintRemark());
				jo.put("MaintMan", am.getMaintMan());

				main.add(jo);
			}
			
		}
		PrintWriter out = response.getWriter();
		out.println(main.toString());
		out.flush();
		out.close();
	}
}
