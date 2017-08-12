package com.sf.energy.project.display.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article
{
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    private int articleID = 0;
    private int channelID = 0;
    private String channel = "";
    private String title = "";
    private Date submitDate = null;
    private String author = "";
    private String imgPath = "";
    private String content = "";
    private int clickCount = 0;

    public int getArticleID()
    {
        return articleID;
    }

    public void setArticleID(int articleID)
    {
        this.articleID = articleID;
    }

    public Article()
    {
        submitDate = new Date();
    }

    public int getChannelID()
    {
        return channelID;
    }

    public void setChannelID(int channelID)
    {
        this.channelID = channelID;
    }

    public String getChannel()
    {
        return channel;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public String getImgPath()
    {
        return imgPath;
    }

    public void setImgPath(String imgPath)
    {
        this.imgPath = imgPath;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLongSubmitDateString()
    {
        return sdf1.format(submitDate);
    }

    public String getShortSubmitDateString()
    {
        return sdf2.format(submitDate);
    }

    public Date getSubmitDate()
    {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate)
    {
        this.submitDate = submitDate;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getContent()
    {
        return content;
    }

    public String getDescription(int limit)
    {
        if (content.length() > limit)
            return content.substring(0, limit);
        else
        {
            return content;
        }
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public int getClickCount()
    {
        return clickCount;
    }

    public void setClickCount(int clickCount)
    {
        this.clickCount = clickCount;
    }
}
