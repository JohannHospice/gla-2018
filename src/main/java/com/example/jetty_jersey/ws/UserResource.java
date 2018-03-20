package com.example.jetty_jersey.ws;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DAO.DAO;
import DAO.Map;
import DAO.User;

@Path("/user")
public class UserResource{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public ArrayList<User> getListUser() {

		return DAO.getActionUser().getUsers();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{name}")
	public ArrayList<Map> getMaps(@PathParam("name") String name) {
		
		return DAO.getActionMap().getMapsByUsername(name);
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{name}/friends")
	public ArrayList<String> getFriends(@PathParam("name") String name) {
		
		return DAO.getActionUser().getFriends(name);
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{name}/info")
	public User getUser(@PathParam("name") String name) {
		
		return DAO.getActionUser().getInfoUser(name);
	}

}
