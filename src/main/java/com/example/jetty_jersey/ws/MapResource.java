package com.example.jetty_jersey.ws;

import DAO.DAO;
import DAO.Map;
import DAO.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

@Path("/map")
public class MapResource extends Ressource {

    /**
     * @return
     * @throws IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public ArrayList<Map> getMaps() throws IOException {

        return DAO.getActionMap().getMaps(DAO.client);
    }


    /**
     * @return
     * @throws IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/public")
    public ArrayList<Map> getPublicMaps() throws IOException {

        ArrayList<Map> list = DAO.getActionMap().getPublicMaps(DAO.client);
		if (list.size() == 0)
			return null;
		return list;
    }

    /**
     * @param mapname
     * @return
     * @throws IOException
     * @throws AuthException 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/by-name/{mapname}")
    public Map getOneMap(@Context HttpServletRequest httpRequest,@PathParam("mapname") String mapname) throws IOException, AuthException {
    	User user = getUserBySession(httpRequest);
        Map map = DAO.getActionMap().getOneMap(DAO.client, mapname);
        if(map.getIsPublic() || map.getUsername().equals(user.getUsername()))
        	return map;
        else
        	return null;
    }

    /**
     * @param mapname
     * @return
     * @throws Exception
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/by-name/{mapname}")
    public Response deleteMap(@Context HttpServletRequest httpRequest,@PathParam("mapname") String mapname) throws Exception {
        User user = getUserBySession(httpRequest);
        Map map = DAO.getActionMap().getOneMap(DAO.client, mapname);
        boolean success;
        
        if(map.getUsername().equals(user.getUsername()))
        	success = DAO.getActionMap().deleteMap(DAO.client, mapname);
        return (success) ? Response.status(Response.Status.ACCEPTED).build() : Response.status(Response.Status.NOT_MODIFIED).build();
    }

    /**
     * @param mapname
     * @return
     * @throws IOException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/by-name/{mapname}")
    public Map modifMap(@Context HttpServletRequest httpRequest,@PathParam("mapname") String mapname) throws IOException {
        User user = getUserBySession(httpRequest);
        Map map = DAO.getActionMap().getOneMap(DAO.client, mapname);
        boolean success;
        if(map.getUsername().equals(user.getUsername()))
        	success = DAO.getActionMap().updateMap(DAO.client, map);
        return (success) ? DAO.getActionMap().getOneMap(DAO.client, mapname) : map;
    }

    /**
     *
     * @param tokken
     * @return
     * @throws IOException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/public")
    public ArrayList<Map> searchMap(@QueryParam("tokken") String tokken) throws IOException {

        return DAO.getActionMap().searchMap(DAO.client, tokken, 0, 10,true,false);
    }

    /**
     * @param mapname
     * @return
     * @throws IOException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Map createMap(@Context HttpServletRequest httpRequest, @FormParam("mapname") String mapname) throws IOException {
        try {
            User user = getUserBySession(httpRequest);
            Map map = new Map(user.getUsername(),mapname);
            user.addMap(map.getName());

            DAO.getActionMap().insertMap(DAO.client, map);
            DAO.getActionUser().updateUser(DAO.client, user);
            return map;
        } catch (AuthException e) {
            e.printStackTrace();
        }
        return null;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/share")
    public boolean shareMap(@Context HttpServletRequest httpRequest, @FormParam("mapname") String mapname, @FormParam("username") String username) throws IOException {
        try {
            User user = getUserBySession(httpRequest);

            Map map = DAO.getActionMap().getOneMap(DAO.client, mapname);
            if (user.getMaps().contains(map.getName())) {
                User otherUser = DAO.getActionUser().getOneUser(DAO.client, username);
                map.privateUsers.add(otherUser.getUsername());
                return true;
            }
        } catch (AuthException e) {
            e.printStackTrace();
        }
        return false;
    }


}
