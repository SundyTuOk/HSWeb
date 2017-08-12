package com.sf.energy.project.system.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.light.dao.SLLineDao;
import com.sf.energy.light.model.SLLineModel;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.equipment.model.WatermeterModel;
import com.sf.energy.project.right.dao.UsersArchitectureDao;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.model.ArcMulSelectModel;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.util.DataValidation;
import com.sf.energy.util.ExportHelper;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

/**
 * @author WangZhao 2014-4-17
 * 
 * 
 */
public class ArchManage extends HttpServlet
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
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException, RowsExceededException, WriteException, ParseException
	{
		String method = request.getParameter("method");

		if ("getAllArch".equalsIgnoreCase(method))
			getAllArch(request, response);

		if ("get3DArchInfo".equalsIgnoreCase(method))
			get3DArchInfo(request, response);

		if ("getAllArchForPrePay".equalsIgnoreCase(method))
			getAllArchForPrePay(request, response);

		if ("getArchTree".equalsIgnoreCase(method))
			getArchTree(request, response);
		if ("getArchWaTreeByFloor".equalsIgnoreCase(method))
			getArchWaTreeByFloor(request, response);
		if ("getArchAmmTreeByFloor".equalsIgnoreCase(method))
			getArchAmmTreeByFloor(request, response);

		if ("getArchTreeForPrePay".equalsIgnoreCase(method))
			getArchTreeForPrePay(request, response);

		if ("getLamphuiluTree".equalsIgnoreCase(method))
			getLamphuiluTree(request, response);

		if ("getSelectTreeByArchStyle".equalsIgnoreCase(method))
			getSelectTreeByArchStyle(request, response);

		if ("getArchAmmTree".equalsIgnoreCase(method))
			getArchAmmTree(request, response);

		if ("getAmmTreeForPrePay".equalsIgnoreCase(method))
			getAmmTreeForPrePay(request, response);

		if ("getArchImportantAmmTree".equalsIgnoreCase(method))
			getArchImportantAmmTree(request, response);
		if ("getArchCountMeterAmmTree".equalsIgnoreCase(method))
			getArchCountMeterAmmTree(request, response);

		if ("getArchWaterMeterTree".equalsIgnoreCase(method))
			getArchWaterMeterTree(request, response);

		if ("getArchByArea".equalsIgnoreCase(method))
			getArchByArea(request, response);

		if ("deleteArch".equalsIgnoreCase(method))
			deleteArch(request, response);

		if ("addArch".equalsIgnoreCase(method))
			addArch(request, response);

		if ("updateArch".equalsIgnoreCase(method))
			updateArch(request, response);

		if ("getAllArchByArea".equalsIgnoreCase(method))
			getAllArchByArea(request, response);
		if ("getArchByAreaWithoutUserId".equalsIgnoreCase(method))
			getArchByAreaWithoutUserId(request, response);

		if ("getAllImportantArchByArea".equalsIgnoreCase(method))
			getAllImportantArchByArea(request, response);

		if ("getAllWaterImportantArchByArea".equalsIgnoreCase(method))
			getAllWaterImportantArchByArea(request, response);

		if ("getAllFloorByArch".equalsIgnoreCase(method))
			getAllFloorByArch(request, response);

		if ("getAllImportantFloorByArch".equalsIgnoreCase(method))
			getAllImportantFloorByArch(request, response);

		if ("getAllWaterImportantFloorByArch".equalsIgnoreCase(method))
			getAllWaterImportantFloorByArch(request, response);

		if ("getMulSlect".equalsIgnoreCase(method))
			getMulSlect(request, response);

		if ("upLoadImg".equalsIgnoreCase(method))
			upLoadImg(request, response);

		if ("getAllArchStyle".equalsIgnoreCase(method))
		{
			getAllArchStyle(request, response);
		}

		// 节能管理，能耗实时监管树里面的数据
		if ("getAllAmFenXiangInMonitor".equalsIgnoreCase(method))
		{
			getAllAmFenXiangInMonitor(request, response);
		}

		// 节能管理，能耗实时监管树里面的数据
		if ("getAllWaterFenXiangInMonitor".equalsIgnoreCase(method))
		{
			getAllWaterFenXiangInMonitor(request, response);
		}

		if ("getAllArchByArchStyle".equalsIgnoreCase(method))
		{
			getAllArchByArchStyle(request, response);
		}

		if ("importArch".equalsIgnoreCase(method)){
			importArch(request, response);
		}
		if ("getAreaIDByArchID".equalsIgnoreCase(method)){
			getAreaIDByArchID(request, response);
		}

	}

	private void importArch(HttpServletRequest request, HttpServletResponse response) throws RowsExceededException, WriteException, IOException, SQLException, ParseException
	{
		//		 String uri = request.getRequestURI();//返回请求行中的资源名称
		//		   String url = request.getRequestURL().toString();//获得客户端发送请求的完整url
		//		   String ip = request.getRemoteAddr();//返回发出请求的IP地址
		//		   String params = request.getQueryString();//返回请求行中的参数部分
		//		   String host=request.getRemoteHost();//返回发出请求的客户机的主机名
		//		   int port =request.getRemotePort();//返回发出请求的客户机的端口号。
		//		   System.out.println(ip);
		//		   System.out.println(url);
		//		   System.out.println(uri);
		//		   System.out.println(params);
		//		   System.out.println(host);
		//		   System.out.println(port);
		ExportHelper dh = ExportHelper.getInstance();
		Map<String, File> imFailMap = new HashMap<String, File>();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "建筑管理", "建筑导入");

		String saveFolder = "img/archImg";
		List<List<String>> result = null;
		File file = dh
				.getImportFile(request, response, saveFolder, "theArchFile");

		JSONArray main = new JSONArray();

		JSONObject jo = new JSONObject();

		if (file != null)
		{
			if (file.getAbsolutePath().endsWith(".xls"))
			{
				result = importArchToDB(file, request);
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
		//	System.out.println(main.toString());
		PrintWriter out = response.getWriter();
		out.println(main.toString());
		out.flush();
		out.close();
	}

	private List<List<String>> importArchToDB(File file, HttpServletRequest request) throws SQLException, ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		AreaDao areaDao = new AreaDao();
		ArchitectureDao archDao = new ArchitectureDao();
		DictionaryValueDao dvd = new DictionaryValueDao();
		List<List<String>> failList = new LinkedList<List<String>>();
		String[] theTitles =
			{ "所属区域", "标示编号", "建设时间", "建筑面积","常驻人数", "建筑类别", "建筑名称", "是否显示单元楼",
				"总楼层", "起始楼层", "空调面积", "联系电话", "负责人","功能说明","是否预付费建筑"};
		List<String> firstLine = new LinkedList<String>();

		// 将标题加进去
		for (String title : theTitles)
		{
			firstLine.add(title);
		}

		failList.add(firstLine);

		Workbook book = null;
		try
		{
			book = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			boolean flag = true;
			String errorInfo = "";
			// 得到单元格
			for (int i = 1; i < sheet.getRows(); i++)
			{
				Architecture am = new Architecture();
				errorInfo = "";
				flag = true;
				//				for (int j = 0; j < 15; j++)
				//				{
				//					System.out.println(sheet.getCell(j, i).getContents()+sheet.getCell(j, i).getType());
				//				}
				if (sheet.getColumns() >= 15)
				{				
					// 所属区域
					am.setAreaID(areaDao.queryIDByName(sheet.getCell(0, i).getContents()));

					// 标示编号
					am.setNum(sheet.getCell(1, i).getContents());

					// 建设时间
					if(sheet.getCell(2, i).getType()==CellType.DATE){
						DateCell dc = (DateCell)sheet.getCell(2, i);
						am.setTime(df.format(dc.getDate()));
					}

					// 建筑面积
					am.setArea(Float.parseFloat(sheet.getCell(3, i).getContents()));

					// 常驻人数
					am.setCountMan(Integer.parseInt(sheet.getCell(4, i).getContents()));

					// 建筑类别
					am.setStyle(dvd.queryDicValueNumByDicIDandValue(103, sheet.getCell(5, i).getContents()));

					// 建筑名称
					am.setName((sheet.getCell(6, i).getContents()));

					// 是否显示单元楼
					am.setIsUnit(0);
					if(sheet.getCell(7, i).getContents().equals("楼层")){
						am.setIsUnit(0);
					}
					if(sheet.getCell(7, i).getContents().equals("单元楼")){
						am.setIsUnit(1);
					}

					// 总楼层
					am.setStorey(1);
					am.setStorey(Integer.parseInt(sheet.getCell(8, i).getContents()));

					// 起始楼层
					am.setFloorstart(1);
					am.setFloorstart(Integer.parseInt(sheet.getCell(9, i).getContents()));

					// 空调面积
					am.setAircondition(0);
					am.setAircondition(Float.parseFloat(sheet.getCell(10, i).getContents()));

					// 联系电话
					am.setPhone(sheet.getCell(11, i).getContents());

					// 负责人
					am.setMan(sheet.getCell(12, i).getContents());

					// 功能说明
					am.setFunction(sheet.getCell(13, i).getContents());

					// 是否预付费建筑
					am.setPayment(1);
					if(sheet.getCell(14, i).getContents().equals("是")){
						am.setPayment(1);
					}
					if(sheet.getCell(14, i).getContents().equals("否")){
						am.setPayment(0);
					}

				} else
				{
					flag = false;
					errorInfo += "该行数据列数少于模板列数;";
				}

				if (archDao.hasAddRepeat(am))
				{
					flag = false;
					errorInfo += "不允许添加重复建筑编号或建筑名称";
				}

				if (flag)
				{
					archDao.add(am);
					//添加建筑权限
					Map<String, String> imgMap = new HashMap<String, String>();
					HttpSession session = request.getSession();
					String sessionID = session.getId();
					if (imgMap.get(sessionID) != null)
					{
						imgMap.remove(sessionID);
					}

					// 新增建筑权限赋给超级管理员
					int userID = 0;
					if (session.getAttribute("userId") != null)
					{
						userID = Integer.parseInt(session.getAttribute("userId")
								.toString());
					}
					UsersArchitectureDao uad = new UsersArchitectureDao();
					uad.rightAfterInsert(am.getName(), userID);
				} else
				{
					List<String> info = new LinkedList<String>();
					for (int j = 0; j < 15; j++)
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

			e.printStackTrace();
		}

		file.delete();

		return failList;
	}



	private void upLoadImg(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{

		ExportHelper eh = ExportHelper.getInstance();
		Map<String, String> imgMap = new HashMap<String, String>();
		String saveFolder = "img/archImg";
		File imgFile = eh
				.getImportFile(request, response, saveFolder, "theImg");

		JSONArray main = new JSONArray();

		JSONObject jo = new JSONObject();

		if (imgFile != null)
		{
			String path = imgFile.getAbsolutePath();
			jo.put("type", "success");
			jo.put("path",
					path.substring(path.lastIndexOf("img"), path.length()));

			HttpSession session = request.getSession();
			String sessionID = session.getId();
			if (imgMap.get(sessionID) != null)
			{
				File img = new File(imgMap.get(sessionID));
				img.delete();
			}
			imgMap.put(sessionID, path);
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

	private void getAllFloorByArch(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		int archID = 0;
		if (request.getParameter("archID") != null&&!request.getParameter("archID").equals(""))
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}
		Architecture archModel = ad.queryByID(archID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("Arch_ID", archModel.getId());
		jo.put("Arch_Name", archModel.getName());
		jo.put("Arch_Floor", archModel.getStorey());
		json.add(jo);

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	// 重点用户楼层
	private void getAllImportantFloorByArch(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		HttpSession  session = request.getSession();
		int userID = 0;
		if(session.getAttribute("userId") != null)
		{
			userID = Integer.parseInt(session.getAttribute("userId").toString());
		}

		int archID = 0;
		if (request.getParameter("archID") != null)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}
		List<Architecture> list = ad.queryImportantFloorByarcID(archID,userID);

		Gson gson = new Gson();
		String result = gson.toJson(list);

		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}

	// 有水表的楼层
	private void getAllWaterImportantFloorByArch(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		int archID = 0;
		if (request.getParameter("archID") != null)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}
		List<Architecture> list = ad.queryWaterImportantFloorByarcID(archID);

		Gson gson = new Gson();
		String result = gson.toJson(list);

		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}

	private void getAllArchByArea(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}
		int areaID = 0;
		if (request.getParameter("areaID") != null)
		{
			areaID = Integer.parseInt(request.getParameter("areaID"));
		}
		List<Architecture> list = ad.queryArchByAreaID(areaID, userID);

		JSONArray json = new JSONArray();
		for (Architecture am : list)
		{
			JSONObject jo = new JSONObject();

			jo.put("Arch_ID", am.getId());
			jo.put("Arch_Name", am.getName());
			jo.put("Architecture_AdvancePayment", am.getPayment());
			json.add(jo);
		}

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getAllImportantArchByArea(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}

		int areaID = 0;
		if (request.getParameter("areaID") != null)
		{
			areaID = Integer.parseInt(request.getParameter("areaID"));
		}

		List<Architecture> list = ad.queryImportantArchByAreaID(areaID,userID);

		JSONArray json = new JSONArray();
		for (Architecture am : list)
		{
			JSONObject jo = new JSONObject();

			jo.put("Arch_ID", am.getId());
			jo.put("Arch_Name", am.getName());
			json.add(jo);
		}

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getAllWaterImportantArchByArea(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		int areaID = 0;
		if (request.getParameter("areaID") != null)
		{
			areaID = Integer.parseInt(request.getParameter("areaID"));
		}
		List<Architecture> list = ad.queryWaterImportantArchByAreaID(areaID);

		JSONArray json = new JSONArray();
		for (Architecture am : list)
		{
			JSONObject jo = new JSONObject();

			jo.put("Arch_ID", am.getId());
			jo.put("Arch_Name", am.getName());
			json.add(jo);
		}

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void updateArch(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		ArchitectureDao ad = new ArchitectureDao();
		Map<String, String> imgMap = new HashMap<String, String>();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");



		Architecture am = new Architecture();

		if (request.getParameter("Arch_ID") != null)
			am.setId(Integer.parseInt(request.getParameter("Arch_ID")));

		if (request.getParameter("Arch_AreaID") != null)
			am.setAreaID(Integer.parseInt(request.getParameter("Arch_AreaID")));

		if (request.getParameter("Arch_Style") != null)
			am.setStyle(request.getParameter("Arch_Style"));

		if (request.getParameter("Arch_Num") != null)
			am.setNum(request.getParameter("Arch_Num"));

		if (request.getParameter("Arch_Time") != null)
			am.setTime(request.getParameter("Arch_Time"));

		if (request.getParameter("Arch_Space") != null)
			am.setArea(Float.parseFloat(request.getParameter("Arch_Space")));

		if (request.getParameter("Arch_CountMan") != null)
			am.setCountMan(Integer.parseInt(request
					.getParameter("Arch_CountMan")));

		if (request.getParameter("Arch_Man") != null)
			am.setMan(request.getParameter("Arch_Man"));

		if (request.getParameter("Arch_Name") != null)
			am.setName(request.getParameter("Arch_Name"));

		if (request.getParameter("Arch_Story") != null)
			am.setStorey(Integer.parseInt(request.getParameter("Arch_Story")));

		if (request.getParameter("Arch_FloorStart") != null)
			am.setFloorstart(Integer.parseInt(request
					.getParameter("Arch_FloorStart")));

		if (request.getParameter("Arch_Aircondition") != null)
			am.setAircondition(Float.parseFloat(request
					.getParameter("Arch_Aircondition")));

		if (request.getParameter("Arch_Phone") != null)
			am.setPhone(request.getParameter("Arch_Phone"));

		if (request.getParameter("Arch_Remark") != null)
			am.setFunction(request.getParameter("Arch_Remark"));

		if (request.getParameter("Arch_ImgPath") != null)
			am.setImgUrl(request.getParameter("Arch_ImgPath"));

		if (request.getParameter("Arch_IsPayment") != null
				&& DataValidation.isNumber(request
						.getParameter("Arch_IsPayment")))
			am.setPayment(Integer.parseInt(request
					.getParameter("Arch_IsPayment")));
		if (request.getParameter("isUnit") != null
				&& DataValidation.isNumber(request
						.getParameter("isUnit")))
			am.setIsUnit(Integer.parseInt(request
					.getParameter("isUnit")));

		String resultInfo = null;

		if (ad.hasUpdateRepeat(am))
		{
			resultInfo = "不允许添加重复建筑编号或建筑名称";
		} else if (ad.update(am))
		{
			resultInfo = "success";
			// 获得sessionID
			HttpSession session = request.getSession();
			String sessionID = session.getId();
			if (imgMap.get(sessionID) != null)
			{
				imgMap.remove(sessionID);
			}

			// 编辑相应的权限
			UsersArchitectureDao uad = new UsersArchitectureDao();
			uad.rightAfterModify(
					Integer.parseInt(request.getParameter("Arch_ID")),
					Integer.parseInt(request.getParameter("Arch_AreaID")));
			// 写入日志
			log.writeLog(userLoginName, "建筑管理", "建筑编辑  "+am.getName());
		} else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addArch(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		Map<String, String> imgMap = new HashMap<String, String>();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");




		Architecture am = new Architecture();

		if (request.getParameter("Arch_AreaID") != null)
			am.setAreaID(Integer.parseInt(request.getParameter("Arch_AreaID")));

		if (request.getParameter("Arch_Style") != null)
			am.setStyle(request.getParameter("Arch_Style"));

		if (request.getParameter("Arch_Num") != null)
			am.setNum(request.getParameter("Arch_Num"));

		if (request.getParameter("Arch_Time") != null)
			am.setTime(request.getParameter("Arch_Time"));

		if (request.getParameter("Arch_Space") != null)
			am.setArea(Float.parseFloat(request.getParameter("Arch_Space")));

		if (request.getParameter("Arch_CountMan") != null)
			am.setCountMan(Integer.parseInt(request
					.getParameter("Arch_CountMan")));

		if (request.getParameter("Arch_Man") != null)
			am.setMan(request.getParameter("Arch_Man"));

		if (request.getParameter("Arch_Name") != null)
			am.setName(request.getParameter("Arch_Name"));

		if (request.getParameter("Arch_Story") != null)
			am.setStorey(Integer.parseInt(request.getParameter("Arch_Story")));

		if (request.getParameter("Arch_FloorStart") != null)
			am.setFloorstart(Integer.parseInt(request
					.getParameter("Arch_FloorStart")));

		if (request.getParameter("Arch_Aircondition") != null)
			am.setAircondition(Float.parseFloat(request
					.getParameter("Arch_Aircondition")));

		if (request.getParameter("Arch_Phone") != null)
			am.setPhone(request.getParameter("Arch_Phone"));

		if (request.getParameter("Arch_Remark") != null)
			am.setFunction(request.getParameter("Arch_Remark"));

		if (request.getParameter("Arch_ImgPath") != null)
			am.setImgUrl(request.getParameter("Arch_ImgPath"));

		if (request.getParameter("Arch_IsPayment") != null
				&& DataValidation.isNumber(request
						.getParameter("Arch_IsPayment")))
			am.setPayment(Integer.parseInt(request
					.getParameter("Arch_IsPayment")));
		if (request.getParameter("isUnit") != null
				&& DataValidation.isNumber(request
						.getParameter("isUnit")))
			am.setIsUnit(Integer.parseInt(request
					.getParameter("isUnit")));

		String resultInfo = null;

		if (ad.hasAddRepeat(am))
		{
			resultInfo = "不允许添加重复建筑编号或建筑名称";
		} else if (ad.add(am))
		{
			resultInfo = "success";
			// 获得sessionID

			HttpSession session = request.getSession();
			String sessionID = session.getId();
			if (imgMap.get(sessionID) != null)
			{
				imgMap.remove(sessionID);
			}

			// 新增建筑权限赋给超级管理员
			int userID = 0;
			if (session.getAttribute("userId") != null)
			{
				userID = Integer.parseInt(session.getAttribute("userId")
						.toString());
			}
			UsersArchitectureDao uad = new UsersArchitectureDao();
			uad.rightAfterInsert(am.getName(), userID);
			// 写入日志
			log.writeLog(userLoginName, "建筑管理", "建筑新增   "+am.getName());
		} else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void deleteArch(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");



		Integer archID = 0;
		if ((request.getParameter("Arch_ID") != null)
				&& DataValidation.isNumber(request.getParameter("Arch_ID")))
			archID = Integer.parseInt(request.getParameter("Arch_ID"));
		else
		{
			return;
		}

		String resultInfo = null;
		String nameString=ad.getArchByID(archID).getName();
		if (ad.hasAmmeter(archID) || ad.hasWaterMeter(archID)
				|| ad.hasGather(archID))
		{
			resultInfo = "该建筑下挂有设备（表计、网关），删除建筑前，请先删除所挂的所有设备";
		} else if (ad.delete(archID))
		{
			resultInfo = "success";
			// 删除相应的建筑权限
			UsersArchitectureDao uad = new UsersArchitectureDao();
			uad.rightAfterDelete(archID);

			// 写入日志
			log.writeLog(userLoginName, "建筑管理", "建筑删除   "+nameString);
		}

		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	//集中监测中带电表的树
	private void getArchAmmTree(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
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
				JSONObject jo = buildNewAreaNode(a, userID);
				if (jo != null)
					tree.put(a.getId(), jo);
			}

			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			//System.out.println("数据"+tree.toString());
			out.flush();
			out.close();
		}

	}


	//预付费电表树
	private void getAmmTreeForPrePay(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
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
				JSONObject jo = buildAreaNodeForPrePay(a, userID);
				if (jo != null)
					tree.put(a.getId(), jo);
			}

			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			//System.out.println("数据"+tree.toString());
			out.flush();
			out.close();
		}

	}

	//负载趋势中带重点用户电表的树
	private void getArchImportantAmmTree(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
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
				JSONObject jo = buildImportantAmmAreaNode(a, userID);
				if (jo != null)
					tree.put(a.getId(), jo);
			}

			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			//System.out.println("数据"+tree.toString());
			out.flush();
			out.close();
		}

	}
	private void getArchCountMeterAmmTree(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
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
				JSONObject jo = buildCountMeterAmmAreaNode(a, userID);
				if (jo != null)
					tree.put(a.getId(), jo);
			}

			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			//System.out.println("数据"+tree.toString());
			out.flush();
			out.close();
		}

	}
	//集中监测中带水表的树
	private void getArchWaTreeByFloor(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{

		WatermeterDao ammDao = new WatermeterDao();
		HttpSession session = request.getSession();
		// int userID = 0;
		int archid = 0;
		int floorid = 0;
		// if (session.getAttribute("userId") != null) {
		// userID = Integer
		// .parseInt(session.getAttribute("userId").toString());
		// }
		if (request.getParameter("archid") != null)
		{
			archid = Integer.parseInt(request.getParameter("archid"));
		}
		if (request.getParameter("floorid") != null)
		{
			floorid = Integer.parseInt(request.getParameter("floorid"));
		}
		JSONObject tree = new JSONObject();
		List<WatermeterModel> ammList = ammDao.queryByArchAndFloor(archid, floorid);
		long count=1;
		for (WatermeterModel amm : ammList)
		{
			JSONObject ammNode = new JSONObject();
			ammNode.put("type", "item");
			if(amm.getWATERMETER_STAT()==1){
				ammNode.put("name", "<img src='img/tree/Dianbiao1.gif'> &nbsp;"+amm.getWATERMETER_NAME());
			}else {
				ammNode.put("name", "<img src='img/tree/Dianbiao.gif'> &nbsp;"+amm.getWATERMETER_NAME());
			}
			//			ammNode.put("name", amm.getMeterName());
			ammNode.put("WatermeterID", amm.getWATERMETER_ID());
			//			tree.put(amm.getWATERMETER_ID(), ammNode);
			tree.put(count, ammNode);
			count++;//黄勇修改电表水表树的排序功能
		}
		PrintWriter out = response.getWriter();
		out.println(tree.toString());
		//		System.out.println("数据" + tree.toString());
		out.flush();
		out.close();

	}


	//集中监测中带水表的树
	private void getArchWaterMeterTree(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
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
				JSONObject jo = buildNewAreaNodeforWater(a, userID);
				if (jo != null)
					tree.put(a.getId(), jo);
			}

			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			//System.out.println("水数据"+tree.toString());
			out.flush();
			out.close();
		}

	}

	private void getArchAmmTreeByFloor(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		AmmeterDao ammDao = new AmmeterDao();
		HttpSession session = request.getSession();
		//			int userID = 0;
		int archid = 0;
		int floorid=0;
		//			if (session.getAttribute("userId") != null) {
		//				userID = Integer
		//						.parseInt(session.getAttribute("userId").toString());
		//			}
		if (request.getParameter("archid") != null) {
			archid = Integer.parseInt(request.getParameter("archid"));
		}
		if (request.getParameter("floorid") != null) {
			floorid = Integer.parseInt(request.getParameter("floorid"));
		}
		JSONObject tree = new JSONObject();
		List<AmmeterModel> ammList = ammDao
				.queryByArchAndFloor(archid, floorid);
		long count=1;
		for (AmmeterModel amm : ammList) {
			JSONObject ammNode = new JSONObject();
			ammNode.put("type", "item");
			if(amm.getAmmeterStat()==1){
				ammNode.put("name", "<img src='img/tree/Dianbiao1.gif'> &nbsp;"+amm.getAmmeterName());
			}else {
				ammNode.put("name", "<img src='img/tree/Dianbiao.gif'> &nbsp;"+amm.getAmmeterName());
			}
			//				ammNode.put("name", amm.getAmmeterName());
			ammNode.put("AmmeterID", amm.getAmmeterID());
			tree.put(count, ammNode);
			//				tree.put(amm.getAmmeterID(), ammNode);
			count++;//黄勇修改电表水表树的排序功能
		}
		PrintWriter out = response.getWriter();
		out.println(tree.toString());
		//			System.out.println("数据"+tree.toString());
		out.flush();
		out.close();
	}


	private void getArchTree(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
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

			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			//System.out.println("数据"+tree.toString());
			out.flush();
			out.close();
		}

	}

	//预付费建筑树
	private void getArchTreeForPrePay(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
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
				JSONObject jo = buildAreaNodeForPrePayArch(a, userID);
				if (jo != null)
					tree.put(a.getId(), jo);
			}

			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			//System.out.println("数据"+tree.toString());
			out.flush();
			out.close();
		}

	}


	//路灯回路树
	private void getLamphuiluTree(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
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
				JSONObject jo = buildhuiluNode(a, userID);
				if (jo != null)
					tree.put(a.getId(), jo);
			}

			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			//System.out.println("数据"+tree.toString());
			out.flush();
			out.close();
		}

	}


	//节能管理按建筑分类树
	private void getSelectTreeByArchStyle(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}
		ArrayList<DictionaryValueModel> list = new ArrayList<DictionaryValueModel>();
		DictionaryValueDao dvd = new DictionaryValueDao();

		list = dvd.listDictionaryValueByName("建筑分类");

		//System.out.println("长度"+list.size());
		if (list != null && list.size() > 0)
		{
			JSONObject tree = new JSONObject();

			for (DictionaryValueModel a : list)
			{
				//					System.out.println("55"+a.getDictionaryValue());
				JSONObject jo = buildArchStyleNode(a, userID);
				//if (jo != null)
				tree.put(a.getDictionaryValueNum(), jo);
			}

			PrintWriter out = response.getWriter();
			out.println(tree.toString());
			//System.out.println("数据"+tree.toString());
			out.flush();
			out.close();
		}

	}

	//分类建筑节点
	private JSONObject buildArchStyleNode(DictionaryValueModel DVmodel, int userID)
			throws SQLException
	{
		ArchitectureDao archDao = new ArchitectureDao();

		//System.out.println("66"+DVmodel.getDictionaryValue());
		JSONObject node = null;
		if (DVmodel != null)
		{
			//System.out.println("77"+DVmodel.getDictionaryValueNum());
			List<Architecture> archList = archDao.queryArchByStyle(DVmodel.getDictionaryValueNum().charAt(0));
			node = new JSONObject();
			node.put("name", DVmodel.getDictionaryValue());
			node.put("ArchStyleID", DVmodel.getDictionaryValueNum());

			if (archList != null && archList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject archListNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for (Architecture arch : archList)
				{
					JSONObject archNode = new JSONObject();
					archNode.put("type", "item");
					archNode.put("name", arch.getName());
					archNode.put("ArchID", arch.getId());
					archListNode.put(arch.getId(), archNode);
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
			node.put("AreaID", theArea.getId());

			if (archList != null && archList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject archListNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for (Architecture arch : archList)
				{
					JSONObject archNode = new JSONObject();
					archNode.put("type", "item");
					archNode.put("name", arch.getName());
					archNode.put("AreaID", theArea.getId());
					archNode.put("ArchID", arch.getId());
					archListNode.put(arch.getId(), archNode);
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


	//预付费建筑树节点
	private JSONObject buildAreaNodeForPrePayArch(Area theArea, int userID)
			throws SQLException
	{

		JSONObject node = null;
		if (theArea != null)
		{
			ArchitectureDao archDao = new ArchitectureDao();
			List<Architecture> archList = archDao.queryAdvancePreArchByAreaID(
					theArea.getId(), userID);
			node = new JSONObject();
			node.put("name", theArea.getName());
			node.put("AreaID", theArea.getId());

			if (archList != null && archList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject archListNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for (Architecture arch : archList)
				{
					JSONObject archNode = new JSONObject();
					archNode.put("type", "item");
					archNode.put("name", arch.getName());
					archNode.put("AreaID", theArea.getId());
					archNode.put("ArchID", arch.getId());
					archListNode.put(arch.getId(), archNode);
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

	//回路节点
	private JSONObject buildhuiluNode(Area theArea, int userID)
			throws SQLException
	{
		SLLineDao info=new SLLineDao();
		JSONObject node = null;
		if (theArea != null)
		{
			List<SLLineModel> huilulist=info.queryByAreaID(theArea.getId());

			node = new JSONObject();
			node.put("name", theArea.getName());
			node.put("AreaID", theArea.getId());

			if (huilulist != null && huilulist.size() > 0)
			{
				node.put("type", "folder");
				JSONObject huilulistNode = new JSONObject();

				JSONObject jo = new JSONObject();

				for (SLLineModel sllm : huilulist)
				{
					JSONObject huiluNode = new JSONObject();
					huiluNode.put("type", "item");
					huiluNode.put("name", sllm.getSLLINE_NAME());
					huiluNode.put("SlLineID", sllm.getSLLINE_ID());
					huilulistNode.put(sllm.getSLLINE_ID(), huiluNode);
				}

				jo.put("children", huilulistNode);
				node.put("additionalParameters", jo);
			} else
			{
				node.put("type", "folder");
				//				return null;
			}

		}

		return node;
	}

	private JSONObject buildNewAreaNode(Area theArea, int userID)
			throws SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		ArchitectureDao archDao = new ArchitectureDao();
		AmmeterDao ammDao = new AmmeterDao();
		JSONObject node = null;
		if (theArea != null)
		{
			List<Architecture> archList = archDao.queryArchByAreaID(
					theArea.getId(), userID);
			node = new JSONObject();
			node.put("name", "<img src='img/tree/Area.GIF'> &nbsp;"+theArea.getName());
			node.put("AreaID", theArea.getId());

			if (archList != null && archList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject archListNode = new JSONObject();

				JSONObject jo = new JSONObject();		//建筑

				for (Architecture arch : archList)
				{
					double onlineradio=ad.OnlineMeterRadioByArchid(arch.getId());

					Architecture archModel = ad.queryByID(arch.getId());
					//List<Architecture>	floorList = archDao.queryFloorByArchID(arch.getId(), userID);
					JSONObject archNode = new JSONObject();
					JSONObject floorListNode = new JSONObject();
					JSONObject jo1 = new JSONObject();		//楼层
					for(int i=archModel.getFloorstart();i<=archModel.getStorey();i++)
					{
						if(i!=0){	
							//						List<AmmeterModel>  ammList = ammDao.queryByArchAndFloor(arch.getId(),i);
							JSONObject floorNode = new JSONObject();
							//						JSONObject ammListNode = new JSONObject();
							//						JSONObject jo2 = new JSONObject();		//电表
							//						for(AmmeterModel amm:ammList)
							//						{
							//							JSONObject ammNode = new JSONObject();
							//							ammNode.put("type", "item");
							//							ammNode.put("name", amm.getAmmeterName());
							//							ammNode.put("AmmeterID", amm.getAmmeterID());
							//							ammListNode.put(amm.getAmmeterID(), ammNode);
							//						}
							//						jo2.put("children", ammListNode);
							double flooronlineradio=ad.OnlineMeterRadioByArchAndFloor(arch.getId(), i);
							floorNode.put("type", "folder");
							if(archModel.getIsUnit()==1){
								if(flooronlineradio==0){
									floorNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+i+"门");
								}else if(flooronlineradio==1){
									floorNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+i+"门");
								}else{
									floorNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+i+"门");
								}
							}
							else{
								if(flooronlineradio==0){
									floorNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+i+"楼");
								}else if(flooronlineradio==1){
									floorNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+i+"楼");
								}else{
									floorNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+i+"楼");
								}
							}
							//						floorNode.put("name", i+"楼");
							floorNode.put("FloorID", i);
							//						floorNode.put("additionalParameters", jo2);
							floorListNode.put(i, floorNode);
						}
					}
					jo1.put("children", floorListNode);
					archNode.put("type", "folder");
					if(onlineradio==0){
						archNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+arch.getName());
					}else if(onlineradio==1){
						archNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+arch.getName());
					}else{
						archNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+arch.getName());
					}

					archNode.put("ArchID", arch.getId());
					archNode.put("additionalParameters", jo1);
					archListNode.put(arch.getId(), archNode);
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

	private JSONObject buildAreaNodeForPrePay(Area theArea, int userID)
			throws SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		ArchitectureDao archDao = new ArchitectureDao();
		AmmeterDao ammDao = new AmmeterDao();
		JSONObject node = null;
		if (theArea != null)
		{
			List<Architecture> archList = archDao.queryAdvancePreArchByAreaID(
					theArea.getId(), userID);
			node = new JSONObject();
			node.put("name", "<img src='img/tree/Area.GIF'> &nbsp;"+theArea.getName());
			node.put("AreaID", theArea.getId());

			if (archList != null && archList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject archListNode = new JSONObject();

				JSONObject jo = new JSONObject();		//建筑

				for (Architecture arch : archList)
				{
					double onlineradio=ad.OnlineMeterRadioByArchid(arch.getId());
					Architecture archModel = ad.queryByID(arch.getId());
					//List<Architecture>	floorList = archDao.queryFloorByArchID(arch.getId(), userID);
					JSONObject archNode = new JSONObject();
					JSONObject floorListNode = new JSONObject();
					JSONObject jo1 = new JSONObject();		//楼层
					for(int i=1;i<=archModel.getStorey();i++)
					{
						//						List<AmmeterModel>  ammList = ammDao.queryByArchAndFloor(arch.getId(),i);
						JSONObject floorNode = new JSONObject();
						//						JSONObject ammListNode = new JSONObject();
						//						JSONObject jo2 = new JSONObject();		//电表
						//						for(AmmeterModel amm:ammList)
						//						{
						//							JSONObject ammNode = new JSONObject();
						//							ammNode.put("type", "item");
						//							ammNode.put("name", amm.getAmmeterName());
						//							ammNode.put("AmmeterID", amm.getAmmeterID());
						//							ammListNode.put(amm.getAmmeterID(), ammNode);
						//						}
						//						jo2.put("children", ammListNode);
						double flooronlineradio=ad.OnlineMeterRadioByArchAndFloor(arch.getId(), i);
						floorNode.put("type", "folder");
						if(archModel.getIsUnit()==1){
							if(flooronlineradio==0){
								floorNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+i+"门");
							}else if(flooronlineradio==1){
								floorNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+i+"门");
							}else{
								floorNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+i+"门");
							}
						}
						else{
							if(flooronlineradio==0){
								floorNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+i+"楼");
							}else if(flooronlineradio==1){
								floorNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+i+"楼");
							}else{
								floorNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+i+"楼");
							}
						}
						//						floorNode.put("type", "folder");
						//						floorNode.put("name", i+"楼");
						floorNode.put("FloorID", i);
						//						floorNode.put("additionalParameters", jo2);
						floorListNode.put(i, floorNode);
					}
					jo1.put("children", floorListNode);
					archNode.put("type", "folder");
					if(onlineradio==0){
						archNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+arch.getName());
					}else if(onlineradio==1){
						archNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+arch.getName());
					}else{
						archNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+arch.getName());
					}
					archNode.put("ArchID", arch.getId());
					archNode.put("additionalParameters", jo1);
					archListNode.put(arch.getId(), archNode);
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

	private JSONObject buildCountMeterAmmAreaNode(Area theArea, int userID)
			throws SQLException
	{
		ArchitectureDao archDao = new ArchitectureDao();
		AmmeterDao ammDao = new AmmeterDao();
		JSONObject node = null;
		if (theArea != null)
		{
			List<Architecture> archList = archDao.queryCountMeterArchByAreaID(
					theArea.getId(), userID);
			node = new JSONObject();
			node.put("name", "<img src='img/tree/Area.GIF'> &nbsp;"+theArea.getName());
			node.put("AreaID", theArea.getId());

			if (archList != null && archList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject archListNode = new JSONObject();

				JSONObject jo = new JSONObject();		//建筑

				for (Architecture arch : archList)
				{
					List<Architecture>	floorList = archDao.queryCountMeterFloorByarcID(arch.getId(), userID);
					JSONObject archNode = new JSONObject();
					JSONObject floorListNode = new JSONObject();
					JSONObject jo1 = new JSONObject();		//楼层
					for(Architecture arc : floorList)
					{
						List<AmmeterModel>  ammList = ammDao.queryCountAmmeterByArchAndFloor(arch.getId(), arc.getStorey());
						JSONObject floorNode = new JSONObject();
						JSONObject ammListNode = new JSONObject();
						JSONObject jo2 = new JSONObject();		//电表
						long count=1;
						for(AmmeterModel amm:ammList)
						{
							JSONObject ammNode = new JSONObject();
							ammNode.put("type", "item");
							if(amm.getAmmeterStat()==1){
								ammNode.put("name", "<img src='img/tree/Dianbiao1.gif'> &nbsp;"+amm.getAmmeterName());
							}else {
								ammNode.put("name", "<img src='img/tree/Dianbiao.gif'> &nbsp;"+amm.getAmmeterName());
							}
							//							ammNode.put("name", amm.getAmmeterName());
							ammNode.put("AmmeterID", amm.getAmmeterID());
							ammListNode.put(count, ammNode);
							count++;
							//							ammListNode.put(amm.getAmmeterID(), ammNode);
						}
						jo2.put("children", ammListNode);
						floorNode.put("type", "folder");
						if(arc.getIsUnit()==1){
							floorNode.put("name", arc.getStorey()+"门");
						}
						else{
							floorNode.put("name", arc.getStorey()+"楼");
						}


						floorNode.put("FloorID", arc.getStorey());
						floorNode.put("additionalParameters", jo2);
						floorListNode.put(arc.getStorey(), floorNode);
					}
					jo1.put("children", floorListNode);
					archNode.put("type", "folder");
					double onlineradio=archDao.OnlineMeterRadioByArchid(arch.getId());
					if(onlineradio==0){
						archNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+arch.getName());
					}else if(onlineradio==1){
						archNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+arch.getName());
					}else{
						archNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+arch.getName());
					}
					//					archNode.put("name", arch.getName());
					archNode.put("ArchID", arch.getId());
					archNode.put("additionalParameters", jo1);
					archListNode.put(arch.getId(), archNode);
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
	private JSONObject buildImportantAmmAreaNode(Area theArea, int userID)
			throws SQLException
	{
		ArchitectureDao archDao = new ArchitectureDao();
		AmmeterDao ammDao = new AmmeterDao();
		JSONObject node = null;
		if (theArea != null)
		{
			List<Architecture> archList = archDao.queryImportantArchByAreaID(
					theArea.getId(), userID);
			node = new JSONObject();
			node.put("name", "<img src='img/tree/Area.GIF'> &nbsp;"+theArea.getName());
			node.put("AreaID", theArea.getId());

			if (archList != null && archList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject archListNode = new JSONObject();

				JSONObject jo = new JSONObject();		//建筑

				for (Architecture arch : archList)
				{
					List<Architecture>	floorList = archDao.queryImportantFloorByarcID(arch.getId(), userID);
					JSONObject archNode = new JSONObject();
					JSONObject floorListNode = new JSONObject();
					JSONObject jo1 = new JSONObject();		//楼层
					for(Architecture arc : floorList)
					{
						List<AmmeterModel>  ammList = ammDao.queryImpAmmeterByArchAndFloor(arch.getId(), arc.getStorey());
						JSONObject floorNode = new JSONObject();
						JSONObject ammListNode = new JSONObject();
						JSONObject jo2 = new JSONObject();		//电表
						long count=1;
						for(AmmeterModel amm:ammList)
						{
							JSONObject ammNode = new JSONObject();
							ammNode.put("type", "item");
							if(amm.getAmmeterStat()==1){
								ammNode.put("name", "<img src='img/tree/Dianbiao1.gif'> &nbsp;"+amm.getAmmeterName());
							}else {
								ammNode.put("name", "<img src='img/tree/Dianbiao.gif'> &nbsp;"+amm.getAmmeterName());
							}
							//							ammNode.put("name", amm.getAmmeterName());
							ammNode.put("AmmeterID", amm.getAmmeterID());
							ammListNode.put(count, ammNode);
							count++;
							//							ammListNode.put(amm.getAmmeterID(), ammNode);
						}
						jo2.put("children", ammListNode);
						floorNode.put("type", "folder");
						if(arc.getIsUnit()==1){
							floorNode.put("name", arc.getStorey()+"门");
						}
						else{
							floorNode.put("name", arc.getStorey()+"楼");
						}


						floorNode.put("FloorID", arc.getStorey());
						floorNode.put("additionalParameters", jo2);
						floorListNode.put(arc.getStorey(), floorNode);
					}
					jo1.put("children", floorListNode);
					archNode.put("type", "folder");
					double onlineradio=archDao.OnlineMeterRadioByArchid(arch.getId());
					if(onlineradio==0){
						archNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+arch.getName());
					}else if(onlineradio==1){
						archNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+arch.getName());
					}else{
						archNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+arch.getName());
					}
					//					archNode.put("name", arch.getName());
					archNode.put("ArchID", arch.getId());
					archNode.put("additionalParameters", jo1);
					archListNode.put(arch.getId(), archNode);
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

	private JSONObject buildNewAreaNodeforWater(Area theArea, int userID)
			throws SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		ArchitectureDao archDao = new ArchitectureDao();
		WatermeterDao watermeterDao = new WatermeterDao();
		JSONObject node = null;
		if (theArea != null)
		{
			List<Architecture> archList = archDao.queryArchByAreaID(
					theArea.getId(), userID);
			node = new JSONObject();
			node.put("name", "<img src='img/tree/Area.GIF'> &nbsp;"+theArea.getName());
			node.put("AreaID", theArea.getId());

			if (archList != null && archList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject archListNode = new JSONObject();

				JSONObject jo = new JSONObject();		//建筑

				for (Architecture arch : archList)
				{
					Architecture archModel = ad.queryByID(arch.getId());
					//					List<Architecture>	floorList = archDao.queryFloorByArchID(arch.getId(), userID);
					JSONObject archNode = new JSONObject();
					JSONObject floorListNode = new JSONObject();
					JSONObject jo1 = new JSONObject();		//楼层
					for(int i=archModel.getFloorstart();i<=archModel.getStorey();i++)
					{
						if(i!=0){
							//						List<WatermeterModel>  watemeterList = watermeterDao.queryByArchAndFloor(arch.getId(), i);
							JSONObject floorNode = new JSONObject();
							//						JSONObject watermeterListNode = new JSONObject();
							//						JSONObject jo2 = new JSONObject();		//电表
							//						for(WatermeterModel watermeter:watemeterList)
							//						{
							//							JSONObject watermeterNode = new JSONObject();
							//							watermeterNode.put("type", "item");
							//							watermeterNode.put("name", watermeter.getWATERMETER_NAME());
							//							watermeterNode.put("WatermeterID", watermeter.getWATERMETER_ID());
							//							watermeterListNode.put(watermeter.getWATERMETER_ID(), watermeterNode);
							//						}
							//						jo2.put("children", watermeterListNode);
							double flooronlineradio=ad.OnlineWaMeterRadioByArchAndFloor(arch.getId(), i);
							floorNode.put("type", "folder");
							if(archModel.getIsUnit()==1){
								if(flooronlineradio==0){
									floorNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+i+"门");
								}else if(flooronlineradio==1){
									floorNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+i+"门");
								}else{
									floorNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+i+"门");
								}
							}
							else{
								if(flooronlineradio==0){
									floorNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+i+"楼");
								}else if(flooronlineradio==1){
									floorNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+i+"楼");
								}else{
									floorNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+i+"楼");
								}
							}
							//						floorNode.put("type", "folder");
							//						floorNode.put("name", i+"楼");
							floorNode.put("FloorID", i);
							//						floorNode.put("additionalParameters", jo2);
							floorListNode.put(i, floorNode);
						}
						jo1.put("children", floorListNode);
						archNode.put("type", "folder");
						double onlineradio=ad.OnlineWaMeterRadioByArchid(arch.getId());
						if(onlineradio==0){
							archNode.put("name",  "<img src='img/tree/Arc2.gif'> &nbsp;"+arch.getName());
						}else if(onlineradio==1){
							archNode.put("name",  "<img src='img/tree/Arc.GIF'> &nbsp;"+arch.getName());
						}else{
							archNode.put("name",  "<img src='img/tree/Arc1.gif'> &nbsp;"+arch.getName());
						}
						//					archNode.put("name", arch.getName());
						archNode.put("ArchID", arch.getId());
						archNode.put("additionalParameters", jo1);
						archListNode.put(arch.getId(), archNode);
					}
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


	//	private JSONObject buildArchNode(Architecture theArch, int userID)
	//			throws SQLException
	//	{
	//
	//		JSONObject node = null;
	//		if (theArch != null)
	//		{
	//			List<Architecture> floorList = archDao.queryFloorByArchID(
	//					theArch.getId(), userID);
	//			node = new JSONObject();
	//			node.put("name", theArch.getName());
	//			node.put("ArchID", theArch.getId());
	//
	//			if (floorList != null && floorList.size() > 0)
	//			{
	//				node.put("type", "folder");
	//				JSONObject archListNode = new JSONObject();
	//
	//				JSONObject jo = new JSONObject();
	//
	//				for (Architecture arch : floorList)
	//				{
	//					JSONObject archNode = new JSONObject();
	//					archNode.put("type", "item");
	//					archNode.put("name", arch.getStorey()+"楼");
	//					archNode.put("FloorID", arch.getStorey());
	//					archListNode.put(arch.getId(), archNode);
	//				}
	//
	//				jo.put("children", archListNode);
	//				node.put("additionalParameters", jo);
	//			} else
	//			{
	//				return null;
	//			}
	//
	//		}
	//
	//		return node;
	//	}

	private void getAllArch(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}
		List<Architecture> archlist = ad.displayAll(userID);
		JSONArray main = new JSONArray();

		for (int i = 0; i < archlist.size(); i++)
		{
			Architecture am = archlist.get(i);

			JSONObject jo = new JSONObject();

			jo.put("Arch_ID", am.getId());
			jo.put("Arch_Style", am.getStyle());
			jo.put("Arch_Num", am.getNum());
			jo.put("Arch_Name", am.getName());
			jo.put("Arch_Time", am.getTime());
			jo.put("Arch_Storey", am.getStorey());
			jo.put("Arch_StartFloor", am.getFloorstart());
			jo.put("Arch_AreaID", am.getAreaID());
			jo.put("Arch_Space", am.getArea());
			jo.put("Arch_Aircondition", am.getAircondition());
			jo.put("Arch_CountMan", am.getCountMan());
			jo.put("Arch_Man", am.getMan());
			jo.put("Arch_Phone", am.getPhone());
			jo.put("Arch_Payment", am.getPayment());
			jo.put("Arch_ImgUrl", am.getImgUrl());
			jo.put("Arch_Remark", am.getFunction());
			jo.put("isUnit", am.getIsUnit());
			main.add(jo);
		}

		PrintWriter out = response.getWriter();
		out.println(main.toString());
		out.flush();
		out.close();

	}


	private void getAllArchForPrePay(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}
		List<Architecture> archlist = ad.displayAllForPrePay(userID);
		JSONArray main = new JSONArray();

		for (int i = 0; i < archlist.size(); i++)
		{
			Architecture am = archlist.get(i);

			JSONObject jo = new JSONObject();

			jo.put("Arch_ID", am.getId());
			jo.put("Arch_Style", am.getStyle());
			jo.put("Arch_Num", am.getNum());
			jo.put("Arch_Name", am.getName());
			jo.put("Arch_Time", am.getTime());
			jo.put("Arch_Storey", am.getStorey());
			jo.put("Arch_StartFloor", am.getFloorstart());
			jo.put("Arch_AreaID", am.getAreaID());
			jo.put("Arch_Space", am.getArea());
			jo.put("Arch_Aircondition", am.getAircondition());
			jo.put("Arch_CountMan", am.getCountMan());
			jo.put("Arch_Man", am.getMan());
			jo.put("Arch_Phone", am.getPhone());
			jo.put("Arch_Payment", am.getPayment());
			jo.put("Arch_ImgUrl", am.getImgUrl());
			jo.put("Arch_Remark", am.getFunction());

			main.add(jo);
		}

		PrintWriter out = response.getWriter();
		out.println(main.toString());
		out.flush();
		out.close();

	}
	private void getArchByAreaWithoutUserId(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		HttpSession session = request.getSession();
		int userID = 1;
		//		if (session.getAttribute("userId") != null)
		//		{
		//			userID = Integer
		//					.parseInt(session.getAttribute("userId").toString());
		//		}
		if (request.getParameter("Arch_AreaID") != null)
		{
			int areaID = Integer.parseInt(request.getParameter("Arch_AreaID")
					.toString());
			List<Architecture> list = ad.queryArchByAreaID(areaID, userID);

			if (list != null && list.size() > 0)
			{
				JSONArray json = new JSONArray();
				for (Architecture am : list)
				{
					JSONObject jo = new JSONObject();

					jo.put("Arch_ID", am.getId());
					jo.put("Arch_Style", am.getStyle());
					jo.put("Arch_Num", am.getNum());
					jo.put("Arch_Name", am.getName());
					jo.put("Arch_Time", am.getTime());
					jo.put("Arch_Storey", am.getStorey());
					jo.put("Arch_StartFloor", am.getFloorstart());
					jo.put("Arch_AreaID", am.getAreaID());
					jo.put("Arch_Space", am.getArea());
					jo.put("Arch_Aircondition", am.getAircondition());
					jo.put("Arch_CountMan", am.getCountMan());
					jo.put("Arch_Man", am.getMan());
					jo.put("Arch_Phone", am.getPhone());
					jo.put("Arch_Payment", am.getPayment());
					jo.put("Arch_ImgUrl", am.getImgUrl());
					jo.put("Arch_Remark", am.getFunction());

					json.add(jo);
				}

				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			}
		}

	}

	private void getArchByArea(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}
		if (request.getParameter("Arch_AreaID") != null)
		{
			int areaID = Integer.parseInt(request.getParameter("Arch_AreaID")
					.toString());
			List<Architecture> list = ad.queryArchByAreaID(areaID, userID);

			if (list != null && list.size() > 0)
			{
				JSONArray json = new JSONArray();
				for (Architecture am : list)
				{
					JSONObject jo = new JSONObject();

					jo.put("Arch_ID", am.getId());
					jo.put("Arch_Style", am.getStyle());
					jo.put("Arch_Num", am.getNum());
					jo.put("Arch_Name", am.getName());
					jo.put("Arch_Time", am.getTime());
					jo.put("Arch_Storey", am.getStorey());
					jo.put("Arch_StartFloor", am.getFloorstart());
					jo.put("Arch_AreaID", am.getAreaID());
					jo.put("Arch_Space", am.getArea());
					jo.put("Arch_Aircondition", am.getAircondition());
					jo.put("Arch_CountMan", am.getCountMan());
					jo.put("Arch_Man", am.getMan());
					jo.put("Arch_Phone", am.getPhone());
					jo.put("Arch_Payment", am.getPayment());
					jo.put("Arch_ImgUrl", am.getImgUrl());
					jo.put("Arch_Remark", am.getFunction());

					json.add(jo);
				}

				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			}
		}

	}

	/**
	 * 获取所有建筑类别
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	private void getAllArchStyle(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArrayList<DictionaryValueModel> list = new ArrayList<DictionaryValueModel>();
		DictionaryValueDao dvd = new DictionaryValueDao();

		list = dvd.listDictionaryValueByName("建筑分类");
		if (list != null && list.size() > 0)
		{
			JSONArray json = new JSONArray();
			for (DictionaryValueModel am : list)
			{
				JSONObject jo = new JSONObject();

				jo.put("styleID", am.getDictionaryValueNum().trim());
				jo.put("styleName", am.getDictionaryValue().trim());// 经过测试，去掉trim会有一个回车，导致下拉树样式占2行

				json.add(jo);
			}

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * 获取电能分项(节能管理，能耗实时监管里面的)
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	private void getAllAmFenXiangInMonitor(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArrayList<DictionaryValueModel> list = new ArrayList<DictionaryValueModel>();
		DictionaryValueDao dvd = new DictionaryValueDao();

		list = dvd.listDictionaryValueByName("能耗编码");

		if (list != null && list.size() > 0)
		{
			JSONArray json = new JSONArray();

			for (DictionaryValueModel am : list)
			{
				JSONObject jo = new JSONObject();

				jo.put("amFenXiangID", am.getDictionaryValueNum().trim());
				jo.put("amFenXiangName", am.getDictionaryValue().trim());// 经过测试，去掉trim会有一个回车，导致下拉树样式占2行

				json.add(jo);
			}

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * 获取水耗分项(节能管理，能耗实时监管里面的)
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	private void getAllWaterFenXiangInMonitor(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArrayList<DictionaryValueModel> list = new ArrayList<DictionaryValueModel>();
		DictionaryValueDao dvd = new DictionaryValueDao();

		list = dvd.listDictionaryValueByName("用水分项");

		if (list != null && list.size() > 0)
		{
			JSONArray json = new JSONArray();

			for (DictionaryValueModel am : list)
			{
				JSONObject jo = new JSONObject();

				jo.put("amFenXiangID", am.getDictionaryValueNum().trim());
				jo.put("amFenXiangName", am.getDictionaryValue().trim());// 经过测试，去掉trim会有一个回车，导致下拉树样式占2行

				json.add(jo);
			}

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}

	}

	private void getAllArchByArchStyle(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		char style = request.getParameter("archStyle").charAt(0);
		ArchitectureDao ad = new ArchitectureDao();
		ArrayList<Architecture> list = new ArrayList<Architecture>();
		list = ad.queryArchByStyle(style);

		if (list != null && list.size() > 0)
		{
			JSONArray json = new JSONArray();
			for (Architecture am : list)
			{
				JSONObject jo = new JSONObject();
				jo.put("archID", am.getId());
				jo.put("archName", am.getName());
				json.add(jo);
			}

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * 建筑多选的方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	private void getMulSlect(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}
		ArchitectureDao archDao = new ArchitectureDao();
		Map<String, List<ArcMulSelectModel>> map = null;
		try
		{
			map = archDao.getArcMulSelect(userID);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		Gson gson_MulSelect = new Gson();
		String MulSelectdata = gson_MulSelect.toJson(map);
		PrintWriter out = response.getWriter();
		out.println(MulSelectdata);
		out.flush();
		out.close();
	}
	private void getAreaIDByArchID(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		int archID=-1;
		if (request.getParameter("archID") != null)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}


		int areaID=ad.queryAreaIDByArchID(archID);

		PrintWriter out = response.getWriter();
		out.println(areaID);
		out.flush();
		out.close();
	}
	private void get3DArchInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureDao ad = new ArchitectureDao();
		int archID=-1;
		if (request.getParameter("archID") != null)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}
		Architecture am = ad.getArchByID(archID);
		JSONArray main = new JSONArray();

		//		for (int i = 0; i < archlist.size(); i++)
		//		{
		//			Architecture am = archlist.get(i);

		JSONObject jo = new JSONObject();

		jo.put("Arch_ID", am.getId());
		jo.put("Arch_Style", am.getStyle());
		jo.put("Arch_Num", am.getNum());
		jo.put("Arch_Name", am.getName());
		jo.put("Arch_Time", am.getTime());
		jo.put("Arch_Storey", am.getStorey());
		jo.put("Arch_StartFloor", am.getFloorstart());
		jo.put("Arch_AreaID", am.getAreaID());
		jo.put("Arch_Space", am.getArea());
		jo.put("Arch_Aircondition", am.getAircondition());
		jo.put("Arch_CountMan", am.getCountMan());
		jo.put("Arch_Man", am.getMan());
		jo.put("Arch_Phone", am.getPhone());
		jo.put("Arch_Payment", am.getPayment());
		jo.put("Arch_ImgUrl", am.getImgUrl());
		jo.put("Arch_Remark", am.getFunction());
		jo.put("isUnit", am.getIsUnit());
		main.add(jo);
		//		}

		PrintWriter out = response.getWriter();
		out.println(main.toString());
		//		System.out.println(main.toString());
		out.flush();
		out.close();

	}

}
