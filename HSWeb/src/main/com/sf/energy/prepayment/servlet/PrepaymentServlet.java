package com.sf.energy.prepayment.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.ZoneView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.NewBeanInstanceStrategy;

import com.lowagie.text.pdf.PRAcroForm;
import com.sf.energy.prepayment.dao.AmMeterTuiFangDao;
import com.sf.energy.prepayment.dao.ApCardDZDao;
import com.sf.energy.prepayment.dao.AutoUpDataSaver;
import com.sf.energy.prepayment.dao.BatchOprDao;
import com.sf.energy.prepayment.dao.CMArchDao;
import com.sf.energy.prepayment.dao.CMAreaDao;
import com.sf.energy.prepayment.dao.CMMeterDao;
import com.sf.energy.prepayment.dao.CMMsendCommand;
import com.sf.energy.prepayment.dao.PowerRankingsDao;
import com.sf.energy.prepayment.dao.SaleDetailsDao;
import com.sf.energy.prepayment.dao.VolumeSetDao;
import com.sf.energy.prepayment.dao.YuGouDetailsDao;
import com.sf.energy.prepayment.model.CMArchModel;
import com.sf.energy.prepayment.model.CMAreaModel;
import com.sf.energy.prepayment.model.PowerRankingsModel;
import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.prepayment.model.VolumeSetModel;
import com.sf.energy.prepayment.model.XMLCoder;
import com.sf.energy.prepayment.model.YuGouDetailsModel;
import com.sf.energy.prepayment.service.SendingXMLCode;
import com.sf.energy.prepayment.serviceImpl.SendingAPCode;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.ExportHelper;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;
import com.sf.energy.water.manager.current.dao.CurrentMeasureDao;

