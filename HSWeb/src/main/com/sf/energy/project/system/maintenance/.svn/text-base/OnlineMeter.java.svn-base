package com.sf.energy.project.system.maintenance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.system.model.OnlineMeterModel;
import com.sf.energy.util.ConnDB;

public class OnlineMeter
{
	private int totalCount = 0;

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public ArrayList<OnlineMeterModel> queryMeterState(String queryType,
			int id, int isOnline, int pageCount, int pageIndex) throws SQLException
	{
		String meterName = "";
		String meterState = "";
		String sql = "";
		String strWhere = "";
		OnlineMeterModel omm = null;
		ArrayList<OnlineMeterModel> lst = new ArrayList<OnlineMeterModel>();

		if (isOnline == 2)
		{
			strWhere = "1 <> 1";
		} else if (isOnline == 0)
		{
			strWhere = "AmMeter_Stat = 0";
		} else if (isOnline == 1)
		{
			strWhere = "AmMeter_Stat <> 0";
		} else
		{
			strWhere = "1 = 1";
		}

		sql = "select ammeter_id as Meter_ID,"
				+ "(select Area_Name from (Area)b where a.Area_ID=b.Area_ID) as Area_Name,"
				+ "(select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID) as Architecture_Name,"
				+ "to_char(Floor),"
				+ "amMeter_Name as Meter_Name,"
				+ "(select Gather_Name from (Gather)b where a.Gather_ID=b.Gather_ID) as Gather_Name, "
				+ "(select Partment_Name from  (Partment)b where a.Partment_id=b.Partment_ID) as Partment_Text,"
				+ "AmMeter_485Address as Meter_485Address,"
				+ "(select MeteStyle_Name from (MeteStyle)b where a.MeteStyle_ID=b.MeteStyle_ID) as MeteStyle_Name,"
				+ "AmMeter_Stat as Meter_Stat,"
				+ "nvl(LastTime,'－')  as  LastTime " + "from (AmMeter)a "
				+ "where " + strWhere;

		if ("Area".equals(queryType))
		{
			if (id != -1)
			{
				sql = sql + " and Area_ID = " + id;
			}
		} else if ("Architecture".equals(queryType))
		{
			if (id != -1)
			{
				sql = sql + " and Architecture_ID = " + id;
			}
		}
		
		//水表数据
		String sqlw = "";
        String strWherew = "";
        if (isOnline == 2)
		{
        	strWherew = "1 <> 1";
		} else if (isOnline == 0)
		{
			strWherew = "WaterMeter_Stat = 0";
		} else if (isOnline == 1)
		{
			strWherew = "WaterMeter_Stat <> 0";
		} else
		{
			strWherew = "1 = 1";
		}
        sqlw = "select WaterMeter_ID as Meter_ID," +
        		"(select Area_Name from (Area)b where a.Area_ID=b.Area_ID) as Area_Name," +
        		"(select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID) as Architecture_Name," +
        		"to_char(Floor)," +
        		"WaterMeter_Name as Meter_Name," +
        		"nvl((select Gather_Name from (Gather)b where a.Gather_ID=b.Gather_ID),'-') as Gather_Name, " +
        		"nvl((select Partment_Name from  (Partment)b where a.Partment=b.Partment_ID),'-') as Partment_Text," +
        		"WaterMeter_485Address as Meter_485Address," +
        		"nvl((select MeteStyle_Name from (MeteStyle)b where a.MeteStyle_ID=b.MeteStyle_ID),'-') as MeteStyle_Name," +
        		"WaterMeter_Stat as Meter_Stat," +
        		"nvl(to_char(LastTime,'yyyy-MM-dd HH24:MI:SS'),'-') " +
        		"from (WaterMeter)a where " + strWherew;
        if ("Area".equals(queryType))
		{
			if (id != -1)
			{
				sqlw = sqlw + " and Area_ID = " + id;
			}
		} else if ("Architecture".equals(queryType))
		{
			if (id != -1)
			{
				sqlw = sqlw + " and Architecture_ID = " + id;
			}
		}
        
        String sqlAll = "select * from ( ";
        sqlAll += "                        ( " + sql + " ) ";
        sqlAll += "                         union all ";
        sqlAll += "                        ( " + sqlw + " ) ";
        sqlAll += "                     ) s1  ";



        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlAll,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return lst;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);
			
