package com.sf.energy.project.system.servlet;

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

import com.sf.energy.project.system.dao.AmFixedFinancialSettlementDao;
import com.sf.energy.project.system.dao.ArchitectureFixedDao;
import com.sf.energy.project.system.dao.BldBillSetDao;
import com.sf.energy.project.system.dao.BldFinalAccountsDao;
import com.sf.energy.project.system.dao.BldQuotaDetlByDevDao;
import com.sf.energy.project.system.dao.BldQuotaDetlByPeoDao;
import com.sf.energy.project.system.dao.FixedPrintDao;
import com.sf.energy.project.system.model.AmFixedFinancialSettlementModel;
import com.sf.energy.project.system.model.BldBillSetModel;
import com.sf.energy.project.system.model.BldFinalAccountsModel;
import com.sf.energy.project.system.model.BldQuotaDetlByDevModel;
import com.sf.energy.project.system.model.BldQuotaDetlByPeoModel;
import com.sf.energy.project.system.model.EnergyQuotaManagerModel;
import com.sf.energy.project.system.model.FixedPrintModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class ArchitectureFixedServlet extends HttpServlet
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

		if ("architectureFixedPaginate".equals(method))
			architectureFixedPaginate(request, response);
		if ("getPeoInfoByBldOrArea_ID".equals(method))
			getPeoInfoByBldOrArea_ID(request, response);
		if ("getDevInfoByBldOrArea_ID".equals(method))
			getDevInfoByBldOrArea_ID(request, response);
		if ("addArchPeoQuotaInfo".equals(method))
			addArchPeoQuotaInfo(request, response);
		if ("deletePeoQuotaInfo".equals(method))
			deletePeoQuotaInfo(request, response);
		if ("updateArchPeoQuotaInfo".equals(method))
			updateArchPeoQuotaInfo(request, response);
		if ("queryArchPeoOldInfo".equals(method))
			queryArchPeoOldInfo(request, response);
		if ("getQuotaStatisticInfo".equals(method))
			getQuotaStatisticInfo(request, response);

		if ("updateQuotaStatisticInfo".equals(method))
			updateQuotaStatisticInfo(request, response);

		if ("addArchDevQuotaInfo".equals(method))
			addArchDevQuotaInfo(request, response);
		if ("deleteDevQuotaInfo".equals(method))
			deleteDevQuotaInfo(request, response);
		if ("updateArchDevQuotaInfo".equals(method))
			updateArchDevQuotaInfo(request, response);
		if ("queryArchDevOldInfo".equals(method))
			queryArchDevOldInfo(request, response);

		if ("getArchQuotaUseInfo".equals(method))
			getArchQuotaUseInfo(request, response);
		if ("getArchEnergyStructure".equals(method))
			getArchEnergyStructure(request, response);

		// 定额财务结算
		if ("archFixedFinancialSettlement".equals(method))
			archFixedFinancialSettlement(request, response);
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

		// 同比环比
		if ("makeQuotaTablePerMonth".equals(method))
			makeQuotaTablePerMonth(request, response);
		// 定额明细排序
		if ("archFixedSort".equals(method))
			archFixedSort(request, response);
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

		List<FixedPrintModel> list = fpd.getArchPrintInfo(serialNo);

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
		BldBillSetDao bbsd = new BldBillSetDao();
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
				log.writeLog(userLoginName, "建筑用电定额管理", "删除建筑定额财务结算");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理", "删除建筑定额财务结算",
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

	private void archFixedSort(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		Integer thePageCount = Integer.parseInt(request
				.getParameter("archFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("archFixedPageIndex"));

		String strYear = request.getParameter("archFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		String order = "order by " + request.getParameter("TableName") + " "
				+ request.getParameter("Order");

		ArrayList<EnergyQuotaManagerModel> list = afd.sortQuery(strYear,
				userID, thePageCount, thePageIndex, order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", afd.getTotalCount());
		json.add(jo);

		for (EnergyQuotaManagerModel eqmm : list)
		{
			jo = new JSONObject();
			jo.put("year", eqmm.getYear());
			jo.put("name", eqmm.getName());
			jo.put("totalQuota", eqmm.getTotalQuota());
			jo.put("standardQuota", eqmm.getStandardQuota());
			jo.put("TiaozhengFixed", eqmm.getTiaozhengFixed());
			jo.put("addPerMonthQuota", eqmm.getAddPerMonthQuota());
			jo.put("ZMoney", eqmm.getZMoney());
			jo.put("remainQuota", eqmm.getRemainQuota());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void makeQuotaTablePerMonth(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		String strYear = request.getParameter("strYear");
		String BldOrArea_ID = request.getParameter("BldOrArea_ID");
		String AOrBFlg = request.getParameter("AOrBFlg");

		String resultInfo = "";
		resultInfo = afd.makeQuotaTablePerMonth(strYear, BldOrArea_ID, AOrBFlg);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addArchJuesuan(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException
	{
		OperationLogInterface log = new OperationLogImplement();
		BldFinalAccountsModel bfam = new BldFinalAccountsModel();
		BldFinalAccountsDao bfad = new BldFinalAccountsDao();

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

		bfam.setBILLID(Integer.parseInt(request.getParameter("BillId")));
		bfam.setYEAR(request.getParameter("Year"));
		bfam.setSTARTMM(Integer.parseInt(request.getParameter("StartMM")));
		bfam.setENDMM(Integer.parseInt(request.getParameter("EndMM")));
		bfam.setSTARTYMD(sdf2.format(sdf1.parse(request
				.getParameter("StartYMD"))));
		bfam.setENDYMD(sdf2.format(sdf1.parse(request.getParameter("EndYMD"))));
		bfam.setLASTSERIALNO(Integer.parseInt(request
				.getParameter("LastSerialNo")));
		bfam.setREMARK(request.getParameter("Remark"));

		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
			// //System.out.println(userID);
		}
		bfam.setUSERID(Integer.parseInt(userID));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date systemTime = new Date();
		bfam.setSYSTEMTIME(sdf.format(systemTime));

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

	private void archLoadZhangtao(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		BldBillSetDao bbsd = new BldBillSetDao();
		ArrayList<BldBillSetModel> list = new ArrayList<BldBillSetModel>();
		list = bbsd.getZhangtao();

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		for (BldBillSetModel bbsm : list)
		{
			jo = new JSONObject();
			jo.put("BILLID", bbsm.getBILLID());
			jo.put("BLDORAREA_ID", bbsm.getBLDORAREA_ID());
			jo.put("AORBFLG", bbsm.getAORBFLG());
			jo.put("BILLNAME", bbsm.getBILLNAME());

			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
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

		BldBillSetDao bbsd = new BldBillSetDao();

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (bbsd.addArchZhangtaoSave(bldOrAreaID, aOrbFlg, theName))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用电定额管理", "新建账套");
			} else
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理", "新建账套",
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

	/**
	 * 更新定额统计信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void updateQuotaStatisticInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		OperationLogInterface log = new OperationLogImplement();
		String strYear = request.getParameter("strYear");
		String BldOrArea_ID = request.getParameter("BldOrArea_ID");
		String AOrBFlg = request.getParameter("AOrBFlg");
		String values = request.getParameter("values");

		String resultInfo = "";
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = afd.saveArchQuotaStatisticInfo(strYear, BldOrArea_ID,
					AOrBFlg, values);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用电定额管理", "更新定额统计信息");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理", "更新定额统计信息",
						"更新失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理", "更新定额统计信息",
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

	/**
	 * 获取定额统计信息
	 * 
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getQuotaStatisticInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		String strYear = request.getParameter("strYear");
		String BldOrArea_ID = request.getParameter("BldOrArea_ID");
		String AOrBFlg = request.getParameter("AOrBFlg");

		String resultInfo = "";
		resultInfo = afd.getQuotaStatisticInfo(strYear, BldOrArea_ID, AOrBFlg);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void archFixedFinancialSettlement(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AmFixedFinancialSettlementDao affsd = new AmFixedFinancialSettlementDao();
		Integer thePageCount = Integer.parseInt(request
				.getParameter("architectureFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("architectureFixedPageIndex"));

		String strYear = request.getParameter("architectureFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
			// //System.out.println(userID);
		}
		String order = " order by " + request.getParameter("tableName") + " "
				+ request.getParameter("order");
		ArrayList<AmFixedFinancialSettlementModel> list = affsd
				.getFinancialSettlementInfo(strYear, userID, thePageCount,
						thePageIndex, order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", affsd.getTotalCount());
		json.add(jo);

		for (AmFixedFinancialSettlementModel affsm : list)
		{
			jo = new JSONObject();
			jo.put("BldOrArea_ID", affsm.getBldOrArea_ID());
			jo.put("AOrBflg", affsm.getAOrBflg());
			jo.put("SerialNo", affsm.getSerialNo());
			jo.put("name", affsm.getName());
			jo.put("remark", affsm.getRemark());
			jo.put("year", affsm.getYear());
			jo.put("period", affsm.getPeriod());
			jo.put("amMeterCnt", affsm.getAmMeterCnt());
			jo.put("systemTime", affsm.getSystemTime());
			jo.put("Users_Name", affsm.getUsers_Name());

			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getArchEnergyStructure(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		String strYear = request.getParameter("strYear");
		String strStyle = request.getParameter("strStyle");
		String BldOrArea_ID = request.getParameter("BldOrArea_ID");
		String AOrBFlg = request.getParameter("AOrBFlg");

		String resultInfo = "";
		resultInfo = afd.getEnergyStructureInfo(strYear, strStyle,
				BldOrArea_ID, AOrBFlg);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void getArchQuotaUseInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		String strYear = request.getParameter("strYear");
		String BldOrArea_ID = request.getParameter("BldOrArea_ID");
		String AOrBFlg = request.getParameter("AOrBFlg");

		String resultInfo = "";
		resultInfo = afd.getQuotaUseInfo(strYear, BldOrArea_ID, AOrBFlg);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void queryArchDevOldInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		
		BldQuotaDetlByDevDao bdd = new BldQuotaDetlByDevDao();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));
		BldQuotaDetlByDevModel bdm = bdd.queryByID(valueID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("bldOrAreaID", bdm.getBldOrAreaID());
		jo.put("aOrbFlg", bdm.getaOrbFlg());
		jo.put("year", bdm.getYear());
		jo.put("name", bdm.getName());
		jo.put("price", bdm.getPrice());
		jo.put("m1", bdm.getM1());
		jo.put("m2", bdm.getM2());
		jo.put("m3", bdm.getM3());
		jo.put("m4", bdm.getM4());
		jo.put("m5", bdm.getM5());
		jo.put("m6", bdm.getM6());
		jo.put("m7", bdm.getM7());
		jo.put("m8", bdm.getM8());
		jo.put("m9", bdm.getM9());
		jo.put("m10", bdm.getM10());
		jo.put("m11", bdm.getM11());
		jo.put("m12", bdm.getM12());
		jo.put("totalDevice", bdm.getTotalDevice());
		jo.put("power", bdm.getPower());
		jo.put("RunHoursPerMth", bdm.getRunHoursPerMth());
		jo.put("remark", bdm.getRemark());

		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void updateArchDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		BldQuotaDetlByDevDao bdd = new BldQuotaDetlByDevDao();
		OperationLogInterface log = new OperationLogImplement();
		BldQuotaDetlByDevModel bdm = new BldQuotaDetlByDevModel();

		bdm.setValueID(Integer.parseInt(request.getParameter("valueID")));
		bdm.setBldOrAreaID(Integer.parseInt(request.getParameter("bldOrAreaID")));
		bdm.setaOrbFlg(Integer.parseInt(request.getParameter("aOrbFlg")));
		bdm.setYear(request.getParameter("year"));
		bdm.setName(request.getParameter("name"));
		bdm.setPrice(Float.parseFloat(request.getParameter("price")));
		bdm.setM1(Integer.parseInt(request.getParameter("m1")));
		bdm.setM2(Integer.parseInt(request.getParameter("m2")));
		bdm.setM3(Integer.parseInt(request.getParameter("m3")));
		bdm.setM4(Integer.parseInt(request.getParameter("m4")));
		bdm.setM5(Integer.parseInt(request.getParameter("m5")));
		bdm.setM6(Integer.parseInt(request.getParameter("m6")));
		bdm.setM7(Integer.parseInt(request.getParameter("m7")));
		bdm.setM8(Integer.parseInt(request.getParameter("m8")));
		bdm.setM9(Integer.parseInt(request.getParameter("m9")));
		bdm.setM10(Integer.parseInt(request.getParameter("m10")));
		bdm.setM11(Integer.parseInt(request.getParameter("m11")));
		bdm.setM12(Integer.parseInt(request.getParameter("m12")));
		bdm.setTotalDevice(Integer.parseInt(request.getParameter("totalDevice")));
		bdm.setPower(Float.parseFloat(request.getParameter("power")));
		bdm.setRunHoursPerMth(Float.parseFloat(request
				.getParameter("RunHoursPerMth")));
		bdm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = bdd.modify(bdm);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用电定额管理", "编辑建筑设备类别标准分配定额详细（用电）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理",
						"编辑建筑设备类别标准分配定额详细（用电）", "编辑失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理",
						"编辑建筑设备类别标准分配定额详细（用电）", "编辑失败！！！");
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

	private void deleteDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		BldQuotaDetlByDevDao bdd = new BldQuotaDetlByDevDao();
		OperationLogInterface log = new OperationLogImplement();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (bdd.delete(valueID))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "建筑用电定额管理", "删除建筑设备类别标准分配定额详细（用电）");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理",
						"删除建筑设备类别标准分配定额详细（用电）", "删除失败！！！");
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

	private void addArchDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		BldQuotaDetlByDevDao bdd = new BldQuotaDetlByDevDao();
		OperationLogInterface log = new OperationLogImplement();
		BldQuotaDetlByDevModel bdm = new BldQuotaDetlByDevModel();

		bdm.setBldOrAreaID(Integer.parseInt(request.getParameter("bldOrAreaID")));
		bdm.setaOrbFlg(Integer.parseInt(request.getParameter("aOrbFlg")));
		bdm.setYear(request.getParameter("year"));
		bdm.setName(request.getParameter("name"));
		bdm.setPrice(Float.parseFloat(request.getParameter("price")));
		bdm.setM1(Integer.parseInt(request.getParameter("m1")));
		bdm.setM2(Integer.parseInt(request.getParameter("m2")));
		bdm.setM3(Integer.parseInt(request.getParameter("m3")));
		bdm.setM4(Integer.parseInt(request.getParameter("m4")));
		bdm.setM5(Integer.parseInt(request.getParameter("m5")));
		bdm.setM6(Integer.parseInt(request.getParameter("m6")));
		bdm.setM7(Integer.parseInt(request.getParameter("m7")));
		bdm.setM8(Integer.parseInt(request.getParameter("m8")));
		bdm.setM9(Integer.parseInt(request.getParameter("m9")));
		bdm.setM10(Integer.parseInt(request.getParameter("m10")));
		bdm.setM11(Integer.parseInt(request.getParameter("m11")));
		bdm.setM12(Integer.parseInt(request.getParameter("m12")));
		bdm.setTotalDevice(Integer.parseInt(request.getParameter("totalDevice")));
		bdm.setPower(Float.parseFloat(request.getParameter("power")));
		bdm.setRunHoursPerMth(Float.parseFloat(request
				.getParameter("RunHoursPerMth")));
		bdm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = bdd.insert(bdm);
			if ("success".equals(msg))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用电定额管理", "添加建筑设备类别标准分配定额详细（用电）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理",
						"添加建筑设备类别标准分配定额详细（用电）", "添加失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理",
						"添加建筑设备类别标准分配定额详细（用电）", "添加失败！！！");
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

	private void queryArchPeoOldInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		
		BldQuotaDetlByPeoDao bpd = new BldQuotaDetlByPeoDao();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));
		BldQuotaDetlByPeoModel bpm = bpd.queryByID(valueID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("bldOrAreaID", bpm.getBldOrAreaID());
		jo.put("aOrbFlg", bpm.getaOrbFlg());
		jo.put("year", bpm.getYear());
		jo.put("name", bpm.getName());
		jo.put("price", bpm.getPrice());
		jo.put("m1", bpm.getM1());
		jo.put("m2", bpm.getM2());
		jo.put("m3", bpm.getM3());
		jo.put("m4", bpm.getM4());
		jo.put("m5", bpm.getM5());
		jo.put("m6", bpm.getM6());
		jo.put("m7", bpm.getM7());
		jo.put("m8", bpm.getM8());
		jo.put("m9", bpm.getM9());
		jo.put("m10", bpm.getM10());
		jo.put("m11", bpm.getM11());
		jo.put("m12", bpm.getM12());
		jo.put("totalPeople", bpm.getTotalPeople());
		jo.put("eStandard", bpm.geteStandard());
		jo.put("remark", bpm.getRemark());

		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void updateArchPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		BldQuotaDetlByPeoDao bpd = new BldQuotaDetlByPeoDao();
		OperationLogInterface log = new OperationLogImplement();
		BldQuotaDetlByPeoModel bpm = new BldQuotaDetlByPeoModel();

		bpm.setValueID(Integer.parseInt(request.getParameter("valueID")));
		bpm.setBldOrAreaID(Integer.parseInt(request.getParameter("bldOrAreaID")));
		bpm.setaOrbFlg(Integer.parseInt(request.getParameter("aOrbFlg")));
		bpm.setYear(request.getParameter("year"));
		bpm.setName(request.getParameter("name"));
		bpm.setPrice(Float.parseFloat(request.getParameter("price")));
		bpm.setM1(Integer.parseInt(request.getParameter("m1")));
		bpm.setM2(Integer.parseInt(request.getParameter("m2")));
		bpm.setM3(Integer.parseInt(request.getParameter("m3")));
		bpm.setM4(Integer.parseInt(request.getParameter("m4")));
		bpm.setM5(Integer.parseInt(request.getParameter("m5")));
		bpm.setM6(Integer.parseInt(request.getParameter("m6")));
		bpm.setM7(Integer.parseInt(request.getParameter("m7")));
		bpm.setM8(Integer.parseInt(request.getParameter("m8")));
		bpm.setM9(Integer.parseInt(request.getParameter("m9")));
		bpm.setM10(Integer.parseInt(request.getParameter("m10")));
		bpm.setM11(Integer.parseInt(request.getParameter("m11")));
		bpm.setM12(Integer.parseInt(request.getParameter("m12")));
		bpm.setTotalPeople(Integer.parseInt(request.getParameter("totalPeople")));
		bpm.seteStandard(Float.parseFloat(request.getParameter("eStandard")));
		bpm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = bpd.modify(bpm);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用电定额管理", "编辑建筑人员类别标准分配定额详细（用电）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理",
						"编辑建筑人员类别标准分配定额详细（用电）", "编辑失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理",
						"编辑建筑人员类别标准分配定额详细（用电）", "编辑失败！！！");
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

	private void deletePeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		BldQuotaDetlByPeoDao bpd = new BldQuotaDetlByPeoDao();
		OperationLogInterface log = new OperationLogImplement();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (bpd.delete(valueID))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "建筑用电定额管理", "删除建筑人员类别标准分配定额详细（用电）");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理",
						"删除建筑人员类别标准分配定额详细（用电）", "删除失败！！！");
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

	/**
	 * 按人员添加定额
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void addArchPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		BldQuotaDetlByPeoDao bpd = new BldQuotaDetlByPeoDao();
		OperationLogInterface log = new OperationLogImplement();
		BldQuotaDetlByPeoModel bpm = new BldQuotaDetlByPeoModel();

		bpm.setBldOrAreaID(Integer.parseInt(request.getParameter("bldOrAreaID")));
		bpm.setaOrbFlg(Integer.parseInt(request.getParameter("aOrbFlg")));
		bpm.setYear(request.getParameter("year"));
		bpm.setName(request.getParameter("name"));
		bpm.setPrice(Float.parseFloat(request.getParameter("price")));
		bpm.setM1(Integer.parseInt(request.getParameter("m1")));
		bpm.setM2(Integer.parseInt(request.getParameter("m2")));
		bpm.setM3(Integer.parseInt(request.getParameter("m3")));
		bpm.setM4(Integer.parseInt(request.getParameter("m4")));
		bpm.setM5(Integer.parseInt(request.getParameter("m5")));
		bpm.setM6(Integer.parseInt(request.getParameter("m6")));
		bpm.setM7(Integer.parseInt(request.getParameter("m7")));
		bpm.setM8(Integer.parseInt(request.getParameter("m8")));
		bpm.setM9(Integer.parseInt(request.getParameter("m9")));
		bpm.setM10(Integer.parseInt(request.getParameter("m10")));
		bpm.setM11(Integer.parseInt(request.getParameter("m11")));
		bpm.setM12(Integer.parseInt(request.getParameter("m12")));
		bpm.setTotalPeople(Integer.parseInt(request.getParameter("totalPeople")));
		bpm.seteStandard(Float.parseFloat(request.getParameter("eStandard")));
		bpm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = bpd.insert(bpm);
			if ("success".equals(msg))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "建筑用电定额管理", "添加建筑人员类别标准分配定额详细（用电）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理",
						"添加建筑人员类别标准分配定额详细（用电）", "添加失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "建筑用电定额管理",
						"添加建筑人员类别标准分配定额详细（用电）", "添加失败！！！");
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

	private void getDevInfoByBldOrArea_ID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		String strYear = request.getParameter("strYear");
		String BldOrArea_ID = request.getParameter("BldOrArea_ID");
		String AOrBFlg = request.getParameter("AOrBFlg");

		ArrayList<EnergyQuotaManagerModel> list = afd.getDevInfoByBldOrArea_ID(
				strYear, BldOrArea_ID, AOrBFlg);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		for (EnergyQuotaManagerModel eqmm : list)
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

	private void getPeoInfoByBldOrArea_ID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		String strYear = request.getParameter("strYear");
		String BldOrArea_ID = request.getParameter("BldOrArea_ID");
		String AOrBFlg = request.getParameter("AOrBFlg");

		ArrayList<EnergyQuotaManagerModel> list = afd.getPeoInfoByBldOrArea_ID(
				strYear, BldOrArea_ID, AOrBFlg);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		for (EnergyQuotaManagerModel eqmm : list)
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

	private void architectureFixedPaginate(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		Integer thePageCount = Integer.parseInt(request
				.getParameter("architectureFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("architectureFixedPageIndex"));

		String strYear = request.getParameter("architectureFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		ArrayList<EnergyQuotaManagerModel> list = afd.getAllInfo(strYear,
				userID, thePageCount, thePageIndex);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", afd.getTotalCount());
		json.add(jo);

		for (EnergyQuotaManagerModel eqmm : list)
		{
			jo = new JSONObject();
			jo.put("year", eqmm.getYear());
			jo.put("name", eqmm.getName());
			jo.put("totalQuota", eqmm.getTotalQuota());
			jo.put("standardQuota", eqmm.getStandardQuota());
			jo.put("TiaozhengFixed", eqmm.getTiaozhengFixed());
			jo.put("addPerMonthQuota", eqmm.getAddPerMonthQuota());
			jo.put("ZMoney", eqmm.getZMoney());
			jo.put("remainQuota", eqmm.getRemainQuota());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

}
