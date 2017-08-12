package com.sf.energy.server.serverReciveMessage;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sf.energy.server.db.AutoUpDataSaver;
import com.sf.energy.server.db.OnOffline;
import com.sf.energy.server.tcp.TranSession;
import com.sf.energy.server.tcp.WebSession;
import com.sf.energy.server.tftcp.ServerHandler;
import com.sf.energy.server.tftcp.Tool;
import com.sf.energy.server.tool.GatherToDataSiteDao;
import com.sf.energy.server.tool.GatherToDataSiteModel;

public class ServerData
{
	OnOffline offline = new OnOffline();
	Tool tool=new Tool();
	TranSession session=new TranSession();
	WebSession webSession=new WebSession();
	public static Logger logger = Logger.getLogger(ServerData.class);
	
	public ServerData()
	{
		
	}
	
	public void serverDataSiteCommand(Document doc)
	{
		Element commandback = (Element) doc.getElementsByTagName("commandback")
				.item(0);
		String code = commandback.getAttribute("code");
		if ("alarm".equals(code)) // 报警
		{
			// BackToWeb("", "all", rtinfo);
			// DGAlarm(commandback);
			return;
		}
		if ("correcttime".equals(code)) // 校时
		{
			// CorrectTimeBack(commandback);
			return;
		}
		if ("terminalstate".equals(code)) // 在线网关
		{
			terminalOnline(commandback);
			return;
		}
		logger.info("end");
		if ((code.length() > 6) && (code.substring(0, 6) == "autoup")) // 主动上传
		{
			String commandcode = code.substring(6);
			String metertype = commandback.getAttribute("metertype"); // 表类型,1为电表,2为水表
			// String terminaladdress =
			// gynode.Attributes["terminaladdress"].Value;
			// AutoUpBack(commandback, terminaladdress, commandcode, metertype);
			// String tasknum = CommonCS.GetTaskNum();
			// String rtxml = "<SFROOT gy='" + gyname + "'><" + gyname +
			// " opercode='-1'  tasknum='" + tasknum + "' terminaladdress='" +
			// terminaladdress + "'>";
			// rtxml += "<command code='autoup" + commandcode + "' metertype='"
			// + metertype + "' para=''/></GW></SFROOT>";
			// ServerToDataSiteSendText(rtxml, 1);
			return;
		}
		// if (code == "0E00000000") //故障报警
		// {
		// return;
		// }
		// if ((code.Length>=10)&&(code.Substring(0,10) ==
		// "1000000100"))//透传教室控制器设置开关灯时间命令及路灯命令
		// {
		// String tasknum = gynode.Attributes["tasknum"].Value;
		// String[] str = code.Split(',');
		// if (str[1] == "lamp")
		// {
		// lp.myExecBack(tasknum, rtinfo);
		// }
		// else if (str[1] == "classroom")
		// {
		// cr.myExecBack(tasknum, rtinfo);
		// }
		// return;
		// }
	}
	