			while (rs.next() && (count > 0))
			{
				omm = new OnlineMeterModel();
				meterName = "";
				if (rs.getString(2) != null && !"".equals(rs.getString(2)))
				{
					meterName += rs.getString(2);
				}
				if (rs.getString(3) != null && !"".equals(rs.getString(3)))
				{
					meterName += "." + rs.getString(3);
				}
				if (rs.getString(4) != null && !"".equals(rs.getString(4)))
				{
					meterName += "." + rs.getString(4) + "楼";
				}
				if (rs.getString(5) != null && !"".equals(rs.getString(5)))
				{
					meterName += "." + rs.getString(5);
				}
				// omm.setMeter_Name(rs.getString(2) + "." + rs.getString(3) + "." +
				// rs.getString(4) + "楼" + "." + rs.getString(5));
				omm.setMeterName(meterName);
				if(!"".equals(rs.getString(9)) && rs.getString(9) != null)
				{
					omm.setMeteStyleName(rs.getString(9));
				}else
				{
					omm.setMeteStyleName("");
				}
				
				if(!"".equals(rs.getString(7)) && rs.getString(7) != null && !"null".equals(rs.getString(7)))
				{
					omm.setPartmentText(rs.getString(7));
				}else
				{
					omm.setPartmentText("");
				}
				
				if(!"".equals(rs.getString(6)) && rs.getString(6) != null)
				{
					omm.setGatherName(rs.getString(6));
				}else
				{
					omm.setGatherName("");
				}
				
				omm.setMeter485Address(rs.getString(8));
				omm.setLastTime(rs.getString(11));
				if (rs.getFloat(10) == 0)
				{
					meterState = "<span style=\"color:red\">离线</span>";
				} else
				{
					meterState = "<span style=\"color:Green\">在线</span>";
				}
				omm.setMeterState(meterState);

				lst.add(omm);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}

