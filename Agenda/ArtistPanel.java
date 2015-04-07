package Agenda;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class ArtistPanel extends JPanel{

	public ArtistPanel(Event event, Agenda a, TabbedPane tab)
	{
		super(new BorderLayout());
		JLabel name = new JLabel(event.getArtist().getName());
		JButton edit = new JButton("Edit Info");
		edit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				EditArtistPanel editter = new EditArtistPanel(a, event.getArtist(), tab);
			}
		});
		name.setFont(new Font("Dialog", Font.BOLD, 20));
		JLabel description = new JLabel("<html>" + " <b> Genre: </b> " + event.getArtist().getGenre() + "<br> <br> <b> Beschrijving </b> <br> <br>" + event.getArtist().getDescription() + " <br> <br> <b> achtergrondinfo </b> <br> <br>" + event.getArtist().getBackground() + "</html>");
		description.setFont(new Font("Dialog", Font.PLAIN, 12));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
		panel2.setAlignmentX(LEFT_ALIGNMENT);
		JLabel photo = new JLabel(event.getArtist().getImage());
		panel2.add(name);
		panel2.add(Box.createRigidArea(new Dimension(10,0)));
		panel2.add(edit);
		panel.add(panel2);
		panel.add(new JSeparator(SwingConstants.HORIZONTAL));
		add(photo, BorderLayout.EAST);
		add(description, BorderLayout.CENTER);
		add(panel, BorderLayout.NORTH);
	}
	
}
