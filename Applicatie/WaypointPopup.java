package Applicatie;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Objects.DrawObject;

public class WaypointPopup extends JFrame {

	private static final long serialVersionUID = 1;
	private JLabel title;

	public WaypointPopup(DrawObject w, Panel panel)
	{
		super("Edit WayPoint");
		Waypoint wp = (Waypoint) w;
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel content = new JPanel(new BorderLayout());
		JPanel north = new JPanel();
		if(wp.getSelf() == 0)
		{
			title = new JLabel("Edit WayPoint");
		}
		else
		{
			title = new JLabel("Edit WayPoint " + wp.getSelf());
		}
		title.setFont(new Font("Dialog", Font.BOLD, 24));
		north.add(title);
		content.add(north, BorderLayout.NORTH);
		JPanel center = new JPanel(new FlowLayout());
		JPanel row1 = new JPanel();
		row1.setLayout(new BoxLayout(row1, BoxLayout.Y_AXIS));
		JPanel row2 = new JPanel();
		row2.setLayout(new BoxLayout(row2, BoxLayout.Y_AXIS));
		JPanel south = new JPanel();
		content.add(south, BorderLayout.SOUTH);
		content.add(center, BorderLayout.CENTER);
		center.add(row1);
		center.add(row2);
		
		JPanel empty = new JPanel();
		empty.add(Box.createRigidArea(new Dimension(0, 20)));
		row2.add(empty);
		
		JPanel panel1 = new JPanel();
		JLabel name = new JLabel("Waypoint");
		name.setFont(new Font("Dialog", Font.PLAIN, 20));
		panel1.add(name);
		row1.add(panel1);
		
		JTextField tf = new JTextField(wp.getSelf() + "", 10);
		row2.add(tf);
		
		JPanel panel2 = new JPanel();
		JLabel options = new JLabel("Options");
		options.setFont(new Font("Dialog", Font.PLAIN, 20));
		panel2.add(options);
		row1.add(panel2);
		
		JPanel empty2 = new JPanel();
		empty2.add(Box.createRigidArea(new Dimension(0, 5)));
		row2.add(empty2);
		
		String optionss = "";
		if(wp.getOptions() != null)
		{
			if(wp.getOptions().length != 0)
			{
				int[] optionsa = wp.getOptions();
				optionss += optionsa[0];
				for(int i = 1; i < optionsa.length; i++)
				{
					optionss += "-" + optionsa[i];
				}
			}
		}
		
		JTextField tx = new JTextField("" + optionss, 8);
		row2.add(tx);
		
		JPanel help = new JPanel();
		JLabel example = new JLabel("example: '1-2-3'");
		example.setFont(new Font("Dialog", Font.PLAIN, 12));
		help.add(example);
		row2.add(help);
		
		JButton edit = new JButton("Done");
		edit.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				wp.setSelf(Integer.valueOf(tf.getText()));
				panel.addWaypoint(wp);
				dispose();
				String options[] = tx.getText().split("-");
				int ioptions[] = new int[options.length];
				for(int i = 0; i < options.length; i++)
				{
					ioptions[i] = Integer.valueOf(options[i]);
				}
				wp.setOptions(ioptions);
				
			}
		});
		south.add(edit);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		south.add(cancel);
		
		setContentPane(content);
		setVisible(true);
		setSize(300,250);
	}
	
}
