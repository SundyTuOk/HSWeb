package com.sf.energy.map.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONObject;

import com.sf.energy.light.dao.SLMonitorDao;
import com.sf.energy.light.dao.SlLampDao;
import com.sf.energy.light.model.SLMonitorModel;
import com.sf.energy.light.model.SlLampModel;

public class LampMapServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String flag=req.getParameter("flag");
		
		 if("loadKongzhi".equals(flag))
			{
				try {
					loadKongzhi(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	/**
	 * 导入路灯信息
	 * 
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void loadKongzhi(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String lampId=req.getParameter("id");
		SLMonitorDao slMonitorDao=new SLMonitorDao();
		SLMonitorModel slMonitorModel=new SLMonitorModel();
		slMonitorModel=slMonitorDao.queryLine(lampId);
		 //所属控制器的名称
		String SLKongzhi_Name=slMonitorModel.getSLKongzhi_Name();
		 //最后通讯时间
		String lastTime=slMonitorModel.getLastTime();
		 //回路状态
		String state=slMonitorModel.getLamp_State();
		if("off".equals(state))
		{
			state="关";
		}
		else if("on".equals(state))
		{
			state="开"; 
		}
		//回路地址
		String address=slMonitorModel.getSLLine_Star()+"—"+slMonitorModel.getSLLine_End();
	  
        String lineMes=SLKongzhi_Name+"/"+address+"/"+state+"/"+lastTime;
		
		//发送给前台
		 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("LampMes",lineMes);	
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  		    
		 pw.flush();
		 pw.close();
	}

}
