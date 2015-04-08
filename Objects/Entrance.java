package Objects;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import Applicatie.Panel;

public class Entrance extends DrawObject
{

	public Entrance(Point2D position)
	{
		super("entrance", position);
	}

	@Override
	public String getName()
	{
		return "Entrance";
	}
	
	public void setPosition(Point2D position, boolean b)
	{
		//this.position = position;
		Rectangle2D rect = getRectangle();
		double xl = (Panel.getFieldWidth()/2) + rect.getMinX();
		double xr = Panel.getFieldWidth() - (Panel.getFieldWidth()/2 + rect.getMaxX());
		double yt = (Panel.getFieldHeight()/2) + rect.getMinY();
		double yb = Panel.getFieldHeight() - (Panel.getFieldHeight()/2 + rect.getMaxY());
		if(xl < xr && xl < yt && xl < yb)
		{
			this.position = new Point2D.Double(-Panel.getFieldWidth()/2, position.getY());
		}
		else if(xr < xl && xr < yt && xr < yb)
		{
			this.position = new Point2D.Double(Panel.getFieldWidth()/2 - rect.getWidth(), position.getY());
		}
		else if(yt < xl && yt < xr && yt < yb)
		{
			this.position =  new Point2D.Double(position.getX(), -Panel.getFieldHeight()/2);
		}
		else if(yb < xl && yb < yt && yb < xr)
		{
			this.position = new Point2D.Double(position.getX(), Panel.getFieldHeight()/2 - rect.getHeight());
		}
		else
		{
			this.position = new Point2D.Double(-Panel.getFieldWidth()/2, -Panel.getFieldHeight()/2);
		}
	}

}