	/**
	 * 解析在线网关的xml，将相应的信息存入数据库
	 * 
	 * @param commandback
	 *            元素节点
	 */
	public void terminalOnline(Element commandback)
	{
		try
		{
			NodeList children = commandback.getChildNodes();
			// /遍历commandback孩子节点
			for (int i = 0; i < children.getLength(); i++)
			{
				Element node = (Element) children.item(i);
				String terminaladdress = node.getAttribute("address");
				String ip = node.getAttribute("ip");
				String time = node.getAttribute("time");
				logger.info(terminaladdress + "  " + ip + "  " + time);
				offline.dGOnlineUpdate(terminaladdress, ip, time);
				// /根据网关地址查找网关ID
				int terminalid = offline.findTerminalId(terminaladdress);
				logger.info(terminalid);
				String meterarray = "";
				NodeList childrenList = node.getChildNodes();
				// /遍历commandback孩子节点的孩子节点
				for (int j = 0; j < childrenList.getLength(); j++)
				{
					node = (Element) childrenList.item(j);
					String metertype = node.getAttribute("metertype");
					String address = node.getAttribute("address");
					meterarray += address + ",";
					String metertime = node.getAttribute("time");
					logger.info(terminalid + "   " + address);
					switch (metertype)
					{
						case "1": // 电表
						case "2": // 水表
							// /数据中转站定时上传的在线表计更新数据库
							offline.meterOnlineUpdate(terminalid, address,
									metertime, metertype);
							break;
						// case "3"://路灯
						// String state1 = xnn.Attributes["state1"].Value;
						// String state2 = xnn.Attributes["state2"].Value;
						// String time1 = xnn.Attributes["time1"].Value;
						// String time2 = xnn.Attributes["time2"].Value;
						// OnOffline.LampOnLineUpdate(terminalid, address, time,
						// state1, state2, time1, time2);
						// break;
						// case "4"://教室照明
						// String lightvalue =
						// xnn.Attributes["CurrentLightValue"].Value;
						// String peoplesum =
						// xnn.Attributes["CurrentPeopleSum"].Value;
						// String lineonsum =
						// xnn.Attributes["CurrentLineOnSum"].Value;
						// OnOffline.ClassroomOnLineUpdate(terminalid, address,
						// time, lightvalue, peoplesum, lineonsum);
						// break;
						// case "5"://Lonworks电表
						// OnOffline.LonMeterOnlineUpdate(address, metertime);
						// break;
						default:
							break;
					}
				}
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 对前置机主动上传dataset格式数据的处理
	 * 
	 * @param doc
	 * @throws Exception 
	 */
	public void autoUpDateSet(Document doc) throws Exception
	{
		Element root = doc.getDocumentElement();
		NodeList node = root.getChildNodes();
		//System.out.println("table"+node.getLength());
		for (int i = 0; i < node.getLength(); i++)
		{
			// 获取子节点table
			Element nodeElement=(Element)node.item(i);
			////System.out.println(nodeElement.getChildNodes().item(2).getFirstChild().getNodeValue());
			
			int columnnum = node.item(i).getChildNodes().getLength();
			//System.out.println(columnnum);
			switch (columnnum)
			{
			// case 5: //教室照明控制器状态
			// AutoUp.AutoUpSaveClassroom(xn);//存入数据库
			// break;
				case 6: // 单相
					AutoUpDataSaver.autoUpSave(nodeElement);//存入数据库
					break;
				case 25: // LonWorks
					// AutoUp.AutoUpSaveLonWorks(xn);
					break;
				case 26:// 三相
					AutoUpDataSaver.autoUpSaveThreePhase(nodeElement);//存入数据库
					break;
				default:
					break;
			}
		}
		
	}
	
	
	/**
	 * 将中转站上传上来的信息发送到WEB端
	 * @param useid
	 * @param strTaskNum
	 * @param strMsg
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public void backToWeb(String info) throws ParserConfigurationException, SAXException, IOException{
		//System.out.println("收到了将要转发到web的xml"+info);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(info)));
		// /获取根元素
		Element root = doc.getDocumentElement();
		
		Element gy = (Element) root.getFirstChild();
		String ip = gy.getAttribute("ip");
		//System.out.println("ip is"+ip);		
		webSession=tool.findWebSession(ip);
		webSession.getIoSession().write(info);
		
	}
	/**
	 * 将web端发送的信息传到中转站
	 * @param info
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public void toTran(String info,String terminaladdress) throws SQLException{
		//根据数据网关查找出中转站的ip地址
		GatherToDataSiteModel gatherToDataSiteModel=new GatherToDataSiteModel();
		GatherToDataSiteDao gatherToDataSiteDao=new GatherToDataSiteDao();
		gatherToDataSiteModel=gatherToDataSiteDao.queryByGatherAddress(terminaladdress);
		String str=gatherToDataSiteModel.getDataSiteIP();
		//System.out.println(str);
		session=tool.findSession(str);
		session.getIoSession().write(info);
	}
	public  String f(String info) throws ParserConfigurationException, SAXException, IOException{
		// /获取xml树
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = null;
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new InputSource(new StringReader(info)));
				// /获取根元素
				Element root = doc.getDocumentElement();
				Element element=(Element) root.getElementsByTagName("result").item(0);
				String z=element.getAttribute("ZY0");
		        return z;
	}
	
}
