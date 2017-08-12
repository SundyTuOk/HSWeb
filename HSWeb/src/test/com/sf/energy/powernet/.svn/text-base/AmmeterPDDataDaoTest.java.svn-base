package com.sf.energy.powernet;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.sf.energy.powernet.dao.AmMeterPDDataDao;
import com.sf.energy.powernet.model.AmMeterPDDatasModel;
import com.sf.energy.project.system.dao.MeterStyleDao;
import com.sf.energy.project.system.model.MeterStyle;

public class AmmeterPDDataDaoTest
{
    AmMeterPDDataDao add = new AmMeterPDDataDao();

    @Test
    public void getAmPDDataBetweenByPDPartID() throws SQLException
    {
        Date startTime = new Date(), endTime = new Date();
        startTime.setYear(113);
        endTime.setYear(115);
        List<AmMeterPDDatasModel> list = add.getAmPDDataBetweenByPDPartID(25,
                startTime, endTime, 0, 0);

        System.out.println(list.size());
        System.out.println(list.get(0).getRecordTime().toLocaleString());
        System.out.println(list.get(list.size() - 1).getRecordTime()
                .toLocaleString());
    }

    @Test
    public void test() throws SQLException
    {
        MeterStyle ms = new MeterStyle();
        ms.setMeterStyleFactory("盛帆");
        MeterStyleDao msd = new MeterStyleDao();
        ms = msd.queryByID(1083);
        ms.setMeterStyleType("水表");
        System.out.println(msd.update(ms));
    }
}
