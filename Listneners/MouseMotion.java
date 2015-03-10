package Listneners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;

public class MouseMotion extends MouseMotionAdapter {
	public void mouseDragged(MouseEvent e) {
		Point2D clickPoint = getClickPoint(e.getPoint());
		if(dragObject != null)
		{
			if(SwingUtilities.isLeftMouseButton(e))
				dragObject.position = new Point2D.Double(
						dragObject.position.getX() - (lastClickPosition.getX() - clickPoint.getX()), 
						dragObject.position.getY() - (lastClickPosition.getY() - clickPoint.getY()));
			else
				dragObject.rotation += (lastClickPosition.getX() - clickPoint.getX());
			repaint();
		}
		else
		{
			cameraPoint = new Point2D.Double(
					cameraPoint.getX() + (lastMousePosition.getX() - e.getX()),
					cameraPoint.getY() + (lastMousePosition.getY() - e.getY())
					);
			repaint();
		}
		lastMousePosition = e.getPoint();
		lastClickPosition = clickPoint;
	}
}
