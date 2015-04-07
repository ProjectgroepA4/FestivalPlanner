package Applicatie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;

	public MenuBar(Window w)
	{
		super();

		JMenu file = new JMenu("File");

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				w.setVisible(false);
				w.dispose();
			}
		});
		
		JMenuItem agenda = new JMenuItem("Open Agenda");
		agenda.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new Agenda.Window();
			}
		});

		JMenuItem save = new JMenuItem("Save terrain");
		save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SaveLoad.save(w.getPanel());
			}
		});

		JMenuItem load = new JMenuItem("Load terrain");
		load.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SaveLoad.load(w.getPanel());
			}
		});

		JMenuItem loadagenda = new JMenuItem("Load agenda");
		loadagenda.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				w.getPanel().getAgenda().loadAgenda();
				w.getPanel().addVisitors();
			}
		});

		file.add(agenda);
		file.add(loadagenda);
		file.add(load);
		file.add(save);
		file.add(exit);

		add(file);
	}
}