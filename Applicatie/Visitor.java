package Applicatie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import Agenda.Agenda;
import Agenda.Event;
import Objects.DrawObject;
import Objects.Entrance;
import Objects.Path;
import Objects.Stage;

public class Visitor {

	Point2D position;
	double rotation;
	double scale;
	double speed;
	String filename;
	ArrayList<Action> actions;
	Agenda agenda;
	ArrayList<DrawObject> objects;
	ArrayList<Waypoint> waypoints;
	GregorianCalendar date;
	int target;
	int finalTarget;

	public Visitor(String filename, Point2D position, Agenda agenda,
			ArrayList<DrawObject> objects, ArrayList<Waypoint> waypoints,
			GregorianCalendar date) {
		this.position = position;
		this.filename = filename;
		this.rotation = 0;
		this.speed = 1 + Math.random() * 4;
		this.waypoints = waypoints;
		this.date = date;
		scale = 1;
		actions = new ArrayList<Action>();
		this.agenda = agenda;
		this.objects = objects;
		fillActions();
		System.out.println(actions.size());
		target = 1;
		finalTarget = 4;
	}

	public void draw(Graphics2D g2) {
		AffineTransform tx = getTransform();
		Image image = Images.getImage(filename);
		g2.drawImage(image, tx, null);
	}

	private AffineTransform getTransform() {
		Image image = Images.getImage(filename);
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		tx.translate(position.getX(), position.getY());
		tx.rotate(rotation, image.getWidth(null) / 2, image.getHeight(null) / 2);
		return tx;
	}

	public DrawObject getEntrance() {
		for (DrawObject o : objects) {
			if (o instanceof Entrance) {
				return o;
			}
		}
		return null;
	}

	public void update(ArrayList<DrawObject> objects, int currentTime,
			ArrayList<Visitor> visitors, ArrayList<Path> paths, Panel panel) {
		DrawObject target = getEntrance();
		for (Action a : actions) {
			if (currentTime >= a.getStartTime()
					&& currentTime < a.getStoptime()) {
				finalTarget = a.getWaypoint().getSelf();
			}

		}

		// moveToTarget(target, objects, visitors, paths);
		move(target, panel);
	}

	public void move(DrawObject tar, Panel panel) {
		Point2D targetPoint = panel.getWaypoint(target).getPosition();

		double newRot = Math.atan2(targetPoint.getY() - position.getY(),
				targetPoint.getX() - position.getX());

		int difx = (int) (targetPoint.getX() - position.getX());
		int dify = (int) (targetPoint.getY() - position.getY());
		int distance = (int) Math.sqrt((difx * difx) + (dify * dify));

		if (rotation > newRot && distance > 10) {
			rotation -= 0.15;
		} else if (rotation < newRot && distance > 10) {
			rotation += 0.15;
		}

		Point2D oldPosition = position;

		// face direction
		float directionX = (float) Math.cos(rotation);
		float directionY = (float) Math.sin(rotation);

		if (distance > 10) {
			position = new Point2D.Double(
					(position.getX() + directionX * speed),
					(position.getY() + directionY * speed));
		}

		boolean possible = true;
		for (DrawObject object : objects) {
			if (object instanceof Entrance || object instanceof Waypoint) {
				continue;
			}
			if (hasCollisionDO(object)) {
				possible = false;
			}
		}
		if (possible == false) {
			position = oldPosition;
			rotation += 0.2;
		}
		if (checkIfOnWaypoint(panel)) {
			// for(int i : panel.getWaypoint(target).getOptions())
			// {
			// if(i == finalTarget)
			// {
			// target = finalTarget;
			// }
			// }
			// ArrayList<int[]> options = new ArrayList<int[]>();
			HashMap<Integer, int[]> options = new HashMap<Integer, int[]>();
			for (Waypoint w : panel.getWaypoints()) {
				options.put(w.getSelf(), w.getOptions());
			}
			for (Map.Entry<Integer, int[]> e : options.entrySet()) {
				System.out.println(e.getKey());
				System.out.println("=====================");
				for (int i : e.getValue()) {
					System.out.println(i);
				}
				System.out.println("----------");
			}
		}

	}

	public boolean checkIfOnWaypoint(Panel panel) {
		if (panel.getWaypoint(target).contains(position)) {
			return true;
		}
		return false;
	}

