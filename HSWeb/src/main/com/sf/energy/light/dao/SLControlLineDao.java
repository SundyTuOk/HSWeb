package com.sf.energy.light.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.light.model.SLControlLineModel;
import com.sf.energy.util.ConnDB;

public class SLControlLineDao {
		
	/**
	 * 查询回路所附属的信息
	 * @param lineId
	 * @return
	 * @throws SQLException
	 */
	public SLControlLineModel queryLineMes(String lineId) throws SQLException
	{
		SLControlLineModel slControlLineModel=new SLControlLineModel();
		String sql="select a.SLKONGZHI_INDEX,(case a.SLKongzhi_Index when 2 then b.Lamp_State1 when 1 then b.Lamp_State2 end) state,"
				+ "(case a.SLKongzhi_Index when 2 then b.LAMP_STATE11 when 1 then b.LAMP_STATE21 end) state1,"
				+ "b.SLKONGZHI_485ADDRESS,b.SLKONGZHI_PORT,c.GATHER_ADDRESS,c.DATASITE_ID from slline a,slkongzhi b,gather c  "
				+ "where a.SLKONGZHI_ID=b.SLKONGZHI_ID and b.SLGATHER_ID=c.GATHER_ID and a.SLLINE_ID="+lineId;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{		
				slControlLineModel.setSLKONGZHI_485ADDRESS(rs.getString("SLKONGZHI_485ADDRESS"));
				slControlLineModel.setSLKONGZHI_PORT(rs.getInt("SLKONGZHI_PORT"));
				slControlLineModel.setGather_Address(rs.getString("GATHER_ADDRESS"));
				slControlLineModel.setSLKONGZHI_INDEX(rs.getInt("SLKONGZHI_INDEX"));
				String state="";
				String state1="";
				state=rs.getString("state");//开或关 李嵘20151016
				state1=rs.getString("state1");//手动或自动
				if(state1!=""&&state1!=null){
					if (state1.equals("auto"))
					{
						slControlLineModel.setLineState(state1);
					}
					else
					{
						slControlLineModel.setLineState(state);
					}
				}
				slControlLineModel.setDatasiteID(rs.getString("DATASITE_ID"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return slControlLineModel;
	}
}
