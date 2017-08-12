package com.sf.energy.server.communication;

import java.util.Timer;

import com.sf.energy.server.db.TimerToTask;
import com.sf.energy.server.tftcp.PropertiesConfig;
import com.sf.energy.server.tftcp.Push;
import com.sf.energy.server.tftcp.Server;
import com.sf.energy.server.tftcp.Web;

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
		// /开启服务器
		Server server = new Server(8002);
		server.start();
		Web web=new Web(8003);
		web.start();
		Push push=new Push(8004);
		push.start();
		//加载定时任务
		new TimerToTask();	
	}
}
