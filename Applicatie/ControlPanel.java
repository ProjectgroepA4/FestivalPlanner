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
	private int people;
	JLabel timeLabel;
	JLabel peopleLabel;
	JLabel runningLabel;
	
	public ControlPanel()
	{
		super();
		BoxLayout box = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(box);
		people = 0;
		
		setPreferredSize(new Dimension(0,50));
		setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
		setBackground(new Color(220, 220, 220));
		setFocusable(false);
		
		JPanel simulatorPanel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		simulatorPanel.setPreferredSize(new Dimension(290,50));
		simulatorPanel.setMaximumSize(new Dimension(290,50));
		add(simulatorPanel);
		{
			JLabel simulatorLabel = new JLabel("Simulator: ");
			simulatorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
			simulatorPanel.add(simulatorLabel);
			
			JButton playButton = new JButton("Play");
			playButton.setFocusable(false);
			playButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			playButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					p.getT().start();
					p.setPlay(true);
					runningLabel.setText("Simulation Started");
				}
			});
			simulatorPanel.add(playButton);
			
			JButton stopButton = new JButton("Stop");
			stopButton.setFocusable(false);
			stopButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			stopButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					p.getT().stop();
					p.setPlay(false);
					runningLabel.setText("Simulation Stopped");
				}
			});
			simulatorPanel.add(stopButton);
		}
		
		add(Box.createRigidArea(new Dimension(10,50)));
		
		JPanel timePanel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		timePanel.setPreferredSize(new Dimension(300,50));
		timePanel.setMaximumSize(new Dimension(300,50));
		add(timePanel);
		{
			timeLabel = new JLabel("00:00 00-00-000");
			timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			timePanel.add(timeLabel);
			
			JButton dateButton = new JButton("Pick Date");
			dateButton.setFocusable(false);
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
			peopleLabel = new JLabel(people + " People");
			peopleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			peoplePanel.add(peopleLabel);
			
			JButton peopleButton = new JButton("Set People");
			peopleButton.setFocusable(false);
			peopleButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			peopleButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					new AddPeoplePanel(p);
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
			runningLabel = new JLabel("Simulation stopped");
			runningLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			runningPanel.add(runningLabel);
		}
		
	}
	
	public void setPanel(Panel p)
	{
		this.p = p;
	}
	
	public void setTime(String time)
	{
		timeLabel.setText(time);
	}
	
	public void setPeople(int people)
	{
		this.people = people;
		peopleLabel.setText(people + " People");
	}
}
