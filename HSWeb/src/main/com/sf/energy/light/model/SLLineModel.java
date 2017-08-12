package com.sf.energy.light.model;

public class SLLineModel
{
	// /标识
	private int SLLINE_ID = 0;
	// /所属区域ID
	private int AREA_ID = 0;
	// /所属网关ID
	private int GATHER_ID = 0;
	// /所属控制器
	private int SLKONGZHI_ID = 0;
	// /控制器路号
	private int SLKONGZHI_INDEX = 0;
	// /记录电表
	private int AMMETER_ID = 0;
	// /回路编号
	private String SLLINE_NUM = "";
	// /回路名称
	private String SLLINE_NAME = "";
	// /回路起始地点
	private String SLLINE_STAR = "";
	// /回路终止地点
	private String SLLINE_END = "";
	
	public int getSLLINE_ID()
	{
		return SLLINE_ID;
	}
	public void setSLLINE_ID(int sLLINE_ID)
	{
		SLLINE_ID = sLLINE_ID;
	}
	public int getAREA_ID()
	{
		return AREA_ID;
	}
	public void setAREA_ID(int aREA_ID)
	{
		AREA_ID = aREA_ID;
	}
	public int getGATHER_ID()
	{
		return GATHER_ID;
	}
	public void setGATHER_ID(int gATHER_ID)
	{
		GATHER_ID = gATHER_ID;
	}
	public int getSLKONGZHI_ID()
	{
		return SLKONGZHI_ID;
	}
	public void setSLKONGZHI_ID(int sLKONGZHI_ID)
	{
		SLKONGZHI_ID = sLKONGZHI_ID;
	}
	public int getSLKONGZHI_INDEX()
	{
		return SLKONGZHI_INDEX;
	}
	public void setSLKONGZHI_INDEX(int sLKONGZHI_INDEX)
	{
		SLKONGZHI_INDEX = sLKONGZHI_INDEX;
	}
	public int getAMMETER_ID()
	{
		return AMMETER_ID;
	}
	public void setAMMETER_ID(int aMMETER_ID)
	{
		AMMETER_ID = aMMETER_ID;
	}
	public String getSLLINE_NUM()
	{
		return SLLINE_NUM;
	}
	public void setSLLINE_NUM(String sLLINE_NUM)
	{
		SLLINE_NUM = sLLINE_NUM;
	}
	public String getSLLINE_NAME()
	{
		return SLLINE_NAME;
	}
	public void setSLLINE_NAME(String sLLINE_NAME)
	{
		SLLINE_NAME = sLLINE_NAME;
	}
	public String getSLLINE_STAR()
	{
		return SLLINE_STAR;
	}
	public void setSLLINE_STAR(String sLLINE_STAR)
	{
		SLLINE_STAR = sLLINE_STAR;
	}
	public String getSLLINE_END()
	{
		return SLLINE_END;
	}
	public void setSLLINE_END(String sLLINE_END)
	{
		SLLINE_END = sLLINE_END;
	}
	
	
}
