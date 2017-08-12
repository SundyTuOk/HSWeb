package com.sf.energy.light.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import com.sf.energy.light.model.SLAmContrastModel;
import com.sf.energy.util.ConnDB;

public class SLAmContrastDao {

	DecimalFormat df=new DecimalFormat("####.00");
	NumberFormat nf=NumberFormat.getPercentInstance();//小数百分号
	int total;

	public ArrayList<SLAmContrastModel> listAllHuiluData(String sortName,String order,int areaID,int year,int month,int day ,int pageCount,int pageIndex) throws SQLException
	{
		nf.setMinimumFractionDigits(2);//保留2分小数  
		ArrayList<SLAmContrastModel> list=new ArrayList<SLAmContrastModel>();
		SLAmContrastModel slcm=null;
		String sql="select a.SLLine_ID,(select SLLine_Name from SLLine T where T.SLLine_ID=a.SLLine_ID)TheName," +
				"a.AmMeter_ID,b.ZGross as DZGross,b.ZMoney as DZMoney,c.ZGross as MZGross,c.ZMoney as MZMoney " +
				"from (select SLLine_ID,AmMeter_ID from SLLine where AmMeter_ID<>0 and Area_ID ="+areaID+") a, " +
				"(select AmMeter_ID,ZGross,ZGross*Price_Value as ZMoney from T_DayAm " +
				"where SelectYear="+year+" and SelectMonth="+month+" and SelectDay="+day+") b," +
				" (select AmMeter_ID,ZGross,ZMoney from T_MonthAm where SelectYear="+year+" and SelectMonth="+month+") c" +
				" where a.AmMeter_ID=b.AmMeter_ID and a.AmMeter_ID=c.AmMeter_ID";  


		if(order.equals("asc"))//升序
		{
			sql+=" order by "+sortName+ " asc";
		}
		else if(order.equals("desc"))
		{
			sql+=" order by "+sortName+ " desc";
		}
		else {
			sql+=" order by "+sortName;
		}
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return null;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;
			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			}
			else
				rs.absolute(pageCount * pageIndex);

			while(rs.next() && (count > 0))//只要有区域，数据一直存在
			{
				slcm = new SLAmContrastModel();
				slcm.setHuiluID(rs.getInt("SLLine_ID"));
				slcm.setHuiluName(rs.getString("TheName"));
				slcm.setDayGross(rs.getFloat("DZGross"));
				slcm.setDayMoney(rs.getFloat("DZMoney"));
				slcm.setMonthGross(rs.getFloat("MZGross"));
				slcm.setMonthMoney(rs.getFloat("MZMoney"));
				list.add(slcm);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}


		return list;
	}



	/**
	 * 		查询指定年月日，所有区域路灯用电情况
	 * @param sortName
	 * @param order
	 * @param year	年份
	 * @param month	月份
	 * @param day	日
	 * @return		结果集
	 * @throws SQLException
	 */
	public ArrayList<SLAmContrastModel> listAllAreaData(String sortName,String order,int year,int month,int day) throws SQLException
	{
		nf.setMinimumFractionDigits(2);//保留2位小数  
		ArrayList<SLAmContrastModel> list=new ArrayList<SLAmContrastModel>();

		SLAmContrastModel  slcm=null;

		String sql="select "
				+"			    Area_ID, "
				+"			    ( "
				+"			        select "
				+"			            Y.Area_Name "
				+"			        from "
				+"			            Area Y "
				+"			        where "
				+"			            T.Area_ID = Y.Area_ID "
				+"			    ) TheName, "
				+"			    sum (DZGross) as DZGross, "
				+"			    sum (DZMoney) as DZMoney, "
				+"			    sum (MZGross) as MZGross, "
				+"			    sum (MZMoney) as MZMoney "
				+"			from "
				+"			    ( "
				+"			        select "
				+"			            a.Area_ID, "
				+"			            a.AmMeter_ID, "
				+"			            b.ZGross as DZGross, "
				+"			            b.ZMoney as DZMoney, "
				+"			            c.ZGross as MZGross, "
				+"			            c.ZMoney as MZMoney "
				+"			        from "
				+"			            ( "
				+"			                select "
				+"			                    Area_ID, "
				+"			                    AmMeter_ID "
				+"			                from "
				+"			                    SLLine "
				+"			                where "
				+"			                    AmMeter_ID <> 0 "
				+"			            ) a, "
				+"			            ( "
				+"			                select "
				+"			                    AmMeter_ID, "
				+"			                    ZGross, "
				+"			                    ZGross * Price_Value as ZMoney "
				+"			                from "
				+"			                    T_DayAm "
				+"			                where "
				+"			                    SelectYear = "+year+" "
				+"			                and SelectMonth = "+month+" "
				+"			                and SelectDay = "+day+" "
				+"			            ) b, "
				+"			            ( "
				+"			                select "
				+"			                    AmMeter_ID, "
				+"			                    ZGross, "
				+"			                    ZMoney "
				+"			                from "
				+"			                    T_MonthAm "
				+"			                where "
				+"			                    SelectYear = "+year+" "
				+"			                and SelectMonth = "+month+" "
				+"			            ) c "
				+"			        where "
				+"			            a.AmMeter_ID = b.AmMeter_ID "
				+"			        and a.AmMeter_ID = c.AmMeter_ID "
				+"			    ) T "
				+"			group by "
				+"			    Area_ID";


		if(order.equals("asc"))//升序
		{
			sql+=" order by "+sortName+ " asc";
		}
		else if(order.equals("desc"))
		{
			sql+=" order by "+sortName+ " desc";
		}
		else {
			sql+=" order by "+sortName;
		}

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())//只要有区域，数据一直存在
			{
				slcm=new SLAmContrastModel();
				slcm.setAreaID(rs.getInt("Area_ID"));
				slcm.setAreaName(rs.getString("THEName"));
				slcm.setDayGross(rs.getFloat("DZGross"));
				slcm.setDayMoney(rs.getFloat("DZMoney"));
				slcm.setMonthGross(rs.getFloat("MZGross"));
				slcm.setMonthMoney(rs.getFloat("MZMoney"));
				list.add(slcm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}


		return list;
	}


	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
}
