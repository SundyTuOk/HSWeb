package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.fop.render.ps.PSTextElementBridge;
import org.bouncycastle.asn1.dvcs.Data;
import org.jsmpp.bean.OptionalParameter.Int;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.expert.servlet.RadarAnalysisServlet;
import com.sf.energy.util.ConnDB;

public class CMMeterDao
{

	public HashMap<String, String> getTwoMonthData(int amMeterID, Date start, Date end) throws ParseException, SQLException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		int sum = daysBetween(start, end) + 1;
		HashMap<String, String> dataSet = new HashMap<>();
		String temDate = df2.format(start);
		for (int i = 0; i < sum; i++)
		{
			dataSet.put(temDate, "0");
			cal.setTime(df2.parse(temDate));
			cal.add(Calendar.DATE, 1);
			temDate = df2.format(cal.getTime());
		}

		String SelectWhere = " Ammeter_ID=" + amMeterID;
		String sql = "select SelectYear,SelectMonth,SelectDay,sum(ZGross)ZGross from T_DayAm where " + SelectWhere
				+ " and  (SelectYear*10000+SelectMonth*100+ SelectDay) BETWEEN " + df.format(start) + "  and  " + df.format(end)
				+ "  group by SelectYear,SelectMonth,SelectDay order by SelectYear,SelectMonth,SelectDay";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		String key = null;
		String value = null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				key = rs.getString("SelectYear") + "-" + formatNNUm(rs.getString("SelectMonth")) + "-" + formatNNUm(rs.getString("SelectDay"));
				value = rs.getString("ZGross");
				dataSet.put(key, value);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return dataSet;
	}

	private String formatNNUm(String num)
	{
		int number = Integer.parseInt(num);

		if (number < 10)
			return "0" + number;
		else
			return "" + number;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public int daysBetween(Date smdate, Date bdate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获得电表信息，
	 * 
	 * @param ammID
	 *            电表ID
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public JSONArray getAll_Ammeter_InfoForJZJC(int ammID) throws SQLException, ParseException
	{
		DecimalFormat numFomat = new DecimalFormat("0.00");
		JSONArray js = new JSONArray();
		JSONObject jo = new JSONObject();
		String sql = "select a.*,p.Partment_Name,r.Price_Value,r.Price_Name,e.DictionaryValue_Value eValue,x.DictionaryValue_Value xValue,apd.SYValue,apd.SYMoney,apd.TZValue,apd.TZMoney,z.ZValueZY,z.ValueTime "
				+ "from AmMeter a left join Partment p on p.Partment_ID=a.Partment_ID "
				+ "left join ( select * from(select * from ZAMDATAS"
				+ String.valueOf(ammID)
				+ " order by ValueTime desc)kkkk where ROWNUM =1	)z on 1=1"
				+ " left join (select * from (select * from AmMeterAPDatas where AmMeter_ID="
				+ ammID
				+ "  order by ValueTime desc)kk where  ROWNUM =1 )apd on apd.AmMeter_ID=a.AmMeter_ID "
				+ "left join Price r on r.Price_ID=a.Price_ID "
				+ "left join ("
				+ "select DictionaryValue_Num,DictionaryValue_Value "
				+ "from DictionaryValue where Dictionary_ID=7"
				+ ") e on e.DictionaryValue_Num=substr(a.AmMeter_Num,14,1) "
				+ "left join ("
				+ "select DictionaryValue_Num,DictionaryValue_Value from DictionaryValue where Dictionary_ID=6) x on x.DictionaryValue_Num=a.UseAmStyle "
				+ "where a.AmMeter_ID=" + ammID;
		// System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				// 电表编号Td的组装
				String IsSupply = "<span style=\"color:Green\">合闸</span>";
				String state = rs.getString("IsSupply");

				if ("".equals(state))
					state = "00";
				
				if("0".equals(state.substring(0, 1))){
					IsSupply = "<span style=\"color:Green\">合闸</span>";
				}else{
					IsSupply = "<span style=\"color:red\">分闸</span>";
				}
				/*if ("00".equals(state))
				{
					IsSupply = "空调：<span style=\"color:red\">分闸</span>,照明：<span style=\"color:red\">分闸</span>";
				} else if ("01".equals(state))
				{
					IsSupply = "空调：<span style=\"color:red\">分闸</span>,照明：<span style=\"color:red\">合闸</span>";
				} else if ("10".equals(state))
				{
					IsSupply = "空调：<span style=\"color:red\">合闸</span>,照明：<span style=\"color:red\">分闸</span>";
				} else if ("11".equals(state))
				{
					IsSupply = "空调：<span style=\"color:red\">合闸</span>,照明：<span style=\"color:red\">合闸</span>";
				}*/
				String meterNem = rs.getString("AmMeter_485Address") + "(" + getMeterStatus(rs.getInt("AmMeter_Stat")) + "，" + IsSupply + ")";
				jo.put("ammNum", meterNem);
				//表计类型
				jo.put("METESTYLE_ID", rs.getString("METESTYLE_ID"));

				// 对应操作的组装
				String strAPState = rs.getString("APState");
				if ("".equals(strAPState))
					strAPState = "未开户";
				String strAction = "";
				
				//查询电表协议（07、97）
				String meterPotocol = getMeterPotocol(ammID);
				String AmMeter_CostType = getMeterCostType(ammID);
				
				// 判断costType决定操作的按钮   非费控
				if ("0".equals(AmMeter_CostType))
				{// 后付费
					strAction = "<input id=\"butYuGou\" type=\"button\" value=\"充值\"  button_Level=4 class=\"btn btn-mini aqua glare btn-info aqua glare\" onclick=\"butSaleClick("
							+ AmMeter_CostType + ")\"/>";
				} else
				{// 预付费
					if ("开户".equals(strAPState))
						strAction = "<input id=\"butSale\" class=\"btn btn-mini aqua glare btn-info aqua glare\" button_Level=4 type=\"button\" value=\"售电\"   onclick=\"butSaleClick("
								+ AmMeter_CostType
								+ ")\"/>&nbsp;&nbsp;<input id=\"butXiaohu\" class=\"btn btn-mini aqua glare btn-info aqua glare\" type=\"button\" value=\"销户\"  button_Level=4  onclick=\"butXiaohuClick()\"/>";
					else
						strAction = "<input id=\"butKaihu\" type=\"button\" value=\"开户\"  button_Level=4 class=\"btn btn-mini aqua glare btn-info aqua glare\" onclick=\"butKaihuClick()\"/>";
				}

				String strCut = "<input id=\"butOn\" type=\"button\" value=\"合闸\"  button_Level=3 class=\"btn btn-mini aqua glare btn-info aqua glare\" onclick=\"butOnClick("+meterPotocol+")\"/>&nbsp;&nbsp;<input id=\"butOff\" type=\"button\" value=\"分闸\"  button_Level=3 class=\"btn btn-mini aqua glare btn-info aqua glare\" onclick=\"butOffClick("+meterPotocol+")\"/>";
				String ammOperate = strAction + "&nbsp;&nbsp;" + strCut;

				jo.put("meterPotocol", meterPotocol);
				
				jo.put("ammOperate", ammOperate);
				jo.put("ammCostType", AmMeter_CostType);

				float price = rs.getFloat("Price_Value");
				DecimalFormat df = new DecimalFormat("0.000");

				// 当天用电
				float D_value = getMeterUsed(ammID, "D");
				String dayCost = df.format(price * D_value);

				jo.put("dayValue", numFomat.format(D_value));
				jo.put("dayCost", dayCost);

				// 当月用电
				float M_value = getMeterUsed(ammID, "M");
				String monthCost = df.format(price * M_value);
				jo.put("monthValue", numFomat.format(M_value));
				jo.put("monthCost", monthCost);
				// 电费类型
				String energyCostType = rs.getString("Price_Name");
				jo.put("energyCostType", energyCostType);
				// 电费单价
				jo.put("energyCostPrice", df.format(price));

				String BeiLv = rs.getString("Beilv");
				if(BeiLv==null || "".equals(BeiLv) || BeiLv.equalsIgnoreCase("null")){
					BeiLv = "1";
				}

				// 累计示数=zvaluezy*beilv
				String strTemp = rs.getString("ZValueZY");
				if (strTemp == null || "".equals(strTemp))
					strTemp = "--";
				else
				{
					if (BeiLv != null && !"".equals(BeiLv))
					{
						strTemp = numFomat.format(Float.parseFloat(strTemp));
					}
				}
				jo.put("totalValue", strTemp);
				// 旧表示数  没有乘倍率
				String GValue = rs.getString("GValue");
				if (GValue == null || "".equals(GValue))
				{
					GValue = "0.00";
				}
				String KSY = "<span style='color:blue'>" + numFomat.format(Float.parseFloat(GValue)) + "</span>";
				if("1".equals(meterPotocol)){//97
					KSY += "度";
				}else {//07
					KSY += "元";
				}

				jo.put("oldRemainValue", KSY);
				jo.put("oldRemainValueAttr", BeiLv);

				String SYValue = "";
				// 剩余金额-- 后付费要从结算表里面取
				if ("0".equals(AmMeter_CostType))
				{// 后付费--剩余金额
					SYValue = getRemainMoney(ammID);
				} else
				{
					// 预付费-- 剩余电量
					if("1".equals(meterPotocol)){
						SYValue = rs.getString("SYValue");
					}else {
						SYValue = rs.getString("SYMoney");
					}
					
				}

				if (SYValue == null || "".equals(SYValue))
					SYValue = "0";
				jo.put("RemainValue", numFomat.format(Float.parseFloat(SYValue)));

				// 透支电量
				String TZValue = rs.getString("TZValue");
				String TZMoney = rs.getString("TZMoney");
				if (TZValue == null || "".equals(TZValue))
					TZValue = "--";

				
				if (!"--".equals(TZValue))
				{
					if (Float.parseFloat(TZValue) > 0)
					{
						if("1".equals(meterPotocol)){//97
							float Tmoney = Float.parseFloat(TZValue) * price;
							TZValue = "  <span id=\"labTZValue\" style=\"color:red\">" + numFomat.format(Float.parseFloat(TZValue)) + "度</span>("
									+ df.format(Tmoney) + "元)";
						}else{
							TZValue = "  <span id=\"labTZValue\" style=\"color:red\">" + numFomat.format(Float.parseFloat(TZMoney)) + "元</span>("
									+ df.format(Float.parseFloat(TZMoney)/price) + "度)";
						}
						
					} else
					{
						if("1".equals(meterPotocol)){//97
							TZValue = "  <span id=\"labTZValue\">" + numFomat.format(Float.parseFloat(TZValue)) + "</span>";
						}else {
							TZValue = "  <span id=\"labTZValue\">" + numFomat.format(Float.parseFloat(TZMoney)) + "</span>";
						}
						
					}
				} else
				{
					TZValue = "<span id=\"labTZValue\">0</span>";

				}
				jo.put("overdrawEnergyValue", TZValue);

				// 通讯时间
				String LastTime = rs.getString("LastTime");
				if (LastTime == null || "".equals(LastTime))
					LastTime = "--";
				jo.put("commTime", LastTime);
				// 房间人数
				String APManCount = rs.getString("APManCount");
				if (APManCount == null || "".equals(APManCount))
					APManCount = "--";
				String RoomManCount = rs.getString("RoomManCount");
				if (RoomManCount == null || "".equals(RoomManCount))
					RoomManCount = "--";

				jo.put("pensonMenber", APManCount);
				jo.put("roomMenber", RoomManCount);

				// 图片电表示数
				String ZValueZY = rs.getString("ZValueZY");
				if (ZValueZY == null || "".equals(ZValueZY))
				{
					ZValueZY = "--";
					jo.put("meterShowValue", ZValueZY);
				} else
				{
					String shiShu = numFomat.format((Float.parseFloat(ZValueZY) - rs.getFloat("xiuzheng")) / Float.parseFloat(BeiLv));
					jo.put("meterShowValue", shiShu);
				}
				// 图片剩余电量
				if("0".equals(AmMeter_CostType)){//后付费  结算表里面取的剩余金额
					jo.put("meterShunShiGongLv", numFomat.format(Float.parseFloat(SYValue)));
				}else {
					jo.put("meterShunShiGongLv", numFomat.format(Float.parseFloat(SYValue) / Float.parseFloat(BeiLv)));
				}

				js.add(jo);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return js;
	}

	public String getRemainMoney(int ammID) throws SQLException
	{
		String sYValue = "";
		String sql = "select ThisRemainMoney from (select ThisRemainMoney from AmMeterJieSuan where AmMeter_ID=" + ammID
				+ " order by ThisTime desc) where rownum=1";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sYValue = rs.getString("ThisRemainMoney");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return sYValue;
	}

	private float getMeterUsed(int ammID, String Style) throws SQLException
	{
		float result = 0;
		// TODO Auto-generated method stub
		if ("D".equals(Style))
		{
			SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
			String sql = "select max(zvaluezy)maxValue,min(zvaluezy)minValue from ZAMDATAS" + String.valueOf(ammID) + " "
					+ "where valuetime>=to_date('" + dd.format(new Date()) + "','yyyy-mm-dd') and valuetime<=to_date('" + dd.format(new Date())
					+ "','yyyy-mm-dd')+1 ";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					float maxValue = rs.getFloat("maxValue");
					float minValue = rs.getFloat("minValue");
					result = maxValue - minValue;
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}
			
		} else if ("M".equals(Style))
		{
			SimpleDateFormat dM = new SimpleDateFormat("yyyy-MM");
			String sql = "select max(zvaluezy)maxValue,min(zvaluezy)minValue from ZAMDATAS" + String.valueOf(ammID) + " "
					+ "where valuetime>=to_date('" + dM.format(new Date()) + "','yyyy-mm') and valuetime<=add_months(to_date('"
					+ dM.format(new Date()) + "','yyyy-mm'),1) ";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					float maxValue = rs.getFloat("maxValue");
					float minValue = rs.getFloat("minValue");
					result = maxValue - minValue;
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}
			
		}
		return result;
	}

	String getMeterStatus(int stat)
	{
		switch (stat)
		{
		case 0:
			return "<span id='spanMeterstat' style=\"color:red\">离线</span>";
		case 1:
			return "<span id='spanMeterstat' style=\"color:blue\">在线</span>";
		case 2:
			return "<span id='spanMeterstat' style=\"color:green\">用电</span>";
		default:
			return "";
		}
	}

	/**
	 * 调整房间人数
	 * 
	 * @param amMeterID
	 *            电表ID
	 * @param personnum
	 *            人数
	 * @param roomSpace
	 *            容量
	 * @param check
	 *            是否检测
	 * @return
	 * @throws SQLException
	 */
	public JSONArray updateManCount(int amMeterID, int personnum, int roomSpace, int check) throws SQLException
	{
		JSONArray js = new JSONArray();
		JSONObject jo = new JSONObject();
		String sql = "update AmMeter set APMancount=" + personnum + ",RoomMancount=" + roomSpace + " where ammeter_ID=" + amMeterID;
		Connection conn = null;
		PreparedStatement ps = null;
		int k =0;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			k=ps.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		
		
		if (k == 1)
		{
			jo.put("result", "1");
			jo.put("pensonMenber", personnum);
			jo.put("roomMenber", roomSpace);
		} else
			jo.put("result", "0");

		js.add(jo);

		return js;
	}

	/**
	 * 获得电表信息
	 * 
	 * @param amMeterID
	 * @return
	 * @throws SQLException
	 */
	public JSONArray getAll_SaleEnergy_InfoForJZJC(int amMeterID) throws SQLException
	{
		DecimalFormat df = new DecimalFormat("0.###");
		JSONArray js = new JSONArray();
		JSONObject jo = new JSONObject();
		String sql = "select AmMeter_ID,AmMeter_Num,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName,a.Price_ID,b.Price_Num,Price_Name,Price_Value,nvl(Beilv,1)Beilv from (AmMeter)a left join (Price)b on a.Price_ID=b.Price_ID where AmMeter_ID="
				+ amMeterID;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				String hidPrice_ID = rs.getString("Price_ID");
				String txtConsumerNum = rs.getString("ConsumerNum");
				String txtConsumerName = rs.getString("ConsumerName");
				String txtAmmeterName = rs.getString("AmMeter_Name");
				String txtAmmeterAddress = rs.getString("AmMeter_485Address");
				String txtPrice_Name = rs.getString("Price_Name");
				String txtPrice_Value = df.format(rs.getFloat("Price_Value"));
				String hidBeilv = rs.getString("Beilv");
				jo.put("hidPrice_ID", hidPrice_ID);
				jo.put("txtConsumerNum", txtConsumerNum);
				jo.put("txtConsumerName", txtConsumerName);
				jo.put("txtAmmeterName", txtAmmeterName);
				jo.put("txtAmmeterAddress", txtAmmeterAddress);
				jo.put("txtPrice_Name", txtPrice_Name);
				jo.put("txtPrice_Value", txtPrice_Value);
				jo.put("hidBeilv", hidBeilv);
				js.add(jo);
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return js;
	}

	/**
	 * 获取开户界面的初始化信息
	 * 
	 * @param amMeterID
	 * @return
	 * @throws SQLException
	 */
	public JSONArray getAll_KaiHu_InfoForJZJC(int amMeterID) throws SQLException
	{
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df1 = new DecimalFormat("0.000");
		JSONArray js = new JSONArray();
		JSONObject jo = new JSONObject();
		String sql = "select AmMeter_ID,nvl(Gvalue,0)Gvalue,AmMeter_Num,AmMeter_Name,AmMeter_485Address,ConsumerNum,"
				+ "ConsumerName,a.Price_ID,b.Price_Num,Price_Name,Price_Value,nvl(Beilv,1)Beilv,"
				+ "(select to_char(Max(ValueTime),'yyyy-mm-dd hh24:mi:ss') from ZAMDATAS" + String.valueOf(amMeterID)
				+ ")maxValueTime from (AmMeter)a " + "left join (Price)b on a.Price_ID=b.Price_ID where AmMeter_ID=" + amMeterID;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{

				String hidPrice_ID = rs.getString("Price_ID");
				String ConsumerNum = rs.getString("ConsumerNum");
				String ConsumerName = rs.getString("ConsumerName");
				String AmmeterName = rs.getString("AmMeter_Name");
				String Gvalue = rs.getString("Gvalue");//旧表剩余  没有乘倍率的
				String Price_Name = rs.getString("Price_Name");
				String Price_Value = df1.format(rs.getFloat("Price_Value"));
				String hidBeilv = rs.getString("Beilv");
				
				String meterPotocol = getMeterPotocol(amMeterID);
				
				//电表协议
				jo.put("meterPotocol", meterPotocol);		
				jo.put("hidPrice_ID", hidPrice_ID);
				// 开户账号
				jo.put("ConsumerNum", ConsumerNum);
				// 开户名称
				jo.put("ConsumerName", ConsumerName);
				// 表计
				jo.put("AmmeterName", AmmeterName);
				// 旧表余量
				jo.put("Gvalue", Gvalue);
				// 用电类型
				jo.put("Price_Name", Price_Name);
				// 用电单价
				jo.put("Price_Value", Price_Value);

				jo.put("hidBeilv", hidBeilv);

				String SYValue = "0.00";
				String SYMoney = "0.00"; 
				String TZValue = "0.00";
				String TZMoney = "0.00";
				String Tmoney = "0.00";
				String drSY = "select * from (select  ValueTime,nvl(SYValue,0)SYValue,nvl(SYMoney,0)SYMoney,nvl(TZValue,0)TZValue,nvl(TZMoney,0)TZMoney from AmMeterAPDatas where AmMeter_ID="
						+ amMeterID + "  order by ValueTime Desc) where rownum=1";
//				Connection conn1 = null;
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
//					conn1 = ConnDB.getConnection();
					ps1 = conn.prepareStatement(drSY);
					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						if("1".equals(meterPotocol)){//97
							SYValue = df.format(rs1.getFloat("SYValue"));
							TZValue = df.format(rs1.getFloat("TZValue"));
							Tmoney = df.format(rs1.getFloat("TZValue") * rs.getFloat("Price_Value"));
						}else {//07 透支金额  剩余金额
							SYValue = df.format(rs1.getFloat("SYMoney"));
							Tmoney = df.format(rs1.getFloat("TZMoney"));
							TZValue = df.format(rs1.getFloat("TZMoney")/rs.getFloat("Price_Value"));
						}
						
					}
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps1,rs1);
				}
				
				// 剩余电量/金额
				jo.put("SYValue", SYValue);
				// 透支电量
				jo.put("TZValue", TZValue);
				// 透支金额
				jo.put("Tmoney", Tmoney);

				String hidZValue;
				if (rs.getString("maxValueTime") != null && !"".equals(rs.getString("maxValueTime")))
				{
					// System.out.println(rs.getString("maxValueTime"));
					String sql2 = "select ZValueZY from  ZAMDATAS" + String.valueOf(amMeterID) + " " + "where ValueTime=to_date('"
							+ rs.getString("maxValueTime") + "','yyyy-mm-dd hh24:mi:ss')";
//					Connection conn2 = null;
					PreparedStatement ps2 =null;
					ResultSet rs2 =null;
					try{
//						conn2 = ConnDB.getConnection();
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next())
						{
							hidZValue = rs2.getString("ZValueZY");
							jo.put("hidZValue", hidZValue);
						}
					}catch (SQLException e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release( ps2,rs2);
					}
					
				} else
				{
					jo.put("hidZValue", "0");
				}
				js.add(jo);
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return js;
	}

	/**
	 * 开户成功的操作
	 * 
	 * @param gvalue
	 * @param tZMoney
	 * @param tZValue
	 * @param sYGross
	 * @param kind
	 * @param price
	 * @param gross
	 * @param money
	 * @param AmMeter_485Address
	 * @param AmMeter_Name
	 * @param userID
	 * @param amMeterID
	 * @return
	 * @throws SQLException
	 */
	public String afterKaiHusuccess(int Meter_ID, int UserID, String Money, String Gross, String Price, String kind, String SYGross, String TZValue,
			String TZMoney, String Gvalue, String AmMeter_Name, String AmMeter_485Address) throws SQLException
	{
		String ZValueZY = "";
		String sql = "select nvl(max(ZValueZY),0)ZValueZY from ZAMDATAS" + String.valueOf(Meter_ID);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				ZValueZY = rs.getString("ZValueZY");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		

		String sql1 = "insert into APKaihuInfo(Ammeter_ID,TheTime,OldSY,NewZValue,SYValue,TZValue,TheMoney,Users_ID) values(" + Meter_ID
				+ ",to_date(to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')," + Gvalue + "," + ZValueZY + "," + SYGross + ","
				+ TZValue + "," + TZMoney + "," + UserID + ")";
		Connection conn1 = null;
		PreparedStatement ps1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql1);
			ps1.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1);
		}
		// System.out.println(sql1);

		String sql2 = "update AmMeter set APState='开户',GValue=0 where Ammeter_ID=" + Meter_ID;
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql2);
			ps2.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn2, ps2);
		}
		

		String SaleInfoNum = GetNextChareNum();
		boolean isS = false;
		// 写入数据库
		// 添加售电单，SaleInfoNum必须是唯一的如果主键重复会异常，则重新生成单号添加直到添加成功
		while (!isS)
		{
			try
			{
				if (Float.parseFloat(Gross) > 0)
				{
					String sql3 = "insert into APSaleInfo(SaleInfoNum,Ammeter_ID,AmMeter_Name,AmMeter_485Address,Kind,QSYValue,Users_ID,BuyTime,Status,Price,TheGross,TheMoney)"
							+ "values('"
							+ SaleInfoNum
							+ "',"
							+ Meter_ID
							+ ",'"
							+ AmMeter_Name
							+ "','"
							+ AmMeter_485Address
							+ "',"
							+ kind
							+ ",0,"
							+ UserID
							+ ",to_date(to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss'),0,"
							+ Price
							+ ","
							+ Gross
							+ ","
							+ Money + ")";
					Connection conn3 = null;
					PreparedStatement ps3 = null;
					try
					{
						conn3 = ConnDB.getConnection();
						ps3 = conn.prepareStatement(sql3);
						ps3.executeUpdate();

					} catch (SQLException e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(conn3, ps3);
					}
					

				} else if (Float.parseFloat(Gross) == 0)
				{
					String sql4 = "insert into APSaleInfo(SaleInfoNum,Ammeter_ID,AmMeter_Name,AmMeter_485Address,Kind,QSYValue,Users_ID,BuyTime,Status,Price,TheGross,TheMoney)"
							+ "values('"
							+ SaleInfoNum
							+ "',"
							+ Meter_ID
							+ ",'"
							+ AmMeter_Name
							+ "','"
							+ AmMeter_485Address
							+ "',"
							+ kind
							+ ",1,"
							+ UserID
							+ ",to_date(to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss'),1,"
							+ Price
							+ ","
							+ Gross
							+ ","
							+ Money + ")";
					Connection conn4 = null;
					PreparedStatement ps4 = null;
					try
					{
						conn4 = ConnDB.getConnection();
						ps4 = conn4.prepareStatement(sql4);
						ps4.executeUpdate();

					} catch (SQLException e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(conn4, ps4);
					}
				}
				isS = true;
				break;
			} catch (Exception e)
			{
				SaleInfoNum = GetNextChareNum();
			}
		}
		return ZValueZY + "," + SaleInfoNum;
	}

	public String GetNextChareNum() throws SQLException
	{
		String maxNum = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH;mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd0001");
		SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMdd");

		DecimalFormat nf = new DecimalFormat("0000");

		String sql = "select max(to_number( substr( SaleInfoNum,9,6)))maxNum from APSaleInfo where BuyTime>=to_date('" + df3.format(new Date())
				+ "','yyyy-mm-dd')";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				maxNum = rs.getString("maxNum");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		

		String nextNum = "";
		if (maxNum == null)
		{
			nextNum = df2.format(new Date());
		} else
		{
			int nextnum = Integer.parseInt(maxNum) + 1;

			nextNum = df3.format(new Date()) + nf.format(nextnum);
		}
		return nextNum;

	}

	public String GetNextGDNum() throws SQLException
	{
		String maxNum = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd0001");
		SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMdd");

		DecimalFormat nf = new DecimalFormat("0000");

		String sql = "select max(to_number( substr(AMMGDINFONUM,9,6)))maxNum from AmMeterGDInfo where ReadTime>=to_date('" + df3.format(new Date())
				+ "','yyyy-mm-dd')";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				maxNum = rs.getString("maxNum");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		String nextNum = "";
		if (maxNum == null)
		{
			nextNum = df2.format(new Date());
		} else
		{
			int nextnum = Integer.parseInt(maxNum) + 1;

			nextNum = df3.format(new Date()) + nf.format(nextnum);
		}
		return nextNum;

	}

	public String SerSaveXiaoHuTuiDian(int amMeterID, String ZValueZY, String SYValue, int userID) throws SQLException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String retID = null;
		String sql = "select  AmMeter_485Address,Beilv,(select Gather_Address from (Gather)b where a.Gather_ID=b.Gather_ID)Gather_Address,(select Price_Value from  (Price) b where Price_Style='E' and a.Price_ID=b.Price_ID)Price_Value From (AmMeter) a where AmMeter_ID="
				+ amMeterID;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				DecimalFormat nf = new DecimalFormat("0.###");
				float Price = Float.parseFloat(rs.getString("Price_Value"));
				float Beilv = Float.parseFloat(rs.getString("Beilv"));
				
				float TheMoney = 0;
				String meterPotocol = getMeterPotocol(amMeterID);
				//SYValue 是从表里面度回来的剩余量  除过倍率的
				if("1".equals(meterPotocol)){//97 剩余量
					TheMoney = Beilv * Float.parseFloat(SYValue) * Price;
				}else{//07 就是没有乘倍率的剩余金额
					TheMoney = Beilv * Float.parseFloat(SYValue);
				}
				 
				float TSYValue = Beilv * Float.parseFloat(SYValue);
				float TZValueZY = Beilv * Float.parseFloat(ZValueZY);

				sql = "insert into APXiaohuInfo(Ammeter_ID,TheTime,QSYValue,QZValue,Users_ID,Status,Price,TheMoney) values(" + amMeterID + ",sysdate,"
						+ TSYValue + "," + TZValueZY + "," + userID + ",0," + rs.getString("Price_Value") + "," + nf.format(TheMoney) + ")";
