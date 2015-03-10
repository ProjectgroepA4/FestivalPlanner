package Listneners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;
import Objects.Podium;
import Objects.Toilet;

public class Mouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			Point2D clickPoint = getClickPoint(e.getPoint());
			lastClickPosition = clickPoint;
			lastMousePosition = e.getPoint();
			if(e.getX() < 200)
			{
				if(e.getY() < 300)
					dragObject = new Podium(clickPoint);
				else
					dragObject = new Toilet(clickPoint);
				objects.add(dragObject);
			}
			else
			{
				for(DrawObject o : objects)
					if(o.contains(clickPoint))
						dragObject = o;
			}
		}
	
	
		public void mouseReleased(MouseEvent e) {
			dragObject = null;
		}
}
