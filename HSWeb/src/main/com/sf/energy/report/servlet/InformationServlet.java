package com.sf.energy.report.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.system.dao.InformationDao;
import com.sf.energy.project.system.model.InformationModel;

public class InformationServlet extends HttpServlet
{

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
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
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		doGet(req, resp);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String method = req.getParameter("method");
		//System.out.println("method:" + method);

		if ("getInformation".equals(method))
			getInformation(req, resp);
		if ("delInformation".equals(method))
			delInformation(req, resp);
		if ("updateInformation".equals(method))
			updateInformation(req, resp);	
		if ("insertInformation".equals(method))
			insertInformation(req, resp);
		if ("getTomcatServerInfo".equals(method))
			getTomcatServerInfo(req, resp);
	}

	private void getTomcatServerInfo(HttpServletRequest req,
			HttpServletResponse resp)
	{
		//System.out.println(System.getProperty("os.name"));   
        //System.out.println(System.getProperty("os.arch"));   
        //System.out.println(System.getProperty("os.version"));   
        //System.out.println(System.getProperty("file.separator"));   
        //System.out.println(System.getProperty("path.separator"));   
        //System.out.println(System.getProperty("line.separator"));   
        //System.out.println(System.getProperty("user.name"));   
        //System.out.println(System.getProperty("user.home"));                   
        //System.out.println(System.getProperty("user.dir")); 
		
	}

	private void insertInformation(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException
	{
		String tdarea= req.getParameter("tdarea");
		String jzarea= req.getParameter("jzarea");
		String lharea= req.getParameter("lharea");
		String bkmancount= req.getParameter("bkmancount");
		String yjmancount= req.getParameter("yjmancount");
		String bsmancount= req.getParameter("bsmancount");
		String mancount= req.getParameter("mancount");
		String theTime= req.getParameter("theTime");
		
		InformationModel infoModel=new InformationModel();
		
		infoModel.setTdarea(Float.parseFloat(tdarea));
		infoModel.setArchArea(Float.parseFloat(jzarea));
		infoModel.setLharea(Float.parseFloat(lharea));
		infoModel.setBkMancount(Integer.parseInt(bkmancount));
		infoModel.setYjMancount(Integer.parseInt(yjmancount));
		infoModel.setBsMancount(Integer.parseInt(bsmancount));
		
		infoModel.setCountMan(Float.parseFloat(mancount));
		
		infoModel.setTheTime(theTime);
		
		InformationDao control=new InformationDao();
		boolean result=control.insertInformationInfo(infoModel);
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		if (result)
		{
			JSONObject json = new JSONObject();
			json.put("result", "添加成功");
			PrintWriter pw = resp.getWriter();
			//System.out.println(json.toString());
			pw.print(json.toString());
			pw.flush();
			pw.close();
		} else
		{
			JSONObject json = new JSONObject();
			json.put("result", "添加失败");
			PrintWriter pw = resp.getWriter();
			//System.out.println(json.toString());
			pw.print(json.toString());
			pw.flush();
			pw.close();
		}
	}

	private void updateInformation(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException
	{
		String id= req.getParameter("id");
		String tdarea= req.getParameter("tdarea");
		String jzarea= req.getParameter("jzarea");
		String lharea= req.getParameter("lharea");
		String bkmancount= req.getParameter("bkmancount");
		String yjmancount= req.getParameter("yjmancount");
		String bsmancount= req.getParameter("bsmancount");
		String mancount= req.getParameter("mancount");
		
		InformationModel infoModel=new InformationModel();
		
		infoModel.setInfomation_id(Integer.parseInt(id));
		infoModel.setTdarea(Float.parseFloat(tdarea));
		infoModel.setArchArea(Float.parseFloat(jzarea));
		infoModel.setLharea(Float.parseFloat(lharea));
		infoModel.setBkMancount(Integer.parseInt(bkmancount));
		infoModel.setYjMancount(Integer.parseInt(yjmancount));
		infoModel.setBsMancount(Integer.parseInt(bsmancount));
		
		infoModel.setCountMan(Integer.parseInt(mancount));
		
		InformationDao control=new InformationDao();
		boolean result=control.updateInformationInfo(infoModel);
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		if (result)
		{
			JSONObject json = new JSONObject();
			json.put("result", "更新成功");
			PrintWriter pw = resp.getWriter();
			//System.out.println(json.toString());
			pw.print(json.toString());
		} else
		{
			JSONObject json = new JSONObject();
			json.put("result", "更新失败");
			PrintWriter pw = resp.getWriter();
			//System.out.println(json.toString());
			pw.print(json.toString());
		}
	}

	private void delInformation(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String id= req.getParameter("id");
		InformationDao control=new InformationDao();
		boolean result=control.delInformationInfo(id);
		
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		if (result)
		{
			JSONObject json = new JSONObject();
			json.put("result", "删除成功");
			PrintWriter pw = resp.getWriter();
			//System.out.println(json.toString());
			pw.print(json.toString());
		} else
		{
			JSONObject json = new JSONObject();
			json.put("result", "删除失败");
			PrintWriter pw = resp.getWriter();
			//System.out.println(json.toString());
			pw.print(json.toString());
		}
		
	}

	private void getInformation(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		InformationDao control=new InformationDao();
		List<InformationModel> list=control.getInformationInfo();
		
		JSONArray json=new JSONArray();
		JSONObject jo=null;
		for(InformationModel model:list)
		{
			jo=new JSONObject();
			int id=model.getInfomation_id();
			float tdArea=model.getTdarea();
			float jzArea=model.getArchArea();
			float lhArea=model.getLharea();
			int bkManCount=model.getBkMancount();
			int yjManCount=model.getYjMancount();
			int bsManCount=model.getBsMancount();
			float CountMan=model.getCountMan();
			String theTime=model.getTheTime();
			jo.put("id", id);
			jo.put("tdArea", tdArea);
			jo.put("jzArea", jzArea);
			jo.put("lhArea", lhArea);
			
			jo.put("bkManCount", bkManCount);
			jo.put("yjManCount", yjManCount);
			jo.put("bsManCount", bsManCount);
			jo.put("CountMan", CountMan);
			
			jo.put("theTime", theTime);
			json.add(jo);
		}
		
		//System.out.println(json.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
}
