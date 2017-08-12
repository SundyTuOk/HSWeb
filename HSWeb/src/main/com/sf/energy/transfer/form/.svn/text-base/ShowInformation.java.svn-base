package com.sf.energy.transfer.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;


/**
 * 
 * 界面显示信息类
 * 
 * @author lujinquan
 * @version v1.0
 * @since version v1.0
 */
public class ShowInformation
{
	
	Logger logger  =  Logger.getLogger("ROLLING_FILE");
	
	int showMaxnum=1000;
		
	JScrollPane connInfo;
	JScrollPane datagram;
	// /是否显示发送报文
	private boolean showSend = true;
	// /是否显示接收报文
	private boolean showReceive = true;
	// 是否显示报文
	private boolean showMessage = true;
	// /要显示其报文的网关地
	
	//鼠标标右键点击事件
	JPopupMenu jpm;
	JMenuItem jm[] = new JMenuItem[3];
	
	JPopupMenu tablejpm;
	JMenuItem tablejm[] = new JMenuItem[3];
	
	Clipboard clipboard = null;
	
	private String showTerminalAddress = "所有网关";
	String titles[]={"时间","远程IP地址","终端标示","报文标示","报文长度","报文内容"};
	String data[][]=null;
	DefaultTableModel mode=new DefaultTableModel(data, titles);
	JTable table=new JTable(mode){public boolean isCellEditable(int row, int column)
    {
        return false;}//表格不允许被编辑
	};
	JTextArea text=new JTextArea();
	/**
	 * 构造函数
	 * @param connInfo 主界面上面的TextArea对象
	 * @param datagram 主界面下面的TextArea对象
	 */
	public ShowInformation(JScrollPane connInfo, JScrollPane datagram)
	{		
		//text 建立右键菜单
		jpm = new JPopupMenu("text 右键菜单");
		text.addMouseListener(new myListener());
		  
		jm[0] = new JMenuItem("全选");
		jm[0].setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
		jm[0].addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
//				//System.out.println("text全选");
				text.selectAll();
			}
		});
		jpm.add(jm[0]);
		
		jm[1] = new JMenuItem("复制");
		jm[1].setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		jm[1].addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
//				//System.out.println("text复制");
				text.copy();

			}
		});
		jpm.add(jm[1]);
		jm[2] = new JMenuItem("清空");
		jm[2].setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK));
		jm[2].addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
//				//System.out.println("清空");
				text.setText("");
			}
		});
		jpm.add(jm[2]);
		
		//table 建立右键菜单
		tablejpm=new JPopupMenu();
		  
		tablejm[0] = new JMenuItem("全选");
		tablejm[0].setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
		tablejm[0].addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
//				//System.out.println("table全选");
				table.selectAll();

			}
		});
		tablejpm.add(tablejm[0]);

		tablejm[1] = new JMenuItem("复制");
		tablejm[1].setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		tablejm[1].addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//System.out.println("table复制");
				String info="";
			    clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			    int rows[]=table.getSelectedRows();
			    for(int i=0;i<rows.length;i++)
			    {
			    	for(int j=0;j<titles.length;j++)
			    	{
			    		int row=rows[i];
			    		int col=j;
			    		info+=mode.getValueAt(row, col)+"\t";
			    	}
			    	info+="\n";
			    }
			    //System.out.println(info);
			    StringSelection   text = new StringSelection(info);
			    clipboard.setContents(text, null);
			
			}
		});
		tablejpm.add(tablejm[1]);

		tablejm[2] = new JMenuItem("清除选中");
		tablejm[2].setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK));
		tablejm[2].addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
