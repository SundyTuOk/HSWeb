/**
 * 2014-4-24
 */
package com.sf.energy.project.equipment.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.dao.GatherDao;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.equipment.service.GatherServiceDao;
import com.sf.energy.project.equipment.serviceImpl.GatherServiceDaoImpl;
import com.sf.energy.project.right.dao.UsersArchitectureDao;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DataSiteDao;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.dao.MeterStyleDao;
import com.sf.energy.project.system.dao.PressureDevDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.DataSite;
import com.sf.energy.project.system.model.PressureDevModel;
import com.sf.energy.util.DataValidation;
import com.sf.energy.util.ExportHelper;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

/**
 * @author WangZhao 2014-4-24
 * 
 * 
 */
public class DataGateManage extends HttpServlet
{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			RowsExceededException, WriteException
	{
		String method = request.getParameter("method");

		if ("getAllGather".equals(method))
			getAllGather(request, response);

		if ("deleteGather".equals(method))
			deleteGather(request, response);

		if ("addGather".equals(method))
			addGather(request, response);

		if ("updateGather".equals(method))
			updateGather(request, response);

		if ("deleteSomeGather".equals(method))
			deleteSomeGather(request, response);

		if ("searchSomeGather".equals(method))
			searchSomeGather(request, response);

		if ("export".equals(method))
			export(request, response);

		if ("importGather".equals(method))
			importGather(request, response);

		if ("exportFailResult".equals(method))
			exportFailResult(request, response);

		if ("queryGatherByAreaID".equals(method))
			queryGatherByAreaID(request, response);
		
		if ("query_Pump_Gather_ByAreaID".equals(method))
			query_Pump_Gather_ByAreaID(request, response);
		
		if ("queryGatherByGatherID".equals(method))
			queryGatherByGatherID(request, response);
		
		if ("initSearchBox".equals(method))
			initSearchBox(request, response);
		if ("queryAllGather".equals(method))
			queryAllGather(request, response);
	}

	private void queryGatherByAreaID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		
		GatherDao gd = new GatherDao();
		int areaID = 0;
		if(request.getParameter("areaID")!=null)
			areaID = Integer.parseInt(request.getParameter("areaID"));
		ArrayList<Gather> list = gd.queryGatherByAreaID(areaID);

		JSONArray json = new JSONArray();
		for (int i = list.size() - 1; i >= 0; i--)
		{
			Gather n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("Gather_ID", n.getGatherID());
			jo.put("Gather_AreaID", n.getAreaID());
			jo.put("Gather_AreaName", n.getAreaName());
			jo.put("Gather_ArchID", n.getArchitectureID());
			jo.put("Gather_ArchName", n.getArchitectureName());
			jo.put("Gather_DatasiteID", n.getDatasiteName());
			jo.put("Gather_State", n.getGatherState());
			jo.put("Gather_Num", n.getGatherNum());
			jo.put("Gather_Name", n.getGatherName());
			jo.put("Gather_Address", n.getGatherAddress());
			jo.put("Gather_PassWord", n.getGatherPw());
			jo.put("Gather_InstallAddress", n.getInstallAddress());
			jo.put("Gather_Factory", n.getFactory());
			jo.put("Gather_Version", n.getVersion());
			jo.put("Gather_Size", n.getSize());
			jo.put("Gather_Protocol", n.getProtocol());
			jo.put("Gather_Sendway", n.getSendwayName());
			jo.put("Gather_GatherStyle", n.getGatherStyleName());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}
	
	/**
	 * 李戬
	 * 2015/07/08 根据gatherID查询对应gatherName 返回给前端
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 */
	private void queryGatherByGatherID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		
		GatherDao gd = new GatherDao();
		int gatherID = 0;
		if(request.getParameter("gatherID")!=null)
			gatherID = Integer.parseInt(request.getParameter("gatherID"));
		ArrayList<Gather> list = gd.queryGatherByGatherID(gatherID);

