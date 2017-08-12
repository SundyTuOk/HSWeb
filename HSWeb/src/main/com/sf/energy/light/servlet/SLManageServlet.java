package com.sf.energy.light.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.sf.energy.light.dao.SLControlDao;
import com.sf.energy.light.dao.SLLineDao;
import com.sf.energy.light.dao.SlLampDao;
import com.sf.energy.light.model.SLControlModel;
import com.sf.energy.light.model.SLLineModel;
import com.sf.energy.light.model.SlLampModel;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;

public class SLManageServlet extends HttpServlet 
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

		if ("getSLTree".equals(method))
			getSLTree(request, response);

		if ("getSLControlTree".equals(method))
			getSLControlTree(request, response);
		
		if ("getSLLineTree".equals(method))
			getSLLineTree(request, response);
	}
	
	private void getSLLineTree(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AreaDao areaDao=new AreaDao();
		List<Area> areaList= areaDao.display();
		
		
		if (areaList != null && areaList.size() > 0)
		{
			JSONObject tree = new JSONObject();

			for (Area a : areaList)
			{
				JSONObject jo = SLL_BuildAreaNode(a);
				if (jo != null)
					tree.put(a.getId(), jo);
			}
			
			PrintWriter out = response.getWriter();
		//	System.out.println("tree source : "+tree.toString());
			out.println(tree.toString());
			out.flush();
			out.close();
		}
	}

	private JSONObject SLL_BuildAreaNode(Area theArea) throws SQLException
	{

		SLLineDao slld = new SLLineDao();
		ArrayList<SLLineModel> slLineList = null;
		slLineList = slld.queryAll();
		
		JSONObject node = null;
		if (theArea != null)
		{
			boolean hasChildren=false;
			for(SLLineModel sllm : slLineList)
			{
				if(sllm.getAREA_ID() == theArea.getId())
				{
					hasChildren=true;
					break;
				}
			}
			
			node = new JSONObject();
			node.put("name", theArea.getName());

			if (hasChildren)
			{
				node.put("type", "folder");
				JSONObject slAreaNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for(SLLineModel sllm : slLineList)
				{
					if(sllm.getAREA_ID() == theArea.getId())
					{
						JSONObject slLineNode = new JSONObject();
						slLineNode.put("type", "item");
						slLineNode.put("name", sllm.getSLLINE_NAME());
						slLineNode.put("SlLineID", sllm.getSLLINE_ID());
						slAreaNode.put(sllm.getSLLINE_ID(), slLineNode);
					}
				}

				jo.put("children", slAreaNode);
				node.put("additionalParameters", jo);
			} else
			{
				return null;
			}
		}

		return node;
	}

	private void getSLControlTree(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AreaDao areaDao=new AreaDao();
		List<Area> areaList= areaDao.display();
		
		
		if (areaList != null && areaList.size() > 0)
		{
			JSONObject tree = new JSONObject();

			for (Area a : areaList)
			{
				JSONObject jo = SLC_BuildAreaNode(a);
				if (jo != null)
					tree.put(a.getId(), jo);
			}
			
			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			out.flush();
			out.close();
		}
		
	}	
	private JSONObject SLC_BuildAreaNode(Area theArea) throws SQLException
	{

		SLControlDao slcd = new SLControlDao();
		ArrayList<SLControlModel> slControlList = null;
		slControlList = slcd.queryAll();
		JSONObject node = null;
		if (theArea != null)
		{
			boolean hasChildren=false;
			for(SLControlModel slcm : slControlList)
			{
				if(slcm.getAREA_ID() == theArea.getId())
				{
					hasChildren=true;
					break;
				}
			}
			
			node = new JSONObject();
			node.put("name", theArea.getName());

			if (hasChildren)
			{
				node.put("type", "folder");
				JSONObject slAreaNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for(SLControlModel slcm : slControlList)
				{
					if(slcm.getAREA_ID() == theArea.getId())
					{
						JSONObject slControlNode = new JSONObject();
						slControlNode.put("type", "item");
						slControlNode.put("name", slcm.getSLKONGZHI_NAME());
						slControlNode.put("SlControlID", slcm.getSLKONGZHI_ID());
						slAreaNode.put(slcm.getSLKONGZHI_ID(), slControlNode);
					}
				}

				jo.put("children", slAreaNode);
				node.put("additionalParameters", jo);
			} else
			{
				return null;
			}
		}

		return node;
	}
	
	private void getSLTree(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AreaDao areaDao=new AreaDao();
		List<Area> areaList= areaDao.display();
		
		
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
			out.flush();
			out.close();
		}
	}

	private JSONObject buildAreaNode(Area theArea) throws SQLException
	{
		SLLineDao slld2 = new SLLineDao();
		ArrayList<SLLineModel> slLineList2=null;
		slLineList2 = slld2.queryAll();
		
		JSONObject node = null;
		if (theArea != null)
		{
			boolean hasChildren=false;
			for(SLLineModel sllm2 : slLineList2)
			{
				if(sllm2.getAREA_ID() == theArea.getId())
				{
					hasChildren=true;
					break;
				}
			}
			node = new JSONObject();
			node.put("name", theArea.getName());

			if (hasChildren)
			{
				node.put("type", "folder");
				JSONObject slLineNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for(SLLineModel sllm2 : slLineList2)
				{
					if(sllm2.getAREA_ID() == theArea.getId())
					{
						slLineNode.put(sllm2.getSLLINE_ID(), buildSlLineNode(sllm2));
					}
				}

				jo.put("children", slLineNode.toString());
			//	System.out.println(slLineNode);
				node.put("additionalParameters", jo);
			//	System.out.println("node:"+node.toString());
			} else
			{
				return null;
			}
		}
		return node;
	}
	
	private JSONObject buildSlLineNode(SLLineModel sllm2) throws SQLException
	{
		SlLampDao slmd = new SlLampDao();
		ArrayList<SlLampModel> slManagerList=null;
		slManagerList = slmd.queryAll();
		JSONObject node = null;
		if (sllm2 != null)
		{
			boolean hasChildren=false;
			for(SlLampModel slmm : slManagerList)
			{
				if(slmm.getSlLine_Id() == sllm2.getSLLINE_ID())
				{
					hasChildren=true;
					break;
				}
			}
			
			node = new JSONObject();
			node.put("name", sllm2.getSLLINE_NAME());

			if (hasChildren)
			{
				node.put("type", "folder");
				JSONObject slLineNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for(SlLampModel slmm : slManagerList)
				{
					if(slmm.getSlLine_Id() == sllm2.getSLLINE_ID())
					{
						JSONObject slLampNode = new JSONObject();
						slLampNode.put("type", "item");
						slLampNode.put("name", slmm.getSlLamp_Num());
						slLampNode.put("SlLampID", slmm.getSlLamp_Id());
						slLineNode.put(slmm.getSlLamp_Id(), slLampNode);
					}
				}

				jo.put("children", slLineNode);
				node.put("additionalParameters", jo);
			//	System.out.println("sllnode:"+node.toString());
			} else
			{
				return null;
			}
		}

		return node;
	}
}
