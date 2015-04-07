package Agenda;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AddArtistPanel extends JFrame{

	private static final long serialVersionUID = 1;

	public AddArtistPanel(Agenda agenda)
	{
		super("Artist addscreen!");
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
		JTextField nameTF = new JTextField("",15);
		panel2.add(nameTF);
		center.add(panel2);
		
		JLabel genre = new JLabel("Genre: ");
		JPanel panel3 = new JPanel();
		panel3.add(genre);
		center.add(panel3);
		
		JTextField genreTF = new JTextField("",15);
		JPanel panel4 = new JPanel();
		panel4.add(genreTF);
		center.add(panel4);
		
		JPanel panel5 = new JPanel();
		JLabel image = new JLabel("Image: ");
		panel5.add(image);
		center.add(panel5);
		
		JPanel panel6 = new JPanel();
		JTextField imageTF = new JTextField("null", 15);
		panel6.add(imageTF);
		center.add(panel6);
		
		JPanel panel7 = new JPanel();
		JLabel description = new JLabel("Description: ");
		panel7.add(description);
		center.add(panel7);
		
		JPanel panel8 = new JPanel();
		JTextField descriptionTF = new JTextField("", 15);
		panel8.add(descriptionTF);
		center.add(panel8);
		
		JPanel panel9 = new JPanel();
		JLabel background = new JLabel("Background: ");
		panel9.add(background);
		center.add(panel9);
		
		JPanel panel10 = new JPanel();
		JTextField backgroundTF = new JTextField("", 15);
		panel10.add(backgroundTF);
		center.add(panel10);

		
		
		JButton add = new JButton("Add artist!");
		south.add(add);
		
		add.addActionListener(new ActionListener()
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
					if (artist.getName().equals(name))
					{
						alreadyExists = true;
					}
				}
				if (name.isEmpty() == true){
					JOptionPane.showMessageDialog(null, "Fill in a correct name!");
				}
				else if (alreadyExists == false)
				{
				Artist a = new Artist(name,genre,image,description, background);
				agenda.addArtist(a);
				JOptionPane.showMessageDialog(null, "Artist added!");
				setVisible(false);
				dispose();
				Window.updatePanel();
				} else {
					JOptionPane.showMessageDialog(null, "Choose a different artist name!");
				}
				
			}
		});
		
		
		setContentPane(content);
		setVisible(true);
		setSize(500,320);
	}

}