		JSONArray json = new JSONArray();
		for (int i = list.size() - 1; i >= 0; i--)
		{
			Gather n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("Gather_ID", n.getGatherID());
//			jo.put("Gather_AreaID", n.getAreaID());
//			jo.put("Gather_AreaName", n.getAreaName());
//			jo.put("Gather_ArchID", n.getArchitectureID());
//			jo.put("Gather_ArchName", n.getArchitectureName());
//			jo.put("Gather_DatasiteID", n.getDatasiteName());
//			jo.put("Gather_State", n.getGatherState());
//			jo.put("Gather_Num", n.getGatherNum());
			jo.put("Gather_Name", n.getGatherName());
//			jo.put("Gather_Address", n.getGatherAddress());
//			jo.put("Gather_PassWord", n.getGatherPw());
//			jo.put("Gather_InstallAddress", n.getInstallAddress());
//			jo.put("Gather_Factory", n.getFactory());
//			jo.put("Gather_Version", n.getVersion());
//			jo.put("Gather_Size", n.getSize());
//			jo.put("Gather_Protocol", n.getProtocol());
//			jo.put("Gather_Sendway", n.getSendwayName());
//			jo.put("Gather_GatherStyle", n.getGatherStyleName());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
	//	System.out.println(json.toString());
		out.flush();
		out.close();

	}
	
	private void query_Pump_Gather_ByAreaID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		
		PressureDevDao predd = new PressureDevDao();
		int areaID = 0;
		if(request.getParameter("areaID")!=null)
			areaID = Integer.parseInt(request.getParameter("areaID"));
		ArrayList<PressureDevModel> list = predd.query_Pump_Gather_ByAreaID(areaID);

		JSONArray json = new JSONArray();
		for (int i=0;i<list.size();i++)
		{
			PressureDevModel n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("AREA_NAME", n.getArea_Name());
			jo.put("PUMP_NAME", n.getPumpName());
			jo.put("PUMP_ID", n.getPump_ID());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
	//	System.out.println(json.toString());
		out.flush();
		out.close();

	}
	

	private void importGather(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			RowsExceededException, WriteException
	{
		
		ExportHelper dh = ExportHelper.getInstance();
		Map<String, File> imFailMap = new HashMap<String, File>();
		String saveFolder = "img";
		List<List<String>> result = null;
		File file = dh.getImportFile(request, response, saveFolder,
				"theGatherFile");

		JSONArray main = new JSONArray();

		JSONObject jo = new JSONObject();

		if (file != null)
		{
			if (file.getAbsolutePath().endsWith(".xls"))
			{
				result = importGatherToDB(file, request);
				if (result.size() == 1)
				{
					jo.put("type", "success");
				} else
				{
					jo.put("type", "fail");
					jo.put("info", "部分数据不合法");
					File failFile = dh.getExportFile(result);
					HttpSession session = request.getSession();
					String sessionID = session.getId();
					if (imFailMap.get(sessionID) != null)
					{
						imFailMap.get(sessionID).delete();
						imFailMap.remove(sessionID);
					}
					imFailMap.put(sessionID, failFile);
				}
			} else
			{
				jo.put("type", "fail");
				jo.put("info", "不接受.xls以外的文件格式");
			}

		} else
		{
			jo.put("type", "fail");
			jo.put("info", "操作失败");
		}

		main.add(jo);

		PrintWriter out = response.getWriter();
		out.println(main.toString());
		out.flush();
		out.close();
	}

	private void exportFailResult(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		Map<String, File> imFailMap = new HashMap<String, File>();
		HttpSession session = request.getSession();
		String sessionID = session.getId();

		File file = imFailMap.get(sessionID);

		if (file != null)
		{
			FileInputStream fis = new FileInputStream(file);
			byte[] fb = new byte[fis.available()];
			fis.read(fb);
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String("网关导入失败数据.xls".getBytes("gb2312"),
									"iso8859-1"));

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
			imFailMap.remove(sessionID);
		}

	}

