package Applicatie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;

	public MenuBar(Window w)
	{
		super();

		JMenu file = new JMenu("File");

		JMenuItem item = new JMenuItem("New");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				NewWorldPanel world = new NewWorldPanel(w.getPanel());
			}
		});
		file.add(item);
		
		JMenuItem agenda = new JMenuItem("Open Agenda");
		agenda.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new Agenda.Window();
			}
		});
		file.add(agenda);
		
		item = new JMenuItem("Open");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		file.add(item);
		
		item = new JMenuItem("Save");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		file.add(item);
		
		file.addSeparator();
		
		item = new JMenuItem("Exit");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		file.add(item);
		add(file);
		
		file = new JMenu("Path");
		
		item = new JMenuItem("New Path");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				w.getPanel().startPath();
			}
		});
		
		file.add(item);
		add(file);
		
		file = new JMenu("Help");
		
		item = new JMenuItem("About");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(w.getFrames()[0],"Festivalplanner\nMade by: Wesley de Hek, Kenneth van Ewijk, Remco Sannen, Yorick Rommers & Guus van Dongen ");
			}
		});
		file.add(item);
		add(file);
		
	}
}