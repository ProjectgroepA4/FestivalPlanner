package Objects;

import java.awt.geom.Point2D;

import Applicatie.DrawObject;

public class OldPath extends DrawObject
{

	public OldPath(Point2D position)
	{
		super("images/path.jpg", position);
	}

	@Override
	public String getName()
	{
		return "Path";
	}

}
