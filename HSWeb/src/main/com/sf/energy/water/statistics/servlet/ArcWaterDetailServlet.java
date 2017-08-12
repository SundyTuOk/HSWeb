package com.sf.energy.water.statistics.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.gson.Gson;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.dao.AmHelperImpl;
import com.sf.energy.statistics.model.ArcAmDetailData;
import com.sf.energy.water.statistics.dao.WaterDetailHelperImpl;
import com.sf.energy.water.statistics.model.WaterDetailData;

public class ArcWaterDetailServlet extends HttpServlet {


	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		// System.out.println("method:" + method);

		if ("showContent".equals(method))
			showContent(request, response);
		if ("exportContent".equals(method))
			exportContent(request, response);

	}
	
	private void exportContent(HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		//DecimalFormat df = new DecimalFormat("#.00");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//PrintWriter out = response.getWriter();
		WaterDetailHelperImpl rhi_ArcAmDetail = new WaterDetailHelperImpl();
		int ArcAmDetail_ArcID = -1; // 表示选择选择所有建筑
		int AreaID = -1; // 表示选择所有区域
		//int AMid = 0; // 存放电表ID
		//List<Architecture> list = null; // 通过区域id获取下面的所有建筑id
		//List<Architecture> arealist = null; // 单个区域的建筑ID
		//List<Integer> ArcIDlist = null; // 存放每个区域下的所有建筑
		//List<Integer> allArcIDlist = null; // 存放所有区域下的所有建筑
		//List<Integer> allArcAMIDlist = null; // 存放所有的电表ID

		//Map<Integer, Area> resultMap = null; // 查询所有的区域ID

		//ArcIDlist = new ArrayList<Integer>();
		//allArcIDlist = new ArrayList<Integer>();
		//allArcAMIDlist = new ArrayList<Integer>();
		//ArchitectureDao archDao = new ArchitectureDao();
		//AreaDao areaDao = new AreaDao();
		//Gson gson_ArcAmDetail = new Gson();

		String arcId = "";
		String areaId = "";
		String st = "";
		String et = "";

		if (request.getParameter("aid") != null)
		{
			arcId = request.getParameter("aid");
		}
		if (request.getParameter("areaid") != null)
		{
			areaId = request.getParameter("areaid");
		}
		if (request.getParameter("st") != null)
		{
			st = request.getParameter("st");
		}
		if (request.getParameter("et") != null)
		{
			et = request.getParameter("et");
		}

			// System.out.println("st=" + st);

		if (arcId != null)
		{
			ArcAmDetail_ArcID = Integer.parseInt(arcId);
		}

		if (areaId != null)
		{
			AreaID = Integer.parseInt(areaId);
		}
		
		List<WaterDetailData> listExport=new ArrayList<WaterDetailData>();
		listExport=	rhi_ArcAmDetail.exportArcWaterDetail(ArcAmDetail_ArcID,AreaID,st,et);
		//System.out.println(new Gson().toJson(listExport));
		String filePath = "/img/ArchWmDetail.xls";
		String XlsPath = this.getServletContext().getRealPath(filePath);
		// System.out.println("XlsPath:"+XlsPath);

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("表一");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("水表位置");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("所属部门");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("起始示数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("终止示数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("用水量");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("表地址");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 6);
		cell.setCellValue("数据时间");
		cell.setCellStyle(style);

		

		int i = 0;
		float n = 0;
		for (WaterDetailData sdm : listExport)
		{
			n+=sdm.getNototalvalue();
			i++;
			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(sdm.getArea_name() +"." + sdm.getArchitecture_Name() + "." 
			        + sdm.getFloor() + "楼." + sdm.getWatermeter_name());
			row.createCell((short) 1).setCellValue(sdm.getPartmentName());
			row.createCell((short) 2).setCellValue(sdm.getStartvalue());
			row.createCell((short) 3).setCellValue(sdm.getEndvalue());
			row.createCell((short) 4).setCellValue(sdm.getTotalvalue());
			row.createCell((short) 5).setCellValue(sdm.getWatermeterNo());
			row.createCell((short) 6).setCellValue(sdm.getStarttime()+"至"+sdm.getEndtime());

		}
		//System.out.println(AreaID+" - "+ArcAmDetail_ArcID+" 未纳入计量数据："+n);
		FileOutputStream fout = new FileOutputStream(XlsPath);
		wb.write(fout);
		fout.close();

		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("filePath", filePath);

		PrintWriter pw = response.getWriter();
		pw.print(json.toString());
	
		
	}

	private void showContent(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		
		int ArcAmDetail_ArcID = -1; //表示选择选择所有建筑
		int AreaID = -1;			//表示选择所有区域
		

		Gson gson_ArcAmDetail = new Gson();
		
		
		String arcId = "";
		String areaId = "";
		String st = "";
		String et = "";
		String count = "";
		String index = "";
		
	
		if(request.getParameter("aid") != null)
		{
			arcId = request.getParameter("aid");
		}
		if(request.getParameter("areaid") != null)
		{
			areaId = request.getParameter("areaid");
		}
		if(request.getParameter("st") != null)
		{
			st = request.getParameter("st");
		}
		if(request.getParameter("et") != null)
		{
			et = request.getParameter("et");
		}
		
		////System.out.println("st=" + st);
		
		if(arcId != null ){
			ArcAmDetail_ArcID=Integer.parseInt(arcId);
		}
		
		if(areaId !=null ){
			AreaID=Integer.parseInt(areaId);
		}
		if(request.getParameter("thePageCount") != null)
		{
			count = request.getParameter("thePageCount");
		}

		if(request.getParameter("thePageIndex") != null)
		{
			index = request.getParameter("thePageIndex");
		}
//		//System.out.println("jj"+ jsonArray);

		
		WaterDetailHelperImpl  rhi_ArcAmDetail = new WaterDetailHelperImpl();

		List<WaterDetailData>  	ArcAmDetail_result = null;
		List<WaterDetailData>	maplist = null; //存放map里面的value集合 
	
		
		
		int totalCount=0;
		
		int thePageCount=Integer.parseInt(count);
		int thePageIndex=Integer.parseInt(index);
		if( AreaID == -1) //选了所有区域的情形，列出区域下所有建筑
		{
			////System.out.println("全校");
			int Flag = 1;
			
			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
			
			Map<String,List<WaterDetailData>> ParAmDetail_map = new HashMap<String,List<WaterDetailData>>();//存放对象数据的map
				try{
					ArcAmDetail_result = rhi_ArcAmDetail.getAmDtailAllPartment(thePageCount, thePageIndex, st, et,order);
					totalCount = rhi_ArcAmDetail.getTotalCount();
					WaterDetailData amDetailData = null;
					
					for(int i=0;i<ArcAmDetail_result.size();i++)
					{
						float istotalAm = 0;		//纳入计量
						float nototalAm = 0;		//非纳入计量
						
						amDetailData = ArcAmDetail_result.get(i);
						maplist = new ArrayList<WaterDetailData>();
						for(int j=0;j<ArcAmDetail_result.size();j++)
						{
							if(ArcAmDetail_result.get(j).getArchitecture_Name().equals(amDetailData.getArchitecture_Name()))
							{
								maplist.add(ArcAmDetail_result.get(j));
							}
							
						}
						
						for(int k = 0;k<maplist.size();k++)
						{
							
							istotalAm +=maplist.get(k).getIstotalvalue();
							
							nototalAm +=maplist.get(k).getNototalvalue();
							
						}
						
						for(int m = 0;m<maplist.size();m++)
						{
							maplist.get(m).setCountistotal(istotalAm);
							maplist.get(m).setCountnototal(nototalAm);
						}
						
						
						ParAmDetail_map.put(amDetailData.getArchitecture_Name(), maplist);
						////System.out.println("名字"+amDetailData.getArchitecture_Name());
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String ParAmDetaildata = gson_ArcAmDetail.toJson(new Object[]{Flag,ParAmDetail_map,totalCount});
				////System.out.println("卡拉1"+ParAmDetaildata);
				out.println(ParAmDetaildata);
		}
		else if( ArcAmDetail_ArcID == -1 && AreaID != -1) //只选了区域的情形，列出区域下所有建筑
		{
			////System.out.println("区域");
			int Flag = 2;
			
			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
			
			Map<String,List<WaterDetailData>> ParAmDetail_map = new HashMap<String,List<WaterDetailData>>();//存放对象数据的map
				try{
					ArcAmDetail_result = rhi_ArcAmDetail.getWaterDtailByarea(thePageCount, thePageIndex, AreaID, st, et, order);
					totalCount = rhi_ArcAmDetail.getTotalCount();
					WaterDetailData amDetailData = null;
					
					for(int i=0;i<ArcAmDetail_result.size();i++)
					{
						float istotalAm = 0;		//纳入计量
						float nototalAm = 0;		//非纳入计量
						
						amDetailData = ArcAmDetail_result.get(i);
						maplist = new ArrayList<WaterDetailData>();
						for(int j=0;j<ArcAmDetail_result.size();j++)
						{
							if(ArcAmDetail_result.get(j).getArchitecture_Name().equals(amDetailData.getArchitecture_Name()))
							{
								maplist.add(ArcAmDetail_result.get(j));
							}
							
						}
						
						for(int k = 0;k<maplist.size();k++)
						{
							
							istotalAm +=maplist.get(k).getIstotalvalue();
							
							nototalAm +=maplist.get(k).getNototalvalue();
							
						}
						
						for(int m = 0;m<maplist.size();m++)
						{
							maplist.get(m).setCountistotal(istotalAm);
							maplist.get(m).setCountnototal(nototalAm);
						}
						
						
						ParAmDetail_map.put(amDetailData.getArchitecture_Name(), maplist);
						////System.out.println("名字"+amDetailData.getArchitecture_Name());
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String ParAmDetaildata = gson_ArcAmDetail.toJson(new Object[]{Flag,ParAmDetail_map,totalCount});
				////System.out.println("卡拉2"+ParAmDetaildata);
				out.println(ParAmDetaildata);
		}
		
		if(ArcAmDetail_ArcID != -1 && AreaID != -1)	//选了区域下的某个建筑
		{
			
			////System.out.println("建筑");
			int Flag = 3;
			
			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
			
			Map<String,List<WaterDetailData>> ParAmDetail_map = new HashMap<String,List<WaterDetailData>>();//存放对象数据的map
				try{
					ArcAmDetail_result = rhi_ArcAmDetail.getWaterDtailByArc(thePageCount, thePageIndex, ArcAmDetail_ArcID, st, et, order);
					totalCount = rhi_ArcAmDetail.getTotalCount();
					WaterDetailData amDetailData = null;
					
					for(int i=0;i<ArcAmDetail_result.size();i++)
					{
						float istotalAm = 0;		//纳入计量
						float nototalAm = 0;		//非纳入计量
						
						amDetailData = ArcAmDetail_result.get(i);
						maplist = new ArrayList<WaterDetailData>();
						for(int j=0;j<ArcAmDetail_result.size();j++)
						{
							if(ArcAmDetail_result.get(j).getArchitecture_Name().equals(amDetailData.getArchitecture_Name()))
							{
								maplist.add(ArcAmDetail_result.get(j));
							}
							
						}
						
						for(int k = 0;k<maplist.size();k++)
						{
							
							istotalAm +=maplist.get(k).getIstotalvalue();
							
							nototalAm +=maplist.get(k).getNototalvalue();
							
						}
						
						for(int m = 0;m<maplist.size();m++)
						{
							maplist.get(m).setCountistotal(istotalAm);
							maplist.get(m).setCountnototal(nototalAm);
						}
						
						
						ParAmDetail_map.put(amDetailData.getArchitecture_Name(), maplist);
						////System.out.println("名字"+amDetailData.getArchitecture_Name());
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String ParAmDetaildata = gson_ArcAmDetail.toJson(new Object[]{Flag,ParAmDetail_map,totalCount});
				////System.out.println("卡拉3"+ParAmDetaildata);
				out.println(ParAmDetaildata);
					
		}
	
		out.flush();
		out.close();
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		try
		{
			findMethod(request, response);
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
}

