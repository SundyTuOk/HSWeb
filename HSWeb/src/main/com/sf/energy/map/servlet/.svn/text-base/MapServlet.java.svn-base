package com.sf.energy.map.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.sf.energy.map.dao.AmMeterDao;
import com.sf.energy.map.dao.ArchitrctureDao;
import com.sf.energy.map.model.AmMeterModel;
import com.sf.energy.map.model.ArchitrctureMode;
import com.sf.energy.util.Configuration;



public class MapServlet extends HttpServlet{
	
//	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		  String flag=req.getParameter("flag");
	       
		
			if("queryArcId".equals(flag))
			{
				try {
					querArc(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if("queryArcFloor".equals(flag))
			{
				try {
					//MapwriteXml(req, resp);
					querArcFloor(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if("queryArcAmMeterName".equals(flag))
			{
				try {
					queryArcAmMeterName(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if("queryArcAmMeterMes".equals(flag))
			{
				try {
					queryArcAmMeterMes(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if("getAmChat".equals(flag))
			{
				try {
					getAmChat(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	//将建筑分项用电图表
	private void getAmChat(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String arcId=req.getParameter("ArcId");
		AmMeterDao amMeterDao=new AmMeterDao();				
		Date d=new Date();   
		String theYear=(d.getYear()+1900)+"";
		String theMonth=(d.getMonth()+1)+"";
		String amChatxml=amMeterDao.getMapArcFenxiang(" ",arcId,theYear,theMonth);
		//发送给前台
		 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("amChatxml",amChatxml);					 		 
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  
		
	}
	
	
	private void getWebserviceAddress() throws ConfigurationException
	{
		//电表信息服务地址
		String AmMeterDaoip="";
		//建筑查询信息服务
		String ArchitrctureDaoip="";
		String XlsPath=this.getServletContext().getRealPath("/configuration/webservicesconfig.xml");
		XMLConfiguration config = Configuration.getwebserviceConfiguration(XlsPath);
		// /获取中转站作为服务器监听的端口号
		String webservicesIP = config.getString("webservicesIP.IP");
		// /获取中转站作为客户端的连结服务器的Ip和端口号
		String	Port =config.getString("webservicesPort.Port");

		////System.out.println("webservicesIP:"+webservicesIP);
		AmMeterDaoip="http://"+webservicesIP+":"+Port+"/services/AmMeterDao/";
		ArchitrctureDaoip="http://"+webservicesIP+":"+Port+"/services/ArchitrctureDao/";
		////System.out.println("ArchitrctureDaoip"+ArchitrctureDaoip);
	}
	
	/**
	 * 查询建筑信息
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void querArc(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		
		int id=Integer.parseInt(req.getParameter("ArcId"));
		
		
		ArchitrctureDao architrctureDao=new ArchitrctureDao();
		ArchitrctureMode architrctureMode=new ArchitrctureMode();
		
		// factory.setServiceClass(ArchitrctureDaoImp.class);
	//	 factory.setAddress(ArchitrctureDaoip+"queryArchitrcture");
	//	 ArchitrctureDaoImp client = (ArchitrctureDaoImp)factory.create();
		
		
		architrctureMode=architrctureDao.queryArchitrcture(id);
		String Architecture_Name=architrctureMode.getArchitecture_Name();
		String Architecture_Style=architrctureMode.getArchitecture_Style();
		String Architecture_Area=architrctureMode.getArchitecture_Area();
		String Architecture_Storey=architrctureMode.getArchitecture_Storey();
		String Architecture_Img=architrctureMode.getArcimg();
		int ElecNum=architrctureMode.getElecNum();
		int WaterNum=architrctureMode.getWaterNum();
		
		
		//发送给前台
		 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("Architecture_Name",Architecture_Name);	
		 json.put("Architecture_Style",Architecture_Style);	
		 json.put("Architecture_Area",Architecture_Area);	
		 json.put("Architecture_Storey",Architecture_Storey);
		 json.put("Architecture_Img",Architecture_Img);
		 
		 json.put("ElecNum",ElecNum);	
		 json.put("WaterNum",WaterNum);	
		 		 
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  
		 
	}
	
	//查询楼层信息
	private void querArcFloor(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int id=Integer.parseInt(req.getParameter("ArcId"));
		int floor=0;
		
		
		// factory.setServiceClass(AmMeterDaoImp.class);
		// factory.setAddress(AmMeterDaoip+"queryArcFloor");
		// AmMeterDaoImp client = (AmMeterDaoImp)factory.create();
		AmMeterDao amMeterDao=new AmMeterDao();
		floor=amMeterDao.queryArcFloor(id);
		
		//发送给前台
		 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("floor",floor);	
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  
	}
	//查看电表名称
	private void queryArcAmMeterName(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int arcId=Integer.parseInt(req.getParameter("ArcId"));
		int floor=Integer.parseInt(req.getParameter("floor"));
	
		AmMeterDao amMeterDao=new AmMeterDao();
		List<AmMeterModel> amMeterList=new ArrayList<AmMeterModel>();
		
		// factory.setServiceClass(AmMeterDaoImp.class);
		// factory.setAddress(AmMeterDaoip+"queryArcFoolAmMeter");
		// AmMeterDaoImp client = (AmMeterDaoImp)factory.create();
		 
		amMeterList=amMeterDao.queryArcFoolAmMeter(arcId,floor);
		
		 resp.setContentType("text/html;charset=utf-8");  
		 String jsonString=JSONArray.fromObject(amMeterList).toString();
		
		 PrintWriter pw = resp.getWriter();   
		 pw.print(jsonString); 
	}
	//查看电表信息
	private void queryArcAmMeterMes(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int amMeterId=Integer.parseInt(req.getParameter("amMeterId"));
		
		AmMeterDao amMeterDao=new AmMeterDao();
		AmMeterModel amMeterModel=new AmMeterModel();
		
		
		amMeterDao.carryPro("mapAmMeterHour",amMeterId);
		//获取图表时间
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		//今天日期
		String nowdate=df.format(d);
		//昨天日期
		String lastdate=df.format(d.getTime()-24*60 * 60 * 1000);
		String amMeterStringXml=amMeterDao.getMapArcAm48Hour(lastdate,nowdate);
	
	
		// factory.setServiceClass(AmMeterDaoImp.class);
		// factory.setAddress(AmMeterDaoip+"queryAmMeter");
		// AmMeterDaoImp client = (AmMeterDaoImp)factory.create();
		//获取当前电表示数
		int amMeterValue=amMeterDao.getAmMeterRead(amMeterId);
		
		
		amMeterModel=amMeterDao.queryAmMeter(amMeterId);
		String AmMeter_Name="";
		String AmMeter_485Address="";
		String ConsumerPhone="";
		
		AmMeter_Name=amMeterModel.getAmMeter_Name();
		AmMeter_485Address=amMeterModel.getAmMeter_485Address();
		ConsumerPhone=amMeterModel.getConsumerPhone();
		if(ConsumerPhone==null)
		{
			ConsumerPhone="";
		}
		
		//发送给前台
		 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("AmMeter_Name",AmMeter_Name);	
		 json.put("AmMeter_485Address",AmMeter_485Address);	
		 json.put("ConsumerPhone",ConsumerPhone);	
		 json.put("amMeterValue", amMeterValue);
		 json.put("chatXml",amMeterStringXml);
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  
	}

}
