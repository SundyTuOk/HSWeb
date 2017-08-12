package com.sf.energy.report.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
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
import com.sf.energy.report.dao.EnReportDataHisDao;
import com.sf.energy.report.model.EnReportDataHisModel;
import com.sf.energy.report.model.EnReportDataModel;
import com.sf.energy.util.Configuration;
import com.sf.energy.util.ConnDB;

public class DataManagerServlet extends HttpServlet
{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		String flag = req.getParameter("flag");
		if ("delete".equals(flag))
		{
			deleteDataManager(req, resp);
		} else if ("edit".equals(flag))
		{
			editDataManager(req, resp);
		} else if ("add".equals(flag))
		{
			addDataManager(req, resp);
		} else if ("exec".equals(flag))
		{

			execDataManager(req, resp);

		} else if ("report".equals(flag))
		{
			try
			{
				dataManagerReport(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConfigurationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("deletearray".equals(flag))
		{

			deleteDataManagerArray(req, resp);
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
		}
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
		int thePageCount = Integer.parseInt(req.getParameter("DataManagerPageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("DataManagerPageIndex"));
		String DataManagersortName = req.getParameter("DataManagersortName");
		String DataManagerorder = req.getParameter("DataManagerorder");

		String EnReportData_enable = "";

		List<EnReportDataModel> dataManagerlist = new ArrayList<EnReportDataModel>();
		DataManagerDao dataManagerDao = new DataManagerDao();
		dataManagerlist = dataManagerDao.queryDataManager(DataManagersortName, DataManagerorder, thePageCount, thePageIndex);

		JSONArray json = new JSONArray();
		JSONObject jototal = new JSONObject();
		jototal.put("totalCount", dataManagerDao.getTotalCount());
		json.add(jototal);

		for (EnReportDataModel enReport : dataManagerlist)
		{
			JSONObject jo = new JSONObject();
			if (enReport.getEnReportData_enable() == 1)
			{
				EnReportData_enable = "是";
			} else
			{
				EnReportData_enable = "否";
			}

			jo.put("EnReportData_id", enReport.getEnReportData_id());
			jo.put("EnReportData_num", enReport.getEnReportData_num());
			jo.put("EnReportData_name", enReport.getEnReportData_name());
			jo.put("EnReportData_style", enReport.getEnReportData_style());
			jo.put("EnReportData_enable", EnReportData_enable);
			jo.put("EnReportData_remark", enReport.getEnReportData_remark());
			jo.put("EnReportData_part", enReport.getEnReportData_part());
			jo.put("EnReportData_text", enReport.getEnReportData_text());
			json.add(jo);
		}
		resp.setContentType("text/html;charset=utf-8");

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 删除记录
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void deleteDataManager(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		boolean flag = true;
		boolean flag1 = false;
		boolean flag2 = false;
		int id = 0;
		id = Integer.parseInt(req.getParameter("id"));
		DataManagerDao dataManagerDao = new DataManagerDao();
		try
		{
			flag = dataManagerDao.deleteDataManager(id);
			// flag2=dataManagerDao.deleteEnReportDataHis(id);
			// //System.out.println("a:"+flag1+" b:"+flag2);
			// flag=flag&flag1&flag2;
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

	/**
	 * 对表进行编辑
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void editDataManager(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		boolean flag = false;
		String dataMnagerEditNum = req.getParameter("dataMnagerEditNum");
		String dataMnagerEditName = req.getParameter("dataMnagerEditName");
		String dataMnagerEditStyle = req.getParameter("dataMnagerEditStyle");
		int dataMnagerEditEnable = Integer.parseInt(req.getParameter("dataMnagerEditEnable"));
		String dataManagerEditPart = req.getParameter("dataManagerEditPart");
		String dataMnagerEditText = req.getParameter("dataMnagerEditText");
		String dataMnagerEditRemark = req.getParameter("dataMnagerEditRemark");
		int dataManagerEditId = Integer.parseInt(req.getParameter("dataManagerEditId"));

		EnReportDataModel enReportDataModel = new EnReportDataModel();
		enReportDataModel.setEnReportData_num(dataMnagerEditNum);
		enReportDataModel.setEnReportData_name(dataMnagerEditName);
		enReportDataModel.setEnReportData_style(dataMnagerEditStyle);
		enReportDataModel.setEnReportData_enable(dataMnagerEditEnable);
		enReportDataModel.setEnReportData_part(dataManagerEditPart);
		enReportDataModel.setEnReportData_text(dataMnagerEditText);
		enReportDataModel.setEnReportData_remark(dataMnagerEditRemark);
		enReportDataModel.setEnReportData_id(dataManagerEditId);

		DataManagerDao dataManagerDao = new DataManagerDao();
		try
		{
			flag = dataManagerDao.editDataManager(enReportDataModel);
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
		// 上报

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		if (dataMnagerEditEnable == 1)
		{
			json.put("isReport", "yes");
		} else
		{
			json.put("isReport", "no");
		}
		json.put("dataMnagerEditNum", dataMnagerEditNum);
		json.put("dataMnagerEditName", dataMnagerEditName);
		json.put("dataMnagerEditStyle", dataMnagerEditStyle);
		json.put("dataMnagerEditEnable", dataMnagerEditEnable);
		json.put("dataManagerEditPart", dataManagerEditPart);
		json.put("dataMnagerEditText", dataMnagerEditText);
		json.put("dataMnagerEditRemark", dataMnagerEditRemark);
		json.put("dataManagerEditId", dataManagerEditId);
		json.put("isSuccess", isSuccess);
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());

	}

	/**
	 * 增加
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void addDataManager(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		boolean flag = false;
		String isReport = "no";
		String dataMnagerAddNum = req.getParameter("dataMnagerAddNum");
		String dataMnagerAddName = req.getParameter("dataMnagerAddName");
		String dataMnagerAddStyle = req.getParameter("dataMnagerAddStyle");
		int dataMnagerAddEnable = Integer.parseInt(req.getParameter("dataMnagerAddEnable"));
		String dataManagerAddPart = req.getParameter("dataManagerAddPart");
		String dataMnagerAddText = req.getParameter("dataMnagerAddText");
		String dataMnagerAddRemark = req.getParameter("dataMnagerAddRemark");

		EnReportDataModel enReportDataModel = new EnReportDataModel();
		enReportDataModel.setEnReportData_num(dataMnagerAddNum);
		enReportDataModel.setEnReportData_name(dataMnagerAddName);
		enReportDataModel.setEnReportData_style(dataMnagerAddStyle);
		enReportDataModel.setEnReportData_enable(dataMnagerAddEnable);
		enReportDataModel.setEnReportData_part(dataManagerAddPart);
		enReportDataModel.setEnReportData_text(dataMnagerAddText);
		enReportDataModel.setEnReportData_remark(dataMnagerAddRemark);
		int id = 0;
		DataManagerDao dataManagerDao = new DataManagerDao();
		try
		{
			id = dataManagerDao.getDataManagerId();
			enReportDataModel.setEnReportData_id(id);
			flag = dataManagerDao.addDataManager(enReportDataModel);
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

		// 上报
		if (dataMnagerAddEnable == 1)
		{

			isReport = "yes";
			json.put("id", id);

		}
		json.put("isReport", isReport);
		json.put("isSuccess", isSuccess);
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());

	}

	private void execDataManager(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/html;charset=utf-8");
		int error = 0;
		JSONObject jo1 = new JSONObject();
		JSONArray json = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ResultSetMetaData meta = null;
		String sql = "";
		String para = "";
		String style = "";
		String id = req.getParameter("id");

		DataManagerDao dataManagerDao = new DataManagerDao();
		try
		{
			EnReportDataModel enReportDataModel = new EnReportDataModel();
			enReportDataModel = getEnReportData(id);
			sql = enReportDataModel.getEnReportData_text();
			para = enReportDataModel.getEnReportData_part();
			style = enReportDataModel.getEnReportData_style();

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
				conn= ConnDB.getConnection();
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
				// TODO Auto-generated catch block
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

	/**
	 * 获取能耗上报信息
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	private EnReportDataModel getEnReportData(String id) throws SQLException
	{
		EnReportDataModel enReportDataModel = new EnReportDataModel();
		String sql = "select EnReportData_Text,EnReportData_Style,EnReportData_Part,EnReportData_Remark from EnReportData where EnReportData_ID="
				+ id;
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				enReportDataModel.setEnReportData_style(rs.getString("EnReportData_Style"));
				enReportDataModel.setEnReportData_text(rs.getString("EnReportData_Text"));
				enReportDataModel.setEnReportData_part(rs.getString("EnReportData_Part"));
				enReportDataModel.setEnReportData_remark(rs.getString("EnReportData_Remark"));
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			enReportDataModel.setEnReportData_style(rs.getString("EnReportData_Style"));
			enReportDataModel.setEnReportData_text(rs.getString("EnReportData_Text"));
			enReportDataModel.setEnReportData_part(rs.getString("EnReportData_Part"));
			enReportDataModel.setEnReportData_remark(rs.getString("EnReportData_Remark"));
		}
		if (rs != null)
			rs.close();

		if (ps != null)
			ps.close();*/
		return enReportDataModel;
	}

	/**
	 * 上报能耗
	 * 
	 * @throws SQLException
	 * @throws ParseException
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	private void dataManagerReport(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ParseException, IOException,
			ConfigurationException
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
		String enReportData_ID = req.getParameter("id");
		EnReportDataModel enReportDataModel = new EnReportDataModel();
		enReportDataModel = getEnReportData(enReportData_ID);

		EnReportDataHisDao enReportDataHisDao = new EnReportDataHisDao();
		// 插入历史记录的ID
		id = enReportDataHisDao.getEnReportDataHisId();
		// 执行类型为查询语句

		// 执行时间
		Date date = new Date();
		String ValueTime = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ValueTime = df.format(date);

		// 执行结果
		if (returnflag == true)
		{
			result = 1;
		} else
		{
			result = 0;
		}
		// 备注
		remark = enReportDataModel.getEnReportData_remark();
		enReportDataHisModel.setId(id);
		enReportDataHisModel.setEnReportData_id(Integer.parseInt(enReportData_ID));

		enReportDataHisModel.setValueTime(ValueTime);
		enReportDataHisModel.setStyle(enReportDataModel.getEnReportData_style());
		enReportDataHisModel.setResult(result);
		enReportDataHisModel.setRemark(remark);
		enReportDataHisModel.setEnReportData_Text(enReportDataModel.getEnReportData_text());
		enReportDataHisModel.setEnReportData_Part(enReportDataModel.getEnReportData_part());

		enReportDataHisDao.addEnReportDataHis(enReportDataHisModel);

		// 通过Json传值到页面
		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();

		json.put("result", result);

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void deleteDataManagerArray(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		// idarray=new String[100];
		boolean flag = true;
		boolean flag1 = false;
		// boolean flag2=false;
		String[] idarray = req.getParameterValues("idarray[]");
		DataManagerDao dataManagerDao = new DataManagerDao();
		for (int i = 0; i < idarray.length; i++)
		{

			try
			{
				flag1 = dataManagerDao.deleteDataManager(Integer.parseInt(idarray[i]));
				// flag2=dataManagerDao.deleteEnReportDataHis(Integer.parseInt(idarray[i]));
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
