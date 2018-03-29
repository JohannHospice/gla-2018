package com.example.jetty_jersey.ws;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.elasticsearch.client.RestHighLevelClient;

import DAO.DAO;
import DAO.User;
import DAO.Map;

@Path("/map")
public class MapResource{

	private RestHighLevelClient client = DAO.ClientConnection("0.0.0.0", 8080);
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public ArrayList<Map> getMaps() throws IOException {

		return DAO.getActionMap().getMaps(client);
	}
	
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/public")
	public ArrayList<Map> getPublicMaps() throws IOException {

		return DAO.getActionMap().getPublicMaps(client);
	}
	
	/**
	 * 
	 * @param mapname
	 * @return
	 * @throws IOException
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{mapname}")
	public Map getUsersMap(@PathParam("mapname") String mapname) throws IOException {
		
		return DAO.getActionMap().getOneMap(client,mapname);
	}
	
	/**
	 * 
	 * @param mapname
	 * @return
	 * @throws IOException
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{mapname}")
	public Response deleteMap(@PathParam("mapname") String mapname) throws IOException {
		boolean success = DAO.getActionMap().deleteMap(client,mapname);
		return (success)?Response.status(Response.Status.ACCEPTED).build():Response.status(Response.Status.NOT_MODIFIED).build();
	}
	
	/**
	 * 
	 * @param mapname
	 * @return
	 * @throws IOException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{mapname}")
	public Map setUsersMap(@PathParam("mapname") String mapname) throws IOException {
		Map map = DAO.getActionMap().getOneMap(client, mapname);
		boolean success = DAO.getActionMap().updateMap(client, map);
		return (success)? DAO.getActionMap().getOneMap(client, mapname):map;
	}
	
	/**
	 * A MODIFIER NIVEAU DAO: Il faut une méthode cherchant toutes les map contenant le tokken ds le nom
	 * @param tokken
	 * @return
	 * @throws IOException 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/public")
	public ArrayList<Map> searchMap(@QueryParam("tokken") String tokken) throws IOException {
		
		return DAO.getActionMap().searchMap(client, tokken, 0, 10);
	}
	
	/**
	 * 
	 * @param mapname
	 * @return
	 * @throws IOException 
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/new")
	public Map createMap(@FormParam("mapname") String mapname) throws IOException {
		
		Map newMap = new Map(mapname);
		/*
		 * Utiliser les données de leaflet pr modifier et ajouter des éléments a la Map via une méthode
		 */
		
		boolean success = DAO.getActionMap().createMap(client,newMap);
		return newMap;
	}
	
	


}
