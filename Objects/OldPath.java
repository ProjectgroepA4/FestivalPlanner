package Objects;

import java.awt.geom.Point2D;

public class OldPath extends DrawObject
{

	public OldPath(Point2D position)
	{
		super("path", position);
	}

	@Override
	public String getName()
	{
		return "Path";
	}

}
