package com.sf.energy.transfer.decode;

/**
 * 对收到的报文进行解析 
 * 对收到的报文进行解析的基类
 * @author 崔正阳
 * @version 1.0
 * @see [ReceiveGW]
 * @since [盛帆电子/数据中转站1.0]
 */
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Spring;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sf.energy.transfer.db.ReceiverWithOracle;
import com.sf.energy.util.CreateLog;

public class Receiver
{
	// / 实际报文起始下标
	protected int startIndex = 0;
	// / 连接数据中转站的终端地址
	public String terminalAddress = "002700bb00";
	// / 接收数据缓冲区
	protected byte receiveBuffer[] = null;
	// / 规约
	protected String protocol = "GW";
	// / 国网收到帧的RSEQ,便于数据中转站主动抄读收到回复时比较是否是上一命令的回复,由CommandBack在解析前赋值
	protected int rseq = 0;
	protected ReceiverWithOracle rd = new ReceiverWithOracle();

	public Receiver()
	{
	}

	/**
	 * 去掉FE前导符
	 */
	public void findStartIndex()
	{
		while ((receiveBuffer[startIndex] == 0xFE)
				&& (startIndex < receiveBuffer.length)) // 除去前导符FE
		{
			startIndex++;
		}
	}

	/**
	 * 获取连接数据中转站的终端地址，因规约不同，地址下标不同，所以由子类重载
	 * 
	 * @return 设备终端地址
	 */
	public String terminalAddressAssign()
	{
		return "";
	}

	/**
	 * 接收缓冲区16进制数组转为字符串
	 * 
	 * @param databuffer
	 *            接收缓冲区
	 * @param index
	 *            1表示发送数组;2表示接收数组
	 * @return 报文字符串
	 */
	public String hexBufferToStr(byte[] databuffer, int index)
	{
		return "";
	}

	/**
	 * 接收数组赋值
	 * 
	 * @param bufferA
	 *            接收缓冲区
	 */
	public void receiveBufferAssign(byte[] bufferA)
	{
	}

	/**
	 * 解析是否是主动上报的报文，包括心跳，报警等
	 * 
	 * @param commandKind
	 *            命令的种类（1是轮炒，2WEB命令，3主动上传）
	 * @return 如果是主动上传的报文类型则返回报文的xml解析否则 返回空字符串
	 * @throws SQLException
	 *             到数据库查询终端信息返回的错误
	 */
	public String parseAutoUp(int commandKind) throws SQLException
	{
		return "";
	}

	/**
	 * 对主动上报报文的回复
	 * 
	 * @return 组成的确认报文的字节数组
	 */
	public byte[] ack()
	{
		return null;
	}

	// / 对正常回复报文解析
	/**
	 * 对正常回复报文解析
	 * 
	 * @param operCode
	 *            操作员的编号
	 * @param taskNum
	 *            任务的编号
	 * @param lastCommand
	 *            执行的上一条命令
	 * @param lastPara
	 *            上一条执行命令的命令参数
	 * @return 解析的XML信息
	 */
	public String commandBack(String operCode, String taskNum,
			String lastCommand, String lastPara)
	{
		return "";
	}

	/**
	 * 特殊命令处理
	 * 
	 * @param lastCommand
	 *            上一条命令
	 * @param lastPara
	 *            上一条命令参数
	 * @param pseq
	 *            对应的序列号
	 * @param commandKind
	 *            命令类型
	 * @param parseValue
	 *            解析的
	 * @return 处理是否成功的标志
	 * @throws Exception
	 */
	public Boolean parseSpacialCommand(String lastCommand, String lastPara,
			int pseq, int commandKind, String parseValue) throws Exception
	{
		return true;
	}

	/**
	 * 清空上一命令代码
	 * 
	 * @param lastcommand
	 *            上一条命令
	 * @return 是否清空上一条命令
	 */
	public Boolean clearLastCommand(String lastcommand)
	{
		return true;
	}

	/**
	 * 清空某数据网关下所有表计
	 * 
	 * @param info
	 * @throws Exception
	 */
	protected void ClearTerminalMeters(String info) throws Exception
	{
		if (BackXmlPrepare(info))
		{
			rd.clearOneTerminal(terminalAddress);
		}
	}

