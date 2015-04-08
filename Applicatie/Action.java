package Applicatie;

import java.awt.geom.Point2D;

import Objects.DrawObject;

public class Action
{

	private Point2D position;
	private int starttime;
	private int duration;
	private DrawObject target;

	public Action(Point2D position, int starttime, int duration, DrawObject tar)
	{
		this.position = position;
		this.starttime = starttime;
		this.duration = duration;
		this.target = tar;
	}

	public Point2D getPosition()
	{
		return position;
	}
	
	public DrawObject getTargetObject()
	{
		return target;
	}

	public int getStartTime()
	{
		return starttime;
	}

	public int getStoptime()
	{
		return (starttime + duration);
	}

	public int getDuration()
	{
		return duration;
	}
}