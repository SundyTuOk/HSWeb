package com.sf.energy.util;

/**
 * 数据校验类
 * 
 * @author yanhao
 * 
 */
public class DataValidation
{
    public static boolean isNumber(String number)
    {
        if (number == null || "".equals(number))
            return false;
        return number.matches("[+-]?[0-9]+[0-9]*(\\.[0-9]+)?");
    }

    public static boolean isAlpha(String alpha)
    {
        if (alpha == null)
            return false;
        return alpha.matches("[a-zA-Z]+");
    }

    public static boolean isChinese(String chineseContent)
    {
        if (chineseContent == null)
            return false;
        return chineseContent.matches("[\u4e00-\u9fa5]");
    }

    /**
     * 判断字符串时候合法
     * @param parameter
     * @return
     */
    public static boolean checkParameter(String parameter)
	{
		if(parameter!=null&&!parameter.equals("")&&!parameter.equals("null"))
			return true;
		else {
			return false;
		}
	}
}
