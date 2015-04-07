package Agenda;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Constructs a JTable in a ScrolPane.
 * @author Wesley
 * @version 1.3
 */
public class PanelTable extends JPanel implements Panel{

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private Object[][] data;
	private String[] columnNames = {"Stage",
            "Event",
            "Artist",
            "Begin Time",
            "End Time"};
	private ArrayList<Event> events;
	private ArrayList<Event> fullEvents;
	private JTable table;
	private JTable selectedCell;
	private Agenda a;
	
	/**
	 * Constructor makes the table and adds it to a scrollPane.
	 * @param events
	 */
	public PanelTable(Agenda a) {
		super.setLayout(new BorderLayout());
		this.a = a;
		table= new JTable();
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(table.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { if(e.getClickCount() > 1 ) cellClicked(e); else selectedCell = (JTable) e.getSource(); }
		});
		scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);	
		add(scrollPane,BorderLayout.SOUTH);	
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton button;
		button = new JButton("Filter");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { filter(); }
		});
		buttonPanel.add(button);
		button = new JButton(new ImageIcon("sprites/sprite0.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { update(fullEvents, true); }
		});
		buttonPanel.add(button);
		add(buttonPanel,BorderLayout.NORTH);
	}
	
	/**
	 * Sets the size of the scrollPane, in which the table is placed to fill out its container panel (not sure if this is needed?)
	 */
	public void paintComponent(Graphics g) {
		scrollPane.setPreferredSize(new Dimension(getWidth(),getHeight()-35));
	}
	
	/**
	 * Gets the data from the events arrayList and makes a 2D-array of it.
	 * @param columnes
	 * @param rows
	 */
	public void compileData(int columnes, int rows) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		data = new Object[rows][columnes];	
			for(int x = 0; x < rows; x++) {
				Object[] tempData = new Object[columnes];
				tempData[0] = events.get(x).getStage().getName();
				tempData[1] = events.get(x).getEventName();
				tempData[2] = events.get(x).getArtist().getName();
				tempData[3] = formatter.format(events.get(x).getStartDate().getTime());
				tempData[4] = formatter.format(events.get(x).getEndDate().getTime());
				data[x] = tempData;
			}		  
	}
	
	/**
	 * Refreshes the content on the table with a fresh ArrayList of events, useful when things are removed and added and should be called every time you update the list.
	 * @param events
	 */
	public void update(ArrayList<Event> events)
	{
		update(events, true);
	}
	
	public void update(ArrayList<Event> events, boolean newList) {
		this.events = events;
		if(newList)
			this.fullEvents = events;
		compileData(columnNames.length,events.size());
		AbstractTableModel tableModel = new ContentTable(data,columnNames);
		table.setModel(tableModel);
	}
	
	/**
	 * Method for the action listener when a cell is clicked, opens dialog of the clicked cell.
	 * @param e
	 */
	private void cellClicked(MouseEvent e) {
		JTable target = (JTable) e.getSource();
		switch(target.getSelectedColumn()) {
			case 0:
				//A dialog for podia?
				break;
			case 1:
				openEventDialog(target.getSelectedRow());
				break;
			case 2:
				openArtistDialog(target.getSelectedRow());
				break;
		}
	}

	/**
	 * Opens a dialog with information of the selected event. 
	 * @param row
	 */
	private void openEventDialog(int row) {
		Event event = events.get(row);
		new TabbedPane(a, event, true);				
	}
	
	/**
	 * Opens a dialog with information of the selected artist
	 * @param row
	 */
	private void openArtistDialog(int row) {
		Event event = events.get(row);
		new TabbedPane(a, event, false);				
	}
	
	/**
	 * Filter method which filters on the currently selected cell.
	 * Needs to be under a button action, also a reset button is needed so list can be restored.
	 */
	public void filter() {
		if(selectedCell != null) {
			if(fullEvents.isEmpty()) {
				fullEvents = events;
			}
			ArrayList<Event> filteredList = new ArrayList<>();
			switch(selectedCell.getSelectedColumn()) {
			case 0:
				for(Event event : fullEvents) {
					if(event.getStage().equals(events.get(selectedCell.getSelectedRow()).getStage())) {
						filteredList.add(event);
					}
				}
				break;
			case 1: 
				for(Event event : fullEvents) {
					if(event.getEventName().equals(events.get(selectedCell.getSelectedRow()).getEventName())) {
						filteredList.add(event);
					}
				}
				break;
			case 2: 
				for(Event event : fullEvents) {
					if(event.getArtist().equals(events.get(selectedCell.getSelectedRow()).getArtist())) {
						filteredList.add(event);
					}
				}
				break;
			case 3:
				int selectedCellStartTime = events.get(selectedCell.getSelectedRow()).getStartDate().getTime().getHours()*100;
				selectedCellStartTime += events.get(selectedCell.getSelectedRow()).getStartDate().getTime().getMinutes();
				int selectedCellEndTime = events.get(selectedCell.getSelectedRow()).getEndDate().getTime().getHours()*100;
				selectedCellEndTime += events.get(selectedCell.getSelectedRow()).getEndDate().getTime().getMinutes();
				for(Event event : fullEvents) {
					int time = event.getStartDate().getTime().getHours()*100;
					time += event.getStartDate().getTime().getMinutes();
					if(time >= selectedCellStartTime && time <= selectedCellEndTime) {
						filteredList.add(event);
					}
				}
				break;
			case 4:
				selectedCellStartTime = events.get(selectedCell.getSelectedRow()).getStartDate().getTime().getHours()*100;
				selectedCellStartTime += events.get(selectedCell.getSelectedRow()).getStartDate().getTime().getMinutes();
				selectedCellEndTime = events.get(selectedCell.getSelectedRow()).getEndDate().getTime().getHours()*100;
				selectedCellEndTime += events.get(selectedCell.getSelectedRow()).getEndDate().getTime().getMinutes();
				for(Event event : fullEvents) {
					int time = event.getEndDate().getTime().getHours()*100;
					time += event.getEndDate().getTime().getMinutes();
					if(time >= selectedCellStartTime && time <= selectedCellEndTime) {
						filteredList.add(event);
					}
				}
				break;
			}
			update(filteredList,false);	
			selectedCell = null;
		}
		else {
			JOptionPane.showMessageDialog(this, "Select a cell with the value u want to filter on","No cell selected",JOptionPane.WARNING_MESSAGE);
		}
	}

}
