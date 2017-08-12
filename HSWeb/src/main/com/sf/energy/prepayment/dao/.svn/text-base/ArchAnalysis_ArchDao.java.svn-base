package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.util.ConnDB;

public class ArchAnalysis_ArchDao
{
	public String getFusionChartsStr(String areaList, String archList, int year, int month) throws SQLException
	{
		String strChart = "";
		String strCategories = "";
		String strDatasetTY = "";
		String strDatasetLYM = "";
		String strDatasetLM = "";
		
		JSONArray areaJson = new JSONArray();
		JSONArray archJson = new JSONArray();
		
		int lastYear = year;
		int lastMonth = month - 1;
		if(month == 1)
		{
			lastYear = year -1;
			lastMonth = 12;
		}
		
		if(!"".equals(areaList) && areaList != null)
		{
			areaList = areaList.substring(0,areaList.length()-1);
			areaJson = getAreaDatas(areaList, year, month);
		}
		if(!"".equals(archList) && archList != null)
		{
			archList = archList.substring(0,archList.length()-1);
			archJson = getArchDatas(archList, year, month);
		}
		
		//拼category
        strCategories = "<categories font='Arial' fontSize='12' fontColor='000000'>";
        for (int i = 0; i < areaJson.size(); i++)
        {
            strCategories += "<category name='" + (String) ((JSONObject)areaJson.get(i)).get("Area_Name") + "' />";
        }
        for (int i = 0; i < archJson.size(); i++)
        {
            strCategories += "<category name='" + (String) ((JSONObject)archJson.get(i)).get("Architecture_Name") + "' />";
        }
        strCategories += "</categories>";
        
        //拼dataset
        strDatasetTY += "<dataset seriesName='" + year + "年" + month + "月' color='AFD8F8'>";
        strDatasetLYM += "<dataset seriesName='" + String.valueOf((year-1)) + "年" + month + "月' color='F6BD0F'>";
        strDatasetLM += "<dataset seriesName='" + lastYear + "年" + lastMonth + "月'  color='8BBA00'>";
        for (int i = 0; i < areaJson.size(); i++)
        {
            if("-".equals((String) ((JSONObject)areaJson.get(i)).get("thisMonthGross")))
            {
            	strDatasetTY += "<set value='0' />";
            }else
            {
            	strDatasetTY += "<set value='" + ((JSONObject)areaJson.get(i)).get("thisMonthGross") + "' />";
            }
            
            if("-".equals((String) ((JSONObject)areaJson.get(i)).get("lastYearMonthGross")))
            {
            	strDatasetLYM += "<set value='0' />";
            }else
            {
            	strDatasetLYM += "<set value='" + ((JSONObject)areaJson.get(i)).get("lastYearMonthGross") + "' />";
            }
            
            if("-".equals((String) ((JSONObject)areaJson.get(i)).get("lastMonthGross")))
            {
            	strDatasetLM += "<set value='0' />";
            }else
            {
            	strDatasetLM += "<set value='" + ((JSONObject)areaJson.get(i)).get("lastMonthGross") + "' />";
            }
        }
        for (int i = 0; i < archJson.size(); i++)
        {
        	if("-".equals((String) ((JSONObject)archJson.get(i)).get("thisMonthGross")))
            {
            	strDatasetTY += "<set value='0' />";
            }else
            {
            	strDatasetTY += "<set value='" + ((JSONObject)archJson.get(i)).get("thisMonthGross") + "' />";
            }
            
            if("-".equals((String) ((JSONObject)archJson.get(i)).get("lastYearMonthGross")))
            {
            	strDatasetLYM += "<set value='0' />";
            }else
            {
            	strDatasetLYM += "<set value='" + ((JSONObject)archJson.get(i)).get("lastYearMonthGross") + "' />";
            }
            
            if("-".equals((String) ((JSONObject)archJson.get(i)).get("lastMonthGross")))
            {
            	strDatasetLM += "<set value='0' />";
            }else
            {
            	strDatasetLM += "<set value='" + ((JSONObject)archJson.get(i)).get("lastMonthGross") + "' />";
            }
        }
        strDatasetTY += "</dataset>";
        strDatasetLYM += "</dataset>";
        strDatasetLM += "</dataset>";
        
        strChart += "<graph  yaxisname='建筑售电金额（元）' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80'  showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' showCaption='0' showSubCaption='0' numDivLines='4' showValues='0'>";
        strChart += strCategories + strDatasetTY + strDatasetLYM + strDatasetLM;
        strChart += "</graph>";
        
		return strChart;
	}
	
