package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.util.ConnDB;

public class ArchAnalysis_DayDao
{
	/**
	 * 销售分析-按建筑逐日获取FusionChartsXML
	 * @param selectInfo 选择信息 Style(all,area,arch)|ID
	 * @param queryInfo 查询信息 Style|Year|Month
	 * @return
	 * @throws SQLException 
	 */
	public String getFusionChartsStr(String selectInfo, String queryInfo) throws SQLException
	{
		String strChart = "";
		
		String[] selectList = selectInfo.split("\\|");
		String selectStyle = selectList[0];
		String selectID = selectList[1];

        String[] queryList = queryInfo.split("\\|");
        int queryStyle = Integer.parseInt(queryList[0]);
        int queryYear = Integer.parseInt(queryList[1]);
        int queryMonth = Integer.parseInt(queryList[2]);
        
        JSONArray json = new JSONArray();
        ArrayList<String> liCol = new ArrayList<String>();
        
        if(queryStyle == 1)//电费类型
        {
        	json = getDatesByDF(selectID, selectStyle, queryYear, queryMonth);
        	String sql = "select Price_ID,Price_Num,Price_Name from Price where Price_Style='E'";
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
            		String strCol = rs.getString("Price_Name");
            		liCol.add(strCol);
            	}
    		} catch (Exception e)
    		{
    			e.printStackTrace();
    		}finally{
    			ConnDB.release(conn, ps, rs);
    		}
//        	while(rs.next())
//        	{
//        		String strCol = rs.getString("Price_Name");
//        		liCol.add(strCol);
//        	}
  
        }else if(queryStyle == 2)//售电类型
        {
        	json = getDatesBySD(selectID, selectStyle, queryYear, queryMonth);
        	liCol.add("系统售电");
        	liCol.add("一卡通");
        	liCol.add("统一月补");
        	liCol.add("临时调剂");    
        	liCol.add("微信支付");
        }
        		
        
        
        int totalDays = countDays(queryYear,queryMonth);
        
        String strCategories = "";
        String[] datasets = new String[liCol.size()];
        String strDataset = "";
        
        float zdl = 0;
        //拼category
        strCategories = "<categories>";
        for (int i = 1; i <= totalDays; i++)
        {
            strCategories += "<category name='" + i + "日' />";
        }
        strCategories += "</categories>";
        int flag = 0;
        //拼dataset
        for(int i = 0; i < liCol.size(); i++)
        {
        	datasets[i] = "<dataset seriesName='" + liCol.get(i) + "'  showValues='0'>";
        	
			for (int k = 1; k <= totalDays; k++)
			{
				flag = 0;
				for (int j = 0; j < json.size(); j++)
				{
        			if((liCol.get(i)).equals((String)((JSONObject)json.get(j)).get("Name")) && k == Integer.parseInt((String) ((JSONObject)json.get(j)).get("SelectDay")))
        			{
        				flag = 1;
        				datasets[i] += "<set value='" + ((JSONObject)json.get(j)).get("ZGross") + "' />";	
        				zdl += Float.parseFloat((String) ((JSONObject)json.get(j)).get("ZGross"));
        				break;
        			}
        		}
				if(flag == 0)
				{
					datasets[i] += "<set value='0' />";	
				}
        	}       	
        	datasets[i] += "</dataset>";
        	
        	strDataset += datasets[i];
        }        
        //拼日均线
        String trendline = "";
        Calendar cal = Calendar.getInstance();
        int theDay = cal.get(Calendar.DATE);
        int theMonth = cal.get(Calendar.MONTH) + 1;        
        if(queryMonth == theMonth)//当月的时候使用现在的日作为天数来求出日平均
        {
        	totalDays = theDay;
        }
        String zdlstr = String.valueOf((zdl / totalDays));
        trendline = "<trendlines>";
        trendline += "<line startValue='"+zdlstr+"' color='91C728' displayValue='日均线' showOnTop='1'/>";
        trendline += "</trendlines>";
        
        strChart += "<graph  yAxisName='售电量（千瓦时）' caption='按建筑逐日' numDivLines='3' showValues='0' formatNumberScale='0'>";
        strChart += strCategories;
        strChart += strDataset;
        strChart += trendline;
        strChart += "</graph>";
		
		return strChart;
		
	}
	
	/**
	 * 销售分析-按建筑逐日获取同比环比TableStr
	 * @param selectInfo 选择信息 Style(All,Area,Arch)|ID|Name
	 * @param queryInfo 查询信息 Style|Year|Month
	 * @return
	 * @throws SQLException 
	 */
	public String getTableStr(String selectInfo, String queryInfo) throws SQLException
	{
		String strTable = "";
		String date = "";
		String thisMonth = "";
		String lastYaerMonth = "";
		String tongbi = "";
		String lastMonth = "";
		String huanbi = "";
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		String[] selectList = selectInfo.split("\\|");
		String selectStyle = selectList[0];
		String selectID = selectList[1];

        String[] queryList = queryInfo.split("\\|");
        int queryStyle = Integer.parseInt(queryList[0]);
        int queryYear = Integer.parseInt(queryList[1]);
        int queryMonth = Integer.parseInt(queryList[2]);
        
        JSONArray json = new JSONArray();
        json = getCompareDates(selectID, selectStyle, queryYear, queryMonth);
        //选中月的天数
		int totalDays = countDays(queryYear,queryMonth);
		
		strTable += "<table class='table table-striped table-bordered table-hover'>"; 
		date += "<tr><th>日期</th>";
		thisMonth += "<tr><td>" + queryYear + "年" + queryMonth + "月</td>";
		lastYaerMonth += "<tr><td>" + String.valueOf((queryYear-1)) + "年" + queryMonth + "月</td>";
		tongbi += "<tr><td>同比增幅</td>";
		if(queryMonth == 1)
		{
			lastMonth += "<tr><td>" + String.valueOf((queryYear-1)) + "年12月</td>";
		}else
		{
			lastMonth += "<tr><td>" + queryYear + "年" + String.valueOf((queryMonth-1)) + "月</td>";
		}
		
		huanbi += "<tr><td>环比增幅</td>";
		int flag = 0;
		String thisMonthValue = "";
		String lastYaerMonthValue = "";
		String lastMonthValue = "";
     	for(int i=1;i<=totalDays;i++)
     	{
     		flag = 0;
     		date += "<th>"+i+"日</th>";
     		for(int j = 0; j<json.size(); j++)
     		{
     			if(i == Integer.parseInt((String) ((JSONObject)json.get(j)).get("SelectDay")))
         		{
     				flag = 1;
     				
     				thisMonthValue = (String) ((JSONObject)json.get(j)).get("thisMonth");
     				thisMonth += "<td>"+thisMonthValue+"</td>";
     				if("-".equals(thisMonthValue))
     				{
     					thisMonthValue = "0";
     				}    				
     				
     				lastYaerMonthValue = (String) ((JSONObject)json.get(j)).get("LastYearMonth");
     				lastYaerMonth += "<td>"+lastYaerMonthValue+"</td>";
     				
     				lastMonthValue = (String) ((JSONObject)json.get(j)).get("LastMonth");
     				lastMonth += "<td>"+lastMonthValue+"</td>";
     				
     				if("-".equals(lastYaerMonthValue))
     				{
     					tongbi += "<td>-</td>";
     				}else
     				{
     					if("0".equals(lastYaerMonthValue))
     					{
     						tongbi += "<td>-</td>";
     					}else
     					{
     						float per = Float.valueOf(thisMonthValue) - Float.valueOf(lastYaerMonthValue);
     						per = per / Math.abs(Float.valueOf(lastYaerMonthValue));
     						//per = (float) (Math.round(per * 100 * 100)/100.0);
     						String strPer = df.format(per);
     						per = Float.parseFloat(df.format(per));
     					
     						if (per >= 0)
     						{
     							// 红色的增幅
     							tongbi += "<td><span style='color:Red'>↑" + String.valueOf(strPer) + "%</td>";
     						}
     						else
     						{
     							// Gree色的减幅
     							tongbi += "<td><span style='color:Green'>↓" + String.valueOf(strPer) + "%</td>";
     						}
     					}
     					
     				}
     				
     				if("-".equals(lastMonthValue))
     				{
     					huanbi += "<td>-</td>";
     				}else
     				{
     					if("0".equals(lastMonthValue))
     					{
     						huanbi += "<td>-</td>";
     					}else
     					{
     						float per = Float.valueOf(thisMonthValue) - Float.valueOf(lastMonthValue);
         					per = per / Math.abs(Float.valueOf(lastMonthValue));
         					//per = (float) (Math.round(per * 100 * 100)/100.0);
         					String strPer = df.format(per);
         					per = Float.parseFloat(df.format(per));
         					
         					 if (per >= 0)
         	                {
         	                    // 红色的增幅
         						huanbi += "<td><span style='color:Red'>↑" + String.valueOf(strPer) + "%</td>";
         	                }
         	                else
         	                {
         	                    // Gree色的减幅
         	                	huanbi += "<td><span style='color:Green'>↓" + String.valueOf(strPer) + "%</td>";
         	                }
     					}
     				}
     				break;
         		}
     		}
     		if(flag == 0)
     		{
     			thisMonth += "<td>-</td>";
 				lastYaerMonth += "<td>-</td>";
 				tongbi += "<td>-</td>";
 				lastMonth += "<td>-</td>";
 				huanbi += "<td>-</td>";
     		}
     	}
     	date += "</tr>";
		thisMonth += "</tr>";
		lastYaerMonth += "</tr>";
		tongbi += "</tr>";
		lastMonth += "</tr>";
		huanbi += "</tr>";
		
		strTable += date + thisMonth + lastYaerMonth + tongbi + lastMonth + huanbi;
		strTable += "</table>";

		return strTable;
		
	}
	
	/**
	 * 计算某个月的天数
	 * @param yesr
	 * @param month
	 * @return
	 */
	public int countDays(int yesr, int month)
	{
		int totalDays = 31;
		
		switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
        	totalDays = 31;
            break;
            //对于2月份需要判断是否为闰年
        case 2:
            if ((yesr % 4 == 0 && yesr % 100 != 0) || (yesr % 400 == 0)) {
            	totalDays = 29;
                break;
            } else {
            	totalDays = 28;
                break;
            }
        case 4:
        case 6:
        case 9:
        case 11:
        	totalDays = 30;
            break;
        default:
            break;
		}
		
		return totalDays;		
	}
	
	/**
	 * 根据电费类型查询用量数据
	 * @param ID
	 * @param style 建筑区域类型或者全部
	 * @param year 年份
	 * @param month 月份
	 * @return
	 * @throws SQLException 
	 */
	public JSONArray getDatesByDF(String ID, String style, int year, int month) throws SQLException
	{
		JSONObject jo=new JSONObject();
		JSONArray json=new JSONArray();
		
		String sql = "";
		String strWhere = "";
		
		if ("area".equals(style))
		{
			strWhere = " and b.area_id = " + ID;
		}
		if ("arch".equals(style))
		{
			strWhere = " and b.Architecture_ID = " + ID;
		}
		sql = "select "+ 
				"sum(TheMoney) as ZGross,"+ 
				"extract(day from BuyTime) as SelectDay,"+ 
				"Price_Name as Name "+ 
			"from "+ 
				"(APSaleInfo)a,"+ 
				"(ammeter)b,"+ 
				"(Price)c "+ 
			"where "+ 
				"a.ammeter_ID = b.ammeter_ID "+ 
				"and b.Price_ID=c.Price_ID "+ 
				"and c.Price_Style='E' "+ 
				"and extract(year from BuyTime) ="+ year +
				" and extract(month from BuyTime)="+month + strWhere +
			" group by extract(day from BuyTime),Price_Name";
		
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
				jo.put("ZGross", rs.getString("ZGross"));
				jo.put("SelectDay", rs.getString("SelectDay"));
				jo.put("Name", rs.getString("Name"));
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
//			jo.put("ZGross", rs.getString("ZGross"));
//			jo.put("SelectDay", rs.getString("SelectDay"));
//			jo.put("Name", rs.getString("Name"));
//			json.add(jo);
//		}
		
		return json;		
	}
	
	/**
	 * 根据售电类型查询用量数据
	 * @param style 建筑区域类型或者全部
	 * @param year 年份
	 * @param month 月份
	 * @return
	 * @throws SQLException 
	 */
	public JSONArray getDatesBySD(String ID, String style, int year, int month) throws SQLException
	{
		JSONObject jo=new JSONObject();
		JSONArray json=new JSONArray();
		
		String sql = "";
		String strWhere = "";
		
		if ("area".equals(style))
		{
			strWhere = " and  ammeter_ID in(select ammeter_ID from ammeter where Area_ID=" + ID +")";
		}
		if ("arch".equals(style))
		{
			strWhere = " and ammeter_ID in(select ammeter_ID from ammeter where Architecture_ID=" + ID +")";
		}
		sql = "select "+
				"sum(TheMoney) as ZGross,"+
				"extract(day from BuyTime)as SelectDay,"+
				"Kind as Name "+
			"from "+
				"APSaleInfo "+
			"where "+
				"extract(year from BuyTime)="+ year +
				" and extract(month from BuyTime)="+month + strWhere +
			" group by extract(day from BuyTime),kind";
				
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
				String Name = rs.getString("Name");
				jo.put("ZGross", rs.getString("ZGross"));
				jo.put("SelectDay", rs.getString("SelectDay"));
				if("0".equals(Name))
				{
					Name = "系统售电";
				}else if("1".equals(Name))
				{
					Name = "一卡通";
				}else if("2".equals(Name))
				{
					Name = "统一月补";
				}else if("3".equals(Name))
				{
					Name = "临时调剂";
				}else if("6".equals(Name))
				{
					Name = "微信支付";
				}
				jo.put("Name", Name);
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
//			String Name = rs.getString("Name");
//			jo.put("ZGross", rs.getString("ZGross"));
//			jo.put("SelectDay", rs.getString("SelectDay"));
//			if("0".equals(Name))
//			{
//				Name = "系统售电";
//			}else if("1".equals(Name))
//			{
//				Name = "一卡通";
//			}else if("2".equals(Name))
//			{
//				Name = "统一月补";
//			}else if("3".equals(Name))
//			{
//				Name = "临时调剂";
//			}
//			jo.put("Name", Name);
//			json.add(jo);
//		}
		
		
		return json;		
	}
	
	/**
	 * 获取同比环比数据
	 * @param ID 建筑或区域ID
	 * @param style 建筑或区域类型
	 * @param year 选中年
	 * @param month 选中月
	 * @return
	 * @throws SQLException
	 */
	public JSONArray getCompareDates(String ID, String style, int year, int month) throws SQLException
	{
		JSONObject jo=new JSONObject();
		JSONArray json=new JSONArray();
		
		String sql = "";
		String strWhere = "";
		
		int lastYear = year;
		int lastMonth = month - 1;
		if(month == 1)
		{
			lastYear = year -1;
			lastMonth = 12;
		}
		
		if ("area".equals(style))
		{
			strWhere = " and  ammeter_ID in(select ammeter_ID from ammeter where Area_ID=" + ID +")";
		}
		if ("arch".equals(style))
		{
			strWhere = " and ammeter_ID in(select ammeter_ID from ammeter where Architecture_ID=" + ID +")";
		}
		sql = "select "+
				"thisMonth,LastYearMonth,LastMonth,T1.selectday as SelectDay "+
			"from "+
				"(select "+
					"sum(TheMoney) as thisMonth,extract(day from BuyTime) as SelectDay "+
				"from "+
					"APSaleInfo "+
				"where "+
					"extract(year from BuyTime)="+ year +
					" and extract(month from BuyTime)="+month + strWhere +
				" group by extract(day from BuyTime) "+
				")T1 "+
				"left join "+
				"(select "+
					"sum(TheMoney) as LastYearMonth,extract(day from BuyTime) as SelectDay "+
				"from "+
					"APSaleInfo "+
				"where "+
					"extract(year from BuyTime)="+ String.valueOf((year-1)) +
					" and extract(month from BuyTime)="+month + strWhere +
				" group by extract(day from BuyTime) "+
				")T2 on T1.SelectDay=T2.SelectDay "+
				"left join "+
				"(select "+
					"sum(TheMoney) as LastMonth,extract(day from BuyTime) as SelectDay "+	
				"from "+
					"APSaleInfo "+
				"where "+
					"extract(year from BuyTime)="+ lastYear +
					" and extract(month from BuyTime)="+lastMonth + strWhere +
				" group by extract(day from BuyTime) "+
				")T3 "+
			"on T1.SelectDay=T3.SelectDay ";
				
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
				jo.put("SelectDay", rs.getString("SelectDay"));
				if(!"".equals(rs.getString("thisMonth")) && rs.getString("thisMonth") != null)
				{
					jo.put("thisMonth", rs.getString("thisMonth"));
				}else
				{
					jo.put("thisMonth", "-");
				}
				
				if(!"".equals(rs.getString("LastYearMonth")) && rs.getString("LastYearMonth") != null)
				{
					jo.put("LastYearMonth", rs.getString("LastYearMonth"));
				}else
				{
					jo.put("LastYearMonth", "-");
				}
				
				if(!"".equals(rs.getString("LastMonth")) && rs.getString("LastMonth") != null)
				{
					jo.put("LastMonth", rs.getString("LastMonth"));
				}else
				{
					jo.put("LastMonth", "-");
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
//			jo.put("SelectDay", rs.getString("SelectDay"));
//			if(!"".equals(rs.getString("thisMonth")) && rs.getString("thisMonth") != null)
//			{
//				jo.put("thisMonth", rs.getString("thisMonth"));
//			}else
//			{
//				jo.put("thisMonth", "-");
//			}
//			
//			if(!"".equals(rs.getString("LastYearMonth")) && rs.getString("LastYearMonth") != null)
//			{
//				jo.put("LastYearMonth", rs.getString("LastYearMonth"));
//			}else
//			{
//				jo.put("LastYearMonth", "-");
//			}
//			
//			if(!"".equals(rs.getString("LastMonth")) && rs.getString("LastMonth") != null)
//			{
//				jo.put("LastMonth", rs.getString("LastMonth"));
//			}else
//			{
//				jo.put("LastMonth", "-");
//			}
//			
//			json.add(jo);
//		}
		
		return json;
	}
		
	

}
