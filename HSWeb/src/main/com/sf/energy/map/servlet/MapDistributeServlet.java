package com.sf.energy.map.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.map.dao.MapDistributeDao;
import com.sf.energy.map.daoImp.MapDistributeImp;
import com.sf.energy.map.model.MapDistributeModel;
import com.sf.energy.util.Configuration;

public class MapDistributeServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		  String flag=req.getParameter("flag");
		
		
		  if("arcIdentity".equals(flag))
			{
				try {
					//MapwriteXml(req, resp);
					try {
						queryArcMark(req, resp);
					} catch (ConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		  if("deleteArcMark".equals(flag))
		  {
			  try {
					//MapwriteXml(req, resp);
				  deleteArcMark(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  }
		  if("addArcMark".equals(flag))
		  {
			  try {
					//MapwriteXml(req, resp);
				  addArcMark(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  }
	}
	
	private void getWebserviceAddress() throws ConfigurationException
	{
		
		String XlsPath=this.getServletContext().getRealPath("/configuration/webservicesconfig.xml");
		XMLConfiguration config = Configuration.getwebserviceConfiguration(XlsPath);
		// /获取中转站作为服务器监听的端口号
	String webservicesIP = config.getString("webservicesIP.IP");
		// /获取中转站作为客户端的连结服务器的Ip和端口号
	String	Port =config.getString("webservicesPort.Port");
	
	
//	AmMeterDaoip="http://"+webservicesIP+":"+Port+"/services/AmMeterDao/";
//	ArchitrctureDaoip="http://"+webservicesIP+":"+Port+"/services/ArchitrctureDao/";
	////System.out.println("ArchitrctureDaoip"+ArchitrctureDaoip);
	}
	
	
	//将标志信息添加到数据库
	private void addArcMark(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		
		 String[] arcMarkArray=req.getParameterValues("arcMarkArray[]");
		 String lableList_ID=req.getParameter("lableList_ID");
		 MapDistributeDao mapDistributeDao=new MapDistributeDao();
		 MapDistributeModel mapDistributeModel=new MapDistributeModel();
		 mapDistributeDao.deleteMapMarkAll(lableList_ID);
		 for(int i=0;i<arcMarkArray.length;i=i+4)
		 {
			 mapDistributeModel.setLableList_ID(lableList_ID);
			 mapDistributeModel.setDate_ID(arcMarkArray[i]);
			
			 mapDistributeModel.setRemark(arcMarkArray[i+1]);
			 mapDistributeModel.setPointX(arcMarkArray[i+2]);
			 mapDistributeModel.setPointY(arcMarkArray[i+3]);
			 mapDistributeDao.addMapMark(mapDistributeModel);
		 }
		 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("isSuccess","success");	 
		 PrintWriter pw = resp.getWriter();   
		 pw.print(json.toString());  
		 pw.flush();
		 pw.close();  
	}
	
	
	
	//查询建筑标志信息
	private void queryArcMark(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ConfigurationException
	{
		String identity=req.getParameter("identity");
/*		
		String XlsPath=this.getServletContext().getRealPath("/configuration/webservicesconfig.xml");
		XMLConfiguration config = Configuration.getwebserviceConfiguration(XlsPath);
		// /获取中转站作为服务器监听的端口号
	String webservicesIP = config.getString("webservicesIP.IP");
		// /获取中转站作为客户端的连结服务器的Ip和端口号
	String	Port =config.getString("webservicesPort.Port");
	
	
		
		
	//List<MapDistributeModel> a=new ArrayList<MapDistributeModel>();
	
	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(MapDistributeImp.class);
	factory.setAddress("http://"+webservicesIP+":"+Port+"/services/MapDistributeDao/getMapMark");
	MapDistributeImp mapDistributeDao = (MapDistributeImp)factory.create();
	//a=client.getMapMark("2");
*/		
		
		
		
		
		
		
		
		MapDistributeDao mapDistributeDao=new MapDistributeDao();
		List<MapDistributeModel> list=mapDistributeDao.getMapMark(identity);
		
		//发送信息到前台
		 JSONArray  json=JSONArray.fromObject(list);
		
		 resp.setContentType("text/html;charset=utf-8");  

		 PrintWriter pw = resp.getWriter();  
		 pw.print(json.toString());  
		 pw.flush();
		 pw.close();
	}
	
	private void deleteArcMark(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		
		 String[] idarray=req.getParameterValues("arcArray[]");
		 String lableList_Id=req.getParameter("lableList_Id");
		 boolean flag=true;
		 boolean flag1=true;
		 String isSuccess="success";
		 MapDistributeDao mapDistributeDao=new MapDistributeDao();
		 for(int i=0;i<idarray.length;i++)
		 {
			
			 flag1=mapDistributeDao.deleteMapMar(lableList_Id,idarray[i]);
			 flag=flag&flag1;
		 }
		 if(flag==true)
		 {
			 isSuccess="success";
		 }
		 else {
			 isSuccess="false";
		}
		 isSuccess="success";
		 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("isSuccess",isSuccess);	 
		 PrintWriter pw = resp.getWriter();   
		 pw.print(json.toString());  
		 pw.flush();
		 pw.close();  
	}

}
