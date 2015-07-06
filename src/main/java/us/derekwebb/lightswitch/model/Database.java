package us.derekwebb.lightswitch.model;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 

public class Database
{
	private String connectionString = "jdbc:sqlite:switch.db";
	
	private Connection connection = null;
	
	public Database()
	{
		boolean needToCreateDBTables = false;
		
		// Check for the existance of the DB file.
		if (!(new File("switch.db").exists()))
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
			"create table Switch ( " +
				"id integer primary key autoincrement, " +
				"name text not null, " +
				"active integer not null " +
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
		// Insert SQL
		String sql = "insert into Switch (name, active) values (?, ?)";
		
		// Connect to the database
		this.connect();
		
		// Prepare and execute the statement
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "DB Light 1");
		statement.setInt(2, 0);
		statement.executeUpdate();
		
		statement.setString(1, "DB Light 2");
		statement.setInt(2, 1);
		statement.executeUpdate();
		
		// Close the connection to the database
		this.close();
	}
}