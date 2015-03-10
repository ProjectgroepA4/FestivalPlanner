package Listneners;
import Applicatie.Panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Applicatie.Panel;

public class MouseMotion extends MouseMotionAdapter {

	private Panel panel;

	public MouseMotion(Panel panel)
	{
		this.panel = panel;
	}

	public void mouseDragged(MouseEvent e) {
		Point2D clickPoint = panel.getClickPoint(e.getPoint());
		if(panel.getDragObject() != null)
		{
			if(SwingUtilities.isLeftMouseButton(e)){


				panel.getDragObject().setPosition(new Point2D.Double(
						panel.getDragObject().getPosition().getX() - (panel.getLastClickPosition().getX() - clickPoint.getX()), 
						panel.getDragObject().getPosition().getY() - (panel.getLastClickPosition().getY() - clickPoint.getY())));
			}
			else
			{

				double tempval = panel.getDragObject().getRotation() + (panel.getLastClickPosition().getX() - clickPoint.getX());
				panel.getDragObject().setRotation(tempval);
			}
			panel.repaint();
		}
		else
		{
			panel.setCameraPoint(new Point2D.Double(
					panel.getCameraPoint().getX() + (panel.getLastMousePosition().getX() - e.getX()),
					panel.getCameraPoint().getY() + (panel.getLastMousePosition().getY() - e.getY())
					));
			panel.repaint();
		}
		panel.setLastMousePosition(e.getPoint());
		panel.setLastClickPosition(clickPoint);
	}
}
