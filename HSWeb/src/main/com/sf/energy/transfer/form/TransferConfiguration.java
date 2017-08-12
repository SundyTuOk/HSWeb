/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sf.energy.transfer.form;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.sf.energy.util.Configuration;

/**
 * 终端设置窗体函数
 * 
 * @author cuizhengyang
 * @version v1.0
 * @since version v1.0
 */
public class TransferConfiguration extends javax.swing.JFrame
{

	/**
	 * Creates new form TerminalConfiguration
	 */
	public TransferConfiguration()
	{
		initComponents();
		this.setResizable(false);
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	/**
	 * 初始化控件
	 */
	private void initComponents()
	{

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		connType = new javax.swing.JComboBox();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		ipAddress = new javax.swing.JTextField();
		ipPort = new javax.swing.JTextField();
		reset = new javax.swing.JButton();
		submit = new javax.swing.JButton();
		String ip = null;
		String port = null;

		try {
			config = Configuration.getConfiguration();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		config.setAutoSave(true);
		ip = config.getString("transfer.transferIP");
		port = config.getString("transfer.transferPort");
		if (null != ip && !"".equals(ip))
		{
			ipAddress.setText(ip);
		}
		if (null != ip && !"".equals(ip))
		{
			ipPort.setText(port);
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("终端连接配置");

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("通讯方式：");

		connType.setModel(new javax.swing.DefaultComboBoxModel(new String[]
		{ "网络" }));

		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setText("侦 听 IP：");

		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel3.setText("侦听端口：");

		reset.setText("重置");
		reset.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				resetActionPerformed(evt);
			}
		});

		submit.setText("确定");
		submit.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				submitActionPerformed(evt);
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
										.addContainerGap(52, Short.MAX_VALUE)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(reset)
														.addGroup(
																jPanel1Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(
																				jLabel1)
																		.addComponent(
																				jLabel2)
																		.addComponent(
																				jLabel3)))
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(17,
																				17,
																				17)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addComponent(
																								ipPort,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								112,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								jPanel1Layout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.TRAILING)
																										.addComponent(
																												connType,
																												javax.swing.GroupLayout.Alignment.LEADING,
																												0,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												ipAddress,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												111,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addGap(29,
																				29,
																				29))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(49,
																				49,
																				49)
																		.addComponent(
																				submit)
																		.addContainerGap(
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(32, 32, 32)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(
																connType,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(21, 21, 21)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel2)
														.addComponent(
																ipAddress,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(28, 28, 28)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel3)
														.addComponent(
																ipPort,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(reset)
														.addComponent(submit))
										.addContainerGap(26, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addContainerGap()));
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
	//中转站作为服务器监听数据网关重置
	private void resetActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_resetActionPerformed
		// TODO add your handling code here:
		String ip = config.getString("transfer.transferIP");
		String port = config.getString("transfer.transferPort");
		if (null != ip && !"".equals(ip))
		{
			ipAddress.setText(ip);
		}
		if (null != ip && !"".equals(ip))
		{
			ipPort.setText(port);
		}
	}// GEN-LAST:event_resetActionPerformed
//中转站作为服务器监听数据网关确定
	private void submitActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_submitActionPerformed
		// TODO add your handling code here:
        //System.out.println("ooppoo");
		String ip = ipAddress.getText();
		String port = ipPort.getText();

		if (null == ip || "".equals(ip) || null == port || "".equals(port))
		{
			JOptionPane.showMessageDialog(this, "IP地址或端口号不能为空。");
			return;
		}

		if (!ip.matches("(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))"))
		{
			JOptionPane.showMessageDialog(this, "IP地址格式错误");
			return;
		}
		// 判断配置文件是否存在transferIP节点，存在则设值，不存在则添加节点并设值。
		if (config.getString("transfer.transferIP") != null)
		{
			config.setProperty("transfer.transferIP", ip);
		} else
		{
			config.addProperty("transfer.transferIP", ip);
		}
		// 判断配置文件是否存在transferPort节点，存在则设值，不存在则添加节点并设值。
		if (config.getString("transfer.transferPort") != null)
		{
			config.setProperty("transfer.transferPort", port);
		} else
		{
			config.addProperty("transfer.transferPort", ip);
		}
		//关闭进程
        System.exit(0);
        //重启进程
        transfer.startTransfer();
		this.dispose();
	}// GEN-LAST:event_submitActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	
	// / 通讯方式下拉框
	private javax.swing.JComboBox connType;
	// / 中转站IP文本框
	private javax.swing.JTextField ipAddress;
	// / 中转站端口号文本框
	private javax.swing.JTextField ipPort;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	// / 重置按钮
	private javax.swing.JButton reset;
	// / 确定按钮
	private javax.swing.JButton submit;
	// End of variables declaration//GEN-END:variables

	// /读取写入xml的基础类
	private XMLConfiguration config = null;
}
