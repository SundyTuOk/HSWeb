package com.sf.energy.transfer.tranReciveMessage;

import java.sql.SQLException;

import org.apache.mina.core.buffer.IoBuffer;

import com.sf.energy.transfer.db.SenderWithOracle;
import com.sf.energy.transfer.encode.SendGW;
import com.sf.energy.transfer.tcp.Session;
import com.sf.energy.transfer.tcp.TranGateSession;
import com.sf.energy.util.CreateLog;

public class autoReadDate implements Runnable
{
	//命令超时时间
	private double commanddelaytime = 30;
	private TranGateSession tranGateSession=TranGateSession.getTranGateSession();
	//是否自动抄表
	private boolean ifAutoReadMeter = false;
	private int pseq = 0;
	private Session client;
	private String meterType;
	public autoReadDate(Session client, String meterType){
		this.client=client;
		this.meterType=meterType;
	}
	
	/**
	 * 按表类型依次抄读本轮电量线程
	 * @param client session对象
	 * @param meterType 表计类型
	 */
	private void autoReadDate()
	{
		SenderWithOracle so = new SenderWithOracle();
		String terminalAddress = client.getDtgateaddress();
		try
		{
			String meters = so.allMeter(terminalAddress, meterType);
			if(meters.equals("") || meters == null)
			{
				client.setAutoReadOver(true);
				return;
			}
			SendGW ss = new SendGW();
			
			for (int m = 0; m < 3; m++)
			{
				if(tranGateSession.getHashMap().containsValue(client) && ifAutoReadMeter)
				{
					//本数据网关下所有未抄到本轮电量的表计
					String meterstoread = so.metersToRead(terminalAddress, meterType); 
					//如果本数据网关下所有表计的本轮数据都已抄回,则跳出循环,线程结束
					if (meterstoread.equals("") || meterstoread == null) 
                    {
                    	client.setAutoReadOver(true);
                        break;
                    }
					
					String[] meterarray = meterstoread.split(",");
					for (int i = 0; i < meterarray.length - 1; i++)
					{
						if (!tranGateSession.getHashMap().containsValue(client) || !ifAutoReadMeter) //如果需要停止自动抄读,此处及时跳出循环
                        {
                            break;
                        }
						//超时计数
						int j = 0; 
						while ((!client.getLastCommand().equals("") && client.getLastCommand() != null) && (j++ < commanddelaytime * 10))
                        {
                            Thread.sleep(100);
                        }
						
						String[] mm = meterarray[i].split("\\|");
						client.setMeterAddress(mm[0]);
						int meterID = Integer.parseInt(mm[1]);
						String commandCode = ss.makeCommandCode(meterID);
						pseq++;
			            if (pseq == 15)
			            {
			                pseq = 0;
			            }
						client.setLastCommand(commandCode);
						client.setCommandKind(1);
						client.setPseq(pseq);
						byte[] sendBuffer = ss.sendCommand(terminalAddress, commandCode, "", pseq);
						
						boolean sendsuc = sendHex(client, sendBuffer);
						//如果发送不成功，则跳出循环
						if (!sendsuc)
                        {
                            break;
                        }
						
						//窗体显示
						//SetHexFunction(client.getIoSession().getRemoteAddress().toString(), terminalAddress, mm[0], ss.HexBufferToStr(sendbuffer), 1);
						
					}
					//超时计数
					int k = 0;
					while ((!client.getLastCommand().equals("") && client.getLastCommand() != null) && (k++ < commanddelaytime * 10)) 
					{
						Thread.sleep(100);
					}
					if (k >= commanddelaytime * 10)
                    {
						client.setLastCommand("");
                    }
				}
			}
			client.setAutoReadOver(true);
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送报文
	 * @param recvDataClient Session对象
	 * @param rvcbuffer 字节数组
	 * @return 返回发送成功与否标识
	 */
	public boolean sendHex(Session recvDataClient, byte[] rvcbuffer)
	{
		if ((recvDataClient.getIoSession() != null)
				&& (recvDataClient.getIoSession().isConnected()))
		{
			try
			{
				recvDataClient.getIoSession().write(IoBuffer.wrap(rvcbuffer));

				return true;
			} catch (Exception e)
			{
				CreateLog.writeLog("TcpServer.SendHex:" + e.getMessage());
				return false;
			}
		}
		return false;

	}

	@Override
	public void run()
	{
		autoReadDate();
		// TODO Auto-generated method stub
		
	}
}
	

