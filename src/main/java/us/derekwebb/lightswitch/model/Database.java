package us.derekwebb.lightswitch.model;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 

public class Database
{
	private String connectionString = "jdbc:sqlite:light-switch.db";
	
	private Connection connection = null;
	
	public Database()
	{
		// Check for the existance of the DB file.
		if (!(new File("light-switch.db").exists()))
		{
			try
			{
				// If it doesn't exist, then we need to create all of the DB tables
				createTables();
				
				// TODO: For testing
				insertDummyData();
			}
			catch (SQLException e)
			{
			}
		}
	}
	
	public void connect() throws SQLException
	{
		this.connection = DriverManager.getConnection(this.connectionString);
	}
	
	public PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return this.connection.prepareStatement(sql);
	}
	
	public ResultSet executeQuery(String sql) throws SQLException
	{
		// Prepare and execute the statement
		PreparedStatement statement = this.connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		return resultSet;
	}
	
	public void close() throws SQLException
	{
		this.connection.close();
	}
	
	private void createTables() throws SQLException
	{
		// Creates the Switch table
		String sql = 
			"create table LightSwitch ( " +
				"id integer primary key autoincrement, " +
				"name text not null, " +
				"active integer not null, " +
				"pinNumber integer not null " + 
			");";
		
		// Connect to the database
		this.connect();
		
		// Prepare and execute the statement
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.executeUpdate();
		
		// Close the connection to the database
		this.close();
	}
	
	private void insertDummyData() throws SQLException
	{
		LightSwitch gpio00LightSwitch = new LightSwitch("GPIO 0", false, 0);
		gpio00LightSwitch.commit();
		
		LightSwitch gpio02LightSwitch = new LightSwitch("GPIO 2", false, 2);
		gpio02LightSwitch.commit();
		
		LightSwitch gpio03LightSwitch = new LightSwitch("GPIO 3", false, 3);
		gpio03LightSwitch.commit();
		
		LightSwitch gpio04LightSwitch = new LightSwitch("GPIO 4", false, 4);
		gpio04LightSwitch.commit();
	}
}