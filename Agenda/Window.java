package Agenda;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.lang3.time.DateUtils;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 9023061329829975662L;
	private static HashMap<String, Panel> panels = new HashMap<String, Panel>();
	private static JPanel centerPanel;
	private static GregorianCalendar date;
	private static Agenda agenda;
	private static String currentPanel = "table";
	
	public Window()
	{
		/*
		 * Initialize window
		 */
		super("Agenda");
		agenda = new Agenda();
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent evt) {
			     onExit();
			 }
		});
		setSize(1200, 800);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
        try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setJMenuBar(new MenuBar(this));
		
		
		/*
		 * Create Panels
		 */
		
		//Agenda Panels
		Panel tablePanel = new PanelTable(agenda);
		panels.put("table", tablePanel);
		
		Panel art_staPanel = new PanelArtSta(this);
		panels.put("art_sta", art_staPanel);
		
		Panel timeline = new TimelinePanel();
		panels.put("timeline", timeline);
		
		//Main Panels
		JPanel mainPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.DARK_GRAY);
		
		date = new GregorianCalendar();
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
		JLabel dateLabel = new JLabel(formatter.format(date.getTime()));
		dateLabel.setForeground(Color.WHITE);
		bottomPanel.add(dateLabel);
		
		JButton backWeekButton = new JButton("<<");
		backWeekButton.setToolTipText("Go one week back");
		backWeekButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				date.set(GregorianCalendar.DAY_OF_MONTH, date.get(GregorianCalendar.DAY_OF_MONTH) - 7);
				dateLabel.setText(formatter.format(date.getTime()));
				updatePanel();
			}
		});
		bottomPanel.add(new JSeparator(SwingConstants.VERTICAL));
		bottomPanel.add(backWeekButton);
		
		JButton backDayButton = new JButton("<");
		backDayButton.setToolTipText("Go one day back");
		backDayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				date.set(GregorianCalendar.DAY_OF_MONTH, date.get(GregorianCalendar.DAY_OF_MONTH) - 1);
				dateLabel.setText(formatter.format(date.getTime()));
				updatePanel();
			}
		});
		bottomPanel.add(new JSeparator(SwingConstants.VERTICAL));
		bottomPanel.add(backDayButton);
		
		JButton todayButton = new JButton("||");
		todayButton.setToolTipText("Go to today");
		todayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				date = new GregorianCalendar();
				dateLabel.setText(formatter.format(date.getTime()));
				updatePanel();
			}
		});
		bottomPanel.add(new JSeparator(SwingConstants.VERTICAL));
		bottomPanel.add(todayButton);
		
		JButton forwardDayButton = new JButton(">");
		forwardDayButton.setToolTipText("Go one day forward");
		forwardDayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				date.set(GregorianCalendar.DAY_OF_MONTH, date.get(GregorianCalendar.DAY_OF_MONTH) + 1);
				dateLabel.setText(formatter.format(date.getTime()));
				updatePanel();
			}
		});
		bottomPanel.add(new JSeparator(SwingConstants.VERTICAL));
		bottomPanel.add(forwardDayButton);
		
		JButton forwardWeekButton = new JButton(">>");
		forwardWeekButton.setToolTipText("Go one week forward");
		forwardWeekButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				date.set(GregorianCalendar.DAY_OF_MONTH, date.get(GregorianCalendar.DAY_OF_MONTH) + 7);
				dateLabel.setText(formatter.format(date.getTime()));
				updatePanel();
			}
		});
		bottomPanel.add(new JSeparator(SwingConstants.VERTICAL));
		bottomPanel.add(forwardWeekButton);
		
		//Add panels
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		setContentPane(mainPanel);
		
		updatePanel();
		
		//Show window
		setVisible(true);
		this.getRootPane().addComponentListener(new ComponentAdapter() 
		{
			public void componentResized(ComponentEvent e) {
                if(currentPanel == "timeline")
                {
                	TimelinePanel tempTimeLine = (TimelinePanel) timeline;
                    tempTimeLine.refresh();
                }
				

            }
		});
	}
	
	public static void changePanel(String panel)
	{
		currentPanel = panel;
		updatePanel();
	}
	
	public static void updatePanel()
	{
		centerPanel.removeAll();
		Panel p = panels.get(currentPanel);
		p.update(getEvents());
		JPanel p1 = (JPanel) p;
		p1.setPreferredSize(centerPanel.getSize());
		centerPanel.add(p1, BorderLayout.CENTER);
		centerPanel.revalidate();
		centerPanel.repaint();
		p1.repaint();
	}
	
	public void onExit() {
		if(JOptionPane.showConfirmDialog(null, "Are you sure you want to close this program?", "Close Agenda", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
		{
			setVisible(false);
			dispose();
		}
	}
	
	public static ArrayList<Event> getEvents()
	{
		ArrayList<Event> events = new ArrayList<Event>();
		for(Event e : agenda.getEvents())
		{
			if(DateUtils.isSameDay(e.getStartDate(), date) || DateUtils.isSameDay(e.getEndDate(), date))
			{
				events.add(e);
			}
		}
		
		return events;
	}
	
	public Agenda getAgenda()
	{
		return agenda;
	}
	
	public void setAgenda(Agenda a)
	{
		agenda = a; 
	}
	
	
}
