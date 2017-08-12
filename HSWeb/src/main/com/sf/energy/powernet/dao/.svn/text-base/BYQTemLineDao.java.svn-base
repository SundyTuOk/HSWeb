package com.sf.energy.powernet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.sf.energy.util.ConnDB;


 public class BYQTemLineDao
 {
	//加载后定时刷新绑定温度信息
	public void BindTemData (JSONArray json,String DYBYQTemLineList,String thelastTimeList) throws SQLException
	{
		String BYQTemList [] =DYBYQTemLineList.split(",");
		String TimeList[] =thelastTimeList.split(",");
		for(int i=0;i<BYQTemList.length;i++)
		{
			String	TempDev_ID=BYQTemList[i];
			JSONArray	TempDevDataArray=GetTempDevData(TempDev_ID, "", TimeList[i]);
			json.add(TempDevDataArray);
		}
		
		//获取变压器的温度设备信息
			
	}

//获取低压配电房信息
public void getBYQTemLinePDRoomInfo (JSONArray json) throws SQLException
{
	
	//获取配电房内变压器
	String sql = "select PART_ID,PART_NUM,PART_NAME,PART_PARENT from PD_PART where PARTSTYLE_ID=02 order by PART_NAME";
	
	Connection conn1=null;
	PreparedStatement ps1 =null;
	ResultSet rs1 = null;
	try
	{
		conn1 = ConnDB.getConnection();
		ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs1 = ps1.executeQuery();			
		JSONObject TheRoom = new JSONObject();//
		JSONArray LinesInfo=new JSONArray();//变压器列表
		while (rs1.next())
		{
			
			JSONObject joBYQ = new JSONObject();
			String	BYQPART_ID = rs1.getString("PART_ID");
			String	BYQPART_NAME = rs1.getString("PART_NAME");//名称
			String	BYQPART_NUM = rs1.getString("PART_NUM");//编号
			String  PART_PARENT= rs1.getString("PART_PARENT");//父节点
			//查询TheRoom中是否有相应的变压器信息

			GetBYQToRoom( json, BYQPART_ID, PART_PARENT, BYQPART_ID, BYQPART_NAME, BYQPART_NUM);
		
			
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}finally{
		ConnDB.release(conn1, ps1, rs1);
	}
	
	
	//return TheRoom;
	

}
//获取变压器对应的配电房信息
public void GetBYQToRoom(JSONArray json,String PPART_ID,String PPART_PARENT,String PART_ID,String PART_NAME,String PART_NUM)
{

	Connection conn1=null;
	PreparedStatement ps1 =null;
	ResultSet rs1 = null;
	try
	{	//获取变压器对应的配电房ID
		String sql = "select PART_ID,PART_NUM,PART_NAME,PART_PARENT,PARTSTYLE_ID from PD_PART where PART_ID="+PPART_PARENT;
		conn1 = ConnDB.getConnection();
		ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs1 = ps1.executeQuery();			
		if(rs1.next())
		{
			PPART_ID=rs1.getString("PART_ID");
		String	PPART_Name=rs1.getString("PART_NAME");
		String	PPART_Num=rs1.getString("PART_NUM");
			PPART_PARENT=rs1.getString("PART_PARENT");
			String PARTSTYLE_ID= rs1.getString("PARTSTYLE_ID");
			if( rs1.getString("PARTSTYLE_ID").equals("01"))//配电房	
			{
				AddBQYInfoToRoom( json, PPART_ID, PPART_Name,  PART_ID, PART_NAME, PART_NUM);
			}
			else if( rs1.getString("PARTSTYLE_ID").equals("03"))//线路
			{
				GetBYQToRoom(json,PPART_ID, PPART_PARENT,PART_ID,PART_NAME,PART_NUM);
			}
		}
		else {
			//到了最上级仍然没有找到相应的配电房，就将变压器添加到0级上

			AddBQYInfoToRoom( json, "0", "",  PART_ID, PART_NAME, PART_NUM);
		
		}

	}
	catch (Exception e)
	{
		e.printStackTrace();
	}finally{
		ConnDB.release(conn1, ps1, rs1);
	}
}
//将变压器、配电房信息添加到数组
public void AddBQYInfoToRoom(JSONArray json,String PPART_ID,String PPART_Name,String PART_ID,String PART_NAME,String PART_NUM){
	//将变压器配置到配电房下，检查该配电房是否已经存储在TheRoom中

	 boolean hasdata=false;
	 Object[] obj=	json.toArray();  
	 for (int i = 0; i < obj.length; i++)
	{
		 JSONObject TheRoom =(JSONObject)obj[i];
		if(TheRoom.getString("PART_ID").equals(PPART_ID))
		{
			hasdata=true;
			JSONArray LinesInfo =(JSONArray)	TheRoom.get("Data");
			JSONObject jo = new JSONObject();
			
			jo.put("PART_ID", PART_ID);
			jo.put("PART_NAME", PART_NAME);
			jo.put("PART_NUM", PART_NUM);
			
			jo.put("Unit", "℃");//
			
			JSONArray TempDevList = GetBYQTempDevInfo( PART_ID);
			jo.put("TempDevList", TempDevList);//
			LinesInfo.add(jo);
			
			break;
		}
	}
	 if(!hasdata)
	 {
		 JSONObject TheRoom = new JSONObject();
			TheRoom.put("PART_ID", PPART_ID);
			TheRoom.put("RoomName",PPART_Name);
			JSONArray LinesInfo=new JSONArray();//变压器列表
			/****************/
			JSONObject jo = new JSONObject();
		
			jo.put("PART_ID", PART_ID);
			jo.put("PART_NAME", PART_NAME);
			jo.put("PART_NUM", PART_NUM);
			
			jo.put("Unit", "℃");//
			/*JSONArray TempDevList = new JSONArray();
			
			Connection conn1=null;
			PreparedStatement ps1 =null;
			ResultSet rs1 = null;
			try
			{	//获取变压器对应的配电房ID
				String sql = "select TempDev_ID,TempDev_Name from TempDev where VolTaGeTrAnS_ID="+PART_ID;
				conn1 = ConnDB.getConnection();
				ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs1 = ps1.executeQuery();			
				while(rs1.next())
				{
				String	TempDev_ID=rs1.getString("TempDev_ID");
			//获取变压器的温度设备信息
				
				JSONArray	TempDevDataArray=GetTempDevData(TempDev_ID, rs1.getString("TempDev_Name"), "");
				TempDevList.add(TempDevDataArray);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn1, ps1, rs1);
			}
			
			jo.put("TempDevList", TempDevList);//
*/			
			
			JSONArray TempDevList = GetBYQTempDevInfo( PART_ID);
			jo.put("TempDevList", TempDevList);//
			LinesInfo.add(jo);
			/*******************/
			
			TheRoom.put("Data", LinesInfo);
			json.add(TheRoom);
	 }
}
public JSONArray GetBYQTempDevInfo(String PART_ID)
{
	JSONArray TempDevList = new JSONArray();
	
	Connection conn1=null;
	PreparedStatement ps1 =null;
	ResultSet rs1 = null;
	try
	{	//获取变压器对应的配电房ID
		String sql = "select TempDev_ID,TempDev_Name from TempDev where VolTaGeTrAnS_ID="+PART_ID;
		conn1 = ConnDB.getConnection();
		ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs1 = ps1.executeQuery();			
		while(rs1.next())
		{
		String	TempDev_ID=rs1.getString("TempDev_ID");
	//获取变压器的温度设备信息
		
		JSONArray	TempDevDataArray=GetTempDevData(TempDev_ID, rs1.getString("TempDev_Name"), "");
		TempDevList.add(TempDevDataArray);
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}finally{
		ConnDB.release(conn1, ps1, rs1);
	}
	return TempDevList;
}
public JSONArray GetTempDevData(String TempDev_ID,String TempDev_Name,String LastTime)
{
	if(LastTime.equals(""))
	{
	  SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	      Date date = new Date();
	      LastTime = f.format(date);
	}
	JSONArray	DataArray=new JSONArray();
	
	
	Connection conn1=null;
	PreparedStatement ps1 =null;
	ResultSet rs1 = null;
	try
	{	//获取变压器对应的配电房ID
		String sql = "select to_char(VALUETIME,'yyyy-mm-dd hh24:mi:ss')VALUETime,TempValue from TEMPDEVDATAS where TempDev_ID="+TempDev_ID+" and VALUETIME >to_date('"+LastTime+"','yyyy-mm-dd hh24:mi:ss')  order by VALUETIME ";
		conn1 = ConnDB.getConnection();
		ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs1 = ps1.executeQuery();			
		int n=0;
		while(rs1.next())
		{
		/*	n++;
			if(n>10)
				break;*/
		 JSONObject DataInfo = new	 JSONObject();
		 DataInfo.put("VALUETIME",  rs1.getString("VALUETIME"));
		 DataInfo.put("TempValue",  rs1.getString("TempValue"));
		DataArray.add(DataInfo);
		LastTime=rs1.getString("VALUETIME");
	
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}finally{
		ConnDB.release(conn1, ps1, rs1);
	}
	
	
	JSONObject DataInfo1 = new	 JSONObject();
	DataInfo1.put("TempDev_ID",  TempDev_ID);
	DataInfo1.put("TempDev_Name",  TempDev_Name);
	DataInfo1.put("LastTime",  LastTime);
	DataArray.add(0, DataInfo1);
	return DataArray;
}
public void BindDL(JSONArray LinesInfo,String DYMuLineLineList,String thelastTimeList)throws SQLException{
	String LastTime = "";
	String[] PART_IDList = DYMuLineLineList.split(",");
	String[] LastTimeList = thelastTimeList.split(",");
	 Object[] obj=	LinesInfo.toArray();  
	 	 for(int i=0;i<obj.length;i++)
	 	 {
	 		
		 JSONObject jo = JSONObject.fromObject(obj[i]);
		 JSONArray DataArray=new     			JSONArray();
		 	String	 JLID= jo.getString("JLID");
			String	 PART_ID= jo.getString("PART_ID");
		 	
        if(!JLID.equals("") && !JLID.equals("0") )
        {
        	Date d = new Date();//生成当前日期
        	d.getTime();//获取当前日期的时间戳
        	Connection conn=null;
	 		PreparedStatement ps =null;
	 		ResultSet rs = null;
        if(thelastTimeList.equals(""))//没有传时间就默认用当天0点
        {
	 		  SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	 	      Date date = new Date();
	 	      LastTime = f.format(date);
        }
        else{
        	for(int j=0;j<PART_IDList.length;j++)
        	{
        		if(PART_IDList[j].equals(PART_ID))
        		{
        			LastTime=    LastTimeList[j];    
        			break;
        		}
        		
        	}
        	if(LastTime.equals(""))
        	{
        		 SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
   	 	      Date date = new Date();
   	 	      LastTime = f.format(date);
        		
        	}
        }
	 		
        	String sql="select  to_char(VALUETIME,'yyyy-mm-dd hh24:mi:ss') VALUETIME,CURRENTA,CURRENTB,CURRENTC from ZPDDATAS"+JLID+"  where VALUETIME >to_date('"+LastTime+"','yyyy-mm-dd hh24:mi:ss')  order by VALUETIME ";

        	try
    		{
        		conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();	
    		
    			while (rs.next())
    			{
    	
    				 JSONObject DataInfo = new	 JSONObject();
    				 DataInfo.put("VALUETIME",  rs.getString("VALUETIME"));
    			
    				 DataInfo.put("CURRENTA",  rs.getString("CURRENTA"));
    				 DataInfo.put("CURRENTB",  rs.getString("CURRENTB"));
    				 DataInfo.put("CURRENTC",  rs.getString("CURRENTC"));
            		DataArray.add(DataInfo);
            		LastTime=rs.getString("VALUETIME");
            	}
    		
    		}
        	catch (NumberFormatException e)
    		{
    			e.printStackTrace();
    		}finally{
    			ConnDB.release(conn, ps, rs);
    		}
        } 
        
        jo.put("LastTime", LastTime.toString());
        jo.put("DataArray", DataArray);
        LinesInfo.set(i, jo);
     }  

	
	
}
}
