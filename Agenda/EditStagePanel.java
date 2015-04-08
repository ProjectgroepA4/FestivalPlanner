package Agenda;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class EditStagePanel extends JFrame{

	private static final long serialVersionUID = 1;

	public EditStagePanel(Agenda agenda, AgendaStage sta)
	{
		super("Stage editscreen");
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
		JTextField nameTF = new JTextField(sta.getName(),15);
		panel2.add(nameTF);
		center.add(panel2);
		
		JLabel description = new JLabel("Stage Description: ");
		JPanel panel3 = new JPanel();
		panel3.add(description);
		center.add(panel3);
		
		JTextField descriptionTF = new JTextField(sta.getDescription(),15);
		JPanel panel4 = new JPanel();
		panel4.add(descriptionTF);
		center.add(panel4);
		
		
		JButton add = new JButton("Edit stage!");
		south.add(add);
		
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				String name = nameTF.getText();
				String description = descriptionTF.getText();

				boolean alreadyExists = false;
				for (AgendaStage stage : agenda.getStages())
				{
					if (stage.getName().equals(name)  && !stage.equals(sta))
					{
						alreadyExists = true;
					}
				}
				if (name.isEmpty()){
					JOptionPane.showMessageDialog(null, "Fill in a correct name!");
				}
				else if ( alreadyExists == false )
				{
					sta.setName(name);
					sta.setDescription(description);
					JOptionPane.showMessageDialog(null, "Stage edited!");
					setVisible(false);
					dispose();
					Window.updatePanel();
				}
				else {
					JOptionPane.showMessageDialog(null, "Choose a different stage name!");				
				}
				
			}
		});
		
		JButton remove = new JButton("Remove stage!");
		for(Event e : agenda.getEvents())
		{
			if(e.getStage().equals(sta))
			{
				remove.setEnabled(false);
			}
		}
		south.add(remove);
		
		remove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this stage?", "Remove Stage", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
				{
					Iterator<AgendaStage> it = agenda.getStages().iterator();
					while(it.hasNext())
					{
						AgendaStage s = it.next();
						if(s.equals(sta))
						{
							it.remove();
						}
					}
					
					JOptionPane.showMessageDialog(null, "Stage removed!");
					setVisible(false);
					dispose();
					Window.updatePanel();
				}
			}
		});
		
		
		setContentPane(content);
		setVisible(true);
		setSize(400,180);
	}

}