//				Connection conn1 = null;
				PreparedStatement ps1 = null;
				try
				{
//					conn1 = ConnDB.getConnection();
					ps1 = conn.prepareStatement(sql);
					ps1.executeUpdate();

				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps1);
				}
				

				sql = "select max(ID)ID from APXiaohuInfo where Ammeter_ID=" + amMeterID + " and TheTime>=to_date('" + df.format(new Date())
						+ "','yyyy-mm-dd')";
//				Connection conn2 = null;
				PreparedStatement ps2 =null;
				ResultSet rs2 =null;
				try{
//					conn2 = ConnDB.getConnection();
					ps2 = conn.prepareStatement(sql);
					rs2 = ps2.executeQuery();
					if (rs2.next())
					{
						retID = rs2.getString("ID");
					}
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(ps2,rs2);
				}
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return retID;

	}

	/**
	 * 销户成功的处理
	 * 
	 * @param amMeterID
	 * @param SaleInfoNum
	 * @return
	 * @throws SQLException
	 */
	public void SerSaveXiaohu2(int amMeterID, String SaleInfoNum) throws SQLException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (SaleInfoNum != null && !"".equals(SaleInfoNum))
		{
			String sql2 = "update APXiaohuInfo set Status=1,SendTime=sysdate where Ammeter_ID=" + amMeterID + " and ID=" + SaleInfoNum;
			Connection conn = null;
			PreparedStatement ps = null;
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql2);
				ps.executeUpdate();

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps);
			}
			
		}
		String oldSY = null;
		String sql = "select oldSY from (select oldSY from APKaihuInfo where Ammeter_ID=" + amMeterID + " and TheTime >to_date('"
				+ df.format(new Date()) + "','yyyy-mm-dd')-7 order by TheTime desc) where rownum=1";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				oldSY = rs.getString("oldSY");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		String set = " ";
		if (oldSY != null)
		{
			set = ",GValue=" + oldSY;
		}
		String sql3 = "update AmMeter set APState='销户'" + set + " where Ammeter_ID=" + amMeterID;
		Connection conn1 = null;
		PreparedStatement ps1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql3);
			ps1.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1);
		}
		
	}

	/**
	 * 读销户后剩余电量处理逻辑
	 * 
	 * @param amMeterID
	 * @param xiaohuID
	 * @param i
	 * @param zValueZY
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public String xiaoHuHouSYvalueSuc(int amMeterID, String xiaohuID, float HSYValue, float HTZValue, float zValueZY) throws SQLException,
			ParseException
	{
		String sql3 = "update APXiaohuInfo set Status=1,SendTime=sysdate where Ammeter_ID=" + amMeterID + " and ID=" + xiaohuID;
		Connection conn1 = null;
		PreparedStatement ps1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql3);
			ps1.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1);
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SerSaveData(amMeterID, zValueZY, HSYValue, HTZValue, df.format(new Date()));
		return null;
	}

	public void SerSaveData(int amMeterID, float zValueZY, float SYValue, float TZValue, String ValueTime) throws SQLException, ParseException
	{
		String sql = "select  AmMeter_485Address,(select Gather_Address from Gather b where a.Gather_ID=b.Gather_ID)Gather_Address From AmMeter a where AmMeter_ID="
				+ amMeterID;

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				String Gather_Address = rs.getString("Gather_Address");
				String AmMeter_485Address = rs.getString("AmMeter_485Address");
				// 写当前示数
				if (!(zValueZY < 0))
				{

					AutoUpDataSaver.dataSave(Gather_Address, "1", AmMeter_485Address, "0C0110", ValueTime, String.valueOf(zValueZY));
				} else
				{
					// System.out.println("在CMMeterDao类中SerSaveData 函数 ，修改离线");
					String sql3 = "update Ammeter set AmMeter_Stat=0 where AmMeter_ID=" + amMeterID;
//					Connection conn1 = null;
					PreparedStatement ps1 = null;
					try
					{
//						conn1 = ConnDB.getConnection();
						ps1 = conn.prepareStatement(sql3);
						ps1.executeUpdate();

					} catch (SQLException e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release( ps1);
					}
				}

				// 写剩余电量
				if (!(SYValue < 0))
				{

					AutoUpDataSaver.dataSavePurchase(Gather_Address, AmMeter_485Address, ValueTime, "0", "0", String.valueOf(SYValue),
							String.valueOf(TZValue), 0);
				}

			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
	}

	public JSONArray getBrotherMeter(String ammeterId) throws SQLException
	{
		String sql = "SELECT (ammeter_id)id,(AMMETER_NAME)name,(select 1 from dual)style FROM ammeter WHERE  CONSUMERNUM=(SELECT CONSUMERNUM FROM ammeter WHERE ammeter_id="
				+ ammeterId
				+ ") and ammeter_id <>"
				+ ammeterId
				+ " "
				+ "UNION "
				+ "SELECT WATERMETER_ID id,WATERMETER_NAME name,(select 2 from dual)style FROM WATERMETER WHERE  CONSUMERNUM=(SELECT CONSUMERNUM FROM ammeter WHERE ammeter_id="
				+ ammeterId + ")";
		

		JSONArray js = new JSONArray();
		JSONObject jo = null;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				jo = new JSONObject();
				jo.put("id", rs.getString("id"));
				jo.put("name", rs.getString("name"));
				jo.put("style", rs.getString("style"));
				js.add(jo);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return js;
	}

	public boolean getAmMeterJieSuan(int amMeter_ID, String endTime) throws SQLException, ParseException
	{
		boolean b = false;

		String StarTime = "";
		float ThisZValue = 0;
		float LastZValue = 0;
		String ThisTime = "";
		String LastTime = "";
		String BeiLv = "1";
		float ZDS = 0;// 总底数，旧表的总示数
		String errormsg = "";
		float price = 0;
		float ThisRemainMoney = 0;
		float LastRemainMoney = 0;
		// string strsql =
		// "select AmMeter_ID from AmMeterJieSuan where AmMeter_ID=" +
		// amMeter_ID + " and convert(char(10),ReadTime,120)='" + date + "'";
		// object ooo = SqlHelper.ExecuteScalar(ConnString, CommandType.Text,
		// strsql);
		String strsql = "select Beilv,(select price_value from price where price_id=(select price_id from Ammeter where Ammeter_id=" + amMeter_ID
				+ "))price" + " from AmMeter a where AmMeter_ID=" + amMeter_ID;
		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(strsql);
			rs1 = ps1.executeQuery();
			if (rs1.next())
			{
				BeiLv = rs1.getString("Beilv");
				price = rs1.getFloat("price");

			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}
		

		// 获取结算的起始时间，如果系统还未结算就取表码为初始值
		strsql = "select * from (select ThisZValue,to_char(ThisTime,'yyyy-mm-dd hh24:mi:ss')ThisTime,jieSuanTime,ThisRemainMoney from AmMeterJieSuan a where AmMeter_ID=" + amMeter_ID + " order by ThisTime desc) where rownum=1";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strsql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				LastZValue = Float.parseFloat(rs.getString("ThisZValue"));
				LastTime = rs.getString("ThisTime");
				StarTime = rs.getString("jieSuanTime");
				LastRemainMoney = rs.getFloat("ThisRemainMoney");
			} else
			{
				LastZValue = 0;
				LastRemainMoney = 0;
				StarTime = "1900-01-01 00:00:00";
				LastTime = "1900-01-01 00:00:00";
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		

		// 当起始时间小于终止时间时
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date starDate = new Date();
		Date endDate = new Date();

		starDate = sdf.parse(StarTime);
		endDate = sdf.parse(endTime);
		if (starDate.before(endDate))
		{
			boolean ifchange = false;
			// 检查该表在起始至终止时间内是否换过表,取最后一次换表的那一条
			strsql = "select OldZValue from (select  a.OldZValue";
			strsql += " from AmMeterReplce a where AmMeter_ID=" + amMeter_ID + " order by insertTime desc) where rownum=1";
			Connection conn2 = null;
			PreparedStatement ps2 =null;
			ResultSet rs2 =null;
			try{
				conn2 = ConnDB.getConnection();
				ps2 = conn2.prepareStatement(strsql);
				rs2 = ps2.executeQuery();
				if (rs2.next())
				{
					ZDS = rs2.getFloat("OldZValue");
					ifchange = true;
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn2, ps2,rs2);
			}
			

			// 获取终止数据
			float usedmoney = 0;
			float usedvol = 0;
			strsql = "select ValueTime,SYMoney,TZMoney,(select ZValueZY from (select ZValueZY from ZAmDatas" + amMeter_ID
					+ " order by ValueTime desc) where rownum=1)ZValueZY from"
					+ " (select to_char(ValueTime,'yyyy-mm-dd hh24:mi:ss')ValueTime,SYMoney,TZMoney" + " from AmMeterAPDatas where AmMeter_ID="
					+ amMeter_ID + " and ValueTime>to_date('" + StarTime + "','yyyy-mm-dd hh24:mi:ss') and ValueTime<=to_date('" + endTime
					+ "','yyyy-mm-dd hh24:mi:ss')  order by ValueTime desc)" + " where rownum=1";
			//System.out.println(strsql);
			Connection conn3 = null;
			PreparedStatement ps3 =null;
			ResultSet rs3 =null;
			try{
				conn3 = ConnDB.getConnection();
				ps3 = conn3.prepareStatement(strsql);
				rs3 = ps3.executeQuery();
				if (rs3.next())
				{
					ThisZValue = rs3.getFloat("ZValueZY");//乘倍率的

					float minusvalue = 0;
					if (ifchange)// 换过表要减去底数，没换过表只有起码的不用减
					{
						minusvalue = ZDS;
					}
					//ThisZValue就是从表中读取的数据
					//ThisZValue = ThisZValue * Float.parseFloat(BeiLv);
					ThisTime = rs3.getString("ValueTime");
					//有总底数时，用量: 新表总示数+旧表总示数-上次结算的总示数 ，因为上次结算时是存的旧表的量
					usedvol = ThisZValue +  minusvalue - LastZValue;// 用量
					DecimalFormat df = new DecimalFormat("0.##");
					// 使用金额
					usedmoney = Float.parseFloat(df.format(df.parse(usedvol * price  + "")));// 要除倍率
					// 本次剩余金额----上次剩余+已购金额-使用金额
					float buyTotalMoney = 0;
					strsql = "select nvl(sum(ThisPurchaseMoney),0)total from AmMeterGDInfo where AmMeter_ID=" + amMeter_ID + " and ReadTime>to_date('"
							+ StarTime + "','yyyy-mm-dd hh24:mi:ss') and ReadTime<=to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss')";
//					Connection conn4 = null;
					PreparedStatement ps4 =null;
					ResultSet rs4 =null;
					try{
//						conn4 = ConnDB.getConnection();
						ps4 = conn3.prepareStatement(strsql);
						rs4 = ps4.executeQuery();
						if (rs4.next())
						{
							buyTotalMoney = rs4.getFloat("total");
						}

					}catch (SQLException e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release( ps4,rs4);
					}
					

					ThisRemainMoney = LastRemainMoney + buyTotalMoney - usedmoney;

				} else
				{
					ThisZValue = LastZValue;
					ThisTime = LastTime;
				}
				
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn3, ps3,rs3);
			}
			

			// 插入数据库
			strsql = "insert into AmMeterJieSuan(AmMeter_ID,ThisZValue ,LastZValue,ThisTime,LastTime,JieSuanTime,LastRemainMoney,ThisRemainMoney,Used_Money) ";
			strsql += "values(" + amMeter_ID + "," + ThisZValue + "," + LastZValue + ",to_date('" + ThisTime + "','yyyy-mm-dd hh24:mi:ss'),to_date('"
					+ LastTime + "','yyyy-mm-dd hh24:mi:ss'),to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss'),";
			strsql += LastRemainMoney + "," + ThisRemainMoney + "," + usedmoney + ")";
			Connection conn5 = null;
			PreparedStatement ps5 = null;
			
			try
			{
				conn5 = ConnDB.getConnection();
				ps5 = conn5.prepareStatement(strsql);
				b = (ps5.executeUpdate() == 1);

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn5, ps5);
			}
//			PreparedStatement pst5 = ConnDB.getConnection().prepareStatement(strsql);
//			b = (pst5.executeUpdate() == 1);
//
//			if (pst5 != null)
//				pst5.close();

		} else
		{
			errormsg = "本次结算起始时间大于中止时间，不能结算";
		}

		return b;

	}

	public String insertYuGou(int amMeter_ID, String readTime, String money, String userID) throws SQLException
	{

		String GDInfoNum = GetNextGDNum();
		int buyTimes = 0;
		String strsql = "select BuyTimes from (select BuyTimes from AmMeterGDInfo where ammeter_id="+amMeter_ID+" order by ReadTime desc) where rownum=1";
		Connection conn2 = null;
		PreparedStatement ps2 =null;
		ResultSet rs2 =null;
		try{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(strsql);
			rs2 = ps2.executeQuery();
			if (rs2.next())
			{
				buyTimes = rs2.getInt("BuyTimes");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn2, ps2,rs2);
		}
		
		
		strsql = "insert into AmMeterGDInfo(AMMGDINFONUM,AmMeter_ID,ReadTime,BuyTimes,ThisPurchaseMoney,LastRemainMoney,ThisRemainMoney,AmMeter_State,Users_ID) values('"
				+ GDInfoNum
				+ "',"
				+ amMeter_ID
				+ ",to_date('"
				+ readTime
				+ "','yyyy-mm-dd hh24:mi:ss'),"
				+ (buyTimes + 1)
				+ ","
				+ money
				+ ","
				+ 0
				+ "," + 0 + ",0," + userID + ")";
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strsql);
			ps.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		return GDInfoNum;
	}

	public boolean updateYuGou(String gdInfoNum, int amMeter_ID, String readTime) throws SQLException
	{
		float LastRemainMoney = 0;
		float ThisRemainMoney = 0;

		// 查出上次剩余，求出本次剩余
		// 最后一次结算的记录
		String strsql = "select * from (select a.* from  AmMeterJieSuan a where AmMeter_ID=" + amMeter_ID
				+ " order by JieSuanTime desc) where rownum=1";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strsql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 充值采取先生成单子，后结算，在更新，结算的时候已经把单子的金额结算掉了，这里只需要取结算后的数据即可
				// LastRemainMoney = rs.getFloat("ThisRemainMoney");
				// ThisRemainMoney = Float.parseFloat(money) + LastRemainMoney -
				// rs.getFloat("Used_Money");
				LastRemainMoney = rs.getFloat("LastRemainMoney");
				ThisRemainMoney = rs.getFloat("ThisRemainMoney");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		
//		PreparedStatement pst = ConnDB.getConnection().prepareStatement(strsql);
//		ResultSet rs = pst.executeQuery();
//		if (rs.next())
//		{
//			// 充值采取先生成单子，后结算，在更新，结算的时候已经把单子的金额结算掉了，这里只需要取结算后的数据即可
//			// LastRemainMoney = rs.getFloat("ThisRemainMoney");
//			// ThisRemainMoney = Float.parseFloat(money) + LastRemainMoney -
//			// rs.getFloat("Used_Money");
//			LastRemainMoney = rs.getFloat("LastRemainMoney");
//			ThisRemainMoney = rs.getFloat("ThisRemainMoney");
//		}
//
//		if (rs != null)
//			rs.close();
//		if (pst != null)
//			pst.close();
		String readTimeString = "";
		if(!"".equals(readTime) && readTime != null){
			readTimeString = readTime;
		}else{
			Connection conn3 = null;
			PreparedStatement pst3 = null;
			ResultSet rs3 = null;
			try
			{
				String sql3 = "SELECT valuetime from (select * from ZAmDatas"+amMeter_ID+"  ORDER BY VALUETIME DESC) WHERE ROWNUM=1";
				conn3 = ConnDB.getConnection();
				pst3 = conn3.prepareStatement(sql3);
				rs3 = pst3.executeQuery();
				if(rs3.next()){
					readTimeString = rs3.getString("valuetime");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn3, pst3, rs3);
			}
		}

		/*strsql = "update AmMeterGDInfo set ReadTime=to_date('" + readTimeString + "','yyyy-mm-dd hh24:mi:ss'),LastRemainMoney=" + LastRemainMoney
				+ ",ThisRemainMoney=" + ThisRemainMoney + ",AmMeter_State=1 where AMMGDINFONUM='" + gdInfoNum + "'";*/
		strsql = "update AmMeterGDInfo set LastRemainMoney=" + LastRemainMoney
				+ ",ThisRemainMoney=" + ThisRemainMoney + ",AmMeter_State=1 where AMMGDINFONUM='" + gdInfoNum + "'";
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		boolean b = false;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(strsql);
			b = (ps2.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn2, ps2);
		}
		return b;
	}
	
	public String getMeterCostType(int amMeter_ID){
		String ammeter_costType = "1";//默认97
		String sqlString = "select TexingValue from TEXINGVALUE where  METERTEXING_id=32 and METESTYLE_ID=(SELECT METESTYLE_ID from AMMETER WHERE AMMETER_ID="+amMeter_ID+")";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlString);
			rs = ps.executeQuery();
			if(rs.next()){
				ammeter_costType = rs.getString("TexingValue");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return ammeter_costType;
	}

	public String getMeterPotocol(int amMeter_ID) throws SQLException
	{
		String meterPotocol = "1";//默认97
		String sqlString = "select TexingValue from TEXINGVALUE where  METERTEXING_id=18 and METESTYLE_ID=(SELECT METESTYLE_ID from AMMETER WHERE AMMETER_ID=" + amMeter_ID + ")";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlString);
			rs = ps.executeQuery();
			if(rs.next()){
				meterPotocol = rs.getString("TexingValue");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
//		PreparedStatement pst = ConnDB.getConnection().prepareStatement(sqlString);
//		ResultSet rSet = pst.executeQuery();
//		if(rSet.next()){
//			meterPotocol = rSet.getString("TexingValue");
//		}
//		if(rSet!=null)
//			rSet.close();
//		if(pst!=null)
//			pst.close();
		
		
		return meterPotocol;
	}

	public String SerCheckBu(int amMeterID, String saleInfoNum, float qSYValue, int buyTimes)
	{
		String write = "";
		String sql = "select Times,QSYValue,BuyTime from APSaleInfo where Status<>1 and SaleInfoNum='" + saleInfoNum + "'";
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			conn = ConnDB.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			
			if(rs.next()){
				String strQSYValue = rs.getString("QSYValue");
				if("".equals(strQSYValue) || strQSYValue==null){
					write = "1";
				}else {
					if(Float.parseFloat(strQSYValue)>=qSYValue){
						write = "1";
					}else {
						 write = "0";
//						 Connection conn1 = null;
						 PreparedStatement pst1 = null;
						 String sql1 = "update APSaleInfo set Status=1 where SaleInfoNum='" + saleInfoNum + "'";
						 try
						{
//							conn1 = ConnDB.getConnection();
							pst1 = conn.prepareStatement(sql1);
							pst1.executeUpdate();
						} catch (SQLException e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release(pst1);
						}
					}
				}
			}else{
				write = "0";
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, pst, rs);
		}
		
		return write;
	}

	public int getAmMeterIdByAddress(String amMeter_485Address)
	{
		int id = 0;//默认97
		String sqlString = "select ammeter_id from ammeter where  AMMETER_485ADDRESS='"+amMeter_485Address+"'";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlString);
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt("ammeter_id");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return id;
	}
	
	public boolean insertWaterData(int waterID,String value)
	{
		boolean b = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		
		//查询当前时间前最近一条数据信息
		double zValueZY=-1;
		String sqldata1 = "select ZValueZY,to_char(ValueTime,'yyyy-MM-dd hh24:mi:ss')  from ( select * from ( select * from ZWATERDATAS" + String.valueOf(waterID)+" "
						+ "where ValueTime < to_date('"
						+ sdf.format(new Date())
						+ "','yyyy-mm-dd hh24:mi:ss')  order by ValueTime desc) where rownum =1 order by rownum asc) a";
		Connection conn8=null;
		PreparedStatement ps8 =null;
		ResultSet rs8 = null;
				
		try
		{
			conn8 = ConnDB.getConnection();
			ps8 = conn8.prepareStatement(sqldata1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs8 = ps8.executeQuery();			
			if (rs8.next())
			{
				zValueZY = rs8.getDouble(1); 
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn8, ps8, rs8);
		}
		
		if(Double.parseDouble(value)<zValueZY){
			//倒走
			String sqldata2 = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
					+ waterID
					+ ",to_date('"
					+ sdf.format(new Date())
					+ "','yyyy-mm-dd hh24:mi:ss'),"
					+ value
					+ ",102)";
			PreparedStatement ps4 = null;
			Connection conn4 = null;
			try
			{
				conn4 = ConnDB.getConnection();
				ps4 = conn4.prepareStatement(sqldata2);
				ps4.executeUpdate();
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn4, ps4);
			}
			return false;
		}
		
		
		String sqlString = "insert into ZWATERDATAS"+String.valueOf(waterID)+"(ValueTime,ZValueZY) values(to_date('"
					+ sdf.format(new Date())
					+ "','yyyy-mm-dd hh24:mi:ss'),"+value+")";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlString);
			b = (ps.executeUpdate()==1);
			
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return b;
	}
	
	public int getWaterMeterIdByAddress(String waMeter_485Address)
	{
		int id = 0;//默认97
		String sqlString = "select watermeter_id from watermeter where  WATERMETER_485ADDRESS='"+waMeter_485Address+"'";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlString);
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt("watermeter_id");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return id;
	}
	
	public String getAmMeterNameById(String amMeter_id)
	{
		String name = "";//默认97
		String sqlString = "select ammeter_name from ammeter where  AMMETER_id="+amMeter_id;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlString);
			rs = ps.executeQuery();
			if(rs.next()){
				name = rs.getString("ammeter_name");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return name;
	}

	public float getPriceByAmmId(int amMeterID)
	{
		float price = 0;
		String sqlString = "select price_value from price where price_id=(select price_id from ammeter where ammeter_id="+amMeterID+")";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlString);
			rs = ps.executeQuery();
			if(rs.next()){
				price = rs.getFloat("price_value");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return price;
	}
	
	public String getZValuezyById(int amMeterID)
	{
		String zvalue = "";
		String sql = "select beilv,xiuzheng,(select zvaluezy from (select zvaluezy from zamdatas"+amMeterID+" order by valueTime desc) where rownum=1)zvaluezy from ammeter where ammeter_id="+amMeterID;
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DecimalFormat numFomat = new DecimalFormat("0.00");
		try{
			conn = ConnDB.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if(rs.next()){
				String ZValueZY = rs.getString("zvaluezy");
				float xiezheng = rs.getFloat("xiuzheng");
				float Beilv = rs.getFloat("beilv");
				
				if (ZValueZY == null || "".equals(ZValueZY))
				{
					zvalue = "--";
				} else
				{
					zvalue = numFomat.format((Float.parseFloat(ZValueZY) - xiezheng) / Beilv);
				}
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pst,rs);
		}
		
		return zvalue;
	}

	public JSONArray getMeterTypeInfo()
	{
		String sql1 = "Select * from MeteStyle ORDER BY MeteStyle_ID";
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql1);
			rs = ps.executeQuery();
			while (rs.next())
			{
				jo = new JSONObject();
				jo.put("MeteStyle_ID", rs.getString("MeteStyle_ID"));
				jo.put("MeteStyle_Name", rs.getString("MeteStyle_Name"));
				json.add(jo);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return json;
	}
	
	public boolean UpdateSql(String strsql)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strsql);
			b = (ps.executeUpdate()==1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	
	public String getAmMeterByID(String id){
		String str = "";
		String sql = "SELECT AMMETER_NAME,AMMETER_485ADDRESS from AMMETER where AMMETER_ID="+id;
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next())
			{
				str = rs.getString("AMMETER_NAME")+",表地址："+rs.getString("AMMETER_485ADDRESS");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return str;
	}
	
	public JSONArray getTuifangDetail(String AmMeter_ID, String startDateTime, String endDateTime) throws NumberFormatException, ParseException
	{
		String sql = "Select to_char(BuyTime,'yyyy-mm-dd hh24:mi:ss')BUYTIME,thegross,themoney from APSaleInfo where AMMETER_ID="+AmMeter_ID+" and kind=2 and BUYTIME>=to_date('"+startDateTime+"','yyyy-mm-dd hh24:mi:ss') and BUYTIME<=to_date('"+endDateTime+"','yyyy-mm-dd hh24:mi:ss')";
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		DecimalFormat df = new DecimalFormat("0.00");
		float price = getPriceByAmmId(Integer.parseInt(AmMeter_ID));
		String zgrossSale = "";
		String zmoneySale = "";
		String zgrossBu= "";
		String zmoneyBu = "";
		String startZValueZY = "0";
		String endZValueZY = "0";
		String startValueTime = startDateTime;
		String endValueTime = "";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last(); //移到最后一行     
			int rowCount = rs.getRow(); //得到当前行号，也就是记录数     
			rs.beforeFirst();
			//起始示数
			String sqlStart = "select ZVALUEZY,VALUETIME from (SELECT ZVALUEZY,VALUETIME from ZAMDATAS"+AmMeter_ID+" where VALUETIME<=to_date('"+startDateTime+"','yyyy-mm-dd hh24:mi:ss') order by VALUETIME desc) where rownum=1";
			Connection conn3 = null;
			PreparedStatement ps3 = null;
			ResultSet rs3 = null;
			try{
				conn3 = ConnDB.getConnection();
				ps3 = conn3.prepareStatement(sqlStart);
				rs3 = ps3.executeQuery();
				if(rs3.next()){
					//startValueTime = rs3.getString("VALUETIME");
					startZValueZY = rs3.getString("ZVALUEZY");
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn3, ps3, rs3);
			}
			
			if(rowCount<=0){
				//没有月补记录
				
				//终止示数
				String sqlEnd = "select ZVALUEZY,VALUETIME from (SELECT ZVALUEZY,VALUETIME from ZAMDATAS"+AmMeter_ID+" where VALUETIME<=to_date('"+endDateTime+"','yyyy-mm-dd hh24:mi:ss') order by VALUETIME desc) where rownum=1";
				Connection conn4 = null;
				PreparedStatement ps4 = null;
				ResultSet rs4 = null;
				try{
					conn4 = ConnDB.getConnection();
					ps4 = conn4.prepareStatement(sqlEnd);
					rs4 = ps4.executeQuery();
					if(rs4.next()){
						endValueTime = rs4.getString("VALUETIME");
						endZValueZY = rs4.getString("ZVALUEZY");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn4, ps4, rs4);
				}
				//购电金额、电量
				String sqlSale = "SELECT nvl(sum(THEGROSS),0)zgross,nvl(sum(THEMONEY),0)zmoney from APSALEINFO where AMMETER_ID="+AmMeter_ID+" and kind not in (2,3) and BUYTIME>=to_date('"+startDateTime+"','yyyy-mm-dd hh24:mi:ss') and BUYTIME<=to_date('"+endDateTime+"','yyyy-mm-dd hh24:mi:ss')";
				Connection conn1 = null;
				PreparedStatement ps1 = null;
				ResultSet rs1 = null;
				try{
					conn1 = ConnDB.getConnection();
					ps1 = conn1.prepareStatement(sqlSale);
					rs1 = ps1.executeQuery();
					if(rs1.next()){
						zgrossSale = rs1.getString("zgross");
						zmoneySale = rs1.getString("zmoney");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn1, ps1, rs1);
				}
				//月补调剂金额、电量
				String sqlBu = "SELECT nvl(sum(THEGROSS),0)zgross,nvl(sum(THEMONEY),0)zmoney from APSALEINFO where AMMETER_ID="+AmMeter_ID+" and kind in (2,3) and BUYTIME>=to_date('"+startDateTime+"','yyyy-mm-dd hh24:mi:ss') and BUYTIME<=to_date('"+endDateTime+"','yyyy-mm-dd hh24:mi:ss')";
				Connection conn2 = null;
				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				try{
					conn2 = ConnDB.getConnection();
					ps2 = conn2.prepareStatement(sqlBu);
					rs2 = ps2.executeQuery();
					if(rs2.next()){
						zgrossBu = rs2.getString("zgross");
						zmoneyBu = rs2.getString("zmoney");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn2, ps2, rs2);
				}
				
				//已用电量
				float useGross = Float.parseFloat(df.format(df.parse(endZValueZY))) - Float.parseFloat(df.format(df.parse(startZValueZY)));
				//取两位小数
				useGross = Float.parseFloat(df.format(df.parse(useGross+"")));
				//应退电量
				float tuiGross = 0;
				if(useGross<Float.parseFloat(df.format(df.parse(zgrossBu)))){
					//用电小于月补  月补部分不退钱 
					tuiGross = Float.parseFloat(df.format(df.parse(zgrossSale)));
				}else{
					tuiGross = Float.parseFloat(df.format(df.parse(zgrossSale))) + Float.parseFloat(df.format(df.parse(zgrossBu))) - useGross;
				}
				tuiGross = Float.parseFloat(df.format(df.parse(tuiGross+"")));
				//应退金额
				float tuiMoney = Float.parseFloat(df.format(df.parse(tuiGross*price+"")));
				//float buMoney = Float.parseFloat(df.format(df.parse(zmoneySale))) + Float.parseFloat(df.format(df.parse(zmoneyBu)));
				jo = new JSONObject();
				jo.put("time", startDateTime + "至" + endDateTime);
				jo.put("zgrossBu", zgrossBu);
				jo.put("zgrossSale", zgrossSale);
				jo.put("useGross", useGross);
				jo.put("tuiGross", tuiGross);
				jo.put("tuiMoney", tuiMoney);
				json.add(jo);
			}else{
				//有月补记录  按每次月补来计算退钱
				while (rs.next())
				{
					String yueBuTime = rs.getString("BUYTIME");
					//终止示数
					String sqlEnd = "select ZVALUEZY,VALUETIME from (SELECT ZVALUEZY,VALUETIME from ZAMDATAS"+AmMeter_ID+" where VALUETIME<to_date('"+yueBuTime+"','yyyy-mm-dd hh24:mi:ss') order by VALUETIME desc) where rownum=1";
					Connection conn4 = null;
					PreparedStatement ps4 = null;
					ResultSet rs4 = null;
					try{
						conn4 = ConnDB.getConnection();
						ps4 = conn4.prepareStatement(sqlEnd);
						rs4 = ps4.executeQuery();
						if(rs4.next()){
							endValueTime = rs4.getString("VALUETIME");
							endZValueZY = rs4.getString("ZVALUEZY");
						}
					} catch (SQLException e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(conn4, ps4, rs4);
					}
					//购电金额、电量
					String sqlSale = "SELECT nvl(sum(THEGROSS),0)zgross,nvl(sum(THEMONEY),0)zmoney from APSALEINFO where AMMETER_ID="+AmMeter_ID+" and kind not in (2,3) and BUYTIME>=to_date('"+startValueTime+"','yyyy-mm-dd hh24:mi:ss') and BUYTIME<to_date('"+yueBuTime+"','yyyy-mm-dd hh24:mi:ss')";
					Connection conn1 = null;
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
					try{
						conn1 = ConnDB.getConnection();
						ps1 = conn1.prepareStatement(sqlSale);
						rs1 = ps1.executeQuery();
						if(rs1.next()){
							zgrossSale = rs1.getString("zgross");
							zmoneySale = rs1.getString("zmoney");
						}
					} catch (SQLException e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(conn1, ps1, rs1);
					}
					//月补调剂金额、电量
					String sqlBu = "SELECT nvl(sum(THEGROSS),0)zgross,nvl(sum(THEMONEY),0)zmoney from APSALEINFO where AMMETER_ID="+AmMeter_ID+" and kind in (2,3) and BUYTIME>=to_date('"+startValueTime+"','yyyy-mm-dd hh24:mi:ss') and BUYTIME<to_date('"+yueBuTime+"','yyyy-mm-dd hh24:mi:ss')";
					Connection conn2 = null;
					PreparedStatement ps2 = null;
					ResultSet rs2 = null;
					try{
						conn2 = ConnDB.getConnection();
						ps2 = conn2.prepareStatement(sqlBu);
						rs2 = ps2.executeQuery();
						if(rs2.next()){
							zgrossBu = rs2.getString("zgross");
							zmoneyBu = rs2.getString("zmoney");
						}
					} catch (SQLException e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(conn2, ps2, rs2);
					}
					
					//已用电量
					float useGross = Float.parseFloat(df.format(df.parse(endZValueZY))) - Float.parseFloat(df.format(df.parse(startZValueZY)));
					useGross = Float.parseFloat(df.format(df.parse(useGross+"")));
					//应退电量
					float tuiGross = 0;
					if(useGross<Float.parseFloat(df.format(df.parse(zgrossBu)))){
						//用电小于月补  不退钱 
						tuiGross = Float.parseFloat(df.format(df.parse(zgrossSale)));
					}else{
						tuiGross = Float.parseFloat(df.format(df.parse(zgrossSale))) + Float.parseFloat(df.format(df.parse(zgrossBu))) - useGross;
					}
					tuiGross = Float.parseFloat(df.format(df.parse(tuiGross+"")));
					//应退金额
					float tuiMoney = Float.parseFloat(df.format(df.parse(tuiGross*price+"")));
					//float buMoney = Float.parseFloat(df.format(df.parse(zmoneySale))) + Float.parseFloat(df.format(df.parse(zmoneyBu)));
					jo = new JSONObject();
					jo.put("time", startValueTime + "至" + yueBuTime);
					jo.put("zgrossBu", zgrossBu);
					jo.put("zgrossSale", zgrossSale);
					jo.put("useGross", useGross);
					jo.put("tuiGross", tuiGross);
					jo.put("tuiMoney", tuiMoney);
					json.add(jo);
					//给初始示数赋值
					startZValueZY = endZValueZY;
					startValueTime = yueBuTime;
				}
				
				/***********************最后一次月补到结束时间*********************/
				//终止示数
				String sqlEnd = "select ZVALUEZY,VALUETIME from (SELECT ZVALUEZY,VALUETIME from ZAMDATAS"+AmMeter_ID+" where VALUETIME<=to_date('"+endDateTime+"','yyyy-mm-dd hh24:mi:ss') order by VALUETIME desc) where rownum=1";
				Connection conn4 = null;
				PreparedStatement ps4 = null;
				ResultSet rs4 = null;
				try{
					conn4 = ConnDB.getConnection();
					ps4 = conn4.prepareStatement(sqlEnd);
					rs4 = ps4.executeQuery();
					if(rs4.next()){
						endValueTime = rs4.getString("VALUETIME");
						endZValueZY = rs4.getString("ZVALUEZY");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn4, ps4, rs4);
				}
				//购电金额、电量
				String sqlSale = "SELECT nvl(sum(THEGROSS),0)zgross,nvl(sum(THEMONEY),0)zmoney from APSALEINFO where AMMETER_ID="+AmMeter_ID+" and kind not in (2,3) and BUYTIME>=to_date('"+startValueTime+"','yyyy-mm-dd hh24:mi:ss') and BUYTIME<=to_date('"+endDateTime+"','yyyy-mm-dd hh24:mi:ss')";
				Connection conn1 = null;
				PreparedStatement ps1 = null;
				ResultSet rs1 = null;
				try{
					conn1 = ConnDB.getConnection();
					ps1 = conn1.prepareStatement(sqlSale);
					rs1 = ps1.executeQuery();
					if(rs1.next()){
						zgrossSale = rs1.getString("zgross");
						zmoneySale = rs1.getString("zmoney");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn1, ps1, rs1);
				}
				//月补调剂金额、电量
				String sqlBu = "SELECT nvl(sum(THEGROSS),0)zgross,nvl(sum(THEMONEY),0)zmoney from APSALEINFO where AMMETER_ID="+AmMeter_ID+" and kind in (2,3) and BUYTIME>=to_date('"+startValueTime+"','yyyy-mm-dd hh24:mi:ss') and BUYTIME<=to_date('"+endDateTime+"','yyyy-mm-dd hh24:mi:ss')";
				Connection conn2 = null;
				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				try{
					conn2 = ConnDB.getConnection();
					ps2 = conn2.prepareStatement(sqlBu);
					rs2 = ps2.executeQuery();
					if(rs2.next()){
						zgrossBu = rs2.getString("zgross");
						zmoneyBu = rs2.getString("zmoney");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn2, ps2, rs2);
				}
				
				//已用电量
				float useGross = Float.parseFloat(df.format(df.parse(endZValueZY))) - Float.parseFloat(df.format(df.parse(startZValueZY)));
				useGross = Float.parseFloat(df.format(df.parse(useGross+"")));
				//应退电量
				float tuiGross = 0;
				if(useGross<Float.parseFloat(df.format(df.parse(zgrossBu)))){
					//用电小于月补  月补多余部分不退钱 
					tuiGross = Float.parseFloat(df.format(df.parse(zgrossSale)));
				}else{
					tuiGross = Float.parseFloat(df.format(df.parse(zgrossSale))) + Float.parseFloat(df.format(df.parse(zgrossBu))) - useGross;
				}
				tuiGross = Float.parseFloat(df.format(df.parse(tuiGross+"")));
				//应退金额
				float tuiMoney = Float.parseFloat(df.format(df.parse(tuiGross*price+"")));
				//float buMoney = Float.parseFloat(df.format(df.parse(zmoneySale))) + Float.parseFloat(df.format(df.parse(zmoneyBu)));
				jo = new JSONObject();
				jo.put("time", startValueTime + "至" + endDateTime);
				jo.put("zgrossBu", zgrossBu);
				jo.put("zgrossSale", zgrossSale);
				jo.put("useGross", useGross);
				jo.put("tuiGross", tuiGross);
				jo.put("tuiMoney", tuiMoney);
				json.add(jo);
				
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return json;
	}


}
