package Agenda;
import java.io.Serializable;

import javax.swing.ImageIcon;


public class Artist implements Serializable{

	private static final long serialVersionUID = 1;
	
	private String name;
	private String genre;
	private ImageIcon image;
	private String description;
	private String background;
	
	public Artist(String name, String genre, String image, String description, String background)
	{
		this.name = name;
		this.genre = genre;
		this.description = description;
		this.background = background;
		
		if ( !image.equals("null")) {
			this.image = new ImageIcon(image);
		} 
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getGenre()
	{
		return this.genre;
	}
	
	public ImageIcon getImage()
	{
		return this.image;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getBackground()
	{
		return this.background;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setGenre(String genre)
	{
		this.genre = genre;
	}
	
	public void setImageIconString(String image)
	{
		this.image = new ImageIcon(image);
	}
	
	public void setImageIcon(ImageIcon image)
	{
		this.image = image;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public void setBackground(String background)
	{
		this.background = background;
	}
	
	public String toString()
	{
		return name;
	}
}
