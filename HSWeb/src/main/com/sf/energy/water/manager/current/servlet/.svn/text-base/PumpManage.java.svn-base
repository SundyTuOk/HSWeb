package com.sf.energy.water.manager.current.servlet;

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

import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.PumpDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.PumpModel;

public class PumpManage extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
		
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		String method = request.getParameter("method");
		if ("getPumpTree".equalsIgnoreCase(method))
			getPumpTree(request, response);
	}
	private void getPumpTree(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AreaDao areaDao = new AreaDao();
		HttpSession session = request.getSession();
		int userID = 0;
		List<Area> areaList = areaDao.display();

		if (areaList != null && areaList.size() > 0)
		{
			JSONObject tree = new JSONObject();

			for (Area a : areaList)
			{
				JSONObject jo = buildAreaNode(a);
				if (jo != null)
					tree.put(a.getId(), jo);
			}

			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			//System.out.println("数据"+tree.toString());
			out.flush();
			out.close();
		}

	}
	private JSONObject buildAreaNode(Area theArea)
			throws SQLException
	{
		PumpDao pumpDao=new PumpDao();

		JSONObject node = null;
		if (theArea != null)
		{
			List<PumpModel> pumpList = pumpDao.queryPumpByAreaID(
					theArea.getId());
			node = new JSONObject();
			node.put("name", theArea.getName());
			node.put("AreaID", theArea.getId());

			if (pumpList != null && pumpList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject pumpListNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for (PumpModel pump : pumpList)
				{
					JSONObject pumpNode = new JSONObject();
					pumpNode.put("type", "item");
					pumpNode.put("name", pump.getPump_name());
					pumpNode.put("AreaID", theArea.getId());
					pumpNode.put("PumpID", pump.getPump_id());
					pumpListNode.put(pump.getPump_id(), pumpNode);
				}

				jo.put("children", pumpListNode);
				node.put("additionalParameters", jo);
			} else
			{
				return null;
			}

		}

		return node;
	}
	
}