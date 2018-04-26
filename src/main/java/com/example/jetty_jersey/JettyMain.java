package com.example.jetty_jersey;

import org.eclipse.jetty.security.*;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import DAO.DAO;

public class JettyMain {

	public static void main(String[] args) throws Exception {
		// Initialize the server
		Server server = new Server();

		// Add a connector
		ServerConnector connector = new ServerConnector(server);
		connector.setHost("127.0.0.1");
		connector.setPort(8081);
		connector.setIdleTimeout(30000);
		server.addConnector(connector);
<<<<<<< HEAD
		DAO.ClientConnection("127.0.0.1",9200);
=======
		DAO.ClientConnection("127.0.0.1", 9200);
>>>>>>> eb4e12b9b9fba42bf6d5229a2052384a95cb2b75
		// Configure Jersey
		ResourceConfig rc = new ResourceConfig();
		rc.packages(true, "com.example.jetty_jersey.ws");
		rc.register(JacksonFeature.class);

		// Add a servlet handler for web services (/ws/*)
		ServletHolder servletHolder = new ServletHolder(new ServletContainer(rc));
		ServletContextHandler handlerWebServices = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handlerWebServices.setContextPath("/ws");
		handlerWebServices.addServlet(servletHolder, "/*");

		// Add a handler for resources (/*)
		ResourceHandler handlerPortal = new ResourceHandler();
		handlerPortal.setResourceBase("src/main/webapp/temporary-work");
		handlerPortal.setDirectoriesListed(false);
		handlerPortal.setWelcomeFiles(new String[] { "homepage.html" });
		ContextHandler handlerPortalCtx = new ContextHandler();
		handlerPortalCtx.setContextPath("/");
		handlerPortalCtx.setHandler(handlerPortal);

		// Activate handlers
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { handlerWebServices, handlerPortalCtx });
		server.setHandler(contexts);

		// Start server
		server.start();
		server.join();
	}

}
