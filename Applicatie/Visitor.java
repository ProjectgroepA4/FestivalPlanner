package Applicatie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Agenda.Agenda;
import Agenda.Event;
import Objects.DrawObject;
import Objects.Path;

public class Visitor
{

	Point2D position;
	double rotation;
	double scale;
	double speed;
	String filename;
	ArrayList<Action> actions;
	Agenda agenda;
	ArrayList<DrawObject> objects;
	char target;

	public Visitor(String filename, Point2D position, Agenda agenda, ArrayList<DrawObject> objects)
	{
		this.position = position;
		this.filename = filename;
		this.rotation = 0;
		this.speed = 1 + Math.random() * 4;
		scale = 1;
		actions = new ArrayList<Action>();
		this.agenda = agenda;
		this.objects = objects;
		fillActions();
		target = 'z';
	}

	public void draw(Graphics2D g2)
	{
		AffineTransform tx = getTransform();
		Image image = Images.getImage(filename);
		g2.drawImage(image, tx, null);
	}

	private AffineTransform getTransform()
	{
		Image image = Images.getImage(filename);
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		tx.translate(position.getX(), position.getY());
		tx.rotate(rotation, image.getWidth(null) / 2, image.getHeight(null) / 2);
		return tx;
	}

	public void update(ArrayList<DrawObject> objects, int currentTime, ArrayList<Visitor> visitors, ArrayList<Path> paths)
	{
		Point2D target = new Point(500, 500);
		for (Action a : actions)
		{
			if (currentTime >= a.getStartTime() && currentTime < a.getStoptime())
			{
				target = a.getPosition();
			}

		}
		
		moveToTarget(target, objects, visitors, paths);
	}

	public void moveToTarget(Point2D target, ArrayList<DrawObject> objects, ArrayList<Visitor> visitors, ArrayList<Path> paths)
	{
		Point2D tar = target;
		boolean hasPathBelow = false;
		for (Path p : paths)
		{
			if (p.containsPoint(position))
			{
				hasPathBelow = true;
				break;
			}
			Shape containsShape = p.containsPointShape(position);
			if(containsShape != null)
			{
				tar = new Point2D.Double(containsShape.getBounds().getCenterX(), containsShape.getBounds().getCenterY());
			}
		}

		if (hasPathBelow)
		{
			double newRot = Math.atan2(tar.getY() - position.getY(), tar.getX() - position.getX());

			int difx = (int) (tar.getX() - position.getX());
			int dify = (int) (tar.getY() - position.getY());
			int distance = (int) Math.sqrt((difx * difx) + (dify * dify));

			if (rotation > newRot && distance > 10)
			{
				rotation -= 0.15;
			}
			else if (rotation < newRot && distance > 10)
			{
				rotation += 0.15;
			}

			Point2D oldPosition = position;

			// face direction
			float directionX = (float) Math.cos(rotation);
			float directionY = (float) Math.sin(rotation);

			if (distance > 10)
			{
				position = new Point2D.Double((position.getX() + directionX * speed), (position.getY() + directionY * speed));
			}

			boolean possible = true;
			for (DrawObject object : objects)
			{
				if (hitTest(object))
				{
					possible = false;
				}
			}
			for (Visitor object : visitors)
			{
				if (hitTestVisitor(object) && object != this)
				{
					possible = false;
				}
			}
			if (possible == false)
			{
				position = oldPosition;
				rotation += 0.2;
			}
		}
		else
		{
			HashMap<Double, Shape> values = new HashMap<Double, Shape>();
			for (Path p : paths)
			{
				for (Shape s : p.getPath())
				{
					Rectangle b = s.getBounds();
					double maxx = b.getMaxX();
					double minx = b.getMinX();
					double maxy = b.getMaxY();
					double miny = b.getMinY();

					Point2D.Double[] points = new Point2D.Double[4];
					points[0] = new Point2D.Double(minx, miny);
					points[1] = new Point2D.Double(minx, maxy);
					points[2] = new Point2D.Double(maxx, miny);
					points[3] = new Point2D.Double(maxx, maxy);

					Double lastdistance = null;
					for (Point2D.Double point : points)
					{
						double distance = Point2D.distance(point.getX(), point.getY(), position.getX(), position.getY());
						if (lastdistance == null)
						{
							lastdistance = distance;
							continue;
						}
						if (distance < lastdistance)
						{
							lastdistance = distance;
						}
					}
					values.put(lastdistance, s);

				}
			}
			Double lastdistance = null;
			Shape lastShape = null;
			for (Map.Entry<Double, Shape> e : values.entrySet())
			{
				double distance = e.getKey();
				if (lastdistance == null)
				{
					lastdistance = distance;
					continue;
				}
				if (distance < lastdistance)
				{
					lastdistance = distance;
				}
			}
			lastShape = values.get(lastdistance);
			
			Point2D.Double target2 = new Point2D.Double(lastShape.getBounds().getCenterX(), lastShape.getBounds().getCenterY());
			
			double newRot = Math.atan2(target2.getY() - position.getY(), target2.getX() - position.getX());

			int difx = (int) (target2.getX() - position.getX());
			int dify = (int) (target2.getY() - position.getY());
			int distance = (int) Math.sqrt((difx * difx) + (dify * dify));

			if (rotation > newRot && distance > 10)
			{
				rotation -= 0.15;
			}
			else if (rotation < newRot && distance > 10)
			{
				rotation += 0.15;
			}

			Point2D oldPosition = position;

			// face direction
			float directionX = (float) Math.cos(rotation);
			float directionY = (float) Math.sin(rotation);

			if (distance > 10)
			{
				position = new Point2D.Double((position.getX() + directionX * speed), (position.getY() + directionY * speed));
			}

			boolean possible = true;
			for (DrawObject object : objects)
			{
				if (hitTest(object))
				{
					possible = false;
				}
			}
			for (Visitor object : visitors)
			{
				if (hitTestVisitor(object) && object != this)
				{
					possible = false;
				}
			}
			if (possible == false)
			{
				position = oldPosition;
				rotation += 0.2;
			}
		}
	}

