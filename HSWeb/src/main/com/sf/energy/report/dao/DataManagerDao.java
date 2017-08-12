package com.sf.energy.report.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.report.model.EnReportDataModel;
import com.sf.energy.util.ConnDB;

public class DataManagerDao
{
	EnReportDataModel enReportDataModel = null;

	// 得到总记录
	private int totalCount = 0;

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	/**
	 * 查询数据表管理的数据
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<EnReportDataModel> queryDataManager(String DataManagersortName, String DataManagerorder, int pageCount, int pageIndex)
			throws SQLException
	{
		List<EnReportDataModel> list = new ArrayList<EnReportDataModel>();
		String sql = "select EnReportData_ID,EnReportData_Num,EnReportData_Name,EnReportData_Style,EnReportData_Enable,EnReportData_Text,nvl(EnReportData_Part,' ') EnReportData_Part,EnReportData_Remark from EnReportData order by "
				+ DataManagersortName + " " + DataManagerorder;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			// 得到总记录数
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

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
				enReportDataModel = new EnReportDataModel();
				enReportDataModel.setEnReportData_id(rs.getInt("EnReportData_ID"));
				enReportDataModel.setEnReportData_num(rs.getString("EnReportData_Num"));
				enReportDataModel.setEnReportData_name(rs.getString("EnReportData_Name"));
				enReportDataModel.setEnReportData_style(rs.getString("EnReportData_Style"));
				enReportDataModel.setEnReportData_enable(rs.getInt("EnReportData_Enable"));
				enReportDataModel.setEnReportData_part(rs.getString("EnReportData_Text"));
				enReportDataModel.setEnReportData_text(rs.getString("EnReportData_Part"));
				enReportDataModel.setEnReportData_remark(rs.getString("EnReportData_Remark"));
				list.add(enReportDataModel);
				count--;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;

	}

	/**
	 * 根据id删除数据表的数据
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteDataManager(int id) throws SQLException
	{
		String sql = "delete from EnReportData where EnReportData_ID=" + id;

		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 删除历史记录
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteEnReportDataHis(int id) throws SQLException
	{
		String sqlHis = "delete from EnReportDataHis where EnReportData_ID=" + id;
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlHis);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 增加EnReportData的数据
	 * 
	 * @param enReportDataModel
	 * @return
	 * @throws SQLException
	 */
	public boolean addDataManager(EnReportDataModel enReportDataModel) throws SQLException
	{
		String sql = "insert into EnReportData(EnReportData_id,EnReportData_num,EnReportData_name,EnReportData_style,EnReportData_enable,EnReportData_part,EnReportData_text,EnReportData_remark) values('"
				+ enReportDataModel.getEnReportData_id()
				+ "','"
				+ enReportDataModel.getEnReportData_num()
				+ "','"
				+ enReportDataModel.getEnReportData_name()
				+ "','"
				+ enReportDataModel.getEnReportData_style()
				+ "','"
				+ enReportDataModel.getEnReportData_enable()
				+ "','"
				+ enReportDataModel.getEnReportData_part()
				+ "','"
				+ enReportDataModel.getEnReportData_text() + "','" + enReportDataModel.getEnReportData_remark() + "')";

		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 获取DataManager的
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getDataManagerId() throws SQLException
	{
		int id = 0;
		String sql = "select max(EnReportData_ID) Id from EnReportData";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next())
			{
				id = rs.getInt("Id");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return id + 1;
	}

	public boolean editDataManager(EnReportDataModel enReportDataModel) throws SQLException
	{
		String sql = "update EnReportData set EnReportData_Num='" + enReportDataModel.getEnReportData_num() + "',EnReportData_Name='"
				+ enReportDataModel.getEnReportData_name() + "',EnReportData_Enable='" + enReportDataModel.getEnReportData_enable()
				+ "',EnReportData_Style='" + enReportDataModel.getEnReportData_style() + "',EnReportData_Text='"
				+ enReportDataModel.getEnReportData_text() + "',EnReportData_Part='" + enReportDataModel.getEnReportData_part()
				+ "',EnReportData_Remark='" + enReportDataModel.getEnReportData_remark() + "'where EnReportData_ID="
				+ enReportDataModel.getEnReportData_id();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 
	 */
	public String getSql(String id)
	{
		String sql = "select ";
		return null;
	}

	/**
	 * 执行存储过程
	 * 
	 * @param proName
	 *            存储过程名
	 * @param Parameters
	 *            存储过程参数
	 * @return
	 * @throws SQLException
	 */
	public JSONArray execPro(String proName, String Parameters) throws SQLException
	{
		JSONArray json = new JSONArray();
		ResultSetMetaData meta = null;

		String proString = "";
		proString = "{ call " + proName + "(";
		Connection conn =null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			int length = 0;
			if ((Parameters == null) || (" ".equals(Parameters)))
			{

				proString = proString + "?) }";
				cstmt = conn.prepareCall(proString);

			} else
			{
				String[] ParaArray = Parameters.split(",");
				length = ParaArray.length;

				for (int i = 0; i < length; i++)
				{
					proString = proString + "?,";
				}
				proString = proString + "?) }";

				cstmt = conn.prepareCall(proString);
				for (int i = 1; i < length + 1; i++)
				{
					cstmt.setString(i, ParaArray[i - 1]);
				}

			}

			cstmt.registerOutParameter(length + 1, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(length + 1);

			if (rs.next())
			{
				// rs.previous();
				meta = rs.getMetaData();
				// 取得列数
				int count = meta.getColumnCount();
				int i = 0;

				JSONObject info = new JSONObject();
				info.put("ColumnCount", meta.getColumnCount());
				json.add(info);

				JSONObject jo = new JSONObject();
				for (i = 1; i <= count; i++)
				{
					// 获取列名
					jo.put("" + i, meta.getColumnLabel(i));
				}
				json.add(jo);

				int countNum = 20;
				do
				{
					JSONObject job = new JSONObject();
					for (i = 1; i <= count; i++)
					{
						job.put("" + i, rs.getObject(i));
					}
					json.add(job);
					countNum--;
				} while (rs.next() && countNum > 0);

			}
		} catch (SQLException e)
		{
			// TODO: handle exception
			e.printStackTrace();
		} finally
		{
			if (conn != null)
			{
				try
				{
					// 将Connection连接对象还给数据库连接池
					conn.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (rs != null)
			{
				try
				{
					// 关闭存储查询结果的ResultSet对象
					rs.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				rs = null;
			}
			if(cstmt!=null){
	            try{
	                //关闭负责执行SQL命令的Statement对象
	            	cstmt.close();
	            }catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
		}

		return json;

	}

}
