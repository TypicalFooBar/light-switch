package us.derekwebb.lightswitch.servlet;

import java.io.IOException;

import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import us.derekwebb.lightswitch.model.LightSwitch;
import us.derekwebb.lightswitch.model.Database;

@SuppressWarnings("serial")
public class LightSwitchServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Gson gson = new Gson();
		String jsonReturn = "";
		
		if (request.getParameter("action").equals("getLightSwitchList"))
		{
			ArrayList<LightSwitch> lightSwitchList = getLightSwitchList();
			jsonReturn = gson.toJson(lightSwitchList);
		}
		else if (request.getParameter("action").equals("updateLightSwitch"))
		{
			LightSwitch lightSwitch = gson.fromJson(request.getParameter("lightSwitch"), LightSwitch.class);
			lightSwitch.commit();
		}
        else if (request.getParameter("action").equals("turnOn"))
        {
            turnOn(request.getParameter("idList"));
        }
		
        // Add headers
        response.addHeader("Access-Control-Allow-Origin", "*");
        
        // Send the response
		response.getWriter().println(jsonReturn);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Gson gson = new Gson();
		String jsonReturn = "";
		
		if (request.getParameter("action").equals("updateLightSwitch"))
		{
			ArrayList<LightSwitch> lightSwitchList = gson.fromJson(request.getParameter("lightSwitchList"), new TypeToken<ArrayList<LightSwitch>>(){}.getType());
			
			for (LightSwitch lightswitch : lightSwitchList)
			{
				lightswitch.commit();
			}
		}
		
		// Add headers
        response.addHeader("Access-Control-Allow-Origin", "*");
        
        // Send the response
		response.getWriter().println(jsonReturn);
	}
    
    private void turnOn(String idList)
    {
        String[] idArray = idList.split(",");
        
        for (int i = 0; i < idArray.length; ++i)
        {
            LightSwitch lightSwitch = new LightSwitch(Integer.parseInt(idArray[i]));
            lightSwitch.setActive(true);
            lightSwitch.commit();
        }
    }
	
	private ArrayList<LightSwitch> getLightSwitchList()
	{
		ArrayList<LightSwitch> lightSwitchList = new ArrayList<LightSwitch>();
		
		try
		{
			// Create the Database object
			Database db = new Database();
			
			// SQL to run
			String sql = "select id, name, active, pinNumber from LightSwitch";
			
			// Open a connection
			db.connect();
			
			// Run the query
			ResultSet resultSet = db.executeQuery(sql);
			
			// Loop through the ResultSet
			while (resultSet.next())
			{
				// Get the values
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				boolean on = (resultSet.getInt("active") != 0);
				int pinNumber = resultSet.getInt("pinNumber");
				
				// Create a new LightSwitch object and add it to the list
				lightSwitchList.add(new LightSwitch(id, name, on, pinNumber));
			}
			
			// Close the database connection
			db.close();
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return lightSwitchList;
	}
}