	public void fillActions()
	{
		int startTime = 540;
		int stopTime = 1260;

		while (startTime < stopTime)
		{
			int random = (int) Math.floor(Math.random() * 51);
			if (random < 30)
			{
				Point2D position = null;
				for (Event e : agenda.getEvents())
				{
					if (convertMinutesToHours(startTime) > e.getStart() && convertMinutesToHours(startTime) < e.getStop())
					{
						for (DrawObject d : objects)
						{
							if (d.getFileName().equals(e.getStage().getName()))
							{
								position = d.getPosition();
							}
						}
					}
				}
				int randomtime = (int) (40 + (Math.random() * (60 - 40)));
				if (position != null)
				{
					actions.add(new Action(position, startTime, randomtime));
				}
				startTime = startTime + randomtime;

			}
			else if (random >= 30 && random < 35)
			{
				Point2D position = null;
				for (DrawObject d : objects)
				{
					if (d.getFileName().equals("entrance"))
					{
						position = d.getPosition();
					}
				}
				if (position != null)
				{
					actions.add(new Action(position, startTime, 35));
					startTime = startTime + 35;
				}
				startTime = startTime + 35;

			}
			else if (random > 35)
			{
				Point2D position = null;
				for (DrawObject d : objects)
				{
					if (d.getFileName().equals("wc"))
					{
						position = d.getPosition();
					}
				}
				if (position != null)
				{
					actions.add(new Action(position, startTime, 35));
				}
				startTime = startTime + 35;

			}
		}
	}

	public int convertMinutesToHours(int startTime)
	{
		int time = 0;
		int hours = startTime / 60;
		int minutes = startTime % 60;
		time = (hours * 100) + minutes;
		return time;
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

	public boolean hitTest(DrawObject to2)
	{
		return (getEndX() >= to2.getPosition().getX() && getEndY() >= to2.getPosition().getY() && to2.getEndX() >= position.getX() && to2.getEndY() >= position.getY());

	}

	public boolean hitTestVisitor(Visitor v)
	{
		return (getEndX() >= v.position.getX() && getEndY() >= v.position.getY() && v.getEndX() >= position.getX() && v.getEndY() >= position.getY());
	}
}