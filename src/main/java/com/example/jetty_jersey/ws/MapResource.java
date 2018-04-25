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

        return DAO.getActionMap().getPublicMaps(DAO.client);
    }

    /**
     * @param mapname
     * @return
     * @throws IOException
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{mapname}")
    public Map getUsersMap(@PathParam("mapname") String mapname) throws IOException {

        return DAO.getActionMap().getOneMap(DAO.client, mapname);
    }

    /**
     * @param mapname
     * @return
     * @throws IOException
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{mapname}")
    public Response deleteMap(@PathParam("mapname") String mapname) throws IOException {
        boolean success = DAO.getActionMap().deleteMap(DAO.client, mapname);
        return (success) ? Response.status(Response.Status.ACCEPTED).build() : Response.status(Response.Status.NOT_MODIFIED).build();
    }

    /**
     * @param mapname
     * @return
     * @throws IOException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{mapname}")
    public Map setUsersMap(@PathParam("mapname") String mapname) throws IOException {
        Map map = DAO.getActionMap().getOneMap(DAO.client, mapname);
        boolean success = DAO.getActionMap().updateMap(DAO.client, map);
        return (success) ? DAO.getActionMap().getOneMap(DAO.client, mapname) : map;
    }

    /**
     * A MODIFIER NIVEAU DAO: Il faut une méthode cherchant toutes les map contenant le tokken ds le nom
     *
     * @param tokken
     * @return
     * @throws IOException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/public")
    public ArrayList<Map> searchMap(@QueryParam("tokken") String tokken) throws IOException {

        return DAO.getActionMap().searchMap(DAO.client, tokken, 0, 10);
    }

    /**
     * @param mapname
     * @return
     * @throws IOException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Map createMap(@Context HttpServletRequest httpRequest, @FormParam("mapname") String mapname) throws IOException {
        try {
            User user = getUserBySession(httpRequest);
            user.addMap(mapname);
            Map map = new Map(mapname);

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
