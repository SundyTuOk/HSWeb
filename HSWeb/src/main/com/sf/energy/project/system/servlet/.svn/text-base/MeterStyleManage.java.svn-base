/**
 * 2014-4-24
 */
package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.project.system.dao.MeterStyleDao;
import com.sf.energy.project.system.model.MeterStyle;
import com.sf.energy.project.system.model.MeterTexing;
import com.sf.energy.util.DataValidation;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

/**
 * @author WangZhao 2014-4-24
 * 
 * 
 */
public class MeterStyleManage extends HttpServlet
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
			HttpServletResponse response) throws IOException, SQLException
	{
		String method = request.getParameter("method");

		if ("getAllMeterStyle".equals(method))
			getAllMeterStyle(request, response);

		if ("deleteMeterStyle".equals(method))
			deleteMeterStyle(request, response);

		if ("addMeterStyle".equals(method))
			addMeterStyle(request, response);

		if ("updateMeterStyle".equals(method))
			updateMeterStyle(request, response);

		if ("deleteSomeMeterStyle".equals(method))
			deleteSomeMeterStyle(request, response);

	}

	private void deleteSomeMeterStyle(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		MeterStyleDao msd = new MeterStyleDao();
		String idList = request.getParameter("IDList");
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		String[] list = idList.split(" ");
		Integer ID = 0;
		String resultInfo = "success";
		for (String id : list)
		{
			
			ID = Integer.parseInt(id);
			String nameString=msd.queryByID(ID).getMeterStyleName();
			log.writeLog(userLoginName, "表计类型", "删除表计类型    "+nameString);
			if (!msd.delete(ID))
				resultInfo = "fail";
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void updateMeterStyle(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AmmeterDao amDao = new AmmeterDao();
		WatermeterDao wmDao = new WatermeterDao();
		MeterStyleDao msd = new MeterStyleDao();
		MeterStyle meterStyle = null;
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		if (request.getParameter("MeterStyle_ID") != null
				&& DataValidation.isNumber(request
						.getParameter("MeterStyle_ID")))
			meterStyle = msd.queryByID(Integer.parseInt(request
					.getParameter("MeterStyle_ID")));
		else
		{
			return;
		}
		meterStyle.setMeterStyleNum(request.getParameter("MeterStyle_Num"));
		meterStyle.setMeterStyleName(request.getParameter("MeterStyle_Name"));
		meterStyle.setMeterStyleType(request.getParameter("MeterStyle_Type"));
		meterStyle.setMeterStyleFactory(request
				.getParameter("MeterStyle_Factory"));
		meterStyle.setMeterStyleRemark(request
				.getParameter("MeterStyle_Remark"));

		if (meterStyle.getDetaiList() == null)
		{
			meterStyle.setDetaiList(new LinkedList<MeterTexing>());
		}

		if ("电表".equals(meterStyle.getMeterStyleType()))
		{
			MeterTexing mt = null;
			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 2);
			mt.setValue(request.getParameter("2"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 4);
			mt.setValue(request.getParameter("4"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 5);
			mt.setValue(request.getParameter("5"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 6);
			mt.setValue(request.getParameter("6"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 7);
			mt.setValue(request.getParameter("7"));
			meterStyle.getDetaiList().add(mt);

			// 电表倍率
			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 8);
			mt.setValue(request.getParameter("8"));
			meterStyle.getDetaiList().add(mt);
			amDao.updateBeiLv(meterStyle.getMeterStyleID(),
					Integer.parseInt(request.getParameter("8")));

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 9);
			mt.setValue(request.getParameter("9"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 10);
			mt.setValue(request.getParameter("10"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 12);
			mt.setValue(request.getParameter("12"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 18);
			mt.setValue(request.getParameter("18"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 19);
			mt.setValue(request.getParameter("19"));
			meterStyle.getDetaiList().add(mt);
			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 32);
			mt.setValue(request.getParameter("32"));
			meterStyle.getDetaiList().add(mt);
			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 33);
			mt.setValue(request.getParameter("33"));
			meterStyle.getDetaiList().add(mt);
			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 34);
			mt.setValue(request.getParameter("34"));
			meterStyle.getDetaiList().add(mt);
		}

		if ("水表".equals(meterStyle.getMeterStyleType()))
		{
			MeterTexing mt = null;

			// 水表倍率
			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 13);
			mt.setValue(request.getParameter("13"));
			meterStyle.getDetaiList().add(mt);
			wmDao.updateBeiLv(meterStyle.getMeterStyleID(),
					Integer.parseInt(request.getParameter("13")));

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 14);
			mt.setValue(request.getParameter("14"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 20);
			mt.setValue(request.getParameter("20"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 22);
			mt.setValue(request.getParameter("22"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 23);
			mt.setValue(request.getParameter("23"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 24);
			mt.setValue(request.getParameter("24"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 26);
			mt.setValue(request.getParameter("26"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 27);
			mt.setValue(request.getParameter("27"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 28);
			mt.setValue(request.getParameter("28"));
			meterStyle.getDetaiList().add(mt);

			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 29);
			mt.setValue(request.getParameter("29"));
			meterStyle.getDetaiList().add(mt);
			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 31);
			mt.setValue(request.getParameter("31"));
			meterStyle.getDetaiList().add(mt);
			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 35);
			mt.setValue(request.getParameter("35"));
			meterStyle.getDetaiList().add(mt);
		}

		if ("气表".equals(meterStyle.getMeterStyleType()))
		{
			MeterTexing mt = null;
			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 15);
			mt.setValue(request.getParameter("15"));
			meterStyle.getDetaiList().add(mt);
		}

		String resultInfo = null;
		if (msd.update(meterStyle)){
			resultInfo = "success";
			// 写入日志
			log.writeLog(userLoginName, "表计类型", "编辑表计类型    "+meterStyle.getMeterStyleName());
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addMeterStyle(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		MeterStyleDao msd = new MeterStyleDao();
		MeterStyle meterStyle = new MeterStyle();

		meterStyle.setMeterStyleNum(request.getParameter("MeterStyle_Num"));
		meterStyle.setMeterStyleName(request.getParameter("MeterStyle_Name"));
		meterStyle.setMeterStyleType(request.getParameter("MeterStyle_Type"));
		meterStyle.setMeterStyleFactory(request
				.getParameter("MeterStyle_Factory"));
		meterStyle.setMeterStyleRemark(request
				.getParameter("MeterStyle_Remark"));

		if (meterStyle.getDetaiList() == null)
		{
			meterStyle.setDetaiList(new LinkedList<MeterTexing>());
		}
		if ("电表".equals(meterStyle.getMeterStyleType()))
		{
			MeterTexing mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 2);
			mt.setMETERTEXING_ID(2);
			mt.setValue(request.getParameter("2"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(4);
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 4);
			mt.setValue(request.getParameter("4"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 5);
			mt.setMETERTEXING_ID(5);
			mt.setValue(request.getParameter("5"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 6);
			mt.setMETERTEXING_ID(6);
			mt.setValue(request.getParameter("6"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(7);
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 7);
			mt.setValue(request.getParameter("7"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
			// 电表倍率
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 8);
			mt.setMETERTEXING_ID(8);
			mt.setValue(request.getParameter("8"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 9);
			mt.setMETERTEXING_ID(9);
			mt.setValue(request.getParameter("9"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 10);
			mt.setMETERTEXING_ID(10);
			mt.setValue(request.getParameter("10"));
			meterStyle.getDetaiList().add(mt);
			
			
			mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 12);
			mt.setMETERTEXING_ID(12);
			mt.setValue(request.getParameter("12"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 18);
			mt.setMETERTEXING_ID(18);
			mt.setValue(request.getParameter("18"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 19);
			mt.setMETERTEXING_ID(19);
			mt.setValue(request.getParameter("19"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 32);
			mt.setMETERTEXING_ID(32);
			mt.setValue(request.getParameter("32"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 32);
			mt.setMETERTEXING_ID(33);
			mt.setValue(request.getParameter("33"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 32);
			mt.setMETERTEXING_ID(34);
			mt.setValue(request.getParameter("34"));
			meterStyle.getDetaiList().add(mt);
		}

		if ("水表".equals(meterStyle.getMeterStyleType()))
		{
			MeterTexing mt = new MeterTexing();

			// 水表倍率
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 13);
			mt.setMETERTEXING_ID(13);
			mt.setValue(request.getParameter("13"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(14);
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 14);
			mt.setValue(request.getParameter("14"));
			meterStyle.getDetaiList().add(mt);

//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 20);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(20);
			mt.setValue(request.getParameter("20"));
			meterStyle.getDetaiList().add(mt);

//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 22);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(22);
			mt.setValue(request.getParameter("22"));
			meterStyle.getDetaiList().add(mt);

//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 23);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(23);
			mt.setValue(request.getParameter("23"));
			meterStyle.getDetaiList().add(mt);

//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 24);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(24);
			mt.setValue(request.getParameter("24"));
			meterStyle.getDetaiList().add(mt);

//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 26);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(26);
			mt.setValue(request.getParameter("26"));
			meterStyle.getDetaiList().add(mt);

//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 27);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(27);
			mt.setValue(request.getParameter("27"));
			meterStyle.getDetaiList().add(mt);

//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 28);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(28);
			mt.setValue(request.getParameter("28"));
			meterStyle.getDetaiList().add(mt);

//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 29);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(29);
			mt.setValue(request.getParameter("29"));
			meterStyle.getDetaiList().add(mt);
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 31);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(31);
			mt.setValue(request.getParameter("31"));
			meterStyle.getDetaiList().add(mt);
			mt = new MeterTexing();
			mt.setMETERTEXING_ID(35);
			mt.setValue(request.getParameter("35"));
			meterStyle.getDetaiList().add(mt);
		}

		if ("气表".equals(meterStyle.getMeterStyleType()))
		{
			MeterTexing mt = new MeterTexing();
			mt.setMETERTEXING_ID(15);
//			mt = msd.queryMeterTexing(meterStyle.getMeterStyleID(), 15);
			mt.setValue(request.getParameter("15"));
			meterStyle.getDetaiList().add(mt);
		}
		 OperationLogInterface log = new OperationLogImplement();
			String userLoginName = (String) request.getSession().getAttribute(
					"userName");
		
		String resultInfo = null;
		if (meterStyle.getMeterStyleName() != null
				&& msd.existByName(meterStyle.getMeterStyleName()))
		{
			resultInfo = "该表计名已存在";
		} else if (msd.add(meterStyle)){
			// 写入日志
			log.writeLog(userLoginName, "表计类型", "添加表计类型    "+meterStyle.getMeterStyleName());
			resultInfo = "success";
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void deleteMeterStyle(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		MeterStyleDao msd = new MeterStyleDao();
		Integer MeterStyleID = Integer.parseInt(request
				.getParameter("MeterStyleID"));
		 OperationLogInterface log = new OperationLogImplement();
			String userLoginName = (String) request.getSession().getAttribute(
					"userName");
		String resultInfo = null;
		String nameString=msd.queryByID(MeterStyleID).getMeterStyleName();
		if (msd.delete(MeterStyleID)){
			resultInfo = "success";
			// 写入日志
			log.writeLog(userLoginName, "表计类型", "删除表计类型    "+nameString);
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getAllMeterStyle(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		MeterStyleDao msd = new MeterStyleDao();
		List<MeterStyle> list = msd.displayAll();

		JSONArray json = new JSONArray();
		for (int i = list.size() - 1; i >= 0; i--)
		{
			MeterStyle n = list.get(i);
			JSONObject jo = buildNode(n);
			json.add(jo);
		}

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private JSONObject buildNode(MeterStyle meter)
	{
		JSONObject node = null;

		if (meter != null)
		{
			node = new JSONObject();
			node.put("MeterStyle_ID", meter.getMeterStyleID());
			node.put("MeterStyle_Num", meter.getMeterStyleNum());
			node.put("MeterStyle_Name", meter.getMeterStyleName());
			node.put("MeterStyle_Type", meter.getMeterStyleType());
			node.put("MeterStyle_Factory", meter.getMeterStyleFactory());
			node.put("MeterStyle_Remark", meter.getMeterStyleRemark());

			if (meter.getDetaiList() != null && meter.getDetaiList().size() > 0)
			{
				JSONArray list = new JSONArray();
				for (MeterTexing mt : meter.getDetaiList())
				{
					JSONObject jo = new JSONObject();
					jo.put("METERTEXING_ID", mt.getMETERTEXING_ID());
					jo.put("METERTEXING_NAME", mt.getMETERTEXING_NAME());
					jo.put("METESTYLE_TYPE", mt.getMETESTYLE_TYPE());
					jo.put("METERTEXING_REMARK", mt.getMETERTEXING_REMARK());
					jo.put("METERTEXING_VALUESTYLE",
							mt.getMETERTEXING_VALUESTYLE());
					jo.put("METERTEXING_SHOWTYPE", mt.getMETERTEXING_SHOWTYPE());
					jo.put("TEXINGVALUE", mt.getValue());
					list.add(jo);
				}
				node.put("MeterStyle_TexingList", list);
			}
		}

		return node;
	}
}
