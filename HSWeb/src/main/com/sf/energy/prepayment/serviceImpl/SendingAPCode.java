package com.sf.energy.prepayment.serviceImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.sf.energy.prepayment.model.XMLCoder;
import com.sf.energy.prepayment.service.SendingXMLCode;
import com.sf.energy.util.Configuration;
import com.sf.energy.util.GetServerInfo;

public class SendingAPCode implements SendingXMLCode
{
	@Override
	public String sendXMLToServer(XMLCoder xmlCoder)
			throws UnknownHostException, IOException
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
		try {
			
			s.setSoTimeout(GetServerInfo.getServerOverTime());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//超时
		OutputStream os = s.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		
		////////////////////////
		commondXML="<SFROOT gy='GW'><GW identify='1' opercode='" +xmlCoder.getOperCode()
				+"' datasiteid='"+ xmlCoder.getDatasiteID()
				+"' ip='"+ s.getLocalSocketAddress().toString()
				+"' tasknum='"+ xmlCoder.getTaskNum()
				+ "' terminaladdress='"+ xmlCoder.getTerminalAddress()
				+ "' way='" +xmlCoder.getWay()
				+"'><command code='" +xmlCoder.getCode()	
				+"' metertype='" +xmlCoder.getMeterType()
				+"' pw='" +xmlCoder.getPw()
				+"' para='" +xmlCoder.getPara()
				+"'></command></GW></SFROOT>\r\n";		
		System.out.println(new Date().toLocaleString()+":向服务器发送的xml"+commondXML);
		dos.writeBytes(commondXML);
		dos.flush();
		
		
		InputStream is = s.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		 try { 
        messageFromServer = br.readLine();
		 }catch(SocketTimeoutException e){
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
