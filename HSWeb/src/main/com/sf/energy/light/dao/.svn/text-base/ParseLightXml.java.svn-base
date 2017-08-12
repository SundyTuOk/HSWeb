package com.sf.energy.light.dao;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sf.energy.light.model.SLControlLineModel;
import com.sf.energy.light.model.SlHistoryModel;
import com.sf.energy.util.ConnDB;

public class ParseLightXml
{
	/**
	 * 解析路灯xml，并且更新路灯数据
	 * 
	 * @param info
	 *            路灯xml
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws SQLException
	 */
	public boolean updateLightMes(String info) throws ParserConfigurationException, SAXException, IOException, SQLException
	{
		//
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(info)));
		Element root = doc.getDocumentElement();
		Element gy = (Element) root.getFirstChild();
		NodeList children = root.getChildNodes();
		String terminaladdress = gy.getAttribute("terminaladdress");
		NodeList rootList = doc.getElementsByTagName("commandback");
		Element node = (Element) rootList.item(0);
		String code = node.getAttribute("code");
		String error = node.getAttribute("error");
				
	
		if ("1".equals(error))
		{
			return false;
		}
		SLControlLineModel slControlLineModel = new SLControlLineModel();
		int sLGATHER_ID = getgather(terminaladdress);
		String[] a = code.split(",");
		slControlLineModel.setSLGATHER_ID(sLGATHER_ID);
				
		
		slControlLineModel.setSLKONGZHI_485ADDRESS(a[3]);

		if (a.length == 4)
		{
			NodeList resultList = doc.getElementsByTagName("result");
			Element resultNode = (Element) resultList.item(0);
			String resultValue = resultNode.getAttribute("value");

			String[] result = resultValue.split(",");

			slControlLineModel.setLineState01(result[3]);
			slControlLineModel.setLineState02(result[4]);
			String time = "";
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			time = formatter.format(date);
			slControlLineModel.setLastTime(time);
			updateReadSLkongzhi(slControlLineModel);

			return true;
		} else
		{
			slControlLineModel.setLineState01(a[4]);
			slControlLineModel.setLineState02(a[5]);
			if ("auto".equals(a[4]))
			{
				slControlLineModel.setLamp_state11("auto");
			} else
			{
				slControlLineModel.setLamp_state11("manual");
			}
			if ("auto".equals(a[5]))
			{
				slControlLineModel.setLamp_state21("auto");
			} else
			{
				slControlLineModel.setLamp_state21("manual");
			}
			String time = "";
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			time = formatter.format(date);
			slControlLineModel.setLastTime(time);
			updateSLkongzhi(slControlLineModel);

			return true;
		}

	}
	/**
	 * 解析路灯xml，并且更新路灯数据
	 * 
	 * @param info
	 *            路灯xml
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws SQLException
	 */
	public boolean updateLightMes(String info,String kongzhiAddress,String State01,String State02) throws ParserConfigurationException, SAXException, IOException, SQLException
	{
		//
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(info)));
		Element root = doc.getDocumentElement();
		Element gy = (Element) root.getFirstChild();
		NodeList children = root.getChildNodes();
		String terminaladdress = gy.getAttribute("terminaladdress");
		NodeList rootList = doc.getElementsByTagName("commandback");
		Element node = (Element) rootList.item(0);
		String code = node.getAttribute("code");
		String error = node.getAttribute("error");
				
	
		if ("1".equals(error))
		{
			return false;
		}
		SLControlLineModel slControlLineModel = new SLControlLineModel();
		int sLGATHER_ID = getgather(terminaladdress);
		String[] a = code.split(",");
		slControlLineModel.setSLGATHER_ID(sLGATHER_ID);
		
		if (kongzhiAddress.length()>=12)//电表当路灯控制器
		{
			slControlLineModel.setSLKONGZHI_485ADDRESS(kongzhiAddress);
			//<?xml version="1.0" encoding="UTF-8" standalone="no"?><SFROOT gy="GW"><GW identify="1" ip="/192.168.9.157:3163" isend="1" opercode="管理员" tasknum="160527091954678" terminaladdress="002700C200"><commandback code="1000000100" error="0" errormessage=""><result AmMeter_Point="" Meter_Address="001501022414" ValueTime="2016-05-27 09:19:53"/></commandback></GW></SFROOT>
			//<?xml version="1.0" encoding="UTF-8" standalone="no"?><SFROOT gy="GW"><GW identify="1" ip="/192.168.9.157:3721" isend="1" opercode="管理员" tasknum="160527100800122" terminaladdress="002700C200"><commandback code="1000000100,30,04000503,3,2400,e,8,1,1,60,10,001501022414,02000000,," error="0" errormessage=""><result AmMeter_Point="" DataID="04000503" Flag="10" Meter_Address="001501022414" ValueTime="2016-05-27 10:08:08"/></commandback></GW></SFROOT>
			
			if (a.length == 1)//设置命令
			{
				slControlLineModel.setLineState01(State01);
				slControlLineModel.setLineState02(State02);
				String time = "";
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				time = formatter.format(date);
				slControlLineModel.setLastTime(time);
				updateReadSLkongzhi(slControlLineModel);
				slControlLineModel.setLamp_state11("manual");
				slControlLineModel.setLamp_state21("manual");
				updateSLkongzhi(slControlLineModel);
			}
			else 
			{
				//抄读状态命令
				//Element result = (Element) node.getChildNodes();
				
				NodeList result = node.getElementsByTagName("result");
				if(result.getLength()>0)
				{
					Element resultElement = (Element) result.item(0);
				String	Flag=resultElement.getAttribute("Flag");
				
				if(Flag.equals("0")||Flag.equals("00")||Flag.equals("10"))
				{
					slControlLineModel.setLineState01("on");
					slControlLineModel.setLineState02("on");
									
				}
				else {
					slControlLineModel.setLineState01("off");
					slControlLineModel.setLineState02("off");
				}
				String time = "";
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				time = formatter.format(date);
				slControlLineModel.setLastTime(time);
				updateReadSLkongzhi(slControlLineModel);
				}
			}
			
			
			
			
//手工开灯或关灯数据库自动修改为手工模式
			//slControlLineModel.setLamp_state11("auto");
			//slControlLineModel.setLamp_state11("manual");
			
			return true;
		}
		
		
		
		slControlLineModel.setSLKONGZHI_485ADDRESS(a[3]);

		if (a.length == 4)
		{
			NodeList resultList = doc.getElementsByTagName("result");
			Element resultNode = (Element) resultList.item(0);
			String resultValue = resultNode.getAttribute("value");

			String[] result = resultValue.split(",");

			slControlLineModel.setLineState01(result[3]);
			slControlLineModel.setLineState02(result[4]);
			String time = "";
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			time = formatter.format(date);
			slControlLineModel.setLastTime(time);
			updateReadSLkongzhi(slControlLineModel);

			return true;
		} else
		{
			slControlLineModel.setLineState01(a[4]);
			slControlLineModel.setLineState02(a[5]);
			if ("auto".equals(a[4]))
			{
				slControlLineModel.setLamp_state11("auto");
			} else
			{
				slControlLineModel.setLamp_state11("manual");
			}
			if ("auto".equals(a[5]))
			{
				slControlLineModel.setLamp_state21("auto");
			} else
			{
				slControlLineModel.setLamp_state21("manual");
			}
			String time = "";
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			time = formatter.format(date);
			slControlLineModel.setLastTime(time);
			updateSLkongzhi(slControlLineModel);

			return true;
		}

	}
	/**
	 * 解析路灯xml，并且更新路灯数据
	 * 
	 * @param info
	 *            路灯xml
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws SQLException
	 */
	public boolean checkTime(String info) throws ParserConfigurationException, SAXException, IOException, SQLException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(info)));
		Element root = doc.getDocumentElement();
		Element gy = (Element) root.getFirstChild();
		NodeList children = root.getChildNodes();
		String terminaladdress = gy.getAttribute("terminaladdress");
		NodeList rootList = doc.getElementsByTagName("commandback");
		Element node = (Element) rootList.item(0);
		String error = node.getAttribute("error");
		if ("1".equals(error))
		{
			return false;
		}
		else
			return true;

	}

	/**
	 * 判断这条xml是否报错
	 * 
	 * @param info
	 * @return 如果返回0 则操作成功，返回1,则操作失败
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public boolean isError(String info) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(info)));
		Element root = doc.getDocumentElement();
		String error = "";
		if ("0".equals(error))
		{
			return false;
		} else
		{
			return true;
		}
	}

	/**
	 * 获取数据网关的id
	 * 
	 * @param gatherAddress
	 * @return
	 * @throws SQLException
	 */
	public int getgather(String gatherAddress) throws SQLException
	{
		int gatherId = 0;
		String sql = "select GATHER_ID from gather where GATHER_ADDRESS='" + gatherAddress + "'";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				gatherId = rs.getInt("GATHER_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return gatherId;
	}

	/**
	 * 开关灯，自动后更新控制器回路状态信息
	 * 
	 * @param slControlLineModel
	 * @return
	 * @throws SQLException
	 */
	public boolean updateSLkongzhi(SLControlLineModel slControlLineModel) throws SQLException
	{
		if(slControlLineModel.getLamp_state11().equals("")&&slControlLineModel.getLamp_state21().equals(""))
		{
			return true;
		}
		String sql = "update slkongzhi set LASTTIME=to_date('" + slControlLineModel.getLastTime() + "','yyyy-mm-dd hh24:mi:ss')," + 
				"LAMP_STATE11='"
				+ slControlLineModel.getLamp_state11() + "',LAMP_STATE21='" + slControlLineModel.getLamp_state21() + "'" + " ,SLKONGZHI_STATE='1' "
				+ "where SLGATHER_ID=" + slControlLineModel.getSLGATHER_ID() + " and SLKONGZHI_485ADDRESS='"
				+ slControlLineModel.getSLKONGZHI_485ADDRESS()+"'";

		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
			
			if(slControlLineModel.getSLKONGZHI_485ADDRESS().length()>=12)//电表当路灯控制器
			{
				if(slControlLineModel.getLamp_state11() =="auto")
				{
					sql = "update SLLine set LATEOPR=1 where SLKONGZHI_ID "+
"in(  select SLKONGZHI_ID from slkongzhi  where SLGATHER_ID=" + slControlLineModel.getSLGATHER_ID() + " and SLKONGZHI_485ADDRESS='"
				+ slControlLineModel.getSLKONGZHI_485ADDRESS()+"')";
							
				}
				else
				{
					sql = "update SLLine set LATEOPR=0 where SLKONGZHI_ID "+
"in(  select SLKONGZHI_ID from slkongzhi  where SLGATHER_ID=" + slControlLineModel.getSLGATHER_ID() + " and SLKONGZHI_485ADDRESS='"
				+ slControlLineModel.getSLKONGZHI_485ADDRESS()+"')";
					
				}
				ps = conn.prepareStatement(sql);
				b = (ps.executeUpdate() == 1);	
			}
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		return b;
	}

	/**
	 * 抄读路灯状态后更新控制器回路状态信息
	 * 
	 * @param slControlLineModel
	 * @return
	 * @throws SQLException
	 */
	public boolean updateReadSLkongzhi(SLControlLineModel slControlLineModel) throws SQLException
	{
		if(slControlLineModel.getLineState01().equals("")&&slControlLineModel.getLineState02().equals(""))
		{
			return true;
		}
		String sql = "update slkongzhi set LASTTIME=to_date('" + slControlLineModel.getLastTime() + "','yyyy-mm-dd hh24:mi:ss'),LAMP_STATE1='"
				+ slControlLineModel.getLineState01() + "',LAMP_STATE2='" + slControlLineModel.getLineState02()
				+ "',SLKONGZHI_STATE='1' where SLGATHER_ID=" + slControlLineModel.getSLGATHER_ID() + "and SLKONGZHI_485ADDRESS="
				+ slControlLineModel.getSLKONGZHI_485ADDRESS();

		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 插入路灯历史记录数据
	 * 
	 * @param slHistoryModel
	 * @return
	 */
	public boolean addSLHistory(SlHistoryModel slHistoryModel) throws SQLException
	{
		int id = 0;
		id = getSLHistoryId();
		String time = "";
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = formatter.format(date);
		String sql = "insert into slhistory(SLHISTORY_ID,SLHISTORY_TIME,SLHISTORY_STATE,USERS_name,SLHISTORY_STYLE,SLHISTORY_OPR,SLLINE_ID,TASKNUM) values('"
				+ id
				+ "',to_date('"
				+ time
				+ "','yyyy-mm-dd hh24:mi:ss'),'"
				+ slHistoryModel.getSlHistory_State()
				+ "','"
				+ slHistoryModel.getUsers_Name()
				+ "','"
				+ slHistoryModel.getSlHistory_Style()
				+ "','"
				+ slHistoryModel.getSlHistory_Opr()
				+ "','"
				+ slHistoryModel.getSLLINE_ID() + "','" + slHistoryModel.getTASKNUM() + "')";

		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 获取路灯历史记录id
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getSLHistoryId() throws SQLException
	{
		int id = 0;
		String sql = "select max(SLHISTORY_ID) Id from SlHistory";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				id = rs.getInt("Id");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return id + 1;
	}

}
