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

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.map.daoImp.ArchitrctureDaoImp;
import com.sf.energy.map.model.ArchitrctureMode;
import com.sf.energy.report.dao.ArcSampleDao;
import com.sf.energy.report.model.ArcSampleModel;
import com.sf.energy.util.Configuration;

public class ArcSampleServlet extends HttpServlet
{
	String ip = "";
	String ArchitrctureDaoip = "";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		String flag = req.getParameter("flag");

		/*
		 * try { getWebserviceAddress(); } catch (ConfigurationException e1) {
		 * // TODO Auto-generated catch block e1.printStackTrace(); }
		 */
		if ("loaddate".equals(flag))
		{
			try
			{
				loadArcSample(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("load".equals(flag))
		{
			try
			{
				try
				{
					webserviceLoad(req, resp);
				} catch (ConfigurationException e)
				{
					e.printStackTrace();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		else if ("check".equals(flag))
		{
			checkArcSample(req, resp);
		}

	}

	/**
	 * 获取webservice的地址和端口号
	 * 
	 * @throws ConfigurationException
	 */
	private void getWebserviceAddress() throws ConfigurationException
	{
		String XlsPath = this.getServletContext().getRealPath("/configuration/webservicesconfig.xml");
		XMLConfiguration config = Configuration.getwebserviceConfiguration(XlsPath);
		// /获取中转站作为服务器监听的端口号
		String webservicesIP = config.getString("webservicesIP.IP");
		// /获取中转站作为客户端的连结服务器的Ip和端口号
		String Port = config.getString("webservicesPort.Port");
		ip = "http://" + webservicesIP + ":" + Port + "/services/ArcSampleDao/";

		ArchitrctureDaoip = "http://" + webservicesIP + ":" + Port + "/services/ArchitrctureDao/";

	}
	
	/**
	 * 返回webservice的地址和端口号    删除实例变量
	 * @return
	 * @throws ConfigurationException
	 */
	private  String returnWebserviceAddress() throws ConfigurationException
	{
		//String ip = "";
		String ArchitrctureDaoipa = "";
		String XlsPath = this.getServletContext().getRealPath("/configuration/webservicesconfig.xml");
		XMLConfiguration config = Configuration.getwebserviceConfiguration(XlsPath);
		// /获取中转站作为服务器监听的端口号
		String webservicesIP = config.getString("webservicesIP.IP");
		// /获取中转站作为客户端的连结服务器的Ip和端口号
		String Port = config.getString("webservicesPort.Port");
		ip = "http://" + webservicesIP + ":" + Port + "/services/ArcSampleDao/";

		ArchitrctureDaoipa = "http://" + webservicesIP + ":" + Port + "/services/ArchitrctureDao/";
		return ArchitrctureDaoipa;
	}
	

	/**
	 * webservice 加载
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws ConfigurationException 
	 */
	private void webserviceLoad(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ConfigurationException
	{
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		ArchitrctureMode architrctureMode = new ArchitrctureMode();

		factory.setServiceClass(ArchitrctureDaoImp.class);
		factory.setAddress(returnWebserviceAddress() + "queryArchitrcture");
		ArchitrctureDaoImp client = (ArchitrctureDaoImp) factory.create();

		architrctureMode = client.queryArchitrcture(21);
	}

	/**
	 * 更新数据库，保持被选中的建筑
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void loadArcSample(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		resp.setContentType("text/html;charset=utf-8");
		int thePageCount = Integer.parseInt(req.getParameter("ArcSamplePageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("ArcSamplePageIndex"));
		// String selectArcSampleStyle=req.getParameter("selectArcSampleStyle");
		// String selectArcSampleID=req.getParameter("selectArcSampleID");
		String archid = req.getParameter("archid");
		String archstyleid = req.getParameter("archstyleid");

		String ArcSamplesortName = req.getParameter("ArcSamplesortName");
		String ArcSampleorder = req.getParameter("ArcSampleorder");

		List<ArcSampleModel> arcSampleList = new ArrayList<ArcSampleModel>();
		/*
		 * factory.setServiceClass(ArcSampleDaoImp.class);
		 * factory.setAddress(ip+"queryArcSample"); ArcSampleDaoImp client =
		 * (ArcSampleDaoImp)factory.create();
		 */
		ArcSampleDao client = new ArcSampleDao();
		arcSampleList = client.queryArcSample(ArcSamplesortName, ArcSampleorder, archstyleid, archid, thePageCount, thePageIndex);
		JSONArray json = new JSONArray();
		JSONObject jototal = new JSONObject();

		jototal.put("totalCount", client.getTotalCount());
		json.add(jototal);

		for (ArcSampleModel arcSampleModel : arcSampleList)
		{
			JSONObject jo = new JSONObject();

			jo.put("architecture_ID", arcSampleModel.getArchitecture_ID());
			jo.put("architecture_Num", arcSampleModel.getArchitecture_Num());
			jo.put("architecture_Name", arcSampleModel.getArchitecture_Name());
			jo.put("architecture_Style", arcSampleModel.getArchitecture_Style());
			jo.put("architecture_Man", arcSampleModel.getArchitecture_Man());
			jo.put("architecture_Phone", arcSampleModel.getArchitecture_Phone());
			jo.put("isEnReport", arcSampleModel.getIsEnReport());
			json.add(jo);
		}

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();

	}

	private int getTotal()
	{
		/*
		 * factory.setServiceClass(ArcSampleDaoImp.class);
		 * factory.setAddress(ip+"getTotalCount"); ArcSampleDaoImp client =
		 * (ArcSampleDaoImp)factory.create();
		 */
		ArcSampleDao client = new ArcSampleDao();
		return client.getTotalCount();
	}

	/**
	 * 保存样本
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void checkArcSample(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/html;charset=utf-8");
		String id = req.getParameter("id");
		String isEnReport = req.getParameter("isEnReport");
		ArcSampleDao arcSampleDao = new ArcSampleDao();
		try
		{
			arcSampleDao.checkArcSample(id, isEnReport);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.put("success", "success");
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());

	}
}
