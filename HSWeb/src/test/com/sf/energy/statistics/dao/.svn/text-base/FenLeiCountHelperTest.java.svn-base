package com.sf.energy.statistics.dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.sf.energy.statistics.model.FenLeiCountModel;

public class FenLeiCountHelperTest
{
    FenLeiCountHelper fch = new FenLeiCountHelperImpl();

    @Test
    public void getArcFenLeiDataEveryDayInArea()
    {
        try
        {
            List<FenLeiCountModel> list = fch.getArcFenLeiDataEveryDayInArea(1,
                    2014, 8);

            print(list);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void getArcFenLeiDataByMonthInArea()
    {
        try
        {
            List<FenLeiCountModel> list = fch.getArcFenLeiDataByMonthInArea(
                    1120, 2014, 4);
            print(list);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void getArcFenLeiDataEveryMonthInArea()
    {
        try
        {
            List<FenLeiCountModel> list = fch.getArcFenLeiDataEveryMonthInArea(
                    1, 2014);

            Collections.sort(list, new Comparator<FenLeiCountModel>()
            {
                public int compare(FenLeiCountModel arg0, FenLeiCountModel arg1)
                {
                    return arg0.getSelectMonth().compareTo(
                            arg1.getSelectMonth());
                }
            });

            for (FenLeiCountModel fcm : list)
            {
                System.out.println(fcm.getSelectMonth());
            }
            // print(list);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void print(List<FenLeiCountModel> list)
    {
        for (FenLeiCountModel fcm : list)
        {
            print(fcm);
            System.out.println();
        }
    }

    private void print(FenLeiCountModel fcm)
    {
        System.out.println("年:" + fcm.getSelectYear());
        System.out.println("月:" + fcm.getSelectMonth());
        System.out.println("日:" + fcm.getSelectDay());

        System.out.println("建筑名称:" + fcm.getArchitectureName());
        System.out.println("建筑ID:" + fcm.getArchitectureID());

        System.out.println("使用电量:" + fcm.getDianNengCount());
        System.out.println("电费:" + fcm.getDianNengMoney());

        System.out.println("使用水量:" + fcm.getShuiNengCount());
        System.out.println("水费:" + fcm.getShuiNengMoney());

        // System.out.println("使用气量:" + fcm.getQiCount());
        // System.out.println("燃气费:" + fcm.getQiMoney());
        //
        // System.out.println("使用煤量:" + fcm.getMeiCount());
        // System.out.println("煤费:" + fcm.getMeiMoney());
        //
        // System.out.println("使用汽油量:" + fcm.getQiCount());
        // System.out.println("汽油费:" + fcm.getQiMoney());
        //
        // System.out.println("使用煤油量:" + fcm.getMeiYouCount());
        // System.out.println("煤油费:" + fcm.getMeiYouMoney());
        //
        // System.out.println("使用柴油量:" + fcm.getChaiYouCount());
        // System.out.println("柴油费:" + fcm.getChaiYouMoney());
        //
        // System.out.println("使用液化石油气量:" + fcm.getYeHuaShiYouQiCount());
        // System.out.println("液化石油气费:" + fcm.getYeHuaShiYouQiMoney());
        //
        // System.out.println("使用人工煤气量:" + fcm.getRenGongMeiQiCount());
        // System.out.println("人工煤气费:" + fcm.getRenGongMeiQiMoney());
        //
        // System.out.println("使用燃气量:" + fcm.getRanRiCount());
        // System.out.println("燃气费:" + fcm.getRanRiMoney());
    }
}
