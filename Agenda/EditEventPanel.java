package Agenda;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class EditEventPanel extends JFrame{

	private static final long serialVersionUID = 1;

	public EditEventPanel(Agenda agenda, Event e, TabbedPane tab)
	{
		super("Event editscreen!");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel content = new JPanel(new BorderLayout());
		JPanel south = new JPanel(new FlowLayout());
		JPanel center = new JPanel(new GridLayout(7,2));
		content.add(south, BorderLayout.SOUTH);
		content.add(center, BorderLayout.CENTER);
		
		
		JPanel panel1 = new JPanel();
		JLabel name = new JLabel("Event name: ");
		panel1.add(name);
		center.add(panel1);
		
		JPanel panel2 = new JPanel();
		JTextField nameTF = new JTextField(e.getEventName(),15);
		panel2.add(nameTF);
		center.add(panel2);
		
		JLabel starttime = new JLabel("Starttime: ");
		JPanel panel3 = new JPanel();
		panel3.add(starttime);
		center.add(panel3);
		
		Integer[] hours = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 };
		JComboBox<Integer> hour = new JComboBox<Integer>(hours);
		hour.setSelectedIndex(e.getStartDate().get(GregorianCalendar.HOUR_OF_DAY));
		JPanel panel4 = new JPanel();
		panel4.add(hour);
		center.add(panel4);
		
		Integer[] minutes = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59};
		JComboBox<Integer> minute = new JComboBox<Integer>(minutes);
		minute.setSelectedIndex(e.getStartDate().get(GregorianCalendar.MINUTE));
		panel4.add(minute);
		panel4.add(new JSeparator(SwingConstants.VERTICAL));
		panel4.add(new JSeparator(SwingConstants.VERTICAL));
		
		
		Integer[] days = { 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
		JComboBox<Integer> day = new JComboBox<Integer>(days);
		day.setSelectedIndex(e.getStartDate().get(GregorianCalendar.DAY_OF_MONTH) - 1);
		panel4.add(day);
		
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		JComboBox<String> month = new JComboBox<String>(months);
		month.setSelectedIndex(e.getStartDate().get(GregorianCalendar.MONTH));
		panel4.add(month);
		
		
		Integer[] years = { 2015,2016,2017,2018};
		JComboBox<Integer> year = new JComboBox<Integer>(years);
		year.setSelectedItem(e.getStartDate().get(GregorianCalendar.YEAR));
		panel4.add(year);
		
		
		
		JPanel panel5 = new JPanel();
		JLabel endtime = new JLabel("Endtime: ");
		panel5.add(endtime);
		center.add(panel5);
		
		JComboBox<Integer> hourE = new JComboBox<Integer>(hours);
		hourE.setSelectedIndex(e.getEndDate().get(GregorianCalendar.HOUR_OF_DAY));
		JPanel panel6 = new JPanel();
		panel6.add(hourE);
		center.add(panel6);
		
		JComboBox<Integer> minuteE = new JComboBox<Integer>(minutes);
		minuteE.setSelectedIndex(e.getEndDate().get(GregorianCalendar.MINUTE));
		panel6.add(minuteE);
		panel6.add(new JSeparator(SwingConstants.VERTICAL));
		panel6.add(new JSeparator(SwingConstants.VERTICAL));
		
		
		JComboBox<Integer> dayE = new JComboBox<Integer>(days);
		dayE.setSelectedIndex(e.getEndDate().get(GregorianCalendar.DAY_OF_MONTH) - 1);
		panel6.add(dayE);
		
		JComboBox<String> monthE = new JComboBox<String>(months);
		monthE.setSelectedIndex(e.getEndDate().get(GregorianCalendar.MONTH));
		panel6.add(monthE);
		
		
		JComboBox<Integer> yearE = new JComboBox<Integer>(years);
		yearE.setSelectedItem(e.getEndDate().get(GregorianCalendar.YEAR));
		panel6.add(yearE);	
		
		
		JPanel panel7 = new JPanel();
		JLabel artist2 = new JLabel("Artist: ");
		panel7.add(artist2);
		center.add(panel7);
		
		JPanel panel8 = new JPanel();
		String[] artists = new String[agenda.getArtists().size()];
		int artistIndex = 0;
		
		for ( int idx = 0; idx < agenda.getArtists().size(); idx ++)
		{
			artists[idx] = agenda.getArtists().get(idx).getName();
			if(agenda.getArtists().get(idx).equals(e.getArtist()))
			{
				artistIndex = idx;
			}
		}
		
		JComboBox<String> artist = new JComboBox<String>(artists);
		artist.setSelectedIndex(artistIndex);
		panel8.add(artist);
		center.add(panel8);
		
		JPanel panel9 = new JPanel();
		JLabel stage = new JLabel("Stage: ");
		panel9.add(stage);
		center.add(panel9);
		
		JPanel panel10 = new JPanel();
		String[] stages = new String[agenda.getStages().size()];
		
		int stageIndex = 0;
		
		for ( int idx = 0; idx < agenda.getStages().size(); idx ++)
		{
			stages[idx] = agenda.getStages().get(idx).getName();
			if(agenda.getStages().get(idx).equals(e.getStage()))
			{
				stageIndex = idx;
			}
		}
		
		JComboBox<String> stage2 = new JComboBox<String>(stages);
		stage2.setSelectedIndex(stageIndex);
		panel10.add(stage2);
		center.add(panel10);
		
		JPanel panel11 = new JPanel();
		JLabel description = new JLabel("Description: ");
		panel11.add(description);
		center.add(panel11);
		
		JPanel panel12 = new JPanel();
		JTextField descriptionTF = new JTextField(e.getDescription(),15);
		panel12.add(descriptionTF);
		center.add(panel12);
		
		JPanel panel13 = new JPanel();
		JLabel expectedPopularity = new JLabel("Expected Popularity: ");
		panel13.add(expectedPopularity);
		center.add(panel13);
		
		JPanel panel14 = new JPanel();
		Integer[] eps= { 1,2,3,4,5,6,7,8,9,10}; 
		JComboBox<Integer> ep = new JComboBox<Integer>(eps);
		ep.setSelectedIndex(e.getExpectedPopularity() - 1);
		panel14.add(ep);
		center.add(panel14);
		
		

		
		
		JButton add = new JButton("Edit event!");
		south.add(add);
		
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent f) {
				boolean everythingCorrect = true;
				String name = nameTF.getText();
				
				int hourS = hour.getSelectedIndex();
				int minuteS = minute.getSelectedIndex();
				int dayS = day.getSelectedIndex() + 1;
				String monthS = (String) month.getSelectedItem();
				int yearS = year.getSelectedIndex() + 2015;
				
				int hourEnd = hourE.getSelectedIndex();
				int minuteEnd = minuteE.getSelectedIndex();
				int dayEnd = dayE.getSelectedIndex() + 1;
				String monthEnd = (String) monthE.getSelectedItem();
				int yearEnd = yearE.getSelectedIndex() + 2015;
				
				String artistName = (String)artist.getSelectedItem();
				
				Artist artist = null;
				
				for(Artist a : agenda.getArtists())
				{
					if (a.getName().equals(artistName))
					{
						artist = a;
					}
				}
				
				String stageName = (String)stage2.getSelectedItem();
				
				AgendaStage stage = null;

				for(AgendaStage s : agenda.getStages())
				{
					if (s.getName().equals(stageName))
					{
						stage = s;
					}
				}
				
				String description = descriptionTF.getText();
				
				int popularity = ep.getSelectedIndex() + 1;
				
				if (dayS > monthDays(monthS)){
					everythingCorrect = false;
					JOptionPane.showMessageDialog(null, "Invalid Date!");
				}
				else if (yearS > yearEnd || monthToInt(monthS) > monthToInt(monthEnd) || dayS > dayEnd || hourS > hourEnd || minuteS > minuteEnd){
					everythingCorrect = false;	
					JOptionPane.showMessageDialog(null, "End time is earlier then start time!");
				}
				else if (dayS != dayEnd || monthS.equals(monthEnd) == false || yearS != yearEnd){
					everythingCorrect = false;	
					JOptionPane.showMessageDialog(null, "Event is too long! It can't be longer than a single day.");
				} else if (name.isEmpty()){
					everythingCorrect = false;
					JOptionPane.showMessageDialog(null, "Fill in a correct name!");
				} else if (stage == null){
					everythingCorrect = false;
					JOptionPane.showMessageDialog(null, "Missing a stage!");
				} else if (artist == null){
					JOptionPane.showMessageDialog(null, "Missing a artist!");
				}
				
				
				if (everythingCorrect == true)
				{
					e.setEventName(name);
					e.setDescription(description);
					e.setExpectedPopularity(popularity);
					e.setArtist(artist);
					e.setStage(stage);
					e.setStartDate(yearS, monthToInt(monthS), dayS, hourS, minuteS);
					e.setEndDate(yearEnd, monthToInt(monthEnd), dayEnd, hourEnd, minuteEnd);
					
					everythingCorrect = false;	
					JOptionPane.showMessageDialog(null, "Event edited!");
					setVisible(false);
					dispose();
					Window.updatePanel();
					if(tab != null)
					{
					tab.setVisible(false);
					tab.dispose();
					Event eventartist = null;
					for(Event event: agenda.getEvents())
					{
						if (event.getEventName().equals(name))
						{
							eventartist = event;
						}
					}
					TabbedPane panel = new TabbedPane(agenda, eventartist, true);
					}
				}
				
				
			}
		});
		
		JButton remove = new JButton("Remove event!");
		south.add(remove);
		
		remove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent f) {
				
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this event?", "Remove Event", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
				{
					Iterator<Event> it = agenda.getEvents().iterator();
					while(it.hasNext())
					{
						Event ev = it.next();
						if(ev.equals(e))
						{
							it.remove();
						}
					}
					
					JOptionPane.showMessageDialog(null, "Event removed!");
					setVisible(false);
					dispose();
					Window.updatePanel();
					if(tab != null)
					{
						tab.setVisible(false);
						tab.dispose();
					}
				}
			}
		});
		
		
		setContentPane(content);
		setVisible(true);
		setSize(700,320);
	}
	
	public int monthToInt(String month)
	{
		int number = 0;
		switch (month)
		{
		case "January": number = 1; break;
		case "February": number = 2; break;
		case "March": number = 3; break;
		case "April": number = 4; break;
		case "May": number = 5; break;
		case "June":number = 6; break;
		case "July": number = 7; break;
		case "August": number = 8; break;
		case "September":number = 9; break;
		case "October": number = 10; break;
		case "November": number = 11; break;
		case "December": number = 12; break;
		}
		return number;		
	}
	
	public int monthDays(String month)
	{
		int number = 0;
		switch (month)
		{
		case "January": number = 31; break;
		case "February": number = 28; break;
		case "March": number = 31; break;
		case "April": number = 30; break;
		case "May": number = 31; break;
		case "June":number = 30; break;
		case "July": number = 31; break;
		case "August": number = 31; break;
		case "September":number = 30; break;
		case "October": number = 31; break;
		case "November": number = 30; break;
		case "December": number = 31; break;
		}
		return number;		
	}
	

}
