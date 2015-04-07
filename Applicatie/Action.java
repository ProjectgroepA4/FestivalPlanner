package Applicatie;

import java.awt.geom.Point2D;

public class Action
{

	private Point2D position;
	private int starttime;
	private int duration;

	public Action(Point2D position, int starttime, int duration)
	{
		this.position = position;
		this.starttime = starttime;
		this.duration = duration;
	}

	public Point2D getPosition()
	{
		return position;
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