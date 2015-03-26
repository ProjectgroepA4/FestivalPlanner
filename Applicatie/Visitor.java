package Applicatie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Agenda.Agenda;
import Agenda.Event;

public class Visitor {

	Point2D position;
	double rotation;
	double scale;
	String filename;
	ArrayList<Action> actions;
	Agenda agenda;
	ArrayList<DrawObject> objects;

	public Visitor(String filename, Point2D position, Agenda agenda, ArrayList<DrawObject> objects) {
		this.position = position;
		this.filename = filename;
		scale = 1;
		rotation = 0;
		actions = new ArrayList<Action>();
		this.agenda = agenda;
		this.objects = objects;
		fillActions();
		for (Action a : actions){
			System.out.println(a.getStartTime());
		}
	}

	public void draw(Graphics2D g2) {
		Image image = new ImageIcon(filename).getImage();
		g2.drawImage(image, getTransform(), null);
	}

	private AffineTransform getTransform() {
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		tx.translate(position.getX(), position.getY());
		Image image = new ImageIcon(filename).getImage();
		tx.rotate(Math.toRadians(rotation), image.getWidth(null) / 2,
				image.getHeight(null) / 2);
		return tx;
	}

	public void update(ArrayList<DrawObject> objects, int currentTime) {
		Point2D target = new Point(-100,-100);
		for (Action a : actions){
			if (currentTime >= a.getStartTime() && currentTime < a.getStoptime()){
				target = a.getPosition();
			}
			
		}
		moveToTarget(target, objects);		
	}
	
	public void moveToTarget(Point2D target, ArrayList<DrawObject> objects){
		int x = 0;
		int y = 0;
		
		if (target.getX() > position.getX()){
			x = 2;
		} else if (target.getX() < position.getX()){
			x = - 2;
		}
		
		if (target.getY() > position.getY()){
			y = 2;
		} else if (target.getY() < position.getY()){
			y = - 2;
		}
		
		Point2D oldPosition = position;
		position = new Point2D.Double((int) (position.getX() + x),
				(int) (position.getY() + y));
		boolean possible = true;
		for (DrawObject object : objects) {
			if (hitTest(object)) {
				possible = false;
			}
		}
		if (possible == false) {
			position = oldPosition;
		}
	}

	public void fillActions() {
		int startTime = 540;
		int stopTime = 1260;

		while (startTime < stopTime) {
			int random = (int) Math.floor(Math.random() * 51);
			System.out.println(random);
			if (random < 30) {
				Point2D position = null;
				for (Event e : agenda.getEvents()) {
					if (convertMinutesToHours(startTime) > e.getStart()
							&& convertMinutesToHours(startTime) < e.getStop()) {
						for (DrawObject d : objects) {
							if (d.getFileName().equals(e.getStage().getName())) {
								position = d.getPosition();
							}
						}
					}
				}
				int randomtime = (int) (40 + (Math.random() * (60 - 40)));
				if ( position != null ){
					actions.add(new Action(position, startTime, randomtime));
				}
				startTime = startTime + randomtime;

			} else if (random >= 30 && random < 35) {
				Point2D position = null;
				for (DrawObject d : objects) {
					if (d.getFileName().equals("entrance")) {
						position = d.getPosition();
					}
				}
				if ( position != null){
					actions.add(new Action(position, startTime, 35));
					startTime = startTime + 35;
				}	
				startTime = startTime + 35;

			} else if (random > 35) {
				Point2D position = null;
				for (DrawObject d : objects) {
					if (d.getFileName().equals("wc")) {
						position = d.getPosition();
					}
				}
				if ( position != null){
					actions.add(new Action(position, startTime, 35));
				}
				startTime = startTime + 35;

			}
		}
	}

	public int convertMinutesToHours(int startTime) {
		int time = 0;
		int hours = startTime / 60;
		int minutes = startTime % 60;
		time = (hours * 100) + minutes;
		return time;
	}

	public int getEndX() {
		Image image = new ImageIcon(filename).getImage();
		return (int) (position.getX() + image.getWidth(null));
	}

	public int getEndY() {
		Image image = new ImageIcon(filename).getImage();
		return (int) (position.getY() + image.getHeight(null));
	}

	public boolean hitTest(DrawObject to2) {
		return (getEndX() >= to2.getX() && getEndY() >= to2.getY()
				&& to2.getEndX() >= position.getX() && to2.getEndY() >= position
				.getY());
	}
}
