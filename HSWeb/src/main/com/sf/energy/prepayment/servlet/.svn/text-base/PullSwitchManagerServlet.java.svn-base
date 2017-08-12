package com.sf.energy.prepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.prepayment.dao.PullSwitchManagerDao;
import com.sf.energy.prepayment.model.ExchangeMeterModel;
import com.sf.energy.prepayment.model.PullSwitchManagerModel;
import com.sf.energy.util.Configuration;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;
import com.sf.energy.weixin.models.weixinParameters;

public class PullSwitchManagerServlet extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try {
			findMethod(req, resp);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String method = req.getParameter("method");
		//System.out.println("method:" + method);
		//查询分合闸信息
		if ("get_info_PWM".equals(method))
			get_info_EMR(req, resp);
		
		if ("getDianFeiLeiXingName".equals(method))
			getDianFeiLeiXingName(req, resp);
		if ("getoperHuiLu".equals(method))
			getoperHuiLu(req, resp);
			
		if ("getXuanXiangOption".equals(method))
			getXuanXiangOption(req, resp);
			
		if ("getOptionContent".equals(method))
			getOptionContent(req, resp);
		
		if ("confirmBtn_PSM_New_Div".equals(method))
			confirmBtn_PSM_New_Div(req, resp);
		
		if ("geteditManagermentInfo".equals(method))
			geteditManagermentInfo(req, resp);
		
		if ("confirmBtn_PSM_Edit_Div".equals(method))
			confirmBtn_PSM_Edit_Div(req, resp);
		
