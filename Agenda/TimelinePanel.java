package Agenda;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;


public class TimelinePanel extends JPanel implements Panel {

	private Timeline timeline;
//	private WestPanel westPanel;
	
	public TimelinePanel() 
	{
		super(new BorderLayout());
		
		this.setSize(600, 600);
		this.setVisible(true);
		timeline = new Timeline(this);
//		westPanel = new WestPanel();
//		this.add(timeline.createMainPanel(),BorderLayout.CENTER);
//		this.add(new JButton());
//		this.add(timeline.createWestPanel(), BorderLayout.WEST);
	}
	
	public void refresh()
	{
		this.removeAll();	
		timeline.refresh();
//		westPanel.refresh();
		this.add(timeline,BorderLayout.CENTER);
		this.add(timeline.createWestPanel(), BorderLayout.WEST);
		this.revalidate();
//		timeline.refresh();
	}

	@Override
	public void update(ArrayList<Event> events) 
	{
		timeline.setEvents(events);
//		System.out.println(events.size());
//		westPanel.setEvents(events);
//		refresh();
//		this.add(timeline.createMainPanel(),BorderLayout.CENTER);
//		this.add(new JButton());
//		this.add(timeline.createWestPanel(), BorderLayout.WEST);
		
		//dit fixt het half
		if(events.size() != 0)
		{
			this.removeAll();
			timeline.refresh();
			this.add(timeline.createWestPanel(),BorderLayout.WEST);
			this.add(timeline, BorderLayout.CENTER);
			this.revalidate();
		}
		else
		{
			this.removeAll();
			timeline.createNull();
			this.add(timeline);
			this.revalidate();
		}
		
	}
	
	
	public TimelinePanel getTimelinePanel()
	{
		return this;
	}

}
