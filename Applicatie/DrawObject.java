package Applicatie;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
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
	private Rectangle2D rektAngle;

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
		if(selected) {
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(7));
			rektAngle = new Rectangle2D.Double((int) position.getX(), (int) position.getY(), image.getWidth(null), image.getHeight(null));
			tx = getTransformRectangle(rektAngle.getWidth(),rektAngle.getHeight());
			Shape transformedRektAngle = tx.createTransformedShape(rektAngle);
			g.draw(transformedRektAngle);
		}
	}

	protected AffineTransform getTransform() {
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		tx.translate(position.getX(), position.getY());
		tx.rotate(Math.toRadians(rotation), image.getWidth(null) / 2, image.getHeight(null) / 2);
		return tx;
	}

	/**
	 * Special transformation for the selection rectangle, because the translation of the image one places the rectangle translated from the image.
	 * @param width
	 * @param height
	 * @return
	 */
	private AffineTransform getTransformRectangle(double width, double height) {
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		tx.rotate(Math.toRadians(rotation), width / 2, height / 2);
		return tx;
	}
	
	public boolean contains(Point2D clickPoint) {
		Shape shape = new Rectangle2D.Double(0,0,image.getWidth(null), image.getHeight(null));
		return getTransform().createTransformedShape(shape).contains(clickPoint);
	}
	
	public void setSelected(boolean b)
	{
		selected = b;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isSelected() {
		return selected;
	}
	
	public Rectangle2D getRectangle() {
		return rektAngle;
	}
	
}
