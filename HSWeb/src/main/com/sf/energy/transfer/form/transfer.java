/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sf.energy.transfer.form;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

import com.sf.energy.transfer.communication.GeneralCommunication;
import com.sf.energy.transfer.tranReciveMessage.AutoUp;
import com.sf.energy.util.Configuration;

/**
 * 
 * <数据中转站主界面>
 * 
 * @author 陆锦泉
 * @version v1.0
 * @since version v1.0
 */
public class transfer extends javax.swing.JFrame
{
	static Logger logger=Logger.getLogger(transfer.class);

	public transfer()
	{
		XMLConfiguration config = null;
		try {
			config = Configuration.getConfiguration();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /获取界面显示的最大行数
		int maxNum = Integer.parseInt(config.getString("showNum.maxNum"));

		initComponents();
		
		si = new ShowInformation(jScrollPane1, jScrollPane2);
		
		si.setShowMaxnum(maxNum);
		//监听端口
//		gc = new GeneralCommunication(si);
	
//		gc.startTranClient();
		logger.info("执行完界面的初始化");
	}

	@SuppressWarnings("unchecked")
	private void initComponents()
	{

		jPanel3 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		showConnInfo = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		showDatagram = new javax.swing.JTextArea();
		jMenuBar1 = new javax.swing.JMenuBar();
		configure = new javax.swing.JMenu();
		serverConfigure = new javax.swing.JMenuItem();
		terminalConfigure = new javax.swing.JMenuItem();
		dataConfigure = new javax.swing.JMenuItem();
		operation = new javax.swing.JMenu();
		startAutoRead = new javax.swing.JCheckBoxMenuItem();
		stopAutoRead = new javax.swing.JCheckBoxMenuItem();
		jSeparator1 = new javax.swing.JPopupMenu.Separator();
		lonworksUp = new javax.swing.JCheckBoxMenuItem();
		lonworksOff = new javax.swing.JCheckBoxMenuItem();
		jSeparator2 = new javax.swing.JPopupMenu.Separator();
		lampUP = new javax.swing.JCheckBoxMenuItem();
		lampOff = new javax.swing.JCheckBoxMenuItem();
		jSeparator3 = new javax.swing.JPopupMenu.Separator();
		classroomUP = new javax.swing.JCheckBoxMenuItem();
		classroomOFF = new javax.swing.JCheckBoxMenuItem();
		jSeparator4 = new javax.swing.JPopupMenu.Separator();
		startAutoUp = new javax.swing.JCheckBoxMenuItem();
		stopAutoUp = new javax.swing.JCheckBoxMenuItem();
		query = new javax.swing.JMenu();
		infoQuery = new javax.swing.JMenuItem();
		dataQuery = new javax.swing.JMenuItem();
		datagaram = new javax.swing.JMenu();
		filter = new javax.swing.JMenuItem();
		jSeparator5 = new javax.swing.JPopupMenu.Separator();
		showAllData = new javax.swing.JMenuItem();
		showSendData = new javax.swing.JMenuItem();
		showRecvData = new javax.swing.JMenuItem();
		stopShowData = new javax.swing.JMenuItem();
		help = new javax.swing.JMenu();
		about = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("数据中转站");

		//jScrollPane2.setLayout(new BorderLayout());
		showConnInfo.setColumns(20);
		showConnInfo.setRows(5);
		showConnInfo.setEditable(false);
		jScrollPane1.setViewportView(showConnInfo);

		showDatagram.setColumns(20);
		showDatagram.setRows(5);
		showDatagram.setEditable(false);
		jScrollPane2.setViewportView(showDatagram);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jScrollPane1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				642,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jScrollPane2)))
										.addContainerGap()));
		jPanel3Layout
				.setVerticalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												193,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jScrollPane2,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												235, Short.MAX_VALUE)
										.addContainerGap()));

		configure.setText("配置");

		serverConfigure.setText("服务器连接配置");
		serverConfigure.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				serverConfigureActionPerformed(evt);
			}
		});
		configure.add(serverConfigure);

		terminalConfigure.setText("终端连接配置");
		terminalConfigure.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				terminalConfigureActionPerformed(evt);
			}
		});
		configure.add(terminalConfigure);

		dataConfigure.setText("数据源配置");
		dataConfigure.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				dataConfigureActionPerformed(evt);
			}
		});
		configure.add(dataConfigure);

		jMenuBar1.add(configure);

		// 操作菜单的设计
		operation.setText("操作");

		
		 /*是否启动自动抄表的组件*/
		startAutoRead.setSelected(true);
		startAutoRead.setText("启动自动抄表");
		startAutoRead.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent e)
			{
				//gc.getTick().setIfAutoReadMeter(true);
//				gc.getAutoReadTask().ifAutoRead.set(true);
			}
		});
		operation.add(startAutoRead);

		stopAutoRead.setText("停止自动抄表");
		stopAutoRead.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
