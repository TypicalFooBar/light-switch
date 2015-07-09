package us.derekwebb.lightswitch;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

import us.derekwebb.lightswitch.servlet.LightSwitchServlet;

public class LightSwitch
{
	public static GpioController gpio = GpioFactory.getInstance();
	public static GpioPinDigitalOutput pinOutput = gpio.provisionDigitalOutputPin(com.pi4j.io.gpio.RaspiPin.GPIO_11);
	
	public static void main(String[] args) throws Exception
	{
		Server server = new Server(8080);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("./www");
		
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(LightSwitchServlet.class, "/api/light-switch");
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { 
			resourceHandler,
			servletHandler
		});
		
		server.setHandler(handlers);
		
		server.start();
		
		// TODO: Used only for testing
		//java.awt.Desktop.getDesktop().browse(new java.net.URI("http://localhost:8080"));
		
		server.join();
		
		gpio.shutdown();
	}
}