		if ("del_info_PSM".equals(method))
			del_info_PSM(req, resp);
	}
	
	private void del_info_PSM(HttpServletRequest req, HttpServletResponse resp) throws NumberFormatException, SQLException, IOException
	{
		PullSwitchManagerDao psmd = new PullSwitchManagerDao();
		String ID = req.getParameter("delete_PSM_info_ID");
		String[] IDs=ID.split("\\|");
		int k=0;
		for (int i = 0; i < IDs.length; i++)
		{
			Boolean b = psmd.delete(Integer.parseInt(IDs[i]));
			if(b)
			{
				k++;
			}
		}
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "拉合闸管理", "删除"+k+"条拉合闸信息");
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("result", k);
		json.add(jo);
		
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
		
	}


	private void confirmBtn_PSM_Edit_Div(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		String ID = req.getParameter("ID");
		
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "拉合闸管理", "编辑拉合闸信息,ID:"+ID);
		
		String planNum = req.getParameter("planNum");
		String planName = req.getParameter("planName");
		String laZha = req.getParameter("laZha");
	
		String heZha = req.getParameter("heZha");
		String choose = req.getParameter("choose");
		
		String biaoJiType = req.getParameter("biaoJiType");
		
		String caoZuoHuiLu = req.getParameter("caoZuoHuiLu");
		String zhiXingType = req.getParameter("zhiXingType");
		String beiZhu = req.getParameter("beiZhu");	
		String laZhaDate = req.getParameter("laZhaDate");	
		String heZhaDate = req.getParameter("heZhaDate");
		PullSwitchManagerDao psmd = new PullSwitchManagerDao();
		PullSwitchManagerModel model=new PullSwitchManagerModel();
		
		model.setId(Integer.parseInt(ID));
		model.setPlanNum(planNum);
		model.setPlanName(planName);
		model.setLaZha(laZha);
		model.setHeZha(heZha);
		model.setBiaoJiType(biaoJiType);
		model.setCaoZuoHuiLu(caoZuoHuiLu);
		model.setZhiXingType(zhiXingType);
		model.setChoose(choose);
		model.setBeiZhu(beiZhu);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		if(psmd.update(model,laZhaDate,heZhaDate))
			jo.put("result", "1");
		else
			jo.put("result", "0");
		
		json.add(jo);
		
		resp.setContentType("application/x-json");
		PrintWriter pw=resp.getWriter();
		//System.out.println(json.toString());
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}


	/**
	 * 点击编辑按钮响应的函数
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 */
	private void geteditManagermentInfo(HttpServletRequest req,
			HttpServletResponse resp) throws NumberFormatException, SQLException, IOException
	{
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		String ID = req.getParameter("ID");
		PullSwitchManagerDao psmd = new PullSwitchManagerDao();
		
		PullSwitchManagerModel psmm=psmd.queryByID(Integer.parseInt(ID));
		
		jo.put("id", psmm.getId());
		jo.put("planNum", psmm.getPlanNum());
		jo.put("planName", psmm.getPlanName());
		jo.put("laZha", psmm.getLaZha());
		jo.put("heZha", psmm.getHeZha());
		jo.put("biaoJiType", psmm.getBiaoJiType());
		jo.put("caoZuoHuiLu", psmm.getCaoZuoHuiLu());
		jo.put("zhiXingType", psmm.getZhiXingType());
		jo.put("Choose", psmm.getChoose());
		jo.put("BeiZhu", psmm.getBeiZhu());
		jo.put("laZhaDate", psmm.getLazhadate());
		jo.put("heZhaDate", psmm.getHezhadate());
		json.add(jo);
		resp.setContentType("application/x-json");
		PrintWriter pw=resp.getWriter();
//		System.out.println(json.toString());
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}


	private void confirmBtn_PSM_New_Div(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		String planNum = req.getParameter("planNum");
		String planName = req.getParameter("planName");
		
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "拉合闸管理", "新建拉合闸信息,编号："+planNum+",名称："+planName);
		
		String laZha = req.getParameter("laZha");
	
		String heZha = req.getParameter("heZha");
		String choose = req.getParameter("choose");
		String biaoJiType = req.getParameter("biaoJiType");
		
		String caoZuoHuiLu = req.getParameter("caoZuoHuiLu");
		String zhiXingType = req.getParameter("zhiXingType");
		String beiZhu = req.getParameter("beiZhu");
		
		String laZhaDate = req.getParameter("laZhaDate");	
		String heZhaDate = req.getParameter("heZhaDate");

		
		PullSwitchManagerDao psmd = new PullSwitchManagerDao();
		PullSwitchManagerModel model=new PullSwitchManagerModel();
		model.setPlanNum(planNum);
		model.setPlanName(planName);
		model.setLaZha(laZha);
		model.setHeZha(heZha);
		model.setBiaoJiType(biaoJiType);
		model.setCaoZuoHuiLu(caoZuoHuiLu);
		model.setZhiXingType(zhiXingType);
		model.setChoose(choose);
		model.setBeiZhu(beiZhu);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		if(psmd.insert(model,laZhaDate,heZhaDate))
			jo.put("result", "1");
		else
			jo.put("result", "0");
		json.add(jo);
			
		resp.setContentType("application/x-json");
		PrintWriter pw=resp.getWriter();
		//System.out.println(json.toString());
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}


	/**
	 * 查询选项的内容
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void getOptionContent(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		int style = Integer.parseInt(req.getParameter("style"));
		int id = Integer.parseInt(req.getParameter("id"));
		int priceID = Integer.parseInt(req.getParameter("price"));
		
		PullSwitchManagerDao psmd = new PullSwitchManagerDao();
		JSONArray json =psmd.getOptionContent(style,id,priceID);
		
		resp.setContentType("application/x-json");
		PrintWriter pw=resp.getWriter();
		//System.out.println(json.toString());
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}


	/**
	 * 获取选项要选的值
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void getXuanXiangOption(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		HttpSession session=req.getSession();
    	int userID=0;
    	if(session.getAttribute("userId")!=null)
    	{
    		userID=Integer.parseInt(session.getAttribute("userId").toString());
    	}
    	
		int style = Integer.parseInt(req.getParameter("style"));
		PullSwitchManagerDao psmd = new PullSwitchManagerDao();
		JSONArray json =psmd.getXuanXiangOption(style,userID);
		
		resp.setContentType("application/x-json");
		PrintWriter pw=resp.getWriter();
		//System.out.println(json.toString());
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}


	/**
	 * 获取电费类型的名称
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws IOException 
	 */
