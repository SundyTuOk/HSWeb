package com.sf.energy.statistics.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.DataExportDao;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.dao.AmHelperImpl;
import com.sf.energy.statistics.model.ArcAmDetailData;

public class ArcAmDetailServlet extends HttpServlet
{

	DecimalFormat dFormat = new DecimalFormat("#0.00");
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		// System.out.println("method:" + method);

		if ("showContent".equals(method))
			showContent(request, response);
		if ("exportContent".equals(method))
			exportContent(request, response);

	}

	private void showContent(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		int ArcAmDetail_ArcID = -1; // 表示选择选择所有建筑
		int AreaID = -1; // 表示选择所有区域
		Gson gson_ArcAmDetail = new Gson();

		String arcId = "";
		String areaId = "";
		String st = "";
		String et = "";
		String count = "";
		String index = "";
		int price_id=0;
		int part_id=0;
		if (request.getParameter("aid") != null)
		{
			arcId = request.getParameter("aid");
		}
		if (request.getParameter("areaid") != null)
		{
			areaId = request.getParameter("areaid");
		}
		if (request.getParameter("st") != null)
		{
			st = request.getParameter("st");
		}
		if (request.getParameter("et") != null)
		{
			et = request.getParameter("et");
		}

		if (arcId != null)
		{
			ArcAmDetail_ArcID = Integer.parseInt(arcId);
		}

		if (areaId != null)
		{
			AreaID = Integer.parseInt(areaId);
		}
		if (request.getParameter("thePageCount") != null)
		{
			count = request.getParameter("thePageCount");
		}

		if (request.getParameter("thePageIndex") != null)
		{
			index = request.getParameter("thePageIndex");
		}
		
		if (request.getParameter("price_id") != null)
		{
			price_id = Integer.parseInt(request.getParameter("price_id"));
		}
		if (request.getParameter("part_id") != null)
		{
			part_id = Integer.parseInt(request.getParameter("part_id"));
		}
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer.parseInt(session.getAttribute("userId")
					.toString());
		}
	

		//AmHelperImpl rhi_ArcAmDetail = new AmHelperImpl();
		DataExportDao dao = new DataExportDao();
		List<ArcAmDetailData> ArcAmDetail_result = null;
		List<ArcAmDetailData> maplist = null; // 存放map里面的value集合
		int totalCount = 0;

		int thePageCount = Integer.parseInt(count);
		int thePageIndex = Integer.parseInt(index);
		if (AreaID == -1) // 选了所有区域的情形，列出区域下所有建筑
		{
			// System.out.println("全校");
			int Flag = 1;

			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
			//默认按表名排序
			order+= ",ammeter_name";
			Map<String, List<ArcAmDetailData>> ParAmDetail_map = new HashMap<String, List<ArcAmDetailData>>();// 存放对象数据的map
			try
			{
				//ArcAmDetail_result = rhi_ArcAmDetail.getAmDtailAllPartment(thePageCount, thePageIndex, st, et, order,userID);
				ArcAmDetail_result = dao.queryShowAmmeterInfo(AreaID, ArcAmDetail_ArcID, thePageCount, thePageIndex, st, et, order, userID, price_id, part_id);
				totalCount = dao.getTotalCount();
				ArcAmDetailData amDetailData = null;

				for (int i = 0; i < ArcAmDetail_result.size(); i++)
				{
					float istotalAm = 0; // 纳入计量
					float nototalAm = 0; // 非纳入计量

					amDetailData = ArcAmDetail_result.get(i);
					maplist = new ArrayList<ArcAmDetailData>();
					for (int j = 0; j < ArcAmDetail_result.size(); j++)
					{
						if (ArcAmDetail_result.get(j).getArchitecture_Name().equals(amDetailData.getArchitecture_Name()))
						{
							maplist.add(ArcAmDetail_result.get(j));
						}

					}

					for (int k = 0; k < maplist.size(); k++)
					{

						istotalAm += maplist.get(k).getIstotalvalue();

						nototalAm += maplist.get(k).getNototalvalue();

					}

					for (int m = 0; m < maplist.size(); m++)
					{
						maplist.get(m).setCountistotal(istotalAm);
						maplist.get(m).setCountnototal(nototalAm);
					}

					ParAmDetail_map.put(amDetailData.getArchitecture_Name(), maplist);
					//System.out.println("名字"+amDetailData.getArchitecture_Name());

				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			String ParAmDetaildata = gson_ArcAmDetail.toJson(new Object[]
			{ Flag, ParAmDetail_map, totalCount });
			//System.out.println("卡拉1"+ParAmDetaildata);
			out.println(ParAmDetaildata);
		} else if (ArcAmDetail_ArcID == -1 && AreaID != -1) // 只选了区域的情形，列出区域下所有建筑
		{
			// System.out.println("区域");
			int Flag = 2;

			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
			order+= ",ammeter_name";
			Map<String, List<ArcAmDetailData>> ParAmDetail_map = new HashMap<String, List<ArcAmDetailData>>();// 存放对象数据的map
			try
			{
				ArcAmDetail_result = dao.queryShowAmmeterInfo(AreaID, ArcAmDetail_ArcID, thePageCount, thePageIndex, st, et, order, userID, price_id, part_id);
				//ArcAmDetail_result = rhi_ArcAmDetail.getAmDtailByarea(thePageCount, thePageIndex, AreaID, st, et, order,userID);
				totalCount = dao.getTotalCount();
				ArcAmDetailData amDetailData = null;

				for (int i = 0; i < ArcAmDetail_result.size(); i++)
				{
					float istotalAm = 0; // 纳入计量
					float nototalAm = 0; // 非纳入计量

					amDetailData = ArcAmDetail_result.get(i);
					maplist = new ArrayList<ArcAmDetailData>();
					for (int j = 0; j < ArcAmDetail_result.size(); j++)
					{
						if (ArcAmDetail_result.get(j).getArchitecture_Name().equals(amDetailData.getArchitecture_Name()))
						{
							maplist.add(ArcAmDetail_result.get(j));
						}

					}

					for (int k = 0; k < maplist.size(); k++)
					{

						istotalAm += maplist.get(k).getIstotalvalue();

						nototalAm += maplist.get(k).getNototalvalue();

					}

					for (int m = 0; m < maplist.size(); m++)
					{
						maplist.get(m).setCountistotal(istotalAm);
						maplist.get(m).setCountnototal(nototalAm);
					}

					ParAmDetail_map.put(amDetailData.getArchitecture_Name(), maplist);
					// System.out.println("名字"+amDetailData.getArchitecture_Name());

				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			String ParAmDetaildata = gson_ArcAmDetail.toJson(new Object[]
			{ Flag, ParAmDetail_map, totalCount });
			//System.out.println("卡拉2"+ParAmDetaildata);
			out.println(ParAmDetaildata);
		}

		if (ArcAmDetail_ArcID != -1 && AreaID != -1) // 选了区域下的某个建筑
		{

			// System.out.println("建筑");
			int Flag = 3;

			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
			order+= ",ammeter_name";
			Map<String, List<ArcAmDetailData>> ParAmDetail_map = new HashMap<String, List<ArcAmDetailData>>();// 存放对象数据的map
			try
			{
				ArcAmDetail_result = dao.queryShowAmmeterInfo(AreaID, ArcAmDetail_ArcID, thePageCount, thePageIndex, st, et, order, userID, price_id, part_id);
				//ArcAmDetail_result = rhi_ArcAmDetail.getAmDtailByarc(thePageCount, thePageIndex, ArcAmDetail_ArcID, st, et, order,userID);
				totalCount = dao.getTotalCount();
				ArcAmDetailData amDetailData = null;

				for (int i = 0; i < ArcAmDetail_result.size(); i++)
				{
					float istotalAm = 0; // 纳入计量
					float nototalAm = 0; // 非纳入计量

					amDetailData = ArcAmDetail_result.get(i);
					maplist = new ArrayList<ArcAmDetailData>();
					for (int j = 0; j < ArcAmDetail_result.size(); j++)
					{
						if (amDetailData.getArchitecture_Name().equals(ArcAmDetail_result.get(j).getArchitecture_Name()))
						{
							maplist.add(ArcAmDetail_result.get(j));
						}

					}

					for (int k = 0; k < maplist.size(); k++)
					{

						istotalAm += maplist.get(k).getIstotalvalue();

						nototalAm += maplist.get(k).getNototalvalue();

					}

					for (int m = 0; m < maplist.size(); m++)
					{
						maplist.get(m).setCountistotal(istotalAm);
						maplist.get(m).setCountnototal(nototalAm);
					}

					ParAmDetail_map.put(amDetailData.getArchitecture_Name(), maplist);
					// System.out.println("名字"+amDetailData.getArchitecture_Name());

				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			String ParAmDetaildata = gson_ArcAmDetail.toJson(new Object[]
			{ Flag, ParAmDetail_map, totalCount });
			// System.out.println("卡拉3"+ParAmDetaildata);
			out.println(ParAmDetaildata);

		}

		out.flush();
		out.close();
	}

	
	//方法备份 勿删
//	private void showContent(HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//
//		int ArcAmDetail_ArcID = -1; // 表示选择选择所有建筑
//		int AreaID = -1; // 表示选择所有区域
//		Gson gson_ArcAmDetail = new Gson();
//
//		String arcId = "";
//		String areaId = "";
//		String st = "";
//		String et = "";
//		String count = "";
//		String index = "";
//		int price_id=0;
//		int part_id=0;
//		if (request.getParameter("aid") != null)
//		{
//			arcId = request.getParameter("aid");
//		}
//		if (request.getParameter("areaid") != null)
//		{
//			areaId = request.getParameter("areaid");
//		}
//		if (request.getParameter("st") != null)
//		{
//			st = request.getParameter("st");
//		}
//		if (request.getParameter("et") != null)
//		{
//			et = request.getParameter("et");
//		}
//
//		if (arcId != null)
//		{
//			ArcAmDetail_ArcID = Integer.parseInt(arcId);
//		}
//
//		if (areaId != null)
//		{
//			AreaID = Integer.parseInt(areaId);
//		}
//		if (request.getParameter("thePageCount") != null)
//		{
//			count = request.getParameter("thePageCount");
//		}
//
//		if (request.getParameter("thePageIndex") != null)
//		{
//			index = request.getParameter("thePageIndex");
//		}
//		
//		if (request.getParameter("price_id") != null)
//		{
//			price_id = Integer.parseInt(request.getParameter("price_id"));
//		}
//		if (request.getParameter("part_id") != null)
//		{
//			part_id = Integer.parseInt(request.getParameter("part_id"));
//		}
//		HttpSession session = request.getSession();
//		int userID = 0;
//		if (session.getAttribute("userId") != null)
//		{
//			userID = Integer.parseInt(session.getAttribute("userId")
//					.toString());
//		}
//	
//
//		AmHelperImpl rhi_ArcAmDetail = new AmHelperImpl();
//		List<ArcAmDetailData> ArcAmDetail_result = null;
//		List<ArcAmDetailData> maplist = null; // 存放map里面的value集合
//		int totalCount = 0;
//
//		int thePageCount = Integer.parseInt(count);
//		int thePageIndex = Integer.parseInt(index);
//		if (AreaID == -1) // 选了所有区域的情形，列出区域下所有建筑
//		{
//			// System.out.println("全校");
//			int Flag = 1;
//
//			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
//			//默认按表名排序
//			order+= ",ammeter_name";
//			Map<String, List<ArcAmDetailData>> ParAmDetail_map = new HashMap<String, List<ArcAmDetailData>>();// 存放对象数据的map
//			try
//			{
//				ArcAmDetail_result = rhi_ArcAmDetail.getAmDtailAllPartment(thePageCount, thePageIndex, st, et, order,userID);
//				totalCount = rhi_ArcAmDetail.getTotalCount();
//				ArcAmDetailData amDetailData = null;
//
//				for (int i = 0; i < ArcAmDetail_result.size(); i++)
//				{
//					float istotalAm = 0; // 纳入计量
//					float nototalAm = 0; // 非纳入计量
//
//					amDetailData = ArcAmDetail_result.get(i);
//					maplist = new ArrayList<ArcAmDetailData>();
//					for (int j = 0; j < ArcAmDetail_result.size(); j++)
//					{
//						if (ArcAmDetail_result.get(j).getArchitecture_Name().equals(amDetailData.getArchitecture_Name()))
//						{
//							maplist.add(ArcAmDetail_result.get(j));
//						}
//
//					}
//
//					for (int k = 0; k < maplist.size(); k++)
//					{
//
//						istotalAm += maplist.get(k).getIstotalvalue();
//
//						nototalAm += maplist.get(k).getNototalvalue();
//
//					}
//
//					for (int m = 0; m < maplist.size(); m++)
//					{
//						maplist.get(m).setCountistotal(istotalAm);
//						maplist.get(m).setCountnototal(nototalAm);
//					}
//
//					ParAmDetail_map.put(amDetailData.getArchitecture_Name(), maplist);
//					// System.out.println("名字"+amDetailData.getArchitecture_Name());
//
//				}
//			} catch (SQLException e)
//			{
//				e.printStackTrace();
//			}
//			String ParAmDetaildata = gson_ArcAmDetail.toJson(new Object[]
//			{ Flag, ParAmDetail_map, totalCount });
//			//System.out.println("卡拉1"+ParAmDetaildata);
//			out.println(ParAmDetaildata);
//		} else if (ArcAmDetail_ArcID == -1 && AreaID != -1) // 只选了区域的情形，列出区域下所有建筑
//		{
//			// System.out.println("区域");
//			int Flag = 2;
//
//			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
//			order+= ",ammeter_name";
//			Map<String, List<ArcAmDetailData>> ParAmDetail_map = new HashMap<String, List<ArcAmDetailData>>();// 存放对象数据的map
//			try
//			{
//				ArcAmDetail_result = rhi_ArcAmDetail.getAmDtailByarea(thePageCount, thePageIndex, AreaID, st, et, order,userID);
//				totalCount = rhi_ArcAmDetail.getTotalCount();
//				ArcAmDetailData amDetailData = null;
//
//				for (int i = 0; i < ArcAmDetail_result.size(); i++)
//				{
//					float istotalAm = 0; // 纳入计量
//					float nototalAm = 0; // 非纳入计量
//
//					amDetailData = ArcAmDetail_result.get(i);
//					maplist = new ArrayList<ArcAmDetailData>();
//					for (int j = 0; j < ArcAmDetail_result.size(); j++)
//					{
//						if (ArcAmDetail_result.get(j).getArchitecture_Name().equals(amDetailData.getArchitecture_Name()))
//						{
//							maplist.add(ArcAmDetail_result.get(j));
//						}
//
//					}
//
//					for (int k = 0; k < maplist.size(); k++)
//					{
//
//						istotalAm += maplist.get(k).getIstotalvalue();
//
//						nototalAm += maplist.get(k).getNototalvalue();
//
//					}
//
//					for (int m = 0; m < maplist.size(); m++)
//					{
//						maplist.get(m).setCountistotal(istotalAm);
//						maplist.get(m).setCountnototal(nototalAm);
//					}
//
//					ParAmDetail_map.put(amDetailData.getArchitecture_Name(), maplist);
//					// System.out.println("名字"+amDetailData.getArchitecture_Name());
//
//				}
//			} catch (SQLException e)
//			{
//				e.printStackTrace();
//			}
//			String ParAmDetaildata = gson_ArcAmDetail.toJson(new Object[]
//			{ Flag, ParAmDetail_map, totalCount });
//			//System.out.println("卡拉2"+ParAmDetaildata);
//			out.println(ParAmDetaildata);
//		}
//
//		if (ArcAmDetail_ArcID != -1 && AreaID != -1) // 选了区域下的某个建筑
//		{
//
//			// System.out.println("建筑");
//			int Flag = 3;
//
//			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
//			order+= ",ammeter_name";
//			Map<String, List<ArcAmDetailData>> ParAmDetail_map = new HashMap<String, List<ArcAmDetailData>>();// 存放对象数据的map
//			try
//			{
//				ArcAmDetail_result = rhi_ArcAmDetail.getAmDtailByarc(thePageCount, thePageIndex, ArcAmDetail_ArcID, st, et, order,userID);
//				totalCount = rhi_ArcAmDetail.getTotalCount();
//				ArcAmDetailData amDetailData = null;
//
//				for (int i = 0; i < ArcAmDetail_result.size(); i++)
//				{
//					float istotalAm = 0; // 纳入计量
//					float nototalAm = 0; // 非纳入计量
//
//					amDetailData = ArcAmDetail_result.get(i);
//					maplist = new ArrayList<ArcAmDetailData>();
//					for (int j = 0; j < ArcAmDetail_result.size(); j++)
//					{
//						if (amDetailData.getArchitecture_Name().equals(ArcAmDetail_result.get(j).getArchitecture_Name()))
//						{
//							maplist.add(ArcAmDetail_result.get(j));
//						}
//
//					}
//
//					for (int k = 0; k < maplist.size(); k++)
//					{
//
//						istotalAm += maplist.get(k).getIstotalvalue();
//
//						nototalAm += maplist.get(k).getNototalvalue();
//
//					}
//
//					for (int m = 0; m < maplist.size(); m++)
//					{
//						maplist.get(m).setCountistotal(istotalAm);
//						maplist.get(m).setCountnototal(nototalAm);
//					}
//
//					ParAmDetail_map.put(amDetailData.getArchitecture_Name(), maplist);
//					// System.out.println("名字"+amDetailData.getArchitecture_Name());
//
//				}
//			} catch (SQLException e)
//			{
//				e.printStackTrace();
//			}
//			String ParAmDetaildata = gson_ArcAmDetail.toJson(new Object[]
//			{ Flag, ParAmDetail_map, totalCount });
//			 //System.out.println("卡拉3"+ParAmDetaildata);
//			out.println(ParAmDetaildata);
//
//		}
//
//		out.flush();
//		out.close();
//	}

	private void exportContent(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//DecimalFormat df = new DecimalFormat("#.00");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//PrintWriter out = response.getWriter();
		//AmHelperImpl rhi_ArcAmDetail = new AmHelperImpl();
		DataExportDao dao = new DataExportDao();
		int ArcAmDetail_ArcID = -1; // 表示选择选择所有建筑
		int AreaID = -1; // 表示选择所有区域

		String arcId = "";
		String areaId = "";
		String st = "";
		String et = "";
		int price_id=0;
		int part_id=0;
		
		
		if (request.getParameter("aid") != null)
		{
			arcId = request.getParameter("aid");
		}
		if (request.getParameter("areaid") != null)
		{
			areaId = request.getParameter("areaid");
		}
		if (request.getParameter("st") != null)
		{
			st = request.getParameter("st");
		}
		if (request.getParameter("et") != null)
		{
			et = request.getParameter("et");
		}

	

		// System.out.println("st=" + st);

		if (arcId != null)
		{
			ArcAmDetail_ArcID = Integer.parseInt(arcId);
		}

		if (areaId != null)
		{
			AreaID = Integer.parseInt(areaId);
		}
		
		if (request.getParameter("price_id") != null)
		{
			price_id = Integer.parseInt(request.getParameter("price_id"));
		}
		if (request.getParameter("part_id") != null)
		{
			part_id = Integer.parseInt(request.getParameter("part_id"));
		}
		
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer.parseInt(session.getAttribute("userId")
					.toString());
		}
		List<ArcAmDetailData> listExport=new ArrayList<ArcAmDetailData>();
		//listExport=	rhi_ArcAmDetail.exportArcAmDetail(ArcAmDetail_ArcID,AreaID,st,et);
		listExport = dao.queryExportAmmeterInfo(AreaID, ArcAmDetail_ArcID, st, et, userID, price_id, part_id);
		//System.out.println(new Gson().toJson(listExport));
		String filePath = "/img/ArchAmDetail.xls";
		String XlsPath = this.getServletContext().getRealPath(filePath);
		// System.out.println("XlsPath:"+XlsPath);

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("表一");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("电表位置");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("所属部门");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 2);
		cell.setCellValue("电费类型");
		cell.setCellStyle(style);
		

		cell = row.createCell((short) 3);
		cell.setCellValue("起始示数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("终止示数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("用电量");
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setCellValue("表地址");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 7);
		cell.setCellValue("数据时间");
		cell.setCellStyle(style);
		int i = 0;
		float n = 0;
		for (ArcAmDetailData sdm : listExport)
		{
			n+=sdm.getNototalvalue();
			i++;
			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(sdm.getArea_name() +"." + sdm.getArchitecture_Name() + "." 
			        + sdm.getFloor() + "楼." + sdm.getAmmeter_name());
			row.createCell((short) 1).setCellValue(sdm.getPartmentName());
			row.createCell((short) 2).setCellValue(sdm.getPrice_name());
			row.createCell((short) 3).setCellValue(dFormat.format(sdm.getStartvalue()));
			row.createCell((short) 4).setCellValue(dFormat.format(sdm.getEndvalue()));
			row.createCell((short) 5).setCellValue(dFormat.format(sdm.getTotalvalue()));
			row.createCell((short) 6).setCellValue(sdm.getAmmeterNo());
			row.createCell((short) 7).setCellValue(sdm.getStarttime()+"至"+sdm.getEndtime());

		}
		//System.out.println(AreaID+" - "+ArcAmDetail_ArcID+" 未纳入计量数据："+n);
		FileOutputStream fout = new FileOutputStream(XlsPath);
		wb.write(fout);
		fout.close();

		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("filePath", filePath);

		PrintWriter pw = response.getWriter();
		pw.print(json.toString());
	}
	
	//方法备份 勿删 20170418
//	private void exportContent(HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		//DecimalFormat df = new DecimalFormat("#.00");
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		//PrintWriter out = response.getWriter();
//		AmHelperImpl rhi_ArcAmDetail = new AmHelperImpl();
//		int ArcAmDetail_ArcID = -1; // 表示选择选择所有建筑
//		int AreaID = -1; // 表示选择所有区域
//
//		String arcId = "";
//		String areaId = "";
//		String st = "";
//		String et = "";
//		int price_id=0;
//		int part_id=0;
//		
//		
//		if (request.getParameter("aid") != null)
//		{
//			arcId = request.getParameter("aid");
//		}
//		if (request.getParameter("areaid") != null)
//		{
//			areaId = request.getParameter("areaid");
//		}
//		if (request.getParameter("st") != null)
//		{
//			st = request.getParameter("st");
//		}
//		if (request.getParameter("et") != null)
//		{
//			et = request.getParameter("et");
//		}
//
//	
//
//		// System.out.println("st=" + st);
//
//		if (arcId != null)
//		{
//			ArcAmDetail_ArcID = Integer.parseInt(arcId);
//		}
//
//		if (areaId != null)
//		{
//			AreaID = Integer.parseInt(areaId);
//		}
//		
//		if (request.getParameter("price_id") != null)
//		{
//			price_id = Integer.parseInt(request.getParameter("price_id"));
//		}
//		if (request.getParameter("part_id") != null)
//		{
//			part_id = Integer.parseInt(request.getParameter("part_id"));
//		}
//		
//		List<ArcAmDetailData> listExport=new ArrayList<ArcAmDetailData>();
//		listExport=	rhi_ArcAmDetail.exportArcAmDetail(ArcAmDetail_ArcID,AreaID,st,et);
//		//System.out.println(new Gson().toJson(listExport));
//		String filePath = "/img/ArchAmDetail.xls";
//		String XlsPath = this.getServletContext().getRealPath(filePath);
//		// System.out.println("XlsPath:"+XlsPath);
//
//		// 第一步，创建一个webbook，对应一个Excel文件
//		HSSFWorkbook wb = new HSSFWorkbook();
//		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
//		HSSFSheet sheet = wb.createSheet("表一");
//		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
//		HSSFRow row = sheet.createRow((int) 0);
//		// 第四步，创建单元格，并设置值表头 设置表头居中
//		HSSFCellStyle style = wb.createCellStyle();
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//
//		HSSFCell cell = row.createCell((short) 0);
//		cell.setCellValue("电表位置");
//		cell.setCellStyle(style);
//
//		cell = row.createCell((short) 1);
//		cell.setCellValue("所属部门");
//		cell.setCellStyle(style);
//
//		cell = row.createCell((short) 2);
//		cell.setCellValue("起始示数");
//		cell.setCellStyle(style);
//
//		cell = row.createCell((short) 3);
//		cell.setCellValue("终止示数");
//		cell.setCellStyle(style);
//
//		cell = row.createCell((short) 4);
//		cell.setCellValue("用电量");
//		cell.setCellStyle(style);
//
//		cell = row.createCell((short) 5);
//		cell.setCellValue("表地址");
//		cell.setCellStyle(style);
//		
//		cell = row.createCell((short) 6);
//		cell.setCellValue("数据时间");
//		cell.setCellStyle(style);
//
//		
//
//		int i = 0;
//		float n = 0;
//		for (ArcAmDetailData sdm : listExport)
//		{
//			n+=sdm.getNototalvalue();
//			i++;
//			row = sheet.createRow(i);
//			row.createCell((short) 0).setCellValue(sdm.getArea_name() +"." + sdm.getArchitecture_Name() + "." 
//			        + sdm.getFloor() + "楼." + sdm.getAmmeter_name());
//			row.createCell((short) 1).setCellValue(sdm.getPartmentName());
//			row.createCell((short) 2).setCellValue(sdm.getStartvalue());
//			row.createCell((short) 3).setCellValue(sdm.getEndvalue());
//			row.createCell((short) 4).setCellValue(sdm.getTotalvalue());
//			row.createCell((short) 5).setCellValue(sdm.getAmmeterNo());
//			row.createCell((short) 6).setCellValue(sdm.getStarttime()+"至"+sdm.getEndtime());
//
//		}
//		//System.out.println(AreaID+" - "+ArcAmDetail_ArcID+" 未纳入计量数据："+n);
//		FileOutputStream fout = new FileOutputStream(XlsPath);
//		wb.write(fout);
//		fout.close();
//
//		response.setContentType("text/html;charset=utf-8");
//		JSONObject json = new JSONObject();
//		json.put("filePath", filePath);
//
//		PrintWriter pw = response.getWriter();
//		pw.print(json.toString());
//	}
}
