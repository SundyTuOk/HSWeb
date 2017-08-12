package com.sf.energy.transfer.tranReciveMessage;

import java.util.ArrayList;
import java.util.Date;

import com.sf.energy.transfer.db.SenderWithOracle;
import com.sf.energy.transfer.encode.SendGW;
import com.sf.energy.transfer.tcp.Session;
import com.sf.energy.transfer.tcp.TranGateSession;

public class MissDate implements Runnable{

	TranGateSession tranGateSession=TranGateSession.getTranGateSession();
	
	 /// 与发送有关的数据库操作
    private SenderWithOracle sd = new SenderWithOracle();
	private Session session;
	public MissDate(Session session){
		this.session=session;
	}
	private  void ReadMissData()
    {
        try
        {
            String ipaddress = session.getIoSession().getRemoteAddress().toString();
            String conaddress = session.getDtgateaddress();

            //Thread.Sleep(60000); //先等网关上传
            String missdata = sd.queryMissedData(session.getDtgateaddress());
            if(!((missdata==null)||("".equals(missdata)))) //如果有缺失数据则要先抄缺失数据再接收网关主动上传的数据
            {
               SendGW ss=new SendGW();
                //ss.terminalAddress = conaddress;
               ///补抄功能组包
               byte[] sendbuffer = ss.readLost(conaddress, missdata,0);
                boolean sendsuc=tranGateSession.sendHex(session, sendbuffer);           
                if (sendsuc)
                {
                	session.setLastReadMissDate(new Date());
                    session.setCommandKind(1);
                    session.setLastCommand("0DFFFF0264");
                    session.setLastPara(missdata);                   
                   // String strframe = rr.hexBufferToStr(sendbuffer, 1);
                    //SetHexFunction(ipaddress, rr.TerminalAddress, "", strframe, 1); //主动上报的回复无表地址
                    sd.updateLastDate(conaddress, missdata);
                }
                session.setNoMissData(false);
                
                if (!sendsuc)
                {
                    return;
                }
            }
            else
            {
            	session.setNoMissData(true);                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ReadMissData();
	}

}
