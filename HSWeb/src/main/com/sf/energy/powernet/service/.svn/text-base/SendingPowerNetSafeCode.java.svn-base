package com.sf.energy.powernet.service;

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
import com.sf.energy.powernet.dao.AmMeterPDDataDao;
import com.sf.energy.project.PDRoom.model.PD_PartInfo03Model;
import com.sf.energy.project.equipment.dao.GatherDao;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.util.Configuration;
import com.sf.energy.util.GetServerInfo;

/**
 * 配网安全发送给服务器
 * @author yanhao
 *
 */
public class SendingPowerNetSafeCode 
{
	/**
	 * 组装抄表XML命令发送到服务器，服务器返回获得的XML数据
	 * @param xmlCode 组装命令发送到服务器
	 * @return 服务器返回命令
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String sendXMLToServer(XMLCoder xmlCoder,PD_PartInfo03Model hlModel) throws ConnectException, UnknownHostException, IOException ,SocketTimeoutException, SQLException
	{
		GatherDao gd=new GatherDao();
		AmMeterPDDataDao apd=new AmMeterPDDataDao();
		Gather gm=new Gather();
		int ammID=apd.getAmmeterIDByPdPartID(hlModel.getPart_ID());
		gm=gd.getGatherXMLInfo(ammID);
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");	
		String tasknum = sdf.format(time);
	
		xmlCoder.setTaskNum(tasknum);
		xmlCoder.setMeterType(1);
		xmlCoder.setProtocol(gm.getProtocol());
		xmlCoder.setWay(gm.getSendway()+"");
		xmlCoder.setPw(gm.getGatherPw());
		xmlCoder.setTerminalAddress(gm.getGatherAddress());//网关地址
		
		//下面组para节点
		String para="";
		para+=gm.getAmm485Address()+",";//电表485地址
		
		para+=hlModel.getZEDGL()+",";
		para+=hlModel.getAEDGL()+",";
		para+=hlModel.getBEDGL()+",";
		para+=hlModel.getCEDGL()+",";
		
		para+=hlModel.getZXEWG()+",";
		para+=hlModel.getAXEWG()+",";
		para+=hlModel.getBXEWG()+",";
		para+=hlModel.getCXEWG()+",";
		
		para+=hlModel.getADYSX()+",";
		para+=hlModel.getADYXX()+",";
		para+=hlModel.getBDYSX()+",";
		para+=hlModel.getBDYXX()+",";
		para+=hlModel.getCDYSX()+",";
		para+=hlModel.getCDYXX()+",";
		
		para+=hlModel.getAXEDL()+",";
		para+=hlModel.getBXEDL()+",";
		para+=hlModel.getCXEDL();
		
		para+=",0.00";//最后加一个为了中转站得到19个参数，原系统也是这样做的
		
		xmlCoder.setPara(para);
		
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
		commondXML="<SFROOT gy='"+xmlCoder.getProtocol()+"'><"+xmlCoder.getProtocol()+" identify='1' opercode='" 
				+xmlCoder.getOperCode()
				+"' datasiteid='"+ gm.getDatasiteID()
				+"' ip='"+ s.getLocalSocketAddress().toString()
				+"' tasknum='"
				+ xmlCoder.getTaskNum()
				+ "' terminaladdress='"
				+ xmlCoder.getTerminalAddress()
				+ "' way='" 
				+xmlCoder.getWay()
				+"'><command metertype='"+xmlCoder.getMeterType()+"' code='setalarm'" 
					
				+" pw='" 
				+xmlCoder.getPw()
				+"' para='" 
				+xmlCoder.getPara()
				+"'></command></"+xmlCoder.getProtocol()+"></SFROOT>\r\n";
		System.out.println(new Date().toLocaleString()+":向服务器发送配网的xml"+commondXML);
		dos.writeBytes(commondXML);
		dos.flush();
		
		
		InputStream is = s.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

        messageFromServer = br.readLine();
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
}
