package com.sf.energy.statistics.servlet;

//数据载体，用来接收浏览器传过来的数据
public class ArcArray{
	private String st;
	private String et;
	private int aids[];
	
	public ArcArray() {
		
	}
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	public String getEt() {
		return et;
	}
	public void setEt(String et) {
		this.et = et;
	}
	public int[] getAids() {
		return aids;
	}
	public void setAids(int[] aids) {
		this.aids = aids;
	}
	
	public String getStartTime(){
		String st[] = this.st.split("/");
		return st[2] + "-" + st[0] + "-" + st[1];
	}
	
	public String getEndTime(){
		String et[] = this.et.split("/");
		return et[2] + "-" + et[0] + "-" + et[1];
	}
}
