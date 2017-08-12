package com.sf.energy.powernet.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.sf.energy.manager.current.service.XMLHelper;
import com.sf.energy.manager.monitor.dao.AmmeterDataMaintenanceDao;
import com.sf.energy.powernet.dao.AmMeterPDDataDao;
import com.sf.energy.powernet.model.CurrentPartDatasModel;
import com.sf.energy.powernet.model.PDPartRoom;
import com.sf.energy.powernet.model.TempWETDevDatasModel;
import com.sf.energy.powernet.service.PowerNetLineInfo;
import com.sf.energy.project.equipment.dao.PDTempWetDao;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.util.ConnDB;

public class PowerNetLineServlet extends HttpServlet
{

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		if ("getPowerNetLineSWF".equalsIgnoreCase(request.getParameter("method")))
		{
			getPowerNetLineSWF(request, response);
		}
		if ("getPowerNetFusionChart".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				getPowerNetFusionChart(request, response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ("getPowerNetLineCurrentDatas".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				try
				{
					getPowerNetLineCurrentDatas(request, response);
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ("getTempWetHistory".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				try
				{
					getTempWetHistory(request, response);
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ("GetGOTOInfo".equalsIgnoreCase(request.getParameter("method")))
		{
			GetGOTOInfo(request, response);
		}
		if ("getPowerNetLineSWFByName".equalsIgnoreCase(request.getParameter("method")))
		{
			getPowerNetLineSWFByName(request, response);
		}
		if ("GetMonitorLineInfo".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				GetMonitorLineInfo(request, response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ("ShowAlarmInfo".equalsIgnoreCase(request.getParameter("method")))
		{

			try
			{
				ShowAlarmInfo(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}

		if ("GetOverInfo".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				GetOverInfo(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		if ("saveXmlString".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				saveXmlString(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		if ("copyXML".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				copyXML(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		if ("getAllPartRoom".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				getAllPartRoom(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if ("bandChooseInfo".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				bandChooseInfo(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		this.doGet(request, response);
	}

	/**
	 * 加载哪个flash
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getPowerNetLineSWF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String flag = "";
		if (request.getParameter("flag") != null)
		{
			flag = request.getParameter("flag");
		}

		if (flag.equals("all"))// 全校flash
		{
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println("all");
			out.flush();
			out.close();
		} else
		{
			int partID = Integer.parseInt(flag);
			AmMeterPDDataDao apd = new AmMeterPDDataDao();
			try
			{
				String xmlString = apd.getFlashXML(partID);
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(xmlString);
				out.flush();
				out.close();
			} catch (SQLException e)
			{

				e.printStackTrace();
			}
		}
	}

	/**
	 * 加载哪个flash
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void getPowerNetFusionChart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		String part_ID = "";
		if (request.getParameter("flag") != null)
		{
			part_ID = request.getParameter("flag");
		}

		int part_id = Integer.parseInt(part_ID);
		PDTempWetDao ptdao = new PDTempWetDao();
		boolean ishasDevice=ptdao.checkPDhasTemWetDevice(part_id);
		//System.out.println(part_id+" "+ptdao.checkPDhasTemWetDevice(part_id));
		AmMeterPDDataDao apd = new AmMeterPDDataDao();
		HashMap<Integer, HashMap<String, String>> lists = new HashMap<Integer, HashMap<String, String>>();
		JSONArray rjArray = new JSONArray();
		JSONObject object = new JSONObject();
		object.put("showOrhide", ishasDevice);
		rjArray.add(object);
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		String date = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		date = df.format(new Date());
		if(ishasDevice==true){
			lists = apd.getTempWETDevDatas("01", part_id, date);
			// Iterator iterator = lists.keySet().iterator();
			for (int i = 0; i < lists.size(); i++)
			{
				HashMap<String, String> line = lists.get(i);
				jo = new JSONObject();
				jo.put("TempWetDev_ID", line.get("TempWetDev_ID"));
				jo.put("ValueTime", line.get("ValueTime"));
				jo.put("TempValue", line.get("TempValue"));
				jo.put("WetValue", line.get("WetValue"));
				jo.put("Part_Name", line.get("Part_Name"));
				json.add(jo);
			}
		}
		rjArray.add(json);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		//System.out.println(rjArray.toString());
		out.println(rjArray.toString());
		out.flush();
		out.close();
	}

	/**
	 * 上下箭头方法(此方法暂时没用上)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void GetGOTOInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String ObjName = "";
		String style = "";
		if (request.getParameter("ObjName") != null)
		{
			ObjName = request.getParameter("ObjName");
		}
		if (request.getParameter("style") != null)
		{
			style = request.getParameter("style");
		}

		String result = "";
		PowerNetLineInfo pnl = new PowerNetLineInfo();
		try
		{
			result = pnl.getGotoString(ObjName, style);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// //System.out.println("flash打印:"+result);
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * 同过设备名称查询flash的xml进行跳转flash
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getPowerNetLineSWFByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String partName = "";
		if (request.getParameter("partName") != null)
		{
			partName = request.getParameter("partName");
		}
		AmMeterPDDataDao apd = new AmMeterPDDataDao();
		try
		{
			String xmlString = apd.getFlashXMLByName(partName);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(xmlString);
			out.flush();
			out.close();
		} catch (SQLException e)
		{

			e.printStackTrace();
		}

	}

	/**
	 * 获取回路上的数据
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void GetMonitorLineInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		String MonitorLineList = request.getParameter("MonitorLineList");
		String MonitorLineType = request.getParameter("MonitorLineType");
		String strNodeInText = request.getParameter("strNodeInText");
		DecimalFormat df = new DecimalFormat("####0.00");

		String write = "";

		String sql = "select JLID,a.Part_ID Part_ID,b.Part_Num Part_Num from PD_PartInfo03 a,PD_Part b "
				+ "where a.Part_ID=b.Part_ID and a.JLLX=0 and a.Part_ID in (" + MonitorLineList + ")";
		Connection conn1 = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			while (rs1.next())
			{
				String ammeterid = rs1.getString("JLID");
				String partid = rs1.getString("Part_ID");
				String Part_Num = rs1.getString("Part_Num");
				if(MonitorLineType.equals("ID"))
				{
					Part_Num		=rs1.getString("Part_ID");			
				}
				if ((ammeterid != null) && (!"".equals(ammeterid)))
				{
					sql = "select x.ValueTime,PowerZY,PowerAY,PowerBY,PowerCY,PowerZW,PowerAW,PowerBW,PowerCW,PowerFactorZ, "
							+ "	PowerFactorA,PowerFactorB,PowerFactorC,VoltageA,VoltageB,VoltageC,CurrentA,CurrentB, "
							+ "	CurrentC,Current0,PowerSZZ,PowerSZA,PowerSZB,PowerSZC from (select max(ValueTime)ValueTime from ZPDDATAS" + ammeterid
							+ ")x,ZPDDATAS" + ammeterid + "  y where x.ValueTime=y.ValueTime ";
					Connection conn = null;
					PreparedStatement ps = null;
					ResultSet rs = null;
					// System.out.println(ammeterid);
					try
					{
						conn = ConnDB.getConnection();
						ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs = ps.executeQuery();
						while (rs.next())
						{
							if (write.equals(""))// txtInLine01PowerZYValue
													// divZValueZY
								write = Part_Num + ",ValueTime," + rs.getString("ValueTime") + "|" + Part_Num + ",PowerZY,"
										+ df.format(rs.getFloat("PowerZY")) + "|" + Part_Num + ",PowerAY," + df.format(rs.getFloat("PowerAY")) + "|"
										+ Part_Num + ",PowerBY," + df.format(rs.getFloat("PowerBY")) + "|" + Part_Num + ",PowerCY,"
										+ df.format(rs.getFloat("PowerCY")) + "|" + Part_Num + ",PowerZW," + df.format(rs.getFloat("PowerZW")) + "|"
										+ Part_Num + ",PowerAW," + df.format(rs.getFloat("PowerAW")) + "|" + Part_Num + ",PowerBW,"
										+ df.format(rs.getFloat("PowerBW")) + "|" + Part_Num + ",PowerCW," + df.format(rs.getFloat("PowerCW")) + "|"
										+ Part_Num + ",PowerFactorZ," + df.format(rs.getFloat("PowerFactorZ")) + "|" + Part_Num + ",PowerFactorA,"
										+ df.format(rs.getFloat("PowerFactorA")) + "|" + Part_Num + ",PowerFactorB,"
										+ df.format(rs.getFloat("PowerFactorB")) + "|" + Part_Num + ",PowerFactorC,"
										+ df.format(rs.getFloat("PowerFactorC")) + "|" + Part_Num + ",VoltageA," + df.format(rs.getFloat("VoltageA"))
										+ "|" + Part_Num + ",VoltageB," + df.format(rs.getFloat("VoltageB")) + "|" + Part_Num + ",VoltageC,"
										+ df.format(rs.getFloat("VoltageC")) + "|" + Part_Num + ",CurrentA," + df.format(rs.getFloat("CurrentA"))
										+ "|" + Part_Num + ",CurrentB," + df.format(rs.getFloat("CurrentB")) + "|" + Part_Num + ",CurrentC,"
										+ df.format(rs.getFloat("CurrentC")) + "|" + Part_Num + ",Current0," + df.format(rs.getFloat("Current0"))
										+ "|" + Part_Num + ",PowerSZZ," + df.format(rs.getFloat("PowerSZZ")) + "|" + Part_Num + ",PowerSZA,"
										+ df.format(rs.getFloat("PowerSZA")) + "|" + Part_Num + ",PowerSZB," + df.format(rs.getFloat("PowerSZB"))
										+ "|" + Part_Num + ",PowerSZC," + df.format(rs.getFloat("PowerSZC"));
							else
								write += "|" + Part_Num + ",ValueTime," + rs.getString("ValueTime") + "|" + Part_Num + ",PowerZY,"
										+ df.format(rs.getFloat("PowerZY")) + "|" + Part_Num + ",PowerAY," + df.format(rs.getFloat("PowerAY")) + "|"
										+ Part_Num + ",PowerBY," + df.format(rs.getFloat("PowerBY")) + "|" + Part_Num + ",PowerCY,"
										+ df.format(rs.getFloat("PowerCY")) + "|" + Part_Num + ",PowerZW," + df.format(rs.getFloat("PowerZW")) + "|"
										+ Part_Num + ",PowerAW," + df.format(rs.getFloat("PowerAW")) + "|" + Part_Num + ",PowerBW,"
										+ df.format(rs.getFloat("PowerBW")) + "|" + Part_Num + ",PowerCW," + df.format(rs.getFloat("PowerCW")) + "|"
										+ Part_Num + ",PowerFactorZ," + df.format(rs.getFloat("PowerFactorZ")) + "|" + Part_Num + ",PowerFactorA,"
										+ df.format(rs.getFloat("PowerFactorA")) + "|" + Part_Num + ",PowerFactorB,"
										+ df.format(rs.getFloat("PowerFactorB")) + "|" + Part_Num + ",PowerFactorC,"
										+ df.format(rs.getFloat("PowerFactorC")) + "|" + Part_Num + ",VoltageA," + df.format(rs.getFloat("VoltageA"))
										+ "|" + Part_Num + ",VoltageB," + df.format(rs.getFloat("VoltageB")) + "|" + Part_Num + ",VoltageC,"
										+ df.format(rs.getFloat("VoltageC")) + "|" + Part_Num + ",CurrentA," + df.format(rs.getFloat("CurrentA"))
										+ "|" + Part_Num + ",CurrentB," + df.format(rs.getFloat("CurrentB")) + "|" + Part_Num + ",CurrentC,"
										+ df.format(rs.getFloat("CurrentC")) + "|" + Part_Num + ",Current0," + df.format(rs.getFloat("Current0"))
										+ "|" + Part_Num + ",PowerSZZ," + df.format(rs.getFloat("PowerSZZ")) + "|" + Part_Num + ",PowerSZA,"
										+ df.format(rs.getFloat("PowerSZA")) + "|" + Part_Num + ",PowerSZB," + df.format(rs.getFloat("PowerSZB"))
										+ "|" + Part_Num + ",PowerSZC," + df.format(rs.getFloat("PowerSZC"));

						}
					} catch (Exception e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(conn, ps, rs);
					}

					// while (rs.next())
					// {
					// if (write.equals(""))// txtInLine01PowerZYValue
					// divZValueZY
					// write = Part_Num + ",ValueTime," +
					// rs.getString("ValueTime") + "|" + Part_Num + ",PowerZY,"
					// + df.format(rs.getFloat("PowerZY")) + "|" + Part_Num +
					// ",PowerAY," + df.format(rs.getFloat("PowerAY")) + "|"
					// + Part_Num + ",PowerBY," +
					// df.format(rs.getFloat("PowerBY")) + "|" + Part_Num +
					// ",PowerCY,"
					// + df.format(rs.getFloat("PowerCY")) + "|" + Part_Num +
					// ",PowerZW," + df.format(rs.getFloat("PowerZW")) + "|"
					// + Part_Num + ",PowerAW," +
					// df.format(rs.getFloat("PowerAW")) + "|" + Part_Num +
					// ",PowerBW,"
					// + df.format(rs.getFloat("PowerBW")) + "|" + Part_Num +
					// ",PowerCW," + df.format(rs.getFloat("PowerCW")) + "|"
					// + Part_Num + ",PowerFactorZ," +
					// df.format(rs.getFloat("PowerFactorZ")) + "|" + Part_Num +
					// ",PowerFactorA,"
					// + df.format(rs.getFloat("PowerFactorA")) + "|" + Part_Num
					// + ",PowerFactorB," +
					// df.format(rs.getFloat("PowerFactorB"))
					// + "|" + Part_Num + ",PowerFactorC," +
					// df.format(rs.getFloat("PowerFactorC")) + "|" + Part_Num +
					// ",VoltageA,"
					// + df.format(rs.getFloat("VoltageA")) + "|" + Part_Num +
					// ",VoltageB," + df.format(rs.getFloat("VoltageB")) + "|"
					// + Part_Num + ",VoltageC," +
					// df.format(rs.getFloat("VoltageC")) + "|" + Part_Num +
					// ",CurrentA,"
					// + df.format(rs.getFloat("CurrentA")) + "|" + Part_Num +
					// ",CurrentB," + df.format(rs.getFloat("CurrentB")) + "|"
					// + Part_Num + ",CurrentC," +
					// df.format(rs.getFloat("CurrentC")) + "|" + Part_Num +
					// ",Current0,"
					// + df.format(rs.getFloat("Current0")) + "|" + Part_Num +
					// ",PowerSZZ," + df.format(rs.getFloat("PowerSZZ")) + "|"
					// + Part_Num + ",PowerSZA," +
					// df.format(rs.getFloat("PowerSZA")) + "|" + Part_Num +
					// ",PowerSZB,"
					// + df.format(rs.getFloat("PowerSZB")) + "|" + Part_Num +
					// ",PowerSZC," + df.format(rs.getFloat("PowerSZC"));
					// else
					// write += "|" + Part_Num + ",ValueTime," +
					// rs.getString("ValueTime") + "|" + Part_Num + ",PowerZY,"
					// + df.format(rs.getFloat("PowerZY")) + "|" + Part_Num +
					// ",PowerAY," + df.format(rs.getFloat("PowerAY")) + "|"
					// + Part_Num + ",PowerBY," +
					// df.format(rs.getFloat("PowerBY")) + "|" + Part_Num +
					// ",PowerCY,"
					// + df.format(rs.getFloat("PowerCY")) + "|" + Part_Num +
					// ",PowerZW," + df.format(rs.getFloat("PowerZW")) + "|"
					// + Part_Num + ",PowerAW," +
					// df.format(rs.getFloat("PowerAW")) + "|" + Part_Num +
					// ",PowerBW,"
					// + df.format(rs.getFloat("PowerBW")) + "|" + Part_Num +
					// ",PowerCW," + df.format(rs.getFloat("PowerCW")) + "|"
					// + Part_Num + ",PowerFactorZ," +
					// df.format(rs.getFloat("PowerFactorZ")) + "|" + Part_Num +
					// ",PowerFactorA,"
					// + df.format(rs.getFloat("PowerFactorA")) + "|" + Part_Num
					// + ",PowerFactorB," +
					// df.format(rs.getFloat("PowerFactorB"))
					// + "|" + Part_Num + ",PowerFactorC," +
					// df.format(rs.getFloat("PowerFactorC")) + "|" + Part_Num +
					// ",VoltageA,"
					// + df.format(rs.getFloat("VoltageA")) + "|" + Part_Num +
					// ",VoltageB," + df.format(rs.getFloat("VoltageB")) + "|"
					// + Part_Num + ",VoltageC," +
					// df.format(rs.getFloat("VoltageC")) + "|" + Part_Num +
					// ",CurrentA,"
					// + df.format(rs.getFloat("CurrentA")) + "|" + Part_Num +
					// ",CurrentB," + df.format(rs.getFloat("CurrentB")) + "|"
					// + Part_Num + ",CurrentC," +
					// df.format(rs.getFloat("CurrentC")) + "|" + Part_Num +
					// ",Current0,"
					// + df.format(rs.getFloat("Current0")) + "|" + Part_Num +
					// ",PowerSZZ," + df.format(rs.getFloat("PowerSZZ")) + "|"
					// + Part_Num + ",PowerSZA," +
					// df.format(rs.getFloat("PowerSZA")) + "|" + Part_Num +
					// ",PowerSZB,"
					// + df.format(rs.getFloat("PowerSZB")) + "|" + Part_Num +
					// ",PowerSZC," + df.format(rs.getFloat("PowerSZC"));
					//
					// }

				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1, rs1);
		}

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// //System.out.println("write: "+write);
		out.println(write);
		out.flush();
		out.close();

	}

	/**
	 * 将报警xml转换成flash能识别的string
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void ShowAlarmInfo(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String alarmXML = request.getParameter("alarmXML");
		String MonitorLineList = request.getParameter("MonitorLineList");
		String MonitorLineType = request.getParameter("MonitorLineType");
		
		XMLHelper xmlHelper = new XMLHelper();
		NodeList list = xmlHelper.getNodeList(alarmXML, "result");
		ArrayList<String[]> dataList = new ArrayList<String[]>();

		String write = "";
		for (int i = 0; i < list.getLength(); i++)
		{
			Element result = (Element) list.item(i);
			String value = result.getAttribute("value");

			String[] infolist = value.split(";");// value里面的内容一分好隔开

			String[] meterinfo = infolist[0].split(",");// 第一个是电表信息
			String AmMeter_485Address = meterinfo[0];
			String valuetime = meterinfo[1];

			String[] strInfo =
			{ "", "", "", "", "", "ff0000" };
			DecimalFormat df = new DecimalFormat("####0.00");

			String sql = "select Part_ID,Part_Num,Part_Name,AmMeter_ID from (select a.Part_ID,Part_Num,Part_Name,JLID from PD_Part  a,PD_PartInfo03  b where a.Part_ID=b.Part_ID and JLLX=0 and a.Part_ID in("
					+ MonitorLineList + "))x,AmMeter  y where x.JLID=y.AmMeter_ID and   AmMeter_485Address='" + AmMeter_485Address + "' ";
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();
				if (rs.next())
				{
					strInfo[0] = AmMeter_485Address;
					strInfo[1] = rs.getString("Part_ID");
					strInfo[2] = rs.getString("Part_Num");
					strInfo[3] = rs.getString("Part_Name");
					if(MonitorLineType.equals("ID"))
					{
						strInfo[2]		=rs.getString("Part_ID");			
					}
					strInfo[4] = "<a href=\"event:OverInfo|" + strInfo[1] + "\" alt='点击查看详情'>" + strInfo[3] + "(" + strInfo[2]
							+ ")</a>  <a href=\"event:CloseOverInfo|" + strInfo[2] + "\" alt='点击查看详情'>关闭</a> \n ";

					for (int j = 1; j < infolist.length; j++)
					{
						String[] valuelist = infolist[j].split(",");
						if (valuelist.length == 3)
						{
							float ZValue = Float.parseFloat(valuelist[0]);
							float XValue = Float.parseFloat(valuelist[1]);

							String strPerValue = "--";
							if (XValue != 0)
							{
								float PerValue = Math.abs(ZValue - XValue) * 100 / XValue;
								strPerValue = df.format(PerValue);
							}
							strInfo[4] += "报   警：" + valuelist[2] + " \n 抄读值：" + valuelist[0] + " \n 限   值：" + valuelist[1] + " \n 越线率："
									+ strPerValue + "%\n";
						}
					}

					dataList.add(strInfo);

				}
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}

			// if (rs.next())
			// {
			// strInfo[0] = AmMeter_485Address;
			// strInfo[1] = rs.getString("Part_ID");
			// strInfo[2] = rs.getString("Part_Num");
			// strInfo[3] = rs.getString("Part_Name");
			// strInfo[4] = "<a href=\"event:OverInfo|" + strInfo[1] +
			// "\" alt='点击查看详情'>" + strInfo[3] + "(" + strInfo[2]
			// + ")</a>  <a href=\"event:CloseOverInfo|" + strInfo[2] +
			// "\" alt='点击查看详情'>关闭</a> \n ";
			//
			// for (int j = 1; j < infolist.length; j++)
			// {
			// String[] valuelist = infolist[j].split(",");
			// if (valuelist.length == 3)
			// {
			// float ZValue = Float.parseFloat(valuelist[0]);
			// float XValue = Float.parseFloat(valuelist[1]);
			//
			// String strPerValue = "--";
			// if (XValue != 0)
			// {
			// float PerValue = Math.abs(ZValue - XValue) * 100 / XValue;
			// strPerValue = df.format(PerValue);
			// }
			// strInfo[4] += "报   警：" + valuelist[2] + " \n 抄读值：" + valuelist[0]
			// + " \n 限   值：" + valuelist[1] + " \n 越线率：" + strPerValue
			// + "%\n";
			// }
			// }
			//
			// dataList.add(strInfo);
			//
			// }

			strInfo[4] += "时   间：" + valuetime;

		}

		for (int j = 0; j < dataList.size(); j++)
		{// AmMeter_485Address,Part_ID,Part_Num,Part_Name,Text,Color
			String[] strInfo = dataList.get(j);
			if (write == "")
				write = strInfo[0] + "‖" + strInfo[1] + "‖" + strInfo[2] + "‖" + strInfo[3] + "‖" + strInfo[4] + "‖" + strInfo[5];
			else
				write += "¦" + strInfo[0] + "‖" + strInfo[1] + "‖" + strInfo[2] + "‖" + strInfo[3] + "‖" + strInfo[4] + "‖" + strInfo[5];

		}

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// System.out.println("write: "+write);
		out.println(write);
		out.flush();
		out.close();

	}

	public void GetOverInfo(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String write = "";
		String MonitorLineList="0";
		if(request.getParameter("MonitorLineList")!=""&&request.getParameter("MonitorLineList")!=null){
			MonitorLineList = request.getParameter("MonitorLineList");
		}
		String MonitorLineType ="";//  request.getParameter("MonitorLineType");
		if(request.getParameter("MonitorLineType")!=""&&request.getParameter("MonitorLineType")!=null){
			MonitorLineType = request.getParameter("MonitorLineType");
		}
		String sql = "select PD_OverID,a.OverStyle,(select DictionaryValue_Value from DictionaryValue where Dictionary_ID=29 and DictionaryValue_Num=a.OverStyle)OverStyleName,a.Part_ID,a.ValueTime,ZValue,XValue,Part_Num,Part_Name  from PD_Over  a,PD_Part  c, (select Part_ID,OverStyle,max(ValueTime)ValueTime from PD_Over where Part_ID in("
				+ MonitorLineList
				+ ")"
				+ "and valueTime>(current_date-1/24) group by Part_ID,OverStyle)  b where a.Part_ID=b.Part_ID and  a.Part_ID=c.Part_ID and a.ValueTime=b.ValueTime and a.OverStyle=b.OverStyle";
		DecimalFormat df = new DecimalFormat("####0.00");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next())
			{	
				String Part_Num=rs.getString("Part_Num") ;
				if(MonitorLineType.equals("ID"))
			{
					Part_Num	=rs.getString("Part_ID");			
			}
				if (write == "")// txtInLine01PowerZYValue
					write = rs.getString("PD_OverID") + "," + rs.getString("OverStyle") + "," + rs.getString("OverStyleName") + ","
							+ rs.getString("ValueTime") + "," + df.format(rs.getFloat("ZValue")) + "," + df.format(rs.getFloat("XValue")) + ","
							+ Part_Num + "," + rs.getString("Part_Name");
				else
					write += "|" + rs.getString("PD_OverID") + "," + rs.getString("OverStyle") + "," + rs.getString("OverStyleName") + ","
							+ rs.getString("ValueTime") + "," + df.format(rs.getFloat("ZValue")) + "," + df.format(rs.getFloat("XValue")) + ","
							+ Part_Num + "," + rs.getString("Part_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		// while (rs.next())
		// {
		// if (write == "")// txtInLine01PowerZYValue
		// write = rs.getString("PD_OverID") + "," + rs.getString("OverStyle") +
		// "," + rs.getString("OverStyleName") + ","
		// + rs.getString("ValueTime") + "," + df.format(rs.getFloat("ZValue"))
		// + "," + df.format(rs.getFloat("XValue")) + ","
		// + rs.getString("Part_Num") + "," + rs.getString("Part_Name");
		// else
		// write += "|" + rs.getString("PD_OverID") + "," +
		// rs.getString("OverStyle") + "," + rs.getString("OverStyleName") + ","
		// + rs.getString("ValueTime") + "," + df.format(rs.getFloat("ZValue"))
		// + "," + df.format(rs.getFloat("XValue")) + ","
		// + rs.getString("Part_Num") + "," + rs.getString("Part_Name");
		// }

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// //System.out.println("write: "+write);
		out.println(write);
		out.flush();
		out.close();

	}

	private void getTempWetHistory(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException
	{
		SimpleDateFormat dft = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AmMeterPDDataDao apd = new AmMeterPDDataDao();
		int flag = 0;
		if (request.getParameter("flag") != null && request.getParameter("flag") != "")
			flag = Integer.parseInt(request.getParameter("flag"));
		else
		{
			return;
		}

		String start = null;
		if (request.getParameter("Start_Time") != null)
			start = request.getParameter("Start_Time");

		String end = null;
		if (request.getParameter("End_Time") != null)
			end = request.getParameter("End_Time");

		List<TempWETDevDatasModel> list = apd.getTempWETDevDatasHistory(flag, start, end);

		int tempDate = -1;
		JSONArray json = new JSONArray();
		if (list != null && list.size() != 0)
		{
			for (TempWETDevDatasModel a : list)
			{
				if (a.getRecordTimeDate().getDate() != tempDate)
				{
					tempDate = a.getRecordTimeDate().getDate();
					JSONObject jo = new JSONObject();
					jo.put("Date", a.getRecordTimeDate().getDate());

					JSONObject jo1 = new JSONObject();
					jo1.put("RecordTime", df.format(a.getRecordTimeDate()));
					jo1.put("Time", dft.format(a.getRecordTimeDate()));
					jo1.put("WETValue", a.getWetvalue());
					jo1.put("Temp", a.getTemperature());
					JSONArray itemList = new JSONArray();
					itemList.add(jo1);

					jo.put("item", itemList);

					json.add(jo);
				} else
				{
					JSONObject jo1 = new JSONObject();
					jo1.put("RecordTime", df.format(a.getRecordTimeDate()));
					jo1.put("Time", dft.format(a.getRecordTimeDate()));
					jo1.put("WETValue", a.getWetvalue());
					jo1.put("Temp", a.getTemperature());
					json.getJSONObject(json.size() - 1).getJSONArray("item").add(jo1);
				}

			}
		}
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getPowerNetLineCurrentDatas(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException,
			IOException
	{
		AmMeterPDDataDao apd = new AmMeterPDDataDao();
		int flag = 0;
		if (request.getParameter("flag") != null && request.getParameter("flag") != "")
		{
			flag = Integer.parseInt(request.getParameter("flag"));
		} else
		{
			return;
		}
		// JSONArray json = new JSONArray();
		// JSONObject jo = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		map = apd.getCurrentTempWet(flag);

		List<CurrentPartDatasModel> list = apd.getPowerNetLineCurrentDatas(flag);

		Gson gson = new Gson();
		String data = gson.toJson(new Object[]
		{ map, list });
		PrintWriter out = response.getWriter();
		out.println(data);
		out.flush();
		out.close();
	}

	private void saveXmlString(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException
	{
		String XMLString = null;
		String XMLName = null;
		int Part_Id = 0;
		if (request.getParameter("XMLString") != null)
			XMLString = request.getParameter("XMLString");
		if (request.getParameter("XMLName") != null)
			XMLName = request.getParameter("XMLName");

		if (request.getParameter("Part_Id") != null)
			Part_Id = Integer.parseInt(request.getParameter("Part_Id"));

		// save to this filename
		String prefix = XMLName.substring(XMLName.lastIndexOf(".") + 1);
		if (prefix.equalsIgnoreCase("xml"))
		{
			String sql = "update PD_PartInfo01 set XMLName='" + XMLName + "'  where Part_ID=" + Part_Id;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			} catch (SQLException e)
			{
				e.printStackTrace();
				PrintWriter out = response.getWriter();
				out.println("编辑失败");
				out.flush();
				out.close();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}
			//String path = this.getServletContext().getRealPath("/") + "PowerNet\\XMLData\\" + XMLName;
			String path = this.getServletContext().getRealPath("/") + "/PowerNet/XMLData/" + XMLName;
			File file = new File(path);  
	        if(file.exists())  
	        file.delete();  
	        BufferedWriter  writer;  
	  
	        try  
	        {  
	            FileOutputStream fos = new FileOutputStream(path,true);  
	            writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));  
	            writer.append(XMLString);  
	            writer.close();  
				PrintWriter out = response.getWriter();
				out.println("编辑成功");
				out.flush();
				out.close();
	        }  
	        catch (IOException e)  
	        {  
	            e.printStackTrace(); 
				PrintWriter out = response.getWriter();
				out.println("编辑失败");
				out.flush();
				out.close();
	        }
//			try
//			{
//				String fileName = this.getServletContext().getRealPath("/") + "/PowerNet/XMLData/" + XMLName;
//				File file = new File(fileName);
//
//				if (!file.exists())
//				{
//					file.createNewFile();
//				}
//				// use FileWriter to write file
//				FileWriter fw = new FileWriter(file.getAbsoluteFile());
//				BufferedWriter bw = new BufferedWriter(fw);
//				bw.write(XMLString);
//				bw.close();
//				PrintWriter out = response.getWriter();
//				out.println("编辑成功");
//				out.flush();
//				out.close();
//			} catch (MalformedURLException e)
//			{
//				e.printStackTrace();
//				PrintWriter out = response.getWriter();
//				out.println("编辑失败");
//				out.flush();
//				out.close();
//			} catch (IOException e)
//			{
//				e.printStackTrace();
//				PrintWriter out = response.getWriter();
//				out.println("编辑失败");
//				out.flush();
//				out.close();
//			}

		} else
		{

			PrintWriter out = response.getWriter();
			out.println("数据地址格式错误");
			out.flush();
			out.close();
		}
	}

	private void copyXML(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException
	{
		int Part_Id_2 = 0;
		String XMLName = null;
		

		if (request.getParameter("XMLName") != null)
			XMLName = request.getParameter("XMLName");
		int Part_Id = 0;
		if (request.getParameter("Part_Id") != null)
			Part_Id = Integer.parseInt(request.getParameter("Part_Id"));
		if (request.getParameter("Part_Id_2") != null)
			Part_Id_2 = Integer.parseInt(request.getParameter("Part_Id_2"));
		String XMLName2 = null;
		// save to this filename
		String prefix = XMLName.substring(XMLName.lastIndexOf(".") + 1);
		if (prefix.equalsIgnoreCase("xml"))
		{
			String sql = "update PD_PartInfo01 set XMLName='" + XMLName + "'  where Part_ID=" + Part_Id;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			} catch (SQLException e)
			{
				e.printStackTrace();
				PrintWriter out = response.getWriter();
				out.println("克隆失败");
				out.flush();
				out.close();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}
			String sql2 = "select XMLName from PD_PartInfo01 where Part_ID=" + Part_Id_2;
			Connection conn2 = null;
			PreparedStatement ps2 = null;
			ResultSet rs2 = null;
			try
			{
				conn2 = ConnDB.getConnection();
				ps2 = conn2.prepareStatement(sql2);
				rs2 = ps2.executeQuery();
				if (rs2.next())
				{
					XMLName2 = rs2.getString("XMLName");
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
				PrintWriter out = response.getWriter();
				out.println("克隆失败");
				out.flush();
				out.close();
			} finally
			{
				ConnDB.release(conn2, ps2, rs2);
			}
			boolean b = CopyXMLMethod(XMLName2, XMLName);
			if (b == false)
			{
				PrintWriter out = response.getWriter();
				out.println("克隆失败");
				out.flush();
				out.close();
			} else
			{
				PrintWriter out = response.getWriter();
				out.println("克隆成功");
				out.flush();
				out.close();
			}

		} else
		{

			PrintWriter out = response.getWriter();
			out.println("数据地址格式错误");
			out.flush();
			out.close();
		}
	}

	private boolean CopyXMLMethod(String fileName1, String fileName2)
	{
		try
		{
			FileInputStream fin = new FileInputStream(new File(this.getServletContext().getRealPath("/") + "/PowerNet/XMLData/" + fileName1));
			FileOutputStream fout = new FileOutputStream(new File(this.getServletContext().getRealPath("/") + "/PowerNet/XMLData/" + fileName2));
			int bytesRead;
			byte[] buf = new byte[4 * 1024]; // 4K
			while ((bytesRead = fin.read(buf)) != -1)
			{
				fout.write(buf, 0, bytesRead);
			}
			fout.flush();
			fout.close();
			fin.close();
			return true;
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
			return false;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	private void getAllPartRoom(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException,
			IOException
	{
		AmMeterPDDataDao apd = new AmMeterPDDataDao();
		List<PDPartRoom> list=apd.getAllPartRoom();
		Gson gson = new Gson();
		String data = gson.toJson(list);
//		System.out.println(data);
		PrintWriter out = response.getWriter();
		out.println(data);
		out.flush();
		out.close();
	}
	private void bandChooseInfo(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException,
	IOException
	{
		int Part_Id = 0;
		if (request.getParameter("Part_Id") != null)
			Part_Id = Integer.parseInt(request.getParameter("Part_Id"));
		AmMeterPDDataDao apd = new AmMeterPDDataDao();
		PDPartRoom PD=apd.getPartRoomByID(Part_Id);
//		Gson gson = new Gson();
		String data = PD.getPart_id()+"*"+PD.getPartStyle_ID()+"*"+PD.getPart_Num()+"*"+PD.getPart_Name();
//		System.out.println(data);
		PrintWriter out = response.getWriter();
		out.println(data);
		out.flush();
		out.close();
	}
}
