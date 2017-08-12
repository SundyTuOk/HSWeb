package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.project.system.model.MeterStyle;
import com.sf.energy.project.system.model.MeterTexing;
import com.sf.energy.util.ConnDB;

/**
 * 对表计类型进行增删改查操作类
 * 
 * @author 李涵淼
 * @version 1.0
 * @since version 1.0
 * 
 */
public class MeterStyleDao
{
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// DBProcess db = null;

	/**
	 * 定义list为ArrayList，对象为表计类型对象
	 * 
	 */
	public MeterStyleDao()
	{

	}

	public MeterStyle getFirstMeteStyle() throws SQLException
	{
		MeterStyle meter = null;

		String sql = "select * from MeteStyle where RowNum = 1";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				meter = new MeterStyle();

				meter.setMeterStyleID(rs.getInt("MeterStyle_ID"));
				meter.setMeterStyleNum(rs.getString("MeterStyle_Num"));
				meter.setMeterStyleName(rs.getString("MeterStyle_Name"));
				meter.setMeterStyleType(rs.getString("MeterStyle_Type"));
				meter.setMeterStyleFactory(rs.getString("MeterStyle_Factory"));
				meter.setMeterStyleTime(rs.getString("MeterStyle_Time"));
				meter.setMeterStyleRemark(rs.getString("MeterStyle_Remark"));
				meter.setDetaiList(getDetailList(meter.getMeterStyleID()));

				return meter;
			}
			else
			{
				meter = new MeterStyle();
				meter.setMeterStyleName("自动添加的表计类型");

				add(meter);

				return getFirstMeteStyle();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		
	}

	/**
	 * 传入sql语句，对数据库进行操作，查出所有表计类型对象，存入List中，并返回
	 * 
	 * @param sql
	 *            查询表计类型的sql语句
	 * @return list 对象为表计对象的List
	 * @throws SQLException
	 */
	public List<MeterStyle> display(String sql) throws SQLException
	{
		MeterStyle meter = null;
		List<MeterStyle> list = new ArrayList<MeterStyle>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next())
			{
				meter = new MeterStyle();
				meter.setMeterStyleID(rs.getInt("MeterStyle_ID"));
				meter.setMeterStyleNum(rs.getString("MeterStyle_Num"));
				meter.setMeterStyleName(rs.getString("MeterStyle_Name"));
				meter.setMeterStyleType(rs.getString("MeterStyle_Type"));
				meter.setMeterStyleFactory(rs.getString("MeterStyle_Factory"));
				meter.setMeterStyleTime(rs.getString("MeterStyle_Time"));
				meter.setMeterStyleRemark(rs.getString("MeterStyle_Remark"));
				meter.setDetaiList(getDetailList(meter.getMeterStyleID()));
				list.add(meter);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}


