package com.sf.energy.manager.current.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

import com.sf.energy.manager.current.model.XMLCoder;



/**
 * 【废弃的类】
 * @author yanhao
 *
 */
@Deprecated
public class CommToServer implements Callable<String>
{
	private Socket socket;
	private DataInputStream din;
	private  DataOutputStream dout;
	String commondXML;
	
	
	
	
	public CommToServer (String addr,int port,String clientName ,XMLCoder xmlCoder) throws IOException
	{
		
		socket=new Socket(addr,port);
		din=new DataInputStream(socket.getInputStream());
		dout=new DataOutputStream(socket.getOutputStream());
		
		
		xmlCoder.setOperCode("1");
		xmlCoder.setWay("2");
		xmlCoder.setPw("00000000000000000000000000000000");
		xmlCoder.setPara("");
		
		
		commondXML="<SFROOT gy='GW'><GW opercode='" 
				+xmlCoder.getOperCode()
				+"' tasknum='"
				+ xmlCoder.getTaskNum()
				+ "' terminaladdress='"
				+ xmlCoder.getTerminalAddress()
				+ "' way='" 
				+xmlCoder.getWay()
				+"'><command code='" 
				+xmlCoder.getCommandCodeAFN()+xmlCoder.getCommandCodeDA()+xmlCoder.getCommandCodeDT()		
				+"' pw='" 
				+xmlCoder.getPw()
				+"' para='" 
				+xmlCoder.getPara()
				+"'></command></GW></SFROOT>\r\n";
		
	}

	@Override
	public String call() throws Exception
	{
		String returnMessage;
		////System.out.println("线程连接成功");
		dout.writeBytes(commondXML);
		////System.out.println("向服务器发送的xml成功："+commondXML);
		dout.flush();
		
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		returnMessage = br.readLine();
		
		////System.out.println("服务器发送回来的xml："+returnMessage);
		din.close();
		br.close();
		dout.close();
		socket.close();
		return returnMessage;
	}
}
