package Objects;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;


public class Stage extends DrawObject {

	public Stage(Point2D position) {
		super("images/stage4.png", position);
	}

	@Override
	public String getName() {
		return "Stage";
	}


}
