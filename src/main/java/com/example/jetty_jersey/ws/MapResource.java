package com.example.jetty_jersey.ws;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import DAO.DAO;
import DAO.User;
import DAO.Map;

@Path("/map")
public class MapResource{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public ArrayList<Map> getMaps() {

		return DAO.getActionMap().getMaps();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/public")
	public ArrayList<Map> getPublicMaps() {

		return DAO.getActionMap().getPublicMaps();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{name}")
	public ArrayList<Map> getUsersMap(@PathParam("name") String name) {
		
		return DAO.getActionMap().getPublicMapsByUsername(name);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/public")
	public ArrayList<Map> searchMap(@QueryParam("tokken") String tokken) {
		
		return DAO.getActionMap().getMapsByName(tokken);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/new")
	public Map createMap(@FormParam("name") String name) {
		Map newMap = DAO.getActionMap().createMap(name);
		return newMap;
	}
	
	


}
