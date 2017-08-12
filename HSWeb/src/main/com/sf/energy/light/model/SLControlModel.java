package com.sf.energy.light.model;

public class SLControlModel
{
	// /标识
	private int SLKONGZHI_ID = 0;
	// /所属区域ID
	private int AREA_ID = 0;
	// /所属网关ID
	private int SLGATHER_ID = 0;
	// /控制器编号
	private String SLKONGZHI_NUM = "";
	// /控制器名称
	private String SLKONGZHI_NAME = "";
	// /控制器485地址
	private String SLKONGZHI_485ADDRESS = "";
	// /控制器型号
	private String SLKONGZHI_SIZE = "";
	// /控制器端口号
	private int SLKONGZHI_PORT = 0;
	// /所属区域
	private String AREA_ADDRESS="";
	
	// private String version = "";
	// private String lampState1 = "";
	// private String lampState2 = "";
	// private int lampAcctime1;
	// private int lampAcctime2;
	// private String lastTime = "";
	// private int slControlState;
	// private String lampState11 = "";
	// private String lampState21 = "";
	
	public int getSLKONGZHI_ID()
	{
		return SLKONGZHI_ID;
	}
	public void setSLKONGZHI_ID(int sLKONGZHI_ID)
	{
		SLKONGZHI_ID = sLKONGZHI_ID;
	}
	public int getAREA_ID()
	{
		return AREA_ID;
	}
	public void setAREA_ID(int aREA_ID)
	{
		AREA_ID = aREA_ID;
	}
	public int getSLGATHER_ID()
	{
		return SLGATHER_ID;
	}
	public void setSLGATHER_ID(int sLGATHER_ID)
	{
		SLGATHER_ID = sLGATHER_ID;
	}
	public String getSLKONGZHI_NUM()
	{
		return SLKONGZHI_NUM;
	}
	public void setSLKONGZHI_NUM(String sLKONGZHI_NUM)
	{
		SLKONGZHI_NUM = sLKONGZHI_NUM;
	}
	public String getSLKONGZHI_NAME()
	{
		return SLKONGZHI_NAME;
	}
	public void setSLKONGZHI_NAME(String sLKONGZHI_NAME)
	{
		SLKONGZHI_NAME = sLKONGZHI_NAME;
	}
	public String getSLKONGZHI_485ADDRESS()
	{
		return SLKONGZHI_485ADDRESS;
	}
	public void setSLKONGZHI_485ADDRESS(String sLKONGZHI_485ADDRESS)
	{
		SLKONGZHI_485ADDRESS = sLKONGZHI_485ADDRESS;
	}
	public String getSLKONGZHI_SIZE()
	{
		return SLKONGZHI_SIZE;
	}
	public void setSLKONGZHI_SIZE(String sLKONGZHI_SIZE)
	{
		SLKONGZHI_SIZE = sLKONGZHI_SIZE;
	}
	public int getSLKONGZHI_PORT()
	{
		return SLKONGZHI_PORT;
	}
	public void setSLKONGZHI_PORT(int sLKONGZHI_PORT)
	{
		SLKONGZHI_PORT = sLKONGZHI_PORT;
	}
	public String getAREA_ADDRESS() {
		return AREA_ADDRESS;
	}
	public void setAREA_ADDRESS(String aREA_ADDRESS) {
		AREA_ADDRESS = aREA_ADDRESS;
	}
	
    
	
}
