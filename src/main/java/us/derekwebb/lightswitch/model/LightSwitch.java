package us.derekwebb.lightswitch.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class LightSwitch
{
	private int id;
	private String name;
	private boolean active;
	private int pinNumber;
	
	public LightSwitch(String name, boolean active, int pinNumber)
	{
		this(-1, name, active, pinNumber);
	}
	
	public LightSwitch(int id, String name, boolean active, int pinNumber)
	{
		this.id = id;
		this.name = name;
		this.active = active;
		this.pinNumber = pinNumber;
	}
	
	public String getName() { return this.name; }
	public boolean isActive() { return this.active; }
	public int getPinNumber() { return this.pinNumber; }
	
	/**
	 * Updates this object in the database or creates a new one if one
	 * previously did not exist.
	 */
	public void commit()
	{
		if (this.id == -1)
		{
			insert();
		}
		else
		{
			update();
		}
		
		updateGPIO();
	}
	
	private void updateGPIO()
	{
		
		
		if (this.active)
		{
			us.derekwebb.lightswitch.LightSwitch.pinOutput.high();
		}
		else
		{
			us.derekwebb.lightswitch.LightSwitch.pinOutput.low();
		}
	}
	
	private void insert()
	{
		try
		{
			// SQL to run
			String sql = "insert into LightSwitch (name, active, pinNumber) values (?, ?, ?)";
			
			// Open a connection
			Database db = new Database();
			db.connect();
			
			// Prepare the statement
			PreparedStatement statement = db.prepareStatement(sql);
			statement.setString(1, this.name);
			statement.setInt(2, this.active?1:0);
			statement.setInt(3, this.pinNumber);
			
			// Execute the update
			statement.executeUpdate();
			
			// Get the last inserted rowid to set this object's id
			statement = db.prepareStatement("select last_insert_rowid()");
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			this.id = resultSet.getInt(1);
			
			// Close the database connection
			db.close();
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void update()
	{
		try
		{
			// SQL to run
			String sql =
				"update LightSwitch " +
				"set name = ?, active = ?, pinNumber = ? " +
				"where id = ?";
			
			// Open a connection
			Database db = new Database();
			db.connect();
			
			// Prepare the statement
			PreparedStatement statement = db.prepareStatement(sql);
			statement.setString(1, this.name);
			statement.setInt(2, this.active?1:0);
			statement.setInt(3, this.pinNumber);
			statement.setInt(4, this.id);
			
			// Execute the update
			statement.executeUpdate();
			
			// Close the database connection
			db.close();
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}