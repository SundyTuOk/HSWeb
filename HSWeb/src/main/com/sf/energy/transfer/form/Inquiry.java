
package com.sf.energy.transfer.form;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.sf.energy.transfer.db.SenderWithOracle;

/**
 * 信息查询窗体
 * 
 * @author cuizhengyang
 * @version v1.0
 * @since version v1.0
 */
public class Inquiry extends javax.swing.JFrame
{

	/**
	 * Creates new form Inquiry
	 */
	public Inquiry()
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

			for(int i=0;i<dgAddress.length;i++)
			{
				name[i+1] = dgAddress[i];
				mode.addElement(name[i+1]);
			}
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(this, "数据库连接发生错误！");
		}

		terminal = new javax.swing.JComboBox(mode);
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		meterType = new javax.swing.JComboBox();
		meterStyle = new javax.swing.JComboBox();
		submit = new javax.swing.JButton();
		save = new javax.swing.JButton();
		showTablePane = new javax.swing.JScrollPane();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("信息查询");
		jLabel1.setText("数据网关：");

		jLabel2.setText("表计类型：");
		
		jLabel3.setText("      共计   0  条");
		meterType.setModel(new DefaultComboBoxModel(new String[]
		{ "电表", "水表" }));
		meterType.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				meterTypeActionPerformed(e);
			}
		});

		meterStyle.setModel(new DefaultComboBoxModel(new String[]
		{ "单相表", "三相表" }));

		submit.setText("查询");
		submit.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				submitActionPerformed(evt);
			}
		});

		save.setText("另存为");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(	javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(21, 21, 21)
										.addComponent(jLabel1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												terminal,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												80,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												58, Short.MAX_VALUE)
										.addComponent(jLabel2)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												meterType,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												meterStyle,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(63, 63, 63)
										.addComponent(
												submit,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												65,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(save).addGap(32, 32, 32))
						.addComponent(showTablePane).addComponent(jLabel3));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
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
														.addComponent(
																meterStyle,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(submit)
														.addComponent(save)
														)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												showTablePane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												293, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jLabel3)
										)
										
										
										);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * 设备类型的监听器
	 * 
	 * @param e
	 *            单击事件
	 */
	private void meterTypeActionPerformed(ItemEvent e)
	{
		if (e.getStateChange() == ItemEvent.SELECTED)
		{
			String type = (String) meterType.getSelectedItem();
			if (type.equals("电表"))
			{
				meterStyle.setVisible(true);
			} else
			{
				meterStyle.setVisible(false);
			}
		}

	}
	/**
	 * 查询按钮的响应时间
	 * @param evt 单击事件
	 */
	private void submitActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_submitActionPerformed

		String type = (String) meterType.getSelectedItem();
		String style = (String) meterStyle.getSelectedItem();
		String dgAddress = "";
		if (!"所有网关".equals((String) terminal.getSelectedItem()))
		{
			dgAddress = (String) terminal.getSelectedItem();
		}
		
		try
		{
			table = swo.queryMeter(type, style, dgAddress,jLabel3);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "queryMeter（）函数报错");
		}
		
		table.setVisible(true);
		showTablePane.setViewportView(table);
	}// GEN-LAST:event_submitActionPerformed
	
//	/**
//	 * 传入结果集，和表的字段名称生成一个JTable 的组件
//	 * @param title 字段名称数组	
//	 * @param rs 表查出来的结果集
//	 * @return JTable 组件
//	 * @throws SQLException
//	 */
//	JTable getJTable(String title[], ResultSet rs) throws SQLException
//	{
//		// 获取列数，
//		int col = title.length;
//		// 获取行数
//		rs.last();
//		int row = rs.getRow();
//		// 获取表格信息
//		Object[][] info = new Object[row][col];
//		int count = 0;
//		rs.beforeFirst();
//		while (rs.next())
//		{
//			for (int i = 1; i <= col; i++)
//			{
//				info[count][i-1] = rs.getString(i);
//			}
//			count++;
//		}
//		JTable table = new JTable(info, title);
//		return table;
//	}
//	/**
//	 * 查询结果集的所有字段名称
//	 * @param rs 查询到的结果
//	 * @return 字段名称
//	 * @throws SQLException
//	 */
//	String[] getTitle(ResultSet rs) throws SQLException
//	{
//	ResultSetMetaData rsmd=rs.getMetaData();
//	int count=rsmd.getColumnCount();
//	String[] title=new String[count];
//	for(int i=1;i<=count;i++)
//	{
//	title[i-1]=rsmd.getColumnName(i);
//	}
//	return title;
//	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	// /显示JTable的组件
	private javax.swing.JScrollPane showTablePane;
	// /表计类型的设定下拉框
	private javax.swing.JComboBox meterStyle;
	// /电表还是水表的下拉框
	private javax.swing.JComboBox meterType;
	// /“另存为” 按钮
	private javax.swing.JButton save;
	// /“查询”按钮
	private javax.swing.JButton submit;
	// /“数据网关”的下拉框
	private javax.swing.JComboBox terminal;
	// /要显示的信息表
	private JTable table;
	SenderWithOracle swo = new SenderWithOracle();
	// End of variables declaration//GEN-END:variables
	
}
