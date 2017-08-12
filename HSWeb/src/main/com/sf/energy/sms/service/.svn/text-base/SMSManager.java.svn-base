package com.sf.energy.sms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.AGateway.Protocols;
import org.smslib.Message.MessageEncodings;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;

import com.sf.energy.util.Configuration;


public class SMSManager
{
	private static final Logger logger = Logger.getLogger(SendMessage.class);
	private static Service smsService=null;
	private static SerialModemGateway gateway=null;
	
	

	/**
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws GatewayException 
	 * @throws TimeoutException  
	     *  
	     */
	public  SMSManager()
	{
		
	}

	/**
	 * 启动短信服务
	 */
	public void start()
	{
		smsService = Service.getInstance();
		List<AGateway> agatewayList = new ArrayList<AGateway>();

		
		XMLConfiguration config=null;
		try
		{
			config = Configuration.getConfiguration("configuration/ProjectConfiguration.xml");
		} catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
	
		String portName = config.getString("SMS.portName");
		int baudRate=Integer.parseInt(config.getString("SMS.baudRate"));
		String factory=config.getString("SMS.factory");
		String model=config.getString("SMS.model");
		String smsNumber=config.getString("SMS.smsNumber");
		String pin=config.getString("SMS.pin");
		
		//System.out.println("SMS portName:"+portName);
		
		if(gateway==null)
		{	
			gateway = new SerialModemGateway("modem." + portName, portName, baudRate, factory, model);
		}
		gateway.setSmscNumber(smsNumber);//短信服务中心号码
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setProtocol(Protocols.PDU);
		gateway.setSimPin(pin);
			
		
		agatewayList.add(gateway);
		try
		{
			for (AGateway gatewayTmp : agatewayList)
			{
				smsService.addGateway(gatewayTmp);
			}
		} catch (GatewayException ex)
		{
			logger.error(ex.getMessage());
		}
		logger.info("SMS服务正在启动.....");
		try
		{
			if(!isStarted() && gateway!=null)
			{
				smsService.startService();
			}
		} catch (Exception ex)
		{
			logger.error("SMS服务启动失败：", ex);
		}
		
	}

	/**
	 * 关闭短信服务
	 */
	public void destroy()
	{
		try
		{
			smsService.stopService();
			if(gateway!=null)
			{
				smsService.removeGateway(gateway);
				gateway.stopGateway();
			}
		} catch (Exception ex)
		{
			logger.error("SMS服务停止失败：", ex);
		}
		logger.info("SMS服务已停止！");
	}

	/**
	 * 发送短信
	 * 支持群发
	 * @param msg
	 * @return Boolean
	 */
	public boolean sendSMS(String phones,String content )
	{
		boolean isSuccess=false;
		String[] phonesArray = phones.split(" ");
		for (int i = 0; i < phonesArray.length; i++)
		{
			OutboundMessage msg = new OutboundMessage(phonesArray[i], content);
			try
			{
				msg.setEncoding(MessageEncodings.ENCUCS2);//中文
				if(smsService.sendMessage(msg))
				{
					isSuccess=true;
				}
				else {
					isSuccess=false;
					System.out.println("第"+(i+1)+"条短信发送失败！");
					break;
				}
			} catch (Exception e)
			{
				logger.error("第"+(i+1)+"条短信发送失败：", e);
			}
		}
		return isSuccess;
	}

	
	
	
	/**
	 * 判断短信服务是否已经启动
	 * @return
	 */
	private boolean isStarted()
	{
		if (smsService.getServiceStatus() == Service.ServiceStatus.STARTED)
		{
			for (AGateway gateway : smsService.getGateways())
			{
				if (gateway.getStatus() == AGateway.GatewayStatuses.STARTED)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 读取短信
	 * 
	 * @return List
	 */
	public List<InboundMessage> readSMS()
	{
		List<InboundMessage> msgList = new LinkedList<InboundMessage>();
		if (!isStarted())
		{
			return msgList;
		}
		try
		{
			smsService.readMessages(msgList,
					InboundMessage.MessageClasses.ALL);
			
			//smsService.deleteMessage(InboundMessage.MessageClasses.READ);
			
			logger.info("收到短信条数 " + msgList.size());
		} catch (Exception e)
		{
			logger.error("获取短信失败：", e);
		}
		return msgList;
	}
	
	/**
	 * 获取短信猫信息
	 * @param gateway
	 * @throws TimeoutException
	 * @throws GatewayException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void getInfoFromGateway(SerialModemGateway gateway) throws TimeoutException, GatewayException, IOException, InterruptedException
	{
		 System.out.println();
		 //打印设备信息
		 System.out.println("Modem Information:");
		 System.out.println("  Manufacturer: " + gateway.getManufacturer());
		 System.out.println("  Model: " + gateway.getModel());
		 System.out.println("  Serial No: " + gateway.getSerialNo());
		 System.out.println("  SIM IMSI: " + gateway.getImsi());
		 System.out.println("  Signal Level: " + gateway.getSignalLevel() +
		 " dBm");
		 System.out.println("  Battery Level: " + gateway.getBatteryLevel() +
		 "%");
		 System.out.println();
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws GatewayException 
	 * @throws TimeoutException 
	 */
	public static void main(String[] args) throws TimeoutException, GatewayException, IOException, InterruptedException
	{
		Logger.getRootLogger().setLevel(Level.INFO);
		SMSManager smsHandler = new SMSManager();
		//启动服务
		smsHandler.start();
		// 发送短信
		smsHandler.sendSMS("15527237558 13871153863", "测试！");
		// 读取短信
		List<InboundMessage> readList = smsHandler.readSMS();
		for (InboundMessage in : readList)
		{
			System.out.println("发送时间："+in.getDate().toLocaleString()+" 发信人：" + in.getOriginator() + " 短信内容:"
					+ in.getText());
			System.out.println(in.getId());
			System.out.println(in.getSmscNumber());//8613800270506
			System.out.println(in.getType());//INBOUND
			System.out.println(in.getDstPort());//-1
			System.out.println(in.getEncoding());//ENCUCS2
		}
		//关闭短信服务
		smsHandler.destroy();
		//System.out.println("*****************");
	}
}
