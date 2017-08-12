package com.sf.energy.light.serviceImpl;

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
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import com.sf.energy.light.model.XMLCoder;
import com.sf.energy.light.service.SendingXMLCode;
import com.sf.energy.util.Configuration;


public class SendingLampOnOffXml implements SendingXMLCode
{
    private Socket s=null;
	public boolean creatCon() throws UnknownHostException, IOException
	{
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
		try {
			 s = new Socket(serverIP,Integer.parseInt(serverPort));
			} catch (ConnectException e) {
				// TODO: handle exception
			
				return false;
			}
		return true;
	}
	
	public String sendXMLToServer(XMLCoder xmlCoder) throws UnknownHostException, IOException
	{
		String commondXML=null;
		

		String messageFromServer=null;
		
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
		 s.setKeepAlive(true);  
         s.setSoTimeout(20 * 1000); 
        
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
		dos.writeBytes(commondXML);
		dos.flush();
		System.out.println(commondXML);
		 
			 
		
		InputStream is = s.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		 try { 
        messageFromServer = br.readLine();
		 }catch(SocketTimeoutException e){  
           return "TimeOut"; 
         }
        dos.close();
        br.close();
        s.close();
        
        if(messageFromServer!=null)
        	return messageFromServer;
        else {
			return null;
		}
		
	}
	

}
