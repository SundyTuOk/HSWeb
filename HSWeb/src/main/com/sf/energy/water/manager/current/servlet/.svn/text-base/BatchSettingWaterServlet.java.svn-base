package com.sf.energy.water.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.equipment.model.WatermeterModel;
import com.sf.energy.project.system.dao.AmmPriceDao;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class BatchSettingWaterServlet extends HttpServlet
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

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		if ("getAllAmmToBatchSetting".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				getAllAmmToBatchSetting(request, response);
			} catch (SQLException e)
			{

				e.printStackTrace();
			}
		}
		if ("batchSettingSomeAmm".equalsIgnoreCase(request.getParameter("method")))
		{
			batchSettingSomeAmm(request, response);
		}
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

		this.doGet(request, response);
	}

	public void batchSettingSomeAmm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		boolean b = false;
		String idList = "";
		WatermeterDao ad = new WatermeterDao();

		int areaID = Integer.parseInt(request.getParameter("areaID"));
//		int partmentID = Integer.parseInt(request.getParameter("partmentID"));
		int archID = Integer.parseInt(request.getParameter("archID"));
		int floor = Integer.parseInt(request.getParameter("floor"));
		int metestyleID = Integer.parseInt(request.getParameter("metestyleID"));
		int priceID = Integer.parseInt(request.getParameter("priceID"));
		int isOffine = Integer.parseInt(request.getParameter("isOffine"));
		int isTotal = Integer.parseInt(request.getParameter("isTotal"));
		int useAmmStyleID = Integer.parseInt(request.getParameter("useAmmStyleID"));
		int isComputation = Integer.parseInt(request.getParameter("isComputation"));
		String ammFX = request.getParameter("ammFX");
		// String ammYJZX = request.getParameter("ammYJZX");
		int isLeakingCheck = Integer.parseInt(request.getParameter("isLeakingCheck"));
		float leakingValue = Float.parseFloat(request.getParameter("leakingValue"));

		int parentID = Integer.parseInt(request.getParameter("parentID"));
		int UpdateAllBatchSetting = Integer.parseInt(request.getParameter("UpdateAllBatchSetting"));

		if (request.getParameter("theIDList") != null)
		{
			idList = request.getParameter("theIDList");
		}
		String resultInfo = null;

		String[] walist = idList.split(" ");
		if (UpdateAllBatchSetting == 0)
		{
			for (String id : walist)
			{
				try
				{
					
					// 先查出不需要修改的
					if (!"-1".equals(ammFX))// 说明修改了分项
					{
						ad.editFX(Integer.parseInt(id), ammFX);
					}
					// if(!ammYJZX.equals("-1"))//直接修改
					// {
					// ad.editFXNum(Integer.parseInt(id), ammYJZX);
					// }

					WatermeterModel am = ad.getWatermeterInfoByID(Integer.parseInt(id));
					if (areaID != -1)
					{
						am.setAREA_ID(areaID);
					}
					/*if (partmentID != -1)
					{
						am.setPARTMENT(partmentID);
					}*/
					if (archID != -1)
					{
						am.setARCHITECTURE_ID(archID);
					}
					if (floor != -1)
					{
						am.setFLOOR(floor);
					}
					if (metestyleID != -1)
					{
						am.setMETESTYLE_ID(metestyleID);
					}
					if (priceID != -1)
					{
						am.setPRICE_ID(priceID);
					}

					if (isOffine != -1)
					{
						am.setISOFFALARM(isOffine);
					}

					if (isTotal != -1)
					{
						am.setISCOUNTMETER(isTotal);
					}

					if (isComputation != -1)
					{
						am.setISCOMPUTATION(isComputation);
					}

					if (isLeakingCheck != -1)
					{
						am.setWLEAKAGECHECK(isLeakingCheck);
					}

					am.setWLEAKAGEVALUE(leakingValue);

					if (useAmmStyleID != -1)
					{
						am.setUSEAMSTYLE(useAmmStyleID);
					}
					
					if (parentID != -1)
					{
						am.setPARENT_ID(parentID);
					}
					if (ad.updateWatermeter(am) == true)
					{
						OperationLogInterface log = new OperationLogImplement();
						HttpSession session = request.getSession();
						String userLoginName = (String) session.getAttribute("userName");
						try
						{
							log.writeLog(userLoginName, "水表批量设置", "批量编辑水表属性！"+am.getWATERMETER_NAME());
						} catch (SQLException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						resultInfo = "success";
					} else
					{
						resultInfo = "faild";
					}

				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}else if(UpdateAllBatchSetting == 1){
			int schoolID = -1;
			int queryAreaID = -1;
			int queryArchID = -1;
			int queryFloor = -1;
			int queryAmmID = -1;

			ArrayList<WatermeterModel> list = new ArrayList<WatermeterModel>();
			String sortName = "Area_Name";
			String order = "desc";

			if (request.getParameter("sortName") != null)
			{
				sortName = request.getParameter("sortName");
			}
			if (request.getParameter("order") != null)
			{
				if (request.getParameter("order").equals("desc"))
				{
					order = "desc";
				} else if (request.getParameter("order").equals("asc"))
				{
					order = "asc";
				}

			}
			if (Integer.parseInt(request.getParameter("queryAreaID")) != -1)
			{
				queryAreaID = Integer.parseInt(request.getParameter("queryAreaID"));
			}
			if (Integer.parseInt(request.getParameter("queryArchID")) != -1)
			{
				queryArchID = Integer.parseInt(request.getParameter("queryArchID"));
			}

			if (Integer.parseInt(request.getParameter("queryFloor")) != -1)
			{
				queryFloor = Integer.parseInt(request.getParameter("queryFloor"));
			}
			if (Integer.parseInt(request.getParameter("queryAmmID")) != -1)
			{
				queryAmmID = Integer.parseInt(request.getParameter("queryAmmID"));
			}
			// //System.out.println("选择的信息：areaID:"+areaID+" archID:"+archID+" floor:"+floor+" ammID:"+ammID);

			// 如果是全校业务
			if (queryAreaID == -1 && queryArchID == -1 && queryFloor == -1 && queryAmmID == -1)
			{
				schoolID = 0;// 将school置为非-1的任何数，则代表是全校的业务
			}

			if (schoolID != -1)// 查整个学校
			{
				try
				{
					list =  ad.paginate(sortName, order, Integer.MAX_VALUE, 0);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			else
			// 其他情况，区域，建筑，楼层
			{
				try
				{
					// 综合查询
					list = ad.searchAll(sortName, order, queryAreaID, queryArchID, queryFloor, queryAmmID, Integer.MAX_VALUE, 0);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			for (WatermeterModel am  : list)
			{
				try
				{
					// 先查出不需要修改的
					if (!"-1".equals(ammFX))// 说明修改了分项
					{
						ad.editFX(am.getWATERMETER_ID(), ammFX);
					}


					if (areaID != -1)
					{
						am.setAREA_ID(areaID);
					}
					/*if (partmentID != -1)
					{
						am.setPartment(partmentID);
					}*/
					if (archID != -1)
					{
						am.setARCHITECTURE_ID(archID);
					}
					if (floor != -1)
					{
						am.setFLOOR(floor);
					}
					if (metestyleID != -1)
					{
						am.setMETESTYLE_ID(metestyleID);
					}
					if (priceID != -1)
					{
						am.setPRICE_ID(priceID);
					}

					if (isOffine != -1)
					{
						am.setISOFFALARM(isOffine);
					}

					if (isTotal != -1)
					{
						am.setISCOUNTMETER(isTotal);
					}

					if (isComputation != -1)
					{
						am.setISCOMPUTATION(isComputation);
					}

					if (useAmmStyleID != -1)
					{
						am.setUSEAMSTYLE(useAmmStyleID);
					}
					if (parentID != -1)
					{
						am.setPARENT_ID(parentID);
					}
					if (ad.updateWatermeter(am) == true)
					{
						resultInfo = "success";
						OperationLogInterface log = new OperationLogImplement();
						HttpSession session = request.getSession();
						String userLoginName = (String) session.getAttribute("userName");
						try
						{
							log.writeLog(userLoginName, "水表批量设置", "批量编辑水表属性！"+am.getWATERMETER_NAME());
						} catch (SQLException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else
					{
						resultInfo = "faild";
					}

				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			
		}
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	/**
	 * 综合查询所选择导航（学校，区域，建筑，楼层）的电表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void getAllAmmToBatchSetting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		int schoolID = -1;
		int areaID = -1;
		int archID = -1;
		int floor = -1;
		int ammID = -1;
		String sortName = "Area_Name";
		String order = "desc";

		if (request.getParameter("sortName") != null)
		{
			sortName = request.getParameter("sortName");
		}
		if (request.getParameter("order") != null)
		{
			if (request.getParameter("order").equals("desc"))
			{
				order = "desc";
			} else if (request.getParameter("order").equals("asc"))
			{
				order = "asc";
			}

		}
		int thePageCount = Integer.parseInt(request.getParameter("BatchSettingPageCount"));
		int thePageIndex = Integer.parseInt(request.getParameter("BatchSettingPageIndex"));
		ArrayList<WatermeterModel> list = new ArrayList<WatermeterModel>();

		if (Integer.parseInt(request.getParameter("schoolID")) != -1)
		{
			schoolID = Integer.parseInt(request.getParameter("schoolID"));
		}
		if (Integer.parseInt(request.getParameter("areaID")) != -1)
		{
			areaID = Integer.parseInt(request.getParameter("areaID"));
		}
		if (Integer.parseInt(request.getParameter("archID")) != -1)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}

		if (Integer.parseInt(request.getParameter("floor")) != -1)
		{
			floor = Integer.parseInt(request.getParameter("floor"));
		}
		if (Integer.parseInt(request.getParameter("ammID")) != -1)
		{
			ammID = Integer.parseInt(request.getParameter("ammID"));
		}
		// //System.out.println("选择的信息：areaID:"+areaID+" archID:"+archID+" floor:"+floor+" ammID:"+ammID);

		// 如果是全校业务
		if (areaID == -1 && archID == -1 && floor == -1 && ammID == -1)
		{
			schoolID = 0;// 将school置为非-1的任何数，则代表是全校的业务
		}

		WatermeterDao ad = new WatermeterDao();
		AmmPriceDao apd = new AmmPriceDao();
		WatermeterModel am = new WatermeterModel();
		if (schoolID != -1)// 查整个学校
		{
			try
			{
				list = ad.paginate(sortName, order, thePageCount, thePageIndex);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		else
		// 其他情况，区域，建筑，楼层
		{
			try
			{
				// 综合查询
				list = ad.searchAll(sortName, order, areaID, archID, floor, ammID, thePageCount, thePageIndex);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("totalCount", ad.getTotal());
		json.add(jo);
		for (int i = 0; i < list.size(); i++)
		{
			jo = new JSONObject();
			am = list.get(i);
			jo.put("ammNum", am.getWATERMETER_485ADDRESS());
			jo.put("partmentName", am.getPartmentName());
			jo.put("isComputation", am.getISCOMPUTATION());// 纳入计量
			jo.put("useAmStyle", am.getUseStyleName());// 性质
			jo.put("styleName", am.getUseAmStyleName());// 分项
			jo.put("beilv", am.getBEILV());
			jo.put("ammPrice", apd.queryByID(am.getPRICE_ID()).getPriceValue());
			jo.put("costCheck", am.getCOSTCHECK());
			jo.put("standByCheck", am.getSTANDBYCHECK());
			jo.put("ammAddress", am.getWATERMETER_485ADDRESS());
			jo.put("archName", am.getArchName());
			jo.put("ammArchID", am.getARCHITECTURE_ID());
			jo.put("ammFloor", am.getFLOOR());
			jo.put("ammName", am.getMeterName());
			jo.put("ammArea", am.getAreaName());
			jo.put("ammAreaID", am.getAREA_ID());
			jo.put("ammStyle", am.getMeterStyleName());
			jo.put("ammState", am.getWATERMETER_STAT());// 状态
			jo.put("parent_name", am.getPARENT_NAME());
			// 为了编辑输出的信息
			jo.put("ammID", am.getWATERMETER_ID());
			jo.put("meteStyle", am.getMeterStyleName());// 表计类型
			jo.put("isOfflineWaring", am.getISOFFALARM());// 离线报警
			jo.put("isTotalAmm", am.getISCOUNTMETER());// 是否总表
			// jo.put("styleNameYJZX", am.getUseStyleNameYJZX());// 一级子项
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		// //System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

}
