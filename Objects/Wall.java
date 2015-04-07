package Objects;

import java.awt.geom.Point2D;

public class Wall extends DrawObject
{

	public Wall(Point2D position)
	{
		super("images/wall.png", position);
	}

	@Override
	public String getName()
	{
		return "Wall";
	}

}
