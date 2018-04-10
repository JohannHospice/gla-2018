package auth;

import DAO.User;

import javax.annotation.security.PermitAll;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@PermitAll
@Path("/auth")
public class AuthResource {

    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@Context HttpServletRequest httpRequest) {
        User response = null;
        Principal principal = httpRequest.getUserPrincipal();

        if (principal != null) {
            response = new User();
            response.setUsername(principal.getName());
        }
        return response;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse login(@Context HttpServletRequest httpRequest, LoginRequest request) {
        LoginResponse response = new LoginResponse();

        if (httpRequest.getUserPrincipal() == null) {
            try {
                httpRequest.login(request.getUsername(), request.getPassword());
                httpRequest.getSession().getId(); // initialize session
                response.setSuccess(true);
            } catch (ServletException ex) {
                response.setSuccess(false);
            }
        } else {
            response.setSuccess(false);
        }
        return response;
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse signup(@Context HttpServletRequest httpRequest, LoginRequest request) {
        LoginResponse response = new LoginResponse();
        //TODO
        response.setSuccess(false);
        return response;
    }

    @POST
    @Path("/logout")
    public void logout(@Context HttpServletRequest httpRequest) {
        try {
            httpRequest.logout();
        } catch (ServletException ex) {
            Logger.getLogger(AuthResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}