	public String getTableStr(String areaList, String archList, int year, int month) throws SQLException
	{
		String strTable = "";
		String archNameTR = "";
		String thisMonthTR = "";
		String lastYearMonthTR = "";
		String tongbiTR = "";
		String lastMonthTR = "";
		String huanbiTR = "";
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		JSONArray areaJson = new JSONArray();
		JSONArray archJson = new JSONArray();
		
		int lastYear = year;
		int lastMonth = month - 1;
		if(month == 1)
		{
			lastYear = year -1;
			lastMonth = 12;
		}
		
		if(!"".equals(areaList) && areaList != null)
		{
			areaList = areaList.substring(0,areaList.length()-1);
			areaJson = getAreaDatas(areaList, year, month);
		}
		if(!"".equals(archList) && archList != null)
		{
			archList = archList.substring(0,archList.length()-1);
			archJson = getArchDatas(archList, year, month);
		}
		
		strTable += "<table class='table table-striped table-bordered table-hover'>"; 
		archNameTR += "<tr><th>建筑名称</th>";
		thisMonthTR += "<tr><td>" + year + "年" + month + "月</td>";
		lastYearMonthTR += "<tr><td>" + String.valueOf((year-1)) + "年" + month + "月</td>";
		tongbiTR += "<tr><td>同比增幅</td>";
		if(month == 1)
		{
			lastMonthTR += "<tr><td>" + String.valueOf((year-1)) + "年12月</td>";
		}else
		{
			lastMonthTR += "<tr><td>" + year + "年" + String.valueOf((month-1)) + "月</td>";
		}
		huanbiTR += "<tr><td>环比增幅</td>";
		
		String thisMonthValue = "";
		String lastYearMonthValue = "";
		String lastMonthValue = "";
		for (int i = 0; i < areaJson.size(); i++)
        {
			archNameTR += "<th>"+((JSONObject)areaJson.get(i)).get("Area_Name")+"</th>";
			thisMonthValue = (String) ((JSONObject)areaJson.get(i)).get("thisMonthGross");
			thisMonthTR += "<td>"+thisMonthValue+"</td>";
			if("-".equals(thisMonthValue))
			{
				thisMonthValue = "0";
			}    				
				
			lastYearMonthValue = (String) ((JSONObject)areaJson.get(i)).get("lastYearMonthGross");
			lastYearMonthTR += "<td>"+lastYearMonthValue+"</td>";
				
			lastMonthValue = (String) ((JSONObject)areaJson.get(i)).get("lastMonthGross");
			lastMonthTR += "<td>"+lastMonthValue+"</td>";
				
			if("-".equals(lastYearMonthValue))
			{
				tongbiTR += "<td>-</td>";
			}else
			{
				if("0".equals(lastYearMonthValue))
				{
					tongbiTR += "<td>-</td>";
				}else
				{
					float per = Float.valueOf(thisMonthValue) - Float.valueOf(lastYearMonthValue);
					per = per / Math.abs(Float.valueOf(lastYearMonthValue));
					per = Float.parseFloat(df.format(per));
					String strPer = df.format(per);
					//per = (float) (Math.round(per * 100 * 100)/100.0);
					
					if (per >= 0)
					{
						// 红色的增幅
						tongbiTR += "<td><span style='color:Red'>↑" + String.valueOf(strPer) + "%</td>";
					}
					else
					{
						// Gree色的减幅
						tongbiTR += "<td><span style='color:Green'>↓" + String.valueOf(strPer) + "%</td>";
					}
				}
					
			}
				
			if("-".equals(lastMonthValue))
			{
				huanbiTR += "<td>-</td>";
			}else
			{
				if("0".equals(lastMonthValue))
				{
					huanbiTR += "<td>-</td>";
				}else
				{
					float per = Float.valueOf(thisMonthValue) - Float.valueOf(lastMonthValue);
					per = per / Math.abs(Float.valueOf(lastMonthValue));
 					//per = (float) (Math.round(per * 100 * 100)/100.0);
					per = Float.parseFloat(df.format(per));
					String strPer = df.format(per);
 					
 					 if (per >= 0)
 	                {
 	                    // 红色的增幅
 						huanbiTR += "<td><span style='color:Red'>↑" + String.valueOf(strPer) + "%</td>";
 	                }
 	                else
 	                {
 	                    // Gree色的减幅
 	                	huanbiTR += "<td><span style='color:Green'>↓" + String.valueOf(strPer) + "%</td>";
 	                }
				}
			}
        }
        for (int i = 0; i < archJson.size(); i++)
        {
        	archNameTR += "<th>"+((JSONObject)archJson.get(i)).get("Architecture_Name")+"</th>";
        	thisMonthValue = (String) ((JSONObject)archJson.get(i)).get("thisMonthGross");
			thisMonthTR += "<td>"+thisMonthValue+"</td>";
			if("-".equals(thisMonthValue))
			{
				thisMonthValue = "0";
			}    				
				
			lastYearMonthValue = (String) ((JSONObject)archJson.get(i)).get("lastYearMonthGross");
			lastYearMonthTR += "<td>"+lastYearMonthValue+"</td>";
				
			lastMonthValue = (String) ((JSONObject)archJson.get(i)).get("lastMonthGross");
			lastMonthTR += "<td>"+lastMonthValue+"</td>";
				
			if("-".equals(lastYearMonthValue))
			{
				tongbiTR += "<td>-</td>";
			}else
			{
				if("0".equals(lastYearMonthValue))
				{
					tongbiTR += "<td>-</td>";
				}else
				{
					float per = Float.valueOf(thisMonthValue) - Float.valueOf(lastYearMonthValue);
					per = per / Math.abs(Float.valueOf(lastYearMonthValue));
					//per = (float) (Math.round(per * 100 * 100)/100.0);
					per = Float.parseFloat(df.format(per));
					String strPer = df.format(per);
					
					if (per >= 0)
					{
						// 红色的增幅
						tongbiTR += "<td><span style='color:Red'>↑" + String.valueOf(strPer) + "%</td>";
					}
					else
					{
						// Gree色的减幅
						tongbiTR += "<td><span style='color:Green'>↓" + String.valueOf(strPer) + "%</td>";
					}
				}
					
			}
				
			if("-".equals(lastMonthValue))
			{
				huanbiTR += "<td>-</td>";
			}else
			{
				if("0".equals(lastMonthValue))
				{
					huanbiTR += "<td>-</td>";
				}else
				{
					float per = Float.valueOf(thisMonthValue) - Float.valueOf(lastMonthValue);
					per = per / Math.abs(Float.valueOf(lastMonthValue));
 					//per = (float) (Math.round(per * 100 * 100)/100.0);
					per = Float.parseFloat(df.format(per));
					String strPer = df.format(per);
 					
 					 if (per >= 0)
 	                {
 	                    // 红色的增幅
 						huanbiTR += "<td><span style='color:Red'>↑" + String.valueOf(strPer) + "%</td>";
 	                }
 	                else
 	                {
 	                    // Gree色的减幅
 	                	huanbiTR += "<td><span style='color:Green'>↓" + String.valueOf(strPer) + "%</td>";
 	                }
				}
			}
        }
        
        archNameTR += "</tr>";
        thisMonthTR += "</tr>";
		lastYearMonthTR += "</tr>";
		tongbiTR += "</tr>";
		lastMonthTR += "</tr>";
		huanbiTR += "</tr>";
		
		strTable += archNameTR + thisMonthTR + lastYearMonthTR + tongbiTR + lastMonthTR + huanbiTR;
		strTable += "</table>";
		
		return strTable;
	}
	
	
	/**
	 * 销售分析-按建筑比较-区域数据
	 * @param areaList
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public JSONArray getAreaDatas(String areaList, int year, int month) throws SQLException
	{
		JSONObject jo=new JSONObject();
		JSONArray json=new JSONArray();
		
		String sql = "";
		
		int lastYear = year;
		int lastMonth = month - 1;
		if(month == 1)
		{
			lastYear = year -1;
			lastMonth = 12;
		}
		
		sql = "select "+
				"T.*,LastYearMonthGross "+
			"from "+
				"(select "+
					"T.*,LastMonthGross "+
				"from "+
					"(select "+
						"T.*,thisMonthGross "+
					"from "+
						"(select "+
							"Area_ID,Area_Name "+
						"from "+
							"Area "+
						"where "+
							"Area_ID in("+areaList+") "+
						")T "+
						"left join "+
						"(select "+
							"Area_ID, sum(TheMoney) as thisMonthGross "+
						"from "+
							"(select "+
								"(select Area_ID from (ammeter)b where a.ammeter_ID=b.ammeter_ID) as Area_ID,TheMoney "+
							"from "+
								"(APSaleInfo)a "+
							"where "+
								"extract(year from BuyTime)="+ year +
								"and extract(month from BuyTime)="+month +
								"and  ammeter_ID in( select ammeter_ID from ammeter where Area_ID in ("+areaList+"))"+
							")T   "+
							"group by Area_ID"+
						")a on T.Area_ID=a.Area_ID"+
					")T "+
					"left join "+
					"(select "+
						"Area_ID, sum(TheMoney) as LastMonthGross "+
					"from "+
						"(select "+
							"(select Area_ID from (ammeter)b where a.ammeter_ID=b.ammeter_ID)as Area_ID,TheMoney "+
						"from "+
							"(APSaleInfo)a "+
						"where "+
							"extract(year from BuyTime)="+ lastYear +
							"and extract(month from BuyTime)="+lastMonth +
							"and  ammeter_ID in( select ammeter_ID from ammeter where Area_ID in ("+areaList+"))"+
						")T   "+
						"group by Area_ID"+
					")b on T.Area_ID=b.Area_ID"+
				")T "+
				"left join "+
				"(select "+
					"Area_ID, sum(TheMoney) as LastYearMonthGross "+
				"from "+
					"(select "+
						"(select Area_ID from (ammeter)b where a.ammeter_ID=b.ammeter_ID) as Area_ID,TheMoney "+
					"from "+
						"(APSaleInfo)a "+
					"where "+
						"extract(year from BuyTime)="+ String.valueOf((year-1)) +
						"and extract(month from BuyTime)="+month +
						"and  ammeter_ID in( select ammeter_ID from ammeter where Area_ID in ("+areaList+"))"+
					")T   "+
					"group by Area_ID"+
				")c  on T.Area_ID=c.Area_ID ";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while(rs.next())
			{
				jo=new JSONObject();
				jo.put("Area_Name", rs.getString("Area_Name"));
				//选中年月数据
				if(!"".equals(rs.getString("thisMonthGross")) && rs.getString("thisMonthGross") != null)
				{
					jo.put("thisMonthGross", rs.getString("thisMonthGross"));
				}else
				{
					jo.put("thisMonthGross", "-");
				}
				//选中年月去年年月数据
				if(!"".equals(rs.getString("lastYearMonthGross")) && rs.getString("lastYearMonthGross") != null)
				{
					jo.put("lastYearMonthGross", rs.getString("lastYearMonthGross"));
				}else
				{
					jo.put("lastYearMonthGross", "-");
				}
				//选中年月上个月数据
				if(!"".equals(rs.getString("lastMonthGross")) && rs.getString("lastMonthGross") != null)
				{
					jo.put("lastMonthGross", rs.getString("lastMonthGross"));
				}else
				{
					jo.put("lastMonthGross", "-");
				}
				json.add(jo);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while(rs.next())
//		{
//			jo=new JSONObject();
//			jo.put("Area_Name", rs.getString("Area_Name"));
//			//选中年月数据
//			if(!"".equals(rs.getString("thisMonthGross")) && rs.getString("thisMonthGross") != null)
//			{
//				jo.put("thisMonthGross", rs.getString("thisMonthGross"));
//			}else
//			{
//				jo.put("thisMonthGross", "-");
//			}
//			//选中年月去年年月数据
//			if(!"".equals(rs.getString("lastYearMonthGross")) && rs.getString("lastYearMonthGross") != null)
//			{
//				jo.put("lastYearMonthGross", rs.getString("lastYearMonthGross"));
//			}else
//			{
//				jo.put("lastYearMonthGross", "-");
//			}
//			//选中年月上个月数据
//			if(!"".equals(rs.getString("lastMonthGross")) && rs.getString("lastMonthGross") != null)
//			{
//				jo.put("lastMonthGross", rs.getString("lastMonthGross"));
//			}else
//			{
//				jo.put("lastMonthGross", "-");
//			}
//			json.add(jo);
//		}
		
		return json;	
	}
	
	/**
	 * 销售分析-按建筑比较-建筑数据
	 * @param areaList
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public JSONArray getArchDatas(String archList, int year, int month) throws SQLException
	{
		JSONObject jo=new JSONObject();
		JSONArray json=new JSONArray();
		
		String sql = "";
		
		int lastYear = year;
		int lastMonth = month - 1;
		if(month == 1)
		{
			lastYear = year -1;
			lastMonth = 12;
		}
		
		sql = "select T.*,LastYearMonthGross from "+
				"(select T.*,LastMonthGross from "+
				"(select T.*,thisMonthGross from "+
				"(select Architecture_ID,Architecture_Name from Architecture where Architecture_ID in("+archList+"))T left join "+
				"(select Architecture_ID, sum(TheMoney) as thisMonthGross from (select (select Architecture_ID from (ammeter)b where a.ammeter_ID=b.ammeter_ID) as Architecture_ID,TheMoney from (APSaleInfo)a where extract(year from BuyTime)="+ year +" and extract(month from BuyTime)="+ month +" and  ammeter_ID in( select ammeter_ID from ammeter where Architecture_ID in ("+archList+")))T   group by Architecture_ID)a on T.Architecture_ID=a.Architecture_ID)T left join "+
				"(select Architecture_ID, sum(TheMoney) as LastMonthGross from (select (select Architecture_ID from (ammeter)b where a.ammeter_ID=b.ammeter_ID) as Architecture_ID,TheMoney from (APSaleInfo)a where extract(year from BuyTime)="+ lastYear +" and extract(month from BuyTime)="+ lastMonth +" and  ammeter_ID in( select ammeter_ID from ammeter where Architecture_ID in ("+archList+")))T   group by Architecture_ID)b on T.Architecture_ID=b.Architecture_ID)T left join "+
				"(select Architecture_ID, sum(TheMoney) as LastYearMonthGross from (select (select Architecture_ID from (ammeter)b where a.ammeter_ID=b.ammeter_ID) as Architecture_ID,TheMoney from (APSaleInfo)a where extract(year from BuyTime)="+ String.valueOf((year-1)) +" and extract(month from BuyTime)="+ month +" and  ammeter_ID in( select ammeter_ID from ammeter where Architecture_ID in ("+archList+")))T   group by Architecture_ID)c  on T.Architecture_ID=c.Architecture_ID";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while(rs.next())
			{
				jo=new JSONObject();
				jo.put("Architecture_Name", rs.getString("Architecture_Name"));
				//选中年月数据
				if(!"".equals(rs.getString("thisMonthGross")) && rs.getString("thisMonthGross") != null)
				{
					jo.put("thisMonthGross", rs.getString("thisMonthGross"));
				}else
				{
					jo.put("thisMonthGross", "-");
				}
				//选中年月去年年月数据
				if(!"".equals(rs.getString("lastYearMonthGross")) && rs.getString("lastYearMonthGross") != null)
				{
					jo.put("lastYearMonthGross", rs.getString("lastYearMonthGross"));
				}else
				{
					jo.put("lastYearMonthGross", "-");
				}
				//选中年月上个月数据
				if(!"".equals(rs.getString("lastMonthGross")) && rs.getString("lastMonthGross") != null)
				{
					jo.put("lastMonthGross", rs.getString("lastMonthGross"));
				}else
				{
					jo.put("lastMonthGross", "-");
				}
				json.add(jo);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while(rs.next())
//		{
//			jo=new JSONObject();
//			jo.put("Architecture_Name", rs.getString("Architecture_Name"));
//			//选中年月数据
//			if(!"".equals(rs.getString("thisMonthGross")) && rs.getString("thisMonthGross") != null)
//			{
//				jo.put("thisMonthGross", rs.getString("thisMonthGross"));
//			}else
//			{
//				jo.put("thisMonthGross", "-");
//			}
//			//选中年月去年年月数据
//			if(!"".equals(rs.getString("lastYearMonthGross")) && rs.getString("lastYearMonthGross") != null)
//			{
//				jo.put("lastYearMonthGross", rs.getString("lastYearMonthGross"));
//			}else
//			{
//				jo.put("lastYearMonthGross", "-");
//			}
//			//选中年月上个月数据
//			if(!"".equals(rs.getString("lastMonthGross")) && rs.getString("lastMonthGross") != null)
//			{
//				jo.put("lastMonthGross", rs.getString("lastMonthGross"));
//			}else
//			{
//				jo.put("lastMonthGross", "-");
//			}
//			json.add(jo);
//		}

		
		return json;	
	}

}
