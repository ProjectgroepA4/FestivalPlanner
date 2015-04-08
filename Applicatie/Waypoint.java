package Applicatie;

import java.awt.geom.Point2D;

import Objects.DrawObject;

public class Waypoint extends DrawObject
{
	private int[] options;
	private int self;
	public Waypoint(Point2D position)
	{
		super("waypoint", position);
	}

	public Point2D getAccuratePoint()
	{
		return new Point2D.Double(super.getPosition().getX() + (super.getImageRectangle().getWidth()/2), super.getPosition().getY() + super.getImageRectangle().getHeight());
	}
	
	@Override
	public String getName()
	{
		return "Waypoint";
	}

	public int[] getOptions()
	{
		return options;
	}

	public void setOptions(int[] options)
	{
		this.options = options;
	}

	public int getSelf()
	{
		return self;
	}

	public void setSelf(int self)
	{
		this.self = self;
	}

}
