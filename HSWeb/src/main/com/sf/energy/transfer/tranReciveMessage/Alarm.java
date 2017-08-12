package com.sf.energy.transfer.tranReciveMessage;

import com.sf.energy.transfer.tcp.Session;
import com.sf.energy.transfer.tftcp.TranClient;

public class Alarm
{
	TranClient tranClient=new TranClient();
	 /// <summary>
    /// 报警
    /// </summary>
    /// <param name="alarmstr"></param>
    public void DGAlarm(Session session,String alarmstr)
    {
        try
        {
            if (!((alarmstr==null)||("".equals(alarmstr))))
            {
                String alarmxml = "<SFROOT gy='" + session.getGy() + "'><" + session.getGy() + " opercode='-1' tasknum='" + session.getTasknum() + "' isend='1'><commandback code='alarm' error='0' errormessage=''>";
                String[] alarmarray = alarmstr.split("|");
                for (int i = 0; i < alarmarray.length - 1; i++)
                {
                    alarmxml += "<result name=\"\" value=\"" + alarmarray[i] + "\"/>";
                }
                alarmxml += "</commandback></" + session.getGy() + "></SFROOT>";
                tranClient.sendServer(alarmxml);
            }
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
    }
}
