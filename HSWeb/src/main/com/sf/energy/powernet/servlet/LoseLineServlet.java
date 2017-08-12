package com.sf.energy.powernet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsmpp.bean.OptionalParameter.Int;

import com.sf.energy.powernet.dao.LoseLineDao;

public class LoseLineServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(req, resp);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		doGet(req, resp);
	}
	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String method = req.getParameter("method");
//		//System.out.println("method:" + method);

		if ("getDataInfo".equals(method))
			getDataInfo(req, resp);
		else 	if ("getChildDataInfo".equals(method))
			getChildDataInfo(req, resp);
		
	}

	private void getChildDataInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		LoseLineDao control=new LoseLineDao();
		String parentID = req.getParameter("partID").trim();
		String ChildList = req.getParameter("ChildList").trim();
		String startTime = req.getParameter("startTime").trim();
		String endTime = req.getParameter("endTime").trim();
		String[] child=ChildList.split(",");

		JSONArray json=new JSONArray();
		
		for(int i=0;i<child.length;i++)
		{
			JSONObject joInfo=null;
			if("".equals(child[i]) || child[i]==null)
				continue;
			else{
				int	 pID=Integer.parseInt(child[i]);
			joInfo=control.getInfo(pID, startTime, endTime);
			json.add(joInfo);
			}
		}

		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getDataInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		LoseLineDao control=new LoseLineDao();
		Integer thePageCount = Integer.parseInt(req
				.getParameter("loseLinePageCount"));
		Integer thePageIndex = Integer.parseInt(req
				.getParameter("loseLinePageIndex"));
		
		String parentID = req.getParameter("parentID").trim();
		String startTime = req.getParameter("startTime").trim();
		String endTime = req.getParameter("endTime").trim();
		
//		//System.out.println(parentID + "  " + startTime + " " + endTime);
		JSONArray json=new JSONArray();
		if("0".equals(parentID))
		{
			//json=getfirstInfo(parentID,startTime,endTime,thePageCount,thePageIndex);
			json=getfirstInfoNew(parentID,startTime,endTime,thePageCount,thePageIndex);
		}
		else
			json=getOneInfo(parentID,startTime,endTime);
		
