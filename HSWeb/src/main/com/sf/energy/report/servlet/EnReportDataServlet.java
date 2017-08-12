package com.sf.energy.report.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.sf.energy.report.dao.EnReportDataDao;
import com.sf.energy.report.model.EnReportDataModel;
import com.sf.energy.report.model.EnReportModel;

public class EnReportDataServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String flag=req.getParameter("flag");
		if("add".equals(flag))
		{
			addEnReportData(req,resp);
		}
	
		
	}
	
	private void addEnReportData(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String addenergdataNum=req.getParameter("addenergdataNum");
		String addenergdataName=req.getParameter("addenergdataName");
		String addenergdataType=req.getParameter("addenergdataType");
		String addenergdataFz=req.getParameter("addenergdataFz");
		String addenergdataPhone=req.getParameter("addenergdataPhone");
		String addenergdataState=req.getParameter("addenergdataState");
	    int id=0;
	
		
		EnReportDataModel enReportDataModel=new EnReportDataModel();
		enReportDataModel.setEnReportData_num(addenergdataNum);
		enReportDataModel.setEnReportData_name(addenergdataName);
		enReportDataModel.setEnReportData_style(addenergdataType);
		enReportDataModel.setEnReportData_remark(addenergdataState);
		
		EnReportDataDao enReportDataDao=new EnReportDataDao();
		try {
		 id=enReportDataDao.getEnReportDataId();
			enReportDataModel.setEnReportData_id(id);
			enReportDataDao.addEnReportData(enReportDataModel);		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("id",id);
		 json.put("addenergdataNum",addenergdataNum);
		 json.put("addenergdataName",addenergdataName); 
		 json.put("addenergdataType",addenergdataType); 
		 json.put("addenergdataState",addenergdataState);
		 
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  
	}
	
}
