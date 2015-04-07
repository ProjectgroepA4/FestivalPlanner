package Listeners;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import Applicatie.Panel;

public class Keyboard implements KeyListener {

	private Panel panel;
	
	public Keyboard(Panel panel) {
		this.panel = panel;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == e.VK_DELETE ) {
			if(panel.getSelectedObject() != null) 
				panel.removeObject(panel.getSelectedObject());
			else if(panel.getCurrentPath() != null) 
				panel.removePath(panel.getCurrentPath());
		}
		else if(e.getKeyCode() == e.VK_LEFT) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setPosition(new Point2D.Double(panel.getSelectedObject().getPosition().getX()-5,panel.getSelectedObject().getPosition().getY()));
			panel.checkCollision();
		}
		else if(e.getKeyCode() == e.VK_RIGHT) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setPosition(new Point2D.Double(panel.getSelectedObject().getPosition().getX()+5,panel.getSelectedObject().getPosition().getY()));
			panel.checkCollision();
		}
		else if(e.getKeyCode() == e.VK_UP) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setPosition(new Point2D.Double(panel.getSelectedObject().getPosition().getX(),panel.getSelectedObject().getPosition().getY()-5));
			panel.checkCollision();
		}
		else if(e.getKeyCode() == e.VK_DOWN) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setPosition(new Point2D.Double(panel.getSelectedObject().getPosition().getX(),panel.getSelectedObject().getPosition().getY()+5));
			panel.checkCollision();
		}
		else if(e.getKeyCode() == e.VK_ENTER) {
			if(panel.getClickedOption().equals("Path")) {
				panel.setClickedOption("drag");
				panel.getCurrentPath().setTempPoint(null);
				panel.setcurrentPath(null);
				panel.getPP().clearSelected();
			}
		}
		else if(e.getKeyCode() == e.VK_P) {
			panel.startPath();
		}
		panel.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//if(e.getKeyCode() == e.VK_LEFT) {
			checkCollision();
		//}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	private void checkCollision() {
		if(panel.getSelectedObject() != null) {
			if(panel.getSelectedObject().getRectangleColor() != Color.BLACK) {
				panel.getSelectedObject().setPosition(panel.getSelectionPosition());
				panel.getSelectedObject().setRectangleColor(Color.BLACK);
			}
			panel.repaint();
		}
	}
}
