package Objects;

import java.awt.geom.Point2D;

public class Entrance extends DrawObject
{

	public Entrance(Point2D position)
	{
		super("images/entrance.png", position);
	}

	@Override
	public String getName()
	{
		return "Entrance";
	}

}
