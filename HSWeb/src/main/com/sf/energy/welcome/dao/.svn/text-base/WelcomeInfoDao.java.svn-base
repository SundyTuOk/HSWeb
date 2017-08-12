package com.sf.energy.welcome.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sf.energy.util.ConnDB;
import com.sf.energy.util.ResultDataSet;
import com.sf.energy.welcome.Model.BaoJingInfoModel;

public class WelcomeInfoDao
{
	public Map<String,Float> getAmMeterTotalDatas(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Float> list=new HashMap<String,Float>();
		Calendar c = Calendar.getInstance();            //得到当前日期和时间
		c.add(Calendar.DATE, -1);
        Date Startdate=c.getTime(); 
        String time=df.format(Startdate); 
        
	        String sqlAm="select ammeter_ID from ammeter where PARTMENT_ID=1 and ISCOUNTMETER=1 and ISIMPORTANTUSER=1" ;
//			String sqlDatas="select to_char(valuetime,'yyyy-MM-dd HH24:mi:ss') valuetime,ZVALUEZY  from ZamDatas32715 where valuetime > to_date('2015-10-29','yyyy-mm-dd')";
			Connection conn=null;
	        PreparedStatement ps=null;
	        ResultSet rs=null;
	        try
			{
				conn=ConnDB.getConnection();
				ps=conn.prepareStatement(sqlAm);
				rs=ps.executeQuery();
				while(rs.next()){
					
					
					
//					list.put(rs.getString("valuetime"), rs.getFloat("ZVALUEZY"));
					String ammeterID=rs.getString("ammeter_ID");
					String sqlDatas="select * from T_MINUTEAMZB where SelectTime > to_date('"+time+"','yyyy-mm-dd') and ammeter_ID="+ammeterID;
					PreparedStatement ps1=null;
					ResultSet rs1=null;
					try{
						ps1=conn.prepareStatement(sqlDatas);
						rs1=ps1.executeQuery();
						while(rs1.next()){
							if (list.containsKey(rs1.getString("SelectTime")))
							{
	
								float count = list.get(rs1.getString("SelectTime")) + rs1.getFloat("ZGross");
	
								list.put(rs1.getString("SelectTime"), count);
	
							} else
							{
	
								list.put(rs1.getString("SelectTime"), rs1.getFloat("ZGross"));
	
							}
//							list.put(rs1.getString("SelectTime"), rs1.getFloat("ZGross")+list.get(rs1.getString("SelectTime")));
						}
					}catch(SQLException e){
						e.printStackTrace();
					}finally{
						ConnDB.release(ps1, rs1);
					}
				}
				
				
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				ConnDB.release(conn,ps, rs);
			}
		
		return list;
	}
	public List<BaoJingInfoModel> getWarningInfo(){
		List<BaoJingInfoModel> list=new ArrayList<BaoJingInfoModel>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();            //得到当前日期和时间
		c.add(Calendar.DATE, -1);
        Date Startdate=c.getTime(); 
        String time=df.format(Startdate); 
        String sql="select BAOJINGINFO_ID,BAOJINGINFO_TITLE , MODULE_NUM,to_char(BAOJINGINFO_TIME,'yyyy-mm-dd hh24:mi:ss') BAOJINGINFO_TIME from BAOJINGINFO where BAOJINGINFO_TIME >to_date('"+time+"','yyyy-mm-dd') order by BAOJINGINFO_TIME desc" ;
		//System.out.println(sql);
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				BaoJingInfoModel model=new BaoJingInfoModel();
				model.setBaoJingInfo_ID(rs.getInt("BAOJINGINFO_ID"));
				model.setBaoJingInfo_content(rs.getString("BAOJINGINFO_TITLE"));
				model.setBaoJingInfo_MoudleNum(rs.getString("MODULE_NUM"));
				model.setBaoJingInfo_Time(rs.getString("BAOJINGINFO_TIME"));
				list.add(model);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps, rs);
		}
        return list;
	}
	
	public List<BaoJingInfoModel> getWarningInfo(int count){
		List<BaoJingInfoModel> list=new ArrayList<BaoJingInfoModel>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();            //得到当前日期和时间
		c.add(Calendar.DATE, -1);
        Date Startdate=c.getTime(); 
        String time=df.format(Startdate); 
        String sql="SELECT t.* ,ROWNUM ro FROM (select BAOJINGINFO_ID,BAOJINGINFO_TITLE , "
        		+ "MODULE_NUM,to_char(BAOJINGINFO_TIME,'yyyy-mm-dd hh24:mi:ss') BAOJINGINFO_TIME "
        		+ "from BAOJINGINFO where BAOJINGINFO_TIME >to_date('"+time+"','yyyy-mm-dd') "
        				+ "order by BAOJINGINFO_TIME desc) t  WHERE ROWNUM <="+count ;
		//System.out.println(sql);
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				BaoJingInfoModel model=new BaoJingInfoModel();
				model.setBaoJingInfo_ID(rs.getInt("BAOJINGINFO_ID"));
				model.setBaoJingInfo_content(rs.getString("BAOJINGINFO_TITLE"));
				model.setBaoJingInfo_MoudleNum(rs.getString("MODULE_NUM"));
				model.setBaoJingInfo_Time(rs.getString("BAOJINGINFO_TIME"));
				list.add(model);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps, rs);
		}
        return list;
	}
	
	public Map<String,Float> getWaterMeterTotalDatas(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Float> list=new HashMap<String,Float>();
		Calendar c = Calendar.getInstance();            //得到当前日期和时间
		c.add(Calendar.DATE, -1);
        Date Startdate=c.getTime(); 
        String time=df.format(Startdate); 
        
        
	        String sqlAm="select watermeter_ID from WaterMeter where PARTMENT=1 and ISCOUNTMETER=1" ;
			Connection conn=null;
	        PreparedStatement ps=null;
	        ResultSet rs=null;
	        try
			{
				conn=ConnDB.getConnection();
				ps=conn.prepareStatement(sqlAm);
				rs=ps.executeQuery();
				while(rs.next()){
					
					
					
//					list.put(rs.getString("valuetime"), rs.getFloat("ZVALUEZY"));
					String watermeter_ID=rs.getString("watermeter_ID");
					String sqlDatas="select * from T_MINUTEWAZB where SelectTime > to_date('"+time+"','yyyy-mm-dd') and watermeter_ID="+watermeter_ID;
					PreparedStatement ps1=null;
					ResultSet rs1=null;
					try{
						ps1=conn.prepareStatement(sqlDatas);
						rs1=ps1.executeQuery();
						while(rs1.next()){
							if (list.containsKey(rs1.getString("SelectTime")))
							{
	
								float count = list.get(rs1.getString("SelectTime")) + rs1.getFloat("ZGross");
	
								list.put(rs1.getString("SelectTime"), count);
	
							} else
							{
	
								list.put(rs1.getString("SelectTime"), rs1.getFloat("ZGross"));
	
							}
//							list.put(rs1.getString("SelectTime"), rs1.getFloat("ZGross")+list.get(rs1.getString("SelectTime")));
						}
					}catch(SQLException e){
						e.printStackTrace();
					}finally{
						ConnDB.release(ps1, rs1);
					}
				}
				
				
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				ConnDB.release(conn,ps, rs);
			}
//        }   
//		}
		
		return list;
	}
}
