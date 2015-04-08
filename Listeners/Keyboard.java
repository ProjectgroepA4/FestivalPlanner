package Listeners;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import Applicatie.NewWorldPanel;
import Applicatie.Panel;
import Applicatie.SaveLoad;

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
				panel.getSelectedObject().setPosition(new Point2D.Double(panel.getSelectedObject().getPosition().getX()-5,panel.getSelectedObject().getPosition().getY()), true);
			panel.checkCollision();
			panel.getPP().update();
		}
		else if(e.getKeyCode() == e.VK_RIGHT) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setPosition(new Point2D.Double(panel.getSelectedObject().getPosition().getX()+5,panel.getSelectedObject().getPosition().getY()), true);
			panel.checkCollision();
			panel.getPP().update();
		}
		else if(e.getKeyCode() == e.VK_UP) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setPosition(new Point2D.Double(panel.getSelectedObject().getPosition().getX(),panel.getSelectedObject().getPosition().getY()-5), true);
			panel.checkCollision();
			panel.getPP().update();
		}
		else if(e.getKeyCode() == e.VK_DOWN) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setPosition(new Point2D.Double(panel.getSelectedObject().getPosition().getX(),panel.getSelectedObject().getPosition().getY()+5), true);
			panel.checkCollision();
			panel.getPP().update();
		}
		else if(e.getKeyCode() == e.VK_ADD) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setScale(panel.getSelectedObject().getScale()+0.2);
			panel.checkCollision();
			panel.getPP().update();
		}
		else if(e.getKeyCode() == e.VK_SUBTRACT) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setScale(Math.max(panel.getSelectedObject().getScale()-0.2, 0.2));
			panel.checkCollision();
			panel.getPP().update();
		}
		else if(e.getKeyCode() == e.VK_COMMA) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setRotation((panel.getSelectedObject().getRotation()-5)%360);
			panel.checkCollision();
			panel.getPP().update();
		}
		else if(e.getKeyCode() == e.VK_PERIOD) {
			if(panel.getSelectedObject() != null) 
				panel.getSelectedObject().setRotation((panel.getSelectedObject().getRotation()+5)%360);
			panel.checkCollision();
			panel.getPP().update();
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
		else if(e.getKeyCode() == e.VK_N) {
			NewWorldPanel world = new NewWorldPanel(panel);
		}
		else if(e.getKeyCode() == e.VK_S) {
			SaveLoad.save(panel);
		}
		else if(e.getKeyCode() == e.VK_O) {
			SaveLoad.load(panel);
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
