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


public class MuLineDao
{
	//加载后定时刷新绑定低压配电房信息
	public void BindDYMuLinePDData (JSONArray json,String DYMuLineLineList,String thelastTimeList) throws SQLException
	{
		//获取低压配电房
		String sql = "select PART_ID,PART_NUM,PART_NAME from PD_PART where PARTSTYLE_ID=01 "+
				"and PART_ID  in(select PART_PARENT from PD_PART where PART_ID in("+DYMuLineLineList+"))";
		
		Connection conn1=null;
		PreparedStatement ps1 =null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs1 = ps1.executeQuery();			
			while (rs1.next())
			{
				String RoomPART_ID=rs1.getString("PART_ID");
				String RoomPART_Name=rs1.getString("PART_NAME");
				//检查该配电房是否有低压母线
				//**************************************加载配电房内低压回路******************************************************//
				 sql = "select aa.PART_ID,aa.PART_NAME,aa.PART_NUM,nvl( b.DYSXL,5)DYSXL,nvl(b.DYXXL,5)DYXXL,nvl(b.AXEDL,5)AXEDL,nvl(b.BXEDL,5)BXEDL,nvl(b.CXEDL,5)CXEDL,nvl(b.XEDLL,5)XEDLL,nvl(b.JLID,0)JLID from PD_PART  aa,PD_PARTINFO03  b where aa.PART_ID=b.PART_ID and b.ISMULINE=1 "+ 
							" and aa.PART_PARENT ="+RoomPART_ID;
				
				String PART_ID = "0";
				String PART_NAME = "";
				String PART_NUM = "";
				String AXEDL = "";//A电流越线值
				String BXEDL = "";//B电流越线值
				String CXEDL = "";//C电流越线值
				String XEDLL = "";//总电流越线值
				String JLID="";//计量表计ID
				JSONObject TheRoom = new JSONObject();
				
				TheRoom.put("PART_ID", RoomPART_ID);
				TheRoom.put("RoomName",RoomPART_Name);
				JSONArray LinesInfo=new JSONArray();
				
				Connection conn=null;
				PreparedStatement ps =null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs = ps.executeQuery();	

					while (rs.next())
					{
						JSONObject jo = new JSONObject();
						PART_ID = rs.getString("PART_ID");
						PART_NAME = rs.getString("PART_NAME");//名称
						PART_NUM = rs.getString("PART_NUM");//编号
						AXEDL = rs.getString("AXEDL");//A电流越线值
						BXEDL = rs.getString("BXEDL");//A电流越线值
						CXEDL = rs.getString("CXEDL");//A电流越线值
						XEDLL = rs.getString("XEDLL");//总电流越线值
						JLID = rs.getString("JLID");//计量表计ID

				
						jo.put("PART_ID", PART_ID);
						jo.put("PART_NAME", PART_NAME);
						jo.put("PART_NUM", PART_NUM);
						jo.put("AXEDL", AXEDL);
						jo.put("BXEDL", BXEDL);
						jo.put("CXEDL", CXEDL);
						jo.put("XEDLL", XEDLL);
						jo.put("JLID", JLID);
						jo.put("LEVEL", 10);//电压等级
						jo.put("Unit", "A");//电流等级单位
						LinesInfo.add(jo);
						
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn, ps, rs);
				}
				 Object[] obj=	LinesInfo.toArray();  
				 
				 if(obj.length >0)//只显示有低压回路的配电房
				 {
					//绑定当前相电压值
					BindDL(LinesInfo,DYMuLineLineList, thelastTimeList);
					TheRoom.put("Data", LinesInfo);
					
					json.add(TheRoom);
				 }
				//**************************************end加载配电房内低压回路******************************************************//
			
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

	public void   BindLineInfo(JSONArray theLineInfo,String PART_PARENT,int LEVEL,String Unit)
	{
		String sql = "select PART_ID,PARTSTYLE_ID,PART_NAME,PART_NUM,(select count(PART_ID) from PD_PART  b where b.PART_PARENT=aa.PART_ID)ChildCount from PD_PART  aa where PART_PARENT ="+PART_PARENT;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				String PART_ID=rs.getString("PART_ID");
				
				if( rs.getString("PARTSTYLE_ID").equals("03"))//是回路就监测是否是总线
				{
					Connection conn1=null;
					PreparedStatement ps1 =null;
					ResultSet rs1 = null;
					try
					{
						sql="select aa.PART_ID,aa.PART_NAME,aa.PART_NUM,nvl(b.AXEDL,0)AXEDL,nvl(b.BXEDL,0)BXEDL,nvl(b.CXEDL,0)CXEDL,nvl(b.XEDLL,0)XEDLL,nvl(b.JLID,0)JLID from PD_PART  aa,PD_PARTINFO03  b where aa.PART_ID=b.PART_ID and b.ISMULINE=1 and aa.PART_ID="+PART_ID;
						conn1 = ConnDB.getConnection();
						ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs1 = ps1.executeQuery();			
						while (rs1.next())
						{
							JSONObject jo = new JSONObject();
							PART_ID = rs1.getString("PART_ID");
							String PART_NAME = rs1.getString("PART_NAME");//名称
							String PART_NUM = rs1.getString("PART_NUM");//编号
							String AXEDL = rs1.getString("AXEDL");//A电流越线值
							String BXEDL = rs1.getString("BXEDL");//B电流越线值
							String CXEDL = rs1.getString("CXEDL");//C电流越线值
							String XEDLL = rs1.getString("XEDLL");////总电流越线值
							String JLID = rs1.getString("JLID");//计量表计ID

			
							jo.put("PART_ID", PART_ID);
							jo.put("PART_NAME", PART_NAME);
							jo.put("PART_NUM", PART_NUM);
							jo.put("AXEDL", AXEDL);
							jo.put("BXEDL", BXEDL);
							jo.put("CXEDL", CXEDL);
							jo.put("XEDLL", XEDLL);
							jo.put("JLID", JLID);
							jo.put("LEVEL", LEVEL);//电压等级
							jo.put("Unit", Unit);//电压等级单位
							theLineInfo.add(jo);
						}
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn1, ps1, rs1);
					}
				}
				if( rs.getString("PARTSTYLE_ID").equals("01"))//如果是配电房就结束递归
				{//&&Unit.equals("kv")
					//continue;
				}
				if( rs.getInt("ChildCount")>0)//有下级节点
				{
					BindLineInfo(theLineInfo,PART_ID, LEVEL, Unit);
				}
				else {
				continue;
				}
				
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	}

//获取低压配电房信息
public void getDYMuLinePDRoomInfo (JSONArray json) throws SQLException
{
	
	//获取低压配电房
	String sql = "select PART_ID,PART_NUM,PART_NAME from PD_PART where PARTSTYLE_ID=01 "+
			"and PART_ID  not in(select PART_ID from PD_PARTINFO01 where LOWER(DYLEVEL)='10kv' or LOWER(DYLEVEL)='10000v') order by PART_NAME";
	
	Connection conn1=null;
	PreparedStatement ps1 =null;
	ResultSet rs1 = null;
	try
	{
		conn1 = ConnDB.getConnection();
		ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs1 = ps1.executeQuery();			
		while (rs1.next())
		{
			String RoomPART_ID=rs1.getString("PART_ID");
			String RoomPART_Name=rs1.getString("PART_NAME");
			//检查该配电房是否有低压母线
			//**************************************加载配电房内低压回路******************************************************//
			 sql = "select aa.PART_ID,aa.PART_NAME,aa.PART_NUM,nvl(b.AXEDL,0)AXEDL,nvl(b.BXEDL,0)BXEDL,nvl(b.CXEDL,0)CXEDL,nvl(b.XEDLL,0)XEDLL,nvl(b.JLID,0)JLID from PD_PART  aa,PD_PARTINFO03  b where aa.PART_ID=b.PART_ID and b.ISMULINE=1 "+ 
						" and aa.PART_PARENT ="+RoomPART_ID;
			
			JSONObject TheRoom = new JSONObject();
			
			TheRoom.put("PART_ID", RoomPART_ID);
			TheRoom.put("RoomName",RoomPART_Name);
			JSONArray LinesInfo=new JSONArray();
			
			BindLineInfo(LinesInfo,rs1.getString("PART_ID"),220,"A");
			

			 Object[] obj=	LinesInfo.toArray();  
			 
			 if(obj.length >0)//只显示有低压回路的配电房
			 {
				//绑定当前相电压值
						       
				 BindDL(LinesInfo,"", "");
				TheRoom.put("Data", LinesInfo);
				
				json.add(TheRoom);
			 }
			//**************************************end加载配电房内低压回路******************************************************//
		
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
