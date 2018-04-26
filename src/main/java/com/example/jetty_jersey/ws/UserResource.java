package com.example.jetty_jersey.ws;

import DAO.DAO;
import DAO.Map;
import DAO.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;

@Path("/user")
public class UserResource extends Ressource {

    /**
     * @return
     * @throws IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public ArrayList<User> getListUser() throws IOException {

        return DAO.getActionUser().getUsers(DAO.client);
    }

    /**
     * @return
     * @throws IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search")
    public ArrayList<User> searchUser(@QueryParam("utokken") String tokken) throws IOException {

        return DAO.getActionUser().searchUser(DAO.client, tokken, 0, 10);
    }

    /**
     * @param username
     * @return
     * @throws IOException
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public ArrayList<Map> getMaps(@PathParam("username") String username) throws IOException {
        return DAO.getActionMap().getPublicMapsByUsername(DAO.client, username);
    }

    /**
     * @param username
     * @return
     * @throws IOException
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{username}/friends")
    public ArrayList<User> getFriends(@PathParam("username") String username) throws IOException {

        return DAO.getActionUser().getFriends(DAO.client, username);
    }

    /**
     * @param username
     * @return
     * @throws IOException
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{username}/info")
    public User getUserBySession(@PathParam("username") String username) throws IOException {

        return DAO.getActionUser().getOneUser(DAO.client, username);
    }

    /**
     * @param username
     * @return
     * @throws IOException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{username}/info")
    public User modifyUser(@PathParam("username") String username) throws IOException {
        User user = DAO.getActionUser().getOneUser(DAO.client, username);
        boolean success = DAO.getActionUser().updateUser(DAO.client, user);
        return (success) ? DAO.getActionUser().getOneUser(DAO.client, username) : user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/friends")
    public ArrayList<String> getFriends(@Context HttpServletRequest httpRequest, @FormParam("friendname") String friendName) throws IOException {
        User user = getUserBySession(httpRequest);
        return user.friends;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/friends/add")
    public boolean addFriend(@Context HttpServletRequest httpRequest, @FormParam("friendname") String friendName) throws IOException {
        User user = getUserBySession(httpRequest);
        User friend = DAO.getActionUser().getOneUser(DAO.client, friendName);
        if (friend != null) {
            user.friends.add(user.getUsername());
            return true;
        }
        return false;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/friends/remove")
    public boolean removeFriend(@Context HttpServletRequest httpRequest, @FormParam("friendname") final String friendName) throws IOException {
        User user = getUserBySession(httpRequest);
        if (user.friends.contains(friendName)) {
            user.friends.remove(friendName);
            DAO.getActionUser().updateUser(DAO.client, user);
            return true;
        }
        return false;
    }
}
