package Listeners;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;

import Applicatie.Panel;

public class MouseMotion extends MouseMotionAdapter {

	private Panel panel;

	public MouseMotion(Panel panel)
	{
		this.panel = panel;
	}

	public void mouseDragged(MouseEvent e) {
		Point2D clickPoint = panel.getClickPoint(e.getPoint());
		if(panel.getDragObject() != null) {
			Point2D objectPoint = panel.getDragObject().getObjectPoint(panel.getDragObject().getPosition());
			switch(panel.getClickedOption()) {
				case "drag":
					if(panel.getDragObject() != null)
						panel.getDragObject().setPosition(new Point2D.Double(
							(panel.getDragObject().getPosition().getX() ) - ((panel.getLastClickPosition().getX() - clickPoint.getX()) ) / panel.getDragObject().getScale(), 
							(panel.getDragObject().getPosition().getY()) - (panel.getLastClickPosition().getY() - clickPoint.getY()) / panel.getDragObject().getScale()  ));
						System.out.println(panel.getDragObject().getPosition().getX());
						System.out.println(panel.getLastClickPosition().getX());
						System.out.println(clickPoint.getX());
						System.out.println("----------'");
					break;
				case "upperLeft":
					if(clickPoint.getX() < panel.getDragObject().getPosition().getX() && clickPoint.getY() < panel.getDragObject().getPosition().getY()) {
						resizeBigger();
					}
					else if (clickPoint.getX() > panel.getDragObject().getPosition().getX() && clickPoint.getY() > panel.getDragObject().getPosition().getY()) {
						resizeSmaller();
					}
					break;
				case "upperRight":
					if(clickPoint.getX() > panel.getDragObject().getRectangle().getX() + panel.getDragObject().getRectangle().getWidth() && clickPoint.getY() < panel.getDragObject().getRectangle().getY()) {
						resizeBigger();
					}
					else if (clickPoint.getX() < panel.getDragObject().getRectangle().getX() + panel.getDragObject().getRectangle().getWidth() && clickPoint.getY() > panel.getDragObject().getRectangle().getY()) {
						resizeSmaller();
					}
					break;
				case "bottomLeft": 
					if(clickPoint.getX() < panel.getDragObject().getRectangle().getX() && clickPoint.getY() > panel.getDragObject().getRectangle().getY() + panel.getDragObject().getRectangle().getHeight()) {
						resizeBigger();
					}
					else if (clickPoint.getX() > panel.getDragObject().getRectangle().getX() && clickPoint.getY() < panel.getDragObject().getRectangle().getY() + panel.getDragObject().getRectangle().getHeight()) {
						resizeSmaller();
					}
					break;
				case "bottomRight":
					if(clickPoint.getX() > panel.getDragObject().getRectangle().getX() + panel.getDragObject().getRectangle().getWidth() && clickPoint.getY() > panel.getDragObject().getRectangle().getY() + panel.getDragObject().getRectangle().getHeight()) {
						resizeBigger();
					}
					else if (clickPoint.getX() < panel.getDragObject().getRectangle().getX() + panel.getDragObject().getRectangle().getWidth() && clickPoint.getY() < panel.getDragObject().getRectangle().getY() + panel.getDragObject().getRectangle().getHeight()) {
						resizeSmaller();
					}
					break;
				case "rotate": 
					double tempval = panel.getDragObject().getRotation() + (panel.getLastClickPosition().getX() - clickPoint.getX());
					panel.getDragObject().setRotation(tempval);
					break;
			}
		}
		else {
			panel.setCameraPoint(new Point2D.Double(
				panel.getCameraPoint().getX() + (panel.getLastMousePosition().getX() - e.getX()),
				panel.getCameraPoint().getY() + (panel.getLastMousePosition().getY() - e.getY())
				));
		}		
		panel.repaint();
		panel.setLastMousePosition(e.getPoint());
		panel.setLastClickPosition(clickPoint);
		panel.getPP().update();
	}
	
	private void resizeBigger() {
		double scale = panel.getDragObject().getScale()*1 + 0.02;
		panel.getDragObject().setScale(scale);
		panel.getDragObject().setPosition(new Point2D.Double(panel.getDragObject().getPosition().getX()-2,panel.getDragObject().getPosition().getY()-2));
	}
	
	private void resizeSmaller() {
		if(panel.getDragObject().getScale() > 0.2) {
			double scale = panel.getDragObject().getScale()*1 - 0.02;
			panel.getDragObject().setScale(scale);
			panel.getDragObject().setPosition(new Point2D.Double(panel.getDragObject().getPosition().getX()+2,panel.getDragObject().getPosition().getY()+2));
		}
	}
}