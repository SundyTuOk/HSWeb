package com.sf.energy.water.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.system.dao.BldQuotaByDevWaterDao;
import com.sf.energy.project.system.dao.BldQuotaByPeoWaterDao;
import com.sf.energy.project.system.dao.FixedPrintDao;
import com.sf.energy.project.system.model.BldQuotaByDevWaterModel;
import com.sf.energy.project.system.model.BldQuotaByPeoWaterModel;
import com.sf.energy.project.system.model.FixedPrintModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;
import com.sf.energy.water.manager.current.dao.BldBillSetWaterDao;
import com.sf.energy.water.manager.current.dao.BldFinalAccountsWaterDao;
import com.sf.energy.water.manager.current.dao.WaterArchitectureFixedDao;
import com.sf.energy.water.manager.current.dao.WaterFixedFinancialSettlementDao;
import com.sf.energy.water.manager.current.model.BldBillSetWaterModel;
import com.sf.energy.water.manager.current.model.BldFinalAccountsWaterModel;
import com.sf.energy.water.manager.current.model.WaterFixedFinancialSettlementModel;
import com.sf.energy.water.manager.current.model.WaterQuotaManagerModel;

public class WaterArchFixedServlet extends HttpServlet
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
		} catch (Exception e)
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
			HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);

		if ("waterArchFixedPaginate".equals(method))
			waterArchFixedPaginate(request, response);
		if ("getDevInfoByWaterBldOrArea_ID".equals(method))
			getDevInfoByWaterBldOrArea_ID(request, response);
		if ("getPeoInfoByWaterBldOrArea_ID".equals(method))
			getPeoInfoByWaterBldOrArea_ID(request, response);
		if ("getWaterArchQuotaUseInfo".equals(method))
			getWaterArchQuotaUseInfo(request, response);
		if ("getArchWaterStructure".equals(method))
			getArchWaterStructure(request, response);

		if ("addArchWaterPeoQuotaInfo".equals(method))
			addArchWaterPeoQuotaInfo(request, response);
		if ("deleteArchWaterPeoQuotaInfo".equals(method))
			deleteArchWaterPeoQuotaInfo(request, response);
		if ("updateArchWaterPeoQuotaInfo".equals(method))
			updateArchWaterPeoQuotaInfo(request, response);
		if ("queryArchWaterPeoOldInfo".equals(method))
			queryArchWaterPeoOldInfo(request, response);

		if ("addArchWaterDevQuotaInfo".equals(method))
			addArchWaterDevQuotaInfo(request, response);
		if ("deleteArchWaterDevQuotaInfo".equals(method))
			deleteArchWaterDevQuotaInfo(request, response);
		if ("updateArchWaterDevQuotaInfo".equals(method))
			updateArchWaterDevQuotaInfo(request, response);
		if ("queryArchWaterDevOldInfo".equals(method))
			queryArchWaterDevOldInfo(request, response);
		// 定额财务结算
		if ("archWaterFixedFinancialSettlement".equals(method))
			archWaterFixedFinancialSettlement(request, response);
		if ("archLoadZhangtao".equals(method))
			archLoadZhangtao(request, response);
		if ("addArchZhangtao".equals(method))
			addArchZhangtao(request, response);
		if ("addArchJuesuan".equals(method))
			addArchJuesuan(request, response);
		if ("archDelJiesuan".equals(method))
			archDelJiesuan(request, response);
		if ("fixedPrint".equals(method))
			fixedPrint(request, response);

		if ("getWaterQuotaStatisticInfo".equals(method))
			getWaterQuotaStatisticInfo(request, response);

		if ("updateWaterQuotaStatisticInfo".equals(method))
			updateWaterQuotaStatisticInfo(request, response);

		// 同比环比
		if ("makeWaterQuotaTablePerMonth".equals(method))
			makeWaterQuotaTablePerMonth(request, response);

		// 定额明细排序
		if ("waterArchFixedSort".equals(method))
			waterArchFixedSort(request, response);

	}

	private void fixedPrint(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			NumberFormatException, ParseException
	{
		FixedPrintDao fpd = new FixedPrintDao();
		String serialNo = null;
		if (request.getParameter("SerialNo") != null)
		{
			serialNo = request.getParameter("SerialNo");
		} else
		{
			return;
		}

		List<FixedPrintModel> list = fpd.getArchWaterPrintInfo(serialNo);

		JSONArray main = new JSONArray();

		if (list != null && list.size() > 0)
		{
			for (FixedPrintModel fpm : list)
			{
				JSONObject jo = new JSONObject();
				// /单位名
				jo.put("UnitName", fpm.getUnitName());
				// /区域或建筑名称
				jo.put("OrganizeName", fpm.getOrganizeName());
				// /年度计划(元)
				jo.put("TotalQuota", fpm.getTotalQuota());
				// /年度已用金额(元)
				jo.put("ZMoney", fpm.getZMoney());
				// /年度剩余金额(元)
				jo.put("RemainQuota", fpm.getRemainQuota());
				// /上期累计(千瓦时)
				jo.put("LastPower", fpm.getLastPower());
				// /本期累计(千瓦时)
				jo.put("ThisPower", fpm.getThisPower());
				// /年度已用电量(千瓦时)
				jo.put("ZPower", fpm.getZMoney());
				// /本期累计已用金额(元)
				jo.put("ThisMoney", fpm.getThisMoney());
				// /分项明细
				jo.put("FenxiangDetil", fpm.getFenxiangDetil());
				// /性质明细
				jo.put("XingzhiDetil", fpm.getXingzhiDetil());
				// /结算期间
				jo.put("Period", fpm.getPeriod());
				// /平均单价
				jo.put("Price", fpm.getPrice());
				// /制表单位
				jo.put("ReportUnit", fpm.getReportUnit());

				main.add(jo);
			}
		}

		PrintWriter out = response.getWriter();
		out.println(main.toString());
		out.flush();
		out.close();
	}

	private void archDelJiesuan(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		OperationLogInterface log = new OperationLogImplement();
		BldBillSetWaterDao bbsd = new BldBillSetWaterDao();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (bbsd.delete(valueID))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "建筑用水定额管理", "删除建筑定额财务结算");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理", "删除建筑定额财务结算",
						"删除失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addArchJuesuan(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException
	{
		OperationLogInterface log = new OperationLogImplement();
		BldFinalAccountsWaterModel bfam = new BldFinalAccountsWaterModel();
		BldFinalAccountsWaterDao bfad = new BldFinalAccountsWaterDao();

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

		bfam.setBillId(Integer.parseInt(request.getParameter("BillId")));
		bfam.setYear(request.getParameter("Year"));
		bfam.setStartMM(Integer.parseInt(request.getParameter("StartMM")));
		bfam.setEndMM(Integer.parseInt(request.getParameter("EndMM")));
		bfam.setStartYMD(sdf2.format(sdf1.parse(request
				.getParameter("StartYMD"))));
		bfam.setEndYMD(sdf2.format(sdf1.parse(request.getParameter("EndYMD"))));
		bfam.setLastSerialNo(Integer.parseInt(request
				.getParameter("LastSerialNo")));
		String remark=request.getParameter("Remark");
		bfam.setRemark(remark);

		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
			// //System.out.println(userID);
		}
		bfam.setUserID(Integer.parseInt(userID));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date systemTime = new Date();
		bfam.setSystemtime(sdf.format(systemTime));

		String resultInfo = null;
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (bfad.insert(bfam))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用电定额管理", "新建决算记录");
			} else
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理", "新建决算记录",
						"添加失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void addArchZhangtao(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		OperationLogInterface log = new OperationLogImplement();
		String bldOrAreaID = request.getParameter("bldOrAreaID");
		String aOrbFlg = request.getParameter("aOrbFlg");
		String theName = request.getParameter("theName");

		BldBillSetWaterDao bbsd = new BldBillSetWaterDao();

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (bbsd.addArchZhangtaoSave(bldOrAreaID, aOrbFlg, theName))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用水定额管理", "新建账套");
			} else
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理", "新建账套",
						"添加失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void archLoadZhangtao(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		BldBillSetWaterDao bbsd = new BldBillSetWaterDao();
		ArrayList<BldBillSetWaterModel> list = new ArrayList<BldBillSetWaterModel>();
		list = bbsd.getZhangtao();

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		for (BldBillSetWaterModel bbsm : list)
		{
			jo = new JSONObject();
			jo.put("BILLID", bbsm.getBillId());
			jo.put("BLDORAREA_ID", bbsm.getBldOrArea_ID());
			jo.put("AORBFLG", bbsm.getAOrBflg());
			jo.put("BILLNAME", bbsm.getBillName());

			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void waterArchFixedSort(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		Integer thePageCount = Integer.parseInt(request
				.getParameter("waterArchFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("waterArchFixedPageIndex"));

		String strYear = request.getParameter("waterArchFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
			// //System.out.println(userID);
		}
		String order = "order by " + request.getParameter("TableName") + " "
				+ request.getParameter("Order");

		ArrayList<WaterQuotaManagerModel> list = wafd.sortQuery(strYear,
				userID, thePageCount, thePageIndex, order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", wafd.getTotalCount());
		json.add(jo);

		for (WaterQuotaManagerModel wqmm : list)
		{
			jo = new JSONObject();
			jo.put("year", wqmm.getYear());
			jo.put("name", wqmm.getName());
			jo.put("totalQuota", wqmm.getTotalQuota());
			jo.put("standardQuota", wqmm.getStandardQuota());
			jo.put("TiaozhengFixed", wqmm.getTiaozhengFixed());
			jo.put("addPerMonthQuota", wqmm.getAddPerMonthQuota());
			jo.put("ZMoney", wqmm.getZMoney());
			jo.put("remainQuota", wqmm.getRemainQuota());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void makeWaterQuotaTablePerMonth(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		String strYear = request.getParameter("waterArchFixedSearchYear");
		String BldOrArea_ID = request.getParameter("waterBldOrArea_ID");
		String AOrBFlg = request.getParameter("waterAOrBFlg");

		String resultInfo = "";
		resultInfo = wafd
				.makeQuotaTablePerMonth(strYear, BldOrArea_ID, AOrBFlg);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void updateWaterQuotaStatisticInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		OperationLogInterface log = new OperationLogImplement();
		String strYear = request.getParameter("waterArchFixedSearchYear");
		String BldOrArea_ID = request.getParameter("waterBldOrArea_ID");
		String AOrBFlg = request.getParameter("waterAOrBFlg");
		String values = request.getParameter("values");

		String resultInfo = "";
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = wafd.saveWaterArchQuotaStatisticInfo(strYear,
					BldOrArea_ID, AOrBFlg, values);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用水定额管理", "更新定额统计信息");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理", "更新定额统计信息",
						"更新失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理", "更新定额统计信息",
						"更新失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getWaterQuotaStatisticInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		String strYear = request.getParameter("waterArchFixedSearchYear");
		String BldOrArea_ID = request.getParameter("waterBldOrArea_ID");
		String AOrBFlg = request.getParameter("waterAOrBFlg");

		String resultInfo = "";
		resultInfo = wafd.getWaterQuotaStatisticInfo(strYear, BldOrArea_ID,
				AOrBFlg);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void archWaterFixedFinancialSettlement(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		
		WaterFixedFinancialSettlementDao wffsd = new WaterFixedFinancialSettlementDao();		Integer thePageCount = Integer.parseInt(request
				.getParameter("waterArchFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("waterArchFixedPageIndex"));

		String strYear = request.getParameter("waterArchFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
			// //System.out.println(userID);
		}
		String order = " order by " + request.getParameter("tableName") + " "
				+ request.getParameter("order");
		ArrayList<WaterFixedFinancialSettlementModel> list = wffsd
				.getFinancialSettlementInfo(strYear, userID, thePageCount,
						thePageIndex, order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", wffsd.getTotalCount());
		json.add(jo);

		for (WaterFixedFinancialSettlementModel wffsm : list)
		{
			jo = new JSONObject();
			jo.put("BldOrArea_ID", wffsm.getBldOrArea_ID());
			jo.put("AOrBflg", wffsm.getAOrBflg());
			jo.put("SerialNo", wffsm.getSerialNo());
			jo.put("name", wffsm.getName());
			jo.put("remark", wffsm.getRemark());
			jo.put("year", wffsm.getYear());
			jo.put("period", wffsm.getPeriod());
			jo.put("amMeterCnt", wffsm.getAmMeterCnt());
			jo.put("systemTime", wffsm.getSystemTime());
			jo.put("Users_Name", wffsm.getUsers_Name());

			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void queryArchWaterDevOldInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		
		BldQuotaByDevWaterDao bdwd = new BldQuotaByDevWaterDao();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));
		BldQuotaByDevWaterModel bdwm = bdwd.queryByID(valueID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("bldOrAreaID", bdwm.getBldOrAreaID());
		jo.put("aOrbFlg", bdwm.getaOrbFlg());
		jo.put("year", bdwm.getYear());
		jo.put("name", bdwm.getName());
		jo.put("price", bdwm.getPrice());
		jo.put("m1", bdwm.getM1());
		jo.put("m2", bdwm.getM2());
		jo.put("m3", bdwm.getM3());
		jo.put("m4", bdwm.getM4());
		jo.put("m5", bdwm.getM5());
		jo.put("m6", bdwm.getM6());
		jo.put("m7", bdwm.getM7());
		jo.put("m8", bdwm.getM8());
		jo.put("m9", bdwm.getM9());
		jo.put("m10", bdwm.getM10());
		jo.put("m11", bdwm.getM11());
		jo.put("m12", bdwm.getM12());
		jo.put("totalDevice", bdwm.getTotalDevice());
		jo.put("power", bdwm.getPower());
		jo.put("RunHoursPerMth", bdwm.getRunHoursPerMth());
		jo.put("remark", bdwm.getRemark());

		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void updateArchWaterDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		BldQuotaByDevWaterDao bdwd = new BldQuotaByDevWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		BldQuotaByDevWaterModel bdwm = new BldQuotaByDevWaterModel();

		bdwm.setValueID(Integer.parseInt(request.getParameter("valueID")));
		bdwm.setBldOrAreaID(Integer.parseInt(request
				.getParameter("bldOrAreaID")));
		bdwm.setaOrbFlg(Integer.parseInt(request.getParameter("aOrbFlg")));
		bdwm.setYear(request.getParameter("year"));
		bdwm.setName(request.getParameter("name"));
		bdwm.setPrice(Float.parseFloat(request.getParameter("price")));
		bdwm.setM1(Integer.parseInt(request.getParameter("m1")));
		bdwm.setM2(Integer.parseInt(request.getParameter("m2")));
		bdwm.setM3(Integer.parseInt(request.getParameter("m3")));
		bdwm.setM4(Integer.parseInt(request.getParameter("m4")));
		bdwm.setM5(Integer.parseInt(request.getParameter("m5")));
		bdwm.setM6(Integer.parseInt(request.getParameter("m6")));
		bdwm.setM7(Integer.parseInt(request.getParameter("m7")));
		bdwm.setM8(Integer.parseInt(request.getParameter("m8")));
		bdwm.setM9(Integer.parseInt(request.getParameter("m9")));
		bdwm.setM10(Integer.parseInt(request.getParameter("m10")));
		bdwm.setM11(Integer.parseInt(request.getParameter("m11")));
		bdwm.setM12(Integer.parseInt(request.getParameter("m12")));
		bdwm.setTotalDevice(Integer.parseInt(request
				.getParameter("totalDevice")));
		bdwm.setPower(Float.parseFloat(request.getParameter("power")));
		bdwm.setRunHoursPerMth(Float.parseFloat(request
				.getParameter("RunHoursPerMth")));
		bdwm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = bdwd.modify(bdwm);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用水定额管理", "编辑建筑设备类别标准分配定额详细（用水）。");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"编辑建筑设备类别标准分配定额详细（用水）", "编辑失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"编辑建筑设备类别标准分配定额详细（用水）", "编辑失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void deleteArchWaterDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		BldQuotaByDevWaterDao bdwd = new BldQuotaByDevWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (bdwd.delete(valueID))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "建筑用水定额管理", "删除建筑设备类别标准分配定额详细（用水）。");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"删除建筑设备类别标准分配定额详细（用水）", "删除失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addArchWaterDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		BldQuotaByDevWaterDao bdwd = new BldQuotaByDevWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		BldQuotaByDevWaterModel bdwm = new BldQuotaByDevWaterModel();

		bdwm.setBldOrAreaID(Integer.parseInt(request
				.getParameter("bldOrAreaID")));
		bdwm.setaOrbFlg(Integer.parseInt(request.getParameter("aOrbFlg")));
		bdwm.setYear(request.getParameter("year"));
		bdwm.setName(request.getParameter("name"));
		bdwm.setPrice(Float.parseFloat(request.getParameter("price")));
		bdwm.setM1(Integer.parseInt(request.getParameter("m1")));
		bdwm.setM2(Integer.parseInt(request.getParameter("m2")));
		bdwm.setM3(Integer.parseInt(request.getParameter("m3")));
		bdwm.setM4(Integer.parseInt(request.getParameter("m4")));
		bdwm.setM5(Integer.parseInt(request.getParameter("m5")));
		bdwm.setM6(Integer.parseInt(request.getParameter("m6")));
		bdwm.setM7(Integer.parseInt(request.getParameter("m7")));
		bdwm.setM8(Integer.parseInt(request.getParameter("m8")));
		bdwm.setM9(Integer.parseInt(request.getParameter("m9")));
		bdwm.setM10(Integer.parseInt(request.getParameter("m10")));
		bdwm.setM11(Integer.parseInt(request.getParameter("m11")));
		bdwm.setM12(Integer.parseInt(request.getParameter("m12")));
		bdwm.setTotalDevice(Integer.parseInt(request
				.getParameter("totalDevice")));
		bdwm.setPower(Float.parseFloat(request.getParameter("power")));
		bdwm.setRunHoursPerMth(Float.parseFloat(request
				.getParameter("RunHoursPerMth")));
		bdwm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = bdwd.insert(bdwm);
			if ("success".equals(msg))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用水定额管理", "添加建筑设备类别标准分配定额详细（用水）。");
			} else if ("fail".equals(msg))
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"添加建筑设备类别标准分配定额详细（用水）", "添加失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"添加建筑设备类别标准分配定额详细（用水）", "添加失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void queryArchWaterPeoOldInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		BldQuotaByPeoWaterDao bpwd = new BldQuotaByPeoWaterDao();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));
		BldQuotaByPeoWaterModel bpwm = bpwd.queryByID(valueID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("bldOrAreaID", bpwm.getBldOrAreaID());
		jo.put("aOrbFlg", bpwm.getaOrbFlg());
		jo.put("year", bpwm.getYear());
		jo.put("name", bpwm.getName());
		jo.put("price", bpwm.getPrice());
		jo.put("m1", bpwm.getM1());
		jo.put("m2", bpwm.getM2());
		jo.put("m3", bpwm.getM3());
		jo.put("m4", bpwm.getM4());
		jo.put("m5", bpwm.getM5());
		jo.put("m6", bpwm.getM6());
		jo.put("m7", bpwm.getM7());
		jo.put("m8", bpwm.getM8());
		jo.put("m9", bpwm.getM9());
		jo.put("m10", bpwm.getM10());
		jo.put("m11", bpwm.getM11());
		jo.put("m12", bpwm.getM12());
		jo.put("totalPeople", bpwm.getTotalPeople());
		jo.put("eStandard", bpwm.geteStandard());
		jo.put("remark", bpwm.getRemark());

		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void updateArchWaterPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		BldQuotaByPeoWaterDao bpwd = new BldQuotaByPeoWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		BldQuotaByPeoWaterModel bpwm = new BldQuotaByPeoWaterModel();

		bpwm.setValueID(Integer.parseInt(request.getParameter("valueID")));
		bpwm.setBldOrAreaID(Integer.parseInt(request
				.getParameter("bldOrAreaID")));
		bpwm.setaOrbFlg(Integer.parseInt(request.getParameter("aOrbFlg")));
		bpwm.setYear(request.getParameter("year"));
		bpwm.setName(request.getParameter("name"));
		bpwm.setPrice(Float.parseFloat(request.getParameter("price")));
		bpwm.setM1(Integer.parseInt(request.getParameter("m1")));
		bpwm.setM2(Integer.parseInt(request.getParameter("m2")));
		bpwm.setM3(Integer.parseInt(request.getParameter("m3")));
		bpwm.setM4(Integer.parseInt(request.getParameter("m4")));
		bpwm.setM5(Integer.parseInt(request.getParameter("m5")));
		bpwm.setM6(Integer.parseInt(request.getParameter("m6")));
		bpwm.setM7(Integer.parseInt(request.getParameter("m7")));
		bpwm.setM8(Integer.parseInt(request.getParameter("m8")));
		bpwm.setM9(Integer.parseInt(request.getParameter("m9")));
		bpwm.setM10(Integer.parseInt(request.getParameter("m10")));
		bpwm.setM11(Integer.parseInt(request.getParameter("m11")));
		bpwm.setM12(Integer.parseInt(request.getParameter("m12")));
		bpwm.setTotalPeople(Integer.parseInt(request
				.getParameter("totalPeople")));
		bpwm.seteStandard(Float.parseFloat(request.getParameter("eStandard")));
		bpwm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = bpwd.modify(bpwm);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用水定额管理", "编辑建筑人员类别标准分配定额详细（用水）。");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"编辑建筑人员类别标准分配定额详细（用水）", "编辑失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"编辑建筑人员类别标准分配定额详细（用水）", "编辑失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void deleteArchWaterPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		BldQuotaByPeoWaterDao bpwd = new BldQuotaByPeoWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (bpwd.delete(valueID))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "建筑用水定额管理", "删除建筑人员类别标准分配定额详细（用水）。");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"删除建筑人员类别标准分配定额详细（用水）", "删除失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo.trim());
		out.flush();
		out.close();

	}

	private void addArchWaterPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		BldQuotaByPeoWaterDao bpwd = new BldQuotaByPeoWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		BldQuotaByPeoWaterModel bpwm = new BldQuotaByPeoWaterModel();

		bpwm.setBldOrAreaID(Integer.parseInt(request
				.getParameter("bldOrAreaID")));
		bpwm.setaOrbFlg(Integer.parseInt(request.getParameter("aOrbFlg")));
		bpwm.setYear(request.getParameter("year"));
		bpwm.setName(request.getParameter("name"));
		bpwm.setPrice(Float.parseFloat(request.getParameter("price")));
		bpwm.setM1(Integer.parseInt(request.getParameter("m1")));
		bpwm.setM2(Integer.parseInt(request.getParameter("m2")));
		bpwm.setM3(Integer.parseInt(request.getParameter("m3")));
		bpwm.setM4(Integer.parseInt(request.getParameter("m4")));
		bpwm.setM5(Integer.parseInt(request.getParameter("m5")));
		bpwm.setM6(Integer.parseInt(request.getParameter("m6")));
		bpwm.setM7(Integer.parseInt(request.getParameter("m7")));
		bpwm.setM8(Integer.parseInt(request.getParameter("m8")));
		bpwm.setM9(Integer.parseInt(request.getParameter("m9")));
		bpwm.setM10(Integer.parseInt(request.getParameter("m10")));
		bpwm.setM11(Integer.parseInt(request.getParameter("m11")));
		bpwm.setM12(Integer.parseInt(request.getParameter("m12")));
		bpwm.setTotalPeople(Integer.parseInt(request
				.getParameter("totalPeople")));
		bpwm.seteStandard(Float.parseFloat(request.getParameter("eStandard")));
		bpwm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = bpwd.insert(bpwm);
			if ("success".equals(msg))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用水定额管理", "添加建筑人员类别标准分配定额详细（用水）。");
			} else if ("fail".equals(msg))
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"添加建筑人员类别标准分配定额详细（用水）", "添加失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"添加建筑人员类别标准分配定额详细（用水）", "添加失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 写入日志
			try
			{
				log.writeLogErrInfo(userLoginName, "建筑用水定额管理",
						"添加建筑人员类别标准分配定额详细（用水）", e.getMessage());
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getArchWaterStructure(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		String strYear = request.getParameter("waterArchFixedSearchYear");
		String strStyle = request.getParameter("waterArchStyle");
		String BldOrArea_ID = request.getParameter("waterBldOrArea_ID");
		String AOrBFlg = request.getParameter("waterAOrBFlg");

		String resultInfo = "";
		resultInfo = wafd.getWaterStructureInfo(strYear, strStyle,
				BldOrArea_ID, AOrBFlg);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void getWaterArchQuotaUseInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		String strYear = request.getParameter("waterArchFixedSearchYear");
		String BldOrArea_ID = request.getParameter("waterBldOrArea_ID");
		String AOrBFlg = request.getParameter("waterAOrBFlg");

		String resultInfo = "";
		resultInfo = wafd.getWaterQuotaUseInfo(strYear, BldOrArea_ID, AOrBFlg);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void getPeoInfoByWaterBldOrArea_ID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		String strYear = request.getParameter("waterArchFixedSearchYear");
		String BldOrArea_ID = request.getParameter("waterBldOrArea_ID");
		String AOrBFlg = request.getParameter("waterAOrBFlg");

		ArrayList<WaterQuotaManagerModel> list = wafd
				.getWaterPeoInfoByPartment_ID(strYear, BldOrArea_ID, AOrBFlg);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		for (WaterQuotaManagerModel eqmm : list)
		{
			jo = new JSONObject();
			jo.put("valueID", eqmm.getValueID());
			jo.put("year", eqmm.getYear());
			jo.put("userAmStyle", eqmm.getUserAmStyle());
			jo.put("Biaozhun", eqmm.getBiaozhun());
			jo.put("countFixedGross", eqmm.getCountFixedGross());
			jo.put("price", eqmm.getPrice());
			jo.put("countFixedMoney", eqmm.getCountFixedMoney());
			jo.put("remark", eqmm.getRemark());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void getDevInfoByWaterBldOrArea_ID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		String strYear = request.getParameter("waterArchFixedSearchYear");
		String BldOrArea_ID = request.getParameter("waterBldOrArea_ID");
		String AOrBFlg = request.getParameter("waterAOrBFlg");

		ArrayList<WaterQuotaManagerModel> list = wafd
				.getWaterDevInfoByPartment_ID(strYear, BldOrArea_ID, AOrBFlg);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		for (WaterQuotaManagerModel eqmm : list)
		{
			jo = new JSONObject();
			jo.put("valueID", eqmm.getValueID());
			jo.put("year", eqmm.getYear());
			jo.put("userAmStyle", eqmm.getUserAmStyle());
			jo.put("Biaozhun", eqmm.getBiaozhun());
			jo.put("countFixedGross", eqmm.getCountFixedGross());
			jo.put("price", eqmm.getPrice());
			jo.put("countFixedMoney", eqmm.getCountFixedMoney());
			jo.put("remark", eqmm.getRemark());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void waterArchFixedPaginate(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		Integer thePageCount = Integer.parseInt(request
				.getParameter("waterArchFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("waterArchFixedPageIndex"));

		String strYear = request.getParameter("waterArchFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
			// //System.out.println(userID);
		}
		ArrayList<WaterQuotaManagerModel> list = wafd.getAllInfo(strYear,
				userID, thePageCount, thePageIndex);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", wafd.getTotalCount());
		json.add(jo);

		for (WaterQuotaManagerModel wqmm : list)
		{
			jo = new JSONObject();
			jo.put("year", wqmm.getYear());
			jo.put("name", wqmm.getName());
			jo.put("totalQuota", wqmm.getTotalQuota());
			jo.put("standardQuota", wqmm.getStandardQuota());
			jo.put("TiaozhengFixed", wqmm.getTiaozhengFixed());
			jo.put("addPerMonthQuota", wqmm.getAddPerMonthQuota());
			jo.put("ZMoney", wqmm.getZMoney());
			jo.put("remainQuota", wqmm.getRemainQuota());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

}
