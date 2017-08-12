package com.sf.energy.transfer.tranReciveMessage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.sf.energy.transfer.decode.ReceiveGW;
import com.sf.energy.transfer.decode.Receiver;
import com.sf.energy.transfer.tcp.Session;
import com.sf.energy.transfer.tcp.TranGateSession;
import com.sf.energy.transfer.tftcp.TranClient;
import com.sf.energy.transfer.tftcp.TranServer;

public class TranGataData
{
	private static Logger logger = Logger.getLogger(TranGataData.class);
	AutoUp autoUp=new AutoUp();
	Session session = new Session();
	TranGateSession tranGateSession = TranGateSession.getTranGateSession();
	TranClient tranClient=new TranClient();
	Alarm alarm=new Alarm();
	private HashMap<String, Session> hashMap;
	// /报文起始位置
	private int startindex = 0;
	// /存放发送的报文
	byte[] sendbuffer = new byte[1024];
	// /存放网关上传的数据
	private byte[] recvData;
	// /网关的信息
	private IoSession ioSession;
	
	
	public TranGataData(byte[] recvData, IoSession session)
	{
		URL	url=ClassLoader.getSystemResource("log4j.properties");
		PropertyConfigurator.configure(url);
		this.recvData = recvData;
		this.ioSession = session;
	}
	/**
	 * 接收到数据网关上传数据的处理
	 * @throws Exception 
	 */
	public void sRecvData() throws Exception
	{
		Receiver rr = new ReceiveGW();
		hashMap = tranGateSession.getHashMap();
		Set<Map.Entry<String, Session>> iter = hashMap.entrySet();
		Iterator<Map.Entry<String, Session>> it = iter.iterator();
		while (it.hasNext())
		{
			session = it.next().getValue();
			if (session.getIoSession() == ioSession)
			{
				break;
			}
		}
		/// 将收到的报文转为16进制的字节数组
		 while (((recvData[startindex]) == 0xFE) && (startindex <
		 recvData.length))
		 {
		 startindex++;
		 }
		if ((session.getGy() == null) || ("".equals(session.getGy())))
		{
			
			String gw=ParseGY(recvData, startindex);			
			session.setGy(gw);
			//System.out.println("收到的信息为"+session.getGy());
			if ((session.getGy() == null) || ("".equals(session.getGy())))
			{
				return;
			}
		}
		///赋值解析规格
		rr.setProtocol(session.getGy());
		rr.setStartIndex(startindex);
		rr.receiveBufferAssign(recvData);
		rr.terminalAddressAssign();
		if ((session.getDtgateaddress() == null)
				|| ("".equals(session.getDtgateaddress())))
		{
			session.setDtgateaddress(rr.getTerminalAddress());
		}
		String ipaddress = session.getIoSession().getRemoteAddress().toString();
		// /接收缓冲区16进制数组转为字符串 ,2表示接收数组
		String strframe = rr.hexBufferToStr(recvData, 2);
		// /解析是否是主动上报的报文，包括心跳，报警等 命令的种类（1是轮炒，2WEB命令，3主动上传）
		String info = rr.parseAutoUp(session.getCommandKind());
		if (!info.isEmpty())
		{
			//textArea.setText(info);
			logger.info("报文解析为：" + info);
			 try
			{
				autoUp.DGAutoUp(ipaddress, strframe, rr, info, session);
				//return;
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// /对主动上报报文的回复
		sendbuffer = rr.ack();
		//System.out.println(sendbuffer);
		if ((sendbuffer != null) || ("".equals(sendbuffer)))
		{
			sendAck(sendbuffer, ioSession);
//			byte[] a={0x68,0x32,0x00,0x32,0x00,0x68,0x4b,0x27,0x00,(byte) 0xbb,0x00,0x00,0x0c,0x60,0x08,0x01,0x01,0x10,(byte) 0xb3,0x16};
//			//System.out.println("send");
//			ioSession.write(IoBuffer.wrap(a));
			return;

		}
		
		 //判断是否要回复 web客户端	
//		  boolean ifright = CheckCommandCode(rr.getReceiveBuffer(),startindex,session.getLastCommand()); 
//		  if (!ifright) 
//		  { 
//			  //System.out.println(session.getLastCommand()+ifright);
//			  return; 
//		  }
		 
		///解析发送给服务器的报文
		String parseValue = rr.commandBack(session.getOperCode(), session.getTasknum(),
				session.getLastCommand(), session.getLastPara());
		logger.info("上传服务器报文解析为：" + parseValue);
		//textArea.setText(parseValue);
	//	//System.out.println(session.getLastCommand()+"  "+session.getLastPara()+"  "+session.getPseq()+"  "+session.getCommandKind()+"  "+parseValue);
	//	boolean ifrightcommand =rr.parseSpacialCommand(session.getLastCommand(),session.getLastPara(),session.getPseq(), session.getCommandKind(),parseValue);
	//	 //System.out.println("Commandkind"+session.getCommandKind());
		if (session.getCommandKind()== 2) //如果普通命令的回复,就返给服务器 
		{ 
			if(!((parseValue==null)||("".equals(parseValue)))) 
			{
		    tranClient.sendServer(parseValue); //发送给服务器 
		    } 
		  if(rr.clearLastCommand(session.getLastCommand())) 
		  { 
			  session.setLastCommand("");
			  //ifAutoReadMeter = lastautoreadmeter;
			  //ifAutoReadLamp = lastautoreadlamp;
		  }
		} 
	    else 
		  { 
	    	//轮抄时本回复属于上一命令 
//	    	if (ifrightcommand)		  
//		    { 
//	    		session.setLastCommand("");
//	    	 } 
		    alarm.DGAlarm(session,parseValue); //报警 
		    } 		 		
	}
	
	/**
	 * 根据报文格式区分主动上传的报文是国网规约还是多功能表规约
	 * 
	 * @param receivebuffer
	 *            网关上传的报文
	 * @param startindex
	 *            报文起始位置
	 * @return
	 */
	private static String ParseGY(byte[] receivebuffer, int startindex)
	{
		String flag = "", gy = "";
		if (receivebuffer[3] == 0x68) // 湖北规约
		{
			flag += "1";
		} else
		{
			flag += "0";
		}
		if (receivebuffer[5] == 0x68) // 国网规约
		{
			flag += "1";
		} else
		{
			flag += "0";
		}
		if (receivebuffer[startindex + 7] == 0x68) // 多功能表规约
		{
			flag += "1";
		} else
		{
			flag += "0";
		}
		switch (flag)
		{
			case "000":
				break; // 不满足任何规约
			case "001":
				gy = "DGN645";
				break; // 多功能表
			case "010":
				gy = "GW";
				break; // 国网
			case "011": // 国网规约报文与多功能表报文没办法绝对分清楚,可能再加上校验码核对,但是还是难以分清
				int endindexgw = (receivebuffer[2] * 256 + receivebuffer[1] - 2) / 4 + 7;
				if (receivebuffer[endindexgw] == 0x16) // 考虑到多功能表规约的主动上传只有自定义的心跳,帧长度为12,小于国网规约的最小帧长度20,所以可以这样判断
				{
					gy = "GW";
				} else
				{
					gy = "DGN645";
				}
				break;
			case "100":
				gy = "HB";
				break; // 湖北
			case "101":
				int endindexhb = receivebuffer[2] * 256 + receivebuffer[1] + 6;
				if (receivebuffer[endindexhb] == 0x16) // 湖北规约的最短帧长于多功能表规约的最长帧,所以按湖北帧长计算,如果满足湖北的结束符,则不可能满足多功能表规约,只能是湖北规约
				{
					gy = "HB";
				} else
				// 如果不满足湖北规约,则只能是多功能表规约
				{
					gy = "DGN645";
				}
				break;
			case "110":
				break; // 国网规约的第三个字节不可能为68
			case "111":
				break; // 国网规约的第三个字节不可能为68
			default:
				break;
		}
		return gy;
	}
	
	/**
	 * 检查是否此命令回复
	 * @param ReceiveBuffer 数据网关发送的字节数组
	 * @param startindex 起始位置
	 * @param LastCommand 上一条命令代码
	 * @return
	 */
	private boolean CheckCommandCode(byte[] ReceiveBuffer, int startindex,
			String LastCommand)
	{
		//System.out.println(startindex);
		boolean ifright = true;
		String afn = hexToString(ReceiveBuffer[12 + startindex]);
		//System.out.println("输出"+afn);
		String receivecommand = afn
				+ hexToString(ReceiveBuffer[14 + startindex])
				+ hexToString(ReceiveBuffer[15 + startindex])
				+ hexToString(ReceiveBuffer[16 + startindex])
				+ hexToString(ReceiveBuffer[17 + startindex]); // 收到的命令代码
		if (((LastCommand == null) || ("".equals(LastCommand)))
				|| (LastCommand.length() < 10))// 命令代码不对则直接回复
		{
			ifright = false;
			
		} else
		{
			if (afn.equals("00"))
			{
				ifright = true;
			} else
			{
				String lastcode = LastCommand.substring(0, 10);
				//System.out.println("lastcode"+lastcode);
				//System.out.println("receivecommand"+receivecommand);
				if (lastcode.equals(receivecommand.toUpperCase()))
				{
					//System.out.println("find");
					ifright = true;
				} else
				{
					ifright = false;
				}
			}
		}
		return ifright;
	}
	
	/**
	 * 讲一个字节转换成对应的十六进制字符串
	 * 
	 * @param b
	 *            一个字节
	 * @return 字节对应的十六进制字符串
	 */
	public static String hexToString(byte b)
	{
		String sl = Integer.toHexString(b & 0x0f);
		String sh = Integer.toHexString((b >> 4) & 0x0f);
		String s = sh + sl;
		return s;
		
	}
	
	public static void sendAck(byte[] sendData, IoSession ioSession)
	{
		// int len = ((sendData[2] & 0xff) * 256+ (sendData[1] & 0xff) - 2) / 4
		// + 8;
		// byte[] recvData=Arrays.copyOf(sendData, len);
		ioSession.write(IoBuffer.wrap(sendData));
	}
}
