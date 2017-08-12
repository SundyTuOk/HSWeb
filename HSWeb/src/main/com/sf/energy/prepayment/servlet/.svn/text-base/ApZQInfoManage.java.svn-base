package com.sf.energy.prepayment.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.prepayment.dao.ApZQInfoDao;
import com.sf.energy.prepayment.dao.CMMeterDao;
import com.sf.energy.prepayment.model.ApZQInfo;
import com.sf.energy.util.DataValidation;
import com.sf.energy.util.ExportHelper;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class ApZQInfoManage extends HttpServlet
{
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException,
			RowsExceededException, WriteException
	{
		String method = request.getParameter("method");

		if ("parginate".equals(method))
			parginate(request, response);

		if ("delete".equals(method))
			delete(request, response);

		if ("export".equals(method))
			export(request, response);
	}

	private void export(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException,
			RowsExceededException, WriteException
	{
		ApZQInfoDao akd = new ApZQInfoDao();
		Date start = new Date();
		if (request.getParameter("Start") != null)
		{
			start = df.parse(request.getParameter("Start"));
		}

		Date end = new Date();
		if (request.getParameter("End") != null)
		{
			end = df.parse(request.getParameter("End"));
		}
		end.setTime(end.getTime() + 1000 * 60 * 60 * 24);

		String sortLable = "SortLable";
		if (request.getParameter("SortLable") != null)
		{
			sortLable = request.getParameter("SortLable");
		}

		String sortType = "Asc";
		if (request.getParameter("SortType") != null)
		{
			sortType = request.getParameter("SortType");
		}

		boolean isAsc = true;
		if (sortType.equalsIgnoreCase("Asc"))
		{
			isAsc = true;
		}

		if (sortType.equalsIgnoreCase("Desc"))
		{
			isAsc = false;
		}

		int pageCount = 10;
		if (request.getParameter("PageCount") != null && DataValidation.isNumber(request.getParameter("PageCount")))
		{
			pageCount = Integer.parseInt(request.getParameter("PageCount"));
		}

		int pageIndex = 0;
		if (request.getParameter("PageIndex") != null && DataValidation.isNumber(request.getParameter("PageIndex")))
		{
			pageIndex = Integer.parseInt(request.getParameter("PageIndex"));
		}

		List<ApZQInfo> list = akd.search(start, end, sortLable, isAsc);

		File file = getExportFile(list);

		FileInputStream fis = new FileInputStream(file);
		byte[] fb = new byte[fis.available()];
		fis.read(fb);
		response.setHeader("Content-disposition", "attachment; filename=" + new String("总清信息导出文件.xls".getBytes("gb2312"), "iso8859-1"));

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

	private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		ApZQInfoDao akd = new ApZQInfoDao();
		String[] IDArray = null;
		if (request.getParameter("IDArray") != null)
		{
			IDArray = request.getParameter("IDArray").split(",");
		}

		int[] array = new int[IDArray.length];

		for (int i = 0; i < IDArray.length; i++)
		{
			if (DataValidation.isNumber(IDArray[i])){
				array[i] = Integer.parseInt(IDArray[i]);
				
				String info = akd.getZQInfoByID(array[i]);
				String userLoginName = (String) request.getSession().getAttribute(
						"userName");
				OperationLogInterface log = new OperationLogImplement();
				// 写入日志
				log.writeLog(userLoginName, "总清报表", "删除总清信息,"+info);
			}
				
		}

		String info = "";
		if (akd.deleteInfo(array))
		{
			info = "success";
		} else
		{
			info = "fail";
		}

		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	private void parginate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException
	{
		ApZQInfoDao akd = new ApZQInfoDao();
		Date start = new Date();
		if (request.getParameter("Start") != null)
		{
			start = df.parse(request.getParameter("Start"));
		}

		Date end = new Date();
		if (request.getParameter("End") != null)
		{
			end = df.parse(request.getParameter("End"));
		}
		end.setTime(end.getTime() + 1000 * 60 * 60 * 24);

		String sortLable = "SortLable";
		if (request.getParameter("SortLable") != null)
		{
			sortLable = request.getParameter("SortLable");
		}

		String sortType = "Asc";
		if (request.getParameter("SortType") != null)
		{
			sortType = request.getParameter("SortType");
		}

		boolean isAsc = true;
		if (sortType.equalsIgnoreCase("Asc"))
		{
			isAsc = true;
		}

		if (sortType.equalsIgnoreCase("Desc"))
		{
			isAsc = false;
		}

		int pageCount = 10;
		if (request.getParameter("PageCount") != null && DataValidation.isNumber(request.getParameter("PageCount")))
		{
			pageCount = Integer.parseInt(request.getParameter("PageCount"));
		}

		int pageIndex = 0;
		if (request.getParameter("PageIndex") != null && DataValidation.isNumber(request.getParameter("PageIndex")))
		{
			pageIndex = Integer.parseInt(request.getParameter("PageIndex"));
		}

		Map<String, Object> result = null;
		if (end.after(start))
			result = akd.searchParginate(start, end, sortLable, isAsc, pageCount, pageIndex);
		int totalCount = 0;
		List<ApZQInfo> list = null;
		if (result != null)
		{
			list = (List<ApZQInfo>) result.get("List");

			if (result.get("Count") != null)
			{
				totalCount = Integer.parseInt(result.get("Count").toString());
			}
		}

		JSONArray main = new JSONArray();

		JSONObject count = new JSONObject();
		count.put("TotalCount", totalCount);
		main.add(count);

		if (list != null && list.size() > 0)
		{
			for (ApZQInfo a : list)
			{
				main.add(buildNode(a));
			}
		}

		PrintWriter out = response.getWriter();
		out.println(main.toString());
		out.flush();
		out.close();
	}

	private JSONObject buildNode(ApZQInfo a) throws SQLException
	{
		JSONObject jo = new JSONObject();

		CMMeterDao dao = new CMMeterDao();
		String meterPotocol = dao.getMeterPotocol(a.getAmmeter_ID());
		jo.put("meterPotocol", meterPotocol);
		jo.put("ID", a.getID());
		jo.put("AreaName", a.getAreaName());
		jo.put("ArchName", a.getArchName());
		jo.put("Floor", a.getFloor());
		jo.put("AmmeterName", a.getAmmeter_Name());
		jo.put("ZQTime", ldf.format(a.getTheTime()));
		jo.put("OldSY", a.getOldsy());
		jo.put("NewZvalue", a.getNewzValue());
		jo.put("SYValue", a.getSyValue());
		jo.put("TZValue", a.getTzValue());
		jo.put("TZMoney", a.getTheMoney());
		jo.put("UserName", a.getUserName());

		return jo;
	}

	private File getExportFile(List<ApZQInfo> list) throws RowsExceededException, WriteException, IOException, SQLException
	{
		ExportHelper dh = ExportHelper.getInstance();
		File file = null;
		String[] theTitles =
		{ "区域", "建筑", "楼层", "房间", "总清时间", "旧表剩余量", "旧表剩余金额", "新表总用量", "剩余电量", "剩余金额", "透支电量", "剩余金额", "操作员" };
		List<String> firstLine = new LinkedList<String>();

		List<List<String>> data = new LinkedList<List<String>>();

		// 将标题加进去
		for (String title : theTitles)
		{
			firstLine.add(title);
		}

		data.add(firstLine);

		// 将查询结果数据加进去
		if (list != null)
		{
			for (int i = 0; i < list.size(); i++)
			{
				ApZQInfo am = list.get(i);
				List<String> item = new LinkedList<String>();
				CMMeterDao dao = new CMMeterDao();
				String meterPotocol = dao.getMeterPotocol(am.getAmmeter_ID());

				// "区域"
				item.add(am.getAreaName());

				// "建筑"
				item.add(am.getArchName());

				// "楼层"
				item.add(am.getFloor() + "");

				// "房间"
				item.add(am.getAmmeter_Name());

				// "开户时间"
				item.add(ldf.format(am.getTheTime()));

				if ("1".equals(meterPotocol))
				{
					// "旧表剩余量"
					item.add(am.getOldsy() + "");
					// "旧表剩余金额"
					item.add("-");
					// "新表总用量"
					item.add(am.getNewzValue() + "");

					// "剩余电量"
					item.add(am.getSyValue() + "");
					// "剩余金额"
					item.add("-");
				} else
				{
					// "旧表剩余量"
					item.add("-");
					// "旧表剩余金额"
					item.add(am.getOldsy() + "");
					// "新表总用量"
					item.add(am.getNewzValue() + "");

					// "剩余电量"
					item.add("-");
					// "剩余金额"
					item.add(am.getSyValue() + "");
				}

				// "透支电量"
				item.add(am.getTzValue() + "");

				// "剩余金额"
				item.add(am.getTheMoney() + "");

				// "操作员"
				item.add(am.getUserName());

				data.add(item);
			}
		}

		file = dh.getExportFile(data);

		return file;
	}
}
