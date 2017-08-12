package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.prepayment.model.APTuiModel;
import com.sf.energy.util.ConnDB;

public class APTuiDao
{

	public APTuiModel queryInfo(String amMeter_ID, String saleInfoNum) throws SQLException
	{
		APTuiModel model =null;
		String sql ="select AmMeter_ID,AmMeter_Num,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName,a.Price_ID,b.Price_Num,Price_Name,Price_Value,nvl(Beilv,1)Beilv from (AmMeter)a left join (Price)b on a.Price_ID=b.Price_ID where AmMeter_ID="+amMeter_ID ;

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
				if(model==null)
					model= new APTuiModel();
				model.setAmMeter_ID(amMeter_ID);
				model.setAmMeter_Num(rs.getString("AmMeter_Num"));
				model.setAmMeter_Name(rs.getString("AmMeter_Name"));
				model.setAmMeter_485Address(rs.getString("AmMeter_485Address"));
				model.setConsumerNum(rs.getString("ConsumerNum"));
				model.setConsumerName(rs.getString("ConsumerName"));
				model.setPrice_ID(rs.getString("Price_ID"));
				model.setPrice_Num(rs.getString("Price_Num"));
				model.setPrice_Name(rs.getString("Price_Name"));
				model.setPrice_Value(rs.getString("Price_Value"));
				model.setBeilv(rs.getString("Beilv"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}


		sql="select SaleInfoNum,Times,Kind,QSYValue,HSYValue,to_char(BuyTime,'yyyy-mm-dd hh24:mi:ss')BuyTime,to_char(SendTime,'yyyy-mm-dd hh24:mi:ss')SendTime,Status,Price,TheGross,TheMoney from APSaleInfo where SaleInfoNum="+saleInfoNum;
		Connection conn1=null;
		PreparedStatement ps1 =null;
		ResultSet rs1 = null;

		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs1 = ps1.executeQuery();			
			while(rs1.next())
			{
				if(model==null)
					model= new APTuiModel();
				model.setSaleInfoNum(saleInfoNum);
				model.setTIMES(rs1.getString("Times"));
				model.setKind(rs1.getString("Kind"));
				model.setQSYValue(rs1.getString("QSYValue"));
				model.setHSYValue(rs1.getString("HSYValue"));
				model.setBuyTime(rs1.getString("BuyTime"));
				model.setSendTime(rs1.getString("SendTime"));
				model.setStatus(rs1.getString("Status"));
				model.setPrice(rs1.getString("Price"));
				model.setTheGross(rs1.getString("TheGross"));
				model.setTheMoney(rs1.getString("TheMoney"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}

//		while (rs.next())
//		{
//			if(model==null)
//				model= new APTuiModel();
//			model.setSaleInfoNum(saleInfoNum);
//			model.setTIMES(rs.getString("Times"));
//			model.setKind(rs.getString("Kind"));
//			model.setQSYValue(rs.getString("QSYValue"));
//			model.setHSYValue(rs.getString("HSYValue"));
//			model.setBuyTime(rs.getString("BuyTime"));
//			model.setSendTime(rs.getString("SendTime"));
//			model.setStatus(rs.getString("Status"));
//			model.setPrice(rs.getString("Price"));
//			model.setTheGross(rs.getString("TheGross"));
//			model.setTheMoney(rs.getString("TheMoney"));
//		}

		return model;
	}

}
