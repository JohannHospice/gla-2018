package com.example.jetty_jersey.ws;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DAO.DAO;
import DAO.User;

@Path("/user")
public class UserResource{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users")
	public ArrayList<User> getListUser() {

		return DAO.getActionUser().getUsers();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/friends")
	public ArrayList<User> getFriends(User user) {
		
		return new ArrayList<User>();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/info")
	public String getInfoUser(User user) {
		
		return new String();
	}

}
