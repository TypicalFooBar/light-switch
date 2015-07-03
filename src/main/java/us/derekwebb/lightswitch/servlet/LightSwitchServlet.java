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

import us.derekwebb.lightswitch.model.LightSwitch;

@SuppressWarnings("serial")
public class LightSwitchServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Gson gson = new Gson();
		String jsonReturn = "";
		
		if (request.getParameter("action").equals("getLightSwitches"))
		{
			ArrayList<LightSwitch> lightSwitches = getLightSwitches();
			jsonReturn = gson.toJson(lightSwitches);
		}
		
		response.getWriter().println(jsonReturn);
	}
	
	private ArrayList<LightSwitch> getLightSwitches()
	{
		ArrayList<LightSwitch> lightSwitches = new ArrayList<LightSwitch>();
		lightSwitches.add(new LightSwitch("Switch 1", true));
		lightSwitches.add(new LightSwitch("Switch 2", false));
		
		return lightSwitches;
	}
}