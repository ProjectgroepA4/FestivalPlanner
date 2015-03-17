package Objects;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;


public class Food extends DrawObject {

	public Food(Point2D position) {
		super("images/food.png", position);
	}

	public String getName() {
		return "Food";
	}

}
