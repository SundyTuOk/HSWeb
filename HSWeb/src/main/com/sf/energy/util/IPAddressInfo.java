package com.sf.energy.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.servlet.http.HttpServletRequest;


/**
 * 获取登录的IP地址
 * @author yanhao
 *
 */
public class IPAddressInfo
{
	/**
	 * 获取登录用户的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIPAddress(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
		{
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.equalsIgnoreCase("0:0:0:0:0:0:0:1"))
		{
			ip = "127.0.0.1";
		}
		return ip;
	}

	/**
	 * 获取登录用户的MAC地址
	 * 
	 * @param ip
	 * @return
	 */
	public static String getMACAddress(String ip)
	{
		String str = "";
		String macAddress = "";
		try
		{
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++)
			{
				str = input.readLine();
				if (str != null)
				{
					if (str.indexOf("MAC Address") > 1)
					{
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace(System.out);
		}
		return macAddress;
	}
}
