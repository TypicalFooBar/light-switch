package us.derekwebb.lightswitch.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ServletHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("serial")
public class LightSwitchServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		LightSwitches lightSwitches = new LightSwitches();
		lightSwitches.add(new LightSwitch("Switch 1", true));
		lightSwitches.add(new LightSwitch("Switch 2", false));
		
		Gson gson = new Gson();
		String jsonReturn = gson.toJson(lightSwitches);
		
		response.getWriter().println(jsonReturn);
	}
	
	public class LightSwitches
	{
		private ArrayList<LightSwitch> lightSwitches = new ArrayList<LightSwitch>();
		
		public void add(LightSwitch lightSwitch)
		{
			this.lightSwitches.add(lightSwitch);
		}
		
		public ArrayList<LightSwitch> getSwitches() { return this.lightSwitches; }
	}
	
	public class LightSwitch
	{
		private String name;
		private boolean on;
		
		public LightSwitch(String name, boolean on)
		{
			this.name = name;
			this.on = on;
		}
		
		public String getName() { return this.name; }
		public boolean isOn() { return this.on; }
	}
}