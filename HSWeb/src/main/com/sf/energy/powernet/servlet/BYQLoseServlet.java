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

public class BYQLoseServlet extends HttpServlet
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

		if ("getBYQLoseDataInfo".equals(method))
			getBYQLoseDataInfo(req, resp);
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

	private void getBYQLoseDataInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		LoseLineDao control=new LoseLineDao();
		String startTime = req.getParameter("startTime").trim();
		String endTime = req.getParameter("endTime").trim();
		String SelectBYQLoseList = req.getParameter("SelectBYQLoseList");
		
		
		JSONArray json=new JSONArray();
		JSONArray jsonInList=new JSONArray();
		JSONArray jsonOutList=new JSONArray();
		String[] BYQRoom=SelectBYQLoseList.split(",");//配电房列表
		int InLineCount=0;//入线总数
		float InLineValue=0;
		int OutLineCount=0;//出线总数
		float OutLineValue=0;
		
		

		for(int i=0;i<BYQRoom.length;i++)
		{
			String BYQRoomID=BYQRoom[i];
			if(BYQRoomID.equals("'0'"))
				break;
			String BYQRoomName= control.getBYQName(BYQRoomID);
			//获取变压器入线信息
			String InLineString=control.getBYQInLineID(BYQRoomID);
			if(!InLineString.equals(""))
			{
				JSONObject joIn = new JSONObject();
				joIn=control.getInfo(Integer.parseInt( InLineString),startTime,endTime);
				joIn.put("BYQRoomName", BYQRoomName);
				jsonInList.add(joIn);
				InLineCount++;
				 if(!"--".equals(joIn.get("useValue").toString()))
					 InLineValue+=Float.parseFloat(joIn.get("useValue").toString());
				 
			}
			//获取变压器出线信息
			String OutLineString="";
			OutLineString=control. getBYQOutLineID(BYQRoomID,OutLineString);
			String[] OutLineList=OutLineString.split(",");//配电房列表
			for(int j=0;j<OutLineList.length;j++)
			{
				String OutLineID=OutLineList[j];
				
				if(!OutLineID.equals(""))
				{
					JSONObject joOut = new JSONObject();
					joOut=control.getInfo(Integer.parseInt( OutLineID),startTime,endTime);
					joOut.put("BYQRoomName", BYQRoomName);
					jsonOutList.add(joOut);
					
					OutLineCount++;
					 if(!"--".equals(joOut.get("useValue").toString()))
						 OutLineValue+=Float.parseFloat(joOut.get("useValue").toString());
				}
			}
		}
		JSONObject CountInfo=new JSONObject();
		 float loseValue=InLineValue-OutLineValue;
		 CountInfo.put("InLineCount", InLineCount);
		 CountInfo.put("InLineValue", InLineValue);
		 CountInfo.put("OutLineCount", OutLineCount);
		 CountInfo.put("OutLineValue", OutLineValue);
		 
		 CountInfo.put("loseValue", loseValue);
		if(InLineValue==0)
		{
			CountInfo.put("loselV", "--");
		}
		else
		{
			float loselV=loseValue/InLineValue*100;
			CountInfo.put("loselV", loselV);
		}
		CountInfo.put("InList", jsonInList);
		CountInfo.put("OutList", jsonOutList);
		//json.add(CountInfo);
		
		
		
//		System.out.println(json.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(CountInfo.toString());
		out.flush();
		out.close();
	}



}
