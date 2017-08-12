package com.sf.energy.project.system.maintenance;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.sf.energy.manager.current.model.XMLCoder;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.util.Configuration;
import com.sf.energy.util.ConnDB;

public class OnlineGather {

	private int totalCount = 0;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public ArrayList<Gather> display(int archid,int areaid, int pageCount, int pageIndex, String order)
			throws SQLException {
		Gather gather = null;
		ArrayList<Gather> list = new ArrayList<Gather>();
		String where=" where 1=1 ";
		if(archid!=-1){
			where +=" and architecture_id="+archid +" ";
		}
		if(areaid!=-1){
			where +=" and area_id="+areaid +" ";
		}

		String sql = "SELECT * FROM Gather "+ where + order ;
		// System.out.println(sql);
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
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0) {
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0)) {
				gather = new Gather();
				gather.setGatherID(rs.getInt("Gather_ID"));
				gather.setDatasiteID(rs.getInt("Datasite_ID"));
				String gatherState = "";
				if (rs.getInt("Gather_State") == 0) {
					gatherState = "<span style=\"color:red\">离线</span>";
				} else {
					gatherState = "<span style=\"color:Green\">在线</span>";
				}

				gather.setGatherStateName(gatherState);

				if (!"".equals(rs.getString("Gather_Name"))
						&& rs.getString("Gather_Name") != null
						&& !"null".equals(rs.getString("Gather_Name"))) {
					gather.setGatherName(rs.getString("Gather_Name"));
				} else {
					gather.setGatherName("");
				}

				if (!"".equals(rs.getString("Gather_Address"))
						&& rs.getString("Gather_Address") != null
						&& !"null".equals(rs.getString("Gather_Address"))) {
					gather.setGatherAddress(rs.getString("Gather_Address"));
				} else {
					gather.setGatherAddress("");
				}

				if (!"".equals(rs.getString("LastTime"))
						&& rs.getString("LastTime") != null
						&& !"null".equals(rs.getString("LastTime"))) {
					gather.setLastTime(rs.getString("LastTime"));
				} else {
					gather.setLastTime("");
				}

				if (!"".equals(rs.getString("Ip")) && rs.getString("Ip") != null
						&& !"null".equals(rs.getString("Ip"))) {
					gather.setIp(rs.getString("Ip"));
				} else {
					gather.setIp("");
				}

				if (!"".equals(rs.getString("LastSetTime"))
						&& rs.getString("LastSetTime") != null
						&& !"null".equals(rs.getString("LastSetTime"))) {
					gather.setLastSetTime(rs.getString("LastSetTime"));
				} else {
					gather.setLastSetTime("");
				}

				if (!"".equals(rs.getString("LastSetMsg"))
						&& rs.getString("LastSetMsg") != null
						&& !"null".equals(rs.getString("LastSetMsg"))) {
					gather.setLastSetMsg(rs.getString("LastSetMsg"));
				} else {
					gather.setLastSetMsg("");
				}

				list.add(gather);
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
	 * 参数读取
	 * 
	 * @param terminalAddress
	 * @param gatherID
	 * @return
	 * @throws SQLException
	 */
	public String[] getParameters(String terminalAddress, int gatherID,
			String userID,String datasiteid) throws SQLException {
		ArrayList<XMLCoder> list = new ArrayList<XMLCoder>();

		// /表计总数
		int num = 0;
		int count = 0;
		String[] point = null;
		// / 命令参数
		String parameters = "";
		String meterPoint = "";

		String queryAmMeter = "select AMMETER_POINT from ammeter where gather_id = "
				+ gatherID + " order by AMMETER_POINT";
		PreparedStatement smAm = null;
		ResultSet rsAm = null;
		Connection conn=null;
		try
		{
			conn = ConnDB.getConnection();
			smAm = conn.prepareStatement(queryAmMeter);
			rsAm = smAm.executeQuery();
			while (rsAm.next()) {
				meterPoint += rsAm.getString("AMMETER_POINT") + ",";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, smAm, rsAm);
		}
		if (!"".equals(meterPoint)) {
			point = meterPoint.split(",");
			num = point.length;
			if (num >= 10) {
				parameters = "10";
			} else if (num > 0 && num < 10) {
				parameters = String.valueOf(num);
			}
			for (int i = 0; i < point.length; i++) {
				XMLCoder xc = new XMLCoder();
				// 命令码
				xc.setCode("0A00000201");
				// 设置任务编号
				Date time = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
				xc.setTaskNum(sdf.format(time));
				// 网关地址
				xc.setTerminalAddress(terminalAddress);
				// 设置表计类型
				xc.setMeterType(1);
				xc.setOperCode(userID);
				xc.setDatasiteID(datasiteid);
				xc.setIp("{webip}");
				count++;
				parameters += "," + point[i];

				if (count == 10 && num >= 10) {
					count = 0;
					num -= 10;
					xc.setPara(parameters);
					list.add(xc);
					if (num >= 10) {
						parameters = "10";
					} else {
						parameters = String.valueOf(num);
					}
				} else if (num < 10 && num == count) {
					count = 0;
					num = 0;
					xc.setPara(parameters);
					list.add(xc);
				}
			}
		}
		meterPoint = "";
	
		String queryWaMeter = "select WATERMETER_POINT from watermeter where gather_id = "
				+ gatherID + " order by WATERMETER_POINT";
		PreparedStatement smWa = null;
		ResultSet rsWa = null;
		Connection conn1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			smWa = conn1.prepareStatement(queryWaMeter);
			rsWa = smWa.executeQuery();
			while (rsWa.next()) {
				meterPoint += rsWa.getString("WATERMETER_POINT") + ",";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, smWa, rsWa);
		}

		if (!"".equals(meterPoint)) {
			point = meterPoint.split(",");
			num = point.length;
			count = 0;
			if (num >= 10) {
				parameters = "10";
			} else if (num > 0 && num < 10) {
				parameters = String.valueOf(num);
			}
			for (int i = 0; i < point.length; i++) {
				XMLCoder xc = new XMLCoder();
				// 命令码
				xc.setCode("0A00000201");
				// 设置任务编号
				Date time = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
				xc.setTaskNum(sdf.format(time));
				// 网关地址
				xc.setTerminalAddress(terminalAddress);
				// 设置表计类型
				xc.setMeterType(2);
				xc.setOperCode(userID);
				xc.setDatasiteID(datasiteid);
				xc.setIp("{webip}");
				count++;
				parameters += "," + point[i];

				if (count == 10 && num >= 10) {
					count = 0;
					num -= 10;
					xc.setPara(parameters);
					list.add(xc);
					if (num >= 10) {
						parameters = "10";
					} else {
						parameters = String.valueOf(num);
					}
				} else if (num < 10 && num == count) {
					count = 0;
					num = 0;
					xc.setPara(parameters);
					list.add(xc);
				}
			}
		}

		String[] sendList = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			XMLCoder xc = list.get(i);
			sendList[i] = makeSendCode(xc);
		}

		return sendList;
	}

	/**
	 * 组发送XML
	 * 
	 * @param xmlCoder
	 * @return
	 */
	public String makeSendCode(XMLCoder xmlCoder) {
		String commondXML = null;
		commondXML = "<SFROOT gy='GW'><GW identify='1' "
				+ "opercode='"+ xmlCoder.getOperCode() + "' "
				+ "datasiteid='"+ xmlCoder.getDatasiteID() + "' "
				+ "ip='{webip}' "
				+ "tasknum='"+ xmlCoder.getTaskNum() + "' terminaladdress='"
				+ xmlCoder.getTerminalAddress() + "' way='" + xmlCoder.getWay()
				+ "'><command metertype='" + xmlCoder.getMeterType()
				+ "' code='" + xmlCoder.getCode() + "' pw='" + xmlCoder.getPw()
				+ "' autoread='" + xmlCoder.getAutoRead() + "' para='"
				+ xmlCoder.getPara() + "'></command></GW></SFROOT>\r\n";

		return commondXML;
	}

	/**
	 * 发送命令
	 * 
	 * @param command
	 * @return
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws NumberFormatException
	 */
	public String sendCommand(String command) throws NumberFormatException,
	UnknownHostException, IOException {
		String messageFromServer = null;

		Socket s = null;
		XMLConfiguration config = null;
		try {
			config = Configuration
					.getConfiguration("configuration/ProjectConfiguration.xml");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		String serverIP = config.getString("server.IP");
		String serverPort = config.getString("server.port");

		s = new Socket(serverIP, Integer.parseInt(serverPort));
		s.setSoTimeout(20 * 1000);
		OutputStream os = s.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);

		command=command.replace("{webip}", s.getLocalSocketAddress().toString());
		System.out.println(new
				Date().toLocaleString()+":向服务器发送的xml"+command);
		dos.writeBytes(command);
		dos.flush();

		InputStream is = s.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		messageFromServer = br.readLine();
		dos.close();
		br.close();
		s.close();

		return messageFromServer;
	}

	/**
	 * 清空网关
	 * 
	 * @param gatherID
	 * @throws SQLException
	 */
	public XMLCoder cleanGather(int gatherID, int port, String userID)
			throws SQLException {
		XMLCoder xc = new XMLCoder();
		// 命令码
		xc.setCode("0500001006");

		// 设置任务编号
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf.format(time));

		// 网关地址
		String sql = "select Gather_ID,Gather_Address,Gather_Password,SendWay,Protocol,datasite_id from Gather where Gather_ID="
				+ gatherID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			if (rs.next()) {
				// 网关地址
				xc.setTerminalAddress(rs.getString("Gather_Address"));
				xc.setDatasiteID(rs.getString("datasite_id"));
				// //通讯地址
				// xc.setWay(rs.getString("SendWay"));
				// //密码
				// //xc.setPw(rs.getString("Gather_Password"));
				// //规约
				// xc.setProtocol(rs.getString("Protocol"));
			} else {
				return null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		// 设置命令参数
		String parameters = "";
		parameters = String.valueOf(port);
		xc.setPara(parameters);
		xc.setOperCode(userID);

		return xc;
	}

	/**
	 * 表计下达
	 * 
	 * @param gatherID
	 *            网关ID
	 * @return
	 * @throws SQLException
	 */
	public String[] sendMeters(int gatherID, String userID) throws SQLException {
		ArrayList<XMLCoder> list = new ArrayList<XMLCoder>();

		int num = 0;
		int meterNum = 0;
		int waterMeterNum = 0;
		String Gather_Address = "";
		String Protocol = "";
		String isAutoRead = "1";
		String DATASITE_ID="";
		// 规约
		// xc.setProtocol("GW");

		// / 命令参数
		String parameters = "";

		String sql = "select Gather_ID,Gather_Address,Gather_Password,SendWay,Protocol,DATASITE_ID from Gather where Gather_ID="
				+ gatherID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			if (rs.next()) {
				Gather_Address = rs.getString("Gather_Address");
				// 网关地址
				// xc.setTerminalAddress(rs.getString("Gather_Address"));
				// 通讯地址
				// xc.setWay(rs.getString("SendWay"));
				// 密码
				// xc.setPw(rs.getString("Gather_Password"));
				// 规约
				Protocol = rs.getString("Protocol");
				DATASITE_ID=rs.getString("DATASITE_ID");
				// xc.setProtocol();
			} else {
				return null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		isAutoRead = GWGetAutoRead(Gather_Address);

		sql = "select count(*) from ammeter where gather_id = " + gatherID;

		// sql = "select a.a+b.b from "+
		// "(select count(*) as a from ammeter where gather_id = " + gatherID +
		// ")a,"+
		// "(select count(*) as b from watermeter where gather_id = " + gatherID
		// + ")b";

		PreparedStatement ps1=null;
		ResultSet rs1=null;
		Connection conn1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs1 = ps1.executeQuery();
			if (rs1.next()) {
				// 设置该网关下表计数量
				num = rs1.getInt(1);
				meterNum += num;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}

		sql = "select count(*) from watermeter where gather_id = " + gatherID;

		// sql = "select a.a+b.b from "+
		// "(select count(*) as a from ammeter where gather_id = " + gatherID +
		// ")a,"+
		// "(select count(*) as b from watermeter where gather_id = " + gatherID
		// + ")b";

		PreparedStatement pswa=null;
		ResultSet rswa=null;
		Connection conn2 = null;
		try
		{
			conn2 = ConnDB.getConnection();
			pswa = conn2.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rswa = pswa.executeQuery();
			if (rswa.next()) {
				// 设置该网关下表计数量
				waterMeterNum = rswa.getInt(1);
				meterNum += waterMeterNum;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, pswa, rswa);
		}

		// parameters += String.valueOf(xc.getCount());

		sql = "";
		sql += "select ";
		sql += "    Meter_Type,";
		sql += "    Meter_ID,";
		sql += "    Meter_Port ";
		sql += "from ";
		sql += "    ( ";
		// 电表(Meter_Type = 1)
		sql += "        select ";
		sql += "            1 as Meter_Type,";
		sql += "            AmMeter_ID as Meter_ID,";
		sql += "            ( ";
		sql += "                select ";
		sql += "                    TexingValue ";
		sql += "                from ";
		sql += "                    (TexingValue)c ";
		sql += "                where  ";
		sql += "                    c.MeteStyle_ID=a.MeteStyle_ID and ";
		sql += "                    c.MeterTexing_ID=19"; // 电表抄表端口
		sql += "            )Meter_Port,AmMeter_Point as Meter_Point ";
		sql += "        from ";
		sql += "            (AmMeter)a,";
		sql += "            (Gather)b  ";
		sql += "        where ";
		sql += "            a.Gather_ID=" + gatherID + " and ";
		sql += "            a.Gather_ID=b.Gather_ID ";

		sql += "        union all ";
		// 水表(Meter_Type = 2)
		sql += "        select ";
		sql += "            2 as Meter_Type,";
		sql += "            WaterMeter_ID as Meter_ID,";
		sql += "            ( ";
		sql += "                select ";
		sql += "                    TexingValue ";
		sql += "                from ";
		sql += "                    (TexingValue)c ";
		sql += "                where  ";
		sql += "                    c.MeteStyle_ID=a.MeteStyle_ID and ";
		sql += "                    c.MeterTexing_ID=28 "; // 水表抄表端口
		sql += "            )Meter_Port,WaterMeter_Point as Meter_Point ";
		sql += "        from ";
		sql += "            (WaterMeter)a,";
		sql += "            (Gather)b  ";
		sql += "        where ";
		sql += "            a.Gather_ID=" + gatherID + " and ";
		sql += "            a.Gather_ID=b.Gather_ID ";
		sql += "    )  s1 ";
		sql += "order by ";
		sql += "   Meter_Type,Meter_Point ";

		Connection conn0=null;
		PreparedStatement ps2 =null;
		ResultSet rs2 = null;

		String[] sendList =null;
		try
		{
			conn0 = ConnDB.getConnection();
			ps2 = conn0.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs2 = ps2.executeQuery();
			String meterType = "1";
			String meterID = "0";
			String meterPort = "2";

			if (num >= 10) {
				parameters = "10";
			} else if (num > 0 && num < 10) {
				parameters = String.valueOf(num);
			} else if (waterMeterNum >= 10) {
				parameters = "10";
			} else if (waterMeterNum > 0 && waterMeterNum < 10) {
				parameters = String.valueOf(waterMeterNum);
			}
			int count = 0;
			while (rs2.next()) {
				XMLCoder xc = new XMLCoder();
				// 操作员码
				//xc.setOperCode("1");
				// 通讯方式
				// xc.setWay("2");
				// 命令码
				xc.setCode("0400000201");
				// 通信密码
				xc.setPw("00000000000000000000000000000000");
				// 是否自动轮抄
				xc.setAutoRead(isAutoRead);
				// 设置任务编号
				Date time = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
				xc.setTaskNum(sdf.format(time));
				// 网关地址
				xc.setTerminalAddress(Gather_Address);
				// 规约
				xc.setProtocol(Protocol);
				// 电表数量
				xc.setCount(meterNum);
				// 设置表计类型
				meterType = rs2.getString("Meter_Type");
				xc.setMeterType(Integer.parseInt(meterType));
				// 设置opercode
				xc.setOperCode(userID);
				xc.setDatasiteID(DATASITE_ID);
				xc.setIp("{webip}");
				count++;
				if ("1".equals(meterType)) {
					meterID = rs2.getString("Meter_ID");
					//meterPort = "2";
					meterPort = rs2.getString("Meter_Port");//李嵘20150320

					sql = "select "
							+ "AmMeter_Point as Meter_Point,"
							+ "MeteStyle_ID,"
							+ "nvl((select TexingValue as TongxunSulv from (TexingValue)c where c.MeteStyle_ID=a.MeteStyle_ID and c.MeterTexing_ID=5),'2400') as TongxunSulv,"
							+ "nvl((select TexingValue as TongxunXieyi from (TexingValue)c where c.MeteStyle_ID=a.MeteStyle_ID and c.MeterTexing_ID=18),'30') as TongxunXieyi,"
							+ "AmMeter_485Address as Meter_485Address,"
							+ "AmMeter_Password  as Meter_Password,"
							+ "(select TexingValue as Feilv from (TexingValue)c where c.MeteStyle_ID=a.MeteStyle_ID and c.MeterTexing_ID=2) as Feilv,"
							+ "(select MeteStyle_Num from (MeteStyle)c where c.MeteStyle_ID=a.MeteStyle_ID) as MeteStyle_Num,"
							+ "(select MeteStyle_Name from (MeteStyle)c where c.MeteStyle_ID=a.MeteStyle_ID) as MeteStyle_Name,"
							+ "'000000000000' as  Gather_TongxinAddress,ISIMPORTANTUSER " + "from "//李嵘20150320
							+ "(AmMeter)a " + "where " + "AmMeter_ID=" + meterID;
					PreparedStatement ps3 = null;
					ResultSet rs3 = null;
					Connection conn3 = null;
					try
					{
						conn3 = ConnDB.getConnection();
						ps3 = conn3
								.prepareStatement(sql,
										ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_READ_ONLY);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							String meterpoint = rs3.getString("Meter_Point");
							String speed = rs3.getString("TongxunSulv");
							String protocol = rs3.getString("TongxunXieyi");
							String address = rs3.getString("Meter_485Address");
							String password = rs3.getString("Meter_Password");
							String feilv = rs3.getString("Feilv");
							if ("".equals(feilv) || feilv == null) {
								feilv = "0";
							}
							String part8 = "6,2";
							String collectoraddress = rs3
									.getString("Gather_TongxinAddress");

							//李嵘20150320 
							//大类号小类号：
							//D7	D6	D5		D4		D3		D2			D1		D0
							//备用	备用	ESAM：1	重点用户：1	双计量：1	统一拉合闸：1	透传：1	预付费表：1
							String bigkind = "0"; // 非重点用户、非ESAM
							String isimportant=rs3.getString("ISIMPORTANTUSER");//重点用户 李嵘20150320

							if (isimportant.equals("1"))
							{
								collectoraddress="000000000001";//为了配合朗金网关规约
								bigkind="1";//重点用户、非ESAM
							}
							String smallkind="0";
							String meterstylenum = rs3.getString("MeteStyle_Num");
							if (("07".equals(meterstylenum))
									|| ("08".equals(meterstylenum))
									|| ("09".equals(meterstylenum))) // 预付费表
							{
								smallkind = "1";
							}
							String meterstylename = rs3.getString("MeteStyle_Name");
							if(meterstylename.contains("ESAM")) {
								if(bigkind.equals("1")){
									bigkind = "3";
								} else {
									bigkind = "2";
								}
							}
							/*String bigkind = "1"; // 单相表
							String meterstylenum = rs3.getString("MeteStyle_Num");
							if (("02H".equals(meterstylenum))
									|| ("03H".equals(meterstylenum))
									|| ("05H".equals(meterstylenum))
									|| ("06H".equals(meterstylenum))
									|| ("08H".equals(meterstylenum))) // 三相表
							{
								bigkind = "2";
							}*/
							//String part10 = "0";
							parameters += "," + meterpoint + "," + meterpoint + ","
									+ speed + "," + meterPort + "," + protocol + ","

									+ address + "," + password + "," + feilv + ","
									+ part8 + "," + collectoraddress + "," + bigkind
									+ "," + smallkind;
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn3, ps3, rs3);
					}
					if (count == 10 && num >= 10) {
						count = 0;
						num -= 10;
						xc.setPara(parameters);
						list.add(xc);
						if (num >= 10) {
							parameters = "10";
						} else {
							parameters = String.valueOf(num);
						}
					} else if (num < 10 && num == count) {
						count = 0;
						num = 0;
						xc.setPara(parameters);
						list.add(xc);
						if (waterMeterNum >= 10) {
							parameters = "10";
						} else if (waterMeterNum > 0 && waterMeterNum < 10) {
							parameters = String.valueOf(waterMeterNum);
						}
					}

				} else if ("2".equals(meterType)) {
					meterID = rs2.getString("Meter_ID");
					//meterPort = "3";
					meterPort = rs2.getString("Meter_Port");//李嵘20150320
					sql = "select "
							+ "WaterMeter_Point as Meter_Point,"
							+ "MeteStyle_ID,"
							+ "nvl((select TexingValue as TongxunSulv from (TexingValue)c where c.MeteStyle_ID=a.MeteStyle_ID and c.MeterTexing_ID=23),'2400') as TongxunSulv,"
							+ "nvl((select TexingValue as TongxunXieyi from (TexingValue)c where c.MeteStyle_ID=a.MeteStyle_ID and c.MeterTexing_ID=27),'30') as TongxunXieyi,"
							+ "WaterMeter_485Address as Meter_485Address,"
							+ "WaterMeter_Password  as Meter_Password,"
							+ "(select TexingValue as Feilv from (TexingValue)c where c.MeteStyle_ID=a.MeteStyle_ID and c.MeterTexing_ID=20) as Feilv,"
							+ "(select TexingValue as IsTouChuan from (TexingValue)c where c.MeteStyle_ID=a.MeteStyle_ID and c.MeterTexing_ID=31) as IsTouChuan,"
							+ "("
							+ "select "
							+ "d.DictionaryValue_Value as makecode "
							+ "from "
							+ "(TexingValue)c,"
							+ "(DictionaryValue)d "
							+ "where "
							+ "c.MeteStyle_ID=a.MeteStyle_ID "
							+ "and c.MeterTexing_ID=29 and c.TexingValue = d.DictionaryValue_Num	and d.Dictionary_ID = 27"
							+ ") as makecode,"
							+ "(select MeteStyle_Num from (MeteStyle)c where c.MeteStyle_ID=a.MeteStyle_ID) as MeteStyle_Num,"
							+ "'000000000000' as  Gather_TongxinAddress " + "from "
							+ "(WaterMeter)a " + "where " + "WaterMeter_ID="
							+ meterID;
					Connection conn4=null;
					PreparedStatement ps4 =null;
					ResultSet rs4 = null;

					try
					{
						conn4 = ConnDB.getConnection();
						ps4 = conn4
								.prepareStatement(sql,
										ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_READ_ONLY);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							String meterpoint = rs4.getString("Meter_Point");
							String speed = rs4.getString("TongxunSulv");
							String protocol = rs4.getString("TongxunXieyi");
							String address = rs4.getString("Meter_485Address");
							String password = rs4.getString("Meter_Password");
							String feilv = rs4.getString("Feilv");
							int IsTouChuan=rs4.getInt("IsTouChuan");
							if ("".equals(feilv) || feilv == null) {
								feilv = "0";
							}
							String part8 = "6,2";
							String collectoraddress = rs4
									.getString("Gather_TongxinAddress");
							if (!protocol.equals(1))//只要不是97规约水表，都是188规约
							{
								collectoraddress = padLeft(
										rs4.getString("makecode"), 4)
										+ padLeft(rs4.getString("MeteStyle_Num"), 2)
										+ "000000";
							}
							/*String collectoraddress = padLeft(
									rs4.getString("makecode"), 4)
									+ padLeft(rs4.getString("MeteStyle_Num"), 2)
									+ "000000";*/
							String bigkind = "0";// 水表都不需要抄电流电压


							/*String meterstylenum = rs4.getString("MeteStyle_Num");
							if (("02H".equals(meterstylenum))
									|| ("03H".equals(meterstylenum))
									|| ("05H".equals(meterstylenum))
									|| ("06H".equals(meterstylenum))
									|| ("08H".equals(meterstylenum))) // 三相表
							{
								bigkind = "2";
							}*/
							String smallkind = "0";//水表都不是预付费表
							if(IsTouChuan==1){
								smallkind="2";
							}

							parameters += "," + meterpoint + "," + meterpoint + ","
									+ speed + "," + meterPort + "," + protocol + ","
									+ address + "," + password + "," + feilv + ","
									+ part8 + "," + collectoraddress + "," + bigkind
									+ "," + smallkind;
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn4, ps4, rs4);
					}

					if (count == 10 && waterMeterNum >= 10) {
						count = 0;
						waterMeterNum -= 10;
						xc.setPara(parameters);
						list.add(xc);
						if (waterMeterNum >= 10) {
							parameters = "10";
						} else {
							parameters = String.valueOf(waterMeterNum);
						}
					} else if (waterMeterNum < 10 && waterMeterNum == count) {
						count = 0;
						waterMeterNum = 0;
						xc.setPara(parameters);
						list.add(xc);
					}
				}

			}

			sendList = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				XMLCoder xc = list.get(i);
				sendList[i] = makeSendCode(xc);
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps2, rs2);
		}

		return sendList;
	}

	/**
	 * 网关校时
	 * 
	 * @param terminalAddress
	 * @return
	 * @throws SQLException
	 */
	public XMLCoder gatherCheckTime(String terminalAddress, String userID,String datasiteID)
			throws SQLException {
		XMLCoder xc = new XMLCoder();
		// 命令码
		xc.setCode("0500004003");
		// 设置任务编号
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("E");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyMMddHHmmss");
		xc.setTaskNum(sdf1.format(time));

		// 设置命令参数
		String weekDay = sdf.format(new Date());
		switch (weekDay) {
		case "星期一":
			weekDay = "01";
			break;
		case "星期二":
			weekDay = "02";
			break;
		case "星期三":
			weekDay = "03";
			break;
		case "星期四":
			weekDay = "04";
			break;
		case "星期五":
			weekDay = "05";
			break;
		case "星期六":
			weekDay = "06";
			break;
		case "星期日":
			weekDay = "07";
			break;
		}
		String para = sdf2.format(new Date()) + weekDay;
		xc.setPara(para);
		// 网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setOperCode(userID);
		xc.setDatasiteID(datasiteID);
		return xc;
	}
	/**
	 * 除通信外全部初始化
	 * 
	 * @param terminalAddress
	 * @return
	 */
	public XMLCoder gatherInitExceptComm(String terminalAddress, String userID) {
		XMLCoder xc = new XMLCoder();
		// 命令码
		xc.setCode("0100000800");
		// 设置任务编号
		Date time = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf1.format(time));

		xc.setPara("");
		// 网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setOperCode(userID);
		String sql = "select datasite_id from Gather where Gather_Address='"+terminalAddress+"'";
		Connection conn4=null;
		PreparedStatement ps4 =null;
		ResultSet rs4 = null;

		try
		{
			conn4 = ConnDB.getConnection();
			ps4 = conn4
					.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs4 = ps4.executeQuery();
			if (rs4.next()) {
				String datasite_id = rs4.getString("datasite_id");
				xc.setDatasiteID(datasite_id);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn4, ps4, rs4);
		}
		return xc;
	}


	/**
	 * 定时上报1 类数据任务设置
	 * 
	 * @param terminalAddress
	 * @return
	 */
	public XMLCoder gatherReport(String terminalAddress, String userID,int reportTime) {
		XMLCoder xc = new XMLCoder();
		// 命令码
		xc.setCode("0401010108");
		// 设置任务编号
		Date time = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf1.format(time));
		SimpleDateFormat times = new SimpleDateFormat("yyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);

		int w = cal.get(Calendar.DAY_OF_WEEK)-1;


		String timenum=times.format(time)+"0000000"+w;
		xc.setPara("0,"+reportTime+","+timenum+",1,2,FFFF0164FFFF0103");
		// 网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setOperCode(userID);
		String sql = "select datasite_id from Gather where Gather_Address='"+terminalAddress+"'";
		Connection conn4=null;
		PreparedStatement ps4 =null;
		ResultSet rs4 = null;

		try
		{
			conn4 = ConnDB.getConnection();
			ps4 = conn4
					.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs4 = ps4.executeQuery();
			if (rs4.next()) {
				String datasite_id = rs4.getString("datasite_id");
				xc.setDatasiteID(datasite_id);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn4, ps4, rs4);
		}
		return xc;
	}
	/**
	 * 定时上报1 类数据任务启动/停止设置
	 * 
	 * @param terminalAddress
	 * @return
	 */
	public XMLCoder gatherReportStart(String terminalAddress, String userID) {
		XMLCoder xc = new XMLCoder();
		// 命令码
		xc.setCode("0401010408");
		// 设置任务编号
		Date time = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf1.format(time));

		xc.setPara("1");
		// 网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setOperCode(userID);

		String sql = "select datasite_id from Gather where Gather_Address='"+terminalAddress+"'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn
					.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next()) {
				String datasite_id = rs.getString("datasite_id");
				xc.setDatasiteID(datasite_id);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return xc;
	}

	/**修改主站IP
	 * @param terminalAddress
	 * @param userID
	 * @param iP
	 * @param port
	 * @return
	 * @throws ParseException 
	 */
	public XMLCoder gatherFillDatas(String terminalAddress, String fillTimes,String userID) throws ParseException
	{
		XMLCoder xc = new XMLCoder();
		// 命令码
		xc.setCode("0DFFFF0264");
		// 设置任务编号
		Date time = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf1.format(time));
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat sdf33=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date date=sdf33.parse(fillTimes);  
		xc.setPara(sdf2.format(date));
		// 网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setOperCode(userID);
		String sql = "select datasite_id from Gather where Gather_Address='"+terminalAddress+"'";
		Connection conn4=null;
		PreparedStatement ps4 =null;
		ResultSet rs4 = null;

		try
		{
			conn4 = ConnDB.getConnection();
			ps4 = conn4
					.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs4 = ps4.executeQuery();
			if (rs4.next()) {
				String datasite_id = rs4.getString("datasite_id");
				xc.setDatasiteID(datasite_id);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn4, ps4, rs4);
		}
		return xc;
	}

	/**修改主站IP
	 * @param terminalAddress
	 * @param userID
	 * @param iP
	 * @param port
	 * @return
	 */
	public XMLCoder gatherChangeIP(String terminalAddress, String userID, String iP, String port,String datasiteID)
	{
		XMLCoder xc = new XMLCoder();
		// 命令码
		xc.setCode("0400000400");
		// 设置任务编号
		Date time = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf1.format(time));

		xc.setPara(iP+","+port+","+iP+","+port+",cmnet");
		// 网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setOperCode(userID);
		//xc.setDatasiteID(datasite_id);
		xc.setDatasiteID(datasiteID);
		return xc;
	}

	/**
	 * 版本读取
	 * 
	 * @param terminalAddress
	 * @return
	 */
	public XMLCoder readEition(String terminalAddress, String userID) {
		XMLCoder xc = new XMLCoder();
		// 命令码
		xc.setCode("0900000100");
		// 设置任务编号
		Date time = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf1.format(time));

		xc.setPara("");
		// 网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setOperCode(userID);
		String sql = "select datasite_id from Gather where Gather_Address='"+terminalAddress+"'";
		Connection conn4=null;
		PreparedStatement ps4 =null;
		ResultSet rs4 = null;

		try
		{
			conn4 = ConnDB.getConnection();
			ps4 = conn4
					.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs4 = ps4.executeQuery();
			if (rs4.next()) {
				String datasite_id = rs4.getString("datasite_id");
				xc.setDatasiteID(datasite_id);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn4, ps4, rs4);
		}
		return xc;
	}

	/**
	 * 时钟读取
	 * 
	 * @param terminalAddress
	 * @return
	 */
	public XMLCoder readTime(String terminalAddress, String userID) {
		XMLCoder xc = new XMLCoder();
		// 命令码
		xc.setCode("0C00000200");
		// 设置任务编号
		Date time = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf1.format(time));

		xc.setPara("");
		// 网关地址
		xc.setTerminalAddress(terminalAddress);
		xc.setOperCode(userID);
		String sql = "select datasite_id from Gather where Gather_Address='"+terminalAddress+"'";
		Connection conn4=null;
		PreparedStatement ps4 =null;
		ResultSet rs4 = null;

		try
		{
			conn4 = ConnDB.getConnection();
			ps4 = conn4
					.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs4 = ps4.executeQuery();
			if (rs4.next()) {
				String datasite_id = rs4.getString("datasite_id");
				xc.setDatasiteID(datasite_id);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn4, ps4, rs4);
		}
		return xc;
	}

	/**
	 * 配置之后更新数据库gather表
	 * 
	 * @param lastSetTime
	 * @param lastSetMsg
	 * @param gatherID
	 * @return
	 * @throws SQLException
	 */
	public boolean updateGather(String lastSetTime, String lastSetMsg,
			int gatherID) throws SQLException {
		String sql = "";
		if ("".equals(lastSetTime)) {
			sql = "update gather set LASTSETMSG='" + lastSetMsg
					+ "' where GATHER_ID = " + gatherID;
		} else {
			//			if ("表计下达".equals(lastSetMsg)) {
			//				sql = "update gather set LASTSETTIME='" + lastSetTime
			//						+ "',LASTSETMSG='" + lastSetMsg + "'"
			//						+  "' where GATHER_ID = " + gatherID;
			//			} else {
			sql = "update gather set LASTSETMSG='" + lastSetMsg
					+ "',lastSetTime='" + lastSetTime + "' where GATHER_ID = "
					+ gatherID;
			//			}
		}

		Connection conn = null;
		PreparedStatement sm = null;
		boolean b =false;
		try
		{
			conn = ConnDB.getConnection();
			sm = conn.prepareStatement(sql);
			b = (sm.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, sm);
		}

		return b;
	}

	/**
	 * 国网集中器获取自动轮抄标志
	 * 
	 * @param terminaladdress
	 * @return
	 * @throws SQLException
	 */
	public String GWGetAutoRead(String terminaladdress) throws SQLException {
		String autoread = "1";
		String sql = "select Gather_Style from Gather where Gather_Address='"
				+ terminaladdress + "'";

		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs=null;
		try
		{
			conn = ConnDB.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			if (rs.next()) {
				autoread = rs.getString("Gather_Style");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, sm, rs);
		}

		return autoread;
	}

	/**
	 * 字符串补齐，不足位用"0"补齐
	 * 
	 * @param s
	 *            需要补齐的字符串
	 * @param length
	 *            需要补齐成的长度
	 * @return 补齐后的字符串
	 */
	private static String padLeft(String s, int length) {
		if (s.length() >= length) {
			return s.substring(0, length);
		}
		byte[] bs = new byte[length];
		byte[] ss = s.getBytes();
		Arrays.fill(bs, (byte) (48 & 0xff));
		System.arraycopy(ss, 0, bs, length - ss.length, ss.length);
		return new String(bs);
	}




}
