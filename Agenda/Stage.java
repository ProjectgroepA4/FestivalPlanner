package Agenda;


import java.io.Serializable;

public class Stage implements Serializable {

	private static final long serialVersionUID = 1;
	
	private String name;
	private String description;
	
	public Stage(String name, String description)
	{
		this.name = name;
		this.description = description;
	}
	
	public String getName()
	{
		return this.name;
	}
		
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		return name;
	}
}