//				gc.getAutoReadTask().ifAutoRead.set(false);
			}
		});
		operation.add(stopAutoRead);
		autoReadGroup = new ButtonGroup();
		autoReadGroup.add(startAutoRead);
		autoReadGroup.add(stopAutoRead);
		operation.add(jSeparator1);

		/*是否启动自动抄Lonworks的组件*/
		lonworksUp.setSelected(true);
		lonworksUp.setText("启动自动抄Lonworks");
		lonworksUp.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
//				gc.getTick().setIfAutoReadLon(true);
			}
		});
		operation.add(lonworksUp);
		
		lonworksOff.setText("停止自动抄Lonworks");
		lonworksOff.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
//				gc.getTick().setIfAutoReadLon(false);
			}
		});
		lonworksGroup = new ButtonGroup();
		lonworksGroup.add(lonworksUp);
		lonworksGroup.add(lonworksOff);
		operation.add(lonworksOff);
		operation.add(jSeparator2);

		/*是否启动自动抄路灯的组件*/
		lampUP.setSelected(true);
		lampUP.setText("启动自动抄路灯");
		lampUP.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
//				gc.getTick().setIfAutoReadLamp(true);
			}
		});
		operation.add(lampUP);
		
		lampOff.setText("停止自动抄读路灯");
		lampOff.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
//				gc.getTick().setIfAutoReadLamp(false);
			}
		});
		lampGroup = new ButtonGroup();
		lampGroup.add(lampUP);
		lampGroup.add(lampOff);
		operation.add(lampOff);
		operation.add(jSeparator3);
		
		/*是否启动自动抄教室照明的组件*/
		classroomUP.setSelected(true);
		classroomUP.setText("启动自动抄教室照明");
		classroomUP.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
//				gc.getTick().setIfAutoReadClassroom(true);
			}
		});
		
		operation.add(classroomUP);
		classroomOFF.setText("停止自动抄教室照明");
		classroomOFF.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
//				gc.getTick().setIfAutoReadClassroom(false);
			}
		});
		operation.add(classroomOFF);
		classroomGroup = new ButtonGroup();
		classroomGroup.add(classroomUP);
		classroomGroup.add(classroomOFF);
		operation.add(jSeparator4);
		
		/*是否启动自动抄教室照明的组件*/
		startAutoUp.setSelected(true);
		startAutoUp.setText("启动上传服务器");
		startAutoUp.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
//				gc.getTick().setIfAutoUp(true);
			}
		});
		operation.add(startAutoUp);

		stopAutoUp.setSelected(true);
		stopAutoUp.setText("停止上传服务器");
		stopAutoUp.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
