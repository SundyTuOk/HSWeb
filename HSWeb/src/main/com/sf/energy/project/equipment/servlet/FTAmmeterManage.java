package com.sf.energy.project.equipment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

import net.sf.json.JSONObject;

import com.sf.energy.manager.current.dao.FTAmCurrentManageDao;
import com.sf.energy.project.equipment.dao.FTAmmeterDao;
import com.sf.energy.project.equipment.model.FTAmmeterModel;

public class FTAmmeterManage extends HttpServlet
{
	DateFormat df  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if("displayAllFTAmmeterInfo".equals(method)){
			displayAllFTAmmeterInfo(request,response);
		}
		if("updateFTAmmeterInfo".equals(method)){
			updateFTAmmeterInfo(request,response);
		}
		if("deleteFTAmmeterInfo".equals(method)){
			deleteFTAmmeterInfo(request,response);
		}
		if("resortFTAmmeterData".equals(method)){
			resortFTAmmeterData(request,response);
		}
		if("resortFTAmmeterDataAtBatch".equals(method)){
			resortFTAmmeterDataAtBatch(request,response);
		}
	}

	private void resortFTAmmeterDataAtBatch(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resortType =null;
		if(request.getParameter("resortType")!=null&&!request.getParameter("resortType").equals("")){
			resortType = request.getParameter("resortType");
		}
		if(resortType==null){
			PrintWriter out = response.getWriter();
			out.println("操作类型有误!");
			out.flush();
			out.close();
			return;
		}
		String type=null;
		if(request.getParameter("type")!=null&&!request.getParameter("type").equals("")){
			type = request.getParameter("type");
		}
		if(type==null){
			PrintWriter out = response.getWriter();
			out.println("操作类型有误!");
			out.flush();
			out.close();
			return;
		}
		String startDateStr = null;
		Date sDate = null;
		if(request.getParameter("startDate")!=null&&!request.getParameter("startDate").equals("")){
			startDateStr = request.getParameter("startDate");
		}
		String endDateStr = null;
		Date eDate = null;
		if(request.getParameter("endDate")!=null&&!request.getParameter("endDate").equals("")){
			endDateStr = request.getParameter("endDate");
		}
		if(startDateStr!=null){
			sDate = df.parse(startDateStr);
		}
		if(endDateStr!=null){
			eDate = df.parse(endDateStr);
		}
		int time =10;
		if(request.getParameter("time")!=null&&!request.getParameter("time").equals("")){
			time = Integer.parseInt(request.getParameter("time"));
		}
		FTAmCurrentManageDao fDao = new FTAmCurrentManageDao();
		int count = 0;
		String result = "";
		if(type.equals("select")){//处理选中
			String idstr = null;
			if(request.getParameter("ids")!=null&&!request.getParameter("ids").equals("")){
				idstr = request.getParameter("ids");
			}
			if(idstr==null){
				PrintWriter out = response.getWriter();
				out.println("电表Id有误!");
				out.flush();
				out.close();
				return;
			}
			String[] ids = idstr.split(",");
			if(resortType.equals("deleteAndResort")){//将时间段内原有数据删掉然后重新整理
				for(int i=0;i<ids.length;i++){
					int ammeterId = Integer.parseInt(ids[i]);
					if(fDao.deleteFTAmmeterData(ammeterId, sDate, eDate)){
						count = fDao.replenishFTAmmeterData(ammeterId, sDate, eDate, time*60);
					}
					result = "插入分摊数据 "+count+" 条!";
					if(count==-1){
						result = "该电表所关联电表当前时间段内无数据!";
					}
				}
			}else if(resortType.equals("Resort")){//在电表数据表后插入数据

				result = fDao.autoreplenishFTAmData(ids, new Date(), time*60);
			} 
		}else if(type.equals("all")){//处理所有
			List<FTAmmeterModel> list = new FTAmmeterDao().queryAllFTAmmeterIDs();
			if(list==null){
				result = "无分摊电表信息!";
			}
			int successCount = 0;
			int length = list.size();
			if(resortType.equals("deleteAndResort")){//将时间段内原有数据删掉然后重新整理
				for(FTAmmeterModel model:list){
					if(fDao.deleteFTAmmeterData(model.getAmmeterId(), sDate, eDate)){
						count = fDao.replenishFTAmmeterData(model.getAmmeterId(), sDate, eDate, time*60);
					}
					if(count!=-1){
						successCount++;
					}
				}
				result = "监测到分摊电表 "+length+" 块,插入数据电表 "+successCount+" 块!";
			}else if(resortType.equals("Resort")){//在电表数据表后插入数据
				result = fDao.autoreplenishFTAmData(new Date(), time*60);
			} 
		}
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}

	private void resortFTAmmeterData(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int ammeterId = 0;
		if(request.getParameter("Ammeter_ID")!=null&&!request.getParameter("Ammeter_ID").equals("")){
			ammeterId = Integer.parseInt(request.getParameter("Ammeter_ID"));
		}
		if(ammeterId==0){
			PrintWriter out = response.getWriter();
			out.println("电表Id有误!");
			out.flush();
			out.close();
			return;
		}
		String resortType =null;
		if(request.getParameter("resortType")!=null&&!request.getParameter("resortType").equals("")){
			resortType = request.getParameter("resortType");
		}
		if(resortType==null){
			PrintWriter out = response.getWriter();
			out.println("操作类型有误!");
			out.flush();
			out.close();
			return;
		}
		String ammeterName = "";
		if(request.getParameter("Ammeter_Name")!=null&&!request.getParameter("Ammeter_Name").equals("")){
			ammeterName = request.getParameter("Ammeter_Name");
		}
		String startDateStr = null;
		Date sDate = null;
		if(request.getParameter("startDate")!=null&&!request.getParameter("startDate").equals("")){
			startDateStr = request.getParameter("startDate");
		}
		String endDateStr = null;
		Date eDate = null;
		if(request.getParameter("endDate")!=null&&!request.getParameter("endDate").equals("")){
			endDateStr = request.getParameter("endDate");
		}
		if(startDateStr!=null){
			sDate = df.parse(startDateStr);
		}
		if(endDateStr!=null){
			eDate = df.parse(endDateStr);
		}
		int time =10;
		if(request.getParameter("time")!=null&&!request.getParameter("time").equals("")){
			time = Integer.parseInt(request.getParameter("time"));
		}
		int count = 0;
		String result = "";
		FTAmCurrentManageDao fDao = new FTAmCurrentManageDao();
		if(resortType.equals("deleteAndResort")){//将时间段内原有数据删掉然后重新整理
			if(fDao.deleteFTAmmeterData(ammeterId, sDate, eDate)){
				count = fDao.replenishFTAmmeterData(ammeterId, sDate, eDate, time*60);
			}
			result = ammeterName+" 重新插入分摊数据 "+count+" 条!";
			if(count==-1){
				result = ammeterName+" 所关联电表当前时间段内无数据!";
			}else if(count==-2){
				result = ammeterName+" 无能耗分摊公式信息!";
			}
		}else if(resortType.equals("Resort")){//在电表数据表后插入数据
			result = fDao.autoReplenishFTAmData(ammeterId, new Date(), time*60);
		} 
		//System.out.println(result);
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}

	private void displayAllFTAmmeterInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int offset = 0;
		if(request.getParameter("offset") != null)
		{
			offset = Integer.parseInt(request.getParameter("offset"));
		}

		int limit = 0;
		if(request.getParameter("limit") != null)
		{
			limit = Integer.parseInt(request.getParameter("limit"));
		}

		int areaId = -3;
		if(request.getParameter("areaId") != null)
		{
			areaId = Integer.parseInt(request.getParameter("areaId"));
		}

		int archId = -3;
		if(request.getParameter("archId") != null)
		{
			archId = Integer.parseInt(request.getParameter("archId"));
		}

		int gatherId = -3;
		if(request.getParameter("gatherId") != null)
		{
			gatherId = Integer.parseInt(request.getParameter("gatherId"));
		}

		String ammeterName = null;
		if(request.getParameter("ammeterName") != null)
		{
			ammeterName = request.getParameter("ammeterName");
		}

		FTAmmeterDao dao = new FTAmmeterDao();
		List<FTAmmeterModel> list = null;
		list = dao.queryAllFTAmmeter(offset,limit,areaId,archId,gatherId,ammeterName);
		//		for(FTAmmeterModel n:list){
		//			System.out.println(n.getAmmeterName()+":"+dao.formateFormula(n.getParameters(), n.getFormula()));
		//		}
		JSONObject object = new JSONObject();
		if(list==null){
			object.put("rows",  new JSONArray());
		}else{
			object.put("rows",  list);
		}
		object.put("total", dao.getCount(areaId,archId,gatherId,ammeterName));	
		PrintWriter out = response.getWriter();
		out.println(object.toString());
		out.flush();
		out.close();
	}

	private void deleteFTAmmeterInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int ammeterId = 0;
		if(request.getParameter("Ammeter_ID")!=null&&!request.getParameter("Ammeter_ID").equals("")){
			ammeterId = Integer.parseInt(request.getParameter("Ammeter_ID"));
		}else{
			return;
		}
		FTAmmeterDao dao = new FTAmmeterDao();
		String resultInfo = "删除公式信息失败";
		if(dao.deleteFTAmmeter(ammeterId)){
			resultInfo = "删除公式信息成功";
		}
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void updateFTAmmeterInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		FTAmmeterModel model = new FTAmmeterModel();
		int ammeterId = 0;
		if(request.getParameter("Ammeter_ID")!=null&&!request.getParameter("Ammeter_ID").equals("")){
			ammeterId = Integer.parseInt(request.getParameter("Ammeter_ID"));
			model.setAmmeterId(ammeterId);
		}else{
			return;
		}
		model.setParameters(request.getParameter("parameters"));
		model.setFormula(request.getParameter("formula"));
		FTAmmeterDao dao = new FTAmmeterDao();	
		String resultInfo = "更新公式信息失败";
		if(dao.deleteFTAmmeter(ammeterId)){
			if(dao.insertFTAmmeter(model)){
				resultInfo = "更新公式信息成功";
			}
		}
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			findMethod(request,response);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
