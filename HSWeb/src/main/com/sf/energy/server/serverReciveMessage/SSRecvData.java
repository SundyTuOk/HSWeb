package com.sf.energy.server.serverReciveMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

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

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.session.IoSession;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sf.energy.server.db.CommandModel;
import com.sf.energy.server.db.Lamp;
import com.sf.energy.server.tcp.TranSession;
import com.sf.energy.server.tftcp.Tool;

/**
 * 服务器接收到中转站报文后的处理
 * 
 * @author kuaiyue
 * @version 1.0
 */
public class SSRecvData
{
	Tool tool = new Tool();
	TranSession session = new TranSession();
	ServerData serverData = new ServerData();
	private String info;
	private IoSession ioSession;

	public SSRecvData(String info, IoSession ioSession)
	{
		this.info = info;
		this.ioSession = ioSession;
	}

	/**
	 * 服务器接收到中转站数据或者web端数据后的处理函数
	 * 
	 * @throws Exception
	 */
	public void receiveFromFront() throws Exception
	{
		String ip = ioSession.getRemoteAddress().toString();
		if (info.length() < 20)
		{
			return;
		}

		// /获取xml树
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(info)));
		// /获取根元素
		Element root = doc.getDocumentElement();
		if (root.getTagName() == "NewDataSet")
		{
			serverData.autoUpDateSet(doc);
			return;
		}

		// 得到第一个孩子节点
		Element gy = (Element) root.getFirstChild();
		// 标识xml的去向
		String identify = gy.getAttribute("identify");
		String opercode = gy.getAttribute("opercode");
		String terminaladdress = gy.getAttribute("terminaladdress");

		//System.out.println(terminaladdress);
		switch (identify)
		{
		case "0":// 服务器Demo上发送的命令
			// String tasknum = gy.getAttribute("tasknum");
			// frmShebei.myExecBack(tasknum, strs1[0]);
			break;
		case "-1":// 服务器与数据中转站之间的一些命令
			// serverData.serverDataSiteCommand(doc);
			break;
		// web端过来的信息，将发往中转站
		case "1":
			gy.setAttribute("ip", ip);
			info = callWriteXmlString(doc);
			serverData.toTran(info, terminaladdress);
			break;
		// 中转站过来的信息，将发往web端
		case "2":
			String tasknum = gy.getAttribute("tasknum");
			String lampTaskNum= Lamp.getInstance().TaskNum;
			if (tasknum.equals(lampTaskNum))
			{
				//System.out.println("这条返回是路灯的命令，开始对该命令进行出路***************************************");
				
				info = callWriteXmlString(doc);
				Lamp.getInstance().myExecBack(tasknum, info);
			} else
			{
				info = callWriteXmlString(doc);
				serverData.backToWeb(info);
			}
			break;
		}
	}

	// 将Document内容 写入XML字符串并返回
	private String callWriteXmlString(Document doc)
	{

		try
		{
			Source source = new DOMSource(doc);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			OutputStreamWriter write = new OutputStreamWriter(outStream);
			Result result = new StreamResult(write);

			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			// xformer.setOutputProperty(OutputKeys.ENCODING, encoding);

			xformer.transform(source, result);		
			return outStream.toString();

		} catch (TransformerConfigurationException e)
		{
			e.printStackTrace();
			return null;
		} catch (TransformerException e)
		{
			e.printStackTrace();
			return null;
		}

	}

}
