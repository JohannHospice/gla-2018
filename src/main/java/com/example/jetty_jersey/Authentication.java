package com.example.jetty_jersey;

import DAO.DAO;
import DAO.User;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.UserStore;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Password;
import org.elasticsearch.client.RestHighLevelClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class Authentication extends HttpServlet {

	private RestHighLevelClient client = DAO.ClientConnection("0.0.0.0", 8080);
    @GET
    @Path("/mylogin")//({username}{password})
    public Response login(@PathParam("username") String username, @PathParam("password") String password) throws IOException {
        User user = DAO.getActionUser().getOneUser(client, username);
        if (user != null) {
            // todo: add user to session
        	if(user.getPassword()==password)
        		return Response.status(200).entity("user found").build();
        	else
        		return Response.status(Response.Status.NOT_FOUND).entity("Wrong password for username: " + username).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Resource not found for username: " + username).build();

    }


    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS | ServletContextHandler.SECURITY);

        context.addServlet(new ServletHolder(new DefaultServlet() {
            @Override
            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                response.getWriter().append("hello " + request.getUserPrincipal().getName());
            }
        }), "/*");

        context.addServlet(new ServletHolder(new DefaultServlet() {
            @Override
            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                response.getWriter().append("<html><form method='POST' action='/j_security_check'>"
                        + "<input type='text' name='j_username'/>"
                        + "<input type='password' name='j_password'/>"
                        + "<input type='submit' value='Login'/></form></html>");
            }
        }), "/login");

        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__BASIC_AUTH);//__FORM_AUTH
        constraint.setRoles(new String[]{"user", "admin"});
        constraint.setAuthenticate(true);

        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setConstraint(constraint);
        constraintMapping.setPathSpec("/*");

        UserStore userStore = new UserStore();
        userStore.addUser("username", new Password("password"), new String[]{"user"});

        HashLoginService loginService = new HashLoginService();
        loginService.setUserStore(userStore);

        FormAuthenticator authenticator = new FormAuthenticator("/login", "/login", false);

        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.addConstraintMapping(constraintMapping);
        securityHandler.setLoginService(loginService);
        securityHandler.setAuthenticator(authenticator);

        context.setSecurityHandler(securityHandler);

        server.start();
        server.join();
    }
}
