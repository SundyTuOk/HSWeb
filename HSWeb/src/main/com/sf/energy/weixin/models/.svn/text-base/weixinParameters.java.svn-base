package com.sf.energy.weixin.models;

import java.io.IOException;
import java.util.Properties;
public class weixinParameters {
	private static weixinParameters wp;
	private static String appid;
	//private static String appkey;
	private static String appsecret;
	private static String key;
	private static String mch_id;
	private static String apiclient_cert;
	
	static{
		Properties prop = new Properties();
		try {
			//System.out.println("weixinParameters init!");
			prop.load(weixinParameters.class.getClassLoader().getResourceAsStream("weixin.properties"));
			appid = prop.getProperty("APPID");
			appsecret = prop.getProperty("APPSECRET");
			key = prop.getProperty("KEY");
			mch_id = prop.getProperty("MCHID");
			apiclient_cert = prop.getProperty("apiclient_cert");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private weixinParameters(){
		
	}
	public static weixinParameters getInstance(){
		//简单单例模式 不考虑并发安全等问题
		if(wp==null){
			wp = new weixinParameters();
		}
		return wp;
	}
	public String getAppid() {
		return appid;
	}
//	public void setAppid(String appid) {
//		this.appid = appid;
//	}
	
	public String getAppsecret() {
		return appsecret;
	}
//	public void setAppsecret(String appsecret) {
//		this.appsecret = appsecret;
//	}
	public String getKey() {
		return key;
	}
//	public void setKey(String key) {
//		this.key = key;
//	}
	
	public String getMch_id() {
		return mch_id;
	}
//	public void setMch_id(String mch_id) {
//		this.mch_id = mch_id;
//	}
	public  String getApiclient_cert()
	{
		return apiclient_cert;
	}
//	public static void setApiclient_cert(String apiclient_cert)
//	{
//		weixinParameters.apiclient_cert = apiclient_cert;
//	}
}
