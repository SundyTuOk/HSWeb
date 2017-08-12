package com.sf.energy.transfer.communication;

import java.util.Timer;

import com.sf.energy.transfer.tftcp.PropertiesConfig;
import com.sf.energy.transfer.tftcp.TranClient;
import com.sf.energy.transfer.tftcp.TranServer;

/**
 * 启动服务器的主函数
 * 
 * @author ky
 * @version 1.0
 */
public class GeneralCommunication
{
	public static void main(String[] args)
	{
//		PropertiesConfig p = new PropertiesConfig();
//		// /获取中转站作为服务器监听的端口号
//		int tranServerPort = Integer.parseInt(p.readData("transerverport"));
//		// /获取中转站作为客户端的连结服务器的Ip和端口号
//		String tranClientIp = p.readData("tranclientip");
//		int tranClientPort = Integer.parseInt(p.readData("tranclientport"));
		// /开启中转站客户端
		TranClient tranClient = new TranClient("115.156.249.2", 8002);
		tranClient.start();
		// /开启中转站服务器
		TranServer tranServer = new TranServer(8001);
		tranServer.start();
		
		Timer timer = new Timer();
		TimerTick tick = new TimerTick();		
		timer.schedule(tick, 120000, 30000);
//		//整理日信息
//		TimeProcedureDay timeProcedure=new TimeProcedureDay();
//		timer.schedule(timeProcedure, 120000,60000);
//		//整理月信息
//		TimeProcedureMonth timeProcedureMonth=new TimeProcedureMonth();
//		timer.schedule(timeProcedureMonth, 120000,60000);
		
	}
}
