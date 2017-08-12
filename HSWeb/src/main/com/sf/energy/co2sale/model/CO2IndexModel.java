package com.sf.energy.co2sale.model;

public class CO2IndexModel
{
	private int partment_id;
	private int year;
	private float style_1;//落叶阔叶林  吸收率14
	private float style_2;//常绿阔叶林  吸收率29
	private float style_3;//针叶林  吸收率22
	private float style_4;//灌木林  吸收率14
	private float style_5;//草坪绿地  吸收率10
	private float add_1;//雨水回收系统    公式：指标量=指标两*（1+完成率*0.1）
	private float add_2;//煤改气   公式：排放量=排放量*（1-完成率*0.08）
	private float add_3;//空气源热泵   公式：排放量=排放量*（1-完成率*0.08）
	private float add_4;//太阳能利用   公式：排放量=排放量*（1-完成率*0.08）
	public int getPartment_id()
	{
		return partment_id;
	}
	public void setPartment_id(int partment_id)
	{
		this.partment_id = partment_id;
	}
	public int getYear()
	{
		return year;
	}
	public void setYear(int year)
	{
		this.year = year;
	}
	public float getStyle_1()
	{
		return style_1;
	}
	public void setStyle_1(float style_1)
	{
		this.style_1 = style_1;
	}
	public float getStyle_2()
	{
		return style_2;
	}
	public void setStyle_2(float style_2)
	{
		this.style_2 = style_2;
	}
	public float getStyle_3()
	{
		return style_3;
	}
	public void setStyle_3(float style_3)
	{
		this.style_3 = style_3;
	}
	public float getStyle_4()
	{
		return style_4;
	}
	public void setStyle_4(float style_4)
	{
		this.style_4 = style_4;
	}
	public float getStyle_5()
	{
		return style_5;
	}
	public void setStyle_5(float style_5)
	{
		this.style_5 = style_5;
	}
	public float getAdd_1()
	{
		return add_1;
	}
	public void setAdd_1(float add_1)
	{
		this.add_1 = add_1;
	}
	public float getAdd_2()
	{
		return add_2;
	}
	public void setAdd_2(float add_2)
	{
		this.add_2 = add_2;
	}
	public float getAdd_3()
	{
		return add_3;
	}
	public void setAdd_3(float add_3)
	{
		this.add_3 = add_3;
	}
	public float getAdd_4()
	{
		return add_4;
	}
	public void setAdd_4(float add_4)
	{
		this.add_4 = add_4;
	}
}
