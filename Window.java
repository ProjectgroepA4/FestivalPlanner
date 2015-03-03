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
		FestivalPanel fp = new FestivalPanel();
		TopBar tb = new TopBar(fp);
		SideBar sb = new SideBar(fp);
		BottomBar bb = new BottomBar(fp);
		contentPanel.add(fp, BorderLayout.CENTER);
		contentPanel.add(tb, BorderLayout.NORTH);
		contentPanel.add(sb, BorderLayout.EAST);
		contentPanel.add(bb, BorderLayout.SOUTH);
		
		setContentPane(contentPanel);
		//END CONTENT PANELS
		
		
		//MENUBAR
		setJMenuBar(new MenuBar(this));
		//END MENUBAR
		
		setFocusable(true);
		setVisible(true);
	}
}
