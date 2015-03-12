package Listeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;
import Applicatie.Panel;

public class MouseWheel implements MouseWheelListener {
	private Panel panel;
	private int scrollfactor, min, max;

	public MouseWheel(Panel panel)
	{
		this.panel = panel;
		scrollfactor = 20;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		Point2D clickPoint = panel.getClickPoint(e.getPoint());
		for(DrawObject o : panel.getObjects())
		{
			if(o.contains(clickPoint))
			{
				double scale = o.getScale()* 1 + (e.getPreciseWheelRotation()/10.0);
				o.setScale(scale);
				panel.repaint();
				return;
			}
		}

		if(e.getY() < 150)
		{

			if(e.getPreciseWheelRotation() < 0)
			{
				panel.setScrollfactor(panel.getScrollfactor() - scrollfactor);
			}
			else
			{
				panel.setScrollfactor(panel.getScrollfactor() + scrollfactor);
			}

		}
		else
		{
			//			cameraPoint
			double cameraScale = panel.getCameraScale() * 1 - (e.getPreciseWheelRotation()/10);
			panel.setCameraScale((float) cameraScale);

		}


		panel.repaint();
	}
}
