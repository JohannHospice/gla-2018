package gla2018.grp4;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class App 
{
    public static void main(String[] args)
    {
		Server server = new Server();

		ServerConnector connector = new ServerConnector(server);
		connector.setHost("127.0.0.1");
		connector.setPort(8080);
		connector.setIdleTimeout(10000);
		server.addConnector(connector);

        ResourceConfig config = new ResourceApplication();
		ServletHolder servletHolder = new ServletHolder(new ServletContainer(config));
		ServletContextHandler handlerWebServices = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handlerWebServices.addServlet(servletHolder, "/*");

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[]{handlerWebServices});
		server.setHandler(contexts);

        try
        {
            server.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
