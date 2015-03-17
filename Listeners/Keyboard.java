package Listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Applicatie.Panel;

public class Keyboard implements KeyListener {

	private Panel panel;
	
	public Keyboard(Panel panel) {
		this.panel = panel;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == e.VK_DELETE ) {
			if(panel.getSelectedObject() != null) {
				System.out.println("lala");
				panel.removeObject(panel.getSelectedObject());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
