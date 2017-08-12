package com.sf.energy.weixin.models;

import java.util.HashMap;
import java.util.Map;

/**
 * 这些信息在数据查询出来时会被隐藏掉，不返回前端
 * @author Administrator
 *
 */
public class orderQueryHideMap
{
	private Map<String, String> map;
	
	public orderQueryHideMap(){
		map = new HashMap<String, String>();
		map.put("appid", "公众账号ID");
		map.put("mch_id", "商户号");
		map.put("nonce_str", "随机字符串");
		map.put("sign", "签名");
		map.put("openid", "用户标识");
		
		map.put("cash_fee_type", "现金支付货币类型");
		map.put("coupon_fee", "代金券金额");
		map.put("coupon_count", "代金券使用数量");
		
		map.put("refund_fee_", "退款金额");
		map.put("coupon_batch_id_", "代金券批次ID");
		map.put("coupon_type_", "代金券类型");
		map.put("coupon_id_", "代金券ID");
		map.put("coupon_fee_", "单个代金券支付金额");
		map.put("settlement_total_fee", "应结订单金额");
	}
	public Map<String, String> getMap()
	{
		return map;
	}
}
