package Listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;

import Applicatie.Panel;
import Objects.DrawObject;

public class MouseMotion extends MouseMotionAdapter
{

	private Panel panel;

	public MouseMotion(Panel panel)
	{
		this.panel = panel;
	}

	public void mouseDragged(MouseEvent e)
	{
		Point2D clickPoint = panel.getClickPoint(e.getPoint());
		if (panel.getDragObject() != null)
		{
			switch (panel.getClickedOption())
			{
				case "drag":
					if (panel.getDragObject() != null)
						panel.getDragObject().setPosition(new Point2D.Double((panel.getDragObject().getPosition().getX()) - ((panel.getLastClickPosition().getX() - clickPoint.getX())) / panel.getDragObject().getScale(), (panel.getDragObject().getPosition().getY()) - (panel.getLastClickPosition().getY() - clickPoint.getY()) / panel.getDragObject().getScale()));
					panel.checkCollision();
					break;
				case "upperLeft":
					if (clickPoint.getX() < panel.getDragObject().getPosition().getX() && clickPoint.getY() < panel.getDragObject().getPosition().getY())
					{
						resizeBigger();
					}
					else if (clickPoint.getX() > panel.getDragObject().getPosition().getX() && clickPoint.getY() > panel.getDragObject().getPosition().getY())
					{
						resizeSmaller(clickPoint);
					}
					break;
				case "upperRight":
					if (clickPoint.getX() > panel.getDragObject().getRectangle().getX() + panel.getDragObject().getRectangle().getWidth() && clickPoint.getY() < panel.getDragObject().getRectangle().getY())
					{
						resizeBigger();
					}
					else if (clickPoint.getX() < panel.getDragObject().getRectangle().getX() + panel.getDragObject().getRectangle().getWidth() && clickPoint.getY() > panel.getDragObject().getRectangle().getY())
					{
						resizeSmaller(clickPoint);
					}
					break;
				case "bottomLeft":
					if (clickPoint.getX() < panel.getDragObject().getRectangle().getX() && clickPoint.getY() > panel.getDragObject().getRectangle().getY() + panel.getDragObject().getRectangle().getHeight())
					{
						resizeBigger();
					}
					else if (clickPoint.getX() > panel.getDragObject().getRectangle().getX() && clickPoint.getY() < panel.getDragObject().getRectangle().getY() + panel.getDragObject().getRectangle().getHeight())
					{
						resizeSmaller(clickPoint);
					}
					break;
				case "bottomRight":
					if (clickPoint.getX() > panel.getDragObject().getRectangle().getX() + panel.getDragObject().getRectangle().getWidth() && clickPoint.getY() > panel.getDragObject().getRectangle().getY() + panel.getDragObject().getRectangle().getHeight())
					{
						resizeBigger();
					}
					else if (clickPoint.getX() < panel.getDragObject().getRectangle().getX() + panel.getDragObject().getRectangle().getWidth() && clickPoint.getY() < panel.getDragObject().getRectangle().getY() + panel.getDragObject().getRectangle().getHeight())
					{
						resizeSmaller(clickPoint);
					}
					break;
				case "rotate":
					double tempval = panel.getDragObject().getRotation() - (panel.getLastClickPosition().getX() - clickPoint.getX());
					panel.getDragObject().setRotation(tempval);
					break;
			}
		}
		else
		{
			panel.setCameraPoint(new Point2D.Double(panel.getCameraPoint().getX() + (panel.getLastMousePosition().getX() - e.getX()), panel.getCameraPoint().getY() + (panel.getLastMousePosition().getY() - e.getY())));
		}

		panel.repaint();
		panel.setLastMousePosition(e.getPoint());
		panel.setLastClickPosition(clickPoint);
		panel.getPP().update();
	}
	
	public void mouseMoved(MouseEvent e) {
		if(panel.getClickedOption().equals("Path")) 
			panel.getCurrentPath().setTempPoint(panel.getClickPoint(e.getPoint()));
		panel.repaint();
	}

	private void resizeBigger()
	{
		double scale = panel.getDragObject().getScale()*1 + 0.02;
		panel.getDragObject().setScale(scale);
		panel.getDragObject().setPosition(new Point2D.Double(panel.getDragObject().getPosition().getX() -2, panel.getDragObject().getPosition().getY() - 2));
		panel.checkCollision();
	}

	private void resizeSmaller(Point2D clickPoint)
	{
		System.out.println(clickPoint.getX());
		if (panel.getDragObject().getScale() > 0.2)
		{
			double scale = panel.getDragObject().getScale()*1 - 0.02;
			panel.getDragObject().setScale(scale);
			panel.getDragObject().setPosition(new Point2D.Double(panel.getDragObject().getPosition().getX() +2, panel.getDragObject().getPosition().getY() +2 ));
			panel.checkCollision();
		}
	}
}
