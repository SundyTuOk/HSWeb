package com.sf.energy.report.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.report.dao.EnReportDao;
import com.sf.energy.report.model.EnReportModel;

public class EnReportServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String flag=req.getParameter("flag");
		
		if("add".equals(flag))
		{
			addEnReport(req, resp);
		}
		else if("delete".equals(flag)){
			
			deleteEnReport(req, resp);
		}
		else if("edit".equals(flag)){
			editEnReport(req, resp);
		}
/*		else if("query".equals(flag)){
		
			query(req, resp);
		} */
		else if("deletearray".equals(flag))
		{
			
			deleteEnReportArray(req, resp);
		}
		else if("loaddate".equals(flag))
		{			
			try {
				loadEnReportData(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 加载数据
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void loadEnReportData(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("EnReportPageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("EnReportPageIndex"));
		String enReportsortName=req.getParameter("enReportsortName");
		String enReportorder=req.getParameter("enReportorder");
		List<EnReportModel> enReportList=new ArrayList<EnReportModel>();
	
		EnReportDao enReportDao=new EnReportDao();
		enReportList=enReportDao.queryEnReport(enReportsortName,enReportorder,thePageCount,thePageIndex);
		
		JSONArray json = new JSONArray();
		JSONObject jototal =new JSONObject();
		
		jototal.put("totalCount", enReportDao.getTotalCount());
		json.add(jototal);
		
		for (EnReportModel eReportModel : enReportList)
		{
			JSONObject	jo = new JSONObject();
						
						
			jo.put("Energynum", eReportModel.getEnergynum());
			jo.put("Energyname", eReportModel.getEnergyname());
			jo.put("Energyunit", eReportModel.getEnergyunit());
			jo.put("Calorific", eReportModel.getCalorific());
			jo.put("Coefficient", eReportModel.getCoefficient());
			jo.put("Ismanual",eReportModel.getIsmanual());  
			jo.put("Isreport",eReportModel.getIsreport());		
			json.add(jo);
			 
		}
		resp.setContentType("text/html;charset=utf-8");  

		 PrintWriter pw = resp.getWriter();  
		 pw.print(json.toString());  
		 pw.flush();
		 pw.close();
	}
	/**
	 * 添加
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void addEnReport(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		boolean flag=false;
		int id=0;
		String addenergyName=req.getParameter("addenergyName");
		String addenergyUnit=req.getParameter("addenergyUnit");
		double addenergyCalor=Double.parseDouble(req.getParameter("addenergyCalor"));
		double addenergyCoeff=Double.parseDouble(req.getParameter("addenergyCoeff"));
		int addenergyManual=Integer.parseInt(req.getParameter("addenergyManual"));
		int addenergyReport=Integer.parseInt(req.getParameter("addenergyReport"));
		EnReportModel enReportModel=new EnReportModel();
		enReportModel.setEnergyname(addenergyName);
		enReportModel.setEnergyunit(addenergyUnit);
		enReportModel.setCalorific(addenergyCalor);
		enReportModel.setCoefficient(addenergyCoeff);
		enReportModel.setIsmanual(addenergyManual);
		enReportModel.setIsreport(addenergyReport);
		//enReportModel.setEnergynum("54645");
		EnReportDao enReportDao=new EnReportDao();
		try {
			id=enReportDao.getEnReportId();
			enReportModel.setEnergynum(Integer.toString(id));
			
			flag=enReportDao.addEnReport(enReportModel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		 json.put("addenergyNum",id);
		 json.put("addenergyName",addenergyName); 
		 json.put("addenergyUnit",addenergyUnit); 
		 json.put("addenergyCalor",addenergyCalor); 
		 json.put("addenergyCoeff",addenergyCoeff); 
		 json.put("addenergyManual",addenergyManual); 
		 json.put("addenergyReport",addenergyReport); 
		 json.put("isSuccess",isSuccess);	 
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  
	}
	
	/**
	 * 修改
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void editEnReport(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		boolean flag=false;
		String isManual="";
		String isReport="";
		
		EnReportModel enReportModel=new EnReportModel();
		int id=Integer.parseInt(req.getParameter("xznum"));
		String num=req.getParameter("xznum");
		String energyName=req.getParameter("energyName");
		String energyUnit=req.getParameter("energyUnit");
		double energyCalor=Double.parseDouble(req.getParameter("energyCalor"));
		double energyCoeff=Double.parseDouble(req.getParameter("energyCoeff"));
		int energyManual=Integer.parseInt(req.getParameter("energyManual"));
		int energyReport=Integer.parseInt(req.getParameter("energyReport"));
		
		
		enReportModel.setEnergynum(num);
		enReportModel.setEnergyname(energyName);
		enReportModel.setEnergyunit(energyUnit);
		enReportModel.setCalorific(energyCalor);
		enReportModel.setCoefficient(energyCoeff);
		enReportModel.setIsmanual(energyManual);
		enReportModel.setIsreport(energyReport);
		EnReportDao enReportDao=new EnReportDao();
		try {
			flag=enReportDao.updateEnReport(id, enReportModel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(energyManual==1)
		{
			isManual="是";
		}
		else {
			isManual="否";
		}
		
		if(energyReport==1)
		{
			isReport="是";
		}
		else 
		{
			isReport="否";
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
		 json.put("num",num); 
		 json.put("energyName",energyName); 
		 json.put("energyUnit", energyUnit);
		 json.put("energyCalor", energyCalor);
		 json.put("energyCoeff", energyCoeff);
		 json.put("energyManual", isManual);
		 json.put("energyReport", isReport);
		 json.put("isSuccess",isSuccess);	
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  
	}
	
	private void deleteEnReport(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		boolean flag=false;
		int id=Integer.parseInt(req.getParameter("id"));
	
		EnReportDao enReportDao=new EnReportDao();
		try {
			flag=enReportDao.deleteEnReport(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	//批量删除
	private void deleteEnReportArray(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		boolean flag=true;
	    boolean flag1=false;	
		 String[] idarray=req.getParameterValues("idarray[]");
		 EnReportDao enReportDao=new EnReportDao();
				for(int i=0;i<idarray.length;i++)
				{
					
					try {
						flag1=enReportDao.deleteEnReport(Integer.parseInt(idarray[i]));
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

}
