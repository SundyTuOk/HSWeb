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

import com.sf.energy.light.model.SLControlModel;
import com.sf.energy.prepayment.model.ExchangeMeterModel;
import com.sf.energy.util.ConnDB;
import com.sun.mail.imap.Utility.Condition;

public class ExchangeMeterDao
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

	public ArrayList<ExchangeMeterModel> queryInfo(int pageCount,
			int pageIndex, String selectInfo, String queryMsg, boolean isPage,String order)
			throws SQLException
	{
		ArrayList<ExchangeMeterModel> list = new ArrayList<ExchangeMeterModel>();
		ExchangeMeterModel emm = null;
		String[] info = selectInfo.split("\\|");

		String strWhere1 = "";
		String strWhere2 = "";
		if (queryMsg != null && !"".equals(queryMsg))
		{
			String[] msg = queryMsg.split("\\|");
			strWhere1 = " and insertTime>=" + "to_date('" + msg[0]
					+ "','yyyy-mm-dd')" + " and insertTime<=" + "to_date('"
					+ msg[1] + "','yyyy-mm-dd')" + " ";
		}

		if ("all".equals(info[0]))
		{
			strWhere2 = " and c.Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)";
		}
		if ("area".equals(info[0]))
		{
			strWhere2 = " and (c.Area_ID="
					+ info[1]
					+ " and c.Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1))";
		}
		if ("arch".equals(info[0]))
		{
			strWhere2 = " and (c.Architecture_ID=" + info[1] + ")";
		}
		if ("floor".equals(info[0]))
		{
			strWhere2 = " and (c.Architecture_ID=" + info[1]
					+ ") and (c.Floor=" + info[2] + ")";
		}
		if ("meter".equals(info[0]))
		{
			strWhere2 = " and (c.AmMeter_ID=" + info[1] + ")";
		}

		String sql = "select "
				+ "ID,a.AmMeter_ID,c.AmMeter_Name,"
				+ "(select Architecture_Name from (Architecture)b where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"
				+ "(select Area_Name from (Area)b where b.Area_ID=c.Area_ID)Area_Name,Floor,"
				+ "OldAddress,NewAddress,OldZValue,NewZValue,OldSYValue,NewSYValue,isZQ,"
				+ "(select Users_Name from (users)b where a.Users_ID=b.Users_ID)Users_Name,"
				+ "to_char(insertTime,'yyyy-mm-dd hh24:mi:ss')insertTime "
				+ "from " + "(AmmeterReplce)a," + "(AmMeter)c " + "where "
				+ "a.AmMeter_ID=c.AmMeter_ID " + strWhere1 + strWhere2 + order;

		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			
			if(isPage==true)
			{
				setTotalCount(count);
				if (count <= (pageCount * pageIndex))
					return list;

				count = count - pageCount * pageIndex;

				if (count >= pageCount)
					count = pageCount;

				if ((pageCount * pageIndex) == 0)
					rs.beforeFirst();
				else
					rs.absolute(pageCount * pageIndex);
			}
			else
			{
				rs.beforeFirst();
			}

			while (rs.next() && (count > 0))
			{
				emm = new ExchangeMeterModel();
				emm.setID(rs.getInt("ID"));
				emm.setAmMeter_ID(rs.getInt("AmMeter_ID"));
				emm.setArea_Name(rs.getString("Area_Name"));
				emm.setArchitecture_Name(rs.getString("Architecture_Name"));
				emm.setFloorName(rs.getString("Floor"));
				emm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				emm.setOldAddress(rs.getString("OldAddress"));
				emm.setNewAddress(rs.getString("NewAddress"));
				emm.setOldZValue(rs.getFloat("OldZValue"));
				emm.setNewZValue(rs.getFloat("NewZValue"));
				emm.setOldSYValue(rs.getFloat("OldSYValue"));
				emm.setNewSYValue(rs.getFloat("NewSYValue"));

				String isZQ = rs.getString("isZQ");
				if ("1".equals(isZQ))
				{
					isZQ = "是";
				} else
				{
					isZQ = "否";
				}
				emm.setIsZQ(isZQ);
				emm.setUsers_Name(rs.getString("Users_Name"));
				emm.setInsertTime(rs.getString("insertTime"));

				list.add(emm);
				count--;
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return list;

	}

	public boolean insert(ExchangeMeterModel emm) throws SQLException
	{
		String sql = "INSERT INTO AMMETERREPLCE(AMMETER_ID,OLDADDRESS,NEWADDRESS,OLDZVALUE,NEWZVALUE,OLDSYVALUE,NEWSYVALUE,ISZQ,USERS_ID,REASON,REMARK,INSERTTIME)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi'))";
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		int id=0;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, emm.getAmMeter_ID());
			ps.setString(2, emm.getOldAddress());
			ps.setString(3, emm.getNewAddress());
			ps.setFloat(4, emm.getOldZValue());
			ps.setFloat(5, emm.getNewZValue());
			ps.setFloat(6, emm.getOldSYValue());
			ps.setFloat(7, emm.getNewSYValue());
			ps.setInt(8, Integer.parseInt(emm.getIsZQ()));
			ps.setInt(9, emm.getUSERS_ID());
			ps.setString(10, emm.getREASON());
			ps.setString(11, emm.getREMARK());
			ps.setString(12, emm.getInsertTime());

			b = (ps.executeUpdate() == 1);
			if(b)
			{
				Calendar cal=Calendar.getInstance();
			
				if(emm.getIsXZ().equals("1"))//启用修正
				{
					String sql1="update AmMeter set AmMeter_485Address='" + emm.getNewAddress()+ "',XIUZHENG="+(emm.getOldZValue()-emm.getNewZValue())+" where AmMeter_ID=" + emm.getAmMeter_ID();
					//String sql2="Delete from ZAMDATAS"+String.valueOf(emm.getAmMeter_ID());
					//String sql3="update T_DayAm set EndReadingDate=to_date(sysdate,'yyyy-mm-dd hh24:mi:ss'),EndZValueZY=0 " +
					//		"where AmMeter_ID=" + emm.getAmMeter_ID() + " and SelectYear=" +cal.get(Calendar.YEAR) + " and SelectMonth=" +cal.get(Calendar.MONTH)+1 + " and SelectDay=" + cal.get(Calendar.DAY_OF_MONTH);
					Statement st=null;
//					Connection conn0=null;
					try
					{
//						conn0 = ConnDB.getConnection();
						st = conn.createStatement();
						st.addBatch(sql1);
						//st.addBatch(sql2);
						//st.addBatch(sql3);
						int[] k=st.executeBatch();
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						if(st!=null){
							st.close();
						}
					}
				}
				else 
				{
				
					String sql1="update AmMeter set AmMeter_485Address='" + emm.getNewAddress()+ "' where AmMeter_ID=" + emm.getAmMeter_ID();
					String sql2="Delete from ZAMDATAS"+String.valueOf(emm.getAmMeter_ID());
					String sql3="update T_DayAm set EndReadingDate=to_date(sysdate,'yyyy-mm-dd hh24:mi:ss'),EndZValueZY="+emm.getNewZValue()+" " +
							"where AmMeter_ID=" + emm.getAmMeter_ID() + " and SelectYear=" +cal.get(Calendar.YEAR) + " and SelectMonth=" +cal.get(Calendar.MONTH)+1 + " and SelectDay=" + cal.get(Calendar.DAY_OF_MONTH);
					Statement st=null;
	//				Connection conn0=null;
					try
					{
	//					conn0 = ConnDB.getConnection();
						st = conn.createStatement();
						st.addBatch(sql1);
						st.addBatch(sql2);
						st.addBatch(sql3);
						int[] k=st.executeBatch();
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						if(st!=null){
							st.close();
						}
					}
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		
		return b;
	}
	
	public String getAmMeterReplaceInfoByID(int id){
		String str = "";
		String sql = "SELECT AMMETER_NAME,AMMETER_485ADDRESS from AMMETER where AMMETER_ID=(SELECT AMMETER_ID from AMMETERREPLCE where id="+id+")";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next())
			{
				str = rs.getString("AMMETER_NAME")+",表地址："+rs.getString("AMMETER_485ADDRESS");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return str;
	}

	public boolean delete(int ID) throws SQLException
	{
		String sql = "delete FROM AMMETERREPLCE where ID =  " + ID;
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	public boolean modify(ExchangeMeterModel emm) throws SQLException
	{
		String sql = "update AmmeterReplce set NewAddress=?,OldZValue=?,NewZValue=?,OldSYValue=?,NewSYValue=?,isZQ=?,Reason=?,Remark=? where ID="+emm.getID();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, emm.getNewAddress());
			ps.setFloat(2, emm.getOldZValue());
			ps.setFloat(3, emm.getNewZValue());
			ps.setFloat(4, emm.getOldSYValue());
			ps.setFloat(5, emm.getNewSYValue());
			ps.setInt(6, Integer.parseInt(emm.getIsZQ()));
			ps.setString(7, emm.getREASON());
			ps.setString(8, emm.getREMARK());
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		return b;
	}

	/**
	 * 新建换表记录查询旧表的信息
	 * 
	 * @param meterId
	 * @return
	 * @throws SQLException 
	 */
	public JSONArray get_oldMeterInfo_EMR(int meterId) throws SQLException
	{
		DecimalFormat df=new DecimalFormat("0.##");
		JSONObject jo=new JSONObject();
		JSONArray json=new JSONArray();
		String sql = "select AmMeter_ID,AmMeter_Name,"
				+ "(select Architecture_Name from (Architecture)b where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"
				+ "(select Area_Name from (Area)b where b.Area_ID=c.Area_ID)Area_Name,(Floor)FloorName,AmMeter_485Address,"
				+ "(select to_char(MAX(ValueTime),'yyyy-mm-dd hh24:mi:ss') from (AmMeterAPDatas)b where c.AmMeter_ID=b.AmMeter_ID)APValueTime,"
				+ "(select to_char(MAX(ValueTime),'yyyy-mm-dd hh24:mi:ss') from (ZAMDATAS"+String.valueOf(meterId)+")b)ZYValueTime from (AmMeter)c "
						+ "where AmMeter_ID="
				+ meterId;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				String meterPotocol = (new CMMeterDao()).getMeterPotocol(meterId);
				jo.put("meterPotocol", meterPotocol);
				jo.put("Area_Name", rs.getString("Area_Name"));
				jo.put("Architecture_Name", rs.getString("Architecture_Name"));
				jo.put("FloorName", rs.getString("FloorName"));
				jo.put("AmMeter_Name", rs.getString("AmMeter_Name"));
				jo.put("AmMeter_485Address", rs.getString("AmMeter_485Address"));
				String apTime=rs.getString("APValueTime");
				//System.out.println(" czy:"+apTime);
				if (apTime!= null&&!"".equals(apTime))
	            {
					String sysql = "select SYValue,SYMoney from AmMeterAPDatas where AmMeter_ID="+meterId+" and ValueTime=to_date('"+apTime+"','yyyy-mm-dd hh24:mi:ss')";

					PreparedStatement syps=null;
					ResultSet syrs=null;
//					Connection conns = null;
					try
					{
//						conns = ConnDB.getConnection();
						syps = conn.prepareStatement(sysql);
						syrs = syps.executeQuery();
						if(syrs.next())
						{
							if("1".equals(meterPotocol)){
								jo.put("SYValue", df.format(syrs.getFloat("SYValue")));
							}else {
								jo.put("SYValue", df.format(syrs.getFloat("SYMoney")));
							}
							
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(syps, syrs);
					}
		
	            }else
	            {
	            	jo.put("SYValue", "0.00");
	            }
				
				String Time=rs.getString("ZYValueTime");
				//System.out.println(" czy:"+Time);
				if (Time!= null&&!"".equals(Time))
	            {
					String sysql1 = "select zvaluezy from ZAMDATAS"+String.valueOf(meterId)+" "
							+ "where ValueTime=to_date('"+Time+"','yyyy-mm-dd hh24:mi:ss')";

					PreparedStatement syps1=null;
					ResultSet syrs1=null;
//					Connection conns1 = null;
					try
					{
//						conns1 =ConnDB.getConnection();
						syps1 = conn.prepareStatement(sysql1);
						syrs1 = syps1.executeQuery();
						if(syrs1.next())
						{
							jo.put("zvaluezy", df.format(syrs1.getFloat(1)));
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release( syps1, syrs1);
					}
	            }
				else
				{
					jo.put("zvaluezy","0.00");
				}
				
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		json.add(jo);
		return json;
	}

	
	public JSONArray get_EditMeterInfo_EMR(int meterId) throws SQLException
	{
		DecimalFormat df=new DecimalFormat("0.##");
		JSONObject jo=new JSONObject();
		JSONArray json=new JSONArray();
		String sql = "select a.ID,a.AmMeter_ID,c.AmMeter_Name,to_char(a.insertTime,'yyyy-mm-dd hh24:mi:ss'),(select Architecture_Name from (Architecture) b where b.Architecture_ID=c.Architecture_ID)Architecture_Name,(select Area_Name from (Area)b where b.Area_ID=c.Area_ID)Area_Name,(Floor)FloorName,OldAddress,NewAddress,OldZValue,NewZValue,OldSYValue,NewSYValue,isZQ,Reason,Remark,(select Users_Name from (users)b where a.Users_ID=b.Users_ID)Users_Name,to_char(insertTime,'yyyy-mm-dd hh24:mi:ss')insertTime from (AmmeterReplce)a,(AmMeter )c where a.AmMeter_ID=c.AmMeter_ID and ID="+meterId;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				String meterPotocol = (new CMMeterDao()).getMeterPotocol(rs.getInt("AmMeter_ID"));
				jo.put("meterPotocol", meterPotocol);
				jo.put("AmMeter_ID", rs.getString("AmMeter_ID"));
				jo.put("Area_Name", rs.getString("Area_Name"));
				jo.put("Architecture_Name", rs.getString("Architecture_Name"));
				jo.put("FloorName", rs.getString("FloorName"));
				jo.put("AmMeter_Name", rs.getString("AmMeter_Name"));
				
				jo.put("OldAddress", rs.getString("OldAddress"));
				jo.put("NewAddress", rs.getString("NewAddress"));
				jo.put("OldZValue", rs.getString("OldZValue"));
				jo.put("OldSYValue", rs.getString("OldSYValue"));
				jo.put("NewZValue", rs.getString("NewZValue"));
				
				jo.put("NewSYValue", rs.getString("NewSYValue"));
				jo.put("insertTime", rs.getString("insertTime"));
				jo.put("Users_Name", rs.getString("Users_Name"));
				jo.put("isZQ", rs.getString("isZQ"));
				jo.put("Reason", rs.getString("Reason"));
				
				jo.put("Remark", rs.getString("Remark"));
			}
			
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		json.add(jo);
		return json;
	}

	public int getAmmeterID(int eMID)
	{
		int id = 0;
		String sql = "select AMMETER_ID from AMMETERREPLCE where id="+eMID;
		
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null; 
		
		try
		{
			connection = ConnDB.getConnection();
			pst = connection.prepareStatement(sql);
			rs = pst.executeQuery();
			if(rs.next()){
				id = rs.getInt("AMMETER_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(connection, pst, rs);
		}
		
		return id;
	}

}