	private void export(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, RowsExceededException,
			WriteException
	{
		
		GatherServiceDao gsd = new GatherServiceDaoImpl();
		ExportHelper dh = ExportHelper.getInstance();
		
		int areaID = 0;
		if (request.getParameter("areaID") != null)
			areaID = Integer.parseInt(request.getParameter("areaID"));

		int state = -1;
		if (request.getParameter("state") != null)
			state = Integer.parseInt(request.getParameter("state"));

		String address = null;
		if (!"".equals(request.getParameter("address")))
			address = request.getParameter("address");

		String name = null;
		if (!"".equals(request.getParameter("name")))
			name = request.getParameter("name");

		String num = null;
		if (!"".equals(request.getParameter("num")))
			num = request.getParameter("num");

		List<Gather> list = gsd.complexQueryGather(areaID, state, address,
				name, num);

		String[] theTitles =
		{ "所属建筑名称(100Byte)", "所属中继站名称(50Byte)", "网关状态(16Byte)", "网关编号(20Byte)",
				"网关名称(50Byte)", "网关通讯地址(12Byte)", "网关密码(10Byte)",
				"安装地址(200Byte)", "厂家编号(50Byte)", "网关版本(50Byte)",
				"网关型号(20Byte)", "网关规约(16Byte)", "通讯方式(16Byte)", "抄表方式(16Byte)" };
		List<String> firstLine = new LinkedList<String>();

		List<List<String>> data = new LinkedList<List<String>>();

		// 将标题加进去
		for (String title : theTitles)
		{
			firstLine.add(title);
		}

		data.add(firstLine);

		// 将查询结果数据加进去
		for (Gather am : list)
		{
			List<String> item = new LinkedList<String>();

			// 所属建筑名称
			item.add(am.getArchitectureName());

			// 所属中继站名称
			item.add(am.getGatherName());

			// 网关状态
			item.add(am.getGatherStateName());

			// 网关编号
			item.add(am.getGatherNum());

			// 网关名称
			item.add(am.getGatherName());

			// 网关通讯地址
			item.add(am.getGatherAddress());

			// 网关密码
			item.add(am.getGatherPw());

			// 安装地址
			item.add(am.getInstallAddress());

			// 厂家编号
			item.add(am.getFactory());

			// 网关版本
			item.add(am.getVersion());

			// 网关型号
			item.add(am.getSize());

			// 网关规约
			item.add(am.getProtocol());

			// 通讯方式
			item.add(am.getSendwayName());

			// 抄表方式
			item.add(am.getGatherStyleName());

			data.add(item);
		}

		File file = dh.getExportFile(data);

		FileInputStream fis = new FileInputStream(file);
		byte[] fb = new byte[fis.available()];
		fis.read(fb);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String("网关导出文件.xls".getBytes("gb2312"), "iso8859-1"));

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

	private void searchSomeGather(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		GatherDao gatherDao = new GatherDao();
		int DataGateManagePageCount = 0;
		if (request.getParameter("DataGateManagePageCount") != null)
			DataGateManagePageCount = Integer.parseInt(request.getParameter("DataGateManagePageCount"));
		int DataGateManagePageIndex = 0;
		if (request.getParameter("DataGateManagePageIndex") != null)
			DataGateManagePageIndex = Integer.parseInt(request.getParameter("DataGateManagePageIndex"));
		
		int areaID = 0;
		if (request.getParameter("areaID") != null)
			areaID = Integer.parseInt(request.getParameter("areaID"));
		int archID = 0;
		if (request.getParameter("archID") != null)
			archID = Integer.parseInt(request.getParameter("archID"));
		
		int state = -1;
		if (request.getParameter("state") != null)
			state = Integer.parseInt(request.getParameter("state"));

		String address = null;
		if (request.getParameter("address") != null
				&& !"".equals(request.getParameter("address")))
			address = request.getParameter("address");

		String name = null;
		if (request.getParameter("name") != null
				&& !"".equals(request.getParameter("name")))
			name = request.getParameter("name");

		String num = null;
		if (request.getParameter("num") != null
				&& !"".equals(request.getParameter("num")))
			num = request.getParameter("num");

		String sortLable = "Gather_ID";
		if (request.getParameter("SortLable") != null
				&& !"".equals(request.getParameter("SortLable")))
			sortLable = request.getParameter("SortLable");

		String sortType = "Asc";
		if (request.getParameter("SortType") != null
				&& !"".equals(request.getParameter("SortType")))
			sortType = request.getParameter("SortType");

		boolean isAsc = true;
		if (sortType.equalsIgnoreCase("Asc"))
		{
			isAsc = true;
		}

		if (sortType.equalsIgnoreCase("Desc"))
		{
			isAsc = false;
		}
		
		List<Gather> list = gatherDao.search(areaID,archID, state, address, name, num,
				sortLable, isAsc,DataGateManagePageCount,DataGateManagePageIndex);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", gatherDao.getTotalCount());
		// jo.put("pageCount", ad.getRecordCount());
		// jo.put("pageIndex", ad.getRecordCount());
		// jo.put("currentCount", ad.getRecordCount());
		json.add(jo);
		for (int i = list.size() - 1; i >= 0; i--)
		{
			Gather n = list.get(i);
			json.add(buildNode(n));
		}

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void deleteSomeGather(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		
		GatherDao gd = new GatherDao();
		String idList = request.getParameter("IDList");
		String[] list = idList.split(" ");
		Integer ID = 0;
		String resultInfo = "success", nameList = "";
		for (String id : list)
		{
			ID = Integer.parseInt(id);

			if (!gd.delete(ID))
				resultInfo = "fail";
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void updateGather(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		ArchitectureDao ad = new ArchitectureDao();
		GatherDao gd = new GatherDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		Gather gather = new Gather();

		if (request.getParameter("Gather_ID") != null
				&& DataValidation.isNumber(request.getParameter("Gather_ID")))
			gather.setGatherID(Integer.parseInt(request
					.getParameter("Gather_ID")));
		else
		{
			return;
		}

		if (request.getParameter("Gather_Arch") != null
				&& DataValidation.isNumber(request.getParameter("Gather_Arch")))
			gather.setArchitectureID(Integer.parseInt(request
					.getParameter("Gather_Arch")));
		else
		{
			return;
		}

		Architecture arch = ad.queryByID(gather.getArchitectureID());
		if (arch != null)
			gather.setAreaID(arch.getAreaID());

		if (request.getParameter("Gather_DatasiteID") != null
				&& DataValidation.isNumber(request
						.getParameter("Gather_DatasiteID")))
			gather.setDatasiteID(Integer.parseInt(request
					.getParameter("Gather_DatasiteID")));

		if (request.getParameter("Gather_State") != null
				&& DataValidation
						.isNumber(request.getParameter("Gather_State")))
			gather.setGatherState(Integer.parseInt(request
					.getParameter("Gather_State")));

		if (request.getParameter("Gather_Num") != null)
			gather.setGatherNum(request.getParameter("Gather_Num"));

		if (request.getParameter("Gather_Name") != null)
			gather.setGatherName(request.getParameter("Gather_Name"));

		if (request.getParameter("Gather_Address") != null)
			gather.setGatherAddress(request.getParameter("Gather_Address"));

		if (request.getParameter("Gather_PassWord") != null)
			gather.setGatherPw(request.getParameter("Gather_PassWord"));

		if (request.getParameter("Gather_InstallAddress") != null)
			gather.setInstallAddress(request
					.getParameter("Gather_InstallAddress"));

		if (request.getParameter("Gather_Factory") != null)
			gather.setFactory(request.getParameter("Gather_Factory"));

		if (request.getParameter("Gather_Version") != null)
			gather.setVersion(request.getParameter("Gather_Version"));

		if (request.getParameter("Gather_Size") != null)
			gather.setSize(request.getParameter("Gather_Size"));

		if (request.getParameter("Gather_Protocol") != null)
			gather.setProtocol(request.getParameter("Gather_Protocol"));

		if (request.getParameter("Gather_Sendway") != null
				&& DataValidation.isNumber(request
						.getParameter("Gather_Sendway")))
			gather.setSendway(Integer.parseInt(request
					.getParameter("Gather_Sendway")));

		if (request.getParameter("Gather_GatherStyle") != null
				&& DataValidation.isNumber(request
						.getParameter("Gather_GatherStyle")))
			gather.setGatherStyle(Integer.parseInt(request
					.getParameter("Gather_GatherStyle")));

		String resultInfo = null;

		if (gd.checkUpdateRepeat(gather))
		{
			resultInfo = "不允许添加相同编号，名称或通讯地址的网关";
		} else if (gd.update(gather)){
			resultInfo = "success";
			// 写入日志
			log.writeLog(userLoginName, "网关管理", "新增数据网关"+gather.getGatherName());
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addGather(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		GatherDao gd = new GatherDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		Gather gather = new Gather();

		if (request.getParameter("Gather_Arch") != null
				&& DataValidation.isNumber(request.getParameter("Gather_Arch")))
			gather.setArchitectureID(Integer.parseInt(request
					.getParameter("Gather_Arch")));
		else
		{
			return;
		}

		Architecture arch = ad.queryByID(gather.getArchitectureID());
		if (arch != null)
			gather.setAreaID(arch.getAreaID());

		if (request.getParameter("Gather_DatasiteID") != null
				&& DataValidation.isNumber(request
						.getParameter("Gather_DatasiteID")))
			gather.setDatasiteID(Integer.parseInt(request
					.getParameter("Gather_DatasiteID")));

		if (request.getParameter("Gather_State") != null
				&& DataValidation
						.isNumber(request.getParameter("Gather_State")))
			gather.setGatherState(Integer.parseInt(request
					.getParameter("Gather_State")));

		if (request.getParameter("Gather_Num") != null)
			gather.setGatherNum(request.getParameter("Gather_Num"));

		if (request.getParameter("Gather_Name") != null)
			gather.setGatherName(request.getParameter("Gather_Name"));

		if (request.getParameter("Gather_Address") != null)
			gather.setGatherAddress(request.getParameter("Gather_Address"));

		if (request.getParameter("Gather_PassWord") != null)
			gather.setGatherPw(request.getParameter("Gather_PassWord"));

		if (request.getParameter("Gather_InstallAddress") != null)
			gather.setInstallAddress(request
					.getParameter("Gather_InstallAddress"));

		if (request.getParameter("Gather_Factory") != null)
			gather.setFactory(request.getParameter("Gather_Factory"));

		if (request.getParameter("Gather_Version") != null)
			gather.setVersion(request.getParameter("Gather_Version"));

		if (request.getParameter("Gather_Size") != null)
			gather.setSize(request.getParameter("Gather_Size"));

		if (request.getParameter("Gather_Protocol") != null)
			gather.setProtocol(request.getParameter("Gather_Protocol"));

		if (request.getParameter("Gather_Sendway") != null
				&& DataValidation.isNumber(request
						.getParameter("Gather_Sendway")))
			gather.setSendway(Integer.parseInt(request
					.getParameter("Gather_Sendway")));

		if (request.getParameter("Gather_GatherStyle") != null
				&& DataValidation.isNumber(request
						.getParameter("Gather_GatherStyle")))
			gather.setGatherStyle(Integer.parseInt(request
					.getParameter("Gather_GatherStyle")));

		String resultInfo = null;
		if (gd.checkAddRepeat(gather))
		{
			resultInfo = "不允许添加相同编号，名称或通讯地址的网关";
		} else if (gd.add(gather)){
			resultInfo = "success";
			// 写入日志
			log.writeLog(userLoginName, "网关管理", "新增数据网关"+gather.getGatherName());
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void deleteGather(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		
		GatherDao gd = new GatherDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		Integer GatherID = Integer.parseInt(request.getParameter("GatherID"));

		String resultInfo = "";
		String nameString=gd.queryByID(GatherID).getGatherName();
		if (gd.hasAmmeter(GatherID) || gd.hasWaterMeter(GatherID))
		{
			resultInfo += "该网关下挂有电表或水表，删除该网管前，请先删除所挂设备";
		} else if (gd.delete(GatherID)){
			resultInfo = "success";
			// 写入日志
			log.writeLog(userLoginName, "网关管理", "删除数据网关   "+nameString);
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getAllGather(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		
		int DataGateManagePageCount = 0;
		if (request.getParameter("DataGateManagePageCount") != null)
			DataGateManagePageCount = Integer.parseInt(request.getParameter("DataGateManagePageCount"));
		int DataGateManagePageIndex = 0;
		if (request.getParameter("DataGateManagePageIndex") != null)
			DataGateManagePageIndex = Integer.parseInt(request.getParameter("DataGateManagePageIndex"));
		GatherDao gd = new GatherDao();
		
		List<Gather> list = gd.display(DataGateManagePageCount,DataGateManagePageIndex);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", gd.getTotalCount());
		// jo.put("pageCount", ad.getRecordCount());
		// jo.put("pageIndex", ad.getRecordCount());
		// jo.put("currentCount", ad.getRecordCount());
		json.add(jo);
		for (int i = list.size() - 1; i >= 0; i--)
		{
			Gather n = list.get(i);
			json.add(buildNode(n));
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		out.close();
	}
	/**
	 * 不分页
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	private void queryAllGather(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		
			GatherDao gd = new GatherDao();
		
		List<Gather> list = gd.display();

		JSONArray json = new JSONArray();
		for (int i = list.size() - 1; i >= 0; i--)
		{
			Gather n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("Gather_ID", n.getGatherID());
			jo.put("Gather_AreaID", n.getAreaID());
			jo.put("Gather_AreaName", n.getAreaName());
			jo.put("Gather_ArchID", n.getArchitectureID());
			jo.put("Gather_ArchName", n.getArchitectureName());
			jo.put("Gather_DatasiteID", n.getDatasiteName());
			jo.put("Gather_State", n.getGatherState());
			jo.put("Gather_Num", n.getGatherNum());
			jo.put("Gather_Name", n.getGatherName());
			jo.put("Gather_Address", n.getGatherAddress());
			jo.put("Gather_PassWord", n.getGatherPw());
			jo.put("Gather_InstallAddress", n.getInstallAddress());
			jo.put("Gather_Factory", n.getFactory());
			jo.put("Gather_Version", n.getVersion());
			jo.put("Gather_Size", n.getSize());
			jo.put("Gather_Protocol", n.getProtocol());
			jo.put("Gather_Sendway", n.getSendwayName());
			jo.put("Gather_GatherStyle", n.getGatherStyleName());
			json.add(jo);
		}
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		out.close();
	}

	private List<List<String>> importGatherToDB(File amFile,
			HttpServletRequest request) throws SQLException
	{
		DataSiteDao datasiteDao = new DataSiteDao();
		GatherDao gd = new GatherDao();
		AreaDao areaDao = new AreaDao();
		ArchitectureDao archDao = new ArchitectureDao();
		GatherDao gatherDao = new GatherDao();
		List<List<String>> failList = new LinkedList<List<String>>();

		String[] theTitles =
		{ "所属建筑名称", "所属中继站名称", "网关状态", "网关编号", "网关名称", "网关通讯地址", "网关密码",
				"安装地址", "厂家编号", "网关版本", "网关型号", "网关规约", "通讯方式", "抄表方式", "错误信息" };
		List<String> firstLine = new LinkedList<String>();

		// 将标题加进去
		for (String title : theTitles)
		{
			firstLine.add(title);
		}
		failList.add(firstLine);
		Map<String, Architecture> archMap = archDao.getMapDataByArchName();
		Map<String, DataSite> ddMap = datasiteDao.getMapByName();
		Workbook book = null;
		try
		{
			book = Workbook.getWorkbook(amFile);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			List<Gather> list = new LinkedList<Gather>();
			// 得到单元格
			Boolean flag = true;
			String errorInfo = "";
			for (int i = 1; i < sheet.getRows(); i++)
			{
				Gather am = new Gather();
				errorInfo = "";
				flag = true;
				if (sheet.getColumns() >= 14)
				{
					// 所属建筑
					String archName = sheet.getCell(0, i).getContents();
					if (archName != null && archName.length() > 0)
					{
						if (archMap.get(archName) == null)
						{
							Architecture theArch = new Architecture();
							theArch.setName(archName);
							theArch.setAreaID(areaDao.getFirstArea().getId());
							archDao.add(theArch);
							// 新增建筑权限赋给超级管理员
							HttpSession session = request.getSession();
							int userID = 0;
							if (session.getAttribute("userId") != null)
							{
								userID = Integer.parseInt(session.getAttribute(
										"userId").toString());
							}
							UsersArchitectureDao uad = new UsersArchitectureDao();
							uad.rightAfterInsert(archName, userID);
							archMap = archDao.getMapDataByArchName();
						}
						am.setArchitectureID(archMap.get(archName).getId());
						am.setAreaID(archMap.get(archName).getAreaID());
					} else
					{
						flag = false;
						errorInfo += "网关所属建筑名称不得为空;";
					}

					// 所属中继站名称
					if (ddMap.get(sheet.getCell(1, i).getContents()) != null)
						am.setDatasiteID(ddMap.get(
								sheet.getCell(1, i).getContents())
								.getDatasiteID());

					// 网关状态
					if (sheet.getCell(2, i).getContents().equals("采集"))
						am.setGatherState(1);
					else
						am.setGatherState(0);

					// 网关编号
					am.setGatherNum(sheet.getCell(3, i).getContents());

					// 网关名称
					am.setGatherName(sheet.getCell(4, i).getContents());

					// 网关通讯地址
					am.setGatherAddress(sheet.getCell(5, i).getContents());

					// 网关密码
					am.setGatherPw(sheet.getCell(6, i).getContents());

					// 安装地址
					am.setInstallAddress(sheet.getCell(7, i).getContents());

					// 厂家编号
					am.setFactory(sheet.getCell(8, i).getContents());

					// 网关版本
					am.setVersion(sheet.getCell(9, i).getContents());

					// 网关型号
					am.setSize(sheet.getCell(10, i).getContents());

					// 网关规约
					String protocol = sheet.getCell(11, i).getContents();
					if (protocol.equals("国网协议"))
						am.setProtocol("GW");

					// 通讯方式
					if (sheet.getCell(12, i).getContents().equals("串口方式"))
						am.setSendway(1);

					if (sheet.getCell(12, i).getContents().equals("网络方式"))
						am.setSendway(2);

					// 抄表方式
					if (sheet.getCell(13, i).getContents().equals("主动轮抄"))
						am.setSendway(1);

					if (sheet.getCell(13, i).getContents().equals("主动上报"))
						am.setSendway(0);
				} else
				{
					flag = false;
					errorInfo += "该行数据记录列数少于模板要求列数;";
				}

				if (gatherDao.checkAddRepeat(am))
				{
					flag = false;
					errorInfo += "不允许添加相同编号，名称或通讯地址的网关;";
				}

				if (flag)
					list.add(am);
				else
				{
					List<String> info = new LinkedList<String>();
					for (int j = 0; j < 14; j++)
					{
						info.add(sheet.getCell(j, i).getContents());
					}
					info.add(errorInfo);
					failList.add(info);
				}
			}

			gd.add(list);
			book.close();
		} catch (BiffException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		amFile.delete();

		return failList;
	}

	private JSONObject buildNode(Gather n)
	{
		JSONObject jo = new JSONObject();
		jo.put("Gather_ID", n.getGatherID());
		jo.put("Gather_AreaID", n.getAreaID());
		jo.put("Gather_AreaName", n.getAreaName());
		jo.put("Gather_ArchID", n.getArchitectureID());
		jo.put("Gather_ArchName", n.getArchitectureName());
		jo.put("Gather_DatasiteID", n.getDatasiteID());
		jo.put("Gather_DatasiteName", n.getDatasiteName());
		jo.put("Gather_State", n.getGatherState());
		jo.put("Gather_StateName", n.getGatherStateName());
		jo.put("Gather_Num", n.getGatherNum());
		jo.put("Gather_Name", n.getGatherName());
		jo.put("Gather_Address", n.getGatherAddress());
		jo.put("Gather_PassWord", n.getGatherPw());
		jo.put("Gather_InstallAddress", n.getInstallAddress());
		jo.put("Gather_Factory", n.getFactory());
		jo.put("Gather_Version", n.getVersion());
		jo.put("Gather_Size", n.getSize());
		jo.put("Gather_Protocol", n.getProtocol());
		jo.put("Gather_ProtocolName", n.getProtocolName());
		jo.put("Gather_Sendway", n.getSendway());
		jo.put("Gather_SendwayName", n.getSendwayName());
		jo.put("Gather_GatherStyle", n.getGatherStyle());
		jo.put("Gather_GatherStyleName", n.getGatherStyleName());
		return jo;
	}
	private void initSearchBox(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AreaDao areaDao = new AreaDao();
		ArchitectureDao archDao = new ArchitectureDao();
		List<Area> areaList=areaDao.display();
		List<Architecture> archList = archDao.displayAll();


		if ( archList != null || areaList != null)
		{
			JSONObject main = new JSONObject();

			if (areaList != null && areaList.size() > 0)
			{
				JSONArray list = new JSONArray();

				for (Area a : areaList)
				{
					JSONObject jo = new JSONObject();

					jo.put("Area_ID", a.getId());
					jo.put("Area_Name", a.getName());

					list.add(jo);
				}

				main.put("AreaList", list);
			}

			if (archList != null && archList.size() > 0)
			{
				JSONArray list = new JSONArray();

				for (Architecture a : archList)
				{
					JSONObject jo = new JSONObject();

					jo.put("Area_ID", a.getAreaID());
					jo.put("Arch_ID", a.getId());
					jo.put("Arch_Name", a.getName());

					list.add(jo);
				}

				main.put("ArchList", list);
			}

			PrintWriter out = response.getWriter();
			out.println(main.toString());
			out.flush();
			out.close();
		}
	}
}
