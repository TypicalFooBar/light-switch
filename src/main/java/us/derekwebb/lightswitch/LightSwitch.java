package us.derekwebb.lightswitch;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;

import us.derekwebb.lightswitch.servlet.LightSwitchServlet;

public class LightSwitch
{
	public static void main(String[] args) throws Exception
	{
		Server server = new Server(8080);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("./www");
		
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(LightSwitchServlet.class, "/api/switch");
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { 
			resourceHandler,
			servletHandler
		});
		
		server.setHandler(handlers);
		
		server.start();
		server.join();
	}
}