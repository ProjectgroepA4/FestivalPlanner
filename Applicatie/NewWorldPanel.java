package Applicatie;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewWorldPanel extends JFrame {

	public NewWorldPanel(Panel topPanel) {
		super("New World");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		String[] terrains = {"Grass", "Sand"};
		JPanel mainPanel = new JPanel(new BorderLayout());
		JButton button;
		JLabel label;
		JTextField heightTextField;
		JTextField widthTextField;
		JComboBox terrainBox;
		
		//Top:
		JPanel panel = new JPanel();
		BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(box);
		panel.add(Box.createRigidArea(new Dimension(0,20)));
		label = new JLabel("Enter your preferences: ");
		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(0,15)));
		//Width:
			JPanel linePanel = new JPanel(); 
			label = new JLabel("Width: ");
			widthTextField = new JTextField("1920");
			widthTextField.setPreferredSize(new Dimension(100,20));
			linePanel.add(label);
			linePanel.add(widthTextField);
			panel.add(linePanel);
		//Height: 
			linePanel = new JPanel(); 
			label = new JLabel("Height: ");
			heightTextField = new JTextField("1080");
			heightTextField.setPreferredSize(new Dimension(100,20));
			linePanel.add(label);
			linePanel.add(heightTextField);
			panel.add(linePanel);
		//Terrain:
			linePanel = new JPanel();
			label = new JLabel("Terrain: ");
			terrainBox = new JComboBox(terrains);
			terrainBox.setPreferredSize(new Dimension(100,20));
			linePanel.add(label);
			linePanel.add(terrainBox);
			panel.add(linePanel);
		//Image:
			
		
		
		
		mainPanel.add(panel,BorderLayout.NORTH);
		//Bottom:
		panel = new JPanel();
		//Cancel:
		button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				NewWorldPanel.this.dispose();
			}
		});
		panel.add(button);
		
		button = new JButton("Apply");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				topPanel.newWorld(Integer.parseInt(widthTextField.getText()),Integer.parseInt(heightTextField.getText()),terrainBox.getSelectedIndex());
				NewWorldPanel.this.dispose();
			}
		});
		panel.add(button);
		
		
		mainPanel.add(panel,BorderLayout.SOUTH);
		add(mainPanel);
		
		//Finishing off
		pack();
		setSize(300,350);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
