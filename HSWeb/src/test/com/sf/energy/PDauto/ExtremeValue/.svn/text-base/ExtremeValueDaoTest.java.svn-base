package com.sf.energy.PDauto.ExtremeValue;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.sf.energy.PDauto.dao.ExtremeValueDao;
import com.sf.energy.PDauto.model.ExtremeModel;

public class ExtremeValueDaoTest
{
	ExtremeValueDao evd = new ExtremeValueDao();

	@Test
	public void getExtremeValueBetween()
	{
		Date start = new Date(), end = new Date();
		start.setYear(1);

		end.setYear(200);
		try
		{
			List<ExtremeModel> list = evd
					.getExtremeValueBetween(13, start, end);

			System.out.println(list.size());
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void aaaaaaaa()
	{
		List<Integer> list = new LinkedList<Integer>();

		for (int i = 5; i < 18; i++)
		{
			list.add(i);
		}

		print(list);

		list.remove(2);

		print(list);

	}

	private void print(List<Integer> list)
	{
		for (Integer i : list)
		{
			System.out.print(i + " ");
		}

		System.out.print("\n");
	}
}
