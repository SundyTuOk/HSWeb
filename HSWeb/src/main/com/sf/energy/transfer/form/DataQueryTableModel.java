package com.sf.energy.transfer.form;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTable;

public class DataQueryTableModel
{

	private String[] title = null;
	private JTable table = null;
	private ArrayList<HashMap<String, String>> list=null;
	
	public ArrayList<HashMap<String, String>> getList()
	{
		return list;
	}
	public void setList(ArrayList<HashMap<String, String>> list)
	{
		this.list = list;
	}
	public String[] getTitle()
	{
		return title;
	}
	public void setTitle(String[] title)
	{
		this.title = title;
	}
	public JTable getTable()
	{
		return table;
	}
	public void setTable(JTable table)
	{
		this.table = table;
	}
	
	
}
