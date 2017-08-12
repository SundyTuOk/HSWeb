package com.sf.energy.transfer.communication;

import java.awt.dnd.Autoscroll;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.crypto.Data;

import org.apache.mina.core.session.IoSession;

import com.sf.energy.transfer.db.SenderWithOracle;
import com.sf.energy.transfer.tcp.Session;
import com.sf.energy.transfer.tcp.TranGateSession;
import com.sf.energy.transfer.tftcp.TranClient;
import com.sf.energy.transfer.tranReciveMessage.AutoData;
import com.sf.energy.transfer.tranReciveMessage.AutoUp;
import com.sf.energy.transfer.tranReciveMessage.MissDate;
import com.sf.energy.transfer.tranReciveMessage.SendCommand;
import com.sf.energy.transfer.tranReciveMessage.TranGataData;
import com.sf.energy.transfer.tranReciveMessage.autoReadDate;

/**
 * 定时发送命令
 * @author ky
 * @version 1.0
 *
 */
public class TimerTick extends TimerTask
{   
	 /// 与发送有关的数据库操作
    private SenderWithOracle sd = new SenderWithOracle();
	// 线程池
    private ExecutorService executorService; 
		// 单个CPU时线程池中的工作线程个数
	private final int POOL_SIZE = 4; 
	 
    /// 是否主动上传用量
    private boolean ifAutoUp = true;
    
    /// 上次主动上传时间   
    private Date lastautouptime = new Date(100, 1, 1);

    
    /// 上次主动上传开关   
    private boolean lastautoup = true;

    
    /// 是否自动抄读电表
    private boolean ifAutoReadMeter = true;

   
    /// 上次主动抄表开关
    private boolean lastautoreadmeter = false;

  
    /// 是否自动抄读路灯
    private boolean ifAutoReadLamp = true;

   
    /// 上次主动抄路灯开关  
    private boolean lastautoreadlamp = true;

   
    /// 是否自动抄读Lon
    private boolean ifAutoReadLon = true;

    
    /// 上次主动抄lonworks开关
    private boolean lastautoreadlon = true;

   
    /// 是否自动抄读教室照明控制器
    private boolean ifAutoReadClassroom = true;

    
    /// 上次主动抄教室照明开关
    private boolean lastautoreadclassroom = true;

    
    /// 是否保持与服务器的连接
    private boolean keepconnect = true;

    /// 采集电表频率
    private long readfrequencyam = 20*60*1000;

    /// 采集水表频率
    private double readfrequencywa = 1440*60*1000;

    /// 采集路灯频率
    private double readfrequencylamp = 5*60*1000;

    /// 采集Lonworks电表频率
    private double readfrequencylon = 1*60*1000;

    /// 采集教室照明控制器频率
    private double readfrequencyclassroom = 5*60*1000;

    /// 上传频率
    private long upfrequency = 60*60*1000;

   
    /// 命令超时时间
    private double commanddelaytime = 30;
    
    AutoUp autoUp=new AutoUp();
    
    /// 终端通讯超时时间，默认10分钟
    private int delaytime = 10*60*1000;
    boolean mytimertickrun = false;
    /**
     * 1.用来检查是否有长时间未通讯的电表,如果有,则从sessiontable中踢掉
     * 2.用来检查是否到了新的小时,如果是,则启动本小时重新自动抄表
     * @throws IOException
     */
     public void MyTimerTick() throws IOException
     {
    	 
    	 Session session=new Session();
    	 TranGateSession tranGateSession=TranGateSession.getTranGateSession();
         HashMap<String, Session> hashMap=tranGateSession.getHashMap();
         Set<Map.Entry<String,Session>> set=hashMap.entrySet();
		 Iterator<Map.Entry<String,Session>> iter=set.iterator();
		 Date date=new Date();
         if (mytimertickrun)
         {
             return;
         }
         //mytimertickrun = true;
//         try
//         {         
//             if ((ifAutoReadMeter) || (ifAutoReadLamp) || (ifAutoReadClassroom)) 
//             {
//            	while(iter.hasNext()){
//     			session=iter.next().getValue();
//     			if(session.isAutoReadOver()){
//     				 if (ifAutoReadMeter)
//                     {      
//                         if ((date.getTime()-session.getThisAmReadTime().getTime())>=readfrequencyam) 
//                         {
//                        	 
//                        	 session.setAutoReadOver(false);
//                             session.setThisAmReadTime(date);                 
//                             sd.readFlgClear("1");
//                             ///定时抄读,上传     
//                             executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()* POOL_SIZE);
//                     		 executorService.execute(new autoReadDate(session, "1"));                                                  
//                             continue; 
//                         }
//                     }
//     			}
//     		}
//          }
//         }
//         catch (Exception e) {
//			// TODO: handle exception
//        	 e.printStackTrace();
//		}

         if (ifAutoUp) 
         {
             if ((date.getTime()-lastautouptime.getTime())>=upfrequency)
             {
            	 //System.out.println("dd");
                 lastautouptime.setTime(date.getTime());
                 //System.out.println(date.getTime()-lastautouptime.getTime());
                 AutoUp();
             }
         }
       //发送在线终端到服务器
         //System.out.println("start to send server");
        
         autoUp.TerminalOnline("gy", "-1", "000000000000000", "terminalstate");    
         //中谷不需要此功能
         //此功能必须有，因为如果不再有新的连接过来的话，tcpsocket类库中的超时踢掉功能就不再起作用  
        while(iter.hasNext()) 
         {
        	session=iter.next().getValue();
        	long temp=session.getDtgateconnecttime().getTime()+delaytime;
             if (temp<date.getTime())
             {
            	 //System.out.println("T抄表");
                tranGateSession.closeSession(session);
                // string ifcheckin = rd.CheckTerminalInfo(cli.Dtgateaddress);
               //  string info = string.Format(DateTime.Now.TimeOfDay.ToString().Substring(0, 8) + " �رճ�ʱδͨѶ�ն�{0}" + ifcheckin + "������!\r\n\r\n", cli.Dtgateaddress);
                // SetTextFunction(info, 1);
                 break;
             }
         }
        //读缺失数据超时1分钟
         while(iter.hasNext()){
  			session=iter.next().getValue();
             if (!session.isNoMissData())
             {
                 if ((date.getTime()-session.getLastReadMissDate().getTime())>commanddelaytime)
                 {
                	 //System.out.println("不抄");
                	 executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()* POOL_SIZE);
             		 executorService.execute(new MissDate(session));
                     
                 }
             }
         }
     }
    
     /// 主动上传日电量与小时电量
   private  boolean autouprun = false;
     public void AutoUp()
     {
         if (autouprun )
         {
             return;
         }
         if (new TranClient().getSession().getIoSession().isConnected())
         {
        	 //System.out.println("上传日电量");
        	 executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()* POOL_SIZE);
     		 executorService.execute(new AutoData());
             
         }
     }
     
     
     
     
     
     
     
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			MyTimerTick();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
     
    
}
                
            

           
         

