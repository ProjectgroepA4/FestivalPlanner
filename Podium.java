import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Podium extends DrawObject {
	
	Image selection = null;
	
	public Podium(int num, Point2D position) {
		super("images/stage" + num + ".png", position);
		try {
			selection = ImageIO.read(new File("images/selection.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g)
	{
		super.draw(g);
		if(selected)
		{
			//Rectangle2D rect = new Rectangle2D.Double(0,0,super.image.getWidth(null), super.image.getHeight(null));
			//Shape sh = super.getTransform().createTransformedShape(rect);
			//g.setPaint(new Color(0,0,0));
			//g.draw(sh);
			g.drawImage(selection, super.getTransform(), null);
		}
		
	}
	
}
