package com.sf.energy.expert.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.expert.dao.EnAuditArcDao;
import com.sf.energy.expert.model.AreaArcModel;
import com.sf.energy.expert.model.EnAuditArcModel;
import com.sf.energy.project.PDRoom.dao.PD_PartDao;
import com.sf.energy.project.PDRoom.dao.PD_PartInfo02Dao;
import com.sf.energy.project.PDRoom.model.PD_PartModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.report.model.EnReportDataHisModel;

public class EnAuditArcServlet extends HttpServlet{

	DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat df2 = new SimpleDateFormat("yyyy-MM");
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String flag=req.getParameter("flag");


		if("edit".equals(flag))
		{
			try {
				editEnAuditArc(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		else if("delete".equals(flag))
		{
			try {
				deleteEnAuditArc(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("deletearray".equals(flag))
		{

			deleteEnAuditArcArray(req, resp);
		}
		else if("selectArc".equals(flag))
		{

			try {
				selectArc(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("add".equals(flag))
		{
			try {
				addEnAuditArc(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if("loaddate".equals(flag))
		{
			try {
				loadDataEnAuditArc(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if("reportChat".equals(flag))
		{
			try {
				getReportChat(req, resp);
			} catch (Exception e) {
				// 
				e.printStackTrace();
			}
		}
	}

	/**
	 * 得到报告饼图
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 * @throws Exception 
	 */
	private void getReportChat(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, Exception
	{
		List<String> reportList=new ArrayList<String>();
		List<String> kongTiaoList=new ArrayList<String>();
		String arcId=req.getParameter("arcId");
		String arcName=req.getParameter("arcName");
		//String queryYear = req.getParameter("year");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		
		String queryS = startDate;
		String queryE = endDate;
		EnAuditArcDao enAuditArcDao=new EnAuditArcDao();
		String subName=queryS+" 至 "+queryE;
		//		System.out.println("arcId:"+arcId);
		reportList=enAuditArcDao.getReportXml(arcId,arcName,subName,queryS,queryE);

		String root=enAuditArcDao.getRoot();
		kongTiaoList=enAuditArcDao.kongTiao(arcId,queryS,queryE);

		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 		 
		JSONArray  jo=JSONArray.fromObject(reportList);			
		json.put("jo",jo);

		JSONArray  kt=JSONArray.fromObject(kongTiaoList);			
		json.put("kt",kt);
		
		float total = 0;
		total = new ArchitectureDao().queryArchMonEnTotal(Integer.parseInt(arcId),queryS,queryE);
		json.put("total", total);
		PrintWriter pw = resp.getWriter();  
		//System.out.println(json.toString());
		pw.print(json.toString());  
		pw.flush();
		pw.close();	

	}



	/**
	 * 增加
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void addEnAuditArc(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		boolean flag=false;
		String EnAuditArcAddArc=req.getParameter("EnAuditArcAddArc");
		int Arc_parentAddID=Integer.parseInt(req.getParameter("Arc_parentAddID"));
		String EnAuditArcAddYear=req.getParameter("EnAuditArcAddYear");
		String EnAuditArcAddStartTime=req.getParameter("EnAuditArcAddStartTime");
		String EnAuditArcAddEndTime=req.getParameter("EnAuditArcAddEndTime");
		String EnAuditArcAddNum=req.getParameter("EnAuditArcAddNum");
		String EnAuditArcAddName=req.getParameter("EnAuditArcAddName");
		String EnAuditArcAddEmployers=req.getParameter("EnAuditArcAddEmployers");
		String EnAuditArcAddFZMan=req.getParameter("EnAuditArcAddFZMan");
		String EnAuditArcAddSHMan=req.getParameter("EnAuditArcAddSHMan");
		String EnAuditArcAddTime=req.getParameter("EnAuditArcAddTime");

		EnAuditArcDao enAuditArcDao=new EnAuditArcDao();
		int id=enAuditArcDao.getEnAuditArcId();

		EnAuditArcModel enAuditArcModel=new EnAuditArcModel();
		enAuditArcModel.setEnAuditArc_ID(id);
		enAuditArcModel.setArchitecture_Name(EnAuditArcAddArc);
		enAuditArcModel.setArchitecture_ID(Arc_parentAddID);
		enAuditArcModel.setEnAuditArc_Year(EnAuditArcAddYear);
		enAuditArcModel.setEnAuditArc_StartTime(EnAuditArcAddStartTime);
		enAuditArcModel.setEnAuditArc_EndTime(EnAuditArcAddEndTime);
		enAuditArcModel.setEnAuditArc_Num(EnAuditArcAddNum);
		enAuditArcModel.setEnAuditArc_Name(EnAuditArcAddName);
		enAuditArcModel.setEnAuditArc_Employers(EnAuditArcAddEmployers);
		enAuditArcModel.setEnAuditArc_FZMan(EnAuditArcAddFZMan);
		enAuditArcModel.setEnAuditArc_SHMan(EnAuditArcAddSHMan);
		enAuditArcModel.setEnAuditArc_Time(EnAuditArcAddTime);

		flag=enAuditArcDao.addEnAuditArc(enAuditArcModel);
		String isSuccess="";
		if(flag==true)
		{
			isSuccess="success";
		}
		else {
			isSuccess="failure";
		}		   
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		json.put("isSuccess",isSuccess);	 
		json.put("a","a");
		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString());  
		pw.flush();
		pw.close();	

	}






	/**
	 * 修改
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws IOException 
	 */


	private void editEnAuditArc(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		boolean flag=false;
		int id=Integer.parseInt(req.getParameter("EnAuditArcEditID"));
		String EnAuditArcEditNum=req.getParameter("EnAuditArcEditNum");
		String EnAuditArcEditName=req.getParameter("EnAuditArcEditName");
		String EnAuditArcEditYear=req.getParameter("EnAuditArcEditYear");
		String EnAuditArcEditEmployers=req.getParameter("EnAuditArcEditEmployers");
		String EnAuditArcEditArc=req.getParameter("EnAuditArcEditArc");
		String EnAuditArcEditFZMan=req.getParameter("EnAuditArcEditFZMan");
		String EnAuditArcEditSHMan=req.getParameter("EnAuditArcEditSHMan");
		String EnAuditArcEditTime=req.getParameter("EnAuditArcEditTime");
		String EnAuditArcEditStartTime=req.getParameter("EnAuditArcEditStartTime");
		String EnAuditArcEditEndTime=req.getParameter("EnAuditArcEditEndTime");
		int Arc_parentEditID=Integer.parseInt(req.getParameter("Arc_parentEditID"));


		/*	    String[] arrayStr =new String[]{};

	     arrayStr = EnAuditArcEditStartTime.split("/");
	     EnAuditArcEditStartTime=arrayStr[2]+"年";

		//System.out.println(EnAuditArcEditStartTime); */


		EnAuditArcModel enAuditArcModel=new EnAuditArcModel();
		enAuditArcModel.setEnAuditArc_ID(id);
		enAuditArcModel.setEnAuditArc_Num(EnAuditArcEditNum);
		enAuditArcModel.setEnAuditArc_Name(EnAuditArcEditName);
		enAuditArcModel.setEnAuditArc_Year(EnAuditArcEditYear);
		enAuditArcModel.setEnAuditArc_Employers(EnAuditArcEditEmployers);
		enAuditArcModel.setEnAuditArc_FZMan(EnAuditArcEditFZMan);
		enAuditArcModel.setEnAuditArc_SHMan(EnAuditArcEditSHMan);
		enAuditArcModel.setEnAuditArc_Time(EnAuditArcEditTime);
		enAuditArcModel.setEnAuditArc_StartTime(EnAuditArcEditStartTime);
		enAuditArcModel.setEnAuditArc_EndTime(EnAuditArcEditEndTime);
		enAuditArcModel.setArchitecture_ID(Arc_parentEditID);

		EnAuditArcDao enAuditArcDao=new EnAuditArcDao();
		flag=enAuditArcDao.editEnAuditArc(enAuditArcModel);
		String isSuccess="";
		if(flag==true)
		{
			isSuccess="success";
		}
		else {
			isSuccess="failure";
		}		  
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		json.put("isSuccess",isSuccess);	 
		json.put("a","a");
		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString());  
		pw.flush();
		pw.close();	

	}
	/**
	 * 删除
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException 
	 */
	private void deleteEnAuditArc(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		boolean flag=false;
		int id=0;
		id=Integer.parseInt(req.getParameter("id"));
		EnAuditArcDao enAuditArcDao=new EnAuditArcDao();
		flag=enAuditArcDao.deleteEnAuditArc(id);

		String isSuccess="";
		if(flag==true)
		{
			isSuccess="success";
		}
		else {
			isSuccess="failure";
		}		  
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		json.put("isSuccess",isSuccess);	 
		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString());  
		pw.flush();
		pw.close();	

	}

	/**
	 * 批量删除
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void deleteEnAuditArcArray(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		boolean flag=true;
		boolean flag1=false;
		String[] idarray=req.getParameterValues("idarray[]");
		EnAuditArcDao enAuditArcDao=new EnAuditArcDao();
		for(int i=0;i<idarray.length;i++)
		{

			try {
				flag1=enAuditArcDao.deleteEnAuditArc(Integer.parseInt(idarray[i]));	
				flag=flag&flag1;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String isSuccess="";
		if(flag==true)
		{
			isSuccess="success";
		}
		else {
			isSuccess="failure";
		}
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		json.put("isSuccess",isSuccess);	 
		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString());  
		pw.flush();
		pw.close();	
	}

	/**
	 * 导入数据
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void loadDataEnAuditArc(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("EnAuditArcPageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("EnAuditArcPageIndex"));
		String arcSelectID=req.getParameter("arcSelectID");
		String arcSelectArea=req.getParameter("arcSelectArea");
		String EnAuditArcsortName=req.getParameter("EnAuditArcsortName");
		String EnAuditArcorder=req.getParameter("EnAuditArcorder");

		EnAuditArcDao enAuditArcDao=new EnAuditArcDao();
		List<EnAuditArcModel> list=new ArrayList<EnAuditArcModel>();
		list=enAuditArcDao.querEnAuditArcID(EnAuditArcsortName,EnAuditArcorder,arcSelectArea,arcSelectID,thePageCount,thePageIndex);

		JSONArray json = new JSONArray();
		JSONObject jototal =new JSONObject();
		jototal.put("totalCount", enAuditArcDao.getTotalCount());
		json.add(jototal);

		for (EnAuditArcModel eAuditArcModel : list)
		{
			JSONObject	jo = new JSONObject();



			jo.put("enAuditArc_ID", eAuditArcModel.getEnAuditArc_ID());
			jo.put("enAuditArc_Num", eAuditArcModel.getEnAuditArc_Num());
			jo.put("enAuditArc_Name", eAuditArcModel.getEnAuditArc_Name());
			jo.put("enAuditArc_Year", eAuditArcModel.getEnAuditArc_Year());
			jo.put("enAuditArc_Employers", eAuditArcModel.getEnAuditArc_Employers());
			jo.put("architecture_Num",eAuditArcModel.getArchitecture_Num());
			jo.put("architecture_Name", eAuditArcModel.getArchitecture_Name());	

			jo.put("enAuditArc_FZMan", eAuditArcModel.getEnAuditArc_FZMan());
			jo.put("enAuditArc_SHMan", eAuditArcModel.getEnAuditArc_SHMan());
			jo.put("enAuditArc_Time", eAuditArcModel.getEnAuditArc_Time());
			jo.put("enAuditArc_StartTime",eAuditArcModel.getEnAuditArc_StartTime());
			jo.put("enAuditArc_EndTime", eAuditArcModel.getEnAuditArc_EndTime());
			jo.put("architecture_ID", eAuditArcModel.getArchitecture_ID());

			jo.put("ARCHITECTURE_AREA",eAuditArcModel.getARCHITECTURE_AREA());
			jo.put("ARCHITECTURE_AIRCONDITION",eAuditArcModel.getARCHITECTURE_AIRCONDITION());
			jo.put("ARCHITECTURE_TIME",eAuditArcModel.getARCHITECTURE_TIME());
			jo.put("ARCHITECTURE_STYLE",eAuditArcModel.getARCHITECTURE_STYLE());

			json.add(jo);
		}

		resp.setContentType("text/html;charset=utf-8");  

		PrintWriter pw = resp.getWriter();  
		pw.print(json.toString());  
		pw.flush();
		pw.close();
	}

	/**
	 * 选择建筑
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void selectArc(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{

		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		JSONArray areaArray = new JSONArray();  
		JSONArray arcArray = new JSONArray();  
		JSONObject member = null;  
		int Area_ID=0;
		int Architecture_ID=0;
		String Area_Name="";
		String Architecture_Name="";

		List<AreaArcModel> arealist=new ArrayList<AreaArcModel>();
		List<AreaArcModel> arclist=new ArrayList<AreaArcModel>();
		AreaArcModel areaArcModel=new AreaArcModel();


		EnAuditArcDao enAuditArcDao=new EnAuditArcDao();

		arealist=enAuditArcDao.getArea();

		arclist=enAuditArcDao.getArc();

		Iterator<AreaArcModel> areaIt=arealist.iterator(); 
		while(areaIt.hasNext())
		{
			member=new JSONObject();
			areaArcModel=areaIt.next();
			Area_ID=areaArcModel.getArea_ID();
			Area_Name=areaArcModel.getArea_Name();


			member.put("Area_ID",Area_ID);
			member.put("Area_Name",Area_Name);		  
			areaArray.add(member);
		}

		json.put("areaArray", areaArray);  



		Iterator<AreaArcModel> arcIt=arclist.iterator(); 
		while(arcIt.hasNext())
		{
			member=new JSONObject();
			areaArcModel=arcIt.next();
			Architecture_ID=areaArcModel.getArchitecture_ID();
			Architecture_Name=areaArcModel.getArchitecture_Name();		
			Area_ID=areaArcModel.getArea_ID();

			member.put("Architecture_ID",Architecture_ID);
			member.put("Architecture_Name",Architecture_Name);	
			member.put("Area_ID",Area_ID);
			arcArray.add(member);
		}
		json.put("arcArray", arcArray); 
		String root=enAuditArcDao.getRoot();
		json.put("root",root); 
		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString());  

	}

}
