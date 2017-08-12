package com.sf.energy.transfer.tranReciveMessage;

import com.sf.energy.transfer.db.SenderWithOracle;
import com.sf.energy.transfer.tftcp.TranClient;
/**
 * 主动上传数据的线程类
 * @author ky
 * @version 1.0
 */
public class AutoData implements Runnable{
	 /// 与发送有关的数据库操作
    private SenderWithOracle sd = new SenderWithOracle();
    TranClient tranClient=new TranClient();
	  /// <summary>
    /// 数据中转站主动上传小时用量
    /// </summary>
    private void AutoUpData()
    {
        boolean autouprun = true;
        try
        {
            //sd.AutoUpPrepare();
            AutoUpMeter("1"); //电表
           // AutoUpMeter("2"); //水表
            AutoUpMeterThreePhase(); //三相数据
            //AutoUpLampState();//路灯
            //AutoUpLonWorks(); //LonWorks数据
            //AutoUpClassroom(); //教室照明控制器当前数据
            //sd.AutoUpClose();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        autouprun = false;
    }
    
   /**
    * 主动上传某表类型数据
    * @param metertype 表计的类型
    */
    private void AutoUpMeter(String metertype)
    {
    	//System.out.println("开始上传电表");
        try
        {
            String ah = sd.autoUpToServer(metertype);
            //System.out.println("单项"+ah);
            while (!((ah==null)||("".equals(ah))))
            {
                boolean sendlen = tranClient.sendServer(ah);
                if (sendlen==false)
                {
                    break;
                }
                //SetTextFunction(DateTime.Now.TimeOfDay.ToString().Substring(0, 8) + " 上传一个用量数据包成功！" + "\r\n\r\n", 1);
                ah = sd.autoUpToServer(metertype);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void AutoUpMeterThreePhase()
    {
        try
        {
            String ah = sd.autoUpThreePhase();
            //System.out.println("三项"+ah);
            while (!((ah==null)||("".equals(ah))))
            {
                boolean sendlen = tranClient.sendServer(ah);
                if (sendlen==false)
                {
                    break;
                }
                //SetTextFunction(DateTime.Now.TimeOfDay.ToString().Substring(0, 8) + " 上传一个瞬时量数据包成功！" + "\r\n\r\n", 1);
                ah = sd.autoUpThreePhase();
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
		AutoUpData();
		
	}

}
