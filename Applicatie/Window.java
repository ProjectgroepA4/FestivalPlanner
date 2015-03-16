package Applicatie;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Window extends JFrame {
	private static final long serialVersionUID = -1324363758675184283L;

	Window()
	{
		super("Festival Plannner");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,600);
		setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
		
		//CONTENT PANELS
		JPanel contentPanel = new JPanel(new BorderLayout());
		Panel fp = new Panel();
		contentPanel.add(fp, BorderLayout.CENTER);
		
		setContentPane(contentPanel);
		//END CONTENT PANELS
		
		
		//MENUBAR
		setJMenuBar(new MenuBar(this));
		//END MENUBAR
		
		setFocusable(true);
		setVisible(true);
	}
}
