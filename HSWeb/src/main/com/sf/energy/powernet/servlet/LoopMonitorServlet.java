package com.sf.energy.powernet.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.powernet.dao.AmMeterPDDataDao;
import com.sf.energy.powernet.model.AmMeterPDDatasModel;
import com.sf.energy.project.PDRoom.dao.PD_PartDao;
import com.sf.energy.project.PDRoom.model.PD_PartModel;
public class LoopMonitorServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String flag=req.getParameter("flag");
	       
			
			if("selectPD".equals(flag))
			{
				try {
					selectPD_Part(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if("queyHL".equals(flag))
			{
				try {
					MakeHLGraph(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	
	/**
	 * 选择列表
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void selectPD_Part(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		JSONArray array = new JSONArray();  
		JSONObject member = null;  
		int id=0;
		int parent_id=0;
		String name="";
	
		String PartStyle_ID="";
		List<PD_PartModel> list=new ArrayList<PD_PartModel>();
		PD_PartModel partModel=new PD_PartModel();
		 PD_PartDao partDao=new PD_PartDao();
		 list=partDao.queryPD_PartSelect();
		 Iterator<PD_PartModel> partIt=list.iterator(); 
		 while(partIt.hasNext())
		  {
			 member=new JSONObject();
		   partModel=partIt.next();
		   id=partModel.getPart_ID();
		   parent_id=partModel.getPart_Parent();
		   name=partModel.getPart_Name();
		
		   PartStyle_ID=partModel.getPartStyle_ID();
		   
		
		   member.put("id",id);
		   member.put("parent_id",parent_id);
		   member.put("name",name);
		  
		   member.put("PartStyle_ID",PartStyle_ID);
		   array.add(member);
		  }
		 String root=partDao.getRoot();
		 json.put("root", root);
		 json.put("jsonArray", array);  
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString()); 
	}
      
	
	
	private void MakeHLGraph(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		resp.setContentType("text/html;charset=utf-8");  
		int id=Integer.parseInt(req.getParameter("id"));		
		String glstyle=req.getParameter("glstyle");
		String startDate=req.getParameter("startDate");
	    String LoopName=req.getParameter("LoopMonitorHLName");
	 
	    
	    String[] arrayTime =new String[]{};
	    arrayTime = startDate.split("-");
	    String time=arrayTime[0]+"年"+arrayTime[1]+"月"+arrayTime[2]+"日";
	   
	    AmMeterPDDataDao amMeterPDDataDao=new AmMeterPDDataDao();
	    String str="";
	    
	    
	    List<AmMeterPDDatasModel> list=new ArrayList<AmMeterPDDatasModel>();
	    AmMeterPDDatasModel amMeterPDDatasModel=new AmMeterPDDatasModel();
	    
	    JSONObject json = new JSONObject(); 
		JSONArray array = new JSONArray();  
		JSONObject member = null;  
	    //有功功率
	    if("PowerY".equals(glstyle))
	    {
	    	str="PowerZY,PowerAY,PowerBY,PowerCY";
	    	list=amMeterPDDataDao.queryAmMeterPDDatasModelE(id,startDate,str,glstyle);
	    	Iterator<AmMeterPDDatasModel> it=list.iterator();
	    	while(it.hasNext())
	    	{
	    		amMeterPDDatasModel=it.next();
	    		member=new JSONObject();	    		
	    		
	    		member.put("valueTime", amMeterPDDatasModel.getValueTime());
	    		member.put("powerAY", amMeterPDDatasModel.getPowerAY());
	    		member.put("powerBY", amMeterPDDatasModel.getPowerBY());
	    		member.put("powerCY", amMeterPDDatasModel.getPowerCY());
	    		member.put("powerZY", amMeterPDDatasModel.getPowerZY());
	    		array.add(member);
	    		
	    	}
	    }
	    //无功功率
	    else if("PowerW".equals(glstyle))
	    {
	    	str="PowerZW,PowerAW,PowerBW,PowerCW";
	    	list=amMeterPDDataDao.queryAmMeterPDDatasModelE(id,startDate,str,glstyle);
	    	Iterator<AmMeterPDDatasModel> it=list.iterator();
	    	while(it.hasNext())
	    	{
	    		amMeterPDDatasModel=it.next();
	    		member=new JSONObject();	    		
	    		
	    		member.put("valueTime", amMeterPDDatasModel.getValueTime());
	    		member.put("powerAW", amMeterPDDatasModel.getPowerAW());
	    		member.put("powerBW", amMeterPDDatasModel.getPowerBW());
	    		member.put("powerCW", amMeterPDDatasModel.getPowerCW());
	    		member.put("powerZW", amMeterPDDatasModel.getPowerZW());
	    		array.add(member);
	    		
	    	}
	    }
	    //功率因数
	    else if("PowerFactor".equals(glstyle))
	    {
	    	str="PowerFactorZ,PowerFactorA,PowerFactorB,PowerFactorC";
	    	list=amMeterPDDataDao.queryAmMeterPDDatasModelE(id,startDate,str,glstyle);
	    	
	    	Iterator<AmMeterPDDatasModel> it=list.iterator();
	    	while(it.hasNext())
	    	{
	    		amMeterPDDatasModel=it.next();
	    		member=new JSONObject();	    		
	    		
	    		member.put("valueTime", amMeterPDDatasModel.getValueTime());
	    		member.put("powerFactorA", amMeterPDDatasModel.getPowerFactorA());
	    		member.put("powerFactorB", amMeterPDDatasModel.getPowerFactorB());
	    		member.put("powerFactorC", amMeterPDDatasModel.getPowerFactorC());
	    		member.put("powerFactorZ", amMeterPDDatasModel.getPowerFactorZ());
	    		array.add(member);
	    		
	    	}
	    }
	    //电压
	    else if("Voltage".equals(glstyle))
	    {
	    	str="VoltageA,VoltageB,VoltageC";
	    	list=amMeterPDDataDao.queryAmMeterPDDatasModelE(id,startDate,str,glstyle);
	    	
	    	Iterator<AmMeterPDDatasModel> it=list.iterator();
	    	while(it.hasNext())
	    	{
	    		amMeterPDDatasModel=it.next();
	    		member=new JSONObject();	    		
	    		
	    		member.put("valueTime", amMeterPDDatasModel.getValueTime());
	    		member.put("voltageA", amMeterPDDatasModel.getVoltageA());
	    		member.put("voltageB", amMeterPDDatasModel.getVoltageB());
	    		member.put("voltageC", amMeterPDDatasModel.getVoltageC());
	    	
	    		array.add(member);
	    		
	    	}
	    }
	    //电流
	    else if("Current".equals(glstyle))
	    {
	    	str="CurrentA,CurrentB,CurrentC,Current0";
	    	list=amMeterPDDataDao.queryAmMeterPDDatasModelE(id,startDate,str,glstyle);
	    	
	    	Iterator<AmMeterPDDatasModel> it=list.iterator();
	    	while(it.hasNext())
	    	{
	    		amMeterPDDatasModel=it.next();
	    		member=new JSONObject();	    		
	    		
	    		member.put("valueTime", amMeterPDDatasModel.getValueTime());
	    		member.put("current0", amMeterPDDatasModel.getCurrent0());
	    		member.put("currentA", amMeterPDDatasModel.getCurrentA());
	    		member.put("currentB", amMeterPDDatasModel.getCurrentB());
	    		member.put("currentC", amMeterPDDatasModel.getCurrentC());
	    	
	    		array.add(member);
	    		
	    	}
	    }	  
	    
	    //list转为JSON数组
	     // JSONArray arry=JSONArray.fromObject(list);
	      json.put("jsonArray", array);
	      json.put("LoopName", LoopName);
	      json.put("glstyle",glstyle);
	      json.put("time",time);
	      
	  
	      PrintWriter pw = resp.getWriter();   
		  pw.print(json.toString());
		  
	   
		
	}
}
