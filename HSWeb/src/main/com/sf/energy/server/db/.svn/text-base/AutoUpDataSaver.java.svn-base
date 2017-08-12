package com.sf.energy.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.w3c.dom.Element;

import com.sf.configuration.Configuration;
import com.sf.energy.util.ConnDB;
import com.sun.mail.imap.Utility.Condition;

/**
 * <存储单相三相数据到数据库> 
 * 
 * @author lujinquan、cuizhengyang
 * @version v1.0
 * @since 服务程序
 */
public class AutoUpDataSaver
{
	
	/**
	 * 主动上传的单项数据的存储
	 * @param root XML的节点
	 * @throws Exception
	 */
	public static void autoUpSave(Element root) throws Exception
	{
		String DG_Address = root.getChildNodes().item(0).getFirstChild().getNodeValue();
		String meterType = root.getChildNodes().item(1).getFirstChild().getNodeValue();
		String Meter_Address = root.getChildNodes().item(2).getFirstChild().getNodeValue();
		String Command_Code = root.getChildNodes().item(3).getFirstChild().getNodeValue();
		String Read_Time = root.getChildNodes().item(4).getFirstChild().getNodeValue();
		String ZY0 = root.getChildNodes().item(5).getFirstChild().getNodeValue();
		int result =100;
		if (meterType.isEmpty() || meterType.equals("1"))
		{
			result = dataSave(DG_Address, meterType, Meter_Address,
					Command_Code, Read_Time, ZY0);
		}
		// 存储水表
		else if (meterType.equals("2"))
		{
			result=waterDataSave(DG_Address, meterType, Meter_Address,
					Command_Code, Read_Time, ZY0);
		} else
		{
			//其它的时候，返回错误
			result=-1;
			return;	
		}
		if (result != 0) //失败
        {
            exceptionAlarm(meterType, Meter_Address, Read_Time, ZY0, result);
        }
	}
	
	/**
	 * 存储主动上传的三相数据
	 * @param root XML的节点
	 * @throws Exception
	 */
	public static void autoUpSaveThreePhase(Element root) throws Exception
	{
		//三相数据
		ArrayList<String> data = new ArrayList<String>();

		data.add(root.getChildNodes().item(0).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(1).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(2).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(3).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(4).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(5).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(6).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(7).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(8).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(9).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(10).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(11).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(12).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(13).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(14).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(15).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(16).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(17).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(18).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(19).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(20).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(21).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(22).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(23).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(24).getFirstChild().getNodeValue());
		data.add(root.getChildNodes().item(25).getFirstChild().getNodeValue());

		int result = 100;
		
		result = AutoUpDataSaver.dataSaveThreePhase(data);
		
		if (result != 0) //失败
        {
            exceptionAlarm("1", data.get(1), data.get(2), "三相数据", result);
        }
	}
	
