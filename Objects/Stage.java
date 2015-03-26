package Objects;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;


public class Stage extends DrawObject {

	public Stage(Point2D position) {
		super("stage1", position);
	}

	@Override
	public String getName() {
		return "Stage";
	}


}