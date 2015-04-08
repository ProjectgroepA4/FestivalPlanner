package Agenda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;


public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = -2095136277753179215L;

	
	public MenuBar(Window w)
	{
		super();
		
		/*
		 * FILE
		 */
		JMenu file = new JMenu("File");
		
		JMenuItem newEvent = new JMenuItem("New Event");
		newEvent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddEventPanel(w.getAgenda());
			}
		});
		
		JMenuItem newArtist = new JMenuItem("New Artist");
		newArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddArtistPanel(w.getAgenda());
			}
		});
		
		JMenuItem newStage = new JMenuItem("New Stage");
		newStage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddStagePanel(w.getAgenda());
			}
		});
		
		JMenuItem newAgenda = new JMenuItem("New Agenda");
		newAgenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to create a new agenda?", "New Agenda", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
				{
					Agenda ag = w.getAgenda();
					ag.clearAgenda();
					Window.updatePanel();
				}
			}
		});
		
		JMenuItem open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Applicatie.SaveLoad.load(w.getPanel());
				Window.updatePanel();
			}
		});
		
		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Applicatie.SaveLoad.save(w.getPanel());
				Window.updatePanel();
			}
		});
		
		JMenuItem exit = new JMenuItem("Exit"); 
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to close this program?", "Close Agenda", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
				{
					w.setVisible(false);
					w.dispose();
				}
			}
		});
		
		file.add(newEvent);
		file.add(newArtist);
		file.add(newStage);
		file.addSeparator();
		file.add(newAgenda);
		file.addSeparator();
		file.add(open);
		file.add(save);
		file.addSeparator();
		file.add(exit);
		add(file);
		
		
		/*
		 * VIEW
		 */
		
		JMenu view = new JMenu("View");
		
		JMenuItem timeline = new JMenuItem("Timeline");
		timeline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window.changePanel("timeline");
			}
		});
		
		JMenuItem table = new JMenuItem("Table");
		table.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window.changePanel("table");
			}
		});
		
		JMenuItem art_sta = new JMenuItem("Artists and Stages");
		art_sta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window.changePanel("art_sta");
			}
		});
		
		view.add(timeline);
		view.add(table);
		view.add(art_sta);
		add(view);
	}
}
