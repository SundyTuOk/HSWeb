package com.sf.energy.project.system.model;

/**
 * <数据中转站实体类，并提供了set和get方法>
 * 
 * @author 李涵淼
 * @version 1.0
 * @since[version 1.0]
 * 
 */
public class DataSite
{
	// /数据中转站ID
	private int datasiteID;

	// /数据中转站编号
	private String datasiteNum;

	// /数据中转站名称
	private String datasiteName;

	// /数据中转站IP
	private String datasiteIP;

	// /数据中转站端口
	private int datasitePort;

	// /数据中转站心跳间隔
	private int datasiteHeart;

	// /数据中转站最后连接时间
	private String datasiteLastConTime;

	// /数据中转站备注
	private String datasiteRemark;

	// /数据中转站当前状态
	private int datasiteState;

	public DataSite(int datasiteID, String datasiteNum, String datasiteName,
			String datasiteIP, int datasitePort, int datasiteHeart,
			String datasiteLastConTime, String datasiteRemark, int datasiteState)
	{
		this.datasiteID = datasiteID;
		this.datasiteNum = datasiteNum;
		this.datasiteName = datasiteName;
		this.datasiteIP = datasiteIP;
		this.datasitePort = datasitePort;
		this.datasiteHeart = datasiteHeart;
		this.datasiteLastConTime = datasiteLastConTime;
		this.datasiteRemark = datasiteRemark;
		this.datasiteState = datasiteState;
	}

	public DataSite()
	{

	}

	/**
	 * 
	 * set和get方法
	 * 
	 */

	public int getDatasiteID()
	{
		return datasiteID;
	}

	public void setDatasiteID(int datasiteID)
	{
		this.datasiteID = datasiteID;
	}

	public String getDatasiteNum()
	{
		return datasiteNum;
	}

	public void setDatasiteNum(String datasiteNum)
	{
		this.datasiteNum = datasiteNum;
	}

	public String getDatasiteName()
	{
		return datasiteName;
	}

	public void setDatasiteName(String datasiteName)
	{
		this.datasiteName = datasiteName;
	}

	public String getDatasiteIP()
	{
		return datasiteIP;
	}

	public void setDatasiteIP(String datasiteIP)
	{
		this.datasiteIP = datasiteIP;
	}

	public int getDatasitePort()
	{
		return datasitePort;
	}

	public void setDatasitePort(int datasitePort)
	{
		this.datasitePort = datasitePort;
	}

	public int getDatasiteHeart()
	{
		return datasiteHeart;
	}

	public void setDatasiteHeart(int datasiteHeart)
	{
		this.datasiteHeart = datasiteHeart;
	}

	public String getDatasiteLastConTime()
	{
		return datasiteLastConTime;
	}

	public void setDatasiteLastConTime(String datasiteLastConTime)
	{
		this.datasiteLastConTime = datasiteLastConTime;
	}

	public String getDatasiteRemark()
	{
		return datasiteRemark;
	}

	public void setDatasiteRemark(String datasiteRemark)
	{
		this.datasiteRemark = datasiteRemark;
	}

	public int getDatasiteState()
	{
		return datasiteState;
	}

	public void setDatasiteState(int datasiteState)
	{
		this.datasiteState = datasiteState;
	}

}
