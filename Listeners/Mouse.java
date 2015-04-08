package Listeners;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import Applicatie.Panel;
import Applicatie.PathPopup;

import Applicatie.Waypoint;
import Applicatie.WaypointPopup;
import Objects.DrawObject;
import Objects.Path;
import Objects.Entrance;

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

		if (!panel.getClickedOption().equals("Path"))
		{
			if (e.getY() < 150)
			{
				int i = 0;
				int last = 0;
				for (BufferedImage image : panel.getPanelInfo())
				{
					if (e.getX() >= panel.getScrollfactor() && e.getX() < last + image.getWidth() + panel.getScrollfactor())
					{
						DrawObject tempDrawObj = panel.createNewDrawObject(i);
						if (tempDrawObj != null)
						{
							tempDrawObj.setPosition(clickPoint);
							if (!panel.getObjects().isEmpty())
								panel.clearObjectSelection();
							panel.setDragObject(tempDrawObj);
							panel.getDragObject().setSelected(true);
							panel.getPP().setSelected(tempDrawObj);
							panel.setSelectedObject(tempDrawObj);
							selectedObject = tempDrawObj;
						}
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
						if (SwingUtilities.isRightMouseButton(e))
						{
							if (o instanceof Waypoint)
							{
								new WaypointPopup(o, panel);

							}
						}
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
						o.setSelected(true);
						selectedObject = o;
						panel.setDragObject(o);
						panel.getPP().setSelected(o);
						panel.setSelectedObject(o);
						panel.setSelectionPosition(panel.getDragObject().getPosition());

					}
				}
				Path clickedPath = panel.getClickedPath(clickPoint);
				if (clickedPath != null)
				{
					if (SwingUtilities.isRightMouseButton(e))
					{
						new PathPopup(clickedPath, panel);
					}
				}
				else
				{
					panel.checkPath(clickPoint);
				}

			}
			if (panel.getDragObject() == null && selectedObject != null)
			{
				selectedObject.setSelected(false);
				panel.setSelectedObject(null);
				panel.getPP().clearSelected();
				selectedObject = null;
			}
		}
		else
		{ // Making path.

			panel.getCurrentPath().addPoint(new Point2D.Double(clickPoint.getX(), clickPoint.getY()));

		}
		panel.repaint();
	}

	public void mouseReleased(MouseEvent e)
	{
		if (panel.getDragObject() != null)
		{
			if (panel.getDragObject().getRectangleColor() != Color.RED)
			{
				panel.getDragObject().setPosition(panel.getDragObject().getPosition(), true);
				panel.getPP().update();
				panel.setDragObject(null);
				panel.setClickedOption("drag");
			}
			else
			{
				panel.getDragObject().setPosition(panel.getSelectionPosition());
				panel.getDragObject().setRectangleColor(Color.BLACK);
			}
			panel.setSelectionPosition(panel.getSelectedObject().getPosition());
		}
		panel.repaint();
	}
}