	/**
	 * 添加修改某数据网关下表计
	 * 
	 * @param info
	 *            xml信息
	 * @param para
	 *            命令参数
	 * @param metertype
	 *            表的类型
	 * @param ifautoread
	 *            是否是自动抄读
	 * @throws Exception
	 */
	protected void modifyTerminalAddress(String info, String para,
			String meterType, String ifautoread) throws Exception
	{
		if (BackXmlPrepare(info))
		{
			rd.insertTerminal(terminalAddress, "GW", ifautoread);
			String[] meters = para.split(",");
			int meternum = (meters.length - 1) / 13;
			for (int i = 0; i < meternum; i++)
			{
				int meterid = Integer.parseInt(meters[13 * i + 1]);
				// String meteraddress = meters[13 * i + 6].PadLeft(12, '0');
				// //防止不足12位
				// String meterstyle = meters[13 * i + 12].PadLeft(2, '0');
				// rd.insertTerminalMeters(terminalAddress, meterid,
				// meteraddress, metertype, meterstyle);
			}
		}
	}

	/**
	 * xml的解析准备
	 * 
	 * @param info
	 *            XML信息
	 * @return 是否可以解析
	 * @throws Exception
	 */
	protected boolean BackXmlPrepare(String info) throws Exception
	{
		NodeList list = getNodeList(info, "commandback");
		Element commandBack = (Element) list.item(0);
		String error = commandBack.getAttribute("error");
		if (error == "0")
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * 自动抄读数据存入数据库
	 * 
	 * @param info
	 *            xml信息
	 * @param lastcommand
	 *            上一条命令
	 * @param pseq
	 *            报文序列号
	 * @return 若是上一条命令的回复返回字符串true,若不是上一条命令的回复返回字符串false
	 *         若error=0，要存入数据库，和上一条命令不匹配返回字符串false，若数据异常 返回报警的信息
	 */
	public String CVSave(String info, String lastcommand, int pseq)
	{
		String rtXml = info;
		NodeList list = null;
		String err;
		try
		{
			list = getNodeList(rtXml, "commandback");
			for (int i = 0; i < list.getLength(); i++)
			{
				Element commandBack = (Element) list.item(i);
				if (commandBack.hasAttribute("error"))
				{
					err = commandBack.getAttribute("error");
					// 失败
					if (err.equals("1"))
					{
						// 是上一命令的否认回答
						if (rseq == pseq)
						{
							return "true";
						} else
						{
							return "flase";
						}
					} else if (err.equals("0")) // 成功
					{
						String commandCode = commandBack.getAttribute("code");
						String[] str = commandCode.split(",");
						commandCode = str[0];
						if (!commandCode.equals(lastcommand))
						{
							return "flase";
						}
						// 只存afn+dt,如0C0110
						commandCode = commandCode.substring(0, 2)
								+ commandCode.substring(6, 10);
						if (commandCode.equals("0C0103")||commandCode.equals("0c0103")) // 三相数据
						{
							String alarmInfo = saveThreeValue(commandBack);
							return alarmInfo;
						} else

						{
							// 单相数据及水表数据
							saveValue(commandBack, commandCode);
						}
					}
				}
			}
		} catch (Exception ex)
		{
			CreateLog.writeLog("Receive.CVSave:" + info + " " + ex.toString());
		}
		return "true";
	}

	/**
	 * 存储单相电表的数据
	 * @param commandBack xml中的commandback节点
	 * @param commandCode 命令码
	 * @throws Exception
	 */
	private void saveValue(Element commandBack, String commandCode)
			throws Exception
	{
		NodeList result = commandBack.getElementsByTagName("result");
		for (int i = 0; i < result.getLength(); i++)
		{
			Element resultElement = (Element) result.item(i);
			String point = resultElement.getAttribute("AmMeter_Point");
			String meterAddress = resultElement.getAttribute("Meter_Address");
			String readTime = resultElement.getAttribute("ValueTime");
			String dataValue = resultElement.getAttribute("ZY0");
			
			// 调用数据库操作，获取表的类型
			ArrayList<String> meter = rd.pointToAddress(terminalAddress, point);
			String meterType = meter.get(1);
			
			if (!dataValue.equals("-1"))
			{
				rd.dataSave(terminalAddress, meterAddress, commandCode,
						readTime, dataValue, meterType);
			}
		}

	}
	/**
	 * 存储三项电表的数据
	 * @param commandBack xml中的commandback节点
	 * @return 若有报警信息返回报警的信息，没有的话返回空字符串
	 * @throws SQLException
	 */
	private String saveThreeValue(Element commandBack) throws SQLException
	{
		String alarm = "";
		String info = "";
		NodeList result = commandBack.getElementsByTagName("result");
		for (int i = 0; i < result.getLength(); i++)
		{
			Element resultElement = (Element) result.item(i);
			String meteraddress = resultElement.getAttribute("Meter_Address");
			String readtime = resultElement.getAttribute("ValueTime");

			ArrayList<String> dataArray = new ArrayList<String>();
			String powerzy = resultElement.getAttribute("PowerZY");
			dataArray.add("PowerZY," + powerzy);
			String poweray = resultElement.getAttribute("PowerAY");
			dataArray.add("PowerAY," + poweray);
			String powerby = resultElement.getAttribute("PowerBY");
			dataArray.add("PowerBY," + powerby);
			String powercy = resultElement.getAttribute("PowerCY");
			dataArray.add("PowerCY," + powercy);

			String powerzw = resultElement.getAttribute("PowerZW");
			dataArray.add("PowerZW," + powerzw);
			String poweraw = resultElement.getAttribute("PowerAW");
			dataArray.add("PowerAW," + poweraw);
			String powerbw = resultElement.getAttribute("PowerBW");
			dataArray.add("PowerBW," + powerbw);
			String powercw = resultElement.getAttribute("PowerCW");
			dataArray.add("PowerCW," + powercw);

			String powerFactorZ = resultElement.getAttribute("PowerFactorZ");
			dataArray.add("PowerFactorZ," + powerFactorZ);
			String powerFactorA = resultElement.getAttribute("PowerFactorA");
			dataArray.add("PowerFactorA," + powerFactorA);
			String powerFactorB = resultElement.getAttribute("PowerFactorB");
			dataArray.add("PowerFactorB," + powerFactorB);
			String powerFactorC = resultElement.getAttribute("PowerFactorC");
			dataArray.add("PowerFactorC," + powerFactorC);

			String voltagea = resultElement.getAttribute("VoltageA");
			dataArray.add("VoltageA," + voltagea);
			String voltageb = resultElement.getAttribute("VoltageB");
			dataArray.add("VoltageB," + voltageb);
			String voltagec = resultElement.getAttribute("VoltageC");
			dataArray.add("VoltageC," + voltagec);

			String currenta = resultElement.getAttribute("CurrentA");
			dataArray.add("CurrentA," + currenta);
			String currentb = resultElement.getAttribute("CurrentB");
			dataArray.add("CurrentB," + currentb);
			String currentc = resultElement.getAttribute("CurrentC");
			dataArray.add("CurrentC," + currentc);
			String current0 = resultElement.getAttribute("Current0");
			dataArray.add("Current0," + current0);

			String powerszz = resultElement.getAttribute("PowerSZZ");
			dataArray.add("PowerSZZ," + powerszz);
			String powersza = resultElement.getAttribute("PowerSZA");
			dataArray.add("PowerSZA," + powersza);
			String powerszb = resultElement.getAttribute("PowerSZB");
			dataArray.add("PowerSZB," + powerszb);
			String powerszc = resultElement.getAttribute("PowerSZC");
			dataArray.add("PowerSZC," + powerszc);

			boolean ifvalid = false;
			// 检查数据是否有效；
			for (String dataValue : dataArray)
			{
				if (dataValue.substring(dataValue.length() - 2,
						dataValue.length()) != "-1")
				{
					ifvalid = true;
					break;
				}
			}
			if (ifvalid)
			{
				alarm = rd.dataSaveThreePhase(terminalAddress, meteraddress,
						readtime, dataArray);
				if (alarm!=null&&!"".equals(alarm))
				{
					info += meteraddress + "," + readtime + ";" + alarm + "|";
				}
			}
		}

		return info;
	}

	/**
	 * 获得某个节点的
	 * @param xml XML字符串
	 * @param tagName 节点的名称
	 * @return 对应节点的element对象
	 * @throws Exception
	 */
	public NodeList getNodeList(String xml, String tagName) throws Exception
	{
		InputSource source = null;
		StringReader read = null;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dombuilder = domfac.newDocumentBuilder();

		read = new StringReader(xml);

		source = new InputSource(read);

		Document document = dombuilder.parse(source);
		NodeList nodeList = document.getElementsByTagName(tagName);
		return nodeList;
	}

	public int getStartIndex()
	{
		return startIndex;
	}

	public void setStartIndex(int startIndex)
	{
		this.startIndex = startIndex;
	}

	public String getTerminalAddress()
	{
		return terminalAddress;
	}

	public void setTerminalAddress(String terminalAddress)
	{
		this.terminalAddress = terminalAddress;
	}

	public byte[] getReceiveBuffer()
	{
		return receiveBuffer;
	}

	public void setReceiveBuffer(byte[] receiveBuffer)
	{
		this.receiveBuffer = receiveBuffer;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public void setProtocol(String protocol)
	{
		this.protocol = protocol;
	}

	public int getRseq()
	{
		return rseq;
	}

	public void setRseq(int rseq)
	{
		this.rseq = rseq;
	}

	public ReceiverWithOracle getRd()
	{
		return rd;
	}

	public void setRd(ReceiverWithOracle rd)
	{
		this.rd = rd;
	}

}
