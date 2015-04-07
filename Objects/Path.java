package Objects;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Create's a path.
 * 
 * @author Wesley de Hek
 * @version 1.2
 */
public class Path
{

	private ArrayList<Point2D> points;
	private ArrayList<Shape> lines;
	private Point2D tempPoint;
	private BufferedImage pathBackground;

	/**
	 * Constructor.
	 */
	public Path()
	{
		points = new ArrayList<>();
		lines = new ArrayList<>();
		try
		{
			pathBackground = ImageIO.read(new File("images/newPath.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Draws the path.
	 * 
	 * @param g2
	 *            - the Graphics2D object.
	 */
	public void draw(Graphics2D g2)
	{
		TexturePaint paint = new TexturePaint(pathBackground, new Rectangle2D.Double(0, 0, 128, 128));
		g2.setPaint(paint);
		g2.setStroke(new BasicStroke(30, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if (!points.isEmpty())
		{
			for (int i = 1; i < points.size(); i++)
			{
				Point2D previousPoint = points.get(i - 1);
				Point2D point = points.get(i);
				g2.drawLine((int) previousPoint.getX(), (int) previousPoint.getY(), (int) point.getX(), (int) point.getY());
			}
			// Drawing line that follows the mouse:
			if (tempPoint != null)
			{
				Point2D lastPoint = points.get(points.size() - 1);
				g2.drawLine((int) lastPoint.getX(), (int) lastPoint.getY(), (int) tempPoint.getX(), (int) tempPoint.getY());
			}
		}
	}

	/**
	 * Check if one of the lines contains the given point and return the Shape.
	 * 
	 * @param point
	 *            - The point of your object.
	 * @return if the Shape contains the point.
	 */
	public Shape containsPointShape(Point2D point)
	{
		for (Shape line : getPath())
		{
			if (line.contains(point))
			{
				return line;
			}
		}
		return null;
	}
	
	/**
	 * Check if one of the lines contains the given point.
	 * 
	 * @param point
	 *            - the point you want to containment with.
	 * @return if the path contains the point.
	 */
	public boolean containsPoint(Point2D point)
	{
		for (Shape line : getPath())
		{
			if (line.contains(point))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Get array with line's from the points.
	 * 
	 * @return a array with all the lines of the path.
	 */
	public ArrayList<Shape> getPath()
	{
		lines.clear();
		for (int i = 1; i < points.size(); i++)
		{
			Point2D previousPoint = points.get(i - 1);
			Point2D point = points.get(i);
			Line2D line = new Line2D.Double((int) previousPoint.getX(), (int) previousPoint.getY(), (int) point.getX(), (int) point.getY());
			BasicStroke stroke = new BasicStroke(30, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			lines.add(stroke.createStrokedShape(line));
		}
		return lines;
	}

	/**
	 * Add a point to the path.
	 * 
	 * @param point
	 *            - the point you want the path to pass.
	 */
	public void addPoint(Point2D point)
	{
		points.add(point);
	}

	/**
	 * Get all the path points.
	 * 
	 * @return all the points of the path.
	 */
	public ArrayList<Point2D> getPoints()
	{
		return points;
	}

	/**
	 * Set the temporary point, useful for following the mouse while drawing the
	 * path.
	 * 
	 * @param point
	 *            - the point of the mouse.
	 */
	public void setTempPoint(Point2D point)
	{
		tempPoint = point;
	}
}