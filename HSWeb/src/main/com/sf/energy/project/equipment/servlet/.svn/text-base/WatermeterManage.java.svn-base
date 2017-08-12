package com.sf.energy.project.equipment.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
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
import com.sf.energy.project.equipment.model.WatermeterModel;
import com.sf.energy.project.right.dao.UsersArchitectureDao;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DataSiteDao;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.dao.MeterStyleDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.DataSite;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.project.system.model.MeterStyle;
import com.sf.energy.report.dao.SystemInfoDao;
import com.sf.energy.util.DataValidation;
import com.sf.energy.util.ExportHelper;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class WatermeterManage extends HttpServlet
{

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		} catch (ParseException e)
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
			RowsExceededException, WriteException, ParseException
	{
		String method = request.getParameter("method");

		if ("initSearchBox".equals(method))
			initSearchBox(request, response);

		if ("deleteWatermeter".equals(method))
			deleteWatermeter(request, response);

		if ("addWaterMeter".equals(method))
			addWaterMeter(request, response);

		if ("updateWatermeter".equals(method))
			updateWatermeter(request, response);

		if ("searchSomeWatermeter".equals(method))
			searchSomeWatermeter(request, response);

		if ("paginate".equals(method))
			paginate(request, response);

		if ("getWatermeterByArch".equals(method))
			getWatermeterByArch(request, response);

		if ("getAllAmmByArchAndFloor".equals(method))
			getAllAmmByArchAndFloor(request, response);

		if ("getAllWatermeterTree".equals(method))
			getAllWatermeterTree(request, response);

		if ("searchPaginate".equals(method))
			searchPaginate(request, response);

		if ("export".equals(method))
			export(request, response);

		if ("importWm".equals(method))
			importWm(request, response);

		if ("deleteSomeWatermeter".equals(method))
			deleteSomeWatermeter(request, response);

		if ("exportFailResult".equals(method))
			exportFailResult(request, response);
	}

	private void deleteSomeWatermeter(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterDao wd = new WatermeterDao();

		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "水表管理", "水表批量删除");

		String IDList = null;
		if (request.getParameter("IDList") != null)
			IDList = request.getParameter("IDList");
		else
			return;

		String[] strList = IDList.split(",");
		List<Integer> list = null;
		if (strList != null && strList.length > 0)
		{
			list = new LinkedList<Integer>();
			for (String num : strList)
			{
				list.add(Integer.parseInt(num));
			}
		}
		String info = "fail";
		if (wd.deleteSomeWatermeter(list))
			info = "success";

		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	private void importWm(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			ParseException, RowsExceededException, WriteException
	{

		ExportHelper dh = ExportHelper.getInstance();
		Map<String, File> imFailMap = new HashMap<String, File>();
		String saveFolder = "img";
		List<List<String>> result = null;
		File file = dh
				.getImportFile(request, response, saveFolder, "theWmFile");

		JSONArray main = new JSONArray();

		JSONObject jo = new JSONObject();

		if (file != null)
		{
			if (file.getAbsolutePath().endsWith(".xls"))
			{
				result = importWmToDB(file, request);
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
							+ new String("水表导如失败数据.xls".getBytes("gb2312"),
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
		WatermeterDao wd = new WatermeterDao();

		DictionaryValueDao dictValueDao = new DictionaryValueDao();
		ExportHelper dh = ExportHelper.getInstance();	
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "水表管理", "水表导出");

		int areaID = 0;
		if (request.getParameter("areaID") != null)
			areaID = Integer.parseInt(request.getParameter("areaID"));

		int arch = 0;
		if (request.getParameter("archID") != null)
			arch = Integer.parseInt(request.getParameter("archID"));

		int gather = 0;
		if (request.getParameter("gatherID") != null)
			gather = Integer.parseInt(request.getParameter("gatherID"));

		String address = null;
		if (!"".equals(request.getParameter("address"))
				&& request.getParameter("address") != null)
			address = request.getParameter("address");

		String name = null;
		if (!"".equals(request.getParameter("name"))
				&& request.getParameter("name") != null)
			name = request.getParameter("name");

		String num = null;
		if (!"".equals(request.getParameter("num"))
				&& request.getParameter("num") != null)
			num = request.getParameter("num");

		String sortLabel = "WaterMeter_ID";
		if (request.getParameter("SortLabel") != null)
			sortLabel = request.getParameter("SortLabel");

		boolean isAsc = false;
		if (request.getParameter("SortType") != null)
		{
			if ("Asc".equalsIgnoreCase(request.getParameter("SortType")))
				isAsc = true;

			if ("Desc".equalsIgnoreCase(request.getParameter("SortType")))
				isAsc = false;
		}

		Map<String, DictionaryValueModel> dvMap = dictValueDao.getMapByValue(6);

		String sql = wd.buildSql(areaID, arch, gather, address, name, num,
				sortLabel, isAsc);

		String[] theTitles =
			{ "用户编号(20Byte)", "用户名称(50Byte)", "用户联系电话(20Byte)", "所属建筑(100Byte)",
				"水表名称(70Byte)", "表计类型(50Byte)", "用水分类(50Byte)", "出厂编号(Byte)",
				"表计厂家(50Byte)", "通讯地址(30Byte)", "网关地址(12Byte)", "水表密码(8Byte)","用水性质(50Byte)","价格(20Byte)","是否总表(1Byte)","是否纳入计量(1Byte)","修正量(20Byte)" };

		String[] titles =
			{ "水表测量点", "所属建筑", "所属网关", "水表编号", "表名", "密码", "通讯地址", "厂家", "出厂编号",
				"资产号", "是否供水", "总示数", "用水类型", "用户编号", "用户名", "用户电话", "用户地址",
				"是否重点用户", "是否总表", "所属部门", "所在楼层", "表记类型", "是否纳入计量", "水表状态",
				"是否离线报警", "是否检测匹配模型", "修正量", ".是否检测漏水", "排漏参数", "倍率" };

		List<String> firstLine = new LinkedList<String>();

		List<WatermeterModel> list = wd.listWatermeter(sql);

		List<List<String>> data = new LinkedList<List<String>>();

		// 将标题加进去
		for (String title : theTitles)
		{
			firstLine.add(title);
		}

		data.add(firstLine);

		// 将查询结果数据加进去
		for (WatermeterModel am : list)
		{
			List<String> item = new LinkedList<String>();

			// 用户编号
			item.add(am.getCONSUMERNUM());

			// 用户名称
			item.add(am.getCONSUMERNAME());

			// 用户联系电话
			item.add(am.getCONSUMERPHONE());

			// 所属建筑
			item.add(am.getArchName() + "-" + am.getFLOOR());

			// 水表名称
			item.add(am.getWATERMETER_NAME());

			// 表计类型
			item.add(am.getMeterStyleName());

			// 用水类型
			item.add(am.getUseStyleName());

			// 出厂编号
			item.add(am.getMAKERCODE());

			// 表计厂家
			item.add(am.getMAKER());

			// 通讯地址
			item.add(am.getWATERMETER_485ADDRESS());

			// 网关地址
			item.add(am.getGatherAddress());

			// 水表密码
			item.add(am.getWATERMETER_PASSWORD());

			item.add(am.getUseAmStyleName());
			//"价格(20Byte)",
			AmmeterDao amDao=new AmmeterDao();
			try
			{
				String priceName=amDao.queryPriceNameByID(am.getPRICE_ID());
				item.add(priceName);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			item.add(am.getISCOUNTMETER()+"");

			item.add(am.getISCOMPUTATION()+"");

			item.add(am.getXIUZHENG()+"");
			data.add(item);
		}
		File file = dh.getExportFile(data);

		FileInputStream fis = new FileInputStream(file);
		byte[] fb = new byte[fis.available()];
		fis.read(fb);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String("水表导出文件.xls".getBytes("gb2312"), "iso8859-1"));

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

		file.delete();
	}

	private void getAllWatermeterTree(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{

		AreaDao areaDao = new AreaDao();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}
		List<Area> areaList = areaDao.display();

		if (areaList != null && areaList.size() > 0)
		{
			JSONObject tree = new JSONObject();

			for (Area a : areaList)
			{
				JSONObject jo = buildAreaNode(a, userID);
				if (jo != null)
					tree.put(a.getId(), jo);
			}
			// //System.out.println(tree.toString());
			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			out.flush();
			out.close();
		}
	}

	private JSONObject buildAreaNode(Area theArea, int userID)
			throws SQLException
	{

		ArchitectureDao archDao = new ArchitectureDao();
		JSONObject node = null;
		if (theArea != null)
		{
			List<Architecture> archList = archDao.queryArchByAreaID(
					theArea.getId(), userID);
			node = new JSONObject();
			node.put("name", theArea.getName());

			if (archList != null && archList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject archListNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for (Architecture arch : archList)
				{
					archListNode.put(arch.getId(), buildArchNode(arch));
				}

				jo.put("children", archListNode);
				node.put("additionalParameters", jo);
			} else
			{
				return null;
			}

		}

		return node;
	}

	private JSONObject buildArchNode(Architecture theArch) throws SQLException
	{
		JSONObject node = null;
		if (theArch != null)
		{
			node = new JSONObject();
			node.put("name", theArch.getName());

			if (theArch.getStorey() > 0)
			{
				node.put("type", "folder");
				JSONObject floorListNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for (int floor = 1; floor <= theArch.getStorey(); floor++)
				{
					floorListNode.put(floor, buildNodeInFloor(theArch, floor));
				}

				jo.put("children", floorListNode);
				node.put("additionalParameters", jo);
			} else
			{
				return null;
			}
		}

		return node;
	}

	private JSONObject buildNodeInFloor(Architecture theArch, int theFloor)
			throws SQLException
	{
		WatermeterDao wd = new WatermeterDao();
		JSONObject node = null;
		if (theArch != null)
		{
			node = new JSONObject();
			node.put("name", theFloor + "楼");
			List<WatermeterModel> amList = wd.queryByArchAndFloor(
					theArch.getId(), theFloor);

			if (amList != null && amList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject amListInFloor = new JSONObject();

				JSONObject jo = new JSONObject();

				for (WatermeterModel am : amList)
				{
					JSONObject amNode = new JSONObject();
					amNode.put("type", "item");
					amNode.put("name", am.getWATERMETER_NAME());
					amNode.put("WatermeterID", am.getWATERMETER_ID());
					amListInFloor.put(am.getWATERMETER_ID(), amNode);
				}

				jo.put("children", amListInFloor);
				node.put("additionalParameters", jo);
			} else
			{
				return null;
			}
		}

		return node;
	}

	private void getWatermeterByArch(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterDao wd = new WatermeterDao();
		int archID = 0;
		if (request.getParameter("Arch_ID") != null)
			archID = Integer.parseInt(request.getParameter("Arch_ID"));

		List<WatermeterModel> list = wd.getWatermeterByArch(archID);

		JSONArray json = new JSONArray();

		if (list != null)
			for (WatermeterModel n : list)
			{
				JSONObject jo = new JSONObject();
				jo.put("WATERMETER_ID", n.getWATERMETER_ID());
				jo.put("WATERMETER_POINT", n.getWATERMETER_POINT());
				jo.put("AREA_ID", n.getAREA_ID());
				jo.put("ARCHITECTURE_ID", n.getARCHITECTURE_ID());
				jo.put("PARENT_ID", n.getPARENT_ID());
				jo.put("GATHER_ID", n.getGATHER_ID());
				jo.put("WATERMETER_NUM", n.getWATERMETER_NUM());
				jo.put("WATERMETER_NAME", n.getWATERMETER_NAME());
				jo.put("WATERMETER_PASSWORD", n.getWATERMETER_PASSWORD());
				jo.put("WATERMETER_485ADDRESS", n.getWATERMETER_485ADDRESS());
				jo.put("MAKER", n.getMAKER());
				jo.put("MAKERCODE", n.getMAKERCODE());
				jo.put("ASSETNO", n.getASSETNO());
				jo.put("ISSUPPLY", n.getISSUPPLY());
				jo.put("ZVALUE", n.getZVALUE());
				jo.put("USEAMSTYLE", n.getUSEAMSTYLE());
				jo.put("CONSUMERNUM", n.getCONSUMERNUM());
				jo.put("CONSUMERNAME", n.getCONSUMERNAME());
				jo.put("CONSUMERPHONE", n.getCONSUMERPHONE());
				jo.put("CONSUMERADDRESS", n.getCONSUMERADDRESS());
				jo.put("ISIMPORTANTUSER", n.getISIMPORTANTUSER());
				jo.put("ISCOUNTMETER", n.getISCOUNTMETER());
				jo.put("PARTMENT", n.getPARTMENT());
				jo.put("FLOOR", n.getFLOOR());
				jo.put("METESTYLE_ID", n.getMETESTYLE_ID());
				jo.put("ISCOMPUTATION", n.getISCOMPUTATION());
				jo.put("PRICE_ID", n.getPRICE_ID());
				jo.put("WATERMETER_STAT", n.getWATERMETER_STAT());
				jo.put("ISOFFALARM", n.getISOFFALARM());
				jo.put("COSTCHECK", n.getCOSTCHECK());
				jo.put("STANDBYCHECK", n.getSTANDBYCHECK());
				jo.put("XIUZHENG", n.getXIUZHENG());

				if (n.getLASTTIME() != null)
					jo.put("LASTTIME", n.getLASTTIME());
				else
					jo.put("LASTTIME", null);

				jo.put("WLEAKAGECHECK", n.getWLEAKAGECHECK());
				jo.put("WLEAKAGEVALUE", n.getWLEAKAGEVALUE());
				jo.put("DATAFROM", n.getDATAFROM());
				jo.put("BEILV", n.getBEILV());
				jo.put("LimitPart", n.getLimitPart());

				json.add(jo);

			}

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void searchSomeWatermeter(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{

	}

	private void searchPaginate(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{

		WatermeterDao wd = new WatermeterDao();
		int areaID = 0;
		if (request.getParameter("areaID") != null)
			areaID = Integer.parseInt(request.getParameter("areaID"));

		int arch = 0;
		if (request.getParameter("archID") != null)
			arch = Integer.parseInt(request.getParameter("archID"));

		int gather = 0;
		if (request.getParameter("gatherID") != null)
			gather = Integer.parseInt(request.getParameter("gatherID"));

		String address = null;
		if (!"".equals(request.getParameter("address"))
				&& request.getParameter("address") != null)
			address = request.getParameter("address");

		String name = null;
		if (!"".equals(request.getParameter("name"))
				&& request.getParameter("name") != null)
			name = request.getParameter("name");

		String num = null;
		if (!"".equals(request.getParameter("num"))
				&& request.getParameter("num") != null)
			num = request.getParameter("num");

		Integer thePageCount = 10;
		if (request.getParameter("pageCount") != null)
			thePageCount = Integer.parseInt(request.getParameter("pageCount"));

		Integer thePageIndex = 0;
		if (request.getParameter("pageIndex") != null)
			thePageIndex = Integer.parseInt(request.getParameter("pageIndex"));

		String sortLabel = "WaterMeter_ID";
		if (request.getParameter("SortLabel") != null)
			sortLabel = request.getParameter("SortLabel");

		boolean isAsc = false;
		if (request.getParameter("SortType") != null)
		{
			if ("Asc".equalsIgnoreCase(request.getParameter("SortType")))
				isAsc = true;

			if ("Desc".equalsIgnoreCase(request.getParameter("SortType")))
				isAsc = false;
		}

		Map<String, Object> result = wd.queryPaginate(areaID, arch, gather,
				address, name, num, sortLabel, isAsc, thePageCount,
				thePageIndex);
		List<WatermeterModel> list = null;

		if (result != null && result.get("List") != null)
			list = (List<WatermeterModel>) result.get("List");

		JSONArray wmmeterList = new JSONArray();
		if (list != null && list.size() > 0)
		{
			JSONObject jo = new JSONObject();
			jo.put("totalCount", result.get("TotalCount"));
			wmmeterList.add(jo);

			for (int i = 0; i < list.size(); i++)
			{
				WatermeterModel n = list.get(i);
				JSONObject jo1 = buildNode(n);
				wmmeterList.add(jo1);
			}

		} else
		{
			JSONObject jo = new JSONObject();
			jo.put("totalCount", 0);
			wmmeterList.add(jo);
		}

		PrintWriter out = response.getWriter();
		out.println(wmmeterList.toString());
		out.flush();
		out.close();
	}

	private JSONObject buildNode(WatermeterModel n)
	{
		JSONObject jo = new JSONObject();

		jo.put("WATERMETER_ID", n.getWATERMETER_ID());
		jo.put("WATERMETER_POINT", n.getWATERMETER_POINT());
		jo.put("AREA_ID", n.getAREA_ID());
		jo.put("AREA_NAME", n.getAreaName());
		jo.put("ARCHITECTURE_ID", n.getARCHITECTURE_ID());
		jo.put("ARCHITECTURE_NAME", n.getArchName());
		jo.put("PARENT_ID", n.getPARENT_ID());
		jo.put("GATHER_ID", n.getGATHER_ID());
		jo.put("GATHER_NAME", n.getGatherName());
		jo.put("WATERMETER_NUM", n.getWATERMETER_NUM());
		jo.put("WATERMETER_NAME", n.getWATERMETER_NAME());
		jo.put("WATERMETER_PASSWORD", n.getWATERMETER_PASSWORD());
		jo.put("WATERMETER_485ADDRESS", n.getWATERMETER_485ADDRESS());
		jo.put("MAKER", n.getMAKER());
		jo.put("MAKERCODE", n.getMAKERCODE());
		jo.put("ASSETNO", n.getASSETNO());
		jo.put("ISSUPPLY", n.getISSUPPLY());
		jo.put("ZVALUE", n.getZVALUE());
		jo.put("USEAMSTYLE", n.getUSEAMSTYLE());
		jo.put("CONSUMERNUM", n.getCONSUMERNUM());
		jo.put("CONSUMERNAME", n.getCONSUMERNAME());
		jo.put("CONSUMERPHONE", n.getCONSUMERPHONE());
		jo.put("CONSUMERADDRESS", n.getCONSUMERADDRESS());
		jo.put("ISIMPORTANTUSER", n.getISIMPORTANTUSER());
		jo.put("ISCOUNTMETER", n.getISCOUNTMETER());
		jo.put("PARTMENT", n.getPARTMENT());
		jo.put("FLOOR", n.getFLOOR());
		jo.put("METESTYLE_ID", n.getMETESTYLE_ID());
		jo.put("ISCOMPUTATION", n.getISCOMPUTATION());
		jo.put("PRICE_ID", n.getPRICE_ID());
		jo.put("WATERMETER_STAT", n.getWATERMETER_STAT());
		jo.put("ISOFFALARM", n.getISOFFALARM());
		jo.put("COSTCHECK", n.getCOSTCHECK());
		jo.put("STANDBYCHECK", n.getSTANDBYCHECK());
		jo.put("XIUZHENG", n.getXIUZHENG());
		if (n.getLASTTIME() != null)
			jo.put("LASTTIME", n.getLASTTIME());
		else
			jo.put("LASTTIME", null);
		jo.put("WLEAKAGECHECK", n.getWLEAKAGECHECK());
		jo.put("WLEAKAGEVALUE", n.getWLEAKAGEVALUE());
		jo.put("DATAFROM", n.getDATAFROM());
		jo.put("BEILV", n.getBEILV());
		jo.put("LimitPart", n.getLimitPart());
		jo.put("isSale", n.getIsSale());
		jo.put("isRecycle", n.getIsRecycle());

		return jo;
	}

	private void paginate(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterDao wd = new WatermeterDao();
		int thePageCount = 0;
		if(DataValidation.checkParameter(request.getParameter("WatermeterPageCount")))
			thePageCount = Integer.parseInt(request
					.getParameter("WatermeterPageCount"));

		int thePageIndex = 0;
		if(DataValidation.checkParameter(request.getParameter("WatermeterPageIndex")))
			thePageIndex = Integer.parseInt(request
					.getParameter("WatermeterPageIndex"));
		
		List<WatermeterModel> list = wd.paginate(thePageCount, thePageIndex);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", wd.getRecordCount());
		json.add(jo);

		for (WatermeterModel n : list)
		{
			jo.put("WATERMETER_ID", n.getWATERMETER_ID());
			jo.put("WATERMETER_POINT", n.getWATERMETER_POINT());
			jo.put("AREA_ID", n.getAREA_ID());
			jo.put("ARCHITECTURE_ID", n.getARCHITECTURE_ID());
			jo.put("PARENT_ID", n.getPARENT_ID());
			jo.put("GATHER_ID", n.getGATHER_ID());
			jo.put("WATERMETER_NUM", n.getWATERMETER_NUM());
			jo.put("WATERMETER_NAME", n.getWATERMETER_NAME());
			jo.put("WATERMETER_PASSWORD", n.getWATERMETER_PASSWORD());
			jo.put("WATERMETER_485ADDRESS", n.getWATERMETER_485ADDRESS());
			jo.put("MAKER", n.getMAKER());
			jo.put("MAKERCODE", n.getMAKERCODE());
			jo.put("ASSETNO", n.getASSETNO());
			jo.put("ISSUPPLY", n.getISSUPPLY());
			jo.put("ZVALUE", n.getZVALUE());
			jo.put("USEAMSTYLE", n.getUSEAMSTYLE());
			jo.put("CONSUMERNUM", n.getCONSUMERNUM());
			jo.put("CONSUMERNAME", n.getCONSUMERNAME());
			jo.put("CONSUMERPHONE", n.getCONSUMERPHONE());
			jo.put("CONSUMERADDRESS", n.getCONSUMERADDRESS());
			jo.put("ISIMPORTANTUSER", n.getISIMPORTANTUSER());
			jo.put("ISCOUNTMETER", n.getISCOUNTMETER());
			jo.put("PARTMENT", n.getPARTMENT());
			jo.put("FLOOR", n.getFLOOR());
			jo.put("METESTYLE_ID", n.getMETESTYLE_ID());
			jo.put("ISCOMPUTATION", n.getISCOMPUTATION());
			jo.put("PRICE_ID", n.getPRICE_ID());
			jo.put("WATERMETER_STAT", n.getWATERMETER_STAT());
			jo.put("ISOFFALARM", n.getISOFFALARM());
			jo.put("COSTCHECK", n.getCOSTCHECK());
			jo.put("STANDBYCHECK", n.getSTANDBYCHECK());
			jo.put("XIUZHENG", n.getXIUZHENG());
			if (n.getLASTTIME() != null)
				jo.put("LASTTIME", n.getLASTTIME());
			else
				jo.put("LASTTIME", null);
			jo.put("WLEAKAGECHECK", n.getWLEAKAGECHECK());
			jo.put("WLEAKAGEVALUE", n.getWLEAKAGEVALUE());
			jo.put("DATAFROM", n.getDATAFROM());
			jo.put("BEILV", n.getBEILV());
			jo.put("LimitPart", n.getLimitPart());

			json.add(jo);

		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void updateWatermeter(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterDao wd = new WatermeterDao();
		WatermeterModel wm = new WatermeterModel();

		
		int WATERMETER_ID = 0;
		int PARENT_ID;

		if (request.getParameter("WATERMETER_ID") != null
				&& DataValidation.isNumber(request
						.getParameter("WATERMETER_ID")))
		{
			WATERMETER_ID=Integer.parseInt(request.getParameter("WATERMETER_ID"));
			wm.setWATERMETER_ID(Integer.parseInt(request
					.getParameter("WATERMETER_ID")));
		}
		wm = wd.getWatermeterByID(WATERMETER_ID);

		if (wm == null)
			return;
		int oldMeteStyleID=wm.getMETESTYLE_ID();
		float oldBeiLv=wm.getBEILV();
		float oldXiuZheng=wm.getXIUZHENG();	

		if (request.getParameter("WATERMETER_POINT") != null
				&& DataValidation.isNumber(request
						.getParameter("WATERMETER_POINT")))
			wm.setWATERMETER_POINT(Integer.parseInt(request
					.getParameter("WATERMETER_POINT")));

		if (request.getParameter("AREA_ID") != null
				&& DataValidation.isNumber(request.getParameter("AREA_ID")))
			wm.setAREA_ID(Integer.parseInt(request.getParameter("AREA_ID")));

		if (request.getParameter("ARCHITECTURE_ID") != null
				&& DataValidation.isNumber(request
						.getParameter("ARCHITECTURE_ID")))
			wm.setARCHITECTURE_ID(Integer.parseInt(request
					.getParameter("ARCHITECTURE_ID")));

		if (request.getParameter("PARENT_ID") != null
				&& DataValidation.isNumber(request.getParameter("PARENT_ID")))
		{
			PARENT_ID=Integer.parseInt(request.getParameter("PARENT_ID"));
			if(WATERMETER_ID==PARENT_ID)
			{
				wm.setPARENT_ID(0);
			}
			else
			{
				wm.setPARENT_ID(Integer.parseInt(request.getParameter("PARENT_ID")));
			}

		}

		if (request.getParameter("GATHER_ID") != null
				&& DataValidation.isNumber(request.getParameter("GATHER_ID")))
			wm.setGATHER_ID(Integer.parseInt(request.getParameter("GATHER_ID")));
		if (request.getParameter("isSale") != null
				&& DataValidation.isNumber(request.getParameter("isSale")))
			wm.setIsSale(Integer.parseInt(request.getParameter("isSale")));
		if (request.getParameter("isRecycle") != null
				&& DataValidation.isNumber(request.getParameter("isRecycle")))
			wm.setIsRecycle(Integer.parseInt(request.getParameter("isRecycle")));
		
		//		if (request.getParameter("WATERMETER_NUM") != null)
		//			wm.setWATERMETER_NUM(request.getParameter("WATERMETER_NUM"));

		if (request.getParameter("WATERMETER_NUM") != null){
			String waterMeter_Num = request.getParameter("WATERMETER_NUM");
			ArchitectureDao archDao=new ArchitectureDao();
			SystemInfoDao sid = new SystemInfoDao();
			Architecture arch = archDao.queryByID(wm.getARCHITECTURE_ID());
			String districtNum = sid.querySystemInfo(1).getSystemInfo_xznum();
			districtNum = padLeft(districtNum, 6);
			waterMeter_Num = districtNum + "F" + arch.getStyle() + arch.getNum() + "02" + waterMeter_Num + "00";
			wm.setWATERMETER_NUM(waterMeter_Num);
		}
		/*		if (request.getParameter("WATERMETER_NUM") != null){
			String waterMeter_Num = request.getParameter("WATERMETER_NUM");

			Architecture arch = archDao.queryByID(wm.getARCHITECTURE_ID());
			String districtNum = sid.querySystemInfo(1).getSystemInfo_xznum();
			districtNum = padLeft(districtNum, 6);
			waterMeter_Num = districtNum + "F" + arch.getStyle() + arch.getNum() + "02" + waterMeter_Num + "00";
			wm.setWATERMETER_NUM(waterMeter_Num);
		}*/

		if (request.getParameter("WATERMETER_NAME") != null)
			wm.setWATERMETER_NAME(request.getParameter("WATERMETER_NAME"));

		if (request.getParameter("WATERMETER_PASSWORD") != null)
			wm.setWATERMETER_PASSWORD(request
					.getParameter("WATERMETER_PASSWORD"));

		if (request.getParameter("WATERMETER_485ADDRESS") != null)
			wm.setWATERMETER_485ADDRESS(padLeft(request
					.getParameter("WATERMETER_485ADDRESS"),12));

		if (request.getParameter("MAKER") != null)
			wm.setMAKER(request.getParameter("MAKER"));

		if (request.getParameter("MAKERCODE") != null)
			wm.setMAKERCODE(request.getParameter("MAKERCODE"));

		if (request.getParameter("ASSETNO") != null)
			wm.setASSETNO(request.getParameter("ASSETNO"));

		if (request.getParameter("ISSUPPLY") != null
				&& DataValidation.isNumber(request.getParameter("ISSUPPLY")))
			wm.setISSUPPLY(Integer.parseInt(request.getParameter("ISSUPPLY")));

		if (request.getParameter("ZVALUE") != null
				&& DataValidation.isNumber(request.getParameter("ZVALUE")))
			wm.setZVALUE(Integer.parseInt(request.getParameter("ZVALUE")));

		if (request.getParameter("USEAMSTYLE") != null
				&& DataValidation.isNumber(request.getParameter("USEAMSTYLE")))
			wm.setUSEAMSTYLE(Integer.parseInt(request
					.getParameter("USEAMSTYLE")));

		if (request.getParameter("CONSUMERNUM") != null)
			wm.setCONSUMERNUM(request.getParameter("CONSUMERNUM"));

		if (request.getParameter("CONSUMERNAME") != null)
			wm.setCONSUMERNAME(request.getParameter("CONSUMERNAME"));

		if (request.getParameter("CONSUMERPHONE") != null)
			wm.setCONSUMERPHONE(request.getParameter("CONSUMERPHONE"));

		if (request.getParameter("CONSUMERADDRESS") != null)
			wm.setCONSUMERADDRESS(request.getParameter("CONSUMERADDRESS"));

		if (request.getParameter("ISIMPORTANTUSER") != null
				&& DataValidation.isNumber(request
						.getParameter("ISIMPORTANTUSER")))
			wm.setISIMPORTANTUSER(Integer.parseInt(request
					.getParameter("ISIMPORTANTUSER")));

		if (request.getParameter("ISCOUNTMETER") != null
				&& DataValidation
				.isNumber(request.getParameter("ISCOUNTMETER")))
			wm.setISCOUNTMETER(Integer.parseInt(request
					.getParameter("ISCOUNTMETER")));

		if (request.getParameter("PARTMENT") != null
				&& DataValidation.isNumber(request.getParameter("PARTMENT")))
			wm.setPARTMENT(Integer.parseInt(request.getParameter("PARTMENT")));

		if (request.getParameter("FLOOR") != null
				&& DataValidation.isNumber(request.getParameter("FLOOR")))
			wm.setFLOOR(Integer.parseInt(request.getParameter("FLOOR")));

		if (request.getParameter("METESTYLE_ID") != null
				&& DataValidation
				.isNumber(request.getParameter("METESTYLE_ID")))
			wm.setMETESTYLE_ID(Integer.parseInt(request
					.getParameter("METESTYLE_ID")));

		if (request.getParameter("ISCOMPUTATION") != null
				&& DataValidation.isNumber(request
						.getParameter("ISCOMPUTATION")))
			wm.setISCOMPUTATION(Integer.parseInt(request
					.getParameter("ISCOMPUTATION")));

		if (request.getParameter("PRICE_ID") != null
				&& DataValidation.isNumber(request.getParameter("PRICE_ID")))
			wm.setPRICE_ID(Integer.parseInt(request.getParameter("PRICE_ID")));

		if (request.getParameter("WATERMETER_STAT") != null
				&& DataValidation.isNumber(request
						.getParameter("WATERMETER_STAT")))
			wm.setWATERMETER_STAT(Integer.parseInt(request
					.getParameter("WATERMETER_STAT")));

		if (request.getParameter("ISOFFALARM") != null
				&& DataValidation.isNumber(request.getParameter("ISOFFALARM")))
			wm.setISOFFALARM(Integer.parseInt(request
					.getParameter("ISOFFALARM")));

		if (request.getParameter("COSTCHECK") != null
				&& DataValidation.isNumber(request.getParameter("COSTCHECK")))
			wm.setCOSTCHECK(Integer.parseInt(request.getParameter("COSTCHECK")));

		if (request.getParameter("STANDBYCHECK") != null
				&& DataValidation
				.isNumber(request.getParameter("STANDBYCHECK")))
			wm.setSTANDBYCHECK(Integer.parseInt(request
					.getParameter("STANDBYCHECK")));

		if (request.getParameter("XIUZHENG") != null
				&& DataValidation.isNumber(request.getParameter("XIUZHENG")))
			wm.setXIUZHENG(Integer.parseInt(request.getParameter("XIUZHENG")));

		// if (request.getParameter("LASTTIME") != null)
		// wm.setLASTTIME(Integer.parseInt(request.getParameter("LASTTIME")));

		// wm.setLASTTIME(rs.getDate("LASTTIME"));

		if (request.getParameter("WLEAKAGECHECK") != null
				&& DataValidation.isNumber(request
						.getParameter("WLEAKAGECHECK")))
			wm.setWLEAKAGECHECK(Integer.parseInt(request
					.getParameter("WLEAKAGECHECK")));

		if (request.getParameter("WLEAKAGEVALUE") != null
				&& DataValidation.isNumber(request
						.getParameter("WLEAKAGEVALUE")))
			wm.setWLEAKAGEVALUE(Integer.parseInt(request
					.getParameter("WLEAKAGEVALUE")));

		if (request.getParameter("DATAFROM") != null
				&& DataValidation.isNumber(request.getParameter("DATAFROM")))
			wm.setDATAFROM(Integer.parseInt(request.getParameter("DATAFROM")));

		if (request.getParameter("BEILV") != null
				&& DataValidation.isNumber(request.getParameter("BEILV")))
			wm.setBEILV(Integer.parseInt(request.getParameter("BEILV")));

		if (request.getParameter("LimitPart") != null
				&& DataValidation.isNumber(request.getParameter("LimitPart")))
			wm.setLimitPart(Float.parseFloat(request.getParameter("LimitPart")));

		if (request.getParameter("FenLei") != null)
			wm.setFenlei(request.getParameter("FenLei"));

		String resultInfo = null;
		if (wd.hasUpdateRepeat(wm))
		{
			resultInfo = "同一网关下不允许重复的测量点或通讯地址";
		} else if (wd.updateWatermeter(wm)){
			if(oldXiuZheng!=wm.getXIUZHENG()){
				wd.updateAmMeterDatasWithChangeXiuzheng(wm.getWATERMETER_ID(),oldXiuZheng,wm.getXIUZHENG());
			}
			if(oldBeiLv!=wm.getBEILV()){
				wd.updateAmMeterDatasWithChangeBeiLV(wm.getWATERMETER_ID(),oldBeiLv,wm.getBEILV(),wm.getXIUZHENG());
			}
			resultInfo = "success";
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void deleteWatermeter(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterDao wd = new WatermeterDao();	
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "水表管理", "水表删除");

		if (request.getParameter("WATERMETER_ID") != null)
		{
			Integer ID = Integer
					.parseInt(request.getParameter("WATERMETER_ID"));

			String resultInfo = null;
			if (wd.deleteWatermeterByID(ID))
				resultInfo = "success";
			else
				resultInfo = "fail";

			PrintWriter out = response.getWriter();
			out.println(resultInfo);
			out.flush();
			out.close();
		}

	}

	private void addWaterMeter(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterDao wd = new WatermeterDao();	
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		
		// 写入日志
		log.writeLog(userLoginName, "水表管理", "水表新增");

		WatermeterModel wm = new WatermeterModel();

		if (request.getParameter("WATERMETER_POINT") != null
				&& DataValidation.isNumber(request
						.getParameter("WATERMETER_POINT")))
			wm.setWATERMETER_POINT(Integer.parseInt(request
					.getParameter("WATERMETER_POINT")));

		if (request.getParameter("AREA_ID") != null
				&& DataValidation.isNumber(request.getParameter("AREA_ID")))
			wm.setAREA_ID(Integer.parseInt(request.getParameter("AREA_ID")));

		if (request.getParameter("ARCHITECTURE_ID") != null
				&& DataValidation.isNumber(request
						.getParameter("ARCHITECTURE_ID")))
			wm.setARCHITECTURE_ID(Integer.parseInt(request
					.getParameter("ARCHITECTURE_ID")));

		if (request.getParameter("PARENT_ID") != null
				&& DataValidation.isNumber(request.getParameter("PARENT_ID")))
			wm.setPARENT_ID(Integer.parseInt(request.getParameter("PARENT_ID")));

		if (request.getParameter("GATHER_ID") != null
				&& DataValidation.isNumber(request.getParameter("GATHER_ID")))
			wm.setGATHER_ID(Integer.parseInt(request.getParameter("GATHER_ID")));
		if (request.getParameter("isSale") != null
				&& DataValidation.isNumber(request.getParameter("isSale")))
			wm.setIsSale(Integer.parseInt(request.getParameter("isSale")));
		if (request.getParameter("isRecycle") != null
				&& DataValidation.isNumber(request.getParameter("isRecycle")))
			wm.setIsRecycle(Integer.parseInt(request.getParameter("isRecycle")));
		//		if (request.getParameter("WATERMETER_NUM") != null)
		//			wm.setWATERMETER_NUM(request.getParameter("WATERMETER_NUM"));
		if (request.getParameter("WATERMETER_NUM") != null){
			String waterMeter_Num = request.getParameter("WATERMETER_NUM");
			ArchitectureDao archDao=new ArchitectureDao();
			SystemInfoDao sid = new SystemInfoDao();
			Architecture arch = archDao.queryByID(wm.getARCHITECTURE_ID());
			String districtNum = sid.querySystemInfo(1).getSystemInfo_xznum();
			districtNum = padLeft(districtNum, 6);
			waterMeter_Num = districtNum + "F" + arch.getStyle() + arch.getNum() + "02" + waterMeter_Num + "00";
			wm.setWATERMETER_NUM(waterMeter_Num);
		}
		if (request.getParameter("WATERMETER_NAME") != null)
			wm.setWATERMETER_NAME(request.getParameter("WATERMETER_NAME"));

		if (request.getParameter("WATERMETER_PASSWORD") != null)
			wm.setWATERMETER_PASSWORD(request
					.getParameter("WATERMETER_PASSWORD"));

		if (request.getParameter("WATERMETER_485ADDRESS") != null)
			wm.setWATERMETER_485ADDRESS(padLeft(request
					.getParameter("WATERMETER_485ADDRESS"),12));

		if (request.getParameter("MAKER") != null)
			wm.setMAKER(request.getParameter("MAKER"));

		if (request.getParameter("MAKERCODE") != null)
			wm.setMAKERCODE(request.getParameter("MAKERCODE"));

		if (request.getParameter("ASSETNO") != null)
			wm.setASSETNO(request.getParameter("ASSETNO"));

		if (request.getParameter("ISSUPPLY") != null
				&& DataValidation.isNumber(request.getParameter("ISSUPPLY")))
			wm.setISSUPPLY(Integer.parseInt(request.getParameter("ISSUPPLY")));

		if (request.getParameter("ZVALUE") != null
				&& DataValidation.isNumber(request.getParameter("ZVALUE")))
			wm.setZVALUE(Integer.parseInt(request.getParameter("ZVALUE")));

		if (request.getParameter("USEAMSTYLE") != null
				&& DataValidation.isNumber(request.getParameter("USEAMSTYLE")))
			wm.setUSEAMSTYLE(Integer.parseInt(request
					.getParameter("USEAMSTYLE")));

		if (request.getParameter("CONSUMERNUM") != null)
			wm.setCONSUMERNUM(request.getParameter("CONSUMERNUM"));

		if (request.getParameter("CONSUMERNAME") != null)
			wm.setCONSUMERNAME(request.getParameter("CONSUMERNAME"));

		if (request.getParameter("CONSUMERPHONE") != null)
			wm.setCONSUMERPHONE(request.getParameter("CONSUMERPHONE"));

		if (request.getParameter("CONSUMERADDRESS") != null)
			wm.setCONSUMERADDRESS(request.getParameter("CONSUMERADDRESS"));

		if (request.getParameter("ISIMPORTANTUSER") != null
				&& DataValidation.isNumber(request
						.getParameter("ISIMPORTANTUSER")))
			wm.setISIMPORTANTUSER(Integer.parseInt(request
					.getParameter("ISIMPORTANTUSER")));

		if (request.getParameter("ISCOUNTMETER") != null
				&& DataValidation
				.isNumber(request.getParameter("ISCOUNTMETER")))
			wm.setISCOUNTMETER(Integer.parseInt(request
					.getParameter("ISCOUNTMETER")));

		if (request.getParameter("PARTMENT") != null
				&& DataValidation.isNumber(request.getParameter("PARTMENT")))
			wm.setPARTMENT(Integer.parseInt(request.getParameter("PARTMENT")));

		if (request.getParameter("FLOOR") != null
				&& DataValidation.isNumber(request.getParameter("FLOOR")))
			wm.setFLOOR(Integer.parseInt(request.getParameter("FLOOR")));

		if (request.getParameter("METESTYLE_ID") != null
				&& DataValidation
				.isNumber(request.getParameter("METESTYLE_ID")))
			wm.setMETESTYLE_ID(Integer.parseInt(request
					.getParameter("METESTYLE_ID")));

		if (request.getParameter("ISCOMPUTATION") != null
				&& DataValidation.isNumber(request
						.getParameter("ISCOMPUTATION")))
			wm.setISCOMPUTATION(Integer.parseInt(request
					.getParameter("ISCOMPUTATION")));

		if (request.getParameter("PRICE_ID") != null
				&& DataValidation.isNumber(request.getParameter("PRICE_ID")))
			wm.setPRICE_ID(Integer.parseInt(request.getParameter("PRICE_ID")));

		if (request.getParameter("WATERMETER_STAT") != null
				&& DataValidation.isNumber(request
						.getParameter("WATERMETER_STAT")))
			wm.setWATERMETER_STAT(Integer.parseInt(request
					.getParameter("WATERMETER_STAT")));

		if (request.getParameter("ISOFFALARM") != null
				&& DataValidation.isNumber(request.getParameter("ISOFFALARM")))
			wm.setISOFFALARM(Integer.parseInt(request
					.getParameter("ISOFFALARM")));

		if (request.getParameter("COSTCHECK") != null
				&& DataValidation.isNumber(request.getParameter("COSTCHECK")))
			wm.setCOSTCHECK(Integer.parseInt(request.getParameter("COSTCHECK")));

		if (request.getParameter("STANDBYCHECK") != null
				&& DataValidation
				.isNumber(request.getParameter("STANDBYCHECK")))
			wm.setSTANDBYCHECK(Integer.parseInt(request
					.getParameter("STANDBYCHECK")));

		if (request.getParameter("XIUZHENG") != null
				&& DataValidation.isNumber(request.getParameter("XIUZHENG")))
			wm.setXIUZHENG(Integer.parseInt(request.getParameter("XIUZHENG")));

		// if (request.getParameter("LASTTIME") != null)
		// wm.setLASTTIME(Integer.parseInt(request.getParameter("LASTTIME")));

		// wm.setLASTTIME(rs.getDate("LASTTIME"));

		if (request.getParameter("WLEAKAGECHECK") != null
				&& DataValidation.isNumber(request
						.getParameter("WLEAKAGECHECK")))
			wm.setWLEAKAGECHECK(Integer.parseInt(request
					.getParameter("WLEAKAGECHECK")));

		if (request.getParameter("WLEAKAGEVALUE") != null
				&& DataValidation.isNumber(request
						.getParameter("WLEAKAGEVALUE")))
			wm.setWLEAKAGEVALUE(Integer.parseInt(request
					.getParameter("WLEAKAGEVALUE")));

		if (request.getParameter("DATAFROM") != null
				&& DataValidation.isNumber(request.getParameter("DATAFROM")))
			wm.setDATAFROM(Integer.parseInt(request.getParameter("DATAFROM")));

		if (request.getParameter("BEILV") != null
				&& DataValidation.isNumber(request.getParameter("BEILV")))
			wm.setBEILV(Integer.parseInt(request.getParameter("BEILV")));

		if (request.getParameter("LimitPart") != null
				&& DataValidation.isNumber(request.getParameter("LimitPart")))
			wm.setLimitPart(Integer.parseInt(request.getParameter("LimitPart")));

		//		if (request.getParameter("FenLei") != null)
		//			wm.setFenlei(request.getParameter("FenLei"));

		String resultInfo = null;

		if (wd.hasAddRepeat(wm))
		{
			resultInfo = "同一网关下不允许重复的测量点或通讯地址";
		} else if (wd.addWatermeter(wm))
			resultInfo = "success";
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void getAllAmmByArchAndFloor(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		WatermeterDao wd = new WatermeterDao();
		int archID = 0;
		int floor = 0;
		if (request.getParameter("archID") != null)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}
		if (request.getParameter("floor") != null)
		{
			floor = Integer.parseInt(request.getParameter("floor"));
		}

		List<WatermeterModel> list = wd.queryByArchAndFloor(archID, floor);

		if (list == null || list.size() == 0)
		{
			list = new LinkedList<WatermeterModel>();
		}
		JSONArray json = new JSONArray();
		JSONObject jo = null;

		for (int i = 0; i < list.size(); i++)
		{
			WatermeterModel n = list.get(i);
			jo = new JSONObject();
			jo.put("Ammeter_ID", n.getWATERMETER_ID());
			jo.put("Ammeter_Name", n.getWATERMETER_NAME());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private List<List<String>> importWmToDB(File amFile,
			HttpServletRequest request) throws SQLException, ParseException
			{
		WatermeterDao wd = new WatermeterDao();
		AmmeterDao ad = new AmmeterDao();
		DataSiteDao dataSiteDao = new DataSiteDao();
		AreaDao areaDao = new AreaDao();
		ArchitectureDao archDao = new ArchitectureDao();
		DictionaryValueDao dictValueDao = new DictionaryValueDao();
		MeterStyleDao msDao = new MeterStyleDao();
		GatherDao gatherDao = new GatherDao();
		DepartmentDao groupDao = new DepartmentDao();
		DictionaryValueDao dvd = new DictionaryValueDao();
		List<List<String>> failList = new LinkedList<List<String>>();
		String[] theTitles =
			{ "用户编号", "用户名称", "用户联系电话", "所属建筑", "水表名称", "表计类型", "用水分类", "出厂编号",
				"表计厂家", "通讯地址", "网关地址", "水表密码", "用水性质","价格","是否总表","是否纳入计量","修正量","错误信息" };
		List<String> firstLine = new LinkedList<String>();

		// 将标题加进去
		for (String title : theTitles)
		{
			firstLine.add(title);
		}

		failList.add(firstLine);
		Map<String, Architecture> archMap = archDao.getMapDataByArchName();
		Map<String, MeterStyle> msMap = msDao.getMapByName();
		Map<String, DictionaryValueModel> dvMap = dictValueDao.getMapByValue(25);
		Map<String, DictionaryValueModel> dvMap2 = dictValueDao.getMapByValue(26);
		Map<String, Gather> gatherMap = gatherDao.getMapByAdddr();
		Map<String, Department> groupMap = groupDao.getAllDepartMapByName();
		List<DictionaryValueModel> styleList = dvd
				.getDictionaryValueByDictionaryID(3);
		DataSite dataSite = dataSiteDao.getFirstDataSite();
		Workbook book = null;
		try
		{
			book = Workbook.getWorkbook(amFile);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			List<WatermeterModel> list = new LinkedList<WatermeterModel>();
			boolean flag = true;
			String errorInfo = "";
			// 得到单元格
			for (int i = 1; i < sheet.getRows(); i++)
			{
				WatermeterModel am = new WatermeterModel();
				errorInfo = "";
				flag = true;
				if (sheet.getColumns() >= 16)
				{
					// 用户号
					am.setCONSUMERNUM(sheet.getCell(0, i).getContents());

					// 用户名
					am.setCONSUMERNAME(sheet.getCell(1, i).getContents());

					// 用户电话
					am.setCONSUMERPHONE(sheet.getCell(2, i).getContents());

					String archInfo = sheet.getCell(3, i).getContents();

					// 所属建筑
					Architecture theArch = null;
					if (archInfo != null
							&& archInfo.length() > 0
							&& archInfo.lastIndexOf("-") > -1
							&& archInfo.lastIndexOf("-") < (archInfo.length() - 1))
					{
						String archName = archInfo.substring(0,
								archInfo.lastIndexOf("-"));
						String floor = archInfo.substring(archInfo
								.lastIndexOf("-") + 1);

						if (archName != null && archName.length() > 0)
						{
							if (archMap.get(archName) == null)
							{
								theArch = new Architecture();
								theArch.setName(archName);
								theArch.setAreaID(areaDao.getFirstArea()
										.getId());
								if (DataValidation.isNumber(floor))
								{
									// 楼层
									theArch.setStorey(Integer.parseInt(floor));
								}
								if (styleList != null && styleList.size() > 0)
								{
									theArch.setStyle(styleList.get(0)
											.getDictionaryValueNum());
								}
								archDao.add(theArch);
								// 新增建筑权限赋给超级管理员
								HttpSession session = request.getSession();
								int userID = 0;
								if (session.getAttribute("userId") != null)
								{
									userID = Integer.parseInt(session
											.getAttribute("userId").toString());
								}
								UsersArchitectureDao uad = new UsersArchitectureDao();
								uad.rightAfterInsert(archName, userID);
								archMap = archDao.getMapDataByArchName();
							}
						} else
						{
							flag = false;
							errorInfo += "水表所属建筑名称不得为空;";
						}
						theArch = archMap.get(archName);
						am.setARCHITECTURE_ID(archMap.get(archName).getId());
						am.setAREA_ID(archMap.get(archName).getAreaID());

						if (DataValidation.isNumber(floor))
						{
							// 楼层
							am.setFLOOR(Integer.parseInt(floor));
						} else
						{
							flag = false;
							errorInfo += "楼层部分需为数字;";
						}
					} else
					{
						flag = false;
						errorInfo += "建筑名称一栏格式不对，应该用'-'连接建筑名和楼层，形如XXXX-XX;";
					}

					// 电表名称
					String amName = sheet.getCell(4, i).getContents();
					am.setWATERMETER_NAME(amName);

					String meterStyleName = sheet.getCell(5, i).getContents();
					if(meterStyleName!=null&&meterStyleName!=""){
						// 表计类型
						if (msMap.get(meterStyleName) == null)
						{
							MeterStyle meter = new MeterStyle();
							meter.setMeterStyleName(meterStyleName);
							meter.setMeterStyleType("水表");
							meter.setMeterStyleNum("10");
							msDao.add(meter);

							msMap = msDao.getMapByName();
						}
						am.setMETESTYLE_ID(msMap.get(meterStyleName)
								.getMeterStyleID());
					}
					am.setBEILV(msDao.getBeiLvByMSID(msMap.get(meterStyleName)
							.getMeterStyleID()));
					String Xingzhi = sheet.getCell(12, i).getContents();

					if (dvMap.get(Xingzhi) != null)
					{
						// 用水性质
						am.setWATERMETER_NUM(dvMap.get(Xingzhi)
								.getDictionaryValueNum());
					}
					String usStyName = sheet.getCell(6, i).getContents();
					if (dvMap2.get(usStyName) != null)
					{
						// 用水分项
						am.setUSEAMSTYLE(Integer.parseInt(dvMap2.get(usStyName)
								.getDictionaryValueNum()));
					}
					// 出厂编号
					am.setMAKERCODE(sheet.getCell(7, i).getContents());

					// 生产厂家
					am.setMAKER(sheet.getCell(8, i).getContents());

					// 表计485地址
					String amAddr = sheet.getCell(9, i).getContents();
					amAddr=padLeft(amAddr,12);
					am.setWATERMETER_485ADDRESS(amAddr);

					String gatherAddr = sheet.getCell(10, i).getContents();
					int maxPoint = 0;
					if (gatherAddr != null && gatherAddr.length() > 0)
					{
						if (gatherMap.get(gatherAddr) == null)
						{
							Gather gath = new Gather();
							gath.setGatherAddress(gatherAddr);
							gath.setGatherNum(gatherAddr);
							gath.setGatherName(gatherAddr);
							gath.setInstallAddress(theArch.getName());
							gath.setGatherPw("11111111");
							gath.setAreaID(theArch.getAreaID());
							if (dataSite != null)
							{
								gath.setDatasiteID(dataSite.getDatasiteID());
							}
							gatherDao.add(gath);
							gatherMap = gatherDao.getMapByAdddr();
							maxPoint = 2;
						} else
						{
							int amMax = ad.getMaxPointByGather(gatherMap.get(
									gatherAddr).getGatherID());
							int wmMax = wd.getMaxPointByGather(gatherMap.get(
									gatherAddr).getGatherID());
							maxPoint = Math.max(amMax, wmMax) + 1;
						}

						// 所属网关
						am.setGATHER_ID(gatherMap.get(gatherAddr).getGatherID());

						// 得到最大测量点
						am.setWATERMETER_POINT(maxPoint);

						SystemInfoDao sid = new SystemInfoDao();
						Architecture arch = archDao.queryByID(am.getARCHITECTURE_ID());
						String districtNum = sid.querySystemInfo(1).getSystemInfo_xznum();
						districtNum = padLeft(districtNum, 6);
						String waterMeter_Num = districtNum + "F" + arch.getStyle() + arch.getNum() + "02" + am.getWATERMETER_NUM() + "00";
						am.setWATERMETER_NUM(waterMeter_Num);

					} else
					{
						flag = false;
						errorInfo += "网关地址不得为空;";
					}

					// 密码
					am.setWATERMETER_PASSWORD(sheet.getCell(11, i)
							.getContents());

					AmmeterDao amDao=new AmmeterDao();
					//价格
					if(sheet.getCell(13, i).getContents()!=null&&sheet.getCell(13, i).getContents()!=""){
						String PriceInfo = sheet.getCell(13, i).getContents();

						am.setPRICE_ID(amDao.queryPriceIdByPriceName(PriceInfo));
					}

					//是否总表
					if(sheet.getCell(14, i).getContents()!=null&&sheet.getCell(14, i).getContents()!="")
						am.setISCOUNTMETER(Integer.parseInt(sheet.getCell(14, i).getContents()));

					//是否纳入计量
					if(sheet.getCell(15, i).getContents()!=null&&sheet.getCell(15, i).getContents()!="")
						am.setISCOMPUTATION(Integer.parseInt(sheet.getCell(15, i).getContents()));

					//修正量
					if(sheet.getCell(16, i).getContents()!=null&&sheet.getCell(16, i).getContents()!=""){
						am.setXIUZHENG(Float.parseFloat(sheet.getCell(16, i).getContents()));
					}
				} else
				{
					flag = false;
					errorInfo += "该行数据列数少于模板列数;";
				}

				if (wd.hasAddRepeat(am))
				{
					flag = false;
					errorInfo += "同一网关下不允许重复的测量点或通讯地址";
				}

				if (flag)
				{
					wd.addWatermeter(am);
				} else
				{
					List<String> info = new LinkedList<String>();
					for (int j = 0; j < 17; j++)
					{
						info.add(sheet.getCell(j, i).getContents());
					}
					info.add(errorInfo);
					failList.add(info);
				}
			}
			book.close();
		} catch (BiffException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		amFile.delete();

		return failList;
			}
	private void initSearchBox(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterDao wd = new WatermeterDao();
		List<Gather> gatherList = wd.getAllGatherInWaterMeter();
		List<Architecture> archList = wd.getAllArchInWaterMeter();
		List<Area> areaList = wd.getAllAreaInWaterMeter();
		//		List<String> wamAddrList = wd.getAllAddressInWaterMeter();
		//		List<String> wamNameList = wd.getAllNameInWaterMeter();
		//		List<String> consumNumList = wd.getAllConNumInWaterMeter();

		// List<Area> allAreaList = areaDao.display();
		// List<Department> allGroupList = groupDao.display();
		// List<Architecture> allArchList = archDao.displayAll();
		// List<Gather> allGather = gatherDao.display();

		if (gatherList != null || archList != null || areaList != null
				/*|| wamAddrList != null || wamNameList != null
				|| consumNumList != null*/)
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

			if (gatherList != null && gatherList.size() > 0)
			{
				JSONArray list = new JSONArray();

				for (Gather a : gatherList)
				{
					JSONObject jo = new JSONObject();
					jo.put("Arch_ID", a.getArchitectureID());
					jo.put("Area_ID", a.getAreaID());
					jo.put("Gather_ID", a.getGatherID());
					jo.put("Gather_Name", a.getGatherName());

					list.add(jo);
				}

				main.put("GatherList", list);
			}

			//			if (wamAddrList != null && areaList.size() > 0)
			//			{
			//				JSONArray list = new JSONArray();
			//
			//				for (String a : wamAddrList)
			//				{
			//					JSONObject jo = new JSONObject();
			//
			//					jo.put("Watermeter_Addr", a);
			//
			//					list.add(jo);
			//				}
			//
			//				main.put("WamAddrList", list);
			//			}
			//
			//			if (wamNameList != null && wamNameList.size() > 0)
			//			{
			//				JSONArray list = new JSONArray();
			//
			//				for (String a : wamNameList)
			//				{
			//					JSONObject jo = new JSONObject();
			//
			//					jo.put("Wammeter_Name", a);
			//
			//					list.add(jo);
			//				}
			//
			//				main.put("wamNameList", list);
			//			}
			//
			//			if (consumNumList != null && consumNumList.size() > 0)
			//			{
			//				JSONArray list = new JSONArray();
			//
			//				for (String a : consumNumList)
			//				{
			//					JSONObject jo = new JSONObject();
			//
			//					jo.put("Consum_Num", a);
			//
			//					list.add(jo);
			//				}
			//
			//				main.put("ConsumNumList", list);
			//			}

			PrintWriter out = response.getWriter();
			out.println(main.toString());
			out.flush();
			out.close();
		}
	}

	/**
	 * 字符串补齐，不足位用"0"补齐
	 * 
	 * @param s
	 *            需要补齐的字符串
	 * @param length
	 *            需要补齐成的长度
	 * @return 补齐后的字符串
	 */
	private static String padLeft(String s, int length) {
		if (s.length() >= length) {
			return s.substring(0, length);
		}
		byte[] bs = new byte[length];
		byte[] ss = s.getBytes();
		Arrays.fill(bs, (byte) (48 & 0xff));
		System.arraycopy(ss, 0, bs, length - ss.length, ss.length);
		return new String(bs);
	}
}