public class PrepaymentServlet extends HttpServlet
{

//	private ReentrantLock lock = new ReentrantLock();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(req, resp);
		} catch (SQLException | ParseException e)
		{

			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String method = req.getParameter("method");
		// System.out.println("method:" + method);
		if("ReJiesuan_click".equals(method))
			ReJiesuan_click(req, resp);
		if("importWaterData".equals(method))
			importWaterData(req, resp);
		if("TuifangConfirm".equals(method))
			TuifangConfirm(req, resp);
		if("getTuifangInfo".equals(method))
			getTuifangInfo(req, resp);
		if("importSaleInfo".equals(method))
			importSaleInfo(req, resp);
		if ("gd_out_btn_click".equals(method))
			gd_out_btn_click(req, resp);
		if ("check_info_gd".equals(method))
			check_info_gd(req, resp);
		if ("delete_gd_info".equals(method))
			delete_gd_info(req, resp);
		if ("get_Info_gd".equals(method))
			get_Info_gd(req, resp);
		if ("yuGou".equals(method))
			yuGou(req, resp);
		// 集中监测
		if ("getAllSchoolInfoForJZJC".equals(method))
			getAllSchoolInfoForJZJC(req, resp);

		if ("getAllAreaInfoForJZJC".equals(method))
			getAllAreaInfoForJZJC(req, resp);

		if ("getAllArchtectureInfoForJZJC".equals(method))
			getAllArchtectureInfoForJZJC(req, resp);

		if ("getAll_Floor_InfoForJZJC".equals(method))
			getAll_Floor_InfoForJZJC(req, resp);

		if ("getAll_Ammeter_InfoForJZJC".equals(method))
			getAll_Ammeter_InfoForJZJC(req, resp);

		if ("getAll_TwoMonth_ChartInfo".equals(method))
			getAll_TwoMonth_ChartInfo(req, resp);

		if ("personAdjust".equals(method))
			personAdjust(req, resp);
		
		if ("getReadEXFZ".equals(method))
			getReadEXFZ(req, resp);
		
		if ("getReadStateNum".equals(method))
			getReadStateNum(req, resp);

		if ("getReadAmmeter".equals(method))
			getReadAmmeter(req, resp);

		if ("getAll_SaleEnergy_InfoForJZJC".equals(method))
			getAll_SaleEnergy_InfoForJZJC(req, resp);

		if ("getAll_KaiHu_InfoForJZJC".equals(method))
			getAll_KaiHu_InfoForJZJC(req, resp);

		if ("heZha".equals(method))
			heZha(req, resp);

		if ("fenZha".equals(method))
			fenZha(req, resp);

		if ("kaiHu".equals(method))
			kaiHu(req, resp);

		if ("xiaFaDianLiang".equals(method))
			xiaFaDianLiang(req, resp);

		if ("kaiHuHouSyvalue".equals(method))
			kaiHuHouSyvalue(req, resp);

		if ("xiaoHuTuiDian".equals(method))
			xiaoHuTuiDian(req, resp);

		if ("xiaoHu".equals(method))
			xiaoHu(req, resp);
		if ("xiaoHuHouSYvalue".equals(method))
			xiaoHuHouSYvalue(req, resp);

		// 用电排名
		if ("get_school_Info_PR".equals(method))
			get_school_Info_PR(req, resp);
		if ("get_Area_Info_PR".equals(method))
			get_Area_Info_PR(req, resp);
		if ("get_Arch_Info_PR".equals(method))
			get_Arch_Info_PR(req, resp);
		if ("get_Floor_Info_PR".equals(method))
			get_Floor_Info_PR(req, resp);

		if ("get_Amm_Info_PR".equals(method))
			get_Amm_Info_PR(req, resp);

		// 售电明细
		if ("check_info_SD".equals(method))
			check_info_SD(req, resp);
		if ("delete_SD_info".equals(method))
			delete_SD_info(req, resp);
		if ("get_Info_SaleDetail".equals(method))
			get_Info_SaleDetail(req, resp);

		if ("SD_out_btn_click".equals(method))
			SD_out_btn_click(req, resp);
		if ("SDWater_out_btn_click".equals(method))
			SDWater_out_btn_click(req, resp);
		if ("UpLoadInputFile_click".equals(method))
			UpLoadInputFile_click(req, resp);
		if ("All_Jiesuan_click".equals(method))
			All_Jiesuan_click(req, resp);
		if ("Jiesuan_click".equals(method))
			Jiesuan_click(req, resp);
		
		//售水明细
		if ("check_info_SDWater".equals(method))
			check_info_SDWater(req, resp);

		// 批量操作
		if ("get_info_VS".equals(method))
			get_info_VS(req, resp);
		/*if ("tongYiYueBu".equals(method))
			tongYiYueBu(req, resp);
		if ("get_info_VS".equals(method))
			linShiTiaoJi(req, resp);
		if ("linShiTiaoJi".equals(method))
			qiTaCaoZuo(req, resp);*/
		if ("VS_out_btn_click".equals(method))
			VS_out_btn_click(req, resp);
		// 售电
		if ("saleEnergy".equals(method))
			saleEnergy(req, resp);
		if ("xiaFaGouDian".equals(method))
			xiaFaGouDian(req, resp);
		if ("readGouDianHou".equals(method))
			readGouDianHou(req, resp);

		// 补发
		if ("bufa".equals(method))
			bufa(req, resp);
		// 退购
		if ("tuigou".equals(method))
			tuigou(req, resp);

		if ("getBrotherAmmInfo".equals(method))
			getBrotherAmmInfo(req, resp);

		if ("getBrotherWmmInfo".equals(method))
			getBrotherWmmInfo(req, resp);
		//表计类型
		if("getMeterTypeInfo".equals(method)){
			getMeterTypeInfo(req, resp);
		}
		//更改表计类型方法
		if("updateMeterTypeById".equals(method)){
			updateMeterTypeById(req, resp);
		}
	}
	
	private void TuifangConfirm(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String AmMeter_ID = req.getParameter("Amm");
		String detail = req.getParameter("detail");
		String tui_money = req.getParameter("tui_money");
		AmMeterTuiFangDao dao = new AmMeterTuiFangDao();
		boolean b = dao.insertTuiFangInfo(AmMeter_ID,detail,tui_money);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		if(b){
			jo.put("isSuccess", "1");
		}else{
			jo.put("isSuccess", "0");
		}
		json.add(jo);
		
		resp.setContentType("application/x-json");
		PrintWriter pw = resp.getWriter();
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}
	
	private void getTuifangInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, NumberFormatException, ParseException
	{
		String AmMeter_ID = req.getParameter("Amm");
		String startDateTime = req.getParameter("startDateTime") + ":00";
		String endDateTime = req.getParameter("endDateTime") + ":00";
		CMMeterDao dao = new CMMeterDao();
		JSONArray json = dao.getTuifangDetail(AmMeter_ID, startDateTime, endDateTime);
		resp.setContentType("application/x-json");
		PrintWriter pw = resp.getWriter();
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}

	private void updateMeterTypeById(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{
		String MeteStyle_ID = req.getParameter("MeteStyle_ID");
		String AmMeter_ID = req.getParameter("Amm");
		String sql = "update ammeter set METESTYLE_ID=" + MeteStyle_ID + " where AMMETER_ID=" + AmMeter_ID;
		boolean b = (new CMMeterDao()).UpdateSql(sql);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		if(b){
			jo.put("isSuccess", "1");
			
			String info = (new CMMeterDao()).getAmMeterByID(AmMeter_ID);
			String userLoginName = (String) req.getSession().getAttribute(
					"userName");
			OperationLogInterface log = new OperationLogImplement();
			// 写入日志
			log.writeLog(userLoginName, "集中监测", "修改表计类型,"+info);
		}else{
			jo.put("isSuccess", "0");
		}
		json.add(jo);
		
		resp.setContentType("application/x-json");
		PrintWriter pw = resp.getWriter();
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}

	private void getMeterTypeInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		CMMeterDao dao = new CMMeterDao();
		JSONArray json = dao.getMeterTypeInfo();

		resp.setContentType("application/x-json");
		PrintWriter pw = resp.getWriter();
		// System.out.println(json.toString());
		pw.println(json.toString());
		pw.flush();
		pw.close();
		
	}
	
	private void importWaterData(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, NumberFormatException, ParseException
	{

		ExportHelper dh = ExportHelper.getInstance();
		String saveFolder = "img/archImg";
		//List<List<String>> result = null;
		String result = "";
		//theWaterDataFile是file选择框的name名称
		File file = dh.getImportFile(req, resp, saveFolder, "theWaterDataFile");

		JSONArray json = new JSONArray();

		JSONObject jo = new JSONObject();

		if (file != null)
		{
			if (file.getAbsolutePath().endsWith(".xls"))
			{
				result = importWaterDataToDB(file, req);
//				System.out.println(result);
				if("".equals(result)){
					jo.put("type", "success");
				}else{
					jo.put("type", "fail");
					jo.put("info", result);
				}

			}else{
				jo.put("type", "fail");
				jo.put("info", "不接受.xls以外的文件格式");
			}

		}else {
			jo.put("type", "fail");
			jo.put("info", "操作失败");
		}
		
		json.add(jo);
		PrintWriter pw = resp.getWriter();
		pw.println(json.toString());
		pw.flush();
		pw.close();
		
	
		
	}

	private void importSaleInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, NumberFormatException, ParseException
	{
		ExportHelper dh = ExportHelper.getInstance();
		String saveFolder = "img/archImg";
		//List<List<String>> result = null;
		String result = "";
		File file = dh.getImportFile(req, resp, saveFolder, "theSaleInfoFile");

		JSONArray json = new JSONArray();

		JSONObject jo = new JSONObject();

		if (file != null)
		{
			if (file.getAbsolutePath().endsWith(".xls"))
			{
				result = importSaleInfoToDB(file, req);
//				System.out.println(result);
				if("".equals(result)){
					jo.put("type", "success");
				}else{
					jo.put("type", "fail");
					jo.put("info", result);
				}

			}else{
				jo.put("type", "fail");
				jo.put("info", "不接受.xls以外的文件格式");
			}

		}else {
			jo.put("type", "fail");
			jo.put("info", "操作失败");
		}
		
		json.add(jo);
		PrintWriter pw = resp.getWriter();
		pw.println(json.toString());
		pw.flush();
		pw.close();
		
	}
	
	private String importWaterDataToDB(File cardFile, HttpServletRequest request) throws SQLException, NumberFormatException, ParseException
	{

		int userID = (int) request.getSession().getAttribute("userId");//获取用户ID

		Workbook book = null;
        String result = "";
		
		try{
			book = Workbook.getWorkbook(cardFile);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			
			int count = 0;
			int EroorTimes = 0;//错误次数
			int noNum = 0;
            
            if(sheet.getColumns()>=4){
            	for (int i = 1; i < sheet.getRows(); i++)
            	{
            		count++;
            		//1：房间，2：表地址，3：起始示数，4：终止示数
					String WaterMeter_485Address = sheet.getCell(1, i).getContents();
					String Value = sheet.getCell(3, i).getContents();
					if(!"".equals(Value)){
						DecimalFormat df = new DecimalFormat("0.00");
						
						CMMeterDao cmmd = new CMMeterDao();
						int WaMeterID = cmmd.getWaterMeterIdByAddress(WaterMeter_485Address);
						if(WaMeterID==0){
							EroorTimes++;
							continue;
						}
						
						boolean b = cmmd.insertWaterData(WaMeterID,df.format(df.parse(Value)));
						if(!b){
							EroorTimes++;
						}
					}else{
						noNum++;
					}	
					
				}
            	
            	result = "总共有"+count+"条记录，导入失败"+EroorTimes+"条,没有数据"+noNum+"条";
            }
			
			
		} catch (BiffException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	
		
	}
	
	private String importSaleInfoToDB(File cardFile, HttpServletRequest request) throws SQLException, NumberFormatException, ParseException
	{
		int userID = (int) request.getSession().getAttribute("userId");//获取用户ID

		Workbook book = null;
        String result = "";
		
		try{
			book = Workbook.getWorkbook(cardFile);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			
			int count = 0;
			int EroorTimes = 0;//错误次数
            
            if(sheet.getColumns()>=3){
            	for (int i = 1; i < sheet.getRows(); i++)
            	{
            		count++;
					String AmMeter_485Address = sheet.getCell(1, i).getContents();
					String Money = sheet.getCell(2, i).getContents();
					DecimalFormat df = new DecimalFormat("0.00");
					
					CMMeterDao cmmd = new CMMeterDao();
					int AmMeterID = cmmd.getAmMeterIdByAddress(AmMeter_485Address);
					if(AmMeterID==0){
						EroorTimes++;
						continue;
					}
					float price = cmmd.getPriceByAmmId(AmMeterID);
					if(price==0){
						EroorTimes++;
						continue;
					}
					CMMsendCommand csc = new CMMsendCommand();
					csc.getPara(AmMeterID);
					String Gross = df.format(df.parse(Float.parseFloat(Money)/price + ""));
					

					String SaleInfoNum = "";
					SaleInfoNum = cmmd.GetNextChareNum();
					boolean isS = false;
					
					// 写入数据库
					// 添加售电单，SaleInfoNum必须是唯一的如果主键重复会异常，则重新生成单号添加直到添加成功
					while (!isS)
					{
						/*try
						{*/
							String sql3 = "insert into APSaleInfo(SaleInfoNum,Ammeter_ID,AmMeter_Name,AmMeter_485Address,Kind,Users_ID,BuyTime,Status,Price,TheGross,TheMoney)"
									+ "values('"
									+ SaleInfoNum
									+ "',"
									+ AmMeterID
									+ ",'"
									+ csc.getAmMeter_Name()
									+ "','"
									+ csc.getAmMeter_485Address()
									+ "',"
									+ 3
									+ ","
									+ userID
									+ ",to_date(to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss'),0,"
									+ price
									+ ","
									+ Gross
									+ ","
									+ Money + ")";
							
							Connection conn = null;
							PreparedStatement ps = null;
							try
							{
								conn = ConnDB.getConnection();
								ps = conn.prepareStatement(sql3);
								int bb = ps.executeUpdate();
								isS = true;
								if(bb!=1){
									EroorTimes++;
								}
								break;
							} catch (SQLException e)
							{
								EroorTimes++;
								SaleInfoNum = cmmd.GetNextChareNum();
								e.printStackTrace();
							} finally
							{
								ConnDB.release(conn, ps);
							}
						
					}
					
					
				}
            	
            	result = "总共有"+count+"条记录，导入失败"+EroorTimes+"条";
            }
			
			
		} catch (BiffException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	private void get_Info_SaleDetail(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");
		String SelectFloorID = req.getParameter("SelectFloorID");
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		String selectInfo = "";
		String queryMsg = "";

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		if ("0".equals(SelectTreeFlag))
		{// 全校
			selectInfo += "all|";
		} else if ("1".equals(SelectTreeFlag))
		{// 区域
			selectInfo += "area|" + SelectTreeID;
		} else if ("2".equals(SelectTreeFlag))
		{// 建筑
			selectInfo += "arch|" + SelectTreeID;
		} else if ("3".equals(SelectTreeFlag))
		{// 楼层
			selectInfo += "floor|" + SelectTreeID + "|" + SelectFloorID;
		} else if ("4".equals(SelectTreeFlag))
		{// 电表
			selectInfo += "meter|" + SelectTreeID;
		}

		SaleDetailsDao sdd = new SaleDetailsDao();
		ArrayList<SaleDetailsModel> list = new ArrayList<SaleDetailsModel>();
		list = sdd.queryInfo(thePageCount, thePageIndex, selectInfo, queryMsg, true, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", sdd.getTotalCount());
		json.add(jo);

		for (SaleDetailsModel sdm : list)
		{
			jo = new JSONObject();
			CMMeterDao dao = new CMMeterDao();
			String meterPotocol = dao.getMeterPotocol(sdm.getAmMeter_ID());
			jo.put("meterPotocol", meterPotocol);
			jo.put("amMeterID", sdm.getAmMeter_ID());
			jo.put("saleInfoNum", sdm.getSALEINFONUM());
			jo.put("orderNo", sdm.getORDERNO());
			jo.put("studentID", sdm.getSTUDENTID());
			jo.put("sign", sdm.getSign());
			jo.put("archName", sdm.getArchitecture_Name());
			jo.put("floorName", sdm.getFloorName());
			jo.put("amMeterName", sdm.getAmMeter_Name());
			jo.put("kindName", sdm.getKindName());
//			jo.put("Price_ID", sdm.getPrice_ID());
			jo.put("Price_Name", sdm.getPrice_Name());
			jo.put("status", sdm.getStatus());
			jo.put("buyTime", sdm.getBuyTime());
			jo.put("price", sdm.getPrice());
			jo.put("theGross", sdm.getTheGross());
			jo.put("theMoney", sdm.getTheMoney());
			jo.put("sendTime", sdm.getSendTime());
			jo.put("QSYValue", sdm.getQSYValue());
			jo.put("HSYValue", sdm.getHSYValue());
			jo.put("userName", sdm.getUsers_Name());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 获得水表同房间的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws SQLException
	 */
	private void getBrotherWmmInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{
		String wmmID = req.getParameter("Amm");
		CurrentMeasureDao dao = new CurrentMeasureDao();
		JSONArray json = dao.getBrotherMeter(wmmID);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 获得电表同房间的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws SQLException
	 */
	private void getBrotherAmmInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{
		String ammeterId = req.getParameter("Amm");
		CMMeterDao dao = new CMMeterDao();
		JSONArray json = dao.getBrotherMeter(ammeterId);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void check_info_gd(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");
		String SelectFloorID = req.getParameter("SelectFloorID");
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		String startDateTime = req.getParameter("startDateTime");
		String endDateTime = req.getParameter("endDateTime");
		String selectInfo = "";
		String queryMsg = startDateTime + "|" + endDateTime;

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		if ("0".equals(SelectTreeFlag))
		{// 全校
			selectInfo += "all|";
		} else if ("1".equals(SelectTreeFlag))
		{// 区域
			selectInfo += "area|" + SelectTreeID;
		} else if ("2".equals(SelectTreeFlag))
		{// 建筑
			selectInfo += "arch|" + SelectTreeID;
		} else if ("3".equals(SelectTreeFlag))
		{// 楼层
			selectInfo += "floor|" + SelectTreeID + "|" + SelectFloorID;
		} else if ("4".equals(SelectTreeFlag))
		{// 电表
			selectInfo += "meter|" + SelectTreeID;
		}

		YuGouDetailsDao dao = new YuGouDetailsDao();
		ArrayList<YuGouDetailsModel> list = new ArrayList<YuGouDetailsModel>();
		list = dao.queryInfo(thePageCount, thePageIndex, selectInfo, queryMsg, true, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);

		for (YuGouDetailsModel ygdm : list)
		{
			jo = new JSONObject();
			jo.put("amMeterID", ygdm.getAmMeter_ID());
			jo.put("ammGDInfoNum", ygdm.getAMMGDINFONUM());
			jo.put("architecture_Name", ygdm.getArchitecture_Name());
			jo.put("floorName", ygdm.getFloorName());
			jo.put("amMeterName", ygdm.getAmMeter_Name());
			jo.put("readTime", ygdm.getReadTime());
			jo.put("buyTimes", ygdm.getBuyTimes());

			jo.put("thisPurchaseMoney", ygdm.getThisPurchaseMoney());
			jo.put("lastRemainMoney", ygdm.getLastRemainMoney());
			jo.put("thisRemainMoney", ygdm.getThisRemainMoney());
			jo.put("userName", ygdm.getUsers_Name());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void delete_gd_info(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String theID = req.getParameter("theID");
		YuGouDetailsDao dao = new YuGouDetailsDao();
		int result = dao.deleteInfo(theID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("result", result);
		json.add(jo);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
	}

	private void get_Info_gd(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");
		String SelectFloorID = req.getParameter("SelectFloorID");
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		String selectInfo = "";
		String queryMsg = "";

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		if ("0".equals(SelectTreeFlag))
		{// 全校
			selectInfo += "all|";
		} else if ("1".equals(SelectTreeFlag))
		{// 区域
			selectInfo += "area|" + SelectTreeID;
		} else if ("2".equals(SelectTreeFlag))
		{// 建筑
			selectInfo += "arch|" + SelectTreeID;
		} else if ("3".equals(SelectTreeFlag))
		{// 楼层
			selectInfo += "floor|" + SelectTreeID + "|" + SelectFloorID;
		} else if ("4".equals(SelectTreeFlag))
		{// 电表
			selectInfo += "meter|" + SelectTreeID;
		}

		YuGouDetailsDao dao = new YuGouDetailsDao();
		ArrayList<YuGouDetailsModel> list = new ArrayList<YuGouDetailsModel>();
		list = dao.queryInfo(thePageCount, thePageIndex, selectInfo, queryMsg, true, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);

		for (YuGouDetailsModel ygdm : list)
		{
			jo = new JSONObject();
			jo.put("amMeterID", ygdm.getAmMeter_ID());
			jo.put("ammGDInfoNum", ygdm.getAMMGDINFONUM());
			jo.put("architecture_Name", ygdm.getArchitecture_Name());
			jo.put("floorName", ygdm.getFloorName());
			jo.put("amMeterName", ygdm.getAmMeter_Name());
			jo.put("readTime", ygdm.getReadTime());
			jo.put("buyTimes", ygdm.getBuyTimes());

			jo.put("thisPurchaseMoney", ygdm.getThisPurchaseMoney());
			jo.put("lastRemainMoney", ygdm.getLastRemainMoney());
			jo.put("thisRemainMoney", ygdm.getThisRemainMoney());
			jo.put("userName", ygdm.getUsers_Name());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void tuigou(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		ReentrantLock lock = new ReentrantLock();
		int AmMeterID = Integer.parseInt(req.getParameter("amMeterID"));
		String SaleInfoNum = req.getParameter("saleInfoNum");
		int flag = 0;

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		CMMsendCommand csc = new CMMsendCommand();
		csc.getPara(AmMeterID);
		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = new XMLCoder();

		float QSYValue = 0;// 退电前剩余金额
		float theGross = 0;// 退购电量
		float theMoney = 0;// 退购金额 李嵘20150410
		float gross = 0;
		float money = 0;// 李嵘20150410
		float HSYValue = 0;// 退电后剩余金额
		String times = "";// 购电次数
		String SaleInfoTimes = "";
		String protocol = "1";// 默认97规约 李嵘20150410

		if ("0".equals(csc.getAmMeter_Stat()))
		{
			jo = new JSONObject();
			jo.put("isError", "5");
			jo.put("message", "设备不在线，暂时不能下发。");

			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} else
		{
			HttpSession session = req.getSession();
			String userID = "1";
			if (session.getAttribute("userName") != null)
			{
				userID = session.getAttribute("userName").toString();
			}

			String sql = "select SaleInfoNum,Times,Kind,QSYValue,HSYValue,BuyTime,SendTime,Status,Price,TheGross,TheMoney from APSaleInfo where SaleInfoNum='"
					+ SaleInfoNum + "'";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					if ("0".equals(rs.getString("Status")))
					{
						flag = 1;
						jo = new JSONObject();
						jo.put("isError", "3");
						jo.put("message", "售电单没有下发，不能退电。");

						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					}
					SaleInfoTimes = rs.getString("Times");
					times = rs.getString("Times");
					// System.out.println(times);
					theGross = rs.getFloat("TheGross");
					theMoney = rs.getFloat("TheMoney");// 李嵘20150410

				} else
				{
					flag = 1;
					jo = new JSONObject();
					jo.put("isError", "4");
					jo.put("message", "售电单不存在!");

					json.add(jo);
					resp.setContentType("application/x-json");

					PrintWriter out = resp.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				}

				if (flag == 1)
				{
					ConnDB.release(conn, ps,rs);
					return;
				}
				// 读退电前剩余电量(兼容97 07)
				xc = csc.readMeter(AmMeterID, userID);

				NodeList gwList=null;
				String task;
				try
				{
					lock.lock();
					String str = sxc.sendXMLToServer(xc);
					System.out.println("收到的回复是" + str);
					
					gwList = getNodeList(str, "GW");
					for (int m = 0; m < gwList.getLength(); m++)
					{
						Element tasknum = (Element) gwList.item(m);
						if (tasknum.hasAttribute("tasknum"))
						{
							task = tasknum.getAttribute("tasknum");
							if(task.equals(xc.getTaskNum())){
								NodeList list = null;
								String err = "";
								String errMsg = "读退电前剩余金额(电量)失败！";
								list = getNodeList(str, "commandback");

								Element commandBack = (Element) list.item(0);
								if (commandBack.hasAttribute("error"))
								{
									err = commandBack.getAttribute("error");
									// errMsg += commandBack.getAttribute("errormessage");
									// 失败
									if (err.equals("1"))
									{
										flag = 1;
										jo = new JSONObject();
										jo.put("isError", "1");
										jo.put("message", errMsg);
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										out.println(json.toString());
										out.flush();
										out.close();
									} else if (err.equals("0")) // 成功
									{
										DecimalFormat df = new DecimalFormat("0.##");
										NodeList result = commandBack.getElementsByTagName("result");
										for (int j = 0; j < result.getLength(); j++)
										{
											Element resultElement = (Element) result.item(j);
											String SYValue = "0.00";
											
											String dataid = "";
											String meterAddress = "";
											boolean isExecFlag = true;
											if(resultElement.hasAttribute("DataID")){
												dataid = resultElement.getAttribute("DataID");
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
													isExecFlag = false;
												}
											}else {
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if(!meterAddress.equals(xc.getMeterAddress())){
													isExecFlag = false;
												}
											}
											if(isExecFlag){
												if (resultElement.hasAttribute("SYValue"))// 97表
												{
													SYValue = resultElement.getAttribute("SYValue");
													times = resultElement.getAttribute("BuyTimes");
												} else
												// 07规约
												{
													protocol = "30";
													SYValue = resultElement.getAttribute("SYMoney");
												}
												SYValue = df.format(df.parse(SYValue));
												if ("-1".equals(SYValue))
												{
													flag = 1;
													jo = new JSONObject();
													jo.put("isError", "1");
													jo.put("message", "网关抄读失败！");
													json.add(jo);
												} else
												{
													if ("1".equals(protocol))// 97
													{
														QSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();
														gross = QSYValue - theGross;
													} else
													// 07
													{
														QSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();
														money = QSYValue - theMoney;
													}
												}
											}else{
												jo = new JSONObject();
												jo.put("isError", "7");
												jo.put("message", "返回表地址或命令码错误！");

												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}
											
										}
									}
								}
							}else{
								jo = new JSONObject();
								jo.put("isError", "6");
								jo.put("message", "任务编号错误！");
								json.add(jo);
								resp.setContentType("application/x-json");

								PrintWriter out = resp.getWriter();
								out.println(json.toString());
								out.flush();
								out.close();
							}
						}
					}
					
					
				} catch (SocketTimeoutException e)
				{
					flag = 1;
					jo = new JSONObject();
					jo.put("isError", "2");
					jo.put("message", "命令超时重发！");

					json.add(jo);
					resp.setContentType("application/x-json");

					PrintWriter out = resp.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				} finally
				{
					lock.unlock();
				}
				if (flag == 1)
				{
					ConnDB.release(conn, ps,rs);
					return;
				}

				// 退电   07直接传购电单里面的购电次数，如果退前面购买的，他会将当前的购电单退掉
				if ("30".equals(protocol))// 07规约要
				{
					xc = csc.readMeter07(AmMeterID, userID, "07010202");// 上次购电后总购电次数

					try
					{
						lock.lock();
						String str = sxc.sendXMLToServer(xc);
						System.out.println("收到的回复是" + str);
						
						gwList = getNodeList(str, "GW");
						for (int m = 0; m < gwList.getLength(); m++)
						{
							Element tasknum = (Element) gwList.item(m);
							if (tasknum.hasAttribute("tasknum"))
							{
								task = tasknum.getAttribute("tasknum");
								if(task.equals(xc.getTaskNum())){
									NodeList list = null;
									String err = "";
									String errMsg = "读退电前剩余金额(电量)失败！";
									list = getNodeList(str, "commandback");

									Element commandBack = (Element) list.item(0);
									if (commandBack.hasAttribute("error"))
									{
										err = commandBack.getAttribute("error");
										// errMsg += commandBack.getAttribute("errormessage");
										// 失败
										if (err.equals("1"))
										{
											flag = 1;
											jo = new JSONObject();
											jo.put("isError", "1");
											jo.put("message", errMsg);
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											out.println(json.toString());
											out.flush();
											out.close();
										} else if (err.equals("0")) // 成功
										{
											DecimalFormat df = new DecimalFormat("0.##");
											NodeList result = commandBack.getElementsByTagName("result");
											for (int j = 0; j < result.getLength(); j++)
											{
												Element resultElement = (Element) result.item(j);
												
												String dataid = "";
												String meterAddress = "";
												boolean isExecFlag = true;
												if(resultElement.hasAttribute("DataID")){
													dataid = resultElement.getAttribute("DataID");
													if(resultElement.hasAttribute("Meter_Address")){
														meterAddress = resultElement.getAttribute("Meter_Address");
													}
													if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
														isExecFlag = false;
													}
												}else {
													if(resultElement.hasAttribute("Meter_Address")){
														meterAddress = resultElement.getAttribute("Meter_Address");
													}
													if(!meterAddress.equals(xc.getMeterAddress())){
														isExecFlag = false;
													}
												}
												if(isExecFlag){
													times = resultElement.getAttribute("BuyTimes");
													//判断售点单的购电次数和表里面的购电次数是否一样，07表只能退最后一次的
													if(!SaleInfoTimes.equals(times)){
														jo = new JSONObject();
														jo.put("isError", "8");
														jo.put("message", "该售电单不是最后一次购电，不能退购！");

														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														out.println(json.toString());
														out.flush();
														out.close();
													}
												}else{
													jo = new JSONObject();
													jo.put("isError", "7");
													jo.put("message", "返回表地址或命令码错误！");

													json.add(jo);
													resp.setContentType("application/x-json");

													PrintWriter out = resp.getWriter();
													out.println(json.toString());
													out.flush();
													out.close();
												}
												// System.out.println(times+"  "+resultElement.getAttribute("BuyTimes"));
												// if ((Integer.parseInt(times) + 1) !=
												// Integer.parseInt(resultElement.getAttribute("BuyTimes")))
												// {
												// jo = new JSONObject();
												// jo.put("isError", "1");
												// jo.put("message", "该电表只允许退购上一次电量");
												// json.add(jo);
												// resp.setContentType("application/x-json");
												//
												// PrintWriter out = resp.getWriter();
												// out.println(json.toString());
												// out.flush();
												// out.close();
												// return;
												// } else
												// {
												
												// }
											}
										}
									}
								}else{
									jo = new JSONObject();
									jo.put("isError", "6");
									jo.put("message", "任务编号错误！");

									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								}
							}
						}	
					} catch (SocketTimeoutException e)
					{
						flag = 1;
						jo = new JSONObject();
						jo.put("isError", "2");
						jo.put("message", "命令超时重发！");

						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} finally
					{
						lock.unlock();
					}
					if (flag == 1)
					{
						ConnDB.release(conn, ps,rs);
						return;
					}
				}

				xc = csc.xiaoHuTuiDian(AmMeterID, times, userID);
				try
				{
					lock.lock();
					String str = sxc.sendXMLToServer(xc);
					System.out.println("收到的回复是" + str);
					
					gwList = getNodeList(str, "GW");
					for (int m = 0; m < gwList.getLength(); m++)
					{
						Element tasknum = (Element) gwList.item(m);
						if (tasknum.hasAttribute("tasknum"))
						{
							task = tasknum.getAttribute("tasknum");
							if(task.equals(xc.getTaskNum())){
								NodeList list = null;
								String err = "";
								String errMsg = "退电失败！";
								list = getNodeList(str, "commandback");

								Element commandBack = (Element) list.item(0);
								if (commandBack.hasAttribute("error"))
								{
									err = commandBack.getAttribute("error");
									errMsg += commandBack.getAttribute("errormessage");
									// 失败
									if (err.equals("1"))
									{
										flag = 1;
										jo = new JSONObject();
										jo.put("isError", "1");
										jo.put("message", errMsg);
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										out.println(json.toString());
										out.flush();
										out.close();
									}else if(err.equals("0")){
										NodeList result = commandBack.getElementsByTagName("result");
										for (int j = 0; j < result.getLength(); j++)
										{
											Element resultElement = (Element) result.item(j);
											
											String dataid = "";
											String meterAddress = "";
											boolean isExecFlag = true;
											if(resultElement.hasAttribute("DataID")){
												dataid = resultElement.getAttribute("DataID");
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
													isExecFlag = false;
												}
											}else {
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if(!meterAddress.equals(xc.getMeterAddress())){
													isExecFlag = false;
												}
											}
											if(!isExecFlag){
												jo = new JSONObject();
												jo.put("isError", "7");
												jo.put("message", "返回表地址或命令码错误！");
												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}
										}
									}
								}
							}else{
								jo = new JSONObject();
								jo.put("isError", "6");
								jo.put("message", "任务编号错误！");
								json.add(jo);
								resp.setContentType("application/x-json");

								PrintWriter out = resp.getWriter();
								out.println(json.toString());
								out.flush();
								out.close();
							}
						}
					}
				} catch (SocketTimeoutException e)
				{
					flag = 1;
					jo = new JSONObject();
					jo.put("isError", "2");
					jo.put("message", "命令超时重发！");

					json.add(jo);
					resp.setContentType("application/x-json");

					PrintWriter out = resp.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				} finally
				{
					lock.unlock();
				}

				if (flag == 1)
				{
					ConnDB.release(conn, ps,rs);
					return;
				}

				// 启动预付费
				if("1".equals(protocol)){
					xc = csc.kaiHuCommand(AmMeterID, userID);
					try
					{
						lock.lock();
						String str = sxc.sendXMLToServer(xc);
						System.out.println("收到的回复是" + str);
						
						gwList = getNodeList(str, "GW");
						for (int m = 0; m < gwList.getLength(); m++)
						{
							Element tasknum = (Element) gwList.item(m);
							if (tasknum.hasAttribute("tasknum"))
							{
								task = tasknum.getAttribute("tasknum");
								if(task.equals(xc.getTaskNum())){
									NodeList list = null;
									String err = "";
									String errMsg = "启动预付费失败！";
									list = getNodeList(str, "commandback");

									Element commandBack = (Element) list.item(0);
									if (commandBack.hasAttribute("error"))
									{
										err = commandBack.getAttribute("error");
										errMsg += commandBack.getAttribute("errormessage");
										// 失败
										if (err.equals("1"))
										{
											flag = 1;
											jo = new JSONObject();
											jo.put("isError", "1");
											jo.put("message", errMsg);
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											out.println(json.toString());
											out.flush();
											out.close();
										} else if (err.equals("0")) // 成功
										{
											NodeList result = commandBack.getElementsByTagName("result");
											for (int j = 0; j < result.getLength(); j++)
											{
												Element resultElement = (Element) result.item(j);
												
												String dataid = "";
												String meterAddress = "";
												boolean isExecFlag = true;
												if(resultElement.hasAttribute("DataID")){
													dataid = resultElement.getAttribute("DataID");
													if(resultElement.hasAttribute("Meter_Address")){
														meterAddress = resultElement.getAttribute("Meter_Address");
													}
													if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
														isExecFlag = false;
													}
												}else {
													if(resultElement.hasAttribute("Meter_Address")){
														meterAddress = resultElement.getAttribute("Meter_Address");
													}
													if(!meterAddress.equals(xc.getMeterAddress())){
														isExecFlag = false;
													}
												}
												if(!isExecFlag){
													jo = new JSONObject();
													jo.put("isError", "7");
													jo.put("message", "返回表地址或命令码错误！");
													json.add(jo);
													resp.setContentType("application/x-json");

													PrintWriter out = resp.getWriter();
													out.println(json.toString());
													out.flush();
													out.close();
												}
											}
										}
									}
								}else{
									jo = new JSONObject();
									jo.put("isError", "6");
									jo.put("message", "任务编号错误！");
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								}
							}
						}

					} catch (SocketTimeoutException e)
					{
						flag = 1;
						jo = new JSONObject();
						jo.put("isError", "2");
						jo.put("message", "命令超时重发！");

						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} finally
					{
						lock.unlock();
					}
					if (flag == 1)
					{
						ConnDB.release(conn, ps,rs);
						return;
					}

					if (gross < 0)
					{
						gross = 0;
					}
					/*if (money < 0)
					{
						money = 0;
					}*/
					/*if (((gross >= 0) && ("1".equals(protocol))) || ((money >= 0) && ("30".equals(protocol))))
					{*/
					if (gross >= 0)
					{
						// 下发剩余电量
						xc = csc.sendTGross(AmMeterID, String.valueOf(gross), "0", userID);
						/*if ("1".equals(protocol))
						{
							xc = csc.sendTGross(AmMeterID, String.valueOf(gross), "0", userID);
						} else
						{
							xc = csc.sendTGross(AmMeterID, String.valueOf(money), "0", userID);
						}*/

						try
						{
							lock.lock();
							String str = sxc.sendXMLToServer(xc);
							System.out.println("收到的回复是" + str);
							
							gwList = getNodeList(str, "GW");
							for (int m = 0; m < gwList.getLength(); m++)
							{
								Element tasknum = (Element) gwList.item(m);
								if (tasknum.hasAttribute("tasknum"))
								{
									task = tasknum.getAttribute("tasknum");
									if(task.equals(xc.getTaskNum())){
										NodeList list = null;
										String err = "";
										String errMsg = "启动预付费失败！";
										list = getNodeList(str, "commandback");

										Element commandBack = (Element) list.item(0);
										if (commandBack.hasAttribute("error"))
										{
											err = commandBack.getAttribute("error");
											errMsg += commandBack.getAttribute("errormessage");
											// 失败
											if (err.equals("1"))
											{
												flag = 1;
												jo = new JSONObject();
												jo.put("isError", "1");
												jo.put("message", errMsg);
												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											} else if (err.equals("0")) // 成功
											{
												NodeList result = commandBack.getElementsByTagName("result");
												for (int j = 0; j < result.getLength(); j++)
												{
													Element resultElement = (Element) result.item(j);
													
													String dataid = "";
													String meterAddress = "";
													boolean isExecFlag = true;
													if(resultElement.hasAttribute("DataID")){
														dataid = resultElement.getAttribute("DataID");
														if(resultElement.hasAttribute("Meter_Address")){
															meterAddress = resultElement.getAttribute("Meter_Address");
														}
														if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
															isExecFlag = false;
														}
													}else {
														if(resultElement.hasAttribute("Meter_Address")){
															meterAddress = resultElement.getAttribute("Meter_Address");
														}
														if(!meterAddress.equals(xc.getMeterAddress())){
															isExecFlag = false;
														}
													}
													if(!isExecFlag){
														jo = new JSONObject();
														jo.put("isError", "7");
														jo.put("message", "返回表地址或命令码错误！");
														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														out.println(json.toString());
														out.flush();
														out.close();
													}
												}
											}
										}
									}else{
										jo = new JSONObject();
										jo.put("isError", "6");
										jo.put("message", "任务编号错误！");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										out.println(json.toString());
										out.flush();
										out.close();
									}
								}
							}
							
						} catch (SocketTimeoutException e)
						{
							flag = 1;
							jo = new JSONObject();
							jo.put("isError", "2");
							jo.put("message", "命令超时重发！");

							json.add(jo);
							resp.setContentType("application/x-json");

							PrintWriter out = resp.getWriter();
							out.println(json.toString());
							out.flush();
							out.close();
						} finally
						{
							lock.unlock();
						}
					}
				}
				
				xc = csc.readMeter(AmMeterID, userID);
				try
				{
					lock.lock();
					String str = sxc.sendXMLToServer(xc);
					System.out.println("收到的报文的回复是：" + str);
					
					gwList = getNodeList(str, "GW");
					for (int m = 0; m < gwList.getLength(); m++)
					{
						Element tasknum = (Element) gwList.item(m);
						if (tasknum.hasAttribute("tasknum"))
						{
							task = tasknum.getAttribute("tasknum");
							if(task.equals(xc.getTaskNum())){
								NodeList list = null;
								String err;
								list = getNodeList(str, "commandback");

								Element commandBack = (Element) list.item(0);
								if (commandBack.hasAttribute("error"))
								{
									err = commandBack.getAttribute("error");
									// 失败
									if (err.equals("1"))
									{
										flag = 1;
										jo = new JSONObject();
										jo.put("isError", "1");
										jo.put("message", "读退电后金额(电量)失败！");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										out.println(json.toString());
										out.flush();
										out.close();
									} else if (err.equals("0")) // 成功
									{
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										DecimalFormat df = new DecimalFormat("0.##");
										NodeList result = commandBack.getElementsByTagName("result");

										Element resultElement = (Element) result.item(0);
										
										String dataid = "";
										String meterAddress = "";
										boolean isExecFlag = true;
										if(resultElement.hasAttribute("DataID")){
											dataid = resultElement.getAttribute("DataID");
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
												isExecFlag = false;
											}
										}else {
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if(!meterAddress.equals(xc.getMeterAddress())){
												isExecFlag = false;
											}
										}
										if(isExecFlag){
											String readTime = resultElement.getAttribute("ValueTime");
											String SYValue = "0.00", TotoleUsed = "0.00", TZValue = "0.00";
											float ZValueZY = -1, HTZValue = -1;
											if ("1".equals(protocol))// 97
											{
												SYValue = resultElement.getAttribute("SYValue");
												TZValue = resultElement.getAttribute("TZValue");
												TotoleUsed = resultElement.getAttribute("TotoleUsed");
												ZValueZY = Float.parseFloat(TotoleUsed) * csc.getBeiLv();
												SYValue = df.format(df.parse(SYValue));
												HSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();
												HTZValue = Float.parseFloat(TZValue) * csc.getBeiLv();
											} else
											// 07
											{
												SYValue = resultElement.getAttribute("SYMoney");
												SYValue = df.format(df.parse(SYValue));
												HSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();

												xc = csc.readMeter07(AmMeterID, userID, "00900201");// 07当前透支金额
												if (xc != null)
												{
													try
													{
														str = sxc.sendXMLToServer(xc);
														System.out.println("收到的报文的回复是：" + str);
														
														gwList = getNodeList(str, "GW");
														for (m = 0; m < gwList.getLength(); m++)
														{
															tasknum = (Element) gwList.item(m);
															if (tasknum.hasAttribute("tasknum"))
															{
																task = tasknum.getAttribute("tasknum");
																if(task.equals(xc.getTaskNum())){
																	list = getNodeList(str, "commandback");
																	commandBack = (Element) list.item(0);
																	result = commandBack.getElementsByTagName("result");
																	
																	err = getAttrValue(str, "commandback", "error");
																	if ("0".equals(err))
																	{
																		resultElement = (Element) result.item(0);
																		
																		dataid = "";
																		meterAddress = "";
																		isExecFlag = true;
																		if(resultElement.hasAttribute("DataID")){
																			dataid = resultElement.getAttribute("DataID");
																			if(resultElement.hasAttribute("Meter_Address")){
																				meterAddress = resultElement.getAttribute("Meter_Address");
																			}
																			if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																				isExecFlag = false;
																			}
																		}else {
																			if(resultElement.hasAttribute("Meter_Address")){
																				meterAddress = resultElement.getAttribute("Meter_Address");
																			}
																			if(!meterAddress.equals(xc.getMeterAddress())){
																				isExecFlag = false;
																			}
																		}
																		if(isExecFlag){
																			TZValue = getAttrValue(str, "result", "TZMoney");
																			TZValue = df.format(df.parse(TZValue));
																			HTZValue = Float.parseFloat(TZValue) * csc.getBeiLv();
																		}else{
																			jo = new JSONObject();
																			jo.put("isError", "7");
																			jo.put("message", "返回表地址或命令码错误！");
																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		}
																			
																	}
																}else{
																	jo = new JSONObject();
																	jo.put("isError", "6");
																	jo.put("message", "任务编号错误！");
																	json.add(jo);
																	resp.setContentType("application/x-json");

																	PrintWriter out = resp.getWriter();
																	out.println(json.toString());
																	out.flush();
																	out.close();
																}
															}
														}
													} catch (SocketTimeoutException e)
													{
														jo = new JSONObject();
														jo.put("isError", "2");
														jo.put("message", "命令超时重发！");

														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														out.println(json.toString());
														out.flush();
														out.close();
													}
												}
											}

											if ("-1".equals(SYValue))
											{
												jo = new JSONObject();
												jo.put("isError", "1");
												jo.put("message", "网关抄读失败！");
												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											} else
											{
												CMMeterDao cmd = new CMMeterDao();
												cmd.SerSaveData(AmMeterID, ZValueZY, HSYValue, HTZValue, readTime);
												jo = new JSONObject();
												jo.put("isError", "0");
												jo.put("QSYValue", QSYValue);
												jo.put("HSYValue", HSYValue);
												jo.put("theGross", theGross);
												jo.put("theMoney", theMoney);
												jo.put("protocol", protocol);
												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}
										}else{
											jo = new JSONObject();
											jo.put("isError", "7");
											jo.put("message", "返回表地址或命令码错误！");

											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											out.println(json.toString());
											out.flush();
											out.close();
										}
										
									}
								}
							}else{
								jo = new JSONObject();
								jo.put("isError", "6");
								jo.put("message", "任务编号错误！");

								json.add(jo);
								resp.setContentType("application/x-json");

								PrintWriter out = resp.getWriter();
								out.println(json.toString());
								out.flush();
								out.close();
							}
						}
					}
				} catch (SocketTimeoutException e)
				{
					flag = 1;
					jo = new JSONObject();
					jo.put("isError", "2");
					jo.put("message", "命令超时重发！");

					json.add(jo);
					resp.setContentType("application/x-json");

					PrintWriter out = resp.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				} finally
				{
					lock.unlock();
				}
				
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}
			
			String info = (new CMMeterDao()).getAmMeterByID(AmMeterID+"");
			String userLoginName = (String) req.getSession().getAttribute(
					"userName");
			OperationLogInterface log = new OperationLogImplement();
			// 写入日志
			log.writeLog(userLoginName, "预付费", "退购,"+info+",金额："+theMoney+"电量："+theGross);
			/*
			//Statement sm = ConnDB.getConnection().createStatement();
		//	ResultSet rs = sm.executeQuery(sql);*/
			

			/*if (sm != null)
				sm.close();
			if (rs != null)
				rs.close();*/
		}
	}

	private void bufa(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		int AmMeterID = Integer.parseInt(req.getParameter("amMeterID"));
		String SaleInfoNum = req.getParameter("saleInfoNum");

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		CMMsendCommand csc = new CMMsendCommand();
		csc.getPara(AmMeterID);
		String protocol = "1";// 默认97

		if ("0".equals(csc.getAmMeter_Stat()))
		{
			jo = new JSONObject();
			jo.put("isError", "2");
			// jo.put("message", "售电单已经生成，但设备不在线，暂时不能下发。");

			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} else
		{
			HttpSession session = req.getSession();
			String userID = "1";
			if (session.getAttribute("userName") != null)
			{
				userID = session.getAttribute("userName").toString();
			}

			String sql = "select Status,TheGross,TheMoney from APSaleInfo where SaleInfoNum='" + SaleInfoNum + "'";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					String Status = rs.getString("Status");
					String TheGross = rs.getString("TheGross");
					String TheMoney = rs.getString("TheMoney");// 李嵘20150410
					if (!"1".equals(Status))
					{
						SendingXMLCode sxc = new SendingAPCode();
						XMLCoder xc = csc.readMeter(AmMeterID, userID);

						if (xc != null)
						{
							String str = null;
							try
							{
								str = sxc.sendXMLToServer(xc);
								System.out.println("收到的报文的回复是：" + str);
							} catch (SocketTimeoutException e)
							{
								jo = new JSONObject();
								jo.put("isError", "5");
								jo.put("message", "命令超时重发！");

								json.add(jo);
								resp.setContentType("application/x-json");

								PrintWriter out = resp.getWriter();
								out.println(json.toString());
								out.flush();
								out.close();
							}

							// System.out.println("收到的报文的回复是："+str);

							if (str == null)
								return;
							
							NodeList gwList=null;
							String task;
							gwList = getNodeList(str, "GW");
							for (int m = 0; m < gwList.getLength(); m++)
							{
								Element tasknum = (Element) gwList.item(m);
								if (tasknum.hasAttribute("tasknum"))
								{
									task = tasknum.getAttribute("tasknum");
									if(task.equals(xc.getTaskNum())){
										NodeList list = null;
										String err;
										list = getNodeList(str, "commandback");

										for (int i = 0; i < list.getLength(); i++)
										{
											Element commandBack = (Element) list.item(i);
											if (commandBack.hasAttribute("error"))
											{
												err = commandBack.getAttribute("error");
												// 失败
												if (err.equals("1"))
												{
													jo = new JSONObject();
													jo.put("isError", "1");
													// jo.put("message", "回复信息有误！");
													json.add(jo);
													resp.setContentType("application/x-json");

													PrintWriter out = resp.getWriter();
													out.println(json.toString());
													out.flush();
													out.close();
												} else if (err.equals("0")) // 成功
												{
													DecimalFormat df = new DecimalFormat("0.##");
													NodeList result = commandBack.getElementsByTagName("result");
													for (int j = 0; j < result.getLength(); j++)
													{
														Element resultElement = (Element) result.item(j);
														
														String dataid = "";
														String meterAddress = "";
														boolean isExecFlag = true;
														if(resultElement.hasAttribute("DataID")){
															dataid = resultElement.getAttribute("DataID");
															if(resultElement.hasAttribute("Meter_Address")){
																meterAddress = resultElement.getAttribute("Meter_Address");
															}
															if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																isExecFlag = false;
															}
														}else {
															if(resultElement.hasAttribute("Meter_Address")){
																meterAddress = resultElement.getAttribute("Meter_Address");
															}
															if(!meterAddress.equals(xc.getMeterAddress())){
																isExecFlag = false;
															}
														}
														if(isExecFlag){
															//String meterAddress = resultElement.getAttribute("Meter_Address");
															String readTime = resultElement.getAttribute("ValueTime");
															String SYValue = "0.00", TZValue = "0.00", TotoleUsed = "0.00", TotoleBuy = "0.00";
															int BuyTimes = 0;

															if (resultElement.hasAttribute("SYValue"))// 97表
															{
																SYValue = resultElement.getAttribute("SYValue");
																TZValue = resultElement.getAttribute("TZValue");
																TotoleUsed = resultElement.getAttribute("TotoleUsed");
																TotoleBuy = resultElement.getAttribute("TotoleBuy");
																BuyTimes = Integer.parseInt(resultElement.getAttribute("BuyTimes"));
																TZValue = df.format(df.parse(TZValue));
															} else
															// 07
															{
																protocol = "30";
																SYValue = resultElement.getAttribute("SYMoney");
															}

															SYValue = df.format(df.parse(SYValue));

															if ("-1".equals(SYValue))
															{
																jo = new JSONObject();
																jo.put("isError", "1");
																json.add(jo);
															} else
															{
																float QSYValue = 0, QTZValue = 0;
																if ("1".equals(protocol))
																{
																	QSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();
																	QTZValue = Float.parseFloat(TZValue) * csc.getBeiLv();
																} else
																// 07规约要单独读当前透支和购电次数
																{
																	QSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();
																	TheGross = TheMoney;

																	xc = csc.readMeter07(AmMeterID, userID, "00900201");// 当前透支
																	if (xc != null)
																	{
																		try
																		{
																			str = sxc.sendXMLToServer(xc);
																			System.out.println("收到的报文的回复是：" + str);
																		} catch (SocketTimeoutException e)
																		{
																			jo = new JSONObject();
																			jo.put("isError", "2");
																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			// System.out.println(json.toString());
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		}

																		if (str == null)
																			return;
																		
																		gwList = getNodeList(str, "GW");
																		for (m = 0; m < gwList.getLength(); m++)
																		{
																			tasknum = (Element) gwList.item(m);
																			if (tasknum.hasAttribute("tasknum"))
																			{
																				task = tasknum.getAttribute("tasknum");
																				if(task.equals(xc.getTaskNum())){
																					list = getNodeList(str, "commandback");

																					for (i = 0; i < list.getLength(); i++)
																					{
																						commandBack = (Element) list.item(i);
																						if (commandBack.hasAttribute("error"))
																						{
																							err = commandBack.getAttribute("error");
																							// 失败
																							if (err.equals("1"))
																							{
																								String errormessage = commandBack.getAttribute("errormessage");
																								jo = new JSONObject();
																								jo.put("isError", "3");
																								jo.put("errormessage", errormessage);
																								json.add(jo);
																								resp.setContentType("application/x-json");

																								PrintWriter out = resp.getWriter();
																								// System.out.println(json.toString());
																								out.println(json.toString());
																								out.flush();
																								out.close();
																							} else if (err.equals("0")) // 成功
																							{
																								result = commandBack.getElementsByTagName("result");
																								for (j = 0; j < result.getLength(); j++)
																								{
																									resultElement = (Element) result.item(j);
																									
																									dataid = "";
																									meterAddress = "";
																									isExecFlag = true;
																									if(resultElement.hasAttribute("DataID")){
																										dataid = resultElement.getAttribute("DataID");
																										if(resultElement.hasAttribute("Meter_Address")){
																											meterAddress = resultElement.getAttribute("Meter_Address");
																										}
																										if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																											isExecFlag = false;
																										}
																									}else {
																										if(resultElement.hasAttribute("Meter_Address")){
																											meterAddress = resultElement.getAttribute("Meter_Address");
																										}
																										if(!meterAddress.equals(xc.getMeterAddress())){
																											isExecFlag = false;
																										}
																									}
																									if(isExecFlag){
																										readTime = resultElement.getAttribute("ValueTime");
																										if (resultElement.hasAttribute("TZMoney"))// 07表当前透支
																										{
																											TZValue = resultElement.getAttribute("TZMoney");
																											TZValue = df.format(df.parse(TZValue));
																										}
																									}else{
																										jo = new JSONObject();
																										jo.put("isError", "7");
																										json.add(jo);
																										resp.setContentType("application/x-json");

																										PrintWriter out = resp.getWriter();
																										// System.out.println(json.toString());
																										out.println(json.toString());
																										out.flush();
																										out.close();
																									}
																									
																								}
																							}
																						}
																					}
																				}else{
																					jo = new JSONObject();
																					jo.put("isError", "6");
																					json.add(jo);
																					resp.setContentType("application/x-json");

																					PrintWriter out = resp.getWriter();
																					// System.out.println(json.toString());
																					out.println(json.toString());
																					out.flush();
																					out.close();
																				}
																			}
																		}

																		
																	} else
																	{
																		jo = new JSONObject();
																		jo.put("isError", "1");
																		json.add(jo);
																		resp.setContentType("application/x-json");

																		PrintWriter out = resp.getWriter();
																		// System.out.println(json.toString());
																		out.println(json.toString());
																		out.flush();
																		out.close();
																	}
																	QTZValue = Float.parseFloat(TZValue) * csc.getBeiLv();

																	xc = csc.readMeter07(AmMeterID, userID, "07010202");// 上次购电后总购电次数
																	if (xc != null)
																	{
																		try
																		{
																			str = sxc.sendXMLToServer(xc);
																			System.out.println("收到的报文的回复是：" + str);
																		} catch (SocketTimeoutException e)
																		{
																			jo = new JSONObject();
																			jo.put("isError", "2");
																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			// System.out.println(json.toString());
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		}

																		if (str == null)
																			return;
																		
																		gwList = getNodeList(str, "GW");
																		for (m = 0; m < gwList.getLength(); m++)
																		{
																			tasknum = (Element) gwList.item(m);
																			if (tasknum.hasAttribute("tasknum"))
																			{
																				task = tasknum.getAttribute("tasknum");
																				if(task.equals(xc.getTaskNum())){
																					list = getNodeList(str, "commandback");

																					for (i = 0; i < list.getLength(); i++)
																					{
																						commandBack = (Element) list.item(i);
																						if (commandBack.hasAttribute("error"))
																						{
																							err = commandBack.getAttribute("error");
																							// 失败
																							if (err.equals("1"))
																							{
																								String errormessage = commandBack.getAttribute("errormessage");
																								jo = new JSONObject();
																								jo.put("isError", "3");
																								jo.put("errormessage", errormessage);
																								json.add(jo);
																								resp.setContentType("application/x-json");

																								PrintWriter out = resp.getWriter();
																								// System.out.println(json.toString());
																								out.println(json.toString());
																								out.flush();
																								out.close();
																							} else if (err.equals("0")) // 成功
																							{
																								result = commandBack.getElementsByTagName("result");
																								for (j = 0; j < result.getLength(); j++)
																								{
																									resultElement = (Element) result.item(j);
																									
																									dataid = "";
																									meterAddress = "";
																									isExecFlag = true;
																									if(resultElement.hasAttribute("DataID")){
																										dataid = resultElement.getAttribute("DataID");
																										if(resultElement.hasAttribute("Meter_Address")){
																											meterAddress = resultElement.getAttribute("Meter_Address");
																										}
																										if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																											isExecFlag = false;
																										}
																									}else {
																										if(resultElement.hasAttribute("Meter_Address")){
																											meterAddress = resultElement.getAttribute("Meter_Address");
																										}
																										if(!meterAddress.equals(xc.getMeterAddress())){
																											isExecFlag = false;
																										}
																									}
																									if(isExecFlag){
																										readTime = resultElement.getAttribute("ValueTime");
																										if (resultElement.hasAttribute("BuyTimes"))// 07表上次购电后总购电次数
																										{
																											BuyTimes = Integer.parseInt(resultElement.getAttribute("BuyTimes"));
																										}
																									}else{
																										jo = new JSONObject();
																										jo.put("isError", "7");
																										json.add(jo);
																										resp.setContentType("application/x-json");

																										PrintWriter out = resp.getWriter();
																										// System.out.println(json.toString());
																										out.println(json.toString());
																										out.flush();
																										out.close();
																									}
																										
																								}
																							}
																						}
																					}
																				}else{
																					jo = new JSONObject();
																					jo.put("isError", "6");
																					json.add(jo);
																					resp.setContentType("application/x-json");

																					PrintWriter out = resp.getWriter();
																					// System.out.println(json.toString());
																					out.println(json.toString());
																					out.flush();
																					out.close();
																				}
																			}
																		}

																		
																	} else
																	{
																		jo = new JSONObject();
																		jo.put("isError", "1");
																		json.add(jo);
																		resp.setContentType("application/x-json");

																		PrintWriter out = resp.getWriter();
																		// System.out.println(json.toString());
																		out.println(json.toString());
																		out.flush();
																		out.close();
																	}
																}

																//补发验证电量是否已发送
																CMMeterDao dao = new CMMeterDao();
																String resString = dao.SerCheckBu(AmMeterID,SaleInfoNum,QSYValue,BuyTimes);
																jo = new JSONObject();
																jo.put("isError", "0");
																jo.put("check", resString);
																jo.put("QSYValue", QSYValue);
																jo.put("QTZValue", QTZValue);
																jo.put("TheGross", TheGross);
																jo.put("BuyTimes", BuyTimes);
																jo.put("SaleInfoNum", SaleInfoNum);
																jo.put("money", TheMoney);
																json.add(jo);
															}
															resp.setContentType("application/x-json");
															PrintWriter out = resp.getWriter();
															out.println(json.toString());
															out.flush();
															out.close();
														}else{
															jo = new JSONObject();
															jo.put("isError", "7");
															json.add(jo);
															resp.setContentType("application/x-json");

															PrintWriter out = resp.getWriter();
															// System.out.println(json.toString());
															out.println(json.toString());
															out.flush();
															out.close();
														}
														
													}
												}
											}
										}
									}else{
										jo = new JSONObject();
										jo.put("isError", "6");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										out.println(json.toString());
										out.flush();
										out.close();
									}
								}
							}

							
						} else
						{
							jo = new JSONObject();
							jo.put("isError", "1");
							json.add(jo);
							resp.setContentType("application/x-json");

							PrintWriter out = resp.getWriter();
							out.println(json.toString());
							out.flush();
							out.close();
						}
					} else
					{
						jo = new JSONObject();
						jo.put("isError", "3");
						// jo.put("message", "售电单已经生成，但设备不在线，暂时不能下发。");

						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					}

				} else
				{
					jo = new JSONObject();
					jo.put("isError", "4");
					// jo.put("message", "售电单已经生成，但设备不在线，暂时不能下发。");

					json.add(jo);
					resp.setContentType("application/x-json");

					PrintWriter out = resp.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}
			
//			Statement sm = ConnDB.getConnection().createStatement();
//			ResultSet rs = sm.executeQuery(sql);
			
			/*if (sm != null)
				sm.close();
			if (rs != null)
				rs.close();*/

		}

	}

	/**
	 * 读购电后电量
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void readGouDianHou(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}
		int AmMeterID = Integer.parseInt(req.getParameter("AmmId"));
		String BuyTimes = req.getParameter("BuyTimes");
		String QSYValue = req.getParameter("QSYValue");
		String SaleInfoNum = req.getParameter("SaleInfoNum");

		JSONArray json = new JSONArray();
		JSONObject jo = null;
		CMMsendCommand aprm = new CMMsendCommand();
		aprm.getPara(AmMeterID);

		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = aprm.readMeter(AmMeterID, userID);
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// System.out.println("收到的报文的回复是："+str);

			if (str == null)
				return;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						NodeList list = null;
						String err;
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{
								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									DecimalFormat df = new DecimalFormat("0.##");
									NodeList result = commandBack.getElementsByTagName("result");
									for (int j = 0; j < result.getLength(); j++)
									{
										Element resultElement = (Element) result.item(j);
										
										String dataid = "";
										String meterAddress = "";
										boolean isExecFlag = true;
										if(resultElement.hasAttribute("DataID")){
											dataid = resultElement.getAttribute("DataID");
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
												isExecFlag = false;
											}
										}else {
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if(!meterAddress.equals(xc.getMeterAddress())){
												isExecFlag = false;
											}
										}
										if(isExecFlag){
											String readTime = resultElement.getAttribute("ValueTime");
											String protocol = "1";// 97
											String SYValue = "0.00", TZValue = "0.00";
											float HSYValue = 0, HTZValue = 0, ZValueZY = -1;
											if (resultElement.hasAttribute("SYValue"))// 97表
											{
												SYValue = resultElement.getAttribute("SYValue");
												String TotoleUsed = resultElement.getAttribute("TotoleUsed");
												SYValue = df.format(df.parse(SYValue));

												TZValue = resultElement.getAttribute("TZValue");
												TZValue = df.format(df.parse(TZValue));

												HTZValue = Float.parseFloat(TZValue) * aprm.getBeiLv();
												ZValueZY = Float.parseFloat(TotoleUsed) * aprm.getBeiLv();
												HSYValue = Float.parseFloat(SYValue) * aprm.getBeiLv();
											} else
											{
												protocol = "30";
												SYValue = resultElement.getAttribute("SYMoney");
												SYValue = df.format(df.parse(SYValue));
												HSYValue = Float.parseFloat(SYValue) * aprm.getBeiLv();

												xc = aprm.readMeter07(AmMeterID, userID, "00900201");// 07当前透支金额
												if (xc != null)
												{
													try
													{
														str = sxc.sendXMLToServer(xc);
														System.out.println("收到的报文的回复是：" + str);
														
														gwList = getNodeList(str, "GW");
														for (m = 0; m < gwList.getLength(); m++)
														{
															tasknum = (Element) gwList.item(m);
															if (tasknum.hasAttribute("tasknum"))
															{
																task = tasknum.getAttribute("tasknum");
																if(task.equals(xc.getTaskNum())){
																	list = getNodeList(str, "commandback");
																	commandBack = (Element) list.item(0);
																	
																	err = getAttrValue(str, "commandback", "error");
																	if ("0".equals(err))
																	{
																		result = commandBack.getElementsByTagName("result");
																		resultElement = (Element) result.item(0);
																		
																		dataid = "";
																		meterAddress = "";
																		isExecFlag = true;
																		if(resultElement.hasAttribute("DataID")){
																			dataid = resultElement.getAttribute("DataID");
																			if(resultElement.hasAttribute("Meter_Address")){
																				meterAddress = resultElement.getAttribute("Meter_Address");
																			}
																			if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																				isExecFlag = false;
																			}
																		}else {
																			if(resultElement.hasAttribute("Meter_Address")){
																				meterAddress = resultElement.getAttribute("Meter_Address");
																			}
																			if(!meterAddress.equals(xc.getMeterAddress())){
																				isExecFlag = false;
																			}
																		}
																		if(isExecFlag){
																			TZValue = getAttrValue(str, "result", "TZMoney");
																			TZValue = df.format(df.parse(TZValue));
																			HTZValue = Float.parseFloat(TZValue) * aprm.getBeiLv();
																		}else{
																			jo = new JSONObject();
																			jo.put("isError", "7");
																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			// System.out.println(json.toString());
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		}
																			
																	}else{
																		String errormessage = getAttrValue(str, "commandback", "errormessage");
																		jo = new JSONObject();
																		jo.put("isError", "3");
																		jo.put("errormessage", errormessage);
																		json.add(jo);
																		resp.setContentType("application/x-json");

																		PrintWriter out = resp.getWriter();
																		// System.out.println(json.toString());
																		out.println(json.toString());
																		out.flush();
																		out.close();
																	}
																}else{
																	jo = new JSONObject();
																	jo.put("isError", "6");
																	json.add(jo);
																	resp.setContentType("application/x-json");

																	PrintWriter out = resp.getWriter();
																	// System.out.println(json.toString());
																	out.println(json.toString());
																	out.flush();
																	out.close();
																}
															}
														}
													} catch (SocketTimeoutException e)
													{
														jo = new JSONObject();
														jo.put("isError", "2");
														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														// System.out.println(json.toString());
														out.println(json.toString());
														out.flush();
														out.close();
													}
												}
											}

											// 比较售电后剩余电量和购电前剩余电量，如果售电时返回的失败，但是购电后剩余金额增加了表示购电成功,否则失败
											if (HSYValue > Float.parseFloat(QSYValue))
											{
												String sql = "update APSaleInfo set QSYValue=" + QSYValue + ",HSYValue=" + HSYValue + ",Times=" + BuyTimes
														+ ",Status=1,SendTime=to_date('" + sdf.format(new Date()) + "','yyyy-mm-dd hh24:mi:ss') where SaleInfoNum='"
														+ SaleInfoNum + "'";
												Connection conn = null;
												PreparedStatement ps = null;
												try
												{
													conn = ConnDB.getConnection();
													ps = conn.prepareStatement(sql);
													ps.executeUpdate();

												} catch (SQLException e)
												{
													e.printStackTrace();
												} finally
												{
													ConnDB.release(conn, ps);
												}

												CMMeterDao cmd = new CMMeterDao();
												cmd.SerSaveData(AmMeterID, ZValueZY, HSYValue, HTZValue, readTime);

											} else
											{
												// 如果购电失败将检查购电量
												String sql = "update APSaleInfo set HSYValue=" + HSYValue + ",Status=2 where SaleInfoNum='" + SaleInfoNum + "'";
//												Statement sm = ConnDB.getConnection().createStatement();
//												sm.executeUpdate(sql);
//												if (sm != null)
//													sm.close();
												Connection conn2 = null;
												PreparedStatement ps2 = null;
												try
												{
													conn2 = ConnDB.getConnection();
													ps2 = conn2.prepareStatement(sql);
													ps2.executeUpdate();

												} catch (SQLException e)
												{
													e.printStackTrace();
												} finally
												{
													ConnDB.release(conn2, ps2);
												}
												
											}
											jo = new JSONObject();
											jo.put("isError", "0");
											jo.put("HSYValue", HSYValue);
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}else{
											jo = new JSONObject();
											jo.put("isError", "7");
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}
										
									}
								}
							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}

			
		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}
	}

	/**
	 * 下发购电量
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void xiaFaGouDian(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		int AmMeterID = Integer.parseInt(req.getParameter("AmmId"));

		String BuyTimes = req.getParameter("BuyTimes");
		String QSYValue = req.getParameter("QSYValue");
		String SaleInfoNum = req.getParameter("SaleInfoNum");

		JSONArray json = new JSONArray();
		JSONObject jo = null;
		CMMsendCommand aprm = new CMMsendCommand();
		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = null;
		String gross = "0.00", money = "0.00";
		aprm.getPara(AmMeterID);
		if ("1".equals(aprm.getMeterProtocol()))
		{
			gross = req.getParameter("gross");
			xc = aprm.sendTGross(AmMeterID, gross, BuyTimes, userID);
		} else
		{
			money = req.getParameter("money");
			xc = aprm.sendTGross(AmMeterID, money, BuyTimes, userID);
		}

		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// System.out.println("收到的报文的回复是："+str);

			if (str == null)
				return;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						NodeList list = null;
						String err;
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{
								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									NodeList result = commandBack.getElementsByTagName("result");
									Element resultElement = (Element) result.item(0);
									
									String dataid = "";
									String meterAddress = "";
									boolean isExecFlag = true;
									if(resultElement.hasAttribute("DataID")){
										dataid = resultElement.getAttribute("DataID");
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
											isExecFlag = false;
										}
									}else {
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if(!meterAddress.equals(xc.getMeterAddress())){
											isExecFlag = false;
										}
									}
									if(isExecFlag){
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										String sql = "update APSaleInfo set QSYValue=" + QSYValue + ",Times=" + BuyTimes + ",Status=1,SendTime=to_date('"
												+ sdf.format(new Date()) + "','yyyy-mm-dd hh24:mi:ss') where SaleInfoNum='" + SaleInfoNum + "'";
//										Statement sm = ConnDB.getConnection().createStatement();
//										sm.executeUpdate(sql);
//										if (sm != null)
//											sm.close();
										Connection conn = null;
										PreparedStatement ps = null;
										try
										{
											conn = ConnDB.getConnection();
											ps = conn.prepareStatement(sql);
											ps.executeUpdate();

										} catch (SQLException e)
										{
											e.printStackTrace();
										} finally
										{
											ConnDB.release(conn, ps);
										}
										
										jo = new JSONObject();
										jo.put("isError", "0");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}else{
										jo = new JSONObject();
										jo.put("isError", "7");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}
									
								}
							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}

			
		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}
	}

	/**
	 * 后付费充值
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	private void yuGou(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}
		int AmMeter_ID = Integer.parseInt(req.getParameter("Amm"));
		String money = req.getParameter("money");
		String gross = req.getParameter("gross");
		String price = req.getParameter("price");
		
		String info = (new CMMeterDao()).getAmMeterByID(AmMeter_ID+"");
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "集中监测", "后付费电表充值,表ID"+AmMeter_ID+",充值金额："+money);

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		CMMsendCommand csc = new CMMsendCommand();
		csc.getPara(AmMeter_ID);

		CMMeterDao dao = new CMMeterDao();

		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = csc.readMeter(AmMeter_ID, userID);
		// 插入预购信息
		// buyTimes不应该是从表中读取的，充值的记录不会下到表中，所以buyTimes应该自己记录，充值一次就在原来的基础上加1
		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf0.format(new Date());
		String gdInfoNum = dao.insertYuGou(AmMeter_ID, dateString, money, userID);
		String readTime = "";
		if (gdInfoNum != "")
		{
			if (xc != null)
			{
				String str = null;
				try
				{
					str = sxc.sendXMLToServer(xc);
					System.out.println("收到回复的报文:" + str);
				} catch (SocketTimeoutException e)
				{
					jo = new JSONObject();
					jo.put("isError", "3");
					jo.put("message", "命令超时重发！");

					json.add(jo);
					resp.setContentType("application/x-json");

					PrintWriter out = resp.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				}

				if (str == null)
					return;
				
				NodeList gwList=null;
				String task;
				gwList = getNodeList(str, "GW");
				for (int m = 0; m < gwList.getLength(); m++)
				{
					Element tasknum = (Element) gwList.item(m);
					if (tasknum.hasAttribute("tasknum"))
					{
						task = tasknum.getAttribute("tasknum");
						if(task.equals(xc.getTaskNum())){
							NodeList list = null;
							String err;
							list = getNodeList(str, "commandback");

							for (int i = 0; i < list.getLength(); i++)
							{
								Element commandBack = (Element) list.item(i);
								if (commandBack.hasAttribute("error"))
								{
									err = commandBack.getAttribute("error");
									// 失败
									if (err.equals("1"))
									{
										jo = new JSONObject();
										jo.put("isError", "1");
										jo.put("message", commandBack.getAttribute("errormessage"));
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										out.println(json.toString());
										out.flush();
										out.close();
									} else if (err.equals("0")) // 成功
									{
										DecimalFormat df = new DecimalFormat("0.##");
										NodeList result = commandBack.getElementsByTagName("result");
										for (int j = 0; j < result.getLength(); j++)
										{
											Element resultElement = (Element) result.item(j);
											
											String dataid = "";
											String meterAddress = "";
											boolean isExecFlag = true;
											if(resultElement.hasAttribute("DataID")){
												dataid = resultElement.getAttribute("DataID");
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
													isExecFlag = false;
												}
											}else {
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if(!meterAddress.equals(xc.getMeterAddress())){
													isExecFlag = false;
												}
											}
											if(isExecFlag){
												//String meterAddress = resultElement.getAttribute("Meter_Address");
												readTime = resultElement.getAttribute("ValueTime");
												String  TotoleUsed = "";
												float zValueZY = 0;
												if (resultElement.hasAttribute("TotoleUsed"))// 97表
												{
													TotoleUsed = resultElement.getAttribute("TotoleUsed");
													TotoleUsed = df.format(df.parse(TotoleUsed));
												} else if (resultElement.hasAttribute("ZValueZY"))// 07表剩余金额
												{
													TotoleUsed = resultElement.getAttribute("ZValueZY");
													TotoleUsed = df.format(df.parse(TotoleUsed));
												}
												
												if("".equals(TotoleUsed)){
													jo = new JSONObject();
													jo.put("isError", "1");
													json.add(jo);
												}else{
													String sql = "select  AmMeter_485Address,(select Gather_Address from Gather b where a.Gather_ID=b.Gather_ID)Gather_Address From AmMeter a where AmMeter_ID="
															+ AmMeter_ID;

													Connection conn = null;
													PreparedStatement ps =null;
													ResultSet rs =null;
													try{
														conn = ConnDB.getConnection();
														ps = conn.prepareStatement(sql);
														rs = ps.executeQuery();
														while (rs.next())
														{
															String Gather_Address = rs.getString("Gather_Address");
															String AmMeter_485Address = rs.getString("AmMeter_485Address");
															// 写当前示数
															if (!(zValueZY < 0))
															{

																AutoUpDataSaver.dataSave(Gather_Address, "1", AmMeter_485Address, "0C0110", readTime, String.valueOf(zValueZY));
															} else
															{
																// System.out.println("在CMMeterDao类中SerSaveData 函数 ，修改离线");
																String sql3 = "update Ammeter set AmMeter_Stat=0 where AmMeter_ID=" + AmMeter_ID;
																Connection conn1 = null;
																PreparedStatement ps1 = null;
																try
																{
																	conn1 = ConnDB.getConnection();
																	ps1 = conn1.prepareStatement(sql3);
																	ps1.executeUpdate();

																} catch (SQLException e)
																{
																	e.printStackTrace();
																} finally
																{
																	ConnDB.release(conn1, ps1);
																}
															}


														}
													}catch (SQLException e)
													{
														e.printStackTrace();
													} finally
													{
														ConnDB.release(conn, ps,rs);
													}
													
													jo = new JSONObject();
													jo.put("isError", "0");
													json.add(jo);
												}
												
												resp.setContentType("application/x-json");
												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}else{
												jo = new JSONObject();
												jo.put("isError", "7");
												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												// System.out.println(json.toString());
												out.println(json.toString());
												out.flush();
												out.close();
											}
												
										}
									}
								}
							}
						}else{
							jo = new JSONObject();
							jo.put("isError", "6");
							json.add(jo);
							resp.setContentType("application/x-json");

							PrintWriter out = resp.getWriter();
							// System.out.println(json.toString());
							out.println(json.toString());
							out.flush();
							out.close();
						}
					}
				}

				

			} else
			{
				jo = new JSONObject();
				jo.put("isError", "1");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}
			
			// 结算
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			boolean b = dao.getAmMeterJieSuan(AmMeter_ID, date);
			if (b)
			{
				// 更新信息
				b = dao.updateYuGou(gdInfoNum, AmMeter_ID, readTime);
				/*if (b)
				{
					jo = new JSONObject();
					jo.put("isError", "0");
					json.add(jo);
				}*/
			}

		}

	}

	/**
	 * 读购电前剩余电量
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void saleEnergy(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userID") != null)
		{
			userID = session.getAttribute("userID").toString();
		}
		String userName = "1";
		if (session.getAttribute("userName") != null)
		{
			userName = session.getAttribute("userName").toString();
		}
		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		String price = req.getParameter("price");
		String money = req.getParameter("money");
		String gross = req.getParameter("gross");
		float beilv = Float.parseFloat(req.getParameter("beilv"));
		
		String info = (new CMMeterDao()).getAmMeterByID(AmMeterID+"");
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "集中监测", "售电,"+info+",金额："+money+",电量："+gross);

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		CMMsendCommand csc = new CMMsendCommand();
		csc.getPara(AmMeterID);

		CMMeterDao cmmd = new CMMeterDao();

		String SaleInfoNum = "";
		SaleInfoNum = cmmd.GetNextChareNum();
		boolean isS = false;
		// 写入数据库
		// 添加售电单，SaleInfoNum必须是唯一的如果主键重复会异常，则重新生成单号添加直到添加成功
		while (!isS)
		{
			/*try
			{*/
				String sql3 = "insert into APSaleInfo(SaleInfoNum,Ammeter_ID,AmMeter_Name,AmMeter_485Address,Kind,Users_ID,BuyTime,Status,Price,TheGross,TheMoney)"
						+ "values('"
						+ SaleInfoNum
						+ "',"
						+ AmMeterID
						+ ",'"
						+ csc.getAmMeter_Name()
						+ "','"
						+ csc.getAmMeter_485Address()
						+ "',"
						+ 0
						+ ","
						+ userID
						+ ",to_date(to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss'),0,"
						+ price
						+ ","
						+ (float)(Math.round(Float.parseFloat(gross)/beilv*100)/100)
						+ ","
						+ money + ")";
				
				Connection conn = null;
				PreparedStatement ps = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(sql3);
					ps.executeUpdate();
					isS = true;
					break;
				} catch (SQLException e)
				{
					SaleInfoNum = cmmd.GetNextChareNum();
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn, ps);
				}
				/*PreparedStatement ps3 = ConnDB.getConnection().prepareStatement(sql3);
				ps3.executeQuery();

				if (ps3 != null)
					ps3.close();*/
				
			/*} catch (Exception e)
			{
				SaleInfoNum = cmmd.GetNextChareNum();
			}*/
		}

		if ("0".equals(csc.getAmMeter_Stat()))
		{
			jo = new JSONObject();
			jo.put("isError", "2");
			// jo.put("message", "售电单已经生成，但设备不在线，暂时不能下发。");

			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} else
		{
			SendingXMLCode sxc = new SendingAPCode();
			XMLCoder xc = csc.readMeter(AmMeterID, userName);

			if (xc != null)
			{
				String str = null;
				try
				{
					str = sxc.sendXMLToServer(xc);
					System.out.println("收到的报文的回复是：" + str);
				} catch (SocketTimeoutException e)
				{
					jo = new JSONObject();
					jo.put("isError", "3");
					jo.put("message", "命令超时重发！");

					json.add(jo);
					resp.setContentType("application/x-json");

					PrintWriter out = resp.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				}

				// System.out.println("收到的报文的回复是："+str);

				if (str == null)
					return;
				
				NodeList gwList=null;
				String task;
				gwList = getNodeList(str, "GW");
				for (int m = 0; m < gwList.getLength(); m++)
				{
					Element tasknum = (Element) gwList.item(m);
					if (tasknum.hasAttribute("tasknum"))
					{
						task = tasknum.getAttribute("tasknum");
						if(task.equals(xc.getTaskNum())){
							NodeList list = null;
							String err;
							list = getNodeList(str, "commandback");

							for (int i = 0; i < list.getLength(); i++)
							{
								Element commandBack = (Element) list.item(i);
								if (commandBack.hasAttribute("error"))
								{
									err = commandBack.getAttribute("error");
									// 失败
									if (err.equals("1"))
									{
										jo = new JSONObject();
										jo.put("isError", "1");
										jo.put("message", commandBack.getAttribute("errormessage"));
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										out.println(json.toString());
										out.flush();
										out.close();
									} else if (err.equals("0")) // 成功
									{
										DecimalFormat df = new DecimalFormat("0.##");
										NodeList result = commandBack.getElementsByTagName("result");
										for (int j = 0; j < result.getLength(); j++)
										{
											Element resultElement = (Element) result.item(j);
											
											String dataid = "";
											String meterAddress = "";
											boolean isExecFlag = true;
											if(resultElement.hasAttribute("DataID")){
												dataid = resultElement.getAttribute("DataID");
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
													isExecFlag = false;
												}
											}else {
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if(!meterAddress.equals(xc.getMeterAddress())){
													isExecFlag = false;
												}
											}
											if(isExecFlag){
												//String meterAddress = resultElement.getAttribute("Meter_Address");
												String readTime = resultElement.getAttribute("ValueTime");
												String SYValue = "", TZValue = "0.00", TotoleUsed = "", TotoleBuy = "", BuyTimes = "0";
												String protocol = "1";// 默认97规约
												if (resultElement.hasAttribute("SYValue"))// 97表
												{
													SYValue = resultElement.getAttribute("SYValue");
													TZValue = resultElement.getAttribute("TZValue");
													TotoleUsed = resultElement.getAttribute("TotoleUsed");
													TotoleBuy = resultElement.getAttribute("TotoleBuy");
													BuyTimes = resultElement.getAttribute("BuyTimes");

													SYValue = df.format(df.parse(SYValue));
													TotoleUsed = df.format(df.parse(TotoleUsed));
													TZValue = df.format(df.parse(TZValue));
												} else if (resultElement.hasAttribute("SYMoney"))// 07表剩余金额
												{
													protocol = "30";
													SYValue = resultElement.getAttribute("SYMoney");
													SYValue = df.format(df.parse(SYValue));

													xc = csc.readMeter07(AmMeterID, userName, "00010000");// 07当前电量
													if (xc != null)
													{
														str = null;
														try
														{
															str = sxc.sendXMLToServer(xc);
															System.out.println("收到的报文的回复是：" + str);
														} catch (SocketTimeoutException e)
														{
															jo = new JSONObject();
															jo.put("isError", "2");
															json.add(jo);
															resp.setContentType("application/x-json");

															PrintWriter out = resp.getWriter();
															// System.out.println(json.toString());
															out.println(json.toString());
															out.flush();
															out.close();
														}

														if (str == null)
															return;
														
														gwList = getNodeList(str, "GW");
														for (m = 0; m < gwList.getLength(); m++)
														{
															tasknum = (Element) gwList.item(m);
															if (tasknum.hasAttribute("tasknum"))
															{
																task = tasknum.getAttribute("tasknum");
																if(task.equals(xc.getTaskNum())){
																	list = getNodeList(str, "commandback");

																	for (i = 0; i < list.getLength(); i++)
																	{
																		commandBack = (Element) list.item(i);
																		if (commandBack.hasAttribute("error"))
																		{
																			err = commandBack.getAttribute("error");
																			// 失败
																			if (err.equals("1"))
																			{
																				String errormessage = commandBack.getAttribute("errormessage");
																				jo = new JSONObject();
																				jo.put("isError", "3");
																				jo.put("errormessage", errormessage);
																				json.add(jo);
																				resp.setContentType("application/x-json");

																				PrintWriter out = resp.getWriter();
																				// System.out.println(json.toString());
																				out.println(json.toString());
																				out.flush();
																				out.close();
																			} else if (err.equals("0")) // 成功
																			{
																				result = commandBack.getElementsByTagName("result");
																				for (j = 0; j < result.getLength(); j++)
																				{
																					resultElement = (Element) result.item(j);
																					
																					dataid = "";
																					meterAddress = "";
																					isExecFlag = true;
																					if(resultElement.hasAttribute("DataID")){
																						dataid = resultElement.getAttribute("DataID");
																						if(resultElement.hasAttribute("Meter_Address")){
																							meterAddress = resultElement.getAttribute("Meter_Address");
																						}
																						if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																							isExecFlag = false;
																						}
																					}else {
																						if(resultElement.hasAttribute("Meter_Address")){
																							meterAddress = resultElement.getAttribute("Meter_Address");
																						}
																						if(!meterAddress.equals(xc.getMeterAddress())){
																							isExecFlag = false;
																						}
																					}
																					if(isExecFlag){
																						readTime = resultElement.getAttribute("ValueTime");
																						if (resultElement.hasAttribute("ZValueZY"))// 07表当前电量
																						{
																							TotoleUsed = resultElement.getAttribute("ZValueZY");
																							TotoleUsed = df.format(df.parse(TotoleUsed));
																						}
																					}else{
																						jo = new JSONObject();
																						jo.put("isError", "7");
																						json.add(jo);
																						resp.setContentType("application/x-json");

																						PrintWriter out = resp.getWriter();
																						// System.out.println(json.toString());
																						out.println(json.toString());
																						out.flush();
																						out.close();
																					}
																					
																				}
																			}
																		}
																	}
																}else{
																	jo = new JSONObject();
																	jo.put("isError", "6");
																	json.add(jo);
																	resp.setContentType("application/x-json");

																	PrintWriter out = resp.getWriter();
																	// System.out.println(json.toString());
																	out.println(json.toString());
																	out.flush();
																	out.close();
																}
															}
														}

														
													} else
													{
														jo = new JSONObject();
														jo.put("isError", "1");
														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														// System.out.println(json.toString());
														out.println(json.toString());
														out.flush();
														out.close();
													}
													xc = csc.readMeter07(AmMeterID, userID, "00900201");// 07当前透支金额
													if (xc != null)
													{
														str = null;
														try
														{
															str = sxc.sendXMLToServer(xc);
															System.out.println("收到的报文的回复是：" + str);
														} catch (SocketTimeoutException e)
														{
															jo = new JSONObject();
															jo.put("isError", "2");
															json.add(jo);
															resp.setContentType("application/x-json");

															PrintWriter out = resp.getWriter();
															// System.out.println(json.toString());
															out.println(json.toString());
															out.flush();
															out.close();
														}

														if (str == null)
															return;
														
														gwList = getNodeList(str, "GW");
														for (m = 0; m < gwList.getLength(); m++)
														{
															tasknum = (Element) gwList.item(m);
															if (tasknum.hasAttribute("tasknum"))
															{
																task = tasknum.getAttribute("tasknum");
																if(task.equals(xc.getTaskNum())){
																	list = getNodeList(str, "commandback");

																	for (i = 0; i < list.getLength(); i++)
																	{
																		commandBack = (Element) list.item(i);
																		if (commandBack.hasAttribute("error"))
																		{
																			err = commandBack.getAttribute("error");
																			// 失败
																			if (err.equals("1"))
																			{
																				String errormessage = commandBack.getAttribute("errormessage");
																				jo = new JSONObject();
																				jo.put("isError", "3");
																				jo.put("errormessage", errormessage);
																				json.add(jo);
																				resp.setContentType("application/x-json");

																				PrintWriter out = resp.getWriter();
																				// System.out.println(json.toString());
																				out.println(json.toString());
																				out.flush();
																				out.close();
																			} else if (err.equals("0")) // 成功
																			{
																				result = commandBack.getElementsByTagName("result");
																				for (j = 0; j < result.getLength(); j++)
																				{
																					resultElement = (Element) result.item(j);
																					
																					dataid = "";
																					meterAddress = "";
																					isExecFlag = true;
																					if(resultElement.hasAttribute("DataID")){
																						dataid = resultElement.getAttribute("DataID");
																						if(resultElement.hasAttribute("Meter_Address")){
																							meterAddress = resultElement.getAttribute("Meter_Address");
																						}
																						if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																							isExecFlag = false;
																						}
																					}else {
																						if(resultElement.hasAttribute("Meter_Address")){
																							meterAddress = resultElement.getAttribute("Meter_Address");
																						}
																						if(!meterAddress.equals(xc.getMeterAddress())){
																							isExecFlag = false;
																						}
																					}
																					if(isExecFlag){
																						readTime = resultElement.getAttribute("ValueTime");
																						if (resultElement.hasAttribute("TZMoney"))// 07表透支金额
																						{
																							TZValue = resultElement.getAttribute("TZMoney");
																							TZValue = df.format(df.parse(TZValue));
																						}
																					}else{
																						jo = new JSONObject();
																						jo.put("isError", "7");
																						json.add(jo);
																						resp.setContentType("application/x-json");

																						PrintWriter out = resp.getWriter();
																						// System.out.println(json.toString());
																						out.println(json.toString());
																						out.flush();
																						out.close();
																					}
																					
																				}
																			}
																		}
																	}
																}else{
																	jo = new JSONObject();
																	jo.put("isError", "6");
																	json.add(jo);
																	resp.setContentType("application/x-json");

																	PrintWriter out = resp.getWriter();
																	// System.out.println(json.toString());
																	out.println(json.toString());
																	out.flush();
																	out.close();
																}
															}
														}

														
													} else
													{
														jo = new JSONObject();
														jo.put("isError", "1");
														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														// System.out.println(json.toString());
														out.println(json.toString());
														out.flush();
														out.close();
													}
													xc = csc.readMeter07(AmMeterID, userName, "07010202");// 读充值次数
													if (xc != null)
													{
														str = null;
														try
														{
															str = sxc.sendXMLToServer(xc);
															System.out.println("收到的报文的回复是：" + str);
														} catch (SocketTimeoutException e)
														{
															jo = new JSONObject();
															jo.put("isError", "2");
															json.add(jo);
															resp.setContentType("application/x-json");

															PrintWriter out = resp.getWriter();
															// System.out.println(json.toString());
															out.println(json.toString());
															out.flush();
															out.close();
														}

														if (str == null)
															return;

														list = getNodeList(str, "commandback");

														for (i = 0; i < list.getLength(); i++)
														{
															commandBack = (Element) list.item(i);
															if (commandBack.hasAttribute("error"))
															{
																err = commandBack.getAttribute("error");
																// 失败
																if (err.equals("1"))
																{
																	String errormessage = commandBack.getAttribute("errormessage");
																	jo = new JSONObject();
																	jo.put("isError", "3");
																	jo.put("errormessage", errormessage);
																	json.add(jo);
																	resp.setContentType("application/x-json");

																	PrintWriter out = resp.getWriter();
																	// System.out.println(json.toString());
																	out.println(json.toString());
																	out.flush();
																	out.close();
																} else if (err.equals("0")) // 成功
																{
																	result = commandBack.getElementsByTagName("result");
																	for (j = 0; j < result.getLength(); j++)
																	{
																		resultElement = (Element) result.item(j);
																		
																		dataid = "";
																		meterAddress = "";
																		isExecFlag = true;
																		if(resultElement.hasAttribute("DataID")){
																			dataid = resultElement.getAttribute("DataID");
																			if(resultElement.hasAttribute("Meter_Address")){
																				meterAddress = resultElement.getAttribute("Meter_Address");
																			}
																			if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																				isExecFlag = false;
																			}
																		}else {
																			if(resultElement.hasAttribute("Meter_Address")){
																				meterAddress = resultElement.getAttribute("Meter_Address");
																			}
																			if(!meterAddress.equals(xc.getMeterAddress())){
																				isExecFlag = false;
																			}
																		}
																		if(isExecFlag){
																			readTime = resultElement.getAttribute("ValueTime");
																			if (resultElement.hasAttribute("BuyTimes"))// 07表充值次数
																			{
																				BuyTimes = resultElement.getAttribute("BuyTimes");
																			}
																		}else{
																			jo = new JSONObject();
																			jo.put("isError", "7");
																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			// System.out.println(json.toString());
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		}
	
																	}
																}
															}
														}
													} else
													{
														jo = new JSONObject();
														jo.put("isError", "1");
														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														// System.out.println(json.toString());
														out.println(json.toString());
														out.flush();
														out.close();
													}
												}
												if ("-1".equals(SYValue))
												{
													jo = new JSONObject();
													jo.put("isError", "1");
													json.add(jo);
												} else
												{
													float QSYValue = 0, QTZValue = 0;
													if ("1".equals(protocol))
													{
														QSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();
														QTZValue = Float.parseFloat(TZValue) * csc.getBeiLv();
													} else
													{
														QSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();
														QTZValue = Float.parseFloat(TZValue) * csc.getBeiLv();
													}

													jo = new JSONObject();
													jo.put("isError", "0");
													jo.put("QSYValue", QSYValue);
													jo.put("QTZValue", QTZValue);
													jo.put("BuyTimes", BuyTimes);
													jo.put("SaleInfoNum", SaleInfoNum);
													json.add(jo);
												}
												resp.setContentType("application/x-json");
												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}else{
												jo = new JSONObject();
												jo.put("isError", "7");
												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}
											
										}
									}
								}
							}
						}else{
							jo = new JSONObject();
							jo.put("isError", "6");
							json.add(jo);
							resp.setContentType("application/x-json");

							PrintWriter out = resp.getWriter();
							out.println(json.toString());
							out.flush();
							out.close();
						}
					}
				}

				
			} else
			{
				jo = new JSONObject();
				jo.put("isError", "1");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 读销户后剩余电量
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void xiaoHuHouSYvalue(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}
		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		String XiaohuID = req.getParameter("XiaohuID");
		String theBeilv = req.getParameter("beilv");

		JSONArray json = new JSONArray();
		JSONObject jo = null;
		CMMsendCommand aprm = new CMMsendCommand();

		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = aprm.readMeter(AmMeterID, userID);
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// System.out.println("收到的报文的回复是："+str);

			if (str == null)
				return;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						NodeList list = null;
						String err;
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{

								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									DecimalFormat df = new DecimalFormat("0.##");
									NodeList result = commandBack.getElementsByTagName("result");
									for (int j = 0; j < result.getLength(); j++)
									{
										Element resultElement = (Element) result.item(j);
										
										String dataid = "";
										String meterAddress = "";
										boolean isExecFlag = true;
										if(resultElement.hasAttribute("DataID")){
											dataid = resultElement.getAttribute("DataID");
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
												isExecFlag = false;
											}
										}else {
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if(!meterAddress.equals(xc.getMeterAddress())){
												isExecFlag = false;
											}
										}
										if(isExecFlag){
											//String meterAddress = resultElement.getAttribute("Meter_Address");
											String readTime = resultElement.getAttribute("ValueTime");
											String SYValue = "", TZValue = "0.00", TotoleUsed = "", TotoleBuy = "", BuyTimes = "0";
											String protocol = "1";// 默认97规约
											float ZValueZY = -1, HSYValue = 0, HTZValue = 0;
											if (resultElement.hasAttribute("SYValue"))// 97表
											{
												SYValue = resultElement.getAttribute("SYValue");
												TZValue = resultElement.getAttribute("TZValue");
												TotoleUsed = resultElement.getAttribute("TotoleUsed");
												TotoleBuy = resultElement.getAttribute("TotoleBuy");
												BuyTimes = resultElement.getAttribute("BuyTimes");
												SYValue = df.format(df.parse(SYValue));
												TZValue = df.format(df.parse(TZValue));

												ZValueZY = Float.parseFloat(TotoleUsed) * Float.parseFloat(theBeilv);
												HSYValue = Float.parseFloat(SYValue) * Float.parseFloat(theBeilv);
												HTZValue = Float.parseFloat(TZValue) * Float.parseFloat(theBeilv);
											} else
											{
												SYValue = resultElement.getAttribute("SYMoney");
												SYValue = df.format(df.parse(SYValue));
												HSYValue = Float.parseFloat(SYValue);

												xc = aprm.readMeter07(AmMeterID, userID, "00900201");// 07当前透支金额
												if (xc != null)
												{
													try
													{
														str = sxc.sendXMLToServer(xc);
														System.out.println("收到的报文的回复是：" + str);
														
														gwList = getNodeList(str, "GW");
														for (m = 0; m < gwList.getLength(); m++)
														{
															tasknum = (Element) gwList.item(m);
															if (tasknum.hasAttribute("tasknum"))
															{
																task = tasknum.getAttribute("tasknum");
																if(task.equals(xc.getTaskNum())){
																	list = getNodeList(str, "commandback");
																	commandBack = (Element) list.item(0);
																	
																	err = getAttrValue(str, "commandback", "error");
																	if ("0".equals(err))
																	{
																		result = commandBack.getElementsByTagName("result");
																		resultElement = (Element) result.item(0);
																		
																		dataid = "";
																		meterAddress = "";
																		isExecFlag = true;
																		if(resultElement.hasAttribute("DataID")){
																			dataid = resultElement.getAttribute("DataID");
																			if(resultElement.hasAttribute("Meter_Address")){
																				meterAddress = resultElement.getAttribute("Meter_Address");
																			}
																			if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																				isExecFlag = false;
																			}
																		}else {
																			if(resultElement.hasAttribute("Meter_Address")){
																				meterAddress = resultElement.getAttribute("Meter_Address");
																			}
																			if(!meterAddress.equals(xc.getMeterAddress())){
																				isExecFlag = false;
																			}
																		}
																		if(isExecFlag){
																			TZValue = getAttrValue(str, "result", "TZMoney");
																			TZValue = df.format(df.parse(TZValue));
																			HTZValue = Float.parseFloat(TZValue);
																		}else{
																			jo = new JSONObject();
																			jo.put("isError", "7");
																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			// System.out.println(json.toString());
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		}
																			
																	}
																}else{
																	jo = new JSONObject();
																	jo.put("isError", "6");
																	json.add(jo);
																	resp.setContentType("application/x-json");

																	PrintWriter out = resp.getWriter();
																	// System.out.println(json.toString());
																	out.println(json.toString());
																	out.flush();
																	out.close();
																}
															}
														}
														
													} catch (SocketTimeoutException e)
													{

													}
												}
											}
											CMMeterDao dao = new CMMeterDao();
											String a = dao.xiaoHuHouSYvalueSuc(AmMeterID, XiaohuID, HSYValue, HTZValue, ZValueZY);

											jo = new JSONObject();
											jo.put("isError", "0");
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}else{
											jo = new JSONObject();
											jo.put("isError", "7");
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}
										
									}
								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}


			

		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * 销户
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void xiaoHu(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		String XiaohuID = req.getParameter("XiaohuID");
		
		String BuyTimes = req.getParameter("BuyTimes");
		String ZValueZY = req.getParameter("ZValueZY");
		String SYValue = req.getParameter("SYValue");
		
		CMMeterDao dao = new CMMeterDao();
		
		String info = dao.getAmMeterByID(AmMeterID+"");
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "集中监测", "销户,"+info);
		
		String meterPotocol = dao.getMeterPotocol(AmMeterID);
		String a = null;
		if("30".equals(meterPotocol)){
			//07表存销户信息  97表在退电的时候存了  这里不用存
			a = dao.SerSaveXiaoHuTuiDian(AmMeterID, ZValueZY, SYValue, Integer.parseInt(userID));
		}

		if (a != null)
		{
			XiaohuID = a;
		}
		/*if(Integer.parseInt(BuyTimes)==0){//存储销户信息
			CMMeterDao dao = new CMMeterDao();
			String a = dao.SerSaveXiaoHuTuiDian(AmMeterID, ZValueZY, SYValue, Integer.parseInt(userID));

			if (a != null)
			{
				XiaohuID = a;
			}
		}*/

		CMMsendCommand aprm = new CMMsendCommand();
		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = aprm.xiaoHuCommand(AmMeterID, userLoginName, 0);
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// System.out.println("收到的报文的回复是："+str);

			if (str == null)
				return;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						NodeList list = null;
						String err;
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{

								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									NodeList result = commandBack.getElementsByTagName("result");
									Element resultElement = (Element) result.item(0);
									
									String dataid = "";
									String meterAddress = "";
									boolean isExecFlag = true;
									if(resultElement.hasAttribute("DataID")){
										dataid = resultElement.getAttribute("DataID");
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
											isExecFlag = false;
										}
									}else {
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if(!meterAddress.equals(xc.getMeterAddress())){
											isExecFlag = false;
										}
									}
									if(isExecFlag){
										dao.SerSaveXiaohu2(AmMeterID, XiaohuID);
										
										if("30".equals(meterPotocol)){
											//存储一条0的剩余记录  因为 07表销户后没有抄表
											String sql = "select  AmMeter_485Address,(select Gather_Address from Gather b where a.Gather_ID=b.Gather_ID)Gather_Address From AmMeter a where AmMeter_ID="
													+ AmMeterID;

											Connection conn = null;
											PreparedStatement ps =null;
											ResultSet rs =null;
											try{
												conn = ConnDB.getConnection();
												ps = conn.prepareStatement(sql);
												rs = ps.executeQuery();
												while (rs.next())
												{
													String Gather_Address = rs.getString("Gather_Address");
													String AmMeter_485Address = rs.getString("AmMeter_485Address");
													SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
													// 写剩余电量
														AutoUpDataSaver.dataSavePurchase(Gather_Address, AmMeter_485Address, df.format(new Date()), "0", "0", "0",
																"0", 0);
													
												}
											}catch (SQLException e)
											{
												e.printStackTrace();
											} finally
											{
												ConnDB.release(conn, ps,rs);
											}
										}
										
										jo = new JSONObject();
										jo.put("isError", "0");
										jo.put("meterPotocol", meterPotocol);
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}else{
										jo = new JSONObject();
										jo.put("isError", "6");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}
									
								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}

			

		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * 销户退电
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws Exception
	 * @throws UnknownHostException
	 */
	private void xiaoHuTuiDian(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		JSONArray json = new JSONArray();
		JSONObject jo = null;

		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		String BuyTimes = req.getParameter("BuyTimes");
		String ZValueZY = req.getParameter("ZValueZY");
		String SYValue = req.getParameter("SYValue");

		CMMsendCommand aprm = new CMMsendCommand();
		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = aprm.xiaoHuTuiDian(AmMeterID, BuyTimes, userLoginName);
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// System.out.println("收到的报文的回复是："+str);
			if (str == null)
				return;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						NodeList list = null;
						String err;
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{

								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									NodeList result = commandBack.getElementsByTagName("result");
									Element resultElement = (Element) result.item(0);
									
									String dataid = "";
									String meterAddress = "";
									boolean isExecFlag = true;
									if(resultElement.hasAttribute("DataID")){
										dataid = resultElement.getAttribute("DataID");
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
											isExecFlag = false;
										}
									}else {
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if(!meterAddress.equals(xc.getMeterAddress())){
											isExecFlag = false;
										}
									}
									if(isExecFlag){
										CMMeterDao dao = new CMMeterDao();

										String a = dao.SerSaveXiaoHuTuiDian(AmMeterID, ZValueZY, SYValue, Integer.parseInt(userID));

										if (a != null)
										{
											jo = new JSONObject();
											jo.put("isError", "0");
											jo.put("XiaohuID", a);
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										} else
										{
											jo = new JSONObject();
											jo.put("isError", "2");
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}
									}else{
										jo = new JSONObject();
										jo.put("isError", "7");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}
									
								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}

		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}
	}

	/**
	 * 读开户后剩余电量
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void kaiHuHouSyvalue(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{

		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		String beilv = req.getParameter("beilv");

		String saleInfoNum = req.getParameter("saleInfoNum");
		CMMsendCommand aprm = new CMMsendCommand();

		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = aprm.readMeter(AmMeterID, userID);
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// System.out.println("收到的报文的回复是："+str);
			if (str == null)
				return;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						NodeList list = null;
						String err;
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{

								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);

									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									DecimalFormat df = new DecimalFormat("0.00");
									NodeList result = commandBack.getElementsByTagName("result");
									for (int j = 0; j < result.getLength(); j++)
									{
										Element resultElement = (Element) result.item(j);
										
										String dataid = "";
										String meterAddress = "";
										boolean isExecFlag = true;
										if(resultElement.hasAttribute("DataID")){
											dataid = resultElement.getAttribute("DataID");
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
												isExecFlag = false;
											}
										}else {
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if(!meterAddress.equals(xc.getMeterAddress())){
												isExecFlag = false;
											}
										}
										if(isExecFlag){
											//String meterAddress = resultElement.getAttribute("Meter_Address");
											String readTime = resultElement.getAttribute("ValueTime");

											String SYValue = "0.00", TZValue = "0.00", TotoleUsed = "0.00", TotoleBuy = "0.00", BuyTimes = "";
											String protocol = "1";// 默认97规约
											if (resultElement.hasAttribute("SYValue"))// 97表
											{
												SYValue = resultElement.getAttribute("SYValue");
												TZValue = resultElement.getAttribute("TZValue");
												TotoleUsed = resultElement.getAttribute("TotoleUsed");
												TotoleBuy = resultElement.getAttribute("TotoleBuy");
												BuyTimes = resultElement.getAttribute("BuyTimes");

												SYValue = df.format(df.parse(SYValue));
												TotoleUsed = df.format(df.parse(TotoleUsed));
												TZValue = df.format(df.parse(TZValue));

											} else if (resultElement.hasAttribute("SYMoney"))// 07表剩余金额
											{
												protocol = "30";
												SYValue = resultElement.getAttribute("SYMoney");
												SYValue = df.format(df.parse(SYValue));

												xc = aprm.readMeter07(AmMeterID, userID, "00010000");
												if (xc != null)
												{
													str = null;
													try
													{
														str = sxc.sendXMLToServer(xc);
														System.out.println("收到的报文的回复是：" + str);
													} catch (SocketTimeoutException e)
													{
														jo = new JSONObject();
														jo.put("isError", "2");
														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														// System.out.println(json.toString());
														out.println(json.toString());
														out.flush();
														out.close();
													}

													if (str == null)
														return;
													
													gwList = getNodeList(str, "GW");
													for (m = 0; m < gwList.getLength(); m++)
													{
														tasknum = (Element) gwList.item(m);
														if (tasknum.hasAttribute("tasknum"))
														{
															task = tasknum.getAttribute("tasknum");
															if(task.equals(xc.getTaskNum())){
																list = getNodeList(str, "commandback");

																for (i = 0; i < list.getLength(); i++)
																{
																	commandBack = (Element) list.item(i);
																	if (commandBack.hasAttribute("error"))
																	{
																		err = commandBack.getAttribute("error");
																		// 失败
																		if (err.equals("1"))
																		{
																			String errormessage = commandBack.getAttribute("errormessage");
																			jo = new JSONObject();
																			jo.put("isError", "3");
																			jo.put("errormessage", errormessage);
																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			// System.out.println(json.toString());
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		} else if (err.equals("0")) // 成功
																		{
																			result = commandBack.getElementsByTagName("result");
																			for (j = 0; j < result.getLength(); j++)
																			{
																				resultElement = (Element) result.item(j);
																				
																				dataid = "";
																				meterAddress = "";
																				isExecFlag = true;
																				if(resultElement.hasAttribute("DataID")){
																					dataid = resultElement.getAttribute("DataID");
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																						isExecFlag = false;
																					}
																				}else {
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if(!meterAddress.equals(xc.getMeterAddress())){
																						isExecFlag = false;
																					}
																				}
																				if(isExecFlag){
																					readTime = resultElement.getAttribute("ValueTime");
																					if (resultElement.hasAttribute("ZValueZY"))// 07表当前电量
																					{
																						TotoleUsed = resultElement.getAttribute("ZValueZY");
																						TotoleUsed = df.format(df.parse(TotoleUsed));
																					}
																				}else{
																					jo = new JSONObject();
																					jo.put("isError", "7");
																					json.add(jo);
																					resp.setContentType("application/x-json");

																					PrintWriter out = resp.getWriter();
																					// System.out.println(json.toString());
																					out.println(json.toString());
																					out.flush();
																					out.close();
																				}
																				
																				
																			}
																		}
																	}
																}
															}else{
																jo = new JSONObject();
																jo.put("isError", "6");
																json.add(jo);
																resp.setContentType("application/x-json");

																PrintWriter out = resp.getWriter();
																// System.out.println(json.toString());
																out.println(json.toString());
																out.flush();
																out.close();
															}
														}
													}
												} else
												{
													jo = new JSONObject();
													jo.put("isError", "1");
													json.add(jo);
													resp.setContentType("application/x-json");

													PrintWriter out = resp.getWriter();
													// System.out.println(json.toString());
													out.println(json.toString());
													out.flush();
													out.close();
												}
												xc = aprm.readMeter07(AmMeterID, userID, "00900201");// 当前透支金额
												if (xc != null)
												{
													str = null;
													try
													{
														str = sxc.sendXMLToServer(xc);
														System.out.println("收到的报文的回复是：" + str);
													} catch (SocketTimeoutException e)
													{
														jo = new JSONObject();
														jo.put("isError", "2");
														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														// System.out.println(json.toString());
														out.println(json.toString());
														out.flush();
														out.close();
													}

													if (str == null)
														return;
													
													gwList = getNodeList(str, "GW");
													for (m = 0; m < gwList.getLength(); m++)
													{
														tasknum = (Element) gwList.item(m);
														if (tasknum.hasAttribute("tasknum"))
														{
															task = tasknum.getAttribute("tasknum");
															if(task.equals(xc.getTaskNum())){
																list = getNodeList(str, "commandback");

																for (i = 0; i < list.getLength(); i++)
																{
																	commandBack = (Element) list.item(i);
																	if (commandBack.hasAttribute("error"))
																	{
																		err = commandBack.getAttribute("error");
																		// 失败
																		if (err.equals("1"))
																		{
																			String errormessage = commandBack.getAttribute("errormessage");
																			jo = new JSONObject();
																			jo.put("isError", "3");
																			jo.put("errormessage", errormessage);
																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			// System.out.println(json.toString());
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		} else if (err.equals("0")) // 成功
																		{
																			result = commandBack.getElementsByTagName("result");
																			for (j = 0; j < result.getLength(); j++)
																			{
																				resultElement = (Element) result.item(j);
																				
																				dataid = "";
																				meterAddress = "";
																				isExecFlag = true;
																				if(resultElement.hasAttribute("DataID")){
																					dataid = resultElement.getAttribute("DataID");
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																						isExecFlag = false;
																					}
																				}else {
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if(!meterAddress.equals(xc.getMeterAddress())){
																						isExecFlag = false;
																					}
																				}
																				if(isExecFlag){
																					readTime = resultElement.getAttribute("ValueTime");
																					if (resultElement.hasAttribute("TZMoney"))// 07表透支金额
																					{
																						TZValue = resultElement.getAttribute("TZMoney");
																						TZValue = df.format(df.parse(TZValue));
																					}
																				}else{
																					jo = new JSONObject();
																					jo.put("isError", "7");
																					json.add(jo);
																					resp.setContentType("application/x-json");

																					PrintWriter out = resp.getWriter();
																					// System.out.println(json.toString());
																					out.println(json.toString());
																					out.flush();
																					out.close();
																				}
																				
																				
																			}
																		}
																	}
																}
															}else{
																jo = new JSONObject();
																jo.put("isError", "6");
																json.add(jo);
																resp.setContentType("application/x-json");

																PrintWriter out = resp.getWriter();
																// System.out.println(json.toString());
																out.println(json.toString());
																out.flush();
																out.close();
															}
														}
													}

													
												} else
												{
													jo = new JSONObject();
													jo.put("isError", "1");
													json.add(jo);
													resp.setContentType("application/x-json");

													PrintWriter out = resp.getWriter();
													// System.out.println(json.toString());
													out.println(json.toString());
													out.flush();
													out.close();
												}
												xc = aprm.readMeter07(AmMeterID, userID, "07010202");
												if (xc != null)
												{
													str = null;
													try
													{
														str = sxc.sendXMLToServer(xc);
														System.out.println("收到的报文的回复是：" + str);
													} catch (SocketTimeoutException e)
													{
														jo = new JSONObject();
														jo.put("isError", "2");
														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														// System.out.println(json.toString());
														out.println(json.toString());
														out.flush();
														out.close();
													}

													if (str == null)
														return;
													
													gwList = getNodeList(str, "GW");
													for (m = 0; m < gwList.getLength(); m++)
													{
														tasknum = (Element) gwList.item(m);
														if (tasknum.hasAttribute("tasknum"))
														{
															task = tasknum.getAttribute("tasknum");
															if(task.equals(xc.getTaskNum())){
																list = getNodeList(str, "commandback");

																for (i = 0; i < list.getLength(); i++)
																{
																	commandBack = (Element) list.item(i);
																	if (commandBack.hasAttribute("error"))
																	{
																		err = commandBack.getAttribute("error");
																		// 失败
																		if (err.equals("1"))
																		{
																			String errormessage = commandBack.getAttribute("errormessage");
																			jo = new JSONObject();
																			jo.put("isError", "3");
																			jo.put("errormessage", errormessage);
																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			// System.out.println(json.toString());
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		} else if (err.equals("0")) // 成功
																		{
																			result = commandBack.getElementsByTagName("result");
																			for (j = 0; j < result.getLength(); j++)
																			{
																				resultElement = (Element) result.item(j);
																				
																				dataid = "";
																				meterAddress = "";
																				isExecFlag = true;
																				if(resultElement.hasAttribute("DataID")){
																					dataid = resultElement.getAttribute("DataID");
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																						isExecFlag = false;
																					}
																				}else {
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if(!meterAddress.equals(xc.getMeterAddress())){
																						isExecFlag = false;
																					}
																				}
																				if(isExecFlag){
																					readTime = resultElement.getAttribute("ValueTime");
																					if (resultElement.hasAttribute("BuyTimes"))// 07表充值次数
																					{
																						BuyTimes = resultElement.getAttribute("BuyTimes");
																					}
																				}else{
																					jo = new JSONObject();
																					jo.put("isError", "7");
																					json.add(jo);
																					resp.setContentType("application/x-json");

																					PrintWriter out = resp.getWriter();
																					// System.out.println(json.toString());
																					out.println(json.toString());
																					out.flush();
																					out.close();
																				}
																					
																			}
																		}
																	}
																}
															}else{
																jo = new JSONObject();
																jo.put("isError", "6");
																json.add(jo);
																resp.setContentType("application/x-json");

																PrintWriter out = resp.getWriter();
																// System.out.println(json.toString());
																out.println(json.toString());
																out.flush();
																out.close();
															}
														}
													}

													
												} else
												{
													jo = new JSONObject();
													jo.put("isError", "1");
													json.add(jo);
													resp.setContentType("application/x-json");

													PrintWriter out = resp.getWriter();
													// System.out.println(json.toString());
													out.println(json.toString());
													out.flush();
													out.close();
												}
												// ///////////////////////////////////////////
											}

											if ("-1".equals(SYValue))
											{
												jo = new JSONObject();
												jo.put("isError", "1");
												json.add(jo);
											} else
											{
												CMMeterDao dao = new CMMeterDao();
												float HSYValue = 0, HTZValue = 0;
												if ("1".equals(protocol))
												{
													float zValueZY = Float.parseFloat(TotoleUsed) * Float.parseFloat(beilv);
													float sYValue = Float.parseFloat(SYValue) * Float.parseFloat(beilv);
													float tZValue = Float.parseFloat(TZValue) * Float.parseFloat(beilv);
													dao.SerSaveData(AmMeterID, zValueZY, sYValue, tZValue, readTime);
													HSYValue = Float.parseFloat(SYValue) * Float.parseFloat(beilv);
													HTZValue = Float.parseFloat(TZValue) * Float.parseFloat(beilv);
												} else
												{
													float sYMoney = Float.parseFloat(SYValue) * Float.parseFloat(beilv);
													float tZMoney = Float.parseFloat(TZValue) * Float.parseFloat(beilv);
													dao.SerSaveData(AmMeterID, -1, sYMoney, tZMoney, readTime);
													HSYValue = Float.parseFloat(SYValue);
													HTZValue = Float.parseFloat(TZValue);
												}

												jo = new JSONObject();
												jo.put("isError", "0");
												jo.put("HSYValue", df.format(HSYValue));
												jo.put("HTZValue", df.format(HTZValue));
												jo.put("BuyTimes", BuyTimes);
												json.add(jo);

												String sql = "update APSaleInfo set HSYvalue=" + HSYValue+" where SALEINFONUM='"+saleInfoNum+"'";
												Connection conn = null;
												PreparedStatement ps = null;
												try
												{
													conn = ConnDB.getConnection();
													ps = conn.prepareStatement(sql);
													ps.executeUpdate();

												} catch (SQLException e)
												{
													e.printStackTrace();
												} finally
												{
													ConnDB.release(conn, ps);
												}
												
												/*PreparedStatement ps3 = ConnDB.getConnection().prepareStatement(sql);
												ps3.executeUpdate();

												if (ps3 != null)
													ps3.close();*/
											}
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}else{
											jo = new JSONObject();
											jo.put("isError", "7");
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}
										
									}
								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}

			

		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}
	}

	/**
	 * 下发电量
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws
	 * @throws Exception
	 */
	private void xiaFaDianLiang(HttpServletRequest req, HttpServletResponse resp) throws SQLException, Exception
	{
		HttpSession session = req.getSession();
		int userID = 0;
		String userName="";
		if (session.getAttribute("userID") != null)
		{
			userID = Integer.parseInt(session.getAttribute("userID").toString());
		}
		if (session.getAttribute("userName") != null)
		{
			userName = session.getAttribute("userName").toString();
		}
		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		String Money = req.getParameter("kaihu_txtCountMoney");
		String SaleMoney = req.getParameter("kaihu_txtSaleMoney");
		String Gross = req.getParameter("kaihu_txtSaleGross");
		String Price = req.getParameter("kaihu_txtPrice_Value");
		String Kind = "0";
		String SYGross = req.getParameter("kaihu_txtSYValue");
		String TZValue = req.getParameter("kaihu_txtTZValue");
		String TZMoney = req.getParameter("kaihu_txtTZMoney");
		String Gvalue = req.getParameter("kaihu_txtGvalue");
		float beilv = Float.parseFloat(req.getParameter("beilv"));

		CMMsendCommand aprm = new CMMsendCommand();
		aprm.getPara(AmMeterID);

		CMMeterDao dao = new CMMeterDao();

		// 产生售电单
		String aaa = dao.afterKaiHusuccess(AmMeterID, userID, Money, (float)(Math.round(Float.parseFloat(Gross)/beilv*100)/100)+"", Price, Kind, SYGross, TZValue, TZMoney, Gvalue, aprm.getAmMeter_Name(),
				aprm.getAmMeter_485Address());

		String[] para = aaa.split(",");
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		//07表下发的应该是售电金额  而不是收入金额
		XMLCoder xc = aprm.kaiHuXiaFaDianLiang(AmMeterID, Gross, SYGross, Gvalue, para[0], userName, Money);
		//XMLCoder xc = aprm.kaiHuXiaFaDianLiang(AmMeterID, Gross, SYGross, Gvalue, para[0], String.valueOf(userID), SaleMoney);
		SendingXMLCode sxc = new SendingAPCode();
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");

				// xiaFaDianLiangShiBai();
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// System.out.println("收到的报文的回复是："+str);

			NodeList list = null;
			String err;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{

								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									NodeList result = commandBack.getElementsByTagName("result");
									Element resultElement = (Element) result.item(0);
									
									String dataid = "";
									String meterAddress = "";
									boolean isExecFlag = true;
									if(resultElement.hasAttribute("DataID")){
										dataid = resultElement.getAttribute("DataID");
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
											isExecFlag = false;
										}
									}else {
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if(!meterAddress.equals(xc.getMeterAddress())){
											isExecFlag = false;
										}
									}
									if(isExecFlag){
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										String sql = "update APSaleInfo set Times=1,Status=1,SendTime=to_date('" + sdf.format(new Date())
												+ "','yyyy-mm-dd hh24:mi:ss') where SaleInfoNum='" + para[1] + "'";
										Connection conn = null;
										PreparedStatement ps = null;
										try
										{
											conn = ConnDB.getConnection();
											ps = conn.prepareStatement(sql);
											ps.executeUpdate();

										} catch (SQLException e)
										{
											e.printStackTrace();
										} finally
										{
											ConnDB.release(conn, ps);
										}										
										
										
										jo = new JSONObject();
										jo.put("isError", "0");
										jo.put("saleInfoNum", para[1]);
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}else{
										jo = new JSONObject();
										jo.put("isError", "7");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}
									
									
								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}
			
		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}
	}

	/**
	 * 开户命令
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void kaiHu(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		
//		String info = (new CMMeterDao()).getAmMeterByID(AmMeterID+"");
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "集中监测", "开户,表ID"+AmMeterID);
		
		String Money = req.getParameter("kaihu_txtCountMoney");
		String Gross = req.getParameter("kaihu_txtSaleGross");
		String Price = req.getParameter("kaihu_txtPrice_Value");
		String Kind = "0";
		String SYGross = req.getParameter("kaihu_txtSYValue");
		String TZValue = req.getParameter("kaihu_txtTZValue");
		String TZMoney = req.getParameter("kaihu_txtTZMoney");
		String Gvalue = req.getParameter("kaihu_txtGvalue");

		JSONArray json = new JSONArray();
		JSONObject jo = null;
		CMMsendCommand aprm = new CMMsendCommand();

		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = aprm.kaiHuCommand(AmMeterID, userID);

		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				jo.put("message", "命令超时重发！");

				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// System.out.println("收到的报文的回复是："+str);

			if (str == null)
				return;
			
			NodeList list = null;
			String err;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{

								err = commandBack.getAttribute("error");
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									NodeList result = commandBack.getElementsByTagName("result");
									Element resultElement = (Element) result.item(0);
									
									String dataid = "";
									String meterAddress = "";
									boolean isExecFlag = true;
									if(resultElement.hasAttribute("DataID")){
										dataid = resultElement.getAttribute("DataID");
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
											isExecFlag = false;
										}
									}else {
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if(!meterAddress.equals(xc.getMeterAddress())){
											isExecFlag = false;
										}
									}
									if(isExecFlag){
										jo = new JSONObject();
										jo.put("isError", "0");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}else{
										jo = new JSONObject();
										jo.put("isError", "7");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}
									
								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}
		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * 分闸
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void fenZha(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		
//		String info = (new CMMeterDao()).getAmMeterByID(AmMeterID+"");
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "集中监测", "分闸,表ID"+AmMeterID);
		
		String ddlLine = req.getParameter("ddlLine");
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		CMMsendCommand aprm = new CMMsendCommand();

		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = aprm.fenZhaCommand(AmMeterID, ddlLine, userID);
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// System.out.println("收到的报文的回复是："+str);

			if (str == null)
				return;

			NodeList list = null;
			String err;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{

								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									NodeList result = commandBack.getElementsByTagName("result");
									Element resultElement = (Element) result.item(0);
									
									String dataid = "";
									String meterAddress = "";
									boolean isExecFlag = true;
									if(resultElement.hasAttribute("DataID")){
										dataid = resultElement.getAttribute("DataID");
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
											isExecFlag = false;
										}
									}else {
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if(!meterAddress.equals(xc.getMeterAddress())){
											isExecFlag = false;
										}
									}
									if(isExecFlag){
										String IsSupply = "";
										String sql = "select IsSupply from AmMeter where Ammeter_ID=" + AmMeterID;
										Connection conn = null;
										PreparedStatement ps =null;
										ResultSet rs =null;
										try{
											conn = ConnDB.getConnection();
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											if (rs.next())
											{
												IsSupply = rs.getString("IsSupply");
											}
										}catch (SQLException e)
										{
											e.printStackTrace();
										} finally
										{
											ConnDB.release(conn, ps,rs);
										}
										
//										PreparedStatement ps = null;
//										ps = ConnDB.getConnection().prepareStatement(sql);
//										ResultSet rs = ps.executeQuery();
//										if (rs.next())
//										{
//											IsSupply = rs.getString("IsSupply");
//										}
//										if (rs != null)
//											rs.close();
//										if (ps != null)
//											ps.close();

										while (IsSupply.length() < 2)
										{
											IsSupply = "0" + IsSupply;
										}
										if ("C028".equals(ddlLine))
										{
											IsSupply = "1" + IsSupply.substring(1);
										} else if ("C027".equals(ddlLine))
										{
											IsSupply = IsSupply.substring(0, 1) + "1";
										}

										String sql2 = "update AmMeter set IsSupply='" + IsSupply + "' where Ammeter_ID=" + AmMeterID;
//										PreparedStatement ps2 = null;
//										ps2 = ConnDB.getConnection().prepareStatement(sql2);
//										ps2.executeUpdate();
//										if (ps2 != null)
//											ps.close();
										Connection conn1 = null;
										PreparedStatement ps1 = null;
										try
										{
											conn1 = ConnDB.getConnection();
											ps1 = conn1.prepareStatement(sql2);
											ps1.executeUpdate();

										} catch (SQLException e)
										{
											e.printStackTrace();
										} finally
										{
											ConnDB.release(conn1, ps1);
										}

										jo = new JSONObject();
										jo.put("isError", "0");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}else{
										jo = new JSONObject();
										jo.put("isError", "7");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}
										
								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}
			
		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * 合闸
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void heZha(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		
		//String info = (new CMMeterDao()).getAmMeterByID(AmMeterID+"");
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "集中监测", "合闸,表ID"+AmMeterID);
		
		String ddlLine = req.getParameter("ddlLine");
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		CMMsendCommand aprm = new CMMsendCommand();

		SendingXMLCode sxc = new SendingAPCode();
//		String userrealname=(String) req.getSession().getAttribute("realName");
		XMLCoder xc = aprm.heZhaCommand(AmMeterID, ddlLine, userID);
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// System.out.println("收到的报文的回复是："+str);

			if (str == null)
				return;

			NodeList list = null;
			String err;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{

								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									NodeList result = commandBack.getElementsByTagName("result");
									Element resultElement = (Element) result.item(0);
									
									String dataid = "";
									String meterAddress = "";
									boolean isExecFlag = true;
									if(resultElement.hasAttribute("DataID")){
										dataid = resultElement.getAttribute("DataID");
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
											isExecFlag = false;
										}
									}else {
										if(resultElement.hasAttribute("Meter_Address")){
											meterAddress = resultElement.getAttribute("Meter_Address");
										}
										if(!meterAddress.equals(xc.getMeterAddress())){
											isExecFlag = false;
										}
									}
									if(isExecFlag){
										String IsSupply = null;
										String sql = "select IsSupply from AmMeter where Ammeter_ID=" + AmMeterID;
										Connection conn = null;
										PreparedStatement ps =null;
										ResultSet rs =null;
										try{
											conn = ConnDB.getConnection();
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											if (rs.next())
											{
												IsSupply = rs.getString("IsSupply");
											}
										}catch (SQLException e)
										{
											e.printStackTrace();
										} finally
										{
											ConnDB.release(conn, ps,rs);
										}
										
//										PreparedStatement ps = null;
//										ps = ConnDB.getConnection().prepareStatement(sql);
//										ResultSet rs = ps.executeQuery();
//										if (rs.next())
//										{
//											IsSupply = rs.getString("IsSupply");
//										}
//										if (rs != null)
//											rs.close();
//										if (ps != null)
//											ps.close();

										while (IsSupply.length() < 2)
										{
											IsSupply = "0" + IsSupply;
										}
										if ("C028".equals(ddlLine))
											IsSupply = "0" + IsSupply.substring(1);
										else if ("C027".equals(ddlLine))
											IsSupply = IsSupply.substring(0, 1) + "0";

										String sql2 = "update AmMeter set IsSupply='" + IsSupply + "' where Ammeter_ID=" + AmMeterID;
										Connection conn1 = null;
										PreparedStatement ps1 = null;
										try
										{
											conn1 = ConnDB.getConnection();
											ps1 = conn1.prepareStatement(sql2);
											ps1.executeUpdate();

										} catch (SQLException e)
										{
											e.printStackTrace();
										} finally
										{
											ConnDB.release(conn1, ps1);
										}
										
//										PreparedStatement ps2 = null;
//										ps2 = ConnDB.getConnection().prepareStatement(sql2);
//										ps2.executeUpdate();
				//
//										if (ps2 != null)
//											ps.close();

										jo = new JSONObject();
										jo.put("isError", "0");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}else{
										jo = new JSONObject();
										jo.put("isError", "7");
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										// System.out.println(json.toString());
										out.println(json.toString());
										out.flush();
										out.close();
									}

								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}
			
		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}
	}

	/**
	 * 获取打开开户界面的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getAll_KaiHu_InfoForJZJC(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{

		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		CMMeterDao dao = new CMMeterDao();
		JSONArray json = dao.getAll_KaiHu_InfoForJZJC(AmMeterID);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 获得售电界面的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getAll_SaleEnergy_InfoForJZJC(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		CMMeterDao dao = new CMMeterDao();
		JSONArray json = dao.getAll_SaleEnergy_InfoForJZJC(AmMeterID);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 集抄电表
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void getReadAmmeter(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		String cunchu = req.getParameter("cunchu");
		String beiLv = req.getParameter("beiLv");

		CMMsendCommand aprm = new CMMsendCommand();

		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = aprm.readMeter(AmMeterID, userID);
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}

			if (str == null)
				return;

			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						NodeList list = null;
						String err;
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{
								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									DecimalFormat df = new DecimalFormat("0.00");
									NodeList result = commandBack.getElementsByTagName("result");
									for (int j = 0; j < result.getLength(); j++)
									{
										Element resultElement = (Element) result.item(j);
										
										String dataid = "";
										String meterAddress = "";
										boolean isExecFlag = true;
										if(resultElement.hasAttribute("DataID")){
											dataid = resultElement.getAttribute("DataID");
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
												isExecFlag = false;
											}
										}else {
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if(!meterAddress.equals(xc.getMeterAddress())){
												isExecFlag = false;
											}
										}
										if(isExecFlag){
											//String meterAddress = resultElement.getAttribute("Meter_Address");
											String readTime = resultElement.getAttribute("ValueTime");

											String SYValue = "", TZValue = "", TotoleUsed = "", TotoleBuy = "", BuyTimes = "";
											String protocol = "1";// 默认97规约
											if (resultElement.hasAttribute("SYValue"))// 97表
											{
												cunchu = "1";
												SYValue = resultElement.getAttribute("SYValue");
												TZValue = resultElement.getAttribute("TZValue");
												TotoleUsed = resultElement.getAttribute("TotoleUsed");
												TotoleBuy = resultElement.getAttribute("TotoleBuy");
												BuyTimes = resultElement.getAttribute("BuyTimes");

												SYValue = df.format(df.parse(SYValue));
												TZValue = df.format(df.parse(TZValue));
												TotoleUsed = df.format(df.parse(TotoleUsed));
											} else if(resultElement.hasAttribute("ZValueZY")){//07 非费控  抄总示数
												cunchu = "0";
												readTime = resultElement.getAttribute("ValueTime");
												TotoleUsed = resultElement.getAttribute("ZValueZY");
												TotoleUsed = df.format(df.parse(TotoleUsed));
												String sql = "select  AmMeter_485Address,(select Gather_Address from Gather b where a.Gather_ID=b.Gather_ID)Gather_Address From AmMeter a where AmMeter_ID="
														+ AmMeterID;

												Connection conn = null;
												PreparedStatement ps =null;
												ResultSet rs =null;
												try
												{
													conn = ConnDB.getConnection();
													ps = conn.prepareStatement(sql);
													rs = ps.executeQuery();
													if (rs.next())
													{
														String Gather_Address = rs.getString("Gather_Address");
														String AmMeter_485Address = rs.getString("AmMeter_485Address");
														float ZValue = Float.parseFloat(TotoleUsed) * Float.parseFloat(beiLv);
														if (!(ZValue < 0))
														{
															//存储总示数
															AutoUpDataSaver.dataSave(Gather_Address, "1", AmMeter_485Address, "0C0110", readTime, String.valueOf(ZValue));
														}
													}
													
												}
												catch(Exception e){
													e.printStackTrace();
												}finally{
													ConnDB.release(conn, ps, rs);
												}
												
											}
											else if (resultElement.hasAttribute("SYMoney"))// 07费控表  剩余金额
											{
												cunchu = "1";
												protocol = "30";
												SYValue = resultElement.getAttribute("SYMoney");
												SYValue = df.format(df.parse(SYValue));
												// getReadAmmeter1(resp,AmMeterID,userID,cunchu,beiLv);
												xc = aprm.readMeter07(AmMeterID, userID, "00010000");
												if (xc != null)
												{
													str = null;
													try
													{
														str = sxc.sendXMLToServer(xc);
														System.out.println("收到的报文的回复是：" + str);
													} catch (SocketTimeoutException e)
													{
														jo = new JSONObject();
														jo.put("isError", "2");
														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														// System.out.println(json.toString());
														out.println(json.toString());
														out.flush();
														out.close();
													}

													if (str == null)
														return;
													
													gwList = getNodeList(str, "GW");
													for (int n = 0; n < gwList.getLength(); n++)
													{
														tasknum = (Element) gwList.item(n);
														if (tasknum.hasAttribute("tasknum"))
														{
															task = tasknum.getAttribute("tasknum");
															if(task.equals(xc.getTaskNum())){
																list = getNodeList(str, "commandback");

																for (i = 0; i < list.getLength(); i++)
																{
																	commandBack = (Element) list.item(i);
																	if (commandBack.hasAttribute("error"))
																	{
																		err = commandBack.getAttribute("error");
																		// 失败
																		if (err.equals("1"))
																		{
																			String errormessage = commandBack.getAttribute("errormessage");
																			jo = new JSONObject();
																			jo.put("isError", "3");
																			jo.put("errormessage", errormessage);
																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			// System.out.println(json.toString());
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		} else if (err.equals("0")) // 成功
																		{
																			result = commandBack.getElementsByTagName("result");
																			for (j = 0; j < result.getLength(); j++)
																			{
																				resultElement = (Element) result.item(j);
																				
																				dataid = "";
																				meterAddress = "";
																				isExecFlag = true;
																				if(resultElement.hasAttribute("DataID")){
																					dataid = resultElement.getAttribute("DataID");
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																						isExecFlag = false;
																					}
																				}else {
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if(!meterAddress.equals(xc.getMeterAddress())){
																						isExecFlag = false;
																					}
																				}
																				if(isExecFlag){
																					readTime = resultElement.getAttribute("ValueTime");
																					if (resultElement.hasAttribute("ZValueZY"))// 07表当前电量
																					{
																						TotoleUsed = resultElement.getAttribute("ZValueZY");
																						TotoleUsed = df.format(df.parse(TotoleUsed));
																					}
																				}else{
																					jo = new JSONObject();
																					jo.put("isError", "7");
																					json.add(jo);
																					resp.setContentType("application/x-json");

																					PrintWriter out = resp.getWriter();
																					// System.out.println(json.toString());
																					out.println(json.toString());
																					out.flush();
																					out.close();
																				}
																				
																				
																			}
																		}
																	}
																}
															}else{
																jo = new JSONObject();
																jo.put("isError", "6");
																json.add(jo);
																resp.setContentType("application/x-json");

																PrintWriter out = resp.getWriter();
																// System.out.println(json.toString());
																out.println(json.toString());
																out.flush();
																out.close();
															}
														}
													}
												} else
												{
													jo = new JSONObject();
													jo.put("isError", "1");
													json.add(jo);
													resp.setContentType("application/x-json");

													PrintWriter out = resp.getWriter();
													// System.out.println(json.toString());
													out.println(json.toString());
													out.flush();
													out.close();
												}
												
											}

											jo = new JSONObject();
											jo.put("isError", "0");
											jo.put("SYValue", SYValue);
											jo.put("TotoleUsed", TotoleUsed);
											jo.put("BuyTimes", BuyTimes);
											jo.put("readTime", readTime);
											jo.put("meterPotocol", protocol);
											json.add(jo);

											if ("1".equals(cunchu))
											{
												CMMeterDao dao = new CMMeterDao();
												//System.out.println("TotoleUsed：" + String.valueOf(TotoleUsed));
												//System.out.println("beiLv：" + String.valueOf(beiLv));
												float zValueZY = Float.parseFloat(TotoleUsed) * Float.parseFloat(beiLv);
												float sYValue = 0, tZValue = 0;
												if ("1".equals(protocol))// 97规约
												{
													sYValue = Float.parseFloat(SYValue) * Float.parseFloat(beiLv);
													tZValue = Float.parseFloat(TZValue) * Float.parseFloat(beiLv);
												} else
												// 07规约
												{
													sYValue = Float.parseFloat(SYValue) * Float.parseFloat(beiLv);

													xc = aprm.readMeter07(AmMeterID, userID, "00900201");// 07当前透支金额
													if (xc != null)
													{
														try
														{
															str = sxc.sendXMLToServer(xc);
															System.out.println("收到的报文的回复是：" + str);
															gwList = getNodeList(str, "GW");
															for (m = 0; m < gwList.getLength(); m++)
															{
																tasknum = (Element) gwList.item(m);
																if (tasknum.hasAttribute("tasknum"))
																{
																	task = tasknum.getAttribute("tasknum");
																	if(task.equals(xc.getTaskNum())){
																		list = getNodeList(str, "commandback");
																		commandBack = (Element) list.item(0);
																		
																		err = getAttrValue(str, "commandback", "error");
																		if ("0".equals(err))
																		{
																			result = commandBack.getElementsByTagName("result");
																			resultElement = (Element) result.item(0);
																			dataid = "";
																			meterAddress = "";
																			isExecFlag = true;
																			if(resultElement.hasAttribute("DataID")){
																				dataid = resultElement.getAttribute("DataID");
																				if(resultElement.hasAttribute("Meter_Address")){
																					meterAddress = resultElement.getAttribute("Meter_Address");
																				}
																				if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																					isExecFlag = false;
																				}
																			}else {
																				if(resultElement.hasAttribute("Meter_Address")){
																					meterAddress = resultElement.getAttribute("Meter_Address");
																				}
																				if(!meterAddress.equals(xc.getMeterAddress())){
																					isExecFlag = false;
																				}
																			}
																			if(isExecFlag){
																				TZValue = getAttrValue(str, "result", "TZMoney");
																				TZValue = df.format(df.parse(TZValue));
																				tZValue = Float.parseFloat(TZValue) * Float.parseFloat(beiLv);
																			}else{
																				jo = new JSONObject();
																				jo.put("isError", "7");// 返回任务编号错误
																				json.add(jo);
																				resp.setContentType("application/x-json");
																				PrintWriter out = resp.getWriter();
																				out.println(json.toString());
																				out.flush();
																				out.close();
																			}
																			
																			
																		}
																	}else{
																		jo = new JSONObject();
																		jo.put("isError", "6");// 返回任务编号错误
																		json.add(jo);
																		resp.setContentType("application/x-json");
																		PrintWriter out = resp.getWriter();
																		out.println(json.toString());
																		out.flush();
																		out.close();
																	}
																}
															}
															
														} catch (SocketTimeoutException e)
														{
															e.printStackTrace();
														}
													}
												}
												dao.SerSaveData(AmMeterID, zValueZY, sYValue, tZValue, readTime);
											}

											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}else{
											jo = new JSONObject();
											jo.put("isError", "7");
											json.add(jo);
											resp.setContentType("application/x-json");
											PrintWriter out = resp.getWriter();
											out.println(json.toString());
											out.flush();
											out.close();
										}
										
										

									}
								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");// 返回任务编号错误
						json.add(jo);
						resp.setContentType("application/x-json");
						PrintWriter out = resp.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}
			
			
			

		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}
	}

	/**
	 * 07表集抄电表第二个命令
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	// private void getReadAmmeter1(HttpServletResponse resp, int AmMeterID,
	// String userID, String cunchu, String beiLv) throws Exception
	// {
	// JSONArray json = new JSONArray();
	// JSONObject jo = null;
	//
	// CMMsendCommand aprm = new CMMsendCommand();
	//
	// SendingXMLCode sxc = new SendingAPCode();
	// XMLCoder xc = aprm.readMeter07(AmMeterID, userID, "00010000");
	// if (xc != null)
	// {
	// String str = null;
	// try
	// {
	// str = sxc.sendXMLToServer(xc);
	// System.out.println("收到的报文的回复是：" + str);
	// } catch (SocketTimeoutException e)
	// {
	// jo = new JSONObject();
	// jo.put("isError", "2");
	// json.add(jo);
	// resp.setContentType("application/x-json");
	//
	// PrintWriter out = resp.getWriter();
	// // System.out.println(json.toString());
	// out.println(json.toString());
	// out.flush();
	// out.close();
	// }
	//
	// if (str == null)
	// return;
	//
	// NodeList list = null;
	// String err;
	// list = getNodeList(str, "commandback");
	//
	// for (int i = 0; i < list.getLength(); i++)
	// {
	// Element commandBack = (Element) list.item(i);
	// if (commandBack.hasAttribute("error"))
	// {
	// err = commandBack.getAttribute("error");
	// // 失败
	// if (err.equals("1"))
	// {
	// String errormessage = commandBack.getAttribute("errormessage");
	// jo = new JSONObject();
	// jo.put("isError", "3");
	// jo.put("errormessage", errormessage);
	// json.add(jo);
	// resp.setContentType("application/x-json");
	//
	// PrintWriter out = resp.getWriter();
	// // System.out.println(json.toString());
	// out.println(json.toString());
	// out.flush();
	// out.close();
	// } else if (err.equals("0")) // 成功
	// {
	// DecimalFormat df = new DecimalFormat("0.00");
	// NodeList result = commandBack.getElementsByTagName("result");
	// for (int j = 0; j < result.getLength(); j++)
	// {
	// Element resultElement = (Element) result.item(j);
	// String readTime = resultElement.getAttribute("ValueTime");
	// String TotoleUsed = "";
	// if (resultElement.hasAttribute("ZValueZY"))// 07表当前电量
	// {
	// TotoleUsed = resultElement.getAttribute("ZValueZY");
	// TotoleUsed = df.format(df.parse(TotoleUsed));
	// }
	//
	// jo = new JSONObject();
	// jo.put("isError", "0");
	// jo.put("TotoleUsed", TotoleUsed);
	// jo.put("readTime", readTime);
	// json.add(jo);
	//
	// if ("1".equals(cunchu))
	// {
	// CMMeterDao dao = new CMMeterDao();
	// float zValueZY07 = Float.parseFloat(TotoleUsed) *
	// Float.parseFloat(beiLv);
	// dao.SerSaveData(AmMeterID, zValueZY07, -1, readTime);
	// }
	//
	// resp.setContentType("application/x-json");
	//
	// PrintWriter out = resp.getWriter();
	// // System.out.println(json.toString());
	// out.println(json.toString());
	// out.flush();
	// out.close();
	// }
	// }
	// }
	// }
	// } else
	// {
	// jo = new JSONObject();
	// jo.put("isError", "1");
	// json.add(jo);
	// resp.setContentType("application/x-json");
	//
	// PrintWriter out = resp.getWriter();
	// // System.out.println(json.toString());
	// out.println(json.toString());
	// out.flush();
	// out.close();
	// }
	// }

	/**
	 * 抄读恶性负载
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void getReadEXFZ(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		String meterPotocol = (new CMMeterDao()).getMeterPotocol(AmMeterID);
		CMMsendCommand aprm = new CMMsendCommand();

		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = aprm.readMeterEXFZ(AmMeterID, userID);
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的回复是" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}
			// System.out.println("收到的报文的回复是："+str);
			if (str == null)
				return;
			NodeList list = null;
			String err;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{

								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									DecimalFormat df = new DecimalFormat("0.##");
									NodeList result = commandBack.getElementsByTagName("result");
									for (int j = 0; j < result.getLength(); j++)
									{
										Element resultElement = (Element) result.item(j);
										
										String dataid = "";
										String meterAddress = "";
										boolean isExecFlag = true;
										if(resultElement.hasAttribute("DataID")){
											dataid = resultElement.getAttribute("DataID");
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
												isExecFlag = false;
											}
										}else {
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if(!meterAddress.equals(xc.getMeterAddress())){
												isExecFlag = false;
											}
										}
										if(isExecFlag){
											String retInfo = "";
											//String Flag = resultElement.getAttribute("Flag");
											//String Meter_Address = resultElement.getAttribute("Meter_Address");
											//String ValueTime = resultElement.getAttribute("ValueTime");
											if("1".equals(meterPotocol)){//97
												String Flag = resultElement.getAttribute("Flag");
												String Vol = resultElement.getAttribute("Vol");
												if("1".equals(Flag)){
													retInfo += "恶性负载分闸；";
												}else{
													retInfo += "非恶性负载分闸；";
												}
												retInfo += "恶性负载限值" + Vol + "W";
											}else{//07
												//恶载时年月日时分秒
												String ValueTime = resultElement.getAttribute("ValueTime");
												//恶载时当前正向有功总电能
												String ZValueZY = resultElement.getAttribute("ZValueZY");
												//恶载时当前反向有功总电能
												String ZValueFY = resultElement.getAttribute("ZValueFY");
												//恶载前电压电流瞬时功率
												String BFVoltage = resultElement.getAttribute("BFVoltage");
												String BFCurrent = resultElement.getAttribute("BFCurrent");
												String BFPower = resultElement.getAttribute("BFPower");
												//恶载时电压电流瞬时功率
												String AFTVoltage = resultElement.getAttribute("AFTVoltage");
												String AFTCurrent = resultElement.getAttribute("AFTCurrent");
												String AFTPower = resultElement.getAttribute("AFTPower");
												//恶载时功率因数
												String PowerFactor = resultElement.getAttribute("PowerFactor");
												
												retInfo += "恶性负载时间:" + ValueTime
														+ ",恶性负载时当前正向有功总电能:" + ZValueZY
														+ "KWh,恶性负载时当前反向有功总电能:" + ZValueFY
														+ "KWh,恶性负载前电压电流瞬时功率:" + BFVoltage
														+ "V " + BFCurrent
														+ "A " + BFPower
														+ "kW,恶性负载时电压电流瞬时功率:" + AFTVoltage
														+ "V " + AFTCurrent
														+ "A " + AFTPower
														+ "kW,恶性负载时功率因数:" + PowerFactor;
											}
											
											jo = new JSONObject();
											jo.put("isError", "0");
											jo.put("info", retInfo);
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}else{
											jo = new JSONObject();
											jo.put("isError", "7");
											jo.put("info", "返回表地址或命令码错误！");
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}
										
									}
								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						jo.put("info", "任务编号错误！");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}
			
			

		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 抄读状态字
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void getReadStateNum(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		CMMsendCommand aprm = new CMMsendCommand();

		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = aprm.readMeterState(AmMeterID, userID);
		if (xc != null)
		{
			String str = null;
			try
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的回复是" + str);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				// System.out.println(json.toString());
				out.println(json.toString());
				out.flush();
				out.close();
			}
			// System.out.println("收到的报文的回复是："+str);
			if (str == null)
				return;
			NodeList list = null;
			String err;
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xc.getTaskNum())){
						list = getNodeList(str, "commandback");

						for (int i = 0; i < list.getLength(); i++)
						{
							Element commandBack = (Element) list.item(i);
							if (commandBack.hasAttribute("error"))
							{

								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", "3");
									jo.put("errormessage", errormessage);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									// System.out.println(json.toString());
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									DecimalFormat df = new DecimalFormat("0.##");
									NodeList result = commandBack.getElementsByTagName("result");
									for (int j = 0; j < result.getLength(); j++)
									{
										Element resultElement = (Element) result.item(j);
										
										String dataid = "";
										String meterAddress = "";
										boolean isExecFlag = true;
										if(resultElement.hasAttribute("DataID")){
											dataid = resultElement.getAttribute("DataID");
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
												isExecFlag = false;
											}
										}else {
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if(!meterAddress.equals(xc.getMeterAddress())){
												isExecFlag = false;
											}
										}
										if(isExecFlag){
											String Flag = resultElement.getAttribute("Flag");
											String Meter_Address = resultElement.getAttribute("Meter_Address");
											String ValueTime = resultElement.getAttribute("ValueTime");
											String sql = null;
											sql = "update AmMeter set IsSupply='"+Flag+"' where Ammeter_ID=" + AmMeterID;
											/*if ("00".equals(Flag))
											{
												sql = "update AmMeter set IsSupply='11' where Ammeter_ID=" + AmMeterID;

											} else if ("01".equals(Flag))
											{
												sql = "update AmMeter set IsSupply='10' where Ammeter_ID=" + AmMeterID;
											} else if ("10".equals(Flag))
											{
												sql = "update AmMeter set IsSupply='01' where Ammeter_ID=" + AmMeterID;
											} else if ("11".equals(Flag))
											{
												sql = "update AmMeter set IsSupply='00' where Ammeter_ID=" + AmMeterID;
											}*/
											PreparedStatement ps = null;
											// System.out.println("抄读状态:  "+sql);
											if (sql != null)
											{
												Connection conn = null;
//												PreparedStatement ps = null;
												try
												{
													conn = ConnDB.getConnection();
													ps = conn.prepareStatement(sql);
													ps.executeUpdate();

												} catch (SQLException e)
												{
													e.printStackTrace();
												} finally
												{
													ConnDB.release(conn, ps);
												}
												// System.out.println("执行抄读状态:  "+sql);
//												ps = ConnDB.getConnection().prepareStatement(sql);
//												ps.executeUpdate();
											}
											if (ps != null)
												ps.close();

											jo = new JSONObject();
											jo.put("isError", "0");
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}else{
											jo = new JSONObject();
											jo.put("isError", "7");
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											// System.out.println(json.toString());
											out.println(json.toString());
											out.flush();
											out.close();
										}
										
									}
								}

							}
						}
					}else{
						jo = new JSONObject();
						jo.put("isError", "6");
						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						// System.out.println(json.toString());
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}
			

		} else
		{
			jo = new JSONObject();
			jo.put("isError", "1");
			json.add(jo);
			resp.setContentType("application/x-json");

			PrintWriter out = resp.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * 调整房间人数
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void personAdjust(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		
		String info = (new CMMeterDao()).getAmMeterByID(AmMeterID+"");
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "集中监测", "修改房间人数,"+info);
		
		// 现住人数
		int personnum = Integer.parseInt(req.getParameter("personnum"));
		// 房间容量
		int roomSpace = Integer.parseInt(req.getParameter("roomSpace"));
		// 是否自动执行退补操作1代表是，0代表否
		int check = Integer.parseInt(req.getParameter("check"));

		CMMeterDao dao = new CMMeterDao();
		JSONArray json = dao.updateManCount(AmMeterID, personnum, roomSpace, check);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 集中监测中获得两个月的用电情况
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws ParseException
	 * @throws IOException
	 */
	private void getAll_TwoMonth_ChartInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ParseException, IOException
	{
		int AmMeterID = Integer.parseInt(req.getParameter("Amm"));
		String date = req.getParameter("date");
		CMMeterDao dao = new CMMeterDao();

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		Date end = getEndDay(date);
		Date start = getStartDay(date);

		HashMap<String, String> dataset = dao.getTwoMonthData(AmMeterID, start, end);

		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String temDate = df2.format(start);
		for (int i = 0; i < dataset.size(); i++)
		{
			jo = new JSONObject();
			jo.put("date", temDate);
			jo.put("value", dataset.get(temDate));
			json.add(jo);
			cal.setTime(df2.parse(temDate));
			cal.add(Calendar.DATE, 1);
			temDate = df2.format(cal.getTime());

		}
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private Date getStartDay(String date) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(df.parse(date));

		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);

		return cal.getTime();

	}

	private Date getEndDay(String date) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(df.parse(date));
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);

		return cal.getTime();
	}

	/**
	 * 集中监测中获得电表的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getAll_Ammeter_InfoForJZJC(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ParseException
	{
		int AmmID = Integer.parseInt(req.getParameter("Amm"));
		CMMeterDao dao = new CMMeterDao();
		JSONArray json = dao.getAll_Ammeter_InfoForJZJC(AmmID);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 批量设置中的其他操作的执行函数
	 * 
	 * @param req
	 * @param resp
	 */
	private void qiTaCaoZuo(HttpServletRequest req, HttpServletResponse resp)
	{
		String allOrSelect = req.getParameter("comand");
		String commandStyle = req.getParameter("commandStyle");
	}

	/**
	 * 批量设置中的临时调剂的执行函数
	 * 
	 * @param req
	 * @param resp
	 */
	private void linShiTiaoJi(HttpServletRequest req, HttpServletResponse resp)
	{
		String allOrSelect = req.getParameter("comand");
		String tjLiang = req.getParameter("tjLiang");
	}

	/**
	 * 统一月补的按钮执行函数
	 * 
	 * @param req
	 * @param resp
	 */
	private void tongYiYueBu(HttpServletRequest req, HttpServletResponse resp)
	{

		String allOrSelect = req.getParameter("comand");
	}

	/**
	 * 查询批量操作的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws SQLException
	 */
	private void get_info_VS(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{

		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int price = Integer.parseInt(req.getParameter("price"));
		int flag = Integer.parseInt(req.getParameter("flag"));
		int AmMeterStatus = Integer.parseInt(req.getParameter("AmMeterStatus"));
		int floorID = 0;
		if (flag == 3)
		{
			floorID = Integer.parseInt(req.getParameter("FloorID"));
		}
		int Id = Integer.parseInt(req.getParameter("Id"));

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		String selectInfo = "";
		if (flag == 0)
			selectInfo += "all|";
		else if (flag == 1)
			selectInfo += "area|" + Id;
		else if (flag == 2)
			selectInfo += "arch|" + Id;
		else if (flag == 3)
			selectInfo += "floor|" + Id + "|" + floorID;
		else if (flag == 4)
			selectInfo += "meter|" + Id;

		VolumeSetDao vsd = new VolumeSetDao();
		ArrayList<VolumeSetModel> list = new ArrayList<VolumeSetModel>();
		list = vsd.queryInfo(thePageCount, thePageIndex, selectInfo, true, tableName, order, price, AmMeterStatus);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", vsd.getTotalCount());
		json.add(jo);

		for (VolumeSetModel vsm : list)
		{
			jo = new JSONObject();

			CMMeterDao dao = new CMMeterDao();
			String meterPotocol = dao.getMeterPotocol(vsm.getAmMeter_ID());// 默认97
			String AmMeter_CostType = dao.getMeterCostType(vsm.getAmMeter_ID());
			/*String sqlString = "select a.AMMETER_COSTTYPE,b.TEXINGVALUE from Ammeter a LEFT JOIN TEXINGVALUE b on (a.METESTYLE_ID=b.METESTYLE_ID) where a.AMMETER_ID="
					+ vsm.getAmMeter_ID() + " and b.METERTEXING_ID=18";*/		
//			PreparedStatement pst = ConnDB.getConnection().prepareStatement(sqlString);
//			ResultSet rSet = pst.executeQuery();
//			if (rSet.next())
//			{
//				meterPotocol = rSet.getString("TexingValue");
//				AmMeter_CostType = rSet.getString("AMMETER_COSTTYPE");
//			}
//			if (rSet != null)
//				rSet.close();
//			if (pst != null)
//				pst.close();

			jo.put("meterPotocol", meterPotocol);
			jo.put("AmMeter_CostType", AmMeter_CostType);

			jo.put("AmMeter_ID", vsm.getAmMeter_ID());
			jo.put("Architecture_Name", vsm.getArchitecture_Name());
			jo.put("FloorName", vsm.getFloorName());
			jo.put("AmMeter_Name", vsm.getAmMeter_Name());
			jo.put("Price", vsm.getPrice());
			jo.put("SYValue", vsm.getSYValue());
			jo.put("METESTYLE_NAME", vsm.getMeteStyle_Name());
			jo.put("APState", vsm.getAPState());
			jo.put("AmMeter_Stat", vsm.getAmMeter_Stat());
			jo.put("IsSupply", vsm.getIsSupply());

			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void delete_SD_info(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String IDs = req.getParameter("delete_SD_info_ID");
		String[] id = IDs.split("\\|");

		SaleDetailsDao sdd = new SaleDetailsDao();

		// 统计删除插入条数
		int result = 0;

		for (int i = 0; i < id.length; i++)
		{
			if (sdd.delete(id[i]))
			{
				result++;
			}
		}
		PrintWriter out = resp.getWriter();
		out.println("成功删除" + result + "条数据。");
		out.flush();
		out.close();

	}

	/**
	 * 根据条件查询售电明细的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void check_info_SD(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int flag = Integer.parseInt(req.getParameter("flag"));
		int floorID = 0;
		if (flag == 3)
		{
			floorID = Integer.parseInt(req.getParameter("FloorID"));
		}
		int Id = Integer.parseInt(req.getParameter("Id"));

		String startDateTime = req.getParameter("startDateTime");
		String endDateTime = req.getParameter("endDateTime");
		String state = req.getParameter("state");
		String style = req.getParameter("style");
		String weixinOrder = null;
		if(style.equals("6")){
			weixinOrder = req.getParameter("weixinOrder");
		}
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		String selectInfo = "";
		String queryMsg = "";
		if (flag == 0)
			selectInfo += "all|";
		else if (flag == 1)
			selectInfo += "area|" + Id;
		else if (flag == 2)
			selectInfo += "arch|" + Id;
		else if (flag == 3)
			selectInfo += "floor|" + Id + "|" + floorID;
		else if (flag == 4)
			selectInfo += "meter|" + Id;
		if(weixinOrder!=null){
			queryMsg = startDateTime + "|" + endDateTime + "|" + state + "|" + style+"|"+weixinOrder;
		}else{
			queryMsg = startDateTime + "|" + endDateTime + "|" + state + "|" + style;
		}
		//queryMsg = startDateTime + "|" + endDateTime + "|" + state + "|" + style;
		SaleDetailsDao sdd = new SaleDetailsDao();
		ArrayList<SaleDetailsModel> list = new ArrayList<SaleDetailsModel>();
		list = sdd.queryInfo(thePageCount, thePageIndex, selectInfo, queryMsg, true, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", sdd.getTotalCount());
		json.add(jo);

		for (SaleDetailsModel sdm : list)
		{
			jo = new JSONObject();

			CMMeterDao dao = new CMMeterDao();
			String meterPotocol = dao.getMeterPotocol(sdm.getAmMeter_ID());

			jo.put("meterPotocol", meterPotocol);

			jo.put("amMeterID", sdm.getAmMeter_ID());
			jo.put("saleInfoNum", sdm.getSALEINFONUM());
			jo.put("orderNo", sdm.getORDERNO());
			jo.put("studentID", sdm.getSTUDENTID());
			jo.put("sign", sdm.getSign());
			jo.put("archName", sdm.getArchitecture_Name());
			jo.put("floorName", sdm.getFloorName());
			jo.put("amMeterName", sdm.getAmMeter_Name());
			jo.put("kindName", sdm.getKindName());
//			jo.put("Price_ID", sdm.getPrice_ID());
			jo.put("Price_Name", sdm.getPrice_Name());
			jo.put("status", sdm.getStatus());
			jo.put("buyTime", sdm.getBuyTime());
			jo.put("price", sdm.getPrice());
			jo.put("theGross", sdm.getTheGross());
			jo.put("theMoney", sdm.getTheMoney());
			jo.put("sendTime", sdm.getSendTime());
			jo.put("QSYValue", sdm.getQSYValue());
			jo.put("HSYValue", sdm.getHSYValue());
			jo.put("userName", sdm.getUsers_Name());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void check_info_SDWater(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{

		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int flag = Integer.parseInt(req.getParameter("flag"));
		int floorID = 0;
		if (flag == 3)
		{
			floorID = Integer.parseInt(req.getParameter("FloorID"));
		}
		int Id = Integer.parseInt(req.getParameter("Id"));

		String startDateTime = req.getParameter("startDateTime");
		String endDateTime = req.getParameter("endDateTime");
		
		String style = req.getParameter("style");
		String weixinOrder = null;
		if(style.equals("6")){
			weixinOrder = req.getParameter("weixinOrder");
		}
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		String selectInfo = "";
		String queryMsg = "";
		if (flag == 0)
			selectInfo += "all|";
		else if (flag == 1)
			selectInfo += "area|" + Id;
		else if (flag == 2)
			selectInfo += "arch|" + Id;
		else if (flag == 3)
			selectInfo += "floor|" + Id + "|" + floorID;
		else if (flag == 4)
			selectInfo += "meter|" + Id;
		if(weixinOrder!=null){
			queryMsg = startDateTime + "|" + endDateTime +  "|" + style+"|"+weixinOrder;
		}else{
			queryMsg = startDateTime + "|" + endDateTime + "|" + style;
		}
		//queryMsg = startDateTime + "|" + endDateTime + "|" + state + "|" + style;
		SaleDetailsDao sdd = new SaleDetailsDao();
		ArrayList<SaleDetailsModel> list = new ArrayList<SaleDetailsModel>();
		list = sdd.queryInfoWater(thePageCount, thePageIndex, selectInfo, queryMsg, true, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", sdd.getTotalCount());
		json.add(jo);

		for (SaleDetailsModel sdm : list)
		{
			jo = new JSONObject();

			CMMeterDao dao = new CMMeterDao();
			
			jo.put("waMeterID", sdm.getAmMeter_ID());
			jo.put("saleInfoNum", sdm.getSALEINFONUM());
			jo.put("orderNo", sdm.getORDERNO());
			jo.put("studentID", sdm.getSTUDENTID());
			jo.put("sign", sdm.getSign());
			jo.put("archName", sdm.getArchitecture_Name());
			jo.put("floorName", sdm.getFloorName());
			jo.put("amMeterName", sdm.getAmMeter_Name());
			jo.put("kindName", sdm.getKindName());
//			jo.put("Price_ID", sdm.getPrice_ID());
			jo.put("Price_Name", sdm.getPrice_Name());
			jo.put("buyTime", sdm.getBuyTime());
			jo.put("price", sdm.getPrice());
			jo.put("theMoney", sdm.getTheMoney());
			jo.put("userName", sdm.getUsers_Name());
			jo.put("starValue", sdm.getQSYValue());
			jo.put("endValue", sdm.getHSYValue());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	
	}
	
	
	/**
	 * 充值明细-导出功能
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void gd_out_btn_click(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");
		String SelectFloorID = req.getParameter("SelectFloorID");
		String startDateTime = req.getParameter("startDateTime");
		String endDateTime = req.getParameter("endDateTime");
		String selectInfo = "";
		String queryMsg = "";
		if ("".equals(startDateTime) || "".equals(endDateTime))
		{
			queryMsg = "";
		} else
		{
			queryMsg = startDateTime + "|" + endDateTime;
		}

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		if ("0".equals(SelectTreeFlag))
		{// 全校
			selectInfo += "all|";
		} else if ("1".equals(SelectTreeFlag))
		{// 区域
			selectInfo += "area|" + SelectTreeID;
		} else if ("2".equals(SelectTreeFlag))
		{// 建筑
			selectInfo += "arch|" + SelectTreeID;
		} else if ("3".equals(SelectTreeFlag))
		{// 楼层
			selectInfo += "floor|" + SelectTreeID + "|" + SelectFloorID;
		} else if ("4".equals(SelectTreeFlag))
		{// 电表
			selectInfo += "meter|" + SelectTreeID;
		}

		YuGouDetailsDao dao = new YuGouDetailsDao();
		ArrayList<YuGouDetailsModel> list = new ArrayList<YuGouDetailsModel>();
		list = dao.queryInfo(0, 0, selectInfo, queryMsg, false, tableName, order);

		String filePath = "/img/gdDetail.xls";
		String XlsPath = this.getServletContext().getRealPath(filePath);

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
		cell.setCellValue("充值编号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("建筑");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("楼层");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("房间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("购买时间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("购买次数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setCellValue("购买金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 7);
		cell.setCellValue("购买前剩余金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 8);
		cell.setCellValue("购买后剩余金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 9);
		cell.setCellValue("充值人");
		cell.setCellStyle(style);

		int i = 0;
		for (YuGouDetailsModel ygdm : list)
		{
			i++;
			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(ygdm.getAMMGDINFONUM());
			row.createCell((short) 1).setCellValue(ygdm.getArchitecture_Name());
			row.createCell((short) 2).setCellValue(ygdm.getFloorName());
			row.createCell((short) 3).setCellValue(ygdm.getAmMeter_Name());
			row.createCell((short) 4).setCellValue(ygdm.getReadTime());
			row.createCell((short) 5).setCellValue(ygdm.getBuyTimes());
			row.createCell((short) 6).setCellValue(ygdm.getThisPurchaseMoney());
			row.createCell((short) 7).setCellValue(ygdm.getLastRemainMoney());
			row.createCell((short) 8).setCellValue(ygdm.getThisRemainMoney());
			row.createCell((short) 9).setCellValue(ygdm.getUsers_Name());
		}

		FileOutputStream fout = new FileOutputStream(XlsPath);
		wb.write(fout);
		fout.close();

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("filePath", filePath);

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());

	}
	
	/**
	 * 批量结算
	 * @throws ParseException 
	 */
	private void All_Jiesuan_click(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ParseException
	{
		SaleDetailsDao sdd = new SaleDetailsDao();
		String resultInfo = sdd.getAllWaterMeterJieSuan();
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("info", resultInfo);
		json.add(jo);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	private void ReJiesuan_click(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException{
		int watermeterid = 0;
		if(req.getParameter("watermeterid")!=null&&!req.getParameter("watermeterid").equals("")){
			watermeterid = Integer.parseInt(req.getParameter("watermeterid"));
		}
		SaleDetailsDao sdd = new SaleDetailsDao();
		String info = sdd.waMeterReJieSuan(watermeterid);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("info", info);
		json.add(jo);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 结算选定区域、建筑、房间
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void Jiesuan_click(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ParseException
	{
		int watermeterid = 0;
		if(req.getParameter("watermeterid")!=null&&!req.getParameter("watermeterid").equals("")){
			watermeterid = Integer.parseInt(req.getParameter("watermeterid"));
		}
		int archid = 0;
		if(req.getParameter("archid")!=null&&!req.getParameter("archid").equals("")){
			archid = Integer.parseInt(req.getParameter("archid"));
		}
		int floorid = 0;
		if(req.getParameter("floorid")!=null&&!req.getParameter("floorid").equals("")){
			floorid = Integer.parseInt(req.getParameter("floorid"));
		}
		int areaid = 0;
		if(req.getParameter("areaid")!=null&&!req.getParameter("areaid").equals("")){
			areaid = Integer.parseInt(req.getParameter("areaid"));
		}
//		System.out.println("watermeterid:"+watermeterid+" archid:"+archid
//				+" floorid:"+floorid+" areaid:"+areaid);
		SaleDetailsDao sdd = new SaleDetailsDao();
		String resultInfo = sdd.getWaterMeterJieSuan(areaid, archid, floorid, watermeterid);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("info", resultInfo);
		json.add(jo);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 导出--水表导入数据的模板
	 */
	private void UpLoadInputFile_click(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int flag = Integer.parseInt(req.getParameter("flag"));
		int floorID = 0;
		if (flag == 3)
		{
			floorID = Integer.parseInt(req.getParameter("FloorID"));
		}
		int Id = Integer.parseInt(req.getParameter("Id"));

		String selectInfo = "";
		if (flag == 0)
			selectInfo += "all|";
		else if (flag == 1)
			selectInfo += "area|" + Id;
		else if (flag == 2)
			selectInfo += "arch|" + Id;
		else if (flag == 3)
			selectInfo += "floor|" + Id + "|" + floorID;
		else if (flag == 4)
			selectInfo += "meter|" + Id;

		SaleDetailsDao sdd = new SaleDetailsDao();
		ArrayList<SaleDetailsModel> list = new ArrayList<SaleDetailsModel>();
		list = sdd.queryInfoWaterInput(selectInfo);

		String filePath = "/img/WaterInputFile.xls";
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
		cell.setCellValue("房间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("表地址");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("起始示数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("终止示数");
		cell.setCellStyle(style);

		int i = 0;
		for (SaleDetailsModel sdm : list)
		{
			i++;

			CMMeterDao dao = new CMMeterDao();
			
			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(sdm.getAmMeter_Name());
			row.createCell((short) 1).setCellValue(sdm.getAmMeter_485());
			row.createCell((short) 2).setCellValue(sdm.getTheGross());
		}

		FileOutputStream fout = new FileOutputStream(XlsPath);
		wb.write(fout);
		fout.close();

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("filePath", filePath);

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
	
	
	}
	
		
	/**
	 * 水费明细导出
	 */
	private void SDWater_out_btn_click(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{

		int flag = Integer.parseInt(req.getParameter("flag"));
		int floorID = 0;
		if (flag == 3)
		{
			floorID = Integer.parseInt(req.getParameter("FloorID"));
		}
		int Id = Integer.parseInt(req.getParameter("Id"));

		String isSelect = req.getParameter("isSelect");
		String startDateTime = req.getParameter("startDateTime");
		String endDateTime = req.getParameter("endDateTime");
		String state = req.getParameter("state");
		String saleStyle = req.getParameter("style");
		String weixinOrder = null;
		if(saleStyle.equals("6")){
			weixinOrder = req.getParameter("weixinOrder");
		}
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		String selectInfo = "";
		String queryMsg = "";
		if (flag == 0)
			selectInfo += "all|";
		else if (flag == 1)
			selectInfo += "area|" + Id;
		else if (flag == 2)
			selectInfo += "arch|" + Id;
		else if (flag == 3)
			selectInfo += "floor|" + Id + "|" + floorID;
		else if (flag == 4)
			selectInfo += "meter|" + Id;

		if ("1".equals(isSelect))
		{
			if(weixinOrder!=null){
				queryMsg = startDateTime + "|" + endDateTime + "|" + saleStyle+"|"+weixinOrder;
			}else{
				queryMsg = startDateTime + "|" + endDateTime + "|" + saleStyle;
			}
			//queryMsg = startDateTime + "|" + endDateTime + "|" + state + "|" + saleStyle;
		}

		SaleDetailsDao sdd = new SaleDetailsDao();
		ArrayList<SaleDetailsModel> list = new ArrayList<SaleDetailsModel>();
		list = sdd.queryInfoWater(0, 0, selectInfo, queryMsg, false, tableName, order);

		String filePath = "/img/SailDetailWater.xls";
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
		cell.setCellValue("售水编号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("卡号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("建筑");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("楼层");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("房间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("售水类型");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 6);
		cell.setCellValue("水费类型");
		cell.setCellStyle(style);

		cell = row.createCell((short) 7);
		cell.setCellValue("购买时间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 8);
		cell.setCellValue("单价");
		cell.setCellStyle(style);

		cell = row.createCell((short) 9);
		cell.setCellValue("购买金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 10);
		cell.setCellValue("起始示数");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 11);
		cell.setCellValue("终止示数");
		cell.setCellStyle(style);

		int i = 0;
		for (SaleDetailsModel sdm : list)
		{
			i++;

			CMMeterDao dao = new CMMeterDao();
			String meterPotocol = dao.getMeterPotocol(sdm.getAmMeter_ID());
			
			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(sdm.getSALEINFONUM());
			row.createCell((short) 1).setCellValue(sdm.getSTUDENTID());
			row.createCell((short) 2).setCellValue(sdm.getArchitecture_Name());
			row.createCell((short) 3).setCellValue(sdm.getFloorName());
			row.createCell((short) 4).setCellValue(sdm.getAmMeter_Name());
			row.createCell((short) 5).setCellValue(sdm.getKindName());
			row.createCell((short) 6).setCellValue(sdm.getPrice_Name());
			row.createCell((short) 7).setCellValue(sdm.getBuyTime());
			row.createCell((short) 8).setCellValue(sdm.getPrice());
			row.createCell((short) 9).setCellValue(sdm.getTheMoney());
			row.createCell((short) 10).setCellValue(sdm.getQSYValue());
			row.createCell((short) 11).setCellValue(sdm.getHSYValue());

		}

		FileOutputStream fout = new FileOutputStream(XlsPath);
		wb.write(fout);
		fout.close();

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("filePath", filePath);

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
	
	}

	/**
	 * 售电明细-导出功能
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void SD_out_btn_click(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int flag = Integer.parseInt(req.getParameter("flag"));
		int floorID = 0;
		if (flag == 3)
		{
			floorID = Integer.parseInt(req.getParameter("FloorID"));
		}
		int Id = Integer.parseInt(req.getParameter("Id"));

		String isSelect = req.getParameter("isSelect");
		String startDateTime = req.getParameter("startDateTime");
		String endDateTime = req.getParameter("endDateTime");
		String state = req.getParameter("state");
		String saleStyle = req.getParameter("style");
		String weixinOrder = null;
		if(saleStyle.equals("6")){
			weixinOrder = req.getParameter("weixinOrder");
		}
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		String selectInfo = "";
		String queryMsg = "";
		if (flag == 0)
			selectInfo += "all|";
		else if (flag == 1)
			selectInfo += "area|" + Id;
		else if (flag == 2)
			selectInfo += "arch|" + Id;
		else if (flag == 3)
			selectInfo += "floor|" + Id + "|" + floorID;
		else if (flag == 4)
			selectInfo += "meter|" + Id;

		if ("1".equals(isSelect))
		{
			if(weixinOrder!=null){
				queryMsg = startDateTime + "|" + endDateTime + "|" + state + "|" + saleStyle+"|"+weixinOrder;
			}else{
				queryMsg = startDateTime + "|" + endDateTime + "|" + state + "|" + saleStyle;
			}
			//queryMsg = startDateTime + "|" + endDateTime + "|" + state + "|" + saleStyle;
		}

		SaleDetailsDao sdd = new SaleDetailsDao();
		ArrayList<SaleDetailsModel> list = new ArrayList<SaleDetailsModel>();
		list = sdd.queryInfo(0, 0, selectInfo, queryMsg, false, tableName, order);

		String filePath = "/img/SailDetail.xls";
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
		cell.setCellValue("售电编号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("卡号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("建筑");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("楼层");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("房间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("售电类型");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 6);
		cell.setCellValue("电费类型");
		cell.setCellStyle(style);

		cell = row.createCell((short) 7);
		cell.setCellValue("下发状态");
		cell.setCellStyle(style);

		cell = row.createCell((short) 8);
		cell.setCellValue("购买时间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 9);
		cell.setCellValue("单价");
		cell.setCellStyle(style);

		cell = row.createCell((short) 10);
		cell.setCellValue("购买电量");
		cell.setCellStyle(style);

		cell = row.createCell((short) 11);
		cell.setCellValue("购买金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 12);
		cell.setCellValue("下发时间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 13);
		cell.setCellValue("下发前剩余电量");
		cell.setCellStyle(style);

		cell = row.createCell((short) 14);
		cell.setCellValue("下发后剩余电量");
		cell.setCellStyle(style);

		cell = row.createCell((short) 15);
		cell.setCellValue("下发前剩余金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 16);
		cell.setCellValue("下发后剩余金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 17);
		cell.setCellValue("售电人");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 18);
		cell.setCellValue("订单编号");
		cell.setCellStyle(style);

		int i = 0;
		for (SaleDetailsModel sdm : list)
		{
			i++;

			CMMeterDao dao = new CMMeterDao();
			String meterPotocol = dao.getMeterPotocol(sdm.getAmMeter_ID());
			
			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(sdm.getSALEINFONUM());
			row.createCell((short) 1).setCellValue(sdm.getSTUDENTID());
			row.createCell((short) 2).setCellValue(sdm.getArchitecture_Name());
			row.createCell((short) 3).setCellValue(sdm.getFloorName());
			row.createCell((short) 4).setCellValue(sdm.getAmMeter_Name());
			row.createCell((short) 5).setCellValue(sdm.getKindName());
			row.createCell((short) 6).setCellValue(sdm.getPrice_Name());
			row.createCell((short) 7).setCellValue(sdm.getStatus());
			row.createCell((short) 8).setCellValue(sdm.getBuyTime());
			row.createCell((short) 9).setCellValue(sdm.getPrice());
			row.createCell((short) 10).setCellValue(sdm.getTheGross());
			row.createCell((short) 11).setCellValue(sdm.getTheMoney());
			row.createCell((short) 12).setCellValue(sdm.getSendTime());
			if ("1".equals(meterPotocol))
			{// 97 电量
				row.createCell((short) 13).setCellValue(sdm.getQSYValue());
				row.createCell((short) 14).setCellValue(sdm.getHSYValue());
				row.createCell((short) 15).setCellValue("-");
				row.createCell((short) 16).setCellValue("-");
			} else
			{// 07 金额
				row.createCell((short) 13).setCellValue("-");
				row.createCell((short) 14).setCellValue("-");
				row.createCell((short) 15).setCellValue(sdm.getQSYValue());
				row.createCell((short) 16).setCellValue(sdm.getHSYValue());
			}

			row.createCell((short) 17).setCellValue(sdm.getUsers_Name());
			row.createCell((short) 18).setCellValue(sdm.getORDERNO());

		}

		FileOutputStream fout = new FileOutputStream(XlsPath);
		wb.write(fout);
		fout.close();

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("filePath", filePath);

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
	}

	/**
	 * 批量设置界面的导出功能
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void VS_out_btn_click(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int flag = Integer.parseInt(req.getParameter("flag"));
		int floorID = 0;
		if (flag == 3)
		{
			floorID = Integer.parseInt(req.getParameter("FloorID"));
		}
		int Id = Integer.parseInt(req.getParameter("Id"));
		int price = Integer.parseInt(req.getParameter("price"));
		int AmMeterStatus = Integer.parseInt(req.getParameter("AmMeterStatus"));
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		String selectInfo = "";
		if (flag == 0)
			selectInfo += "all|";
		else if (flag == 1)
			selectInfo += "area|" + Id;
		else if (flag == 2)
			selectInfo += "arch|" + Id;
		else if (flag == 3)
			selectInfo += "floor|" + Id + "|" + floorID;
		else if (flag == 4)
			selectInfo += "meter|" + Id;

		VolumeSetDao vsd = new VolumeSetDao();
		ArrayList<VolumeSetModel> list = new ArrayList<VolumeSetModel>();
		list = vsd.queryInfo_out(selectInfo, tableName, order, price,AmMeterStatus);

		String filePath = "/img/VolumeSet.xls";
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
		cell.setCellValue("楼栋");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("楼层");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("电表名称");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("电费单价");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("剩余电量");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("剩余金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setCellValue("表计类型");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 7);
		cell.setCellValue("账户状态");
		cell.setCellStyle(style);

		cell = row.createCell((short) 8);
		cell.setCellValue("在线状态");
		cell.setCellStyle(style);

		cell = row.createCell((short) 9);
		cell.setCellValue("供电状态");
		cell.setCellStyle(style);

		int i = 0;
		for (VolumeSetModel sdm : list)
		{
			i++;

			CMMeterDao dao = new CMMeterDao();
			String meterPotocol = dao.getMeterPotocol(sdm.getAmMeter_ID());// 默认97
			String AmMeter_CostType = dao.getMeterCostType(sdm.getAmMeter_ID());
//			String sqlString = "select a.AMMETER_COSTTYPE,b.TEXINGVALUE from Ammeter a LEFT JOIN TEXINGVALUE b on (a.METESTYLE_ID=b.METESTYLE_ID) where a.AMMETER_ID="
//					+ sdm.getAmMeter_ID() + " and b.METERTEXING_ID=18";		
//			PreparedStatement pst = ConnDB.getConnection().prepareStatement(sqlString);
//			ResultSet rSet = pst.executeQuery();
//			if (rSet.next())
//			{
//				meterPotocol = rSet.getString("TexingValue");
//				AmMeter_CostType = rSet.getString("AMMETER_COSTTYPE");
//			}
//			if (rSet != null)
//				rSet.close();
//			if (pst != null)
//				pst.close();

			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(sdm.getArchitecture_Name());
			row.createCell((short) 1).setCellValue(sdm.getFloorName());
			row.createCell((short) 2).setCellValue(sdm.getAmMeter_Name());
			row.createCell((short) 3).setCellValue(sdm.getPrice());

			if ("1".equals(meterPotocol) && !"1".equals(meterPotocol))
			{// 97
				row.createCell((short) 4).setCellValue(sdm.getSYValue());
				row.createCell((short) 5).setCellValue("-");
			} else
			{// 07
				row.createCell((short) 4).setCellValue("-");
				row.createCell((short) 5).setCellValue(sdm.getSYValue());
			}
			row.createCell((short) 6).setCellValue(sdm.getMeteStyle_Name());
			row.createCell((short) 7).setCellValue(sdm.getAPState());
			row.createCell((short) 8).setCellValue(sdm.getAmMeter_Stat());
			row.createCell((short) 9).setCellValue(sdm.getIsSupply());

		}
		FileOutputStream fout = new FileOutputStream(XlsPath);
		wb.write(fout);
		fout.close();

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("filePath", filePath);

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());

	}

	private void get_Amm_Info_PR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		// TODO Auto-generated method stub
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int archID = Integer.parseInt(req.getParameter("ArchId"));
		int month = Integer.parseInt(req.getParameter("month"));
		int year = Integer.parseInt(req.getParameter("year"));

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		PowerRankingsDao prd = new PowerRankingsDao();
		ArrayList<PowerRankingsModel> list = new ArrayList<PowerRankingsModel>();
		list = prd.queryInfoByAmmhID(thePageCount, thePageIndex, archID, year, month, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", prd.getTotalCount());
		json.add(jo);

		for (PowerRankingsModel prm : list)
		{
			jo = new JSONObject();
			jo.put("rank", prm.getRank());
			jo.put("archName", prm.getArchitecture_Name());
			jo.put("floorName", prm.getFloorName());
			jo.put("amMeterName", prm.getAmMeter_Name());
			jo.put("priceName", prm.getPrice_Name());
			jo.put("zGross", prm.getZGross());
			jo.put("zMoney", prm.getZMoney());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 获得楼层的用电排名
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void get_Floor_Info_PR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		// TODO Auto-generated method stub
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int archID = Integer.parseInt(req.getParameter("ArchId"));
		int floorNum = Integer.parseInt(req.getParameter("FloorID"));
		int month = Integer.parseInt(req.getParameter("month"));
		int year = Integer.parseInt(req.getParameter("year"));

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		PowerRankingsDao prd = new PowerRankingsDao();
		ArrayList<PowerRankingsModel> list = new ArrayList<PowerRankingsModel>();
		list = prd.queryInfoByFloorNum(thePageCount, thePageIndex, archID, floorNum, year, month, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", prd.getTotalCount());
		json.add(jo);

		for (PowerRankingsModel prm : list)
		{
			jo = new JSONObject();
			jo.put("rank", prm.getRank());
			jo.put("archName", prm.getArchitecture_Name());
			jo.put("floorName", prm.getFloorName());
			jo.put("amMeterName", prm.getAmMeter_Name());
			jo.put("priceName", prm.getPrice_Name());
			jo.put("zGross", prm.getZGross());
			jo.put("zMoney", prm.getZMoney());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 或得建筑的用电排名
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void get_Arch_Info_PR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{

		// TODO Auto-generated method stub
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int archID = Integer.parseInt(req.getParameter("ArchId"));
		int month = Integer.parseInt(req.getParameter("month"));
		int year = Integer.parseInt(req.getParameter("year"));

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		PowerRankingsDao prd = new PowerRankingsDao();
		ArrayList<PowerRankingsModel> list = new ArrayList<PowerRankingsModel>();
		list = prd.queryInfoByArchID(thePageCount, thePageIndex, archID, year, month, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", prd.getTotalCount());
		json.add(jo);

		for (PowerRankingsModel prm : list)
		{
			jo = new JSONObject();
			jo.put("rank", prm.getRank());
			jo.put("archName", prm.getArchitecture_Name());
			jo.put("floorName", prm.getFloorName());
			jo.put("amMeterName", prm.getAmMeter_Name());
			jo.put("priceName", prm.getPrice_Name());
			jo.put("zGross", prm.getZGross());
			jo.put("zMoney", prm.getZMoney());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 或得区域的用电排名
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void get_Area_Info_PR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{

		// TODO Auto-generated method stub
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int areaID = Integer.parseInt(req.getParameter("AreaId"));
		int month = Integer.parseInt(req.getParameter("month"));
		int year = Integer.parseInt(req.getParameter("year"));

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		PowerRankingsDao prd = new PowerRankingsDao();
		ArrayList<PowerRankingsModel> list = new ArrayList<PowerRankingsModel>();
		list = prd.queryInfoByAreaID(thePageCount, thePageIndex, areaID, year, month, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", prd.getTotalCount());
		json.add(jo);

		for (PowerRankingsModel prm : list)
		{
			jo = new JSONObject();
			jo.put("rank", prm.getRank());
			jo.put("archName", prm.getArchitecture_Name());
			jo.put("floorName", prm.getFloorName());
			jo.put("amMeterName", prm.getAmMeter_Name());
			jo.put("priceName", prm.getPrice_Name());
			jo.put("zGross", prm.getZGross());
			jo.put("zMoney", prm.getZMoney());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 或得全校的用电排名
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void get_school_Info_PR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int month = Integer.parseInt(req.getParameter("month"));
		int year = Integer.parseInt(req.getParameter("year"));

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		PowerRankingsDao prd = new PowerRankingsDao();
		ArrayList<PowerRankingsModel> list = new ArrayList<PowerRankingsModel>();
		list = prd.queryAllInfo(thePageCount, thePageIndex, year, month, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", prd.getTotalCount());
		json.add(jo);

		for (PowerRankingsModel prm : list)
		{
			jo = new JSONObject();
			jo.put("rank", prm.getRank());
			jo.put("archName", prm.getArchitecture_Name());
			jo.put("floorName", prm.getFloorName());
			jo.put("amMeterName", prm.getAmMeter_Name());
			jo.put("priceName", prm.getPrice_Name());
			jo.put("zGross", prm.getZGross());
			jo.put("zMoney", prm.getZMoney());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 获得楼层集中监测的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws SQLException
	 */
	private void getAll_Floor_InfoForJZJC(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int archID = Integer.parseInt(req.getParameter("ArchId"));
		int floorNum = Integer.parseInt(req.getParameter("FloorID"));
		CMArchDao dao = new CMArchDao();
		ArrayList<CMArchModel> list = dao.queryInfoByFloorNum(thePageCount, thePageIndex, archID, floorNum);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);
		DecimalFormat numDf = new DecimalFormat("0.00");
		for (CMArchModel model : list)
		{
			String name = model.getAmMeterName();
			float dayGross = model.getDayGross();
			float RemainValue = model.getRemainValue();
			int AmMeterID = model.getAmMeterID();
			int color = model.getColorStyle();
			int FloorNum = model.getFloorNum();
			String zvaluezy = model.getzValuezy();

			jo = new JSONObject();
			jo.put("name", name);
			jo.put("AmMeterID", AmMeterID);
			String meterPotocol = (new CMMeterDao()).getMeterPotocol(AmMeterID);
			String costType = (new CMMeterDao()).getMeterCostType(AmMeterID);
			jo.put("meterPotocol", meterPotocol);
			jo.put("costType", costType);
			jo.put("zvaluezy", zvaluezy);
			jo.put("dayGross", numDf.format(dayGross));
			jo.put("RemainValue", numDf.format(RemainValue));
			jo.put("color", color);
			jo.put("FloorNum", FloorNum);
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 获得建筑集中监测的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getAllArchtectureInfoForJZJC(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ParseException
	{
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int archID = Integer.parseInt(req.getParameter("ArchId"));
		DecimalFormat numDf = new DecimalFormat("0.00");
		CMArchDao dao = new CMArchDao();
		ArrayList<CMArchModel> list = dao.queryInfoByArchID(thePageCount, thePageIndex, archID);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);

		for (CMArchModel model : list)
		{
			String name = model.getAmMeterName();
			float dayGross = model.getDayGross();
			float RemainValue = model.getRemainValue();
			int AmMeterID = model.getAmMeterID();
			int color = model.getColorStyle();
			int FloorNum = model.getFloorNum();
			String zvaluezy = model.getzValuezy();

			jo = new JSONObject();
			jo.put("name", name);
			jo.put("AmMeterID", AmMeterID);
			
			String meterPotocol = (new CMMeterDao()).getMeterPotocol(AmMeterID);
			String costType = (new CMMeterDao()).getMeterCostType(AmMeterID);
			jo.put("meterPotocol", meterPotocol);
			jo.put("costType", costType);
			jo.put("zvaluezy", zvaluezy);
			jo.put("dayGross", numDf.format(dayGross));
			jo.put("RemainValue", numDf.format(RemainValue));
			jo.put("color", color);
			jo.put("FloorNum", FloorNum);
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 获得全区集中监测的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getAllAreaInfoForJZJC(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int areaID = Integer.parseInt(req.getParameter("AreaId"));

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		CMAreaDao dao = new CMAreaDao();
		ArrayList<CMAreaModel> list = dao.queryInfoByAreaID(thePageCount, thePageIndex, areaID, tableName, order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);

		for (CMAreaModel model : list)
		{
			String name = model.getAreaOrArchName();
			float dayGross = model.getDayAmGross();
			float dayMoney = model.getDayAmMoney();
			float monthGross = model.getMonthAmGross();
			float monthMoney = model.getMonthAmMoney();
			int MeterCount = model.getMeterCount();
			int UsingMeterCount = model.getUsingMeterCount();
			int OffMeterCount = model.getOffMeterCount();
			int Architecture_ID = model.getArchitecture_ID();
			jo = new JSONObject();
			jo.put("Architecture_ID", Architecture_ID);
			jo.put("name", name);
			jo.put("dayGross", dayGross);
			jo.put("dayMoney", dayMoney);
			jo.put("monthGross", monthGross);
			jo.put("monthMoney", monthMoney);
			jo.put("MeterCount", MeterCount);
			jo.put("UsingMeterCount", UsingMeterCount);
			jo.put("OffMeterCount", OffMeterCount);
			json.add(jo);
		}
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 获得全校集中监测的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getAllSchoolInfoForJZJC(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		CMAreaDao dao = new CMAreaDao();
		ArrayList<CMAreaModel> list = dao.queryAllInfo(thePageCount, thePageIndex, tableName, order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);

		for (CMAreaModel model : list)
		{
			String name = model.getAreaOrArchName();
			float dayGross = model.getDayAmGross();
			float dayMoney = model.getDayAmMoney();
			float monthGross = model.getMonthAmGross();
			float monthMoney = model.getMonthAmMoney();
			int MeterCount = model.getMeterCount();
			int UsingMeterCount = model.getUsingMeterCount();
			int OffMeterCount = model.getOffMeterCount();
			int AreaID = model.getAreaID();
			jo = new JSONObject();
			jo.put("AreaID", AreaID);
			jo.put("name", name);
			jo.put("dayGross", dayGross);
			jo.put("dayMoney", dayMoney);
			jo.put("monthGross", monthGross);
			jo.put("monthMoney", monthMoney);
			jo.put("MeterCount", MeterCount);
			jo.put("UsingMeterCount", UsingMeterCount);
			jo.put("OffMeterCount", OffMeterCount);
			json.add(jo);
		}
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 获得某个节点的
	 * 
	 * @param xml
	 *            XML字符串
	 * @param tagName
	 *            节点的名称
	 * @return 对应节点的element对象
	 * @throws Exception
	 */
	public NodeList getNodeList(String xml, String tagName) throws Exception
	{
		InputSource source = null;
		StringReader read = null;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dombuilder = domfac.newDocumentBuilder();

		read = new StringReader(xml);

		source = new InputSource(read);

		Document document = dombuilder.parse(source);
		NodeList nodeList = document.getElementsByTagName(tagName);
		return nodeList;
	}

	public String getAttrValue(String xml, String tagName, String attrName)
	{
		InputSource source = null;
		StringReader read = null;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dombuilder;
		NodeList nodeList = null;
		String result = null;
		try
		{
			dombuilder = domfac.newDocumentBuilder();

			read = new StringReader(xml);

			source = new InputSource(read);

			Document document = dombuilder.parse(source);
			nodeList = document.getElementsByTagName(tagName);
			Element elm = (Element) nodeList.item(0);
			result = elm.getAttribute(attrName);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
