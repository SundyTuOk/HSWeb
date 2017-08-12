package com.sf.energy.expert.count;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
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

import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class FenLeiCountReportManage extends HttpServlet
{
//    private String everyDayCountWebPath = "model/waterReport/everyDayCount.jasper";
//    private String everyDayCountPath = null;
//    private JasperReport everyDayReport = null;
//
//    private String everyMonthCountWebPath = "model/waterReport/everyMonthCount.jasper";
//    private String everyMonthCountPath = null;
//    private JasperReport everyMonthReport = null;

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
        }
        catch (UnsupportedEncodingException e)
        {
            
            e.printStackTrace();
        }
        catch (IOException e)
        {
            
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            
            e.printStackTrace();
        }
        catch (JRException e)
        {
            
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            
            e.printStackTrace();
        }

    }

    private void findMethod(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException,
            JRException, ClassNotFoundException, ParseException
    {
        String method = request.getParameter("method");
       // //System.out.println("GetWaterReport---method:" + method);

        if (method.equalsIgnoreCase("getHtml"))
            getHtml(request, response);

        if (method.equalsIgnoreCase("export"))
            export(request, response);

        if (method.equalsIgnoreCase("getPageCount"))
            getPageCount(request, response);

    }

    private void getHtml(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException,
            JRException, ClassNotFoundException, ParseException
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
        }
        else
        {
            PrintWriter out = response.getWriter();
            out.println("empty Data");
            out.flush();
            out.close();
        }
    }

    private void export(HttpServletRequest request, HttpServletResponse response)
            throws JRException, ClassNotFoundException, SQLException,
            IOException, ParseException
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
            JRException, ClassNotFoundException, ParseException
    {
        JasperPrint jasperPrint = getJasperPrint(request);
        int pageCount = jasperPrint.getPages().size();

        PrintWriter out = response.getWriter();
        out.println(pageCount);
        out.flush();
        out.close();
    }

    private JasperPrint getJasperPrint(HttpServletRequest request)
            throws JRException, ClassNotFoundException, SQLException,
            ParseException
    {
    	WaterReportHelper erh = new WaterReportHelperImpl();
        String reportName = request.getParameter("reportName");
        String type = request.getParameter("type");
        Integer ID = Integer.parseInt(request.getParameter("ID"));
        JasperReport jasperReport = getJasperReport(reportName);
        List<WaterReportModel> list = null;
        JasperPrint jasperPrint = null;
        if ("FenLeiMonthCountReport".equals(reportName))
        {
            ////System.out.println("type:" + type);
            if ("group".equals(type))
            {
                int queryYear = Integer.parseInt(request
                        .getParameter("queryYear"));
                int queryMonth = Integer.parseInt(request
                        .getParameter("queryMonth"));
                list = erh.getGroupCountEveryDay(ID, queryYear, queryMonth);
            }

            if ("arch".equals(type))
            {
                int queryYear = Integer.parseInt(request
                        .getParameter("queryYear"));
                int queryMonth = Integer.parseInt(request
                        .getParameter("queryMonth"));
                list = erh.getArcCountEveryDay(ID, queryYear, queryMonth);
            }
        }

        if ("FenLeiYearCountReport".equals(reportName))
        {
            if ("group".equals(type))
            {
                int queryYear = Integer.parseInt(request
                        .getParameter("queryYear"));
                list = erh.getGroupCountEveryMonth(ID, queryYear);
            }

            if ("arch".equals(type))
            {
                int queryYear = Integer.parseInt(request
                        .getParameter("queryYear"));
               // //System.out.println("ID:" + ID + " queryYear:" + queryYear);
                list = erh.getArcCountEveryMonth(ID, queryYear);
            }
        }

        if ((list == null) || (list.size() == 0))
        {
            ////System.out.println("数据源为空");
            return null;
        }

        JRDataSource datesource = new JRBeanCollectionDataSource(list);
        jasperPrint = JasperFillManager.fillReport(jasperReport, null,
                datesource);

        return jasperPrint;
    }

    @SuppressWarnings("deprecation")
	private JasperReport getJasperReport(String value) throws JRException
    {
    	String everyDayCountWebPath = "model/waterReport/everyDayCount.jasper";
    	String everyDayCountPath = null;
    	JasperReport everyDayReport = null;

    	String everyMonthCountWebPath = "model/waterReport/everyMonthCount.jasper";
    	String everyMonthCountPath = null;
    	JasperReport everyMonthReport = null;
        JasperReport jasperReport = null;
        if ("everyDayCountReport".equals(value))
        {
            if (everyDayCountPath == null)
            {
                everyDayCountPath = this.getServletConfig().getServletContext()
                        .getRealPath(everyDayCountWebPath);
            }

            if (everyDayReport == null)
            {
                everyDayReport = (JasperReport) JRLoader
                        .loadObject(everyDayCountPath);
            }

            jasperReport = everyDayReport;
        }

        if ("everyMonthCountReport".equals(value))
        {
            if (everyMonthCountPath == null)
            {
                everyMonthCountPath = this.getServletConfig()
                        .getServletContext()
                        .getRealPath(everyMonthCountWebPath);
            }

            if (everyMonthReport == null)
            {
                everyMonthReport = (JasperReport) JRLoader
                        .loadObject(everyMonthCountPath);
            }

            jasperReport = everyMonthReport;
        }

        return jasperReport;
    }
}
