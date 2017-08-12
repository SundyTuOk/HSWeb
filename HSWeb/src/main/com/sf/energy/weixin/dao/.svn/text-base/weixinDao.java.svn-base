package com.sf.energy.weixin.dao;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import oracle.sql.CLOB;

import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.util.ConnDB;
import com.sf.energy.weixin.models.billModel;
import com.sf.energy.weixin.models.orderModel;
import com.sf.energy.weixin.models.refundModel;

public class weixinDao
{
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 *  向billdata表中插入一条记录
	 *  记录格式为
	 *  20160714  json长字符串  对账单状态
	 * @param str
	 * @return
	 */
	public boolean insertOneRecord(String date,String str){
		boolean b = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO WEIXINBILL (BILL_DATE, BILL_STATE, BILLDATAS) "
				+ "VALUES (?, ?, ?)";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			ps.setInt(2, 0);
			ps.setString(3, str);
			b=(ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps);
		}
		return b;
	}

	public billModel getDayBill(String date){
		billModel model = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM	WEIXINBILL WHERE BILL_DATE = ?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			rs = ps.executeQuery();
			if(rs.next()){
				model = buildInstance(rs);
				//System.out.println(model);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps,rs);
		}
		return model;
	}
	public boolean deleteDayBill(String date){
		boolean b = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM WEIXINBILL WHERE BILL_DATE=?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			b=(ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps);
		}
		return b;
	}

	/**
	 * 根据flag 查询 minute 分钟前的数据
	 * @param flag
	 * @param minute
	 * @return
	 */
	public List<orderModel> queryAllWeixinInfoByFlagAndBeforTime(int flag,int minute){
		List<orderModel> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Date nowDate = new Date();
		Date queryDate = new Date(nowDate.getTime()-(minute*60*1000));
		String datestr = df.format(queryDate);
		String sql = "SELECT * FROM WEIXINORDERINFO WHERE ORDER_FLAG = ? and order_date<=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, flag);
			ps.setString(2, datestr);
			rs = ps.executeQuery();
			while (rs.next())
			{
				orderModel model = buildOrder(rs);
				if(model!=null){
					if(list==null){
						list = new ArrayList<orderModel>();
					}
					list.add(model);
				}

			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps,rs);
		}
		return list;
	}

	public List<orderModel> queryAllWeixinInfoByFlag(int flag){
		List<orderModel> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM WEIXINORDERINFO WHERE ORDER_FLAG = ?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, flag);
			rs = ps.executeQuery();
			while (rs.next())
			{
				orderModel model = buildOrder(rs);
				if(model!=null){
					if(list==null){
						list = new ArrayList<orderModel>();
					}
					list.add(model);
				}

			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps,rs);
		}
		return list;
	}

	/**
	 * 将数据库中所有flag为2的记录该为0
	 * @return
	 */
	public boolean updateWexinInfoFlagFromOldToNew(int oldflag,int newflag){
		boolean b = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE WEIXINORDERINFO SET ORDER_FLAG = ? WHERE ORDER_FLAG =?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newflag);
			ps.setInt(2, oldflag);
			b=(ps.executeUpdate()>=1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps);
		}
		return b;
	}

	public boolean updateWexinInfoFlagById(int Id,int flag){
		boolean b = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE WEIXINORDERINFO SET ORDER_FLAG = ? WHERE Id =?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, flag);
			ps.setInt(2, Id);
			b=(ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps);
		}
		return b;
	}

	public boolean deleteWexinInfoById(int Id){
		boolean b = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM WEIXINORDERINFO WHERE Id = ?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Id);
			if(ps.executeUpdate()==1);
			b=true;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps);
		}
		return b;
	}

	public boolean clearWexinInfo(){
		boolean b = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM WEIXINORDERINFO WHERE ORDER_FLAG = 0";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			if(ps.executeUpdate()>=1);
			b=true;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps);
		}
		return b;
	}
	public int queryAmmeterIdByOrderNo(String orderNo){
		int ammeterId = 0;
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select ammeter_id from weixinorderinfo where order_no=?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setString(1, orderNo);
			rs = ps.executeQuery();
			if(rs.next()){
				ammeterId = rs.getInt("ammeter_id");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps,rs);
		}
		return ammeterId;
	}
	public boolean updateWexinInfoBySaleInfo(SaleDetailsModel model){
		boolean b =false;

		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update weixinorderinfo set saleinfonum=? ,saleinfotime=?,ORDER_MONEY=?,ORDER_FLAG = 1 where order_no=?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setString(1, model.getSALEINFONUM());
			ps.setString(2, model.getBuyTime());
			ps.setString(3, model.getTheMoney());
			ps.setString(4, model.getORDERNO());
			b=(ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps);
		}
		return b;


	}

	public boolean updateWexinInfo(JSONObject object){
		boolean b =false;
		if(object.containsKey("out_trade_no")&&object.containsKey("transaction_id")){
			String infoId = object.getString("out_trade_no");
			String orderNo = object.getString("transaction_id");
			Connection conn = null;
			PreparedStatement ps = null;
			String sql = "update weixinorderinfo set ORDER_NO=? ,ORDER_FLAG = 2 where ID=?";
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);			
				ps.setString(1, orderNo);
				ps.setInt(2, Integer.parseInt(infoId));				
				b=(ps.executeUpdate()==1);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn,ps);
			}
			return b;
		}
		return b;
	}
	public orderModel queryWixinInfo(int id){
		orderModel model = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from weixinorderinfo where ID=?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				model = buildOrder(rs);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps,rs);
		}
		return model;
	}
	/**
	 * 退款业务
	 */
	public int insertRefundInfo(refundModel model){
		int Id=0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "INSERT INTO WEIXINREFUNDINFO (OUT_TRADE_NO, ORDER_NO,ORDER_MONEY,"
				+ "ORDER_DATE,AMMETER_ID, AMMETER_NAME,SALEINFONUM,SALEINFOTIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);			
			ps.setString(1, model.getOut_trade_no());
			ps.setString(2, model.getOrderNo());
			ps.setString(3, model.getOrderMoney());
			ps.setString(4, model.getOrderDate());
			ps.setInt(5, model.getAmmeterId());
			ps.setString(6, model.getAmmeterName());
			ps.setString(7, model.getSaleInfoNum());
			ps.setString(8, model.getSaleInfoTime());
			ps.executeUpdate();
			Id = queryRefundIdByOrderNo(model.getOrderNo());
//			rs = ps.getGeneratedKeys();
//			if(rs.next())
//			{
//				Id = rs.getInt(1);
//			}
			
		} catch (SQLException e)
		{	
			
			e.printStackTrace();
		}finally{
			
			ConnDB.release(conn,ps,rs);
		}
		return Id;	
	}
	
	public boolean updateRefundInfoFlag(int Id,int newFlag){
		boolean b =false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE WEIXINREFUNDINFO SET REFUND_FLAG=? WHERE Id = ?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setInt(1, newFlag);
			ps.setInt(2, Id);	
			b=(ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps);
		}
		return b;
	}
	public boolean updateRefundInfoFlagAndUserId(String orderNo ,int newFlag,int userId){
		boolean b =false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE WEIXINREFUNDINFO SET REFUND_FLAG=? , USER_ID = ?, USER_NAME=? ,REFUND_DATE=? WHERE ORDER_NO = ?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setInt(1, newFlag);
			ps.setInt(2, userId);
			ps.setString(3, new UsersDao().queryById(userId).getUsersName());
			ps.setString(4, df.format(new Date()));
			ps.setString(5, orderNo);	
			b=(ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps);
		}
		return b;
	}
	
	public boolean updateRefundInfo(refundModel model){
		boolean b =false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE WEIXINREFUNDINFO SET OUT_TRADE_NO=?, OUT_REFUND_NO=?, "
				+ "REFUND_ID=?, REFUND_MONEY=?, REFUND_DATE=?, REFUND_FLAG=?, USER_ID=? ,USER_NAME=? WHERE ORDER_NO = ?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setString(1, model.getOut_trade_no());
			ps.setString(2, model.getOut_refund_no());
			ps.setString(3, model.getRefundNo());
			ps.setString(4, model.getRefundMoney());
			ps.setString(5, df.format(new Date()));
			ps.setInt(6, 1);//将flag设置为1表示该订单退款申请成功
			ps.setInt(7, model.getUserId());
			ps.setString(8, model.getUserName());
			ps.setString(9, model.getOrderNo());
			b=(ps.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps);
		}
		return b;
	}

	public int queryRefundIdByOrderNo(String orderNo){
		int Id = 0;
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select id from WEIXINREFUNDINFO where order_no=?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setString(1, orderNo);
			rs = ps.executeQuery();
			if(rs.next()){
				Id = rs.getInt("id");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps,rs);
		}
		return Id;
	}
	public refundModel queryRefundModelByOrderNo(String orderNo){

		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		refundModel model = null;
		String sql = "select * from WEIXINREFUNDINFO where ORDER_NO=?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setString(1, orderNo);
			rs = ps.executeQuery();
			if(rs.next()){
				model = buildRefundModel(rs);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps,rs);
		}
		return model;
	}
	public refundModel queryRefundModelById(int Id){

		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		refundModel model = null;
		String sql = "select * from WEIXINREFUNDINFO where Id=?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setInt(1, Id);
			rs = ps.executeQuery();
			if(rs.next()){
				model = buildRefundModel(rs);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps,rs);
		}
		return model;
	}

	private orderModel buildOrder(ResultSet rs) throws SQLException{
		orderModel model = null;		
		if(rs.getString("AMMETER_ID")==null||rs.getString("AMMETER_ID").equals(" "))
			return null;
		else{
			model = new orderModel();
			model.setId(rs.getInt("ID"));
			model.setAmmeterId(rs.getInt("AMMETER_ID"));
			model.setOrder_No(rs.getString("ORDER_NO"));
			model.setSaleInfoNum(rs.getString("SaleInfoNum"));
		}
		return model;
	}

	private billModel buildInstance(ResultSet rs) throws SQLException{
		billModel model = new billModel();
		model.setDate(rs.getString("BILL_DATE"));
//		CLOB clob = (CLOB) rs  
//				.getClob("BILLDATAS");  
		//String value = clob.getSubString(1, (int) clob.length());  
		//model.setDatas(clob.getSubString(1, (int) clob.length()));
		model.setDatas(rs.getString("BILLDATAS"));
		model.setState(rs.getInt("BILL_STATE"));
		return model;
	}

	private refundModel buildRefundModel(ResultSet rs) throws SQLException{
		refundModel model = new refundModel();
		model.setId(rs.getInt("Id"));
		model.setOut_trade_no(rs.getString("OUT_TRADE_NO"));
		model.setOrderNo(rs.getString("ORDER_NO"));
		model.setOut_refund_no(rs.getString("OUT_REFUND_NO"));
		model.setRefundNo(rs.getString("REFUND_ID"));
		model.setOrderMoney(rs.getString("ORDER_MONEY"));
		model.setRefundMoney(rs.getString("REFUND_MONEY"));
		model.setOrderDate(rs.getString("ORDER_DATE"));
		model.setRefundDate(rs.getString("REFUND_DATE"));
		model.setRefundFlag(rs.getInt("REFUND_FLAG"));
		model.setAmmeterId(rs.getInt("AMMETER_ID"));
		model.setAmmeterName(rs.getString("AMMETER_NAME"));
		model.setSaleInfoNum(rs.getString("SALEINFONUM"));
		model.setSaleInfoTime(rs.getString("SALEINFOTIME"));
		model.setUserId(rs.getInt("USER_ID"));
		model.setUserName(rs.getString("USER_NAME"));
		return model;
	}
}
