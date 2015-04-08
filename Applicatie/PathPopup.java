package Applicatie;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Objects.Path;

public class PathPopup extends JFrame
{

	private static final long serialVersionUID = 1;

	public PathPopup(Path p, Panel panel)
	{
		super("");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		JPanel south = new JPanel(new FlowLayout());
		JPanel center = new JPanel();
		JPanel panel1 = new JPanel();
		JLabel name = new JLabel("Add WayPoint");
		name.setFont(new Font("Dialog", Font.BOLD, 20));
		panel1.add(name);
		content.add(panel1);
		content.add(center);
		content.add(south);

		JTextField tf = new JTextField(10);
		center.add(tf);

		JButton edit = new JButton("Done");
		edit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				p.addWaypoint(Integer.valueOf(tf.getText()), panel);
				dispose();
			}
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		south.add(edit);
		south.add(cancel);
		
		setContentPane(content);
		setVisible(true);
		setSize(200, 150);
	}

}
