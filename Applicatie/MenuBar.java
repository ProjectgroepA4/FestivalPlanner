package Applicatie;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class MenuBar extends JMenuBar {
		
	
	private static final long serialVersionUID = 1L;

	public MenuBar(Window w)
	{
		super();
		
		JMenu file = new JMenu("File");
		
		JMenuItem exit = new JMenuItem("Exit"); 
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					System.exit(0);
			}
		});
		
		JMenuItem save = new JMenuItem("Save"); 
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					SaveLoad.save(w.fp);
			}
		});
		
		JMenuItem load = new JMenuItem("Load"); 
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					SaveLoad.load(w.fp);
			}
		});
		
		file.add(load);
		file.add(save);
		file.add(exit);
		
		add(file);
	}
}