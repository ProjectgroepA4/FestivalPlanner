import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;


public class DrawObject {
	Point2D position;
	double rotation;
	double scale;
	Image image;
	protected boolean selected;

	public DrawObject(String filename, Point2D position)
	{
		image = new ImageIcon(filename).getImage();
		scale = 1;
		rotation = 0;
		this.position = position;
	}
	
	public void draw(Graphics2D g)
	{
		AffineTransform tx = getTransform();
		g.drawImage(image, tx, null);
	}

	protected AffineTransform getTransform() {
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		tx.translate(position.getX(), position.getY());
		tx.rotate(Math.toRadians(rotation), image.getWidth(null) / 2, image.getHeight(null) / 2);
		return tx;
	}

	public boolean contains(Point point) {
		Shape shape = new Rectangle2D.Double(0,0,image.getWidth(null), image.getHeight(null));
		return getTransform().createTransformedShape(shape).contains(point);
	}
	
	public void setSelected(boolean b)
	{
		selected = b;
	}
}
