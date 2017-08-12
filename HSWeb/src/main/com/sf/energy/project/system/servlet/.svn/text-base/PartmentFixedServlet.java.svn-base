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
import com.sf.energy.project.system.dao.DeptBillSetDao;
import com.sf.energy.project.system.dao.DeptFinalAccountsDao;
import com.sf.energy.project.system.dao.DeptQuotaDetlByDevDao;
import com.sf.energy.project.system.dao.DeptQuotaDetlByPeoDao;
import com.sf.energy.project.system.dao.FixedPrintDao;
import com.sf.energy.project.system.dao.PartmentFixedDao;
import com.sf.energy.project.system.model.AmFixedFinancialSettlementModel;
import com.sf.energy.project.system.model.DeptBillSetModel;
import com.sf.energy.project.system.model.DeptFinalAccountsModel;
import com.sf.energy.project.system.model.DeptQuotaDetlByDevModel;
import com.sf.energy.project.system.model.DeptQuotaDetlByPeoModel;
import com.sf.energy.project.system.model.EnergyQuotaManagerModel;
import com.sf.energy.project.system.model.FixedPrintModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class PartmentFixedServlet extends HttpServlet
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

		if ("partmentFixedPaginate".equals(method))
			partmentFixedPaginate(request, response);
		if ("getPeoInfoByPartment_ID".equals(method))
			getPeoInfoByPartment_ID(request, response);
		if ("getDevInfoByPartment_ID".equals(method))
			getDevInfoByPartment_ID(request, response);

		if ("addDeptPeoQuotaInfo".equals(method))
			addDeptPeoQuotaInfo(request, response);
		if ("deleteDeptPeoQuotaInfo".equals(method))
			deleteDeptPeoQuotaInfo(request, response);
		if ("updateDeptPeoQuotaInfo".equals(method))
			updateDeptPeoQuotaInfo(request, response);
		if ("queryDeptPeoOldInfo".equals(method))
			queryDeptPeoOldInfo(request, response);

		if ("addDeptDevQuotaInfo".equals(method))
			addDeptDevQuotaInfo(request, response);
		if ("deleteDeptDevQuotaInfo".equals(method))
			deleteDeptDevQuotaInfo(request, response);
		if ("updateDeptDevQuotaInfo".equals(method))
			updateDeptDevQuotaInfo(request, response);
		if ("queryDeptDevOldInfo".equals(method))
			queryDeptDevOldInfo(request, response);

		if ("getDeptQuotaUseInfo".equals(method))
			getDeptQuotaUseInfo(request, response);
		if ("getDeptEnergyStructure".equals(method))
			getDeptEnergyStructure(request, response);

		// 定额财务结算
		if ("deptFixedFinancialSettlement".equals(method))
			deptFixedFinancialSettlement(request, response);
		if ("deptLoadZhangtao".equals(method))
			deptLoadZhangtao(request, response);
		if ("addDeptZhangtao".equals(method))
			addDeptZhangtao(request, response);
		if ("addDeptJuesuan".equals(method))
			addDeptJuesuan(request, response);
		if ("deptDelJiesuan".equals(method))
			deptDelJiesuan(request, response);
		if ("fixedPrint".equals(method))
			fixedPrint(request, response);

		if ("getDeptQuotaStatisticInfo".equals(method))
			getDeptQuotaStatisticInfo(request, response);

		if ("updateDeptQuotaStatisticInfo".equals(method))
			updateDeptQuotaStatisticInfo(request, response);

		// 同比环比
		if ("makeDeptQuotaTablePerMonth".equals(method))
			makeDeptQuotaTablePerMonth(request, response);

		// 定额一览排序
		if ("deptFixedSort".equals(method))
			deptFixedSort(request, response);
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

		List<FixedPrintModel> list = fpd.getDeptPrintInfo(serialNo);

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

	private void deptDelJiesuan(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		OperationLogInterface log = new OperationLogImplement();
		DeptFinalAccountsDao dfad = new DeptFinalAccountsDao();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (dfad.delete(valueID))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "部门用电定额管理", "删除部门定额财务结算");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理", "删除部门定额财务结算",
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

	private void addDeptJuesuan(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, IOException
	{
		
		OperationLogInterface log = new OperationLogImplement();
		DeptFinalAccountsModel dfam = new DeptFinalAccountsModel();
		DeptFinalAccountsDao dfad = new DeptFinalAccountsDao();

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

		dfam.setBILLID(Integer.parseInt(request.getParameter("BillId")));
		dfam.setYEAR(request.getParameter("Year"));
		dfam.setSTARTMM(Integer.parseInt(request.getParameter("StartMM")));
		dfam.setENDMM(Integer.parseInt(request.getParameter("EndMM")));
		dfam.setSTARTYMD(sdf2.format(sdf1.parse(request
				.getParameter("StartYMD"))));
		dfam.setENDYMD(sdf2.format(sdf1.parse(request.getParameter("EndYMD"))));
		dfam.setLASTSERIALNO(Integer.parseInt(request
				.getParameter("LastSerialNo")));
		dfam.setREMARK(request.getParameter("Remark"));

		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
			// //System.out.println(userID);
		}
		dfam.setUSERID(Integer.parseInt(userID));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date systemTime = new Date();
		dfam.setSYSTEMTIME(sdf.format(systemTime));

		String resultInfo = null;
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (dfad.insert(dfam))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用电定额管理", "新建决算记录");
			} else
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理", "新建决算记录",
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

	private void addDeptZhangtao(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		OperationLogInterface log = new OperationLogImplement();
		String partmentID = request.getParameter("partmentID");
		String theName = request.getParameter("theName");

		DeptBillSetDao dbsd = new DeptBillSetDao();

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (dbsd.addDeptZhangtaoSave(partmentID, theName))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用电定额管理", "新建账套");
			} else
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理", "新建账套",
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

	private void deptLoadZhangtao(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		DeptBillSetDao dbsd = new DeptBillSetDao();
		ArrayList<DeptBillSetModel> list = new ArrayList<DeptBillSetModel>();
		list = dbsd.getZhangtao();

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		for (DeptBillSetModel dbsm : list)
		{
			jo = new JSONObject();
			jo.put("BILLID", dbsm.getBILLID());
			jo.put("PARTMENT_ID", dbsm.getPARTMENT_ID());
			jo.put("BILLNAME", dbsm.getBILLNAME());

			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void deptFixedSort(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		PartmentFixedDao pfd = new PartmentFixedDao();
		Integer thePageCount = Integer.parseInt(request
				.getParameter("deptFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("deptFixedPageIndex"));

		String strYear = request.getParameter("deptFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		String order = "order by " + request.getParameter("TableName") + " "
				+ request.getParameter("Order");

		ArrayList<EnergyQuotaManagerModel> list = pfd.sortQuery(strYear,
				userID, thePageCount, thePageIndex, order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", pfd.getTotalCount());
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

	private void makeDeptQuotaTablePerMonth(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		PartmentFixedDao pfd = new PartmentFixedDao();
		String strYear = request.getParameter("strYear");
		String strPartment_ID = request.getParameter("strPartment_ID");

		String resultInfo = "";
		resultInfo = pfd.makeQuotaTablePerMonth(strYear, strPartment_ID);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void updateDeptQuotaStatisticInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		PartmentFixedDao pfd = new PartmentFixedDao();	
		OperationLogInterface log = new OperationLogImplement();
		String strYear = request.getParameter("strYear");
		String strPartment_ID = request.getParameter("strPartment_ID");
		String values = request.getParameter("values");

		String resultInfo = "";
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = pfd.saveDeptQuotaStatisticInfo(strYear,
					strPartment_ID, values);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用电定额管理", "更新定额统计信息");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理", "更新定额统计信息",
						"更新失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理", "更新定额统计信息",
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

	private void getDeptQuotaStatisticInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		PartmentFixedDao pfd = new PartmentFixedDao();
		String strYear = request.getParameter("strYear");
		String strPartment_ID = request.getParameter("strPartment_ID");

		String resultInfo = "";
		resultInfo = pfd.getQuotaStatisticInfo(strYear, strPartment_ID);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void deptFixedFinancialSettlement(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AmFixedFinancialSettlementDao affsd = new AmFixedFinancialSettlementDao();
		Integer thePageCount = Integer.parseInt(request
				.getParameter("partmentFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("partmentFixedPageIndex"));

		String strYear = request.getParameter("partmentFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}
		String order = " order by " + request.getParameter("tableName") + " "
				+ request.getParameter("order");
		ArrayList<AmFixedFinancialSettlementModel> list = affsd
				.getDeptFinancialSettlementInfo(strYear, userID, thePageCount,
						thePageIndex, order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", affsd.getTotalCount());
		json.add(jo);

		for (AmFixedFinancialSettlementModel affsm : list)
		{
			jo = new JSONObject();
			jo.put("Partment_ID", affsm.getPartment_ID());
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

	private void getDeptEnergyStructure(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		PartmentFixedDao pfd = new PartmentFixedDao();
		String strYear = request.getParameter("strYear");
		String strStyle = request.getParameter("strStyle");
		String strPartment_ID = request.getParameter("strPartment_ID");

		String resultInfo = "";
		resultInfo = pfd.getEnergyStructureInfo(strYear, strStyle,
				strPartment_ID);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getDeptQuotaUseInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		PartmentFixedDao pfd = new PartmentFixedDao();
		String strYear = request.getParameter("strYear");
		String strPartment_ID = request.getParameter("strPartment_ID");

		String resultInfo = "";
		resultInfo = pfd.getQuotaUseInfo(strYear, strPartment_ID);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void queryDeptDevOldInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		
		DeptQuotaDetlByDevDao ddd = new DeptQuotaDetlByDevDao();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));
		DeptQuotaDetlByDevModel ddm = ddd.queryByID(valueID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("partmentID", ddm.getPartmentID());
		jo.put("year", ddm.getYear());
		jo.put("name", ddm.getName());
		jo.put("price", ddm.getPrice());
		jo.put("m1", ddm.getM1());
		jo.put("m2", ddm.getM2());
		jo.put("m3", ddm.getM3());
		jo.put("m4", ddm.getM4());
		jo.put("m5", ddm.getM5());
		jo.put("m6", ddm.getM6());
		jo.put("m7", ddm.getM7());
		jo.put("m8", ddm.getM8());
		jo.put("m9", ddm.getM9());
		jo.put("m10", ddm.getM10());
		jo.put("m11", ddm.getM11());
		jo.put("m12", ddm.getM12());
		jo.put("totalDevice", ddm.getTotalDevice());
		jo.put("power", ddm.getPower());
		jo.put("RunHoursPerMth", ddm.getRunHoursPerMth());
		jo.put("remark", ddm.getRemark());

		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void updateDeptDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		DeptQuotaDetlByDevDao ddd = new DeptQuotaDetlByDevDao();
		OperationLogInterface log = new OperationLogImplement();
		DeptQuotaDetlByDevModel ddm = new DeptQuotaDetlByDevModel();

		ddm.setValueID(Integer.parseInt(request.getParameter("valueID")));
		ddm.setPartmentID(Integer.parseInt(request.getParameter("partmentID")));
		ddm.setYear(request.getParameter("year"));
		ddm.setName(request.getParameter("name"));
		ddm.setPrice(Float.parseFloat(request.getParameter("price")));
		ddm.setM1(Integer.parseInt(request.getParameter("m1")));
		ddm.setM2(Integer.parseInt(request.getParameter("m2")));
		ddm.setM3(Integer.parseInt(request.getParameter("m3")));
		ddm.setM4(Integer.parseInt(request.getParameter("m4")));
		ddm.setM5(Integer.parseInt(request.getParameter("m5")));
		ddm.setM6(Integer.parseInt(request.getParameter("m6")));
		ddm.setM7(Integer.parseInt(request.getParameter("m7")));
		ddm.setM8(Integer.parseInt(request.getParameter("m8")));
		ddm.setM9(Integer.parseInt(request.getParameter("m9")));
		ddm.setM10(Integer.parseInt(request.getParameter("m10")));
		ddm.setM11(Integer.parseInt(request.getParameter("m11")));
		ddm.setM12(Integer.parseInt(request.getParameter("m12")));
		ddm.setTotalDevice(Integer.parseInt(request.getParameter("totalDevice")));
		ddm.setPower(Float.parseFloat(request.getParameter("power")));
		ddm.setRunHoursPerMth(Float.parseFloat(request
				.getParameter("RunHoursPerMth")));
		ddm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = ddd.modify(ddm);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用电定额管理", "编辑部门设备类别标准分配定额详细（用电）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理",
						"编辑部门设备类别标准分配定额详细（用电）", "编辑失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理",
						"编辑部门设备类别标准分配定额详细（用电）", "编辑失败！！！");
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

	private void deleteDeptDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		DeptQuotaDetlByDevDao ddd = new DeptQuotaDetlByDevDao();
		OperationLogInterface log = new OperationLogImplement();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (ddd.delete(valueID))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "部门用电定额管理", "删除部门设备类别标准分配定额详细（用电）");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理",
						"删除部门设备类别标准分配定额详细（用电）", "删除失败！！！");
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

	private void addDeptDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		DeptQuotaDetlByDevDao ddd = new DeptQuotaDetlByDevDao();
		OperationLogInterface log = new OperationLogImplement();
		DeptQuotaDetlByDevModel ddm = new DeptQuotaDetlByDevModel();

		ddm.setPartmentID(Integer.parseInt(request.getParameter("partmentID")));
		ddm.setYear(request.getParameter("year"));
		ddm.setName(request.getParameter("name"));
		ddm.setPrice(Float.parseFloat(request.getParameter("price")));
		ddm.setM1(Integer.parseInt(request.getParameter("m1")));
		ddm.setM2(Integer.parseInt(request.getParameter("m2")));
		ddm.setM3(Integer.parseInt(request.getParameter("m3")));
		ddm.setM4(Integer.parseInt(request.getParameter("m4")));
		ddm.setM5(Integer.parseInt(request.getParameter("m5")));
		ddm.setM6(Integer.parseInt(request.getParameter("m6")));
		ddm.setM7(Integer.parseInt(request.getParameter("m7")));
		ddm.setM8(Integer.parseInt(request.getParameter("m8")));
		ddm.setM9(Integer.parseInt(request.getParameter("m9")));
		ddm.setM10(Integer.parseInt(request.getParameter("m10")));
		ddm.setM11(Integer.parseInt(request.getParameter("m11")));
		ddm.setM12(Integer.parseInt(request.getParameter("m12")));
		ddm.setTotalDevice(Integer.parseInt(request.getParameter("totalDevice")));
		ddm.setPower(Float.parseFloat(request.getParameter("power")));
		ddm.setRunHoursPerMth(Float.parseFloat(request
				.getParameter("RunHoursPerMth")));
		ddm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = ddd.insert(ddm);
			if ("success".equals(msg))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用电定额管理", "添加部门设备类别标准分配定额详细（用电）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理",
						"添加部门设备类别标准分配定额详细（用电）", "添加失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理",
						"添加部门设备类别标准分配定额详细（用电）", "添加失败！！！");
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

	private void queryDeptPeoOldInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		
		DeptQuotaDetlByPeoDao dpd = new DeptQuotaDetlByPeoDao();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));
		DeptQuotaDetlByPeoModel dpm = dpd.queryByID(valueID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("partmentID", dpm.getPartmentID());
		jo.put("year", dpm.getYear());
		jo.put("name", dpm.getName());
		jo.put("price", dpm.getPrice());
		jo.put("m1", dpm.getM1());
		jo.put("m2", dpm.getM2());
		jo.put("m3", dpm.getM3());
		jo.put("m4", dpm.getM4());
		jo.put("m5", dpm.getM5());
		jo.put("m6", dpm.getM6());
		jo.put("m7", dpm.getM7());
		jo.put("m8", dpm.getM8());
		jo.put("m9", dpm.getM9());
		jo.put("m10", dpm.getM10());
		jo.put("m11", dpm.getM11());
		jo.put("m12", dpm.getM12());
		jo.put("totalPeople", dpm.getTotalPeople());
		jo.put("eStandard", dpm.geteStandard());
		jo.put("remark", dpm.getRemark());

		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void updateDeptPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		DeptQuotaDetlByPeoDao dpd = new DeptQuotaDetlByPeoDao();
		OperationLogInterface log = new OperationLogImplement();
		DeptQuotaDetlByPeoModel dpm = new DeptQuotaDetlByPeoModel();

		dpm.setValueID(Integer.parseInt(request.getParameter("valueID")));
		dpm.setPartmentID(Integer.parseInt(request.getParameter("partmentID")));
		dpm.setYear(request.getParameter("year"));
		dpm.setName(request.getParameter("name"));
		dpm.setPrice(Float.parseFloat(request.getParameter("price")));
		dpm.setM1(Integer.parseInt(request.getParameter("m1")));
		dpm.setM2(Integer.parseInt(request.getParameter("m2")));
		dpm.setM3(Integer.parseInt(request.getParameter("m3")));
		dpm.setM4(Integer.parseInt(request.getParameter("m4")));
		dpm.setM5(Integer.parseInt(request.getParameter("m5")));
		dpm.setM6(Integer.parseInt(request.getParameter("m6")));
		dpm.setM7(Integer.parseInt(request.getParameter("m7")));
		dpm.setM8(Integer.parseInt(request.getParameter("m8")));
		dpm.setM9(Integer.parseInt(request.getParameter("m9")));
		dpm.setM10(Integer.parseInt(request.getParameter("m10")));
		dpm.setM11(Integer.parseInt(request.getParameter("m11")));
		dpm.setM12(Integer.parseInt(request.getParameter("m12")));
		dpm.setTotalPeople(Integer.parseInt(request.getParameter("totalPeople")));
		dpm.seteStandard(Float.parseFloat(request.getParameter("eStandard")));
		dpm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = dpd.modify(dpm);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用电定额管理", "编辑部门人员类别标准分配定额详细（用电）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理",
						"编辑部门人员类别标准分配定额详细（用电）", "编辑失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理",
						"编辑部门人员类别标准分配定额详细（用电）", "编辑失败！！！");
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

	private void deleteDeptPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
	
		DeptQuotaDetlByPeoDao dpd = new DeptQuotaDetlByPeoDao();
		OperationLogInterface log = new OperationLogImplement();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (dpd.delete(valueID))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "部门用电定额管理", "删除部门人员类别标准分配定额详细（用电）");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理",
						"删除部门人员类别标准分配定额详细（用电）", "删除失败！！！");
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

	private void addDeptPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		DeptQuotaDetlByPeoDao dpd = new DeptQuotaDetlByPeoDao();
		OperationLogInterface log = new OperationLogImplement();
		DeptQuotaDetlByPeoModel dpm = new DeptQuotaDetlByPeoModel();

		dpm.setPartmentID(Integer.parseInt(request.getParameter("partmentID")));
		dpm.setYear(request.getParameter("year"));
		dpm.setName(request.getParameter("name"));
		dpm.setPrice(Float.parseFloat(request.getParameter("price")));
		dpm.setM1(Integer.parseInt(request.getParameter("m1")));
		dpm.setM2(Integer.parseInt(request.getParameter("m2")));
		dpm.setM3(Integer.parseInt(request.getParameter("m3")));
		dpm.setM4(Integer.parseInt(request.getParameter("m4")));
		dpm.setM5(Integer.parseInt(request.getParameter("m5")));
		dpm.setM6(Integer.parseInt(request.getParameter("m6")));
		dpm.setM7(Integer.parseInt(request.getParameter("m7")));
		dpm.setM8(Integer.parseInt(request.getParameter("m8")));
		dpm.setM9(Integer.parseInt(request.getParameter("m9")));
		dpm.setM10(Integer.parseInt(request.getParameter("m10")));
		dpm.setM11(Integer.parseInt(request.getParameter("m11")));
		dpm.setM12(Integer.parseInt(request.getParameter("m12")));
		dpm.setTotalPeople(Integer.parseInt(request.getParameter("totalPeople")));
		dpm.seteStandard(Float.parseFloat(request.getParameter("eStandard")));
		dpm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = dpd.insert(dpm);
			if ("success".equals(msg))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用电定额管理", "添加部门人员类别标准分配定额详细（用电）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理",
						"添加部门人员类别标准分配定额详细（用电）", "添加失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用电定额管理",
						"添加部门人员类别标准分配定额详细（用电）", "添加失败！！！");
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

	private void getDevInfoByPartment_ID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		PartmentFixedDao pfd = new PartmentFixedDao();
		String strYear = request.getParameter("strYear");
		String strPartment_ID = request.getParameter("strPartment_ID");

		ArrayList<EnergyQuotaManagerModel> list = pfd.getDevInfoByPartment_ID(
				strYear, strPartment_ID);

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

	private void getPeoInfoByPartment_ID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		PartmentFixedDao pfd = new PartmentFixedDao();
		String strYear = request.getParameter("strYear");
		String strPartment_ID = request.getParameter("strPartment_ID");

		ArrayList<EnergyQuotaManagerModel> list = pfd.getPeoInfoByPartment_ID(
				strYear, strPartment_ID);

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

	private void partmentFixedPaginate(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		PartmentFixedDao pfd = new PartmentFixedDao();
		Integer thePageCount = Integer.parseInt(request
				.getParameter("partmentFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("partmentFixedPageIndex"));

		String strYear = request.getParameter("partmentFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		ArrayList<EnergyQuotaManagerModel> list = pfd.getAllInfo(strYear,
				userID, thePageCount, thePageIndex);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", pfd.getTotalCount());
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
