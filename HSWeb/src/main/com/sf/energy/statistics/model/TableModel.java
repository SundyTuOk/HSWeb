package com.sf.energy.statistics.model;

public class TableModel
{
	public String field;
	public String title;
	public Boolean sortable=true;
	public Boolean hidden=false;
	public TableModel(String field,String title,Boolean sortable){
		this.field=field;
		this.title=title;
		this.sortable=sortable;
	}
	public TableModel(String field,String title,Boolean sortable,Boolean hidden){
		this.field=field;
		this.title=title;
		this.sortable=sortable;
		this.hidden=hidden;
	}
}
