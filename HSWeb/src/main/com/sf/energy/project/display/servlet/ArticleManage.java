package com.sf.energy.project.display.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.display.dao.ArticleDao;
import com.sf.energy.project.display.model.Article;
import com.sf.energy.project.display.model.ChannelModel;

public class ArticleManage extends HttpServlet
{

    String numberPatern = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            findMethod(request, response);
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JRException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void findMethod(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException,
            JRException, ClassNotFoundException
    {
        String method = request.getParameter("method");

        if (method.equalsIgnoreCase("getArticlesByChannel"))
            getArticlesByChannel(request, response);

        if (method.equalsIgnoreCase("getLatestArticles"))
            getLatestArticles(request, response);

        if (method.equalsIgnoreCase("getPopularArticles"))
            getPopularArticles(request, response);

        if (method.equalsIgnoreCase("getAllArticles"))
            getAllArticles(request, response);

        if (method.equalsIgnoreCase("getAllChanneles"))
            getAllChanneles(request, response);

        if (method.equalsIgnoreCase("getPaginatedArticlesByChannel"))
            getPaginatedArticlesByChannel(request, response);

        if (method.equalsIgnoreCase("addNewArticle"))
            addNewArticle(request, response);

        if (method.equalsIgnoreCase("updateArticle"))
            updateArticle(request, response);

        if (method.equalsIgnoreCase("updateChannel"))
            updateChannel(request, response);

        if (method.equalsIgnoreCase("deleteChannel"))
            deleteChannel(request, response);

        if (method.equalsIgnoreCase("addChannel"))
            addChannel(request, response);

        if (method.equalsIgnoreCase("initNewsList"))
            initNewsList(request, response);

        if (method.equalsIgnoreCase("initAside"))
            initAside(request, response);

        if (method.equalsIgnoreCase("getDetail"))
            getDetail(request, response);

        if (method.equalsIgnoreCase("deleteArticle"))
            deleteArticle(request, response);
    }

    private void deleteArticle(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        int articleID = 0;
        if (request.getParameter("ArticleID") != null
                && request.getParameter("ArticleID").matches(numberPatern))
        {
            articleID = Integer.parseInt(request.getParameter("ArticleID"));
        }
        else
        {
            return;
        }

        String info = "fail";
        if (ad.deleteArticleByID(articleID))
            info = "success";

        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }

    private void getDetail(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        Article article = null;

        int articleID = 0;
        if (request.getParameter("ChannelID") != null
                && request.getParameter("ChannelID").matches(numberPatern))
        {
            articleID = Integer.parseInt(request.getParameter("ChannelID"));
        }
        else
        {
            return;
        }

        article = ad.selectArticleByID(articleID);

        if (article != null)
        {
            ad.addClickCount(articleID);

            JSONObject jo = new JSONObject();

            jo.put("ArticleID", article.getArticleID());
            jo.put("ChannelID", article.getChannelID());
            jo.put("Title", article.getTitle());
            jo.put("SubmitDate", article.getLongSubmitDateString());
            jo.put("Author", article.getAuthor());
            jo.put("ImgPath", article.getImgPath());
            jo.put("Content", article.getContent());
            jo.put("ClickCount", article.getClickCount());

            PrintWriter out = response.getWriter();
            out.println(jo.toString());
            out.flush();
            out.close();
        }

    }

    private void initAside(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        List<Article> latestList = ad.selectLatestArticles(9);
        List<Article> popularList = ad.selectPopularArticles(9);

        if ((latestList != null && latestList.size() > 0)
                || (popularList != null && popularList.size() > 0))
        {
            JSONObject main = new JSONObject();

            if (latestList != null && latestList.size() > 0)
            {
                JSONArray latest = new JSONArray();
                for (Article art : latestList)
                {
                    JSONObject jo = new JSONObject();

                    jo.put("ArticleID", art.getArticleID());
                    jo.put("Title", art.getTitle());

                    latest.add(jo);
                }

                main.put("LatestList", latest);
            }

            if (popularList != null && popularList.size() > 0)
            {
                JSONArray popular = new JSONArray();
                for (Article art : popularList)
                {
                    JSONObject jo = new JSONObject();

                    jo.put("ArticleID", art.getArticleID());
                    jo.put("Title", art.getTitle());

                    popular.add(jo);
                }

                main.put("PopularList", popular);
            }

            PrintWriter out = response.getWriter();
            out.println(main.toString());
            out.flush();
            out.close();
        }

    }

    private void initNewsList(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        List<ChannelModel> channelList = ad.selectAllChanneles();
        List<Article> articleList = null;
        int limitCount = 5;

        if (channelList != null && channelList.size() > 0)
        {
            JSONArray json = new JSONArray();

            for (ChannelModel cm : channelList)
            {
                articleList = ad.selectArticleByChannelID(cm.getChannelID(),
                        limitCount);
                JSONArray channelArticle = new JSONArray();

                for (Article atr : articleList)
                {
                    JSONObject jo = new JSONObject();

                    jo.put("ArticleID", atr.getArticleID());
                    jo.put("Title", atr.getTitle());
                    jo.put("SubmitDate", atr.getShortSubmitDateString());

                    channelArticle.add(jo);
                }

                JSONObject jo1 = new JSONObject();

                jo1.put("ChannelName", cm.getChannelName());
                jo1.put("ArticleList", channelArticle);

                json.add(jo1);
            }

            PrintWriter out = response.getWriter();
            out.println(json);
            out.flush();
            out.close();
        }
    }

    private void addChannel(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        String newChannelName = null;
        if (request.getParameter("ChannelName") != null)
        {
            newChannelName = request.getParameter("ChannelName");
        }
        else
        {
            return;
        }

        String info = "fail";
        if (ad.insertChannel(newChannelName))
            info = "success";

        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }

    private void deleteChannel(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        int channelID = 0;
        if (request.getParameter("ChannelID") != null
                && request.getParameter("ChannelID").matches(numberPatern))
        {
            channelID = Integer.parseInt(request.getParameter("ChannelID"));
        }
        else
        {
            return;
        }

        String info = "fail";
        if (ad.deleteChannelByID(channelID))
            info = "success";

        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }

    private void updateChannel(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        String newChannelName = null;
        if (request.getParameter("ChannelName") != null)
        {
            newChannelName = request.getParameter("ChannelName");
        }
        else
        {
            return;
        }

        int channelID = 0;
        if (request.getParameter("ChannelID") != null
                && request.getParameter("ChannelID").matches(numberPatern))
        {
            channelID = Integer.parseInt(request.getParameter("ChannelID"));
        }
        else
        {
            return;
        }

        String info = "fail";
        if (ad.updateChannel(channelID, newChannelName))
            info = "success";

        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }

    private void updateArticle(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        int articleID = 0;
        if (request.getParameter("ArticleID") != null
                && request.getParameter("ArticleID").matches(numberPatern))
            articleID = Integer.parseInt(request.getParameter("ArticleID"));
        else
        {
            return;
        }

        Article a = ad.selectArticleByID(articleID);

        if (a == null)
            return;

        String title = null;
        if (request.getParameter("Title") != null)
            title = request.getParameter("Title");
        else
        {
            return;
        }

        int channelID = 0;
        if (request.getParameter("ChannelID") != null
                && request.getParameter("ChannelID").matches(numberPatern))
            channelID = Integer.parseInt(request.getParameter("ChannelID"));
        else
        {
            return;
        }

        String content = null;
        if (request.getParameter("Content") != null)
            content = request.getParameter("Content");
        else
        {
            return;
        }

        // String imgPath = null;
        // if (request.getParameter("ImgPath") != null)
        // imgPath = request.getParameter("ImgPath");
        // else
        // {
        // return;
        // }

        a.setTitle(title);
        a.setChannelID(channelID);
        a.setContent(content);
        // a.setImgPath(imgPath);
        if (request.getSession().getAttribute("userName") != null)
            a.setAuthor(request.getSession().getAttribute("userName")
                    .toString());

        String info = "fail";
        if (ad.updateArticle(a))
            info = "success";

        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }

    private void addNewArticle(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        Article a = new Article();

        String title = null;
        if (request.getParameter("Title") != null)
            title = request.getParameter("Title");
        else
        {
            return;
        }

        int channelID = 0;
        if (request.getParameter("ChannelID") != null
                && request.getParameter("ChannelID").matches(numberPatern))
            channelID = Integer.parseInt(request.getParameter("ChannelID"));
        else
        {
            return;
        }

        String content = null;
        if (request.getParameter("Content") != null)
            content = request.getParameter("Content");
        else
        {
            return;
        }

        // String imgPath = null;
        // if (request.getParameter("ImgPath") != null)
        // imgPath = request.getParameter("ImgPath");
        // else
        // {
        // return;
        // }

        a.setTitle(title);
        a.setChannelID(channelID);
        a.setContent(content);
        // a.setImgPath(imgPath);
        if (request.getSession().getAttribute("userName") != null)
            a.setAuthor(request.getSession().getAttribute("userName")
                    .toString());

        String info = "fail";
        if (ad.addArticle(a))
            info = "success";

        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }

    private void getAllChanneles(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        List<ChannelModel> list = ad.selectAllChanneles();

        if (list != null && list.size() > 0)
        {
            JSONArray json = new JSONArray();

            for (ChannelModel cm : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("ChannelID", cm.getChannelID());
                jo.put("ChannelName", cm.getChannelName());

                json.add(jo);
            }

            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();
            out.close();
        }

    }

    private void getPaginatedArticlesByChannel(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	int descripTionLimit = 400;
    	ArticleDao ad = new ArticleDao();
        List<Article> list = null;

        int pageIndex = 10;
        if (request.getParameter("PageIndex") != null)
            pageIndex = Integer.parseInt(request.getParameter("PageIndex"));
        else
        {
            return;
        }

        int pageCount = 10;
        if (request.getParameter("PageCount") != null)
            pageCount = Integer.parseInt(request.getParameter("PageCount"));
        else
        {
            return;
        }

        int channelID = 0;
        if (request.getParameter("ChannelID") != null)
        {
            channelID = Integer.parseInt(request.getParameter("ChannelID"));
        }
        else
        {
            return;
        }

        list = ad.selectPaginatedArticlesByChannel(channelID, pageIndex,
                pageCount);

        if (list != null && list.size() > 0)
        {
            JSONArray json = new JSONArray();

            for (Article a : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("ArticleID", a.getArticleID());
                jo.put("Channel", a.getChannel());
                jo.put("Title", a.getTitle());

                String content = a.getContent();
                String descripTion = null;

                if (content.length() > descripTionLimit)
                    descripTion = content.substring(0, descripTionLimit);
                else
                {
                    descripTion = content;
                }

                jo.put("DescripTion", descripTion);

                jo.put("Author", a.getAuthor());
                jo.put("ImgPath", a.getImgPath());
                // jo.put("Content", a.getContent());
                jo.put("ClickCount", a.getClickCount());
                jo.put("SubmitDate", a.getShortSubmitDateString());

                json.add(jo);
            }

            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();
            out.close();
        }

    }

    private void getAllArticles(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	int descripTionLimit = 400;
    	ArticleDao ad = new ArticleDao();
        Map<Integer, String> channeles = ad.getAllChannelMap();
        List<Article> list = null;
        JSONArray allArticles = new JSONArray();

        int count = 10;
        if (request.getParameter("count") != null)
            count = Integer.parseInt(request.getParameter("count"));

        if (channeles != null && channeles.size() > 0)
        {
            for (Integer channelID : channeles.keySet())
            {
                list = ad.selectArticleByChannelID(channelID, count);

                JSONObject channelArticle = new JSONObject();

                channelArticle.put("Channel", channelID);

                JSONArray articleList = new JSONArray();

                for (Article a : list)
                {
                    JSONObject jo = new JSONObject();

                    jo.put("ArticleID", a.getArticleID());
                    jo.put("Channel", a.getChannel());
                    jo.put("Title", a.getTitle());
                    jo.put("SubmitDate", a.getShortSubmitDateString());
                    jo.put("Author", a.getAuthor());
                    jo.put("ImgPath", a.getImgPath());

                    String content = a.getContent();
                    String descripTion = null;
                    if (content.length() > descripTionLimit)
                        descripTion = content.substring(0, descripTionLimit);
                    else
                    {
                        descripTion = content;
                    }

                    jo.put("DescripTion", descripTion);

                    jo.put("ClickCount", a.getClickCount());

                    articleList.add(jo);
                }

                channelArticle.put("ArticleList", articleList);

                allArticles.add(channelArticle);
            }

            PrintWriter out = response.getWriter();
            out.println(allArticles.toString());
            out.flush();
            out.close();

        }

    }

    private void getPopularArticles(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        List<Article> list = null;
        JSONArray json = new JSONArray();

        int count = 10;
        if (request.getParameter("count") != null)
            count = Integer.parseInt(request.getParameter("count"));

        list = ad.selectPopularArticles(count);

        if (list != null && list.size() > 0)
        {
            for (Article a : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("ArticleID", a.getArticleID());
                jo.put("Title", a.getTitle());

                json.add(jo);
            }

            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();
            out.close();
        }

    }

    private void getLatestArticles(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ArticleDao ad = new ArticleDao();
        List<Article> list = null;
        JSONArray json = new JSONArray();

        int count = 10;
        if (request.getParameter("count") != null)
            count = Integer.parseInt(request.getParameter("count"));

        list = ad.selectLatestArticles(count);

        if (list != null && list.size() > 0)
        {
            for (Article a : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("ArticleID", a.getArticleID());
                jo.put("Title", a.getTitle());

                json.add(jo);
            }

            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();
            out.close();
        }

    }

    private void getArticlesByChannel(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException,
            JRException, ClassNotFoundException
    {
    	int descripTionLimit = 400;
    	ArticleDao ad = new ArticleDao();
        List<Article> list = null;
        JSONArray json = new JSONArray();

        int count = -1;
        if (request.getParameter("count") != null)
            count = Integer.parseInt(request.getParameter("count"));

        int channelID = 0;
        if (request.getParameter("Channel") != null)
        {
            channelID = Integer.parseInt(request.getParameter("Channel"));
        }
        else
        {
            return;
        }

        if (channelID != 0)
        {
            list = ad.selectArticleByChannelID(channelID, count);

            if (list != null && list.size() > 0)
            {
                for (Article a : list)
                {
                    JSONObject jo = new JSONObject();

                    jo.put("ArticleID", a.getArticleID());
                    jo.put("Channel", a.getChannel());
                    jo.put("Title", a.getTitle());
                    jo.put("SubmitDate", a.getShortSubmitDateString());
                    jo.put("Author", a.getAuthor());
                    jo.put("ImgPath", a.getImgPath());

                    String content = a.getContent();
                    String descripTion = null;
                    if (content.length() > descripTionLimit)
                        descripTion = content.substring(0, descripTionLimit);
                    else
                    {
                        descripTion = content;
                    }

                    jo.put("DescripTion", descripTion);

                    jo.put("ClickCount", a.getClickCount());

                    json.add(jo);
                }

                PrintWriter out = response.getWriter();
                out.println(json.toString());
                out.flush();
                out.close();
            }
        }

    }

    private JSONArray buildJsonArray(List<Article> data)
    {
        JSONArray json = new JSONArray();

        for (Article a : data)
        {
            JSONObject jo = new JSONObject();

            jo.put("ArticleID", a.getArticleID());
            jo.put("Channel", a.getChannel());
            jo.put("Title", a.getTitle());
            jo.put("SubmitDate", a.getShortSubmitDateString());
            jo.put("Author", a.getAuthor());
            jo.put("ImgPath", a.getImgPath());
            jo.put("Content", a.getContent());
            jo.put("ClickCount", a.getClickCount());

            json.add(jo);
        }

        return json;
    }
}
