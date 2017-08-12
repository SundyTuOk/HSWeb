package com.sf.energy.project.equipment.dao;

import java.sql.SQLException;

import org.junit.Test;

import com.sf.energy.project.equipment.model.LightDeviceCtlModel;

public class LightDeviceCtlDaoTest
{
	LightDeviceCtlDao ldcd = new LightDeviceCtlDao();

	@Test
	public void addLightDeviceCtl() throws SQLException
	{
		LightDeviceCtlModel ldc = new LightDeviceCtlModel();

		ldc.setLightRoomNo(1);
		ldc.setGather_ID(1);
		ldc.setGather_PortNo(1);
		ldc.setBaudRate(9600);

		ldcd.addLightDeviceCtl(ldc);
	}

	@Test
	public void getLightDeviceCtlByDeviceNo() throws SQLException
	{
		LightDeviceCtlModel ldc = null;

		ldc = ldcd.getLightDeviceCtlByDeviceNo(7);

		if (ldc != null)
			System.out.println("success");
	}
}
