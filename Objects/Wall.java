package Objects;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;


public class Wall extends DrawObject {

	public Wall(Point2D position) {
		super("wall", position);
	}

	@Override
	public String getName() {
		return "Wall";
	}

}