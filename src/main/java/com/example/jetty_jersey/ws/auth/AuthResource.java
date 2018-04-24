package com.example.jetty_jersey.ws.auth;

import DAO.DAO;
import DAO.User;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@PermitAll
@Path("/auth")
public class AuthResource {


    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@Context HttpServletRequest httpRequest) {
        return (User) httpRequest.getSession().getAttribute("user");
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleResponse login(@Context HttpServletRequest httpRequest,
                                @FormParam("username") String username,
                                @FormParam("password") String password) {

        System.out.println("in /login");
        try {
            User user = DAO.getActionUser().getOneUser(DAO.client, username);
            if (user.getPassword().equals(password)) {
                httpRequest.getSession().getId(); // initialize session
                httpRequest.getSession().setAttribute("user", user);
                return new SimpleResponse(true);
            }
            System.out.println("Mauvais mot de passe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SimpleResponse(false);
    }


    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public SimpleResponse signup(@Context HttpServletRequest httpRequest,
                                 @FormParam("username") String username,
                                 @FormParam("email") String email,
                                 @FormParam("password") String password,
                                 @FormParam("passwordConfirm") String passwordConfirm) {
        try {
            if (password.equals(passwordConfirm)) {
                User user = new User();
                user.setMail(email);
                user.setPassword(password);
                user.setUsername(username);
                DAO.getActionUser().insertUser(DAO.client, user);
                return new SimpleResponse(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SimpleResponse(false);
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleResponse logout(@Context HttpServletRequest httpRequest) {
        if (httpRequest.getSession().getAttribute("username") != null) {
            httpRequest.getSession().removeAttribute("user");
            return new SimpleResponse(true);
        }
        return new SimpleResponse(false);
    }

}