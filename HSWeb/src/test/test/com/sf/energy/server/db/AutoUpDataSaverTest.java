package test.com.sf.energy.server.db;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.system.dao.GatherDao;
import com.sf.energy.project.system.model.Gather;
import com.sf.energy.server.db.AutoUpDataSaver;
import com.sf.energy.util.ConnDB;

/**
 * <测试存储单相三相数据到数据库> 
 * 
 * @author 陆锦泉
 * @version v1.0
 * @since 服务程序
 */
public class AutoUpDataSaverTest
{

	@Test
	public void testDataSave()
	{
		// 插入网关信息
		Gather gm = new Gather();
		GatherDao gd = new GatherDao();
		gm.setGatherNum("test001");
		gm.setGatherName("testgather");
		gm.setGatherAddress("test000001");
		gd.add(gm);

		// 插入电表信息
		AmmeterDao ad = new AmmeterDao();
		AmmeterModel am = new AmmeterModel();
		am.setAmmeterPoint(4);
		am.setAmmeterNum("4");
		am.setAmmeterName("testmeter1");
		am.setAmmeterAddress485("0000001110");
		try
		{
			ad.insert(am);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		// 插入单相电量数据
		try
		{
			AutoUpDataSaver.dataSave("test000001", "01", "0000001110",
					"0C0110", "2014-04-10 17:00:00", "100.00");
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 断言
		String sql = "select ZValueZY from ammeterdatas where VALUETIME = to_date('2014-04-10 17:00:00','yyyy-mm-dd hh24:mi:ss')";
		ResultSet rs = null;
		rs = ConnDB.executeQuery(sql);
		try
		{
			if(rs.next())
			{
				int a = rs.getInt(1);
				assertEquals(100, a);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 删除临时测试数据
		sql = "delete from gather where GATHER_ADDRESS = 'test000001'";
		ConnDB.executeUpdate(sql);
		
		sql = "delete from ammeter where AMMETER_485ADDRESS = '0000001110'";
		ConnDB.executeUpdate(sql);
		
		sql = "delete from ammeterdatas where VALUETIME = to_date('2014-04-10 17:00:00','yyyy-mm-dd hh24:mi:ss')";
		ConnDB.executeUpdate(sql);

	}

	@Test
	public void testDataSaveThreePhase()
	{
		// 插入网关信息
		Gather gm = new Gather();
		GatherDao gd = new GatherDao();
		gm.setGatherNum("test001");
		gm.setGatherName("testgather");
		gm.setGatherAddress("test000001");
		gd.add(gm);

		// 插入电表信息
		AmmeterDao ad = new AmmeterDao();
		AmmeterModel am = new AmmeterModel();
		am.setAmmeterPoint(4);
		am.setAmmeterNum("4");
		am.setAmmeterName("testmeter1");
		am.setAmmeterAddress485("0000001110");
		try
		{
			ad.insert(am);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		// 插入三相电量用量数据
		ArrayList<String> dataArray = new ArrayList<String>();
		dataArray.add("test000001");
		dataArray.add("0000001110");
		dataArray.add("2014-04-10 17:00:00");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");
		dataArray.add("2");

		try
		{
			AutoUpDataSaver.dataSaveThreePhase(dataArray);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 断言
		String sql = "select POWERZY from ammeterpddatas where VALUETIME = to_date('2014-04-10 17:00:00','yyyy-mm-dd hh24:mi:ss')";
		ResultSet rs = null;
		rs = ConnDB.executeQuery(sql);
		try
		{
			if(rs.next())
			{
				int a = rs.getInt(1);
				assertEquals(2, a);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 删除临时测试数据
		sql = "delete from gather where GATHER_ADDRESS = 'test000001'";
		ConnDB.executeUpdate(sql);
		
		sql = "delete from ammeter where AMMETER_485ADDRESS = '0000001110'";
		ConnDB.executeUpdate(sql);
		
		sql = "delete from ammeterpddatas where VALUETIME = to_date('2014-04-10 17:00:00','yyyy-mm-dd hh24:mi:ss')";
		ConnDB.executeUpdate(sql);

	}

}
