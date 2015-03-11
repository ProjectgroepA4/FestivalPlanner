package Listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import Applicatie.DrawObject;
import Applicatie.Panel;
import Objects.Podium;
import Objects.Toilet;
import Applicatie.Panel;

public class Mouse extends MouseAdapter {
	
	private Panel panel;

	public Mouse(Panel panel)
	{
		this.panel = panel;
	}

	public void mousePressed(MouseEvent e) {
		Point2D clickPoint = panel.getClickPoint(e.getPoint());
		panel.setLastClickPosition(clickPoint);
		panel.setLastMousePosition(e.getPoint());

		if(e.getX() < 200)
		{
			if(e.getY() < 300)
				panel.setDragObject(new Podium(clickPoint));
			else
				panel.setDragObject(new Toilet(clickPoint));
			panel.add(panel.getDragObject());
		}
		else
		{
			for(DrawObject o : panel.getObjects())
				if(o.contains(clickPoint))
					panel.setDragObject(o);
		}
	}


	public void mouseReleased(MouseEvent e) {
		panel.setDragObject(null);
	}
}
