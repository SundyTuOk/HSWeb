package com.sf.energy.manager.current.serviceImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.sf.energy.manager.current.model.XMLCoder;
import com.sf.energy.manager.current.service.SendingXMLCode;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.util.Configuration;
import com.sf.energy.util.GetServerInfo;

/**
 * 实现即时抄表发送命令
 * @author yanhao
 *
 */
public class SendingImmediateCode implements SendingXMLCode
{
	/**
	 * 组装抄表XML命令发送到服务器，服务器返回获得的XML数据
	 * @param xmlCode 组装命令发送到服务器
	 * @return 服务器返回命令
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String sendXMLToServer(XMLCoder xmlCoder) throws ConnectException, UnknownHostException, IOException , SQLException
	{
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");	
		String tasknum = sdf.format(time);
		
		xmlCoder.setTaskNum(tasknum);
		xmlCoder.setMeterType(1);
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
		
		////System.out.println("serverIP: "+serverIP+" serverPort: "+serverPort);
		
		s = new Socket(serverIP,Integer.parseInt(serverPort));
		s.setSoTimeout(GetServerInfo.getServerOverTime());//超时
		
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
		System.out.println(new Date().toLocaleString()+":向服务器发送即抄的xml"+commondXML);
		dos.writeBytes(commondXML);
		dos.flush();
		
		
		InputStream is = s.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

        
        try
		{
        	messageFromServer = br.readLine();
		} catch (SocketTimeoutException e)
		{
			extracted();
		}
        
        dos.close();
        br.close();
        s.close();
        
        if(messageFromServer!=null)
        	return messageFromServer;
        else 
        {
			return null;
		}
	}

	private void extracted() throws SocketTimeoutException
	{
		throw new SocketTimeoutException();
	}
}
