package Applicatie;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Objects.DrawObject;

public class WaypointPopup extends JFrame {

	private static final long serialVersionUID = 1;

	public WaypointPopup(DrawObject w)
	{
		super("Artist editscreen!");
		Waypoint wp = (Waypoint) w;
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel content = new JPanel(new BorderLayout());
		JPanel south = new JPanel(new FlowLayout());
		JPanel center = new JPanel(new GridLayout(5,2));
		content.add(south, BorderLayout.SOUTH);
		content.add(center, BorderLayout.CENTER);
		
		JPanel panel1 = new JPanel();
		JLabel name = new JLabel("Waypoint");
		panel1.add(name);
		center.add(panel1);
		
		JTextField tf = new JTextField(5);
		center.add(tf);
		
		JButton edit = new JButton("Done");
		edit.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				wp.setSelf(Integer.valueOf(tf.getText()));
				dispose();
			}
		});
		south.add(edit);
		
		setContentPane(content);
		setVisible(true);
		setSize(500,320);
	}
	
}
