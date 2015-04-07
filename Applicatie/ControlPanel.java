package Applicatie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends JPanel
{
	private static final long serialVersionUID = 6805244250902524351L;

	private Panel p;
	
	public ControlPanel(Panel p)
	{
		super();
		BoxLayout box = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(box);
		this.p = p;
		setPreferredSize(new Dimension(0,50));
		setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
		setBackground(new Color(220, 220, 220));
		
		
		JPanel simulatorPanel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		simulatorPanel.setPreferredSize(new Dimension(350,50));
		simulatorPanel.setMaximumSize(new Dimension(350,50));
		add(simulatorPanel);
		{
			JLabel simulatorLabel = new JLabel("Simulator: ");
			simulatorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
			simulatorPanel.add(simulatorLabel);
			
			JButton playButton = new JButton("Play");
			playButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			playButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					p.getT().start();
				}
			});
			simulatorPanel.add(playButton);
			
			JButton pauseButton = new JButton("Pause");
			pauseButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			pauseButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					p.getT().stop();
				}
			});
			simulatorPanel.add(pauseButton);
			
			JButton stopButton = new JButton("Stop");
			stopButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			stopButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					p.getT().stop();
				}
			});
			simulatorPanel.add(stopButton);
		}
		
		add(Box.createRigidArea(new Dimension(10,50)));
		
		JPanel timePanel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		timePanel.setPreferredSize(new Dimension(280,50));
		timePanel.setMaximumSize(new Dimension(280,50));
		add(timePanel);
		{
			JLabel timeLabel = new JLabel("00:00 00-00-000");
			timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			timePanel.add(timeLabel);
			
			JButton dateButton = new JButton("Pick Date");
			dateButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			dateButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					///TODO implement
				}
			});
			timePanel.add(dateButton);
		}
		
		add(Box.createRigidArea(new Dimension(10,50)));
		
		JPanel peoplePanel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		peoplePanel.setPreferredSize(new Dimension(285,50));
		peoplePanel.setMaximumSize(new Dimension(285,50));
		add(peoplePanel);
		{
			JLabel peopleLabel = new JLabel("0/0 People");
			peopleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			peoplePanel.add(peopleLabel);
			
			JButton peopleButton = new JButton("Set People");
			peopleButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			peopleButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					p.addVisitors(1);
				}
			});
			peoplePanel.add(peopleButton);
		}
		
		add(Box.createHorizontalGlue());
		
		JPanel runningPanel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		runningPanel.setPreferredSize(new Dimension(195,50));
		runningPanel.setMaximumSize(new Dimension(195,50));
		add(runningPanel);
		{
			JLabel runningLabel = new JLabel("Simulation stopped");
			runningLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			runningPanel.add(runningLabel);
		}
		
	}
}