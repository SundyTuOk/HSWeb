package com.sf.energy.manager.current.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.google.gson.Gson;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.DataValidation;
import com.sf.energy.util.ExportHelper;



public class DayAmmeterManage extends HttpServlet
{

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat dfd = new DecimalFormat("0.00");
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			findMethod(request, response);

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		String method = request.getParameter("method");

		if ("getDayAmmeterData".equals(method))
			getDayAmmeterData(request, response);
		if ("export".equals(method))
			try
			{
				export(request, response);
			} catch (RowsExceededException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	private void getDayAmmeterData(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException
	{
		ElecReportHelper erh = new ElecReportHelperImpl();
		List<ReportModel> list = null;
		String dayAmTable = "";
		Date start = null;
		if (request.getParameter("startDate") != null)
			start = df.parse(request.getParameter("startDate"));
		else
		{
			return;
		}

		Date end = null;
		if (request.getParameter("endDate") != null)
			end = df.parse(request.getParameter("endDate"));
		else
		{
			return;
		}

		if (start.after(end))
			return;
		//System.out.println(df.format(start)+"-"+df.format(end));
		String chooseType = request.getParameter("chooseType");
		if (!"group".equals(chooseType) && !"arch".equals(chooseType) && !"ammeter".equals(chooseType))
			return;

		int ID = 0;
		if (request.getParameter("ID") != null && DataValidation.isNumber(request.getParameter("ID")))
			ID = Integer.parseInt(request.getParameter("ID"));
		else
		{
			return;
		}

		int startIndex = 0;
		if ("group".equals(chooseType))
		{
			list = erh.getGroupCountEveryDayBetween(ID, start, end);
		}

		if ("arch".equals(chooseType))
		{
			list = erh.getArchCountEveryDayBetween(ID, start, end);
		}

		if ("ammeter".equals(chooseType))
		{
			list = erh.getAmmeterCountEveryDayZL(ID, start, end);
		}
		//System.out.println(new Gson().toJson(list));
		if (list != null && list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				if(i!=0){
					if (list.get(i).getSelectMonth() != list.get(i - 1).getSelectMonth())
					{
						dayAmTable = dayAmTable + getMonthInfo(list.subList(startIndex, i));
						startIndex = i;
					}
				}
				if (i == (list.size() - 1))
					dayAmTable = dayAmTable + getMonthInfo(list.subList(startIndex, i + 1));

			}

		} else
		{
			dayAmTable = "empty data";
		}
		//System.out.println(dayAmTable);
		PrintWriter out = response.getWriter();
		out.println(dayAmTable);
		out.flush();
		out.close();
	}

	public String getMonthInfo(List<ReportModel> list)
	{
		String info = "", head = "";
		List<String> dayList = new LinkedList<String>();

		Date tempDate = new Date();
		int selectYear = 0, selectMonth = 0, selectDay = 0;
		float totalCount = 0;

		if (list.size() > 0)
		{
			selectYear = list.get(0).getSelectYear();
			selectMonth = list.get(0).getSelectMonth();
			selectDay = list.get(0).getSelectDay();

			tempDate.setYear(list.get(0).getSelectYear() - 1900);
			tempDate.setMonth(list.get(0).getSelectMonth() - 1);
			tempDate.setDate(list.get(0).getSelectDay());

			int i = 0;

			while (i < tempDate.getDay())
			{
				if (tempDate.getDay() == 0 || tempDate.getDay() == 6)
				{
					dayList.add("<td><div class='weekDayAmmeterDate'></div>" + "<div class='dayAmmeterCount'></div></td>");
				} else
				{
					dayList.add("<td><div class='weekEndAmmeterDate'></div>" + "<div class='dayAmmeterCount'></div></td>");
				}

				i++;
			}

			Date theDate = new Date();
			for (i = 0; i < list.size(); i++)
			{
				if (i != 0)
				{
					if ((list.get(i).getSelectDay() - list.get(i - 1).getSelectDay()) > 1)
					{
						for (int j = 0; j < (list.get(i).getSelectDay() - list.get(i - 1).getSelectDay() - 1); j++)
						{
							dayList.add("<td><div class=''></div>" + "<div class=''></div></td>");
						}
					}
				}

				selectYear = list.get(i).getSelectYear();
				selectMonth = list.get(i).getSelectMonth();
				selectDay = list.get(i).getSelectDay();

				theDate.setYear(list.get(i).getSelectYear() - 1900);
				theDate.setMonth(list.get(i).getSelectMonth() - 1);
				theDate.setDate(list.get(i).getSelectDay());

				if (theDate.getDay() == 0 || theDate.getDay() == 6)
				{
					dayList.add("<td><div class='weekEndAmmeterDate'>" + list.get(i).getSelectDay() + "</div>" + "<div class='dayAmmeterCount'>"
							+ list.get(i).getTotalEnergyCount() + "</div></td>");
				} else
				{
					dayList.add("<td><div class='weekDayAmmeterDate'>" + list.get(i).getSelectDay() + "</div>" + "<div class='dayAmmeterCount'>"
							+ list.get(i).getTotalEnergyCount() + "</div></td>");
				}

				totalCount = totalCount + list.get(i).getTotalEnergyCount();
			}

			tempDate.setYear(list.get(list.size() - 1).getSelectYear() - 1900);
			tempDate.setMonth(list.get(list.size() - 1).getSelectMonth() - 1);
			tempDate.setDate(list.get(list.size() - 1).getSelectDay());

			i = tempDate.getDay();

			while (i < 6)
			{
				dayList.add("<td><div class='dayAmmeterDate'></div>" + "<div class='dayAmmeterCount'></div></td>");
				i++;
			}

			int lineCount = dayList.size() / 7;

			info = "<tr>" + "<td rowspan='" + lineCount + "'>" + selectYear + "年" + selectMonth + "月</td>";

			for (i = 0; i < 7; i++)
			{
				info = info + dayList.get(i);
			}

			info = info + "<td rowspan='" + lineCount + "'>" + dfd.format(totalCount) + "</td></tr><tr>";

			for (i = 7; i < dayList.size(); i++)
			{
				info = info + dayList.get(i);

				if (((i + 1) % 7 == 0) && ((i + 1) != dayList.size()))
					info = info + "</tr><tr>";

				if ((i + 1) == dayList.size())
					info = info + "</tr>";
			}
		}

		return info;
	}

	private void export(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, RowsExceededException,
			WriteException, ParseException
	{
		ElecReportHelper erh = new ElecReportHelperImpl();
		List<ReportModel> list = null;
		String dayAmTable = "";
		Date start = null;
		if (request.getParameter("startDate") != null)
			start = df.parse(request.getParameter("startDate"));
		else
		{
			return;
		}

		Date end = null;
		if (request.getParameter("endDate") != null)
			end = df.parse(request.getParameter("endDate"));
		else
		{
			return;
		}

		if (start.after(end))
			return;

		String chooseType = request.getParameter("chooseType");
		if (!"group".equals(chooseType) && !"arch".equals(chooseType) && !"ammeter".equals(chooseType))
			return;

		int ID = 0;
		if (request.getParameter("ID") != null && DataValidation.isNumber(request.getParameter("ID")))
			ID = Integer.parseInt(request.getParameter("ID"));
		else
		{
			return;
		}

		int startIndex = 0;
		String ammeterName = "";
		// if ("group".equals(chooseType))
		// {
		// list = erh.getGroupCountEveryDayBetween(ID, start, end);
		// }
		//
		// if ("arch".equals(chooseType))
		// {
		// list = erh.getArchCountEveryDayBetween(ID, start, end);
		// }

		if ("ammeter".equals(chooseType))
		{
			list = erh.getAmmeterCountEveryDayZL(ID, start, end);
		}
//		if (list != null && list.size() > 0)
//		{
//			for (int i = 0; i < list.size(); i++)
//			{
//				if (i == (list.size() - 1))
//					dayAmTable = dayAmTable + getMonthInfo(list.subList(startIndex, i + 1));
//
//				if (i == 0)
//					continue;
//
//				if (list.get(i).getSelectMonth() != list.get(i - 1).getSelectMonth())
//				{
//					dayAmTable = dayAmTable + getMonthInfo(list.subList(startIndex, i));
//					startIndex = i;
//				}
//			}
//		} else
//		{
//			dayAmTable = "empty data";
//		}
		AmmeterDao ammeterDao = new AmmeterDao();
		ammeterName = ammeterDao.queryNameByID(ID);
//		createExcelFormTable(ammeterName+"日用电量导出文件.xls",dayAmTable,1);
		File file = getExportDayAmmeterFile(list, ammeterName);

		FileInputStream fis = new FileInputStream(file);
		byte[] fb = new byte[fis.available()];
		fis.read(fb);
		response.setHeader("Content-disposition", "attachment; filename=" + new String("日用电量导出文件.xls".getBytes("gb2312"), "iso8859-1"));

		ByteArrayInputStream bais = new ByteArrayInputStream(fb);
		ServletOutputStream sos = response.getOutputStream();
		int b;
		while ((b = bais.read()) != -1)
		{
			sos.write(b);
		}
		bais.close();

		sos.flush();
		sos.close();
		fis.close();

		// 要关闭所用的流才能成功删除文件，否则删除失败，导致临时文件堆积
		file.delete();
	}

	private File getExportDayAmmeterFile(List<ReportModel> amList, String name) throws RowsExceededException, WriteException, IOException
	{

		ExportHelper dh = ExportHelper.getInstance();
		File file = null;
		String[] theTitles =
		{ "设备名称", "数据时间", "日用量" };
		List<String> firstLine = new LinkedList<String>();

		List<List<String>> data = new LinkedList<List<String>>();

		// 将标题加进去
		for (String title : theTitles)
		{
			firstLine.add(title);
		}

		data.add(firstLine);

		// 将查询结果数据加进去
		for (int i = 0; i < amList.size(); i++)
		{
			ReportModel am = amList.get(i);
			List<String> item = new LinkedList<String>();

			// 设备名称
			item.add(name);

			// 表计类型
			item.add(am.getSelectYear() + "-" + am.getSelectMonth() + "-" + am.getSelectDay());

			// 隶属机构
			item.add(am.getTotalEnergyCount() + "");

			data.add(item);
		}
		file = dh.getExportFile(data);

		return file;
	}


}
