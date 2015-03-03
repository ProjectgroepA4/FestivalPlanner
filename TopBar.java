import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class TopBar extends JPanel {
	
	public TopBar(FestivalPanel fp)
	{
		super(new GridLayout(1,4));
		setPreferredSize(new Dimension(0, 150));
		
		JButton addStage0 = new JButton(new ImageIcon("images/stage0.png"));
		addStage0.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fp.add(new Podium(0, new Point2D.Double(0,0)));
			}
		});
		add(addStage0);
		
		JButton addStage1 = new JButton(new ImageIcon("images/stage1.png"));
		addStage1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fp.add(new Podium(1, new Point2D.Double(0,0)));
			}
		});
		add(addStage1);
		
		JButton addStage2 = new JButton(new ImageIcon("images/stage2.png"));
		addStage2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fp.add(new Podium(2, new Point2D.Double(0,0)));
			}
		});
		add(addStage2);
		
		JButton addStage3 = new JButton(new ImageIcon("images/stage3.png"));
		addStage3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fp.add(new Podium(3, new Point2D.Double(0,0)));
			}
		});
		add(addStage3);
	}
}
