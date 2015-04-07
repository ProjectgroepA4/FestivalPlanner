package Objects;

import java.awt.geom.Point2D;

public class Toilet extends DrawObject
{

	public Toilet(Point2D position)
	{
		super("wc", position);
	}

	@Override
	public String getName()
	{
		return "Toilet";
	}

}
