package com.example.jetty_jersey.ws;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.elasticsearch.client.RestHighLevelClient;

import DAO.DAO;
import DAO.Map;
import DAO.User;

@Path("/user")
public class UserResource{

	private RestHighLevelClient client = DAO.ClientConnection("0.0.0.0", 8080);

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public ArrayList<User> getListUser() throws IOException {

		return DAO.getActionUser().getUsers(client);
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public ArrayList<User> searchUser(@QueryParam("utokken") String tokken) throws IOException {

		return DAO.getActionUser().searchUser(client, tokken, 0, 10);
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 * @throws IOException
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{username}")
	public ArrayList<Map> getMaps(@PathParam("username") String username) throws IOException {
		return DAO.getActionMap().getPublicMapsByUsername(client, username);
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 * @throws IOException
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{username}/friends")
	public ArrayList<User> getFriends(@PathParam("username") String username) throws IOException {
		
		return DAO.getActionUser().getFriends(client,username);
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 * @throws IOException 
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{username}/info")
	public User getUser(@PathParam("username") String username) throws IOException {
		
		return DAO.getActionUser().getOneUser(client,username);
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 * @throws IOException 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{username}/info")
	public User modifyUser(@PathParam("username") String username) throws IOException {
		User user = DAO.getActionUser().getOneUser(client, username);
		boolean success = DAO.getActionUser().updateUser(client, user);
		return (success)?DAO.getActionUser().getOneUser(client, username):user;
	}

}
