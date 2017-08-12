package com.sf.energy.water.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sf.energy.util.DataValidation;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class DayWaterManage extends HttpServlet
{
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat   dfd   =new DecimalFormat("0.00");  
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			findMethod(request, response);

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			ParseException
	{
		String method = request.getParameter("method");

		if ("getDayWaterData".equals(method))
			getDayWaterData(request, response);

	}

	private void getDayWaterData(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, SQLException,
			IOException
	{
		WaterReportHelper erh = new WaterReportHelperImpl();
		List<WaterReportModel> list = null;
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
		if (!"group".equals(chooseType) && !"arch".equals(chooseType)
				&& !"watermeter".equals(chooseType))
			return;

		int ID = 0;
		if (request.getParameter("ID") != null
				&& DataValidation.isNumber(request.getParameter("ID")))
			ID = Integer.parseInt(request.getParameter("ID"));
		else
		{
			return;
		}

		if ("group".equals(chooseType))
		{
			list = erh.getGroupCountEveryDayBetween(ID, start, end);
		}

		if ("arch".equals(chooseType))
		{
			list = erh.getArchCountEveryDayBetween(ID, start, end);
		}

		if ("watermeter".equals(chooseType))
		{
			list = erh.getWatermeterCountEveryDay(ID, start, end);
		}

		int startIndex = 0;
		if (list != null && list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				if (i == (list.size() - 1)){
					/**
					 * 若当前循环为最后一条数据，
					 * 需先判断最后一条数据与倒数第二条数据是否为同一月份数据
					 * 李戬 2016.7.15
					 */
					if(i>0){
						if(list.get(i).getSelectMonth() != list.get(i - 1)
								.getSelectMonth()){
							dayAmTable = dayAmTable
									+ getMonthInfo(list.subList(startIndex, i));
							startIndex = i;
							dayAmTable = dayAmTable
									+ getMonthInfo(list.subList(startIndex, i + 1));
						}
						else{
							dayAmTable = dayAmTable
									+ getMonthInfo(list.subList(startIndex, i + 1));
						}
					}else{
						dayAmTable = dayAmTable
								+ getMonthInfo(list.subList(startIndex, i + 1));
					}
				}

				if (i == 0)
					continue;

				if (list.get(i).getSelectMonth() != list.get(i - 1)
						.getSelectMonth())
				{
					dayAmTable = dayAmTable
							+ getMonthInfo(list.subList(startIndex, i));
					startIndex = i;
				}
			}

		}
		else
		{
			dayAmTable = "empty data";
		}

		PrintWriter out = response.getWriter();
		out.println(dayAmTable);
		out.flush();
		out.close();
	}

	private String getMonthInfo(List<WaterReportModel> list)
	{
		if (list == null)
			return null;

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
				dayList.add("<td><div class='weekEndWatermeterDate'></div>"
						+ "<div class='dayWatermeterCount'></div></td>");
				i++;
			}

			Date theDate = new Date();
			for (i = 0; i < list.size(); i++)
			{
				if (i != 0)
				{
					if ((list.get(i).getSelectDay() - list.get(i - 1)
							.getSelectDay()) > 1)
					{
						for (int j = 0; j < (list.get(i).getSelectDay()
								- list.get(i - 1).getSelectDay() - 1); j++)
						{
							dayList.add("<td><div class=''></div>"
									+ "<div class=''></div></td>");
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
					dayList.add("<td><div class='weekEndWatermeterDate'>"
							+ list.get(i).getSelectDay() + "</div>"
							+ "<div class='dayWatermeterCount'>"
							+ list.get(i).getTotalWaterCount() + "</div></td>");
				}
				else
				{
					dayList.add("<td><div class='weekDayWatermeterDate'>"
							+ list.get(i).getSelectDay() + "</div>"
							+ "<div class='dayWatermeterCount'>"
							+ list.get(i).getTotalWaterCount() + "</div></td>");
				}
				totalCount = totalCount + list.get(i).getTotalWaterCount();
			}

			tempDate.setYear(list.get(list.size() - 1).getSelectYear() - 1900);
			tempDate.setMonth(list.get(list.size() - 1).getSelectMonth() - 1);
			tempDate.setDate(list.get(list.size() - 1).getSelectDay());

			i = tempDate.getDay();

			while (i < 6)
			{
				dayList.add("<td><div class='dayWatermeterDate'></div>"
						+ "<div class='dayWatermeterCount'></div></td>");
				i++;
			}

			int lineCount = dayList.size() / 7;

			info = "<tr>" + "<td rowspan='" + lineCount + "'>" + selectYear
					+ "年" + selectMonth + "月</td>";

			for (i = 0; i < 7; i++)
			{
				info = info + dayList.get(i);
			}
			/**
			 * 合计保留两位
			 */
			info = info + "<td rowspan='" + lineCount + "'>" + dfd.format(totalCount)
					+ "</td></tr><tr>";

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

}
