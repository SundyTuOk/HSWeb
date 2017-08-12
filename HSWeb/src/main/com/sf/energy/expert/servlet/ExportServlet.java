	
package com.sf.energy.expert.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.apps.rasterizer.SVGConverterException;

import com.sf.energy.expert.dao.MyConverter;





public class ExportServlet extends HttpServlet {

		
//	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		response.setContentType("text/html");
		
		
		String svg = request.getParameter("svg");
		
		String width = request.getParameter("width");
		
		String type = request.getParameter("type");
		
		String filename = request.getParameter("filename");
		
	
		String method = request.getParameter("method");
		
		
		@SuppressWarnings("deprecation")
		String WebRoot = request.getRealPath("")+"\\temp\\";
		
		
		String temp = WebRoot+System.currentTimeMillis()+(int)(Math.random()*1000)+".svg";

		
		File svgTempFile = new File(temp);
		
		OutputStreamWriter svgFileOsw = new OutputStreamWriter(new FileOutputStream(svgTempFile),"UTF-8");
		svgFileOsw.write(svg);
		svgFileOsw.flush();
		svgFileOsw.close();
		
		
		
		try {
			File resultFile = null;
			
			if(type.equals("image/svg+xml")) {
				resultFile = new File(temp);
				filename = "chart.svg";
			} else {
				MyConverter mc = new MyConverter();
				
				filename = mc.conver(temp,WebRoot, type, Float.parseFloat(width));
				
				resultFile = new File(WebRoot+filename);
			}
			FileInputStream resultFileFi = new FileInputStream(resultFile);
			long l = resultFile.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			
			
			if(method.equals("download")) {
//				System.out.println("download");
				response.setContentType("application/x-msdownload");
				response.setContentLength((int) l);
				response.setHeader("Content-Disposition", "attachment; filename=" + filename);
				while ((long) k < l) {
					int j;
					j = resultFileFi.read(abyte0, 0, 65000);
					k += j;
					response.getOutputStream().write(abyte0, 0, j);
				}
				resultFileFi.close();
				resultFile.delete();
			} else {
//				System.out.println("show");
				response.setCharacterEncoding("utf-8");
				String filepath = (WebRoot+filename).replaceAll("\\\\", "\\\\\\\\");
				
				response.getWriter().write("{\"filename\":\""+filepath+"\"}");
			}
			
			svgTempFile.delete();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SVGConverterException e) {
			e.printStackTrace();
		}
		
		
	}

}
