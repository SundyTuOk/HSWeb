package com.sf.energy.transfer.tranReciveMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sf.energy.transfer.db.SenderWithOracle;
import com.sf.energy.transfer.decode.ReceiveGW;
import com.sf.energy.transfer.decode.Receiver;
import com.sf.energy.transfer.encode.SendGW;
import com.sf.energy.transfer.tcp.Session;
import com.sf.energy.transfer.tcp.TranGateSession;
import com.sf.energy.transfer.tftcp.TranClient;

public class AutoUp {
	
    /// 与发送有关的数据库操作
    private SenderWithOracle sd = new SenderWithOracle();
	TranGateSession tranGateSession=TranGateSession.getTranGateSession();
	HashMap<String,Session> hashMap=tranGateSession.getHashMap();
	Session session=new Session();
    /// 是否自动抄读电表   
    private boolean isAutoReadMeter = false;
	/// 线程池
	private ExecutorService executorService; 
	/// 单个CPU时线程池中的工作线程个数
	private final int POOL_SIZE = 4; 
	boolean terminalonlinerun = false;
	TranClient tranClient=new TranClient();
	
    /// 主动上报处理
    /// </summary>
    public void DGAutoUp(String ipaddress, String strframe, Receiver rr, String info, Session session) throws Exception
    {
        String parsevalue="";
        //SetHexFunction(ipaddress, rr.TerminalAddress, "", strframe, 2); //主动上报无表地址
        String[] infostr = info.split("\\|");
        if (infostr.length == 3)
        {
            switch (infostr[0])
            {
                case "02"://链路接口检测
                    //SetTextFunction(infostr[2], 1);
                    switch (infostr[1])
                    {
                        case "0100"://登陆
                        	//System.out.println("发送xml到服务器");
                           TerminalOnline("gy", "-1", "000000000000000", "terminalstate");
                            break;
                        case "0200"://退出登陆(目前还没有遇到退出登陆的情况)
                            break;
                        case "0400"://心跳
                            break;
                        default: break;
                    }
                    break;
                case "0C": //数据网关主动上传数据
                case "0c":
//                    if (session.isNoMissData()) //如果一周内没有缺失数据才接收网关主动上传的数据
//                    {
                        parsevalue = infostr[2];
                        rr.CVSave(parsevalue, infostr[0] + "ffff" + infostr[1], 0);
//                        rr.parseSpacialCommand(infostr[0] + "FFFF" + infostr[1], session.getLastPara(), session.getPseq(), 1, parsevalue);
//                    }
                    break;
//                case "0D":
//                case "0d"://抄读缺失数据时控制域还是主动上报
//                	session.setLastReadMissDate(new Date());
//                    parsevalue = infostr[2];
//                    rr.parseSpacialCommand(infostr[0] + "FFFF" + infostr[1],session.getLastPara(), session.getPseq(), 1,  parsevalue);                   
//                    break;
//                case "0E":
//                case "0e"://故障报警不在中转站显示,直接上传到服务器,暂时不上报
//                    break;
                    default:
                    	break;
            }
        }
        byte[] sendbuffer = rr.ack();
        int len=sendbuffer.length;
        if (len > 0)
        {
        	boolean sendsuc=tranGateSession.sendHex(session, sendbuffer);
            if (sendsuc)
            {
            	//System.out.println("send 网关 ok");
                //strframe = rr.hexBufferToStr(sendbuffer, 1);
                //SetHexFunction(ipaddress, rr.TerminalAddress, "", strframe, 1); //主动上报的回复无表地址
            }
            if (!sendsuc)
            {
                return;
            }
        }
        
//
//        if ((!isAutoReadMeter) && (infostr.length == 3) && (infostr[0] == "02") && (infostr[1] == "0100")) //如果是主动上传模式且是登陆帧,要检查是否要抄缺失的历史数据
//        {
//        	// 创建线程池
//    	
//		    executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()* POOL_SIZE);
//		   executorService.execute(new MissDate(session));;
//        }
//        else if (session.getLastCommand()== "0DFFFF0264")
//        {
//            if (rr.clearLastCommand(session.getLastCommand())) 
//            {
//                session.setLastCommand("");
//             // 创建线程池            	
//        		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()* POOL_SIZE);
//        		executorService.execute(new MissDate(session));; //抛入线程池异步执行
//            }
//         }                      
    }
   /**
    * 获取在线网关及在线表计,并组成XML,每个网关发一次
    * @param gy 国王协议
    * @param opercode 操作员代码
    * @param tasknum  任务编号
    * @param commandcode 命令编号
    */
    public void TerminalOnline(String gy, String opercode, String tasknum, String commandcode)
    {
    	Set<Map.Entry<String,Session>> iter=hashMap.entrySet();
    	Iterator<Map.Entry<String,Session>> it=iter.iterator();
        if (terminalonlinerun)
        {
            return;
        }
        terminalonlinerun = true;
        try
        {
        	while(it.hasNext()){
        		session=it.next().getValue();
        		 String onlineterminal = "<terminal address='" + session.getDtgateaddress() + "' ip='" + session.getIoSession().getRemoteAddress().toString().split(":")[0] + "' time='" + session.getDtgateconnecttime() + "'>";
        		 onlineterminal += MetersOnline(session, "1"); //在线电表
        		 onlineterminal += "</terminal>";
        		 String revalue = "<SFROOT gy='" + gy + "'><" + gy + " opercode='" + opercode + "' tasknum='" + tasknum + "'><commandback code='" + commandcode + "'>" + onlineterminal + "</commandback></" + gy + "></SFROOT>";
        		 tranClient.sendServer(revalue);
        	}
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        terminalonlinerun = false;
    }
    
    /**
     * 获取某类型在线表计
     * @param session 当前网关的会话类
     * @param metertype 表计类型
     * @return
     * @throws SQLException
     */
    private String MetersOnline(Session session, String metertype) throws SQLException
    {
    	String str;
        String metersxml = "";
        ArrayList<String> meters = sd.metersOnline(session.getDtgateaddress(), metertype);
        Iterator<String> it=meters.iterator();
        while(it.hasNext()){
        	str=it.next();
        	String[] strarray = str.split(",");
        	 metersxml += "<meter metertype='" + metertype + "' address='" + strarray[0] + "' time='" + strarray[1] + "'/>";
        }
       
        return metersxml;
    }   
}

