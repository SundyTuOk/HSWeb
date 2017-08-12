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
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import com.sf.energy.manager.current.model.XMLCoder;
import com.sf.energy.manager.current.service.SendingXMLCode;
import com.sf.energy.util.Configuration;
import com.sf.energy.util.GetServerInfo;

public class CleanGather implements SendingXMLCode
{

	@Override
	public String sendXMLToServer(XMLCoder xmlCoder) throws  ConnectException,
			UnknownHostException, IOException,
			SQLException
	{
		
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
		s.setSoTimeout(GetServerInfo.getServerOverTime());
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
				+"'><command code='" 
				+xmlCoder.getCode()	
				+"' pw='" 
				+xmlCoder.getPw()
				+"' para='" 
				+xmlCoder.getPara()
				+"'></command></GW></SFROOT>\r\n";
		
//		System.out.println(new Date().toLocaleString()+":向服务器发送清空网关的xml"+commondXML);
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
        
		return messageFromServer;
	}
	private void extracted() throws SocketTimeoutException
	{
		throw new SocketTimeoutException();
	}

}