	public void moveToTarget(DrawObject target, ArrayList<DrawObject> objects,
			ArrayList<Visitor> visitors, ArrayList<Path> paths) {
		Point2D tar = target.getPosition();
		boolean hasPathBelow = false;
		for (Path p : paths) {
			if (p.containsPoint(position)) {
				hasPathBelow = true;
				break;
			}
			Shape containsShape = p.containsPointShape(position);
			if (containsShape != null) {
				tar = new Point2D.Double(
						containsShape.getBounds().getCenterX(), containsShape
								.getBounds().getCenterY());
			}
		}

		if (hasPathBelow) {
			double newRot = Math.atan2(tar.getY() - position.getY(), tar.getX()
					- position.getX());

			int difx = (int) (tar.getX() - position.getX());
			int dify = (int) (tar.getY() - position.getY());
			int distance = (int) Math.sqrt((difx * difx) + (dify * dify));

			if (rotation > newRot && distance > 10) {
				rotation -= 0.15;
			} else if (rotation < newRot && distance > 10) {
				rotation += 0.15;
			}

			Point2D oldPosition = position;

			// face direction
			float directionX = (float) Math.cos(rotation);
			float directionY = (float) Math.sin(rotation);

			if (distance > 10) {
				position = new Point2D.Double((position.getX() + directionX
						* speed), (position.getY() + directionY * speed));
			}

			boolean possible = true;
			for (DrawObject object : objects) {
				if (hasCollisionDO(object)) {
					possible = false;
				}
			}
			for (Visitor object : visitors) {
				if (hasCollision(object) && object != this) {
					possible = false;
				}
			}
			if (possible == false) {
				position = oldPosition;
				rotation += 0.2;
			}
		} else {
			HashMap<Double, Shape> values = new HashMap<Double, Shape>();
			for (Path p : paths) {
				for (Shape s : p.getPath()) {
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
					for (Point2D.Double point : points) {
						double distance = Point2D.distance(point.getX(),
								point.getY(), position.getX(), position.getY());
						if (lastdistance == null) {
							lastdistance = distance;
							continue;
						}
						if (distance < lastdistance) {
							lastdistance = distance;
						}
					}
					values.put(lastdistance, s);

				}
			}
			Double lastdistance = null;
			Shape lastShape = null;
			for (Map.Entry<Double, Shape> e : values.entrySet()) {
				double distance = e.getKey();
				if (lastdistance == null) {
					lastdistance = distance;
					continue;
				}
				if (distance < lastdistance) {
					lastdistance = distance;
				}
			}
			lastShape = values.get(lastdistance);

			Point2D.Double target2 = new Point2D.Double(lastShape.getBounds()
					.getCenterX(), lastShape.getBounds().getCenterY());

			double newRot = Math.atan2(target2.getY() - position.getY(),
					target2.getX() - position.getX());

			int difx = (int) (target2.getX() - position.getX());
			int dify = (int) (target2.getY() - position.getY());
			int distance = (int) Math.sqrt((difx * difx) + (dify * dify));

			if (rotation > newRot && distance > 10) {
				rotation -= 0.15;
			} else if (rotation < newRot && distance > 10) {
				rotation += 0.15;
			}

			Point2D oldPosition = position;

			// face direction
			float directionX = (float) Math.cos(rotation);
			float directionY = (float) Math.sin(rotation);

			if (distance > 10) {
				position = new Point2D.Double((position.getX() + directionX
						* speed), (position.getY() + directionY * speed));
			}

			boolean possible = true;
			for (DrawObject object : objects) {
				if (hasCollisionDO(object)) {
					possible = false;
				}
			}
			for (Visitor object : visitors) {
				if (hasCollision(object) && object != this) {
					possible = false;
				}
			}
			if (possible == false) {
				position = oldPosition;
				rotation += 0.2;
			}
		}
	}

