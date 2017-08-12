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

public class ArchAnalysis_MonthDao
{
	
	/**
	 * 销售分析-按建筑逐月获取FusionChartsXML
	 * @param selectInfo 选择信息 Style(all,area,arch)|ID
	 * @param queryInfo 查询信息 Style|Year
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
        
        JSONArray json = new JSONArray();
        ArrayList<String> liCol = new ArrayList<String>();
        
        if(queryStyle == 1)//电费类型
        {
        	json = getDatasByDF(selectID, selectStyle, queryYear);
        	//System.out.println("电费类型："+json);
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
        	json = getDatasBySD(selectID, selectStyle, queryYear);
        	liCol.add("系统售电");
        	liCol.add("一卡通");
        	liCol.add("统一月补");
        	liCol.add("临时调剂");  
        	liCol.add("微信支付"); 
        }
        
        String strCategories = "";
        String[] datasets = new String[liCol.size()];
        String strDataset = "";
        
        float zdl = 0;
        //拼category
        strCategories = "<categories>";
        for (int i = 1; i <= 12; i++)
        {
            strCategories += "<category name='" + i + "月' />";
        }
        strCategories += "</categories>";
        int flag = 0;
        //拼dataset
        for(int i = 0; i < liCol.size(); i++)
        {
        	datasets[i] = "<dataset seriesName='" + liCol.get(i) + "'  showValues='0'>";
        	
			for (int k = 1; k <= 12; k++)
			{
				flag = 0;
				for (int j = 0; j < json.size(); j++)
				{
        			if((liCol.get(i)).equals((String)((JSONObject)json.get(j)).get("Name")) && k == Integer.parseInt((String) ((JSONObject)json.get(j)).get("SelectMonth")))
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
        int theMonth = cal.get(Calendar.MONTH) + 1; //用当前月作为平均总月数
        String zdlstr = String.valueOf((zdl / theMonth));
        trendline = "<trendlines>";
        trendline += "<line startValue='"+zdlstr+"' color='91C728' displayValue='日均线' showOnTop='1'/>";
        trendline += "</trendlines>";
        
        strChart += "<graph  yAxisName='售电量（千瓦时）' caption='按建筑逐月' numDivLines='3' showValues='0' formatNumberScale='0'>";
        strChart += strCategories;
        strChart += strDataset;
        strChart += trendline;
        strChart += "</graph>";
		//System.out.println(strChart);
		return strChart;
		
	}
	
	/**
	 * 销售分析-按建筑逐月获取同比环比TableStr
	 * @param selectInfo 选择信息 Style(All,Area,Arch)|ID|Name
	 * @param queryInfo 查询信息 Style|Year
	 * @return
	 * @throws SQLException 
	 */
	public String getTableStr(String selectInfo, String queryInfo) throws SQLException
	{
		String strTable = "";
		String date = "";
		String thisYear = "";
		String lastYear = "";
		String tongbi = "";
		String huanbi = "";
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		// / 选中对象年的已经使用定额
		float[] fM1 = new float[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		// / 选中对象年的前一年的已经使用定额
		float[] fM2 = new float[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		
		
		String[] selectList = selectInfo.split("\\|");
		String selectStyle = selectList[0];
		String selectID = selectList[1];

        String[] queryList = queryInfo.split("\\|");
        int queryStyle = Integer.parseInt(queryList[0]);
        int queryYear = Integer.parseInt(queryList[1]);
        
        JSONArray json = new JSONArray();
        json = getCompareDatas(selectID, selectStyle, queryYear);
		
		strTable += "<table class='table table-striped table-bordered table-hover'>"; 
		date += "<tr><th>日期</th>";
		thisYear += "<tr><td>" + queryYear + "年</td>";
		lastYear += "<tr><td>" + String.valueOf((queryYear-1)) + "年</td>";
		tongbi += "<tr><td>同比增幅</td>";
		huanbi += "<tr><td>环比增幅</td>";
		int flag = 0;
		String thisYearValue = "";
		String lastYearValue = "";
     	for(int i=1;i<=12;i++)
     	{
     		flag = 0;
     		date += "<th>"+i+"月</th>";
     		for(int j = 0; j<json.size(); j++)
     		{
     			if(i == Integer.parseInt((String) ((JSONObject)json.get(j)).get("SelectMonth")))
         		{
     				flag = 1;
     				
     				thisYearValue = (String) ((JSONObject)json.get(j)).get("thisYear");
     				thisYear += "<td>"+thisYearValue+"</td>";
     				if("-".equals(thisYearValue))
     				{
     					fM1[i-1] = 0;
     				}else
     				{
     					fM1[i-1] = Float.valueOf(thisYearValue);
     				}
     				
     				lastYearValue = (String) ((JSONObject)json.get(j)).get("lastYear");
     				lastYear += "<td>"+lastYearValue+"</td>";
     				
     				if("-".equals(lastYearValue))
     				{
     					fM2[i-1] = 0;
     				}else
     				{
     					fM2[i-1] = Float.valueOf(lastYearValue);
     				}
     				
     				break;
         		}
     		}
     		if(flag == 0)
     		{
     			fM1[i-1] = 0;
     			fM2[i-1] = 0;
     			thisYear += "<td>-</td>";
     			lastYear += "<td>-</td>";
     		}
     	}
     	// 同比增幅
        for (int i = 0; i < 12; i++)
        {
            float fResult;
            String strResult;
            if (fM1[i] != 0 && fM2[i] != 0)
            {
                fResult = fM1[i] - fM2[i];
                fResult = fResult / Math.abs(fM2[i]);
                //fResult = Math.round(fResult * 100 * 100)/100.0;
                strResult = df.format(fResult);
                fResult = Float.parseFloat(df.format(fResult));
                if (fResult >= 0)
                {
                    // 红色的增幅
                	tongbi += "<td><span style='color:Red'>↑" + String.valueOf(strResult) + "%</td>";
                }
                else
                {
                    // Gree色的减幅
                	tongbi += "<td><span style='color:Green'>↓" + String.valueOf(strResult) + "%</td>";
                }
            }
            else
            {
                // “-”无可比性
            	tongbi += "<td>-</td>";
            }
        }
        
        // 环比增幅
        // 选中对象年1月份和前年12月份的环比
        if (fM1[0] != 0 && fM2[11] != 0)
        {
            float fResult;
            String strResult;
            fResult = fM1[0] - fM2[11];
            fResult = fResult / Math.abs(fM2[11]);
            //fResult = Math.round(fResult * 100 * 100)/100.0;
            strResult = df.format(fResult);
            fResult = Float.parseFloat(df.format(fResult));
            if (fResult >= 0)
            {
                // 红色的增幅
            	huanbi += "<td><span style='color:Red'>↑" + String.valueOf(strResult) + "%</td>";
            }
            else
            {
                // Gree色的减幅
            	huanbi += "<td><span style='color:Green'>↓" + String.valueOf(strResult) + "%</td>";
            }
        }
        else
        {
            // “-”无可比性
        	huanbi += "<td>-</td>";
        }
        for (int i = 1; i < 12; i++)
        {
            if (fM1[i] != 0 && fM1[i - 1] != 0)
            {
                float fResult;
                String strResult;
                fResult = fM1[i] - fM1[i - 1];
                fResult = fResult / Math.abs(fM1[i - 1]);
                //fResult = Math.round(fResult * 100 * 100)/100.0;
                strResult = df.format(fResult);
                fResult = Float.parseFloat(df.format(fResult));
                if (fResult >= 0)
                {
                    // 红色的增幅
                	huanbi += "<td><span style='color:Red'>↑" + String.valueOf(strResult) + "%</td>";
                }
                else
                {
                    // Gree色的减幅
                	huanbi += "<td><span style='color:Green'>↓" + String.valueOf(strResult) + "%</td>";
                }
            }
            else
            {
                // “-”无可比性
            	huanbi += "<td>-</td>";
            }
        }
     	date += "</tr>";
     	thisYear += "</tr>";
     	lastYear += "</tr>";
		tongbi += "</tr>";
		huanbi += "</tr>";
		
		strTable += date + thisYear + lastYear + tongbi + huanbi;
		strTable += "</table>";

		return strTable;
		
	}
	
	/**
	 * 根据电费类型查询用量数据
	 * @param ID
	 * @param style 建筑区域类型或者全部
	 * @param year 年份
	 * @return
	 * @throws SQLException 
	 */
	public JSONArray getDatasByDF(String ID, String style, int year) throws SQLException
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
				"extract(month from BuyTime) as SelectMonth,"+ 
				"Price_Name as Name "+ 
			"from "+ 
				"(APSaleInfo)a,"+ 
				"(ammeter)b,"+ 
				"(Price)c "+ 
			"where "+ 
				"a.ammeter_ID = b.ammeter_ID "+ 
				"and b.Price_ID=c.Price_ID "+ 
				"and c.Price_Style='E' "+ 
				"and extract(year from BuyTime) ="+ year + strWhere +
			" group by extract(month from BuyTime),Price_Name";
		
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
				jo.put("SelectMonth", rs.getString("SelectMonth"));
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
//			jo.put("SelectMonth", rs.getString("SelectMonth"));
//			jo.put("Name", rs.getString("Name"));
//			json.add(jo);
//		}
		
		return json;		
	}
	
	/**
	 * 根据售电类型查询用量数据
	 * @param style 建筑区域类型或者全部
	 * @param year 年份
	 * @return
	 * @throws SQLException 
	 */
	public JSONArray getDatasBySD(String ID, String style, int year) throws SQLException
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
				"extract(month from BuyTime) as SelectMonth,"+
				"Kind as Name "+
			"from "+
				"APSaleInfo "+
			"where "+
				"extract(year from BuyTime)="+ year + strWhere +
			" group by extract(month from BuyTime),kind";
				
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
				jo.put("SelectMonth", rs.getString("SelectMonth"));
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
//			jo.put("SelectMonth", rs.getString("SelectMonth"));
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
	 * @return
	 * @throws SQLException
	 */
	public JSONArray getCompareDatas(String ID, String style, int year) throws SQLException
	{
		JSONObject jo=new JSONObject();
		JSONArray json=new JSONArray();
		
		String sql = "";
		String strWhere = "";
		
		int lastYear = year - 1;
		
		if ("area".equals(style))
		{
			strWhere = " and  ammeter_ID in(select ammeter_ID from ammeter where Area_ID=" + ID +")";
		}else if ("arch".equals(style))
		{
			strWhere = " and ammeter_ID in(select ammeter_ID from ammeter where Architecture_ID=" + ID +")";
		}
		sql = "select "+
				"thisYear,lastYear,T1.SelectMonth as SelectMonth "+
			"from "+
				"(select "+
					"sum(TheMoney) as thisYear,extract(month from BuyTime) as SelectMonth "+
				"from "+
					"APSaleInfo "+
				"where "+
					"extract(year from BuyTime)="+ year + strWhere +
				" group by extract(month from BuyTime) "+
				")T1 "+
				"left join "+
				"(select "+
					"sum(TheMoney) as lastYear,extract(month from BuyTime) as SelectMonth "+
				"from "+
					"APSaleInfo "+
				"where "+
					"extract(year from BuyTime)="+ lastYear + strWhere +
				" group by extract(month from BuyTime) "+
				")T2 on T1.SelectMonth=T2.SelectMonth ";
				
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
				jo.put("SelectMonth", rs.getString("SelectMonth"));
				if(!"".equals(rs.getString("thisYear")) && rs.getString("thisYear") != null)
				{
					jo.put("thisYear", rs.getString("thisYear"));
				}else
				{
					jo.put("thisYear", "-");
				}
				
				if(!"".equals(rs.getString("lastYear")) && rs.getString("lastYear") != null)
				{
					jo.put("lastYear", rs.getString("lastYear"));
				}else
				{
					jo.put("lastYear", "-");
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
//			jo.put("SelectMonth", rs.getString("SelectMonth"));
//			if(!"".equals(rs.getString("thisYear")) && rs.getString("thisYear") != null)
//			{
//				jo.put("thisYear", rs.getString("thisYear"));
//			}else
//			{
//				jo.put("thisYear", "-");
//			}
//			
//			if(!"".equals(rs.getString("lastYear")) && rs.getString("lastYear") != null)
//			{
//				jo.put("lastYear", rs.getString("lastYear"));
//			}else
//			{
//				jo.put("lastYear", "-");
//			}
//			
//			json.add(jo);
//		}

		
		return json;
	}

}
