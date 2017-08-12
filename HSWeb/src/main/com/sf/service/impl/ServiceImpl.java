package com.sf.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.sf.energy.map.dao.AmMeterDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Area;
import com.sf.service.interfaces.Service;
import com.sf.service.model.User;

@Component
public class ServiceImpl implements Service
{

	
	@Override
	public List<Area> getAreas()
	{
		AreaDao dao=new AreaDao();
		List<Area> list=new ArrayList<Area>();
		try
		{
			list = dao.display();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public String getAmmeterRead(int id)
	{
		AmMeterDao dao=new AmMeterDao();
		String value ="0";
		try
		{
			value=String.valueOf(dao.getAmMeterRead(id));
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	
	@Override
	public String sayHallo(String name) {
		
		return "[{\"hello\":\"hello \""+name+"\" !!\"}]";
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return new User("zgf", 26, "132@qq.com");
	}

	@Override
	public List<User> getUsers() {
		List<User> users=new ArrayList<User>();
		users.add(new User("zgf", 27, "123@qq.com"));
		users.add(new User("czy", 25, "234@qq.com"));
		users.add(new User("yr", 25, "345@qq.com"));
		return users;
	}

	@Override
	public String addUser(MultivaluedMap<String,String> formParams) {
		System.out.println(formParams);
		System.out.println(formParams.getFirst("user.name"));
		return "SUCCESS";
	}

	@Override
	public String sayWords(String words) {
		System.out.println(words);
		return words;
	}

	@Override
	public String addUser(User user)
	{
		System.out.println(user.getName());
		return null;
	}


}