//				gc.getTick().setIfAutoUp(false);
			}
		});
		operation.add(stopAutoUp);

		autoUpGroup = new ButtonGroup();
		autoUpGroup.add(startAutoUp);
		autoUpGroup.add(stopAutoUp);
		jMenuBar1.add(operation);

		// 查询菜单的设计
		query.setText("查询");

		infoQuery.setText("信息查询");
		infoQuery.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				infoQueryActionPerformed(evt);
			}
		});
		query.add(infoQuery);

		dataQuery.setText("数据查询");
		dataQuery.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				dataQueryActionPerformed(e);
			}
		});
		query.add(dataQuery);

		jMenuBar1.add(query);

		datagaram.setText("报文");

		filter.setText("筛选条件");
		filter.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				filterActionPerformed(evt);
			}
		});
		datagaram.add(filter);
		datagaram.add(jSeparator5);

		showAllData.setText("显示全部报文");
		showAllData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllDataActionPerformed(evt);
            }
        });
        datagaram.add(showAllData);

        showSendData.setText("显示发送报文");
        showSendData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showSendDataActionPerformed(evt);
            }
        });
        datagaram.add(showSendData);

        showRecvData.setText("显示接收报文");
        showRecvData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showRecvDataActionPerformed(evt);
            }
        });
        datagaram.add(showRecvData);

        stopShowData.setText("停止显示报文");
        stopShowData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopShowDataActionPerformed(evt);
            }
        });
		datagaram.add(stopShowData);

		jMenuBar1.add(datagaram);

		help.setText("帮助");

		about.setText("关于");
		help.add(about);

		jMenuBar1.add(help);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}
		

	private void terminalConfigureActionPerformed(java.awt.event.ActionEvent evt)
	{
		new TransferConfiguration().setVisible(true);
	}

	private void infoQueryActionPerformed(java.awt.event.ActionEvent evt)
	{
		new Inquiry().setVisible(true);
	}

	private void dataQueryActionPerformed(ActionEvent e)
	{
		new DataQuery().setVisible(true);
	}

	private void dataConfigureActionPerformed(java.awt.event.ActionEvent evt)
	{
		new DataConfiguration().setVisible(true);
	}

	

	private void jCheckBoxMenuItem7ActionPerformed(
			java.awt.event.ActionEvent evt)
	{

	}

	private void serverConfigureActionPerformed(java.awt.event.ActionEvent evt)
	{

		new ServerConfiguration().setVisible(true);
	}

	private void filterActionPerformed(java.awt.event.ActionEvent evt)
	{
		new Filter(si).setVisible(true);
	}
	
	//筛选条件显示全部报文响应事件
    private void showAllDataActionPerformed(java.awt.event.ActionEvent evt) {  
    	si.setShowMessage(true);
        si.setShowSend(true);
        si.setShowReceive(true);
    } 
    //筛选条件显示发送报文响应事件
    private void showSendDataActionPerformed(java.awt.event.ActionEvent evt) {
    	si.setShowMessage(true);
        si.setShowSend(true);
        si.setShowReceive(false);
    } 
    //筛选条件显示接收报文响应事件
    private void showRecvDataActionPerformed(java.awt.event.ActionEvent evt) {
    	si.setShowMessage(true);
        si.setShowSend(false);
        si.setShowReceive(true);
    }                                            
    //筛选条件停止显示报文响应事件
    private void stopShowDataActionPerformed(java.awt.event.ActionEvent evt) {                                             
        si.setShowMessage(false);
    } 

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[])
	{
		startTransfer();
	}
	
	public static void startTransfer()
	{
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(transfer.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(transfer.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(transfer.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(transfer.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				logger.info("启动中专站界面！");
				new transfer().setVisible(true);
			}
		});
	}

	// /关于菜单项
	private javax.swing.JMenuItem about;
	// /配置菜单
	private javax.swing.JMenu configure;
	// /数据源配置菜单项
	private javax.swing.JMenuItem dataConfigure;
	// /数据查询菜单项
	private javax.swing.JMenuItem dataQuery;
	// /报文菜单
	private javax.swing.JMenu datagaram;
	// /“筛选条件”菜单项
	private javax.swing.JMenuItem filter;
	// /"帮助"菜单
	private javax.swing.JMenu help;
	// /"信息查询"菜单项
	private javax.swing.JMenuItem infoQuery;
	// /"启动自动抄LONWORKS"菜单项
	private javax.swing.JCheckBoxMenuItem lonworksUp;
	// /"停止自动抄LONWORKS"菜单项
	private javax.swing.JCheckBoxMenuItem lonworksOff;
	private ButtonGroup lonworksGroup;
	// /"启动自动抄路灯"菜单项
	private javax.swing.JCheckBoxMenuItem lampUP;
	// /"停止自动抄路灯"菜单项
	private javax.swing.JCheckBoxMenuItem lampOff;
	private ButtonGroup lampGroup;
	// /"启动自动抄教室照明"菜单项
	private javax.swing.JCheckBoxMenuItem classroomUP;
	// /"停止自动抄教室照明"菜单项
	private javax.swing.JCheckBoxMenuItem classroomOFF;
	private ButtonGroup classroomGroup;
	// /整个菜单的集合组件
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JPanel jPanel3;
	// /显示连接信息的屏幕
	private javax.swing.JScrollPane jScrollPane1;
	// /显示数据的屏幕
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JPopupMenu.Separator jSeparator1;
	private javax.swing.JPopupMenu.Separator jSeparator2;
	private javax.swing.JPopupMenu.Separator jSeparator3;
	private javax.swing.JPopupMenu.Separator jSeparator4;
	private javax.swing.JPopupMenu.Separator jSeparator5;
	// /"操作"菜单
	private javax.swing.JMenu operation;
	// /"查询"菜单
	private javax.swing.JMenu query;
	// /"服务器连接配置"菜单项
	private javax.swing.JMenuItem serverConfigure;
	// /"显示全部报文"菜单项
	private javax.swing.JMenuItem showAllData;
	// /显示连接信息文本域
	private javax.swing.JTextArea showConnInfo;
	// /显示数据信息文本域
	private javax.swing.JTextArea showDatagram;
	// /"显示接收报文"菜单项
	private javax.swing.JMenuItem showRecvData;
	// /"显示发送报文"菜单项
	private javax.swing.JMenuItem showSendData;
	// /"启动自动抄表"菜单项
	private javax.swing.JCheckBoxMenuItem startAutoRead;
	// /"启动上传服务器"菜单项
	private javax.swing.JCheckBoxMenuItem startAutoUp;
	// /"停止自动抄表"菜单项
	private javax.swing.JCheckBoxMenuItem stopAutoRead;
	// /"停止上传服务器"菜单项
	private javax.swing.JCheckBoxMenuItem stopAutoUp;
	// /"停止显示报文"菜单项
	private javax.swing.JMenuItem stopShowData;
	// /"终端连接配置"菜单项
	private javax.swing.JMenuItem terminalConfigure;
	private ButtonGroup autoReadGroup;
	private ButtonGroup autoUpGroup;
	// /ShowInformation对象实例
	private ShowInformation si;
	private GeneralCommunication gc = null;
}