	public ArrayList<OnlineMeterModel> queryMeterIsOnline(int areaID,
			int archID, int isOnLine, int pageCount, int pageIndex)
			throws SQLException
	{
		String meterName = "";
		String meterState = "";
		String sql = "";
		String strWhere = "";
		OnlineMeterModel omm = null;
		ArrayList<OnlineMeterModel> lst = new ArrayList<OnlineMeterModel>();

		if (isOnLine == 2)
		{
			strWhere = "1 <> 1";
		} else if (isOnLine == 0)
		{
			strWhere = "AmMeter_Stat = 0";
		} else if (isOnLine == 1)
		{
			strWhere = "AmMeter_Stat <> 0";
		} else
		{
			strWhere = "1 = 1";
		}
		sql = "select ammeter_id as Meter_ID,"
				+ "(select Area_Name from (Area)b where a.Area_ID=b.Area_ID) as Area_Name,"
				+ "(select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID) as Architecture_Name,"
				+ "to_char(Floor),"
				+ "amMeter_Name as Meter_Name,"
				+ "(select Gather_Name from (Gather)b where a.Gather_ID=b.Gather_ID) as Gather_Name, "
				+ "(select Partment_Name from  (Partment)b where a.Partment_id=b.Partment_ID) as Partment_Text,"
				+ "AmMeter_485Address as Meter_485Address,"
				+ "(select MeteStyle_Name from (MeteStyle)b where a.MeteStyle_ID=b.MeteStyle_ID) as MeteStyle_Name,"
				+ "AmMeter_Stat as Meter_Stat,"
				+ "nvl(LastTime,'－')  as  LastTime " + "from (AmMeter)a "
				+ "where " + strWhere;
		if (archID != -1)
		{
			sql += " and Architecture_ID = " + archID;
		} else if (areaID != -1)
		{
			sql +=  " and Area_ID = " + areaID;
		} 
		
		// 水表数据
		String sqlw = "";
		String strWherew = "";
		if (isOnLine == 2)
		{
			strWherew = "1 <> 1";
		} else if (isOnLine == 0)
		{
			strWherew = "WaterMeter_Stat = 0";
		} else if (isOnLine == 1)
		{
			strWherew = "WaterMeter_Stat <> 0";
		} else
		{
			strWherew = "1 = 1";
		}
		sqlw = "select WaterMeter_ID as Meter_ID,"
				+ "(select Area_Name from (Area)b where a.Area_ID=b.Area_ID) as Area_Name,"
				+ "(select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID) as Architecture_Name,"
				+ "to_char(Floor),"
				+ "WaterMeter_Name as Meter_Name,"
				+ "(select Gather_Name from (Gather)b where a.Gather_ID=b.Gather_ID) as Gather_Name, "
				+ "(select Partment_Name from  (Partment)b where a.Partment=b.Partment_ID) as Partment_Text,"
				+ "WaterMeter_485Address as Meter_485Address,"
				+ "(select MeteStyle_Name from (MeteStyle)b where a.MeteStyle_ID=b.MeteStyle_ID) as MeteStyle_Name,"
				+ "WaterMeter_Stat as Meter_Stat,"
				+ "nvl(to_char(LastTime,'yyyy-MM-dd HH24:MI:SS'),'-') as LastTime "
				+ "from (WaterMeter)a where " + strWherew;
		if (archID != -1)
		{
			sqlw += " and Architecture_ID = " + archID;
		} else if (areaID != -1)
		{
			sqlw +=  " and Area_ID = " + areaID;
		}
		
		String sqlAll = "select * from ( ";
        sqlAll += "                        ( " + sql + " ) ";
        sqlAll += "                         union all ";
        sqlAll += "                        ( " + sqlw + " ) ";
        sqlAll += "                     ) s1  ";
        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlAll,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = null;
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return lst;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				omm = new OnlineMeterModel();
				meterName = "";
				if (rs.getString(2) != null && !"".equals(rs.getString(2)))
				{
					meterName += rs.getString(2);
				}
				if (rs.getString(3) != null && !"".equals(rs.getString(3)))
				{
					meterName += "." + rs.getString(3);
				}
				if (rs.getString(4) != null && !"".equals(rs.getString(4)))
				{
					meterName += "." + rs.getString(4) + "楼";
				}
				if (rs.getString(5) != null && !"".equals(rs.getString(5)))
				{
					meterName += "." + rs.getString(5);
				}
				// omm.setMeter_Name(rs.getString(2) + "." + rs.getString(3) + "." +
				// rs.getString(4) + "楼" + "." + rs.getString(5));
				omm.setMeterName(meterName);
				if(!"".equals(rs.getString(9)) && rs.getString(9) != null)
				{
					omm.setMeteStyleName(rs.getString(9));
				}else
				{
					omm.setMeteStyleName("");
				}
				
				if(!"".equals(rs.getString(7)) && rs.getString(7) != null && !"null".equals(rs.getString(7)))
				{
					omm.setPartmentText(rs.getString(7));
				}else
				{
					omm.setPartmentText("");
				}
				
				if(!"".equals(rs.getString(6)) && rs.getString(6) != null)
				{
					omm.setGatherName(rs.getString(6));
				}else
				{
					omm.setGatherName("");
				}
				omm.setMeter485Address(rs.getString(8));
				omm.setLastTime(rs.getString(11));
				if (rs.getFloat(10) == 0)
				{
					meterState = "<span style=\"color:red\">离线</span>";
				} else
				{
					meterState = "<span style=\"color:Green\">在线</span>";
				}
				omm.setMeterState(meterState);

				lst.add(omm);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}
	
