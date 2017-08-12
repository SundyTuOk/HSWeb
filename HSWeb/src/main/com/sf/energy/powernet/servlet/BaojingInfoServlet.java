package com.sf.energy.powernet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.powernet.dao.BaoJingInfoDao;
import com.sf.energy.powernet.model.BaoJingInfoModel;
import com.sf.energy.prepayment.dao.SaleDetailsDao;
import com.sf.energy.prepayment.model.VolumeSetModel;
import com.sf.energy.project.PDRoom.dao.PD_PartDao;
import com.sf.energy.project.PDRoom.model.PD_PartModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class BaojingInfoServlet extends HttpServlet
{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(req, resp);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		doGet(req, resp);
	}
	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String method = req.getParameter("method");
//		//System.out.println("method:" + method);
		
		if("selectPD_Part".equals(method))
			selectPD_Part(req,resp);
		
		if("getDataForBaojingInfo".equals(method))
			getDataForBaojingInfo(req,resp);
		
		if("deleteBaojingInfo".equals(method))
			deleteBaojingInfo(req,resp);
		
	}
	
	private void deleteBaojingInfo(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException
	{
		String IDs=req.getParameter("delete_EMR_info_ID");
		String[] id=IDs.split("\\|");
		
		
		BaoJingInfoDao dao=new BaoJingInfoDao();
		// 统计删除插入条数
		int result = 0;

		for (int i = 0; i < id.length; i++)
		{
			if(dao.deleteSLPlanByID(id[i]))
			{
				result++;
			}
		}
		OperationLogInterface log = new OperationLogImplement();
		HttpSession session = req.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		log.writeLog(userLoginName, "配电自动化中事故告警", "删除事故告警信息");
		PrintWriter out = resp.getWriter();
		out.println("成功删除"+result+"条数据。");
		out.flush();
		out.close();
		

	}



	/**
	 *获得信息 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getDataForBaojingInfo(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		Integer thePageCount = Integer.parseInt(req
				.getParameter("cPageCount"));
		Integer thePageIndex = Integer.parseInt(req
				.getParameter("cPageIndex"));
		
		String partID = req.getParameter("parentID").trim();
		String startTime = req.getParameter("startTime").trim();
		String endTime = req.getParameter("endTime").trim();
		
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");
		String style = req.getParameter("style");
		
//		//System.out.println(thePageCount+" "+thePageIndex+" "+partID+" "+startTime+" "+endTime+" "+tableName+" "+order+" "+style);
		
		BaoJingInfoDao dao=new BaoJingInfoDao();
		ArrayList<BaoJingInfoModel> list=dao.queryInfo(thePageCount,thePageIndex,partID,style,startTime,endTime,tableName,order);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);
		
		for(BaoJingInfoModel vsm : list)
		{
			jo = new JSONObject();
			jo.put("BAOJINGINFO_ID", vsm.getBAOJINGINFO_ID());
			jo.put("BAOJINGINFO_TIME", vsm.getBAOJINGINFO_TIME());
			jo.put("BAOJINGINFO_STYLE", vsm.getBAOJINGINFO_STYLE());
			jo.put("BAOJINGINFO_TITLE", vsm.getBAOJINGINFO_TITLE());
			jo.put("BAOJINGINFO_NAME", vsm.getBAOJINGINFO_NAME());
			json.add(jo);
		}
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
//		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
		
		
	}

	

	/**
	 * 树形列表信息获取
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void selectPD_Part(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		
		int treeStyle=Integer.parseInt(req.getParameter("flag"));
		
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		JSONArray array = new JSONArray();  
		JSONObject member = null;  
		int id=0;
		int parent_id=0;
		String name="";
	
		String PartStyle_ID="";
		List<PD_PartModel> list=new ArrayList<PD_PartModel>();
		PD_PartModel partModel=new PD_PartModel();
		 PD_PartDao partDao=new PD_PartDao();
		 
		 list=partDao.makeTree(treeStyle);
		 
		 Iterator<PD_PartModel> partIt=list.iterator(); 
		 while(partIt.hasNext())
		  {
			 member=new JSONObject();
		   partModel=partIt.next();
		   id=partModel.getPart_ID();
		   parent_id=partModel.getPart_Parent();
		   name=partModel.getPart_Name();
		  
		   PartStyle_ID=partModel.getPartStyle_ID();
		   
		 //  //System.out.println(id+"  "+parent_id+"  "+name);
		   member.put("id",id);
		   member.put("parent_id",parent_id);
		   member.put("name",name);
		
		   member.put("PartStyle_ID",PartStyle_ID);
		   array.add(member);
		  }
		 String root=partDao.getRoot();
		 json.put("root", root);
		 json.put("jsonArray", array);  
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString()); 
	}
	
	

}
