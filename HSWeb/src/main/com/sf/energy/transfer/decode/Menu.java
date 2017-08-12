package com.sf.energy.transfer.decode;
/**
 *为解析报文提供具体实现类
 *根据具体的规约从ArrayList中选取具体的实现类，Arraylist中的 第一个是国网的实体类
 *@author czy
 *@version 1.0
 *@see [TraGateRec/sRecvData]
 *@since [盛帆电子/数据中转站1.0]
 */

import java.util.ArrayList;

public class Menu {
	
	ArrayList<Receiver> recMenu=new ArrayList<Receiver>();
	public Menu() {
		
		ReceiveGW gw=new ReceiveGW();
		recMenu.add(gw);
	}

}
