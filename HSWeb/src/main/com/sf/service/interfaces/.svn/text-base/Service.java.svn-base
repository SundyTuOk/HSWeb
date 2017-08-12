package com.sf.service.interfaces;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.sf.energy.project.system.model.Area;
import com.sf.service.model.User;

@Path(value = "/test")
public interface Service
{
	@GET
	@Path("/getAreas")
	@Produces({ MediaType.APPLICATION_JSON})
	public List<Area> getAreas();
	
	@GET
	@Path("/getAmmeterRead/{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public String getAmmeterRead(@PathParam("id") int id);
	
	@GET
	@Path("/sayhello/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public String sayHallo(@PathParam("name") String name);
	
	@GET
	@Path("/getUser")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser();
	
	@GET
	@Path("/getUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers();
	
	@POST
	@Path("/addUser1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public String addUser(MultivaluedMap<String, String> formParams);
	
	
	@POST
	@Path("/sayWords")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public String sayWords(String words);
	
	@POST
	@Path("/addUser")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public String addUser( User user);
}
