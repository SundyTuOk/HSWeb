/**
 * 2014-4-25
 */
package com.sf.energy.project.equipment.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lowagie.text.pdf.codec.Base64.OutputStream;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.dao.GatherDao;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.equipment.model.WatermeterModel;
import com.sf.energy.project.equipment.serviceImpl.AmmeterSearchImpl;
import com.sf.energy.project.right.dao.UsersArchitectureDao;
import com.sf.energy.project.system.dao.AmmPriceDao;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DataSiteDao;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.dao.MeterStyleDao;
import com.sf.energy.project.system.dao.PumpDao;
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

/**
 * @author WangZhao 2014-4-25
 * 
 * 
 */
public class AmmeterManage extends HttpServlet
{
	
	
	String numberPatern = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";

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

		if("getFirstFXByFXNum".equals(method)){
			getFirstFXByFXNum(request, response);
		}
		
		if("getGatherInfoByArch".equals(method))
			getGatherInfoByArch(request, response);
		
		if("getArchInfoByArea".equals(method))
			getArchInfoByArea(request, response);
		
		if("getAmmeterInfoByID".equals(method))
			getAmmeterInfoByID(request, response);
		
		if ("getAllAmmeters".equals(method))
			getAllAmmeter(request, response);

		if ("getAmmeterByArch".equals(method))
			getAmmeterByArch(request, response);

		if ("deleteAmmeter".equals(method))
			deleteAmmeter(request, response);

		if ("addAmmeter".equals(method))
			addAmmeter(request, response);

		if ("updateAmmeter".equals(method))
			updateAmmeter(request, response);

		if ("searchSomeAmmeter".equals(method))
			searchSomeAmmeter(request, response);

		if ("paginate".equals(method))
			paginate(request, response);

		if ("searchPaginate".equals(method))
			searchPaginate(request, response);

		if ("getAllAmmByArchAndFloor".equals(method))
			getAllAmmByArchAndFloor(request, response);
		
		if ("initSearchBox".equals(method))
			initSearchBox(request, response);

		if ("getAllAmmeterTree".equals(method))
			getAllAmmeterTree(request, response);

		if ("getImportantAmmByArchAndFloor".equals(method))
			getImportantAmmByArchAndFloor(request, response);

		if ("getWaterImportantAmmByArchAndFloor".equals(method))
			getWaterImportantAmmByArchAndFloor(request, response);

		if ("deleteSomeAmmeter".equals(method))
			deleteSomeAmmeter(request, response);

		if ("export".equals(method))
			export(request, response);

		if ("importAm".equals(method))
			importAm(request, response);

		if ("exportFailResult".equals(method))
			exportFailResult(request, response);

		if ("queryByGatherID".equals(method))
			queryByGatherID(request, response);
		
		if ("queryallAmmeterName".equals(method))
			queryallAmmeterName(request, response);
		
		if ("initEditstyleSearchBox".equals(method))
			initEditstyleSearchBox(request, response);
		
		if ("EditAmmeterStyle".equals(method))
			EditAmmeterStyle(request, response);
		
		if ("LoadEditstyleAreaChange".equals(method))
			LoadEditstyleAreaChange(request, response);

		if ("LoadEditstyleArchChange".equals(method))
			LoadEditstyleArchChange(request, response);
		
		if ("EditAmStylesearch".equals(method))
			EditAmStylesearch(request, response);
		
