package com.sf.energy.water.manager.current.servlet;

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
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.events.EndDocument;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.common.base.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sf.energy.powernet.dao.AmMeterPDDataDao;
import com.sf.energy.powernet.model.PDPartRoom;
import com.sf.energy.powernet.model.TempWETDevDatasModel;
import com.sf.energy.project.system.dao.PumpDao;
import com.sf.energy.project.system.model.PumpCurrentManagerModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.dao.CurrentMeasureDao;
import com.sf.energy.water.manager.current.dao.PressureDevDatasModel;
import com.sf.energy.water.manager.current.model.CurrentManagerModel;

public class PumpCurrentManagerServlet extends HttpServlet
{

String TheEditType="";
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		TheEditType=request.getParameter("TheEditType");
		if(TheEditType==null)
			TheEditType="";
		if ("whenSelectedSchoolFunction".equals(method))
		{
			try
			{
				whenSelectedSchoolFunction(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if ("whenSelectedAreaFunction".equals(method))
		{
			try
			{
				whenSelectedAreaFunction(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if ("getFusionChart".equals(method))
		{
			try
			{
				getFusionChart(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if ("getPumpMonitorSWF".equals(method))
		{
			try
			{
				getPumpMonitorSWF(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if ("getPumpDevHistory".equals(method))
		{
			try
			{
				getPumpDevHistory(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if ("GetMonitorLineInfo".equals(method))
		{
			try
			{
				GetMonitorLineInfo(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if ("queryPressureDev_CountByPumpID".equals(method))
		{
			try
			{
				queryPressureDev_CountByPumpID(request, response);
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
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	/**
	 * 压力计个数
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void queryPressureDev_CountByPumpID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int PumpID = 0;
		if (request.getParameter("PumpID") != null)
		{
			PumpID = Integer.parseInt(request.getParameter("PumpID"));
		}
		PumpDao apd = new PumpDao();
		try
		{
			int count = apd.queryPressureDev_CountByPumpID(PumpID);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(count);
			out.flush();
			out.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 加载哪个flash
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getPumpMonitorSWF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
			int PumpID = Integer.parseInt(flag);
			PumpDao apd = new PumpDao();
			try
			{
				String xmlString = apd.getFlashXML(PumpID);
				if(TheEditType.equals("HWJD"))
				{
					 xmlString = apd.getFlashHWJDXML(PumpID);
				}
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
	 * 获取Flash上的数据
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void GetMonitorLineInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		String Pump_ID = request.getParameter("Pump_ID");
		DecimalFormat df = new DecimalFormat("####0.00");

		String write = "";

		String sql = "select Pump_JiliangID ,Pump_ID ,Pump_Num  from Pump where Pump_ID=" + Pump_ID;
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
				String ammeterids = rs1.getString("Pump_JiliangID");
				String[] ammeteridlist = ammeterids.split(",");
				String Part_Num = rs1.getString("Pump_Num");
				for (String ammeterid : ammeteridlist)
				{
					if ((ammeterid != null) && (!"".equals(ammeterid)))
					{
						sql = "select x.ValueTime,PowerZY,PowerAY,PowerBY,PowerCY,PowerZW,PowerAW,PowerBW,PowerCW,PowerFactorZ, "
								+ "	PowerFactorA,PowerFactorB,PowerFactorC,VoltageA,VoltageB,VoltageC,CurrentA,CurrentB, "
								+ "	CurrentC,Current0,PowerSZZ,PowerSZA,PowerSZB,PowerSZC from (select max(ValueTime)ValueTime from ZPDDATAS"
								+ ammeterid + ")x,ZPDDATAS" + ammeterid + "  y where x.ValueTime=y.ValueTime ";
						Connection conn = null;
						PreparedStatement ps = null;
						ResultSet rs = null;
						try
						{
							conn = ConnDB.getConnection();
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							Part_Num = "Meter" + ammeterid;
							while (rs.next())
							{
								if (write.equals(""))// txtInLine01PowerZYValue
									// divZValueZY
									write = Part_Num + ",ValueTime," + rs.getString("ValueTime") + "|" + Part_Num + ",PowerZY,"
											+ df.format(rs.getFloat("PowerZY")) + "|" + Part_Num + ",PowerAY," + df.format(rs.getFloat("PowerAY"))
											+ "|" + Part_Num + ",PowerBY," + df.format(rs.getFloat("PowerBY")) + "|" + Part_Num + ",PowerCY,"
											+ df.format(rs.getFloat("PowerCY")) + "|" + Part_Num + ",PowerZW," + df.format(rs.getFloat("PowerZW"))
											+ "|" + Part_Num + ",PowerAW," + df.format(rs.getFloat("PowerAW")) + "|" + Part_Num + ",PowerBW,"
											+ df.format(rs.getFloat("PowerBW")) + "|" + Part_Num + ",PowerCW," + df.format(rs.getFloat("PowerCW"))
											+ "|" + Part_Num + ",PowerFactorZ," + df.format(rs.getFloat("PowerFactorZ")) + "|" + Part_Num
											+ ",PowerFactorA," + df.format(rs.getFloat("PowerFactorA")) + "|" + Part_Num + ",PowerFactorB,"
											+ df.format(rs.getFloat("PowerFactorB")) + "|" + Part_Num + ",PowerFactorC,"
											+ df.format(rs.getFloat("PowerFactorC")) + "|" + Part_Num + ",VoltageA,"
											+ df.format(rs.getFloat("VoltageA")) + "|" + Part_Num + ",VoltageB," + df.format(rs.getFloat("VoltageB"))
											+ "|" + Part_Num + ",VoltageC," + df.format(rs.getFloat("VoltageC")) + "|" + Part_Num + ",CurrentA,"
											+ df.format(rs.getFloat("CurrentA")) + "|" + Part_Num + ",CurrentB," + df.format(rs.getFloat("CurrentB"))
											+ "|" + Part_Num + ",CurrentC," + df.format(rs.getFloat("CurrentC")) + "|" + Part_Num + ",Current0,"
											+ df.format(rs.getFloat("Current0")) + "|" + Part_Num + ",PowerSZZ," + df.format(rs.getFloat("PowerSZZ"))
											+ "|" + Part_Num + ",PowerSZA," + df.format(rs.getFloat("PowerSZA")) + "|" + Part_Num + ",PowerSZB,"
											+ df.format(rs.getFloat("PowerSZB")) + "|" + Part_Num + ",PowerSZC," + df.format(rs.getFloat("PowerSZC"));
								else
									write += "|" + Part_Num + ",ValueTime," + rs.getString("ValueTime") + "|" + Part_Num + ",PowerZY,"
											+ df.format(rs.getFloat("PowerZY")) + "|" + Part_Num + ",PowerAY," + df.format(rs.getFloat("PowerAY"))
											+ "|" + Part_Num + ",PowerBY," + df.format(rs.getFloat("PowerBY")) + "|" + Part_Num + ",PowerCY,"
											+ df.format(rs.getFloat("PowerCY")) + "|" + Part_Num + ",PowerZW," + df.format(rs.getFloat("PowerZW"))
											+ "|" + Part_Num + ",PowerAW," + df.format(rs.getFloat("PowerAW")) + "|" + Part_Num + ",PowerBW,"
											+ df.format(rs.getFloat("PowerBW")) + "|" + Part_Num + ",PowerCW," + df.format(rs.getFloat("PowerCW"))
											+ "|" + Part_Num + ",PowerFactorZ," + df.format(rs.getFloat("PowerFactorZ")) + "|" + Part_Num
											+ ",PowerFactorA," + df.format(rs.getFloat("PowerFactorA")) + "|" + Part_Num + ",PowerFactorB,"
											+ df.format(rs.getFloat("PowerFactorB")) + "|" + Part_Num + ",PowerFactorC,"
											+ df.format(rs.getFloat("PowerFactorC")) + "|" + Part_Num + ",VoltageA,"
											+ df.format(rs.getFloat("VoltageA")) + "|" + Part_Num + ",VoltageB," + df.format(rs.getFloat("VoltageB"))
											+ "|" + Part_Num + ",VoltageC," + df.format(rs.getFloat("VoltageC")) + "|" + Part_Num + ",CurrentA,"
											+ df.format(rs.getFloat("CurrentA")) + "|" + Part_Num + ",CurrentB," + df.format(rs.getFloat("CurrentB"))
											+ "|" + Part_Num + ",CurrentC," + df.format(rs.getFloat("CurrentC")) + "|" + Part_Num + ",Current0,"
											+ df.format(rs.getFloat("Current0")) + "|" + Part_Num + ",PowerSZZ," + df.format(rs.getFloat("PowerSZZ"))
											+ "|" + Part_Num + ",PowerSZA," + df.format(rs.getFloat("PowerSZA")) + "|" + Part_Num + ",PowerSZB,"
											+ df.format(rs.getFloat("PowerSZB")) + "|" + Part_Num + ",PowerSZC," + df.format(rs.getFloat("PowerSZC"));

							}
						} catch (SQLException e)
						{
							e.printStackTrace();
						} finally
						{
							ConnDB.release(conn, ps, rs);
						}
					}
				}

			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1, rs1);
		}
		String sqlPump = "select PressureDev.PressureDev_NUM,PressureDev.PRESSUREDEV_ID from PressureDev,Pump where Pump.pump_id=PressureDev.pump_id and Pump.pump_id="
				+ Pump_ID;
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sqlPump);
			rs2 = ps2.executeQuery();
			while (rs2.next())
			{
				String pressureDev_ID = rs2.getString("PressureDev_ID");
				String PressureDev_num = rs2.getString("PressureDev_num");
				String sqldev = "select * from pressureDevdatas where pressureDev_id=" + pressureDev_ID + " and rownum<=1  ORDER BY VALUETIME DESC";
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(sqldev);
					rs = ps.executeQuery();
					String Part_Num = PressureDev_num;
					while (rs.next())
					{

						if (write.equals(""))// txtInLine01PowerZYValue
							// divZValueZY
							write = Part_Num + ",PressMeter," +df.format( rs.getFloat("PressureValue"));
						else
							write += "|" + Part_Num + ",PressMeter," + df.format(rs.getFloat("PressureValue"));

					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn, ps, rs);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn2, ps2, rs2);
		}

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// System.out.println("write: "+write);
		out.println(write);
		out.flush();
		out.close();

	}

	/**
	 * 选择水泵房的水泵房集中监测Fusionchart
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	public void getFusionChart(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		DecimalFormat df = new DecimalFormat("####0.00");
		int pump_id = 0;
		List<String> PressureList = new ArrayList<String>();
		PumpDao cd = new PumpDao();
		if (request.getParameter("pump_id") != null)
		{
			pump_id = Integer.parseInt(request.getParameter("pump_id"));
		}
		PressureList = cd.getPressure_IDByPumpID(pump_id);
		JSONArray json = new JSONArray();
		JSONArray json2 = new JSONArray();
		JSONObject jo = new JSONObject();
		for (int i = 0; i < PressureList.size(); i++)
		{
			String sql = "select a.*,b.PressureDev_name from PressureDevDatas a,PressureDev b where a.pressureDev_id=b.PressureDev_id and a.valueTime>TO_DATE (to_char(sysdate,'yyyy-mm-dd'),'YYYY/MM/DD HH24:MI:SS' ) and a.PressureDev_id= "
					+ PressureList.get(i)+"  order by a.valueTime ";
			Connection conn1 = null;
			PreparedStatement ps1 =null;
			ResultSet rs1 =null;
			try{
				conn1 = ConnDB.getConnection();
				ps1 = conn1.prepareStatement(sql);
				rs1 = ps1.executeQuery();
				json2 = new JSONArray();
				while (rs1.next())
				{
					jo = new JSONObject();
					jo.put("PressureDev_ID", rs1.getString("PressureDev_ID"));
					jo.put("PressureDev_name", rs1.getString("PressureDev_name"));
					jo.put("valuetime", rs1.getString("valuetime"));
					jo.put("PressureValue",df.format(rs1.getFloat("PressureValue")));
					json2.add(jo);
				}
				json.add(json2);
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn1, ps1,rs1);
			}

		}

		// String result = json.toJson(PressureList);

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 选择学校的水泵房集中监测
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	public void whenSelectedSchoolFunction(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PumpDao cd = new PumpDao();
		List<PumpCurrentManagerModel> list = new ArrayList<PumpCurrentManagerModel>();
		String sortName = "Area_Name";
		String order = "asc";

		if (request.getParameter("sortName") != null)
		{
			sortName = request.getParameter("sortName");
		}
		if (request.getParameter("order") != null)
		{
			if (request.getParameter("order").equals("desc"))
			{
				order = "desc";
			} else if (request.getParameter("order").equals("asc"))
			{
				order = "asc";
			}

		}
		list = cd.listAllPumpData(sortName, order);

		Gson json = new Gson();
		String result = json.toJson(list);

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// //System.out.println(result);
		out.println(result);
		out.flush();
		out.close();

	}

	public void whenSelectedAreaFunction(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PumpDao cd = new PumpDao();

		Calendar cal = Calendar.getInstance();// 使用日历类

		List<PumpCurrentManagerModel> list = new ArrayList<PumpCurrentManagerModel>();
		String sortName = "Area_Name";
		String order = "asc";
		String area_id = "";
		if (request.getParameter("sortName") != null)
		{
			sortName = request.getParameter("sortName");
		}
		if (request.getParameter("area_id") != null)
		{
			area_id = request.getParameter("area_id");
		}
		if (request.getParameter("order") != null)
		{
			if (request.getParameter("order").equals("desc"))
			{
				order = "desc";
			} else if (request.getParameter("order").equals("asc"))
			{
				order = "asc";
			}

		}
		list = cd.listPumpDataByArea(sortName, order, area_id);

		Gson json = new Gson();
		String result = json.toJson(list);

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// //System.out.println(result);
		out.println(result);
		out.flush();
		out.close();

	}
	/**
	 * 选择水泵房的水泵房集中监测Fusionchart
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	public void getPumpDevHistory(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		SimpleDateFormat dft = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int pump_id = 0;
		List<String> PressureList = new ArrayList<String>();
		PumpDao cd = new PumpDao();
		if (request.getParameter("pump_id") != null)
		{
			pump_id = Integer.parseInt(request.getParameter("pump_id"));
		}
		String start = null;
        if (request.getParameter("Start_Time") != null)
            start = request.getParameter("Start_Time");

        String end = null;
        if (request.getParameter("End_Time") != null)
            end = request.getParameter("End_Time");
		PressureList = cd.getPressure_IDByPumpID(pump_id);


		List<List<PressureDevDatasModel>> list = getPressureDevDatasHistory(PressureList,start,end);

	        int tempDate = -1;
	        JSONArray json = new JSONArray();
	        if (list != null && list.size() != 0)
	        {
	            for (int j=0;j<list.get(0).size(); j++)
	            {
	            	PressureDevDatasModel a=list.get(0).get(j);
	                if (a.getRecordTimeDate().getDate() != tempDate)
	                {
	                    tempDate = a.getRecordTimeDate().getDate();
	                    JSONObject jo = new JSONObject();
	                    jo.put("Date", a.getRecordTimeDate().getDate());

	                    JSONObject jo1 = new JSONObject();
	                    jo1.put("RecordTime", df.format(a.getRecordTimeDate()));
	                    jo1.put("Time", dft.format(a.getRecordTimeDate()));
	                    String value="";
	                    for(int i=0;i<list.size();i++){
	                    	if(list.get(i).size()!=0){
	                    		value+=(list.get(i).get(j).getPressureDev_name()+":"+list.get(i).get(j).getPressureValue()+"\n");
	                    	}
	                    }
	                    jo1.put("value", value);
	                    JSONArray itemList = new JSONArray();
	                    itemList.add(jo1);

	                    jo.put("item", itemList);

	                    json.add(jo);
	                }
	                else
	                {
	                    JSONObject jo1 = new JSONObject();
	                    jo1.put("RecordTime", df.format(a.getRecordTimeDate()));
	                    jo1.put("Time", dft.format(a.getRecordTimeDate()));
	                    String value="";
	                    for(int i=0;i<list.size();i++){
	                    	if(list.get(i).size()!=0){
	                    		value+=(list.get(i).get(j).getPressureDev_name()+":"+list.get(i).get(j).getPressureValue()+"\n");
	                    	}
	                    }
	                    jo1.put("value", value);
	                    json.getJSONObject(json.size() - 1).getJSONArray("item")
	                            .add(jo1);
	                }

	            }
	        }
	        PrintWriter out = response.getWriter();
	        out.println(json.toString());
	        out.flush();
	        out.close();

	}
	public List<List<PressureDevDatasModel>> getPressureDevDatasHistory(List<String> PressureList,String start,String end ){
		List<List<PressureDevDatasModel>> list=new ArrayList<List<PressureDevDatasModel>>();
		SimpleDateFormat dft = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < PressureList.size(); i++)
		{
			String sql = "select a.PressureDev_ID,to_char(a.valuetime,'yyyy-MM-dd HH24:mi:ss') valuetime,a.PressureValue,b.PressureDev_name from PressureDevDatas a,PressureDev b where a.pressureDev_id=b.PressureDev_id and a.valueTime between TO_DATE ('"+start+"','YYYY-MM-DD HH24:MI:SS' ) and  TO_DATE ('"+end+"','YYYY-MM-DD HH24:MI:SS' ) and a.PressureDev_id= "
					+ PressureList.get(i)+"  order by a.valueTime ";
			Connection conn1 = null;
			PreparedStatement ps1 =null;
			ResultSet rs1 =null;
			List<PressureDevDatasModel> ls=new ArrayList<PressureDevDatasModel>();
			try{
				conn1 = ConnDB.getConnection();
				ps1 = conn1.prepareStatement(sql);
//				System.out.println(sql);
				rs1 = ps1.executeQuery();
				
				while (rs1.next())
				{
					PressureDevDatasModel pd=new PressureDevDatasModel();
					
					pd.setPressureDev_ID(rs1.getInt("PressureDev_ID"));
					pd.setPressureDev_name(rs1.getString("PressureDev_name"));
					try
					{
						pd.setRecordTimeDate(df.parse(rs1.getString("valuetime")));
					} catch (ParseException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pd.setPressureValue(rs1.getFloat("PressureValue"));
					ls.add(pd);
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn1, ps1,rs1);
			}
			list.add(ls);
		}
		
		return list;
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
			String sql = "update Pump set XMLName='" + XMLName + "'  where Pump_ID=" + Part_Id;
			if(TheEditType.equals("HWJD"))
			{
				 sql = "update Pump set HWJDXMLName='" + XMLName + "'  where Pump_ID=" + Part_Id;
			}
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

			try
			{
				String path = this.getServletContext().getRealPath("/") + "/WaterManager/XMLData/" + XMLName;
//				File file = new File(fileName);
//
//				if (!file.exists())
//				{
//					file.createNewFile();
//				}
				// use FileWriter to write file
//				FileWriter fw = new FileWriter(file.getAbsoluteFile());
//				BufferedWriter bw = new BufferedWriter(fw);
//				bw.write(XMLString);
//				bw.close();
//				PrintWriter out = response.getWriter();
//				out.println("编辑成功");
//				out.flush();
//				out.close();
				File file = new File(path);  
		        if(file.exists())  
		        file.delete();  
		        BufferedWriter  writer;  
				FileOutputStream fos = new FileOutputStream(path,true);  
	            writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));  
	            writer.append(XMLString);  
	            writer.close();  
				PrintWriter out = response.getWriter();
				out.println("编辑成功");
				out.flush();
				out.close();
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
				PrintWriter out = response.getWriter();
				out.println("编辑失败");
				out.flush();
				out.close();
			} catch (IOException e)
			{
				e.printStackTrace();
				PrintWriter out = response.getWriter();
				out.println("编辑失败");
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

	private void copyXML(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException
	{
		int Part_Id_2 = 0;
		String XMLName = null;
		int Part_Id = 0;

		if (request.getParameter("XMLName") != null)
			XMLName = request.getParameter("XMLName");

		if (request.getParameter("Part_Id") != null)
			Part_Id = Integer.parseInt(request.getParameter("Part_Id"));
		if (request.getParameter("Part_Id_2") != null)
			Part_Id_2 = Integer.parseInt(request.getParameter("Part_Id_2"));
		String XMLName2 = null;
		// save to this filename
		String prefix = XMLName.substring(XMLName.lastIndexOf(".") + 1);
		if (prefix.equalsIgnoreCase("xml"))
		{
			String sql = "update Pump set XMLName='" + XMLName + "'  where Pump_ID=" + Part_Id;
			if(TheEditType.equals("HWJD"))
			{
				 sql = "update Pump set HWJDXMLName='" + XMLName + "'  where Pump_ID=" + Part_Id;
			}
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
			String sql2 = "select XMLName from pump where Pump_ID=" + Part_Id_2;
			if(TheEditType.equals("HWJD"))
			{
				sql2 = "select HWJDXMLName as XMLName  from pump where Pump_ID=" + Part_Id_2;
			}
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
			FileInputStream fin = new FileInputStream(new File(this.getServletContext().getRealPath("/") + "/WaterManager/XMLData/" + fileName1));
			FileOutputStream fout = new FileOutputStream(new File(this.getServletContext().getRealPath("/") + "/WaterManager/XMLData/" + fileName2));
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
		List<PDPartRoom> list=new ArrayList<PDPartRoom>();
		String sql = "select * from Pump ";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				PDPartRoom pdRoom=new PDPartRoom();
				pdRoom.setPart_id(rs.getInt("pump_id"));
				pdRoom.setPart_Name(rs.getString("pump_name"));
				
				pdRoom.setXMLName(rs.getString("XMLNAME"));
				list.add(pdRoom);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		Gson gson = new Gson();
		String data = gson.toJson(list);
//		System.out.println(data);
		PrintWriter out = response.getWriter();
		out.println(data);
		out.flush();
		out.close();
	}
}
