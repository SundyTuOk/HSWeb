package com.sf.energy.powernet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.sf.energy.util.ConnDB;


public class MuBarDao
{
	//绑定高压配电房信息
	public void BindGYMuBarPDData(JSONArray json,String GYMubarLineList) throws SQLException
	{
			String sql = "select aa.PART_ID,aa.PART_NAME,aa.PART_NUM,nvl( b.DYSXL,5)DYSXL,nvl(b.DYXXL,5)DYXXL,nvl(b.XEDLL,5)XEDLL,nvl(b.JLID,0)JLID from PD_PART  aa,PD_PARTINFO03  b where aa.PART_ID=b.PART_ID "+ 
						" and aa.PART_ID in("+GYMubarLineList+")";
			
			String PART_ID = "0";
			String PART_NAME = "";
			String PART_NUM = "";
			String DYSXL = "";//电压上线越线率
			String DYXXL = "";//电压下线越线率
			String XEDLL = "";//电流越线率
			String JLID="";//计量表计ID
			JSONObject TheRoom = new JSONObject();
			TheRoom.put("PART_ID", "0");
			TheRoom.put("RoomName", "10KV母线电压监控");
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
					DYSXL = rs.getString("DYSXL");//电压上线越线率（%）
					DYXXL = rs.getString("DYXXL");//电压下线越线率（%）
					XEDLL = rs.getString("XEDLL");//电流越线率（%）
					JLID = rs.getString("JLID");//计量表计ID

			
					jo.put("PART_ID", PART_ID);
					jo.put("PART_NAME", PART_NAME);
					jo.put("PART_NUM", PART_NUM);
					jo.put("DYSXL", DYSXL);
					jo.put("DYXXL", DYXXL);
					jo.put("XEDLL", XEDLL);
					jo.put("JLID", JLID);
					jo.put("LEVEL", 10);//电压等级
					jo.put("Unit", "kv");//电压等级单位
					LinesInfo.add(jo);
					
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
		
			//绑定当前线电压值
			BindXianDY(LinesInfo);

			TheRoom.put("Data", LinesInfo);
			
			json.add(TheRoom) ;
			
		}
	//绑定低压配电房信息
	public void BindDYMuBarPDData (JSONArray json,String DYMubarLineList) throws SQLException
	{
		
		//获取低压配电房
		String sql = "select PART_ID,PART_NUM,PART_NAME from PD_PART where PARTSTYLE_ID=01 "+
				"and PART_ID  in(select PART_PARENT from PD_PART where PART_ID in("+DYMubarLineList+"))";
		//获取低压配电房
	//	String sql = "select PART_ID,PART_NUM,PART_NAME from PD_PART where PARTSTYLE_ID=01 "+
		//		"and PART_ID  in("+DYMubarLineList+")";
		
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
				 sql = "select aa.PART_ID,aa.PART_NAME,aa.PART_NUM,nvl( b.DYSXL,5)DYSXL,nvl(b.DYXXL,5)DYXXL,nvl(b.XEDLL,5)XEDLL,nvl(b.JLID,0)JLID from PD_PART  aa,PD_PARTINFO03  b where aa.PART_ID=b.PART_ID and b.ISMULINE=1 "+ 
							" and aa.PART_PARENT ="+RoomPART_ID;
				
				String PART_ID = "0";
				String PART_NAME = "";
				String PART_NUM = "";
				String DYSXL = "";//电压上线越线率
				String DYXXL = "";//电压下线越线率
				String XEDLL = "";//电流越线率
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
						DYSXL = rs.getString("DYSXL");//电压上线越线率（%）
						DYXXL = rs.getString("DYXXL");//电压下线越线率（%）
						XEDLL = rs.getString("XEDLL");//电流越线率（%）
						JLID = rs.getString("JLID");//计量表计ID

				
						jo.put("PART_ID", PART_ID);
						jo.put("PART_NAME", PART_NAME);
						jo.put("PART_NUM", PART_NUM);
						jo.put("DYSXL", DYSXL);
						jo.put("DYXXL", DYXXL);
						jo.put("XEDLL", XEDLL);
						jo.put("JLID", JLID);
						jo.put("LEVEL", 220);//电压等级
						jo.put("Unit", "v");//电压等级单位
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
					BindXiangDY(LinesInfo);
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
						sql="select aa.PART_ID,aa.PART_NAME,aa.PART_NUM,nvl( b.DYSXL,5)DYSXL,nvl(b.DYXXL,5)DYXXL,nvl(b.XEDLL,5)XEDLL,nvl(b.JLID,0)JLID from PD_PART  aa,PD_PARTINFO03  b where aa.PART_ID=b.PART_ID and b.ISMULINE=1 and aa.PART_ID="+PART_ID;
						conn1 = ConnDB.getConnection();
						ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs1 = ps1.executeQuery();			
						while (rs1.next())
						{
							JSONObject jo = new JSONObject();
							PART_ID = rs1.getString("PART_ID");
							String PART_NAME = rs1.getString("PART_NAME");//名称
							String PART_NUM = rs1.getString("PART_NUM");//编号
							String DYSXL = rs1.getString("DYSXL");//电压上线越线率（%）
							String DYXXL = rs1.getString("DYXXL");//电压下线越线率（%）
							String XEDLL = rs1.getString("XEDLL");//电流越线率（%）
							String JLID = rs1.getString("JLID");//计量表计ID

					
							jo.put("PART_ID", PART_ID);
							jo.put("PART_NAME", PART_NAME);
							jo.put("PART_NUM", PART_NUM);
							jo.put("DYSXL", DYSXL);
							jo.put("DYXXL", DYXXL);
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
					continue;
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

	//获取高压配电房信息
public void getGYMuBarPDRoomInfo(JSONArray json) throws SQLException
{
	JSONObject TheRoom = new JSONObject();
	TheRoom.put("PART_ID", "0");
	TheRoom.put("RoomName", "10KV母线电压监控");
	JSONArray LinesInfo=new JSONArray();
	Connection conn1=null;
	PreparedStatement ps1 =null;
	ResultSet rs1 = null;
	try
	{
		String sql = "select PART_ID from PD_PARTINFO01 where LOWER(DYLEVEL)='10kv' or LOWER(DYLEVEL)='10000v'";
		conn1 = ConnDB.getConnection();
		ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs1 = ps1.executeQuery();			
		while (rs1.next())
		{
		
			BindLineInfo(LinesInfo,rs1.getString("PART_ID"),10,"kv");
		}
	} 
	catch (Exception e)
	{
		e.printStackTrace();
	}finally{
		ConnDB.release(conn1, ps1, rs1);
	}
	
		//绑定当前线电压值
	BindXianDY(LinesInfo);

		TheRoom.put("Data", LinesInfo);
		
		json.add(TheRoom) ;
		
	}

public void BindXianDY(JSONArray LinesInfo)throws SQLException{
	Connection conn1=null;
	PreparedStatement ps1 =null;
	ResultSet rs1 = null;
	
	 Object[] obj=	LinesInfo.toArray();  
	 	 for(int i=0;i<obj.length;i++){
		 JSONObject jo = JSONObject.fromObject(obj[i]);
		 
		 	String	 JLID= jo.getString("JLID");
	
        if(!JLID.equals("") && !JLID.equals("0") )
        {
        	String sqlString="select VALUETIME,VOLTAGEA from (select VALUETIME,VOLTAGEA from ZPDDATAS"+JLID+" order by VALUETIME DESC)T where rownum=1";
        	try
    		{
        		conn1 = ConnDB.getConnection();
    			ps1 = conn1.prepareStatement(sqlString,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    			rs1 = ps1.executeQuery();
    			if (rs1.next())
    			{
    				double	VOLTAGEA=  rs1.getDouble("VOLTAGEA");
        			//将相电压换算成线电压
        				VOLTAGEA=VOLTAGEA*1.732/1000;
        				
        				
    				jo.put("VALUETIME",  rs1.getString("VALUETIME"));
    			
            		jo.put("VOLTAGEA", String.format("%.2f",VOLTAGEA));
            	}
    		
    		}
        	catch (NumberFormatException e)
    		{
    			e.printStackTrace();
    		}finally{
    			ConnDB.release(conn1, ps1, rs1);
    		}
        }else {
        		jo.put("VALUETIME", "");
        		jo.put("VOLTAGEA", "");
		}
        LinesInfo.set(i, jo);
     }  


	
}
//获取低压配电房信息
public void getDYMuBarPDRoomInfo (JSONArray json) throws SQLException
{
		
	
	//获取低压配电房
	String sql = "select PART_ID,PART_NUM,PART_NAME from PD_PART where PARTSTYLE_ID=01 "+
			"and PART_ID  not in(select PART_ID from PD_PARTINFO01 where LOWER(DYLEVEL)='10kv' or LOWER(DYLEVEL)='10000v')  order by PART_NAME";
	
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
			
			JSONObject TheRoom = new JSONObject();
			
			TheRoom.put("PART_ID", RoomPART_ID);
			TheRoom.put("RoomName",RoomPART_Name);
			JSONArray LinesInfo=new JSONArray();
			BindLineInfo(LinesInfo,rs1.getString("PART_ID"),220,"v");
		
			 Object[] obj=	LinesInfo.toArray();  
			 
			 if(obj.length >0)//只显示有低压回路的配电房
			 {
				//绑定当前相电压值
				BindXiangDY(LinesInfo);
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

public void BindXiangDY(JSONArray LinesInfo)throws SQLException{
	Connection conn1=null;
	PreparedStatement ps1 =null;
	ResultSet rs1 = null;
	
	 Object[] obj=	LinesInfo.toArray();  
	 	 for(int i=0;i<obj.length;i++){
		 JSONObject jo = JSONObject.fromObject(obj[i]);
		 
		 	String	 JLID= jo.getString("JLID");
	
        if(!JLID.equals("") && !JLID.equals("0") )
        {
        	String sqlString="select VALUETIME,VOLTAGEA,VOLTAGEB,VOLTAGEC from (select VALUETIME,VOLTAGEA,VOLTAGEB,VOLTAGEC from ZPDDATAS"+JLID+" order by VALUETIME DESC)T where rownum=1";
        	try
    		{
        		conn1 = ConnDB.getConnection();
    			ps1 = conn1.prepareStatement(sqlString,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    			rs1 = ps1.executeQuery();
    			if (rs1.next())
    			{
    			
    				jo.put("VALUETIME",  rs1.getString("VALUETIME"));
    			
            		jo.put("VOLTAGEA",  rs1.getString("VOLTAGEA"));
            		jo.put("VOLTAGEB",  rs1.getString("VOLTAGEB"));
            		jo.put("VOLTAGEC",  rs1.getString("VOLTAGEC"));
            	}
    		
    		}
        	catch (NumberFormatException e)
    		{
    			e.printStackTrace();
    		}finally{
    			ConnDB.release(conn1, ps1, rs1);
    		}
        }else {
        		jo.put("VALUETIME", "");
        		jo.put("VOLTAGEA", "");
        		jo.put("VOLTAGEB", "");
        		jo.put("VOLTAGEC", "");
		}
        LinesInfo.set(i, jo);
     }  

	
	
	//LinesInfo
	//LinesInfo
	//if(TheRoom.)
	//String JLID=TheRoom.getString("JLID");
	
}




}
