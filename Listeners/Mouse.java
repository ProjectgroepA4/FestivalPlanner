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
	private DrawObject selectedObject;

	public Mouse(Panel panel)
	{
		this.panel = panel;
		selectedObject = null;
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
				if(o.contains(clickPoint)) {
					if(o == selectedObject) {
						boolean upperLeft = clickPoint.getX() > o.getRectangle().getX() && clickPoint.getX() < o.getRectangle().getX()+o.getRectangle().getWidth()/10
											&& clickPoint.getY() > o.getRectangle().getY() && clickPoint.getY() < o.getRectangle().getY()+o.getRectangle().getHeight()/10;
						if(upperLeft) {
							System.out.println("lal");
							panel.setClickedOption("upperLeft");
						}
						else {
							panel.setClickedOption("drag");
						}
					}
					if(selectedObject != null)
						selectedObject.setSelected(false);
					panel.setDragObject(o);
					o.setSelected(true);
					selectedObject = o;
					System.out.println("selected");
				}
		}
		if(panel.getDragObject() == null && selectedObject != null) {
			System.out.println("deselected");
			selectedObject.setSelected(false);
			selectedObject = null;
		}
		panel.repaint();	
	}


	public void mouseReleased(MouseEvent e) {
		panel.setDragObject(null);
		panel.repaint();
	}
}
