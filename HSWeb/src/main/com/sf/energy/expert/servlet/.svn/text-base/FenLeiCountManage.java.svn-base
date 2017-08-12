package com.sf.energy.expert.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.sf.energy.statistics.dao.FenLeiCountHelper;
import com.sf.energy.statistics.dao.FenLeiCountHelperImpl;
import com.sf.energy.statistics.model.FenLeiCountModel;

public class FenLeiCountManage extends HttpServlet
{

	private String fenLeiCountWebPath = "model/FenLeiCountReport.jasper";

	JasperReport fenLeiCountReport = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			findMethod(request, response);
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			JRException, ClassNotFoundException
	{
		String method = request.getParameter("method");
		////System.out.println("FenLeiCountReport---method:" + method);

		if (method.equalsIgnoreCase("getHtml"))
			getHtml(request, response);

		if (method.equalsIgnoreCase("export"))
			export(request, response);

		if (method.equalsIgnoreCase("getPageCount"))
			getPageCount(request, response);

	}

	private void getHtml(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			JRException, ClassNotFoundException
	{
		JasperPrint jasperPrint = getJasperPrint(request);

		Integer queryPageIndex = Integer.parseInt(request
				.getParameter("queryPageIndex")) - 1;
		Integer totalPageNum = -1;
		if (jasperPrint != null)
			totalPageNum = jasperPrint.getPages().size() - 1;

		if (queryPageIndex >= 0 && queryPageIndex <= totalPageNum)
		{
			JRHtmlExporter exporter = new JRHtmlExporter();
			exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT,
					jasperPrint);
			exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER,
					response.getWriter());
			exporter.setParameter(
					JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
					Boolean.FALSE);

			exporter.setParameter(JRExporterParameter.PAGE_INDEX,
					queryPageIndex);
			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
					"utf-8");

			exporter.exportReport();
		} else
		{
			PrintWriter out = response.getWriter();
			out.println("empty Data");
			out.flush();
			out.close();
		}
	}

	private void export(HttpServletRequest request, HttpServletResponse response)
			throws JRException, ClassNotFoundException, SQLException,
			IOException
	{
		JasperPrint jasperPrint = getJasperPrint(request);
		String fileType = request.getParameter("fileType");
		if ("excel".equalsIgnoreCase(fileType))
		{
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,
					jasperPrint);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);
			exporter.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			/*  */
			response.setHeader("Content-Disposition",
					"attachment;filename=Excel_File.xls");
			/* */
			response.setContentType("application/vnd_ms-excel");
			/*  */
			exporter.exportReport();
		}

		if ("pdf".equalsIgnoreCase(fileType))
		{
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			/*  */
			response.setHeader("Content-Disposition",
					"attachment;filename=PDF__File.pdf");
			/*  */
			response.setContentType("application/pdf");
			response.setCharacterEncoding("UTF-8");
			/*  */
			exporter.exportReport();
		}
	}

	private void getPageCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			JRException, ClassNotFoundException
	{
		JasperPrint jasperPrint = getJasperPrint(request);
		int pageCount = jasperPrint.getPages().size();

		PrintWriter out = response.getWriter();
		out.println(pageCount);
		out.flush();
		out.close();
	}

	private JasperPrint getJasperPrint(HttpServletRequest request)
			throws JRException, ClassNotFoundException, SQLException
	{
		FenLeiCountHelper fch = new FenLeiCountHelperImpl();
		int ID = 0;
		if (request.getParameter("Area_ID") != null)
			ID = Integer.parseInt(request.getParameter("Area_ID"));
		else
			return null;

		int queryYear = 0;
		if (request.getParameter("Query_Year") != null)
			queryYear = Integer.parseInt(request.getParameter("Query_Year"));
		else
			return null;

		String type = null;
		if (request.getParameter("Query_Type") != null)
			type = request.getParameter("Query_Type");
		else
			return null;

		int queryMonth = 0;
		if ("Month_Count".equals(type))
		{
			if (request.getParameter("Query_Month") != null)
				queryMonth = Integer.parseInt(request
						.getParameter("Query_Month"));
			else
				return null;
		}

		getJasperReport();

		List<FenLeiCountModel> list = null;
		JasperPrint jasperPrint = null;
		
		if ("Month_Count".equals(type))
			list = fch.getArcFenLeiDataByMonthInArea(ID, queryYear, queryMonth);
		else 
			list = fch.getArcFenLeiDataByYearInArea(ID, queryYear);

		if ((list == null) || (list.size() == 0))
		{
			////System.out.println("数据源为空");
			return null;
		}

		if (fenLeiCountReport == null)
		{
			////System.out.println("报表模板为空");
			return null;
		}

		JRDataSource datesource = new JRBeanCollectionDataSource(list);
		jasperPrint = JasperFillManager.fillReport(fenLeiCountReport, null,
				datesource);

		return jasperPrint;
	}

	@SuppressWarnings("deprecation")
	private void getJasperReport() throws JRException
	{
		if (fenLeiCountReport != null)
			return;

		String jasperPath = this.getServletConfig().getServletContext()
				.getRealPath(fenLeiCountWebPath);

		fenLeiCountReport = (JasperReport) JRLoader.loadObject(jasperPath);

	}
}
