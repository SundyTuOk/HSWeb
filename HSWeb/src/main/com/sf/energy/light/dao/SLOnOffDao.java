package com.sf.energy.light.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sf.energy.light.model.XMLCoder;
import com.sf.energy.util.ConnDB;


public class SLOnOffDao
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
	private String datasite_id;
	/**
	 * 路灯开关灯命令
	 * @param port
	 * @param lampControlAddress
	 * @param state1
	 * @param state2
	 * @param terminalAddress
	 * @return
	 */
	public XMLCoder lampOnOff(int port, String lampControlAddress, String state1,  String state2, String terminalAddress
			, String userID,String datasiteid)
	{
		XMLCoder xc = new XMLCoder();
		
		if (lampControlAddress.length()>=12)//电表当路灯控制器
		{
			xc=MeterOnOffCommand(lampControlAddress,terminalAddress,"CO28",  state1,userID);
		}
		else {
		//命令码
		xc.setCode("1000000100");
		xc.setDatasiteID(datasiteid);
		//设置任务编号
    	Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf.format(time));
		//网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setOperCode(userID);
		 
		String parameters = "";
		parameters = "lamp,10," + port + ",1200,n,8,2,1,5,10," + lampControlAddress + "," + state1 + "," + state2;
		xc.setPara(parameters);
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
	public XMLCoder MeterOnOffCommand(String lampControlAddress,String terminalAddress, String strPart,String state1, String userID)
	{
		XMLCoder xc = null;
		if (getPara(lampControlAddress,terminalAddress))
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
			
			
			String TheState="0";
			if(state1=="on")
			{
				if (meterProtocol.equals("30"))
				{
					//dataid="0000001B";//07
					dataid="0000001C";//07
				}
				TheState="1";
			}else {
				if (meterProtocol.equals("30"))
				{
					dataid="0000001A";//07
				}
			}
			
			String PartList = meterProtocol+","+dataid +"," + Meter_Port + "," + Meter_Sulv
					+ ",e,8,1,1,60,10," + AmMeter_485Address
					+ "," + AmMeter_Password + ","+TheState+",0";
			xc.setDataid(dataid);
			xc.setMeterAddress(AmMeter_485Address);
			xc.setPara(PartList);
			// 网关地址
			xc.setTerminalAddress(terminaladdress);
		}
		return xc;
	}


	public boolean getPara(String lampControlAddress,String terminalAddress ) 
	{
		boolean ok = false;
		String sql = "SELECT A .Gather_ID,Gather_Address,Gather_Password,Protocol,Gather_State,SendWay,IP,AmMeter_Point,AmMeter_Name,NVL (BeiLv, 1) BeiLv,"
				+ "(SELECT	TexingValue	 FROM  (TexingValue) c WHERE  c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 19 ) Meter_Port,"
				+ "(SELECT	TexingValue  FROM  (TexingValue) c WHERE  c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 5  ) Meter_Sulv,"
				+ " (select DICTIONARYVALUE_VALUE from DICTIONARYVALUE where DICTIONARY_ID=22 and DICTIONARYVALUE_NUM=(SELECT	TexingValue  FROM  (TexingValue) c WHERE  c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 4  )) Meter_JiaoYan,"
				+ "	AmMeter_485Address,	AmMeter_Password,(SELECT  TexingValue "
				+ "	FROM (TexingValue) c WHERE	c.MeteStyle_ID = b.MeteStyle_ID	AND c.MeterTexing_ID = 18) meterProtocol,Price_ID,"
				+ "	AmMeter_Stat,NVL (GValue, 0) GValue,datasite_id	FROM (AmMeter) b	LEFT JOIN (Gather) A ON A .Gather_ID = b.Gather_ID	WHERE AmMeter_485Address='"+ lampControlAddress+"' and Gather_Address='"+terminalAddress+"'";
	
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
	
	/**
	 * 抄读状态字
	 * 
	 * @param amMeterID
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder readMeterState(String lampControlAddress,String terminalAddress,String userID) throws SQLException
	{
		XMLCoder xc = null;
		if (getPara(lampControlAddress,terminalAddress))
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

	public XMLCoder readLampState(int port, String lampControlAddress,String terminalAddress, int SLLine_ID, String userID) throws SQLException
	{
		XMLCoder xc = new XMLCoder();
		if (lampControlAddress.length()>=12)//电表当路灯控制器
		{
			xc=readMeterState(lampControlAddress,terminalAddress,userID);
		}
		else {
		String terminalIP = "";
		
		String way = "";
		String pw = "";
		String protocol = "";
		String SLKongzhi_Index = "1";
		
		//命令码
		xc.setCode("1000000100");
		//设置任务编号
    	Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf.format(time));
		
		String sql = "select Protocol,Gather_Address,Gather_Password,SendWay,SLKongzhi_Index,IP,datasite_id from Gather a,(select SLGather_ID,SLKongzhi_Index from SLLine c, SLKongzhi d where c.SLKongzhi_ID=d.SLKongzhi_ID and SLLine_ID=" + SLLine_ID + ") b where a.Gather_ID=b.SLGather_ID";
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
				terminalAddress = rs.getString("Gather_Address");
	            way = rs.getString("SendWay");
	            pw = rs.getString("Gather_Password");
	            protocol = rs.getString("Protocol");
	            SLKongzhi_Index = rs.getString("SLKongzhi_Index");
	            terminalIP = rs.getString("IP");
	            datasite_id=rs.getString("datasite_id");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		//网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setWay(way);
		xc.setPw(pw);
		xc.setProtocol(protocol);
		xc.setOperCode(userID);
		xc.setDatasiteID(datasite_id); 
		String parameters = "";
		parameters = "lamp,41," + port + ",1200,n,8,2,1,5,10," + lampControlAddress + ",,, ,";
		xc.setPara(parameters);
		}
		return xc;	
	}
	public XMLCoder lampCheckTime(int port, String lampControlAddress, int SLLine_ID, String userID) throws SQLException
	{
		XMLCoder xc = new XMLCoder();
		
		String terminalIP = "";
		String terminalAddress = "";
		String way = "";
		String pw = "";
		String protocol = "";
		String SLKongzhi_Index = "1";
		
		//命令码
		xc.setCode("1000000100");
		//设置任务编号
    	Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf.format(time));
		
		String sql = "select Protocol,Gather_Address,Gather_Password,SendWay,SLKongzhi_Index,IP,datasite_id from Gather a,(select SLGather_ID,SLKongzhi_Index from SLLine c, SLKongzhi d where c.SLKongzhi_ID=d.SLKongzhi_ID and SLLine_ID=" + SLLine_ID + ") b where a.Gather_ID=b.SLGather_ID";
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
				terminalAddress = rs.getString("Gather_Address");
	            way = rs.getString("SendWay");
	            pw = rs.getString("Gather_Password");
	            protocol = rs.getString("Protocol");
	            SLKongzhi_Index = rs.getString("SLKongzhi_Index");
	            terminalIP = rs.getString("IP");
	            datasite_id=rs.getString("datasite_id");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		//网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setWay(way);
		xc.setPw(pw);
		xc.setProtocol(protocol);
		xc.setOperCode(userID);
		xc.setDatasiteID(datasite_id);  
		String parameters = "";
		parameters = "lamp,5D," + port + ",1200,n,8,2,1,5,10," + lampControlAddress + ",,, ,";
		xc.setPara(parameters);
		
		return xc;	
	}
}
