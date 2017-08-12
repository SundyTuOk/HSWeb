package com.sf.energy.prepayment.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.sf.energy.prepayment.model.ByMonthSellModel;
import com.sf.energy.prepayment.model.ByYearSellModel;

public class PrepaymenReportHelperTest
{
    PrepaymenReportHelper prh = new PrepaymenReportHelper();

    @Test
    public void getAllSellInfoByMonth() throws SQLException
    {
        List<ByMonthSellModel> list = prh.getAllSellInfoByMonth(2014, 6);

        if (list != null && list.size() > 0)
        {
            for (ByMonthSellModel bsm : list)
            {
                print(bsm);
            }
        }

    }

    @Test
    public void getArchSellInfoByMonth() throws SQLException
    {
        List<ByMonthSellModel> list = prh.getArchSellInfoByMonth(21, 2014, 6);

        if (list != null && list.size() > 0)
        {
            for (ByMonthSellModel bsm : list)
            {
                print(bsm);
            }
        }
    }

    @Test
    public void getAreaSellInfoByMonth() throws SQLException
    {
        List<ByMonthSellModel> list = prh.getAreaSellInfoByMonth(1, 2014, 6);

        if (list != null && list.size() > 0)
        {

            for (ByMonthSellModel bsm : list)
            {
                print(bsm);
            }
        }
    }

    @Test
    public void getAllSellInfoByYear() throws SQLException
    {
        ByYearSellModel bsm = prh.getAllSellInfoByYear(2014);

        if (bsm != null)
        {
            System.out.println("1月：" + bsm.getMonth1());
            System.out.println("2月：" + bsm.getMonth2());
            System.out.println("3月：" + bsm.getMonth3());
            System.out.println("4月：" + bsm.getMonth4());
            System.out.println("5月：" + bsm.getMonth5());
            System.out.println("6月：" + bsm.getMonth6());
            System.out.println("7月：" + bsm.getMonth7());
            System.out.println("8月：" + bsm.getMonth8());
            System.out.println("9月：" + bsm.getMonth9());
            System.out.println("10月：" + bsm.getMonth10());
            System.out.println("11月：" + bsm.getMonth11());
            System.out.println("12月：" + bsm.getMonth12());
        }
    }

    private void print(ByMonthSellModel bmsm)
    {
        System.out.println("年：" + bmsm.getQueryYear());
        System.out.println("月：" + bmsm.getQueryMonth());
        System.out.println("日：" + bmsm.getDay());
        System.out.println("名称：" + bmsm.getName());
        System.out.println("用量：" + bmsm.getDayCount());
        System.out.println();
    }
}
