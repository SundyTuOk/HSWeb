package com.sf.energy.server.db;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.mina.core.future.WriteFuture;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sf.energy.server.tcp.TranSession;
import com.sf.energy.server.tftcp.Tool;
import com.sf.energy.util.ConnDB;

public class Lamp  {
	private static Lamp uniqueInstance=null;
	// /失败重抄次数,最多2次
	private  int readTimes = 0;
	// /抄某一规约的所有集中器命令是否已全部返回
	private  boolean ifReadOver = false;

	private  boolean ifstarttimer = false;
	// 当前命令的任务编号
	public  String TaskNum = null;
	// /上一次命令的发送时间
	private Date lastbacktime;
	// /待发送的命令数组
	public   ArrayList<String> CommandArray = new ArrayList<String>();
	// /发送失败的命令数组
	public   ArrayList<String> FailArray = new ArrayList<String>();

	//超时时间定额
	int timeOutNum=-1;

	static Timer timer;

	private Lamp()
	{

	}
	public static Lamp getInstance() {  
		if (uniqueInstance == null) {  
			uniqueInstance = new Lamp();  
		}  
		return uniqueInstance;  
	}  


	/**
	 * 组建路灯命令
	 * @throws Exception
	 */
	public  void SetLampSchedule() throws Exception
	{
		Date thisday = new Date();
		SimpleDateFormat taskNumDF = new SimpleDateFormat("yyMMddHHmmssSSS");
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");	

		String TaskNum="";
		//查询网关以及对应的信息，每条记录是一个网关
		String strsql = "select a.SLLine_ID,a.Area_ID,a.Gather_ID,(select Gather_Address from Gather where Gather_ID=a.Gather_ID) as Gather_Address,(select IP from Gather where Gather_ID=a.Gather_ID) as IP,"
				+ "b.SwitchScheduleID as schoolId,c.SwitchScheduleID as areaId,d.SwitchScheduleID as lineId"
				+ " from SLLine a left join (select SwitchScheduleID,Type,Area_ID,SLLine_ID from SLSchedule where "
				+ "yyyy = "
				+ getTodayYear(thisday)
				+ " and mm = "
				+ getTodayMonth(thisday)
				+ " and dd = "
				+ getTodayDay(thisday)
				+ " and type = 1) b ON 1 = 1 left join (select SwitchScheduleID,Type,Area_ID,SLLine_ID from SLSchedule"
				+ " where yyyy = "
				+ getTodayYear(thisday)
				+ " and mm = "
				+ getTodayMonth(thisday)
				+ " and dd = "
				+ getTodayDay(thisday)
				+ " and type = 2) c ON a.Area_ID = c.Area_ID left join (select SwitchScheduleID,Type,Area_ID,SLLine_ID"
				+ " from SLSchedule where yyyy = "
				+ getTodayYear(thisday)
				+ " and mm = "
				+ getTodayMonth(thisday)
				+ " and dd = "
				+ getTodayDay(thisday)
				+ " and type = 3) d ON a.SLLine_ID = d.SLLine_ID";
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection conn =null;
		try
		{
			conn=ConnDB.getConnection();
			ps = conn.prepareStatement(strsql);
			rs=ps.executeQuery();
			while(rs.next())
			{	
				String areaid = rs.getString("Area_ID");//区域
				String terminaladdress = rs.getString("Gather_Address"); //网关地址
				String lineid = rs.getString("SLLine_ID");//回路
				String schoolscheduleid = rs.getString("schoolId");//学校时间表ID
				String areascheduleid = rs.getString("areaId");//区域时间表ID
				String linescheduleid = rs.getString("lineId");//回路时间表ID
				String IP=rs.getString("IP");

				String thissheduleid=""; 
				thissheduleid = linescheduleid; //默认以回路时间表ID为准
				if (linescheduleid==null||"".equals(linescheduleid))
				{
					if (areascheduleid!=null && !"".equals(areascheduleid)) //区域
					{
						thissheduleid = areascheduleid;
					}
					else
					{
						thissheduleid = schoolscheduleid; //学校
					}
				}
				if (thissheduleid==null||"".equals(thissheduleid))
				{
					continue;
				}
				//查询该网关下的控制器
				strsql = "select SLKongzhi_Port,SLKongzhi_485Address,SLKongzhi_Index,SLLine_Name from (SLLine)a,(SLKongzhi)b where a.SLKongZhi_ID=b.SLKongzhi_ID and SLLine_ID=" + lineid;

				PreparedStatement ps1 = null;
				ResultSet controlRs=null;
				Connection conn1 = null;
				try
				{
					conn1 = ConnDB.getConnection();
					ps1 = conn1.prepareStatement(strsql);
					controlRs=ps1.executeQuery();
					while(controlRs.next())
					{
						String controlport = controlRs.getString(1);
						String controladdress = controlRs.getString(2);
						String lineindex = controlRs.getString(3);
						String linename = controlRs.getString(4);
						TaskNum=taskNumDF.format(new Date());

						/**
						 * 校时命令组件
						 */
						String para = "lamp,5D," + controlport + ",1200,n,8,2,1,5,10," + controladdress + "," + df.format(new Date()) + ",,";
						String strCommand = "<SFROOT gy=\"GW\"><GW identify=\"1\" terminaladdress=\"" + terminaladdress + "\" IP=\"" + IP + "\" opercode=\"-1\" tasknum=\"" + TaskNum + "\" way=\"2\"><command code=\"1000000100\" pw=\"\" para=\"" + para + "\"/></GW></SFROOT>";
						CommandArray.add(strCommand); //校时

						//读取参数
						strsql = "select Onhh1,Onmm1,Offhh1,Offmm1,Onhh2,Onmm2,Offhh2,Offmm2 from SLSchedule where SwitchScheduleID=" + thissheduleid;
						PreparedStatement ps2 = null;
						ResultSet canshuRs=null;
						Connection conn2 = null;
						try
						{
							conn2 = ConnDB.getConnection();
							ps2 = conn2.prepareStatement(strsql);
							canshuRs=ps2.executeQuery();
							while(canshuRs.next())
							{
								String hh1 = canshuRs.getString(1); //第一次开灯时
								String mm1 = canshuRs.getString(2); //第一次开灯分
								String hh2 = canshuRs.getString(3);//第一次关灯时
								String mm2 = canshuRs.getString(4); //第一次关灯分
								String hh3 = canshuRs.getString(5);//第二次开灯时
								String mm3 = canshuRs.getString(6);//第二次开灯分
								String hh4 = canshuRs.getString(7);//第二次关灯时
								String mm4 = canshuRs.getString(8);//第二次关灯分

								if (hh1==null ||"".equals(hh1) || mm1==null ||"".equals(mm1)  ||hh2==null ||"".equals(hh2) || mm2==null ||"".equals(mm2) )
								{
									continue;
								}
								hh1 = padLeft0(hh1);//第一次开灯时

								mm1 = padLeft0(mm1); //第一次开灯分
								hh2 = padLeft0(hh2); //第一次关灯时
								mm2 = padLeft0(mm2) ; //第一次关灯分

								String schedulePara = "double";//默认双时段落
								if (hh3==null ||"".equals(hh3) || mm3==null ||"".equals(mm3)  ||hh4==null ||"".equals(hh4) || mm2==null ||"".equals(mm4) )
								{
									schedulePara = "normal,0," + hh2 + mm2 + ",0000";//单时段
									hh4 = hh2;
									mm4 = mm2;
								}
								else
								{
									hh3 = padLeft0(hh3); //第二次开灯时
									mm3 = padLeft0(mm3); //第二次开灯分
									hh4 = padLeft0(hh4); //第二次关灯时
									mm4 = padLeft0(mm4); //第二次关灯分
									schedulePara = "double,0," + hh2 + mm2 + "," + hh3 + mm3;//双时段
								}

								para = controlport + ",1200,n,8,2,1,5,10,";
								if ("1".equals(lineindex)) //1路
								{
									para = "lamp,53," + para ;
								}
								else if ("2".equals(lineindex)) //2路
								{
									para = "lamp,54," + para ;
								}
								para += controladdress + "," + schedulePara;

								/**
								 * 设置工作模式及中间两个时间点的命令组建
								 */
								//生成任务编码
								TaskNum=taskNumDF.format(new Date());
								strCommand = "<SFROOT gy=\"GW\"><GW identify=\"1\" terminaladdress=\"" + terminaladdress + "\"  IP=\"" + IP + "\" opercode=\"-1\" tasknum=\"" + TaskNum + "\" way=\"2\"><command code=\"1000000100\" pw=\"\" para=\"" + para + "\"/></GW></SFROOT>";
								CommandArray.add(strCommand); //设置工作模式命令

								//            		 WriteCommandInfo(lineid,standardDf.format(new Date()) , "发送", "设置工作模式", TaskNum);

								para = controlport + ",1200,n,8,2,1,5,10,";
								if ("1".equals(lineindex)) //1路
								{
									para = "lamp,5E," + para;
								}
								else if ("2".equals(lineindex)) //2路
								{
									para = "lamp,60," + para;
								}

								para += controladdress + "," + hh1 + mm1 + "," + hh4 + mm4;
								/**
								 * 设置1-6月头尾两个时间点的命令组建
								 */
								TaskNum=taskNumDF.format(new Date());
								strCommand = "<SFROOT gy=\"GW\"><GW  identify=\"1\" terminaladdress=\"" + terminaladdress + "\" IP=\"" + IP + "\" opercode=\"-1\" tasknum=\"" + TaskNum + "\" way=\"2\"><command code=\"1000000100\" pw=\"\" para=\"" + para + "\"/></GW></SFROOT>";
								CommandArray.add(strCommand); //设置1-6月头尾两个时间点

								// WriteCommandInfo(lineid, standardDf.format(new Date()) , "发送", "设置1-6月开关灯时间", TaskNum);

								para = controlport + ",1200,n,8,2,1,5,10,";
								if ("1".equals(lineindex)) //1路
								{
									para = "lamp,5F," + para;
								}
								else if ("2".equals(lineindex)) //2路
								{
									para = "lamp,61," + para;
								}
								para += controladdress + "," + hh1 + mm1 + "," + hh4 + mm4;
								/**
								 * 设置7-12月头尾两个时间点的命令组建
								 */
								TaskNum=taskNumDF.format(new Date());
								strCommand = "<SFROOT gy=\"GW\"><GW identify=\"1\" terminaladdress=\"" + terminaladdress + "\" IP=\"" + IP + "\" opercode=\"-1\" tasknum=\"" + TaskNum + "\" way=\"2\"><command code=\"1000000100\" pw=\"\" para=\"" + para + "\"/></GW></SFROOT>";
								CommandArray.add(strCommand); //设置7-12月头尾两个时间点

								//WriteCommandInfo(lineid, standardDf.format(new Date()) , "发送", "设置7-12月开关灯时间", TaskNum);
							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release(conn2, ps2, canshuRs);
						}

					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn1, ps1, controlRs);
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		firstSend();
	}

	public void firstSend()
	{
		//System.out.println("第一遍发送命令 开始定时！*******************************************************************************************");
		readTimes = 0;
		ifReadOver = false;

		if (CommandArray.size() > 0) {
			// 定时器的逻辑打开
			ifstarttimer = true;
		}
		timer = new Timer();
		timer.schedule(new RemindTask(), 10000,5000);
		SendTask();
	}

	/**
	 * 定时其逻辑
	 * @author Administrator
	 *
	 */
	class RemindTask extends TimerTask {
		public void run() {

			MyTimerTick();
		}
	}
	/**
	 * 定时器具体的逻辑
	 */
	private void MyTimerTick() {
		Date dt = new Date();
		Date temp = null;
		temp = addminutes(dt, timeOutNum);
		if (ifstarttimer) {
			if ((!ifReadOver) && (temp.after(lastbacktime))) {
				if (CommandArray.size() > 0) {
					String errorcommand = CommandArray.get(0);
					FailArray.add(errorcommand);
					CommandArray.remove(0);

				}
				//System.out.println("超时发送下一条命令！*******************************************************************************************");
				SendTask();
			}
		}

	}
	/**
	 * 发送路灯参数
	 */
	private void SendTask() {
		//System.out.println("共有"+CommandArray.size()+"命令！************************************命令总数************************");
		if (CommandArray.size() > 0) {

			String strCommand = CommandArray.get(0);
			// 发送命令
			boolean issucceed = false;
			try {
				//System.out.println("发送一个命令："+strCommand+"****************************************命令发送***************************************");
				issucceed = sendLampCommand(strCommand);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lastbacktime = new Date();
			if (!issucceed) {
				String errorcommand = CommandArray.get(0);
				FailArray.add(errorcommand);

				CommandArray.remove(0);

				SendTask();
			}
		} else {

			if (readTimes < 2) // 重抄2次
			{
				// 从新发送两次，检查是否有发送失败的
				readTimes++;
				for (String ob : FailArray) {
					CommandArray.add(ob);
				}
				FailArray.clear();
				SendTask();
			} else {
				ifReadOver = true;
				ifstarttimer = false;
				FailArray.clear();
			}
		}

	}

	/**
	 * 返回解析及存数据库
	 * 
	 * @param strTaskNum
	 *            任务编号
	 * @param TaskMsg
	 *            任务信息
	 */
	public  void myExecBack(String strTaskNum, String TaskMsg) {

		//System.out.println("收到路灯命令的回复！"+TaskMsg+"*******************************************************************************************");
		String state = "";
		if (strTaskNum.equals(TaskNum)) {
			lastbacktime = new Date();
			NodeList list = null;
			try {
				list = getNodeList(TaskMsg, "commandback");
			} catch (Exception e) {
				//System.out.println("解析有问题");
				e.printStackTrace();
			}
			Element commandBack = (Element) list.item(0);
			String error = commandBack.getAttribute("error");

			if (CommandArray.size() > 0) {
				String exectime = null;
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				exectime = df.format(new Date());

				if (error.equals("0")) // 抄读失败
				{
					state = "成功";
				} else
					// 抄读失败
				{
					state = "失败";
					String errorcommand = CommandArray.get(0);
					FailArray.add(errorcommand);
				}
				String strsql = "insert into SLHistory(SLHistory_Time,SLHistory_State,Users_ID,SLHistory_Style,SLHistory_Opr) ";
				strsql += "values(to_date('" + exectime + "','yyyy-mm-dd hh24:mi:ss'),'" + state + "',0,'自动','路灯计划任务命令')";

				PreparedStatement ps = null;
				Connection conn = null;
				try {
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(strsql);
					ps.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					ConnDB.release(conn, ps);
				}

				CommandArray.remove(0);
			}
			//System.out.println(state+"发送下一条命令！*******************************************************************************************");
			SendTask();
		}
	}

	/**
	 * 给对应的时间添加分钟
	 * 
	 * @param time
	 *            时间
	 * @param min
	 *            分钟数
	 * @return
	 */
	private Date addminutes(Date time, int min) {
		Date result = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.MINUTE, min);
		result = cal.getTime();

		return result;

	}



	/**
	 * 发送过一条命令
	 * @param command
	 * @return
	 * @throws SQLException
	 */
	public boolean sendLampCommand(String command) throws SQLException {
		/***********************************************************************/
		NodeList list = null;
		try {
			list = getNodeList(command, "GW");
		} catch (Exception e) {
			//System.out.println("解析有问题");
			e.printStackTrace();
		}
		Element commandBack = (Element) list.item(0);
		// 解析XML 获得 tasknum 和 IP
		TaskNum = commandBack.getAttribute("tasknum");
		String terminalIP = commandBack.getAttribute("IP");
		/***********************************注意由于是从数据库里读出来的网关IP有”/“ 所以西面一句话加了substring函数*********************************************************************/
		terminalIP = terminalIP.substring(1, terminalIP.length());
		String terminaladdress = commandBack.getAttribute("terminaladdress");

		//System.out.println("任务编号是：" + TaskNum);
		//System.out.println("网关IP是：" + terminalIP);
		try
		{
			command=rebuildComand(command);
		} catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("命令是：" + command);


		String sql = "select datasite_ip as IP from (datasite)a ,(gather)b where a.datasite_ID=b.datasite_ID and gather_address='"
				+ terminaladdress + "'";
		String dataSite_IP = "";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
				dataSite_IP = rs.getString("IP");
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		//		if (rs.next())
		//			dataSite_IP = rs.getString("IP");
		/***********************************************************************/

		Tool tool = new Tool();
		TranSession session = tool.findSession(dataSite_IP);
		// 获得session，
		if (session == null) {
			return false;
		} else {
			WriteFuture wf = session.getIoSession().write(command);
			return true;
		}
	}
	/**
	 * 判断是否是校时命令，如果是的话，更新校时的参数
	 * @param command
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private String rebuildComand(String command) throws ParserConfigurationException, SAXException, IOException
	{
		InputSource source = null;
		StringReader read = null;
		String info=command;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dombuilder = domfac.newDocumentBuilder();

		read = new StringReader(command);

		source = new InputSource(read);

		Document document = dombuilder.parse(source);
		NodeList list = document.getElementsByTagName("command");

		Element commandBack = (Element) list.item(0);
		String para = commandBack.getAttribute("para");
		String[] canshu=para.split(",");
		if("5D".equals(canshu[1]))
		{	
			SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");	
			canshu[11]=df.format(new Date());
			String  newPara="";
			for(String a:canshu)
			{
				newPara+=a+",";
			}
			newPara+=",";
			commandBack.setAttribute("para", newPara);

			info=callWriteXmlString(document);
		}
		return info;
	}

	/**
	 * 获得某个节点的
	 * 
	 * @param xml
	 *            XML字符串
	 * @param tagName
	 *            节点的名称
	 * @return 对应节点的element对象
	 * @throws Exception
	 */
	private static NodeList getNodeList(String xml, String tagName) throws Exception {
		InputSource source = null;
		StringReader read = null;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dombuilder = domfac.newDocumentBuilder();

		read = new StringReader(xml);

		source = new InputSource(read);

		Document document = dombuilder.parse(source);
		NodeList nodeList = document.getElementsByTagName(tagName);
		return nodeList;
	}

	/**
	 * 从组xml
	 * @param doc
	 * @return
	 */
	private static String callWriteXmlString(Document doc) {

		try {
			Source source = new DOMSource(doc);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			OutputStreamWriter write = new OutputStreamWriter(outStream);
			Result result = new StreamResult(write);

			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			//xformer.setOutputProperty(OutputKeys.ENCODING, encoding);

			xformer.transform(source, result);
			return outStream.toString();

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (TransformerException e) {
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * 关闭连接
	 * @param pstmt
	 * @param rs
	 * @throws SQLException
	 */
	//	private static void close(PreparedStatement pstmt, ResultSet rs)
	//			throws SQLException
	//	{
	//		if (rs != null)
	//			rs.close();
	//
	//		if (pstmt != null)
	//			pstmt.close();
	//
	//	}
	/**
	 * 获得所查日期的年
	 * @param time
	 * @return
	 */
	private static int getTodayYear(Date time)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		int year = 0;
		year = Integer.parseInt(df.format(time));
		return year;
	}
	/**
	 * 获得所查日期的月
	 * @param time
	 * @return
	 */
	private static int getTodayMonth(Date time)
	{
		SimpleDateFormat df = new SimpleDateFormat("MM");
		int month = 0;
		month = Integer.parseInt(df.format(time));
		return month;
	}
	/**
	 * 获得所查的日期的天
	 * @param time
	 * @return
	 */
	private static int getTodayDay(Date time)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd");
		int day = 0;
		day = Integer.parseInt(df.format(time));
		return day;
	}
	/**
	 * 参数不满两位的左侧添加0
	 * @param canshu 参数
	 * @return
	 */
	private static String padLeft0(String canshu)
	{
		if(canshu.length()<2)
		{
			canshu="0"+canshu;
		}
		return canshu;
	}
}
