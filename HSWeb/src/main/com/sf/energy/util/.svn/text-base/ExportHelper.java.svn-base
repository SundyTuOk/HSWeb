package com.sf.energy.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ExportHelper
{
	private volatile static ExportHelper instance = null;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static ExportHelper getInstance()
	{
		if (instance == null)
		{
			synchronized (ExportHelper.class)
			{
				if (instance == null)
				{
					instance = new ExportHelper();
				}
			}
		}
		return instance;
	}

	public File getImportFile(HttpServletRequest request, HttpServletResponse response, String saveFolderPath, String fieldName)
	{
		File imgFile = null;

		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件需要上传到的路径
		String path = request.getRealPath(saveFolderPath);

		String filename = null;
		boolean isOk = false;

		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		factory.setRepository(new File(path));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);

		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);

		try
		{
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);

			for (FileItem item : list)
			{
				// 获取表单的属性名字
				String name = item.getFieldName();

				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField())
				{
					// 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
					String value = item.getString();

					request.setAttribute(name, value);
				}
				// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
				else
				{
					/**
					 * 以下三步，主要获取 上传文件的名字
					 */
					if (!name.equalsIgnoreCase(fieldName))
						continue;

					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					// String filename = value.substring(start + 1);
					filename = UUID.randomUUID() + value.substring(value.lastIndexOf("."), value.length());
					request.setAttribute(name, filename);

					// 真正写到磁盘上
					// 它抛出的异常 用exception 捕捉

					// item.write( new File(path,filename) );//第三方提供的

					// 手动写的
					imgFile = new File(path, filename);
					OutputStream out = new FileOutputStream(imgFile);

					InputStream in = item.getInputStream();

					int length = 0;
					byte[] buf = new byte[1024];

					// in.read(buf) 每次读到的数据存放在 buf 数组中
					while ((length = in.read(buf)) != -1)
					{
						// 在 buf 数组中 取出数据 写到 （输出流）磁盘上
						out.write(buf, 0, length);

					}

					in.close();
					out.close();

					break;
				}
			}

		} catch (FileUploadException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block

			// e.printStackTrace();
		}

		return imgFile;
	}

	public File getExportFile(String sql) throws IOException, SQLException, RowsExceededException, WriteException
	{
		File f = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData meta = null;
			int columnCount = 0, rowCount = 0;
			List<String> typeList = null;

			while (rs.next())
			{
				rowCount++;
				if (meta == null)
				{
					String fileName = UUID.randomUUID() + ".xls";
					f = new File(fileName);
					wwb = Workbook.createWorkbook(f);
					ws = wwb.createSheet("FirstSheet", 0);

					meta = rs.getMetaData();
					columnCount = meta.getColumnCount();
					typeList = new ArrayList<String>(columnCount);
					for (int i = 1; i <= columnCount; i++)
					{
						jxl.write.Label label = new jxl.write.Label(i - 1, 0, meta.getColumnLabel(i));
						ws.addCell(label);
						typeList.add(meta.getColumnTypeName(i));
					}
				}

				for (int j = 1; j <= columnCount; j++)
				{
					jxl.write.Label label = null;
					String content = "";
					if (typeList.get(j - 1).equalsIgnoreCase("DATE"))
					{
						Timestamp ts = rs.getTimestamp(meta.getColumnLabel(j));
						if (ts != null)
						{
							Date theDate = new Date(ts.getTime());
							content = df.format(theDate);
						}
					} else
					{
						content = rs.getObject(meta.getColumnLabel(j)).toString();
					}
					label = new jxl.write.Label(j - 1, rowCount, content);
					ws.addCell(label);
				}

			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		/*
		 * PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		 * ResultSet rs = ps.executeQuery();
		 */

		wwb.write();
		wwb.close();

		return f;
	}

	public File getParginateExportFile(String sql, int pageCount, int pageIndex) throws IOException, SQLException, RowsExceededException,
			WriteException
	{
		File f = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

			ResultSetMetaData meta = null;
			int columnCount = 0, rowCount = 0;
			List<String> typeList = null;

			while (rs.next() && (count > 0))
			{
				rowCount++;
				count--;
				if (meta == null)
				{
					String fileName = UUID.randomUUID() + ".xls";
					f = new File(fileName);
					wwb = Workbook.createWorkbook(f);
					ws = wwb.createSheet("FirstSheet", 0);

					meta = rs.getMetaData();
					columnCount = meta.getColumnCount();
					typeList = new ArrayList<String>(columnCount);
					for (int i = 1; i <= columnCount; i++)
					{
						jxl.write.Label label = new jxl.write.Label(i - 1, 0, meta.getColumnLabel(i));
						ws.addCell(label);
						typeList.add(meta.getColumnTypeName(i));
					}
				}

				for (int j = 1; j <= columnCount; j++)
				{
					jxl.write.Label label = null;
					String content = "";
					if (typeList.get(j - 1).equalsIgnoreCase("DATE"))
					{
						Timestamp ts = rs.getTimestamp(meta.getColumnLabel(j));
						if (ts != null)
						{
							Date theDate = new Date(ts.getTime());
							content = df.format(theDate);
						}
					} else
					{
						if (rs.getObject(meta.getColumnLabel(j)) != null)
						{
							content = rs.getObject(j).toString();
						}

					}
					label = new jxl.write.Label(j - 1, rowCount, content);
					ws.addCell(label);
				}
			}

			wwb.write();
			wwb.close();
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return f;
	}

	public File getParginateExportFile(String[] titleList, String sql, int pageCount, int pageIndex) throws IOException, SQLException,
			RowsExceededException, WriteException
	{
		File f = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

			ResultSetMetaData meta = null;
			int columnCount = 0, rowCount = 0;
			List<String> typeList = null;

			while (rs.next() && (count > 0))
			{
				rowCount++;
				count--;
				if (meta == null)
				{
					String fileName = UUID.randomUUID() + ".xls";
					f = new File(fileName);
					wwb = Workbook.createWorkbook(f);
					ws = wwb.createSheet("FirstSheet", 0);

					meta = rs.getMetaData();
					columnCount = Math.min(titleList.length, meta.getColumnCount());
					typeList = new ArrayList<String>(columnCount);
					for (int i = 1; i <= columnCount; i++)
					{
						jxl.write.Label label = new jxl.write.Label(i - 1, 0, titleList[i - 1]);
						ws.addCell(label);
						typeList.add(meta.getColumnTypeName(i));
					}
				}

				for (int j = 1; j <= columnCount; j++)
				{
					jxl.write.Label label = null;
					String content = "";
					if (typeList.get(j - 1).equalsIgnoreCase("DATE"))
					{
						Timestamp ts = rs.getTimestamp(meta.getColumnLabel(j));
						if (ts != null)
						{
							Date theDate = new Date(ts.getTime());
							content = df.format(theDate);
						}
					} else
					{
						if (rs.getObject(j) != null)
						{
							content = rs.getObject(meta.getColumnLabel(j)).toString();
						}

					}
					label = new jxl.write.Label(j - 1, rowCount, content);
					ws.addCell(label);
				}
			}

			wwb.write();
			wwb.close();
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return f;
	}

	public File getExportFile(String[] titleList, String[] lableList, String sql) throws IOException, SQLException, RowsExceededException,
			WriteException
	{
		File f = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			ResultSetMetaData meta = null;
			int columnCount = 0, rowCount = 0;
			List<String> typeList = null;
			List<String> queryLableList = null;

			while (rs.next())
			{
				rowCount++;
				if (meta == null)
				{
					String fileName = UUID.randomUUID() + ".xls";
					f = new File(fileName);
					wwb = Workbook.createWorkbook(f);
					ws = wwb.createSheet("FirstSheet", 0);

					meta = rs.getMetaData();
					columnCount = meta.getColumnCount();
					queryLableList = new ArrayList<String>(columnCount);

					if (columnCount <= 0)
						return null;

					// 得到查询结果的所有列名
					for (int i = 1; i <= columnCount; i++)
					{
						queryLableList.add(meta.getColumnLabel(i));
					}

					typeList = new ArrayList<String>(lableList.length);
					// 如果要求得到的列名列表中包含不存在的列名，停止继续
					boolean exist = false;
					for (int i = 1; i <= lableList.length; i++)
					{
						exist = false;

						for (int j = 1; j <= queryLableList.size(); j++)
						{
							if (queryLableList.get(j - 1).toUpperCase().equalsIgnoreCase(lableList[i - 1]))
							{
								// 将指定列名填入表格
								jxl.write.Label label = new jxl.write.Label(i - 1, 0, titleList[i - 1]);
								ws.addCell(label);

								// 得到要查询的列的数据类型
								typeList.add(meta.getColumnTypeName(j));
								exist = true;
								break;
							}
						}

						if (!exist)
						{
							return null;
						}
					}
				}

				for (int j = 0; j < lableList.length; j++)
				{
					jxl.write.Label label = null;
					String content = "";
					if (typeList.get(j).equalsIgnoreCase("DATE"))
					{
						Timestamp ts = rs.getTimestamp(lableList[j]);
						if (ts != null)
						{
							Date theDate = new Date(ts.getTime());
							content = df.format(theDate);
						}
					} else
					{
						if (rs.getObject(lableList[j]) != null)
						{
							content = rs.getObject(lableList[j]).toString();
						}

					}
					label = new jxl.write.Label(j, rowCount, content);
					ws.addCell(label);
				}
			}

			wwb.write();
			wwb.close();
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		

		return f;
	}

	public File getExportFile(String[] titleList, List<List<String>> data) throws RowsExceededException, WriteException, IOException
	{
		File f = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;

		if (data != null && data.size() > 0)
		{
			String fileName = UUID.randomUUID() + ".xls";
			f = new File(fileName);
			wwb = Workbook.createWorkbook(f);
			ws = wwb.createSheet("FirstSheet", 0);
		} else
		{
			return null;
		}

		int rowCount = 1;

		// 将指定列名填入表格第一行
		for (int i = 0; i < titleList.length; i++)
		{
			jxl.write.Label label = new jxl.write.Label(i, 0, titleList[i]);
			ws.addCell(label);
		}

		for (int j = 0; j < data.size(); j++)
		{
			for (int i = 0; i < titleList.length; i++)
			{
				jxl.write.Label label = new jxl.write.Label(i, rowCount, data.get(j).get(i));
				ws.addCell(label);
			}
			rowCount++;
		}

		wwb.write();
		wwb.close();

		return f;
	}

	public File getExportFile(List<List<String>> data) throws RowsExceededException, WriteException, IOException
	{
		File f = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;

		if (data != null && data.size() > 0)
		{
			String fileName = UUID.randomUUID() + ".xls";
			f = new File(fileName);
			wwb = Workbook.createWorkbook(f);
			ws = wwb.createSheet("FirstSheet", 0);
		} else
		{
			return null;
		}
		for (int j = 0; j < data.size(); j++)
		{
			for (int i = 0; i < data.get(0).size(); i++)
			{
				jxl.write.Label label = new jxl.write.Label(i, j, data.get(j).get(i));
				ws.addCell(label);
			}
		}

		wwb.write();
		wwb.close();

		return f;
	}
}
