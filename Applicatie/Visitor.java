package Applicatie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

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
		// System.out.println(position.getX());
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
		boolean hasPathBelow = false;
		for (Path p : paths)
		{
			System.out.println(position);
			System.out.println(p.getPoints());
			if (p.containsPoint(position))
			{
				hasPathBelow = true;
				break;
			}
		}

		if (hasPathBelow)
		{
			double newRot = Math.atan2(target.getY() - position.getY(), target.getX() - position.getX());

			int difx = (int) (target.getX() - position.getX());
			int dify = (int) (target.getY() - position.getY());
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
			for(Path p : paths)
			{
//				System.out.println(p.getPoints());
				break;
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