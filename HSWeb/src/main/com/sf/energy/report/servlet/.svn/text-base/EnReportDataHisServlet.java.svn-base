package com.sf.energy.report.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

import com.sf.energy.report.dao.DataManagerDao;
import com.sf.energy.report.dao.EnReportClient;
import com.sf.energy.report.dao.EnReportDao;
import com.sf.energy.report.dao.EnReportDataHisDao;
import com.sf.energy.report.model.EnReportDataHisModel;
import com.sf.energy.report.model.EnReportDataModel;
import com.sf.energy.util.Configuration;
import com.sf.energy.util.ConnDB;

public class EnReportDataHisServlet extends HttpServlet
{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		String flag = req.getParameter("flag");
		if ("delete".equals(flag))
		{
			deleteEnReportDataHis(req, resp);
		} else if ("deletearray".equals(flag))
		{

			deleteEnReportDataHisArray(req, resp);
		} else if ("loaddate".equals(flag))
		{
			try
			{
				loaddate(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("carrydate".equals(flag))
		{
			try
			{
				carryData(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("report".equals(flag))
		{
			try
			{
				enReport(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConfigurationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 上报
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	private void enReport(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ConfigurationException
	{
		boolean returnflag = false;
		int id = 0;

		int result = 0;
		String remark = "";
		EnReportDataHisModel enReportDataHisModel = new EnReportDataHisModel();
		String reportMessage = req.getParameter("tableXml");

		// 上报历史记录
		String XlsPath = this.getServletContext().getRealPath("/configuration/webservicesconfig.xml");
		XMLConfiguration config = Configuration.getwebserviceConfiguration(XlsPath);
		// /获取中转站作为服务器监听的端口号
		String enReprotWebServiceIP = config.getString("enReprotWebService.IP");
		// /获取中转站作为客户端的连结服务器的Ip和端口号
		int enReprotWebServicePort = Integer.parseInt(config.getString("enReprotWebService.Port"));
		EnReportClient enReportClient = new EnReportClient(enReprotWebServiceIP, enReprotWebServicePort);
		returnflag = enReportClient.enReportSend(reportMessage);

		// 用于联合表的ID
		id = Integer.parseInt(req.getParameter("id"));

		Date date = new Date();
		String ValueTime = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ValueTime = df.format(date);

		EnReportDataHisDao enReportDataHisDao = new EnReportDataHisDao();
		// 插入历史记录的ID

		// 执行结果
		if (returnflag == true)
		{
			result = 1;
		} else
		{
			result = 0;
		}

		enReportDataHisModel.setId(id);
		enReportDataHisModel.setValueTime(ValueTime);
		enReportDataHisModel.setResult(result);
		enReportDataHisDao.editDataManager(enReportDataHisModel);

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();

		json.put("result", result);

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 立即执行
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void carryData(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{

		resp.setContentType("text/html;charset=utf-8");
		int error = 0;
		JSONObject jo1 = new JSONObject();
		ResultSet rs = null;
		JSONArray json = new JSONArray();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSetMetaData meta = null;
		String sql = "";
		String para = "";
		String style = "";
		String id = req.getParameter("id");
		DataManagerDao dataManagerDao = new DataManagerDao();
		try
		{
			EnReportDataHisModel enReportDataHisModel = new EnReportDataHisModel();
			enReportDataHisModel = getEnReportDataHisMes(id);
			sql = enReportDataHisModel.getEnReportData_Text();
			para = enReportDataHisModel.getEnReportData_Part();
			style = enReportDataHisModel.getStyle();

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean flag = true;
		if ("存储过程".equals(style))
		{
			try
			{
				json = dataManagerDao.execPro(sql, para);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				error = 1;

			}

		} else if ("查询语句".equals(style))
		{
			try
			{
				conn=ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();

				if (rs.next())
				{
					// rs.previous();
					meta = rs.getMetaData();
					// 取得列数
					int count = meta.getColumnCount();
					int i = 0;

					JSONObject info = new JSONObject();
					info.put("ColumnCount", meta.getColumnCount());
					json.add(info);

					JSONObject jo = new JSONObject();
					for (i = 1; i <= count; i++)
					{
						// 获取列名
						jo.put("" + i, meta.getColumnLabel(i));
					}
					json.add(jo);

					int countNum = 20;
					do
					{
						JSONObject job = new JSONObject();
						for (i = 1; i <= count; i++)
						{
							job.put("" + i, rs.getObject(i));
						}
						json.add(job);
						countNum--;
					} while (rs.next() && countNum > 0);

				}
			} catch (SQLException e)
			{
				error = 2;
			}finally{
				ConnDB.release(conn, ps,rs);
			}
		}
		jo1.put("error", error);
		json.add(jo1);

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private EnReportDataHisModel getEnReportDataHisMes(String id) throws SQLException
	{
		EnReportDataHisModel enReportDataHisModel = new EnReportDataHisModel();
		String sql = "select EnReportData_Text,ENREPORTDATA_PART,STYLE from EnReportDataHis where id=" + id;

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				enReportDataHisModel.setEnReportData_Text(rs.getString("EnReportData_Text"));
				enReportDataHisModel.setEnReportData_Part(rs.getString("ENREPORTDATA_PART"));
				enReportDataHisModel.setStyle(rs.getString("STYLE"));
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		/*
		//PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			enReportDataHisModel.setEnReportData_Text(rs.getString("EnReportData_Text"));
			enReportDataHisModel.setEnReportData_Part(rs.getString("ENREPORTDATA_PART"));
			enReportDataHisModel.setStyle(rs.getString("STYLE"));
		}
		if (rs != null)
			rs.close();

		if (ps != null)
			ps.close();*/
		return enReportDataHisModel;

	}

	/**
	 * 加载数据
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void loaddate(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("EnReportDataHisPageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("EnReportDataHisPageIndex"));
		String enHistorysortName = req.getParameter("enHistorysortName");
		String enHistoryorder = req.getParameter("enHistoryorder");

		List<EnReportDataHisModel> list = new ArrayList<EnReportDataHisModel>();
		EnReportDataHisDao enReportDataHisDao = new EnReportDataHisDao();
		list = enReportDataHisDao.queryEnReportDataHis(enHistorysortName, enHistoryorder, thePageCount, thePageIndex);

		JSONArray json = new JSONArray();
		JSONObject jototal = new JSONObject();
		jototal.put("totalCount", enReportDataHisDao.getTotalCount());
		json.add(jototal);

		for (EnReportDataHisModel enReportHis : list)
		{
			JSONObject jo = new JSONObject();

			String enReportDataresult = "";
			if (enReportHis.getResult() == 1)
			{
				enReportDataresult = "执行成功";
			} else
			{
				enReportDataresult = "执行失败";
			}
			jo.put("id", enReportHis.getId());
			jo.put("EnReportData_id", enReportHis.getEnReportData_id());
			jo.put("EnReportData_num", enReportHis.getEnReportData_num());
			jo.put("EnReportData_name", enReportHis.getEnReportData_name());
			jo.put("Style", enReportHis.getStyle());
			jo.put("ValueTime", enReportHis.getValueTime());
			jo.put("enReportDataresult", enReportDataresult);
			jo.put("Remark", enReportHis.getRemark());
			json.add(jo);
		}

		resp.setContentType("text/html;charset=utf-8");

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 删除
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void deleteEnReportDataHis(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		boolean flag = false;
		int id = 0;
		id = Integer.parseInt(req.getParameter("id"));

		EnReportDataHisDao enReportDataHisDao = new EnReportDataHisDao();
		try
		{
			flag = enReportDataHisDao.deleteEnReportDataHis(id);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String isSuccess = "";
		if (flag == true)
		{
			isSuccess = "success";
		} else
		{
			isSuccess = "failure";
		}
		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("isSuccess", isSuccess);
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

	// 批量删除
	private void deleteEnReportDataHisArray(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		boolean flag = true;
		boolean flag1 = false;
		String[] idarray = req.getParameterValues("idarray[]");
		EnReportDataHisDao enReportDataHisDao = new EnReportDataHisDao();
		for (int i = 0; i < idarray.length; i++)
		{

			try
			{
				flag1 = enReportDataHisDao.deleteEnReportDataHis(Integer.parseInt(idarray[i]));
				flag = flag & flag1;
			} catch (NumberFormatException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String isSuccess = "";
		if (flag == true)
		{
			isSuccess = "success";
		} else
		{
			isSuccess = "failure";
		}
		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("isSuccess", isSuccess);
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}
}
