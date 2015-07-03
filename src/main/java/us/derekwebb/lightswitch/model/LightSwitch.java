package us.derekwebb.lightswitch.model;

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