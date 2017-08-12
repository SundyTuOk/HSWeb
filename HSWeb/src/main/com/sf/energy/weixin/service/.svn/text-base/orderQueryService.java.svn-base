package com.sf.energy.weixin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.prepayment.dao.SaleDetailsDao;
import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.weixin.dao.weixinDao;
import com.sf.energy.weixin.methods.OrderQuery;
import com.sf.energy.weixin.methods.resendOrder;
import com.sf.energy.weixin.models.orderModel;
import com.sf.energy.weixin.models.orderQueryHideMap;
import com.sf.energy.weixin.models.orderQueryMap;
import com.sf.energy.weixin.utils.MapUtils;
import com.sf.energy.weixin.utils.WeiXinOperatorUtil;

public class orderQueryService
{
	public String order_Query_By_OrderNo_Service(String transaction_id){
		String str = null;
		str = OrderQuery.orderQueryV(transaction_id);
		//System.out.println("查询结果:"+str);
		//str = doubleCheckOrderNoReturn(transaction_id,str);
		str = WeiXinOperatorUtil.ckeckResultState(str);
		str = MapUtils.hideStrByMap(new orderQueryHideMap().getMap(), str);
		str = MapUtils.specialDeal(str);
		//这一步一定要放在最后
		str = MapUtils.translateStrByMap(new orderQueryMap().getMap(), str);
		//System.out.println("订单查询结果:"+str);
		return str;
	}
	/**
	 * 根据订单查询返回结果中的订单号与查询输入订单号做比较
	 * 
	 * 微信订单号查询可能是模糊查询
	 * @param transaction_id
	 * @param str
	 * @return
	 */
	private String doubleCheckOrderNoReturn(String transaction_id,String str){
		if(doubleCheckOrderNo(transaction_id,str)){
			return str;
		}else{
			if(str.indexOf("transaction_id")!=-1){
				str = "[{'type':'object'},{'result_code':'FAIL','sign':'','mch_id':'','err_code':'ORDERNOTCONSISTENT','err_code_des':'订单查询有误','return_msg':'OK','appid':'','nonce_str':'','return_code':'SUCCESS'}]";
			}
			return str;
		}
	}
	
	private boolean doubleCheckOrderNo(String transaction_id,String str){		
		if(str.indexOf("transaction_id")!=-1){
			JSONArray arr = JSONArray.fromObject(str);
			JSONObject object = (JSONObject) arr.get(1);
			String query_transaction_id = object.getString("transaction_id");
			if(query_transaction_id.equals(transaction_id)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 重新插入订单服务
	 * @param orderNo
	 * @return
	 * 定义几种返回结果类型
	 * flag=-4:生成订单对象异常  485地址不存在,电表信息错误
	 * flag=-3:程序运行异常
	 * flag=-2:微信订单查询结果异常（不是订单不存在错误）
	 * flag=-1:微信订单不存在,不重新插入
	 * flag=0:订单存在且数据库中存在，不重新插入
	 * flag=1:订单存在但数据库中未插入,需要重新插入但插入未成功
	 * flag=2:订单存在但数据库中未插入,需要重新插入且插入成功
	 */
	public int insert_new_weixinOder_Service(int ammeterId,String transaction_id,String studendName){
		int flag = -3;
		String arrstr = order_Query_By_OrderNo_Service(transaction_id);
				
		if(arrstr.equals("")||arrstr==null||arrstr.indexOf("{}")!=-1){
			return -2;
		}
		//System.out.println(arrstr);
		JSONArray array = JSONArray.fromObject(arrstr);
		JSONObject obj = array.getJSONObject(1);
		String return_code = obj.getString("返回状态码");
		if(return_code.equals("FAIL")){
			return -2; 
		}
		String result_code = obj.getString("业务结果");
		if(result_code.equals("FAIL")||result_code==null||result_code.equals("")){//若微信订单查询结果出错
			String err_code = obj.getString("错误代码");
			if(err_code.equals("ORDERNOTEXIST")){
				return -1;
			}else{
				return -2;
			}
		}else{
			SaleDetailsDao sDao = new SaleDetailsDao();
			String orderNo = obj.getString("微信支付订单号");
			SaleDetailsModel sModel = sDao.queryDetailByOrderNo(orderNo);
			if(sModel==null){
				try
				{
					SaleDetailsModel insertModel = sDao.getInsertModel(ammeterId, arrstr);
					if(insertModel==null){
						return -4;
					}
					boolean b = sDao.insertWeixinOrder(insertModel,studendName);
					if(b){
						return 2;
					}else{
						return 1;
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				} 
			}else{
				return 0;
			}			
		}
		return flag;
	}
	
	public Map<String, Integer> check_And_Resend_Order_Service(){
		weixinDao wDao = new weixinDao();
		wDao.updateWexinInfoFlagFromOldToNew(2,0);
		Map<String, Integer> rmap  = new HashMap<String, Integer>();
		//int totalcount = 0;
		int insertcount = 0;
		int errorcount = 0;
		Map<String, Integer> map = resendOrder.updataWexinInfoDB();
		int sucesscount = map.get("sucesscount");
		int i = 1;
		if(sucesscount>0){		
			List<orderModel> list = wDao.queryAllWeixinInfoByFlag(2);
			SaleDetailsDao sDao = new SaleDetailsDao();
			for(orderModel n:list){
				System.out.println("正在生成购电单 第:"+(i++));
				int db_ammeterId = wDao.queryAmmeterIdByOrderNo(n.getOrder_No());
				if(db_ammeterId==0||n.getAmmeterId()!=db_ammeterId){//生成购电单前做一次电表id的校验
					errorcount++;
					continue;
				}
				int flag = insert_new_weixinOder_Service(n.getAmmeterId(), n.getOrder_No(),"批量处理异常");
				if(flag==2||flag==0){
					SaleDetailsModel sModel = sDao.queryDetailByOrderNo(n.getOrder_No());				
					if(sModel!=null){
						wDao.updateWexinInfoBySaleInfo(sModel);
						insertcount++;
					}else{
						errorcount++;
					}
				}else if(flag==-1){//微信订单不存在
					wDao.deleteWexinInfoById(n.getId());
					errorcount++;
				}else if(flag==-4){//表地址异常
					wDao.updateWexinInfoFlagById(n.getId(), -2);
					errorcount++;
				}
			}
		}
		rmap.put("totalcount", sucesscount);
		rmap.put("insertcount", insertcount);
		rmap.put("errorcount", errorcount);
		System.out.println("购电单处理完毕 "+ new Gson().toJson(rmap));
		return rmap;
	}
	
	/**
	 * 
	 */
	public void checkAndResendOrder(){
		
	}
}
