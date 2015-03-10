package Listneners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;
import Applicatie.Panel;

public class MouseWheel implements MouseWheelListener {
	private Panel panel;
	
	public MouseWheel(Panel panel)
	{
		this.panel = panel;
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
		
//		cameraPoint
		double cameraScale = panel.getCameraScale() * 1 - (e.getPreciseWheelRotation()/10);
		panel.setCameraScale((float) cameraScale);
		panel.repaint();
	}
}
