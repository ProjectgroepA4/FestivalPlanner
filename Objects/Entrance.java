package Objects;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;


public class Entrance extends DrawObject {

	public Entrance(Point2D position) {
		super("entrance", position);
	}

	@Override
	public String getName() {
		return "Entrance";
	}
}