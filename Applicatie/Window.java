package Applicatie;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Listeners.Keyboard;

public class Window extends JFrame
{
	private static final long serialVersionUID = -1324363758675184283L;

	private Panel fp;
	
	Window() {
		super("Festival Plannner");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setSize(500, 600);
		setMinimumSize(new Dimension(1160, 680));
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

		// CONTENT PANELS
		JPanel contentPanel = new JPanel(new BorderLayout());
		PropertiesPanel pp = new PropertiesPanel();
		ControlPanel cp = new ControlPanel();
		fp = new Panel(pp, cp);
		contentPanel.add(fp, BorderLayout.CENTER);
		contentPanel.add(cp, BorderLayout.SOUTH);
		contentPanel.add(pp, BorderLayout.EAST);
		contentPanel.add(fp, BorderLayout.CENTER);
		

		addKeyListener(new Keyboard(fp));
		setContentPane(contentPanel);
		// END CONTENT PANELS

		// MENUBAR
		setJMenuBar(new MenuBar(this));
		// END MENUBAR

		setFocusable(true);
		setVisible(true);
	}
	
	public Panel getPanel() {
		return fp;
	}
}
