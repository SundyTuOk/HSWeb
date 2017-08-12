/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sf.energy.transfer.form;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import com.eltima.components.ui.DatePicker;
import com.sf.energy.transfer.db.SenderWithOracle;

/**
 * 
 * 数据中转站数据查询界面
 * 
 * @author lujinquan
 * @version v1.0
 * @since version v1.0
 */
public class DataQuery extends javax.swing.JFrame
{

	/**
	 * Creates new form DataQuery
	 */
	public DataQuery()
	{
		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents()
	{
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		// 从数据库读取jcombox 的表象
		DefaultComboBoxModel mode = new DefaultComboBoxModel();
		String dgAddress[] = null;
		try
		{
			dgAddress = swo.queryDG();

			String name[] = new String[dgAddress.length + 1];
			name[0] = "所有网关";
			mode.addElement(name[0]);

			for (int i = 0; i < dgAddress.length; i++)
			{
				name[i + 1] = dgAddress[i];
				mode.addElement(name[i + 1]);
			}
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(this, "数据库连接发生错误！");
		}

		terminal = new javax.swing.JComboBox(mode);
		jLabel2 = new javax.swing.JLabel();
		meterType = new javax.swing.JComboBox();
		jLabel3 = new javax.swing.JLabel();
		commandType = new javax.swing.JComboBox();
		column = new javax.swing.JButton();
		jLabel4 = new javax.swing.JLabel();
		showDate = new DatePicker(this, new Date());
		allDate = new javax.swing.JCheckBox();
		query = new javax.swing.JButton();
		save = new javax.swing.JButton();
		showTablePane = new javax.swing.JScrollPane();
		
		jLabel5 = new javax.swing.JLabel();
		
		showTablePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		
		tips1 = new javax.swing.JLabel();
		tips2 = new javax.swing.JLabel();
		tips1.setText("命令说明：0C0110位数据中转站轮抄当前示值；0C0164为数据网关主动上传当前示值；");

		tips2.setText("0D0264为数据中转站补抄冻结示值；0C0103位当前三相数据；0C0410为当前反向示值。");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("数据查询");
		setPreferredSize(new java.awt.Dimension(760, 550));

		setResizable(true);

		jLabel1.setText("数据网关：");

		jLabel2.setText("表计类型；");

		meterType.setModel(new javax.swing.DefaultComboBoxModel(new String[]
		{ "电表", "水表" }));

		jLabel3.setText("命令类型：");

		commandType.setModel(new javax.swing.DefaultComboBoxModel(new String[]
		{ "0C0164", "0C0103", "0C0410" }));

		column.setText("显示筛选");
		column.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				columnActionPerformed(evt);
			}
		});

		jLabel4.setText("日期：");
		showDate.setPattern("yyyy-MM-dd");

		
		allDate.setText("所有日期");
		allDate.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				// TODO Auto-generated method stub
				allDateActionPerFormed(e);

			}
		});

		query.setText("查询");
		query.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				queryActionPerformed(evt);
			}
		});

		save.setText("另存为");
		save.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				saveActionPerformed(evt);
			}
		});
		
		jLabel5.setText("      共计   0  条");
		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(showTablePane,
								javax.swing.GroupLayout.Alignment.TRAILING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(23, 23, 23)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				tips2)
																		.addGap(0,
																				0,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				tips1)
																		.addContainerGap(
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel1)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												terminal,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												82,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel4)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												showDate,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												119,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addGap(35,
																				35,
																				35)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel2)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												meterType,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												78,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(36,
																												36,
																												36)
																										.addComponent(
																												jLabel3)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												commandType,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												174,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												column))
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												allDate)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												query)
																										.addGap(59,
																												59,
																												59)
																										.addComponent(
																												save)))
																		.addGap(18,
																				18,
																				18)))));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(
																terminal,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel2)
														.addComponent(
																meterType,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel3)
														.addComponent(
																commandType,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(column))
										.addGap(18, 18, 18)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel4)
														.addComponent(
																showDate,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																30,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(allDate)
														.addComponent(query)
														.addComponent(save))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(tips1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(tips2)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												12, Short.MAX_VALUE)
										.addComponent(
												showTablePane,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												320,
												javax.swing.GroupLayout.PREFERRED_SIZE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jLabel5)
				);
		
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addComponent(jLabel5).addContainerGap()));

		pack();
	}

	private void allDateActionPerFormed(ItemEvent e)
	{
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED)
		{
			isAllDate = true;
		} else
		{
			isAllDate = false;
		}

	}

	/**
	 * @param evt
	 */
	private void columnActionPerformed(java.awt.event.ActionEvent evt)
	{
		if (queryTable == null)
		{
			JOptionPane.showMessageDialog(this, "没有任何数据");
		} else
		{

			JScrollPane showColumnView = null;
			JButton submit = null;
			JButton reset = null;
			selectColumnDialog = new javax.swing.JDialog();
			showColumnView = new javax.swing.JScrollPane();
			submit = new javax.swing.JButton();
			reset = new javax.swing.JButton();
			jPanel1 = new javax.swing.JPanel();
			jLabel1 = new javax.swing.JLabel();

			selectColumnDialog.setModal(true); // 设置对话框为模态
			selectColumnDialog.setLocationByPlatform(true); // 由系统平台布置窗体位置
			selectColumnDialog.setTitle("显示列选择对话框对话框"); // 对话框标题
			selectColumnDialog.setLocation(100, 100);
			selectColumnDialog.setSize(300, 200);
			selectColumnDialog.setResizable(false);
			reset.setText("重置");
			submit.setText("确定");
			submit.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					submitActionPerformed(e);
				}
			});
			JPanel jp2 = new JPanel();
			// jp2.add(reset);
			jp2.add(submit);

			String title[] = null;

			if (queryTable.getTitle() != null)
			{
				title = queryTable.getTitle();
			} else
			{
				JOptionPane.showMessageDialog(this, "queryData函数查询有问题！");
			}

			JPanel jp = new JPanel();

			for (int i = 0; i < title.length; i++)
			{
				String name = title[i];
				JCheckBox column = new JCheckBox(name);
				column.addItemListener(new ItemListener()
				{

					@Override
					public void itemStateChanged(ItemEvent e)
					{
						checkBoxActionPerformed(e);
					}
				});
				jp.add(column);

			}
			selectColumnDialog.getContentPane().setLayout(new BorderLayout());
			selectColumnDialog.getContentPane().add(showColumnView,
					BorderLayout.CENTER);
			selectColumnDialog.getContentPane().add(jp2, BorderLayout.SOUTH);
			showColumnView.setViewportView(jp);

			selectColumnDialog.setVisible(true);
		}
	}

	private void submitActionPerformed(ActionEvent e)
	{
		String title[] = new String[selectCol.size()];
		for (int i = 0; i < selectCol.size(); i++)
		{
			title[i] = selectCol.get(i);
		}
		table = getNewJTable(title, queryTable.getList());
		showTablePane.setViewportView(table);
		selectCol.removeAll(selectCol);
		selectColumnDialog.dispose();

	}

	private void checkBoxActionPerformed(ItemEvent e)
	{
		JCheckBox jcb = (JCheckBox) e.getSource();
		String columnName = jcb.getLabel();
		if (e.getStateChange() == ItemEvent.SELECTED)
		{
			selectCol.add(columnName);
		} else
		{
			if (selectCol.contains(columnName))
				selectCol.remove(columnName);
		}
	}

	/**
	 * @param evt
	 */
	private void queryActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_queryActionPerformed
		// TODO add your handling code here:

		String dgaddress = "";
		if (!"所有网关".equals((String) terminal.getSelectedItem()))
		{
			dgaddress = (String) terminal.getSelectedItem();
		}
		String type = (String) meterType.getSelectedItem();
		String readTime = "";
		if (!isAllDate)
		{
			readTime = showDate.getText();
		}
		try
		{
			queryTable = swo.queryData(type,
					(String) commandType.getSelectedItem(), dgaddress, "",
					readTime,jLabel5);
			if (queryTable.getTable() != null)
			{
				JTable table=queryTable.getTable();
				if("0C0103".equals((String) commandType.getSelectedItem()))
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.setPreferredScrollableViewportSize(new Dimension());
				table.setFillsViewportHeight(true);
				table.setAutoCreateRowSorter(true);
				showTablePane.setViewportView(table);
				//System.out.println(selectCol.size());
			} else
			{
				JOptionPane.showMessageDialog(this, "queryData函数查询有问题！");
			}
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(this, "queryData函数查询有问题！");
		}
	}

	private void saveActionPerformed(java.awt.event.ActionEvent evt)
	{

	}

	// /**
	// * 传入结果集，和表的字段名称生成一个JTable 的组件
	// *
	// * @param title
	// * 字段名称数组
	// * @param rs
	// * 表查出来的结果集
	// * @return JTable 组件
	// * @throws SQLException
	// */
	// JTable getJTable(String title[], ResultSet rs) throws SQLException
	// {
	// // 获取列数，
	// int col = title.length;
	// // 获取行数
	// rs.last();
	// int row = rs.getRow();
	// // 获取表格信息
	// Object[][] info = new Object[row][col];
	// int count = 0;
	// rs.beforeFirst();
	// while (rs.next())
	// {
	// for (int i = 1; i <= col; i++)
	// {
	// info[count][i - 1] = rs.getString(title[i - 1]);
	// }
	// count++;
	// }
	// JTable table = new JTable(info, title);
	// return table;
	// }
	//
	private JTable getNewJTable(String[] title,
			ArrayList<HashMap<String, String>> list)
	{
		JTable newTable=new JTable();
		if (title != null && list != null)
		{
			// 获取列数，
			int col = title.length;
			// 获取行数
			int row = list.size();
			// 获取表格信息
			Object[][] info = new Object[row][col];
			int count = 0;
			for (HashMap<String, String> n : list)
			{
				for (int i = 0; i < col; i++)
				{
					info[count][i] = n.get(title[i]);
				}
				count++;
			}
			newTable = new JTable(info, title);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "queryData函数查询有问题！");
		}
		
		return newTable;
	}

	// /**
	// * 查询结果集的所有字段名称
	// *
	// * @param rs
	// * 查询到的结果
	// * @return 字段名称
	// * @throws SQLException
	// */
	// String[] getAllTitle(ResultSet rs) throws SQLException
	// {
	// ResultSetMetaData rsmd = rs.getMetaData();
	// int count = rsmd.getColumnCount();
	// String[] title = new String[count];
	// for (int i = 1; i <= count; i++)
	// {
	// title[i - 1] = rsmd.getColumnName(i);
	// }
	// return title;
	// }

	// Variables declaration - do not modify//GEN-BEGIN:variables
	
	// / 筛选所有日期CheckBox
	private javax.swing.JCheckBox allDate;
	// / 显示筛选按钮
	private javax.swing.JButton column;
	// / 命令类型下拉框
	private javax.swing.JComboBox commandType;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane showTablePane;
	// / 表计类型下拉框
	private javax.swing.JComboBox meterType;
	// / 查询按钮
	private javax.swing.JButton query;
	// / 另存为按钮
	private javax.swing.JButton save;
	// / 日期选择器组件
	private DatePicker showDate;
	// / 数据网关下拉框
	private javax.swing.JComboBox terminal;
	private javax.swing.JLabel tips1;
	private javax.swing.JLabel tips2;
	private SenderWithOracle swo = new SenderWithOracle();
	private JTable table = null;
	// / 是否显示所有日期开关
	boolean isAllDate = false;
	// End of variables declaration//GEN-END:variables
	DataQueryTableModel queryTable = null;
	// / 选中列List
	ArrayList<String> selectCol = new ArrayList<>();
	JDialog selectColumnDialog = null;
}
