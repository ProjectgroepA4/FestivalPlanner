package Applicatie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPeoplePanel extends JFrame {

	public AddPeoplePanel(Panel topPanel) {
		super("Adding people");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setResizable(false);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		JLabel label;
		JButton button;
		
		//Top:
		JPanel panel = new JPanel();
		label = new JLabel("Enter the amount of people you want to add: ");
		panel.add(label);
		mainPanel.add(panel,BorderLayout.NORTH);
		//Center:
		panel = new JPanel();
		JTextField amountOfPeopleField = new JTextField("0");
		amountOfPeopleField.setPreferredSize(new Dimension(100,20));
		amountOfPeopleField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					amountOfPeopleField.setBackground(Color.WHITE);
					int amountOfPeople = Integer.parseInt(amountOfPeopleField.getText());
					amountOfPeopleField.setText(amountOfPeople + "");
				}
				catch(NumberFormatException nf) {
					amountOfPeopleField.setBackground(Color.RED);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		label = new JLabel("people ");
		panel.add(amountOfPeopleField);
		panel.add(label);
		mainPanel.add(panel,BorderLayout.CENTER);
		//Bottom:
		panel = new JPanel();
		button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddPeoplePanel.this.dispose();
			}
		});
		panel.add(button);
		button = new JButton("Apply");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(amountOfPeopleField.getBackground() != Color.RED) {
					topPanel.addVisitors(Integer.parseInt(amountOfPeopleField.getText()));
					AddPeoplePanel.this.dispose();
				}
				else 
					JOptionPane.showMessageDialog(topPanel, "Only numbers are accepted");
			}
		});
		panel.add(button);
		mainPanel.add(panel,BorderLayout.SOUTH);
		
		add(mainPanel);
		
		//Finishing off
		pack();
		setSize(290,150);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
