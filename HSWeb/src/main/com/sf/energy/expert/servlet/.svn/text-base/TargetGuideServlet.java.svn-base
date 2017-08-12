package com.sf.energy.expert.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sf.energy.expert.dao.EstimateDao;
import com.sf.energy.expert.model.EstimateModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class TargetGuideServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("utf-8");
		
		
		try
		{
			completePaper(request, response);
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
			throws ServletException, IOException {
			this.doGet(request, response);
	}

	private  void  completePaper(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		EstimateDao  estd = new EstimateDao();
		EstimateModel  estm = new EstimateModel();
		
		
		if(request.getParameter("year")!=null)
		{
			estm.setEstimate_Year(Integer.parseInt(request.getParameter("year")));
		}
		if(request.getParameter("style")!=null)
		{
			estm.setEstimate_Style(Integer.parseInt(request.getParameter("style")));
		}
		if(request.getParameter("Index_id")!=null)
		{
			estm.setEstimate_Index(request.getParameter("Index_id"));
		}
		if(request.getParameter("Estimate_Name")!=null)
		{
			estm.setEstimate_Name(request.getParameter("Estimate_Name"));
		}
		if(request.getParameter("Page21")!=null)
		{
			estm.setEstimate_Value0(request.getParameter("Page21"));
		}
		if(request.getParameter("Page22")!=null)
		{
			estm.setEstimate_Value1(request.getParameter("Page22"));
		}
		if(request.getParameter("Page31")!=null)
		{
			estm.setEstimate_Value2(request.getParameter("Page31"));
		}
		if(request.getParameter("Page32")!=null)
		{
			estm.setEstimate_Value3(request.getParameter("Page32"));
		}
		if(request.getParameter("Page41")!=null)
		{
			estm.setEstimate_Value4(request.getParameter("Page41"));
		}
		if(request.getParameter("Page42")!=null)
		{
			estm.setEstimate_Value5(request.getParameter("Page42"));
		}
		if(request.getParameter("Page43")!=null)
		{
			estm.setEstimate_Value6(request.getParameter("Page43"));
		}
		if(request.getParameter("Page51")!=null)
		{
			estm.setEstimate_Value7(request.getParameter("Page51"));
		}
		if(request.getParameter("Page61")!=null)
		{
			estm.setEstimate_Value8(request.getParameter("Page61"));
		}
		if(request.getParameter("Page71")!=null)
		{
			estm.setEstimate_Value9(request.getParameter("Page71"));
		}
		if(request.getParameter("Page81")!=null)
		{
			estm.setEstimate_Value10(request.getParameter("Page81"));
		}
		if(request.getParameter("Page82")!=null)
		{
			estm.setEstimate_Value11(request.getParameter("Page82"));
		}
		
		//System.out.println("年"+Integer.parseInt(request.getParameter("year")));
		//System.out.println("年1"+Integer.parseInt(request.getParameter("style")));
		//System.out.println("年2"+request.getParameter("Index_id"));
		
		
		float	count = 0;
		
		count = estd.getTotalCount(estm);
		
		//System.out.println("总分"+count);
		estm.setEstimate_Count(count);			//总分
		
		OperationLogInterface log = new OperationLogImplement();
		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		
		
		String	esID = null;		//Estimate_ID
		esID = estd.getEstimate_ID(estm);
		
		//System.out.println("esID"+esID);
		
		if(esID == null)
		{
			//System.out.println(1);
			esID = "";
		}
		else
		{
			//System.out.println(2);
		}
		
		
		if("".equals(esID))		//没有记录就插入
		{
			//System.out.println("插入");
			if (estd.insert(estm))
			{
				resultInfo = "success";
					// 写入日志
				log.writeLog(userLoginName, "指标考评", "编辑指标考评向导信息");
			}
			else
			{
				resultInfo = "fail";
					// 写入日志
				log.writeLogErrInfo(userLoginName, "指标考评", "编辑指标考评向导信息","编辑失败！！！");
			}
		}
		else			//有记录就更新
		{
			//System.out.println("更新");
			estm.setEstimate_ID(Integer.parseInt(esID));
			if(estd.modify(estm))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "指标考评", "编辑指标考评向导信息");
			}
			else
			{
				resultInfo = "fail";
					// 写入日志
				log.writeLogErrInfo(userLoginName, "指标考评", "编辑指标考评向导信息","编辑失败！！！");
			}
		}
		
		
		
		PrintWriter out = response.getWriter();
		//System.out.println(resultInfo);
		out.println(resultInfo);
		out.flush();
		out.close();
	}
}
