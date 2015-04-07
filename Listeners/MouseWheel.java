package Listeners;

import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import Applicatie.Panel;
import Objects.DrawObject;

public class MouseWheel implements MouseWheelListener
{
	private Panel panel;
	private int scrollfactor;

	public MouseWheel(Panel panel)
	{
		this.panel = panel;
		scrollfactor = 20;
	}

	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if (e.getY() < 150)
		{

			if (e.getPreciseWheelRotation() < 0)
			{
				if (panel.getScrollfactor() + scrollfactor + panel.getPanelInfoLength() < panel.getWidth())
				{
					panel.setScrollfactor(panel.getScrollfactor() + scrollfactor);
				}

			} else
			{
				if (panel.getScrollfactor() - scrollfactor >= 0)
				{
					panel.setScrollfactor(panel.getScrollfactor() - scrollfactor);
				}

			}

		} else
		{
			// cameraPoint
			double cameraScale = panel.getCameraScale() * 1 - (e.getPreciseWheelRotation() / 10);
			if (cameraScale >= 0.1 && cameraScale < 4)
			{
				panel.setCameraScale((float) cameraScale);
				//panel.setCameraPoint(new Point2D.Double(e.getX()-(panel.getWidth()/2)-panel.getCameraPoint().getX(), e.getY()-(panel.getHeight()/2) - panel.getCameraPoint().getY()));
				//panel.setCameraPoint( new Point2D.Double( 
				//							-panel.getClickPoint(new Point(e.getX(), e.getY())).getX(),
				//							-panel.getClickPoint(new Point(e.getX(), e.getY())).getY()
				//							));
				//TODO FIX
				}

		}

		panel.repaint();
	}
}