private void getDianFeiLeiXingName(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		PullSwitchManagerDao psmd = new PullSwitchManagerDao();
		JSONArray json =psmd.getDianFeiLeiXingName();
		
		resp.setContentType("application/x-json");
		PrintWriter pw=resp.getWriter();
		//System.out.println(json.toString());
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}

/**
 * 获取操作回路信息
 * @param req
 * @param resp
 * @throws SQLException 
 * @throws IOException 
 */
private void getoperHuiLu(HttpServletRequest req,
		HttpServletResponse resp) throws SQLException, IOException
{
	
	JSONArray json = new JSONArray();
	
	JSONObject jo = new JSONObject();
	jo.put("Value", "0");
	jo.put("Text", "全部回路");
	json.add(jo);
		
	XMLConfiguration config=null;
	try
	{
		config = Configuration.getConfiguration("configuration/WebInfo.xml");
	} catch (ConfigurationException e)
	{
		e.printStackTrace();
	}
	if(config!=null)
	{
		 String lineA = "照明"; 
		 String lineB = "空调";
		 String lineAStr = "ZM";
		 String lineBStr = "KT";
		 String lineACode = "C027";
		 String lineBCode = "C028";
		 String lineANum = "2";
		 String lineBNum = "1";
		 
		String lineInfoA=config.getString("LineInfo.lineA");
		  if (lineInfoA!= null)
          {
			  String [] lineInfoAlist=lineInfoA.split("\\|");
              lineA = lineInfoAlist[0];
              lineAStr = lineInfoAlist[1];
              lineACode = lineInfoAlist[2];
              lineANum = lineInfoAlist[3];
              JSONObject joIn = new JSONObject();
              joIn.put("Value", lineANum);
              joIn.put("Text", lineA + "回路");
          	json.add(joIn);
          }
		  
		  String lineInfoB=config.getString("LineInfo.lineB");
		  if (lineInfoB!= null)
          {
			  String [] lineInfoBlist=lineInfoB.split("\\|");
              lineB = lineInfoBlist[0];
              lineBStr = lineInfoBlist[1];
              lineBCode = lineInfoBlist[2];
              lineBNum = lineInfoBlist[3];
              JSONObject joIn = new JSONObject();
              joIn.put("Value", lineBNum);
              joIn.put("Text", lineB + "回路");
          	json.add(joIn);
          }
	}
	
	
	resp.setContentType("application/x-json");
	PrintWriter pw=resp.getWriter();
	//System.out.println(json.toString());
	pw.println(json.toString());
	pw.flush();
	pw.close();
}



/**
 * 获得拉合闸首页的信息
 * @param req
 * @param resp
 * @throws SQLException
 * @throws IOException
 */
	private void get_info_EMR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
//		int flag = Integer.parseInt(req.getParameter("flag"));
//		int floorID = 0;
//		if(flag==3)
//		{
//			floorID = Integer.parseInt(req.getParameter("FloorID"));
//		}
//		int Id = Integer.parseInt(req.getParameter("Id"));
		String order = " order by " + req.getParameter("tableName") + " " + req.getParameter("order");
		
		PullSwitchManagerDao psmd = new PullSwitchManagerDao();
		ArrayList<PullSwitchManagerModel> list = new ArrayList<PullSwitchManagerModel>();
		list = psmd.queryInfo(thePageCount, thePageIndex, order);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", psmd.getTotalCount());
		json.add(jo);
		
		for(PullSwitchManagerModel psmm : list)
		{
			jo = new JSONObject();
			jo.put("id", psmm.getId());
			jo.put("planNum", psmm.getPlanNum());
			jo.put("planName", psmm.getPlanName());
			jo.put("laZha", psmm.getLaZha());
			jo.put("heZha", psmm.getHeZha());
			jo.put("biaoJiType", psmm.getBiaoJiType());
			jo.put("caoZuoHuiLu", psmm.getCaoZuoHuiLu());
			jo.put("zhiXingType", psmm.getZhiXingType());			
			json.add(jo);
		}
		
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
		
		
		
	}
}
