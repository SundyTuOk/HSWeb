package com.sf.energy.prepayment.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.prepayment.dao.ApCardDZDao;
import com.sf.energy.prepayment.model.ApCardDZModel;
import com.sf.energy.prepayment.model.ApCardSaleInfoModel;
import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.project.system.dao.DataSiteDao;
import com.sf.energy.util.ExportHelper;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class APCardDZServlet extends HttpServlet
{

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		String method = request.getParameter("method");
		if ("getCardInfo".equals(method))
		{
			getCardInfo(request, response);
		}
		if ("delete_card_info".equals(method))
		{
			delete_card_info(request, response);
		}
		if("cardImport".equals(method)){
			cardImport(request, response);
		}
		if("getDZInfo".equals(method)){
			getDZInfo(request,response);
		}

	}

	private void getDZInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		String type = request.getParameter("type");
		int id = Integer.parseInt(request.getParameter("id"));//对账id
		int thePageCount = Integer.parseInt(request.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(request.getParameter("Pageindex"));
		String order = " order by " + request.getParameter("tableName") + " " + request.getParameter("order");
		
		ApCardDZDao dao = new ApCardDZDao();
		ArrayList<ApCardSaleInfoModel> list = new ArrayList<ApCardSaleInfoModel>();
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		if("1".equals(type)){//一卡通账单
			list = dao.getCardSelectInfo(id, thePageCount, thePageIndex, order);
			jo.put("totalCount", dao.getTotalCount());
			json.add(jo);
			for(ApCardSaleInfoModel saleInfo:list){
				jo.put("BuyTime", saleInfo.getBuyTime());
				jo.put("PosNum", saleInfo.getPosNum());
				jo.put("CardManNum", saleInfo.getCardManNum());
				jo.put("CardManName", saleInfo.getCardManName());
				jo.put("StudentNum", saleInfo.getStudentNum());
				jo.put("TheMoney", saleInfo.getTheMoney());
				jo.put("YMoney", saleInfo.getYMoney());
				jo.put("UserTimes", saleInfo.getUserTimes());
				jo.put("State", saleInfo.getState());
				json.add(jo);
			}
		}
		else if("2".equals(type)){//售电账单
			list = dao.getSaleSelectInfo(id, thePageCount, thePageIndex, order);
			jo.put("totalCount", dao.getTotalCount());
			json.add(jo);
			for(ApCardSaleInfoModel saleInfo : list){
				String KindName = "系统售电";
                if ("0".equals(saleInfo.getKind()))
                {
                    KindName = "系统售电";
                }
                else if ("1".equals(saleInfo.getKind()))
                {
                    KindName = "一卡通售电";
                }
                else if ("2".equals(saleInfo.getKind()))
                {
                    KindName = "统一月补";
                }
                else if ("3".equals(saleInfo.getKind()))
                {
                    KindName = "临时调剂";
                }

                String status = "未下发";
                if ("1".equals(saleInfo.getStatus()))
                {
                    status = "下发成功";
                }
                else if ("2".equals(saleInfo.getStatus()))
                {
                    status = "下发异常";
                }
                else
                {
                    status = "未下发";
                }
				
				jo.put("SaleInfoNum", saleInfo.getSaleInfoNum());
				jo.put("studentID", saleInfo.getStudentID());
				jo.put("Architecture_Name", saleInfo.getArchitecture_Name());
				jo.put("FloorName", saleInfo.getFloorName());
				jo.put("AmMeter_Name", saleInfo.getAmMeter_Name());
				jo.put("Kind", KindName);
				jo.put("Status", status);
				jo.put("BuyTime", saleInfo.getBuyTime());
				jo.put("Price", saleInfo.getPrice());
				jo.put("TheGross", saleInfo.getTheGross());
				jo.put("TheMoney", saleInfo.getTheMoney());
				jo.put("SendTime", saleInfo.getSendTime());
				jo.put("QSYValue", saleInfo.getQSYValue());
				jo.put("HSYValue", saleInfo.getHSYValue());
				json.add(jo);
			}
		}
		else if("3".equals(type)){//对照账单
			list = dao.getDZSelectInfo(id, thePageCount, thePageIndex, order);
			jo.put("totalCount", dao.getTotalCount());
			json.add(jo);
			for(ApCardSaleInfoModel saleInfo : list){
				String DZState = "正常";
                if ("正常".equals(saleInfo.getState()))
                {
                    if ("".equals(saleInfo.getSaleInfoNum()))
                        DZState = "漏充";
                }
                else if ("被冲正".equals(saleInfo.getState()))
                {
                    if (!"".equals(saleInfo.getSaleInfoNum()))
                        DZState = "冲正多充";
                }
                else if ("".equals(saleInfo.getState()))
                {
                    if (!"".equals(saleInfo.getSaleInfoNum()))
                        DZState = "无单多充";
                }
				
				jo.put("BuyTime", saleInfo.getBuyTime());
				jo.put("PosNum", saleInfo.getPosNum());
				jo.put("CardManNum", saleInfo.getCardManNum());
				jo.put("CardManName", saleInfo.getCardManName());
				jo.put("StudentNum", saleInfo.getStudentNum());
				jo.put("TheMoney", saleInfo.getTheMoney());
				jo.put("YMoney", saleInfo.getYMoney());
				jo.put("UserTimes", saleInfo.getUserTimes());
				jo.put("State", saleInfo.getState());
				jo.put("SaleInfoNum", saleInfo.getSaleInfoNum());
				jo.put("AmMeter_Name", saleInfo.getAmMeter_Name());
				jo.put("DZState", DZState);
				json.add(jo);
			}
			
		}
		
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	private void getCardInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(request.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(request.getParameter("Pageindex"));
		String beginDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		String order = " order by " + request.getParameter("tableName") + " " + request.getParameter("order");
		String queryMsg = beginDate + "|" + endDate;

		ApCardDZDao dao = new ApCardDZDao();
		ArrayList<ApCardDZModel> list = new ArrayList<ApCardDZModel>();

		list = dao.queryInfo(thePageCount, thePageIndex, queryMsg, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);

		for (ApCardDZModel psmm : list)
		{
			jo = new JSONObject();
			jo.put("id", psmm.getAPCardDZ_ID());
			jo.put("APCardDZTime", psmm.getAPCardDZTime());
			jo.put("CardMoney", psmm.getCardMoney());
			jo.put("ApMoney", psmm.getApMoney());
			jo.put("CMoney", psmm.getCMoney());
			jo.put("CardTimes", psmm.getCardTimes());
			jo.put("APTimes", psmm.getAPTimes());
			jo.put("ErrorTimes", psmm.getEroorTimes());
			jo.put("ExecTimes", psmm.getExecTimes());
			jo.put("UserName", psmm.getUserName());
			jo.put("insertTime", psmm.getInsertTime());
			json.add(jo);
		}
		response.setContentType("application/x-json");
		// System.out.println("json:" + json.toString());
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void delete_card_info(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "一卡通对账", "删除对账信息");
		
		ApCardDZDao dao = new ApCardDZDao();
		String theID = request.getParameter("theID");
		int res = dao.delete(theID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("result", res);
		json.add(jo);

		response.setContentType("application/x-json");
		PrintWriter pw = response.getWriter();
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}

	private void cardImport(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		ExportHelper dh = ExportHelper.getInstance();
		String saveFolder = "img/archImg";
		//List<List<String>> result = null;
		String result = "";
		
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "一卡通对账", "添加对账信息");
		
		File file = dh.getImportFile(request, response, saveFolder, "theCardFile");

		JSONArray json = new JSONArray();

		JSONObject jo = new JSONObject();

		if (file != null)
		{
			if (file.getAbsolutePath().endsWith(".xls"))
			{
				result = importCardToDB(file, request);
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
		PrintWriter pw = response.getWriter();
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}

	private String importCardToDB(File cardFile, HttpServletRequest request) throws SQLException
	{
		
		int userID = (int) request.getSession().getAttribute("userId");//获取用户ID
		//String userLoginName = (String) request.getSession().getAttribute("userName");//获取用户名
		String dzTime = request.getParameter("theTime");
		//将对账信息插入对账表中
		ApCardDZDao dao = new ApCardDZDao();
		int aPCardDZ_ID = dao.insertDZInfo(dzTime, userID);
		//不展示错误信息，返回一个String类型的errorInfo
		/*List<List<String>> failList = new LinkedList<List<String>>();
		String[] theTitles =
		{ "交易发生时间", "交易类型", "子系统名称", "pos号", "持卡人帐号", "姓名", "学工号", "交易额", "现有余额", "用卡次数", "状态" };
		List<String> firstLine = new LinkedList<String>();
		// 将标题加进去
		for (String title : theTitles)
		{
			firstLine.add(title);
		}
		failList.add(firstLine);*/
//		DataSite dataSite = dataSiteDao.getFirstDataSite();
		Workbook book = null;
		
		String errorInfo = "";
		try
		{
			book = Workbook.getWorkbook(cardFile);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
				
			float CardMoney = 0;//一卡通售电金额
			float ApMoney = 0;//系统售电金额
			float CMoney = 0;//差额
            int CardCountTimes = 0;//对账次数
            int CardTimes = 0;//一卡通售电次数
            int APTimes = 0;//系统售电次数
            int EroorTimes = 0;//错误次数
            String errorType = "";//错误类型
            int execState = 0;//执行状态
            String ammeter_name = "";
			
            if(sheet.getColumns()>=11){
            	// 得到单元格
            	for (int i = 1; i < sheet.getRows(); i++)
            	{
				
					String buyTime = sheet.getCell(0, i).getContents();
					String cardManNum = sheet.getCell(4, i).getContents();
					String cardManName = sheet.getCell(5, i).getContents();
					String studentNum = sheet.getCell(6, i).getContents();
					String theMoney = sheet.getCell(7, i).getContents();
					String state = sheet.getCell(10, i).getContents();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					Date buyTimeDate = new Date();
					try
					{
						buyTimeDate = sdf.parse(buyTime);
					} catch (ParseException e)
					{
						e.printStackTrace();
					}
					String buyTimeString = sdf1.format(buyTimeDate);
					if(!buyTimeString.equals(dzTime)){
						//不是对账日
						continue;
					}
					CardCountTimes++;
					
					String saleInfoNum = "";
					saleInfoNum = dao.getSaleInfoNum(dzTime,studentNum,theMoney,aPCardDZ_ID);
					int APCardSaleInfo_ID = dao.insertAPCardInfo(aPCardDZ_ID, buyTime, sheet.getCell(3, i).getContents(), cardManNum, cardManName, studentNum, theMoney, sheet.getCell(8, i).getContents(), 
							sheet.getCell(9, i).getContents(), state, saleInfoNum);
					if (state.equals("正常"))
	                {
						CardTimes++;
						CardMoney += Double.parseDouble(theMoney);
						//查询相应的售电，一个售电单只能对应一条一卡通充值记录
						if ( "".equals(saleInfoNum))//一卡通已扣款，但是电未下发
	                    {
							 EroorTimes++;
							//记录异常数据
		                    //ErrorType: 漏充  冲正多充  无单多充
							 errorType = "漏冲";
							 execState = 0;
							 dao.insertAPCardErrorInfo(APCardSaleInfo_ID, aPCardDZ_ID, saleInfoNum, errorType, execState, buyTime, ammeter_name);
	                    }
	                }
					else if (state.equals("被冲正")) {
						if ( !"".equals(saleInfoNum))//一卡通未扣款，但是已生成售电单
						{
							EroorTimes++;
							ammeter_name = dao.getAmName(saleInfoNum);
							errorType = "冲正多充";
							execState = 0;
							dao.insertAPCardErrorInfo(APCardSaleInfo_ID, aPCardDZ_ID, saleInfoNum, errorType, execState, buyTime, ammeter_name);
						}
					}
					
				}//for循环结束
            	
            	//检查无单多充状况
            	ArrayList<SaleDetailsModel> saleInfoList = dao.getSaleInfo(dzTime, aPCardDZ_ID);
            	for(SaleDetailsModel sdm : saleInfoList){
            		EroorTimes++;
            		String saleInfoNum = sdm.getSALEINFONUM();
            		errorType = "无单多充";
            		execState = 0;
            		String buyTime = sdm.getBuyTime();
            		ammeter_name = sdm.getAmMeter_Name();
            		dao.insertAPCardErrorInfo(0, 0, saleInfoNum, errorType, execState, buyTime, ammeter_name);
            	}
            	
            	//合计
            	ArrayList<String> totalList = dao.getTotalInfo(dzTime);
            	if(totalList != null){
            		ApMoney = (float) Double.parseDouble(totalList.get(0));
            		APTimes = Integer.parseInt(totalList.get(1));
            	}
            	
            	CMoney = Math.abs(CardMoney-ApMoney);

            	dao.updateCardDZInfo(CardMoney, ApMoney, CMoney, CardCountTimes, CardTimes, APTimes, EroorTimes, aPCardDZ_ID);
				
			}else{
				errorInfo += "该行数据列数少于模板列数;";
			}
            
            if(EroorTimes>0){
            	errorInfo += "总共有" + EroorTimes + "错误信息，请到异常处理界面查看并处理相应的信息！";
            }
            
            //此处没有将错误信息加入到failList集合中
			

		} catch (BiffException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return errorInfo;
	}

}