//				//System.out.println("table清空");
				int rows[]=table.getSelectedRows();
				for(int i=0;i<rows.length;i++)
				{
					mode.removeRow(rows[i]-i);
				}		
			}
		});
		tablejpm.add(tablejm[2]);
		
		table.addMouseListener(new rightmouselistenner());		
		this.connInfo = connInfo;
		this.datagram = datagram;
		text.setEditable(false);
		this.connInfo.setViewportView(text);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.datagram.setViewportView(table);
	}
	
	 class myListener extends MouseAdapter {
		  public void mouseClicked(MouseEvent e) {
		   super.mouseClicked(e);
		   
		   if (e.getButton() == MouseEvent.BUTTON3) {
		    jpm.show(text, e.getX(), e.getY());
		    
		   }
		  }
		 }
	 
	class rightmouselistenner extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (e.getButton() == MouseEvent.BUTTON3)
			{
				tablejpm.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	/**
	 * 显示连接信息到主界面上面的框框。
	 * 
	 * @param infoData
	 *            要显示的信息
	 */
	public void setConnInfoOnView(String infoData)
	{
		text.append(infoData + "\n");
		JScrollBar jscrollBar = connInfo.getVerticalScrollBar();
	    if (jscrollBar != null)
	        jscrollBar.setValue(jscrollBar.getMaximum());
	}

	/**
	 * 显示报文信息到主界面下面的框框
	 * 
	 * @param infoAddress
	 *            终端IP
	 * @param terminalAddress
	 *            终端地址
	 * @param infoData
	 *            报文内容
	 * @param index
	 *            1：发送，2：接收
	 */
	public synchronized void setDatagramOnView(String infoAddress, String terminalAddress,
			String infoData, int index)
	{
		String length;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(now);
		ArrayList<String> info = new ArrayList<>();

		if (!showMessage)
		{
			return;
		}
		if (!showTerminalAddress.equals("所有网关")
				&& !showTerminalAddress.equals(terminalAddress))
		{
			return;
		}
		if (index == 1)
		{
			if (!showSend)
			{
				return;
			}
			length = String.valueOf((int) infoData.length() / 3);
			
			info.add(time);
			info.add(infoAddress);
			info.add(terminalAddress);
			info.add("发送");
			info.add(length);
			info.add(infoData);
			fillTable(info);
//			datagram.append(time + " " + infoAddress + " " + terminalAddress
//					+ " " + "发送" + " " + length + " " + infoData + "\n");
		}
		if (index == 2)
		{
			if (!showReceive)
			{
				return;
			}
			length = String.valueOf((int) infoData.length() / 3);
			info.add(time);
			info.add(infoAddress);
			info.add(terminalAddress);
			info.add("接收");
			info.add(length);
			info.add(infoData);
			fillTable(info);
//			datagram.append(time + " " + infoAddress + " " + terminalAddress
//					+ " " + "接收" + " " + length + " " + infoData + "\n");
		}
	}
	
	private synchronized void  fillTable(final ArrayList<String> info)
	{
		
		String logString="";
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		String[] arr=new String[info.size()];
		for(int i=0;i<info.size();i++)
		{
			arr[i]=info.get(i);
		}
		
		//获取当前的行数
		int nowRowNum=table.getRowCount()+1;
				//table.getHeight()/table.getRowHeight();
		if (nowRowNum+1 >showMaxnum) {
			//System.out.println("*************************************************页面过长清理！"+showMaxnum +"  "+nowRowNum);
			for (int i = 0; i <nowRowNum; i++) {
				for(int j=0;j<titles.length;j++)
		    	{
					
		    		int col=j;
		    		logString+=mode.getValueAt(0, col)+"\t";
		    	}
				logString+="\r\n";
				tableModel.removeRow(0);
			}	
			//System.out.println("*************************************************输出日志");
			logger.fatal(logString);
			tableModel.addRow(arr);
		} else {
			tableModel.addRow(arr);
			
		}
		
		
		
		DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
            	if("接收".equals(table.getValueAt(row, 3)))
        		{
            		setBackground(Color.white);
        		}
        		
            	if("发送".equals(table.getValueAt(row, 3))) 
        		{
        			setBackground(new Color(206, 231, 255));
        		}
//            	if (row % 2 == 0)
//                     setBackground(Color.white); //设置奇数行底色
//                   else if (row % 2 == 1)
//                     setBackground(new Color(206, 231, 255)); //设置偶数行底色

                return super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);//这一句必须有
            }
        };

        table.setDefaultRenderer(Object.class, cellRender);//calendar是个表格
		

	    //　设定每列的宽度为当列的最大的宽度。
	    for(int i= 0; i<table.getColumnCount(); i++){
	      int width = this.getPreferredWidthForCloumn(table,i) + 10;
	      width = 70 > width ? 70 : width;
	      table.getColumnModel().getColumn(i).setPreferredWidth(width);
	    }
		
		table.invalidate();
		datagram.setViewportView(table);

		JScrollBar jscrollBar = datagram.getVerticalScrollBar();
	    if (jscrollBar != null)
	        jscrollBar.setValue(jscrollBar.getMaximum());
	}
	
	// 取得列幅的最大值
	private int getPreferredWidthForCloumn(JTable table,int icol)
	{

	    TableColumnModel tcl = table.getColumnModel();
	    TableColumn col = tcl.getColumn(icol);
	    int c = col.getModelIndex(),width = 0,maxw = 0;

	    for(int r=0;r<table.getRowCount();++r)
	    {

	      TableCellRenderer renderer = table.getCellRenderer(r,c);
	      Component comp = renderer.getTableCellRendererComponent(table,table.getValueAt(r,c),false,false,r,c);
	      width = comp.getPreferredSize().width;
	      maxw = (width > maxw)?width:maxw;
	    }
	    
	    return maxw;
	}


	public boolean isShowSend()
	{
		return showSend;
	}

	public void setShowSend(boolean showSend)
	{
		this.showSend = showSend;
	}

	public boolean isShowReceive()
	{
		return showReceive;
	}

	public void setShowReceive(boolean showReceive)
	{
		this.showReceive = showReceive;
	}

	public boolean isShowMessage()
	{
		return showMessage;
	}

	public void setShowMessage(boolean showMessage)
	{
		this.showMessage = showMessage;
	}

	public String getShowTerminalAddress()
	{
		return showTerminalAddress;
	}

	public void setShowTerminalAddress(String showTerminalAddress)
	{
		this.showTerminalAddress = showTerminalAddress;
	}

	public int getShowMaxnum() {
		return showMaxnum;
	}

	public void setShowMaxnum(int showMaxnum) {
		this.showMaxnum = showMaxnum;
	}
	
	
}
