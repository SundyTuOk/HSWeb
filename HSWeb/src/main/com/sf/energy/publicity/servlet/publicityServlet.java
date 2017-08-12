package com.sf.energy.publicity.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.publicity.service.publicityService;

public class publicityServlet extends HttpServlet
{


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		findMethod(request, response);

		
	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response)
	{
		String method = request.getParameter("method");
		if(method.equals("loadAllInfo")){
			try
			{
				loadAllInfo(request, response);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void loadAllInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		publicityService service = new publicityService();
		String archStyleData = service.getArchStyleDataService();
		String archFenxiang = service.getArchFenxiangEnergy();
		String archAverageData[] =  service.getArchAverageData();
		String partmentFenxiang = service.getPartmentFenxiangEnergy();
		String data = new Gson().toJson(new String[]{archStyleData,archFenxiang,archAverageData[0],archAverageData[1],partmentFenxiang});
		//System.out.println(data);
		PrintWriter out = response.getWriter();
		out.write(data);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		doGet(request, response);
	}

}
