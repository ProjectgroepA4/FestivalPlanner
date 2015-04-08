package Objects;

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
import java.io.Serializable;

import javax.swing.ImageIcon;

import Applicatie.Images;
import Applicatie.Panel;

public abstract class DrawObject implements Serializable
{
	Point2D position;
	double rotation;
	double scale;
	int width;
	int height;
//	private Image image;
	protected boolean selected;
	private String filename;

	protected int areaTop;
	protected int areaBottom;
	protected int areaLeft;
	protected int areaRight;

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
	private Color rectangleColor;

	public DrawObject(String filename, Point2D position)
	{
//		image = new ImageIcon(filename).getImage();

		Image image = Images.getImage(filename);
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.filename = filename;
		scale = 1;
		rotation = 0;
		this.position = position;
		rectangleColor = Color.BLACK;

		areaTop = 0;
		areaBottom = 0;
		areaLeft = 0;
		areaRight = 0;
	}

	public abstract String getName();

	public void draw(Graphics2D g)
	{
		AffineTransform tx = getTransform();
		Image image = Images.getImage(filename);
		g.drawImage(image, tx, null);
		if (selected)
		{
			// g.rotate(Math.toRadians(rotation), width/2,
			// height/2);
			AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(rotation), position.getX() + image.getWidth(null) / 2, position.getY() + image.getHeight(null) / 2);
			// g.transform(rotate);
			g.setColor(rectangleColor);
			g.setStroke(new BasicStroke(7));
			rektAngle = new Rectangle2D.Double((int) position.getX() - areaLeft, (int) position.getY() - areaTop, width + areaLeft + areaRight, height + areaTop + areaBottom);
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
		tx.rotate(Math.toRadians(rotation), (width + areaLeft + areaRight) / 2, (height + areaTop + areaBottom) / 2);
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
			shape = new Rectangle2D.Double(0 - areaLeft, 0 - areaTop, width + areaLeft + areaRight, height + areaTop + areaBottom);
			return getTransform().createTransformedShape(shape).contains(clickPoint);

		}
		else
		{
			shape = new Rectangle2D.Double(-9 - areaLeft, -9 - areaTop, width + 13 + areaLeft + areaRight, height + 13 + areaTop + areaBottom);
			return getTransformSelection().createTransformedShape(shape).contains(clickPoint);
		}

	}

	public Point2D getObjectPoint(Point2D point)
	{
		try
		{
			return getTransform().inverseTransform(point, null);

		}
		catch (NoninvertibleTransformException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checking collision.
	 * 
	 * @param object
	 *            . the drawObject to check collision with.
	 * @return if there is a collision.
	 */
	public boolean collision(DrawObject object)
	{
		Shape ownShape = getRectangle();
		Rectangle2D otherRectangle = object.getTransform().createTransformedShape(object.getImageRectangle()).getBounds2D();
		if(ownShape != null && otherRectangle != null) {
			if (ownShape.intersects(otherRectangle))
				return true;
			else
				return false;
		}
		else
			return false;
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
		if(position.getX() < -Panel.getFieldWidth()/2)
		{
			position.setLocation(-Panel.getFieldWidth()/2, position.getY());
		}
		if(position.getY() < -Panel.getFieldHeight()/2)
		{
			position.setLocation(position.getX(), -Panel.getFieldHeight()/2);
		}
		if(position.getX() > Panel.getFieldWidth()/2 - width)
		{
			position.setLocation(Panel.getFieldWidth()/2-width, position.getY());
		}
		if(position.getY() > Panel.getFieldHeight()/2 - height)
		{
			position.setLocation(position.getX(), Panel.getFieldHeight()/2-height);
		}
		this.position = position;
	}
	public void setPosition(Point2D position, boolean b)
	{
		setPosition(position);
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

	public Color getRectangleColor()
	{
		return rectangleColor;
	}

	public void setRectangleColor(Color rectangleColor)
	{
		this.rectangleColor = rectangleColor;
	}

	public Rectangle2D getImageRectangle()
	{
		Image image = Images.getImage(filename);
		return new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null));
	}

	public int getAreaTop()
	{
		return areaTop;
	}

	public void setAreaTop(int areaTop)
	{
		this.areaTop = areaTop;
	}

	public int getAreaBottom()
	{
		return areaBottom;
	}

	public void setAreaBottom(int areaBottom)
	{
		this.areaBottom = areaBottom;
	}

	public int getAreaLeft()
	{
		return areaLeft;
	}

	public void setAreaLeft(int areaLeft)
	{
		this.areaLeft = areaLeft;
	}

	public int getAreaRight()
	{
		return areaRight;
	}

	public void setAreaRight(int areaRight)
	{
		this.areaRight = areaRight;
	}

	public int getEndX()
	{
		Image image = Images.getImage(filename);
		return (int) (position.getX() + image.getWidth(null));
	}

	public int getEndY()
	{
		Image image = Images.getImage(filename);
		return (int) (position.getY() + image.getHeight(null));
	}

	public String getFileName()
	{
		return filename;
	}

}