	public void fillActions() {
		int startTime = 540;
		int stopTime = 1260;

		while (startTime < stopTime) {
			int random = (int) Math.floor(Math.random() * 51);
			if (random < 40) {
				Point2D position = null;
				DrawObject targetStage = null;
				ArrayList<Event> posEvents = new ArrayList<Event>();
				for (Event e : agenda.getEvents()) {
					if (startTime >= e.getStart() && startTime < e.getStop()) {
						posEvents.add(e);
					}
				}

				if (posEvents.size() != 0) {
					int r = (int) Math.floor(Math.random() * posEvents.size());
					Event evs = posEvents.get(r);
					for (DrawObject d : objects) {
						if (d.getFileName().equals("stage")) {
							Stage s = (Stage) d;
							if (s.getStage().getName()
									.equals(evs.getStage().getName())) {
								position = s.getPosition();
								targetStage = d;
							}
						}
					}
				}

				int randomtime = (int) (40 + (Math.random() * (60 - 40)));
				if (position != null) {
					Waypoint w = getClosedWaypoint(position);
					actions.add(new Action(position, startTime, randomtime,
							targetStage, w));
				}
				startTime = startTime + randomtime;

			} else if (random >= 40 && random < 45) {
				Point2D position = null;
				DrawObject targetStage = null;
				ArrayList<DrawObject> foodPlaces = new ArrayList<DrawObject>();
				for (DrawObject d : objects) {
					if (d.getFileName().equals("food")) {
						foodPlaces.add(d);
					}
				}

				if (foodPlaces.size() != 0) {
					int r = (int) Math.floor(Math.random() * foodPlaces.size());
					position = foodPlaces.get(r).getPosition();
					targetStage = foodPlaces.get(r);
					Waypoint w = getClosedWaypoint(position);
					actions.add(new Action(position, startTime, 35,
							targetStage, w));
					startTime = startTime + 20;
				}
				startTime = startTime + 35;

			} else if (random > 45) {
				Point2D position = null;
				DrawObject targetStage = null;
				ArrayList<DrawObject> toilets = new ArrayList<DrawObject>();
				for (DrawObject d : objects) {
					if (d.getFileName().equals("wc")) {
						toilets.add(d);
					}
				}
				if (toilets.size() != 0) {
					int r = (int) Math.floor(Math.random() * toilets.size());
					position = toilets.get(r).getPosition();
					targetStage = toilets.get(r);
					Waypoint w = getClosedWaypoint(position);
					actions.add(new Action(position, startTime, 35,
							targetStage, w));
					startTime = startTime + 20;
				}
				startTime = startTime + 35;

			}
		}
	}

	public Waypoint getClosedWaypoint(Point2D point) {
		Waypoint waypoint = null;
		int distance = 10000;
		for (Waypoint w : waypoints) {
			if (w.getPosition().distance(point) < distance) {
				waypoint = w;
				distance = (int) w.getPosition().distance(point);
			}
		}
		return waypoint;
	}

	public boolean hasCollisionDO(DrawObject object) {
		Image image = Images.getImage(filename);
		Image image2 = Images.getImage(object.getFileName());
		Rectangle recta = new Rectangle((int) position.getX(),
				(int) position.getY(), image.getWidth(null),
				image.getHeight(null));
		Rectangle rectb = new Rectangle((int) object.getPosition().getX(),
				(int) object.getPosition().getY(), image2.getWidth(null),
				image2.getHeight(null));

		Area a = new Area(recta);
		Area b = new Area(rectb);

		AffineTransform transA = new AffineTransform();
		transA.rotate(rotation, position.getX(), position.getY());

		AffineTransform transB = new AffineTransform();
		transB.rotate(Math.toRadians(object.getRotation()), object
				.getPosition().getX(), object.getPosition().getY());

		Area aa = a.createTransformedArea(transA);
		Area bb = b.createTransformedArea(transB);

		if (bb.intersects(aa.getBounds2D())) {
			return true;
		}
		return false;
	}

	public boolean hasCollision(Visitor v) {
		Image image = Images.getImage(filename);
		Rectangle a = new Rectangle((int) position.getX(),
				(int) position.getY(), image.getWidth(null),
				image.getHeight(null));
		Rectangle b = new Rectangle((int) v.position.getX(),
				(int) v.position.getY(), image.getWidth(null),
				image.getHeight(null));

		Area aa = new Area(a);
		Area bb = new Area(b);

		AffineTransform af = new AffineTransform();
		af.rotate(rotation, position.getX(), position.getY());

		AffineTransform bf = new AffineTransform();
		bf.rotate(v.rotation, v.position.getX(), v.position.getY());

		Area ra = aa.createTransformedArea(af);
		Area rb = bb.createTransformedArea(bf);

		if (ra.intersects(rb.getBounds2D())) {
			return true;
		}
		return false;
	}
}