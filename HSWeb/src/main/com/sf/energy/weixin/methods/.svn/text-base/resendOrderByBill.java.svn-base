package com.sf.energy.weixin.methods;

import java.util.HashMap;
import java.util.Map;

import com.sf.energy.prepayment.dao.SaleDetailsDao;
import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.weixin.service.orderQueryService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class resendOrderByBill
{
	/**
	 * 
	 * @param datestr 20160816
	 * @param type : ALL SUCCESS REFUND
	 * @return
	 */
	public static Map<String, Integer> resendOrder(String datestr,String type){
		Map<String, Integer> rmap = new HashMap<String, Integer>();
		int success = 0;
		int ammetererror = 0;
		int weixinerror = 0;
		int refunderror = 0;
		int ordererror = 0;
		int error = 0;
		String str = downloadbill.billQuery(datestr,type);
		if(str.indexOf("No Bill Exist")!=-1){
			return null;
		}
		JSONArray array = JSONArray.fromObject(str);
		JSONArray datas = array.getJSONArray(1);
		System.out.println(datestr+" 总共"+datas.size()+"条数据");
		for(int i =0;i<datas.size();i++){
			System.out.println("正在处理第:"+(i+1));
			JSONObject obj = datas.getJSONObject(i);
			String state = obj.getString("交易状态");
			if(!state.equals("REFUND")){
				String orderNo = obj.getString("微信订单号");
				SaleDetailsDao sDao = new SaleDetailsDao();
				SaleDetailsModel model = sDao.queryDetailByOrderNo(orderNo);
				if(model==null){
					String ammeterId = obj.getString("商户数据包");
					int flag = new orderQueryService().insert_new_weixinOder_Service
							(Integer.parseInt(ammeterId), orderNo, "单订补发");
					if(flag==2){
						success++;
					}else if(flag == -4){
						ammetererror++;
					}else if(flag==0){
						weixinerror++;
					}else{					
						error++;
					}
				}else{
					ordererror++;
				}				
			}else{
				refunderror++;
			}
		}
		rmap.put("success", success);
		rmap.put("ammetererror", ammetererror);
		rmap.put("weixinerror", weixinerror);
		rmap.put("refunderror", refunderror);
		rmap.put("ordererror", ordererror);
		rmap.put("error", error);
		
		return rmap;
	}
	public static Map<String, Integer> addMap(Map<String, Integer> m1,Map<String, Integer>m2){
		if(m2==null){
			return m1;
		}else if(m1==null){
			return m2;
		}
		m1.put("success", m1.get("success")+m2.get("success"));
		m1.put("ammetererror", m1.get("ammetererror")+m2.get("ammetererror"));
		m1.put("weixinerror", m1.get("weixinerror")+m2.get("weixinerror"));
		m1.put("refunderror", m1.get("refunderror")+m2.get("refunderror"));
		m1.put("ordererror", m1.get("ordererror")+m2.get("ordererror"));
		m1.put("error", m1.get("error")+m2.get("error"));
		return m1;
	}
}
