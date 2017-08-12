/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sf.energy.transfer.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.sf.energy.util.Configuration;


/**
 * 数据源设置窗体
 * 
 * @author cuizhengyang
 * @version v1.0
 * @since version v1.0
 */
public class DataConfiguration extends javax.swing.JFrame
{

	/**
	 * 构造函数
	 */
	public DataConfiguration()
	{
		initComponents();
		this.setResizable(false);
	}

	/**
	 * 初始化窗体
	 */
	@SuppressWarnings("unchecked")
	private void initComponents()
	{
		
			try {
				config=Configuration.getConfiguration();
			} catch (ConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			config.setAutoSave(true);
		
		jPanel1 = new javax.swing.JPanel();
		scan = new javax.swing.JButton();
		pathSubmit = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		backupDays = new javax.swing.JTextField();
		backupSubmit = new javax.swing.JButton();
		manualBackup = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		filePath = new javax.swing.JTextField();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("数据源配置");

		scan.setText("浏览");
		scan.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				scanActionPerformed(evt);
			}
		});

		pathSubmit.setText("确定");
		pathSubmit.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				pathSubmitActionPerformed(e);
			}
		});

		jLabel2.setText("自动备份定时：");

		backupSubmit.setText("确定");
		backupSubmit.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				backupSubmitAnctionPerformed(e);
			}
		});	

		manualBackup.setText("手动备份");

		jLabel3.setText("输入或选择文件的全路径：");

		jLabel4.setText("天");


		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel2,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												94,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												backupDays,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												115,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												jLabel4)
																										.addGap(120,
																												120,
																												120)
																										.addComponent(
																												backupSubmit))
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												filePath,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												317,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												scan)))
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				pathSubmit))
														.addComponent(
																manualBackup)
														.addComponent(jLabel3))
										.addContainerGap(43, Short.MAX_VALUE)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(22, 22, 22)
										.addComponent(jLabel3)
										.addGap(8, 8, 8)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(scan)
														.addComponent(
																pathSubmit)
														.addComponent(
																filePath,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(24, 24, 24)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel2)
														.addComponent(
																backupSubmit)
														.addComponent(
																backupDays,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel4))
										.addGap(29, 29, 29)
										.addComponent(manualBackup)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}
	
	/**
	 * 点击浏览按钮的响应事件
	 * @param evt 单击事件
	 */
	private void scanActionPerformed(java.awt.event.ActionEvent evt)
	{
		JFileChooser fc = new JFileChooser();
		File directory = new File(dir);
		// 打开的窗口路径为当前路径
		fc.setCurrentDirectory(directory);
		fc.setDialogTitle("打开文件");
		// 添加文件过滤
		FileNameExtensionFilter filter1 = new FileNameExtensionFilter(".xls",
				"xls");
		fc.addChoosableFileFilter(filter1);
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter(".dmp",
				"dmp");
		fc.addChoosableFileFilter(filter1);
		// 设置文件过滤
		fc.setFileFilter(fc.getAcceptAllFileFilter());
		int intRetVal = fc.showOpenDialog(new JFrame());
		// 已经选择了文件
		if (intRetVal == JFileChooser.APPROVE_OPTION)
		{
			// 取得已选择文件的路径
			dir = fc.getSelectedFile().getPath();
			// 在文本域中显示此路径
			filePath.setText(dir);
		}

	}
	
	private void pathSubmitActionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		String path=filePath.getText();
		if(path==null||"".equals(path))
		{
			JOptionPane.showMessageDialog(this, "请输入或选择路径！");
			return;
		}
		
		if(config.getString("dbBackup.backupPath")!=null)
		    config.setProperty("dbBackup.backupPath", path);
		else
			config.addProperty("dbBackup.backupPath", path);
		JOptionPane.showMessageDialog(this, "设置路径成功！");
	}

	private void backupSubmitAnctionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	private static String dir = ".";
	// Variables declaration - do not modify//GEN-BEGIN:variables
	// /设定自动备份天数的文本框
	private javax.swing.JTextField backupDays;
	// /设定备份天数的确定按钮
	private javax.swing.JButton backupSubmit;
	// /设定路径的文本框
	private javax.swing.JTextField filePath;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel1;
	// /手动备份按钮
	private javax.swing.JButton manualBackup;
	// /设定路径的确定按钮
	private javax.swing.JButton pathSubmit;
	// /浏览按钮
	private javax.swing.JButton scan;
	// /读取写入xml的基础类
	private XMLConfiguration config = null;
	// End of variables declaration//GEN-END:variables
}
