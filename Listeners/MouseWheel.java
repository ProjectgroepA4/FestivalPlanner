package Listeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;
import Applicatie.Panel;

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
			}

		}

		panel.repaint();
	}
}
