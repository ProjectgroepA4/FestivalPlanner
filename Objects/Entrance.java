package Objects;

import java.awt.geom.Point2D;

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

}