	/**
	 * 对报警的处理
	 * @param meterType 设备类型	
	 * @param meter_Address 设备地址
	 * @param valueTime 存储时间	
	 * @param dataValue 存储的值的类型
	 * @param errCode   错误代码
	 * @throws SQLException
	 */
	private static void exceptionAlarm(String meterType, String meter_Address,
			String valueTime, String dataValue, int errCode) throws SQLException
	{
		String taskStyle = "", errorKind = "";
        switch (meterType)
        {
            case "1": //电表
                if (errCode == 1) //时间超前
                {
                    taskStyle = "05";
                    errorKind = "时间大于当前时间";         
                    amMeterError(taskStyle, meter_Address, valueTime, dataValue, errorKind);
                }
                else if (errCode == 2) //倒走
                {
                    taskStyle = "06";
                    errorKind = "示数小于最近一次示数";
                    amMeterError(taskStyle, meter_Address, valueTime, dataValue, errorKind);
                }
                break;
            case "2": //水表
                if (errCode == 101) //时间超前
                {
                    taskStyle = "15";
                    errorKind = "时间大于当前时间";
                  //  WaMeterError(taskstyle, meter_Address, valueTime, dataValue, errorkind);
                }
                else if (errCode == 102) //倒走
                {
                    taskStyle = "16";
                    errorKind = "示数小于最近一次示数";
                    //WaMeterError(taskstyle, meter_Address, valueTime, dataValue, errorkind);
                }
                break;
            default: break;
        }
        
        if(!errorKind.equals("") && errorKind != null)
        {
        	//System.out.println("错误类型为：" + errorKind);
        }
		
	}
	/**
	 * 电表错误的处理
	 * @param taskstyle 任务类型
	 * @param meter_Address 设备地址
	 * @param valueTime 存储时间
	 * @param dataValue	单项还是三项数据
	 * @param errorkind 错误类型
	 * @throws SQLException
	 */
	private static void amMeterError(String taskstyle, String meter_Address,
			String valueTime, String dataValue, String errorkind) throws SQLException
	{
		String planID=null;
		String sql = "select PlanTask_ID from PlanTask where PlanTask_Style='" + taskstyle + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if(rs.next())
			{
				planID=rs.getString("PlanTask_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if(rs.next())
//		{
//			planID=rs.getString("PlanTask_ID");
//		}

	}

	/**
	 * 存储单项数据到数据库
	 * @param gatherAddress  中转站地址
	 * @param meterType 设备类型
	 * @param meterAddress 设备地址
	 * @param commandCode  命令代码 
	 * @param readTime 读取时间  
	 * @param datavalue 单项数据值
	 * @throws SQLException 
	 * @throws ParseException 
	 * @throws Exception
	 */
	public static int dataSave(String gatherAddress, String meterType,
			String meterAddress, String commandCode, String readTime,
			String dataValue) throws SQLException, ParseException
	{
		// TODO Auto-generated constructor stub
		int meterID = 0;
		String beilv = null;
		String xiuZheng = null;
		double inValue=0;
		String value;
		int cnt = 0;
		double zValueZY=0;
		String sql=null;
		int result = 100;
		String limitPart = null;
		String valueTime = null;

		//获取meterID,倍率，修正参数
		sql = "select AmMeter_ID,nvl(beilv,1) as beilv,nvl(Xiuzheng,0) as Xiuzheng from VES_AmMeter where Gather_Address='"
				+ gatherAddress + "' and AmMeter_485Address='" + meterAddress
				+ "'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				meterID = rs.getInt(1);
				beilv = rs.getString(2);
				xiuZheng = rs.getString(3);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			meterID = rs.getInt(1);
//			beilv = rs.getString(2);
//			xiuZheng = rs.getString(3);
//		}
		
//		// 获取limitPart
//		sql = "select nvl(LimitPart,0) from VES_AmMeter where Gather_Address='"
//				+ gatherAddress + "' and AmMeter_485Address='" + meterAddress
//				+ "'";
//		rs = ConnDB.executeQuery(sql);
//		if (rs.next())
//		{
//			limitPart = rs.getString(1);
//		}
		
		//对meterID 进行判断，校验
		if (meterID == 0)
		{
			result = -1;
			return result;
		}

		// 电表时只存了当前正向有功总电量
		// 水表时只存了当前正向流量
		if (commandCode.equals("0C0110") || commandCode.equals("0c0110")
				|| commandCode.equals("0C0164") || commandCode.equals("0c0164")
				|| commandCode.equals("0D0264") || commandCode.equals("0d0264")
				|| commandCode.isEmpty())
		{
			DecimalFormat numDf = new DecimalFormat("#.000");
			inValue= Double.parseDouble(dataValue) * Double.parseDouble(beilv)
					+ Double.parseDouble(xiuZheng);
			value=numDf.format(inValue);
			inValue=Double.parseDouble(value);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date readDate = df.parse(readTime);
			Date date = new Date();
			// 插入的时间应该比当前的时间小，若大于，则报错
			if (readDate.after(date))
			{
				sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
						+ meterID
						+ ", to_date('"
						+ readTime
						+ "','yyyy-mm-dd hh24:mi:ss'),"+ inValue+",1)";
				PreparedStatement ps3 = null;
				Connection conn3 = null;
				try
				{
					conn3 = ConnDB.getConnection();
					ps3 = conn3.prepareStatement(sql);
					ps3.executeUpdate();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn3, ps3);
				}
				
				result = 1;
				return result;
			}
			
			//对ZValueZY进行判定
			zValueZY = -1;
			sql = "select ZValueZY,ValueTime  from ( select * from ( select * from ZAMDATAS"+String.valueOf(meterID)+" where ValueTime < to_date('"
					+ readTime
					+ "','yyyy-mm-dd hh24:mi:ss')  order by ValueTime desc) where rownum =1 order by rownum asc) a";
			PreparedStatement ps8 = null;
			ResultSet rs8 = null;
			Connection conn8 = null;
			try
			{
				conn8 = ConnDB.getConnection();
				ps8 = conn8.prepareStatement(sql);
				rs8 = ps8.executeQuery();
				if (rs8.next())
				{
					zValueZY = rs8.getDouble(1);
					valueTime = rs8.getString(2);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn8, ps8, rs8);
			}
			
			if (inValue < zValueZY)
			{
				sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
						+ meterID
						+ ",to_date('"
						+ readTime
						+ "','yyyy-mm-dd hh24:mi:ss'),"
						+ inValue
						+ ",2)";
				Connection conn4 = null;
				PreparedStatement ps4 = null;
				try
				{
					conn4 = ConnDB.getConnection();
					ps4 = conn4.prepareStatement(sql);
					ps4.executeUpdate();
					
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn4, ps4);
				}
				result = 2;
				return result;
			}
			zValueZY = -1;
			
			//对cnt进行判定
			sql = "select count(ValueTime)  from ZAMDATAS"+String.valueOf(meterID)+" where ValueTime = to_date('" + readTime + "','yyyy-mm-dd hh24:mi:ss')";
			PreparedStatement ps6 =null;
			ResultSet rs6=null;
			Connection conn6 = null;
			try
			{
				conn6 = ConnDB.getConnection();
				ps6 = conn6.prepareStatement(sql);
				rs6 = ps6.executeQuery();
				if (rs6.next())
				{
					cnt = rs6.getInt(1);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn6, ps6, rs6);
			}

			if (cnt > 0)
			{
				sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
						+ meterID
						+ ",to_date('"
						+ readTime
						+ "','yyyy-mm-dd hh24:mi:ss'),"
						+ inValue
						+ ",3)";
				Connection conn5 = null;
				PreparedStatement ps5 = null;
				try
				{
					conn5  =ConnDB.getConnection();
					ps5 = conn5.prepareStatement(sql);
					ps5.executeUpdate();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn5, ps5);
				}
				result = 3;
				return result;
			}
			
//			// 检查限值
//			if (Double.parseDouble(limitPart) > 0)
//			{
//				double gross;
//				double time;
//				gross = inValue - zValueZY;
//				SimpleDateFormat dateformat = new SimpleDateFormat(
//						"yyyy-MM-dd HH:mm:ss");
//				long diff = dateformat.parse(valueTime).getTime()
//						- dateformat.parse(readTime).getTime();
//				time = diff / (1000 * 60);
//				if (time == 0)
//				{
//					time = 1;
//				}
//				if (Math.abs((gross * 60) / time) > Double
//						.parseDouble(limitPart) * Double.parseDouble(beilv))
//				{
//					sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
//							+ meterID
//							+ ",to_date('"
//							+ readTime
//							+ "','yyyy-mm-dd hh24:mi:ss'),to_number("
//							+ inValue
//							+ ",'9999999999999999.99')),4";
//					ConnDB.executeUpdate(sql);
//					result = 4;
//					return result;
//				}
//			}
			
			sql = " insert into ZAMDATAS"+String.valueOf(meterID)+"(ValueTime,ZValueZY) values("
					+ "to_date('"
					+ readTime
					+ "','yyyy-mm-dd hh24:mi:ss'),"
					+ inValue
					+ ")";
			PreparedStatement ps7 = null;
			Connection conn7=null;
			try
			{
				conn7 =ConnDB.getConnection();
				ps7 = conn7.prepareStatement(sql);
				ps7.executeUpdate();
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn7, ps7);
			}
			result = 0;
			return result;

		    }
		
		return result;
	}

	/**
	 * 存储三相数据到数据库
	 * @param data 三相数据集
	 * @return 返回是否成功或者报警标识
	 * @throws Exception
	 */
	public static int dataSaveThreePhase(ArrayList<String> data) throws Exception
	{
		// 三相数据赋值
		String gatherAddress = data.get(0);
		String meterAddress = data.get(1);
		String readTime = data.get(2);
		String powerZY = data.get(3);
		String powerAY = data.get(4);
		String powerBY = data.get(5);
		String powerCY = data.get(6);
		String powerZW = data.get(7);
		String powerAW = data.get(8);
		String powerBW = data.get(9);
		String powerCW = data.get(10);
		String powerFactorZ = data.get(11);
		String powerFactorA = data.get(12);
		String powerFactorB = data.get(13);
		String powerFactorC = data.get(14);
		String voltageA = data.get(15);
		String voltageB = data.get(16);
		String voltageC = data.get(17);
		String currentA = data.get(18);
		String currentB = data.get(19);
		String currentC = data.get(20);
		String current0 = data.get(21);
		String powerSZZ = data.get(22);
		String powerSZA = data.get(23);
		String powerSZB = data.get(24);
		String powerSZC = data.get(25);
		
		// 结果标识
		int result = 0;
		int meterID = 0;
		int count = 0;
		String sqlInsert = null;
		String sqlValue = null;
		
		String sql ="select AmMeter_ID from VES_AmMeter where Gather_Address = '" + gatherAddress + "' and AmMeter_485Address = '" + meterAddress + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				meterID = rs.getInt(1);
			}
			else 
			{
				result = -1;
				return result;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		if (rs.next())
//		{
//			meterID = rs.getInt(1);
//			if(rs != null)
//			{
//				rs.close();
//			}
//			if(sm != null)
//			{
//				sm.close();
//			}
//		}
//		else 
//		{
//			result = -1;
//			if(rs != null)
//			{
//				rs.close();
//			}
//			if(sm != null)
//			{
//				sm.close();
//			}
//			return result;
//		}
		
		
		if(!powerZY.equals("-1"))
		{
			powerZY = String.valueOf(Math.abs(Double.parseDouble(powerZY)));
		}
		
		if(!powerAY.equals("-1"))
		{
			powerAY = String.valueOf(Math.abs(Double.parseDouble(powerAY)));
		}
		
		if(!powerBY.equals("-1"))
		{
			powerBY = String.valueOf(Math.abs(Double.parseDouble(powerBY)));
		}
		
		if(!powerCY.equals("-1"))
		{
			powerCY = String.valueOf(Math.abs(Double.parseDouble(powerCY)));
		}
		
		if(!powerZW.equals("-1"))
		{
			powerZW = String.valueOf(Math.abs(Double.parseDouble(powerZW)));
		}
		
		if(!powerAW.equals("-1"))
		{
			powerAW = String.valueOf(Math.abs(Double.parseDouble(powerAW)));
		}
		
		if(!powerBW.equals("-1"))
		{
			powerBW = String.valueOf(Math.abs(Double.parseDouble(powerBW)));
		}
		
		
		if(!powerCW.equals("-1"))
		{
			powerCW = String.valueOf(Math.abs(Double.parseDouble(powerCW)));
		}
		
		if(!powerFactorZ.equals("-1"))
		{
			powerFactorZ = String.valueOf(Math.abs(Double.parseDouble(powerFactorZ)));
		}
		
		if(!powerFactorA.equals("-1"))
		{
			powerFactorA = String.valueOf(Math.abs(Double.parseDouble(powerFactorA)));
		}
		
		if(!powerFactorB.equals("-1"))
		{
			powerFactorB = String.valueOf(Math.abs(Double.parseDouble(powerFactorB)));
		}
		
		if(!powerFactorC.equals("-1"))
		{
			powerFactorC = String.valueOf(Math.abs(Double.parseDouble(powerFactorC)));
		}
		
		if(!voltageA.equals("-1"))
		{
			voltageA = String.valueOf(Math.abs(Double.parseDouble(voltageA)));
		}
		
		if(!voltageB.equals("-1"))
		{
			voltageB = String.valueOf(Math.abs(Double.parseDouble(voltageB)));
		}
		
		if(!voltageC.equals("-1"))
		{
			voltageC = String.valueOf(Math.abs(Double.parseDouble(voltageC)));
		}
		
		if(!currentA.equals("-1"))
		{
			currentA = String.valueOf(Math.abs(Double.parseDouble(currentA)));
		}
		
		if(!currentB.equals("-1"))
		{
			currentB = String.valueOf(Math.abs(Double.parseDouble(currentB)));
		}
		
		if(!currentC.equals("-1"))
		{
			currentC = String.valueOf(Math.abs(Double.parseDouble(currentC)));
		}
		
		if(!current0.equals("-1"))
		{
			current0 = String.valueOf(Math.abs(Double.parseDouble(current0)));
		}
		
		if(!powerSZZ.equals("-1"))
		{
			powerSZZ = String.valueOf(Math.abs(Double.parseDouble(powerSZZ)));
		}
		
		if(!powerSZA.equals("-1"))
		{
			powerSZA = String.valueOf(Math.abs(Double.parseDouble(powerSZA)));
		}
		
		if(!powerSZB.equals("-1"))
		{
			powerSZB = String.valueOf(Math.abs(Double.parseDouble(powerSZB)));
		}
		
		if(!powerSZC.equals("-1"))
		{
			powerSZC = String.valueOf(Math.abs(Double.parseDouble(powerSZC)));
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date readDate = df.parse(readTime);
		Date date = new Date();
		// 如果纪录插入时间在系统当前时间之后，则报错
		if (readDate.after(date))
		{
			sql = "insert into AmMeterPDErrorDatas(AmMeter_ID,ValueTime,PowerZY,PowerAY,PowerBY,PowerCY,PowerZW,PowerAW,PowerBW,PowerCW,PowerFactorZ,PowerFactorA,PowerFactorB,PowerFactorC,VoltageA,VoltageB,VoltageC,CurrentA,CurrentB,CurrentC,Current0,PowerSZZ,PowerSZA,PowerSZB,PowerSZC,ErrorCode) "
				  +	"values( " + meterID + " , to_date('" + readTime + "','yyyy-mm-dd hh24:mi:ss')," 
				  + powerZY + "," + powerAY + "," + powerBY + "," + powerCY + "," + powerZW + "," + powerAW + "," + powerBW + "," 
				  + powerCW + "," + powerFactorZ + "," + powerFactorA + "," + powerFactorB + "," + powerFactorC + "," + voltageA 
				  + "," + voltageB + "," + voltageC + "," + currentA + "," + currentB + "," + currentC + "," + current0 + "," 
				  + powerSZZ + "," + powerSZA + "," + powerSZB + "," + powerSZC + ",1)";
	
			PreparedStatement ps1=null;
			Connection conn1 = null;
			try
			{
				conn1 = ConnDB.getConnection();
				ps1 = conn1.prepareStatement(sql);
				ps1.executeUpdate();
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn1, ps1);
			}
			result = 1;
			
			return result;
		}
		
		sql = "select  count(valuetime) from ZPDDATAS"+String.valueOf(meterID)+" where to_char(valuetime,'yyyy-mm-dd hh24:mi:ss') = '" + readTime + "'";
		PreparedStatement ps2=null;
		ResultSet rs2=null;
		Connection conn2 = null;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql);
			rs2 = ps2.executeQuery();
			if (rs2.next())
			{
				count = rs2.getInt(1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, ps2, rs2);
		}
		
		sqlInsert = "insert into ZPDDATAS"+String.valueOf(meterID)+"(ValueTime";
		sqlValue = "values(to_date('" + readTime + "','yyyy-mm-dd hh24:mi:ss')";
	
		if(!powerZY.equals("") && powerZY != null)
		{
			sqlInsert += ", powerZY";
			sqlValue += "," + powerZY;
		}
		
		if(!powerAY.equals("") && powerAY != null)
		{
			sqlInsert += ", powerAY";
			sqlValue += "," + powerAY;
		}
		
		if(!powerBY.equals("") && powerBY != null)
		{
			sqlInsert += ", powerBY";
			sqlValue += "," + powerBY;
		}
		
		if(!powerCY.equals("") && powerCY != null)
		{
			sqlInsert += ", powerCY";
			sqlValue += "," + powerCY;
		}
		
		if(!powerZW.equals("") && powerZW != null)
		{
			sqlInsert += ", powerZW";
			sqlValue += "," + powerZW;
		}
		
		if(!powerAW.equals("") && powerAW != null)
		{
			sqlInsert += ", powerAW";
			sqlValue += "," + powerAW;
		}
		
		if(!powerBW.equals("") && powerBW != null)
		{
			sqlInsert += ", powerBW";
			sqlValue += "," + powerBW;
		}
		
		if(!powerCW.equals("") && powerCW != null)
		{
			sqlInsert += ", powerCW";
			sqlValue += "," + powerCW;
		}
		
		if(!powerFactorZ.equals("") && powerFactorZ != null)
		{
			sqlInsert += ", powerFactorZ";
			sqlValue += "," + powerFactorZ;
		}
		
		if(!powerFactorA.equals("") && powerFactorA != null)
		{
			sqlInsert += ", powerFactorA";
			sqlValue += "," + powerFactorA;
		}
		
		if(!powerFactorB.equals("") && powerFactorB != null)
		{
			sqlInsert += ", powerFactorB";
			sqlValue += "," + powerFactorB;
		}
		
		if(!powerFactorC.equals("") && powerFactorC != null)
		{
			sqlInsert += ", powerFactorC";
			sqlValue += "," + powerFactorC;
		}
		
		if(!voltageA.equals("") && voltageA != null)
		{
			sqlInsert += ", voltageA";
			sqlValue += "," + voltageA;
		}
		
		if(!voltageB.equals("") && voltageB != null)
		{
			sqlInsert += ", voltageB";
			sqlValue += "," + voltageB;
		}
		
		if(!voltageC.equals("") && voltageC != null)
		{
			sqlInsert += ", voltageC";
			sqlValue += "," + voltageC;
		}
		
		if(!currentA.equals("") && currentA != null)
		{
			sqlInsert += ", currentA";
			sqlValue += "," + currentA;
		}
		
		if(!currentB.equals("") && currentB != null)
		{
			sqlInsert += ", currentB";
			sqlValue += "," + currentB;
		}
		
		if(!currentC.equals("") && currentC != null)
		{
			sqlInsert += ", currentC";
			sqlValue += "," + currentC;
		}
		
		if(!current0.equals("") && current0 != null)
		{
			sqlInsert += ", current0";
			sqlValue += "," + current0;
		}
		
		if(!powerSZZ.equals("") && powerSZZ != null)
		{
			sqlInsert += ", powerSZZ";
			sqlValue += "," + powerSZZ;
		}
		
		if(!powerSZA.equals("") && powerSZA != null)
		{
			sqlInsert += ", powerSZA";
			sqlValue += "," + powerSZA;
		}
		
		if(!powerSZB.equals("") && powerSZB != null)
		{
			sqlInsert += ", powerSZB";
			sqlValue += "," + powerSZB;
		}
		
		if(!powerSZC.equals("") && powerSZC != null)
		{
			sqlInsert += ", powerSZC";
			sqlValue += "," + powerSZC;
		}
		
		sqlInsert +=  ")";
		sqlValue += ")";
		sql = sqlInsert + sqlValue;
		if(count == 0)
		{
			PreparedStatement ps3=null;
			Connection conn3 = null;
			try
			{
				conn3 = ConnDB.getConnection();
				ps3 = conn3.prepareStatement(sql);
				ps3.executeUpdate();
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn3, ps3);
			}
		}
		
		return result;
	}
	
	public static int waterDataSave(String gatherAddress, String meterType,
			String meterAddress, String commandCode, String readTime,
			String dataValue) throws Exception
	{
		int meterID = 0;
		String beilv = "";
		String xiuZheng = "";
		double inValue = 0;
		int cnt = 0;
		double zValueZY = 0;
		String sql = "";
		String valueTime = "";
		int result = 100;

		// 获取meterID,倍率，修正参数
		sql = "select WaterMeter_ID,nvl(beilv,1) as beilv,nvl(Xiuzheng,0) as xiuzheng from VES_WaterMeter where Gather_Address='"
				+ gatherAddress + "' and WaterMeter_485Address='" + meterAddress + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				meterID = rs.getInt("WaterMeter_ID");
				beilv = rs.getString("beilv");
				xiuZheng = rs.getString("xiuzheng");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		
//		if (rs.next())
//		{
//			meterID = rs.getInt("WaterMeter_ID");
//			beilv = rs.getString("beilv");
//			xiuZheng = rs.getString("xiuzheng");
//		}
	
		// 对meterID 进行判断，校验
		if (meterID == 0)
		{
			result = -1;
			return result;
		}

		// 水表时只存了当前正向流量
		if ("0C0110".equals(commandCode) || "0c0110".equals(commandCode)
				|| "0C0164".equals(commandCode) || "0c0164".equals(commandCode)
				|| "0D0264".equals(commandCode) || "0d0264".equals(commandCode)
				|| "".equals(commandCode) || commandCode == null)
		{
			inValue = Double.parseDouble(dataValue) * Double.parseDouble(beilv)
					+ Double.parseDouble(xiuZheng);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date readDate = df.parse(readTime);
			Date date = new Date();
			// 插入的时间应该比当前的时间小，若大于，则报错
			if (readDate.after(date))
			{
				sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
						+ meterID
						+ ", to_date('"
						+ readTime
						+ "','yyyy-mm-dd hh24:mi:ss'),to_number("
						+ inValue
						+ ",'9999999999999999.99'),101)";
				PreparedStatement ps1=null;
				Connection conn1 = null;
				try
				{
					conn1= ConnDB.getConnection();
					ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps1.executeUpdate();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn1, ps1);
				}
				result = 101;
				return result;
			}
			
			//对ZValueZY进行判定
			zValueZY = -1;
			sql = "select ZValueZY  from ( select * from ( select * from ZWATERDATAS"+String.valueOf(meterID)+" where ValueTime < to_date('"
					+ readTime
					+ "','yyyy-mm-dd hh24:mi:ss')  order by ValueTime desc) where rownum =1 order by rownum asc) a";
			PreparedStatement ps2=null;
			ResultSet rs2=null;
			Connection conn2 = null;
			try
			{
				conn2 = ConnDB.getConnection();
				ps2 = conn2.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs2 = ps2.executeQuery();
				if (rs2.next())
				{
					zValueZY = rs2.getDouble(1);
				}
			} catch (Exception e)
			{

				e.printStackTrace();
			}finally{
				ConnDB.release(conn2, ps2);
			}
			if (inValue < zValueZY)
			{
				sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
						+ meterID
						+ ",to_date('"
						+ readTime
						+ "','yyyy-mm-dd hh24:mi:ss'),to_number("
						+ inValue
						+ ",'9999999999999999.99'),102)";
				PreparedStatement ps3=null;
				Connection conn3 = null;
				try
				{
					conn3 = ConnDB.getConnection();
					ps3 = conn3.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps3.executeUpdate();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn3, ps3);
				}
				result = 102;
				return result;
			}
			zValueZY = -1;
			
			//对cnt进行判定
			sql = "select count(ValueTime)  from ZWATERDATAS"+String.valueOf(meterID)+" where ValueTime = to_date('" + readTime + "','yyyy-mm-dd hh24:mi:ss')";
			PreparedStatement ps4=null;
			ResultSet rs4=null;
			Connection conn4 = null;
			try
			{
				conn4 = ConnDB.getConnection();
				ps4 = conn4.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs4 = ps4.executeQuery();
				if (rs4.next())
				{
					cnt = rs4.getInt(1);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn4, ps4, rs4);
			}
			if (cnt > 0)
			{
				sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
						+ meterID
						+ ",to_date('"
						+ readTime
						+ "','yyyy-mm-dd hh24:mi:ss'),to_number("
						+ inValue
						+ ",'9999999999999999.99'),103)";
				PreparedStatement ps5=null;
				Connection conn5 = null;
				try
				{
					conn5 = ConnDB.getConnection();
					ps5 = conn5.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps5.executeUpdate();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn5, ps5);
				}
				result = 103;
				return result;
			}
			String ifadddec=	 Configuration.getString("server.IfAddDec");
			if (ifadddec.equals("1"))//添加三位随机小数 李嵘20160906
			{
				if (inValue*1000%1000==0)//如果小数位是0则模拟三位小数
				{
					inValue=AddDec(inValue,zValueZY);
				}
			}
			sql = " insert into ZWATERDATAS"+String.valueOf(meterID)+"(ValueTime,ZValueZY) values(to_date('"
					+ readTime
					+ "','yyyy-mm-dd hh24:mi:ss'),to_number("
					+ inValue
					+ ",'9999999999999999.99'))";
			PreparedStatement ps6=null;
			Connection conn6= null;
			try
			{
				conn6 = ConnDB.getConnection();
				ps6 = conn6.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps6.executeUpdate();
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn6, ps6);
			}
			result = 0;
			return result;
		}

		return result;
	}
	
	/*
	 * 生成模拟两位小数
	 * invalue 原始数据
	 * zvaluezynext 下次最小值
	 */
	private static double AddDec(double inValue,double zValueZYnext)
	{
		if(zValueZYnext==-1)
		{
			return inValue;
		}
		if(inValue>zValueZYnext)
		{
			return inValue;
		}
		double rtdec=0;
		double maxlen=zValueZYnext-inValue;//10.2-10
		
		if (maxlen>1)
		{
			return zValueZYnext;
		}
		else if (maxlen<0){
			return zValueZYnext;
		}
		else
		{//在小数点之间
			Random ra =new Random();
			rtdec=ra.nextDouble()*maxlen;
			DecimalFormat numDf = new DecimalFormat(".000");
			rtdec = Double.parseDouble(numDf.format(rtdec));
			return zValueZYnext+rtdec;
		}
	}
	

}
