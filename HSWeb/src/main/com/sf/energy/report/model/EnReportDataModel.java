package com.sf.energy.report.model;

/**
 * 能耗数据
 * @author Administrator
 *
 */
public class EnReportDataModel {
	//ID
	private int enReportData_id=0;
	//能耗数据表格编号
	private String enReportData_num="";
	//能耗数据表格名称
	private String enReportData_name="";
	//是否启用
	private int enReportData_enable=0;
	//执行类型
	private String enReportData_style="";
	//执行内容
	private String enReportData_text="";
	//参数
	private String enReportData_part="";
	//备注
	private String enReportData_remark="";
	
	
	
	public int getEnReportData_id() {
		return enReportData_id;
	}
	public void setEnReportData_id(int enReportData_id) {
		this.enReportData_id = enReportData_id;
	}
	public String getEnReportData_num() {
		return enReportData_num;
	}
	public void setEnReportData_num(String enReportData_num) {
		this.enReportData_num = enReportData_num;
	}
	public String getEnReportData_name() {
		return enReportData_name;
	}
	public void setEnReportData_name(String enReportData_name) {
		this.enReportData_name = enReportData_name;
	}
	
	public String getEnReportData_style() {
		return enReportData_style;
	}
	public void setEnReportData_style(String enReportData_style) {
		this.enReportData_style = enReportData_style;
	}
	public String getEnReportData_text() {
		return enReportData_text;
	}
	public void setEnReportData_text(String enReportData_text) {
		this.enReportData_text = enReportData_text;
	}
	public String getEnReportData_part() {
		return enReportData_part;
	}
	public void setEnReportData_part(String enReportData_part) {
		this.enReportData_part = enReportData_part;
	}
	public String getEnReportData_remark() {
		return enReportData_remark;
	}
	public void setEnReportData_remark(String enReportData_remark) {
		this.enReportData_remark = enReportData_remark;
	}
	public int getEnReportData_enable() {
		return enReportData_enable;
	}
	public void setEnReportData_enable(int enReportData_enable) {
		this.enReportData_enable = enReportData_enable;
	}
	
	
	
}
