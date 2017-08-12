package com.sf.energy.water.manager.current.serviceImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.sf.energy.util.Configuration;
import com.sf.energy.water.manager.current.model.XMLCoder;
import com.sf.energy.water.manager.current.service.SendingXMLCode;

public class SendingHistoryCode implements SendingXMLCode
{

	/**
	 * 组装抄表XML命令发送到服务器，服务器返回获得的XML数据
	 * @param xmlCode 组装命令发送到服务器
	 * @return 服务器返回命令
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public String sendXMLToServer(XMLCoder xmlCoder) throws UnknownHostException, IOException
	{
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");	
		String tasknum = sdf.format(time);
	
		xmlCoder.setTaskNum(tasknum);
		xmlCoder.setMeterType(2);
		
		xmlCoder.setWay("2");
		xmlCoder.setPara("");
		
		
		String commondXML=null;
		
		
		String messageFromServer=null;
		
		Socket s =null;
		XMLConfiguration config=null;
		try
		{
			config = Configuration.getConfiguration("configuration/ProjectConfiguration.xml");
		} catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
		String serverIP=config.getString("server.IP");
		String serverPort=config.getString("server.port");
		
		s = new Socket(serverIP,Integer.parseInt(serverPort));
		
		OutputStream os = s.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		commondXML="<SFROOT gy='GW'><GW identify='1' opercode='" 
				+xmlCoder.getOperCode()
				+"' datasiteid='"+ xmlCoder.getDatasiteID()
				+"' ip='"+ s.getLocalSocketAddress().toString()
				+"' tasknum='"
				+ xmlCoder.getTaskNum()
				+ "' terminaladdress='"
				+ xmlCoder.getTerminalAddress()
				+ "' way='" 
				+xmlCoder.getWay()
				+"'><command metertype='"+xmlCoder.getMeterType()+"' code='" 
				+xmlCoder.getCommandCodeAFN()+xmlCoder.getCommandCodeDA()+xmlCoder.getCommandCodeDT()		
				+"' pw='" 
				+xmlCoder.getPw()
				+"' para='" 
				+xmlCoder.getPara()
				+"'></command></GW></SFROOT>\r\n";
		System.out.println(new Date().toLocaleString()+":向服务器发送日冻结的xml"+commondXML);
		dos.writeBytes(commondXML);
		dos.flush();
		
		
		InputStream is = s.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

        messageFromServer = br.readLine();
        dos.close();
        br.close();
        s.close();
        
        System.out.println("收到的回复是："+messageFromServer);
        
		return messageFromServer;
	}
	
}
