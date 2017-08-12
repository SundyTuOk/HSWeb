package com.sf.energy.ReportUtil.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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

import com.sf.energy.prepayment.dao.PrepaymenReportHelper;
import com.sf.energy.prepayment.model.ByBetweenDetailModel;
import com.sf.energy.prepayment.model.ByDayDetailModel;
import com.sf.energy.prepayment.model.ByMonthDetailModel;
import com.sf.energy.prepayment.model.ByMonthInfoModel;
import com.sf.energy.prepayment.model.ByMonthSellModel;
import com.sf.energy.prepayment.model.ByYearDetailModel;
import com.sf.energy.prepayment.model.ByYearInfoModel;
import com.sf.energy.prepayment.model.ByYearSellModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.dao.FenLeiCountHelper;
import com.sf.energy.statistics.dao.FenLeiCountHelperImpl;
import com.sf.energy.statistics.model.EnergyRecycleModel;
import com.sf.energy.statistics.model.FenLeiCountModel;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.DataValidation;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class JasperReportManage extends HttpServlet
{

//	// 按年售电
//	private String byYearSellWebPath = "model/PrepaymentManage/byYearSell.jasper";
//	private String byYearSellRealPath = null;
//	private JasperReport byYearSellReport = null;

	// 按月售电
//	private String byMonthSellWebPath = "model/PrepaymentManage/byMonthSell.jasper";
//	private String byMonthSellRealPath = null;
//	private JasperReport byMonthSellReport = null;

//	// 按年综合
//	private String byYearInfoWebPath = "model/PrepaymentManage/byYearInfo.jasper";
//	private String byYearInfoRealPath = null;
//	private JasperReport byYearInfoReport = null;
//
//	// 按月综合
//	private String byMonthInfoWebPath = "model/PrepaymentManage/byMonthInfo.jasper";
//	private String byMonthInfoRealPath = null;
//	private JasperReport byMonthInfoReport = null;
//
//	// 年盘点
//	private String byYearDetailWebPath = "model/PrepaymentManage/byYearDetail.jasper";
//	private String byYearDetailRealPath = null;
//	private JasperReport byYearDetailReport = null;
//
//	// 月盘点
//	private String byMonthDetailWebPath = "model/PrepaymentManage/byMonthDetail.jasper";
//	private String byMonthDetailRealPath = null;
//	private JasperReport byMonthDetailReport = null;
//
//	// 日盘点
//	private String byDayDetailWebPath = "model/PrepaymentManage/byDayDetail.jasper";
//	private String byDayDetailRealPath = null;
//	private JasperReport byDayDetailReport = null;
//
//	// 时间段盘点
//	private String byBetweenDetailWebPath = "model/PrepaymentManage/byBetweenDetail.jasper";
//	private String byBetweenDetailRealPath = null;
//	private JasperReport byBetweenDetailReport = null;
//
//	private ElecReportHelperImpl erh = new ElecReportHelperImpl();
//
//	// 用电逐日台账
//	private String everyDayCountWebPath = "model/everyDayCount.jasper";
//	private String everyDayCountPath = null;
//	private JasperReport everyDayReport = null;
//
//	// 用电逐月台账
//	private String everyMonthCountWebPath = "model/everyMonthCount.jasper";
//	private String everyMonthCountPath = null;
//	private JasperReport everyMonthReport = null;
//
//	// 电月汇总
//	private String monthDetailCountWebPath = "model/MonthDetailCount.jasper";
//	private String monthDetailCountPath = null;
//	private JasperReport monthDetailCountReport = null;
//
//	// 电年汇总
//	private String yearDetailCountWebPath = "model/YearDetailCount.jasper";
//	private String yearDetailCountPath = null;
//	private JasperReport yearDetailCountReport = null;
//
//	private WaterReportHelperImpl wrh = new WaterReportHelperImpl();
//
//	// 用水逐日台账
//	private String waterEveryDayCountWebPath = "model/waterReport/everyDayCount.jasper";
//	private String waterEveryDayCountPath = null;
//	private JasperReport waterEveryDayReport = null;
//
//	// 用水逐月台账
//	private String waterEveryMonthCountWebPath = "model/waterReport/everyMonthCount.jasper";
//	private String waterEveryMonthCountPath = null;
//	private JasperReport waterEveryMonthReport = null;
//
//	// 水月汇总
//	private String waterMonthDetailCountWebPath = "model/waterReport/MonthDetailCount.jasper";
//	private String waterMonthDetailCountPath = null;
//	private JasperReport waterMonthDetailCountReport = null;
//
//	// 水年汇总
//	private String waterYearDetailCountWebPath = "model/waterReport/YearDetailCount.jasper";
//	private String waterYearDetailCountPath = null;
//	private JasperReport waterYearDetailCountReport = null;
//
//
//	// 月分类能耗统计
//	private String monthFenLeiCountWebPath = "model/FenLeiCountReport.jasper";
//	private String monthFenLeiCountPath = null;
//	private JasperReport monthFenLeiCountReport = null;
//
//	// 年分类能耗统计
//	private String yearFenLeiCountWebPath = "model/FenLeiCountReport.jasper";
//	private String yearFenLeiCountPath = null;
//	private JasperReport yearFenLeiCountReport = null;
//
//	// 月能耗账单
//	private String everyDayFenLeiCountWebPath = "model/everyDayFenLeiCount.jasper";
//	private String everyDayFenLeiCountPath = null;
//	private JasperReport everyDayFenLeiCountReport = null;
//
//	// 年能耗账单
//	private String everyMonthFenLeiCountWebPath = "model/everyMonthFenLeiCount.jasper";
//	private String everyMonthFenLeiCountPath = null;
//	private JasperReport everyMonthFenLeiCountReport = null;

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
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, JRException,
			ClassNotFoundException, ParseException
	{
		String method = request.getParameter("method");

		if (method.equalsIgnoreCase("getHtml"))
			getHtml(request, response);

		if (method.equalsIgnoreCase("export"))
			export(request, response);

		if (method.equalsIgnoreCase("getPageCount"))
			getPageCount(request, response);

	}

	private void getHtml(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, JRException,
			ClassNotFoundException, ParseException
	{
		JasperPrint jasperPrint = getJasperPrint(request);

		if (jasperPrint != null)
		{
			Integer queryPageIndex = -1;
			if (request.getParameter("queryIndex") != null)
				queryPageIndex = Integer.parseInt(request.getParameter("queryIndex")) - 1;
			else
			{
				return;
			}
			Integer totalPageNum = -1;

			totalPageNum = jasperPrint.getPages().size() - 1;

			if (queryPageIndex >= 0 && queryPageIndex <= totalPageNum)
			{
				JRHtmlExporter exporter = new JRHtmlExporter();
				exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, response.getWriter());
				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);

				exporter.setParameter(JRExporterParameter.PAGE_INDEX, queryPageIndex);
				exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "utf-8");

				exporter.exportReport();
			}
		} else
		{
			PrintWriter out = response.getWriter();
			out.println("empty Data");
			out.flush();
			out.close();
		}

	}

	private void export(HttpServletRequest request, HttpServletResponse response) throws JRException, ClassNotFoundException, SQLException,
			IOException, ParseException
	{
		String fileType = null;
		if (request.getParameter("fileType") != null)
			fileType = request.getParameter("fileType");
		else
		{
			return;
		}

		JasperPrint jasperPrint = getJasperPrint(request);
		if (jasperPrint == null)
			return;

		if ("excel".equalsIgnoreCase(fileType))
		{
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			/*  */
			response.setHeader("Content-Disposition", "attachment;filename=Excel_File.xls");
			/* */
			response.setContentType("application/vnd_ms-excel");
			/*  */
			exporter.exportReport();
		}

		if ("pdf".equalsIgnoreCase(fileType))
		{
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			/*  */
			response.setHeader("Content-Disposition", "attachment;filename=PDF__File.pdf");
			/*  */
			response.setContentType("application/pdf");
			response.setCharacterEncoding("UTF-8");
			/*  */
			exporter.exportReport();
		}
	}

	private void getPageCount(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, JRException,
			ClassNotFoundException, ParseException
	{
		JasperPrint jasperPrint = getJasperPrint(request);
		int pageCount = 0;
		if (jasperPrint != null)
			pageCount = jasperPrint.getPages().size();
		PrintWriter out = response.getWriter();
		out.println(pageCount);
		out.flush();
		out.close();
	}

	private JasperPrint getJasperPrint(HttpServletRequest request) throws JRException, ClassNotFoundException, SQLException
	{
		FenLeiCountHelper fch = new FenLeiCountHelperImpl();
		WaterReportHelperImpl wrh = new WaterReportHelperImpl();

		// 按年售电
		String byYearSellWebPath = "model/PrepaymentManage/byYearSell.jasper";
		String byYearSellRealPath = null;
		JasperReport byYearSellReport = null;
		// 按月售电
		String byMonthSellWebPath = "model/PrepaymentManage/byMonthSell.jasper";
		String byMonthSellRealPath = null;
		JasperReport byMonthSellReport = null;
		// 按年综合
		String byYearInfoWebPath = "model/PrepaymentManage/byYearInfo.jasper";
		String byYearInfoRealPath = null;
		JasperReport byYearInfoReport = null;

		// 按月综合
		String byMonthInfoWebPath = "model/PrepaymentManage/byMonthInfo.jasper";
		String byMonthInfoRealPath = null;
		JasperReport byMonthInfoReport = null;

		// 年盘点
		String byYearDetailWebPath = "model/PrepaymentManage/byYearDetail.jasper";
		String byYearDetailRealPath = null;
		JasperReport byYearDetailReport = null;

		// 月盘点
		String byMonthDetailWebPath = "model/PrepaymentManage/byMonthDetail.jasper";
		String byMonthDetailRealPath = null;
		JasperReport byMonthDetailReport = null;

		// 日盘点
		String byDayDetailWebPath = "model/PrepaymentManage/byDayDetail.jasper";
		String byDayDetailRealPath = null;
		JasperReport byDayDetailReport = null;

		// 时间段盘点
		String byBetweenDetailWebPath = "model/PrepaymentManage/byBetweenDetail.jasper";
		String byBetweenDetailRealPath = null;
		JasperReport byBetweenDetailReport = null;

		ElecReportHelperImpl erh = new ElecReportHelperImpl();

		// 用电逐日台账
		String everyDayCountWebPath = "model/everyDayCount.jasper";
		String everyDayCountPath = null;
		JasperReport everyDayReport = null;

		// 用电逐月台账
		String everyMonthCountWebPath = "model/everyMonthCount.jasper";
		String everyMonthCountPath = null;
		JasperReport everyMonthReport = null;

		// 电月汇总
		String monthDetailCountWebPath = "model/MonthDetailCount.jasper";
		String monthDetailCountPath = null;
		JasperReport monthDetailCountReport = null;

		// 电年汇总
		String yearDetailCountWebPath = "model/YearDetailCount.jasper";
		String yearDetailCountPath = null;
		JasperReport yearDetailCountReport = null;

		// 用水逐日台账
		String waterEveryDayCountWebPath = "model/waterReport/everyDayCount.jasper";
		String waterEveryDayCountPath = null;
		JasperReport waterEveryDayReport = null;

		// 用水逐月台账
		String waterEveryMonthCountWebPath = "model/waterReport/everyMonthCount.jasper";
		String waterEveryMonthCountPath = null;
		JasperReport waterEveryMonthReport = null;

		// 水月汇总
		String waterMonthDetailCountWebPath = "model/waterReport/MonthDetailCount.jasper";
		String waterMonthDetailCountPath = null;
		JasperReport waterMonthDetailCountReport = null;

		// 水年汇总
		String waterYearDetailCountWebPath = "model/waterReport/YearDetailCount.jasper";
		String waterYearDetailCountPath = null;
		JasperReport waterYearDetailCountReport = null;

		//是否回收分类统计
		String energyRecycleOrNoWebPath = "model/energyRecycleOrNo.jasper";
		String energyRecycleOrNoPath = null;
		JasperReport energyRecycleOrNoReport = null;
		
		// 月分类能耗统计
		String monthFenLeiCountWebPath = "model/FenLeiCountReport.jasper";
		String monthFenLeiCountPath = null;
		JasperReport monthFenLeiCountReport = null;

		// 年分类能耗统计
		String yearFenLeiCountWebPath = "model/FenLeiCountReport.jasper";
		String yearFenLeiCountPath = null;
		JasperReport yearFenLeiCountReport = null;

		// 月能耗账单
		String everyDayFenLeiCountWebPath = "model/everyDayFenLeiCount.jasper";
		String everyDayFenLeiCountPath = null;
		JasperReport everyDayFenLeiCountReport = null;

		// 年能耗账单
		String everyMonthFenLeiCountWebPath = "model/everyMonthFenLeiCount.jasper";
		String everyMonthFenLeiCountPath = null;
		JasperReport everyMonthFenLeiCountReport = null;
		
		ArchitectureDao archDao = new ArchitectureDao();
		DepartmentDao groupDao = new DepartmentDao();
		AreaDao areaDao = new AreaDao();
		PrepaymenReportHelper prh = new PrepaymenReportHelper();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String reportName = null;
		if (request.getParameter("reportName") != null)
			reportName = request.getParameter("reportName");
		else
		{
			return null;
		}

		String type = null;
		if (request.getParameter("type") != null)
			type = request.getParameter("type");
		else
		{
			return null;
		}
		//System.out.println("type:"+type);

		Integer ID = -1;
		if (request.getParameter("ID") != null && DataValidation.isNumber(request.getParameter("ID")))
			ID = Integer.parseInt(request.getParameter("ID"));
		else
		{
			return null;
		}

		int queryYear = -1;
		if (request.getParameter("queryYear") != null && DataValidation.isNumber(request.getParameter("queryYear")))
			queryYear = Integer.parseInt(request.getParameter("queryYear"));
		else
		{
			return null;
		}

		int queryMonth = -1;
		if (request.getParameter("queryMonth") != null && DataValidation.isNumber(request.getParameter("queryMonth")))
			queryMonth = Integer.parseInt(request.getParameter("queryMonth"));

		JasperReport jasperReport = null;

		JasperPrint jasperPrint = null;

		String name = null;

		if ("group".equals(type))
		{
			name = groupDao.queryNameById(ID);
		}

		if ("arch".equals(type))
		{
			name = archDao.queryByID(ID).getName();
		}

		if ("area".equals(type))
		{
			name = areaDao.queryNameById(ID);
		}

		if ("all".equals(type))
		{
			name = prh.getSystmName();
		}

		JRDataSource datesource = null;

		// 按年售电
		if ("byYearSell".equals(reportName))
		{
			jasperReport = getJasperReport(byYearSellWebPath, byYearSellRealPath, byYearSellReport);

			List<ByYearSellModel> list = null;

			ByYearSellModel bsm = null;

			if ("arch".equals(type))
			{
				bsm = prh.getArchSellByYear(name, ID, queryYear);
			}

			if ("area".equals(type))
			{
				bsm = prh.getAreaSellByYear(name, ID, queryYear);
			}

			if ("all".equals(type))
			{
				bsm = prh.getAllSellByYear(queryYear);
			}

			if (bsm != null)
			{
				list = new LinkedList<ByYearSellModel>();
				list.add(bsm);
				datesource = new JRBeanCollectionDataSource(list);
			}

		}

		// 按月售电
		if ("byMonthSell".equals(reportName) && queryMonth != -1)
		{
			jasperReport = getJasperReport(byMonthSellWebPath, byMonthSellRealPath, byMonthSellReport);

			List<ByMonthSellModel> list = null;

			if ("arch".equals(type))
			{
				list = prh.getArchSellByMonth(ID, queryYear, queryMonth);
			}

			if ("area".equals(type))
			{
				list = prh.getAreaSellByMonth(ID, queryYear, queryMonth);
			}

			if ("all".equals(type))
			{
				list = prh.getAllSellByMonth(queryYear, queryMonth);
			}

			if (list != null && list.size() > 0)
				datesource = new JRBeanCollectionDataSource(list);
		}

		// 按年综合
		if ("byYearInfo".equals(reportName))
		{
			jasperReport = getJasperReport(byYearInfoWebPath, byYearInfoRealPath, byYearInfoReport);

			List<ByYearInfoModel> list = null;
			ByYearInfoModel bim = null;

			if ("arch".equals(type))
			{
				bim = prh.getArchInfoByYear(ID, queryYear);
			}

			if ("area".equals(type))
			{
				bim = prh.getAreaInfoByYear(ID, queryYear);
			}

			if ("all".equals(type))
			{
				bim = prh.getAllInfoByYear(queryYear);
			}

			if (bim != null)
			{
				list = new LinkedList<ByYearInfoModel>();
				list.add(bim);
				datesource = new JRBeanCollectionDataSource(list);
			}

		}

		// 按月综合
		if ("byMonthInfo".equals(reportName) && queryMonth != -1)
		{
			jasperReport = getJasperReport(byMonthInfoWebPath, byMonthInfoRealPath, byMonthInfoReport);

			List<ByMonthInfoModel> list = null;
			ByMonthInfoModel bim = new ByMonthInfoModel();
			bim.setName(name);
			bim.setQueryYear(queryYear);
			bim.setQueryMonth(queryMonth);

			if ("arch".equals(type))
			{
				bim = prh.getArchInfoByMonth(ID, queryYear, queryMonth);
			}

			if ("area".equals(type))
			{
				bim = prh.getAreaInfoByMonth(ID, queryYear, queryMonth);
			}

			if ("all".equals(type))
			{
				bim = prh.getAllInfoByMonth(queryYear, queryMonth);
			}

			if (bim != null)
			{
				list = new LinkedList<ByMonthInfoModel>();
				list.add(bim);
				datesource = new JRBeanCollectionDataSource(list);
			}

		}

		// 年盘点
		if ("byYearDetail".equals(reportName))
		{
			jasperReport = getJasperReport(byYearDetailWebPath, byYearDetailRealPath, byYearDetailReport);

			List<ByYearDetailModel> list = prh.getAllDetailByYear(queryYear,type, ID);

			if (list != null && list.size() > 0)
			{
				datesource = new JRBeanCollectionDataSource(list);
			}

		}

		// 月盘点
		if ("byMonthDetail".equals(reportName))
		{
			jasperReport = getJasperReport(byMonthDetailWebPath, byMonthDetailRealPath, byMonthDetailReport);
			
			List<ByMonthDetailModel> list = prh.getAllDetailByMonth(queryYear, queryMonth, type, ID);
			if (list != null && list.size() > 0)
			{
				datesource = new JRBeanCollectionDataSource(list);
			}
		}

		// 日盘点
		if ("byDayDetail".equals(reportName))
		{
			jasperReport = getJasperReport(byDayDetailWebPath, byDayDetailRealPath, byDayDetailReport);

			Date theDate = null;
			if (request.getParameter("SatrtDate") != null)
			{
				try
				{
					theDate = df.parse(request.getParameter("SatrtDate"));
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
				}
			}

			if (theDate != null)
			{
				List<ByDayDetailModel> list = prh.getAllDetailByDay(theDate, type, ID);
				if (list != null && list.size() > 0)
				{
					datesource = new JRBeanCollectionDataSource(list);
				}
			}

		}

		// 时间段盘点
		if ("byBetweenDetail".equals(reportName))
		{
			jasperReport = getJasperReport(byBetweenDetailWebPath, byBetweenDetailRealPath, byBetweenDetailReport);

			String userIDs = request.getParameter("userIDs");
			//System.out.println("userIDs:"+userIDs);
			Date start = null;
			if (request.getParameter("SatrtDate") != null)
			{
				try
				{
					start = df.parse(request.getParameter("SatrtDate"));
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
				}
			}

			Date end = null;
			if (request.getParameter("EndDate") != null)
			{
				try
				{
					end = df.parse(request.getParameter("EndDate"));
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
				}
			}

			if (start != null && end != null)
			{
				List<ByBetweenDetailModel> list = prh.getAllDetailByBetween(start, end, userIDs);
				if (list != null && list.size() > 0)
				{
					datesource = new JRBeanCollectionDataSource(list);
				}
			}

		}
		//能源按是否回收统计
		if ("energyRecycleOrNo".equals(reportName))
		{
			jasperReport = getJasperReport(energyRecycleOrNoWebPath, energyRecycleOrNoPath, energyRecycleOrNoReport);

			//String userIDs = request.getParameter("userIDs");
			//System.out.println("userIDs:"+userIDs);
			Date start = null;
			if (request.getParameter("SatrtDate") != null)
			{
				try
				{
					start = df.parse(request.getParameter("SatrtDate"));
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
				}
			}

			Date end = null;
			if (request.getParameter("EndDate") != null)
			{
				try
				{
					end = df.parse(request.getParameter("EndDate"));
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
				}
			}

			if (start != null && end != null)
			{
				List<EnergyRecycleModel> list = fch.getEnergyRecycle(start, end);
				if (list != null && list.size() > 0)
				{
					datesource = new JRBeanCollectionDataSource(list);
				}
			}

		}

		// 用电逐日台账
		if ("everyDayCountReport".equals(reportName))
		{
			jasperReport = getJasperReport(everyDayCountWebPath, everyDayCountPath, everyDayReport);

			List<ReportModel> list = null;
			if ("group".equals(type))
			{
				if (ID == 1)
				{
					list = erh.getAllCountEveryDay(queryYear, queryMonth);
				} else
				{
					list = erh.getGroupCountEveryDay(ID, queryYear, queryMonth);
				}
			}

			if ("arch".equals(type))
			{
				try
				{
					list = erh.getArcCountEveryDay(ID, queryYear, queryMonth);
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
				}
			}

			if ("area".equals(type))
			{
				list = erh.getAreaCountEveryDay(ID, queryYear, queryMonth);
			}

			if ("all".equals(type))
			{
				list = erh.getAllCountEveryDay(queryYear, queryMonth);
			}

			if (list != null && list.size() > 0)
				datesource = new JRBeanCollectionDataSource(list);
		}

		// 用电逐月台账
		if ("everyMonthCountReport".equals(reportName))
		{
			jasperReport = getJasperReport(everyMonthCountWebPath, everyMonthCountPath, everyMonthReport);
			List<EveryMonthCountModel> list = null;
			if ("group".equals(type))
			{
				list = erh.getEveryMonthByGroup(ID, queryYear);
			}

			if ("arch".equals(type))
			{
				EveryMonthCountModel rm = erh.getEveryMonthByArch(ID, queryYear);
				if (rm != null)
				{
					list = new LinkedList<EveryMonthCountModel>();
					list.add(erh.getEveryMonthByArch(ID, queryYear));

					rm.setTotalName(rm.getTotalName() + " 合计");
					list.add(rm);
				}

			}

			if ("area".equals(type))
			{
				list = erh.getEveryMonthByArea(ID, queryYear);
			}

			if ("all".equals(type))
			{
				list = erh.getEveryMonthByAll(queryYear);
			}

			if (list != null && list.size() > 0)
				datesource = new JRBeanCollectionDataSource(list);
		}

		// 电月汇总
		if ("MonthCountDetailReport".equals(reportName))
		{
			jasperReport = getJasperReport(monthDetailCountWebPath, monthDetailCountPath, monthDetailCountReport);

			ReportModel rm = null;

			if ("group".equals(type))
			{
				if (ID == 1)
				{
					rm = erh.getAllCountDetailByMonth(queryYear, queryMonth);
				} else
				{
					rm = erh.getGroupCountDetailByMonth(ID, queryYear, queryMonth);
				}
				// rm = erh.getGroupCountDetailByMonth(ID, queryYear,
				// queryMonth);
			}

			if ("arch".equals(type))
			{
				rm = erh.getArcCountDetailByMonth(ID, queryYear, queryMonth);
			}

			if ("area".equals(type))
			{
				rm = erh.getAreaCountDetailByMonth(ID, queryYear, queryMonth);
			}

			if ("all".equals(type))
			{
				rm = erh.getAllCountDetailByMonth(queryYear, queryMonth);
			}

			if (rm != null)
			{
				List<ReportModel> list = new LinkedList<ReportModel>();
				list.add(rm);
				datesource = new JRBeanCollectionDataSource(list);
			}
		}

		// 电年汇总
		if ("YearCountDetailReport".equals(reportName))
		{
			jasperReport = getJasperReport(yearDetailCountWebPath, yearDetailCountPath, yearDetailCountReport);

			ReportModel rm = null;

			if ("group".equals(type))
			{
				if (ID == 1)
				{
					rm = erh.getAllCountDetailByYear(queryYear);
				} else
				{
					rm = erh.getGroupCountDetailByYear(ID, queryYear);
				}
				// rm = erh.getGroupCountDetailByYear(ID, queryYear);
			}

			if ("arch".equals(type))
			{
				rm = erh.getArcCountDetailByYear(ID, queryYear);
			}

			if ("area".equals(type))
			{
				rm = erh.getAreaCountDetailByYear(ID, queryYear);
			}

			if ("all".equals(type))
			{
				rm = erh.getAllCountDetailByYear(queryYear);
			}

			if (rm != null)
			{
				List<ReportModel> list = new LinkedList<ReportModel>();
				list.add(rm);
				datesource = new JRBeanCollectionDataSource(list);
			}
		}

		// 用水逐日台账
		if ("waterEveryDayCountReport".equals(reportName))
		{
			jasperReport = getJasperReport(waterEveryDayCountWebPath, waterEveryDayCountPath, waterEveryDayReport);

			List<WaterReportModel> list = null;
			if ("group".equals(type))
			{
				list = wrh.getGroupCountEveryDay(ID, queryYear, queryMonth);
			}

			if ("arch".equals(type))
			{
				try
				{
					list = wrh.getArcCountEveryDay(ID, queryYear, queryMonth);
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
				}
			}

			if ("area".equals(type))
			{
				list = wrh.getAreaCountEveryDay(ID, queryYear, queryMonth);
			}

			if ("all".equals(type))
			{
				list = wrh.getAllCountEveryDay(queryYear, queryMonth);
			}

			if (list != null && list.size() > 0)
				datesource = new JRBeanCollectionDataSource(list);
		}

		// 用水逐月台账
		if ("waterEveryMonthCountReport".equals(reportName))
		{
			jasperReport = getJasperReport(waterEveryMonthCountWebPath, waterEveryMonthCountPath, waterEveryMonthReport);

			List<EveryMonthCountModel> list = null;
			if ("group".equals(type))
			{
				list = wrh.getEveryMonthByGroup(ID, queryYear);
			}

			if ("arch".equals(type))
			{
				EveryMonthCountModel rm = wrh.getEveryMonthByArch(ID, queryYear);
				if (rm != null)
				{
					list = new LinkedList<EveryMonthCountModel>();
					list.add(wrh.getEveryMonthByArch(ID, queryYear));

					rm.setTotalName(rm.getTotalName() + " 合计");
					list.add(rm);
				}
			}

			if ("area".equals(type))
			{
				list = wrh.getEveryMonthByArea(ID, queryYear);
			}

			if ("all".equals(type))
			{
				list = wrh.getEveryMonthByAll(queryYear);
			}

			if (list != null && list.size() > 0)
				datesource = new JRBeanCollectionDataSource(list);
		}

		// 水月汇总
		if ("waterMonthCountDetailReport".equals(reportName))
		{
			jasperReport = getJasperReport(waterMonthDetailCountWebPath, waterMonthDetailCountPath, waterMonthDetailCountReport);

			WaterReportModel rm = null;

			List<WaterReportModel> list = null;
			if ("group".equals(type))
			{
				rm = wrh.getGroupCountDetailByMonth(ID, queryYear, queryMonth);
			}

			if ("arch".equals(type))
			{
				rm = wrh.getArcCountDetailByMonth(ID, queryYear, queryMonth);
			}

			if ("area".equals(type))
			{
				rm = wrh.getAreaCountDetailByMonth(ID, queryYear, queryMonth);
			}

			if ("all".equals(type))
			{
				rm = wrh.getAllCountDetailByMonth(queryYear, queryMonth);
			}

			if (rm != null)
			{
				list = new LinkedList<WaterReportModel>();
				list.add(rm);
				datesource = new JRBeanCollectionDataSource(list);
			}
		}

		// 水年汇总
		if ("waterYearCountDetailReport".equals(reportName))
		{
			jasperReport = getJasperReport(waterYearDetailCountWebPath, waterYearDetailCountPath, waterYearDetailCountReport);

			List<WaterReportModel> list = null;
			WaterReportModel rm = null;
			if ("group".equals(type))
			{
				rm = wrh.getGroupCountDetailByYear(ID, queryYear);
			}

			if ("arch".equals(type))
			{
				rm = wrh.getArcCountDetailByYear(ID, queryYear);
			}

			if ("area".equals(type))
			{
				rm = wrh.getAreaCountDetailByYear(ID, queryYear);
			}

			if ("all".equals(type))
			{
				rm = wrh.getAllCountDetailByYear(queryYear);
			}

			if (rm != null)
			{
				list = new LinkedList<WaterReportModel>();
				list.add(rm);
				datesource = new JRBeanCollectionDataSource(list);
			}
		}

		// 月分类能耗统计
		if ("monthFenLeiCount".equals(reportName))
		{
			
			jasperReport = getJasperReport(monthFenLeiCountWebPath, monthFenLeiCountPath, monthFenLeiCountReport);

			List<FenLeiCountModel> list = null;

			if ("area".equalsIgnoreCase(type))
			{
				list = fch.getArcFenLeiDataByMonthInArea(ID, queryYear, queryMonth);
			}
			if ("arch".equalsIgnoreCase(type))
			{
				list = fch.getArcFenLeiDataByMonthInArch(ID, queryYear, queryMonth);
			}
			if ("all".equalsIgnoreCase(type))
			{
				list = fch.getAllFenLeiDataByMonth(queryYear, queryMonth);
			}

			if (list != null && list.size() > 0)
			{
				datesource = new JRBeanCollectionDataSource(list);
			}
		}

		// 年分类能耗统计
		if ("yearFenLeiCount".equals(reportName))
		{
			jasperReport = getJasperReport(yearFenLeiCountWebPath, yearFenLeiCountPath, yearFenLeiCountReport);

			List<FenLeiCountModel> list = null;

			if ("area".equalsIgnoreCase(type))
			{
				list = fch.getArcFenLeiDataByYearInArea(ID, queryYear);
			}
			if ("arch".equalsIgnoreCase(type))
			{
				list = fch.getArcFenLeiDataByYearInArch(ID, queryYear);
			}
			if ("all".equalsIgnoreCase(type))
			{
				list = fch.getAllFenLeiDataByYear(queryYear);
			}

			if (list != null && list.size() > 0)
			{
				datesource = new JRBeanCollectionDataSource(list);
			}
		}

		// 月能耗账单
		if ("everyDayFenLeiCount".equals(reportName))
		{
			jasperReport = getJasperReport(everyDayFenLeiCountWebPath, everyDayFenLeiCountPath, everyDayFenLeiCountReport);

			List<FenLeiCountModel> list = null;

			if ("area".equalsIgnoreCase(type))
			{
				list = fch.getArcFenLeiDataEveryDayInArea(ID, queryYear, queryMonth);
			}
			if ("arch".equalsIgnoreCase(type))
			{
				list = fch.getArcFenLeiDataEveryDayInArch(ID, queryYear, queryMonth);
			}
			if ("all".equalsIgnoreCase(type))
			{
				list = fch.getAllFenLeiDataEveryDay(queryYear, queryMonth);
			}

			if (list != null && list.size() > 0)
			{
				datesource = new JRBeanCollectionDataSource(list);
			}
		}

		// 年能耗账单
		if ("everyMonthFenLeiCount".equals(reportName))
		{
			jasperReport = getJasperReport(everyMonthFenLeiCountWebPath, everyMonthFenLeiCountPath, everyMonthFenLeiCountReport);

			List<FenLeiCountModel> list = null;

			if ("area".equalsIgnoreCase(type))
			{
				list = fch.getArcFenLeiDataEveryMonthInArea(ID, queryYear);
			}
			if ("arch".equalsIgnoreCase(type))
			{
				list = fch.getArcFenLeiDataEveryMonthInArch(ID, queryYear);
			}
			if ("all".equalsIgnoreCase(type))
			{
				list = fch.getAllFenLeiDataEveryMonth(queryYear);
			}

			if (list != null && list.size() > 0)
			{
				datesource = new JRBeanCollectionDataSource(list);
			}
		}

		if (datesource == null || jasperReport == null)
		{
			return null;
		}

		jasperPrint = JasperFillManager.fillReport(jasperReport, null, datesource);

		return jasperPrint;
	}

	private JasperReport getJasperReport(String reportWebPath, String reportRealPath, JasperReport reportInstance) throws JRException
	{
		if (reportInstance == null)
		{
			if (reportRealPath == null)
			{
				reportRealPath = this.getServletConfig().getServletContext().getRealPath(reportWebPath);

				if (!(new File(reportRealPath)).exists())
					return null;
			}
			reportInstance = (JasperReport) JRLoader.loadObject(reportRealPath);
		}

		return reportInstance;
	}
}
