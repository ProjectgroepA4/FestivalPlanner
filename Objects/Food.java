package Objects;
import java.awt.geom.Point2D;


public class Food extends DrawObject {

	public Food(Point2D position) {
		super("food", position);
	}

	public String getName() {
		return "Food";
	}

}
