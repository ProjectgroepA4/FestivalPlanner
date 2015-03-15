package Listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Map;

import Applicatie.DrawObject;
import Applicatie.Panel;
import Objects.Podium;

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
		if(e.getY() < 150)
		{
//			if(e.getX() < 51) //51 = width podium image
//				panel.setDragObject(new Podium(clickPoint));
//			else
//				panel.setDragObject(new Toilet(clickPoint));
			int i = 0;
			int last = 0;
			for(BufferedImage image : panel.getPanelInfo())
			{
				if(e.getX() >= panel.getScrollfactor() && e.getX() < last + image.getWidth() + panel.getScrollfactor())
				{
					DrawObject tempDrawObj = panel.createNewDrawObject(i);
					tempDrawObj.setPosition(clickPoint);
					panel.setDragObject(tempDrawObj);
					break;
				}
				i++;
				last += image.getWidth();
				
			}
			if(panel.getDragObject() != null)
			{
				panel.add(panel.getDragObject());
			}
		}
		else
		{
			for(DrawObject o : panel.getObjects())
			{
				if(o.contains(clickPoint))
				{
					panel.setDragObject(o);
					panel.getPP().setSelected(o);
					return;
				}
			}
			panel.getPP().clearSelected();
		}
	}

	public void mouseReleased(MouseEvent e) {
		panel.setDragObject(null);
	}
}
