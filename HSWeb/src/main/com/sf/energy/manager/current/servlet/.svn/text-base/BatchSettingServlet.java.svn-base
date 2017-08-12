package com.sf.energy.manager.current.servlet;

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

import com.sf.energy.classroomlight.model.LightRoomModel;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.system.dao.AmmPriceDao;
import com.sf.energy.sms.dao.SMSReceiveDao;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class BatchSettingServlet extends HttpServlet
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
		AmmeterDao ad = new AmmeterDao();

		int areaID = Integer.parseInt(request.getParameter("areaID"));
		int partmentID = Integer.parseInt(request.getParameter("partmentID"));
		int archID = Integer.parseInt(request.getParameter("archID"));
		int floor = Integer.parseInt(request.getParameter("floor"));
		int metestyleID = Integer.parseInt(request.getParameter("metestyleID"));
		int priceID = Integer.parseInt(request.getParameter("priceID"));
		int isOffine = Integer.parseInt(request.getParameter("isOffine"));
		int isTotal = Integer.parseInt(request.getParameter("isTotal"));
		int useAmmStyleID = Integer.parseInt(request.getParameter("useAmmStyleID"));
		int isComputation = Integer.parseInt(request.getParameter("isComputation"));
		String ammFX = request.getParameter("ammFX");
		String ammYJZX = request.getParameter("ammYJZX");
		int parentID = Integer.parseInt(request.getParameter("parentID"));
		int UpdateAllBatchSetting = Integer.parseInt(request.getParameter("UpdateAllBatchSetting"));
		if (request.getParameter("theIDList") != null)
		{
			idList = request.getParameter("theIDList");
		}
		String resultInfo = null;
		if (UpdateAllBatchSetting == 0)
		{
			String[] list = idList.split(" ");
			
			for (String id : list)
			{
				try
				{
					// 先查出不需要修改的
					if (!"-1".equals(ammFX))// 说明修改了分项
					{
						ad.editFX(Integer.parseInt(id), ammFX);
					}

					if (!ammYJZX.equals("-1"))// 直接修改
					{
						ad.editFXNum(Integer.parseInt(id), ammYJZX);
					}

					AmmeterModel am = ad.queryByID(Integer.parseInt(id));
					if (areaID != -1)
					{
						am.setAreaID(areaID);
					}
					if (partmentID != -1)
					{
						am.setPartment(partmentID);
					}
					if (archID != -1)
					{
						am.setArchitectureID(archID);
					}
					if (floor != -1)
					{
						am.setFloor(floor);
					}
					if (metestyleID != -1)
					{
						am.setMeteStyleID(metestyleID);
					}
					if (priceID != -1)
					{
						am.setPriceID(priceID);
					}

					if (isOffine != -1)
					{
						am.setIsOffAlarm(isOffine);
					}

					if (isTotal != -1)
					{
						am.setIsCountMeter(isTotal);
					}

					if (isComputation != -1)
					{
						am.setIsComputation(isComputation);
					}

					if (useAmmStyleID != -1)
					{
						am.setUseAmStyle(useAmmStyleID);
					}
					if (parentID != -1)
					{
						am.setParentID(parentID);
					}
					if (ad.modify(am) == true)
					{
						OperationLogInterface log = new OperationLogImplement();
						HttpSession session = request.getSession();
						String userLoginName = (String) session.getAttribute("userName");
						try
						{
							log.writeLog(userLoginName, "电能表批量设置", "批量编辑电表属性！"+am.getAmmeterName());
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

			ArrayList<AmmeterModel> list = new ArrayList<AmmeterModel>();
			String sortName = "Area_Name";
			boolean order = true;

			if (request.getParameter("sortName") != null)
			{
				sortName = request.getParameter("sortName");
			}
			if (request.getParameter("order") != null)
			{
				if (request.getParameter("order").equals("desc"))
				{
					order = false;
				} else if (request.getParameter("order").equals("asc"))
				{
					order = true;
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
			for (AmmeterModel am  : list)
			{
				try
				{
					// 先查出不需要修改的
					if (!"-1".equals(ammFX))// 说明修改了分项
					{
						ad.editFX(am.getAmmeterID(), ammFX);
					}

					if (!ammYJZX.equals("-1"))// 直接修改
					{
						ad.editFXNum(am.getAmmeterID(), ammYJZX);
					}

					if (areaID != -1)
					{
						am.setAreaID(areaID);
					}
					if (partmentID != -1)
					{
						am.setPartment(partmentID);
					}
					if (archID != -1)
					{
						am.setArchitectureID(archID);
					}
					if (floor != -1)
					{
						am.setFloor(floor);
					}
					if (metestyleID != -1)
					{
						am.setMeteStyleID(metestyleID);
					}
					if (priceID != -1)
					{
						am.setPriceID(priceID);
					}

					if (isOffine != -1)
					{
						am.setIsOffAlarm(isOffine);
					}

					if (isTotal != -1)
					{
						am.setIsCountMeter(isTotal);
					}

					if (isComputation != -1)
					{
						am.setIsComputation(isComputation);
					}

					if (useAmmStyleID != -1)
					{
						am.setUseAmStyle(useAmmStyleID);
					}
					if (parentID != -1)
					{
						am.setParentID(parentID);
					}
					if (ad.modify(am) == true)
					{
						OperationLogInterface log = new OperationLogImplement();
						HttpSession session = request.getSession();
						String userLoginName = (String) session.getAttribute("userName");
						try
						{
							log.writeLog(userLoginName, "电能表批量设置", "批量编辑电表属性！"+am.getAmmeterName());
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
		boolean order = true;

		if (request.getParameter("sortName") != null)
		{
			sortName = request.getParameter("sortName");
		}
		if (request.getParameter("order") != null)
		{
			if (request.getParameter("order").equals("desc"))
			{
				order = false;
			} else if (request.getParameter("order").equals("asc"))
			{
				order = true;
			}

		}
		Integer thePageCount = Integer.parseInt(request.getParameter("BatchSettingPageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("BatchSettingPageIndex"));
		ArrayList<AmmeterModel> list = new ArrayList<AmmeterModel>();

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

		AmmeterDao ad = new AmmeterDao();
		AmmPriceDao apd = new AmmPriceDao();
		AmmeterModel am = new AmmeterModel();
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
			jo.put("ammNum", am.getAmmeterAddress485());
			jo.put("partmentName", am.getPartmentName());
			jo.put("isComputation", am.getIsComputation());// 纳入计量
			jo.put("useAmStyle", am.getUseAmStyleName());// 性质
			jo.put("styleName", am.getUseStyleName());// 分项
			jo.put("beilv", am.getBeilv());
			jo.put("ammPrice", apd.queryByID(am.getPriceID()).getPriceValue());
			jo.put("costCheck", am.getCostCheck());
			jo.put("standByCheck", am.getStandByCheck());
			jo.put("ammAddress", am.getConsumerAddress());
			jo.put("archName", am.getArchName());
			jo.put("ammArchID", am.getArchitectureID());
			jo.put("ammFloor", am.getFloor());
			jo.put("ammName", am.getMeterName());
			jo.put("ammArea", am.getAreaName());
			jo.put("ammAreaID", am.getAreaID());
			jo.put("ammStyle", am.getMeterStyleName());
			jo.put("ammState", am.getAmmeterStat());// 状态
			jo.put("Parent_Name", am.getParent_Name());
			// 为了编辑输出的信息
			jo.put("ammID", am.getAmmeterID());
			jo.put("meteStyle", am.getMeterStyleName());// 表计类型
			jo.put("isOfflineWaring", am.getIsOffAlarm());// 离线报警
			jo.put("isTotalAmm", am.getIsCountMeter());// 是否总表
			jo.put("styleNameYJZX", am.getUseStyleNameYJZX());// 一级子项
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
