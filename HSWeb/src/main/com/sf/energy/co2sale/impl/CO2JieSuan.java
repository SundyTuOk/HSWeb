package com.sf.energy.co2sale.impl;

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

import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.util.ConnDB;

public class CO2JieSuan
{
	Connection conn = null;
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dfd = new SimpleDateFormat("yyyy-MM-dd");
	DepartmentDao dd=new DepartmentDao();
	public CO2JieSuan(){
		try
		{
			conn = ConnDB.getConnection();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void JieSuanCO2() throws SQLException{
		List<Department> parts=qetQuotoPartment(Calendar.getInstance().get(Calendar.YEAR));
		for(int i=0;i<parts.size();i++){
			float thisRemainValue=0;
			float thisBuyValue=0;
			float thisSaleValue=0;
			String thisTime=df.format(new Date());
			Calendar cal = Calendar.getInstance();
			int endyear=cal.get(Calendar.YEAR);
			int startyear=cal.get(Calendar.YEAR);
			
			String endDate=dfd.format(new Date());
			String startDate=startyear+"-01-01";
			String lastTime=startyear+"-01-01"+" 00:00:00";
			float zGross=0;
			//获取前一次的结算记录
			String sql="select * from (SELECT THISREMAINVALUE,TO_CHAR(ThisTime,'yyyy-MM-dd hh24:mi:ss') lastTime,TO_CHAR(ThisTime,'YYYY') year  FROM CO2JIESUAN where PARTMENT_ID="+parts.get(i).getDepartmentID()+" ORDER BY THISTIME desc) where rownum<=1 ";
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();
				while(rs.next())
				{
					thisRemainValue=rs.getFloat("THISREMAINVALUE");
					lastTime=rs.getString("lastTime");
					startDate=lastTime.substring(0,10);
					startyear=rs.getInt("Year");
					
					if(startyear!=endyear){
						thisRemainValue=0;
						startyear=endyear;
						startDate=startyear+"-01-01";
					}
				}
				//获取前一次结算至今的用量
				String sql2="SELECT	PARTMENT_ID,SUM (zgross) zgross FROM T_PARTMENTDAYAM WHERE	to_date(to_char(SELECTYEAR*10000+SELECTMONTH*100+SELECTDAY),'yyyy-mm-dd') BETWEEN to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') AND PARTMENT_ID ="+parts.get(i).getDepartmentID()+" GROUP BY	PARTMENT_ID";
//				Connection conn2=null;
				PreparedStatement ps2 =null;
				ResultSet rs2 = null;
				
				try
				{

//					conn2 = ConnDB.getConnection();
					ps2 = conn.prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);				rs2 = ps2.executeQuery();
					while(rs2.next())
					{
						zGross=(float) (rs2.getFloat("zgross")*4.04/10);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release( ps2, rs2);
				}
				//获取买入或卖出的量
				Map<String, String> buyValueMap=getBuyOrSaleByPartID(parts.get(i).getDepartmentID(), lastTime, thisTime);
				
				thisBuyValue=Float.parseFloat(buyValueMap.get("buyValue"));
				thisSaleValue=Float.parseFloat(buyValueMap.get("saleValue"));
				//获取总的定额
				Map<String, String> total=getTotalQuotaCO2ByPartID(parts.get(i).getDepartmentID(), startyear);
				if(thisRemainValue==0){//如果没有上次结算则把剩余量修改为总定额
					thisRemainValue=Float.parseFloat(total.get("totalQuota"));
				}
				//添加到结算表
				String insert="insert into CO2JieSuan (PARTMENT_ID, THISBUYVALUE, THISSALEVALUE, THISTIME, LASTTIME, LASTREMAINVALUE, THISREMAINVALUE, THISUSEDVALUE,TotalQuota) VALUES ("+parts.get(i).getDepartmentID()+","+thisBuyValue+","+thisSaleValue+",to_date('"+thisTime+"','yyyy-MM-dd hh24:mi:ss'),to_date('"+lastTime+"','yyyy-MM-dd hh24:mi:ss'),"+thisRemainValue+","+(thisRemainValue+thisBuyValue-thisSaleValue-zGross)+","+zGross+","+total.get("totalQuota")+")";
				PreparedStatement ps3=null;
//				Connection conn3 =null;
				try
				{
//					conn3 = ConnDB.getConnection();
					ps3 = conn.prepareStatement(insert,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					int num=ps3.executeUpdate();
					if(num==1){
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release( ps3);
				}
				
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
			
		}
	}
	public List<Department> qetQuotoPartment(int year) throws SQLException{
		List<Department> list=new ArrayList<Department>();
		Department depart = null;
		String sql="select p.* from DeptQuotaCO2 a,PARTMENT p where a.PARTMENT_ID=p.PARTMENT_ID and a.year="+year;
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
				depart = new Department();
				depart.setDepartmentID(rs.getInt("Partment_ID"));
				depart.setDepartmentNum(rs.getString("Partment_Num"));
				depart.setDepartmetName(rs.getString("Partment_Name"));
				depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
				depart.setDepartmentMan(rs.getString("Partment_Man"));
				depart.setDepartmentPhone(rs.getString("Partment_Phone"));
				depart.setDepartmentRemark(rs.getString("Partment_Remark"));
				depart.setDepartmentOrder(rs.getInt("Partment_Order"));
				depart.setDepartmentText(rs.getString("Partment_Text"));
				depart.setDepartmentIDs(rs.getString("Partment_IDs"));

				list.add(depart);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		while(rs.next())
//		{
//			depart = new Department();
//			depart.setDepartmentID(rs.getInt("Partment_ID"));
//			depart.setDepartmentNum(rs.getString("Partment_Num"));
//			depart.setDepartmetName(rs.getString("Partment_Name"));
//			depart.setDepartmentParentID(rs.getInt("Partment_Parent"));
//			depart.setDepartmentMan(rs.getString("Partment_Man"));
//			depart.setDepartmentPhone(rs.getString("Partment_Phone"));
//			depart.setDepartmentRemark(rs.getString("Partment_Remark"));
//			depart.setDepartmentOrder(rs.getInt("Partment_Order"));
//			depart.setDepartmentText(rs.getString("Partment_Text"));
//			depart.setDepartmentIDs(rs.getString("Partment_IDs"));
//
//			list.add(depart);
//			
//		}

		
		
		return list; 
	}
	
	
	/**根据部门ID获取上次结算至今的买入量和卖出量
	 * @param partID
	 * @param lastTime
	 * @param thisTime
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getBuyOrSaleByPartID(int partID,String lastTime,String thisTime) throws SQLException{
		String buyValue="0";
		String saleValue="0";
		Map<String, String> result=new HashMap<String, String>();
		String sql="SELECT NVL(sum(DEALVALUE ),0) AS BuyValue FROM CO2SALEINFO WHERE DEALTIME BETWEEN TO_DATE ('"+lastTime+"','yyyy-MM-dd hh24:mi:ss') AND TO_DATE ('"+thisTime+"','yyyy-MM-dd hh24:mi:ss') AND BUYORSALE = 1 AND ORDERSTATUS = 1 AND BUY_PARTMENTID = "+partID;
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
				buyValue=rs.getString("BuyValue");
			}
			
			String sql1="SELECT NVL(sum(DEALVALUE),0) AS SaleValue FROM CO2SALEINFO WHERE DEALTIME BETWEEN TO_DATE ('"+lastTime+"','yyyy-MM-dd hh24:mi:ss') AND TO_DATE ('"+thisTime+"','yyyy-MM-dd hh24:mi:ss') AND BUYORSALE = 0 AND ORDERSTATUS = 1 AND BUY_PARTMENTID = "+partID;
			PreparedStatement ps1 =null;
			ResultSet rs1 =null;
//			Connection conn1 = null;
			try
			{
//				conn1 = ConnDB.getConnection();
				ps1 = conn.prepareStatement(sql1,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs1 = ps1.executeQuery();
				while(rs1.next())
				{
					saleValue=rs1.getString("SaleValue");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release( ps1, rs1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		while(rs.next())
//		{
//			buyValue=rs.getString("BuyValue");
//		}

		
		result.put("buyValue", buyValue);
		result.put("saleValue", saleValue);
		return result;
	}
	
	/**获取总定额
	 * @param partID
	 * @param startyear
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getTotalQuotaCO2ByPartID(int partID,int startyear) throws SQLException{
		Map<String,String> result=new HashMap<String, String>();
		String sql="SELECT	G .Partment_Name,	f.Partment_ID,	NVL (f.totalQuota, 0) AS totalQuota, NVL (f.standardQuota, 0) AS standardQuota,	NVL (f.TiaozhengFixed, 0) AS TiaozhengFixed, NVL (f.addPerMonthQuota, 0) AS addPerMonthQuota "
				  +" FROM (SELECT A .Partment_ID, A .Partment_Name FROM Partment A where A.PARTMENT_ID="+partID+") G "
				  +" LEFT JOIN (	SELECT D .Partment_ID,D .standardQuota + D .TiaozhengFixed + D .addPerMonthQuota AS totalQuota,	D .standardQuota,	D .TiaozhengFixed,D .addPerMonthQuota FROM "
				  +" ( SELECT c.Partment_ID,c.TiaozhengFixed,c.FixedZhuijia1 + FixedZhuijia2 + FixedZhuijia3 + FixedZhuijia4 + c.FixedZhuijia5 + FixedZhuijia6 + FixedZhuijia7 + FixedZhuijia8 + c.FixedZhuijia9 + FixedZhuijia10 + FixedZhuijia11 + FixedZhuijia12 AS addPerMonthQuota,b.standardQuota	FROM (SELECT A .Partment_ID,NVL (SUM(A .sum1), 0) AS standardQuota FROM "
				  +" ( SELECT Partment_ID, NVL ( SUM ((CASE M1 WHEN 1 THEN 1	ELSE 0 END + CASE M2 WHEN 1 THEN 1 ELSE 0 END + CASE M3	WHEN 1 THEN	1 ELSE 0 END+ CASE M4 WHEN 1 THEN 1	ELSE 0 END+ CASE M5	WHEN 1 THEN	1 ELSE 0 END+ CASE M6 WHEN 1 THEN 1	ELSE 0 END+ CASE M7	WHEN 1 THEN	1 ELSE 0 END+ CASE M8 WHEN 1 THEN 1	ELSE 0 END+ CASE M9	WHEN 1 THEN	1 ELSE 0 END+ CASE M10 WHEN 1 THEN 1 ELSE 0 END+ CASE M11 WHEN 1 THEN 1	ELSE 0 END+ CASE M12 WHEN 1 THEN 1 ELSE 0 END) * TotalDevice * POWER * RunHoursPerMth * 4.04 /10 ),0) AS sum1"
				  +" FROM DeptQutaDetlByDevCO2	WHERE	YEAR = '"+startyear+"'	GROUP BY	Partment_ID"
				  +" UNION ALL	SELECT Partment_ID,	NVL (SUM ((CASE M1	WHEN 1 THEN	1 ELSE 0 END + CASE M2 WHEN 1 THEN 1 ELSE 0 END + CASE M3 WHEN 1 THEN 1	ELSE 0 END+ CASE M4	WHEN 1 THEN	1 ELSE 0 END+ CASE M5 WHEN 1 THEN 1	ELSE 0 END+ CASE M6	WHEN 1 THEN	1 ELSE 0 END+ CASE M7 WHEN 1 THEN 1	ELSE 0 END+ CASE M8	WHEN 1 THEN	1 ELSE 0 END+ CASE M9 WHEN 1 THEN 1	ELSE 0 END+ CASE M10 WHEN 1 THEN 1 ELSE 0 END+ CASE M11	WHEN 1 THEN	1 ELSE 0 END+ CASE M12 WHEN 1 THEN 1 ELSE 0 END)* TotalPeople * EStandard ),0) AS sum1"
				  +" FROM DeptQutaDetlByPeoCO2 WHERE YEAR = '"+startyear+"'	GROUP BY	Partment_ID"
				  +" ) A"
				  +" GROUP BY A .Partment_ID"
				  +" ) b,DeptQuotaCO2 c"
				  +" WHERE	c.Partment_ID = b.Partment_ID"
				  +" AND c. YEAR = '"+startyear+"'"
				  +" ) D) f ON f.Partment_ID = G .Partment_ID ";
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
				result.put("Partment_Name", rs.getString("Partment_Name"));
				result.put("Partment_ID", rs.getString("Partment_ID"));
				result.put("totalQuota", rs.getString("totalQuota"));
				result.put("standardQuota", rs.getString("standardQuota"));
				result.put("TiaozhengFixed", rs.getString("TiaozhengFixed"));
				result.put("addPerMonthQuota", rs.getString("addPerMonthQuota"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		while(rs.next())
//		{
//			result.put("Partment_Name", rs.getString("Partment_Name"));
//			result.put("Partment_ID", rs.getString("Partment_ID"));
//			result.put("totalQuota", rs.getString("totalQuota"));
//			result.put("standardQuota", rs.getString("standardQuota"));
//			result.put("TiaozhengFixed", rs.getString("TiaozhengFixed"));
//			result.put("addPerMonthQuota", rs.getString("addPerMonthQuota"));
//		}

		return result;
	}
	
	
//	public List<String> getPartmentByQuotaCO2(int year) throws SQLException{
//		List<String> partStrings=new ArrayList<String>();
//		
//		String sql="select partment_id from DeptQuotaCO2 where year="+year;
//		ResultSet rs = ps.executeQuery();
//		while(rs.next())
//		{
//		}
//		if (rs != null)
//			rs.close();
//		if (ps != null)
//			ps.close();
//		return partStrings;
//	}
//	
	
}