	public ArrayList<OnlineMeterModel> sortQuery(int areaID,
			int archID, int isOnLine, int pageCount, int pageIndex, String order)throws SQLException
	{
		String meterState = "";
		String sql = "";
		String strWhere = "";
		OnlineMeterModel omm = null;
		ArrayList<OnlineMeterModel> lst = new ArrayList<OnlineMeterModel>();

		if (isOnLine == 2)
		{
			strWhere = "1 <> 1";
		} else if (isOnLine == 0)
		{
			strWhere = "AmMeter_Stat = 0";
		} else if (isOnLine == 1)
		{
			strWhere = "AmMeter_Stat <> 0";
		} else
		{
			strWhere = "1 = 1";
		}
		sql = "select "+
				"AmMeter_ID as Meter_ID,Gather_ID,Area_ID,Architecture_ID,"+
				"nvl(((select Area_Name from (Area)b where a.Area_ID=b.Area_ID) || '.' || (select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID) || '.' || to_char(Floor) || '楼.' || AmMeter_Name),'－') as Meter_Name,"+
				"(select Gather_Name from (Gather)b where a.Gather_ID=b.Gather_ID)Gather_Name, "+
				"(select Partment_Name from  (Partment)b where a.Partment_id=b.Partment_ID)Partment_Text,"+
				"AmMeter_485Address as Meter_485Address,"+
				"(select MeteStyle_Name from (MeteStyle)b where a.MeteStyle_ID=b.MeteStyle_ID)MeteStyle_Name,"+
				"AmMeter_Stat as Meter_Stat,"+
				"nvl(LastTime,'－')  as  LastTime "+
			"from  "+
				"(AmMeter)a "+
			"where " + strWhere;

		if (archID != -1)
		{
			sql += " and Architecture_ID = " + archID;
		} else if (areaID != -1)
		{
			sql +=  " and Area_ID = " + areaID;
		} 
		
		// 水表数据
		String sqlw = "";
		String strWherew = "";
		if (isOnLine == 2)
		{
			strWherew = "1 <> 1";
		} else if (isOnLine == 0)
		{
			strWherew = "WaterMeter_Stat = 0";
		} else if (isOnLine == 1)
		{
			strWherew = "WaterMeter_Stat <> 0";
		} else
		{
			strWherew = "1 = 1";
		}
		sqlw = "select "+
					"WaterMeter_ID as Meter_ID,Gather_ID,Area_ID,Architecture_ID,"+
					"nvl(((select Area_Name from (Area)b where a.Area_ID=b.Area_ID) || '.' || (select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID) || '.' || to_char(Floor) || '楼.' || WaterMeter_Name),'－') as Meter_Name,"+
					"(select Gather_Name from (Gather)b where a.Gather_ID=b.Gather_ID)Gather_Name, "+
					"(select Partment_Name from  (Partment)b where a.Partment=b.Partment_ID)Partment_Text,"+
					"WaterMeter_485Address as Meter_485Address,"+
					"(select MeteStyle_Name from (MeteStyle)b where a.MeteStyle_ID=b.MeteStyle_ID)MeteStyle_Name,"+
					"WaterMeter_Stat as Meter_Stat,"+
					"nvl(to_char(LastTime,'yyyy-MM-dd HH24:MI:SS'),'－') "+
				"from "+
					"(WaterMeter)a "+
				"where " + strWherew;

		if (archID != -1)
		{
			sqlw += " and Architecture_ID = " + archID;
		} else if (areaID != -1)
		{
			sqlw +=  " and Area_ID = " + areaID;
		}
		
		String sqlAll = "select * from ( ";
        sqlAll += "                        ( " + sql + " ) ";
        sqlAll += "                         union all ";
        sqlAll += "                        ( " + sqlw + " ) ";
        sqlAll += "                     ) s1  " + order;

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlAll,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return lst;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				omm = new OnlineMeterModel();
				
				if(!"".equals(rs.getString("MeteStyle_Name")) && rs.getString("MeteStyle_Name") != null)
				{
					omm.setMeteStyleName(rs.getString("MeteStyle_Name"));
				}else
				{
					omm.setMeteStyleName("");
				}
				
				if(!"".equals(rs.getString("Partment_Text")) && rs.getString("Partment_Text") != null && !"null".equals(rs.getString("Partment_Text")))
				{
					omm.setPartmentText(rs.getString("Partment_Text"));
				}else
				{
					omm.setPartmentText("");
				}
				
				if(!"".equals(rs.getString("Gather_Name")) && rs.getString("Gather_Name") != null)
				{
					omm.setGatherName(rs.getString("Gather_Name"));
				}else
				{
					omm.setGatherName("");
				}
				
				omm.setMeterName(rs.getString("Meter_Name"));
//			omm.setMeteStyleName(rs.getString("MeteStyle_Name"));
//			omm.setPartmentText(rs.getString("Partment_Text"));
//			omm.setGatherName(rs.getString("Gather_Name"));
				omm.setMeter485Address(rs.getString("Meter_485Address"));
				omm.setLastTime(rs.getString("LastTime"));
				if (rs.getInt("Meter_Stat") == 0)
				{
					meterState = "<span style=\"color:red\">离线</span>";
				} else
				{
					meterState = "<span style=\"color:Green\">在线</span>";
				}
				omm.setMeterState(meterState);

				lst.add(omm);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}
	public ArrayList<OnlineMeterModel> exportQuery(int areaID,
			int archID, int isOnLine,  String order)throws SQLException
	{
		String meterState = "";
		String gatherState="";
		String sql = "";
		String strWhere = "";
		OnlineMeterModel omm = null;
		ArrayList<OnlineMeterModel> lst = new ArrayList<OnlineMeterModel>();

		if (isOnLine == 2)
		{
			strWhere = "1 <> 1";
		} else if (isOnLine == 0)
		{
			strWhere = "AmMeter_Stat = 0";
		} else if (isOnLine == 1)
		{
			strWhere = "AmMeter_Stat <> 0";
		} else
		{
			strWhere = "1 = 1";
		}
		sql = "select "+
				"AmMeter_ID as Meter_ID,Gather_ID,Area_ID,Architecture_ID,"+
				"nvl(((select Area_Name from (Area)b where a.Area_ID=b.Area_ID) || '.' || (select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID) || '.' || to_char(Floor) || '楼.' || AmMeter_Name),'－') as Meter_Name,"+
				"(select Gather_Name from (Gather)b where a.Gather_ID=b.Gather_ID)Gather_Name,  (SELECT Gather_address FROM (Gather) b WHERE a.Gather_ID = b.Gather_ID) AS Gather_address, (SELECT LASTTIME FROM (Gather) b WHERE a.Gather_ID = b.Gather_ID) AS GaTher_LASTTIME,(SELECT IP FROM (Gather) b WHERE a.Gather_ID = b.Gather_ID) AS GaTher_IP, (SELECT GATHER_STATE FROM (Gather) b WHERE a.Gather_ID = b.Gather_ID) AS GATHER_STATE, "+
				"(select Partment_Name from  (Partment)b where a.Partment_id=b.Partment_ID)Partment_Text,"+
				"AmMeter_485Address as Meter_485Address,"+
				"(select MeteStyle_Name from (MeteStyle)b where a.MeteStyle_ID=b.MeteStyle_ID)MeteStyle_Name,"+
				"AmMeter_Stat as Meter_Stat,"+
				"nvl(LastTime,'－')  as  LastTime "+
			"from  "+
				"(AmMeter)a "+
			"where " + strWhere;

		if (archID != -1)
		{
			sql += " and Architecture_ID = " + archID;
		} else if (areaID != -1)
		{
			sql +=  " and Area_ID = " + areaID;
		} 
		
		// 水表数据
		String sqlw = "";
		String strWherew = "";
		if (isOnLine == 2)
		{
			strWherew = "1 <> 1";
		} else if (isOnLine == 0)
		{
			strWherew = "WaterMeter_Stat = 0";
		} else if (isOnLine == 1)
		{
			strWherew = "WaterMeter_Stat <> 0";
		} else
		{
			strWherew = "1 = 1";
		}
		sqlw = "select "+
					"WaterMeter_ID as Meter_ID,Gather_ID,Area_ID,Architecture_ID,"+
					"nvl(((select Area_Name from (Area)b where a.Area_ID=b.Area_ID) || '.' || (select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID) || '.' || to_char(Floor) || '楼.' || WaterMeter_Name),'－') as Meter_Name,"+
					"(select Gather_Name from (Gather)b where a.Gather_ID=b.Gather_ID) Gather_Name,(SELECT Gather_address FROM (Gather) b WHERE a.Gather_ID = b.Gather_ID) AS Gather_address, (SELECT LASTTIME FROM (Gather) b WHERE a.Gather_ID = b.Gather_ID) AS GaTher_LASTTIME,(SELECT IP FROM (Gather) b WHERE a.Gather_ID = b.Gather_ID) AS GaTher_IP, (SELECT GATHER_STATE FROM (Gather) b WHERE a.Gather_ID = b.Gather_ID) AS GATHER_STATE,  "+
					"(select Partment_Name from  (Partment)b where a.Partment=b.Partment_ID)Partment_Text,"+
					"WaterMeter_485Address as Meter_485Address,"+
					"(select MeteStyle_Name from (MeteStyle)b where a.MeteStyle_ID=b.MeteStyle_ID)MeteStyle_Name,"+
					"WaterMeter_Stat as Meter_Stat,"+
					"nvl(to_char(LastTime,'yyyy-MM-dd HH24:MI:SS'),'－') "+
				"from "+
					"(WaterMeter)a "+
				"where " + strWherew;

		if (archID != -1)
		{
			sqlw += " and Architecture_ID = " + archID;
		} else if (areaID != -1)
		{
			sqlw +=  " and Area_ID = " + areaID;
		}
		
		String sqlAll = "select * from ( ";
        sqlAll += "                        ( " + sql + " ) ";
        sqlAll += "                         union all ";
        sqlAll += "                        ( " + sqlw + " ) ";
        sqlAll += "                     ) s1  " + order;

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlAll,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			

			while (rs.next())
			{
				omm = new OnlineMeterModel();
				
				if(!"".equals(rs.getString("MeteStyle_Name")) && rs.getString("MeteStyle_Name") != null)
				{
					omm.setMeteStyleName(rs.getString("MeteStyle_Name"));
				}else
				{
					omm.setMeteStyleName("");
				}
				
				if(!"".equals(rs.getString("Partment_Text")) && rs.getString("Partment_Text") != null && !"null".equals(rs.getString("Partment_Text")))
				{
					omm.setPartmentText(rs.getString("Partment_Text"));
				}else
				{
					omm.setPartmentText("");
				}
				
				if(!"".equals(rs.getString("Gather_Name")) && rs.getString("Gather_Name") != null)
				{
					omm.setGatherName(rs.getString("Gather_Name"));
				}else
				{
					omm.setGatherName("");
				}
				if(!"".equals(rs.getString("Gather_address")) && rs.getString("Gather_address") != null)
				{
					omm.setGatherAddress(rs.getString("Gather_address"));
				}else
				{
					omm.setGatherAddress("");
				}
				if(!"".equals(rs.getString("GaTher_LASTTIME")) && rs.getString("GaTher_LASTTIME") != null)
				{
					omm.setGatherLASTTIME(rs.getString("GaTher_LASTTIME"));
				}else
				{
					omm.setGatherLASTTIME("");
				}
				if(!"".equals(rs.getString("GaTher_IP")) && rs.getString("GaTher_IP") != null)
				{
					omm.setGatherIP(rs.getString("GaTher_IP"));
				}else
				{
					omm.setGatherIP("");
				}
				
				if (rs.getInt("GATHER_STATE") == 0)
				{
					gatherState = "离线";
				} else
				{
					gatherState = "在线";
				}
				omm.setGatherState(gatherState);
				
				omm.setMeterName(rs.getString("Meter_Name"));

				omm.setMeter485Address(rs.getString("Meter_485Address"));
				omm.setLastTime(rs.getString("LastTime"));
				if (rs.getInt("Meter_Stat") == 0)
				{
					meterState = "离线";
				} else
				{
					meterState = "在线";
				}
				omm.setMeterState(meterState);

				lst.add(omm);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}
}
