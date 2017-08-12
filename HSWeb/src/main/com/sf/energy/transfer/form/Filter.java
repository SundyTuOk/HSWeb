/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sf.energy.transfer.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sf.energy.transfer.db.SenderWithOracle;

/**
 * 
 * 数据中转站筛选条件界面
 * @author lujinquan
 * @version v1.0
 * @since version v1.0
 */
public class Filter extends javax.swing.JFrame
{

	/**
	 * Creates new form Filter
	 */
	public Filter(ShowInformation si)
	{
		initComponents();
		this.si=si;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents()
	{

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();

		submit = new javax.swing.JButton();

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

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("筛选条件");

		jLabel1.setText("数据网关：");

		submit.setText("确定");
		submit.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				submitActionPerformed(e);
			}

		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(48,
																				48,
																				48)
																		.addComponent(
																				jLabel1)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				terminal,
																				0,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addContainerGap(
																				167,
																				Short.MAX_VALUE)
																		.addComponent(
																				submit)))
										.addGap(53, 53, 53)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(17, 17, 17)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(
																terminal,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												18, Short.MAX_VALUE)
										.addComponent(submit).addContainerGap()));

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
	

	private void submitActionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		String filter=(String) terminal.getSelectedItem();
		si.setShowTerminalAddress(filter);
		this.dispose();
	}

	SenderWithOracle swo = new SenderWithOracle();
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel1;
	// / 确定按钮
	private javax.swing.JButton submit;
	// / 数据网关下拉框
	private javax.swing.JComboBox terminal;
	// / ShowInformation对象实例
	private ShowInformation si = null;
	// End of variables declaration//GEN-END:variables
}