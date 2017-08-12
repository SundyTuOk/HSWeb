package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sf.energy.prepayment.model.XMLCoder;
import com.sf.energy.util.ConnDB;

public class CMMsendCommand
{

	private String terminaladdress;
	private String terminalip;
	private String way;
	private String pw;
	private String Protocol;
	private String AmMeter_485Address;
	private String AmMeter_Password;
	private String AmMeter_Stat;
	private String Meter_Port;
	private String Meter_Sulv;
	private String Meter_JiaoYan;
	private float BeiLv;
	private String GValue;
	private String AmMeter_Name;
	private String Price_ID;
	private String meterProtocol;
	private String WaterMeter_Name;
	private String WaterMeter_485Address;
	private String WaterMeter_Password;
	private String WaterMeter_Stat;
	private String datasite_id;
	public String getDatasite_id()
	{
		return datasite_id;
	}
	public void setDatasite_id(String datasite_id)
	{
		this.datasite_id = datasite_id;
	}
	/**
	 * 读三相智能监测仪命令
	 * 
	 * @param amMeterID
	 * @param userID 
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder readSanXiangJianCeYiDanXiang(int amMeterID, String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);
            
			String PartList = "powermonitor"+","+"03"+"," + Meter_Port + "," + Meter_Sulv
					+ ","+Meter_JiaoYan+",1,5,10," + AmMeter_485Address + ","
					 + "0200,0010,";
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}
	public XMLCoder readSanXiangJianCeYiSanXiang(int amMeterID, String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);
            
			String PartList = "powermonitor"+","+"03"+"," + Meter_Port + "," + Meter_Sulv
					+ ","+Meter_JiaoYan+",1,5,10," + AmMeter_485Address + ","
					 + "0000,0052,";
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}
	/**
	 * 设置拉闸报警量
	 * @param amMeterId
	 * @param userID
	 * @return
	 * @throws SQLException 
	 */
	public XMLCoder setLaZhaValue(int amMeterID, String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{

				return null;
			}*/
			String laZhaValue = null;
			
			String sql="select LZVALUE from PRICE where PRICE_ID=(select PRICE_ID from AMMETER WHERE AMMETER_ID="+amMeterID+")";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					laZhaValue=rs.getString("LZVALUE");//BJVALUE
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}
			
			
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);
            String dataid="04001002";
            //非07表不发
            if (!meterProtocol.equals("30"))
            {
            	return null;
            }
            xc.setDataid(dataid);
            xc.setMeterAddress(AmMeter_485Address);
			String PartList = meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
					+ AmMeter_Password + ","+laZhaValue;
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		
		return xc;
	}
	/**
	 * 允许恶性负载范围设置
	 * 
	 * @param amMeterID
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder YunXuEXinFuZaiSheZhi(int amMeterID,String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			if ("".equals(Price_ID)||Price_ID==null)
			{
				return null;
			}
			String EXFZSX =null;
			String EXFZXX =null;
			
			String sql="select EXFZSX,EXFZXX from Price where Price_ID=" + Price_ID;
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					EXFZSX=rs.getString("EXFZSX");
					EXFZXX=rs.getString("EXFZXX");
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}
			
			if ("0".equals(EXFZSX)||"0.00".equals(EXFZSX)||"".equals(EXFZSX)||EXFZSX==null||"0".equals(EXFZXX)||"0.00".equals(EXFZXX)||"".equals(EXFZXX)||EXFZXX==null)
			{
				return null;
			}
			if(Integer.parseInt(EXFZSX)<=Integer.parseInt(EXFZXX))
			{
				return null;
			}
			
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);
			String dataid="C1C0";//97
			String PartList = meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10," + AmMeter_485Address + "," + AmMeter_Password + "," + EXFZXX + "," + EXFZSX;
			if (meterProtocol.equals("30"))
			{
				dataid="0409C102";//07恶性负载允许1下限值
				PartList = meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10," + AmMeter_485Address + "," + AmMeter_Password + "," + EXFZXX;
			}
			xc.setMeterAddress(AmMeter_485Address);
			xc.setDataid(dataid);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}
	
	/**07表恶性负载上下限要分开设置
	 * @param amMeterID
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder YunXuEXinFuZaiShangXian(int amMeterID,String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			if ("".equals(Price_ID)||Price_ID==null)
			{
				return null;
			}
			String EXFZSX =null;
			String EXFZXX =null;
			
			String sql="select EXFZSX,EXFZXX from Price where Price_ID=" + Price_ID;
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					EXFZSX = rs.getString("EXFZSX");
					EXFZXX = rs.getString("EXFZXX");
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}
			
			if ("0".equals(EXFZSX)||"0.00".equals(EXFZSX)||"".equals(EXFZSX)||EXFZSX==null||"0".equals(EXFZXX)||"0.00".equals(EXFZXX)||"".equals(EXFZXX)||EXFZXX==null)
			{
				return null;
			}
			if(Integer.parseInt(EXFZSX)<=Integer.parseInt(EXFZXX))
			{
				return null;
			}
			
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);
			xc.setDataid("0409C103");
			xc.setMeterAddress(AmMeter_485Address);
				String PartList = meterProtocol+",0409C103," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10," + AmMeter_485Address + "," + AmMeter_Password + "," + EXFZSX;
			
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}

	/**
	 * 取消恶性负载设置
	 * 
	 * @param amMeterID
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder QuXiaoEXinFuZaiSheZhi(int amMeterID,String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			if ("".equals(Price_ID)||Price_ID==null)
			{
				return null;
			}
			String EXFZ =null;
//			System.out.println("uuuu");
			String sql="select EXFZ from Price where Price_ID=" + Price_ID;
//			System.out.println("sqldfdf:"+sql);
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					EXFZ = rs.getString("EXFZ");
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}
//			System.out.println("lllnn");
			if ("0".equals(EXFZ)||"0.00".equals(EXFZ)||"".equals(EXFZ)||EXFZ==null)
			{
				return null;
			}
			
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);

			String dataid="C0C0";//97
			String PartList =  meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10," + AmMeter_485Address + "," + AmMeter_Password + ",00," + EXFZ;
			if (meterProtocol.equals("30"))
			{
				dataid="0409C101";//07
				int EXFZ07=8800;
				PartList =  meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10," + AmMeter_485Address + "," + AmMeter_Password + "," + EXFZ07;
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}
	/**
	 * 恶性负载设置
	 * 
	 * @param amMeterID
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder EXinFuZaiSheZhi(int amMeterID,String userID) throws SQLException
	{
//		System.out.println(amMeterID+"  "+userID);
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
//			System.out.println("opop");
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			if ("".equals(Price_ID)||Price_ID==null)
			{
				return null;
			}
			String EXFZ =null;
//			System.out.println("nnnnn");
			String sql="select EXFZ from Price where Price_ID=" + Price_ID;
//			System.out.println("llkkk:"+sql);
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					EXFZ = rs.getString("EXFZ");
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}
			
			if ("0".equals(EXFZ)||"0.00".equals(EXFZ)||"".equals(EXFZ)||EXFZ==null)
			{
				System.out.println("///////");
				return null;
			}
			
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);

			String dataid="C0C0";//97
			String PartList =  meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10," + AmMeter_485Address + "," + AmMeter_Password + ",01," + EXFZ;
			if (meterProtocol.equals("30"))
			{
				dataid="0409C101";//07
				PartList =  meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10," + AmMeter_485Address + "," + AmMeter_Password + "," + EXFZ;
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}
	
	/**远程开关编程键
	 * @param amMeterID
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder ProgramKey(int amMeterID,String userID,String onoff) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);
			
			String dataid = "programkey";
			xc.setDataid(dataid);

			String PartList = meterProtocol+",programkey," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
					+ AmMeter_Password + ","+onoff+",";
			xc.setPara(PartList);
			xc.setMeterAddress(AmMeter_485Address);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}
	
	/**
	 * 设置第一套电价
	 * @param amMeterID
	 * @param userID
	 * @param price_value
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder SetPrice01(int amMeterID,String userID,float price_value) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);

			String PartList = meterProtocol+",040501FF," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
					+ AmMeter_Password + ","+price_value+",";
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}
	
	
	/**
	 * 设置第二套电价
	 * @param amMeterID
	 * @param userID
	 * @param price_value
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder SetPrice02(int amMeterID,String userID,float price_value) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);

			String PartList = meterProtocol+",040502FF," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
					+ AmMeter_Password + ","+price_value+",";
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}
	
	public XMLCoder SetPriceQieHuanTime(int amMeterID,String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);

			String PartList = meterProtocol+",04000108," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
					+ AmMeter_Password + ",,";
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}
	
	/**
	 * 销户后退电
	 * @param amMeterID
	 * @param buyTimes
	 * @param userID
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public XMLCoder xiaoHuTuiDian(int amMeterID, String buyTimes, String userID) throws NumberFormatException, SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{

				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			xc.setMeterType(1);
			if ((buyTimes==null)||("".equals(buyTimes)))
			{
				buyTimes="0";
			}
			int times=Integer.parseInt(buyTimes);
			String dataid="E50E";//97
			if (meterProtocol.equals("30"))
			{
				dataid="070102FE";//07
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			String PartList = meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10,"+ AmMeter_485Address + "," + AmMeter_Password + ","+times+"," ;
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}
	
	/**
	 * 抄读恶性负载
	 * @param amMeterID
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder readMeterEXFZ(int amMeterID,String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{

				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);

			String dataid="C0C0";//97
			if (meterProtocol.equals("30"))
			{
				dataid="9901FF01";//07
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			String PartList = meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
					+ AmMeter_Password + ",,";
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}
	
	/**
	 * 抄读状态字
	 * 
	 * @param amMeterID
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder readMeterState(int amMeterID,String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{

				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);

			String dataid="C020";//97
			if (meterProtocol.equals("30"))
			{
				dataid="04000503";//07
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			String PartList = meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
					+ AmMeter_Password + ",,";
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);

		}
		return xc;
	}

	/**
	 * 读电量命令
	 * 
	 * @param amMeterID
	 * @param userID 
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder readMeter(int amMeterID, String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{

				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);
            String dataid="E50F";//97
            String AmMeter_CostType = (new CMMeterDao()).getMeterCostType(amMeterID);
            if (meterProtocol.equals("30"))
            {
            	dataid="00900200";//07（当前）剩余金额
            	if("0".equals(AmMeter_CostType)){//非费控读总示数
            		dataid = "00010000";
            	}
            }
            xc.setDataid(dataid);
            xc.setMeterAddress(AmMeter_485Address);
			String PartList = meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
					+ AmMeter_Password + ",,";
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}
	/**
	 * 透传读97电量命令
	 * 
	 * @param amMeterID
	 * @param userID 
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder readDanXiangTouChuan(int amMeterID, String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{

				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);
            String dataid="9010";//97
            (new CMMeterDao()).getMeterCostType(amMeterID);
            if (meterProtocol.equals("30"))
            {
            	dataid = "00010000";
            }
            xc.setDataid(dataid);
            xc.setMeterAddress(AmMeter_485Address);
			String PartList = meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
					+ AmMeter_Password + ",,";
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}
	/**
	 * 读电量命令
	 * 
	 * @param amMeterID
	 * @param userID 
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder readWaterMeter(int watermeter, String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getWaterPara(watermeter))
		{
			
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);
            String dataid="901F";//97
			String PartList = meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + WaterMeter_485Address + ","
					+ WaterMeter_Password + ",,";
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}
	/**
	 * 读电量命令(07表要读其他命令)
	 * 
	 * @param amMeterID
	 * @param userID 
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder readMeter07(int amMeterID, String userID,String dataid) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{

				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setPw(pw);

			xc.setMeterType(1);
            //String dataid="00010000";//当前正向有功总电能
//            if (meterProtocol.equals("30"))
//            {
//            	dataid="00900200";//07
//            }
			String PartList = meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
					+ AmMeter_Password + ",,";
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}

	/**
	 * 合闸命令
	 * 空调 C028 照明C027
	 * @param amMeterID
	 * @param strPart
	 * @param userID 
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder heZhaCommand(int amMeterID, String strPart, String username)
			throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{

				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			xc.setOperCode(username);
//			xc.setOperName(username);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));
			
			xc.setMeterType(1);

			xc.setPw(pw);

			if ("".equals(strPart) || strPart == null)
				strPart = "C028";
			String dataid=strPart;//97
			if (meterProtocol.equals("30"))
			{
				//dataid="0000001B";//07
				dataid="0000001C";//07
			}
			String PartList =  meterProtocol+","+dataid +"," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address
					+ "," + AmMeter_Password + ",1,0";
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}

	/**
	 * 分闸命令             分闸报警表计命令传C028 固定
	 * 空调 C028 照明C027
	 * @param amMeterID
	 * @param strPart
	 * @param userID 
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder fenZhaCommand(int amMeterID, String strPart, String userID)
			throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setMeterType(1);

			xc.setPw(pw);

			if ("".equals(strPart) || strPart == null)
				strPart = "C028";

			String dataid=strPart;//97
			if (meterProtocol.equals("30"))
			{
				dataid="0000001A";//07
			}
			String PartList = meterProtocol+","+dataid +"," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address
					+ "," + AmMeter_Password + ",0,0";
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}

	/**
	 * 开户命令 if (GValue > 0) 读开户前剩余电量 下发剩余电量{Gross}度 读开户后剩余电量
	 * 启动预付费命令  启动预付费未开户表计命令
	 * 
	 * @param amMeterID
	 * @param userID 
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder kaiHuCommand(int amMeterID, String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setMeterType(1);

			xc.setPw(pw);
			
			String sqlString = "select nvl(TYDSD,0)TYDSD, nvl(QDYFF,1)QDYFF, nvl(AutoTZ,0)AutoTZ, nvl(TZDL,0)TZDL from Price where Price_ID=(select price_id from Ammeter where Ammeter_id="+amMeterID+")"; 
			String yPart = "010";
			
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rSet =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sqlString);
				rSet = ps.executeQuery();
				if (rSet.next())
				{
					yPart = rSet.getString("AutoTZ") + rSet.getString("TYDSD") + rSet.getString("QDYFF");
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rSet);
			}
			String dataid = "";
			String PartList =null;
			if ("1".equalsIgnoreCase(meterProtocol)) {
				/*PartList= meterProtocol+",E50B," + Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
						+ AmMeter_Password + ",1,";*/
				dataid = "E50B";
				PartList= meterProtocol+",E50B," + Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
						+ AmMeter_Password + ","+yPart+",";
			}
			else
			{
				dataid= "07010101";
				PartList= meterProtocol+",07010101," + Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
						+ AmMeter_Password + ",1,";
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}

	
	/**
	 * 销户命令 取消预付费命令 取消预付费未开户表计命令
	 * @param amMeterID
	 * @param userID
	 * @param flag 07表 取消预付费是2 销户是1
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder xiaoHuCommand(int amMeterID, String userID,int flag) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setMeterType(1);

			xc.setPw(pw);
			
			String sqlString = "select nvl(TYDSD,0)TYDSD, nvl(QDYFF,1)QDYFF, nvl(AutoTZ,0)AutoTZ, nvl(TZDL,0)TZDL from Price where Price_ID=(select price_id from Ammeter where Ammeter_id="+amMeterID+")"; 
			String yPart = "010";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rSet =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sqlString);
				rSet = ps.executeQuery();
				if(rSet.next()){
					yPart = rSet.getString("AutoTZ")+rSet.getString("TYDSD")+"0";
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rSet);
			}
			
			
			String dataid = "";
			String PartList=null;
			if ("1".equalsIgnoreCase(meterProtocol)) {
				/*PartList = meterProtocol+",E50B," + Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
						+ AmMeter_Password + ",0,";*/
				dataid="E50B";
				PartList = meterProtocol+",E50B," + Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
						+ AmMeter_Password + ","+yPart+",";
			}
			else
			{
				dataid = "07010101";
				PartList = meterProtocol+",07010101," +  Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
						+ AmMeter_Password + ","+flag+",";
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}
	
	/**
	 *  取消预付费命令 取消预付费未开户表计命令
	 * 
	 * @param amMeterID
	 * @param userID 
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder QuXiaoYYFCommand(int amMeterID, String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setMeterType(1);

			xc.setPw(pw);
			
			String sqlString = "select nvl(TYDSD,0)TYDSD, nvl(QDYFF,1)QDYFF, nvl(AutoTZ,0)AutoTZ, nvl(TZDL,0)TZDL from Price where Price_ID=(select price_id from Ammeter where Ammeter_id="+amMeterID+")"; 
			String yPart = "010";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rSet =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sqlString);
				rSet = ps.executeQuery();
				if(rSet.next()){
					yPart = rSet.getString("AutoTZ")+rSet.getString("TYDSD")+"0";
				}
				
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rSet);
			}
			
			String dataid = "";
			String PartList=null;
			if ("1".equalsIgnoreCase(meterProtocol)) {
				/*PartList = meterProtocol+",E50B," + Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
						+ AmMeter_Password + ",0,";*/
				dataid = "E50B";
				PartList = meterProtocol+",E50B," + Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
						+ AmMeter_Password + ","+yPart+",";
			}
			else
			{
				dataid = "07010101";
				PartList = meterProtocol+",07010101," +  Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
						+ AmMeter_Password + ",2,";
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}


	/**
	 * 下发购电量
	 * 
	 * @param amMeterID
	 * @param userID 
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder sendTGross(int amMeterID, String Gross,String times, String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setMeterType(1);
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			xc.setPw(pw);
			String dataid = "";
			String PartList=null;
			int buytime=Integer.parseInt(times)+1;
			if ("1".equalsIgnoreCase(meterProtocol)) {
				dataid = "E501";
				float TGross = (float)Math.round(Float.parseFloat(Gross) / BeiLv * 100) / 100 ;
				PartList = meterProtocol+",E501," + Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10,"+ AmMeter_485Address + ","
						+ AmMeter_Password + "," +buytime+"," + TGross;
			}
			else
			{
				dataid = "07010201";
				float TMoney = (float)Math.round(Float.parseFloat(Gross) / BeiLv * 100) / 100 ;
				PartList = meterProtocol+",07010201," + Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10,"+ AmMeter_485Address + ","
						+ AmMeter_Password + "," +buytime+"," + TMoney;
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}
	
	/**
	 * 设置场内模式
	 * @param amMeterID
	 * @param userID
	 * @param flag
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder setMode(int amMeterID,String userID,int flag) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setMeterType(1);

			xc.setPw(pw);
			
			String PartList = meterProtocol+",setmode," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10," + AmMeter_485Address + "," + AmMeter_Password + ","+flag;
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}
	
	/**
	 * 总清
	 * @param amMeterID
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder zongQing(int amMeterID,String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));

			xc.setMeterType(1);

			xc.setPw(pw);
			String dataid = "";
			String PartList=null;
			if ("1".equalsIgnoreCase(meterProtocol))
			{
				dataid = "C120";
			    PartList = meterProtocol+",C120," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10," + AmMeter_485Address + "," + AmMeter_Password + ",";
			}
			else
			{
				dataid = "87728386";
				PartList = meterProtocol+",87728386," + Meter_Port + "," + Meter_Sulv + ",e,8,1,1,60,10," + AmMeter_485Address + "," + AmMeter_Password + ",";
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}
	
	/**
	 * 开户后下发剩余电量
	 * @param amMeterID 电表ID
	 * @param Gross 售电度数
	 * @param SYGross 剩余电量
	 * @param Gvalue 旧表余量
	 * @param ZValueZY ammeter表中的最大示数
	 * @param userID 
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public XMLCoder kaiHuXiaFaDianLiang(int amMeterID,String Gross, String SYValue, String Gvalue, String ZValueZY, String userID,String Money) throws NumberFormatException, SQLException
	{
		DecimalFormat nf=new DecimalFormat("0.00");
		XMLCoder xc = null;
		String dataid = "";
		if (getPara(amMeterID))
		{
			/*if ("0".equals(AmMeter_Stat))
			{
				return null;
			}*/
			float TGross=0,TMoney=0;
			if(Float.parseFloat(Gross)>0)
			{
				if ("1".equals(meterProtocol))//97规约
				{
					TGross = (Float.parseFloat(Gross) + Float.parseFloat(SYValue))/BeiLv;
	                //查询最后一个月内是否下发月补
	                if (Float.parseFloat(Gvalue) > 0)
	                {
	                    String YBTheGross = null;
	                    
	                    String sql="select TheGross from APSaleInfo where Kind in(2,3) and Ammeter_ID=" + amMeterID + " and BuyTime >sysdate-15";
	                    Connection conn = null;
	            		PreparedStatement ps =null;
	            		ResultSet rs =null;
	            		try{
	            			conn = ConnDB.getConnection();
	            			ps = conn.prepareStatement(sql);
	            			rs = ps.executeQuery();
							while (rs.next())
							{
								YBTheGross = rs.getString("TheGross");
							}
	            		}catch (SQLException e)
	            		{
	            			e.printStackTrace();
	            		} finally
	            		{
	            			ConnDB.release(conn, ps,rs);
	            		}
	                    
	                    
	                    if (YBTheGross !=null)
	                    {
	                        TGross = (Float.parseFloat(Gross) + Float.parseFloat(YBTheGross) - Float.parseFloat(SYValue)) / BeiLv;
	                    }
	                }
	                else
	                {
	                     TGross = (Float.parseFloat(Gross)) / BeiLv;
	                }
				}
				else //07规约
				{
					TMoney = (Float.parseFloat(Money) + Float.parseFloat(SYValue))/BeiLv;
	                //查询最后一个月内是否下发月补
	                if (Float.parseFloat(Gvalue) > 0)
	                {
	                    String YBTheMoney = null;
	                    
	                    String sql="select TheMoney from APSaleInfo where Kind in(2,3) and Ammeter_ID=" + amMeterID + " and BuyTime >sysdate-15";
	                    Connection conn = null;
	            		PreparedStatement ps =null;
	            		ResultSet rs =null;
	            		try{
	            			conn = ConnDB.getConnection();
	            			ps = conn.prepareStatement(sql);
	            			rs = ps.executeQuery();
							while (rs.next())
							{
								YBTheMoney = rs.getString("TheMoney");
							}
	            		}catch (SQLException e)
	            		{
	            			e.printStackTrace();
	            		} finally
	            		{
	            			ConnDB.release(conn, ps,rs);
	            		}
	                    
	                    if (YBTheMoney !=null)
	                    {
	                    	TMoney = (Float.parseFloat(Money) + Float.parseFloat(YBTheMoney) - Float.parseFloat(SYValue))/BeiLv;
	                    }
	                }else{
	                	TMoney = Float.parseFloat(Money)/BeiLv;
	                }
				}
                
                xc = new XMLCoder();
    			// 命令码
    			xc.setCode("1000000100");
    			// 设置任务编号
    			Date time = new Date();
    			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
    			xc.setTaskNum(sdf1.format(time));

    			xc.setMeterType(1);
    			
    			xc.setOperCode(userID);
    			xc.setDatasiteID(datasite_id);
    			xc.setPw(pw);
    			
    			String PartList=null;
    			if ("1".equalsIgnoreCase(meterProtocol)) {
    				dataid = "E501";
    				PartList = meterProtocol+",E501," + Meter_Port + "," + Meter_Sulv
        					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
        					+ AmMeter_Password + ",1," + nf.format(TGross);
    			}
    			else
    			{
    				dataid = "07010201";
    				PartList = meterProtocol+",07010201," +  Meter_Port + "," + Meter_Sulv
    						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
    						+ AmMeter_Password + ",1,"+ nf.format(TMoney);
    			}
    			xc.setDataid(dataid);
    			xc.setMeterAddress(AmMeter_485Address);
    			xc.setPara(PartList);
    			// 网关地址
    			xc.setTerminalAddress(terminaladdress);
    			xc.settGrosString(nf.format(TGross));
                
			}
			else
			{
				if (SYValue!=null && !"".equals(SYValue))
                {
                    if (Float.parseFloat(SYValue) > 0)
                    {
                    	if ("1".equals(meterProtocol))//97规约
                    	{
                    		TGross = Float.parseFloat(SYValue)/BeiLv;
                        	//查询最后一个月内是否下发月补
                             if (Float.parseFloat(Gvalue) > 0)
                             {
                                 String YBTheGross = null;
                                 
                                 String sql="select TheGross from APSaleInfo where Kind in(2,3) and Ammeter_ID=" + amMeterID + " and BuyTime >sysdate-15";
                                 Connection conn = null;
                         		PreparedStatement ps =null;
                         		ResultSet rs =null;
                         		try{
                         			conn = ConnDB.getConnection();
                         			ps = conn.prepareStatement(sql);
                         			rs = ps.executeQuery();
									while (rs.next())
									{
										YBTheGross = rs.getString("TheGross");
									}
                         		}catch (SQLException e)
                         		{
                         			e.printStackTrace();
                         		} finally
                         		{
                         			ConnDB.release(conn, ps,rs);
                         		}
                                 

                                 if (YBTheGross !=null)
                                 {
                                	 TGross = Float.parseFloat(Gvalue) - Float.parseFloat(ZValueZY) / BeiLv;
                                 }
                             }                       
                             else
                             {
                                 TGross = Float.parseFloat("0");
                             }
                    	}
                    	else//07规约
                    	{
                    		TMoney = (Float.parseFloat(Money) + Float.parseFloat(SYValue))/BeiLv;
        	                //查询最后一个月内是否下发月补
        	                if (Float.parseFloat(Gvalue) > 0)
        	                {
        	                    String YBTheMoney = null;
        	                    
        	                    String sql="select TheMoney from APSaleInfo where Kind in(2,3) and Ammeter_ID=" + amMeterID + " and BuyTime >sysdate-15";
        	                    Connection conn = null;
        	            		PreparedStatement ps =null;
        	            		ResultSet rs =null;
        	            		try{
        	            			conn = ConnDB.getConnection();
        	            			ps = conn.prepareStatement(sql);
        	            			rs = ps.executeQuery();
									while (rs.next())
									{
										YBTheMoney = rs.getString("TheMoney");
									}
        	            		}catch (SQLException e)
        	            		{
        	            			e.printStackTrace();
        	            		} finally
        	            		{
        	            			ConnDB.release(conn, ps,rs);
        	            		}
        	                    
        	                    
        	                    if (YBTheMoney !=null)
        	                    {
        	                    	TMoney = (Float.parseFloat(Money) + Float.parseFloat(YBTheMoney) - Float.parseFloat(SYValue))/BeiLv;
        	                    }else {
									TMoney = 0;
								}
        	                }
                    	}
                      
                         xc = new XMLCoder();
             			// 命令码
             			xc.setCode("1000000100");
             			xc.setDatasiteID(datasite_id);
             			// 设置任务编号
             			Date time = new Date();
             			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
             			xc.setTaskNum(sdf1.format(time));

             			xc.setMeterType(1);

             			xc.setPw(pw);
             			String PartList=null;
            			if ("1".equalsIgnoreCase(meterProtocol)) {
            				dataid = "E501";
            				PartList = meterProtocol+",E501," + Meter_Port + "," + Meter_Sulv
                					+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
                					+ AmMeter_Password + ",1," + nf.format(TGross);
            			}
            			else
            			{
            				dataid = "07010201";
            				PartList = meterProtocol+",07010201," +  Meter_Port + "," + Meter_Sulv
            						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
            						+ AmMeter_Password + ",1,"+ nf.format(TMoney);
            			}
            			xc.setDataid(dataid);
            			xc.setMeterAddress(AmMeter_485Address);
             			xc.setPara(PartList);
             			// 网关地址
             			xc.setTerminalAddress(terminaladdress);
             			xc.settGrosString(nf.format(TGross));
                    }
                }
			}
		}
		return xc;
	}

	/**
	 * 设置过载恢复和恶性负载恢复命令
	 * 
	 * @param amMeterID
	 * @param time
	 * @param userID
	 * @param flag 0:过载恢复 		1:恶性负载恢复
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder setHuiFuTimeCommand(int amMeterID,int times,String userID,int flag) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(amMeterID))
		{
			xc = new XMLCoder();
			// 命令码
			xc.setCode("1000000100");
			xc.setOperCode(userID);
			xc.setDatasiteID(datasite_id);
			// 设置任务编号
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			xc.setTaskNum(sdf1.format(time));
			xc.setMeterType(1);
			xc.setPw(pw);			
			String dataid = "";
			String PartList =null;
			if ("1".equalsIgnoreCase(meterProtocol)){
				//97 表暂时不做
				return null;
			}
			else
			{
				//flag 0:过载恢复 		1:恶性负载恢复
				if(flag ==0){
					dataid= "0409C001";
				}else if (flag ==1) {
					dataid= "0409C002";
				}
				PartList= meterProtocol+","+dataid+"," + Meter_Port + "," + Meter_Sulv
						+ ",e,8,1,1,60,10," + AmMeter_485Address + ","
						+ AmMeter_Password + ","+times+",";
			}
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}
	
	public boolean getPara(int amMeterID) throws SQLException

	{
		boolean ok = false;
		String sql = "SELECT A .Gather_ID,Gather_Address,Gather_Password,Protocol,Gather_State,SendWay,IP,AmMeter_Point,AmMeter_Name,NVL (BeiLv, 1) BeiLv,"
				+ "(SELECT	TexingValue	 FROM  (TexingValue) c WHERE  c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 19 ) Meter_Port,"
				+ "(SELECT	TexingValue  FROM  (TexingValue) c WHERE  c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 5  ) Meter_Sulv,"
				+ " (select DICTIONARYVALUE_VALUE from DICTIONARYVALUE where DICTIONARY_ID=22 and DICTIONARYVALUE_NUM=(SELECT	TexingValue  FROM  (TexingValue) c WHERE  c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 4  )) Meter_JiaoYan,"
				+ "	AmMeter_485Address,	AmMeter_Password,(SELECT  TexingValue "
				+ "	FROM (TexingValue) c WHERE	c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 18) meterProtocol,Price_ID,"
				+ "	AmMeter_Stat,NVL (GValue, 0) GValue,a.datasite_id FROM (AmMeter) b	LEFT JOIN (Gather) A ON A .Gather_ID = b.Gather_ID	WHERE AMMETER_ID="+ amMeterID;
	
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ok = true;
				terminaladdress = rs.getString("Gather_Address");
				terminalip = rs.getString("IP");
				way = rs.getString("SendWay");
				pw = rs.getString("Gather_Password");
				Protocol = rs.getString("Protocol");
				AmMeter_Name = rs.getString("AmMeter_Name");
				AmMeter_485Address = rs.getString("AmMeter_485Address");
				AmMeter_Password = rs.getString("AmMeter_Password");
				AmMeter_Stat = rs.getString("AmMeter_Stat");
				Meter_Port = rs.getString("Meter_Port");
				Meter_Sulv = rs.getString("Meter_Sulv");
				Meter_JiaoYan=rs.getString("Meter_JiaoYan");
				BeiLv = rs.getFloat("BeiLv");
				GValue = rs.getString("GValue");
				Price_ID = rs.getString("Price_ID");
				meterProtocol=rs.getString("meterProtocol");
				datasite_id=rs.getString("datasite_id");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return ok;

	}
	
	public boolean getWaterPara(int watermeterID) throws SQLException

	{
		boolean ok = false;
		String sql = "SELECT A .Gather_ID,Gather_Address,Gather_Password,datasite_id,Protocol,Gather_State,SendWay,IP,WaterMeter_Point,WaterMeter_Name,NVL (BeiLv, 1) BeiLv,"
				+ "(SELECT	TexingValue	 FROM  (TexingValue) c WHERE  c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 28 ) Meter_Port,"
				+ "(SELECT	TexingValue  FROM  (TexingValue) c WHERE  c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 23  ) Meter_Sulv,"
				+ " (select DICTIONARYVALUE_VALUE from DICTIONARYVALUE where DICTIONARY_ID=22 and DICTIONARYVALUE_NUM=(SELECT	TexingValue  FROM  (TexingValue) c WHERE  c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 22  )) Meter_JiaoYan,"
				+ " (select DICTIONARYVALUE_VALUE from DICTIONARYVALUE where DICTIONARY_ID=27 and DICTIONARYVALUE_NUM=(SELECT	TexingValue  FROM  (TexingValue) c WHERE  c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 29  )) makeCode,"
				+ " (SELECT	c.METESTYLE_NUM	FROM (metestyle) c WHERE	c.MeteStyle_ID = b.MeteStyle_ID	) METESTYLE_NUM,"
				+ "	WaterMeter_485Address,	WaterMeter_Password,(SELECT  TexingValue "
				+ "	FROM (TexingValue) c WHERE	c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 27) meterProtocol,Price_ID,"
				+ "	WaterMeter_Stat	FROM (WaterMeter) b	LEFT JOIN (Gather) A ON A .Gather_ID = b.Gather_ID	WHERE WaterMeter_ID="+ watermeterID;
	
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ok = true;
				terminaladdress = rs.getString("Gather_Address");
				terminalip = rs.getString("IP");
				way = rs.getString("SendWay");
				pw = rs.getString("Gather_Password");
				Protocol = rs.getString("Protocol");
				WaterMeter_Name = rs.getString("WaterMeter_Name");
				String makecode=rs.getString("makecode");
				String METESTYLE_NUM=rs.getString("METESTYLE_NUM");
				
				WaterMeter_485Address = rs.getString("WaterMeter_485Address");
				WaterMeter_485Address=makecode+METESTYLE_NUM+WaterMeter_485Address.substring(2);
				WaterMeter_Password = rs.getString("WaterMeter_Password");
				WaterMeter_Stat = rs.getString("WaterMeter_Stat");
				Meter_Port = rs.getString("Meter_Port");
				Meter_Sulv = rs.getString("Meter_Sulv");
				Meter_JiaoYan=rs.getString("Meter_JiaoYan");
				BeiLv = rs.getFloat("BeiLv");
				Price_ID = rs.getString("Price_ID");
				meterProtocol=rs.getString("meterProtocol");
				datasite_id = rs.getString("datasite_id");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return ok;

	}
	/**
	 * 清空网关
	 * @param gatherID
	 * @throws SQLException 
	 */
	public XMLCoder cleanGather(int gatherID, int port) throws SQLException
	{
		XMLCoder xc = new XMLCoder();
		//命令码
		xc.setCode("0500001006");
		xc.setDatasiteID(datasite_id);
		//设置任务编号
    	Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf.format(time));
		
		//网关地址
		String sql = "select Gather_ID,Gather_Address,Gather_Password,SendWay,Protocol from Gather where Gather_ID=" + gatherID;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if(rs.next())
			{
				//网关地址
				 xc.setTerminalAddress(rs.getString("Gather_Address"));
//				 //通讯地址
//				 xc.setWay(rs.getString("SendWay"));
//				 //密码
//	             //xc.setPw(rs.getString("Gather_Password"));
//				 //规约
//	             xc.setProtocol(rs.getString("Protocol"));
			}else 
			{
				return null;
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		 
		//设置命令参数
		String parameters = "";
		parameters = String.valueOf(port);
		xc.setPara(parameters);
		
		return xc;	
	}

	public String getTerminaladdress()
	{
		return terminaladdress;
	}

	public void setTerminaladdress(String terminaladdress)
	{
		this.terminaladdress = terminaladdress;
	}

	public String getTerminalip()
	{
		return terminalip;
	}

	public void setTerminalip(String terminalip)
	{
		this.terminalip = terminalip;
	}

	public String getWay()
	{
		return way;
	}

	public void setWay(String way)
	{
		this.way = way;
	}

	public String getPw()
	{
		return pw;
	}

	public void setPw(String pw)
	{
		this.pw = pw;
	}

	public String getProtocol()
	{
		return Protocol;
	}

	public void setProtocol(String protocol)
	{
		Protocol = protocol;
	}
	
	public String getMeterProtocol()
	{
		return meterProtocol;
	}

	public String getAmMeter_485Address()
	{
		return AmMeter_485Address;
	}

	public void setAmMeter_485Address(String amMeter_485Address)
	{
		AmMeter_485Address = amMeter_485Address;
	}

	public String getAmMeter_Password()
	{
		return AmMeter_Password;
	}

	public void setAmMeter_Password(String amMeter_Password)
	{
		AmMeter_Password = amMeter_Password;
	}

	public String getAmMeter_Stat()
	{
		return AmMeter_Stat;
	}

	public void setAmMeter_Stat(String amMeter_Stat)
	{
		AmMeter_Stat = amMeter_Stat;
	}

	public String getMeter_Port()
	{
		return Meter_Port;
	}

	public void setMeter_Port(String meter_Port)
	{
		Meter_Port = meter_Port;
	}

	public String getMeter_Sulv()
	{
		return Meter_Sulv;
	}

	public void setMeter_Sulv(String meter_Sulv)
	{
		Meter_Sulv = meter_Sulv;
	}

	public float getBeiLv()
	{
		return BeiLv;
	}

	public void setBeiLv(float beiLv)
	{
		BeiLv = beiLv;
	}

	public String getGValue()
	{
		return GValue;
	}

	public void setGValue(String gValue)
	{
		GValue = gValue;
	}

	public String getAmMeter_Name()
	{
		return AmMeter_Name;
	}

	public void setAmMeter_Name(String amMeter_Name)
	{
		AmMeter_Name = amMeter_Name;
	}

	

		

}
