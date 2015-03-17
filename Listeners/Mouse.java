package Listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Map;

import Applicatie.DrawObject;
import Applicatie.Panel;
import Objects.Stage;

public class Mouse extends MouseAdapter
{

	private Panel panel;
	private DrawObject selectedObject;

	public Mouse(Panel panel)
	{
		this.panel = panel;
		selectedObject = null;
	}

	public void mousePressed(MouseEvent e)
	{
		Point2D clickPoint = panel.getClickPoint(e.getPoint());
		panel.setLastClickPosition(clickPoint);
		panel.setLastMousePosition(e.getPoint());
		if (e.getY() < 150)
		{
			// if(e.getX() < 51) //51 = width podium image
			// panel.setDragObject(new Podium(clickPoint));
			// else
			// panel.setDragObject(new Toilet(clickPoint));
			int i = 0;
			int last = 0;
			for (BufferedImage image : panel.getPanelInfo())
			{
				if (e.getX() >= panel.getScrollfactor() && e.getX() < last + image.getWidth() + panel.getScrollfactor())
				{
					DrawObject tempDrawObj = panel.createNewDrawObject(i);
					tempDrawObj.setPosition(clickPoint);
					panel.setDragObject(tempDrawObj);
					break;
				}
				i++;
				last += image.getWidth();

			}
			if (panel.getDragObject() != null)
			{
				panel.add(panel.getDragObject());
			}
		}
		else
		{
			for (DrawObject o : panel.getObjects())
			{
				if (o.contains(clickPoint))
				{
					if (o == selectedObject)
					{
						boolean upperLeft = o.containsCorner(clickPoint, 0);
						boolean upperRight = o.containsCorner(clickPoint, 1);
						boolean bottomLeft = o.containsCorner(clickPoint, 2);
						boolean bottomRight = o.containsCorner(clickPoint, 3);
						boolean rotate = o.containsCorner(clickPoint, 4);
						if (upperLeft)
						{
							panel.setClickedOption("upperLeft");
						}
						else if (upperRight)
						{
							panel.setClickedOption("upperRight");
						}
						else if (bottomLeft)
						{
							panel.setClickedOption("bottomLeft");
						}
						else if (bottomRight)
						{
							panel.setClickedOption("bottomRight");
						}
						else if (rotate)
						{
							panel.setClickedOption("rotate");
						}
						else
						{
							panel.setClickedOption("drag");
						}
					}
					if (selectedObject != null)
						selectedObject.setSelected(false);
					panel.setDragObject(o);
					o.setSelected(true);
					selectedObject = o;
					panel.setDragObject(o);
					panel.getPP().setSelected(o);
				}
			}
		}
		if (panel.getDragObject() == null && selectedObject != null)
		{
			selectedObject.setSelected(false);
			panel.getPP().clearSelected();
			selectedObject = null;
		}
		panel.repaint();
	}

	public void mouseReleased(MouseEvent e)
	{
		panel.setDragObject(null);
		panel.setClickedOption("drag");
		panel.repaint();
	}
}