//		//System.out.println(json.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}



	private JSONArray getOneInfo(String parentID, String startTime, String endTime) throws SQLException
	{
		LoseLineDao control=new LoseLineDao();
	//获得所有的孩子ID
	String children=control.getChildren(parentID);
	String[] child=children.split(",");
	
	JSONArray json=new JSONArray();
	JSONObject countjo=new JSONObject();
	//计算总数
	countjo.put("totalCount", "1");
	int meterCount=0;
	countjo.put("meterCount", "0");

	
	//查询父亲的信息，生成一个jo 加入json
	int pID=Integer.parseInt(parentID);
	JSONObject parentjo=new JSONObject();
	
	parentjo=control.getInfo(pID, startTime, endTime);
	meterCount++;
	countjo.put("meterCount", meterCount);

	json.add(parentjo);
	float totalValue=0;
	if(!"--".equals(parentjo.get("useValue").toString()))
		totalValue=Float.parseFloat(parentjo.get("useValue").toString());
	//获得该父亲下面的所有的孩子的信息
	float childrenValue=0;
	for(int i=0;i<child.length;i++)
	{
		JSONObject joInfo=null;
		if("".equals(child[i]) || child[i]==null)
			continue;
		else{
		pID=Integer.parseInt(child[i]);
		joInfo=control.getInfo(pID, startTime, endTime);
		 if(!"--".equals(joInfo.get("useValue").toString()))
			 childrenValue+=Float.parseFloat(joInfo.get("useValue").toString());
		 
		json.add(joInfo);
		meterCount++;
		countjo.put("meterCount", meterCount);
		
		}
	}
	
	//处理表下端的数据
	JSONObject jo=new JSONObject();
	
	jo.put("ChildCount", child.length);
	jo.put("childrenValue", childrenValue);
	float loseValue=totalValue-childrenValue;
	jo.put("loseValue", loseValue);
	if(totalValue==0)
	{
		jo.put("loselV", "--");
	}
	else
	{
		float loselV=loseValue/totalValue*100;
		jo.put("loselV", loselV);
	}
	json.add(jo);
	
	json.add(countjo);
		
	return json;
	}
	private JSONArray getOneInfoNew(String parentID, String startTime, String endTime) throws SQLException
	{
		LoseLineDao control=new LoseLineDao();
	//获得所有的孩子ID
	String children=control.getChildren(parentID);
	String[] child=children.split(",");
	
	JSONArray json=new JSONArray();
	
	//JSONObject CLineInfo=new JSONObject();//总回路信息
	
	
	//查询父亲的信息，生成一个jo 加入json
	int pID=Integer.parseInt(parentID);
	JSONObject parentjo=new JSONObject();
	
	//jo.put("partID", parentID);
	//jo.put("partName", partName);
	//jo.put("macAddress", macAddress);
	//jo.put("beilv", beilv);
	//jo.put("startTime",startTime );
	//jo.put("startValue", startValue);
	//jo.put("endTime", endTime);
	//jo.put("endValue", endValue);
	//jo.put("useValue", totalValue);
	parentjo=control.getInfo(pID, startTime, endTime);
	json.add(parentjo);//总回路
	float totalValue=0;
	if(!"--".equals(parentjo.get("useValue").toString()))
		totalValue=Float.parseFloat(parentjo.get("useValue").toString());
	//获得该父亲下面的所有的孩子的信息
	float childrenValue=0;
	for(int i=0;i<child.length;i++)
	{
		JSONObject joInfo=null;
		if("".equals(child[i]) || child[i]==null)
			continue;
		else{
		pID=Integer.parseInt(child[i]);
		joInfo=control.getInfo(pID, startTime, endTime);
		 if(!"--".equals(joInfo.get("useValue").toString()))
			 childrenValue+=Float.parseFloat(joInfo.get("useValue").toString());
		}
	}
	//支路合计
		
	//处理表下端的数据
	JSONObject jo=new JSONObject();
	jo.put("ChildCount", child.length);
	jo.put("ChildList", children);
	jo.put("childrenValue", childrenValue);
	float loseValue=totalValue-childrenValue;
	jo.put("loseValue", loseValue);
	if(totalValue==0)
	{
		jo.put("loselV", "--");
	}
	else
	{
		float loselV=loseValue/totalValue*100;
		jo.put("loselV", loselV);
	}
	json.add(jo);
		
	return json;
	}

	private JSONArray getfirstInfoNew(String parentID, String startTime, String endTime,  int PageCount,int PageIndex) throws SQLException
	{
	
		JSONArray json=new JSONArray();
		JSONObject jo=null;
		
		LoseLineDao control=new LoseLineDao();
		String children=control.getChildren(parentID);
		String[] child=children.split(",");
		
		jo=new JSONObject();
		jo.put("totalCount", child.length);
		json.add(jo);

		int j=PageCount*PageIndex;
		int count=0;
		for(int i=j;i<child.length;i++)
		{
			JSONArray jsonModel=new JSONArray();
			jsonModel=getOneInfoNew(child[i], startTime, endTime);
			json.add(jsonModel);
			count++;
			if(count>=PageCount)
			{
				break;
			}
		}
		return json;
	}


	private JSONArray getfirstInfo(String parentID,String startTime, String endTime,  int PageCount,int PageIndex) throws SQLException
	{
		JSONArray json=new JSONArray();
		JSONObject jo=null;
		
		LoseLineDao control=new LoseLineDao();
		String children=control.getChildren(parentID);
		String[] child=children.split(",");
		
		jo=new JSONObject();
		jo.put("totalCount", child.length);
		json.add(jo);
		
	
		
		int j=PageCount*PageIndex;
		int count=0;
		for(int i=j;i<child.length;i++)
		{
			JSONArray jsonModel=new JSONArray();
			jsonModel=getOneInfo(child[i], startTime, endTime);
			json.add(jsonModel);
			count++;
			if(count>=PageCount)
			{
				break;
			}
		}
		return json;
		
	}

}
