package com.sf.energy.weixin.methods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.weixin.dao.weixinDao;
import com.sf.energy.weixin.models.orderModel;

public class resendOrder
{
	/**
	 * 根据weixininfo表中的Id（即为out_trade_no）查询微信服务器中订单号,然后更新数据库
	 * 
	 * queryAllWeixinInfoByFlag 
	 *  flag：
	 *  0:表示异常订单
	 *  1:表示正常订单
	 *  2:表示正在处理的订单
	 *  -1:表示未支付的订单
	 *  -2:表示表地址异常订单
	 * @return
	 */
	public static Map<String, Integer> updataWexinInfoDB(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		int successcount = 0;
		int failcount = 0;
		int errorcount = 0;
		int notpaycount=0;
		int totalcount = 0;
		weixinDao wDao = new weixinDao();
		List<orderModel> list  = wDao.queryAllWeixinInfoByFlagAndBeforTime(0,5);
		if(list!=null){
			totalcount = list.size();
			int i = 1;
			for(orderModel n:list){
				System.out.println("正在更新weixininfo 第:"+(i++));
				String arr = OrderQueryByTradeNo.orderQueryByTradeNo(String.valueOf(n.getId()));				
				if(arr.indexOf("result_code")!=-1){//查询结果可能为{}
					JSONArray array = new JSONArray().fromObject(arr);
					JSONObject object= array.getJSONObject(1);
					if(object.getString("result_code").equals("SUCCESS")){						
						if(object.getString("trade_state").equals("SUCCESS")){
							if(wDao.updateWexinInfo(object)){
								successcount++;
							}else{
								failcount++;
							}
						}else if(object.getString("trade_state").equals("NOTPAY")){
							wDao.updateWexinInfoFlagById(n.getId(), -1);
							notpaycount++;
						}
					}else if(object.getString("err_code").equals("ORDERNOTEXIST")){
						wDao.deleteWexinInfoById(n.getId());
						errorcount++;
					}
				}
			}
		}
		System.out.println("正在更新weixininfo 处理完毕");
		map.put("sucesscount", successcount);
		map.put("failcount", failcount);
		map.put("errorcount", errorcount);
		map.put("notpaycount", notpaycount);
		map.put("totalcount", totalcount);
		return map;
	}

}
