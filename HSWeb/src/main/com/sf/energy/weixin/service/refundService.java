package com.sf.energy.weixin.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.http.cookie.SM;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.prepayment.dao.SaleDetailsDao;
import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.weixin.dao.weixinDao;
import com.sf.energy.weixin.methods.OrderQuery;
import com.sf.energy.weixin.methods.refund;
import com.sf.energy.weixin.methods.refundQuery;
import com.sf.energy.weixin.models.orderQueryHideMap;
import com.sf.energy.weixin.models.orderQueryMap;
import com.sf.energy.weixin.models.refundModel;
import com.sf.energy.weixin.utils.MapUtils;

public class refundService
{
	private DateFormat df24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * flag	
	 * 	   
	 * 		2:订单存在且支付成功，退款申请失败；	
	 *		1:订单存在且支付成功，退款申请成功；
	 *		0:订单存在且支付成功，退款申请成功，但数据库更新失败；
	 *	   -1:程序运行异常，退款申请失败；
	 * 	   -2:订单已提交退款申请，无需重复申请；
	 *     -3:购电单已生成，无法申请退款；
	 * 	   -4:微信订单查询异常，退款申请失败；
	 * 	   -5:订单不存在，无法申请退款；
	 * 
	 * 		
	 *    
	 * @param orderNo userId
	 * @return flag
	 */
	public int refundOrder_Service(String orderNo,int userId) throws ParseException{
		String arr = OrderQuery.orderQueryV(orderNo);	//先根据订单号查询订单号详细信息			
		if(arr.indexOf("result_code")!=-1){//查询结果可能为{} 正常返回结果中一定会有result_code字段
			JSONArray array = new JSONArray().fromObject(arr);
			JSONObject object= array.getJSONObject(1);
			if(object.getString("result_code").equals("SUCCESS")){//查询结果正常	
				weixinDao wDao = new weixinDao();
				if(object.getString("trade_state").equals("SUCCESS")){//订单存在且已支付
					int Id = wDao.queryRefundIdByOrderNo(orderNo);//先查询退款订单表中是否有该订单信息
					if(Id==0){//订单未提交过退款申请
						SaleDetailsModel sModel = new SaleDetailsDao().queryDetailByOrderNo(orderNo);
						if(sModel!=null){//若微信购电单已生成 则不允许直接退款
							return -3;
						}
						/**
						 * 提交退款申请前先插入一些基本信息
						 * 交易单号，商户交易单号，交易金额（分为单位），交易完成时间等
						 */
						refundModel rModel = new refundModel();
						rModel.setOrderNo(orderNo);
						rModel.setOut_trade_no(object.getString("out_trade_no"));
						rModel.setOrderMoney(object.getString("total_fee"));
						rModel.setOrderDate(df24.format(df.parse(object.getString("time_end"))));
						rModel.setAmmeterId(Integer.parseInt(object.getString("attach")));
						try
						{
							rModel.setAmmeterName(new AmmeterDao().queryNameById(Integer.parseInt(object.getString("attach"))));
						} catch (Exception e1)
						{
							e1.printStackTrace();
							return -1;
						} 
						rModel.setUserId(userId);
						int out_refund_no = wDao.insertRefundInfo(rModel);
						/**
						 * 提交退款申请前先将订单号等信息插入数据库并返回生成的主键Id
						 * 将Id作为商户退款单号发送给微信服务器
						 */
						if(out_refund_no!=0){//插入成功，返回Id
							try{
								//开始申请退款  先将flag改为2 表示该订单正在进行退款处理
								if(wDao.updateRefundInfoFlag(out_refund_no, 2)){
									rModel.setOut_refund_no(String.valueOf(out_refund_no));
									return refund.doRefund(rModel);
								}else{
									return -1;
								}
							}catch (Exception e){
								e.printStackTrace();
								return -1;
							}
						}else{//out_refund_no==0表示第一次插入数据库失败
							return -1;
						}
					}else{//该订单已经提交过退款申请
						refundModel rModel = wDao.queryRefundModelById(Id);
						if(rModel.getRefundFlag()==1){
							return -3;
						}else{
							return refund.doRefund(rModel);
						}						
					}
				}else if (object.getString("trade_state").equals("REFUND")) {//订单已经申请退款
					refundModel rModel = wDao.queryRefundModelByOrderNo(orderNo);
					if(rModel.getRefundFlag()==2){
					
						if(wDao.updateRefundInfoFlagAndUserId(orderNo,1,userId)){
							return 1;
						}else{
							return 0;
						}
					}else{
						return -2;
					}
				}
			}else{
				return -4;
			}
		}
		return -4;
	}
	public String refund_Query_By_OrderNo_Service(String transaction_id){
		String str = null;
		str = refundQuery.refundQuery(transaction_id);
		//System.out.println("查询结果:"+str);
		//str = doubleCheckOrderNoReturn(transaction_id,str);
		//str = WeiXinOperatorUtil.ckeckResultState(str);
		str = MapUtils.hideStrByMap(new orderQueryHideMap().getMap(), str);
		str = MapUtils.specialDeal(str);
		//这一步一定要放在最后
		str = MapUtils.translateStrByMap(new orderQueryMap().getMap(), str);
		//System.out.println("订单查询结果:"+str);
		return str;
	}
	public static void main(String[] args)
	{
		String str = new refundService().refund_Query_By_OrderNo_Service("4007862001201608070782982818");
		System.out.println(str);
	}
}
