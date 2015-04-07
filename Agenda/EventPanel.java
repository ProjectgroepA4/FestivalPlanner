package Agenda;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class EventPanel extends JPanel{

	private static final long serialVersionUID = 504733981352439417L;

	public EventPanel(Event event, Agenda a, TabbedPane tab)
	{
		super(new BorderLayout());
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat timeformatter = new SimpleDateFormat("HH:mm");
		JPanel box1 = new JPanel();
		box1.setLayout(new BoxLayout(box1, BoxLayout.PAGE_AXIS));
		JPanel box2 = new JPanel();
		box2.setLayout(new BoxLayout(box2, BoxLayout.LINE_AXIS));
		JPanel box3 = new JPanel();
		box3.setLayout(new BoxLayout(box3, BoxLayout.PAGE_AXIS));
		JPanel row = new JPanel();
		row.setLayout(new BoxLayout(row, BoxLayout.LINE_AXIS));
		JPanel panel = new JPanel();
		JButton edit = new JButton("Edit Info");
		edit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				EditEventPanel edit = new EditEventPanel(a, event, tab);
			}
		});
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JLabel title = new JLabel(event.getEventName());
		JLabel date = new JLabel("<html>" + dateformatter.format(event.getEndDate().getTime()) + "</html>");
		JLabel data = new JLabel("<html> " + event.getStage().getName() +  "<br> <br>" + event.getArtist().getName() + "<br> <br>" + event.getExpectedPopularity() + "<br> <br>" + timeformatter.format(event.getStartDate().getTime()) + " - " + timeformatter.format(event.getEndDate().getTime()) + "<br> <br> </html>");
		JLabel text = new JLabel("<html> Podium: <br> <br> Artiest: <br> <br> Populariteit: <br> <br> Tijd: <br> <br> </html>");
		box2.setAlignmentX(LEFT_ALIGNMENT);
		date.setFont(new Font("Dialog", Font.BOLD, 15));
		row.setAlignmentX(LEFT_ALIGNMENT);
		date.setAlignmentX(RIGHT_ALIGNMENT);
		title.setFont(new Font("Dialog", Font.BOLD, 20));
		text.setFont(new Font("Dialog", Font.BOLD, 15));
		data.setFont(new Font("Dialog", Font.PLAIN, 15));
		JLabel description = new JLabel("<html> <b> Beschrijving </b> <br> <br>" + event.getDescription() + "</html>");
		description.setFont(new Font("Dialog", Font.PLAIN, 12));
		row.add(title);
		row.add(Box.createRigidArea(new Dimension(10,0)));
		row.add(edit);
		row.add(Box.createRigidArea(new Dimension(30,0)));
		row.add(date);
		box1.add(row);
		box1.add(new JSeparator(SwingConstants.HORIZONTAL));
		box2.add(text);
		box2.add(data);
		box3.add(new JSeparator(SwingConstants.HORIZONTAL));
		box3.add(description);
		panel.add(box1);
		panel.add(box2);
		panel.add(box3);
		add(panel, BorderLayout.CENTER);
	}
}
