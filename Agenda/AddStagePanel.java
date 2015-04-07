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


public class AddStagePanel extends JFrame{

	private static final long serialVersionUID = 1;

	public AddStagePanel(Agenda agenda)
	{
		super("Stage addscreen!");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel content = new JPanel(new BorderLayout());
		JPanel south = new JPanel(new FlowLayout());
		JPanel center = new JPanel(new GridLayout(2,2));
		content.add(south, BorderLayout.SOUTH);
		content.add(center, BorderLayout.CENTER);
		
		JPanel panel1 = new JPanel();
		JLabel name = new JLabel("Stage name: ");
		panel1.add(name);
		center.add(panel1);
		
		JPanel panel2 = new JPanel();
		JTextField nameTF = new JTextField("",15);
		panel2.add(nameTF);
		center.add(panel2);
		
		JLabel description = new JLabel("Stage Description: ");
		JPanel panel3 = new JPanel();
		panel3.add(description);
		center.add(panel3);
		
		JTextField descriptionTF = new JTextField("",15);
		JPanel panel4 = new JPanel();
		panel4.add(descriptionTF);
		center.add(panel4);
		
		
		JButton add = new JButton("Add stage!");
		south.add(add);
		
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				String name = nameTF.getText();
				String description = descriptionTF.getText();

				boolean alreadyExists = false;
				for (Stage stage : agenda.getStages())
				{
					if (stage.getName().equals(name))
					{
						alreadyExists = true;
					}
				}
				if (name.isEmpty()){
					JOptionPane.showMessageDialog(null, "Fill in a correct name!");
				}
				else if ( alreadyExists == false )
				{
					Stage s = new Stage(name,description);
					agenda.addStage(s);
					JOptionPane.showMessageDialog(null, "Stage added!");
					setVisible(false);
					dispose();
					Window.updatePanel();
				}
				else {
					JOptionPane.showMessageDialog(null, "Choose a different stage name!");				
				}
				
			}
		});
		
		
		setContentPane(content);
		setVisible(true);
		setSize(400,180);
	}

}
