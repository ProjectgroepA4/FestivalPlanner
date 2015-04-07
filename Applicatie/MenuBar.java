package Applicatie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JMenuBar
{

	public MenuBar(Window w)
	{
		super();

		JMenu file = new JMenu("File");

		JMenuItem newPath = new JMenuItem("New Path");
		newPath.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				w.getPanel().startPath();
			}
		});
		
		file.add(newPath);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		file.add(exit);

		add(file);
	}
}
