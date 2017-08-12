package com.sf.energy.util;

import java.security.MessageDigest;


/**
 * MD5加密算法
 * 
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */
public class MD5Encryption
{
	/**
	 * MD5加密算法
	 * @param s 需要加密的字符串
	 * @return 加密以后的字符串
	 */
	public final static String MD5(String s)
	{
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try
		{
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++)
			{
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e)
		{
			return null;
		}
	}
	
	 public static void main(String[] args) 
	 { 
		 MD5Encryption md5Encryption=new MD5Encryption();

		 //System.out.println(md5Encryption.MD5("admin"));

		 //System.out.println(md5Encryption.MD5("123"));

	 }
	
}
