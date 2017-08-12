package com.sf.energy.expert.model;

public class EnergyDataTabData {
	//  /建筑ID
	private  int arcID = 0;
	//  /建筑名称
	private  String arcName = null;
	//  /建筑分类名称
	private  String arcSortname = null;
	//  /能耗月份
	private  int month = 0;
	//  /能耗日期
	private  int day = 0;
	//  /建筑面积
	private  float  arcArea = 0;
	//  /用电量
	private  float  amData = 0;
	//  /面积电耗
	private  float  amAreaData = 0;
	//  /用水量
	private  float  waterData = 0;
	//  /面积水耗
	private  float  waterAreaData = 0;
	//  /煤量
	private  float  coaldata = 0;
	//  /汽油量
	private  float   gasdata = 0;
	//  /煤油量
	private	 float   coaloildata = 0;
	//  /柴油量
	private   float	dieseloildata = 0;
	
	//年用电量
	private float ammValueYear=0;
	//年用水量
	private float waterValueYear=0;
	
	//折标系数
	private float zhebiaoxishu=0;
	
	//能耗用量
	private float value=0;
		
	//用能比例
	private String ratio="";
		
	//折标后的值
	private float zhebiaoValue=0;
	
	//总用电量
	private float  totalenergyCount = 0;
	
	//照明插座电量
	private float  zhaomingchazuo = 0;
	
	//空调用电电量
	private float  kongtiaoyongdian = 0;
	
	//动力用电电量
	private float  dongliyongdian = 0;
		
	//特殊用电电量
	private float  teshuyongdian = 0;
	
	//总用水量
	private float  totalwaterCount = 0;
		
	//公共洗手间水量
	private float  gonggongxishoujian = 0;
		
	//食堂餐厅水量
	private float  shitangcanting = 0;
		
	//澡堂淋浴水量
	private float  zaotanglinyu = 0;
			
	//消防灌溉水量
	private float  xiaofangguangai = 0;
	
	
	
	
		public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
		public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
		public String getRatio()
	{
		return ratio;
	}
	public void setRatio(String ratio)
	{
		this.ratio = ratio;
	}
		public float getValue()
	{
		return value;
	}
	public void setValue(float value)
	{
		this.value = value;
	}
		
		public float getZhebiaoValue()
		{
			return zhebiaoValue;
		}
		public void setZhebiaoValue(float zhebiaoValue)
		{
			this.zhebiaoValue = zhebiaoValue;
		}
	public float getZhebiaoxishu()
	{
		return zhebiaoxishu;
	}
	public void setZhebiaoxishu(float zhebiaoxishu)
	{
		this.zhebiaoxishu = zhebiaoxishu;
	}
	public float getAmmValueYear()
	{
		return ammValueYear;
	}
	public void setAmmValueYear(float ammValueYear)
	{
		this.ammValueYear = ammValueYear;
	}
	public float getWaterValueYear()
	{
		return waterValueYear;
	}
	public void setWaterValueYear(float waterValueYear)
	{
		this.waterValueYear = waterValueYear;
	}
	public int getArcID() {
		return arcID;
	}
	public void setArcID(int arcID) {
		this.arcID = arcID;
	}
	public String getArcName() {
		return arcName;
	}
	public void setArcName(String arcName) {
		this.arcName = arcName;
	}
	public String getArcSortname() {
		return arcSortname;
	}
	public void setArcSortname(String arcSortname) {
		this.arcSortname = arcSortname;
	}
	public float getArcArea() {
		return arcArea;
	}
	public void setArcArea(float arcArea) {
		this.arcArea = arcArea;
	}
	public float getAmData() {
		return amData;
	}
	public void setAmData(float amData) {
		this.amData = amData;
	}
	public float getAmAreaData() {
		return amAreaData;
	}
	public void setAmAreaData(float amAreaData) {
		this.amAreaData = amAreaData;
	}
	public float getWaterData() {
		return waterData;
	}
	public void setWaterData(float waterData) {
		this.waterData = waterData;
	}
	public float getWaterAreaData() {
		return waterAreaData;
	}
	public void setWaterAreaData(float waterAreaData) {
		this.waterAreaData = waterAreaData;
	}
	public float getCoaldata() {
		return coaldata;
	}
	public void setCoaldata(float coaldata) {
		this.coaldata = coaldata;
	}
	public float getGasdata() {
		return gasdata;
	}
	public void setGasdata(float gasdata) {
		this.gasdata = gasdata;
	}
	public float getCoaloildata() {
		return coaloildata;
	}
	public void setCoaloildata(float coaloildata) {
		this.coaloildata = coaloildata;
	}
	public float getDieseloildata() {
		return dieseloildata;
	}
	public void setDieseloildata(float dieseloildata) {
		this.dieseloildata = dieseloildata;
	}
	public float getTotalenergyCount() {
		return totalenergyCount;
	}
	public void setTotalenergyCount(float totalenergyCount) {
		this.totalenergyCount = totalenergyCount;
	}
	public float getZhaomingchazuo() {
		return zhaomingchazuo;
	}
	public void setZhaomingchazuo(float zhaomingchazuo) {
		this.zhaomingchazuo = zhaomingchazuo;
	}
	public float getKongtiaoyongdian() {
		return kongtiaoyongdian;
	}
	public void setKongtiaoyongdian(float kongtiaoyongdian) {
		this.kongtiaoyongdian = kongtiaoyongdian;
	}
	public float getDongliyongdian() {
		return dongliyongdian;
	}
	public void setDongliyongdian(float dongliyongdian) {
		this.dongliyongdian = dongliyongdian;
	}
	public float getTeshuyongdian() {
		return teshuyongdian;
	}
	public void setTeshuyongdian(float teshuyongdian) {
		this.teshuyongdian = teshuyongdian;
	}
	public float getTotalwaterCount() {
		return totalwaterCount;
	}
	public void setTotalwaterCount(float totalwaterCount) {
		this.totalwaterCount = totalwaterCount;
	}
	public float getGonggongxishoujian() {
		return gonggongxishoujian;
	}
	public void setGonggongxishoujian(float gonggongxishoujian) {
		this.gonggongxishoujian = gonggongxishoujian;
	}
	public float getShitangcanting() {
		return shitangcanting;
	}
	public void setShitangcanting(float shitangcanting) {
		this.shitangcanting = shitangcanting;
	}
	public float getZaotanglinyu() {
		return zaotanglinyu;
	}
	public void setZaotanglinyu(float zaotanglinyu) {
		this.zaotanglinyu = zaotanglinyu;
	}
	public float getXiaofangguangai() {
		return xiaofangguangai;
	}
	public void setXiaofangguangai(float xiaofangguangai) {
		this.xiaofangguangai = xiaofangguangai;
	}
	
	
	
	
}
