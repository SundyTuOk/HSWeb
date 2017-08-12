package com.sf.energy.project.display.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import com.sf.energy.project.display.model.Article;
import com.sf.energy.project.display.model.ChannelModel;
import com.sf.energy.util.ConnDB;

public class ArticleDao
{

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public boolean addArticle(Article article) throws SQLException, IOException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		//ResultSet rs = null;
		if (selectChannelByID(article.getChannelID()) == null)
			return false;

		boolean flag = false;
		// 将clob转成流形式
		Reader clobReader =null;
		try
		{
			String sql = "insert into article "
					+ "(Title,SubmitDate,Content,Author,ImgPath,ChannelID,ClickCount) values "
					+ "(?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),?,?,?,?,?)";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, article.getTitle());
			ps.setString(2, sdf.format(article.getSubmitDate()));
			clobReader = new StringReader(article.getContent());
			ps.setCharacterStream(3, clobReader, article.getContent().length());
			ps.setString(4, article.getAuthor());
			ps.setString(5, article.getImgPath());
			ps.setInt(6, article.getChannelID());
			ps.setInt(7, article.getClickCount());

			int count = ps.executeUpdate();
			if (count == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			try
			{
				ConnDB.release(conn, ps);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			if (clobReader != null) try
			{
					clobReader.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return flag;
	}

	public Article selectArticleByID(int articleID) throws SQLException,
			IOException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		Article result = null;

		try
		{
			conn = ConnDB.getConnection();
			String sql = "select ArticleID,Title,SubmitDate,Content,Author,ImgPath,ChannelID,ClickCount from article where ArticleID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, articleID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				result = buildInstance(rs);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return result;
	}

	public boolean addClickCount(int articleID) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean flag = false;
		try
		{
			String sql = "update Article set ClickCount = ClickCount+1 where ArticleID= ? ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, articleID);

			if (ps.executeUpdate() == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return flag;
	}

	public List<Article> selectArticleByChannelID(int channelID, int limitCount)
			throws SQLException, IOException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<Article> list = null;
		try
		{
			String sql = "select * from article where ChannelID = ? "
					+ "order by SubmitDate desc";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, channelID);
			int tempCount = limitCount;
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<Article>();
				list.add(buildInstance(rs));
				if (limitCount != -1)
				{
					if (tempCount > 0)
						tempCount--;
					else
					{
						break;
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public boolean deleteArticleByID(int articleID) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean flag = false;
		try
		{
			String sql = "delete from article where ArticleID = ?";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, articleID);
			if (ps.executeUpdate() == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return flag;
	}

	public boolean deleteArticleByChannel(int channelID)
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean flag = false;
		try
		{
			String sql = "delete from article where ChannelID = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, channelID);
			ps.executeUpdate();
			flag = true;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return flag;
	}

	public boolean updateArticle(Article article) throws SQLException,
			IOException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean flag = false;
		// 将clob转成流形式
		Reader clobReader =null;
		try
		{
			String sql = "update Article SET "
					+ "Title = ?,SubmitDate =to_date(?,'yyyy-mm-dd hh24:mi:ss'),Content = ?,"
					+ "Author = ?,ImgPath = ?,ChannelID = ?,ClickCount = ? "
					+ "where ArticleID = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, article.getTitle());
			ps.setString(2, sdf.format(article.getSubmitDate()));

			clobReader = new StringReader(article.getContent());
			ps.setCharacterStream(3, clobReader, article.getContent().length());

			ps.setString(4, article.getAuthor());
			ps.setString(5, article.getImgPath());
			ps.setInt(6, article.getChannelID());
			ps.setInt(7, article.getClickCount());
			ps.setInt(8, article.getArticleID());

			if (ps.executeUpdate() == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
			if (clobReader != null)
				try
				{
					clobReader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			
		}

		return flag;
	}

	public List<Article> selectLatestArticles(int limitCount)
			throws SQLException, IOException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<Article> list = null;
		try
		{
			String sql = "select * from article order by SubmitDate desc";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			int tempCount = limitCount;

			while (rs.next())
			{
				if (limitCount != -1)
				{
					if (tempCount > 0)
						tempCount--;
					else
					{
						break;
					}
				}

				if (list == null)
					list = new LinkedList<Article>();

				list.add(buildInstance(rs));

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<Article> selectPopularArticles(int limitCount)
			throws SQLException, IOException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<Article> list = null;
		try
		{
			String sql = "select * from article order by ClickCount desc";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			int tempCount = limitCount;

			while (rs.next())
			{
				if (limitCount != -1)
				{
					if (tempCount > 0)
						tempCount--;
					else
					{
						break;
					}
				}

				if (list == null)
					list = new LinkedList<Article>();

				list.add(buildInstance(rs));

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public List<ChannelModel> selectAllChanneles() throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<ChannelModel> list = null;
		try
		{
			String sql = "select channel_Name,channel_ID from Channel order by channel_ID desc";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<ChannelModel>();

				ChannelModel cm = new ChannelModel();

				cm.setChannelID(rs.getInt("channel_ID"));
				cm.setChannelName(rs.getString("channel_Name"));
				list.add(cm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<Article> selectPaginatedArticlesByChannel(int channelID,
			int pageIndex, int pageCount) throws SQLException, IOException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<Article> list = null;
		try
		{
			String sql = "select * from Article where channelID = ? order by SubmitDate desc ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, channelID);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			if (count <= (pageCount * pageIndex))
				return null;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				if (list == null)
					list = new LinkedList<Article>();

				list.add(buildInstance(rs));

				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public boolean insertChannel(String newChannel) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		if (selectChannelIDByName(newChannel) != -1)
			return false;
		boolean flag = false;

		try
		{
			String sql = "insert into Channel (Channel_Name) values (?)";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, newChannel);
			if (ps.executeUpdate() == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public String selectChannelByID(int channelID) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String channel = null;

		try
		{
			String sql = "select Channel_Name from Channel where Channel_ID = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, channelID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				channel = rs.getString("Channel_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return channel;
	}

	public int selectChannelIDByName(String channelName) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		int channelID = -1;

		try
		{
			String sql = "select Channel_ID from Channel where Channel_Name = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, channelName);
			rs = ps.executeQuery();
			if (rs.next())
			{
				channelID = rs.getInt("Channel_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return channelID;
	}

	public boolean deleteChannelByID(int channelID) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean flag = false;
		try
		{
			String sql = "delete from Channel where Channel_ID = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, channelID);

			if ((ps.executeUpdate() == 1) && deleteArticleByChannel(channelID))
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return flag;
	}

	public boolean updateChannel(int channelID, String newChannel)
			throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean flag = false;
		try
		{
			String sql = "update Channel set Channel_Name = ? where Channel_ID = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, newChannel);
			ps.setInt(2, channelID);

			if (ps.executeUpdate() == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return flag;
	}

	public Map<Integer, String> getAllChannelMap() throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		Map<Integer, String> map = null;
		try
		{
			String sql = "select Channel_ID,Channel_Name from Channel order by Channel_ID desc";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (map == null)
					map = new HashMap<Integer, String>();

				map.put(rs.getInt("Channel_ID"), rs.getString("Channel_Name"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	private Article buildInstance(ResultSet rs) throws SQLException,
			IOException
	{

		Article a = new Article();

		if (rs.getObject("ArticleID") != null)
			a.setArticleID(rs.getInt("ArticleID"));

		if (rs.getString("Title") != null)
			a.setTitle(rs.getString("Title"));

		if (rs.getTimestamp("SubmitDate") != null)
		{
			Timestamp submitDate = rs.getTimestamp("SubmitDate");
			a.setSubmitDate(new java.util.Date(submitDate.getTime()));
		}

		if (rs.getObject("Content") != null)
		{
			// 导入oracle.sql.CLOB包，或者直接引用这个包
			oracle.sql.CLOB clob = (CLOB) rs.getClob("Content");
			Reader rd = clob.getCharacterStream();// 得到流
			BufferedReader br = new BufferedReader(rd);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (s != null)
			{
				// 循环赋值
				sb.append(s);
				s = br.readLine();
			}
			a.setContent(sb.toString());

			if (br != null)
			{
				try
				{
					br.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			if (rd != null)
			{
				try
				{
					rd.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		if (rs.getString("Author") != null)
			a.setAuthor(rs.getString("Author"));

		if (rs.getString("ImgPath") != null)
			a.setImgPath(rs.getString("ImgPath"));

		if (rs.getObject("ChannelID") != null)
			a.setChannelID(rs.getInt("ChannelID"));

		if (rs.getString("ClickCount") != null)
			a.setClickCount(rs.getInt("ClickCount"));
		return a;
	}
}
