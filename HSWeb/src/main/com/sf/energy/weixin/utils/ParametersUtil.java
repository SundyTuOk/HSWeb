package com.sf.energy.weixin.utils;

public class ParametersUtil {
	public static String getNonce_str() {
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String nonce_str = strTime + strRandom;
		
		return nonce_str;
	}
}
