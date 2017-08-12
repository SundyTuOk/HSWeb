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

import com.sf.energy.project.system.dao.DeptQuotaByDevWaterDao;
import com.sf.energy.project.system.dao.DeptQuotaByPeoWaterDao;
import com.sf.energy.project.system.dao.FixedPrintDao;
import com.sf.energy.project.system.model.DeptQuotaByDevWaterModel;
import com.sf.energy.project.system.model.DeptQuotaByPeoWaterModel;
import com.sf.energy.project.system.model.FixedPrintModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;
import com.sf.energy.water.manager.current.dao.DeptBillSetWaterDao;
import com.sf.energy.water.manager.current.dao.DeptFinalAccountsWaterDao;
import com.sf.energy.water.manager.current.dao.WaterFixedFinancialSettlementDao;
import com.sf.energy.water.manager.current.dao.WaterPartmentFixedDao;
import com.sf.energy.water.manager.current.model.DeptBillSetWaterModel;
import com.sf.energy.water.manager.current.model.DeptFinalAccountsWaterModel;
import com.sf.energy.water.manager.current.model.WaterFixedFinancialSettlementModel;
import com.sf.energy.water.manager.current.model.WaterQuotaManagerModel;

public class WaterDeptFixedServlet extends HttpServlet
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

		if ("waterDeptFixedPaginate".equals(method))
			waterDeptFixedPaginate(request, response);
		if ("getDevInfoByWaterPartment_ID".equals(method))
			getDevInfoByWaterPartment_ID(request, response);
		if ("getPeoInfoByWaterPartment_ID".equals(method))
			getPeoInfoByWaterPartment_ID(request, response);
		if ("getWaterDeptQuotaUseInfo".equals(method))
			getWaterDeptQuotaUseInfo(request, response);
		if ("getDeptWaterStructure".equals(method))
			getDeptWaterStructure(request, response);

		if ("addDeptWaterPeoQuotaInfo".equals(method))
			addDeptWaterPeoQuotaInfo(request, response);
		if ("deleteDeptWaterPeoQuotaInfo".equals(method))
			deleteDeptWaterPeoQuotaInfo(request, response);
		if ("updateDeptWaterPeoQuotaInfo".equals(method))
			updateDeptWaterPeoQuotaInfo(request, response);
		if ("queryDeptWaterPeoOldInfo".equals(method))
			queryDeptWaterPeoOldInfo(request, response);

		if ("addDeptWaterDevQuotaInfo".equals(method))
			addDeptWaterDevQuotaInfo(request, response);
		if ("deleteDeptWaterDevQuotaInfo".equals(method))
			deleteDeptWaterDevQuotaInfo(request, response);
		if ("updateDeptWaterDevQuotaInfo".equals(method))
			updateDeptWaterDevQuotaInfo(request, response);
		if ("queryDeptWaterDevOldInfo".equals(method))
			queryDeptWaterDevOldInfo(request, response);

		// 定额财务结算
		if ("deptWaterFixedFinancialSettlement".equals(method))
			deptWaterFixedFinancialSettlement(request, response);
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

		if ("getWaterDeptQuotaStatisticInfo".equals(method))
			getWaterDeptQuotaStatisticInfo(request, response);

		if ("updateWaterDeptQuotaStatisticInfo".equals(method))
			updateWaterDeptQuotaStatisticInfo(request, response);

		// 同比环比
		if ("makeDeptWaterQuotaTablePerMonth".equals(method))
			makeDeptWaterQuotaTablePerMonth(request, response);
		// 定额明细排序
		if ("waterDeptFixedSort".equals(method))
			waterDeptFixedSort(request, response);

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

		List<FixedPrintModel> list = fpd.getDeptWaterPrintInfo(serialNo);

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
		DeptFinalAccountsWaterDao dfad = new DeptFinalAccountsWaterDao();
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
				log.writeLog(userLoginName, "部门用水定额管理", "删除部门定额财务结算");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理", "删除部门定额财务结算",
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
		DeptFinalAccountsWaterModel dfam = new DeptFinalAccountsWaterModel();
		DeptFinalAccountsWaterDao dfad = new DeptFinalAccountsWaterDao();

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
				log.writeLog(userLoginName, "部门用水定额管理", "新建决算记录");
			} else
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理", "新建决算记录",
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

		DeptBillSetWaterDao dbsd = new DeptBillSetWaterDao();

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (dbsd.addDeptZhangtaoSave(partmentID, theName))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用水定额管理", "新建账套");
			} else
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理", "新建账套",
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
		DeptBillSetWaterDao dbsd = new DeptBillSetWaterDao();
		ArrayList<DeptBillSetWaterModel> list = new ArrayList<DeptBillSetWaterModel>();
		list = dbsd.getZhangtao();

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		for (DeptBillSetWaterModel dbsm : list)
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

	private void waterDeptFixedSort(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		Integer thePageCount = Integer.parseInt(request
				.getParameter("waterDeptFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("waterDeptFixedPageIndex"));

		String strYear = request.getParameter("waterDeptFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
			// //System.out.println(userID);
		}
		String order = "order by " + request.getParameter("TableName") + " "
				+ request.getParameter("Order");

		ArrayList<WaterQuotaManagerModel> list = null;
		list = wpfd.sortQuery(strYear, userID, thePageCount, thePageIndex,
				order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", wpfd.getTotalCount());
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

	private void makeDeptWaterQuotaTablePerMonth(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		String strYear = request.getParameter("waterDeptFixedSearchYear");
		String strPartment_ID = request.getParameter("waterPartment_ID");

		String resultInfo = "";
		resultInfo = wpfd.makeQuotaTablePerMonth(strYear, strPartment_ID);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void updateWaterDeptQuotaStatisticInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		OperationLogInterface log = new OperationLogImplement();
		String strYear = request.getParameter("waterDeptFixedSearchYear");
		String strPartment_ID = request.getParameter("waterPartment_ID");
		String values = request.getParameter("values");

		String resultInfo = "";
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = wpfd.saveWaterDeptQuotaStatisticInfo(strYear,
					strPartment_ID, values);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用水定额管理", "更新定额统计信息");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理", "更新定额统计信息",
						"更新失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理", "更新定额统计信息",
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

	private void getWaterDeptQuotaStatisticInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		String strYear = request.getParameter("waterDeptFixedSearchYear");
		String strPartment_ID = request.getParameter("waterPartment_ID");

		String resultInfo = "";
		resultInfo = wpfd.getWaterQuotaStatisticInfo(strYear, strPartment_ID);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void deptWaterFixedFinancialSettlement(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
	
		WaterFixedFinancialSettlementDao wffsd = new WaterFixedFinancialSettlementDao();		Integer thePageCount = Integer.parseInt(request
				.getParameter("waterDeptFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("waterDeptFixedPageIndex"));

		String strYear = request.getParameter("waterDeptFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		String order = " order by " + request.getParameter("tableName") + " "
				+ request.getParameter("order");
		ArrayList<WaterFixedFinancialSettlementModel> list = wffsd
				.getDeptFinancialSettlementInfo(strYear, userID, thePageCount,
						thePageIndex, order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", wffsd.getTotalCount());
		json.add(jo);

		for (WaterFixedFinancialSettlementModel wffsm : list)
		{
			jo = new JSONObject();
			jo.put("Partment_ID", wffsm.getPartment_ID());
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

	private void addDeptWaterDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		DeptQuotaByDevWaterDao ddwd = new DeptQuotaByDevWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		DeptQuotaByDevWaterModel ddwm = new DeptQuotaByDevWaterModel();

		ddwm.setPartmentID(Integer.parseInt(request.getParameter("partmentID")));
		ddwm.setYear(request.getParameter("year"));
		ddwm.setName(request.getParameter("name"));
		ddwm.setPrice(Float.parseFloat(request.getParameter("price")));
		ddwm.setM1(Integer.parseInt(request.getParameter("m1")));
		ddwm.setM2(Integer.parseInt(request.getParameter("m2")));
		ddwm.setM3(Integer.parseInt(request.getParameter("m3")));
		ddwm.setM4(Integer.parseInt(request.getParameter("m4")));
		ddwm.setM5(Integer.parseInt(request.getParameter("m5")));
		ddwm.setM6(Integer.parseInt(request.getParameter("m6")));
		ddwm.setM7(Integer.parseInt(request.getParameter("m7")));
		ddwm.setM8(Integer.parseInt(request.getParameter("m8")));
		ddwm.setM9(Integer.parseInt(request.getParameter("m9")));
		ddwm.setM10(Integer.parseInt(request.getParameter("m10")));
		ddwm.setM11(Integer.parseInt(request.getParameter("m11")));
		ddwm.setM12(Integer.parseInt(request.getParameter("m12")));
		ddwm.setTotalDevice(Integer.parseInt(request
				.getParameter("totalDevice")));
		ddwm.setPower(Float.parseFloat(request.getParameter("power")));
		ddwm.setRunHoursPerMth(Float.parseFloat(request
				.getParameter("RunHoursPerMth")));
		ddwm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = ddwd.insert(ddwm);
			if ("success".equals(msg))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用水定额管理", "添加部门设备类别标准分配定额详细（用水）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理",
						"添加部门设备类别标准分配定额详细（用水）", "添加失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理",
						"添加部门设备类别标准分配定额详细（用水）", "添加失败！！！");
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

	private void deleteDeptWaterDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		DeptQuotaByDevWaterDao ddwd = new DeptQuotaByDevWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (ddwd.delete(valueID))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "部门用水定额管理", "删除部门设备类别标准分配定额详细（用水）");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理",
						"删除部门设备类别标准分配定额详细（用水）", "删除失败！！！");
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

	private void updateDeptWaterDevQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		DeptQuotaByDevWaterDao ddwd = new DeptQuotaByDevWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		DeptQuotaByDevWaterModel ddwm = new DeptQuotaByDevWaterModel();

		ddwm.setValueID(Integer.parseInt(request.getParameter("valueID")));
		ddwm.setPartmentID(Integer.parseInt(request.getParameter("partmentID")));
		ddwm.setYear(request.getParameter("year"));
		ddwm.setName(request.getParameter("name"));
		ddwm.setPrice(Float.parseFloat(request.getParameter("price")));
		ddwm.setM1(Integer.parseInt(request.getParameter("m1")));
		ddwm.setM2(Integer.parseInt(request.getParameter("m2")));
		ddwm.setM3(Integer.parseInt(request.getParameter("m3")));
		ddwm.setM4(Integer.parseInt(request.getParameter("m4")));
		ddwm.setM5(Integer.parseInt(request.getParameter("m5")));
		ddwm.setM6(Integer.parseInt(request.getParameter("m6")));
		ddwm.setM7(Integer.parseInt(request.getParameter("m7")));
		ddwm.setM8(Integer.parseInt(request.getParameter("m8")));
		ddwm.setM9(Integer.parseInt(request.getParameter("m9")));
		ddwm.setM10(Integer.parseInt(request.getParameter("m10")));
		ddwm.setM11(Integer.parseInt(request.getParameter("m11")));
		ddwm.setM12(Integer.parseInt(request.getParameter("m12")));
		ddwm.setTotalDevice(Integer.parseInt(request
				.getParameter("totalDevice")));
		ddwm.setPower(Float.parseFloat(request.getParameter("power")));
		ddwm.setRunHoursPerMth(Float.parseFloat(request
				.getParameter("RunHoursPerMth")));
		ddwm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = ddwd.modify(ddwm);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用水定额管理", "编辑部门设备类别标准分配定额详细（用水）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理",
						"编辑部门设备类别标准分配定额详细（用水）", "编辑失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理",
						"编辑部门设备类别标准分配定额详细（用水）", "编辑失败！！！");
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

	private void queryDeptWaterDevOldInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		DeptQuotaByDevWaterDao ddwd = new DeptQuotaByDevWaterDao();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));
		DeptQuotaByDevWaterModel ddwm = ddwd.queryByID(valueID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("partmentID", ddwm.getPartmentID());
		jo.put("year", ddwm.getYear());
		jo.put("name", ddwm.getName());
		jo.put("price", ddwm.getPrice());
		jo.put("m1", ddwm.getM1());
		jo.put("m2", ddwm.getM2());
		jo.put("m3", ddwm.getM3());
		jo.put("m4", ddwm.getM4());
		jo.put("m5", ddwm.getM5());
		jo.put("m6", ddwm.getM6());
		jo.put("m7", ddwm.getM7());
		jo.put("m8", ddwm.getM8());
		jo.put("m9", ddwm.getM9());
		jo.put("m10", ddwm.getM10());
		jo.put("m11", ddwm.getM11());
		jo.put("m12", ddwm.getM12());
		jo.put("totalDevice", ddwm.getTotalDevice());
		jo.put("power", ddwm.getPower());
		jo.put("RunHoursPerMth", ddwm.getRunHoursPerMth());
		jo.put("remark", ddwm.getRemark());

		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void addDeptWaterPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		DeptQuotaByPeoWaterDao dpwd = new DeptQuotaByPeoWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		DeptQuotaByPeoWaterModel dpwm = new DeptQuotaByPeoWaterModel();

		dpwm.setPartmentID(Integer.parseInt(request.getParameter("partmentID")));
		dpwm.setYear(request.getParameter("year"));
		dpwm.setName(request.getParameter("name"));
		dpwm.setPrice(Float.parseFloat(request.getParameter("price")));
		dpwm.setM1(Integer.parseInt(request.getParameter("m1")));
		dpwm.setM2(Integer.parseInt(request.getParameter("m2")));
		dpwm.setM3(Integer.parseInt(request.getParameter("m3")));
		dpwm.setM4(Integer.parseInt(request.getParameter("m4")));
		dpwm.setM5(Integer.parseInt(request.getParameter("m5")));
		dpwm.setM6(Integer.parseInt(request.getParameter("m6")));
		dpwm.setM7(Integer.parseInt(request.getParameter("m7")));
		dpwm.setM8(Integer.parseInt(request.getParameter("m8")));
		dpwm.setM9(Integer.parseInt(request.getParameter("m9")));
		dpwm.setM10(Integer.parseInt(request.getParameter("m10")));
		dpwm.setM11(Integer.parseInt(request.getParameter("m11")));
		dpwm.setM12(Integer.parseInt(request.getParameter("m12")));
		dpwm.setTotalPeople(Integer.parseInt(request
				.getParameter("totalPeople")));
		dpwm.seteStandard(Float.parseFloat(request.getParameter("eStandard")));
		dpwm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = dpwd.insert(dpwm);
			if ("success".equals(msg))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用水定额管理", "添加部门人员类别标准分配定额详细（用水）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理",
						"添加部门人员类别标准分配定额详细（用水）", "添加失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理",
						"添加部门人员类别标准分配定额详细（用水）", "添加失败！！！");
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

	private void deleteDeptWaterPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		DeptQuotaByPeoWaterDao dpwd = new DeptQuotaByPeoWaterDao();
		WaterFixedFinancialSettlementDao wffsd = new WaterFixedFinancialSettlementDao();
		OperationLogInterface log = new OperationLogImplement();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (dpwd.delete(valueID))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "部门用水定额管理", "删除部门人员类别标准分配定额详细（用水）");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理",
						"删除部门人员类别标准分配定额详细（用水）", "删除失败！！！");
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

	private void updateDeptWaterPeoQuotaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		DeptQuotaByPeoWaterDao dpwd = new DeptQuotaByPeoWaterDao();
		OperationLogInterface log = new OperationLogImplement();
		DeptQuotaByPeoWaterModel dpwm = new DeptQuotaByPeoWaterModel();

		dpwm.setValueID(Integer.parseInt(request.getParameter("valueID")));
		dpwm.setPartmentID(Integer.parseInt(request.getParameter("partmentID")));
		dpwm.setYear(request.getParameter("year"));
		dpwm.setName(request.getParameter("name"));
		dpwm.setPrice(Float.parseFloat(request.getParameter("price")));
		dpwm.setM1(Integer.parseInt(request.getParameter("m1")));
		dpwm.setM2(Integer.parseInt(request.getParameter("m2")));
		dpwm.setM3(Integer.parseInt(request.getParameter("m3")));
		dpwm.setM4(Integer.parseInt(request.getParameter("m4")));
		dpwm.setM5(Integer.parseInt(request.getParameter("m5")));
		dpwm.setM6(Integer.parseInt(request.getParameter("m6")));
		dpwm.setM7(Integer.parseInt(request.getParameter("m7")));
		dpwm.setM8(Integer.parseInt(request.getParameter("m8")));
		dpwm.setM9(Integer.parseInt(request.getParameter("m9")));
		dpwm.setM10(Integer.parseInt(request.getParameter("m10")));
		dpwm.setM11(Integer.parseInt(request.getParameter("m11")));
		dpwm.setM12(Integer.parseInt(request.getParameter("m12")));
		dpwm.setTotalPeople(Integer.parseInt(request
				.getParameter("totalPeople")));
		dpwm.seteStandard(Float.parseFloat(request.getParameter("eStandard")));
		dpwm.setRemark(request.getParameter("remark"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String msg = dpwd.modify(dpwm);
			if ("success".equals(msg))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "部门用水定额管理", "编辑部门人员类别标准分配定额详细（用水）");
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理",
						"编辑部门人员类别标准分配定额详细（用水）", "编辑失败！！！");
			} else
			{
				resultInfo = msg;
				// 写入日志
				log.writeLogErrInfo(userLoginName, "部门用水定额管理",
						"编辑部门人员类别标准分配定额详细（用水）", "编辑失败！！！");
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

	private void queryDeptWaterPeoOldInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		DeptQuotaByPeoWaterDao dpwd = new DeptQuotaByPeoWaterDao();
		Integer valueID = Integer.parseInt(request.getParameter("valueID"));
		DeptQuotaByPeoWaterModel dpwm = dpwd.queryByID(valueID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("partmentID", dpwm.getPartmentID());
		jo.put("year", dpwm.getYear());
		jo.put("name", dpwm.getName());
		jo.put("price", dpwm.getPrice());
		jo.put("m1", dpwm.getM1());
		jo.put("m2", dpwm.getM2());
		jo.put("m3", dpwm.getM3());
		jo.put("m4", dpwm.getM4());
		jo.put("m5", dpwm.getM5());
		jo.put("m6", dpwm.getM6());
		jo.put("m7", dpwm.getM7());
		jo.put("m8", dpwm.getM8());
		jo.put("m9", dpwm.getM9());
		jo.put("m10", dpwm.getM10());
		jo.put("m11", dpwm.getM11());
		jo.put("m12", dpwm.getM12());
		jo.put("totalPeople", dpwm.getTotalPeople());
		jo.put("eStandard", dpwm.geteStandard());
		jo.put("remark", dpwm.getRemark());

		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void getDeptWaterStructure(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		String strStyle = request.getParameter("waterDeptStyle");
		String strYear = request.getParameter("waterDeptFixedSearchYear");
		String strPartment_ID = request.getParameter("waterPartment_ID");

		String resultInfo = "";
		resultInfo = wpfd.getEnergyStructureInfo(strYear, strStyle,
				strPartment_ID);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void getWaterDeptQuotaUseInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		String strYear = request.getParameter("waterDeptFixedSearchYear");
		String strPartment_ID = request.getParameter("waterPartment_ID");

		String resultInfo = "";
		resultInfo = wpfd.getQuotaUseInfo(strYear, strPartment_ID);

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void getPeoInfoByWaterPartment_ID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		String strYear = request.getParameter("waterDeptFixedSearchYear");
		String strPartment_ID = request.getParameter("waterPartment_ID");

		ArrayList<WaterQuotaManagerModel> list = wpfd
				.getWaterPeoInfoByPartment_ID(strYear, strPartment_ID);

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

	private void getDevInfoByWaterPartment_ID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		
		String strYear = request.getParameter("waterDeptFixedSearchYear");
		String strPartment_ID = request.getParameter("waterPartment_ID");

		ArrayList<WaterQuotaManagerModel> list = wpfd
				.getWaterDevInfoByPartment_ID(strYear, strPartment_ID);

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

	private void waterDeptFixedPaginate(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		Integer thePageCount = Integer.parseInt(request
				.getParameter("waterDeptFixedPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("waterDeptFixedPageIndex"));

		String strYear = request.getParameter("waterDeptFixedSearchYear");
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
			// //System.out.println(userID);
		}
		ArrayList<WaterQuotaManagerModel> list = null;
		list = wpfd.getAllInfo(strYear, userID, thePageCount, thePageIndex);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", wpfd.getTotalCount());
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
