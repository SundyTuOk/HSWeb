package com.sf.energy.transfer.tranReciveMessage;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.apache.mina.core.session.IoSession;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sf.energy.transfer.encode.SendGW;
import com.sf.energy.transfer.encode.Sender;
import com.sf.energy.transfer.tcp.Session;
import com.sf.energy.transfer.tcp.TranGateSession;
import com.sf.energy.transfer.tftcp.TranClient;

public class TranSerClientHandl
{
	/// 是否自动抄读电表
    private boolean ifAutoReadMeter = false;

    /// 上次主动抄表开关
    private boolean lastautoreadmeter = false;

    /// 是否自动抄读路灯
    private boolean ifAutoReadLamp = true;

    /// 上次主动抄路灯开关
    private boolean lastautoreadlamp = true;
    /// 命令超时时间
    private int commanddelaytime = 30*1000;
	
    /// 采集电表频率
    private double readfrequencyam = 20;
   
    /// 上传频率   
    private double upfrequency = 60;
    private boolean keepconnect = true;
    TranGateSession tranGateSession=TranGateSession.getTranGateSession();
    HashMap<String,Session> hashMap=tranGateSession.getHashMap();
    TranClient tranClient=new TranClient();
	
	///从服务器接收到的xml字符串
	private String info;
	public TranSerClientHandl(String info){		
		this.info=info;
	}
	/**
	 * 对web端传过来的xml进行解析，处理
	 * @param info xml字符串
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws InterruptedException 
	 */
   public  void ReveiveFromServer() throws SAXException, IOException{	
		if(info.length()<20){
			 return;
		 }
		else {					
         String gy = "", opercode = "", tasknum = "", terminaladdress="",way="",ip="";
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
 		 DocumentBuilder dBuilder = null;
 		 try
		{
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		 
 		 Document doc = dBuilder.parse(new InputSource(new StringReader(info)));
 		 //得到根节点
 		 Element root = doc.getDocumentElement();
 		 //得到gy
	     gy=root.getAttribute("gy");
 		 Element gw=(Element)(root.getFirstChild());
 		 //获取opercode的值
 		 opercode=gw.getAttribute("opercode");
 		 ip=gw.getAttribute("ip");
 		 //获取tasknum的值
 		 tasknum=gw.getAttribute("tasknum");
 		 ///获取数据网关的地址
         terminaladdress =gw.getAttribute("terminaladdress");
         way =gw.getAttribute("way");
 		 ////System.out.println(terminaladdress);
       String username =null;
         if (opercode == "-1")
         {
             username ="系统管理员";
         }
          
         //遍历gw孩子节点的值
         NodeList rootList=doc.getElementsByTagName("command");
         for(int i = 0; i< rootList.getLength() ; i ++){
        	 Element node=(Element)rootList.item(i);
        	 String commandCode=node.getAttribute("code");
        	 String para=node.getAttribute("para");
        	 String metertype1=node.getAttribute("metertype");
        	 String pw=node.getAttribute("pw");
        	//Lonworks命令 
             if ((commandCode == "04126") || (commandCode == "04134")) 
             {
                 terminaladdress = doc.getElementsByTagName("terminaladdress").item(0).getFirstChild().getNodeValue();
               //para中保存的是表地址串,如：00150700095c6600001
                // LonworksSend(opercode, tasknum, terminaladdress, commandCode, para);
                 continue;
             }
            //服务器对数据中转站校时
             if ("correcttime".equals(commandCode)) 
             {
                              		 
                /// 修改系统时间
                 Runtime.getRuntime().exec("cmd /c time timevalue");               
                 continue;
             }
            
            //服务器读数据中转站在线网关地址
             if (commandCode == "terminalstate")
             {
                // new AutoUp().TerminalOnline(gy, opercode, tasknum, commandCode);
                 continue;
             }
           //对某些命令,如下表号之类,带了电表类型属性
             String metertype = "";
             if (metertype!= null)
             {
                 metertype = metertype1;
             }
           //设置采集频率
            if (commandCode == "setreadfrequency") 
             {
                // readfrequencyam = Double.parseDouble(para);
                //默认电表
                 String segname = "ReadFrequencyAm"; 
                 if (metertype == "2") 
                 {
                     segname = "ReadFrequencyWa";
                 }
                 //修改*.config配置文件
                // boolean ifsuc = CConfig.ChangeMycon(segname, readfrequencyam.ToString());
                boolean ifsuc=true;
                 String revalue = "<SFROOT gy='" + gy + "'><" + gy + " opercode=\"" + opercode + "\" tasknum=\"" + tasknum + "\" isend='1'>"; 
                 if (ifsuc)
                 {
                     revalue += "<commandback code=\"" + commandCode + "\" metertype='" + metertype + "' error=\"0\" errormessage=\"\"></commandback>";
                 }
                 else
                 {
                     revalue += "<commandback code=\"" + commandCode + "\" metertype='" + metertype + "' error=\"1\" errormessage=\"设置失败\"></commandback>";
                 }
                 revalue+="</" + gy + "></SFROOT>";
                 //tranClient.sendServer(revalue);
                 //DataSiteToServerSendText(revalue);
                 continue;
             }
             //设置上传频率
             if (commandCode == "setupfrequency") 
             {
                 //upfrequency = Double.parseDouble(para);
                 // //修改*.config配置文件
                // boolean ifsuc=CConfig.ChangeMycon("UpFrequency", upfrequency.ToString());
                boolean ifsuc=true;
                 String revalue = "<SFROOT gy='" + gy + "'><" + gy + " opercode=\"" + opercode + "\" tasknum=\"" + tasknum + "\" isend='1'>"; 
                 if (ifsuc)
                 {
                     revalue = "<commandback code=\"" + commandCode + "\" error=\"0\" errormessage=\"\"></commandback>";
                 }
                 else
                 {
                     revalue = "<commandback code=\"" + commandCode + "\" error=\"1\" errormessage=\"设置失败\"></commandback>";
                 }
                 revalue += "</" + gy + "></SFROOT>";
                 //tranClient.sendServer(revalue);
                 continue;
             }
             //向数据中转站下发路灯地址
             if (commandCode == "lampdown")
             {
                 String lampinfo = para;
                 //boolean ifsuc=InsertLamp(terminaladdress,lampinfo);
                // BackToServer(gy, terminaladdress, opercode, tasknum, commandCode, ifsuc);
                 continue;
             }
             /////设置报警限制
             if (commandCode == "setalarm") 
             {
                 String alarminfo = para;
                 //boolean ifsuc = sd.InsertAlarm(terminaladdress, alarminfo);
                 //BackToServer(gy,terminaladdress,opercode,tasknum,commandCode,ifsuc);
                 continue;
             }
            //默认密码为国网集中器的16字节密码,因其长度最长,就算是其它规约也不至于报错
             String password = "00000000000000000000000000000000"; 
            
            
             if (!((pw==null)||("".equals(pw))))
             {
                 password =pw;
             }
            //对下表号命令,需同时设置本网关是否自动轮抄
             String terminalautoread = "1"; 
             if (node.getAttribute("autoread") != null)
             {
                 terminalautoread = node.getAttribute("autoread");
             }
            
           //服务器发过来的普通命令
             switch (way) 
             {
                 case "0": //GPRS(集中器为服务器模式)
                 case "1": //CDMA(集中器为服务器模式)
                 case "2": //GPRS(集中器为客户端模式)
                 case "3": //CDMA(集中吕为客户端模式)
                 case "5":  //以太网
                     try
					{
                   	 //System.out.println(para+"  "+ip+"  "+tasknum);
                    	 //System.out.println(terminaladdress+"  "+commandCode+"  "+password);
                    	 //System.out.println(way+"  "+metertype+"  "+terminalautoread);
						 NetSend(gy, ip, tasknum, terminaladdress, commandCode, password, para, metertype, terminalautoread);
					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                     break;
                 case "4": //PSTN
                     break;
                 case "6": //????
                     //if ((myPort != null) && (myPort.IsOpen))
                     //{
                     //    COMSend(gy, opercode, tasknum, terminaladdress, commandcode, password, para, metertype, terminalautoread);
                     //}
                     //else
                     //{
                     //    SetTextFunction("????δ??!", 2);
                     //}
                     break;
                 default: break;
             }
         }
       }
	}
	
	 /// <summary>
    /// 数据网关接收到服务器的命令时调用
    /// </summary>
    /// <param name="operatenode"></param>
   public void NetSend(String gy, String opercode, String tasknum, String terminaladdress, String commandcode, String password, String para,String metertype,String terminalautoread) throws InterruptedException
    {
    	Session session=new Session();
    	Sender ss=new SendGW();
    	byte[] sendbuffer = new byte[500];
        boolean dtgateconn = false;
        Set<Map.Entry<String,Session>> iter=hashMap.entrySet();
        Iterator<Map.Entry<String,Session>> it=iter.iterator();
        while(it.hasNext()){
        	session=it.next().getValue();
        	if(terminaladdress.equals(session.getDtgateaddress())){
        		while(!((session.getLastCommand()==null)||("".equals(session.getLastCommand())))){
        			Date temp=new Date(session.getDtgateconnecttime().getTime()+commanddelaytime);        			
        			if(temp.getTime()<new Date().getTime()){
        				break;
        			}
        			Thread.sleep(100);
        		}
        		  lastautoreadmeter = ifAutoReadMeter;
                  ifAutoReadMeter = false; //停止主动抄表

                  lastautoreadlamp = ifAutoReadLamp;
                  ifAutoReadLamp = false; //停止主动抄路灯
                  session.setOperCode(opercode);
                  session.setTasknum(tasknum);
                  session.setMeterAddress(terminaladdress);
                  session.setLastCommand(commandcode);
                  //如果是下表号及删除某端口下表号命令,则要保存表号或端口信息,以备接收到返回数据后修改数据库
                  //如果是阶梯电价切换任务,也要保存其标志,返给服务器结果时作为判断依据
                  String spepara = ss.specialCommand(commandcode,metertype,terminalautoread,para);
                  if (!((spepara==null)||("".equals(spepara))))
                  {
                	  session.setLastPara(spepara);                     
                  }
                  session.setCommandKind(2);                 
                  sendbuffer = ss.sendCommand(terminaladdress, commandcode, para, 0); 
                  //System.out.println("需要发送的命令");
                  for(int i=0;i<sendbuffer.length;i++){
          			System.out.print(Integer.toHexString(sendbuffer[i]&0xff) + " ");
          		}
                  boolean sendsuc =tranGateSession.sendHex(session, sendbuffer);
                  if (!sendsuc) //如果发送不成功，则跳出循环，否则foreach会出错
                  {
                      break;
                  }
                  dtgateconn = true;
                  String ipaddress =session.getIoSession().getRemoteAddress().toString();                 		                   
              }
        	}
       
     
                 
                 

        if (!dtgateconn)
        {
            String revalue = "<SFROOT gy='"+gy+"'><"+gy+" terminaladdress=\"" + terminaladdress + "\" opercode=\"" + opercode + "\" tasknum=\"" + tasknum + "\" isend='1'><commandback code=\"" + commandcode + "\" error=\"1\" errormessage=\"终端不在线\"></commandback></"+gy+"></SFROOT>";
            tranClient.sendServer(revalue);
        }
    }
    
    private void BackToServer(String gy,String terminaladdress,String opercode,String tasknum,String commandcode,Boolean ifsuc)
    {
        String revalue = "<SFROOT gy='" + gy + "'><" + gy + " terminaladdress=\"" + terminaladdress + "\" opercode=\"" + opercode + "\" tasknum=\"" + tasknum + "\" isend='1'>";
        if (ifsuc)
        {
            revalue += "<commandback code=\"" + commandcode + "\" error=\"0\" errormessage=\"\">";
        }
        else
        {
            revalue += "<commandback code=\"" + commandcode + "\" error=\"1\" errormessage=\"\">";
        }
        revalue += "</commandback></" + gy + "></SFROOT>";
        tranClient.sendServer(revalue);
        //DataSiteToServerSendText(revalue);
    }
	 public  void NetSend1(String gy, String opercode, String tasknum, String terminaladdress, String commandcode, String password, String para,String metertype,String terminalautoread) throws InterruptedException
	    {
	    	//Session session=new Session();
	    	Sender ss=new SendGW();
	    	byte[] sendbuffer = new byte[500];
	        boolean dtgateconn = false;
	        sendbuffer = ss.sendCommand(terminaladdress, commandcode, para, 0); 
	        for(int i=0;i<sendbuffer.length;i++){
				System.out.print(Integer.toHexString(sendbuffer[i]&0xff) + " ");
			}
	        
	    } 

}
