package com.sf.energy.expert.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.EnergyDataTabHelper;
import com.sf.energy.expert.dao.EnergyDataTabImpl;
import com.sf.energy.expert.model.EnergyDataTabData;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.util.DateOperation;
import com.sf.energy.util.GetSystemInfo;


public class EnergyDataTabServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		try {
			findMethod(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	
	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{	
		String StyleID = -1+"";
		int ArcID = -1;
		int	energyID = -1;
		String	amID = -1+"";
		String	waterID = -1+"";
		
		
		StyleID = request.getParameter("styleID");
		if (request.getParameter("archID")!=null&&!"".equals(request.getParameter("archID")))
		{
			ArcID = Integer.parseInt(request.getParameter("archID"));
		}
		if (request.getParameter("energyID")!=null&&!"".equals(request.getParameter("energyID")))
		{
			energyID = Integer.parseInt(request.getParameter("energyID"));

		}
		amID = request.getParameter("amID");
		waterID = request.getParameter("waterID");
		
		
		if((0+"").equals(StyleID) && energyID==0)		//选了全校的情形
		{
			//全校数据
			if("getSchoolData".equalsIgnoreCase(request.getParameter("method")))
			{
				getSchoolData(request,response);
			}
		}
		if((0+"").equals(StyleID) && energyID == 1 && (0+"").equals(amID) )		//选了电耗选项
		{
			//选了全校电耗数据
			if("getEnergyData".equalsIgnoreCase(request.getParameter("method")))
			{
				getEnergyData(request,response);
			}
		}
		
		if((0+"").equals(StyleID)  && energyID == 2 && (0+"").equals(waterID) )		//选了电耗选项
		{
			//选了全校水耗数据
			if("getWaterData".equalsIgnoreCase(request.getParameter("method")))
			{
				getWaterData(request,response);
			}
		}
		
		if((0+"").equals(StyleID)  && energyID == 3  )		//选了其他能耗选项
		{
			//选了全校其他能耗数据
			if("getOtherEnergyData".equalsIgnoreCase(request.getParameter("method")))
			{
				getOtherEnergyData(request,response);
			}
		}
		
		
		if(!(0+"").equals(StyleID) && ArcID ==0)		//选了分类建筑的情形
		{
			//选了分类建筑数据
			if("getArcStyleData".equalsIgnoreCase(request.getParameter("method")))
			{
				getArcStyleData(request,response);
			}
		}
		
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 0)		//选了建筑的情形
		{
			//选了建筑数据
			if("getArchData".equalsIgnoreCase(request.getParameter("method")))
			{
				getArchData(request,response);
			}
		}
		
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 1 && (0+"").equals(amID) )		//选了电耗选项
		{
			//选了电耗数据
			if("getEnergyData".equalsIgnoreCase(request.getParameter("method")))
			{
				getEnergyData(request,response);
			}
		}
		
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 2 && (0+"").equals(waterID) )		//选了电耗选项
		{
			//选了水耗数据
			if("getWaterData".equalsIgnoreCase(request.getParameter("method")))
			{
				getWaterData(request,response);
			}
		}
		
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 3  )		//选了其他能耗选项
		{
			//选了其他能耗数据
			if("getOtherEnergyData".equalsIgnoreCase(request.getParameter("method")))
			{
				getOtherEnergyData(request,response);
			}
		}
	}
	
	//全校的数据
	private	void  getSchoolData(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		String StyleID = -1+"";
		String StyleName = null;			//分类名称
		String SchoolName = null;			//学校名称
		int ArcID = -1;
		int Year = 2014;
		int Month = -1;
		EnergyDataTabData	edModel = null;
		EnergyDataTabData	schoolModel	= null;			//存学校数据
		List<EnergyDataTabData>	list_data = null;		//返回的list结果
		List<EnergyDataTabData>	list_school = null;		//学校数据
		ArrayList<Architecture>	list_arc = null;		//获取分类建筑下面的所有建筑信息
		List<Architecture>		list_style = null;		//获取全校所有分类建筑
		
		//中间变量
		float	arcArea	= 0;		//统计全校数据变量
		float amData = 0;
		float waterData = 0;
		float coaldata = 0;
		float gasdata = 0;
		float coaloildata = 0;
		float dieseloildata = 0;
		
		StyleID = request.getParameter("styleID");
		if (request.getParameter("archID")!=null&&!"".equals(request.getParameter("archID")))
		{
			ArcID = Integer.parseInt(request.getParameter("archID"));
		}	
		//ArcID = Integer.parseInt(request.getParameter("archID"));
		Year=Integer.parseInt(request.getParameter("Year"));
		Month=Integer.parseInt(request.getParameter("Month"));
		SchoolName = GetSystemInfo.getSchoolName();
//		
//		System.out.println("月"+Month);
//		System.out.println("StyleID"+StyleID);
//		System.out.println("ArcID"+ArcID);
		
		EnergyDataTabImpl	edti = new EnergyDataTabImpl();
		ArchitectureDao		archDao = new ArchitectureDao();
		
		Map<String,List<EnergyDataTabData>>		map = new	HashMap<String,List<EnergyDataTabData>>();
		
		HttpSession session = request.getSession();		//获取userID
        int userID = 0;
        if (session.getAttribute("userId") != null)
        {
            userID = Integer.parseInt(session.getAttribute("userId").toString());
        }
		
		list_style = archDao.displayAll(userID);
		
		
		if((0+"").equals(StyleID) )		//选了全校的情形
		{
			
				if(Month == 0)		//全年
				{
					list_school = new ArrayList<EnergyDataTabData>();
					List<String> style_list=archDao.getAllArchStyle();
					
					for(int j = 0;j<style_list.size();j++)
					{
						StyleName = archDao.queryArchStyleName(style_list.get(j).charAt(0));
						////System.out.println("StyleName"+StyleName);
						list_arc = archDao.queryArchByStyle(style_list.get(j).charAt(0));
						list_data	= new ArrayList<EnergyDataTabData>();
						for(int i = 0;i<list_arc.size();i++)
						{
							////System.out.println("ID"+list_arc.get(i).getId());
							edModel = edti.getEnergyDataTabArcByYear(list_arc.get(i).getId(), Year);
							list_data.add(edModel);
							map.put(StyleName.trim(), list_data);
							
//							arcArea +=list_data.get(i).getArcArea();
//							amData +=list_data.get(i).getAmData();
//							waterData +=list_data.get(i).getWaterData();
//							coaldata +=list_data.get(i).getCoaldata();
//							gasdata +=list_data.get(i).getGasdata();
//							coaloildata +=list_data.get(i).getCoaloildata();
//							dieseloildata +=list_data.get(i).getDieseloildata();
							
						}
							
					}
					
//					schoolModel = new EnergyDataTabData();
//					schoolModel.setArcName(SchoolName);
//					schoolModel.setAmData(amData);
//					schoolModel.setAmAreaData(amData/arcArea);
//					schoolModel.setArcArea(arcArea);
//					schoolModel.setGasdata(gasdata);
//					schoolModel.setCoaldata(coaldata);
//					schoolModel.setCoaloildata(coaloildata);
//					schoolModel.setDieseloildata(dieseloildata);
//					schoolModel.setWaterData(waterData);
//					schoolModel.setWaterAreaData(waterData/arcArea);
//					list_school.add(schoolModel);
					
//					map.put(SchoolName.trim(), list_school);		//放入学校值
				}
				else	//某个月
				{
					list_school = new ArrayList<EnergyDataTabData>();
					List<String> style_list=archDao.getAllArchStyle();
					for(int j = 0;j<style_list.size();j++)
					{
						StyleName = archDao.queryArchStyleName(style_list.get(j).charAt(0));
						////System.out.println("StyleName"+StyleName);
						list_arc = archDao.queryArchByStyle(style_list.get(j).charAt(0));
						list_data	= new ArrayList<EnergyDataTabData>();
						for(int i = 0;i<list_arc.size();i++)
						{
							edModel = edti.getEnergyDataTabArcByMonth(list_arc.get(i).getId(), Year, Month);
							list_data.add(edModel);
							map.put(StyleName.trim(), list_data);
							
//							arcArea +=list_data.get(i).getArcArea();
//							amData +=list_data.get(i).getAmData();
//							waterData +=list_data.get(i).getWaterData();
//							coaldata +=list_data.get(i).getCoaldata();
//							gasdata +=list_data.get(i).getGasdata();
//							coaloildata +=list_data.get(i).getCoaloildata();
//							dieseloildata +=list_data.get(i).getDieseloildata();
						}
					}
					
//					schoolModel = new EnergyDataTabData();
//					schoolModel.setArcName(SchoolName);
//					schoolModel.setAmData(amData);
//					schoolModel.setAmAreaData(amData/arcArea);
//					schoolModel.setArcArea(arcArea);
//					schoolModel.setCoaldata(coaldata);
//					schoolModel.setGasdata(gasdata);
//					schoolModel.setCoaloildata(coaloildata);
//					schoolModel.setDieseloildata(dieseloildata);
//					schoolModel.setWaterData(waterData);
//					schoolModel.setWaterAreaData(waterData/arcArea);
//					list_school.add(schoolModel);
//					
//					map.put(SchoolName.trim(), list_school);		//放入学校值
				}
			
		}
		
		String  data = gson.toJson(map);
		////System.out.println("全校建筑"+data);
		out.println(data);
		out.flush();
		out.close();
		
	}
	
	//分类建筑的数据
	private void  getArcStyleData(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		String StyleID = -1+"";
		String StyleName = null;			//分类名称
		int ArcID = -1;
		int Year = 2014;
		int Month = -1;
		EnergyDataTabData	edModel = null;
		List<EnergyDataTabData>	list_data = null;		//返回的list结果
		ArrayList<Architecture>	list_arc = null;		//获取分类建筑下面的所有建筑信息
		
		StyleID = request.getParameter("styleID");
		ArcID = Integer.parseInt(request.getParameter("archID"));
		Year=Integer.parseInt(request.getParameter("Year"));
		Month=Integer.parseInt(request.getParameter("Month"));
		
		////System.out.println("月"+Month);
		////System.out.println("StyleID"+StyleID);
		////System.out.println("ArcID"+ArcID);
		
		EnergyDataTabImpl	edti = new EnergyDataTabImpl();
		ArchitectureDao		archDao = new ArchitectureDao();
		list_data	= new ArrayList<EnergyDataTabData>();
		Map<String,List<EnergyDataTabData>>		map = new	HashMap<String,List<EnergyDataTabData>>();
		
		list_arc = archDao.queryArchByStyle(StyleID.charAt(0));
		StyleName = archDao.queryArchStyleName(StyleID.charAt(0));
		
		////System.out.println("StyleName"+StyleName);
		
		if(!(0+"").equals(StyleID) && ArcID ==0)		//选了分类建筑的情形
		{
			if(Month == 0)		//全年
			{
				for(int i = 0;i<list_arc.size();i++)
				{
					////System.out.println("ID"+list_arc.get(i).getId());
					edModel = edti.getEnergyDataTabArcByYear(list_arc.get(i).getId(), Year);
					list_data.add(edModel);
					map.put(StyleName.trim(), list_data);
				}
				
			}
			else	//某个月
			{
				for(int i = 0;i<list_arc.size();i++)
				{
					edModel = edti.getEnergyDataTabArcByMonth(list_arc.get(i).getId(), Year, Month);
					list_data.add(edModel);
					map.put(StyleName.trim(), list_data);
				}
				
			}
		}
		
		String  data = gson.toJson(map);
		////System.out.println("分类建筑"+data);
		out.println(data);
		out.flush();
		out.close();
		
	}
	
	//建筑的数据
	private void  getArchData(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		String StyleID = -1+"";
		//String StyleName = null;			//分类名称
		int ArcID = -1;
		int	energyID = -1;
		int Year = 2014;
		int Month = -1;
		EnergyDataTabData	edModel = null;
		List<EnergyDataTabData>	list_data = null;		//返回的list结果
		//ArrayList<Architecture>	list_arc = null;		//获取分类建筑下面的所有建筑信息
		
		StyleID = request.getParameter("styleID");
		ArcID = Integer.parseInt(request.getParameter("archID"));
		energyID = Integer.parseInt(request.getParameter("energyID"));
		Year=Integer.parseInt(request.getParameter("Year"));
		Month=Integer.parseInt(request.getParameter("Month"));
		
		////System.out.println("月"+Month);
		////System.out.println("StyleID"+StyleID);
		////System.out.println("ArcID"+ArcID);
		////System.out.println("energyID"+energyID);
		
		EnergyDataTabImpl	edti = new EnergyDataTabImpl();
		//ArchitectureDao		archDao = new ArchitectureDao();
		list_data	= new ArrayList<EnergyDataTabData>();
		Map<String,List<EnergyDataTabData>>		map = new	HashMap<String,List<EnergyDataTabData>>();
		
		//list_arc = archDao.queryArchByStyle(StyleID.charAt(0));
		//StyleName = archDao.queryArchStyleName(StyleID.charAt(0));
		
		////System.out.println("StyleName"+StyleName);
		
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 0)		//选了建筑的情形
		{
			if(Month == 0)		//全年
			{
				for(int i = 1;i<=12;i++)
				{
					////System.out.println("ID"+list_arc.get(i).getId());
					edModel = edti.getEnergyDataTabArcByMonth(ArcID, Year,i);
					list_data.add(edModel);
					map.put(edModel.getArcName().trim(), list_data);
				}
				
			}
			else	//某个月
			{
				int MaxDay = 0;
				DateOperation dateop = new DateOperation();
				MaxDay = dateop.queryMaxDayinMonth(Year, Month);	//查询月份有多少天
				
				for(int i = 1;i<= MaxDay;i++)
				{
					edModel = edti.getEnergyDataTabArcByDay(ArcID, Year, Month,i);
					list_data.add(edModel);
					map.put(edModel.getArcName().trim(), list_data);
				}
				
			}
		}
		
		String  data = gson.toJson(map);
		////System.out.println("建筑数据"+data);
		out.println(data);
		out.flush();
		out.close();
	}
	
	//电能能耗的数据
	private void  getEnergyData(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		String StyleID = "";
		String amID = "";
		int ArcID = 0;
		int Year = 0;
		int Month = 0;
		int	energyID = -1;
		if (request.getParameter("energyID")!=null&&!"".equals(request.getParameter("energyID")))
		{
			energyID = Integer.parseInt(request.getParameter("energyID"));

		}
		if(request.getParameter("styleID") != null)
		{
			StyleID= request.getParameter("styleID");
		}
		if(request.getParameter("amID") != null)
		{
			amID= request.getParameter("amID");
		}
		if(request.getParameter("archID")!= null)
		{
			ArcID= Integer.parseInt(request.getParameter("archID"));
		}
		if(request.getParameter("Year")!= null)
		{
			Year= Integer.parseInt(request.getParameter("Year"));
		}
		if(request.getParameter("Month")!= null)
		{
			Month= Integer.parseInt(request.getParameter("Month"));
		}
		
		
		EnergyDataTabData	edModel = null;
		List<EnergyDataTabData>	list_data = null;		//返回的list结果
		EnergyDataTabImpl	edti = new EnergyDataTabImpl();
		list_data	= new ArrayList<EnergyDataTabData>();
		Map<String,List<EnergyDataTabData>>		map = new	HashMap<String,List<EnergyDataTabData>>();
		if((0+"").equals(StyleID)  && energyID == 1 && (0+"").equals(amID) )		//选了全校电耗选项
		{
			////System.out.println("电耗");
			if(Month == 0)		//全年
			{
				for(int i = 1;i<= 12; i++)
				{
					edModel = edti.getEnergyDataTabDataArcEnergyByMonth(ArcID, Year, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-电耗", list_data);
				}
				
			}
			else		//每月
			{
				int MaxDay = 0;
				DateOperation dateop = new DateOperation();
				MaxDay = dateop.queryMaxDayinMonth(Year, Month);	//查询月份有多少天
				
				for(int i = 1;i<= MaxDay;i++)
				{
					edModel = edti.getEnergyDataTabDataArcEnergyByDay(ArcID, Year, Month, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-电耗", list_data);
				}
			}
		}
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 1 && (0+"").equals(amID) )		//选了电耗选项
		{
			////System.out.println("电耗");
			if(Month == 0)		//全年
			{
				for(int i = 1;i<= 12; i++)
				{
					edModel = edti.getEnergyDataTabDataArcEnergyByMonth(ArcID, Year, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-电耗", list_data);
				}
				
			}
			else		//每月
			{
				int MaxDay = 0;
				DateOperation dateop = new DateOperation();
				MaxDay = dateop.queryMaxDayinMonth(Year, Month);	//查询月份有多少天
				
				for(int i = 1;i<= MaxDay;i++)
				{
					edModel = edti.getEnergyDataTabDataArcEnergyByDay(ArcID, Year, Month, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-电耗", list_data);
				}
			}
		}
		
		String  data = gson.toJson(map);
		////System.out.println("电耗数据"+data);
		out.println(data);
		out.flush();
		out.close();
			
	}
	
	//用水能耗数据
	private void  getWaterData(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
//		String StyleID = -1+"";
//		//String StyleName = null;			//分类名称
//		int ArcID = -1;
//		int	energyID = -1;
//		String	waterID = -1+"";
//		int Year = 2014;
//		int Month = -1;
		String StyleID = "";
		String waterID = "";
		int ArcID = 0;
		int Year = 0;
		int Month = 0;
		int	energyID = -1;
		if (request.getParameter("energyID")!=null&&!"".equals(request.getParameter("energyID")))
		{
			energyID = Integer.parseInt(request.getParameter("energyID"));

		}
		if(request.getParameter("waterID") != null)
		{
			waterID= request.getParameter("waterID");
		}
		if(request.getParameter("styleID") != null)
		{
			StyleID= request.getParameter("styleID");
		}
		if(request.getParameter("archID")!= null)
		{
			ArcID= Integer.parseInt(request.getParameter("archID"));
		}
		if(request.getParameter("Year")!= null)
		{
			Year= Integer.parseInt(request.getParameter("Year"));
		}
		if(request.getParameter("Month")!= null)
		{
			Month= Integer.parseInt(request.getParameter("Month"));
		}
		
		EnergyDataTabData	edModel = null;
		List<EnergyDataTabData>	list_data = null;		//返回的list结果
		//ArrayList<Architecture>	list_arc = null;		//获取分类建筑下面的所有建筑信息
		
//		StyleID = request.getParameter("styleID");
//		ArcID = Integer.parseInt(request.getParameter("archID"));
//		energyID = Integer.parseInt(request.getParameter("energyID"));
//		waterID = request.getParameter("waterID");
//		Year=Integer.parseInt(request.getParameter("Year"));
//		Month=Integer.parseInt(request.getParameter("Month"));
		
		////System.out.println("waterID"+waterID);
		EnergyDataTabImpl	edti = new EnergyDataTabImpl();
		list_data	= new ArrayList<EnergyDataTabData>();
		Map<String,List<EnergyDataTabData>>		map = new	HashMap<String,List<EnergyDataTabData>>();
		if((0+"").equals(StyleID)  && energyID == 2 && (0+"").equals(waterID) )		//选了全校水耗选项
		{
			////System.out.println("水耗");
			if(Month == 0)		//全年
			{
				for(int i = 1;i<= 12; i++)
				{
					edModel = edti.getEnergyDataTabDataArcWaterByMonth(ArcID, Year, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-水耗", list_data);
				}
				
			}
			else		//每月
			{
				int MaxDay = 0;
				DateOperation dateop = new DateOperation();
				MaxDay = dateop.queryMaxDayinMonth(Year, Month);	//查询月份有多少天
				
				for(int i = 1;i<= MaxDay;i++)
				{
					edModel = edti.getEnergyDataTabDataArcWaterByDay(ArcID, Year, Month, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-水耗", list_data);
				}
			}
		}
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 2 && (0+"").equals(waterID) )		//选了电耗选项
		{
			////System.out.println("水耗");
			if(Month == 0)		//全年
			{
				for(int i = 1;i<= 12; i++)
				{
					edModel = edti.getEnergyDataTabDataArcWaterByMonth(ArcID, Year, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-水耗", list_data);
				}
				
			}
			else		//每月
			{
				int MaxDay = 0;
				DateOperation dateop = new DateOperation();
				MaxDay = dateop.queryMaxDayinMonth(Year, Month);	//查询月份有多少天
				
				for(int i = 1;i<= MaxDay;i++)
				{
					edModel = edti.getEnergyDataTabDataArcWaterByDay(ArcID, Year, Month, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-水耗", list_data);
				}
			}
		}
		
		String  data = gson.toJson(map);
		////System.out.println("水耗数据"+data);
		out.println(data);
		out.flush();
		out.close();
			
	}
	
	//其他能耗
	private	void getOtherEnergyData(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		String StyleID = "";
		int ArcID = 0;
		int Year = 0;
		int Month = 0;
		int	energyID = -1;
		if (request.getParameter("energyID")!=null&&!"".equals(request.getParameter("energyID")))
		{
			energyID = Integer.parseInt(request.getParameter("energyID"));

		}
		if(request.getParameter("styleID") != null)
		{
			StyleID= request.getParameter("styleID");
		}
		if(request.getParameter("archID")!= null)
		{
			ArcID= Integer.parseInt(request.getParameter("archID"));
		}
		if(request.getParameter("Year")!= null)
		{
			Year= Integer.parseInt(request.getParameter("Year"));
		}
		if(request.getParameter("Month")!= null)
		{
			Month= Integer.parseInt(request.getParameter("Month"));
		}
		
		EnergyDataTabData	edModel = null;
		List<EnergyDataTabData>	list_data = null;		//返回的list结果
		//ArrayList<Architecture>	list_arc = null;		//获取分类建筑下面的所有建筑信息
		
//		StyleID = request.getParameter("styleID");
//		ArcID = Integer.parseInt(request.getParameter("archID"));
//		energyID = Integer.parseInt(request.getParameter("energyID"));
//		//waterID = request.getParameter("waterID");
//		Year=Integer.parseInt(request.getParameter("Year"));
//		Month=Integer.parseInt(request.getParameter("Month"));
//		
		////System.out.println("waterID"+waterID);
		EnergyDataTabImpl	edti = new EnergyDataTabImpl();
		list_data	= new ArrayList<EnergyDataTabData>();
		Map<String,List<EnergyDataTabData>>		map = new	HashMap<String,List<EnergyDataTabData>>();
		if((0+"").equals(StyleID) && energyID == 3  )		//选了其他能耗选项
		{
			////System.out.println("其他能耗");
			if(Month == 0)		//全年
			{
				for(int i = 1;i<= 12; i++)
				{
					edModel = edti.getEnergyDataTabArcByMonth(ArcID, Year, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-其他能耗", list_data);
				}
				
			}
			else		//每月
			{
				int MaxDay = 0;
				DateOperation dateop = new DateOperation();
				MaxDay = dateop.queryMaxDayinMonth(Year, Month);	//查询月份有多少天
				
				for(int i = 1;i<= MaxDay;i++)
				{
					edModel = edti.getEnergyDataTabArcByDay(ArcID, Year, Month, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-其他能耗", list_data);
				}
			}
		}
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 3  )		//选了其他能耗选项
		{
			////System.out.println("其他能耗");
			if(Month == 0)		//全年
			{
				for(int i = 1;i<= 12; i++)
				{
					edModel = edti.getEnergyDataTabArcByMonth(ArcID, Year, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-其他能耗", list_data);
				}
				
			}
			else		//每月
			{
				int MaxDay = 0;
				DateOperation dateop = new DateOperation();
				MaxDay = dateop.queryMaxDayinMonth(Year, Month);	//查询月份有多少天
				
				for(int i = 1;i<= MaxDay;i++)
				{
					edModel = edti.getEnergyDataTabArcByDay(ArcID, Year, Month, i);
					list_data.add(edModel);
					map.put(edModel.getArcName()+"-其他能耗", list_data);
				}
			}
		}
		
		String  data = gson.toJson(map);
		////System.out.println("其他能耗数据"+data);
		out.println(data);
		out.flush();
		out.close();
	}
	

}
