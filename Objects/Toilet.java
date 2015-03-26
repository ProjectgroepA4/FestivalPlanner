package Objects;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;


public class Toilet extends DrawObject {

	public Toilet(Point2D position) {
		super("wc", position);
	}

	@Override
	public String getName() {
		return "Toilet";
	}


}