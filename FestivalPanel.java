import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class FestivalPanel extends JPanel {
	
	BufferedImage background;
	ArrayList<DrawObject> objects = new ArrayList<>();
	DrawObject dragObject = null;
	DrawObject selectedObject = null;
	Point2D dragOffset;
	
	FestivalPanel()
	{
		
		try {
			background = ImageIO.read(new File("images/grass.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e) {
				for(DrawObject o : objects){
					o.setSelected(false);
					if(o.contains(e.getPoint()))
					{
						dragObject = o;
						selectedObject = o;
						selectedObject.setSelected(true);
						dragOffset = new Point2D.Double(e.getX() - o.position.getX(), e.getY() - o.position.getY());
					}
				}
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				dragObject = null;
				dragOffset = null;
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if(dragObject != null)
				{
					if(SwingUtilities.isLeftMouseButton(e))
						dragObject.position = new Point2D.Double(e.getX() - dragOffset.getX(), e.getY() - dragOffset.getY());
					else
						dragObject.rotation = e.getX();
					repaint();
				}
			}
			
		});
		
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
	
		TexturePaint p = new TexturePaint(background, new Rectangle2D.Double(0, 0, 100, 100));
		g2.setPaint(p);
		g2.fill(new Rectangle2D.Double(0,0,getWidth(), getHeight()));
		
		for(DrawObject o : objects)
			o.draw(g2);
		
	}
	
	public void add(DrawObject o)
	{
		objects.add(o);
		repaint();
	}

}
