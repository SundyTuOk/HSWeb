package com.sf.energy.project.display.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.junit.Test;

import com.sf.energy.project.display.model.Article;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.util.ExportHelper;

public class ArticleDaoTest
{
    ArticleDao ad = new ArticleDao();

    @Test
    public void addChannel() throws SQLException
    {
        String[] aa = { "学习资料", "新闻公告", "行政单位", "用电设备", "学生能耗", "节能专题" };

        for (String a : aa)
        {
            if (ad.insertChannel(a))
                System.out.println(a + " inserted success");
            else
                System.out.println(a + " inserted failed");
        }
    }

    @Test
    public void addArticle() throws IOException
    {
        for (int j = 21; j <= 26; j++)
        {
            for (int i = 0; i < 40; i++)
            {
                Article a = new Article();

                String title = (i + 1) + "王钊是个帅小伙儿小伙";

                a.setTitle(title);
                a.setAuthor("王钊");
                a.setChannelID(j);
                a.setSubmitDate(new Date());
                a.setImgPath("../images/post1.jpg");
                String content = "有时候不是我不理你，其实我也想你了，只是我不知道该对你说什么。"
                        + "不管过去如何，过去的已经过去，最好的总在未来等着你。当我们懂得珍惜平凡的幸福"
                        + "的时候，就已经成了人生的赢家。Nothing is as sweet as you再没什么，能"
                        + "甜蜜如你。我以为只要很认真的喜欢就能打动一个人...";
                a.setContent(content);
                a.setClickCount(i + 1);

                try
                {
                    if (ad.addArticle(a))
                        System.out.println("success");
                    else
                    {
                        System.err.println("fail");
                    }
                }
                catch (SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    @Test
    public void selectPaginatedArticlesByChannel() throws SQLException,
            IOException
    {
        // Article article = ad.selectArticleByID(243);
        // //
        // System.out.println(article.getContent());

        List<Article> list = ad.selectLatestArticles(9);
        //
        System.out.println(list.size());
        // List<Article> list = ad.selectArticleByChannel("新闻公告", -1);

        // List<Article> list = ad.selectPaginatedArticlesByChannel("新闻公告", 0,
        // 10);
        //
        // for (Article a : list)
        // {
        // System.out.println("title:" + a.getTitle());
        // }
    }

    @Test
    public void aaabbb() throws RowsExceededException, WriteException,
            IOException, SQLException
    {
        ExportHelper dh = ExportHelper.getInstance();
        String[] titleList = { "区域ID", "区域编号", "区域名称", "区域地址", "区域电话", "区域备注" }, lableList = {
                "Area_ID", "Area_Num", "Area_Name", "Area_Address",
                "Area_Phone", "Area_Remark" };
        String sql = "Select * from Area";
        dh.getExportFile(titleList, lableList, sql);
    }

    @Test
    public void sdfdssdfs() throws RowsExceededException, WriteException,
            IOException, SQLException
    {
        String s = "新图书馆-1";

        System.out.println(s.substring(0, s.lastIndexOf("-")));
        System.out.println(s.substring(s.lastIndexOf("-") + 1));
    }

    @Test
    public void adfasdfasd()
    {
        Date theDate = new Date();

        System.out.println(theDate.getYear() + " " + theDate.getMonth());
    }

    @Test
    public void csdcds() throws RowsExceededException, WriteException,
            IOException, SQLException
    {
        ExportHelper dh = ExportHelper.getInstance();
        String[] titleList = { "区域ID", "区域编号", "区域名称", "区域地址", "区域电话", "区域备注" };
        String[] lableList = { "Area_ID", "Area_Num", "Area_Name",
                "Area_Address", "Area_Phone", "Area_Remark" };
        AreaDao ad = new AreaDao();
        List<com.sf.energy.project.system.model.Area> l = ad.display();
        List<List<String>> data = new LinkedList<List<String>>();

        List<String> titleLine = new LinkedList<String>();

        for (String title : titleList)
        {
            titleLine.add(title);
        }

        data.add(titleLine);

        for (Area a : l)
        {
            List<String> item = new LinkedList<String>();

            item.add(a.getId() + "");
            item.add(a.getNum());
            item.add(a.getName());
            item.add(a.getAddress());
            item.add(a.getPhone());
            item.add(a.getRemark());

            data.add(item);
        }

        dh.getExportFile(data);
    }
}
