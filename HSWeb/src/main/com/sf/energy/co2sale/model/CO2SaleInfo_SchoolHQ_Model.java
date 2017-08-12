package com.sf.energy.co2sale.model;

public class CO2SaleInfo_SchoolHQ_Model
{
	/** 
	
	* @Fields saleInfoID : TODO(CO2订单信息) 
	
	*/ 
	private int saleInfoID;
	/** 
	
	* @Fields sale_partmentid : TODO(卖方部门ID) 
	
	*/ 
	private int sale_partmentid;
	/** 
	
	* @Fields buy_partmentid : TODO(买方部门ID) 
	
	*/ 
	private int buy_partmentid;
	/** 
	
	* @Fields sale_partmentName : TODO(卖方部门名称) 
	
	*/ 
	private String sale_partmentName;
	/** 
	
	* @Fields buy_partmentName : TODO(买方部门名称) 
	
	*/ 
	private String buy_partmentName;
	/** 
	
	* @Fields buyORSale : TODO(订单是买入还是卖出) 
	
	*/ 
	private int buyORSale;
	/** 
	
	* @Fields dealValue : TODO(成交量) 
	
	*/ 
	private float dealValue;
	/** 
	
	* @Fields price : TODO(单价) 
	
	*/ 
	private float price;
	/** 
	
	* @Fields addTime : TODO(行情添加时间) 
	
	*/ 
	private String addTime;
	/** 
	
	* @Fields dealTime : TODO(订单成交时间) 
	
	*/ 
	private String dealTime;
	/** 
	
	* @Fields status : TODO(订单状态，-1为交易关闭，0为等待接洽，1为交易成功，2为等待付款) 
	
	*/ 
	private int status;
	/** 
	
	* @Fields contactPerson : TODO(联系人姓名) 
	
	*/ 
	private String contactPerson;
	/** 
	
	* @Fields contactInformation : TODO(联系方式) 
	
	*/ 
	private String contactInformation;
	/** 
	
	* @Fields remainValue : TODO(剩余量) 
	
	*/ 
	private float remainValue;
	public int getSaleInfoID()
	{
		return saleInfoID;
	}
	public void setSaleInfoID(int saleInfoID)
	{
		this.saleInfoID = saleInfoID;
	}
	public int getSale_partmentid()
	{
		return sale_partmentid;
	}
	public void setSale_partmentid(int sale_partmentid)
	{
		this.sale_partmentid = sale_partmentid;
	}
	public int getBuy_partmentid()
	{
		return buy_partmentid;
	}
	public void setBuy_partmentid(int buy_partmentid)
	{
		this.buy_partmentid = buy_partmentid;
	}
	public String getSale_partmentName()
	{
		return sale_partmentName;
	}
	public void setSale_partmentName(String sale_partmentName)
	{
		this.sale_partmentName = sale_partmentName;
	}
	public String getBuy_partmentName()
	{
		return buy_partmentName;
	}
	public void setBuy_partmentName(String buy_partmentName)
	{
		this.buy_partmentName = buy_partmentName;
	}
	public int getBuyORSale()
	{
		return buyORSale;
	}
	public void setBuyORSale(int buyORSale)
	{
		this.buyORSale = buyORSale;
	}
	public float getDealValue()
	{
		return dealValue;
	}
	public void setDealValue(float dealValue)
	{
		this.dealValue = dealValue;
	}
	public float getPrice()
	{
		return price;
	}
	public void setPrice(float price)
	{
		this.price = price;
	}
	public String getAddTime()
	{
		return addTime;
	}
	public void setAddTime(String addTime)
	{
		this.addTime = addTime;
	}
	public String getDealTime()
	{
		return dealTime;
	}
	public void setDealTime(String dealTime)
	{
		this.dealTime = dealTime;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public String getContactPerson()
	{
		return contactPerson;
	}
	public void setContactPerson(String contactPerson)
	{
		this.contactPerson = contactPerson;
	}
	public String getContactInformation()
	{
		return contactInformation;
	}
	public void setContactInformation(String contactInformation)
	{
		this.contactInformation = contactInformation;
	}
	public float getRemainValue()
	{
		return remainValue;
	}
	public void setRemainValue(float remainValue)
	{
		this.remainValue = remainValue;
	}
	
}
