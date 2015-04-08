package Agenda;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditArtistPanel extends JFrame {

	private static final long serialVersionUID = 1;

	public EditArtistPanel(Agenda agenda, Artist art, TabbedPane tab)
	{
		super("Artist editscreen!");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel content = new JPanel(new BorderLayout());
		JPanel south = new JPanel(new FlowLayout());
		JPanel center = new JPanel(new GridLayout(5,2));
		content.add(south, BorderLayout.SOUTH);
		content.add(center, BorderLayout.CENTER);
		
		JPanel panel1 = new JPanel();
		JLabel name = new JLabel("Artist name: ");
		panel1.add(name);
		center.add(panel1);
		
		JPanel panel2 = new JPanel();
		JTextField nameTF = new JTextField(art.getName(),15);
		panel2.add(nameTF);
		center.add(panel2);
		
		JLabel genre = new JLabel("Genre: ");
		JPanel panel3 = new JPanel();
		panel3.add(genre);
		center.add(panel3);
		
		JTextField genreTF = new JTextField(art.getGenre(),15);
		JPanel panel4 = new JPanel();
		panel4.add(genreTF);
		center.add(panel4);
		
		JPanel panel5 = new JPanel();
		JLabel image = new JLabel("Image: ");
		image.setVisible(false);
		panel5.add(image);
		center.add(panel5);
		
		JPanel panel6 = new JPanel();
		JTextField imageTF = new JTextField("null", 15);
		imageTF.setVisible(false);
		panel6.add(imageTF);
		center.add(panel6);
		
		JPanel panel7 = new JPanel();
		JLabel description = new JLabel("Description: ");
		panel7.add(description);
		center.add(panel7);
		
		JPanel panel8 = new JPanel();
		JTextField descriptionTF = new JTextField(art.getDescription(), 15);
		panel8.add(descriptionTF);
		center.add(panel8);

		JPanel panel9 = new JPanel();
		JLabel background = new JLabel("Background: ");
		panel9.add(background);
		center.add(panel9);
		
		JPanel panel10 = new JPanel();
		JTextField backgroundTF = new JTextField(art.getBackground(), 15);
		panel10.add(backgroundTF);
		center.add(panel10);

		
		
		JButton edit = new JButton("Edit artist!");
		south.add(edit);
		
		edit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				String name = nameTF.getText();
				String genre = genreTF.getText();
				String image = imageTF.getText();
				String description = descriptionTF.getText();
				String background = backgroundTF.getText();
				
				boolean alreadyExists = false;
				for (Artist artist : agenda.getArtists())
				{
					if (artist.getName().equals(name) && !artist.equals(art))
					{
						alreadyExists = true;
					}
				}
				if (name.isEmpty() == true){
					JOptionPane.showMessageDialog(null, "Fill in a correct name!");
				}
				else if (alreadyExists == false)
				{
					art.setName(name);
					art.setGenre(genre);
					art.setImageIconString(image);
					art.setDescription(description);
					art.setBackground(background);
					JOptionPane.showMessageDialog(null, "Artist edited!");
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
						if (event.getArtist().getName().equals(name))
						{
							eventartist = event;
						}
					}
					TabbedPane panel = new TabbedPane(agenda, eventartist, false);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Choose a different artist name!");
				}
				
			}
		});
		
		JButton remove = new JButton("Remove artist!");
		for(Event e : agenda.getEvents())
		{
			if(e.getArtist().equals(art))
			{
				remove.setEnabled(false);
			}
		}
		south.add(remove);
		
		remove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this artist?", "Remove Artist", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
				{
					Iterator<Artist> it = agenda.getArtists().iterator();
					while(it.hasNext())
					{
						Artist a = it.next();
						if(a.equals(art))
						{
							it.remove();
						}
					}
					
					JOptionPane.showMessageDialog(null, "Artist removed!");
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
		setSize(500,320);
	}
	
}
