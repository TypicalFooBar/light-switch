package us.derekwebb.lightswitch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

import us.derekwebb.lightswitch.handler.IndexServlet;

public class LightSwitch
{
	public class Person
	{
		private String firstName;
		private String lastName;
		private int age;
		
		public Person(String firstName, String lastName, int age)
		{
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
		}
		
		public String toString()
		{
			return "My name is " + this.firstName + " " + this.lastName + " and I am " + age + " years old.";
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		Server server = new Server(8080);
		
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(IndexServlet.class, "/");
		server.setHandler(servletHandler);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("./www");
		server.setHandler(resourceHandler);
		
		server.start();
		server.join();
		
		/*
		String jsonString = "{\"firstName\":\"Derek\",\"lastName\":\"Webb\",\"age\":27}";
		
		Gson gson = new GsonBuilder().create();
		
		Person person = gson.fromJson(jsonString, Person.class);
		System.out.println(person.toString());
		*/
	}
}