		if("getArchInfoByAreaWithoutAmmeter".equals(method))
			getArchInfoByAreaWithoutAmmeter(request, response);
	}

	private void getArchInfoByAreaWithoutAmmeter(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		int areaID=0;
		if(DataValidation.checkParameter(request.getParameter("AreaID")))
			areaID = Integer.parseInt(request.getParameter("AreaID"));
		
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		
		AmmeterDao dao = new AmmeterDao();
		List<Architecture> list = dao.queryArchByAreaIDWithoutAmmeter(areaID);
		if (list != null && list.size() > 0)
		{
			for(Architecture arch : list){
				jo = new JSONObject();
				jo.put("id", arch.getId());
				jo.put("name", arch.getName());
				jo.put("story", arch.getStorey());
				jo.put("start", arch.getFloorstart());
				json.add(jo);
			}
		}
		
		PrintWriter out = response.getWriter();

		out.println(json.toString());
		out.flush();
		out.close();
		
	}
	private void getFirstFXByFXNum(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String FenXiangNum = request.getParameter("FenXiangNum");
		
		AmmeterDao dao = new AmmeterDao();
		JSONArray json = null;
		
		if(FenXiangNum != null){
			json = dao.getFirstFXByFXNum(FenXiangNum);
		}
		
		PrintWriter out = response.getWriter();

		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getGatherInfoByArch(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		int archID =0;
		if(DataValidation.checkParameter(request.getParameter("ArchID")))
			archID = Integer.parseInt(request.getParameter("ArchID"));
		
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		
		GatherDao dao = new GatherDao();
		List<Gather> list = dao.queryGatherByArchID(archID);
		if (list != null && list.size() > 0)
		{
			for(Gather gather : list){
				jo = new JSONObject();
				jo.put("gather_id", gather.getGatherID());
				jo.put("gather_name", gather.getGatherName());
				json.add(jo);
			}
		}
		PrintWriter out = response.getWriter();

		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getArchInfoByArea(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		int areaID=0;
		if(DataValidation.checkParameter(request.getParameter("AreaID")))
			areaID = Integer.parseInt(request.getParameter("AreaID"));
		
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		
		AmmeterDao dao = new AmmeterDao();
		List<Architecture> list = dao.queryArchByAreaID(areaID);
		if (list != null && list.size() > 0)
		{
			for(Architecture arch : list){
				jo = new JSONObject();
				jo.put("id", arch.getId());
				jo.put("name", arch.getName());
				jo.put("story", arch.getStorey());
				jo.put("start", arch.getFloorstart());
				json.add(jo);
			}
		}
		
		PrintWriter out = response.getWriter();

		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	private void EditAmStylesearch(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		AmmeterDao ad = new AmmeterDao();
		// long start = System.currentTimeMillis();
		AmmPriceDao aPriceDao = new AmmPriceDao();
		int areaID = 0;
		if (request.getParameter("areaID") != null)
			areaID = Integer.parseInt(request.getParameter("areaID"));

		int archID = 0;
		if (request.getParameter("archID") != null)
			archID = Integer.parseInt(request.getParameter("archID"));

		int floor = 0;
		if (request.getParameter("floor") != null)
			floor = Integer.parseInt(request.getParameter("floor"));

		int partmentID = 0;
		if (request.getParameter("partmentID") != null)
			partmentID = Integer.parseInt(request.getParameter("partmentID"));

		int priceID = 0;
		if (request.getParameter("priceID") != null)
			priceID = Integer.parseInt(request.getParameter("priceID"));
		int meterstyleID = 0;
		if (request.getParameter("meterstyleID") != null)
			meterstyleID = Integer.parseInt(request.getParameter("meterstyleID"));
		List<AmmeterModel> list = ad.EditAmStylesearch(areaID, archID, floor, partmentID, priceID,meterstyleID);
		JSONArray ammeterList = new JSONArray();
		if (list != null && list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				AmmeterModel n = list.get(i);
				JSONObject jo1 = buildNode(n);
				String pricename = "";
				pricename = aPriceDao.queryNameByID(n.getPriceID());
				jo1.put("Price_Name", pricename);
				ammeterList.add(jo1);
			}
		} 
		PrintWriter out = response.getWriter();
		//System.out.println("serch:"+ammeterList.toString());
		out.println(ammeterList.toString());
		out.flush();
		out.close();		
	}

	private void LoadEditstyleAreaChange(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		AmmeterDao ad = new AmmeterDao();
		int AreaID = 0;
		List<Architecture> list=null;
		JSONArray jArray = new JSONArray();
		if(request.getParameter("AreaID")!=null
				&&!"".equals(request.getParameter("AreaID"))){
			AreaID = Integer.parseInt(request.getParameter("AreaID"));
		}else{
			return;
		}
		if(AreaID==0){//选择所有区域
			list = ad.getAllAmmArch();
		}else{
			list = ad.queryArchByAreaID(AreaID);
		}
		
		if(list.size()>0){
			for(Architecture a:list){
				JSONObject jObject = new JSONObject();
				jObject.put("arch_id",a.getId());
				jObject.put("arch_name", a.getName());
				jArray.add(jObject);
			}
		}
		//System.out.println("LoadEditstyleAreaChange:"+jArray.toString());
		out.println(jArray.toString());
		out.flush();
		out.close();
	}

	private void LoadEditstyleArchChange(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		ArchitectureDao aDao = new ArchitectureDao();
		Architecture arch = new Architecture();
		int archid = 0;
		if(request.getParameter("ArchID")!=null
				&&!"".equals(request.getParameter("ArchID"))){
			archid = Integer.parseInt(request.getParameter("ArchID"));
		}else{
			return;
		}
		if(archid!=0){
			arch = aDao.getArchFloorByArchID(archid);
		}else{
			arch.setStorey(7);
			arch.setFloorstart(1);
		}
		JSONObject json = new JSONObject();
		json.put("storey", arch.getStorey());
		json.put("start", arch.getFloorstart());
		//System.out.println("LoadEditstyleArchChange:"+json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void initEditstyleSearchBox(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		AmmeterDao ad = new AmmeterDao();
		List<Architecture> archList = ad.getAllAmmArch();
		List<Area> areaList = ad.getAllAmmArea();
		List<Department> dpList = ad.getAllDepartment();


		if (dpList != null || archList != null || areaList != null
				/*|| ammAddrList != null || ammNameList != null
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

			if (dpList != null && dpList.size() > 0)
			{
				JSONArray list = new JSONArray();

				for (Department a : dpList)
				{
					JSONObject jo = new JSONObject();
					jo.put("Partment_ID", a.getDepartmentID());
					jo.put("Partment_Name", a.getDepartmetName());
					list.add(jo);
				}

				main.put("PartmentList", list);
			}
			PrintWriter out = response.getWriter();
			//System.out.println("init:"+main.toString());
			out.println(main.toString());
			out.flush();
			out.close();
		}
		
	}

	private void queryallAmmeterName(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{	
		PrintWriter out = response.getWriter();
		//System.out.println("area_id :"+area_id);
		AmmeterDao ammeterDao = new AmmeterDao();
		List<AmmeterModel>  list = null;
		AmmeterModel n = null;
		list = ammeterDao.queryAmmeterAllName();
		JSONArray json = new JSONArray();
		for (int i = list.size() - 1; i >= 0; i--)
		{
			 n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("AMMETER_ID", n.getAmmeterID());
			jo.put("AREA_ID", n.getAreaID());
			jo.put("AMMETER_NAME", n.getAmmeterName());
			json.add(jo);
		}	
		String data = json.toString();
	//	System.out.println("数据"+data);
		out.println(data);
		out.flush();
		out.close();	
	}
	
	private void queryByGatherID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AmmeterDao ad = new AmmeterDao();
		
		int gatherID = 0;
		if (request.getParameter("gatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("gatherID"));
		}
		List<AmmeterModel> list = ad.queryByGatherID(gatherID);

		JSONArray json = new JSONArray();
		JSONObject jo = null;
		// jo.put("totalCount", ad.getRecordCount());
		// json.add(jo);
		//int count = 50;

//		if (list.size() < count)
//			count = list.size();

		for (int i = 0; i <list.size(); i++)
		{
			AmmeterModel n = list.get(i);
			jo = new JSONObject();
			jo.put("Ammeter_StyleName", n.getMeterStyleName());
			jo.put("Ammeter_ID", n.getAmmeterID());
			jo.put("Ammeter_AreaName", n.getAreaName());
			jo.put("Ammeter_PartmentName", n.getPartmentName());
			jo.put("Ammeter_ArchName", n.getArchName());
			jo.put("Ammeter_Floor", n.getFloor());
			jo.put("Ammeter_GatherName", n.getGatherName());
			jo.put("Ammeter_Num", n.getAmmeterNum());
			jo.put("Ammeter_Name", n.getAmmeterName());
			jo.put("Ammeter_Password", n.getAmmeterPassword());
			jo.put("Ammeter_Address485", n.getAmmeterAddress485());
			jo.put("Ammeter_Maker", n.getMaker());
			jo.put("Ammeter_MakerCode", n.getMakerCode());
			jo.put("Ammeter_AssetNo", n.getAssetNo());
			jo.put("Ammeter_IsSupply", n.getIsSupply());
			jo.put("Ammeter_UseStyleName", n.getUseStyleName());
			jo.put("Ammeter_ConsumerNum", n.getConsumerNum());
			jo.put("Ammeter_ConsumerName", n.getConsumerName());
			jo.put("Ammeter_ConsumerPhone", n.getConsumerPhone());
			jo.put("Ammeter_ConsumerAddress", n.getConsumerAddress());
			jo.put("Ammeter_IsImportantUser", n.getIsImportantUser());
			jo.put("Ammeter_IsComputation", n.getIsComputation());
			jo.put("Ammeter_Plose", n.getAmmeterPlose());
			json.add(jo);

		}
		

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
	//	System.out.println("电表查询结果：gatherID="+gatherID+"结果条数:"+list.size()+"  "+json.toString());
		out.flush();
		out.close();

	}

	private void importAm(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			RowsExceededException, WriteException
	{
		
		ExportHelper dh = ExportHelper.getInstance();
		Map<String, File> imFailMap = new HashMap<String, File>();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "电表管理", "电表导入");

		String saveFolder = "img/archImg";
		List<List<String>> result = null;
		File file = dh
				.getImportFile(request, response, saveFolder, "theAmFile");

		JSONArray main = new JSONArray();

		JSONObject jo = new JSONObject();

		if (file != null)
		{
			if (file.getAbsolutePath().endsWith(".xls"))
			{
				result = importAmToDB(file, request);
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
							+ new String("电表导入失败数据.xls".getBytes("gb2312"),
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
		AmmeterDao ad = new AmmeterDao();
		AmmeterSearchImpl as = new AmmeterSearchImpl();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "电表管理", "电表导出");

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

		String sortLabel = "AmMeter_ID";
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

		// 构建查询Sql语句
		String sql = as.buildSql(areaID, arch, gather, address, name, num,
				sortLabel, isAsc);

		List<AmmeterModel> list = ad.display(sql);

		File file = getExportAmFile(list);

		FileInputStream fis = new FileInputStream(file);
		byte[] fb = new byte[fis.available()];
		fis.read(fb);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String("电表导出文件.xls".getBytes("gb2312"), "iso8859-1"));

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

	private File getExportAmFile(List<AmmeterModel> amList)
			throws RowsExceededException, WriteException, IOException
	{
		
		ExportHelper dh = ExportHelper.getInstance();
		File file = null;
		String[] theTitles =
		{ "用户编号(20Byte)", "用户名称(50Byte)", "用户联系电话(20Byte)", "所属建筑(200Byte)",
				"电表名称(50Byte)", "表计类型(50Byte)", "用电类型(50Byte)",
				"表计资产号(50Byte)", "表计厂家(50Byte)", "电表485地址(30Byte)",
				"网关地址(12Byte)", "电表密码(8Byte)", "能耗编码(20Byte)" ,
				"所属部门(20Byte)","价格(20Byte)","重点用户(1Byte)","是否总表(1Byte)","是否纳入计量(1Byte)","修正量(20Byte)","房间人数(8Byte)","房间容量(8Byte)","总表地址(12Byte)"};
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
			AmmeterModel am = amList.get(i);
			List<String> item = new LinkedList<String>();

			// 用户编号
			item.add(am.getConsumerNum());

			// 用户名称
			item.add(am.getConsumerName());

			// 用户联系电话
			item.add(am.getConsumerPhone());

			// 所属建筑
			item.add(am.getArchName() + "-" + am.getFloor());

			// 电表名称
			item.add(am.getAmmeterName());

			// 表计类型
			item.add(am.getMeterStyleName());

			// 用电类型
			item.add(am.getUseAmStyleName());

			// 表计资产号
			item.add(am.getAssetNo());

			// 表计厂家
			item.add(am.getMaker());

			// 电表485地址
			item.add(am.getAmmeterAddress485());

			// 网关地址
			item.add(am.getGatherAddress());

			// 电表密码
			item.add(am.getAmmeterPassword());

			// 能耗编码
			item.add(am.getAmmeterNum());
			//"所属部门(20Byte)",
			
			try
			{
				DepartmentDao dp=new DepartmentDao();
				Department partment;
				partment = dp.query(am.getPartment());
				item.add(partment.getDepartmentNum());
			} catch (NumberFormatException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//"价格(20Byte)",
			AmmeterDao amDao=new AmmeterDao();
			try
			{
				String priceName=amDao.queryPriceNameByID(am.getPriceID());
				item.add(priceName);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//"重点用户(1Byte)"
			item.add(am.getIsImportantUser()+"");
			//"是否总表(1Byte)","是否纳入计量(1Byte)","修正量(20Byte)","房间人数(8Byte)","房间容量(8Byte)"
			item.add(am.getIsCountMeter()+"");
			//"是否纳入计量(1Byte)"
			item.add(am.getIsComputation()+"");
			//"修正量(20Byte)"
			item.add(am.getXiuzheng()+"");
			
			item.add((int)am.getApManCount()+"");
			
			item.add((int)am.getRoomManCount()+"");
			try
			{
//				if (am.getParentID()!=null)
//				{
					item.add(amDao.queryAddrById(am.getParentID()));
//				}
				
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.add(item);
		}
		file = dh.getExportFile(data);

		return file;
	}

	private void deleteSomeAmmeter(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AmmeterDao ad = new AmmeterDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		

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
		if (ad.deleteSomeAmmeter(list)){
			info = "success";
			// 写入日志
			log.writeLog(userLoginName, "电表管理", "电表批量删除");
		}
		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}
	
	private void EditAmmeterStyle(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AmmeterDao ad = new AmmeterDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		String IDList = null;
		int meterstyleid=-1;
		if (request.getParameter("IDList") != null&&!"".equals(request.getParameter("IDList")))
			IDList = request.getParameter("IDList");
		else
			return;
		if (request.getParameter("MeteStyle_ID") != null
				&& !"".equals(request.getParameter("MeteStyle_ID")))
			meterstyleid=(Integer.parseInt(request
					.getParameter("MeteStyle_ID")));
		else 
			return;
		String[] strList = null;
		if(IDList!=null)
			strList = IDList.split(",");
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
		MeterStyleDao msd = new MeterStyleDao();
		MeterStyle meterStyle = null;
		meterStyle = msd.queryByID(meterstyleid);
		if (ad.editAmmeterstyle(list,meterstyleid)){
			info = "success";
			// 写入日志
			log.writeLog(userLoginName, "电表管理", "电表批量修改表计类型,修改为："+meterStyle.getMeterStyleName());
		}
		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	private void getAllAmmeterTree(HttpServletRequest request,
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
		AmmeterDao ad = new AmmeterDao();
		JSONObject node = null;
		if (theArch != null)
		{
			node = new JSONObject();
			node.put("name", theFloor + "楼");
			List<AmmeterModel> amList = ad.queryByArchAndFloor(theArch.getId(),
					theFloor);

			if (amList != null && amList.size() > 0)
			{
				node.put("type", "folder");
				JSONObject amListInFloor = new JSONObject();

				JSONObject jo = new JSONObject();

				for (AmmeterModel am : amList)
				{
					JSONObject amNode = new JSONObject();
					amNode.put("type", "item");
					amNode.put("name", am.getAmmeterName());
					amNode.put("AmmeterID", am.getAmmeterID());
					amListInFloor.put(am.getAmmeterID(), amNode);
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

	private void initSearchBox(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AmmeterDao ad = new AmmeterDao();
		List<Gather> gatherList = ad.getAllAmmGather();
		List<Architecture> archList = ad.getAllAmmArch();
		List<Area> areaList = ad.getAllAmmArea();


		if (gatherList != null || archList != null || areaList != null
				/*|| ammAddrList != null || ammNameList != null
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

			/*if (ammAddrList != null && areaList.size() > 0)
			{
				JSONArray list = new JSONArray();

				for (String a : ammAddrList)
				{
					JSONObject jo = new JSONObject();

					jo.put("Ammeter_Addr", a);

					list.add(jo);
				}

				main.put("AmmAddrList", list);
			}

			if (ammNameList != null && ammNameList.size() > 0)
			{
				JSONArray list = new JSONArray();

				for (String a : ammNameList)
				{
					JSONObject jo = new JSONObject();

					jo.put("Ammeter_Name", a);

					list.add(jo);
				}

				main.put("AmmNameList", list);
			}

			if (consumNumList != null && consumNumList.size() > 0)
			{
				JSONArray list = new JSONArray();

				for (String a : consumNumList)
				{
					JSONObject jo = new JSONObject();

					jo.put("Consum_Num", a);

					list.add(jo);
				}

				main.put("ConsumNumList", list);
			}
*/
			PrintWriter out = response.getWriter();
			out.println(main.toString());
			out.flush();
			out.close();
		}
	}

	private void searchSomeAmmeter(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		
		AmmeterSearchImpl as = new AmmeterSearchImpl();
		int areaID = 0;
		if (request.getParameter("areaID") != null)
			areaID = Integer.parseInt(request.getParameter("areaID"));

		int arch = 0;
		if (request.getParameter("arch") != null)
			arch = Integer.parseInt(request.getParameter("arch"));

		int gather = 0;
		if (request.getParameter("gather") != null)
			gather = Integer.parseInt(request.getParameter("gather"));

		String address = null;
		if (!"".equals(request.getParameter("address")))
			address = request.getParameter("address");

		String name = null;
		if (!"".equals(request.getParameter("name")))
			name = request.getParameter("name");

		String num = null;
		if (!"".equals(request.getParameter("num")))
			num = request.getParameter("num");

		List<AmmeterModel> list = as.complexQueryAmm(areaID, arch, gather,
				address, name, num);

		JSONArray json = new JSONArray();
		for (int i = list.size() - 1; i >= 0; i--)
		{
			AmmeterModel n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("Ammeter_StyleName", n.getMeterStyleName());
			jo.put("Ammeter_ID", n.getAmmeterID());
			jo.put("Ammeter_AreaName", n.getAreaName());
			jo.put("Ammeter_PartmentName", n.getPartmentName());
			jo.put("Ammeter_ArchName", n.getArchName());
			jo.put("Ammeter_Floor", n.getFloor());
			jo.put("Ammeter_GatherName", n.getGatherName());
			jo.put("Ammeter_Num", n.getAmmeterNum());
			jo.put("Ammeter_Name", n.getAmmeterName());
			jo.put("Ammeter_Password", n.getAmmeterPassword());
			jo.put("Ammeter_Address485", n.getAmmeterAddress485());
			jo.put("Ammeter_Maker", n.getMaker());
			jo.put("Ammeter_MakerCode", n.getMakerCode());
			jo.put("Ammeter_AssetNo", n.getAssetNo());
			jo.put("Ammeter_IsSupply", n.getIsSupply());
			jo.put("Ammeter_UseStyleName", n.getUseStyleName());
			jo.put("Ammeter_ConsumerNum", n.getConsumerNum());
			jo.put("Ammeter_ConsumerName", n.getConsumerName());
			jo.put("Ammeter_ConsumerPhone", n.getConsumerPhone());
			jo.put("Ammeter_ConsumerAddress", n.getConsumerAddress());
			jo.put("Ammeter_IsImportantUser", n.getIsImportantUser());
			jo.put("Ammeter_IsComputation", n.getIsComputation());
			jo.put("Ammeter_Plose", n.getAmmeterPlose());
			json.add(jo);
		}

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void updateAmmeter(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AmmeterDao ad = new AmmeterDao();
		
		ArchitectureDao archDao = new ArchitectureDao();
		
		SystemInfoDao sid = new SystemInfoDao();
		
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "电表管理", "电表编辑");

		AmmeterModel ammeter = null;

		int ammeterId = 0;
		if (request.getParameter("AmMeter_ID") != null
				&& request.getParameter("AmMeter_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("AmMeter_ID")))
			ammeterId = Integer.parseInt(request.getParameter("AmMeter_ID"));

		ammeter = ad.queryByID(ammeterId);

		if (ammeter == null)
			return;
		int oldMeteStyleID=ammeter.getMeteStyleID();
		int oldBeiLv=ammeter.getBeilv();
		float oldXiuZheng=ammeter.getXiuzheng();
		
		
		if (request.getParameter("AmMeter_Point") != null
				&& request.getParameter("AmMeter_Point").matches(numberPatern)
				&& !"".equals(request.getParameter("AmMeter_Point")))
			ammeter.setAmmeterPoint(Integer.parseInt(request
					.getParameter("AmMeter_Point")));

		if (request.getParameter("Area_ID") != null
				&& request.getParameter("Area_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Area_ID")))
			ammeter.setAreaID(Integer.parseInt(request.getParameter("Area_ID")));

		if (request.getParameter("Architecture_ID") != null
				&& request.getParameter("Architecture_ID")
						.matches(numberPatern)
				&& !"".equals(request.getParameter("Architecture_ID")))
			ammeter.setArchitectureID(Integer.parseInt(request
					.getParameter("Architecture_ID")));

		Architecture arch = archDao.queryByID(ammeter.getArchitectureID());

		if (request.getParameter("Gather_ID") != null
				&& request.getParameter("Gather_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Gather_ID")))
			ammeter.setGatherID(Integer.parseInt(request
					.getParameter("Gather_ID")));

		if (request.getParameter("FenXiangNum") != null
				&& request.getParameter("firstFenXiang") != null
				&& request.getParameter("secondFenXiang") != null)
		{
			String FenXiangNum = null;
			if (request.getParameter("FenXiangNum") != null)
				FenXiangNum = request.getParameter("FenXiangNum");

			String firstFenXiang = null;
			if (request.getParameter("firstFenXiang") != null)
				firstFenXiang = request.getParameter("firstFenXiang");

			String secondFenXiang = null;
			if (request.getParameter("secondFenXiang") != null)
				secondFenXiang = request.getParameter("secondFenXiang");

			String districtNum = sid.querySystemInfo(1).getSystemInfo_xznum();
			districtNum=padLeft(districtNum, 6);
			String AmMeter_Num = districtNum + "F" + arch.getStyle()
					+ arch.getNum() + "01" + firstFenXiang + secondFenXiang;

			ammeter.setAmmeterNum(AmMeter_Num);
		}

		if (request.getParameter("AmMeter_Name") != null)
			ammeter.setAmmeterName(request.getParameter("AmMeter_Name"));

		if (request.getParameter("AmMeter_Password") != null)
			ammeter.setAmmeterPassword(request.getParameter("AmMeter_Password"));

		if (request.getParameter("AmMeter_485Address") != null)
			ammeter.setAmmeterAddress485(padLeft(request
					.getParameter("AmMeter_485Address"),12));

		if (request.getParameter("Maker") != null)
			ammeter.setMaker(request.getParameter("Maker"));

		if (request.getParameter("MakerCode") != null)
			ammeter.setMakerCode(request.getParameter("MakerCode"));

		if (request.getParameter("AssetNo") != null)
			ammeter.setAssetNo(request.getParameter("AssetNo"));

		if (request.getParameter("AssetNo") != null)
			ammeter.setAssetNo(request.getParameter("AssetNo"));

		if (request.getParameter("IsSupply") != null
				&& request.getParameter("IsSupply").matches(numberPatern)
				&& !"".equals(request.getParameter("IsSupply")))
			ammeter.setIsSupply(Integer.parseInt(request
					.getParameter("IsSupply")));

		if (request.getParameter("ZValue") != null
				&& request.getParameter("ZValue").matches(numberPatern)
				&& !"".equals(request.getParameter("ZValue")))
			ammeter.setzValue(Float.parseFloat(request.getParameter("ZValue")));

		if (request.getParameter("JValue") != null
				&& request.getParameter("JValue").matches(numberPatern)
				&& !"".equals(request.getParameter("JValue")))
			ammeter.setjValue(Float.parseFloat(request.getParameter("JValue")));

		if (request.getParameter("FValue") != null
				&& request.getParameter("FValue").matches(numberPatern)
				&& !"".equals(request.getParameter("FValue")))
			ammeter.setfValue(Float.parseFloat(request.getParameter("FValue")));

		if (request.getParameter("PValue") != null
				&& request.getParameter("PValue").matches(numberPatern)
				&& !"".equals(request.getParameter("PValue")))
			ammeter.setpValue(Float.parseFloat(request.getParameter("PValue")));

		if (request.getParameter("GValue") != null
				&& request.getParameter("GValue").matches(numberPatern)
				&& !"".equals(request.getParameter("GValue")))
			ammeter.setgValue(Float.parseFloat(request.getParameter("GValue")));

		if (request.getParameter("IsImportantUser") != null
				&& request.getParameter("IsImportantUser")
						.matches(numberPatern)
				&& !"".equals(request.getParameter("IsImportantUser")))
			ammeter.setIsImportantUser(Integer.parseInt(request
					.getParameter("IsImportantUser")));

		if (request.getParameter("IsCountMeter") != null
				&& request.getParameter("IsCountMeter").matches(numberPatern)
				&& !"".equals(request.getParameter("IsCountMeter")))
			ammeter.setIsCountMeter(Integer.parseInt(request
					.getParameter("IsCountMeter")));

		if (request.getParameter("isComputation") != null
				&& request.getParameter("isComputation").matches(numberPatern)
				&& !"".equals(request.getParameter("isComputation")))
			ammeter.setIsComputation(Integer.parseInt(request
					.getParameter("isComputation")));

		if (request.getParameter("AmMeter_Plose") != null
				&& request.getParameter("AmMeter_Plose").matches(numberPatern)
				&& !"".equals(request.getParameter("AmMeter_Plose")))
			ammeter.setAmmeterPlose(Integer.parseInt(request
					.getParameter("AmMeter_Plose")));

		if (request.getParameter("isRecycle") != null
				&& request.getParameter("isRecycle").matches(numberPatern)
				&& !"".equals(request.getParameter("isRecycle")))
			ammeter.setIsRecycle(Integer.parseInt(request
					.getParameter("isRecycle")));
		if (request.getParameter("Partment_ID") != null
				&& request.getParameter("Partment_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Partment_ID")))
			ammeter.setPartment(Integer.parseInt(request
					.getParameter("Partment_ID")));

		if (request.getParameter("Floor") != null
				&& request.getParameter("Floor").matches(numberPatern)
				&& !"".equals(request.getParameter("Floor")))
			ammeter.setFloor(Integer.parseInt(request.getParameter("Floor")));
		
		if (request.getParameter("MeteStyle_ID") != null
				&& request.getParameter("MeteStyle_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("MeteStyle_ID"))){
			ammeter.setMeteStyleID(Integer.parseInt(request
					.getParameter("MeteStyle_ID")));
		}
		if (request.getParameter("Price_ID") != null
				&& request.getParameter("Price_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Price_ID")))
			ammeter.setPriceID(Integer.parseInt(request
					.getParameter("Price_ID")));

		if (request.getParameter("AmMeter_Stat") != null
				&& request.getParameter("AmMeter_Stat").matches(numberPatern)
				&& !"".equals(request.getParameter("AmMeter_Stat")))
			ammeter.setAmmeterStat(Integer.parseInt(request
					.getParameter("AmMeter_Stat")));

		if (request.getParameter("IsOffAlarm") != null
				&& request.getParameter("IsOffAlarm").matches(numberPatern)
				&& !"".equals(request.getParameter("IsOffAlarm")))
			ammeter.setIsOffAlarm(Integer.parseInt(request
					.getParameter("IsOffAlarm")));

		if (request.getParameter("CurPower") != null
				&& request.getParameter("CurPower").matches(numberPatern)
				&& !"".equals(request.getParameter("CurPower")))
			ammeter.setCurPower(Float.parseFloat(request
					.getParameter("CurPower")));

		if (request.getParameter("CostCheck") != null
				&& request.getParameter("CostCheck").matches(numberPatern)
				&& !"".equals(request.getParameter("CostCheck")))
			ammeter.setCostCheck(Integer.parseInt(request
					.getParameter("CostCheck")));

		if (request.getParameter("StandByCheck") != null
				&& request.getParameter("StandByCheck").matches(numberPatern)
				&& !"".equals(request.getParameter("StandByCheck")))
			ammeter.setStandByCheck(Integer.parseInt(request
					.getParameter("StandByCheck")));
		
		if (request.getParameter("IsVCCheck") != null
				&& request.getParameter("IsVCCheck").matches(numberPatern)
				&& !"".equals(request.getParameter("IsVCCheck")))
			ammeter.setIsVCCheck(Integer.parseInt(request
					.getParameter("IsVCCheck")));

		if (request.getParameter("Xiuzheng") != null
				&& request.getParameter("Xiuzheng").matches(numberPatern)
				&& !"".equals(request.getParameter("Xiuzheng")))
			ammeter.setXiuzheng(Float.parseFloat(request
					.getParameter("Xiuzheng")));

		if (request.getParameter("LastTime") != null)
			ammeter.setLastTime(request.getParameter("LastTime"));

		if (request.getParameter("ParentID") != null
				&& request.getParameter("ParentID").matches(numberPatern)
				&& !"".equals(request.getParameter("ParentID")))
		{
			int ParentID=Integer.parseInt(request.getParameter("ParentID"));
			if(ParentID==ammeterId)
			{
				ammeter.setParentID(0);
			}else
			{
				ammeter.setParentID(Integer.parseInt(request
						.getParameter("ParentID")));
			}
			
		}
			
		if (request.getParameter("DataFrom") != null
				&& request.getParameter("DataFrom").matches(numberPatern)
				&& !"".equals(request.getParameter("DataFrom")))
			ammeter.setDataFrom(Integer.parseInt(request
					.getParameter("DataFrom")));

		if (request.getParameter("ConsumerNum") != null)
			ammeter.setConsumerNum(request.getParameter("ConsumerNum"));

		if (request.getParameter("ConsumerName") != null)
			ammeter.setConsumerName(request.getParameter("ConsumerName"));

		if (request.getParameter("ConsumerPhone") != null)
			ammeter.setConsumerPhone(request.getParameter("ConsumerPhone"));

		if (request.getParameter("ConsumerAddress") != null)
			ammeter.setConsumerAddress(request.getParameter("ConsumerAddress"));

		if (request.getParameter("UseAmStyle") != null
				&& request.getParameter("UseAmStyle").matches(numberPatern)
				&& !"".equals(request.getParameter("UseAmStyle")))
			ammeter.setUseAmStyle(Integer.parseInt(request
					.getParameter("UseAmStyle")));

		if (request.getParameter("LimitPart") != null
				&& DataValidation.isNumber(request.getParameter("LimitPart")))
			ammeter.setLimitPart(Float.parseFloat(request
					.getParameter("LimitPart")));

		String resultInfo = null;

		if (ad.checkUpdateRepeat(ammeter))
		{
			resultInfo = "同一网关下不允许重复的测量点或通讯地址";
		} else if (ad.modify(ammeter)){
			if(oldXiuZheng!=ammeter.getXiuzheng()){
				ad.updateAmMeterDatasWithChangeXiuzheng(ammeter.getAmmeterID(),oldXiuZheng,ammeter.getXiuzheng());
			}
			if(oldBeiLv!=ammeter.getBeilv()){
				ad.updateAmMeterDatasWithChangeBeiLV(ammeter.getAmmeterID(),oldBeiLv,ammeter.getBeilv(),ammeter.getXiuzheng());
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

	private void addAmmeter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AmmeterDao ad = new AmmeterDao();
		
		ArchitectureDao archDao = new ArchitectureDao();
		
		SystemInfoDao sid = new SystemInfoDao();
		
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "电表管理", "电表新增");

		AmmeterModel ammeter = new AmmeterModel();

		if (request.getParameter("AmMeter_Point") != null
				&& request.getParameter("AmMeter_Point").matches(numberPatern)
				&& !"".equals(request.getParameter("AmMeter_Point")))
			ammeter.setAmmeterPoint(Integer.parseInt(request
					.getParameter("AmMeter_Point")));

		if (request.getParameter("Area_ID") != null
				&& request.getParameter("Area_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Area_ID")))
			ammeter.setAreaID(Integer.parseInt(request.getParameter("Area_ID")));

		if (request.getParameter("Architecture_ID") != null
				&& request.getParameter("Architecture_ID")
						.matches(numberPatern)
				&& !"".equals(request.getParameter("Architecture_ID")))
			ammeter.setArchitectureID(Integer.parseInt(request
					.getParameter("Architecture_ID")));

		Architecture arch = archDao.queryByID(ammeter.getArchitectureID());

		if (request.getParameter("Gather_ID") != null
				&& request.getParameter("Gather_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Gather_ID")))
			ammeter.setGatherID(Integer.parseInt(request
					.getParameter("Gather_ID")));

		if (request.getParameter("FenXiangNum") != null
				&& request.getParameter("firstFenXiang") != null
				&& request.getParameter("secondFenXiang") != null)
		{
			String FenXiangNum = null;
			if (request.getParameter("FenXiangNum") != null)
				FenXiangNum = request.getParameter("FenXiangNum");

			String firstFenXiang = null;
			if (request.getParameter("firstFenXiang") != null)
				firstFenXiang = request.getParameter("firstFenXiang");

			String secondFenXiang = null;
			if (request.getParameter("secondFenXiang") != null)
				secondFenXiang = request.getParameter("secondFenXiang");

			String districtNum = sid.querySystemInfo(1).getSystemInfo_xznum();
			districtNum=padLeft(districtNum, 6);
			String AmMeter_Num = districtNum + "F" + arch.getStyle()
					+ arch.getNum() + "01" + firstFenXiang + secondFenXiang;

			ammeter.setAmmeterNum(AmMeter_Num);
		}

		if (request.getParameter("AmMeter_Name") != null)
			ammeter.setAmmeterName(request.getParameter("AmMeter_Name"));

		if (request.getParameter("AmMeter_Password") != null)
			ammeter.setAmmeterPassword(request.getParameter("AmMeter_Password"));

		if (request.getParameter("AmMeter_485Address") != null)
			ammeter.setAmmeterAddress485(padLeft(request
					.getParameter("AmMeter_485Address"),12));

		if (request.getParameter("Maker") != null)
			ammeter.setMaker(request.getParameter("Maker"));

		if (request.getParameter("MakerCode") != null)
			ammeter.setMakerCode(request.getParameter("MakerCode"));

		if (request.getParameter("AssetNo") != null)
			ammeter.setAssetNo(request.getParameter("AssetNo"));


		if (request.getParameter("IsSupply") != null
				&& request.getParameter("IsSupply").matches(numberPatern)
				&& !"".equals(request.getParameter("IsSupply")))
			ammeter.setIsSupply(Integer.parseInt(request
					.getParameter("IsSupply")));

		if (request.getParameter("ZValue") != null
				&& request.getParameter("ZValue").matches(numberPatern)
				&& !"".equals(request.getParameter("ZValue")))
			ammeter.setzValue(Float.parseFloat(request.getParameter("ZValue")));

		if (request.getParameter("JValue") != null
				&& request.getParameter("JValue").matches(numberPatern)
				&& !"".equals(request.getParameter("JValue")))
			ammeter.setjValue(Float.parseFloat(request.getParameter("JValue")));

		if (request.getParameter("FValue") != null
				&& request.getParameter("FValue").matches(numberPatern)
				&& !"".equals(request.getParameter("FValue")))
			ammeter.setfValue(Float.parseFloat(request.getParameter("FValue")));

		if (request.getParameter("PValue") != null
				&& request.getParameter("PValue").matches(numberPatern)
				&& !"".equals(request.getParameter("PValue")))
			ammeter.setpValue(Float.parseFloat(request.getParameter("PValue")));

		if (request.getParameter("GValue") != null
				&& request.getParameter("GValue").matches(numberPatern)
				&& !"".equals(request.getParameter("GValue")))
			ammeter.setgValue(Float.parseFloat(request.getParameter("GValue")));

		if (request.getParameter("IsImportantUser") != null
				&& request.getParameter("IsImportantUser")
						.matches(numberPatern)
				&& !"".equals(request.getParameter("IsImportantUser")))
			ammeter.setIsImportantUser(Integer.parseInt(request
					.getParameter("IsImportantUser")));

		if (request.getParameter("IsCountMeter") != null
				&& request.getParameter("IsCountMeter").matches(numberPatern)
				&& !"".equals(request.getParameter("IsCountMeter")))
			ammeter.setIsCountMeter(Integer.parseInt(request
					.getParameter("IsCountMeter")));

		if (request.getParameter("isComputation") != null
				&& request.getParameter("isComputation").matches(numberPatern)
				&& !"".equals(request.getParameter("isComputation")))
			ammeter.setIsComputation(Integer.parseInt(request
					.getParameter("isComputation")));

		if (request.getParameter("AmMeter_Plose") != null
				&& request.getParameter("AmMeter_Plose").matches(numberPatern)
				&& !"".equals(request.getParameter("AmMeter_Plose")))
			ammeter.setAmmeterPlose(Integer.parseInt(request
					.getParameter("AmMeter_Plose")));

		if (request.getParameter("isRecycle") != null
				&& request.getParameter("isRecycle").matches(numberPatern)
				&& !"".equals(request.getParameter("isRecycle")))
			ammeter.setIsRecycle(Integer.parseInt(request
					.getParameter("isRecycle")));
		
		if (request.getParameter("Partment_ID") != null
				&& request.getParameter("Partment_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Partment_ID")))
			ammeter.setPartment(Integer.parseInt(request
					.getParameter("Partment_ID")));

		if (request.getParameter("Floor") != null
				&& request.getParameter("Floor").matches(numberPatern)
				&& !"".equals(request.getParameter("Floor")))
			ammeter.setFloor(Integer.parseInt(request.getParameter("Floor")));

		if (request.getParameter("MeteStyle_ID") != null
				&& request.getParameter("MeteStyle_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("MeteStyle_ID")))
			ammeter.setMeteStyleID(Integer.parseInt(request
					.getParameter("MeteStyle_ID")));

		if (request.getParameter("Price_ID") != null
				&& request.getParameter("Price_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Price_ID")))
			ammeter.setPriceID(Integer.parseInt(request
					.getParameter("Price_ID")));

		if (request.getParameter("AmMeter_Stat") != null
				&& request.getParameter("AmMeter_Stat").matches(numberPatern)
				&& !"".equals(request.getParameter("AmMeter_Stat")))
			ammeter.setAmmeterStat(Integer.parseInt(request
					.getParameter("AmMeter_Stat")));

		if (request.getParameter("IsOffAlarm") != null
				&& request.getParameter("IsOffAlarm").matches(numberPatern)
				&& !"".equals(request.getParameter("IsOffAlarm")))
			ammeter.setIsOffAlarm(Integer.parseInt(request
					.getParameter("IsOffAlarm")));

		if (request.getParameter("CurPower") != null
				&& request.getParameter("CurPower").matches(numberPatern)
				&& !"".equals(request.getParameter("CurPower")))
			ammeter.setCurPower(Float.parseFloat(request
					.getParameter("CurPower")));

		if (request.getParameter("CostCheck") != null
				&& request.getParameter("CostCheck").matches(numberPatern)
				&& !"".equals(request.getParameter("CostCheck")))
			ammeter.setCostCheck(Integer.parseInt(request
					.getParameter("CostCheck")));

		if (request.getParameter("StandByCheck") != null
				&& request.getParameter("StandByCheck").matches(numberPatern)
				&& !"".equals(request.getParameter("StandByCheck")))
			ammeter.setStandByCheck(Integer.parseInt(request
					.getParameter("StandByCheck")));
		
		if (request.getParameter("IsVCCheck") != null
				&& request.getParameter("IsVCCheck").matches(numberPatern)
				&& !"".equals(request.getParameter("IsVCCheck")))
			ammeter.setIsVCCheck(Integer.parseInt(request
					.getParameter("IsVCCheck")));

		if (request.getParameter("Xiuzheng") != null
				&& request.getParameter("Xiuzheng").matches(numberPatern)
				&& !"".equals(request.getParameter("Xiuzheng")))
			ammeter.setXiuzheng(Float.parseFloat(request
					.getParameter("Xiuzheng")));

		/*if (request.getParameter("LastTime") != null)
			ammeter.setLastTime(request.getParameter("LastTime"));*/

		if (request.getParameter("ParentID") != null
				&& request.getParameter("ParentID").matches(numberPatern)
				&& !"".equals(request.getParameter("ParentID")))
			ammeter.setParentID(Integer.parseInt(request
					.getParameter("ParentID")));

		if (request.getParameter("DataFrom") != null
				&& request.getParameter("DataFrom").matches(numberPatern)
				&& !"".equals(request.getParameter("DataFrom")))
			ammeter.setDataFrom(Integer.parseInt(request
					.getParameter("DataFrom")));

		if (request.getParameter("ConsumerNum") != null)
			ammeter.setConsumerNum(request.getParameter("ConsumerNum"));

		if (request.getParameter("ConsumerName") != null)
			ammeter.setConsumerName(request.getParameter("ConsumerName"));

		if (request.getParameter("ConsumerPhone") != null)
			ammeter.setConsumerPhone(request.getParameter("ConsumerPhone"));

		if (request.getParameter("ConsumerAddress") != null)
			ammeter.setConsumerAddress(request.getParameter("ConsumerAddress"));

		if (request.getParameter("UseAmStyle") != null
				&& request.getParameter("UseAmStyle").matches(numberPatern)
				&& !"".equals(request.getParameter("UseAmStyle")))
			ammeter.setUseAmStyle(Integer.parseInt(request
					.getParameter("UseAmStyle")));

		String resultInfo = null;

		if (ad.checkAddRepeat(ammeter))
		{
			resultInfo = "同一网关下不允许重复的测量点或通讯地址";
		} else if (ad.insert(ammeter))
			resultInfo = "success";
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void deleteAmmeter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AmmeterDao ad = new AmmeterDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "电表管理", "电表删除");

		int AmmeterID = 0;
		if(DataValidation.checkParameter(request.getParameter("Ammeter_ID")))
			AmmeterID = Integer
			.parseInt(request.getParameter("Ammeter_ID"));

		String resultInfo = null;

		if (ad.delete(AmmeterID))
			resultInfo = "success";
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	
	private void getAmmeterInfoByID(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		int ammeter_id = 0;
		if(DataValidation.checkParameter(request.getParameter("Amm")))
			ammeter_id = Integer.parseInt(request.getParameter("Amm"));
		
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		
		AmmeterDao dao = new AmmeterDao();
		AmmeterModel n = dao.queryByID(ammeter_id);
		if(n != null){
			jo = new JSONObject();
			
			jo = buildNode(n);
			json.add(jo);
		}
		
		
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getAllAmmeter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AmmeterDao ad = new AmmeterDao();
		List<AmmeterModel> list = ad.displayAll();

		JSONArray json = new JSONArray();
		JSONObject jo = null;
		// jo.put("totalCount", ad.getRecordCount());
		// json.add(jo);
		int count = 10;

		if (list.size() < count)
			count = list.size();

		for (int i = list.size() - 1; i > (list.size() - count - 1); i--)
		{
			AmmeterModel n = list.get(i);
			jo = new JSONObject();
			jo.put("Ammeter_StyleName", n.getMeterStyleName());
			jo.put("Ammeter_ID", n.getAmmeterID());
			jo.put("Ammeter_AreaName", n.getAreaName());
			jo.put("Ammeter_PartmentName", n.getPartmentName());
			jo.put("Ammeter_ArchName", n.getArchName());
			jo.put("Ammeter_Floor", n.getFloor());
			jo.put("Ammeter_GatherName", n.getGatherName());
			jo.put("Ammeter_Num", n.getAmmeterNum());
			jo.put("Ammeter_Name", n.getAmmeterName());
			jo.put("Ammeter_Password", n.getAmmeterPassword());
			jo.put("Ammeter_Address485", n.getAmmeterAddress485());
			jo.put("Ammeter_Maker", n.getMaker());
			jo.put("Ammeter_MakerCode", n.getMakerCode());
			jo.put("Ammeter_AssetNo", n.getAssetNo());
			jo.put("Ammeter_IsSupply", n.getIsSupply());
			jo.put("Ammeter_UseStyleName", n.getUseStyleName());
			jo.put("Ammeter_ConsumerNum", n.getConsumerNum());
			jo.put("Ammeter_ConsumerName", n.getConsumerName());
			jo.put("Ammeter_ConsumerPhone", n.getConsumerPhone());
			jo.put("Ammeter_ConsumerAddress", n.getConsumerAddress());
			jo.put("Ammeter_IsImportantUser", n.getIsImportantUser());
			jo.put("Ammeter_IsComputation", n.getIsComputation());
			jo.put("Ammeter_Plose", n.getAmmeterPlose());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getAllAmmByArchAndFloor(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AmmeterDao ad = new AmmeterDao();
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

		List<AmmeterModel> list = ad.queryByArchAndFloor(archID, floor);

		if (list == null || list.size() == 0)
		{
			list = new LinkedList<AmmeterModel>();
		}
		JSONArray json = new JSONArray();
		JSONObject jo = null;

		for (int i = 0; i < list.size(); i++)
		{
			AmmeterModel n = list.get(i);
			jo = new JSONObject();
			jo.put("Ammeter_ID", n.getAmmeterID());
			jo.put("Ammeter_Name", n.getAmmeterName());
			json.add(jo);

		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void getImportantAmmByArchAndFloor(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AmmeterDao ad = new AmmeterDao();
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

		List<AmmeterModel> list = ad.queryImpAmmeterByArchAndFloor(archID,
				floor);

		if (list == null || list.size() == 0)
		{
			list = new LinkedList<AmmeterModel>();
		}
		JSONArray json = new JSONArray();
		JSONObject jo = null;

		for (int i = 0; i < list.size(); i++)
		{
			AmmeterModel n = list.get(i);
			jo = new JSONObject();
			jo.put("Ammeter_ID", n.getAmmeterID());
			jo.put("Ammeter_Name", n.getAmmeterName());
			json.add(jo);

		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void getWaterImportantAmmByArchAndFloor(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AmmeterDao ad = new AmmeterDao();
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

		List<WatermeterModel> list = ad.queryWaterImpAmmeterByArchAndFloor(
				archID, floor);

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
			jo.put("Watermeter_ID", n.getWATERMETER_ID());
			jo.put("Watermeter_Name", n.getWATERMETER_NAME());
			json.add(jo);

		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void getAmmeterByArch(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AmmeterDao ad = new AmmeterDao();
		if (request.getParameter("Arch_ID") != null)
		{
			int archID = Integer.parseInt(request.getParameter("Arch_ID")
					.toString());

			List<AmmeterModel> list = ad.queryByArchID(archID);

			if (list == null || list.size() == 0)
			{
				list = new LinkedList<AmmeterModel>();
			}
			JSONArray json = new JSONArray();
			JSONObject jo = null;

			for (int i = 0; i < list.size(); i++)
			{
				AmmeterModel n = list.get(i);
				jo = new JSONObject();
				jo.put("Ammeter_StyleName", n.getMeterStyleName());
				jo.put("Ammeter_ID", n.getAmmeterID());
				jo.put("Ammeter_AreaName", n.getAreaName());
				jo.put("Ammeter_PartmentName", n.getPartmentName());
				jo.put("Ammeter_ArchName", n.getArchName());
				jo.put("Ammeter_Floor", n.getFloor());
				jo.put("Ammeter_GatherName", n.getGatherName());
				jo.put("Ammeter_Num", n.getAmmeterNum());
				jo.put("Ammeter_Name", n.getAmmeterName());
				jo.put("Ammeter_Password", n.getAmmeterPassword());
				jo.put("Ammeter_Address485", n.getAmmeterAddress485());
				jo.put("Ammeter_Maker", n.getMaker());
				jo.put("Ammeter_MakerCode", n.getMakerCode());
				jo.put("Ammeter_AssetNo", n.getAssetNo());
				jo.put("Ammeter_IsSupply", n.getIsSupply());
				jo.put("Ammeter_UseStyleName", n.getUseStyleName());
				jo.put("Ammeter_ConsumerNum", n.getConsumerNum());
				jo.put("Ammeter_ConsumerName", n.getConsumerName());
				jo.put("Ammeter_ConsumerPhone", n.getConsumerPhone());
				jo.put("Ammeter_ConsumerAddress", n.getConsumerAddress());
				jo.put("Ammeter_IsImportantUser", n.getIsImportantUser());
				jo.put("Ammeter_IsComputation", n.getIsComputation());
				jo.put("Ammeter_Plose", n.getAmmeterPlose());
				json.add(jo);

			}

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		}

	}

	private void searchPaginate(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AmmeterDao ad = new AmmeterDao();
		// long start = System.currentTimeMillis();

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

		Integer thePageCount = 0;
		if (request.getParameter("ammeterPageCount") != null)
			thePageCount = Integer.parseInt(request
					.getParameter("ammeterPageCount"));

		Integer thePageIndex = 0;
		if (request.getParameter("ammeterPageIndex") != null)
			thePageIndex = Integer.parseInt(request
					.getParameter("ammeterPageIndex"));

		String sortLabel = "AmMeter_ID";
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

		Map<String, Object> map = ad.queryPaginate(areaID, arch, gather,
				address, name, num, sortLabel, isAsc, thePageCount,
				thePageIndex);

		List<AmmeterModel> list = null;
		if (map != null)
			list = (List<AmmeterModel>) map.get("List");
		JSONArray ammeterList = new JSONArray();
		if (list != null && list.size() > 0)
		{
			JSONObject jo = new JSONObject();
			jo.put("totalCount", map.get("TotalCount"));
			ammeterList.add(jo);

			for (int i = 0; i < list.size(); i++)
			{
				AmmeterModel n = list.get(i);
				JSONObject jo1 = buildNode(n);
				ammeterList.add(jo1);
			}
		} else
		{
			JSONObject jo = new JSONObject();
			jo.put("totalCount", 0);
			ammeterList.add(jo);
		}

		PrintWriter out = response.getWriter();
		out.println(ammeterList.toString());
		out.flush();
		out.close();
	}

	private void paginate(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AmmeterDao ad = new AmmeterDao();
		
		int thePageCount = 0;
		if(DataValidation.checkParameter(request.getParameter("ammeterPageCount")))
			thePageCount = Integer.parseInt(request
					.getParameter("ammeterPageCount"));

		int thePageIndex =0;
		if(DataValidation.checkParameter(request.getParameter("ammeterPageIndex")))
			thePageIndex = Integer.parseInt(request
					.getParameter("ammeterPageIndex"));
		List<AmmeterModel> list = ad.paginate(thePageCount, thePageIndex);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", ad.getRecordCount());
		// jo.put("pageCount", ad.getRecordCount());
		// jo.put("pageIndex", ad.getRecordCount());
		// jo.put("currentCount", ad.getRecordCount());
		json.add(jo);

		for (AmmeterModel n : list)
		{
			jo = new JSONObject();
			jo.put("Ammeter_StyleName", n.getMeterStyleName());
			jo.put("Ammeter_ID", n.getAmmeterID());
			jo.put("Ammeter_AreaName", n.getAreaName());
			jo.put("Ammeter_PartmentName", n.getPartmentName());
			jo.put("Ammeter_ArchName", n.getArchName());
			jo.put("Ammeter_Floor", n.getFloor());
			jo.put("Ammeter_GatherName", n.getGatherName());
			jo.put("Ammeter_Num", n.getAmmeterNum());
			jo.put("Ammeter_Name", n.getAmmeterName());
			jo.put("Ammeter_Password", n.getAmmeterPassword());
			jo.put("Ammeter_Address485", n.getAmmeterAddress485());
			jo.put("Ammeter_Maker", n.getMaker());
			jo.put("Ammeter_MakerCode", n.getMakerCode());
			jo.put("Ammeter_AssetNo", n.getAssetNo());
			jo.put("Ammeter_IsSupply", n.getIsSupply());
			jo.put("Ammeter_UseStyleName", n.getUseStyleName());
			jo.put("Ammeter_ConsumerNum", n.getConsumerNum());
			jo.put("Ammeter_ConsumerName", n.getConsumerName());
			jo.put("Ammeter_ConsumerPhone", n.getConsumerPhone());
			jo.put("Ammeter_ConsumerAddress", n.getConsumerAddress());
			jo.put("Ammeter_IsImportantUser", n.getIsImportantUser());
			jo.put("Ammeter_IsComputation", n.getIsComputation());
			jo.put("Ammeter_Plose", n.getAmmeterPlose());
			json.add(jo);

		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private JSONObject buildNode(AmmeterModel n)
	{
		JSONObject node = new JSONObject();

		node.put("AmMeter_ID", n.getAmmeterID());
		node.put("AmMeter_Point", n.getAmmeterPoint());
		node.put("Area_ID", n.getAreaID());
		node.put("Architecture_ID", n.getArchitectureID());
		node.put("Gather_ID", n.getGatherID());
		node.put("AmMeter_Num", n.getAmmeterNum());
		node.put("AmMeter_Name", n.getAmmeterName());
		node.put("AmMeter_Password", n.getAmmeterPassword());
		node.put("AmMeter_485Address", n.getAmmeterAddress485());
		node.put("Maker", n.getMaker());
		node.put("MakerCode", n.getMakerCode());
		node.put("AssetNo", n.getAssetNo());
		node.put("IsSupply", n.getIsSupply());
		node.put("ZValue", n.getzValue());
		node.put("JValue", n.getjValue());
		node.put("FValue", n.getfValue());
		node.put("PValue", n.getpValue());
		node.put("GValue", n.getgValue());
		node.put("UseAmStyle", n.getUseAmStyle());
		node.put("ConsumerNum", n.getConsumerNum());
		node.put("ConsumerName", n.getConsumerName());
		node.put("ConsumerPhone", n.getConsumerPhone());
		node.put("ConsumerAddress", n.getConsumerAddress());
		node.put("IsImportantUser", n.getIsImportantUser());
		node.put("IsCountMeter", n.getIsCountMeter());
		node.put("isComputation", n.getIsComputation());
		node.put("AmMeter_Plose", n.getAmmeterPlose());
		node.put("Partment_ID", n.getPartment());
		node.put("Floor", n.getFloor());
		node.put("MeteStyle_ID", n.getMeteStyleID());
		node.put("Price_ID", n.getPriceID());
		node.put("AmMeter_Stat", n.getAmmeterStat());
		node.put("IsOffAlarm", n.getIsOffAlarm());
		node.put("CurPower", n.getCurPower());
		node.put("CostCheck", n.getCostCheck());
		node.put("StandByCheck", n.getStandByCheck());
		node.put("IsVCCheck", n.getIsVCCheck());
		node.put("Xiuzheng", n.getXiuzheng());
		node.put("LastTime", n.getLastTime());
		node.put("beilv", n.getBeilv());
		node.put("ParentID", n.getParentID());
		node.put("DataFrom", n.getDataFrom());
		node.put("ApState", n.getApstate());
		node.put("LimitPart", n.getLimitPart());
		node.put("ApybValue", n.getApybValue());
		node.put("Iszb", n.getISzb());
		node.put("ApManCount", n.getApManCount());

		node.put("Architecture_Name", n.getArchName());
		node.put("Gather_Name", n.getGatherName());
		node.put("Area_Name", n.getAreaName());
		// jo.put("USEAMXZ", n);
		// jo.put("USEAMFX", n);
		// jo.put("USEAMYJZX", n);
		node.put("PartmentName", n.getPartmentName());
		node.put("meteStyle_Name", n.getMeterStyleName());
		node.put("isRecycle", n.getIsRecycle());

		return node;
	}

	private List<List<String>> importAmToDB(File amFile,
			HttpServletRequest request) throws SQLException
	{
		AmmeterDao ad = new AmmeterDao();
		WatermeterDao wd = new WatermeterDao();
		DictionaryValueDao dictValueDao = new DictionaryValueDao();
		MeterStyleDao msDao = new MeterStyleDao();
		AreaDao areaDao = new AreaDao();
		DataSiteDao dataSiteDao = new DataSiteDao();
		ArchitectureDao archDao = new ArchitectureDao();
		GatherDao gatherDao = new GatherDao();
		DictionaryValueDao dvd = new DictionaryValueDao();
		List<List<String>> failList = new LinkedList<List<String>>();
		String[] theTitles =
		{ "用户编号", "用户名称", "用户联系电话", "所属建筑","电表名称", "表计类型", "用电类型", "表计资产号",
				"表计厂家", "电表485地址", "网关地址", "电表密码", "能耗编码","所属部门","价格","重点用户","是否总表","是否纳入计量","修正量","房间人数","房间容量","总表地址", "错误信息" };
		List<String> firstLine = new LinkedList<String>();

		// 将标题加进去
		for (String title : theTitles)
		{
			firstLine.add(title);
		}

		failList.add(firstLine);

		Map<String, Architecture> archMap = archDao.getMapDataByArchName();
		Map<String, MeterStyle> msMap = msDao.getMapByName();
		Map<String, DictionaryValueModel> dvMap = dictValueDao.getMapByValue(6);
		Map<String, Gather> gatherMap = gatherDao.getMapByAdddr();
		List<DictionaryValueModel> styleList = dvd
				.getDictionaryValueByDictionaryID(3);
		DataSite dataSite = dataSiteDao.getFirstDataSite();
		Workbook book = null;
		try
		{
			book = Workbook.getWorkbook(amFile);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			boolean flag = true;
			String errorInfo = "";
			// 得到单元格
			for (int i = 1; i < sheet.getRows(); i++)
			{
				AmmeterModel am = new AmmeterModel();
				errorInfo = "";
				flag = true;
				if (sheet.getColumns() >= 22)
				{
					// 用户号
					am.setConsumerNum(sheet.getCell(0, i).getContents());

					// 用户名
					am.setConsumerName(sheet.getCell(1, i).getContents());

					// 用户电话
					am.setConsumerPhone(sheet.getCell(2, i).getContents());

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
								if (styleList != null && styleList.size() > 0)
								{
									theArch.setStyle(styleList.get(0)
											.getDictionaryValueNum());
								}
								if (DataValidation.isNumber(floor))
								{
									// 楼层
									theArch.setStorey(Integer.parseInt(floor));
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
							theArch = archMap.get(archName);
							am.setArchitectureID(archMap.get(archName).getId());
							am.setAreaID(archMap.get(archName).getAreaID());
						} else
						{
							flag = false;
							errorInfo += "电表所属建筑名称不得为空;";
						}

						if (theArch == null)
							theArch = archDao.getFirstArch();

						if (DataValidation.isNumber(floor))
						{
							// 楼层
							am.setFloor(Integer.parseInt(floor));
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
					am.setAmmeterName(amName);

					String meterStyleName = sheet.getCell(5, i).getContents();

					// 表计类型
					if (msMap.get(meterStyleName) == null)
					{
						MeterStyle meter = new MeterStyle();
						meter.setMeterStyleName(meterStyleName);
						meter.setMeterStyleType("电表");

						msDao.add(meter);

						msMap = msDao.getMapByName();
					}
					am.setMeteStyleID(msMap.get(meterStyleName)
							.getMeterStyleID());

					am.setBeilv(msDao.getBeiLvByMSID(msMap.get(meterStyleName)
							.getMeterStyleID()));

					String usStyName = sheet.getCell(6, i).getContents();
					if (dvMap.get(usStyName) != null)
					{
						// 用电类型
						am.setUseAmStyle(Integer.parseInt(dvMap.get(usStyName)
								.getDictionaryValueNum()));
					}

					// 资产号
					am.setAssetNo(sheet.getCell(7, i).getContents());

					// 生产厂家
					am.setMaker(sheet.getCell(8, i).getContents());

					// 表计485地址
					String amAddr = sheet.getCell(9, i).getContents();
					amAddr=padLeft(amAddr, 12);
					am.setAmmeterAddress485(amAddr);

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
							gath.setGatherPw("11111111");

							if (dataSite != null)
							{
								gath.setDatasiteID(dataSite.getDatasiteID());
							}
							if (theArch != null)
							{
								gath.setInstallAddress(theArch.getName());
								gath.setAreaID(theArch.getAreaID());
								gath.setArchitectureID(theArch.getId());
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
						am.setGatherID(gatherMap.get(gatherAddr).getGatherID());

						// 得到最大测量点
						am.setAmmeterPoint(maxPoint);
					} else
					{
						flag = false;
						errorInfo += "网关地址不得为空;";
					}

					// 密码
					am.setAmmeterPassword(sheet.getCell(11, i).getContents());

					// 能耗编码
					am.setAmmeterNum(padLeft((sheet.getCell(12, i).getContents()),16));
					
					//所属部门
					if(sheet.getCell(13, i).getContents()!=null){
						String PartmentInfo = sheet.getCell(13, i).getContents();
						DepartmentDao dpDao=new DepartmentDao();
						if(PartmentInfo!=null&&PartmentInfo!=""){
							int groupid=dpDao.queryIdByNum(PartmentInfo);
							am.setPartment(groupid);
						}
					}
					AmmeterDao amDao=new AmmeterDao();
					//价格
					if(sheet.getCell(14, i).getContents()!=null&&sheet.getCell(14, i).getContents()!=""){
						String PriceInfo = sheet.getCell(14, i).getContents();
						
						am.setPriceID(amDao.queryPriceIdByPriceName(PriceInfo));
					}
//					//用电性质
//					if(sheet.getCell(15, i).getContents()!=null&&sheet.getCell(15, i).getContents()!=""){
//						String UseAmStyleInfo = sheet.getCell(15, i).getContents();
//						am.setUseAmStyle(amDao.queryUseAmStyleIdByStyleName(UseAmStyleInfo));
//					}
					//重点用户
					if(sheet.getCell(15, i).getContents()!=null&&sheet.getCell(15, i).getContents()!="")
						am.setIsImportantUser(Integer.parseInt(sheet.getCell(15, i).getContents()));
					
					//是否总表
					if(sheet.getCell(16, i).getContents()!=null&&sheet.getCell(16, i).getContents()!="")
						am.setIsCountMeter(Integer.parseInt(sheet.getCell(16, i).getContents()));
					
					//是否纳入计量
					if(sheet.getCell(17, i).getContents()!=null&&sheet.getCell(17, i).getContents()!="")
						am.setIsComputation(Integer.parseInt(sheet.getCell(17, i).getContents()));
					
					//修正量
					if(sheet.getCell(18, i).getContents()!=null&&sheet.getCell(18, i).getContents()!=""){
						am.setXiuzheng(Float.parseFloat(sheet.getCell(18, i).getContents()));
					}
					//房间人数
					if(sheet.getCell(19, i).getContents()!=null&&sheet.getCell(19, i).getContents()!="")
						am.setApManCount((int)(Float.parseFloat(sheet.getCell(19, i).getContents())));
					//房间容量
					if(sheet.getCell(20, i).getContents()!=null&&sheet.getCell(20, i).getContents()!="")
						am.setRoomManCount((int)(Float.parseFloat(sheet.getCell(20, i).getContents())));
					//总表地址
					if(sheet.getCell(21, i).getContents()!=null&&sheet.getCell(21, i).getContents()!=""){
						am.setParentID(amDao.queryIdByAddr(sheet.getCell(21, i).getContents()));
					}
					
//						am.setRoomManCount(Integer.parseInt(sheet.getCell(20, i).getContents()));
					
					
				} else
				{
					flag = false;
					errorInfo += "该行数据列数少于模板列数;";
				}

				if (ad.checkAddRepeat(am))
				{
					flag = false;
					errorInfo += "同一网关下不允许重复的测量点或通讯地址";
				}

				if (flag)
				{
					ad.insert(am);
				} else
				{
					List<String> info = new LinkedList<String>();
					for (int j = 0; j < 22; j++)
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

		amFile.delete();

		return failList;
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
