package Objects;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;


public class Path extends DrawObject {

	public Path(Point2D position) {
		super("path", position);
	}

	public String getName() {
		return "Path";
	}

}