		return list;
	}

	/**
	 * 对数据库进行操作，查出所有表计类型对象
	 * 
	 * @return list 对象为表计对象的List
	 * @throws SQLException
	 */
	public ArrayList<MeterStyle> displayAll() throws SQLException
	{
		MeterStyle meter = null;
		ArrayList<MeterStyle> list = new ArrayList<MeterStyle>();
		String sql = "Select * from MeteStyle ORDER BY MeteStyle_ID";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next())
			{
				meter = new MeterStyle();
				meter.setMeterStyleID(rs.getInt("MeteStyle_ID"));
				meter.setMeterStyleNum(rs.getString("MeteStyle_Num"));
				meter.setMeterStyleName(rs.getString("MeteStyle_Name"));
				meter.setMeterStyleType(rs.getString("MeteStyle_Type"));
				meter.setMeterStyleFactory(rs.getString("MeteStyle_CHANGJIA"));
				meter.setMeterStyleTime(rs.getString("MeteStyle_Time"));
				meter.setMeterStyleRemark(rs.getString("MeteStyle_Remark"));
				meter.setDetaiList(getDetailList(meter.getMeterStyleID()));
				list.add(meter);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public Map<String, MeterStyle> getMapByName() throws SQLException
	{
		Map<String, MeterStyle> map = null;
		String sql = "Select * from MeteStyle";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next())
			{
				if (map == null)
				{
					map = new HashMap<String, MeterStyle>();
				}
				MeterStyle meter = new MeterStyle();
				meter.setMeterStyleID(rs.getInt("MeteStyle_ID"));
				meter.setMeterStyleNum(rs.getString("MeteStyle_Num"));
				meter.setMeterStyleName(rs.getString("MeteStyle_Name"));
				meter.setMeterStyleType(rs.getString("MeteStyle_Type"));
				meter.setMeterStyleFactory(rs.getString("MeteStyle_CHANGJIA"));
				meter.setMeterStyleTime(rs.getString("MeteStyle_Time"));
				meter.setMeterStyleRemark(rs.getString("MeteStyle_Remark"));
				meter.setDetaiList(getDetailList(meter.getMeterStyleID()));

				map.put(meter.getMeterStyleName(), meter);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	/**
	 * 对数据库进行操作，查出所有表计类型对象
	 * 
	 * @return meter 表计对象
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	public MeterStyle queryByID(int id) throws NumberFormatException,
	SQLException
	{
		String sql = "Select * from MeteStyle where meteStyle_ID=" + id;
		MeterStyle meter = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next())
			{
				meter = new MeterStyle();
				meter.setMeterStyleID(rs.getInt("MeteStyle_ID"));
				meter.setMeterStyleNum(rs.getString("MeteStyle_Num"));
				meter.setMeterStyleName(rs.getString("MeteStyle_Name"));
				meter.setMeterStyleType(rs.getString("MeteStyle_Type"));
				meter.setMeterStyleFactory(rs.getString("MeteStyle_CHANGJIA"));
				meter.setMeterStyleTime(rs.getString("MeteStyle_Time"));
				meter.setMeterStyleRemark(rs.getString("MeteStyle_Remark"));

			}
			if (meter != null)
			{
				meter.setDetaiList(getDetailList(meter.getMeterStyleID()));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return meter;
	}

	public boolean existByName(String meteName) throws SQLException
	{
		boolean flag = false;
		String sql = "Select meteStyle_ID from MeteStyle where MeteStyle_Name = ? and RowNum = 1";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, meteName);
			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}


		return flag;
	}

	/**
	 * 通过传入一个表计类型对象，对数据库进行操作，将此表计类型信息插入数据库中
	 * 
	 * @param meter
	 *            表计类型的对象
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */

	public boolean add(MeterStyle meter) throws SQLException
	{
		String sql = "insert into metestyle "
				+ "(meteStyle_Num,meteStyle_Name,meteStyle_Type,"
				+ "MeteStyle_CHANGJIA,meteStyle_Time,meteStyle_Remark) "
				+ "values(?,?,?,?,?,?)";

		Connection conn=null;
		PreparedStatement ps =null;
		boolean updateMeterStyle=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, meter.getMeterStyleNum());
			ps.setString(2, meter.getMeterStyleName());
			ps.setString(3, meter.getMeterStyleType());
			ps.setString(4, meter.getMeterStyleFactory());
			ps.setString(5, df.format(new Date()));
			ps.setString(6, meter.getMeterStyleRemark());

			updateMeterStyle = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		boolean updateDetailList = true;
		sql="select meterstyle_gen.currval MeterStyleID from dual";
		Connection conn2=null;
		PreparedStatement ps2 =null;
		ResultSet rs = null;
		int MeterStyleID=0;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql);
			rs = ps2.executeQuery();
			MeterStyleID = 0;
			if (rs.next())
			{
				MeterStyleID = rs.getInt("MeterStyleID");
			}

			if (meter.getDetaiList() != null)
				updateDetailList = addDetailList(MeterStyleID,
						meter.getDetaiList());
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, ps2, rs);
		}

		return (updateDetailList && updateMeterStyle);

	}

	/**
	 * 通过传入一个表计类型对象，对数据库进行操作，更新数据库中数据
	 * 
	 * @param meter
	 *            表计类型的对象
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean update(MeterStyle meter) throws SQLException
	{
		String sql = "update metestyle set MeteStyle_Num=? ,MeteStyle_Name=?,"
				+ "MeteStyle_Type=?,MeteStyle_CHANGJIA=?,"
				+ "MeteStyle_Time= ?,"
				+ "MeteStyle_Remark=? where MeteStyle_ID= ?  ";

		Connection conn=null;
		PreparedStatement ps =null;
		boolean updateMeterStyle=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, meter.getMeterStyleNum());
			ps.setString(2, meter.getMeterStyleName());
			ps.setString(3, meter.getMeterStyleType());
			ps.setString(4, meter.getMeterStyleFactory());
			ps.setString(5, meter.getMeterStyleTime());
			ps.setString(6, meter.getMeterStyleRemark());
			ps.setInt(7, meter.getMeterStyleID());

			updateMeterStyle = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		boolean updateDetailList = true;
		if (meter.getDetaiList() != null)
			updateDetailList = updateDetailList(meter.getMeterStyleID(),
					meter.getDetaiList());

		return (updateDetailList && updateMeterStyle);
	}

	public boolean updateDetailList(int theMeteStyleID,
			List<MeterTexing> detaiList) throws SQLException
	{
		boolean flag = true;

		for (MeterTexing mt : detaiList)
		{
			if (queryMeterTexing(theMeteStyleID, mt.getMETERTEXING_ID()) == null)
			{
				addMeterTexing(theMeteStyleID, mt.getMETERTEXING_ID(),
						mt.getValue());
			}
			else
			{
				updateMeterTexing(theMeteStyleID, mt.getMETERTEXING_ID(),
						mt.getValue());
			}
		}

		return flag;
	}
	public boolean addDetailList(int theMeteStyleID,
			List<MeterTexing> detaiList) throws SQLException
	{
		boolean flag = true;

		for (MeterTexing mt : detaiList)
		{
			addMeterTexing(theMeteStyleID, mt.getMETERTEXING_ID(),
					mt.getValue());
		}

		return flag;
	}

	public MeterTexing queryMeterTexing(int meteStyleID, int meterTexingID)
			throws SQLException
	{
		MeterTexing result = null;

		String sql = "select * from TexingValue where METESTYLE_ID = ? and METERTEXING_ID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, meteStyleID);
			ps.setInt(2, meterTexingID);

			rs = ps.executeQuery();

			if (rs.next())
			{
				result = new MeterTexing();

				result.setMETERTEXING_ID(meterTexingID);
				result.setValue(rs.getString("TEXINGVALUE"));
			}
			else
			{
				addMeterTexing(meteStyleID, meterTexingID, "");
				return queryMeterTexing(meteStyleID, meterTexingID);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		//
		//        ps.setInt(1, meteStyleID);
		//        ps.setInt(2, meterTexingID);
		//
		//        ResultSet rs = ps.executeQuery();
		//
		//        if (rs.next())
		//        {
		//            result = new MeterTexing();
		//
		//            result.setMETERTEXING_ID(meterTexingID);
		//            result.setValue(rs.getString("TEXINGVALUE"));
		//        }
		//        else
		//        {
		//            addMeterTexing(meteStyleID, meterTexingID, "");
		//            return queryMeterTexing(meteStyleID, meterTexingID);
		//        }
		//
		//        if (rs != null)
		//            rs.close();
		//
		//        if (ps != null)
		//            ps.close();

		return result;
	}

	public boolean addMeterTexing(int meteStyleID, int meterTexingID,
			String value) throws SQLException
	{
		String sql = "insert into TexingValue (METESTYLE_ID,METERTEXING_ID,TEXINGVALUE) values (?,?,?)";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, meteStyleID);
			ps.setInt(2, meterTexingID);
			ps.setString(3, value);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		//
		//        ps.setInt(1, meteStyleID);
		//        ps.setInt(2, meterTexingID);
		//        ps.setString(3, value);
		//
		//        boolean b = (ps.executeUpdate() == 1);
		//
		//        if (ps != null)
		//            ps.close();

		return b;
	}

	public boolean deleteMeterTexing(int meteStyleID, int meterTexingID)
			throws SQLException
	{
		String sql = "delete from TexingValue where METESTYLE_ID = ? and METERTEXING_ID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, meteStyleID);
			ps.setInt(2, meterTexingID);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		//        
		//
		//        ps.setInt(1, meteStyleID);
		//        ps.setInt(2, meterTexingID);
		//
		//        boolean b = (ps.executeUpdate() == 1);
		//
		//        if (ps != null)
		//            ps.close();

		return b;
	}

	public boolean updateMeterTexing(int meteStyleID, int meterTexingID,
			String value) throws SQLException
	{
		String sql = "update TexingValue set TEXINGVALUE = ? where METESTYLE_ID = ? and METERTEXING_ID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, value);
			ps.setInt(2, meteStyleID);
			ps.setInt(3, meterTexingID);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		//
		//        ps.setString(1, value);
		//        ps.setInt(2, meteStyleID);
		//        ps.setInt(3, meterTexingID);
		//
		//        boolean b = (ps.executeUpdate() == 1);
		//
		//        if (ps != null)
		//            ps.close();

		return b;
	}

	/**
	 * 通过传入表计类型的id,对数据进行操作，删除此id对应表计类型
	 * 
	 * @param id
	 *            表计类型的ID
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		String sql = "delete from metestyle where MeteStyle_ID='" + id + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		//
		//        boolean b = (ps.executeUpdate() == 1);
		//
		//        if (ps != null)
		//            ps.close();
		return b;
	}

	/**
	 * 对数据库进行操作，查出所有表计类型对象
	 * 
	 * @param ammType
	 *            电表类型
	 * @return list 对象为表计对象的List
	 * @throws SQLException
	 */
	public ArrayList<MeterStyle> displayAllType(String ammType)
			throws SQLException
			{
		MeterStyle meter = null;
		ArrayList<MeterStyle> list = new ArrayList<MeterStyle>();
		String sql = "Select * from MeteStyle where MeteStyle_Type='" + ammType
				+ "' ORDER BY MeteStyle_ID";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next())
			{
				meter = new MeterStyle();
				meter.setMeterStyleID(rs.getInt("MeteStyle_ID"));
				meter.setMeterStyleNum(rs.getString("MeteStyle_Num"));
				meter.setMeterStyleName(rs.getString("MeteStyle_Name"));
				meter.setMeterStyleType(rs.getString("MeteStyle_Type"));
				meter.setMeterStyleFactory(rs.getString("MeteStyle_CHANGJIA"));
				meter.setMeterStyleTime(rs.getString("MeteStyle_Time"));
				meter.setMeterStyleRemark(rs.getString("MeteStyle_Remark"));
				list.add(meter);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}


		return list;
	}

	public ArrayList<MeterStyle> queryByType(String ammType)
			throws SQLException
			{
		MeterStyle meter = null;
		ArrayList<MeterStyle> list = new ArrayList<MeterStyle>();
		String sql = "Select * from MeteStyle where MeteStyle_Type='" + ammType
				+ "' ORDER BY MeteStyle_ID";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next())
			{
				meter = new MeterStyle();
				meter.setMeterStyleID(rs.getInt("MeteStyle_ID"));
				meter.setMeterStyleNum(rs.getString("MeteStyle_Num"));
				meter.setMeterStyleName(rs.getString("MeteStyle_Name"));
				meter.setMeterStyleType(rs.getString("MeteStyle_Type"));
				meter.setMeterStyleFactory(rs.getString("MeteStyle_CHANGJIA"));
				meter.setMeterStyleTime(rs.getString("MeteStyle_Time"));
				meter.setMeterStyleRemark(rs.getString("MeteStyle_Remark"));

				list.add(meter);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		//        ResultSet rs = ps.executeQuery();
		//
		//        while (rs.next())
		//        {
		//            meter = new MeterStyle();
		//            meter.setMeterStyleID(rs.getInt("MeteStyle_ID"));
		//            meter.setMeterStyleNum(rs.getString("MeteStyle_Num"));
		//            meter.setMeterStyleName(rs.getString("MeteStyle_Name"));
		//            meter.setMeterStyleType(rs.getString("MeteStyle_Type"));
		//            meter.setMeterStyleFactory(rs.getString("MeteStyle_CHANGJIA"));
		//            meter.setMeterStyleTime(rs.getString("MeteStyle_Time"));
		//            meter.setMeterStyleRemark(rs.getString("MeteStyle_Remark"));
		//
		//            list.add(meter);
		//        }
		//        if (rs != null)
		//            rs.close();
		//
		//        if (ps != null)
		//            ps.close();
		return list;
	}

	private MeterStyle buildInstance(ResultSet rs) throws SQLException
	{
		MeterStyle meter = new MeterStyle();

		meter.setMeterStyleID(rs.getInt("MeteStyle_ID"));
		meter.setMeterStyleNum(rs.getString("MeteStyle_Num"));
		meter.setMeterStyleName(rs.getString("MeteStyle_Name"));
		meter.setMeterStyleType(rs.getString("MeteStyle_Type"));
		meter.setMeterStyleFactory(rs.getString("MeteStyle_CHANGJIA"));
		meter.setMeterStyleTime(rs.getString("MeteStyle_Time"));
		meter.setMeterStyleRemark(rs.getString("MeteStyle_Remark"));

		return meter;
	}

	public List<MeterTexing> getDetailList(int theMeterStyleID)
			throws SQLException
			{
		List<MeterTexing> list = null;
		String sql = "select * from TEXINGVALUE,MeterTexing where MeteStyle_ID = ? and TEXINGVALUE.MeterTexing_ID=MeterTexing.MeterTexing_ID";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theMeterStyleID);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<MeterTexing>();

				MeterTexing mt = new MeterTexing();

				mt.setMETERTEXING_ID(rs.getInt("METERTEXING_ID"));
				mt.setMETERTEXING_NAME(rs.getString("METERTEXING_NAME"));
				mt.setMETESTYLE_TYPE(rs.getString("METESTYLE_TYPE"));
				mt.setMETERTEXING_REMARK(rs.getString("METERTEXING_REMARK"));
				mt.setMETERTEXING_VALUESTYLE(rs.getString("METERTEXING_VALUESTYLE"));
				mt.setMETERTEXING_SHOWTYPE(rs.getString("METERTEXING_SHOWTYPE"));
				mt.setValue(rs.getString("TEXINGVALUE"));

				list.add(mt);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		//
		//        ps.setInt(1, theMeterStyleID);
		//
		//        ResultSet rs = ps.executeQuery();
		//
		//        while (rs.next())
		//        {
		//            if (list == null)
		//                list = new LinkedList<MeterTexing>();
		//
		//            MeterTexing mt = new MeterTexing();
		//
		//            mt.setMETERTEXING_ID(rs.getInt("METERTEXING_ID"));
		//            mt.setMETERTEXING_NAME(rs.getString("METERTEXING_NAME"));
		//            mt.setMETESTYLE_TYPE(rs.getString("METESTYLE_TYPE"));
		//            mt.setMETERTEXING_REMARK(rs.getString("METERTEXING_REMARK"));
		//            mt.setMETERTEXING_VALUESTYLE(rs.getString("METERTEXING_VALUESTYLE"));
		//            mt.setMETERTEXING_SHOWTYPE(rs.getString("METERTEXING_SHOWTYPE"));
		//            mt.setValue(rs.getString("TEXINGVALUE"));
		//
		//            list.add(mt);
		//        }
		//
		//        if (rs != null)
		//            rs.close();
		//
		//        if (ps != null)
		//            ps.close();

		return list;
			}

	public String getDetaiHtml(String type) throws SQLException
	{
		String htmlString = "";
		String sql = "select * from MeterTexing where METESTYLE_TYPE = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, type);

			rs = ps.executeQuery();

			while (rs.next())
			{
				String showType = rs.getString("METERTEXING_SHOWTYPE");

				int MeterTexing_ID = rs.getInt("METERTEXING_ID");

				if ("CheckBox".equals(showType))
				{
					htmlString += "<div class='manageSection'>";
					htmlString += "<span>" + rs.getString("METERTEXING_NAME")
							+ ":</span>";
					htmlString += "<select name='"
							+ MeterTexing_ID
							+ "'><option value='1'>是</option><option value='0'>否</option></select>";
					htmlString += "</div>";
				}

				if ("TextBox".equals(showType))
				{
					htmlString += "<div class='manageSection'>";
					htmlString += "<span>" + rs.getString("METERTEXING_NAME")
							+ ":</span>";
					htmlString += "<input type='text' name='" + MeterTexing_ID
							+ "'>";
					htmlString += "</div>";
				}

				if ("DropDownList".equals(showType))
				{
					int valueStyle = Integer.parseInt(rs
							.getString("METERTEXING_VALUESTYLE"));
					List<Map<String, String>> dropList = getDropList(valueStyle);

					String select = "<select name='" + MeterTexing_ID + "'>";
					for (Map<String, String> m : dropList)
					{

						String option = "<option value='" + m.get("num") + "'>"
								+ m.get("value") + "</option>";
						select += option;
					}
					select += "</select>";

					htmlString += "<div class='manageSection'>";
					htmlString += "<span>" + rs.getString("METERTEXING_NAME")
							+ ":</span>";
					htmlString += select;
					htmlString += "</div>";
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		//
		//        ps.setString(1, type);
		//
		//        ResultSet rs = ps.executeQuery();
		//
		//        while (rs.next())
		//        {
		//            String showType = rs.getString("METERTEXING_SHOWTYPE");
		//
		//            int MeterTexing_ID = rs.getInt("METERTEXING_ID");
		//
		//            if ("CheckBox".equals(showType))
		//            {
		//                htmlString += "<div class='manageSection'>";
		//                htmlString += "<span>" + rs.getString("METERTEXING_NAME")
		//                        + ":</span>";
		//                htmlString += "<select name='"
		//                        + MeterTexing_ID
		//                        + "'><option value='1'>是</option><option value='0'>否</option></select>";
		//                htmlString += "</div>";
		//            }
		//
		//            if ("TextBox".equals(showType))
		//            {
		//                htmlString += "<div class='manageSection'>";
		//                htmlString += "<span>" + rs.getString("METERTEXING_NAME")
		//                        + ":</span>";
		//                htmlString += "<input type='text' name='" + MeterTexing_ID
		//                        + "'>";
		//                htmlString += "</div>";
		//            }
		//
		//            if ("DropDownList".equals(showType))
		//            {
		//                int valueStyle = Integer.parseInt(rs
		//                        .getString("METERTEXING_VALUESTYLE"));
		//                List<Map<String, String>> dropList = getDropList(valueStyle);
		//
		//                String select = "<select name='" + MeterTexing_ID + "'>";
		//                for (Map<String, String> m : dropList)
		//                {
		//
		//                    String option = "<option value='" + m.get("num") + "'>"
		//                            + m.get("value") + "</option>";
		//                    select += option;
		//                }
		//                select += "</select>";
		//
		//                htmlString += "<div class='manageSection'>";
		//                htmlString += "<span>" + rs.getString("METERTEXING_NAME")
		//                        + ":</span>";
		//                htmlString += select;
		//                htmlString += "</div>";
		//            }
		//        }
		//
		//        if (rs != null)
		//            rs.close();
		//
		//        if (ps != null)
		//            ps.close();

		return htmlString;

	}

	public List<Map<String, String>> getDropList(int dictionaryID)
			throws SQLException
			{
		List<Map<String, String>> dropList = null;

		String sql = "select * from DICTIONARYVALUE where DICTIONARY_ID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dictionaryID);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (dropList == null)
					dropList = new LinkedList<Map<String, String>>();

				Map<String, String> option = new HashMap<String, String>();

				option.put("num", rs.getString("DICTIONARYVALUE_NUM"));
				option.put("value", rs.getString("DICTIONARYVALUE_VALUE"));

				dropList.add(option);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		//
		//        ps.setInt(1, dictionaryID);
		//
		//        ResultSet rs = ps.executeQuery();
		//
		//        while (rs.next())
		//        {
		//            if (dropList == null)
		//                dropList = new LinkedList<Map<String, String>>();
		//
		//            Map<String, String> option = new HashMap<String, String>();
		//
		//            option.put("num", rs.getString("DICTIONARYVALUE_NUM"));
		//            option.put("value", rs.getString("DICTIONARYVALUE_VALUE"));
		//
		//            dropList.add(option);
		//        }
		//
		//        if (rs != null)
		//            rs.close();
		//
		//        if (ps != null)
		//            ps.close();

		return dropList;
			}

	public Integer getBeiLvByMSID(int meterStyleID)
			throws NumberFormatException, SQLException
	{
		Integer beilv = null;

		MeterStyle ms = queryByID(meterStyleID);

		if (ms == null)
			return 1;

		int MeterTexingID = 0;
		if (ms.getMeterStyleType().equals("电表"))
			MeterTexingID = 8;

		if (ms.getMeterStyleType().equals("水表"))
			MeterTexingID = 13;

		String sql = "select TexingValue from TexingValue where MeteStyle_ID = ? and MeterTexing_ID = ? and RowNum = 1";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, meterStyleID);
			ps.setInt(2, MeterTexingID);
			rs = ps.executeQuery();

			if (rs.next())
			{
				beilv = rs.getInt("TexingValue");
			}
			else
			{
				beilv = 1;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		//
		//        ps.setInt(1, meterStyleID);
		//        ps.setInt(2, MeterTexingID);
		//
		//        ResultSet rs = ps.executeQuery();
		//
		//        if (rs.next())
		//        {
		//            beilv = rs.getInt("TexingValue");
		//        }
		//        else
		//        {
		//            beilv = 1;
		//        }
		//
		//        if (rs != null)
		//            rs.close();
		//
		//        if (ps != null)
		//            ps.close();

		return beilv;
	}
}
