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
    @Path("/by-id/{mapId}")
    public Map getOneMap(@Context HttpServletRequest httpRequest,@PathParam("mapId") String mapId) throws IOException, AuthException {
    	User user = getUserBySession(httpRequest);
        Map map = DAO.getActionMap().getOneMap(DAO.client, mapId);
        if(map==null)
        	return null;
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
    @Path("/by-id/{mapId}")
    public Response deleteMap(@Context HttpServletRequest httpRequest,@PathParam("mapId") String mapId) throws Exception {
        User user = getUserBySession(httpRequest);
        Map map = DAO.getActionMap().getOneMap(DAO.client, mapId);
        boolean success = false;
        if(map==null)
        	return Response.status(Response.Status.NOT_MODIFIED).build();
        if(map.getUsername().equals(user.getUsername())) {
        	success = DAO.getActionMap().deleteMap(DAO.client, mapId);
        	if(success) {
        		for(String privU: map.getPrivateUsers()) {
        			User friend = DAO.getActionUser().getOneUser(DAO.client,privU);
        			if(friend != null) {
        				friend.getMaps().remove(map.getId());
                		DAO.getActionUser().updateUser(DAO.client,friend);
        			}
        		}
        		user.getMaps().remove(map.getId());
        		DAO.getActionUser().updateUser(DAO.client,user);
        	}
        }
        return (success) ? Response.status(Response.Status.ACCEPTED).build() : Response.status(Response.Status.NOT_MODIFIED).build();
    }

    /**
     * @param mapname
     * @return
     * @throws IOException
     * @throws AuthException 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/by-id/{mapId}")
    public Map modifMap(@Context HttpServletRequest httpRequest,@PathParam("mapId") String mapId) throws IOException, AuthException {
        User user = getUserBySession(httpRequest);
        Map map = DAO.getActionMap().getOneMap(DAO.client, mapId);
        boolean success = false;
        if(map.getUsername().equals(user.getUsername()))
        	success = DAO.getActionMap().updateMap(DAO.client, map);
        return (success) ? DAO.getActionMap().getOneMap(DAO.client, mapId) : map;
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
    public ArrayList<Map> searchMap(@FormParam("token") String token) throws IOException {

        return DAO.getActionMap().searchMap(DAO.client, token, 0, 10,true,false);
    }

    /**
     * @param mapname
     * @return
     * @throws IOException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Map createMap(@Context HttpServletRequest httpRequest,
    					@FormParam("mapName") String mapName,
    					@FormParam("public") String isPublic) throws IOException {
        try {
            User user = getUserBySession(httpRequest);
            Map map = new Map(user.getUsername(),mapName);
            if(isPublic==null)
            	map.changePublic();
            user.addMap(map.getId());

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
    public boolean shareMap(@Context HttpServletRequest httpRequest, @FormParam("mapId") String mapId, @FormParam("friendname") String friendname) throws IOException {
        try {
            User user = getUserBySession(httpRequest);
            if(!user.getFriends().contains(friendname))
            	return false;
            Map map = DAO.getActionMap().getOneMap(DAO.client, mapId);
            if(map==null)
            	return false;
            if (user.getMaps().contains(map.getId())) {
                User otherUser = DAO.getActionUser().getOneUser(DAO.client, friendname);
                map.privateUsers.add(otherUser.getUsername());
                otherUser.addMap(mapId);
                DAO.getActionUser().updateUser(DAO.client, otherUser);
                DAO.getActionMap().updateMap(DAO.client, map);
                return true;
            }
        } catch (AuthException e) {
            e.printStackTrace();
        }
        return false;
    }


}
