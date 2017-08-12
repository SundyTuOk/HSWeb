package com.sf.energy.powernet.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.util.ConnDB;

/**
 * 配网中回路falsh相关方法
 * @author yanhao
 *
 */
public class PowerNetLineInfo
{
	/**
	 * 已得到的报警信息组成flash中as所需要的格式
	 * <SFROOT gy='GW'>
	 *	<GW opercode='-1' tasknum='' isend='1'>
	 *	<commandback code='alarm' error='0' errormessage=''>
	 *		<result name='' value='000000000001,2013-05-07 17:58:00;0.0899,0.0000,总有功功率越上限;0.0899,0.0000,A相有功功率越上限;0,100.0000,B相电压越下限;0.056,0.0000,当前B相电流越上限;0.009,0.0000,当前C相电流越上限;'/>
	 *		<result name='' value='000000000002,2013-05-07 17:58:00;81.7,219.0000,A相电压越下限;0,219.0000,B相电压越下限;0,219.0000,C相电压越下限;'/>
     *		<result name='' value='000000000003,2013-05-07 17:58:00;24.6,219.0000,A相电压越下限;0,219.0000,B相电压越下限;0,219.0000,C相电压越下限;'/>
	 *		<result name='' value='000000000091,2013-05-07 17:58:00;0,219.0000,A相电压越下限;0,219.0000,B相电压越下限;0,219.0000,C相电压越下限;198,50.0000,当前A相电流越上限;'/>
	 *	</commandback>
	 *  </GW>
	 *	</SFROOT>  
	 *	
	 * @return
	 */
	public String getAlarmString()
	{
		return null;
	}
	
	/**
	 * 通过设备编号查询出箭头跳转的flash
	 * @param objName 设备ID
	 * @param style 向上goUp或者向下
	 * @return
	 * @throws SQLException 
	 */
	public String getGotoString(String objName,String style) throws SQLException
	{
		String value="";
		String sql="";
		if(style.equalsIgnoreCase("goUp"))//查询上一级节点（全校）
		{
			sql="select Part_ID,PartStyle_ID,Part_Name from PD_Part a, (select Part_Parent from PD_Part where Part_Num='" + objName + "')t where a.Part_ID=t.Part_Parent";
		}
		else//查询下一级节点（配电房）
        {
             sql = "select Part_ID,PartStyle_ID,Part_Name from PD_Part where Part_Num='" + objName + "'";
        }
			
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if(rs.next())//goUp='270,30,0*All*华中师范大学'
			{
				value = objName + "‖" + style + "‖" +rs.getString("Part_ID") + "*" +rs.getString("PartStyle_ID")  + "*" + rs.getString("Part_Name");	
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if(rs.next())//goUp='270,30,0*All*华中师范大学'
//		{
//			value = objName + "‖" + style + "‖" +rs.getString("Part_ID") + "*" +rs.getString("PartStyle_ID")  + "*" + rs.getString("Part_Name");	
//		}

		
 		return value;
	}
}
