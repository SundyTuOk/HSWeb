package com.sf.energy.weixin.methods;

import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.weixin.dao.weixinDao;
import com.sf.energy.weixin.models.refundModel;
import com.sf.energy.weixin.models.weixinParameters;
import com.sf.energy.weixin.utils.ClientCustomSSL;
import com.sf.energy.weixin.utils.ParametersUtil;
import com.sf.energy.weixin.utils.RequestHandler;
import com.sf.energy.weixin.utils.WeiXinOperatorUtil;

public class refund
{
	private static final weixinParameters wp = weixinParameters.getInstance();
	private static final String url="https://api.mch.weixin.qq.com/secapi/pay/refund"; 

	/**
	 * -1:程序运行异常，退款申请失败；
	 *	1:订单存在且支付成功，退款申请成功；
	 *	0:订单存在且支付成功，退款申请成功，但数据库更新失败；
	 * 	2:订单存在且支付成功，退款申请失败；
	 * @param rModel
	 * @return flag
	 */
	public static int doRefund(refundModel rModel){
		weixinDao wDao = new weixinDao();
		try
		{
			String arr = WeChatRefound(rModel.getOrderNo(), rModel.getOut_refund_no(), 
					rModel.getOrderMoney(), rModel.getOrderMoney());
			if(arr.indexOf("result_code")!=-1){
				JSONArray jarray = JSONArray.fromObject(arr);
				JSONObject obj = jarray.getJSONObject(1);
				if(obj.getString("result_code").equals("SUCCESS")){//退款申请成功
					rModel.setOut_refund_no(obj.getString("out_refund_no"));
					rModel.setRefundNo(obj.getString("refund_id"));
					rModel.setRefundMoney(obj.getString("refund_fee"));
					rModel.setUserName(new UsersDao().queryById(rModel.getUserId()).getUsersName());
					if(wDao.updateRefundInfo(rModel)){//退款申请成功且更新数据库成功 并将flag改为1 表示退款申请成功
						return 1;
					}else{//退款申请成功但更新数据库失败
						return 0;
					}
				}else if(obj.getString("result_code").equals("FAIL")){//订单存在且已支付,但单退款申请失败
					return 2;
				}
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			return -1;
			//e.printStackTrace();
		}
		return -1;
	}




	/**
	 * 
	 * @param transaction_id 微信支付订单号	
	 * @param out_refund_no 商户自定义唯一商户退款单号	
	 * @param total_fee 订单总金额
	 * @param refund_fee 退款金额
	 * @return
	 * @throws Exception
	 */
	public static String WeChatRefound(String transaction_id,String out_refund_no,String total_fee,String refund_fee
			) throws Exception {
		String appid = wp.getAppid();
		String appsecret = wp.getAppsecret();
		String mch_id = wp.getMch_id();//邮件里给的
		String pkey = wp.getKey();//商户平台里自己设的密钥
		String apiclient_cert = wp.getApiclient_cert();
		//		String total_fee = "1";// 总金额
		//		String refund_fee = "1";// 退款金额
		//		String out_refund_no = "0";// 退款单号
		String xmlParam = getxmlParam(appid, mch_id, appsecret, pkey, transaction_id
				,total_fee,refund_fee,out_refund_no);
		String resultXML = ClientCustomSSL.doRefund(url, xmlParam,
				apiclient_cert, mch_id);
		String rString=WeiXinOperatorUtil.resultDataOperator(resultXML);
		//System.out.println(rString);
		return rString;
	}
	private static String getxmlParam(String appid, String mch_id,String appsecret, String pkey,String transaction_id
			,String total_fee,String refund_fee,String out_refund_no){
		String nonce_str = ParametersUtil.getNonce_str();
		//String total_fee = "1";// 总金额
		//String refund_fee = "1";// 退款金额
		//String out_refund_no = "0";// 退款单号
		String op_user_id = mch_id;//就是MCHID
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_refund_no", out_refund_no);                //填入退款订单号
		packageParams.put("total_fee", total_fee);               //填入总金额
		packageParams.put("refund_fee", refund_fee); 
		packageParams.put("op_user_id", mch_id); 
		packageParams.put("transaction_id", transaction_id);
		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, pkey);
		String sign = reqHandler.createSign(packageParams);
		String xmlParam = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<transaction_id>" + transaction_id + "</transaction_id>"
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
				+ "<total_fee>" + total_fee + "</total_fee>"
				+ "<refund_fee>" + refund_fee + "</refund_fee>"
				+ "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		return xmlParam;
	}
	public static void main(String[] args)
	{
		String str = null;
		try
		{
			str = WeChatRefound("4007862001201608070784641772","0","1","1");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(str);
	}
}
