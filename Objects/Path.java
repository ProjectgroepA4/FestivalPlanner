package Objects;

import java.awt.geom.Point2D;

public class Path extends DrawObject
{

	public Path(Point2D position)
	{
		super("images/path.jpg", position);
	}

	@Override
	public String getName()
	{
		return "Path";
	}

}
