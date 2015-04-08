package Applicatie;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NewWorldPanel extends JFrame {

	BufferedImage grass,sand,grass2, sand2,stone, selectedImage;
	
	public NewWorldPanel(Panel topPanel) {
		super("New World");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setResizable(false);
		
		try {
			grass = ImageIO.read(new File("images/grassIcon.jpg"));
			sand = ImageIO.read(new File("images/sandIcon.jpg"));
			grass2 = ImageIO.read(new File("images/grassIcon2.png"));
			sand2 = ImageIO.read(new File("images/sandIcon2.jpg"));
			stone = ImageIO.read(new File("images/stoneIcon.jpg"));
			selectedImage = grass;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		String[] terrains = {"Grass", "Grass 2","Sand", "Sand 2", "Stone"};
		JPanel mainPanel = new JPanel(new BorderLayout());
		JButton button;
		JLabel label;
		JTextField heightTextField;
		JTextField widthTextField;
		JComboBox terrainBox;
		JLabel imageLabel;
		
		//Top:
		JPanel panel = new JPanel();
		BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(box);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		label = new JLabel("  Enter your preferences: ");
		linePanel.add(label);
		panel.add(linePanel);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		//Width:
			linePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			label = new JLabel("Width: ");
			widthTextField = new JTextField("1920");
			widthTextField.setPreferredSize(new Dimension(100,20));
			linePanel.add(label);
			linePanel.add(widthTextField);
			panel.add(linePanel);
		//Height: 
			linePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
			label = new JLabel("Height: ");
			heightTextField = new JTextField("1080");
			heightTextField.setPreferredSize(new Dimension(100,20));
			linePanel.add(label);
			linePanel.add(heightTextField);
			panel.add(linePanel);
		//Terrain:
			linePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			label = new JLabel("Terrain: ");
			terrainBox = new JComboBox(terrains);
			terrainBox.setPreferredSize(new Dimension(100,20));
			imageLabel = new JLabel(new ImageIcon(selectedImage));
			terrainBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					switch(terrainBox.getSelectedIndex()) {
						case 0:
							selectedImage = grass;
							break;
						case 1:
							selectedImage = grass2;
							break;
						case 2:
							selectedImage = sand;
							break;
						case 3:
							selectedImage = sand2;
							break;
						case 4:
							selectedImage = stone;
							break;
					}
					imageLabel.setIcon(new ImageIcon(selectedImage));
				}
			});
			linePanel.add(label);
			linePanel.add(terrainBox);
			panel.add(linePanel);
		//Image:
			linePanel = new JPanel();
			linePanel.add(imageLabel);
			panel.add(linePanel);
		
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
		setSize(290,330);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
