package Applicatie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public abstract class DrawObject
{
	Point2D position;
	double rotation;
	double scale;
	private Image image;
	private Image rotateImage;
	protected boolean selected;
	private Rectangle2D rektAngle;
	private Rectangle2D upperLeftCorner;
	private Rectangle2D upperRightCorner;
	private Rectangle2D bottomLeftCorner;
	private Rectangle2D bottomRightCorner;
	private Ellipse2D rotateDot;
	static final int UPPERLEFT = 0;
	static final int UPPERRIGHT = 1;
	static final int BOTTOMLEFT = 2;
	static final int BOTTOMRIGHT = 3;

	public DrawObject(String filename, Point2D position)
	{
		image = new ImageIcon(filename).getImage();
		rotateImage = new ImageIcon("rotate.png").getImage();
		scale = 1;
		rotation = 0;
		this.position = position;
	}

	public abstract String getName();

	public void draw(Graphics2D g)
	{
		AffineTransform tx = getTransform();
		g.drawImage(image, tx, null);
		if (selected)
		{
			// g.rotate(Math.toRadians(rotation), image.getWidth(null)/2,
			// image.getHeight(null)/2);

			AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(rotation), position.getX() + image.getWidth(null) / 2, position.getY() + image.getHeight(null) / 2);
			g.transform(rotate);
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(7));
			rektAngle = new Rectangle2D.Double((int) position.getX(), (int) position.getY(), image.getWidth(null), image.getHeight(null));
			tx = getTransformRectangle();
			rektAngle.setFrame(tx.createTransformedShape(rektAngle).getBounds());
			g.draw(rektAngle);
			g.setColor(Color.YELLOW);
			upperLeftCorner = new Rectangle2D.Double(rektAngle.getX() - 7, rektAngle.getY() - 7, 15, 15);
			upperRightCorner = new Rectangle2D.Double(rektAngle.getX() + rektAngle.getWidth() - 8, rektAngle.getY() - 7, 15, 15);
			bottomLeftCorner = new Rectangle2D.Double(rektAngle.getX() - 7, rektAngle.getY() + rektAngle.getHeight() - 8, 15, 15);
			bottomRightCorner = new Rectangle2D.Double(rektAngle.getX() + rektAngle.getWidth() - 8, rektAngle.getY() + rektAngle.getHeight() - 8, 15, 15);
			g.fill(upperLeftCorner); // upperleft
			g.fill(upperRightCorner); // upperright
			g.fill(bottomLeftCorner); // bottomleft
			g.fill(bottomRightCorner); // bottomright
			g.setColor(Color.RED);
			rotateDot = new Ellipse2D.Double((rektAngle.getX() + (rektAngle.getWidth() / 2)) - 8, rektAngle.getY() - 8, 15, 15);
			g.fill(rotateDot);

		}
	}

	protected AffineTransform getTransform()
	{
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		tx.translate(position.getX(), position.getY());
		tx.rotate(Math.toRadians(rotation), image.getWidth(null) / 2, image.getHeight(null) / 2);
		return tx;
	}

	protected AffineTransform getTransformSelection()
	{
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		tx.translate(position.getX(), position.getY());
		return tx;
	}

	/**
	 * Special transformation for the selection rectangle, because the
	 * translation of the image one places the rectangle translated from the
	 * image.
	 * 
	 * @return affineTransform
	 */
	private AffineTransform getTransformRectangle()
	{
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		return tx;
	}

	public boolean contains(Point2D clickPoint)
	{
		Shape shape;
		if (rektAngle == null)
		{
			shape = new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null));
			return getTransform().createTransformedShape(shape).contains(clickPoint);

		} else
		{
			shape = new Rectangle2D.Double(-9, -9, image.getWidth(null) + 13, image.getHeight(null) + 13);

			return getTransformSelection().createTransformedShape(shape).contains(clickPoint);
		}

	}

	public Point2D getObjectPoint(Point2D point)
	{
		try
		{
			return getTransform().inverseTransform(point, null);

		} catch (NoninvertibleTransformException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method checks if the place thats clicked is inside one of the
	 * corners.
	 * 
	 * @param point
	 * @param corner
	 * @return if the clicked point is inside one of the corners
	 */
	public boolean containsCorner(Point2D point, int corner)
	{
		Rectangle2D chosenCorner = null;
		switch (corner)
		{
			case 0:
				chosenCorner = upperLeftCorner;
				break;
			case 1:
				chosenCorner = upperRightCorner;
				break;
			case 2:
				chosenCorner = bottomLeftCorner;
				break;
			case 3:
				chosenCorner = bottomRightCorner;
				break;
			case 4:
				if (point.getX() > rotateDot.getX() && point.getX() < rotateDot.getX() + rotateDot.getWidth() && point.getY() > rotateDot.getY() && point.getY() < rotateDot.getY() + rotateDot.getHeight())
					return true;
				else
					return false;

		}
		if (point.getX() > chosenCorner.getX() && point.getX() < chosenCorner.getX() + chosenCorner.getWidth() && point.getY() > chosenCorner.getY() && point.getY() < chosenCorner.getY() + chosenCorner.getHeight())
			return true;
		else
			return false;
	}

	public void setSelected(boolean b)
	{
		selected = b;
	}

	public Point2D getPosition()
	{
		return position;
	}

	public void setPosition(Point2D position)
	{
		this.position = position;
	}

	public double getRotation()
	{
		return rotation;
	}

	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}

	public double getScale()
	{
		return scale;
	}

	public void setScale(double scale)
	{
		this.scale = scale;
	}

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public Rectangle2D getRectangle()
	{
		return rektAngle;
	}

	public void setRectangle(Rectangle2D rectangle)
	{
		rektAngle = rectangle;
	}

}
